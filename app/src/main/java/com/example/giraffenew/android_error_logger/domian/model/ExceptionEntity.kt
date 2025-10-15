package com.example.giraffenew.android_error_logger.domian.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.giraffenew.data.entities.LogLevel

//Это entity-класс для работы с базой данных
@Entity(tableName = "exceptions")// название таблицы в БД
data class ExceptionEntity(
    //@PrimaryKey — первичный ключ с автоинкрементом
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0, //— уникальный идентификатор записи
    val timestamp: Long, //временная метка события. Дата (Date) - дата события. Время (Time) - время события с миллисекундами.
    val date: String, // "2025-10-08"
    val time: String, // "16:29:49.712"
    val pid: Int, //Идентификатор процесса
    val tid: Int, //Идентификатор процесса
    val tag: String, //тэг, обычно указывает на компонент системы или приложения, который записывает в лог.
    val packageName: String, // Процесс (Process) - имя процесса, который записал сообщение.
    val level: LogExceptionLevel,  //Уровень логирования (Level) - уровень важности сообщения. одна из букв: V (Verbose), D (Debug), I (Info), W (Warning), E (Error), F (Fatal), S (Silent)
    val message: String //текст сообщения.
)

enum class LogExceptionLevel  { ERROR, FATAL }