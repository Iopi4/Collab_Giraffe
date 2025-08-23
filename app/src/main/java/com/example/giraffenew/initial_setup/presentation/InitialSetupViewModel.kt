package com.example.giraffenew.initial_setup.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.entities.UiInitialSetupRegisterConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
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



    private val _setModelCrane = mutableStateOf("")
    val setValueModelCrane: State<String> = _setModelCrane

    private val _setNumberCrane = mutableStateOf("")
    val setValueNumberCrane: State<String> = _setNumberCrane

    private val _setNumberSBKB = mutableStateOf("")
    val setValueNumberSBKB: State<String> = _setNumberSBKB


    private val _setValueDayCrane = mutableStateOf("")
    val setValueDayCrane: State<String> = _setValueDayCrane

    private val _setValueMonthCrane = mutableStateOf("")
    val setValueMonthCrane: State<String> = _setValueMonthCrane

    private val _setValueYearCrane = mutableStateOf("")
    val setValueYearCrane: State<String> = _setValueYearCrane


    private val _setValueDayRTN = mutableStateOf("")
    val setValueDayRTN: State<String> = _setValueDayRTN

    private val _setValueMonthRTN = mutableStateOf("")
    val setValueMonthRTN: State<String> = _setValueMonthRTN

    private val _setValueYearRTN = mutableStateOf("")
    val setValueYearRTN: State<String> = _setValueYearRTN

    private val _setValueDaySBKB = mutableStateOf("")
    val setValueDaySBKB: State<String> = _setValueDaySBKB

    private val _setValueMonthSBKB = mutableStateOf("")
    val setValueMonthSBKB: State<String> = _setValueMonthSBKB

    private val _setValueYearSBKB = mutableStateOf("")
    val setValueYearSBKB: State<String> = _setValueYearSBKB


    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessage: State<String?> = _errorMessage

    private val _errorMessageCrane = mutableStateOf<String?>(null)
    val errorMessageCrane: State<String?> = _errorMessageCrane

    private val _errorMessageRTN = mutableStateOf<String?>(null)
    val errorMessageRTN: State<String?> = _errorMessageRTN

    private val _errorMessageSBKB = mutableStateOf<String?>(null)
    val errorMessageSBKB: State<String?> = _errorMessageSBKB

    private val _logMessageDateTime = mutableStateOf<String?>(null)
    val logMessageDateTime: State<String?> = _logMessageDateTime

    private val _logMessageCrane = mutableStateOf<String?>(null)
    val logMessageCrane: State<String?> = _logMessageCrane

    private val _logMessageRTN = mutableStateOf<String?>(null)
    val logMessageRTN: State<String?> = _logMessageRTN

    private val _logMessageSBKB = mutableStateOf<String?>(null)
    val logMessageSBKB: State<String?> = _logMessageSBKB

    private var previousValues: Map<Int, Int?> = emptyMap()

    private var dataCollectionJob: Job? = null

    private val addresses = listOf(90, 91, 94, 95, 98, 99) // Только нужные регистры

    init {
        startDataCollection()

    }

    fun startDataCollection() {
        if (dataCollectionJob?.isActive == true) return

        Log.d("InitialSetupVM", "Starting data collection")
        dataCollectionJob = viewModelScope.launch(Dispatchers.Default) {
            modbusRepository.getConfigurationFlow(UiInitialSetupRegisterConfiguration.NAME_INITIAL_SETUP)
                .collect { cache ->
                Log.d("InitialSetupViewModel", "Updating from cache, size: ${cache.size}")

                    // Фильтруем только нужные регистры
                    val currentValues = addresses.associate { addr ->
                        addr to cache[addr]?.value
                    }
                    // Проверяем, изменились ли значения целевых регистров
                    if (haveRelevantRegistersChanged(currentValues)) {
                        Log.d("InitialSetupViewModel", "Relevant registers changed: $currentValues")

                        // Обновляем данные на основе отфильтрованного кэша
                        //updateTime(currentValues)
                        //updateDate(currentValues)

                        // Сохраняем текущие значения для следующего сравнения
                        previousValues = currentValues
                    }

                    // Сохраняем текущие значения для следующего сравнения
                    previousValues = currentValues
            }
        }
    }


    // Функция для проверки изменений в relevant регистрах
    private fun haveRelevantRegistersChanged(currentValues: Map<Int, Int?>): Boolean {
        return addresses.any { address ->
            currentValues[address] != previousValues[address]
        }
    }

    private fun validateAndConvert(value: String, min: Int, max: Int): Int {
        val intValue = value.toIntOrNull() ?: throw NumberFormatException("Некорректное число")
        if (intValue !in min..max) throw IllegalArgumentException("Значение вне диапазона")
        return intValue
    }

    private fun validateAndConvertFloat(value: String, min: Float, max: Float): Int {
        val floatValue = value.toFloatOrNull() ?: throw NumberFormatException("Некорректное число")
        if (floatValue < min || floatValue > max) throw IllegalArgumentException("Значение вне диапазона")
        return (floatValue * 1000).toInt()
    }

    // Приостановка сбора данных
    fun pauseDataCollection() {
        Log.d("InitialSetupVM", "Pausing data collection")
        dataCollectionJob?.cancel()
        dataCollectionJob = null
    }

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

    private fun resetFields() {
        _setValueHour.value = ""
        _setValueMinute.value = ""
        _setValueDay.value = ""
        _setValueMonth.value = ""
        _setValueYear.value = ""
    }

    // Запись даты и времени в ПЛК
    fun onSetDateTime() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Валидация и преобразование значений
                val hour = validateAndConvert(_setValueHour.value, 0, 23)
                val minute = validateAndConvert(_setValueMinute.value, 0, 59)
                val day = validateAndConvert(_setValueDay.value, 1, 31)
                val month = validateAndConvert(_setValueMonth.value, 1, 12)
                val year = validateAndConvert(_setValueYear.value, 2006, 2099)
                // Запись в регистры
                Log.d("InitialSetupViewModel", "Writing registers...")
                modbusRepository.writeRegister(846, hour)
                modbusRepository.writeRegister(847, minute)
                modbusRepository.writeRegister(845, day)
                modbusRepository.writeRegister(844, month)
                modbusRepository.writeRegister(843, year)

                // Активация записи
                modbusRepository.writeRegister(842, 1)
                delay(1000)
                modbusRepository.writeRegister(842, 0)
                _errorMessage.value = null
                _logMessageDateTime.value = "Сохранено"
                // Логирование
                Log.i("InitialSetupVM", "Registers written successfully")
            } catch (e: Exception) {
                Log.e("InitialSetupVM", "Запись не удалась: ${e.message}", e)
                _errorMessage.value = "Запись не удалась: ${e.message}"
                _logMessageDateTime.value = null
            }
        }
    }
    // Запись даты ввода крана в эксплуатацию
    fun onSetDateCrane() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Валидация и преобразование значений
                val day = validateAndConvert(_setValueDayCrane.value, 1, 31)
                val month = validateAndConvert(_setValueMonthCrane.value, 1, 12)
                val year = validateAndConvert(_setValueYearCrane.value, 2006, 2099)

                // Запись в регистры даты ввода крана в эксплуатацию
                modbusRepository.writeRegister(854, day)
                modbusRepository.writeRegister(855, month)
                modbusRepository.writeRegister(856, year)

                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageCrane.value = "Запись не удалась: ${e.message}"
                _logMessageCrane.value = null
            }
        }
    }
    // Запись даты регистрации крана в Ростехнадзоре
    fun onSetDateRTN() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Валидация и преобразование значений
                val day = validateAndConvert(_setValueDayRTN.value, 1, 31)
                val month = validateAndConvert(_setValueMonthRTN.value, 1, 12)
                val year = validateAndConvert(_setValueYearRTN.value, 2006, 2099)

                // Запись в регистры даты регистрации крана в Ростехнадзоре
                modbusRepository.writeRegister(851, day)
                modbusRepository.writeRegister(852, month)
                modbusRepository.writeRegister(853, year)

                _errorMessageRTN.value = null
                _logMessageRTN.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageRTN.value = "Запись не удалась: ${e.message}"
                _logMessageRTN.value = null
            }
        }
    }

    // Запись даты установки СБКБ на кран
    fun onSetDateSBKB() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Валидация и преобразование значений
                val day = validateAndConvert(_setValueDaySBKB.value, 1, 31)
                val month = validateAndConvert(_setValueMonthSBKB.value, 1, 12)
                val year = validateAndConvert(_setValueYearSBKB.value, 2006, 2099)

                // Запись в регистры даты установки СБКБ на кран
                modbusRepository.writeRegister(858, day)
                modbusRepository.writeRegister(859, month)
                modbusRepository.writeRegister(860, year)

                _errorMessageSBKB.value = null
                _logMessageSBKB.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageSBKB.value = "Запись не удалась: ${e.message}"
                _logMessageSBKB.value = null
            }
        }
    }

    // Запись модели крана
    fun onSetModelCrane() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setModelCrane.value, 0.00f,65.535f )
                // Запись в регистры модель крана
                modbusRepository.writeRegister(848, model)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageCrane.value = "Запись не удалась: ${e.message}"
                _logMessageCrane.value = null
            }
        }
    }

    // Запись заводского номера крана
    fun onSetNumberCrane() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val number = validateAndConvert(_setNumberCrane.value, 0, 65535)
                // Запись в регистры модель крана
                modbusRepository.writeRegister(850, number)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageCrane.value = "Запись не удалась: ${e.message}"
                _logMessageCrane.value = null
            }
        }
    }

    // Запись заводского номера СБКБ
    fun onSetNumberSBKB() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val number = validateAndConvert(_setNumberSBKB.value, 0, 65535)
                // Запись в регистры модель крана
                modbusRepository.writeRegister(857, number)
                // Сброс состояний

                _errorMessageSBKB.value = null
                _logMessageSBKB.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageSBKB.value = "Запись не удалась: ${e.message}"
                _logMessageSBKB.value = null
            }
        }
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

    fun updateDayCrane(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueDayCrane.value = value
        }
    }

    fun updateMonthCrane(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueMonthCrane.value = value
        }
    }

    fun updateYearCrane(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueYearCrane.value = value
        }
    }

    fun updateDayRTN(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueDayRTN.value = value
        }
    }

    fun updateMonthRTN(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueMonthRTN.value = value
        }
    }

    fun updateYearRTN(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueYearRTN.value = value
        }
    }

    fun updateDaySBKB(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueDaySBKB.value = value
        }
    }

    fun updateMonthSBKB(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueMonthSBKB.value = value
        }
    }

    fun updateYearSBKB(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueYearSBKB.value = value
        }
    }

    fun updateModelCrane(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,3})?$"))) {
            _setModelCrane.value = value
        }
    }

    fun updateNumberCrane(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setNumberCrane.value = value
        }
    }

    fun updateValueNumberSBKB(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setNumberSBKB.value = value
        }
    }

    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("InitialSetupVM", "ViewModel cleared")
        dataCollectionJob?.cancel()
    }
}