package com.example.giraffenew.accident_and_warning_log.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.giraffenew.accident_and_warning_log.presentation.viewmodel.EventsViewModel
import com.example.giraffenew.R
import com.example.giraffenew.accident_and_warning_log.domian.model.EventEntity
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme


@Composable
//1. Основной экран (EventsScreen)
fun EventsScreen(viewModel: EventsViewModel = hiltViewModel()) //— функция для автоматического внедрения ViewModel через Hilt DI
{
    val activeEvents by viewModel.activeEvents.collectAsState() //collectAsState() — преобразует поток данных в состояние, которое может использоваться в UI
    val archivedEvents by viewModel.archivedEvents.collectAsState()
    val context = LocalContext.current //— получение текущего контекста приложения
    val scrollState = rememberScrollState() //— создание состояния прокрутки для списка

    // Состояние для выбранной вкладки
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf(stringResource(R.string.Alarm_ActiveEvents), stringResource(R.string.Alarm_ArchiveEvents))

//Структура экрана. Для начала создадим отдельные компоненты из которых соберем весь экран
    Column(modifier = Modifier.fillMaxSize()) {
        // Переключатель вкладок
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { title }
                )
            }
        }

// Контент в зависимости от выбранной вкладки
        when (selectedTab) {
            0 -> ActiveEventsContent(activeEvents)
            1 -> ArchivedEventsContent(archivedEvents, viewModel)
        }
    }
}

@Composable
fun ActiveEventsContent(activeEvents: List<EventEntity>) {
    Column(modifier = Modifier.fillMaxSize()) {
        EventTable(
            events = activeEvents,
            title = stringResource(R.string.Alarm_ActiveEvents),
        )
    }
}

@Composable
fun ArchivedEventsContent(
    events: List<EventEntity>,
    viewModel: EventsViewModel
) {
    Column(modifier = Modifier.fillMaxSize()) {
        EventTable(
            events = events,
            title = stringResource(R.string.Alarm_ArchiveEvents),
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { viewModel.clearArchive() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Сбросить архивные ошибки")
        }
    }
}

@Preview
@Composable
fun EventsScreenPreview (
    viewModel: EventsViewModel = hiltViewModel()
) {
    GIRAFFEnewTheme(dynamicColor = false) {
        EventsScreen(viewModel = viewModel
        )
    }
}