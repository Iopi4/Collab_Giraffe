package com.example.giraffenew.accident_and_warning_log.simulator

import com.example.giraffenew.accident_and_warning_log.domian.usecase.HandleStatusChangeUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class EventSimulator(
    private val handleStatusChange: HandleStatusChangeUseCase)
{ private val random = Random
    private val registrationNumbers = listOf("REG-001", "REG-002")
    fun startSimulation() {
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                delay(5000)
                val regNum = registrationNumbers.random()
                val isActive = random.nextBoolean()
                val eventTime = System.currentTimeMillis()
                handleStatusChange(regNum, isActive, eventTime) } } } }