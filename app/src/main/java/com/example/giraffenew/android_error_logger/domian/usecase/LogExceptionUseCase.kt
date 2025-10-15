package com.example.giraffenew.android_error_logger.domian.usecase

import com.example.giraffenew.android_error_logger.domian.model.ExceptionEntity
import com.example.giraffenew.android_error_logger.domian.repository.ExceptionRepository
import javax.inject.Inject

class LogExceptionUseCase
@Inject constructor(
    private val repository: ExceptionRepository
){
    suspend operator fun invoke(exception: ExceptionEntity){
        repository.logException(exception)
    }
}