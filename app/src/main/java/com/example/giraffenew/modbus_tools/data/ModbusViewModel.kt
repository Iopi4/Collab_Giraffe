import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.nio.ByteBuffer


class ModbusViewModel : ViewModel() {
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

    private val _writeValue = MutableStateFlow(0)
    val writeValue: StateFlow<Int> = _writeValue.asStateFlow()

    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused.asStateFlow()

    private val _ipAddress = MutableStateFlow("192.168.1.88")
    val ipAddress: StateFlow<String> = _ipAddress.asStateFlow()

    private val _port = MutableStateFlow(502)
    val port: StateFlow<Int> = _port.asStateFlow()

    private val _slaveId = MutableStateFlow(255)// по умолчанию для моего сервера
    val slaveId: StateFlow<Int> = _slaveId.asStateFlow()

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

    fun updateWriteValue(newValue: Int) {
        _writeValue.value = newValue
    }

    /**Add commentMore actions
     * Connect to the Modbus TCP server
     */
    fun connectToServer() {
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
            delay(1000) // Repeat every second
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
                    isReading = false
                }
                delay(1000) // Repeat every second
            }
        }
    }

    /**Add commentMore actions
     * Stop reading Modbus registers
     */
    fun stopReadingRegisters() {
        isReading = false
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
    }

    sealed class ConnectionStatus {
        object Disconnected : ConnectionStatus()
        object Connected : ConnectionStatus()
        data class Error(val message: String) : ConnectionStatus()
    }

}
