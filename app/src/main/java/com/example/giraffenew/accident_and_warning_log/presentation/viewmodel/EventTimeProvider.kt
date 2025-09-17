package com.example.giraffenew.accident_and_warning_log.presentation.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EventTimeProvider @Inject constructor() {
    private val _eventTimeMillis = MutableStateFlow(System.currentTimeMillis())
    val eventTimeMillis: StateFlow<Long> = _eventTimeMillis.asStateFlow()

    fun updateEventTime(millis: Long) {
        _eventTimeMillis.value = millis
    }
}