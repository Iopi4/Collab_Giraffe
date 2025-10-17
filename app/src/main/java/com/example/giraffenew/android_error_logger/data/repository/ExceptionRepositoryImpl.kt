package com.example.giraffenew.android_error_logger.data.repository

import com.example.giraffenew.android_error_logger.data.dao.ExceptionDao
import com.example.giraffenew.android_error_logger.domian.model.ExceptionEntity
import com.example.giraffenew.android_error_logger.domian.model.LogExceptionLevel
import com.example.giraffenew.android_error_logger.domian.repository.ExceptionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject
import javax.inject.Singleton

/**@Singleton
class ExceptionRepositoryImpl @Inject constructor(
    private val dao: ExceptionDao
): ExceptionRepository {

    override suspend fun logException(exception: ExceptionEntity) {
        dao.insert(exception)
    }

    // Для real-time updates оставить Flow, но ограничить использование
    override fun getAllExceptions(): Flow<List<ExceptionEntity>> {
        return dao.getAll()
    }

    override fun getAllExceptionByLevel(level: LogExceptionLevel): Flow<List<ExceptionEntity>> {
        return dao.getByLevel(level)
    }

    override suspend fun clearAllExceptions() {
        dao.clearAll()
    }

    override suspend fun getExceptionsCount(): Int {
        return dao.getCount()
    }
}
*/
@Singleton
class ExceptionRepositoryImpl @Inject constructor(
    private val dao: ExceptionDao
) : ExceptionRepository {

    private companion object {
        const val TAG = "ExceptionRepository"
    }

    private val _operationCounter = AtomicInteger(0)

    // Добавляем StateFlow для отслеживания активных операций в реальном времени
    private val _activeOperations = MutableStateFlow(0)
    val activeOperations: StateFlow<Int> = _activeOperations.asStateFlow()

    private fun updateOperationCounter() {
        _activeOperations.value = _operationCounter.get()
    }

    override suspend fun logException(exception: ExceptionEntity) {
        _operationCounter.incrementAndGet()
        updateOperationCounter() // Обновляем StateFlow
        val startTime = System.currentTimeMillis()
        try {
            dao.insert(exception)
            val duration = System.currentTimeMillis() - startTime
            android.util.Log.d(TAG, "logException completed in ${duration}ms")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Failed to log exception", e)
            throw e
        } finally {
            _operationCounter.decrementAndGet()
            updateOperationCounter() // Обновляем StateFlow
        }
    }

    override suspend fun getAllExceptions(): List<ExceptionEntity> {
        _operationCounter.incrementAndGet()
        updateOperationCounter() // Обновляем StateFlow
        val startTime = System.currentTimeMillis()
        return try {
            val result = dao.getAll()
            val duration = System.currentTimeMillis() - startTime
            android.util.Log.d(TAG, "getAllExceptions returned ${result.size} items in ${duration}ms")
            result
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Failed to get all exceptions", e)
            throw e
        } finally {
            _operationCounter.decrementAndGet()
            updateOperationCounter() // Обновляем StateFlow
        }
    }

    override suspend fun getAllExceptionByLevel(level: LogExceptionLevel): List<ExceptionEntity> {
        _operationCounter.incrementAndGet()
        updateOperationCounter() // Обновляем StateFlow
        val startTime = System.currentTimeMillis()
        return try {
            val result = dao.getByLevel(level)
            val duration = System.currentTimeMillis() - startTime
            android.util.Log.d(TAG, "getAllExceptionByLevel[$level] returned ${result.size} items in ${duration}ms")
            result
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Failed to get exceptions by level: $level", e)
            throw e
        } finally {
            _operationCounter.decrementAndGet()
            updateOperationCounter() // Обновляем StateFlow
        }
    }

    override suspend fun clearAllExceptions() {
        _operationCounter.incrementAndGet()
        updateOperationCounter() // Обновляем StateFlow
        val startTime = System.currentTimeMillis()
        try {
            dao.clearAll()
            val duration = System.currentTimeMillis() - startTime
            android.util.Log.d(TAG, "clearAllExceptions completed in ${duration}ms")
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Failed to clear exceptions", e)
            throw e
        } finally {
            _operationCounter.decrementAndGet()
            updateOperationCounter() // Обновляем StateFlow
        }
    }

    override suspend fun getExceptionsCount(): Int {
        _operationCounter.incrementAndGet()
        updateOperationCounter() // Обновляем StateFlow
        val startTime = System.currentTimeMillis()
        return try {
            val result = dao.getCount()
            val duration = System.currentTimeMillis() - startTime
            android.util.Log.d(TAG, "getExceptionsCount: $result in ${duration}ms")
            result
        } catch (e: Exception) {
            android.util.Log.e(TAG, "Failed to get exceptions count", e)
            throw e
        } finally {
            _operationCounter.decrementAndGet()
            updateOperationCounter() // Обновляем StateFlow
        }
    }

    // Мониторинг активных операций
    fun getActiveOperationsCount(): Int = _operationCounter.get()

    // Полная диагностика состояния
    suspend fun getDiagnostics(): RepositoryDiagnostics {
        return RepositoryDiagnostics(
            activeOperations = _operationCounter.get(), // Теперь здесь используется актуальное значение
            totalExceptions = dao.getCount(),
            timestamp = System.currentTimeMillis()
        )
    }
}

// Выносим data class наружу
data class RepositoryDiagnostics(
    val activeOperations: Int,
    val totalExceptions: Int,
    val timestamp: Long
)