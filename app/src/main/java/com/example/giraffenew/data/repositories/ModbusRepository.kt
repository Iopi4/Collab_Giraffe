package com.example.giraffenew.data.repositories

import android.util.Log
import com.example.giraffenew.data.ModbusDatabase
import com.example.giraffenew.data.entities.*
import com.example.giraffenew.network.ModbusClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository для управления данными Modbus системы
 * 
 * Объединяет:
 * - Конфигурацию регистров из Room database
 * - Живые данные регистров из ModbusClient
 * - Логирование операций
 * - Hot-reload конфигурации
 * - In-memory кэш для быстрого доступа
 * 
 * Реализует Repository pattern для разделения concerns
 * между data layer и business logic
 */
@Singleton
class ModbusRepository @Inject constructor(
    private val database: ModbusDatabase,
    private val modbusClient: ModbusClient,
    private val json: Json = Json { ignoreUnknownKeys = true }
) {
    
    private val repositoryScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    // DAOs
    private val configDao = database.registerConfigurationDao()
    private val logDao = database.modbusLogDao()
    
    // In-memory cache для live данных регистров
    private val _registerCache = MutableStateFlow<Map<Int, RegisterValue>>(emptyMap())
    val registerCache: StateFlow<Map<Int, RegisterValue>> = _registerCache.asStateFlow()
    
    // Parsed конфигурация регистров
    private val _activeRegisters = MutableStateFlow<List<RegisterInfo>>(emptyList())
    val activeRegisters: StateFlow<List<RegisterInfo>> = _activeRegisters.asStateFlow()
    
    // Статус репозитория
    private val _repositoryStatus = MutableStateFlow<RepositoryStatus>(RepositoryStatus.Initializing)
    val repositoryStatus: StateFlow<RepositoryStatus> = _repositoryStatus.asStateFlow()
    
    companion object {
        private const val TAG = "ModbusRepository"
    }
    
    /**
     * Статусы репозитория
     */
    sealed class RepositoryStatus {
        object Initializing : RepositoryStatus()
        object Ready : RepositoryStatus()
        data class Error(val message: String) : RepositoryStatus()
    }
    
    /**
     * Данные регистра с метаинформацией
     */
    data class RegisterValue(
        val address: Int,
        val value: Int,
        val hexValue: String,
        val timestamp: Long,
        val name: String = "",
        val type: String = "",
        val isValid: Boolean = true,
        val error: String? = null
    ) {
        companion object {
            fun fromReadResult(result: ModbusClient.RegisterReadResult, registerInfo: RegisterInfo? = null): RegisterValue {
                return when (result) {
                    is ModbusClient.RegisterReadResult.Success -> RegisterValue(
                        address = result.address,
                        value = result.value,
                        hexValue = result.value.toString(16).uppercase().padStart(4, '0'),
                        timestamp = result.timestamp,
                        name = registerInfo?.name ?: "",
                        type = registerInfo?.type ?: "",
                        isValid = true
                    )
                    is ModbusClient.RegisterReadResult.Error -> RegisterValue(
                        address = result.address,
                        value = 0,
                        hexValue = "0000",
                        timestamp = result.timestamp,
                        name = registerInfo?.name ?: "",
                        type = registerInfo?.type ?: "",
                        isValid = false,
                        error = result.error
                    )
                }
            }
        }
    }
    
    init {
        // Инициализация репозитория
        repositoryScope.launch {
            initializeRepository()
        }
        
        // Hot-reload конфигурации при изменениях
        repositoryScope.launch {
            configDao.getActiveConfigurationFlow()
                .collect { config ->
                    config?.let { loadConfiguration(it) }
                }
        }
    }
    
    /**
     * Инициализация репозитория
     */
    private suspend fun initializeRepository() {
        try {
            _repositoryStatus.value = RepositoryStatus.Initializing
            
            // Инициализируем базу данных с default данными если нужно
            com.example.giraffenew.data.DatabaseInitializer.initialize(database)
            
            // Загружаем активную конфигурацию
            val activeConfig = configDao.getActiveConfiguration()
            if (activeConfig != null) {
                loadConfiguration(activeConfig)
            } else {
                Log.w(TAG, "No active configuration found")
            }
            
            _repositoryStatus.value = RepositoryStatus.Ready
            logInfo("Repository initialized successfully")
            
        } catch (e: Exception) {
            Log.e(TAG, "Failed to initialize repository", e)
            _repositoryStatus.value = RepositoryStatus.Error("Initialization failed: ${e.message}")
            logError("Repository initialization failed", e.message ?: "Unknown error")
        }
    }
    
    /**
     * Загрузить конфигурацию регистров
     */
    private suspend fun loadConfiguration(config: RegisterConfiguration) {
        try {
            val registersConfig = json.decodeFromString<RegistersConfiguration>(config.jsonConfiguration)
            _activeRegisters.value = registersConfig.registers
            
            Log.i(TAG, "Loaded configuration '${config.name}' with ${registersConfig.registers.size} registers")
            logInfo("Configuration loaded", """{"config_name": "${config.name}", "registers_count": ${registersConfig.registers.size}}""")
            
        } catch (e: Exception) {
            Log.e(TAG, "Failed to parse configuration: ${e.message}")
            logError("Configuration parsing failed", e.message ?: "Unknown error")
        }
    }
    
    // === Configuration Management ===
    
    /**
     * Получить все конфигурации
     */
    fun getAllConfigurations(): Flow<List<RegisterConfiguration>> {
        return configDao.getAllConfigurations()
    }
    
    /**
     * Получить активную конфигурацию
     */
    fun getActiveConfiguration(): Flow<RegisterConfiguration?> {
        return configDao.getActiveConfigurationFlow()
    }
    
    /**
     * Создать новую конфигурацию
     */
    suspend fun createConfiguration(name: String, registers: List<RegisterInfo>): Result<Long> {
        return try {
            val config = RegisterConfiguration(
                name = name,
                jsonConfiguration = json.encodeToString(RegistersConfiguration.serializer(), RegistersConfiguration(registers)),
                isActive = false
            )
            
            val id = configDao.insert(config)
            logInfo("Configuration created", """{"name": "$name", "id": $id, "registers": ${registers.size}}""")
            
            Result.success(id)
            
        } catch (e: Exception) {
            Log.e(TAG, "Failed to create configuration", e)
            logError("Configuration creation failed", e.message ?: "Unknown error")
            Result.failure(e)
        }
    }
    
    /**
     * Активировать конфигурацию
     */
    suspend fun activateConfiguration(configId: Long): Result<Unit> {
        return try {
            // Создаем backup текущей активной конфигурации
            configDao.backupActiveConfiguration()
            
            // Активируем новую конфигурацию
            configDao.setActiveConfiguration(configId)
            
            logInfo("Configuration activated", """{"config_id": $configId}""")
            Result.success(Unit)
            
        } catch (e: Exception) {
            Log.e(TAG, "Failed to activate configuration $configId", e)
            logError("Configuration activation failed", e.message ?: "Unknown error")
            Result.failure(e)
        }
    }
    
    /**
     * Обновить конфигурацию
     */
    suspend fun updateConfiguration(config: RegisterConfiguration): Result<Unit> {
        return try {
            val updatedConfig = config.copy(updatedAt = System.currentTimeMillis())
            configDao.update(updatedConfig)
            
            logInfo("Configuration updated", """{"config_id": ${config.id}, "name": "${config.name}"}""")
            Result.success(Unit)
            
        } catch (e: Exception) {
            Log.e(TAG, "Failed to update configuration ${config.id}", e)
            logError("Configuration update failed", e.message ?: "Unknown error")
            Result.failure(e)
        }
    }
    
    // === Register Data Operations ===
    
    /**
     * Прочитать все активные регистры
     */
    suspend fun readAllActiveRegisters(): BatchReadResult {
        val registers = _activeRegisters.value
        
        if (registers.isEmpty()) {
            return BatchReadResult(
                registerValues = emptyList(),
                errors = listOf("No active registers configured"),
                timestamp = System.currentTimeMillis()
            )
        }
        
        return try {
            // Группируем регистры по непрерывным диапазонам для batch чтения
            val ranges = groupRegistersIntoRanges(registers)
            val allValues = mutableListOf<RegisterValue>()
            val errors = mutableListOf<String>()
            
            for (range in ranges) {
                try {
                    val batchResult = modbusClient.readRegisters(range.start, range.count)
                    
                    // Конвертируем результаты в RegisterValue с метаинформацией
                    val values = batchResult.results.map { result ->
                        val registerInfo = registers.find { it.address == result.address }
                        RegisterValue.fromReadResult(result, registerInfo)
                    }
                    
                    allValues.addAll(values)
                    
                    // Логируем ошибки чтения
                    batchResult.results.filterIsInstance<ModbusClient.RegisterReadResult.Error>()
                        .forEach { error ->
                            errors.add("Register ${error.address}: ${error.error}")
                            logError("Register read failed", error.error, error.address)
                        }
                        
                } catch (e: Exception) {
                    val errorMsg = "Failed to read range ${range.start}-${range.start + range.count - 1}: ${e.message}"
                    errors.add(errorMsg)
                    Log.e(TAG, errorMsg)
                }
            }
            
            // Обновляем cache
            val cacheMap = allValues.associateBy { it.address }
            _registerCache.value = cacheMap
            
            // Логируем успешное чтение
            if (allValues.isNotEmpty()) {
                logDebug("Registers read successfully", """{"count": ${allValues.size}, "errors": ${errors.size}}""")
            }
            
            BatchReadResult(
                registerValues = allValues,
                errors = errors,
                timestamp = System.currentTimeMillis()
            )
            
        } catch (e: Exception) {
            Log.e(TAG, "Failed to read active registers", e)
            logError("Batch read failed", e.message ?: "Unknown error")
            
            BatchReadResult(
                registerValues = emptyList(),
                errors = listOf("Batch read failed: ${e.message}"),
                timestamp = System.currentTimeMillis()
            )
        }
    }
    
    /**
     * Группировка регистров в непрерывные диапазоны для оптимизации
     */
    private fun groupRegistersIntoRanges(registers: List<RegisterInfo>): List<AddressRange> {
        if (registers.isEmpty()) return emptyList()
        
        val sortedAddresses = registers.map { it.address }.sorted()
        val ranges = mutableListOf<AddressRange>()
        
        var currentStart = sortedAddresses.first()
        var currentEnd = currentStart
        
        for (i in 1 until sortedAddresses.size) {
            val address = sortedAddresses[i]
            
            if (address == currentEnd + 1) {
                // Непрерывный диапазон
                currentEnd = address
            } else {
                // Разрыв, создаем новый диапазон
                ranges.add(AddressRange(currentStart, currentEnd - currentStart + 1))
                currentStart = address
                currentEnd = address
            }
        }
        
        // Добавляем последний диапазон
        ranges.add(AddressRange(currentStart, currentEnd - currentStart + 1))
        
        return ranges
    }
    
    /**
     * Получить конкретный регистр из cache
     */
    fun getRegisterValue(address: Int): RegisterValue? {
        return _registerCache.value[address]
    }
    
    /**
     * Получить все регистры из cache как Flow
     */
    fun getRegisterValuesFlow(): Flow<Map<Int, RegisterValue>> {
        return registerCache
    }
    
    /**
     * Записать значение в регистр
     */
    suspend fun writeRegister(address: Int, value: Int): Result<Unit> {
        return try {
            val result = modbusClient.writeRegister(address, value)
            
            when (result) {
                is ModbusClient.RegisterReadResult.Success -> {
                    logInfo("Register write successful", """{"address": $address, "value": $value}""")
                    Result.success(Unit)
                }
                is ModbusClient.RegisterReadResult.Error -> {
                    logError("Register write failed", result.error, address)
                    Result.failure(Exception(result.error))
                }
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Failed to write register $address", e)
            logError("Register write failed", e.message ?: "Unknown error", address)
            Result.failure(e)
        }
    }
    
    // === Modbus Client Management ===
    
    /**
     * Обновить параметры подключения Modbus
     */
    fun updateConnectionParams(ip: String, port: Int = 502, slaveId: Int = 255, timeout: Int = 2000) {
        modbusClient.updateConnectionParams(ip, port, slaveId, timeout)
        logInfo("Connection parameters updated", """{"ip": "$ip", "port": $port, "slaveId": $slaveId, "timeout": $timeout}""")
    }
    
    /**
     * Подключиться к Modbus серверу
     */
    suspend fun connect(): Result<Unit> {
        return try {
            val success = modbusClient.connect()
            if (success) {
                logInfo("Modbus connection established")
                Result.success(Unit)
            } else {
                logError("Modbus connection failed", "Connection returned false")
                Result.failure(Exception("Connection failed"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to connect to Modbus server", e)
            logError("Modbus connection failed", e.message ?: "Unknown error")
            Result.failure(e)
        }
    }
    
    /**
     * Отключиться от Modbus сервера
     */
    suspend fun disconnect() {
        modbusClient.disconnect()
        logInfo("Modbus connection closed")
    }
    
    /**
     * Получить статус подключения
     */
    fun getConnectionState(): StateFlow<ModbusClient.ConnectionState> {
        return modbusClient.connectionState
    }
    
    // === Logging Operations ===
    
    /**
     * Получить последние логи
     */
    fun getRecentLogs(limit: Int = 1000): Flow<List<ModbusLog>> {
        return logDao.getRecentLogs(limit)
    }
    
    /**
     * Получить логи ошибок за последний час
     */
    suspend fun getRecentErrors(): List<ModbusLog> {
        val oneHourAgo = System.currentTimeMillis() - (60 * 60 * 1000L)
        return logDao.getRecentErrors(oneHourAgo)
    }
    
    /**
     * Поиск логов
     */
    suspend fun searchLogs(searchText: String, limit: Int = 200): List<ModbusLog> {
        return logDao.searchLogs(searchText, limit)
    }
    
    /**
     * Очистка старых логов
     */
    suspend fun performLogRotation() {
        logDao.performLogRotation()
        logInfo("Log rotation completed")
    }
    
    // === Utility Methods ===
    
    private suspend fun logDebug(message: String, details: String? = null, address: Int? = null) {
        logDao.insert(ModbusLog(
            level = LogLevel.DEBUG,
            category = LogCategory.READING,
            message = message,
            details = details,
            registerAddress = address
        ))
    }
    
    private suspend fun logInfo(message: String, details: String? = null, address: Int? = null) {
        logDao.insert(ModbusLog(
            level = LogLevel.INFO,
            category = LogCategory.SERVICE,
            message = message,
            details = details,
            registerAddress = address
        ))
    }
    
    private suspend fun logError(message: String, error: String, address: Int? = null) {
        logDao.insert(ModbusLog(
            level = LogLevel.ERROR,
            category = LogCategory.READING,
            message = message,
            details = """{"error": "$error"}""",
            registerAddress = address,
            errorCode = "REGISTER_ERROR"
        ))
    }
    
    /**
     * Cleanup resources
     */
    fun cleanup() {
        modbusClient.cleanup()
    }
    
    // === Data Classes ===
    
    data class BatchReadResult(
        val registerValues: List<RegisterValue>,
        val errors: List<String>,
        val timestamp: Long,
        val duration: Long = 0
    )
    
    private data class AddressRange(
        val start: Int,
        val count: Int
    )
}