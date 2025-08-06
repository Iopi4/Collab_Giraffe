package com.example.giraffenew.accident_and_warning_log

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.giraffenew.accident_and_warning_log.data.local.EventDao
import com.example.giraffenew.accident_and_warning_log.data.remoute.JsonDataSource
import com.example.giraffenew.accident_and_warning_log.data.remoute.RegisterStatusDataSource
import com.example.giraffenew.accident_and_warning_log.data.repository.EventRepository
import com.example.giraffenew.accident_and_warning_log.domian.model.EventDefinition
import com.example.giraffenew.accident_and_warning_log.domian.model.EventEntity
import com.example.giraffenew.accident_and_warning_log.domian.model.EventType
import com.example.giraffenew.accident_and_warning_log.domian.usecase.ClearArchiveUseCase
import com.example.giraffenew.accident_and_warning_log.domian.usecase.GetActiveEventsUseCase
import com.example.giraffenew.accident_and_warning_log.domian.usecase.GetArchiveEventsUseCase
import com.example.giraffenew.accident_and_warning_log.domian.usecase.HandleStatusChangeUseCase
import com.example.giraffenew.accident_and_warning_log.presentation.screen.EventsScreen
import com.example.giraffenew.accident_and_warning_log.presentation.viewmodel.EventsViewModel
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

@Composable
fun AccidentWarningLogScreen (
    viewModel: EventsViewModel = hiltViewModel(),
){
    EventsScreen(viewModel = viewModel)
}
