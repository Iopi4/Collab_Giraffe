package com.example.giraffenew.accident_and_warning_log

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.giraffenew.R
import com.example.giraffenew.accident_and_warning_log.domian.model.EventEntity
import com.example.giraffenew.accident_and_warning_log.presentation.screen.EventTable
import com.example.giraffenew.accident_and_warning_log.presentation.viewmodel.EventsViewModel
import com.example.giraffenew.coordinate_protection.ui.polygon_1.NavBarPolygon1
import com.example.giraffenew.coordinate_protection.ui.polygon_1.NavRoutesPolygon1
import com.example.giraffenew.coordinate_protection.ui.polygon_1.Polygon1Setting
import com.example.giraffenew.coordinate_protection.ui.polygon_1.SettingPointsPolygon1Coordinate
import com.example.giraffenew.coordinate_protection.ui.polygon_1.SettingPointsPolygon1Hook
import com.example.giraffenew.coordinate_protection.ui.polygon_1.SettingPointsPolygon1Manual
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonTabGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsText
import com.example.giraffenew.navigate.presentation.TopBarViewModel
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun AccidentWarningLogScreen (
    eventsViewModel: EventsViewModel = hiltViewModel(),
){
    val activeEvents by eventsViewModel.activeEvents.collectAsState() //collectAsState() — преобразует поток данных в состояние, которое может использоваться в UI
    val archivedEvents by eventsViewModel.archivedEvents.collectAsState()
    val context = LocalContext.current //— получение текущего контекста приложения
    val scrollState = rememberScrollState() //— создание состояния прокрутки для списка

//Структура экрана. Для начала создадим отдельные компоненты из которых соберем весь экран
    val navController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    { NavBarEvents (navController = navController)
        NavHost(navController, startDestination = NavRoutesEvents.ActiveEvents.route){
            composable(NavRoutesEvents.ActiveEvents.route)
            { ActiveEventsContent(activeEvents) }
            composable(NavRoutesEvents.ArchivedEvents.route)
            { ArchivedEventsContent(archivedEvents, eventsViewModel) }
        }
    }
}

@Composable
fun NavBarEvents(
    navController: NavController, ) {

    val myBoxNavBarModifier = Modifier
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()


    Column(myBoxNavBarModifier,
        verticalArrangement = Arrangement.SpaceEvenly)
    {
// Заголовок
        //Код для формирования области в верху с кнопками переходов между страницами
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.Alarm_ActiveEvents,
                    onClick = {
                        navController.navigate(NavRoutesEvents.ActiveEvents.route)
                    },
                ) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.Alarm_ArchiveEvents,
                    onClick = {
                        navController.navigate(NavRoutesEvents.ArchivedEvents.route)
                    },
                ) {}
            }

        }
    }
}
sealed class NavRoutesEvents (val route: String) {
    object ActiveEvents: NavRoutesEvents("Активные Аварии и Предупреждения")
    object ArchivedEvents: NavRoutesEvents("Архивные Аварии и Предупреждения")
}

@Composable
fun ActiveEventsContent(activeEvents: List<EventEntity>) {
    Column(modifier = Modifier
        .fillMaxSize()) {
        EventTable(
            events = activeEvents,
            title = stringResource(R.string.Alarm_ActiveEvents),
            modifier = Modifier.weight(1f) // Занимаем все доступное пространство
        )
    }
}

@Composable
fun ArchivedEventsContent(
    events: List<EventEntity>,
    viewModel: EventsViewModel
) {
    Column(modifier = Modifier
        .fillMaxSize()) {
        Button(
            onClick = { viewModel.clearArchive() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) { Text("Сбросить архивные ошибки") }
        EventTable(
            events = events,
            title = stringResource(R.string.Alarm_ArchiveEvents),
            modifier = Modifier.weight(1f) // Добавляем вес для занимания оставшегося пространства
        )
    }
}

@Preview
@Composable
fun AccidentWarningLogScreenPreview (
    viewModel: EventsViewModel = hiltViewModel()
) {
    GIRAFFEnewTheme(dynamicColor = false) {
        AccidentWarningLogScreen(eventsViewModel = viewModel
        )
    }
}