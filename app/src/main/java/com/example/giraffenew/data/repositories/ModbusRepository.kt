package com.example.giraffenew.data.repositories

import android.util.Log
import com.example.giraffenew.data.ModbusDatabase
import com.example.giraffenew.data.entities.*
import com.example.giraffenew.network.ModbusClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.emptyMap

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
 *
 * Класс является Singleton и использует внедрение зависимостей через конструктор:
    * ModbusDatabase - для работы с локальной базой данных
    * ModbusClient - для взаимодействия с Modbus-устройствами
    * Json (опционально) - для парсинга конфигураций
 *
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ВАЖНО!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
 * - Но в текущей архитектуре ModbusRepository, registerCache является StateFlow, который хранит текущее состояние всего кэша.
 *   И он эмитит каждый раз, когда обновляется любой регистр.
 *  Варианты:
 *    а) Изменить ModbusRepository так, чтобы она предоставляла отдельный поток для подмножества регистров (это выходит за рамки задачи).
 *    б) В ViewModel сделать фильтрацию: преобразовать поток так, чтобы он эмитил только когда изменился один из нужных регистров.
 */
@Singleton
class ModbusRepository @Inject constructor(
    private val database: ModbusDatabase,
    private val modbusClient: ModbusClient,
    private val json: Json = Json { ignoreUnknownKeys = true }
) {

//Использует CoroutineScope с Dispatchers.IO для фоновых операций и SupervisorJob для независимой обработки ошибок в дочерних корутинах.
    private val repositoryScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    // DAOs
    private val configDao = database.registerConfigurationDao()
    private val logDao = database.modbusLogDao()

/**
1. Состояния и потоки данных
 */
    //кэш значений регистров в памяти (StateFlow)
    private val _registerCache = MutableStateFlow<Map<Int, RegisterValue>>(emptyMap())
    val registerCache: StateFlow<Map<Int, RegisterValue>> = _registerCache.asStateFlow()

    /** НАЧАЛО
    1.1 Добавим переменные отслеживания состояния кэш для отдельных конфигураций
     */
    // Кэши для каждой конфигурации (Применяется в случае работы с несколькими конфмгурациями)
    private val _registerCaches = mutableMapOf<String, MutableStateFlow<Map<Int, RegisterValue>>>()
    private val configurationFlows = mutableMapOf<String, StateFlow<Map<Int, RegisterValue>>>()

    // Активная UI конфигурация
    private val _activeUiConfiguration = MutableStateFlow<String?>(null)



    // Job для главного потока чтения
    private var mainReadingJob: Job? = null
    // Job для UI потока чтения
    private var uiReadingJob: Job? = null
    /**
     * 1.1 КОНЕЦ
     */

    /**
    НАЧАЛО 1.2 Добавляем переменную для хранения конфигурации по умолчанию (список RegisterInfo из DefaultRegisterConfiguration).
     */
    private val _defaultRegisters = MutableStateFlow<List<RegisterInfo>>(emptyList())
    private val _defaultRegisterCache = MutableStateFlow<Map<Int, RegisterValue>>(emptyMap())
    val defaultRegisterCache: StateFlow<Map<Int, RegisterValue>> = _defaultRegisterCache.asStateFlow()
    /**
    КОНЕЦ 1.2
     */

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
        /**
         * 2. Инициализация репозитория
         * При создании:
         * Запускает инициализацию в отдельной корутине
         * Настраивает подписку на изменения конфигурации (hot-reload)
         */
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

    init {// Загрузка default-конфигурации при инициализации
        loadDefaultConfiguration()
        startDefaultRegistersReading()
    }

    private fun loadDefaultConfiguration() {
        try {
            val config = json.decodeFromString<RegistersConfiguration>(
                DefaultRegisterConfiguration.DEFAULT_CONFIG_JSON
            )
            _defaultRegisters.value = config.registers
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load default configuration", e)
        }
    }

    private fun startDefaultRegistersReading() {
        repositoryScope.launch {
            while (isActive) {
                try {
                    readDefaultRegisters()
                } catch (e: Exception) {
                    Log.e(TAG, "Error reading default registers", e)
                }
                    delay(900) // Чтение раз в секунду
            }
        }
    }
    private suspend fun readDefaultRegisters() {
        val registers = _defaultRegisters.value
        if (registers.isEmpty()) return

        val ranges = groupRegistersIntoRanges(registers)
        val results = mutableListOf<RegisterValue>()

        for (range in ranges) {
            try {
                val batchResult = modbusClient.readRegisters(range.start, range.count)
                results.addAll(batchResult.results.map { result ->
                    val registerInfo = registers.find { it.address == result.address }
                    RegisterValue.fromReadResult(result, registerInfo)
                })
            } catch (e: Exception) {
                Log.e(TAG, "Error reading range ${range.start}-${range.start + range.count - 1}", e)
            }
        }

        // Обновление кэша только при изменениях
        updateDefaultCache(results)
    }

    private fun updateDefaultCache(newValues: List<RegisterValue>) {
        val currentCache = _defaultRegisterCache.value
        val newCache = currentCache.toMutableMap()
        var hasChanges = false

        for (newValue in newValues) {
            val currentValue = currentCache[newValue.address]
            if (currentValue == null ||
                currentValue.value != newValue.value ||
                currentValue.isValid != newValue.isValid) {
                newCache[newValue.address] = newValue
                hasChanges = true
            }
        }

        if (hasChanges) {
            _defaultRegisterCache.value = newCache
        }
    }



    /**
     * Инициализация репозитория
     * Загружает активную конфигурацию из БД
     * Инициализирует базу default-данными при необходимости
     * Обновляет статус репозитория
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


    /** !!!!!!!!!!!!!!!!!!!!!!!НОВОЕ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     * 2.1  Модификация ModbusRepository
     * Добавим поддержку нескольких конфигураций и потоков чтения:
     */

    // Инициализация конфигураций
    init {
        registerConfiguration(BeepSoundRegisterConfiguration.NAME_BEEP_SOUND, BeepSoundRegisterConfiguration.REGISTERS)
        registerConfiguration(TopBarRegisterConfiguration.NAME_TOP_BAR, TopBarRegisterConfiguration.REGISTERS)
        registerConfiguration(UiHomeScreenRegisterConfiguration.NAME_HOME, UiHomeScreenRegisterConfiguration.REGISTERS)
        registerConfiguration(UiInitialSetupRegisterConfiguration.NAME_INITIAL_SETUP, UiInitialSetupRegisterConfiguration.REGISTERS)
        registerConfiguration(UIServiceMenuRegisterConfiguration.NAME_SERVICE_MENU, UIServiceMenuRegisterConfiguration.REGISTERS)
        registerConfiguration(UISettingLoadCapacityConfiguration.NAME_SETTING_LOAD_CAPACITY, UISettingLoadCapacityConfiguration.REGISTERS)
        registerConfiguration(UISettingSensorsConfiguration.NAME_SETTING_SENSORS, UISettingSensorsConfiguration.REGISTERS)
        registerConfiguration(UICoordinateProtectionConfiguration.NAME_COORDINATE_PROTECTION, UICoordinateProtectionConfiguration.REGISTERS)

        // Запуск главного потока чтения
        startMainConfigurationReading()

        // Запуск UI потока чтения (реагирует на изменения активной конфигурации)
        startUiConfigurationReading()


    }

    private fun registerConfiguration(name: String, registers: List<RegisterInfo>) {
        _registerCaches[name] = MutableStateFlow(emptyMap())
        configurationFlows[name] = _registerCaches[name]!!.asStateFlow()
    }

    // Запуск потока для главной конфигурации
    private fun startMainConfigurationReading() {
        mainReadingJob = repositoryScope.launch {
            while (isActive) {
                val startTime = System.currentTimeMillis()
                readConfigurationRegisters(TopBarRegisterConfiguration.NAME_TOP_BAR)
                val elapsed = System.currentTimeMillis() - startTime
                delay(maxOf(0, 500 - elapsed)) // Точный интервал 0,5 секунды
            }
        }
    }

    // Запуск потока для UI конфигурации
    @OptIn(FlowPreview::class)
    private fun startUiConfigurationReading() {
        repositoryScope.launch {
            _activeUiConfiguration
                .debounce(100) // Добавляем debounce для быстрых переключений.debounce(100) // Добавляем debounce для быстрых переключений
                .collect { configName ->
                Log.d(TAG, "Active UI configuration changed to: $configName")
                // Останавливаем предыдущее чтение
                uiReadingJob?.cancel()

                if (configName != null) {
                    Log.d(TAG, "Starting UI reading job for: $configName")
                    // Запускаем чтение для активной конфигурации
                    uiReadingJob = repositoryScope.launch {
                        Log.d(TAG, "UI reading job started for: $configName")
                        while (isActive && _activeUiConfiguration.value == configName) {
                            val startTime = System.currentTimeMillis()
                            readConfigurationRegisters(configName)
                            val elapsed = System.currentTimeMillis() - startTime
                            delay(maxOf(0, 500 - elapsed)) // Читаем 2 раза в секунду
                        }
                        Log.d(TAG, "UI reading job stopped for: $configName")
                    }
                } else {Log.d(TAG, "No active UI configuration, stopping reading job")}

            }
        }
    }


    // Функция чтения регистров для конкретной конфигурации
    private suspend fun readConfigurationRegisters(configName: String) {
        Log.d(TAG, "Reading configuration: $configName")
        val registers = when (configName) {
            BeepSoundRegisterConfiguration.NAME_BEEP_SOUND -> BeepSoundRegisterConfiguration.REGISTERS
            TopBarRegisterConfiguration.NAME_TOP_BAR -> TopBarRegisterConfiguration.REGISTERS
            UiHomeScreenRegisterConfiguration.NAME_HOME -> UiHomeScreenRegisterConfiguration.REGISTERS
            UiInitialSetupRegisterConfiguration.NAME_INITIAL_SETUP -> UiInitialSetupRegisterConfiguration.REGISTERS
            UIServiceMenuRegisterConfiguration.NAME_SERVICE_MENU -> UIServiceMenuRegisterConfiguration.REGISTERS
            UISettingLoadCapacityConfiguration.NAME_SETTING_LOAD_CAPACITY -> UISettingLoadCapacityConfiguration.REGISTERS
            UISettingSensorsConfiguration.NAME_SETTING_SENSORS -> UISettingSensorsConfiguration.REGISTERS
            UICoordinateProtectionConfiguration.NAME_COORDINATE_PROTECTION -> UICoordinateProtectionConfiguration.REGISTERS
            else -> { Log.w(TAG, "Unknown configuration: $configName")
                emptyList()}
        }

        if (registers.isEmpty()) return

        val ranges = groupRegistersIntoRanges(registers)
        val results = mutableListOf<RegisterValue>()

        for (range in ranges) {
            try {
                val batchResult = modbusClient.readRegisters(range.start, range.count)
                results.addAll(batchResult.results.map { result ->
                    val registerInfo = registers.find { it.address == result.address }
                    RegisterValue.fromReadResult(result, registerInfo)
                })
            } catch (e: Exception) {
                Log.e(TAG, "Error reading range ${range.start}-${range.start + range.count - 1}", e)
            }
        }

        // Обновляем кэш только при изменениях
        updateConfigurationCache(configName, results)

    }

    // Обновление кэша конфигурации только при изменениях
    private fun updateConfigurationCache(configName: String, newValues: List<RegisterValue>) {
        val currentCache = _registerCaches[configName]!!.value
        val newCache = currentCache.toMutableMap()
        var hasChanges = false

        for (newValue in newValues) {
            val currentValue = currentCache[newValue.address]
            if (currentValue == null ||
                currentValue.value != newValue.value ||
                currentValue.isValid != newValue.isValid) {
                newCache[newValue.address] = newValue
                hasChanges = true
            }
        }

        if (hasChanges) {
            _registerCaches[configName]!!.value = newCache
        }
    }

    // Метод для получения потока данных конкретной конфигурации

    fun getConfigurationFlow(configName: String): StateFlow<Map<Int, RegisterValue>> {
        return configurationFlows[configName] ?: run {
            Log.w(TAG, "Requested unknown configuration: $configName")
            // Создаем временный поток для неизвестных конфигураций
            MutableStateFlow<Map<Int, RegisterValue>>(emptyMap()).asStateFlow()
        }
    }

    // Метод для установки активной UI конфигурации
    fun setActiveUiConfiguration(configName: String?) {
        Log.d(TAG, "Requested config: $configName, current: ${_activeUiConfiguration.value}")
        if (_activeUiConfiguration.value != configName) {
            Log.d(TAG, "Actually changing config to: $configName")
            _activeUiConfiguration.value = configName
        } else {
            Log.d(TAG, "Config already set, skipping")
        }
    }


    /**
     * 3. Работа с Modbus
     * Прочитать все активные регистры и записать их в пакет BatchReadResult
     * 19.08.25 Необходимо модифицировать readAllActiveRegisters() так, чтобы он обновлял кэш только при реальном изменении значений регистров:
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
            // Группируем регистры по непрерывным диапазонам для batch пакета
            val ranges = groupRegistersIntoRanges(registers)
            val allValues = mutableListOf<RegisterValue>()
            val errors = mutableListOf<String>()

            for (range in ranges) {
                try {
                    // Выполняем batch-чтение через ModbusClient
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

            // Умное обновление кэша
            val currentCache = _registerCache.value
            val updatedMap = currentCache.toMutableMap()
            var hasChanges = false

            for (newValue in allValues) {
                val currentValue = currentCache[newValue.address]

                // Проверяем, изменилось ли значение или статус ошибки
                val isChanged = when {
                    currentValue == null -> true
                    currentValue.value != newValue.value -> true
                    currentValue.isValid != newValue.isValid -> true
                    currentValue.error != newValue.error -> true
                    else -> false
                }

                if (isChanged) {
                    updatedMap[newValue.address] = newValue
                    hasChanges = true
                }
            }

            // Обновляем кэш только при реальных изменениях
            if (hasChanges) {
                _registerCache.value = updatedMap
            }

            // Логируем успешное чтение
            if (allValues.isNotEmpty()) {
                logDebug("Registers read successfully", """{"count": ${allValues.size}, "errors": ${errors.size}, "changes": $hasChanges}""")
            }

            // Возвращаем BatchReadResult с результатами и ошибками
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
     * метод принимает список объектов `RegisterInfo` и возвращает список объектов `AddressRange`.
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

    private val writeMutex = Mutex() // Для синхронизации записи
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
    suspend fun updateConnectionParams(ip: String, port: Int = 502, slaveId: Int = 255, timeout: Int = 2000) {
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

    //результат пакетного чтения
    data class BatchReadResult(
        val registerValues: List<RegisterValue>,
        val errors: List<String>,
        val timestamp: Long,
        val duration: Long = 0
    )

    // список диапазонов адресов
    private data class AddressRange(
        val start: Int,
        val count: Int
    )

}

/**
Особенности реализации
Оптимизация чтения
Метод groupRegistersIntoRanges группирует регистры в непрерывные диапазоны для минимизации количества Modbus-запросов.

Потоковая архитектура
Использование StateFlow позволяет:

Автоматически обновлять UI при изменениях

Кэшировать данные в памяти

Обеспечивать реактивность

Обработка ошибок
Комплексная система обработки ошибок на всех уровнях:

Статус репозитория

Детализированные ошибки в результатах операций

Логирование всех ошибок

Горячая перезагрузка конфигурации
Подписка на изменения конфигурации в БД позволяет обновлять параметры работы без перезапуска приложения.
 */