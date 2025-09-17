package com.example.giraffenew.accident_and_warning_log.data.repository

import android.util.Log
import com.example.giraffenew.accident_and_warning_log.data.local.EventDao
import com.example.giraffenew.accident_and_warning_log.data.remoute.JsonDataSource
import com.example.giraffenew.accident_and_warning_log.domian.model.EventEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

//Репозиторий в Android Studio — это центральное место, где хранятся и управляются данные проекта.
//Он содержит все файлы проекта и отслеживает историю изменений в них.

open class EventRepository
    @Inject constructor( //используется для внедрения зависимостей (DI) в класс
        private val dataSource: JsonDataSource, //источник данных (для работы с JSON)
        private val dao: EventDao //объект для работы с базой данных. через EventDao можно загрузить или обновить БД
    ) {
    //функция контроля изменения статуса
        open suspend fun handleStatusChange(
        registrationNumber: String,
        isActive: Boolean,
        eventTime: Long
        )
    {
        try {
            if (isActive) {
                // Логика для активации события
                // - Получаем определение события из источника данных:
                val definition = dataSource.getEventDefinition(registrationNumber)
                //Создает новый объект EventEntity:
                //- Заполняет поля на основе полученных данных
                //- Устанавливает isActive = true
                //- Записывает текущее время в timestamp

                val existingEvent = dao.getActiveEventByRegistration(registrationNumber)

            //Создаем событие используя класс EventEntity для работы с БД из domian
                if (existingEvent == null) {
                    val event = EventEntity(
                        registrationNumber = registrationNumber,
                        type = definition.type,
                        description = definition.description,
                        isActive = true,
                        timestamp = eventTime,
                    )
                    dao.insert(event)
                }
            //Вставляем запись события в базу данных через DAO
        }
            else {
            // Логика для деактивации события
            //- Получаем активные события:
            dao.getActiveEvents().first()
                // -Находим событие по номеру регистрации:
                .find{it.registrationNumber == registrationNumber}
                // -Если событие найдено, обновляет его статус:
                ?.let { dao.update(it.copy(isActive = false))
                }
            }
        }catch (e: Exception) {
            Log.e("REGISTER_ERROR", "Error handling status for $registrationNumber", e)

        }
    }
    //3. Методы получения данных
    open fun getActiveEvents(): Flow<List<EventEntity>> = dao.getActiveEvents()
    open fun getArchivedEvents(): Flow<List<EventEntity>> = dao.getArchivedEvents()
//Возвращают потоки (Flow) активных и архивных событий
//Просто проксируют вызовы к DAO

//4. Метод очистки архива
    open suspend fun clearArchive() = dao.clearArchive() //Удаляет все архивные записи из базы данных

}

//Такой подход обеспечивает:
//Отделение бизнес-логики от работы с данными
//Возможность легкой замены источников данных
//Асинхронную работу с базой данных
//Отслеживание изменений в реальном времени