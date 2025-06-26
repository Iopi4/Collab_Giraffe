import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.nio.ByteBuffer


// Serializable data classes for HTTP requests and responses
@Serializable
data class RegisterData(
    val registers: List<Int>,
    val timestamp: Long = System.currentTimeMillis()
)

@Serializable
data class ServerResponse(
    val status: String
)

class ModbusViewModel : ViewModel() {
    // HTTP client for making API requests
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    //Socket - Это файл в который пишется данные. Имя файла это IP и  Port
    private var socket: Socket? = null

    //interface ByteReadChannel - Канал для асинхронного чтения последовательностей байтов.
    // Это канал с одним считывателем. Операции на этом канале не могут быть вызваны одновременно.
    private var input: ByteReadChannel? = null

    //interface ByteWriteChannel - Канал для асинхронной записи последовательностей байтов.
    // Это канал с одним писателем.
    //Операции на этом канале не могут быть вызваны одновременно, если в описании явно не указано иное.
    // Исключениями являются close и flush.
    private var output: ByteWriteChannel? = null

    //Интерфейс SelectorManager позволяет Selectable ждать для SelectInterest.
    //Selectable - Выбираемая сущность с выбираемым каналом NIO, подписками interestOps.
    //SelectInterest- Выберите вид интереса. (READ, WRITE, ACCEPT, CONNECT, CLOSE)
    private lateinit var selectorManager: SelectorManager
    //Private lateinit var в Kotlin — это объявление переменной с отложенной инициализацией (lateinit).
    //Модификатор lateinit позволяет отложить инициализацию свойства до того момента,
    //когда появится возможность присвоить ему значение.

    private var isReading = true
    private var isWrite = true

    //Использование StateFlow для управления состоянием.
    //Это компонент библиотеки Kotlin Coroutines, который предназначен для хранения изменяемого состояния.
    //Это состояние может меняться со временем, и MutableStateFlow гарантирует, что все подписчики
    //получают обновления при изменении состояния.



    private val _registerValues = MutableStateFlow<List<Int>>(emptyList())
    val registerValues: StateFlow<List<Int>> = _registerValues.asStateFlow()

    private val _writeValue = MutableStateFlow("0")
    val writeValue: StateFlow<String> = _writeValue.asStateFlow()

    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused.asStateFlow()

    private val _ipAddress = MutableStateFlow("192.168.1.88")
    val ipAddress: StateFlow<String> = _ipAddress.asStateFlow()

    private val _port = MutableStateFlow(502)
    val port: StateFlow<Int> = _port.asStateFlow()

    private val _slaveId = MutableStateFlow(255)// по умолчанию для моего сервера
    val slaveId: StateFlow<Int> = _slaveId.asStateFlow()

    private val _remoteServerUrl = MutableStateFlow("https://monitor.giraffecrane.ru")
    val remoteServerUrl: StateFlow<String> = _remoteServerUrl.asStateFlow()

    private val _startAddress = MutableStateFlow(0)
    val startAddress: StateFlow<Int> = _startAddress.asStateFlow()

    private val _registerCount = MutableStateFlow(2)
    val registerCount: StateFlow<Int> = _registerCount.asStateFlow()

    private val _connectionStatus =
        MutableStateFlow<ConnectionStatus>(ConnectionStatus.Disconnected)
    val connectionStatus: StateFlow<ConnectionStatus> = _connectionStatus.asStateFlow()

    /**
     * Обновить параметры подключения
     */
    fun updateIpAddress(newIpAddress: String) {
        _ipAddress.value = newIpAddress
    }

    fun updatePort(newPort: Int) {
        _port.value = newPort
    }

    fun updateSlaveId(newSlaveId: Int) {
        _slaveId.value = newSlaveId
    }

    fun updateStartAddress(newStartAddress: Int) {
        _startAddress.value = newStartAddress
    }

    fun updateRegisterCount(newRegisterCount: Int) {
        _registerCount.value = newRegisterCount
    }

    fun updateWriteValue(newValue: String) {
        _writeValue.value = newValue
    }

    fun updateRemoteServerUrl(newUrl: String) {
        _remoteServerUrl.value = newUrl
    }

    /**
     * Send register data to remote server
     */
    private suspend fun sendRegistersToRemoteServer(registers: List<Int>) {
        _remoteServerStatus.value = RemoteServerStatus.Sending
        try {
            val response: HttpResponse = httpClient.post("${remoteServerUrl.value}/api/realtime") {
                contentType(ContentType.Application.Json)
                setBody(RegisterData(registers))
            }

            if (response.status.isSuccess()) {
                val serverResponse = response.body<ServerResponse>()
                if (serverResponse.status == "success") {
                    _remoteServerStatus.value = RemoteServerStatus.Success
                    Log.d("MODBUS", "Successfully sent ${registers.size} registers to remote server")
                } else {
                    _remoteServerStatus.value = RemoteServerStatus.Error("Server returned non-success status: ${serverResponse.status}")
                    Log.e("MODBUS", "Server returned non-success status: ${serverResponse.status}")
                }
            } else {
                _remoteServerStatus.value = RemoteServerStatus.Error("HTTP error: ${response.status}")
                Log.e("MODBUS", "HTTP error when sending registers: ${response.status}")
            }
        } catch (e: Exception) {
            _remoteServerStatus.value = RemoteServerStatus.Error(e.message ?: "Unknown error")
            Log.e("MODBUS", "Error sending registers to remote server: ${e.message}")
        }
    }


    /**Add commentMore actions
     * Stop reading Modbus registers
     */
    fun stopReadingRegisters() {
        isReading = false
    }

    fun stopWriteRegisters() {
        isWrite = false
    }

    /**Add commentMore actions
     * Connect to the Modbus TCP server
     */
    fun connectToServer() {
        //viewModelScope в Kotlin — это встроенная область видимости (CoroutineScope) для корутин внутри объекта ViewModel.
        //Эта область позволяет управлять жизненным циклом корутин, запущенных в рамках ViewModel.
        //Корутины, созданные в viewModelScope, автоматически отменяются при уничтожении соответствующего объекта ViewModel.
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(
                "MODBUS",
                "connect to server at IP: ${ipAddress.value.toString()}, Port: ${port.value.toString()}"
            )
            try {
                selectorManager = SelectorManager(Dispatchers.IO)
                        socket = aSocket(selectorManager).tcp().connect(ipAddress.value, port.value)
                input = socket?.openReadChannel()
                output = socket?.openWriteChannel(autoFlush = true)
                _connectionStatus.value = ConnectionStatus.Connected
            } catch (e: Exception) {
                e.printStackTrace()
                        _connectionStatus.value = ConnectionStatus.Error(e.message ?: "Connection failed")
            }
        }
    }

    /**Add commentMore actions
     * Disconnect from the Modbus TCP server
     */
    fun disconnectServer() {
        Log.d("MODBUS", "disconnect from server")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                socket?.close()
                selectorManager.close()
                _connectionStatus.value = ConnectionStatus.Disconnected
            } catch (e: Exception) {
                e.printStackTrace()
                _connectionStatus.value =
                    ConnectionStatus.Error(e.message ?: "Disconnection failed")
            }
        }
    }

    /**Add commentMore actions
     * Периодически начинать читать регистры Modbus, используя сохраненные параметры
     */
    fun startReadingRegisters() {
        Log.d(
            "MODBUS",
            "start reading registers ${startAddress.value.toString()} - ${registerCount.value.toString()}"
        )
        isReading = true
        viewModelScope.launch(Dispatchers.IO) {
            while (isReading) {
                //Если в try не происходит исключительная ситуация, то тело try выполняется полностью.
                //Если же в try происходит исключение, поток выполнения программы прерывает выполнение тела try
                //и уходит в блок catch, где это исключение обрабатывается.
                try {
                    //Запрос
                    val request = buildModbusRequest()
                    output?.writeFully(request)

                    //Ответ
                    val response = ByteArray(256)
                    //readAvailable - Считывает все доступные байты в буфер dst и немедленно возвращается или приостанавливается, если доступных байтов нет
                    val bytesRead = input?.readAvailable(response, 0, response.size) ?: 0

                    if (bytesRead > 0) {
                        val registerValues = parseRegisterValues(response.copyOfRange(0, bytesRead))
                        _registerValues.value = registerValues
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _connectionStatus.value =
                        ConnectionStatus.Error(e.message ?: "Error reading registers")
                    isReading = false
                }
                //delay(1000) // Repeat every second
                stopReadingRegisters()
        }
        }
    }

    fun startWriteRegister() {
        Log.d(
            "MODBUS",
            "start write register ${startAddress.value.toString()} - ${writeValue.value.toString()}"
        )
        isWrite = true
        viewModelScope.launch(Dispatchers.IO) {
            while (isWrite) {
                //Если в try не происходит исключительная ситуация, то тело try выполняется полностью.
                //Если же в try происходит исключение, поток выполнения программы прерывает выполнение тела try
                //и уходит в блок catch, где это исключение обрабатывается.
                try {
                    //Запрос
                    val request = buildModbusRequestWrite()
                    output?.writeFully(request)

                    //Ответ
                    val response = ByteArray(256)
                    //readAvailable - Считывает все доступные байты в буфер dst и немедленно возвращается или приостанавливается, если доступных байтов нет
                    val bytesRead = input?.readAvailable(response, 0, response.size) ?: 0

                    if (bytesRead > 0) {
                        val registerValues = parseRegisterValuesWrite(response.copyOfRange(0, bytesRead))
                        _registerValues.value = registerValues
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _connectionStatus.value =
                        ConnectionStatus.Error(e.message ?: "Error reading registers")
                    isWrite = false
                }
                stopWriteRegisters()
            }
        }

    }



    /**
     * Создание запроса Modbus TCP для «Чтения регистров хранения»
     */
    private fun buildModbusRequest(): ByteArray {
        //  Из сообщения Modbus RTU удаляется SlaveID адрес в начале и CRC контрольная сумма в конце,
        //что образует PDU, Protocol Data Unit.
        //  К началу получившегося сообщения PDU добавляется новый 7-байтовый заголовок,
        //который называется MBAP Header (Modbus Application Header).
        // Этот заголовок имеет следующие данные:
        //- Transaction Identifier(Идентификатор транзакции): 2 байта устанавливаются Master, чтобы однозначно
        //идентифицировать каждый запрос. Может быть любыми. Эти байты повторятся устройством
        //Slave в ответе, поскольку ответы устройства Slave не всегда могут быть получены
        //в том же порядке, что и запросы.
        val transactionId: Short = 1

        //- Protocol Identifier (Идентификатор протокола): 2 байта устанавливаются Master,
        //всегда будут = 00 00, что соответствует протоколу Modbus.
        val protocolId: Short = 0

        //- Unit Identifier (Идентификатор блока или адрес устройства): 1 байт устанавливается Master.
        //Повторяется устройством Slave для однозначной идентификации устройства Slave.
        val unitId = slaveId.value.toByte()

        val functionCode: Byte = 3
        val startAddress = this.startAddress.value.toShort()
        val registerCount = this.registerCount.value.toShort()

        // MBAP Header (Modbus Application Header) 7 байт:
        //Функция putShort() позволяет записать значение типа Short в объект.
        val mbapHeader = ByteBuffer.allocate(7).apply {
            putShort(transactionId) //Идентификатор транзакции
            putShort(protocolId) //Идентификатор протокола
            putShort((1 + 1 + 2 + 2).toShort()) // Длина (6 байтов идут следом)
            put(unitId) //Адрес устройства 255. Настраивается в Modbus TCP Slave Configuration контроллера (Slave устройства)
        }.array()

        // PDU, Protocol Data Unit. (5 bytes).
        val pdu = ByteBuffer.allocate(5).apply {
            put(functionCode)//Функциональный код (03 читаем Analog Output Holding Registers)
            putShort(startAddress) //Адрес первого регистра
            putShort(registerCount) //Количество требуемых регистров
        }.array()

        return mbapHeader + pdu
    }

    /**
     * Анализ ответа Modbus для извлечения значений регистров
     */
    private fun parseRegisterValues(response: ByteArray): List<Int> {
        return try {
            val byteCount = response[8].toInt() // Количество байтов для регистров в ответе
            val registerValues = mutableListOf<Int>()
            //until - это ключевое слово, которое позволяет создать полуоткрытый диапазон.
            //Такой диапазон включает начальное значение, но не включает конечное.
            for (i in 0 until byteCount step 2) {
                val highByte = response[9 + i].toInt() and 0xFF //Значение старшего разряда регистра (первого слева на право)
                val lowByte = response[9 + i + 1].toInt() and 0xFF //Значение младшего разряда регистра (второго слева на право)
                val value = (highByte shl 8) or lowByte //Объединение значения старшего и младшего регистра
                registerValues.add(value) //Присвоение полученного значения в переменную registerValues
            }
            registerValues
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }


    /**
     * Создание запроса Modbus TCP для «Записи регистров в устройство Slave»
     */
    private fun buildModbusRequestWrite(): ByteArray {
        //  Из сообщения Modbus RTU удаляется SlaveID адрес в начале и CRC контрольная сумма в конце,
        //что образует PDU, Protocol Data Unit.
        //  К началу получившегося сообщения PDU добавляется новый 7-байтовый заголовок,
        //который называется MBAP Header (Modbus Application Header).
        // Этот заголовок имеет следующие данные:
        //- Transaction Identifier(Идентификатор транзакции): 2 байта устанавливаются Master, чтобы однозначно
        //идентифицировать каждый запрос. Может быть любыми. Эти байты повторятся устройством
        //Slave в ответе, поскольку ответы устройства Slave не всегда могут быть получены
        //в том же порядке, что и запросы.
        val transactionId: Short = 1

        //- Protocol Identifier (Идентификатор протокола): 2 байта устанавливаются Master,
        //всегда будут = 00 00, что соответствует протоколу Modbus.
        val protocolId: Short = 0

        //- Unit Identifier (Идентификатор блока или адрес устройства): 1 байт устанавливается Master.
        //Повторяется устройством Slave для однозначной идентификации устройства Slave.
        val unitId = slaveId.value.toByte()

        val functionCode: Byte = 6
        val addressRegister = this.startAddress.value.toShort()
        val registerValueWrite = this.writeValue.value.toShort()

        // MBAP Header (Modbus Application Header) 7 байт:
        //Функция putShort() позволяет записать значение типа Short в объект.
        val mbapHeader = ByteBuffer.allocate(7).apply {
            putShort(transactionId) //Идентификатор транзакции
            putShort(protocolId) //Идентификатор протокола
            putShort((1 + 1 + 2 + 2).toShort()) // Длина (6 байтов идут следом)
            put(unitId) //Адрес устройства 255. Настраивается в Modbus TCP Slave Configuration контроллера (Slave устройства)
        }.array()

        // PDU, Protocol Data Unit. (5 bytes).
        val pdu = ByteBuffer.allocate(5).apply {
            put(functionCode)//Функциональный код (0x06) Запись одного AO Preset Single Register 16 битное	Запись)
            putShort(addressRegister) //Адрес регистра
            putShort(registerValueWrite) //Значение записываемое в регистр
        }.array()

        //ВОПРОС????????????????
        return mbapHeader + pdu

    }

    /**
     * Анализ ответа Modbus для контроля записи значения регистра
     */
    private fun parseRegisterValuesWrite(response: ByteArray): List<Int> {
        return try {
            val byteCount = response[8].toInt() // Количество байтов для регистров в ответе
            val registerValuesWrite = mutableListOf<Int>()
            //until - это ключевое слово, которое позволяет создать полуоткрытый диапазон.
            //Такой диапазон включает начальное значение, но не включает конечное.
            for (i in 0 until byteCount step 2) {
                val highByte = response[8 + i].toInt() and 0xFF //Значение старшего разряда регистра (первого слева на право)
                val lowByte = response[8 +i + 1].toInt() and 0xFF //Значение младшего разряда регистра (второго слева на право)
                val value = (highByte shl 8) or lowByte //Объединение значения старшего и младшего регистра
                registerValuesWrite.add(value) //Присвоение полученного значения в переменную registerValues
            }
            registerValuesWrite
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }



    //этот код является важной частью ViewModel, которая обеспечивает корректное завершение работы и
    //освобождение ресурсов при уничтожении ViewModel. Это помогает поддерживать чистоту памяти и
    //предотвращает потенциальные проблемы с ресурсами приложения.
    override fun onCleared() {
        super.onCleared()
        disconnectServer()
        httpClient.close() // Close HTTP client to avoid memory leaks
    }

    sealed class ConnectionStatus {
        object Disconnected : ConnectionStatus()
        object Connected : ConnectionStatus()
        data class Error(val message: String) : ConnectionStatus()
    }


//BackgroundService_ReadRegisters
    sealed class BackgroundServiceStatus {
        object Stopped : BackgroundServiceStatus()
        object Running : BackgroundServiceStatus()
        data class Error(val message: String) : BackgroundServiceStatus()
    }

    sealed class RemoteServerStatus {
        object NotSent : RemoteServerStatus()
        object Sending : RemoteServerStatus()
        object Success : RemoteServerStatus()
        data class Error(val message: String) : RemoteServerStatus()
    }

    // Background service state
    private val _backgroundServiceStatus = MutableStateFlow<BackgroundServiceStatus>(BackgroundServiceStatus.Stopped)
    val backgroundServiceStatus: StateFlow<BackgroundServiceStatus> = _backgroundServiceStatus.asStateFlow()

    // Remote server status
    private val _remoteServerStatus = MutableStateFlow<RemoteServerStatus>(RemoteServerStatus.NotSent)
    val remoteServerStatus: StateFlow<RemoteServerStatus> = _remoteServerStatus.asStateFlow()

    // Flag to control background service
    private var isBackgroundServiceRunning = false

    /**
     * Start background service to read registers from address 90 to 174
     */
    fun startBackgroundService() {
        if (isBackgroundServiceRunning) return

        isBackgroundServiceRunning = true
        _backgroundServiceStatus.value = BackgroundServiceStatus.Running

        viewModelScope.launch(Dispatchers.IO) {
            try {
                while (isBackgroundServiceRunning) {
                    if (_connectionStatus.value == ConnectionStatus.Connected) {
                        try {
                            // Read registers from 93 to 174
                            val registerValues = mutableListOf<Int>()

                            // Read in chunks of 32 registers to avoid overloading
                            for (startAddr in 90..174 step 32) {
                                val endAddr = minOf(startAddr + 31, 174)
                                val count = endAddr - startAddr + 1

                                // Build request for this chunk
                                val request = buildModbusRequestForRange(startAddr, count)
                                output?.writeFully(request)

                                // Read response
                                val response = ByteArray(256)
                                val bytesRead = input?.readAvailable(response, 0, response.size) ?: 0

                                if (bytesRead > 0) {
                                    val values = parseRegisterValues(response.copyOfRange(0, bytesRead))
                                    registerValues.addAll(values)
                                }

                                // Small delay between chunks
                                delay(100)
                            }

                            // Update register values
                            _registerValues.value = registerValues

                            // Send registers to remote server
                            sendRegistersToRemoteServer(registerValues)

                            // Log success
                            Log.d("MODBUS", "Background service read ${registerValues.size} registers from 93 to 174")
                        } catch (e: Exception) {
                            Log.e("MODBUS", "Error in background service: ${e.message}")
                            _backgroundServiceStatus.value = BackgroundServiceStatus.Error(e.message ?: "Error reading registers from 93 to 174")
                        }
                    }

                    // Wait before next read cycle
                    delay(1000)
                }
            } catch (e: Exception) {
                Log.e("MODBUS", "Background service error: ${e.message}")
                _backgroundServiceStatus.value = BackgroundServiceStatus.Error(e.message ?: "Background service error")
            } finally {
                _backgroundServiceStatus.value = BackgroundServiceStatus.Stopped
            }
        }
    }

    /**
     * Stop background service
     */


    /**
     * Build Modbus request for a specific range of registers
     */
    private fun buildModbusRequestForRange(startAddr: Int, count: Int): ByteArray {
        val transactionId: Short = 1
        val protocolId: Short = 0
        val unitId = slaveId.value.toByte()
        val functionCode: Byte = 3
        val startAddress = startAddr.toShort()
        val registerCount = count.toShort()

        // MBAP Header
        val mbapHeader = ByteBuffer.allocate(7).apply {
            putShort(transactionId)
            putShort(protocolId)
            putShort((1 + 1 + 2 + 2).toShort())
            put(unitId)
        }.array()

        // PDU
        val pdu = ByteBuffer.allocate(5).apply {
            put(functionCode)
            putShort(startAddress)
            putShort(registerCount)
        }.array()

        return mbapHeader + pdu
    }

    //BackgroundService_WriteRegisters
    fun startBackgroundServiceWrite (){
        if (isBackgroundServiceRunning) return

        isBackgroundServiceRunning = true
        _backgroundServiceStatus.value = BackgroundServiceStatus.Running

        viewModelScope.launch(Dispatchers.IO) {
            try {
                while (isBackgroundServiceRunning) {
                    if (_connectionStatus.value == ConnectionStatus.Connected){
                        try {
                            // запись регистров с 900 по 903
                            val registerValues = mutableListOf<Int>()
                            // запись в кусках по 10 регистров, чтобы избежать перегрузки
                            for (startAddr in 900..901 step 1) {
                                val endAddr = minOf(startAddr+1, 901)
                                val count = endAddr - startAddr + 1


                                // Запрос сборки для этого куска
                                val request = buildModbusRequestForRangeWrite(startAddr, count)
                                output?.writeFully(request)


                                //Чтение ответа
                                val response = ByteArray(256) //256 это выделенный объем памяти для получения ответа
                                val bytesWrite = input?.readAvailable ( response, 0, response.size)?:0

                                if (bytesWrite >0) {
                                    val values = parseRegisterValuesWrite(response.copyOfRange(0,bytesWrite))
                                    registerValues.addAll(values)
                                }
                                // Small delay between chunks. Небольшая задержка между фрагментами
                                delay(100)
                            }
                            // Update register values
                            _registerValues.value = registerValues
                            // Log success
                            Log.d("MODBUS", "Background service read ${registerValues.size} registers from 900 to 903")
                        }
                        catch (e: Exception){
                            Log.e("MODBUS", "Error in background service: ${e.message}")
                            _backgroundServiceStatus.value = BackgroundServiceStatus.Error(e.message ?: "Error reading registers from 900 to 903")
                        }
                    }
                    // Wait before next read cycle
                    delay(1000)
                }

            } catch (e: Exception){
                Log.e("MODBUS", "Background service error: ${e.message}")
                _backgroundServiceStatus.value = BackgroundServiceStatus.Error(e.message ?: "Background service error")
            } finally {
                _backgroundServiceStatus.value = BackgroundServiceStatus.Stopped
            }
        }
    }
    /**
     * Build Modbus request for a specific range of registers
     */
    private fun buildModbusRequestForRangeWrite(startAddr: Int, count: Int, ): ByteArray {
        val transactionId: Short = 1
        val protocolId: Short = 0
        val unitId = slaveId.value.toByte()
        val functionCode: Byte = 6
        val startAddress = startAddr.toShort()
        val registerValueWrite = this.writeValue.value.toShort()

        // MBAP Header
        val mbapHeader = ByteBuffer.allocate(7).apply {
            putShort(transactionId)
            putShort(protocolId)
            putShort((1 + 1 + 2 + 2).toShort())
            put(unitId)
        }.array()

        // PDU
        val pdu = ByteBuffer.allocate(5).apply {
            put(functionCode)
            putShort(startAddress)
            putShort(registerValueWrite)
        }.array()

        return mbapHeader + pdu

    }

    fun stopBackgroundService() {
        isBackgroundServiceRunning = false
    }

}
