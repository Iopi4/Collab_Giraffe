package com.example.giraffenew.initial_setup.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InitialSetupViewModel
@Inject constructor(
    private val modbusRepository: ModbusRepository
): ViewModel(){

    //ParametersDateAndTime UI
    private val _currentTime = mutableStateOf("00:00:00")
    val currentTime: State<String> = _currentTime

    private val _currentDate = mutableStateOf("00:00:0000")
    val currentDate: State<String> = _currentDate

    // Состояния для ввода
    private val _setValueHour = mutableStateOf("")
    val setValueHour: State<String> = _setValueHour

    private val _setValueMinute = mutableStateOf("")
    val setValueMinute: State<String> = _setValueMinute

    private val _setValueDay = mutableStateOf("")
    val setValueDay: State<String> = _setValueDay

    private val _setValueMonth = mutableStateOf("")
    val setValueMonth: State<String> = _setValueMonth

    private val _setValueYear = mutableStateOf("")
    val setValueYear: State<String> = _setValueYear

    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    init {
        viewModelScope.launch(Dispatchers.IO) {
            modbusRepository.registerCache.collect { cache ->
                updateTime(cache)
                updateDate(cache)
            }
        }
    }

    // Обработчик кнопки
    fun onSetDateTime() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Валидация и преобразование значений
                val hour = validateAndConvert(_setValueHour.value, 0, 23)
                val minute = validateAndConvert(_setValueMinute.value, 0, 59)
                val day = validateAndConvert(_setValueDay.value, 1, 31)
                val month = validateAndConvert(_setValueMonth.value, 1, 12)
                val year = validateAndConvert(_setValueYear.value, 2025, 2099)

                // Запись в регистры
                modbusRepository.writeRegister(846, hour)
                modbusRepository.writeRegister(847, minute)
                modbusRepository.writeRegister(845, day)
                modbusRepository.writeRegister(844, month)
                modbusRepository.writeRegister(843, year)

                // Активация записи
                modbusRepository.writeRegister(848, 1)

                // Сброс состояний
                resetFields()
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    private fun validateAndConvert(value: String, min: Int, max: Int): Int {
        val intValue = value.toIntOrNull() ?: throw NumberFormatException("Некорректное число")
        if (intValue !in min..max) throw IllegalArgumentException("Значение вне диапазона")
        return intValue
    }

    private fun resetFields() {
        _setValueHour.value = ""
        _setValueMinute.value = ""
        _setValueDay.value = ""
        _setValueMonth.value = ""
        _setValueYear.value = ""
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

    // Обновления для UI
    fun updateHour(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueHour.value = value
        }
    }

    fun updateMinute(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueMinute.value = value
        }
    }

    fun updateDay(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueDay.value = value
        }
    }

    fun updateMonth(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueMonth.value = value
        }
    }

    fun updateYear(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueYear.value = value
        }
    }
}