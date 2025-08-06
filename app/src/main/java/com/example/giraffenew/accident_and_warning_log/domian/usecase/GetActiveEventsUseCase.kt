package com.example.giraffenew.accident_and_warning_log.domian.usecase

import com.example.giraffenew.accident_and_warning_log.data.repository.EventRepository
import com.example.giraffenew.accident_and_warning_log.domian.model.EventEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetActiveEventsUseCase
@Inject constructor(
    private val repository: EventRepository)
{
    operator fun invoke(): Flow<List<EventEntity>> = repository.getActiveEvents()
}