package com.example.giraffenew.setting_load_capacity.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.entities.UISettingLoadCapacityConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingLoadCapacityViewModel
@Inject constructor(
    private val modbusRepository: ModbusRepository
): ViewModel(){


    private val _setValueMinWeight = mutableStateOf("")
    val setValueMinWeight: State<String> = _setValueMinWeight

    private val _setValueMaxWeight = mutableStateOf("")
    val setValueMaxWeight: State<String> = _setValueMaxWeight

    private val _errorMessageGH = mutableStateOf<String?>(null)
    val errorMessageGH: State<String?> = _errorMessageGH

    private val _logMessageGH = mutableStateOf<String?>(null)
    val logMessageGH: State<String?> = _logMessageGH


    private val _errorMessageWeightSensor = mutableStateOf<String?>(null)
    val errorMessageWeightSensor: State<String?> = _errorMessageWeightSensor

    private val _logMessageWeightSensor = mutableStateOf<String?>(null)
    val logMessageWeightSensor: State<String?> = _logMessageWeightSensor

    private val addresses = listOf(
        33508, 33505, 33020, 33200, 33509, 33202, 33025, 33026, 33130, 33110 // Регистры для считывания
    )

    private val _state = mutableStateOf(LoadCapacityState())
    val state: State<LoadCapacityState> = _state

    private var dataCollectionJob: Job? = null

//    init {
//        // Активируем UI конфигурацию для этого экрана
//        modbusRepository.setActiveUiConfiguration(UISettingLoadCapacityConfiguration.NAME_SETTING_LOAD_CAPACITY)
//        startDataCollection()
//    }

    fun setActiveConfiguration() {
        modbusRepository.setActiveUiConfiguration(UISettingLoadCapacityConfiguration.NAME_SETTING_LOAD_CAPACITY)
        startDataCollection()
        writeUiActiveStatus(37) // Активируем регистр
    }

    fun clearActiveConfiguration() {
        pauseDataCollection()
        modbusRepository.setActiveUiConfiguration(null)
        writeUiActiveStatus(0) // Деактивируем регистр
    }

    private fun writeUiActiveStatus(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            modbusRepository.writeRegister(63531, value).fold(
                onSuccess = {
                    Log.d("LoadCapacityVM", "Register 63531 set to $value")
                },
                onFailure = { e ->
                    Log.e("LoadCapacityVM", "Failed to write register 63531", e)
                }
            )
        }
    }

    fun startDataCollection() {
        dataCollectionJob?.cancel() // Всегда отменяем предыдущую job

        Log.d("SettingLoadCapacityMenuVM", "Starting data collection for ${UISettingLoadCapacityConfiguration.NAME_SETTING_LOAD_CAPACITY}")
        dataCollectionJob = viewModelScope.launch(Dispatchers.Default) {
            modbusRepository.getConfigurationFlow(UISettingLoadCapacityConfiguration.NAME_SETTING_LOAD_CAPACITY)
                .collect { cache ->
                    Log.d("SettingLoadCapacityViewModel", "Updating from cache, size: ${cache.size}")
                    try {
                        // Создаем отфильтрованную карту только для нужных адресов
                        val filteredCache = addresses.associate { addr ->
                            addr to cache[addr]?.value
                        }
                        _state.value = createSettingLoadCapacityState(filteredCache)
                    } catch (e: Exception) {
                        Log.e("SettingLoadCapacityVM", "Data collection error", e)
                    }
                }
        }
    }

    // Приостановка сбора данных
    fun pauseDataCollection() {
        Log.d("SettingLoadCapacityViewModel", "Pausing data collection")
        dataCollectionJob?.cancel()
        dataCollectionJob = null
    }
    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДАТЧИКИ ОТОБРАЖЕНИЕ ДАННЫХ И СОСТОЯНИЯ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // //Функция createSettingLoadCapacityState() для обработки отфильтрованных данных:
    private fun createSettingLoadCapacityState(cache: Map<Int, Int?>): LoadCapacityState {
        return LoadCapacityState(
            //ПАРАМЕТРЫ ГРУЗОВОЙ ХАРАКТЕРИСТИКИ
            currentGruzovayZapasovka = cache[33202] ?: 0,
            currentMaxWeight = safeDivideTens(cache[33509] ?: 0),
            currentMaxWeight2 = safeDivideTens(cache[33508] ?: 0),
            currentCraneLenght = safeDivideTens(cache[33505] ?: 0),
            //КАЛИБРОВКА ДАТЧИКА УСИЛИЯ
            currentCurrAI = cache[33020] ?: 0,
            currentWeight = safeDivideTens(cache[33200] ?: 0),
            hmiWeightCalibStatus = cache[33025] ?: 0,
            hmiWeightCalibMessage = cache[33026] ?: 0,
            trolleyCurrent = safeDivideTens(cache[33130] ?: 0),
            heightCurrent = safeDivideTens(cache[33110] ?: 0),

            )
    }

    private fun safeDivideTens(value: Int?): Float {
        return (value ?: 0) / 10f
    }

    data class LoadCapacityState(
        //ПАРАМЕТРЫ ГРУЗОВОЙ ХАРАКТЕРИСТИКИ
        val currentGruzovayZapasovka: Int = 0,
        val currentMaxWeight: Float = 0f,
        val currentMaxWeight2: Float = 0f,
        val currentCraneLenght: Float = 0f,
        //КАЛИБРОВКА ДАТЧИКА УСИЛИЯ
        val currentCurrAI: Int = 0,
        val currentWeight: Float = 0f,
        val hmiWeightCalibStatus: Int = 0, //Статус калибровки датчика усилия
        val hmiWeightCalibMessage: Int = 0, //Сообщения при калибровке датчика усилия

        val trolleyCurrent: Float = 0f,
        val heightCurrent: Float = -0f,

        )

    //Функция проверки и преобразования десятичных чисел
    private fun validateAndConvertFloatTens(value: String, min: Float, max: Float): Int {
        val floatValue = value.toFloatOrNull() ?: throw NumberFormatException("Некорректное число")
        if (floatValue < min || floatValue > max) throw IllegalArgumentException("Значение вне диапазона")
        return (floatValue * 10).toInt()
    }

    private val VIRTUAL_ZERO = 1 //Константа для того, что бы в ПЛК была возможность передать ГХ-0
    // ВЫБОР ГРУЗОВОЙ ХАРАКТЕРИСТИКИ ГХ_0..6. Используем константу для корректной передачи ГХ-0
    fun onSetCargoCharacteristic(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63507, index+VIRTUAL_ZERO)
                _errorMessageGH.value = null
                _logMessageGH.value = "СОХРАНЕНО"
            } catch (e: Exception) {
                _errorMessageGH.value = "Выбрать не удалось:${e.message}"
                _logMessageGH.value = null
            }
        }
    }



    /**
    !!!!!!!!!!!!!!!!!!!КАЛИБРОВКА ДАТЧИКА УСИЛИЯ!!!!!!!!!!!!!!!!!!!!!!!!
     */

    // КАЛИБРОВКА МИНИМАЛЬНОГО ВЕСА.
    fun onSetMinWeightCalibration(
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                Log.d("LoadCapacityViewModel", "Writing registers...")
                // Активация калибровки
                modbusRepository.writeRegister(63028, 1)
                delay(200)
                modbusRepository.writeRegister(63028, 0)
            } catch (e: Exception) {
            }
        }
    }

    // КАЛИБРОВКА МАКСИМАЛЬНОГО ВЕСА.
    fun onSetMaxWeightCalibration(
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Активация калибровки
                modbusRepository.writeRegister(63029, 1)
                delay(200)
                modbusRepository.writeRegister(63029, 0)
            } catch (e: Exception) {
            }
        }
    }

    // СБРОС ТОЧЕК КАЛИБРОВКИ МАКС и МИН ВЕСА.  1-сброс минимума,2-сброс максимума
    fun onResetWeightCalibration(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Активация калибровки
                modbusRepository.writeRegister(63020, index)
                delay(200)
                modbusRepository.writeRegister(63020, 0)
            } catch (e: Exception) {
            }
        }
    }
    // Запись минимального веса груза (т)
    fun onSetValueMinWeight() {
        onSetMinWeightCalibration()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloatTens(_setValueMinWeight.value, 0.0f, 99.9f )
                // Запись в регистры
                modbusRepository.writeRegister(63021, model)
                _errorMessageWeightSensor.value = null
                _logMessageWeightSensor.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWeightSensor.value = null
                _errorMessageWeightSensor.value = "${e.message}"
            }
        }
    }
    // Запись максимального веса груза (т)
    fun onSetValueMaxWeight() {
        onSetMaxWeightCalibration()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloatTens(_setValueMaxWeight.value, 0.0f, 99.9f )
                // Запись в регистры
                modbusRepository.writeRegister(63022, model)
                _errorMessageWeightSensor.value = null
                _logMessageWeightSensor.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWeightSensor.value = null
                _errorMessageWeightSensor.value = "${e.message}"
            }
        }
    }


    // Калибровка датчика веса. Обновления для UI
    fun updateMinWeight(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueMinWeight.value = value } }
    fun updateMaxWeight(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueMaxWeight.value = value } }

    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("SettingLoadCapacityVM", "ViewModel cleared")
        // Деактивируем конфигурацию при уничтожении ViewModel
        modbusRepository.setActiveUiConfiguration(null)
    }
}