package com.example.giraffenew.android_error_logger.domian.usecase

import com.example.giraffenew.android_error_logger.domian.model.ExceptionEntity
import com.example.giraffenew.android_error_logger.domian.model.LogExceptionLevel
import com.example.giraffenew.android_error_logger.domian.repository.ExceptionRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExceptionsByLevelUseCase
@Inject constructor(
    private val repository: ExceptionRepository
){
    operator fun invoke(level: LogExceptionLevel): Flow<List<ExceptionEntity>> = repository.getAllExceptionByLevel(level)
}