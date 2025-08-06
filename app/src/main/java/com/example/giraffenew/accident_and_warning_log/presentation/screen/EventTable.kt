package com.example.giraffenew.accident_and_warning_log.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.giraffenew.accident_and_warning_log.domian.model.EventEntity

@Composable
//2. Компонент EventTable для формирования таблицы. Заголовок, Шапка таблицы, Строки таблицы
fun EventTable(
    events:List<EventEntity>,
    title: String) {
    Column {
        Text(title, style = MaterialTheme.typography.bodyLarge)
        Row(modifier = Modifier.fillMaxWidth()) {
            // Шапка таблицы
            Text("Дата", Modifier.weight(1f))
            Text("Время", Modifier.weight(1f))
            Text("Описание", Modifier.weight(4f))

        }
        //forEach — это функция-расширение в Kotlin, которая:
        // - Проходит по каждому элементу коллекции
        // - Выполняет заданное действие для каждого элемента
        // - В нашем случае — создает UI-компонент для каждого события events из класса для работы с БД
        events.forEach { EventRow(event = it) }
        //Получение списка — берется список событий events
        //Перебор элементов forEach — для каждого события в списке:
        // - Берется текущий элемент (it)
        // - Создается компонент EventRow
        // - Передается текущий элемент в качестве параметра
        //Результат — все строки таблицы добавляются последовательно
    }
}