package com.example.giraffenew.accident_and_warning_log.data.remoute

import android.content.Context
import android.util.Log
import com.example.giraffenew.accident_and_warning_log.domian.model.EventDefinition
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import jakarta.inject.Inject

//Данный код представляет собой реализацию источника данных для работы с JSON-файлом в Android-приложении.
//Он использует Dependency Injection (DI) через аннотацию @Inject для получения контекста приложения
open class JsonDataSource
@Inject constructor(
    @ApplicationContext private val context: Context
    //Аннотация @ApplicationContext указывает, что требуется контекст всего приложения
    //Контекст private val означает, что он доступен только для чтения и инициализируется один раз
) {

    private val definitions: List<EventDefinition> by lazy {
        // свойство definitions нужно для формирования списка объектов EventDefinition
        //  by lazy - данные будут загружены только при первом обращении к свойству
        try {
            //Загрузка данных происходит следующим образом:
            // - Открывается файл events.json из папки assets
            //context.assets.open("events.json")
            val inputStream = context.assets.open("events.json")

                // - Создается буферный ридер для чтения файла
            val jsonString = inputStream.bufferedReader().use { it.readText() }
                 // - Используется Gson для парсинга JSON в массив объектов EventDefinition

            val list = Gson().fromJson(jsonString,
                // - Массив преобразуется в список EventDefinition, который далее используется в методе getEventDefinition
                Array<EventDefinition>::class.java).toList()
            Log.d("JSON_LOAD", "Successfully loaded ${list.size} events from events.json")
            list
        } catch (e: Exception) {
            // Логируем ошибку, чтобы понять, что пошло не так
            Log.e("JsonDataSource", "Error loading events.json", e)
            emptyList()
        }
    }

    open fun getEventDefinition(registrationNumber: String): EventDefinition {
        return definitions.firstOrNull { it.registrationNumber == registrationNumber }
            ?: throw NoSuchElementException("Event definition not found for $registrationNumber")
    //Метод getEventDefinition:
        //Принимает параметр registrationNumber типа String
        //Возвращает объект EventDefinition, найденный по номеру регистрации
        //Использует метод first(), который возвращает первый элемент, удовлетворяющий условию
        //Условие поиска: совпадение registrationNumber с искомым значением

    }
    open fun getAllRegistrationNumbers(): List<String> {
        return definitions.map { it.registrationNumber } //извлекает значение этого свойства для каждого элемента
    }
    //Поэтапное выполнение метода getAllRegistrationNumbers:
    //1. Метод получает доступ к списку definitions
    //2. Для каждого объекта в списке:
    // - Берется текущий объект (через it)
    // - Извлекается его поле registrationNumber
    // - Значение добавляется в новый список
    //Возвращается новый список, содержащий только номера регистрации

}
//Для активации работы с JSON надо добавить Gson библиотеку implementation("com.google.code.gson:gson:2.10.1") в build.gradle.kts