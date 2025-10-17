package com.example.giraffenew.database_monitoring

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppActivityMonitor @Inject constructor() {

    private companion object {
        const val TAG = "AppActivityMonitor"
    }

    private val _globalOperationCounter = AtomicInteger(0)
    private val _globalActiveOperations = MutableStateFlow(0)
    val globalActiveOperations: StateFlow<Int> = _globalActiveOperations.asStateFlow()

    private val _componentOperations = mutableMapOf<String, AtomicInteger>()
    private val _componentActiveOperations = mutableMapOf<String, MutableStateFlow<Int>>()

    // Добавляем детальную статистику по методам
    private val _methodStatistics = mutableMapOf<String, MethodStats>()
    private val _methodStatsFlow = MutableStateFlow<Map<String, MethodStats>>(emptyMap())
    val methodStatsFlow: StateFlow<Map<String, MethodStats>> = _methodStatsFlow.asStateFlow()

    // Для отслеживания памяти
    private val _memoryStats = MutableStateFlow(MemoryStats())
    val memoryStats: StateFlow<MemoryStats> = _memoryStats.asStateFlow()

    private var monitoringJob: Job? = null

    data class MethodStats(
        val methodName: String,
        var callCount: Long = 0,
        var activeCalls: Int = 0,
        var totalExecutionTime: Long = 0,
        var lastCallTime: Long = 0,
        var errorCount: Long = 0
    ) {
        val averageExecutionTime: Long
            get() = if (callCount > 0) totalExecutionTime / callCount else 0
    }

    data class MemoryStats(
        val usedMemoryMB: Long = 0,
        val maxMemoryMB: Long = 0,
        val databaseMemoryMB: Long = 0,
        val timestamp: Long = System.currentTimeMillis()
    )

    fun trackOperationStart(component: String = "Unknown", method: String = "unknown") {
        val fullMethodName = "$component.$method"

        _globalOperationCounter.incrementAndGet()
        _globalActiveOperations.value = _globalOperationCounter.get()

        // Отслеживание по компоненту
        val componentCounter = _componentOperations.getOrPut(component) { AtomicInteger(0) }
        componentCounter.incrementAndGet()

        val componentFlow = _componentActiveOperations.getOrPut(component) { MutableStateFlow(0) }
        componentFlow.value = componentCounter.get()

        // Детальная статистика по методам
        val methodStats = _methodStatistics.getOrPut(fullMethodName) {
            MethodStats(methodName = fullMethodName)
        }
        methodStats.callCount++
        methodStats.activeCalls++
        methodStats.lastCallTime = System.currentTimeMillis()

        updateMethodStatsFlow()
        updateMemoryStats()
    }

    fun trackOperationEnd(component: String = "Unknown", method: String = "unknown", executionTime: Long = 0) {
        val fullMethodName = "$component.$method"

        _globalOperationCounter.decrementAndGet()
        _globalActiveOperations.value = _globalOperationCounter.get()

        // Отслеживание по компоненту
        val componentCounter = _componentOperations[component]
        componentCounter?.decrementAndGet()

        val componentFlow = _componentActiveOperations[component]
        componentFlow?.value = componentCounter?.get() ?: 0

        // Детальная статистика по методам
        val methodStats = _methodStatistics[fullMethodName]
        methodStats?.let {
            it.activeCalls--
            it.totalExecutionTime += executionTime
        }

        updateMethodStatsFlow()
        updateMemoryStats()
    }

    fun trackOperationError(component: String = "Unknown", method: String = "unknown") {
        val fullMethodName = "$component.$method"
        val methodStats = _methodStatistics[fullMethodName]
        methodStats?.errorCount = (methodStats?.errorCount ?: 0) + 1
        updateMethodStatsFlow()
    }

    private fun updateMethodStatsFlow() {
        _methodStatsFlow.value = _methodStatistics.toMap()
    }

    private fun updateMemoryStats() {
        val runtime = Runtime.getRuntime()
        val usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)
        val maxMemory = runtime.maxMemory() / (1024 * 1024)

        _memoryStats.value = MemoryStats(
            usedMemoryMB = usedMemory,
            maxMemoryMB = maxMemory,
            databaseMemoryMB = calculateDatabaseMemory(),
            timestamp = System.currentTimeMillis()
        )
    }

    private fun calculateDatabaseMemory(): Long {
        // Эмпирическая оценка использования памяти базой данных
        // Можно улучшить с помощью Debug.MemoryInfo
        return _globalOperationCounter.get() * 2L // Примерная оценка
    }

    fun getComponentOperations(component: String): StateFlow<Int> {
        return _componentActiveOperations.getOrPut(component) { MutableStateFlow(0) }
    }

    fun getMethodStats(methodName: String): MethodStats? {
        return _methodStatistics[methodName]
    }

    suspend fun getGlobalDiagnostics(): GlobalActivityDiagnostics {
        return GlobalActivityDiagnostics(
            globalActiveOperations = _globalOperationCounter.get(),
            componentOperations = _componentOperations.mapValues { it.value.get() },
            methodStatistics = _methodStatistics.mapValues { it.value.copy() },
            memoryStats = _memoryStats.value,
            timestamp = System.currentTimeMillis()
        )
    }

    fun startMonitoring() {
        if (monitoringJob != null) {
            Log.d(TAG, "Monitoring already started")
            return
        }

        monitoringJob = CoroutineScope(Dispatchers.Default).launch {
            Log.d(TAG, "Starting application activity monitoring")
            while (isActive) {
                updateMemoryStats()
                delay(2000) // Обновляем каждые 2 секунды
            }
        }
    }

    fun stopMonitoring() {
        Log.d(TAG, "Stopping application activity monitoring")
        monitoringJob?.cancel()
        monitoringJob = null
    }

    fun logActivityMetrics() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val diagnostics = getGlobalDiagnostics()
                Log.i(TAG, """
                    Activity Metrics:
                    - Global active operations: ${diagnostics.globalActiveOperations}
                    - Memory usage: ${diagnostics.memoryStats.usedMemoryMB}MB / ${diagnostics.memoryStats.maxMemoryMB}MB
                    - Top methods: ${diagnostics.methodStatistics.values.sortedByDescending { it.callCount }.take(5).joinToString { "${it.methodName}(${it.callCount})" }}
                """.trimIndent())
            } catch (e: Exception) {
                Log.e(TAG, "Error logging activity metrics", e)
            }
        }
    }
}

data class GlobalActivityDiagnostics(
    val globalActiveOperations: Int,
    val componentOperations: Map<String, Int>,
    val methodStatistics: Map<String, AppActivityMonitor.MethodStats>,
    val memoryStats: AppActivityMonitor.MemoryStats,
    val timestamp: Long
)