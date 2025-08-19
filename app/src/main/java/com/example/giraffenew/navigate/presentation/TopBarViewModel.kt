package com.example.giraffenew.navigate.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


//Создание ViewModel для управления временем и датой
@HiltViewModel
class TopBarViewModel
@Inject constructor(modbusRepository: ModbusRepository): ViewModel(){
    private val _currentTime = mutableStateOf("00:00:00")
    val currentTime: State<String> = _currentTime

    private val _currentDate = mutableStateOf("00:00:0000")
    val currentDate: State<String> = _currentDate

    private val _currentModelCrane = mutableStateOf("TDK-00.000")
    val currentModelCrane: State<String> = _currentModelCrane

    private val _currentPulleyBlock = mutableStateOf("Запасовка [... кр.]")
    val currentPulleyBlock: State<String> = _currentPulleyBlock

    private val _currentLoadCapacity = mutableStateOf("ГХ [...]")
    val currentLoadCapacity: State<String> = _currentLoadCapacity

    val addresses = listOf(90, 91, 94, 95, 98, 99, 150, 193, 194 ) // Только нужные регистры


    init {
        viewModelScope.launch {
            modbusRepository.registerCache
                .map { cache ->
                    // Фильтруем только нужные регистры
                    addresses.associate { addr ->
                        addr to cache[addr]?.value
                    }
                }
                .collect { filteredCache ->
                    Log.d("TopBarViewModel", "Updating from cache: $filteredCache")

                    // Обновляем данные на основе отфильтрованного кэша
                    updateTime(filteredCache)
                    updateDate(filteredCache)
                    updateModelCrane(filteredCache)
                    updatePulleyBlock(filteredCache)
                    updateLoadCapacity(filteredCache)
                }
        }
    }
    //GV_PulleyBlock
    //LoadCapacity"
    private fun updateTime(cache: Map<Int, Int?>) {
        val hour = cache[95] ?: return
        val minute = cache[98] ?: return
        val second = cache[99] ?: return

        _currentTime.value = String.format("%02d:%02d:%02d", hour, minute, second)
    }

    private fun updateDate(cache: Map<Int, Int?>) {
        val year = cache[90] ?: return
        val month = cache[91] ?: return
        val day = cache[94] ?: return

        _currentDate.value = String.format("%02d.%02d.%04d", day, month, year)
    }

    private fun updateModelCrane(cache: Map<Int, Int?>) {
        val crane = cache[150] ?: return
        val numberStr = crane.toString().padStart(5, '0')
        _currentModelCrane.value = "TDK-${numberStr.take(2)}.${numberStr.takeLast(3)}"
    }

    private fun updatePulleyBlock(cache: Map<Int, Int?>) {
        val pulleyblock = cache[193] ?: return
        _currentPulleyBlock.value = when (pulleyblock) {
            1 -> "Запасовка [4х кр.]"
            else -> "Запасовка [2х кр.]"
        }
    }

    private fun updateLoadCapacity(cache: Map<Int, Int?>) {
        val loadCapacity = cache[194] ?: return
        _currentLoadCapacity.value = "Груз.хар. [%02d]".format(loadCapacity)
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