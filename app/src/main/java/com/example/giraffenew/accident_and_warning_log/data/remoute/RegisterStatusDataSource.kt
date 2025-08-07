package com.example.giraffenew.accident_and_warning_log.data.remoute

import kotlinx.coroutines.flow.Flow

//Создайте интерфейс источника данных регистров
interface RegisterStatusDataSource {
    //через registerStatuses мы получаем из EventsViewModel обработанные состояния регистров
    val registerStatuses: Flow<Map<String, Boolean>>
}