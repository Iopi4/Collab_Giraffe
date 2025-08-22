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
    // СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ПРИВОДА ПОДЪЕМА
    private val _setValueHoistSpdRef1 = mutableStateOf("")
    val setValueHoistSpdRef1: State<String> = _setValueHoistSpdRef1

    private val _setValueHoistSpdRef2 = mutableStateOf("")
    val setValueHoistSpdRef2: State<String> = _setValueHoistSpdRef2

    private val _setValueHoistSpdRef3 = mutableStateOf("")
    val setValueHoistSpdRef3: State<String> = _setValueHoistSpdRef3

    private val _setValueHoistSpdRef4 = mutableStateOf("")
    val setValueHoistSpdRef4: State<String> = _setValueHoistSpdRef4

    private val _setValueHoistACC = mutableStateOf("")
    val setValueHoistACC: State<String> = _setValueHoistACC

    private val _setValueHoistDEC = mutableStateOf("")
    val setValueHoistDEC: State<String> = _setValueHoistDEC

    //Пониженная скорость при нажатии кнопки
    private val _setValueHoistSpdRefButton = mutableStateOf("")
    val setValueHoistSpdRefButton: State<String> = _setValueHoistSpdRefButton

    private val _setValueHoistSpdRefMax = mutableStateOf("")
    val setValueHoistSpdRefMax: State<String> = _setValueHoistSpdRefMax

    private val _setValueHoistMontageSpdRef1 = mutableStateOf("")
    val setValueHoistMontageSpdRef1: State<String> = _setValueHoistMontageSpdRef1

    private val _setValueHoistMontageSpdRef2 = mutableStateOf("")
    val setValueHoistMontageSpdRef2: State<String> = _setValueHoistMontageSpdRef2

    private val _setValueHoistMontageACC = mutableStateOf("")
    val setValueHoistMontageACC: State<String> = _setValueHoistMontageACC

    private val _setValueHoistMontageDEC = mutableStateOf("")
    val setValueHoistMontageDEC: State<String> = _setValueHoistMontageDEC

    private val _errorMessageHoist = mutableStateOf<String?>(null)
    val errorMessageHoist: State<String?> = _errorMessageHoist

    private val _logMessageHoist = mutableStateOf<String?>(null)
    val logMessageHoist: State<String?> = _logMessageHoist

    private val _errorMessageHoistMontage = mutableStateOf<String?>(null)
    val errorMessageHoistMontage: State<String?> = _errorMessageHoistMontage

    private val _logMessageHoistMontage = mutableStateOf<String?>(null)
    val logMessageHoistMontage: State<String?> = _logMessageHoistMontage


    // СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ПРИВОДА ПОВОРОТА
    private val _setValueSlewSpdRef1 = mutableStateOf("")
    val setValueSlewSpdRef1: State<String> = _setValueSlewSpdRef1

    private val _setValueSlewSpdRef2 = mutableStateOf("")
    val setValueSlewSpdRef2: State<String> = _setValueSlewSpdRef2

    private val _setValueSlewSpdRef3 = mutableStateOf("")
    val setValueSlewSpdRef3: State<String> = _setValueSlewSpdRef3

    private val _setValueSlewSpdRef4 = mutableStateOf("")
    val setValueSlewSpdRef4: State<String> = _setValueSlewSpdRef4

    private val _setValueSlewACC = mutableStateOf("")
    val setValueSlewACC: State<String> = _setValueSlewACC

    private val _setValueSlewDEC = mutableStateOf("")
    val setValueSlewDEC: State<String> = _setValueSlewDEC

    //Снижение скорости поворота
    private val _setValueSlewSpdStopButton = mutableStateOf("")
    val setValueSlewSpdStopButton: State<String> = _setValueSlewSpdStopButton

    private val _errorMessageSlew = mutableStateOf<String?>(null)
    val errorMessageSlew: State<String?> = _errorMessageSlew

    private val _logMessageSlew = mutableStateOf<String?>(null)
    val logMessageSlew: State<String?> = _logMessageSlew

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

    private val addresses = listOf(
        940, 941, 942, 943, 944, 945, 946, 947, 950, 951, 952, 953, // Регистры Hoist
        955, 956, 957, 958, 959, 968, 969,      // Регистры Slew
        960, 961, 962, 963, 964, 965, 966, 967  // Регистры Trolley
    )

    init { startDataCollection() }

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
                        Log.d("ServiceMenuViewModel", "Relevant registers changed: $currentValues")

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
        Log.d("ServiceMenuViewModel", "Pausing data collection")
        dataCollectionJob?.cancel()
        dataCollectionJob = null
    }
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
        return (floatValue * 100).toInt()
    }



    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ПРИВОД ПОДЪЕМА!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // Hoist. Запись времени разгона [сек]
    fun onSetHoistACC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistACC.value, 0.0f, 99.99f )
                // Запись в регистры
                modbusRepository.writeRegister(946, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Hoist. Запись времени торможения [сек]
    fun onSetHoistDEC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistDEC.value, 0.0f,99.99f )
                // Запись в регистры
                modbusRepository.writeRegister(947, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"

            }
        }
    }

    // Hoist. Запись скорости 1.
    fun onSetHoistSpdRef1() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistSpdRef1.value, 0,9999 )
                // Запись в регистры
                modbusRepository.writeRegister(941, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Hoist. Запись скорости 2.
    fun onSetHoistSpdRef2() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistSpdRef2.value, 0,9999 )
                // Запись в регистры
                modbusRepository.writeRegister(942, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Hoist. Запись скорости 3.
    fun onSetHoistSpdRef3() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistSpdRef3.value, 0,9999 )
                // Запись в регистры
                modbusRepository.writeRegister(943, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Hoist. Запись скорости 4.
    fun onSetHoistSpdRef4() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistSpdRef4.value, 0,9999 )
                // Запись в регистры
                modbusRepository.writeRegister(944, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Hoist. Запись скорости при нажатии кнопки
    fun onSetHoistSpdRefButton() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistSpdRefButton.value, 0,999 )
                // Запись в регистры
                modbusRepository.writeRegister(940, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Hoist. Значение максимальной скорости двигателя подъема
    fun onSetHoistSpdRefMax() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistSpdRefMax.value, 0,9999 )
                // Запись в регистры
                modbusRepository.writeRegister(945, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
            }
        }
    }


    // Hoist. Запись времени разгона [сек]. Режим монтаж
    fun onSetHoistMontageACC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistMontageACC.value, 0.0f,99.99f )
                // Запись в регистры
                modbusRepository.writeRegister(952, model)
                _errorMessageHoistMontage.value = null
                _logMessageHoistMontage.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoistMontage.value = null
                _errorMessageHoistMontage.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Hoist. Запись времени торможения [сек]. Режим монтаж
    fun onSetHoistMontageDEC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistMontageDEC.value, 0.0f,99.99f )
                // Запись в регистры
                modbusRepository.writeRegister(953, model)
                _errorMessageHoistMontage.value = null
                _logMessageHoistMontage.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoistMontage.value = null
                _errorMessageHoistMontage.value = "Запись не удалась: ${e.message}"

            }
        }
    }

    // Hoist. Запись скорости 1. Режим монтаж
    fun onSetHoistMontageSpdRef1() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistMontageSpdRef1.value, 0,9999 )
                // Запись в регистры
                modbusRepository.writeRegister(950, model)
                _errorMessageHoistMontage.value = null
                _logMessageHoistMontage.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoistMontage.value = null
                _errorMessageHoistMontage.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Hoist. Запись скорости 2. Режим монтаж
    fun onSetHoistMontageSpdRef2() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistMontageSpdRef2.value, 0,9999 )
                // Запись в регистры
                modbusRepository.writeRegister(951, model)
                _errorMessageHoistMontage.value = null
                _logMessageHoistMontage.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoistMontage.value = null
                _errorMessageHoistMontage.value = "Запись не удалась: ${e.message}"
            }
        }
    }


    /**
!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ПРИВОД ПОВОРОТА!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */

    // Slew. Запись времени разгона [сек]
    fun onSetSlewACC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueSlewACC.value, 0.0f,99.99f )
                // Запись в регистры
                modbusRepository.writeRegister(968, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Slew. Запись времени торможения [сек]
    fun onSetSlewDEC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueSlewDEC.value, 0.0f,99.99f )
                // Запись в регистры
                modbusRepository.writeRegister(969, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "Запись не удалась: ${e.message}"

            }
        }
    }

    // Slew. Запись скорости 1.
    fun onSetSlewSpdRef1() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueSlewSpdRef1.value, 0,9999 )
                // Запись в регистры
                modbusRepository.writeRegister(956, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Slew. Запись скорости 2.
    fun onSetSlewSpdRef2() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueSlewSpdRef2.value, 0,9999 )
                // Запись в регистры
                modbusRepository.writeRegister(957, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Slew. Запись скорости 3.
    fun onSetSlewSpdRef3() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueSlewSpdRef3.value, 0,9999 )
                // Запись в регистры
                modbusRepository.writeRegister(958, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Slew. Запись скорости 4.
    fun onSetSlewSpdRef4() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueSlewSpdRef4.value, 0,9999 )
                // Запись в регистры
                modbusRepository.writeRegister(959, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Slew. Запись скорости для остановки при нажатии кнопки
    fun onSetSlewSpdStopButton() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueSlewSpdStopButton.value, 0,200 )
                // Запись в регистры
                modbusRepository.writeRegister(955, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "Запись не удалась: ${e.message}"
            }
        }
    }


    /**
 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ПРИВОД ТЕЛЕЖКИ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // Trolley. Запись времени разгона [сек]
    fun onSetTrolleyACC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueTrolleyACC.value, 0.0f,99.99f )
                // Запись в регистры
                modbusRepository.writeRegister(964, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Trolley. Запись времени торможения [сек]
    fun onSetTrolleyDEC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueTrolleyDEC.value, 0.0f,99.99f )
                // Запись в регистры
                modbusRepository.writeRegister(965, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"

            }
        }
    }

    // Trolley. Запись скорости 1.
    fun onSetTrolleySpdRef1() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleySpdRef1.value, 0,9999 )
                // Запись в регистры
                modbusRepository.writeRegister(960, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Trolley. Запись скорости 2.
    fun onSetTrolleySpdRef2() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleySpdRef2.value, 0,9999 )
                // Запись в регистры
                modbusRepository.writeRegister(961, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Trolley. Запись скорости 3.
    fun onSetTrolleySpdRef3() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleySpdRef3.value, 0,9999 )
                // Запись в регистры
                modbusRepository.writeRegister(962, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Trolley. Запись скорости 4.
    fun onSetTrolleySpdRef4() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleySpdRef4.value, 0,9999 )
                // Запись в регистры
                modbusRepository.writeRegister(963, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Trolley. Запись пониженная скорость тележки
    fun onSetTrolleyLsp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleyLsp.value, 0,9999 )
                // Запись в регистры
                modbusRepository.writeRegister(966, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Trolley. Значение веса груза при превышении которого запрещена сверхсинхронная работа тележки
    fun onSetTrolleyOverspd() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleyTrolleyOverspd.value, 0,99999 )
                // Запись в регистры
                modbusRepository.writeRegister(967, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Подъем. Обновления для UI
    fun updateHoistACC(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,2})?$"))) {
            _setValueHoistACC.value = value } }
    fun updateHoistDEC(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,2})?$"))) {
            _setValueHoistDEC.value = value } }
    fun updateHoistSpdRef1(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueHoistSpdRef1.value = value } }
    fun updateHoistSpdRef2(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueHoistSpdRef2.value = value } }
    fun updateHoistSpdRef3(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueHoistSpdRef3.value = value } }
    fun updateHoistSpdRef4(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueHoistSpdRef4.value = value } }
    fun updateHoistSpdRefButton(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueHoistSpdRefButton.value = value }}
    fun updateHoistSpdRefMax(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueHoistSpdRefMax.value = value }}

    fun updateHoistMontageACC(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,2})?$"))) {
            _setValueHoistMontageACC.value = value }}
    fun updateHoistMontageDEC(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,2})?$"))) {
            _setValueHoistMontageDEC.value = value }}
    fun updateHoistMontageSpdRef1(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueHoistMontageSpdRef1.value = value }}
    fun updateHoistMontageSpdRef2(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueHoistMontageSpdRef2.value = value }}

    // Поворот. Обновления для UI
    fun updateSlewACC(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,2})?$"))) {
            _setValueSlewACC.value = value } }
    fun updateSlewDEC(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,2})?$"))) {
            _setValueSlewDEC.value = value } }
    fun updateSlewSpdRef1(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueSlewSpdRef1.value = value } }
    fun updateSlewSpdRef2(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueSlewSpdRef2.value = value } }
    fun updateSlewSpdRef3(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueSlewSpdRef3.value = value } }
    fun updateSlewSpdRef4(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueSlewSpdRef4.value = value } }
    fun updateSlewSpdStopButton(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueSlewSpdStopButton.value = value }}


    // Тележка. Обновления для UI
    fun updateTrolleyACC(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,2})?$"))) {
            _setValueTrolleyACC.value = value } }
    fun updateTrolleyDEC(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,2})?$"))) {
            _setValueTrolleyDEC.value = value } }
    fun updateTrolleySpdRef1(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueTrolleySpdRef1.value = value } }
    fun updateTrolleySpdRef2(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueTrolleySpdRef2.value = value } }
    fun updateTrolleySpdRef3(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueTrolleySpdRef3.value = value } }
    fun updateTrolleySpdRef4(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueTrolleySpdRef4.value = value } }

    fun updateTrolleyLsp(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueTrolleyLsp.value = value }}


    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("InitialSetupVM", "ViewModel cleared")
        dataCollectionJob?.cancel()
    }

}