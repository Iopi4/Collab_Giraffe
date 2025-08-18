package com.example.giraffenew.navigate.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

    init {
        viewModelScope.launch {
            modbusRepository.registerCache.collect { cache ->
                updateTime(cache)
                updateDate(cache)
                updateModelCrane(cache)
                updatePulleyBlock(cache)
                updateLoadCapacity(cache)
            }
        }
    }
    //GV_PulleyBlock
    //LoadCapacity"
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

    private fun updateModelCrane(cache: Map<Int, ModbusRepository.RegisterValue>) {
        val crane = cache[150]?.value ?: return //150
        val numberStr = crane.toString().padStart(5, '0') // Гарантируем 5 цифр
        val formatted = "TDK-${numberStr.take(2)}.${numberStr.takeLast(3)}"
        //_currentModelCrane.value = String.format("%05d", crane)
        _currentModelCrane.value = formatted
    }

    private fun updatePulleyBlock(cache: Map<Int, ModbusRepository.RegisterValue>){
        val pulleyblock = cache[193]?.value ?: return //193
        val ghFourPolispaste = "Запасовка [4х кр.]"
        val ghTwoPolispaste = "Запасовка [2х кр.]"

        val ghPolispaste: String = when (pulleyblock) {
            0 -> ghTwoPolispaste
            1 -> ghFourPolispaste
            else -> ghTwoPolispaste
        }
        _currentPulleyBlock.value = ghPolispaste
    }

    private fun updateLoadCapacity(cache: Map<Int, ModbusRepository.RegisterValue>){
        val loadCapacity = cache[194]?.value ?: return //194
        _currentLoadCapacity.value = String.format("Груз.хар. [%02d]", loadCapacity)
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