package com.example.giraffenew.accident_and_warning_log.data.remoute

import kotlinx.coroutines.flow.Flow

//Создайте интерфейс источника данных регистров
interface RegisterStatusDataSource {
    val registerStatuses: Flow<Map<String, Boolean>>
}