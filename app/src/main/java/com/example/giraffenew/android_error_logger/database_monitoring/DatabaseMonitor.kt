package com.example.giraffenew.android_error_logger.database_monitoring

import android.util.Log
import com.example.giraffenew.android_error_logger.data.repository.ExceptionRepositoryImpl
import com.example.giraffenew.android_error_logger.data.repository.RepositoryDiagnostics
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class DatabaseMonitor @Inject constructor(
    private val exceptionRepository: ExceptionRepositoryImpl
) {

    private companion object {
        const val TAG = "DatabaseMonitor"
    }

    private val _memoryUsage = MutableStateFlow<MemoryUsage>(MemoryUsage())
    val memoryUsage: StateFlow<MemoryUsage> = _memoryUsage.asStateFlow()

    private var monitoringJob: Job? = null

    fun startMonitoring() {
        if (monitoringJob != null) {
            Log.d(TAG, "Monitoring already started")
            return
        }

        monitoringJob = CoroutineScope(Dispatchers.Default).launch {
            Log.d(TAG, "Starting database monitoring")
            while (isActive) {
                updateMemoryUsage()
                delay(5000) // Каждые 5 секунд
            }
        }
    }

    fun stopMonitoring() {
        Log.d(TAG, "Stopping database monitoring")
        monitoringJob?.cancel()
        monitoringJob = null
    }

    private fun updateMemoryUsage() {
        try {
            val runtime = Runtime.getRuntime()
            val usedMemory = (runtime.totalMemory() - runtime.freeMemory()) / (1024 * 1024)
            val maxMemory = runtime.maxMemory() / (1024 * 1024)

            _memoryUsage.value = MemoryUsage(
                usedMemoryMB = usedMemory,
                maxMemoryMB = maxMemory,
                timestamp = System.currentTimeMillis()
            )

            // Логируем только если использование памяти высокое
            if (usedMemory > maxMemory * 0.7) {
                Log.w(TAG, "High memory usage: ${usedMemory}MB / ${maxMemory}MB")
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error updating memory usage", e)
        }
    }

    suspend fun getFullDiagnostics(): FullDiagnostics {
        return try {
            FullDiagnostics(
                memoryUsage = _memoryUsage.value,
                repositoryDiagnostics = exceptionRepository.getDiagnostics(),
                gcStats = getGcStats()
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error getting full diagnostics", e)
            FullDiagnostics(
                memoryUsage = MemoryUsage(),
                repositoryDiagnostics = RepositoryDiagnostics(0, 0, System.currentTimeMillis()),
                gcStats = getGcStats()
            )
        }
    }

    private fun getGcStats(): GcStats {
        val runtime = Runtime.getRuntime()
        return GcStats(
            totalMemory = runtime.totalMemory(),
            freeMemory = runtime.freeMemory(),
            usedMemory = runtime.totalMemory() - runtime.freeMemory()
        )
    }

    fun logDatabaseMetrics() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val diagnostics = getFullDiagnostics()
                Log.i(TAG, """
                    Database Metrics:
                    - Active operations: ${diagnostics.repositoryDiagnostics.activeOperations}
                    - Total exceptions: ${diagnostics.repositoryDiagnostics.totalExceptions}
                    - Memory usage: ${diagnostics.memoryUsage.usedMemoryMB}MB / ${diagnostics.memoryUsage.maxMemoryMB}MB
                    - Used memory: ${diagnostics.gcStats.usedMemory / (1024 * 1024)}MB
                """.trimIndent())
            } catch (e: Exception) {
                Log.e(TAG, "Error logging database metrics", e)
            }
        }
    }
}

data class MemoryUsage(
    val usedMemoryMB: Long = 0,
    val maxMemoryMB: Long = 0,
    val timestamp: Long = System.currentTimeMillis()
)

data class FullDiagnostics(
    val memoryUsage: MemoryUsage,
    val repositoryDiagnostics: RepositoryDiagnostics,
    val gcStats: GcStats
)

data class GcStats(
    val totalMemory: Long,
    val freeMemory: Long,
    val usedMemory: Long
)