package com.example.giraffenew.accident_and_warning_log.domian.usecase

import com.example.giraffenew.accident_and_warning_log.data.repository.EventRepository
import javax.inject.Inject

class ClearArchiveUseCase
@Inject constructor(
    private val repository: EventRepository)
{
    suspend operator fun invoke() = repository.clearArchive()
}