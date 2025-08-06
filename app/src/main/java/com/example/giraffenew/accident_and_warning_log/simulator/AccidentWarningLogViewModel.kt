package com.example.giraffenew.accident_and_warning_log.simulator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class AccidentWarningLogViewModel
@Inject constructor(
    private val eventSimulator: EventSimulator ) : ViewModel() {

    init { startSimulation()}

    private fun startSimulation() { viewModelScope.launch { eventSimulator.startSimulation() }}}