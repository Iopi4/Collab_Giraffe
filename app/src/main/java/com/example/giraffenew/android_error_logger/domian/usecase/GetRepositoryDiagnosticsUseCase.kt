package com.example.giraffenew.android_error_logger.domian.usecase

import com.example.giraffenew.android_error_logger.data.repository.ExceptionRepositoryImpl
import com.example.giraffenew.android_error_logger.data.repository.RepositoryDiagnostics
import javax.inject.Inject


// Новый UseCase для мониторинга
class GetRepositoryDiagnosticsUseCase @Inject constructor(
    private val repository: ExceptionRepositoryImpl
) {
    suspend operator fun invoke(): RepositoryDiagnostics = repository.getDiagnostics()
}