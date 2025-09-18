package com.example.giraffenew.accident_and_warning_log


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.giraffenew.R
import com.example.giraffenew.accident_and_warning_log.domian.model.EventEntity
import com.example.giraffenew.accident_and_warning_log.presentation.screen.ClearArchiveDialog
import com.example.giraffenew.accident_and_warning_log.presentation.screen.EventTable
import com.example.giraffenew.accident_and_warning_log.presentation.viewmodel.EventsViewModel
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackgroundEvents
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffeAccess
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonTabGiraffe
import com.example.giraffenew.navigate.presentation.TopBarViewModel
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun AccidentWarningLogScreen (
    eventsViewModel: EventsViewModel = hiltViewModel(),
){
    val activeEvents by eventsViewModel.activeEvents.collectAsState() //collectAsState() — преобразует поток данных в состояние, которое может использоваться в UI
    val archivedEvents by eventsViewModel.archivedEvents.collectAsState()
    val context = LocalContext.current //— получение текущего контекста приложения
    val scrollState = rememberScrollState() //— создание состояния прокрутки для списка

    val topBarViewModel: TopBarViewModel = hiltViewModel()

//Структура экрана. Для начала создадим отдельные компоненты из которых соберем весь экран
    val navController = rememberNavController()

    BasicSettingBackgroundEvents()
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    { NavBarEvents (navController = navController)
        NavHost(navController, startDestination = NavRoutesEvents.ActiveEvents.route){
            composable(NavRoutesEvents.ActiveEvents.route)
            { ActiveEventsContent(activeEvents) }
            composable(NavRoutesEvents.ArchivedEvents.route)
            { ArchivedEventsContent(
                archivedEvents,
                eventsViewModel,
                serviceMode = topBarViewModel.serviceMode.value,
            ) }
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
    viewModel: EventsViewModel,
    serviceMode: Int,
) {
    val topBarViewModel: TopBarViewModel = hiltViewModel()
    val protectedMenusEnabledService = serviceMode == 1
    var showClearArchiveDialog by remember { mutableStateOf(false) } // Состояние для диалога

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            BasicButtonGiraffeAccess(
                modifier = Modifier.padding(start = 40.dp, end = 40.dp),
                text = R.string.Button_Resetting_Archived_Events,
                isLocked = !protectedMenusEnabledService,
                onClick = {
                    if (protectedMenusEnabledService) {
                        showClearArchiveDialog = true // Показываем всплывающее окно Dialog
                    } else topBarViewModel.playAccessDeniedSound()
                },
            )


            EventTable(
                events = events,
                title = stringResource(R.string.Alarm_ArchiveEvents),
                modifier = Modifier.weight(1f) // Добавляем вес для занимания оставшегося пространства
            )
        }
        // Диалоговое окно
        if (showClearArchiveDialog) {
            ClearArchiveDialog (
                onDismiss = { showClearArchiveDialog = false },
                onConfirm = {
                    viewModel.clearArchive()
                    showClearArchiveDialog = false
                }
            )
        }
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