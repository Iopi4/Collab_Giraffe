import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.nio.ByteBuffer

class ModbusViewModel : ViewModel() {
    private var socket: Socket? = null
    private var input: ByteReadChannel? = null
    private var output: ByteWriteChannel? = null
    private lateinit var selectorManager: SelectorManager
    private var isReading = true

    // Using StateFlow for state management
    private val _registerValues = MutableStateFlow<List<Int>>(emptyList())
    val registerValues: StateFlow<List<Int>> = _registerValues.asStateFlow()

    private val _isPaused = MutableStateFlow(false)
    val isPaused: StateFlow<Boolean> = _isPaused.asStateFlow()

    private val _ipAddress = MutableStateFlow("192.168.1.58")
    val ipAddress: StateFlow<String> = _ipAddress.asStateFlow()

    private val _port = MutableStateFlow(5020)
    val port: StateFlow<Int> = _port.asStateFlow()

    private val _slaveId = MutableStateFlow(255)// default for my server
    val slaveId: StateFlow<Int> = _slaveId.asStateFlow()

    private val _startAddress = MutableStateFlow(0)
    val startAddress: StateFlow<Int> = _startAddress.asStateFlow()

    private val _registerCount = MutableStateFlow(2)
    val registerCount: StateFlow<Int> = _registerCount.asStateFlow()

    private val _writeValue = MutableStateFlow(123)
    val writeValue: StateFlow<Int> = _writeValue.asStateFlow()

    private val _connectionStatus =
        MutableStateFlow<ConnectionStatus>(ConnectionStatus.Disconnected)
    val connectionStatus: StateFlow<ConnectionStatus> = _connectionStatus.asStateFlow()

    /**
     * Update connection parameters
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

    /**
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

    /**
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

    /**
     * Start reading Modbus registers periodically using stored parameters
     */
    fun startReadingRegisters() {
        Log.d(
            "MODBUS",
            "start reading registers ${startAddress.value.toString()} - ${registerCount.value.toString()}"
        )
        isReading = true
        viewModelScope.launch(Dispatchers.IO) {
            while (isReading) {
                try {
                    val request = buildModbusRequest()
                    output?.writeFully(request)

                    val response = ByteArray(256)
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

    /**
     * Stop reading Modbus registers
     */
    fun stopReadingRegisters() {
        isReading = false
    }

    /**
     * Build Modbus TCP request for "Read Holding Registers"
     */
    private fun buildModbusRequest(): ByteArray {
        val transactionId: Short = 1
        val protocolId: Short = 0
        val unitId = slaveId.value.toByte()
        val functionCode: Byte = 3
        val startAddress = this.startAddress.value.toShort()
        val registerCount = this.registerCount.value.toShort()

        // MBAP Header (7 bytes)
        val mbapHeader = ByteBuffer.allocate(7).apply {
            putShort(transactionId)
            putShort(protocolId)
            putShort((1 + 1 + 2 + 2).toShort()) // Remaining Length
            put(unitId)
        }.array()

        // PDU (5 bytes)
        val pdu = ByteBuffer.allocate(5).apply {
            put(functionCode)
            putShort(startAddress)
            putShort(registerCount)
        }.array()

        return mbapHeader + pdu
    }

    /**
     * Parse the Modbus response to extract register values
     */
    private fun parseRegisterValues(response: ByteArray): List<Int> {
        return try {
            val byteCount = response[8].toInt() // Byte count for registers
            val registerValues = mutableListOf<Int>()

            for (i in 0 until byteCount step 2) {
                val highByte = response[9 + i].toInt() and 0xFF
                val lowByte = response[9 + i + 1].toInt() and 0xFF
                val value = (highByte shl 8) or lowByte
                registerValues.add(value)
            }
            registerValues
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

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