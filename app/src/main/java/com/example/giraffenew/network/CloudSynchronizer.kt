package com.example.giraffenew.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.example.giraffenew.data.repositories.ModbusRepository
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicLong
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.coroutineContext
import kotlin.math.pow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.Job
import kotlinx.serialization.SerializationException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.atomic.AtomicBoolean

/**
 * CloudSynchronizer для отправки данных регистров в облачный сервис каждые 30 секунд
 *
 * Особенности согласно техзаданию:
 * - Endpoint: https://monitor.giraffecrane.ru/api/crane/registers
 * - JSON payload с массивом записей за 5-секундный период
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
 *         "0": "1A2B",
 *         "1": "3C4D",
 *         "2": "5E6F"
 *         ...
 *         "450": "5E6F"
 *       }
 *     }
 *   ]
 * }
 */

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

//Создадим отдельный репозиторий для управления craneID
@Singleton
class CraneIdRepository @Inject constructor() {
    private val _craneId = MutableStateFlow("CRANE_ID")
    val craneId: StateFlow<String> = _craneId.asStateFlow()

    fun updateCraneId(newCraneId: String) {
        _craneId.value = newCraneId
    }
}

//-----------------------------------ШАГ 1: Инициализация -------------------------------
@Singleton
class CloudSynchronizer @Inject constructor(
    private val context: Context,
    private val craneIdRepository: CraneIdRepository //Добавим отдельный репозиторий для управления craneID
) {
    //Шаг 1.1 Создается HttpClient с настройками (таймауты, ретраи, логирование) для облачных запросов..
    private val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
                prettyPrint = false // Минимизируем размер для network
            })
        }

        //install(ContentEncoding) {
        //gzip() // Автоматическое сжатие
        //}

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d(TAG, "HTTP: $message")
                }
            }
            level = LogLevel.INFO
        }

        install(HttpTimeout) {
            //Время ожидания запроса — это период времени, необходимый для обработки HTTP-вызова: от отправки запроса до получения ответа.
            requestTimeoutMillis = 30000 // 30 секунд timeout

            //Время ожидания соединения — это время, в течение которого клиент должен установить соединение с сервером.
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

    //Присваиваем значение craneId  из репозитория
    private var craneId: String = craneIdRepository.craneId.value

    // Добавляем подписку на изменения craneId
    private var craneIdJob: Job? = null

    private val syncIntervalMs = 30_000L // было 30 секунд. Сделал 5 секунд.

    // Состояние синхронизации
    private val _syncStatus = MutableStateFlow<SyncStatus>(SyncStatus.Stopped)
    val syncStatus: StateFlow<SyncStatus> = _syncStatus.asStateFlow()

    //  Накапливаем показания регистров в процессе синхронизации из ModbusBackgroundService.
    private val accumulatedReadings = mutableListOf<RegisterReading>()

    private val accumulatedReadingsLock = kotlinx.coroutines.sync.Mutex()

    // OfflineQueue. Оффлайн очередь из списка регистров которые еще не были отправлены
    private val offlineQueue = mutableListOf<CloudPayload>()

    //Обработка OfflineQueue. Сохраняем сохраняется в файл (cloud_sync_queue.json) и загружаем данные при старте.
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
        // Максимальный размер offline queue. Количество файлов с массивом регистров. т.е. очередей
        private const val MAX_OFFLINE_QUEUE_SIZE = 100
        private const val MAX_PAYLOAD_SIZE = 1024 * 1024 // 1MB максимальный размер payload
    }

    //Добавим отслеживание статуса подключение к облачному сервису
    private val _cloudConnectionStatus = MutableStateFlow(false)
    val cloudConnectionStatus: StateFlow<Boolean> = _cloudConnectionStatus.asStateFlow()

    // Таймер для проверки "свежести" подключения
    private var lastSuccessfulSyncTime = 0L
    private val connectionTimeoutMs = 60000L // 60 секунд timeout

    // Запускаем мониторинг статуса подключения
    private var connectionMonitorJob: Job? = null

    //Улучшенная версия мониторинга состояния подключения
    private fun startConnectionMonitoring() {
        connectionMonitorJob = syncScope.launch {
            var consecutiveFailures = 0
            while (coroutineContext.isActive) {
                try {
                    updateConnectionStatus()

                    // Если долго нет успешных синхронизаций, пробуем "починить" соединение
                    val timeSinceLastSuccess = System.currentTimeMillis() - lastSuccessfulSyncTime
                    if (timeSinceLastSuccess > connectionTimeoutMs && consecutiveFailures < 5) {
                        Log.w(TAG, "No successful sync for ${timeSinceLastSuccess}ms, checking connection health")
                        consecutiveFailures++

                        // Пробуем легкий health check
                        if (!performHealthCheck()) {
                            Log.w(TAG, "Health check failed, may need to reset connection")
                        }
                    } else if (timeSinceLastSuccess <= connectionTimeoutMs) {
                        consecutiveFailures = 0
                    }

                    delay(5000L)
                } catch (e: Exception) {
                    Log.e(TAG, "Error in connection monitoring", e)
                    delay(10000L)
                }
            }
        }
    }

    private suspend fun performHealthCheck(): Boolean {
        return try {
            // Простой запрос для проверки доступности сервера
            val response: HttpResponse = httpClient.get(cloudEndpoint) {
                timeout { requestTimeoutMillis = 5000 }
            }
            response.status.isSuccess()
        } catch (e: Exception) {
            false
        }
    }

    private fun stopConnectionMonitoring (){
        connectionMonitorJob?.cancel()
        _cloudConnectionStatus.value = false
    }

    private fun updateConnectionStatus(){
        val currenTime = System.currentTimeMillis()
        val isConnected = (currenTime - lastSuccessfulSyncTime)<connectionTimeoutMs
        _cloudConnectionStatus.value = isConnected
    }

    // Обновляем время последней успешной синхронизации
    private fun updateLastSuccessfulSyncTime() {
        lastSuccessfulSyncTime = System.currentTimeMillis()
        updateConnectionStatus()
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
    // Вынесем класс наружу за пределы
    @Serializable
    data class CloudPayload(
    val crane_id: String,
    val readings: List<RegisterReading>,
    val timestamp: String = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
    timeZone = TimeZone.getTimeZone("UTC")
    }.format(Date())
    )
     */


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
        val totalAttempts: Long, // всего попыток
        val successfulSyncs: Long, // успешных отправок
        val failedSyncs: Long,     // неудачных отправок
        val successRate: Double, // процент успеха
        val totalDataSent: Long,  // объем отправленных данных
        val offlineQueueSize: Int,  // размер очереди
        val lastSyncTime: Long,
        val nextSyncTime: Long,
        val isNetworkAvailable: Boolean // доступность сети
    )

    /**
     * Начать синхронизацию
     */
//-----------------------------ШАГ 1.2 - Загружается offlineQueue из файла. Запуск корутин -------------------------------
    fun startSync() {

        if (isStopping.get()) {
            Log.w(TAG, "Cannot start while stopping")
            return
        }

        if (syncJob?.isActive == true) {
            Log.w(TAG, "Sync already running")
            return
        }

        Log.i(TAG, "Starting cloud synchronization")
        _syncStatus.value = SyncStatus.Running

        // Загружаем offline queue. Сначала загружаем файлы регистров которые копились пока не было связи
        loadOfflineQueue()

        // ЗАПУСКАЕМ МОНИТОРИНГ ПОДКЛЮЧЕНИЯ
        startConnectionMonitoring()

        //Запускаем корутины.
        // Первый поток обработки. Основной цикл синхронизации (runSyncLoop) -
        // каждые 5 секунд отправляет последнее значение и в случае ошибки кладет его в offlineQueue.
        syncJob = syncScope.launch {
            runSyncLoop()
        }

        // Второй поток обработки. Запускаем обработку offline queue.
        offlineProcessingJob = syncScope.launch {
            //Обработка offlineQueue (processOfflineQueue) - каждые 5 секунд проверяет, есть ли сеть
            // и не пуста ли очередь, и пытается отправить старые данные.
            processOfflineQueue()
        }
    }

    /**
     * Остановить синхронизацию
     * Добавлена защита от двойного вызова stopSync()
     */

    private val isStopping = AtomicBoolean(false)
    fun stopSync() {
        if (isStopping.getAndSet(true)) {
            Log.w(TAG, "Stop already in progress")
            return
        }

        try {
            Log.i(TAG, "Stopping cloud synchronization")

            syncJob?.cancel()
            offlineProcessingJob?.cancel()
            stopConnectionMonitoring()

            _syncStatus.value = SyncStatus.Stopped
            saveOfflineQueue()

        } finally {
            isStopping.set(false)
        }
    }

    /**
     * Синхронизировать ID крана
     */
    init { startListeningForCraneIdChanges() }
    fun startListeningForCraneIdChanges() {
        craneIdJob = syncScope.launch {
            craneIdRepository.craneId.collect { newCraneId ->
                craneId = newCraneId
                Log.d(TAG, "Crane ID updated to: $craneId")
            }
        }
    }

//-----------------------------ШАГ №2: Накопление данных------------------------------
    /**
     * Добавить данные регистров для синхронизации
     * Оценочный расчет размера одной записи:
     * timestamp (временная метка) = 30 байт
     * Регистры (450 регистров) = 11 байт * 450 = 4950 байт
     * Итого 4980 байт  на одну запись
     * 5 записей × 4,980 байт/запись = 24,900 байт
     * В килобайтах: 24,900 байт ÷ 1024 ≈ 24.316 КБ
     * В магабайтах: 24,316 кбайт ÷ 1024 ≈ 0.024 КБ
     */
// ModbusBackgroundService каждую секунду читает регистры и вызывает cloudSynchronizer.addRegisterData.
// addRegisterData создает RegisterReading и добавляет в accumulatedReadings регистры (с мьютексом).
    suspend fun addRegisterData(registerValues: Map<Int, ModbusRepository.RegisterValue>) {
        if (registerValues.isEmpty()) return

        val reading = RegisterReading(
            timestamp = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US).apply {
                timeZone = TimeZone.getTimeZone("UTC")
            }.format(Date()),
            registers = registerValues.mapKeys { it.key.toString() }
                .mapValues { it.value.hexValue }
        )
        // НАКОПЛЕНИЕ: накапливаем JSON массив с данными регистров каждую секунду
        accumulatedReadingsLock.withLock {
            accumulatedReadings.add(reading)
        }
    }


//-----------------------------ШАГ 3: Периодическая синхронизация (runSyncLoop)------------------------------
    /**
     * Основной цикл синхронизации. runSyncLoop
     * Каждые syncIntervalMs (5 секунд) отправляет последние значения регистров и в случае ошибки кладет его в offlineQueue.
     */
    suspend fun runSyncLoop() {
        while (coroutineContext.isActive) {
            try {

                delay(syncIntervalMs) // было 30 секунд. Стало 5 секунд.
                // 3.1 Проверяем сеть каждый интервал синхронизации syncIntervalMs
                if (!isNetworkAvailable()) {
                    _syncStatus.value = SyncStatus.WaitingForNetwork
                    continue
                }
                //3.2 Если сеть есть, меняем статус на Syncing
                _syncStatus.value = SyncStatus.Syncing

                //3.3 Далее забираем все записанные регистры accumulatedReadings отправляем их на сервер и после очищаем accumulatedReadings.
                val readingsToSync = accumulatedReadingsLock.withLock {
                    val readings = accumulatedReadings.toList()
                    accumulatedReadings.clear() // очистка
                    readings
                }
                // 3.4 ОТПРАВКА: Если есть данные, формируем CloudPayload (crane_id и readings = все данные) и отправляем sendToCloud.
                if (readingsToSync.isNotEmpty()) {
                    val payload = CloudPayload(
                        crane_id = craneId,
                        // Отправляется только последнее значение из массива accumulatedReadings за 5 секунд.
                        readings = readingsToSync.takeLast(1) //Теряем 29 из 30 Json данных!
                        //readings = readingsToSync //Убираем takeLast(1), так как из-за этого теряем 29 из 30 Json данных в очереди!
                    )
                    //Отправка в облако
                    val success = sendToCloud(payload)
                    //3.5 В случае успеха (true) - сбрасываем retryAttempts и статус Running.
                    if (success) {
                        retryAttempts = 0 // Reset retry counter on success
                        _syncStatus.value = SyncStatus.Running
                    } else {
                        //В случае ошибки (false) - добавляем весь payload в offlineQueue (addToOfflineQueue)
                        // и обрабатываем ошибку (handleSyncFailure).
                        addToOfflineQueue(payload)
                        handleSyncFailure()
                    }
                } else {
                    _syncStatus.value = SyncStatus.Running
                }

            } catch (e: Exception) {
                Log.e(TAG, "Error in sync loop", e)
                //Обрабатываем неудачную отправку
                handleSyncFailure()
            }
        }
    }




    /**
     * Отправить payload в облако
     */
    suspend fun sendToCloud(payload: CloudPayload): Boolean {
        return try {
            totalSyncAttempts.incrementAndGet()

            Log.d(TAG, "Sending ${payload.readings.size} readings to cloud")

            val response: HttpResponse = httpClient.post(cloudEndpoint) {
                contentType(ContentType.Application.Json)
                setBody(payload)

                // Добавляем headers
                header("User-Agent", "GIRAFFE-Crane/1.0")
                header("X-Crane-ID", craneId)

                // Таймаут для конкретного запроса
                timeout {
                    requestTimeoutMillis = 30000
                    connectTimeoutMillis = 10000
                    socketTimeoutMillis = 30000
                }

            }

            when {
                response.status.isSuccess() -> {
                    // Обработка успеха
                    successfulSyncs.incrementAndGet()
                    updateLastSuccessfulSyncTime()
                    Log.i(TAG, "Cloud sync successful")
                    true
                }
                response.status.value == 429 -> {
                    // Too Many Requests - exponential backoff
                    Log.w(TAG, "Rate limit exceeded, implementing backoff")
                    false
                }
                response.status.value in 400..499 -> {
                    // Client errors
                    Log.e(TAG, "Client error ${response.status}: ${response.bodyAsText()}")
                    false
                }
                response.status.value in 500..599 -> {
                    // Server errors - ретраим с backoff
                    Log.e(TAG, "Server error ${response.status}")
                    false
                }
                else -> {
                    Log.e(TAG, "Unexpected status ${response.status}")
                    false
                }
            }

        } catch (e: SocketTimeoutException) {
            Log.e(TAG, "Socket timeout during cloud sync")
            false
        } catch (e: ConnectException) {
            Log.e(TAG, "Connection exception during cloud sync: ${e.message}")
            false
        } catch (e: IOException) {
            Log.e(TAG, "Network IO error during cloud sync: ${e.message}")
            false
        } catch (e: SerializationException) {
            Log.e(TAG, "Serialization error during cloud sync: ${e.message}")
            false
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected exception during cloud sync: ${e.message}")
            false
        }
    }

//-----------------------------ШАГ 4: Обработка ошибок синхронизации (handleSyncFailure)------------------------------
    /**
     * Обработать неудачную синхронизацию.
     *
     */
    private suspend fun handleSyncFailure() {
        retryAttempts++  //Увеличиваем счетчик retryAttempts.

        //4.1 Если достигли maxRetryAttempts (10), ждем 1 минуту и сбрасываем счетчик.
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
     * Добавить payload в offline queue.
     * Стоит учесть, что при длительном отсутствии связи в offlineQueue может накопиться много данных
     * (до 600 пачек, т.е. 600 * 5 секунд * 10 очередей = 30000 секунд = 500 минут ~ 8.3 часа данных).
     * Максимальный объем OfflineQueue: 6000 * 0,024 МБ ≈ 142,28 МБ - за 8.3 часа данных
     */
    fun addToOfflineQueue(payload: CloudPayload) {
        try {
            synchronized(offlineQueue) {
                if (offlineQueue.size >= MAX_OFFLINE_QUEUE_SIZE) {
                    // Удаляем самые старые записи
                    val toRemove = offlineQueue.size - MAX_OFFLINE_QUEUE_SIZE + 1
                    repeat(toRemove) { offlineQueue.removeAt(0) } // ← Очистка памяти
                }

                offlineQueue.add(payload)
            }

            //  Очередь периодически сохраняется в файл (при добавлении новой очереди, если размер кратен 5, и при остановке очередь сохраняется).
            if (offlineQueue.size % 5 == 0) {
                saveOfflineQueue()
            }

            Log.d(TAG, "Added payload to offline queue (size: ${offlineQueue.size})")

        } catch (e: Exception) {
            Log.e(TAG, "Failed to add payload to offline queue", e)
        }
    }


//-----------------------------ШАГ 5: Фоновая обработка offlineQueue (processOfflineQueue)------------------------------
    /**
     * Обработать offline queue. Делаем попытки повторной синхронизации с задержкой после каждой неудачи.
     */
    suspend fun processOfflineQueue() {

        var consecutiveFailures = 0
        val maxConsecutiveFailures = 3

        while (coroutineContext.isActive) {
            try {
                //5.1  Проверяем не пуста ли очередь?
                if (isNetworkAvailable() && offlineQueue.isNotEmpty()) {
                    _syncStatus.value = SyncStatus.ProcessingOffline

                    val payload = synchronized(offlineQueue) {
                        if (offlineQueue.isNotEmpty()) offlineQueue.removeAt(0) else null
                    }
                    //5.2  Если есть, берем первый элемент очереди (самый старый) и пытаемся отправить.
                    if (payload != null) {
                        //5.3 Если отправка неудачна и сеть все еще есть, возвращаем элемент в начало очереди и ждем 10 секунд.

                        // ПРОБЛЕМА: Если отправка неуспешна, элемент возвращается в начало,
                        // но нет задержки перед следующей попыткой для этого же элемента.
                        // Возвращаем в начало постоянно один и тотже элемент бесконечно
                        /*
                        if (!success && isNetworkAvailable()) {
                            // Если network есть но отправка неудачная, возвращаем в queue
                            synchronized(offlineQueue) {
                                offlineQueue.add(0, payload) // В начало queue
                            }

                            // Ждем перед следующей попыткой
                            delay(15_000L) // 10 секунд
                        }
                         */

                        //Улучшенная обработка Offline Queue
                        val success = sendToCloud(payload)
                        if (success) {
                            consecutiveFailures = 0 // Сбрасываем счетчик при успехе
                            saveOfflineQueue() // Сохраняем состояние очереди
                        } else {
                            consecutiveFailures++
                            // Возвращаем элемент в очередь
                            synchronized(offlineQueue) {
                                offlineQueue.add(0, payload)
                            }

                            if (consecutiveFailures >= maxConsecutiveFailures) {
                                // После нескольких неудач ждем дольше
                                Log.w(TAG, "Multiple consecutive failures ($consecutiveFailures), waiting longer")
                                delay(60_000L) // 1 минута
                                consecutiveFailures = maxConsecutiveFailures - 1 // Не сбрасываем полностью
                            } else {
                                delay(15_000L) // Стандартная задержка
                            }
                            continue // Пропускаем внешний delay
                        }

                    }
                }

                delay(10_000L) // Проверяем offline queue каждые 5 секунд

            } catch (e: Exception) {
                Log.e(TAG, "Error processing offline queue", e)
                consecutiveFailures++
                delay(30_000L)
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


//-----------------------------ШАГ 6:  Сохранение и загрузка offlineQueue------------------------------
    /**
     * Сохранить offline Queue в файл если количество очередей равно 10
     */
    private fun saveOfflineQueue() {
        try {
            //Если очередь не пустая OfflineQueue сохраняется в файл (cloud_sync_queue.json)
            if (offlineQueue.isNotEmpty()) {
                val json = Json { prettyPrint = true }
                val jsonString = json.encodeToString(
                    ListSerializer(CloudPayload.serializer()),
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
     * Загрузить offline Queue из файла
     */
    private fun loadOfflineQueue() {
        try {
            if (offlineQueueFile.exists()) {
                val jsonString = offlineQueueFile.readText()
                val json = Json { ignoreUnknownKeys = true }
                val loadedQueue = json.decodeFromString(
                    ListSerializer(CloudPayload.serializer()),
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
        //newCraneId: String? = null //Уберем  параметр newCraneId из updateConfiguration
    ) {
        newEndpoint?.let { cloudEndpoint = it }
        //newCraneId?.let { craneId = it } //Уберем  параметр newCraneId из updateConfiguration

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
     * Очистить offline queue. Освобождает память, занимаемую очередью offlineQueue,
     * и удаляет файл на диске. Это освобождает как оперативную память,
     * так и память во внутреннем хранилище приложения.
    Потенциальные проблемы:
     * Если не очищать offlineQueue, то при большом количестве данных в очереди будет занята память и место на диске.
     */
    fun clearOfflineQueue() {
        synchronized(offlineQueue) {
            offlineQueue.clear()
        }
        offlineQueueFile.delete()
        Log.i(TAG, "Offline queue cleared")
    }
    /**
     */

    // Добавляем метод для принудительной проверки статуса
    fun checkConnectionStatus(): Boolean {
        updateConnectionStatus()
        return _cloudConnectionStatus.value
    }


    /**
     * Cleanup ресурсов. Освобождает ресурсы (HTTP-клиент, отменяет корутины),
     * что может помочь в освобождении памяти, особенно если CloudSynchronizer больше не будет использоваться.
    Потенциальные проблемы:
     *      * Если не вызывать cleanup, то HTTP-клиент остается открытым, а корутины (syncScope) не отменяются, что может привести к утечкам памяти.
     */
    fun cleanup() {
        stopSync()
        saveOfflineQueue()
        httpClient.close()
        syncScope.cancel()
        craneIdJob?.cancel()
        connectionMonitorJob?.cancel()
    }

}