package com.example.giraffenew.accident_and_warning_log.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.giraffenew.accident_and_warning_log.domian.model.EventEntity

@Composable
//2. Компонент EventTable для формирования таблицы. Заголовок, Шапка таблицы, Строки таблицы
fun EventTable(
    events:List<EventEntity>,
    title: String,
    modifier: Modifier = Modifier) {
    val scrollState = rememberScrollState()

    LazyColumn (
        modifier = modifier.fillMaxWidth()
    )
    {
        item {
            Text(title, style = MaterialTheme.typography.bodyLarge)
        }
        item {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text("Дата", Modifier.weight(1f))
                Text("Время", Modifier.weight(1f))
                Text("Описание", Modifier.weight(4f))
            }
        }
        items(events) { event -> EventRow(event = event) }
        //events.forEach { EventRow(event = it) }
        //events.forEach { event -> EventRow(event = event) }

        /**
         forEach — это функция-расширение в Kotlin, которая:
         * Проходит по каждому элементу коллекции
         * Выполняет заданное действие для каждого элемента
         * В нашем случае — создает UI-компонент для каждого события events из класса для работы с БД

        Получение списка — берется список событий events
        Перебор элементов forEach — для каждого события в списке:
          *  берется текущий элемент (it)
          * Создается компонент EventRow
          * Передается текущий элемент в качестве параметра
        Результат — все строки таблицы добавляются последовательно
         */


    }
}