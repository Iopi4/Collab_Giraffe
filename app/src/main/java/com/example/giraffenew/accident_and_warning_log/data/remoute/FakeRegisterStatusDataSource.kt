package com.example.giraffenew.accident_and_warning_log.data.remoute

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


// Реализация (заглушка для тестирования)
class FakeRegisterStatusDataSource : RegisterStatusDataSource {
    private val _registerStatuses = MutableStateFlow<Map<String, Boolean>>(emptyMap())

    override val registerStatuses: Flow<Map<String, Boolean>> = _registerStatuses

    // Для тестирования и внешнего управления
    fun updateStatus(register: String, active: Boolean) {
        val newStatus = _registerStatuses.value.toMutableMap().apply {
            put(register, active)
        }
        _registerStatuses.value = newStatus
    }
}