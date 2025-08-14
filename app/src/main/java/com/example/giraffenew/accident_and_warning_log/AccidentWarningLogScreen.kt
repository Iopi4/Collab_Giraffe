package com.example.giraffenew.accident_and_warning_log

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.giraffenew.accident_and_warning_log.presentation.screen.EventsScreen
import com.example.giraffenew.accident_and_warning_log.presentation.viewmodel.EventsViewModel

@Composable
fun AccidentWarningLogScreen (
    viewModel: EventsViewModel = hiltViewModel(),
){
    EventsScreen(viewModel = viewModel)
}
