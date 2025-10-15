package com.example.giraffenew.android_error_logger.data.repository

import com.example.giraffenew.android_error_logger.data.dao.ExceptionDao
import com.example.giraffenew.android_error_logger.domian.model.ExceptionEntity
import com.example.giraffenew.android_error_logger.domian.model.LogExceptionLevel
import com.example.giraffenew.android_error_logger.domian.repository.ExceptionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExceptionRepositoryImpl
@Inject constructor(
    private val dao: ExceptionDao
): ExceptionRepository {
    override suspend fun logException(exception: ExceptionEntity) {
        dao.insert(exception)
    }

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