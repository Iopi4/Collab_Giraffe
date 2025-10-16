package com.example.giraffenew.network

import android.util.Log
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.time.delay
import java.nio.ByteBuffer
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Промышленный Modbus TCP клиент для непрерывной работы 24/7
 * 
 * Извлечен и улучшен из существующего ModbusViewModel
 * 
 * Особенности:
 * - Thread-safe операции
 * - Automatic reconnection с exponential backoff
 * - Batch reading для оптимизации производительности
 * - Industrial-grade error handling
 * - Connection pooling и resource management
 * - Timeout handling для всех операций
 * 
 * @param ipAddress IP адрес Modbus TCP сервера
 * @param port TCP порт (обычно 502)
 * @param slaveId Slave ID устройства (обычно 255)
 * @param timeout Timeout для TCP операций в миллисекундах
 */
@Singleton
class ModbusClient @Inject constructor() {

    // Connection parameters
    private var ipAddress: String = "192.168.1.88"
    private var port: Int = 502
    private var slaveId: Int = 255
    private var timeout: Int = 2000
    
    // TCP connection components
    private var socket: Socket? = null
    private var input: ByteReadChannel? = null
    private var output: ByteWriteChannel? = null
    private var selectorManager: SelectorManager? = null
    
    // Connection state management
    private val _connectionState = MutableStateFlow<ConnectionState>(ConnectionState.Disconnected)
    val connectionState: StateFlow<ConnectionState> = _connectionState.asStateFlow()

    // ДОБАВИМ ПРОСТОЙ БУЛЕВ СТАТУС ДЛЯ UI
    private val _isConnected = MutableStateFlow(false)
    val isConnected: StateFlow<Boolean> = _isConnected.asStateFlow()


    // Добавляем флаг для отслеживания работы сервиса
    private val isServiceRunning = AtomicBoolean(true)

    fun setServiceRunning(running: Boolean) {
        isServiceRunning.set(running)
    }

    // Reconnection logic
    private var reconnectJob: Job? = null
    private var reconnectAttempts = 0
    private val maxReconnectAttempts = 10
    private val baseReconnectDelay = 1000L // Base delay в миллисекундах
    
    // Coroutine scope для внутренних операций
    private val clientScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    /**
     * !!!!!!!!!!!!!!!НОВОЕ!!!!!!!!!!!!!!!!
     * Добавление глобальной синхронизации в ModbusClient
     */
    private val globalMutex = Mutex() // Добавляем глобальный мьютекс
    private val readMutex = Mutex()
    private val writeMutex = Mutex()

    companion object {
        private const val TAG = "ModbusClient"
        private const val MAX_REGISTER_BATCH_SIZE = 120 // Максимальный размер batch для чтения
        private const val MODBUS_FUNCTION_READ_HOLDING = 3.toByte()
        private const val MODBUS_FUNCTION_WRITE_SINGLE = 6.toByte()
    }
    
    /**
     * Состояния подключения
     */
    sealed class ConnectionState {
        object Disconnected : ConnectionState()
        object Connecting : ConnectionState()
        object Connected : ConnectionState()
        data class Error(val message: String, val canRetry: Boolean = true) : ConnectionState()
        object Reconnecting : ConnectionState()
    }
    
    /**
     * Результат операции чтения регистра
     */
    sealed class RegisterReadResult {
        abstract val address: Int // Добавляем абстрактное свойство
        data class Success(
            override val address: Int, // Реализуем свойство
            val value: Int,
            val timestamp: Long = System.currentTimeMillis()
        ) : RegisterReadResult()

        data class Error(
            override val address: Int, // Реализуем свойство
            val error: String,
            val timestamp: Long = System.currentTimeMillis()
        ) : RegisterReadResult()
    }
    
    /**
     * Результат batch чтения регистров
     */
    data class BatchReadResult(
        val results: List<RegisterReadResult>,
        val timestamp: Long = System.currentTimeMillis(),
        val duration: Long = 0
    )
    
    /**
     * Обновить параметры подключения
     */
    fun updateConnectionParams(
        newIpAddress: String,
        newPort: Int = 502,
        newSlaveId: Int = 255,
        newTimeout: Int = 2000
    ) {
        if (newIpAddress != ipAddress || newPort != port || newSlaveId != slaveId || newTimeout != timeout) {
            ipAddress = newIpAddress
            port = newPort
            slaveId = newSlaveId
            timeout = newTimeout
            
            // Переподключаемся с новыми параметрами если были подключены
            if (_connectionState.value == ConnectionState.Connected) {
                clientScope.launch {
                    disconnect()
                    delay(100) // Небольшая задержка
                    connect()
                }
            }
        }
    }

    /**
     * Обновляем все методы изменения состояния подключения для синхронизации булева статуса isConnected
     */
    private fun updateConnectionState(newState: ConnectionState) {
        _connectionState.value = newState
        _isConnected.value = newState == ConnectionState.Connected
    }

    
    /**
     * Установить соединение с Modbus TCP сервером
     * Добавим метод отслеживания статуса подключения
     */
    suspend fun connect(): Boolean = withContext(Dispatchers.IO) {
        if (_connectionState.value == ConnectionState.Connected) {
            return@withContext true
        }
        
        //_connectionState.value = ConnectionState.Connecting
        updateConnectionState(ConnectionState.Connecting)
        Log.d(TAG, "Connecting to Modbus server at $ipAddress:$port")
        
        try {
            // Создаем selector manager
            selectorManager = SelectorManager(Dispatchers.IO)
            
            // Устанавливаем TCP соединение с timeout
            socket = withTimeoutOrNull(timeout.toLong()) {
                aSocket(selectorManager!!).tcp().connect(ipAddress, port)
            }
            
            if (socket == null) {
                throw Exception("Connection timeout after ${timeout}ms")
            }
            
            // Открываем каналы для чтения и записи
            input = socket!!.openReadChannel()
            output = socket!!.openWriteChannel(autoFlush = true)
            
           // _connectionState.value = ConnectionState.Connected
            updateConnectionState(ConnectionState.Connected)
            reconnectAttempts = 0 // Сбрасываем счетчик попыток

            _lastActivityTime.set(System.currentTimeMillis()) // Сбрасываем таймер активности

            // ЗАПУСКАЕМ ЛЕГКОВЕСНЫЙ МОНИТОРИНГ СТАТУСА
            startConnectionStatusMonitoring()

            Log.i(TAG, "Successfully connected to Modbus server at $ipAddress:$port")
            return@withContext true
            
        } catch (e: Exception) {
            Log.e(TAG, "Failed to connect to Modbus server: ${e.message}", e)

            //_connectionState.value = ConnectionState.Error(
            updateConnectionState(ConnectionState.Error(
                message = "Connection failed: ${e.message}",
                canRetry = true
            )
            )

            // Очистка ресурсов при ошибке
            closeResources()
            
            return@withContext false
        }
    }
    
    /**
     * Закрыть соединение
     * добавляем метод для остановки отслеживания статуса соединения
     */
    suspend fun disconnect() = withContext(Dispatchers.IO) {
        Log.d(TAG, "Disconnecting from Modbus server")
        
        reconnectJob?.cancel()
        reconnectJob = null

        stopConnectionStatusMonitoring() // Останавливаем легковесный мониторинг
        
        //_connectionState.value = ConnectionState.Disconnected
        updateConnectionState(ConnectionState.Disconnected)
        closeResources()
        
        Log.i(TAG, "Disconnected from Modbus server")
    }
    
    /**
     * Очистить все ресурсы
     */
    private suspend fun closeResources() {
        try {
            input?.cancel()
            output?.close()
            socket?.close()
            selectorManager?.close()
        } catch (e: Exception) {
            Log.w(TAG, "Error while closing resources: ${e.message}")
        } finally {
            input = null
            output = null
            socket = null
            selectorManager = null
        }
    }
    
    /**
     * Прочитать один регистр
     */
//    suspend fun readRegister(address: Int): RegisterReadResult = withContext(Dispatchers.IO) {
//        return@withContext readRegisters(address, 1).results.firstOrNull()
//            ?: RegisterReadResult.Error(address, "No result returned")
//    }
    
    /**
     * Прочитать диапазон регистров с автоматическим batch разделением
     * НОВОЕ. Добавление globalMutex для глобальной синхронизации в ModbusClient
     */
    suspend fun readRegisters(startAddress: Int, count: Int): BatchReadResult =
        globalMutex.withLock { // Защищаем все операции
            withContext(Dispatchers.IO) {

        val startTime = System.currentTimeMillis()
        
        if (_connectionState.value != ConnectionState.Connected) {
            return@withContext BatchReadResult(
                results = listOf(RegisterReadResult.Error(startAddress, "Not connected to server")),
                timestamp = startTime
            )
        }
        
        val results = mutableListOf<RegisterReadResult>()

                var hasConnectionError = false
        try {
            // Разбиваем большой запрос на batch'и для оптимизации
            var currentAddress = startAddress
            var remainingCount = count
            
            while (remainingCount > 0) {
                val batchSize = minOf(remainingCount, MAX_REGISTER_BATCH_SIZE)
                
                try {
                    val batchResults = readRegisterBatch(currentAddress, batchSize)
                    results.addAll(batchResults)
                    
                } catch (e: Exception) {
                    Log.e(TAG, "Error reading batch starting at $currentAddress: ${e.message}")
                    
                    // Если batch failed, создаем error results для всего batch
                    for (i in 0 until batchSize) {
                        results.add(
                            RegisterReadResult.Error(
                                address = currentAddress + i,
                                error = "Batch read failed: ${e.message}"
                            )
                        )
                    }

                    // Проверяем не нужно ли переподключиться
                    if (isConnectionError(e)) {
                        Log.w(TAG, "Connection error detected, starting reconnection")
                        hasConnectionError = true
                        startReconnection()
                        break
                    }
                }
                
                currentAddress += batchSize
                remainingCount -= batchSize
                
                // Небольшая задержка между batch'ами
                if (remainingCount > 0) {
                    delay(10)
                }
            }
            /**
             * Улучшение обработки ошибок соединения
             */
        } catch (e: Exception) {
            Log.e(TAG, "Error in readRegisters: ${e.message}")
            if (isConnectionError(e)) {
                hasConnectionError = true
                startReconnection()
            }
        }

                // Если была ошибка соединения, обновляем статус
                if (hasConnectionError) {
                    updateConnectionState(ConnectionState.Error("Connection error during read", true))
                }

        val endTime = System.currentTimeMillis()
        return@withContext BatchReadResult(
            results = results,
            timestamp = startTime,
            duration = endTime - startTime
        )
    }}
    
    /**
     * Прочитать batch регистров (внутренний метод)
     * Обновляем методы чтения/записи для отслеживания активности
     * УЛУЧШЕННАЯ ОБРАБОТКА "BROKEN PIPE"

    private suspend fun readRegisterBatch(startAddress: Int, count: Int): List<RegisterReadResult> {
    try {
    val request = buildModbusReadRequest(startAddress, count)
    output?.writeFully(request) ?: throw Exception("Output channel is null")

    val response = ByteArray(256)
    val bytesRead = withTimeoutOrNull(timeout.toLong()) {
    input?.readAvailable(response, 0, response.size) ?: 0
    } ?: throw Exception("Read timeout")

    if (bytesRead == 0) {
    throw Exception("No data received")
    }

    // ОБНОВЛЯЕМ ВРЕМЯ АКТИВНОСТИ ПРИ УСПЕШНОЙ ОПЕРАЦИИ
    _lastActivityTime.set(System.currentTimeMillis())

    return parseModbusResponse(response.copyOfRange(0, bytesRead), startAddress)
    } catch (e: Exception) {
    // При ошибке НЕ обновляем время активности, чтобы детектор неактивности сработал
    // _lastActivityTime.set(System.currentTimeMillis()) // УБИРАЕМ
    throw e
    }
    }
     */
    private suspend fun readRegisterBatch(startAddress: Int, count: Int): List<RegisterReadResult> {
        try {
            // Проверяем соединение перед операцией
            if (_connectionState.value != ConnectionState.Connected) {
                throw Exception("Not connected to server")
            }

            val request = buildModbusReadRequest(startAddress, count)

            // Убрали synchronized, потому что мы уже под globalMutex
            output?.writeFully(request) ?: throw Exception("Output channel is null")

            val response = ByteArray(256)
            val bytesRead = withTimeoutOrNull(timeout.toLong()) {
                input?.readAvailable(response, 0, response.size) ?: 0
            } ?: throw Exception("Read timeout")

            if (bytesRead == 0) {
                throw Exception("No data received")
            }

            // ОБНОВЛЯЕМ ВРЕМЯ АКТИВНОСТИ ПРИ УСПЕШНОЙ ОПЕРАЦИИ
            _lastActivityTime.set(System.currentTimeMillis())

            return parseModbusResponse(response.copyOfRange(0, bytesRead), startAddress)
        } catch (e: Exception) {
            // ПРИ "BROKEN PIPE" НЕМЕДЛЕННО ЗАПУСКАЕМ ПЕРЕПОДКЛЮЧЕНИЕ
            if (e.message?.contains("Broken pipe", ignoreCase = true) == true) {
                Log.w(TAG, "Broken pipe detected, forcing reconnection")
                startReconnection()
            }
            // При ошибке тоже обновляем время активности (попытка связи)
            _lastActivityTime.set(System.currentTimeMillis())
            throw e
        }
    }


    
    /**
     * Записать значение в одиночный регистр
     * НОВОЕ. Добавление globalMutex для глобальной синхронизации в ModbusClient
     */
    suspend fun writeRegister(address: Int, value: Int): RegisterReadResult =
        globalMutex.withLock {
            withContext(Dispatchers.IO) {
        if (_connectionState.value != ConnectionState.Connected) {
            return@withContext RegisterReadResult.Error(address, "Not connected to server")
        }
        
        try {
            val request = buildModbusWriteRequest(address, value)
            
            // Отправляем запрос
            output?.writeFully(request) ?: throw Exception("Output channel is null")
            
            // Читаем ответ подтверждения
            val response = ByteArray(256)
            val bytesRead = withTimeoutOrNull(timeout.toLong()) {
                input?.readAvailable(response, 0, response.size) ?: 0
            } ?: throw Exception("Write timeout")
            
            if (bytesRead == 0) {
                throw Exception("No confirmation received")
            }
            
            // Проверяем успешность записи
            val success = parseWriteResponse(response.copyOfRange(0, bytesRead), address, value)
            
            return@withContext if (success) {
                RegisterReadResult.Success(address, value)
            } else {
                RegisterReadResult.Error(address, "Write operation failed")
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Error writing register $address: ${e.message}")
            
            if (isConnectionError(e)) {
                startReconnection()
            }
            
            return@withContext RegisterReadResult.Error(address, "Write failed: ${e.message}")
        }
    }}
    
    /**
     * Построить Modbus TCP запрос для чтения регистров
     */
    private fun buildModbusReadRequest(startAddress: Int, count: Int): ByteArray {
        val transactionId: Short = 1
        val protocolId: Short = 0
        val unitId = slaveId.toByte()
        val functionCode = MODBUS_FUNCTION_READ_HOLDING
        
        // MBAP Header (7 bytes)
        val mbapHeader = ByteBuffer.allocate(7).apply {
            putShort(transactionId)
            putShort(protocolId)
            putShort((1 + 1 + 2 + 2).toShort()) // Длина PDU
            put(unitId)
        }.array()
        
        // PDU (5 bytes)
        val pdu = ByteBuffer.allocate(5).apply {
            put(functionCode)
            putShort(startAddress.toShort())
            putShort(count.toShort())
        }.array()
        
        return mbapHeader + pdu
    }
    
    /**
     * Построить Modbus TCP запрос для записи регистра
     */
    private fun buildModbusWriteRequest(address: Int, value: Int): ByteArray {
        val transactionId: Short = 1
        val protocolId: Short = 0
        val unitId = slaveId.toByte()
        val functionCode = MODBUS_FUNCTION_WRITE_SINGLE
        
        // MBAP Header (7 bytes)
        val mbapHeader = ByteBuffer.allocate(7).apply {
            putShort(transactionId)
            putShort(protocolId)
            putShort((1 + 1 + 2 + 2).toShort()) // Длина PDU
            put(unitId)
        }.array()
        
        // PDU (5 bytes)
        val pdu = ByteBuffer.allocate(5).apply {
            put(functionCode)
            putShort(address.toShort())
            putShort(value.toShort())
        }.array()
        
        return mbapHeader + pdu
    }
    
    /**
     * Парсинг ответа Modbus для чтения регистров
     */
    private fun parseModbusResponse(response: ByteArray, startAddress: Int): List<RegisterReadResult> {
        val results = mutableListOf<RegisterReadResult>()
        
        try {
            if (response.size < 9) {
                throw Exception("Response too short: ${response.size} bytes")
            }

            // Проверяем наличие ошибки в ответе (старший бит функции установлен)
            val functionCode = response[7].toInt() and 0xFF
            if (functionCode and 0x80 != 0) {
                val errorCode = response[8].toInt() and 0xFF
                val errorMsg = when (errorCode) {
                    1 -> "Illegal Function"
                    2 -> "Illegal Data Address"
                    3 -> "Illegal Data Value"
                    4 -> "Server Device Failure"
                    else -> "Unknown error"
                }

                throw Exception("Modbus exception: $errorMsg (code $errorCode)")
            }

            val byteCount = response[8].toInt() and 0xFF
            
            if (response.size < 9 + byteCount) {
                throw Exception("Incomplete response: expected ${9 + byteCount} bytes, got ${response.size}")
            }
            
            for (i in 0 until byteCount step 2) {
                val registerIndex = i / 2
                val address = startAddress + registerIndex
                
                val highByte = response[9 + i].toInt() and 0xFF
                val lowByte = response[9 + i + 1].toInt() and 0xFF
                val value = (highByte shl 8) or lowByte
                
                results.add(RegisterReadResult.Success(address, value))
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing Modbus response: ${e.message}")
            results.add(RegisterReadResult.Error(startAddress, "Parse error: ${e.message}"))
        }
        
        return results
    }
    
    /**
     * Парсинг ответа для записи регистра
     */
    private fun parseWriteResponse(response: ByteArray, expectedAddress: Int, expectedValue: Int): Boolean {
        try {
            if (response.size < 12) {
                return false
            }
            
            val address = ((response[8].toInt() and 0xFF) shl 8) or (response[9].toInt() and 0xFF)
            val value = ((response[10].toInt() and 0xFF) shl 8) or (response[11].toInt() and 0xFF)
            
            return address == expectedAddress && value == expectedValue
            
        } catch (e: Exception) {
            Log.e(TAG, "Error parsing write response: ${e.message}")
            return false
        }
    }
    
    /**
     * Проверить является ли ошибка связанной с соединением
     */
    private fun isConnectionError(exception: Exception): Boolean {
        val message = exception.message?.lowercase() ?: ""
        return message.contains("connection") ||
                message.contains("timeout") ||
                message.contains("closed") ||
                message.contains("reset") ||
                message.contains("broken pipe") || // Явно добавляем Broken pipe
                message.contains("software caused connection abort") ||
                exception is java.net.ConnectException ||
                exception is java.net.SocketTimeoutException ||
                exception is java.net.SocketException
    }
    
    /**
     * Начать процесс автоматического переподключения
     * добавляем контроль переподключений
     * УЛУЧШЕННЫЙ МЕТОД ПЕРЕПОДКЛЮЧЕНИЯ


    private fun startReconnection() {
    if (reconnectJob?.isActive == true) {
    return // Уже переподключаемся
    }

    //_connectionState.value = ConnectionState.Reconnecting
    updateConnectionState(ConnectionState.Reconnecting)

    reconnectJob = clientScope.launch {
    while (reconnectAttempts < maxReconnectAttempts) {
    reconnectAttempts++

    // Exponential backoff с jitter
    val delay = (baseReconnectDelay * (1L shl (reconnectAttempts - 1))).coerceAtMost(30000L)
    val jitter = (0..1000).random()

    Log.i(TAG, "Reconnection attempt $reconnectAttempts/$maxReconnectAttempts in ${delay + jitter}ms")
    delay(delay + jitter)

    try {
    closeResources()

    if (connect()) {
    Log.i(TAG, "Reconnection successful after $reconnectAttempts attempts")
    return@launch
    }

    } catch (e: Exception) {
    Log.w(TAG, "Reconnection attempt $reconnectAttempts failed: ${e.message}")
    }
    }

    Log.e(TAG, "Failed to reconnect after $maxReconnectAttempts attempts")
    //_connectionState.value = ConnectionState.Error(
    updateConnectionState(ConnectionState.Error(
    "Failed to reconnect after $maxReconnectAttempts attempts",
    canRetry = false
    )
    )
    }
    }
     */
    private fun startReconnection() {
        if (reconnectJob?.isActive == true) {
            Log.d(TAG, "Reconnection already in progress")
            return // Уже переподключаемся
        }

        Log.i(TAG, "Starting reconnection process")
        updateConnectionState(ConnectionState.Reconnecting)

        reconnectJob = clientScope.launch {
            try {
                // Сначала закрываем соединение
                closeResources()

                while (reconnectAttempts < maxReconnectAttempts && isServiceRunning.get()) {
                    reconnectAttempts++

                    // Exponential backoff с jitter
                    val delay = (baseReconnectDelay * (1L shl (reconnectAttempts - 1))).coerceAtMost(30000L)
                    val jitter = (0..1000).random()

                    Log.i(TAG, "Reconnection attempt $reconnectAttempts/$maxReconnectAttempts in ${delay + jitter}ms")
                    delay(delay + jitter)

                    try {
                        if (connect()) {
                            Log.i(TAG, "Reconnection successful after $reconnectAttempts attempts")
                            return@launch
                        }

                    } catch (e: Exception) {
                        Log.w(TAG, "Reconnection attempt $reconnectAttempts failed: ${e.message}")
                    }
                }

                if (reconnectAttempts >= maxReconnectAttempts) {
                    Log.e(TAG, "Failed to reconnect after $maxReconnectAttempts attempts")
                    updateConnectionState(ConnectionState.Error(
                        "Failed to reconnect after $maxReconnectAttempts attempts",
                        canRetry = true
                    ))
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error in reconnection process", e)
                updateConnectionState(ConnectionState.Error(
                    "Reconnection process failed: ${e.message}",
                    canRetry = true
                ))
            }
        }
    }



    
    /**
     * Принудительно перезапустить попытки переподключения
     */
    suspend fun forceReconnect() {
        reconnectAttempts = 0
        disconnect()
        delay(1000)
        connect()
    }
    
    /**
     * Получить информацию о текущем соединении
     */
    fun getConnectionInfo(): Map<String, Any?> {
        return mapOf(
            "ip" to ipAddress,
            "port" to port,
            "slaveId" to slaveId,
            "timeout" to timeout,
            "state" to _connectionState.value::class.simpleName,
            "reconnectAttempts" to reconnectAttempts,
            "isConnected" to (_connectionState.value == ConnectionState.Connected)
        )
    }


    // Добавляем легковесный способ отслеживания активности соединения
    private val _lastActivityTime = AtomicLong(System.currentTimeMillis())
    private var connectionStatusJob: Job? = null

    // Запускаем мониторинг статуса подключения (без блокирующих операций)
    private fun startConnectionStatusMonitoring() {
        connectionStatusJob = clientScope.launch {
            while (coroutineContext.isActive) {
                updateConnectionStatusBasedOnActivity()
                delay(2000L) // Проверяем каждые 2 секунды
            }
        }
    }

    private fun stopConnectionStatusMonitoring() {
        connectionStatusJob?.cancel()
    }

    private fun updateConnectionStatusBasedOnActivity() {
        val currentTime = System.currentTimeMillis()
        val lastActivity = _lastActivityTime.get()
        val timeSinceLastActivity = currentTime - lastActivity

        // Если активность была менее 10 секунд назад - считаем соединение активным
        val isActive = timeSinceLastActivity < 10000L

        // Обновляем статус только если он изменился
        if (isActive != _isConnected.value) {
            _isConnected.value = isActive
            if (!isActive) {
                Log.w(TAG, "Connection inactive for ${timeSinceLastActivity}ms")
            }
        }
    }



    /**
     * Освобождение ресурсов при уничтожении клиента
     * Добавим остановку контроля мониторинга статуса подключения
     */
    fun cleanup() {
        clientScope.cancel()
        stopConnectionStatusMonitoring()
        clientScope.launch {
            disconnect()
        }
    }

    // Добавляем метод для принудительного обновления статуса активности
    fun updateActivity() {
        _lastActivityTime.set(System.currentTimeMillis())
    }
}