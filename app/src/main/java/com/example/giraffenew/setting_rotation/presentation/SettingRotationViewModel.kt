package com.example.giraffenew.setting_rotation.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.entities.UiSettingRotationConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingRotationViewModel
@Inject constructor(
    private val modbusRepository: ModbusRepository
): ViewModel() {

    //Допустимая скорость ручного наложения тормоза об/мин
    private val _setValueSlewSpdStopButton = mutableStateOf("")
    val setValueSlewSpdStopButton: State<String> = _setValueSlewSpdStopButton
    //Время торможения в режиме противохода
    private val _setValueSlewDECRevers = mutableStateOf("")
    val setValueSlewDECRevers: State<String> = _setValueSlewDECRevers

    //Задержка наложения тормоза поворота
    private val _setValueSlewDelayTime = mutableStateOf("")
    val setValueSlewDelayTime: State<String> = _setValueSlewDelayTime

    //Минимальная частота задания после торможения привода.
    private val _setValueSlewBrakeCloseFrq = mutableStateOf("")
    val setValueSlewBrakeCloseFrq: State<String> = _setValueSlewBrakeCloseFrq

    //Частота начала торможения
    private val _setValueSlewFrqStartBraking = mutableStateOf("")
    val setValueSlewFrqStartBraking: State<String> = _setValueSlewFrqStartBraking


    //Задержка времени при достижении частоты начала торможения
    private val _setValueSlewDelayStartBraking = mutableStateOf("")
    val setValueSlewDelayStartBraking: State<String> = _setValueSlewDelayStartBraking

    //Задержка до начала торможения постоянным током
    private val _setValueSlewDelayTimeBrakeCurr = mutableStateOf("")
    val setValueSlewDelayTimeBrakeCurr: State<String> = _setValueSlewDelayTimeBrakeCurr

    //Время торможения постонным током
    private val _setValueSlewTimeBrakeCurrent = mutableStateOf("")
    val setValueSlewTimeBrakeCurrent: State<String> = _setValueSlewTimeBrakeCurrent


    private val _errorMessageSlew = mutableStateOf<String?>(null)
    val errorMessageSlew: State<String?> = _errorMessageSlew

    private val _logMessageSlew = mutableStateOf<String?>(null)
    val logMessageSlew: State<String?> = _logMessageSlew


    private val addresses = listOf(
        31266, 31267, 31268, 31269, 31270, 31271, 31272, 31273 // Регистры для считывания данных
    )

    private val _state = mutableStateOf(SettingRotationState())

    val state: State<SettingRotationState> = _state

    private var dataCollectionJob: Job? = null

    fun setActiveConfiguration() {
        modbusRepository.setActiveUiConfiguration(UiSettingRotationConfiguration.NAME_SETTING_ROTATION)
        startDataCollection()
    }

    fun clearActiveConfiguration() {
        pauseDataCollection()
        modbusRepository.setActiveUiConfiguration(null)
    }

    fun startDataCollection() {
        //if (dataCollectionJob?.isActive == true) return
        dataCollectionJob?.cancel() // Всегда отменяем предыдущую job

        Log.d("SettingRotationVM", "Starting data collection for ${UiSettingRotationConfiguration.NAME_SETTING_ROTATION}")
        dataCollectionJob = viewModelScope.launch(Dispatchers.Default) {
            modbusRepository.getConfigurationFlow(UiSettingRotationConfiguration.NAME_SETTING_ROTATION)
                .collect { cache ->
                    Log.d("SettingRotationViewModel", "Updating from cache, size: ${cache.size}")
                    try {
                        // Создаем отфильтрованную карту только для нужных адресов
                        val filteredCache = addresses.associate { addr ->
                            addr to cache[addr]?.value
                        }
                        _state.value = createServiceMenuState(filteredCache)
                    } catch (e: Exception) {
                        Log.e("SettingRotationVM", "Data collection error", e)
                    }
                }
        }
    }

    // Приостановка сбора данных
    fun pauseDataCollection() {
        Log.d("SettingRotationVM", "Pausing data collection")
        dataCollectionJob?.cancel()
        dataCollectionJob = null
    }






    private fun safeDivideTens(value: Int?): Float {
        return (value ?: 0) / 10f
    }
    private fun safeDivideHundred(value: Int?): Float {
        return (value ?: 0) / 100f
    }
    private fun safeDivideThousand(value: Int?): Float {
        return (value ?: 0) / 1000f
    }

    //Функция проверки и преобразования целых чисел
    private fun validateAndConvert(value: String, min: Int, max: Int): Int {
        val intValue = value.toIntOrNull() ?: throw NumberFormatException("Некорректное число")
        if (intValue !in min..max) throw IllegalArgumentException("Значение вне диапазона")
        return intValue
    }
    //Функции проверки и преобразования десятичных чисел
    private fun validateAndConvertFloat(value: String, min: Float, max: Float): Int {
        val floatValue = value.toFloatOrNull() ?: throw NumberFormatException("Некорректное число")
        if (floatValue < min || floatValue > max) throw IllegalArgumentException("Значение вне диапазона")
        return (floatValue * 10).toInt()
    }
    private fun validateAndConvertFloatHundred(value: String, min: Float, max: Float): Int {
        val floatValue = value.toFloatOrNull() ?: throw NumberFormatException("Некорректное число")
        if (floatValue < min || floatValue > max) throw IllegalArgumentException("Значение вне диапазона")
        return (floatValue * 100).toInt()
    }


    private fun createServiceMenuState(cache: Map<Int, Int?>): SettingRotationState {
        return SettingRotationState(
            hmiSlewSpeedBrakeClose                 = cache[31269] ?: 0,
            hmiSlewDecRevers        = safeDivideTens(cache[31266] ?: 0),
            hmiSlewDelayTimeBrake   = safeDivideTens(cache[31267] ?: 0),
            hmiSlewBrakeCloseFrq = safeDivideHundred(cache[31268] ?: 0),
            hmiSlewDelayTimeBrakeCurr = safeDivideTens(cache[31270] ?: 0),
            hmiSlewTimeBrakeCurrent = safeDivideTens(cache[31271] ?: 0),
            hmiSlewFrqStartBraking  = safeDivideTens(cache[31272] ?: 0),
            hmiSlewDelayStartBraking= safeDivideTens(cache[31273] ?: 0),
        )
    }

    data class SettingRotationState (
        val hmiSlewSpeedBrakeClose: Int = 0, //Допустимая скорость ручного наложения тормоза об/мин

        val hmiSlewDecRevers: Float = 0f, //Время торможения противовключением
        val hmiSlewDelayTimeBrake: Float = 0f, //Время задержки наложения тормоза
        val hmiSlewBrakeCloseFrq: Float = 0f, //Частота удержания привода поворота

        val hmiSlewDelayTimeBrakeCurr: Float = 0f, //Задержка до начала торможения постоянным током
        val hmiSlewTimeBrakeCurrent: Float = 0f, //Время торможения постонным током
        val hmiSlewFrqStartBraking: Float = 0f, //Частота начала торможения
        val hmiSlewDelayStartBraking: Float = 0f, //Задержка времени при достижении частоты начала торможения

    )

    // Slew. Запись скорости для остановки при нажатии кнопки
    fun onSetSlewSpdStopButton() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueSlewSpdStopButton.value, 0,100 )
                // Запись в регистры
                modbusRepository.writeRegister(61269, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "${e.message}"
            }
        }
    }
    // Slew. Запись времени торможения противоходом поворота [сек]
    fun onSetSlewDECRevers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueSlewDECRevers.value, 0.0f,30.0f )
                // Запись в регистры
                modbusRepository.writeRegister(61266, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "${e.message}"
            }
        }
    }
    // Slew. Запись времени задержки наложения торможения поворота [сек]
    fun onSetSlewDelayTime() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueSlewDelayTime.value, 0.0f,30.0f )
                // Запись в регистры
                modbusRepository.writeRegister(61267, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "${e.message}"
            }
        }
    }
    // Slew. Запись частоты удержания привода стрелы [Гц]
    fun onSetSlewBrakeCloseFrq() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloatHundred(_setValueSlewBrakeCloseFrq.value, 0.00f,0.50f )
                // Запись в регистры
                modbusRepository.writeRegister(61268, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "${e.message}"
            }
        }
    }

    // Slew. Запись Частота начала торможения [Hz]
    fun onSetSlewFrqStartBraking() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueSlewFrqStartBraking.value, 0.0f,5.0f )
                // Запись в регистры
                modbusRepository.writeRegister(61272, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "${e.message}"
            }
        }
    }

    // Slew. Запись задержка времени при достижении частоты начала торможения [сек]
    fun onSetSlewDelayStartBraking() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueSlewDelayStartBraking.value, 0.0f,10.0f )
                // Запись в регистры
                modbusRepository.writeRegister(61273, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "${e.message}"
            }
        }
    }

    // Slew. Запись задержка до начала торможения постоянным током [сек]
    fun onSetSlewDelayTimeBrakeCurr() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueSlewDelayTimeBrakeCurr.value, 0.0f,10.0f )
                // Запись в регистры
                modbusRepository.writeRegister(61270, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "${e.message}"
            }
        }
    }

    // Slew. Запись Время торможения постонным током [сек]
    fun onSetSlewTimeBrakeCurrent() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueSlewTimeBrakeCurrent.value, 0.0f,20.0f )
                // Запись в регистры
                modbusRepository.writeRegister(61271, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "${e.message}"
            }
        }
    }
//Обновления значений для UI
    fun updateSlewSpdStopButton(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueSlewSpdStopButton.value = value }}
    fun updateSlewDECRevers(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueSlewDECRevers.value = value } }
    fun updateSlewDelayTime(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueSlewDelayTime.value = value } }
    fun updateSlewBrakeCloseFrq(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,1}(\\.\\d{0,3})?$"))) {
            _setValueSlewBrakeCloseFrq.value = value } }


    fun updateSlewFrqStartBraking(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,1}(\\.\\d{0,1})?$"))) {
            _setValueSlewFrqStartBraking.value = value } }

    fun updateSlewDelayStartBraking(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueSlewDelayStartBraking.value = value } }

    fun updateSlewDelayTimeBrakeCurr(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueSlewDelayTimeBrakeCurr.value = value } }

    fun updateSlewTimeBrakeCurrent(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueSlewTimeBrakeCurrent.value = value } }


    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("SettingRotationVM", "ViewModel cleared")
        // Деактивируем конфигурацию при уничтожении ViewModel
        modbusRepository.setActiveUiConfiguration(null)
    }

}

