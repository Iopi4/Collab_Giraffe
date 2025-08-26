package com.example.giraffenew.setting_sensors.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.entities.UISettingSensorsConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingSensorsViewModule
@Inject constructor(
    private val modbusRepository: ModbusRepository
): ViewModel(

){
    private val _setValueHoistPosFwdStop = mutableStateOf("")
    val setValueHoistPosFwdStop: State<String> = _setValueHoistPosFwdStop

    private val _setValueHoistPosRevStop = mutableStateOf("")
    val setValueHoistPosRevStop: State<String> = _setValueHoistPosRevStop

    private val _setValueHoistPosFwdDwnSp = mutableStateOf("")
    val setValueHoistPosFwdDwnSp: State<String> = _setValueHoistPosFwdDwnSp

    private val _setValueHoistPosRevDwnSp = mutableStateOf("")
    val setValueHoistPosRevDwnSp: State<String> = _setValueHoistPosRevDwnSp

    private val _errorMessageHoist = mutableStateOf<String?>(null)
    val errorMessageHoist: State<String?> = _errorMessageHoist

    private val _logMessageHoist = mutableStateOf<String?>(null)
    val logMessageHoist: State<String?> = _logMessageHoist


//Подписываемся на необходимые нам регистры для отображения на панели оператора
    private val addresses = listOf(
        155, 158, 159, 162, 60, 62, 64, 66, 803, 804,
        805, 806, 807, 808, 809, 810, 811, 812, 813,
        341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353 // Регистры для считывания
    )

    private val _state = mutableStateOf(SettingSensorsState())
    val state: State<SettingSensorsState> = _state

    private var dataCollectionJob: Job? = null

//    init {
//        // Активируем UI конфигурацию для этого экрана
//        modbusRepository.setActiveUiConfiguration(UISettingLoadCapacityConfiguration.NAME_SETTING_LOAD_CAPACITY)
//        startDataCollection()
//    }

    fun setActiveConfiguration() {
        modbusRepository.setActiveUiConfiguration(UISettingSensorsConfiguration.NAME_SETTING_SENSORS)
        startDataCollection()
        writeUiActiveStatus(21) // Активируем регистр
    }

    fun clearActiveConfiguration() {
        pauseDataCollection()
        modbusRepository.setActiveUiConfiguration(null)
        writeUiActiveStatus(0) // Деактивируем регистр
    }

    private fun writeUiActiveStatus(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            modbusRepository.writeRegister(283, value).fold(
                onSuccess = {
                    Log.d("SettingSensorsVM", "Register 283 set to $value")
                },
                onFailure = { e ->
                    Log.e("SettingSensorsVM", "Failed to write register 868", e)
                }
            )
        }
    }

    fun startDataCollection() {
        //if (dataCollectionJob?.isActive == true) return
        dataCollectionJob?.cancel() // Всегда отменяем предыдущую job

        Log.d("SSettingSensorsVM", "Starting data collection for ${UISettingSensorsConfiguration.NAME_SETTING_SENSORS}")
        dataCollectionJob = viewModelScope.launch(Dispatchers.Default) {
            modbusRepository.getConfigurationFlow(UISettingSensorsConfiguration.NAME_SETTING_SENSORS)
                .collect { cache ->
                    Log.d("SettingSensorsViewModel", "Updating from cache, size: ${cache.size}")
                    try {
                        // Создаем отфильтрованную карту только для нужных адресов
                        val filteredCache = addresses.associate { addr ->
                            addr to cache[addr]?.value
                        }
                        _state.value = createSettingSensorsState(filteredCache)
                    } catch (e: Exception) {
                        Log.e("SettingSensorsVM", "Data collection error", e)
                    }
                }
        }
    }

    // Приостановка сбора данных
    fun pauseDataCollection() {
        Log.d("SettingSensorsViewModel", "Pausing data collection")
        dataCollectionJob?.cancel()
        dataCollectionJob = null
    }
    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДАТЧИКИ ОТОБРАЖЕНИЕ ДАННЫХ И СОСТОЯНИЯ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // //Функция createSettingLoadCapacityState() для обработки отфильтрованных данных:
    private fun createSettingSensorsState(cache: Map<Int, Int?>): SettingSensorsState {
        return SettingSensorsState(
            //Параметры крана
            currentHoistPosition = safeDivideTens(cache[159] ?: 0),
            currentSlewPosition = safeDivideTens(cache[158] ?: 0),
            currentTrolleyPosition = safeDivideTens(cache[155] ?: 0),
            currentCranePosition = safeDivideTens(cache[162] ?: 0),
            //Параметры датчиков
            currentAbsEncHoistPosition = cache[60] ?: 0,
            currentAbsEncSlewPosition = cache[62] ?: 0,
            currentAbsEncTrolleyPosition = cache[64] ?: 0,
            currentAbsEncCranePosition = cache[66] ?: 0,
            //Статус теста ограничений подъема
            currentHoistStopDwn1 = cache[341] ?: 0,
            currentHoistStopUp1 = cache[342] ?: 0,
            currentHoistDwnDwnSp1 = cache[342] ?: 0,
            currentHoistUpDwnSp1 = cache[344] ?: 0,
        )
    }


    private fun safeDivide(value: Int?): Float {
        return (value ?: 0) / 1f
    }

    private fun safeDivideTens(value: Int?): Float {
        return (value ?: 0) / 10f
    }

    data class SettingSensorsState(
        //ПАРАМЕТРЫ КРАНА
        val currentHoistPosition: Float = 0f,
        val currentSlewPosition: Float = 0f,
        val currentTrolleyPosition: Float = 0f,
        val currentCranePosition: Float = 0f,
        //ЗНАЧЕНИЕ ДАТЧИКОВ
        val currentAbsEncHoistPosition: Int = 0,
        val currentAbsEncSlewPosition: Int = 0,
        val currentAbsEncTrolleyPosition: Int = 0,
        val currentAbsEncCranePosition: Int = 0,
        val currentHoistStopDwn1: Int = 0,
        val currentHoistStopUp1: Int = 0,
        val currentHoistDwnDwnSp1: Int = 0,
        val currentHoistUpDwnSp1: Int = 0,
    )


    //Функция проверки и преобразования целых чисел
    private fun validateAndConvert(value: String, min: Int, max: Int): Int {
        val intValue = value.toIntOrNull() ?: throw NumberFormatException("Некорректное число")
        if (intValue !in min..max) throw IllegalArgumentException("Значение вне диапазона")
        return intValue
    }
    //Функция проверки и преобразования десятичных чисел
    private fun validateAndConvertFloat(value: String, min: Float, max: Float): Int {
        val floatValue = value.toFloatOrNull() ?: throw NumberFormatException("Некорректное число")
        if (floatValue < min || floatValue > max) throw IllegalArgumentException("Значение вне диапазона")
        return (floatValue * 10).toInt()
    }


    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДАТЧИК ВЫСОТЫ ПОДЪЕМА!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // Hoist. ВКЛЮЧИТЬ/ВЫКЛЮЧИТЬ ТЕСТ ОГРАНИЧЕНИЙ
    fun onSetHoistLimitTestEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(803, 1)
                delay(200)
                modbusRepository.writeRegister(803, 0)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
                _logMessageHoist.value = null
            }
        }
    }

    // Hoist. ЗАПОМНИТЬ положение по высоте при движении вверх
    fun onSetHoistFwdMemEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(804, 1)
                delay(200)
                modbusRepository.writeRegister(804, 0)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
                _logMessageHoist.value = null
            }
        }
    }


    // Hoist. ЗАПОМНИТЬ положение по высоте при движении вверх
    fun onSetHoistRevMemEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(805, 1)
                delay(200)
                modbusRepository.writeRegister(805, 0)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
                _logMessageHoist.value = null
            }
        }
    }

    // Hoist. Запись Стоп Вверх [м]
    fun onSetValueHoistPosFvdStop() {
        onSetHoistFwdMemEn()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistPosFwdStop.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(806, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Hoist. Запись Стоп Вниз [м]
    fun onSetValueHoistPosRevStop() {
        onSetHoistRevMemEn()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistPosRevStop.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(807, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Hoist. Запись снижение скорости Вверх [м]
    fun onSetValueHoistPosFwdDwnSp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistPosFwdDwnSp.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(808, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Hoist. Запись снижение скорости Вниз [м]
    fun onSetValueHoistPosRevDwnSp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistPosRevDwnSp.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(809, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    //ДАТЧИК ВЫСОТЫ. Обновление для UI
    fun updateValueHoistPosFwdStop(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueHoistPosFwdStop.value = value } }

    fun updateValueHoistPosRevStop(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueHoistPosRevStop.value = value } }

    fun updateValueHoistPosFwdDwnSp(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueHoistPosFwdDwnSp.value = value } }

    fun updateValueHoistPosRevDwnSp(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueHoistPosRevDwnSp.value = value } }











    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("SettingSensorsVM", "ViewModel cleared")
        // Деактивируем конфигурацию при уничтожении ViewModel
        modbusRepository.setActiveUiConfiguration(null)
    }
}