package com.example.giraffenew.android_error_logger.domian.usecase

import com.example.giraffenew.android_error_logger.data.repository.ExceptionRepositoryImpl
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetActiveOperationsUseCase @Inject constructor(
    private val repository: ExceptionRepositoryImpl
) {
    operator fun invoke(): StateFlow<Int> = repository.activeOperations
}