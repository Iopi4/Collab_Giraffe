package com.example.giraffenew.android_error_logger.domian.repository

import com.example.giraffenew.android_error_logger.domian.model.ExceptionEntity
import com.example.giraffenew.android_error_logger.domian.model.LogExceptionLevel
import kotlinx.coroutines.flow.Flow

interface ExceptionRepository {

    suspend fun logException(exception: ExceptionEntity)

    suspend fun getAllExceptions(): List<ExceptionEntity>
    //fun getAllExceptions(): Flow<List<ExceptionEntity>>

    suspend fun getAllExceptionByLevel(level: LogExceptionLevel): List<ExceptionEntity>
    //fun getAllExceptionByLevel(level: LogExceptionLevel): Flow<List<ExceptionEntity>>

    suspend fun clearAllExceptions()
    suspend fun getExceptionsCount(): Int
}