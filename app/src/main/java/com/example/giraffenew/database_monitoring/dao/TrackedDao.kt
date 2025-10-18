package com.example.giraffenew.database_monitoring.dao

import com.example.giraffenew.database_monitoring.AppActivityMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


// Базовый класс для отслеживания DAO операций
abstract class TrackedDao(
    private val monitor: AppActivityMonitor,
    private val daoName: String
) {

    protected suspend fun <T> trackOperation(
        methodName: String,
        block: suspend () -> T
    ): T {
        val startTime = System.currentTimeMillis()
        monitor.trackOperationStart(daoName, methodName)

        return try {
            val result = block()
            result
        } catch (e: Exception) {
            monitor.trackOperationError(daoName, methodName)
            throw e
        } finally {
            val executionTime = System.currentTimeMillis() - startTime
            monitor.trackOperationEnd(daoName, methodName, executionTime)
        }
    }

    protected fun <T> trackFlowOperation(
        methodName: String,
        flowBuilder: () -> Flow<T>  // Ожидает лямбду, а не готовый Flow
    ): Flow<T> {
        return flow {
            val startTime = System.currentTimeMillis()
            monitor.trackOperationStart(daoName, methodName)

            try {
                flowBuilder().collect { value ->  // Вызываем лямбду здесь
                    emit(value)
                }
            } catch (e: Exception) {
                monitor.trackOperationError(daoName, methodName)
                throw e
            } finally {
                val executionTime = System.currentTimeMillis() - startTime
                monitor.trackOperationEnd(daoName, methodName, executionTime)
            }
        }
    }
}