package com.example.giraffenew.accident_and_warning_log.domian.model

// Определение события из JSON
//Это data-класс, который представляет собой структуру данных для хранения определения события.
//EventDefinition используется для парсинга JSON-данных и создания базовой структуры события.
data class EventDefinition(
    val registrationNumber: String, //номер регистра
    val type: EventType, //Тип события: Авария или Предупреждение
    val description: String //Описание
)

// Тип события EventType. Jбеспечивает типобезопасность при работе с типами событий.
enum class EventType { ACCIDENT, WARNING }
//перечисление (enum), определяет два возможных типа событий: