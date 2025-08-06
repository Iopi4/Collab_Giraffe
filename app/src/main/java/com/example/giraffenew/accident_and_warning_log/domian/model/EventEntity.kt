package com.example.giraffenew.accident_and_warning_log.domian.model

import androidx.room.Entity
import androidx.room.PrimaryKey

//Это entity-класс для работы с базой данных
@Entity(tableName = "events") // название таблицы в БД

data class EventEntity(
    //@PrimaryKey — первичный ключ с автоинкрементом
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, //— уникальный идентификатор записи
    val registrationNumber: String, //регистрационный номер
    val type: EventType, //тип события
    val description: String, //описание
    val isActive: Boolean, //флаг активности события
    val timestamp: Long, //временная метка события
)

//EventEntity служит для хранения событий в базе данных с дополнительными полями (id, isActive, timestamp).


