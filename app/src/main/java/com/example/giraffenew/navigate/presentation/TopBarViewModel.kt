package com.example.giraffenew.navigate.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


//Создание ViewModel для управления временем и датой
@HiltViewModel
class TopBarViewModel
@Inject constructor(modbusRepository: ModbusRepository): ViewModel(){
    private val _currentTime = mutableStateOf("--:--:--")
    val currentTime: State<String> = _currentTime

    private val _currentDate = mutableStateOf("--:--:----")
    val currentDate: State<String> = _currentDate

    init {
        viewModelScope.launch {
            modbusRepository.registerCache.collect { cache ->
                updateTime(cache)
                updateDate(cache)
            }
        }
    }

    private fun updateTime(cache: Map<Int, ModbusRepository.RegisterValue>){
        val hour = cache[95]?.value ?: return
        val minute = cache[98]?.value ?: return
        val second = cache[99]?.value ?:return

        _currentTime.value = String.format("%02d:%02d:%02d", hour, minute, second)
    }

    private fun updateDate(cache: Map<Int, ModbusRepository.RegisterValue>) {
        val year = cache[90]?.value ?: return
        val month = cache[91]?.value ?: return
        val day = cache[94]?.value ?: return

        _currentDate.value = String.format("%02d.%02d.%04d", day, month, year)
    }
}

/**
Объяснение решения:
DateTimeViewModel:
Подписывается на registerCache из ModbusRepository
Преобразует значения регистров в форматированные строки времени и даты
Использует State для реактивного обновления UI
Обрабатывает отсутствие значений (показывает "--:--:--" и "--.--.----")
 */