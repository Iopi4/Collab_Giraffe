package com.example.giraffenew.android_error_logger.domian.usecase

import com.example.giraffenew.android_error_logger.domian.repository.ExceptionRepository
import javax.inject.Inject


class GetExceptionsCountUseCase @Inject constructor(
    private val repository: ExceptionRepository
) {
    suspend operator fun invoke(): Int = repository.getExceptionsCount()
}