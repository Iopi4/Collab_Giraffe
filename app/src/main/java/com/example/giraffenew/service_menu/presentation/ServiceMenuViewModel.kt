package com.example.giraffenew.service_menu.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.entities.UiServiceMenuRegisterConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ServiceMenuViewModel
@Inject constructor(
    private val modbusRepository: ModbusRepository
): ViewModel(){

    // СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ПРИВОДА ТЕЛЕЖКИ
    private val _setValueTrolleySpdRef1 = mutableStateOf("")
    val setValueTrolleySpdRef1: State<String> = _setValueTrolleySpdRef1

    private val _setValueTrolleySpdRef2 = mutableStateOf("")
    val setValueTrolleySpdRef2: State<String> = _setValueTrolleySpdRef2

    private val _setValueTrolleySpdRef3 = mutableStateOf("")
    val setValueTrolleySpdRef3: State<String> = _setValueTrolleySpdRef3

    private val _setValueTrolleySpdRef4 = mutableStateOf("")
    val setValueTrolleySpdRef4: State<String> = _setValueTrolleySpdRef4

    private val _setValueTrolleyACC = mutableStateOf("")
    val setValueTrolleyACC: State<String> = _setValueTrolleyACC

    private val _setValueTrolleyDEC = mutableStateOf("")
    val setValueTrolleyDEC: State<String> = _setValueTrolleyDEC

    //пониженная скорость тележки
    private val _setValueTrolleyLsp = mutableStateOf("")
    val setValueTrolleyTrolleyLsp: State<String> = _setValueTrolleyLsp

    //значение веса груза при превышении которого запрещена сверхинсхронная работа тележки
    private val _setValueTrolleyTrolleyOverspd = mutableStateOf("")
    val setValueTrolleyTrolleyOverspd: State<String> = _setValueTrolleyTrolleyOverspd


    private val _errorMessageTrolley = mutableStateOf<String?>(null)
    val errorMessageTrolley: State<String?> = _errorMessageTrolley

    private val _logMessageTrolley = mutableStateOf<String?>(null)
    val logMessageTrolley: State<String?> = _logMessageTrolley


    private var previousValues: Map<Int, Int?> = emptyMap()

    private var dataCollectionJob: Job? = null

    private val addresses = listOf(960, 961, 962, 963, 964, 965) // Только нужные регистры

    init {
        startDataCollection()

    }

    fun startDataCollection() {
        if (dataCollectionJob?.isActive == true) return

        Log.d("ServiceMenuVM", "Starting data collection")
        dataCollectionJob = viewModelScope.launch(Dispatchers.Default) {
            modbusRepository.getConfigurationFlow(UiServiceMenuRegisterConfiguration.NAME_SERVICE_MENU)
                .collect { cache ->
                    Log.d("ServiceMenuViewModel", "Updating from cache, size: ${cache.size}")

                    // Фильтруем только нужные регистры
                    val currentValues = addresses.associate { addr ->
                        addr to cache[addr]?.value
                    }
                    // Проверяем, изменились ли значения целевых регистров
                    if (haveRelevantRegistersChanged(currentValues)) {
                        Log.d("TopBarViewModel", "Relevant registers changed: $currentValues")

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

    // Приостановка сбора данных
    fun pauseDataCollection() {
        Log.d("InitialSetupVM", "Pausing data collection")
        dataCollectionJob?.cancel()
        dataCollectionJob = null
    }

    private fun validateAndConvert(value: String, min: Int, max: Int): Int {
        val intValue = value.toIntOrNull() ?: throw NumberFormatException("Некорректное число")
        if (intValue !in min..max) throw IllegalArgumentException("Значение вне диапазона")
        return intValue
    }

    // Тележка. Запись времени разгона [сек]
    fun onSetTrolleyACC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleyACC.value, 0,99 )
                // Запись в регистры модель крана
                modbusRepository.writeRegister(964, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Тележка. Запись времени торможения [сек]
    fun onSetTrolleyDEC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleyDEC.value, 0,99 )
                // Запись в регистры модель крана
                modbusRepository.writeRegister(965, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"

            }
        }
    }

    // Тележка. Запись скорости 1.
    fun onSetTrolleySpdRef1() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleySpdRef1.value, 0,9999 )
                // Запись в регистры модель крана
                modbusRepository.writeRegister(960, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Тележка. Запись скорости 2.
    fun onSetTrolleySpdRef2() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleySpdRef2.value, 0,9999 )
                // Запись в регистры модель крана
                modbusRepository.writeRegister(961, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Тележка. Запись скорости 3.
    fun onSetTrolleySpdRef3() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleySpdRef3.value, 0,9999 )
                // Запись в регистры модель крана
                modbusRepository.writeRegister(962, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Тележка. Запись скорости 4.
    fun onSetTrolleySpdRef4() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleySpdRef4.value, 0,9999 )
                // Запись в регистры модель крана
                modbusRepository.writeRegister(963, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Тележка. Запись скорости 4.
    fun onSetTrolleyLsp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleyLsp.value, 0,9999 )
                // Запись в регистры модель крана
                modbusRepository.writeRegister(966, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Тележка. Запись пониженной скорости.
    fun onSetTrolleyOverspd() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleyTrolleyOverspd.value, 0,999 )
                // Запись в регистры модель крана
                modbusRepository.writeRegister(967, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Тележка. Обновления для UI
    fun updateTrolleyACC(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueTrolleyACC.value = value
        }
    }
    fun updateTrolleyDEC(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueTrolleyDEC.value = value
        }
    }
    fun updateTrolleySpdRef1(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueTrolleySpdRef1.value = value
        }
    }
    fun updateTrolleySpdRef2(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueTrolleySpdRef2.value = value
        }
    }
    fun updateTrolleySpdRef3(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueTrolleySpdRef3.value = value
        }
    }
    fun updateTrolleySpdRef4(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueTrolleySpdRef4.value = value
        }
    }

    fun updateTrolleyLsp(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueTrolleyLsp.value = value
        }
    }








    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("InitialSetupVM", "ViewModel cleared")
        dataCollectionJob?.cancel()
    }

}