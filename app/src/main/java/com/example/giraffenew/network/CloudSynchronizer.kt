package com.example.giraffenew.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.giraffenew.data.repositories.ModbusRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.compression.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.min
import kotlin.math.pow

/**
 * CloudSynchronizer для отправки данных регистров в облачный сервис каждые 30 секунд
 * 
 * Особенности согласно техзаданию:
 * - Endpoint: https://monitor.giraffecrane.ru/api/crane/registers
 * - JSON payload с массивом записей за 30-секундный период
 * - Retry logic с exponential backoff
 * - Offline queue с persistent storage
 * - Data compression (gzip) для больших payload
 * - Industrial-grade reliability для 24/7 работы
 * 
 * Payload format:
 * {
 *   "crane_id": "GIRAFFE_001",
 *   "readings": [
 *     {
 *       "timestamp": "2025-08-11T10:30:01.123Z",
 *       "registers": {
 *         "90": "1A2B",
 *         "91": "3C4D",
 *         "92": "5E6F"
 *       }
 *     }
 *   ]
 * }
 */
@Singleton
class CloudSynchronizer @Inject constructor(
    private val context: Context
) {
    
    // HTTP client для облачных запросов
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
                prettyPrint = false // Минимизируем размер для network
            })
        }
        
        install(ContentEncoding) {
            gzip() // Автоматическое сжатие
        }
        
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d(TAG, "HTTP: $message")
                }
            }
            level = LogLevel.INFO
        }
        
        install(HttpTimeout) {
            requestTimeoutMillis = 30000 // 30 секунд timeout
            connectTimeoutMillis = 10000  // 10 секунд connection timeout
        }
        
        install(HttpRequestRetry) {
            retryOnServerErrors(maxRetries = 2) // Базовый retry для 5xx ошибок
            exponentialDelay()
        }
    }
    
    // Coroutine scope для sync операций
    private val syncScope = CoroutineScope(Dispatchers.IO + SupervisorJob())
    
    // Конфигурация
    private var cloudEndpoint = "https://monitor.giraffecrane.ru/api/crane/registers"
    private var craneId = "GIRAFFE_001"
    private val syncIntervalMs = 30_000L // 30 секунд
    
    // Состояние синхронизации
    private val _syncStatus = MutableStateFlow<SyncStatus>(SyncStatus.Stopped)
    val syncStatus: StateFlow<SyncStatus> = _syncStatus.asStateFlow()
    
    // Accumulated data для следующего sync
    private val accumulatedReadings = mutableListOf<RegisterReading>()
    private val accumulatedReadingsLock = kotlinx.coroutines.sync.Mutex()
    
    // Offline queue
    private val offlineQueue = mutableListOf<CloudPayload>()
    private val offlineQueueFile by lazy { 
        File(context.filesDir, "cloud_sync_queue.json") 
    }
    
    // Retry logic
    private var retryAttempts = 0
    private val maxRetryAttempts = 10
    private val baseRetryDelayMs = 1000L
    
    // Metrics
    private val totalSyncAttempts = AtomicLong(0)
    private val successfulSyncs = AtomicLong(0)
    private val failedSyncs = AtomicLong(0)
    private val totalDataSent = AtomicLong(0) // bytes
    
    // Jobs
    private var syncJob: Job? = null
    private var offlineProcessingJob: Job? = null
    
    companion object {
        private const val TAG = "CloudSynchronizer"
        private const val MAX_OFFLINE_QUEUE_SIZE = 1000 // Максимальный размер offline queue
        private const val MAX_PAYLOAD_SIZE = 1024 * 1024 // 1MB максимальный размер payload
    }
    
    /**
     * Статусы синхронизации
     */
    sealed class SyncStatus {
        object Stopped : SyncStatus()
        object Running : SyncStatus()
        object Syncing : SyncStatus()
        object WaitingForNetwork : SyncStatus()
        data class Error(val message: String, val canRetry: Boolean = true) : SyncStatus()
        object ProcessingOffline : SyncStatus()
    }
    
    /**
     * Data classes согласно API specification
     */
    @Serializable
    data class CloudPayload(
        val crane_id: String,
        val readings: List<RegisterReading>,
        val timestamp: String = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }.format(Date())
    )
    
    @Serializable
    data class RegisterReading(
        val timestamp: String,
        val registers: Map<String, String> // address -> hex value
    )
    
    @Serializable
    data class CloudResponse(
        val status: String,
        val message: String? = null,
        val received_count: Int? = null
    )
    
    /**
     * Метрики синхронизации
     */
    data class SyncMetrics(
        val status: SyncStatus,
        val totalAttempts: Long,
        val successfulSyncs: Long,
        val failedSyncs: Long,
        val successRate: Double,
        val totalDataSent: Long,
        val offlineQueueSize: Int,
        val lastSyncTime: Long,
        val nextSyncTime: Long,
        val isNetworkAvailable: Boolean
    )
    
    /**
     * Начать синхронизацию
     */
    fun startSync() {
        if (syncJob?.isActive == true) {
            Log.w(TAG, "Sync already running")
            return
        }
        
        Log.i(TAG, "Starting cloud synchronization")
        _syncStatus.value = SyncStatus.Running
        
        // Загружаем offline queue
        loadOfflineQueue()
        
        // Запускаем основной sync job
        syncJob = syncScope.launch {
            runSyncLoop()
        }
        
        // Запускаем обработку offline queue
        offlineProcessingJob = syncScope.launch {
            processOfflineQueue()
        }
    }
    
    /**
     * Остановить синхронизацию
     */
    fun stopSync() {
        Log.i(TAG, "Stopping cloud synchronization")
        
        syncJob?.cancel()
        offlineProcessingJob?.cancel()
        
        _syncStatus.value = SyncStatus.Stopped
        
        // Сохраняем offline queue
        saveOfflineQueue()
    }
    
    /**
     * Основной цикл синхронизации
     */
    private suspend fun runSyncLoop() {
        while (isActive) {
            try {
                // Ждем интервал синхронизации
                delay(syncIntervalMs)
                
                if (!isNetworkAvailable()) {
                    _syncStatus.value = SyncStatus.WaitingForNetwork
                    continue
                }
                
                _syncStatus.value = SyncStatus.Syncing
                
                // Получаем accumulated data
                val readingsToSync = accumulatedReadingsLock.withLock {
                    val readings = accumulatedReadings.toList()
                    accumulatedReadings.clear()
                    readings
                }
                
                if (readingsToSync.isNotEmpty()) {
                    val payload = CloudPayload(
                        crane_id = craneId,
                        readings = readingsToSync
                    )
                    
                    val success = sendToCloud(payload)
                    
                    if (success) {
                        retryAttempts = 0 // Reset retry counter on success
                        _syncStatus.value = SyncStatus.Running
                    } else {
                        // Добавляем в offline queue при неудаче
                        addToOfflineQueue(payload)
                        handleSyncFailure()
                    }
                } else {
                    _syncStatus.value = SyncStatus.Running
                }
                
            } catch (e: Exception) {
                Log.e(TAG, "Error in sync loop", e)
                handleSyncFailure()
            }
        }
    }
    
    /**
     * Добавить данные регистров для синхронизации
     */
    suspend fun addRegisterData(registerValues: Map<Int, ModbusRepository.RegisterValue>) {
        if (registerValues.isEmpty()) return
        
        val reading = RegisterReading(
            timestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }.format(Date()),
            registers = registerValues.mapKeys { it.key.toString() }
                                   .mapValues { it.value.hexValue }
        )
        
        accumulatedReadingsLock.withLock {
            accumulatedReadings.add(reading)
        }
    }
    
    /**
     * Отправить payload в облако
     */
    private suspend fun sendToCloud(payload: CloudPayload): Boolean {
        return try {
            totalSyncAttempts.incrementAndGet()
            
            Log.d(TAG, "Sending ${payload.readings.size} readings to cloud")
            
            val response: HttpResponse = httpClient.post(cloudEndpoint) {
                contentType(ContentType.Application.Json)
                setBody(payload)
                
                // Добавляем headers
                header("User-Agent", "GIRAFFE-Crane/1.0")
                header("X-Crane-ID", craneId)
            }
            
            when {
                response.status.isSuccess() -> {
                    val cloudResponse = response.body<CloudResponse>()
                    
                    successfulSyncs.incrementAndGet()
                    totalDataSent.addAndGet(response.request.content.contentLength ?: 0L)
                    
                    Log.i(TAG, "Cloud sync successful: ${cloudResponse.message}")
                    true
                }
                response.status.value in 400..499 -> {
                    // Client errors - не ретраим
                    failedSyncs.incrementAndGet()
                    Log.e(TAG, "Client error in cloud sync: ${response.status} - ${response.bodyAsText()}")
                    false
                }
                else -> {
                    // Server errors - ретраим
                    failedSyncs.incrementAndGet()
                    Log.e(TAG, "Server error in cloud sync: ${response.status}")
                    false
                }
            }
            
        } catch (e: Exception) {
            failedSyncs.incrementAndGet()
            Log.e(TAG, "Exception during cloud sync", e)
            false
        }
    }
    
    /**
     * Обработать неудачную синхронизацию
     */
    private suspend fun handleSyncFailure() {
        retryAttempts++
        
        if (retryAttempts >= maxRetryAttempts) {
            _syncStatus.value = SyncStatus.Error(
                "Max retry attempts reached ($maxRetryAttempts)",
                canRetry = true
            )
            
            // Ждем дольше перед следующей попыткой
            delay(60_000L) // 1 минута
            retryAttempts = 0 // Reset для следующего цикла
        } else {
            // Exponential backoff
            val delayMs = (baseRetryDelayMs * 2.0.pow(retryAttempts - 1)).toLong()
                .coerceAtMost(300_000L) // Максимум 5 минут
            
            _syncStatus.value = SyncStatus.Error(
                "Retry attempt $retryAttempts/$maxRetryAttempts in ${delayMs}ms",
                canRetry = true
            )
            
            delay(delayMs)
        }
        
        _syncStatus.value = SyncStatus.Running
    }
    
    /**
     * Добавить payload в offline queue
     */
    private suspend fun addToOfflineQueue(payload: CloudPayload) {
        try {
            synchronized(offlineQueue) {
                if (offlineQueue.size >= MAX_OFFLINE_QUEUE_SIZE) {
                    // Удаляем самые старые записи
                    val toRemove = offlineQueue.size - MAX_OFFLINE_QUEUE_SIZE + 1
                    repeat(toRemove) { offlineQueue.removeAt(0) }
                }
                
                offlineQueue.add(payload)
            }
            
            // Сохраняем queue периодически
            if (offlineQueue.size % 10 == 0) {
                saveOfflineQueue()
            }
            
            Log.d(TAG, "Added payload to offline queue (size: ${offlineQueue.size})")
            
        } catch (e: Exception) {
            Log.e(TAG, "Failed to add payload to offline queue", e)
        }
    }
    
    /**
     * Обработать offline queue
     */
    private suspend fun processOfflineQueue() {
        while (isActive) {
            try {
                if (isNetworkAvailable() && offlineQueue.isNotEmpty()) {
                    _syncStatus.value = SyncStatus.ProcessingOffline
                    
                    val payload = synchronized(offlineQueue) {
                        if (offlineQueue.isNotEmpty()) offlineQueue.removeAt(0) else null
                    }
                    
                    if (payload != null) {
                        val success = sendToCloud(payload)
                        
                        if (!success && isNetworkAvailable()) {
                            // Если network есть но отправка неудачная, возвращаем в queue
                            synchronized(offlineQueue) {
                                offlineQueue.add(0, payload) // В начало queue
                            }
                            
                            // Ждем перед следующей попыткой
                            delay(10_000L) // 10 секунд
                        }
                    }
                }
                
                delay(5_000L) // Проверяем offline queue каждые 5 секунд
                
            } catch (e: Exception) {
                Log.e(TAG, "Error processing offline queue", e)
                delay(10_000L)
            }
        }
    }
    
    /**
     * Проверить доступность сети
     */
    private fun isNetworkAvailable(): Boolean {
        return try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            
            capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            
        } catch (e: Exception) {
            Log.e(TAG, "Error checking network availability", e)
            false
        }
    }
    
    /**
     * Сохранить offline queue в файл
     */
    private fun saveOfflineQueue() {
        try {
            if (offlineQueue.isNotEmpty()) {
                val json = Json { prettyPrint = true }
                val jsonString = json.encodeToString(
                    kotlinx.serialization.builtins.ListSerializer(CloudPayload.serializer()),
                    offlineQueue.toList()
                )
                
                offlineQueueFile.writeText(jsonString)
                Log.d(TAG, "Offline queue saved (${offlineQueue.size} items)")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to save offline queue", e)
        }
    }
    
    /**
     * Загрузить offline queue из файла
     */
    private fun loadOfflineQueue() {
        try {
            if (offlineQueueFile.exists()) {
                val jsonString = offlineQueueFile.readText()
                val json = Json { ignoreUnknownKeys = true }
                val loadedQueue = json.decodeFromString(
                    kotlinx.serialization.builtins.ListSerializer(CloudPayload.serializer()),
                    jsonString
                )
                
                synchronized(offlineQueue) {
                    offlineQueue.clear()
                    offlineQueue.addAll(loadedQueue.take(MAX_OFFLINE_QUEUE_SIZE))
                }
                
                Log.i(TAG, "Offline queue loaded (${offlineQueue.size} items)")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to load offline queue", e)
            // Очищаем поврежденный файл
            offlineQueueFile.delete()
        }
    }
    
    /**
     * Обновить конфигурацию
     */
    fun updateConfiguration(
        newEndpoint: String? = null,
        newCraneId: String? = null
    ) {
        newEndpoint?.let { cloudEndpoint = it }
        newCraneId?.let { craneId = it }
        
        Log.i(TAG, "Configuration updated - endpoint: $cloudEndpoint, craneId: $craneId")
    }
    
    /**
     * Принудительная синхронизация
     */
    suspend fun forceSyncNow(): Boolean {
        if (_syncStatus.value != SyncStatus.Running) {
            return false
        }
        
        return try {
            val readingsToSync = accumulatedReadingsLock.withLock {
                val readings = accumulatedReadings.toList()
                accumulatedReadings.clear()
                readings
            }
            
            if (readingsToSync.isNotEmpty()) {
                val payload = CloudPayload(
                    crane_id = craneId,
                    readings = readingsToSync
                )
                
                sendToCloud(payload)
            } else {
                Log.w(TAG, "No data to sync")
                true
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Force sync failed", e)
            false
        }
    }
    
    /**
     * Получить метрики синхронизации
     */
    fun getMetrics(): SyncMetrics {
        val total = totalSyncAttempts.get()
        val successful = successfulSyncs.get()
        val successRate = if (total > 0) (successful.toDouble() / total) * 100.0 else 0.0
        
        return SyncMetrics(
            status = _syncStatus.value,
            totalAttempts = total,
            successfulSyncs = successful,
            failedSyncs = failedSyncs.get(),
            successRate = successRate,
            totalDataSent = totalDataSent.get(),
            offlineQueueSize = offlineQueue.size,
            lastSyncTime = 0L, // TODO: track last sync time
            nextSyncTime = System.currentTimeMillis() + syncIntervalMs,
            isNetworkAvailable = isNetworkAvailable()
        )
    }
    
    /**
     * Очистить offline queue
     */
    fun clearOfflineQueue() {
        synchronized(offlineQueue) {
            offlineQueue.clear()
        }
        offlineQueueFile.delete()
        Log.i(TAG, "Offline queue cleared")
    }
    
    /**
     * Cleanup ресурсов
     */
    fun cleanup() {
        stopSync()
        saveOfflineQueue()
        httpClient.close()
        syncScope.cancel()
    }
}