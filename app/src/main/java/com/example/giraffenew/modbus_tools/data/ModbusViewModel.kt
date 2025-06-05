package com.example.giraffenew.modbus_tools.data

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.Socket
import kotlin.random.Random


// ModbusViewModel to handle business logic and state
class ModbusViewModel: ViewModel() {
    var serverIp by mutableStateOf("192.168.33.10")
    var serverPort by mutableStateOf("502")
    var registerAddress by mutableStateOf("")
    var registerValue by mutableStateOf("")
    var connectionStatus by mutableStateOf("Disconnected")
    var readValue by mutableStateOf("")

    private var socket: Socket? = null
    private var outputStream: DataOutputStream? = null
    private var inputStream: DataInputStream? = null

    companion object {
        private const val TAG = "ModbusViewModel"
    }

    fun connect() {
        Log.d(TAG, "Attempting to connect to ${serverIp}:${serverPort}")
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    socket = Socket(serverIp, serverPort.toInt())
                    outputStream = DataOutputStream(socket?.getOutputStream())
                    inputStream = DataInputStream(socket?.getInputStream())
                }
                connectionStatus = "Connected"
            } catch (e: Exception) {
                connectionStatus = "Connection failed: ${e.message}"
            }
        }
    }

    fun disconnect() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    socket?.close()
                    socket = null
                    outputStream = null
                    inputStream = null
                    connectionStatus = "Disconnected"
                } catch (e: Exception) {
                    connectionStatus = "Disconnect failed: ${e.message}"
                }
            }
        }
    }

    fun readRegister() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val address = registerAddress.toInt()
                    // Build Modbus TCP request
                    val request = ByteArray(12)
                    // Transaction ID (random)
                    val transactionId = Random.nextInt(65535)
                    request[0] = (transactionId shr 8).toByte()
                    request[1] = transactionId.toByte()
                    // Protocol ID (0)
                    request[2] = 0
                    request[3] = 0
                    // Length
                    request[4] = 0
                    request[5] = 6
                    // Unit ID
                    request[6] = 1
                    // Function code (3 = read holding registers)
                    request[7] = 3
                    // Register address. Адрес первого регистра
                    request[8] = (address shr 8).toByte()
                    request[9] = address.toByte()
                    // Number of registers (1)
                    request[10] = 0
                    request[11] = 1

                    outputStream?.write(request)
                    outputStream?.flush()

                    // Read response
                    val response = ByteArray(11)
                    inputStream?.readFully(response)

                    // Extract value from response
                    val value =
                        (response[9].toInt() and 0xFF) shl 8 or (response[10].toInt() and 0xFF)
                    readValue = value.toString()
                }
            } catch (e: Exception) {
                readValue = "Read failed: ${e.message}"
            }
        }
    }

    fun writeRegister() {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    val address = registerAddress.toInt()
                    val value = registerValue.toInt()

                    // Build Modbus TCP request
                    val request = ByteArray(12)
                    // Transaction ID (random) Transaction Identifier (Идентификатор транзакции):
                    // 2 байта устанавливаются Master, чтобы однозначно идентифицировать каждый запрос.
                    // Может быть любыми. Эти байты повторятся устройством Slave в ответе, поскольку
                    // ответы устройства Slave не всегда могут быть получены в том же порядке, что и запросы.
                    val transactionId = Random.nextInt(65535)
                    request[0] = (transactionId shr 8).toByte()
                    request[1] = transactionId.toByte()
                    // Protocol ID (0) Protocol Identifier (Идентификатор протокола): 2 байта устанавливаются Master,
                    // всегда будут = 00 00, что соответствует протоколу Modbus.
                    request[2] = 0
                    request[3] = 0
                    // Length Length (Длина): 2 байта устанавливаются Master, идентифицирующие
                    // число байтов в сообщении, которые следуют далее. Считается от Unit Identifier до конца сообщения.
                    request[4] = 0
                    request[5] = 6
                    // Unit ID. Unit Identifier (Идентификатор блока или адрес устройства):
                    // 1 байт устанавливается Master. Повторяется устройством Slave для однозначной идентификации устройства Slave.
                    request[6] = 1
                    // Function code (6 = write single register)
                    request[7] = 6
                    // Register address. Адрес первого регистра
                    request[8] = (address shr 8).toByte()
                    request[9] = address.toByte()
                    // Register value
                    request[10] = (value shr 8).toByte()
                    request[11] = value.toByte()

                    outputStream?.write(request)
                    outputStream?.flush()

                    // Read response
                    val response = ByteArray(12)
                    inputStream?.readFully(response)
                }
                readValue = "Write successful"
            } catch (e: Exception) {
                readValue = "Write failed: ${e.message}"
            }
        }
    }


}