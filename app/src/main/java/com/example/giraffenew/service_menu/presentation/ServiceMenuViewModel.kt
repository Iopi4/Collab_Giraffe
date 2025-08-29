package com.example.giraffenew.service_menu.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.entities.UIServiceMenuRegisterConfiguration
import com.example.giraffenew.data.entities.UiHomeScreenRegisterConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
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
    val setValueTrolleyTrolleyOverSpd: State<String> = _setValueTrolleyTrolleyOverspd

    private val _errorMessageTrolley = mutableStateOf<String?>(null)
    val errorMessageTrolley: State<String?> = _errorMessageTrolley

    private val _logMessageTrolley = mutableStateOf<String?>(null)
    val logMessageTrolley: State<String?> = _logMessageTrolley

//СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА ВЕТРА
    private val _setValueWindSpeedLvl1 = mutableStateOf("")
    val setValueWindSpeedLvl1: State<String> = _setValueWindSpeedLvl1

    private val _setValueWindSpeedLvl2 = mutableStateOf("")
    val setValueWindSpeedLvl2: State<String> = _setValueWindSpeedLvl2

    private val _setValueWindSpeedMontageLvl = mutableStateOf("")
    val setValueWindSpeedMontageLvl: State<String> = _setValueWindSpeedMontageLvl

    private val _setValueWindTimeSmooth = mutableStateOf("")
    val setValueWindTimeSmooth: State<String> = _setValueWindTimeSmooth

    private val _setValueWindSpeedLvlClbr = mutableStateOf("")
    val setValueWindSpeedLvlClbr: State<String> = _setValueWindSpeedLvlClbr

    private val _setValueWindRK = mutableStateOf("")
    val setValueWindRK: State<String> = _setValueWindRK

    private val _errorMessageWind = mutableStateOf<String?>(null)
    val errorMessageWind: State<String?> = _errorMessageWind

    private val _logMessageWind = mutableStateOf<String?>(null)
    val logMessageWind: State<String?> = _logMessageWind


//СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКОВ ПОЛОЖЕНИЯ
    private val _setValueAbsEncRatioHoistGK = mutableStateOf("")
    val setValueAbsEncRatioHoistGK: State<String> = _setValueAbsEncRatioHoistGK

    private val _setValueAbsEncRatioSlewGK = mutableStateOf("")
    val setValueAbsEncRatioSlewGK: State<String> = _setValueAbsEncRatioSlewGK

    private val _setValueAbsEncRatioTrolleyGK = mutableStateOf("")
    val setValueAbsEncRatioTrolleyGK: State<String> = _setValueAbsEncRatioTrolleyGK

    private val _setValueAbsEncRatioCraneGK = mutableStateOf("")
    val setValueAbsEncRatioCraneGK: State<String> = _setValueAbsEncRatioCraneGK

    private val _setValueUDIHoistAbsEncMaxDeltaEn = mutableStateOf("")
    val setValueUDIHoistAbsEncMaxDeltaEn: State<String> = _setValueUDIHoistAbsEncMaxDeltaEn

    private val _setValueUDISlewAbsEncMaxDeltaEn = mutableStateOf("")
    val setValueUDISlewAbsEncMaxDeltaEn: State<String> = _setValueUDISlewAbsEncMaxDeltaEn

    private val _setValueUDITrolleyAbsEncMaxDeltaEn = mutableStateOf("")
    val setValueUDITrolleyAbsEncMaxDeltaEn: State<String> = _setValueUDITrolleyAbsEncMaxDeltaEn

    private val _setValueUDICraneAbsEncMaxDeltaEn = mutableStateOf("")
    val setValueUDICraneAbsEncMaxDeltaEn: State<String> = _setValueUDICraneAbsEncMaxDeltaEn

    private val _errorMessageAbsEnc = mutableStateOf<String?>(null)
    val errorMessageAbsEnc: State<String?> = _errorMessageAbsEnc

    private val _logMessageAbsEnc = mutableStateOf<String?>(null)
    val logMessageAbsEnc: State<String?> = _logMessageAbsEnc


//СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА УСИЛИЯ
    private val _setLoadCellMinWeight = mutableStateOf("")
    val setLoadCellMinWeight: State<String> = _setLoadCellMinWeight

    private val _setLoadCellMaxWeight = mutableStateOf("")
    val setLoadCellMaxWeight: State<String> = _setLoadCellMaxWeight

    private val _setLoadCellSmoothingDepth = mutableStateOf("")
    val setLoadCellSmoothingDepth: State<String> = _setLoadCellSmoothingDepth

    private val _setLoadCellOverSpeedThreshold = mutableStateOf("")
    val setLoadCellOverSpeedThreshold: State<String> = _setLoadCellOverSpeedThreshold

    private val _errorMessageLoadCell = mutableStateOf<String?>(null)
    val errorMessageLoadCell: State<String?> = _errorMessageLoadCell

    private val _logMessageLoadCell = mutableStateOf<String?>(null)
    val logMessageLoadCell: State<String?> = _logMessageLoadCell


    private val addresses = listOf(
        163, 381, 336, 997, 750, 751, 752, 753, 754, 755, 756, 757 // Регистры для считывания
        //940, 941, 942, 943, 944, 945, 946, 947, 950, 951, 952, 953, // Регистры Hoist
        //955, 956, 957, 958, 959, 968, 969,      // Регистры Slew
        //960, 961, 962, 963, 964, 965, 966, 967,  // Регистры Trolley
        //980, 981, 982, 983, 984, 985, 986,// Регистры датчика ветра
        //990, 991, 992, 993, 994, 995, 996, //Регистры датчиков положения
        // 1200, 1201, 1202, 1203 // Регистры датчики усилия
    )

    private val _state = mutableStateOf(ServiceMenuState())
    val state: State<ServiceMenuState> = _state

    private var dataCollectionJob: Job? = null

//    init {
//        // Активируем UI конфигурацию для этого экрана
//        modbusRepository.setActiveUiConfiguration(UIServiceMenuRegisterConfiguration.NAME_SERVICE_MENU)
//        startDataCollection()
//    }

    fun setActiveConfiguration() {
        modbusRepository.setActiveUiConfiguration(UIServiceMenuRegisterConfiguration.NAME_SERVICE_MENU)
        startDataCollection()
    }

    fun clearActiveConfiguration() {
        pauseDataCollection()
        modbusRepository.setActiveUiConfiguration(null)
    }

    fun startDataCollection() {
        //if (dataCollectionJob?.isActive == true) return
        dataCollectionJob?.cancel() // Всегда отменяем предыдущую job

        Log.d("ServiceMenuVM", "Starting data collection for ${UIServiceMenuRegisterConfiguration.NAME_SERVICE_MENU}")
        dataCollectionJob = viewModelScope.launch(Dispatchers.Default) {
            modbusRepository.getConfigurationFlow(UIServiceMenuRegisterConfiguration.NAME_SERVICE_MENU)
                .collect { cache ->
                    Log.d("ServiceMenuViewModel", "Updating from cache, size: ${cache.size}")
                    try {
                        // Создаем отфильтрованную карту только для нужных адресов
                        val filteredCache = addresses.associate { addr ->
                            addr to cache[addr]?.value
                        }
                        _state.value = createServiceMenuState(filteredCache)
                    } catch (e: Exception) {
                        Log.e("ServiceMenuScreenVM", "Data collection error", e)
                    }
                }
        }
    }

    // Приостановка сбора данных
    fun pauseDataCollection() {
        Log.d("HomeScreenVM", "Pausing data collection")
        dataCollectionJob?.cancel()
        dataCollectionJob = null
    }

    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДАТЧИКИ ОТОБРАЖЕНИЕ ДАННЫХ И СОСТОЯНИЯ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // //Функция createServiceMenuState() для обработки отфильтрованных данных:
    private fun createServiceMenuState(cache: Map<Int, Int?>): ServiceMenuState {
        return ServiceMenuState(
            currentWind = safeDivideTens(cache[163] ?: 0),
            currentAnemometerSensor = cache[381] ?: 0,
            currentAnemometerStatus = cache[336] ?: 0,

            currentDataLogErrStopHoist = cache[750] ?: 0,
            currentPasswordOrNaladka = cache[751] ?: 0,
            currentFOURPolygonsEn = cache[752] ?: 0,
            currentWithoutRingCurrentCollector = cache[753] ?: 0,
            currentZoneACCDECActive = cache[754] ?: 0,

            currentAbsEncDeltaControlEn = cache[755] ?: 0,
            currentAbsEncDebugModeHoist = cache[756] ?: 0,
            currentAbsEncDebugModeSlew = cache[757] ?: 0,
            )
    }


    private fun safeDivide(value: Int?): Float {
        return (value ?: 0) / 1f
    }

    private fun safeDivideTens(value: Int?): Float {
        return (value ?: 0) / 10f
    }

    data class ServiceMenuState(
        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА ВЕТРА
        val currentWind: Float = 0f,
        val currentAnemometerSensor: Int = 0,
        val currentAnemometerStatus: Int = 0,
        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКОВ ПОЛОЖЕНИЯ
        val currentAbsEncDeltaControlEn: Int = 0,
        val currentAbsEncDebugModeHoist: Int = 0,
        val currentAbsEncDebugModeSlew: Int = 0,
        //СЕРВИСНЫЙ РЕЖИМ. СИСТЕМНАЯ НАСТРОЙКА УРОВЕНЬ ИНЖЕНЕР
        val currentDataLogErrStopHoist: Int = 0,
        val currentPasswordOrNaladka: Int = 0,
        val currentFOURPolygonsEn: Int = 0,
        val currentWithoutRingCurrentCollector: Int = 0,
        val currentZoneACCDECActive: Int = 0,
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

    //Функция проверки и преобразования тысячных чисел
    private fun validateAndConvertFloatThousand(value: String, min: Float, max: Float): Int {
        val floatValue = value.toFloatOrNull() ?: throw NumberFormatException("Некорректное число")
        if (floatValue < min || floatValue > max) throw IllegalArgumentException("Значение вне диапазона")
        return (floatValue * 1000).toInt()
    }



    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ПРИВОД ПОДЪЕМА!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // Hoist. Запись времени разгона [сек]
    fun onSetHoistACC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistACC.value, 0.0f, 99.9f )
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
                val model = validateAndConvertFloat(_setValueHoistDEC.value, 0.0f,99.9f )
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
                val model = validateAndConvertFloat(_setValueHoistMontageACC.value, 0.0f,99.9f )
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
                val model = validateAndConvertFloat(_setValueHoistMontageDEC.value, 0.0f,99.9f )
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
                val model = validateAndConvertFloat(_setValueSlewACC.value, 0.0f,99.9f )
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
                val model = validateAndConvertFloat(_setValueSlewDEC.value, 0.0f,99.9f )
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
                val model = validateAndConvertFloat(_setValueTrolleyACC.value, 0.0f,99.9f )
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
                val model = validateAndConvertFloat(_setValueTrolleyDEC.value, 0.0f,99.9f )
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
    fun onSetTrolleyOverSpd() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueTrolleyTrolleyOverspd.value, 0.0f,99.9f )
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

    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДАТЧИК ВЕТРА!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // Wind. Запись уровня предупредительной сигнализации (м/c)
    fun onSetWindSpeedLvl1() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueWindSpeedLvl1.value, 0.0f,99.9f )
                // Запись в регистры
                modbusRepository.writeRegister(980, model)
                _errorMessageWind.value = null
                _logMessageWind.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWind.value = null
                _errorMessageWind.value = "Запись не удалась: ${e.message}"
            }
        }
    }
    // Wind. Запись уровня аварийной сигнализации (м/c)
    fun onSetWindSpeedLvl2() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueWindSpeedLvl2.value, 0.0f,99.9f )
                // Запись в регистры
                modbusRepository.writeRegister(981, model)
                _errorMessageWind.value = null
                _logMessageWind.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWind.value = null
                _errorMessageWind.value = "Запись не удалась: ${e.message}"
            }
        }
    }
    // Wind. Запись уровня аварийной сигнализации (м/c), в режиме монтаж
    fun onSetWindSpeedMontageLvl() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueWindSpeedMontageLvl.value, 0.0f,99.9f )
                // Запись в регистры
                modbusRepository.writeRegister(982, model)
                _errorMessageWind.value = null
                _logMessageWind.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWind.value = null
                _errorMessageWind.value = "Запись не удалась: ${e.message}"
            }
        }
    }
    // Wind. Запись времени усреднения, сек
    fun onSetWindTimeSmooth() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueWindTimeSmooth.value, 0.0f,99.9f )
                // Запись в регистры
                modbusRepository.writeRegister(983, model)
                _errorMessageWind.value = null
                _logMessageWind.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWind.value = null
                _errorMessageWind.value = "Запись не удалась: ${e.message}"
            }
        }
    }
    // Wind. Запись значения калибровки
    fun onSetWindSpeedLvlClbr() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueWindSpeedLvlClbr.value, 0.0f,99.9f )
                // Запись в регистры
                modbusRepository.writeRegister(984, model)
                _errorMessageWind.value = null
                _logMessageWind.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWind.value = null
                _errorMessageWind.value = "Запись не удалась: ${e.message}"
            }
        }
    }
    // Wind. Запись коэффициента калибровки 0.001938736
    fun onSetWindRK() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueWindRK.value, 0.0f,0.99999f )
                // Запись в регистры
                modbusRepository.writeRegister(985, model)
                _errorMessageWind.value = null
                _logMessageWind.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWind.value = null
                _errorMessageWind.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    fun onSetAnemometerClbr() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                Log.d("InitialSetupViewModel", "Writing registers...")
                // Активация калибровки
                modbusRepository.writeRegister(986, 1)
                delay(200)
                modbusRepository.writeRegister(986, 0)
                _errorMessageWind.value = null
                _logMessageWind.value = "Сохранено"
                // Логирование
            } catch (e: Exception) {
                _errorMessageWind.value = "Запись не удалась: ${e.message}"
                _logMessageWind.value = null
            }
        }
    }

    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДАТЧИК УСИЛИЯ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // LoadSensor. Запись минимальное значение измерения (показания датчика)
    fun onSetLoadCellMinWeight() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setLoadCellMinWeight.value, 0,10000 )
                // Запись в регистры
                modbusRepository.writeRegister(1200, model)
                _errorMessageLoadCell.value = null
                _logMessageLoadCell.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageLoadCell.value = null
                _errorMessageLoadCell.value = "Запись не удалась: ${e.message}"
            }
        }
    }
    // LoadSensor. Запись максимального значение измерения (показания датчика)
    fun onSetLoadCellMaxWeight() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setLoadCellMaxWeight.value, 0,32767 )
                // Запись в регистры
                modbusRepository.writeRegister(1201, model)
                _errorMessageLoadCell.value = null
                _logMessageLoadCell.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageLoadCell.value = null
                _errorMessageLoadCell.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // LoadSensor. Запись глубина сглаживания веса
    fun onSetLoadCellSmoothingDepth() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setLoadCellSmoothingDepth.value, 0,200 )
                // Запись в регистры
                modbusRepository.writeRegister(1202, model)
                _errorMessageLoadCell.value = null
                _logMessageLoadCell.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageLoadCell.value = null
                _errorMessageLoadCell.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // LoadSensor. Запись ограничение сверхскорости подъема
    fun onSetLoadCellOverSpeedThreshold() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setLoadCellOverSpeedThreshold.value, 0.0f,99.9f )
                // Запись в регистры
                modbusRepository.writeRegister(1203, model)
                _errorMessageLoadCell.value = null
                _logMessageLoadCell.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageLoadCell.value = null
                _errorMessageLoadCell.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДАТЧИКИ ПОЛОЖЕНИЯ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // AbsEncoder. Запись коэффициент датчика подъема
    fun onSetAbsEncRatioHoistGK() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloatThousand(_setValueAbsEncRatioHoistGK.value, 0.0000f,1.9999f )
                // Запись в регистры
                modbusRepository.writeRegister(990, model)
                _errorMessageAbsEnc.value = null
                _logMessageAbsEnc.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageAbsEnc.value = null
                _errorMessageAbsEnc.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // AbsEncoder. Запись коэффициент датчика поворота
    fun onSetAbsEncRatioSlewGK() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloatThousand(_setValueAbsEncRatioSlewGK.value, 0.0000f,1.9999f )
                // Запись в регистры
                modbusRepository.writeRegister(991, model)
                _errorMessageAbsEnc.value = null
                _logMessageAbsEnc.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageAbsEnc.value = null
                _errorMessageAbsEnc.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // AbsEncoder. Запись коэффициент датчика тележки
    fun onSetAbsEncRatioTrolleyGK() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloatThousand(_setValueAbsEncRatioTrolleyGK.value, 0.0000f,1.9999f )
                // Запись в регистры
                modbusRepository.writeRegister(992, model)
                _errorMessageAbsEnc.value = null
                _logMessageAbsEnc.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageAbsEnc.value = null
                _errorMessageAbsEnc.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // AbsEncoder. Запись коэффициент датчика хода крана
    fun onSetAbsEncRatioCraneGK() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloatThousand(_setValueAbsEncRatioCraneGK.value, 0.0000f,1.9999f )
                // Запись в регистры
                modbusRepository.writeRegister(993, model)
                _errorMessageAbsEnc.value = null
                _logMessageAbsEnc.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageAbsEnc.value = null
                _errorMessageAbsEnc.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // AbsEncoder. Запись максмальное рассогласование датчика подъема
    fun onSetUDIHoistAbsEncMaxDeltaEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueUDIHoistAbsEncMaxDeltaEn.value, 0,65535 )
                // Запись в регистры
                modbusRepository.writeRegister(997, model)
                _errorMessageAbsEnc.value = null
                _logMessageAbsEnc.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageAbsEnc.value = null
                _errorMessageAbsEnc.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // AbsEncoder. Запись максмальное рассогласование датчика Поворота
    fun onSetUDISlewAbsEncMaxDeltaEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueUDISlewAbsEncMaxDeltaEn.value, 0,65535 )
                // Запись в регистры
                modbusRepository.writeRegister(998, model)
                _errorMessageAbsEnc.value = null
                _logMessageAbsEnc.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageAbsEnc.value = null
                _errorMessageAbsEnc.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // AbsEncoder. Запись максмальное рассогласование датчика тележки
    fun onSetUDITrolleyAbsEncMaxDeltaEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueUDITrolleyAbsEncMaxDeltaEn.value, 0,65535 )
                // Запись в регистры
                modbusRepository.writeRegister(999, model)
                _errorMessageAbsEnc.value = null
                _logMessageAbsEnc.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageAbsEnc.value = null
                _errorMessageAbsEnc.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // AbsEncoder. Запись максмальное рассогласование датчика хода крана]
    fun onSetUDICraneAbsEncMaxDeltaEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueUDICraneAbsEncMaxDeltaEn.value, 0,65535 )
                // Запись в регистры
                modbusRepository.writeRegister(989, model)
                _errorMessageAbsEnc.value = null
                _logMessageAbsEnc.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageAbsEnc.value = null
                _errorMessageAbsEnc.value = "Запись не удалась: ${e.message}"
            }
        }
    }
    //AbsEncoder. Включить контроль максимального рассогласования абсолютных энкодеров
    fun onSetAbsEncDeltaControlEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                Log.d("InitialSetupViewModel", "Writing registers...")
                // Активация калибровки
                modbusRepository.writeRegister(994, 1)
//                delay(200)
//                modbusRepository.writeRegister(994, 0)
                _errorMessageAbsEnc.value = null
                _logMessageAbsEnc.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageAbsEnc.value = "Запись не удалась: ${e.message}"
                _logMessageAbsEnc.value = null
            }
        }
    }
    // AbsEncoder. Включить установки режима наладки механизма подъема при выходе датчика из строя
    fun onSetAbsEncDebugModeHoistEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                Log.d("InitialSetupViewModel", "Writing registers...")
                // Активация калибровки
                modbusRepository.writeRegister(995, 1)
//                delay(200)
//                modbusRepository.writeRegister(995, 0)
                _errorMessageAbsEnc.value = null
                _logMessageAbsEnc.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageAbsEnc.value = "Запись не удалась: ${e.message}"
                _logMessageAbsEnc.value = null
            }
        }
    }

    fun onSetAbsEncDebugModeSlewEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                Log.d("InitialSetupViewModel", "Writing registers...")
                // Активация калибровки
                modbusRepository.writeRegister(996, 1)
//                delay(200)
//                modbusRepository.writeRegister(996, 0)
                _errorMessageAbsEnc.value = null
                _logMessageAbsEnc.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageAbsEnc.value = "Запись не удалась: ${e.message}"
                _logMessageAbsEnc.value = null
            }
        }
    }



    //Wind. Обновление для UI
    fun updateWindSpeedLvl1(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueWindSpeedLvl1.value = value } }

    fun updateWindSpeedLvl2(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueWindSpeedLvl2.value = value } }

    fun updateWindSpeedMontageLvl(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueWindSpeedMontageLvl.value = value } }

    fun updateWindTimeSmooth(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueWindTimeSmooth.value = value } }

    fun updateWindSpeedLvlClbr(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueWindSpeedLvlClbr.value = value } }

    fun updateWindRK(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,1}(\\.\\d{0,5})?$"))) {
            _setValueWindRK.value = value } }



    //LoadSensor. Обновление для UI
    fun updateLoadCellMinWeight(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setLoadCellMinWeight.value = value } }

    fun updateLoadCellMaxWeight(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setLoadCellMaxWeight.value = value } }

    fun updateLoadCellSmoothingDepth(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setLoadCellSmoothingDepth.value = value } }

    fun updateLoadCellOverSpeedThreshold(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setLoadCellOverSpeedThreshold.value = value } }


    // ДАТЧИКИ ПОЛОЖЕНИЯ. Обновления для UI
    fun updateValueAbsEncRatioHoistGK(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,1}(\\.\\d{0,4})?$"))) {
            _setValueAbsEncRatioHoistGK.value = value } }

    fun updateValueAbsEncRatioSlewGK(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,1}(\\.\\d{0,4})?$"))) {
            _setValueAbsEncRatioSlewGK.value = value } }

    fun updateValueAbsEncRatioTrolleyGK(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,1}(\\.\\d{0,4})?$"))) {
            _setValueAbsEncRatioTrolleyGK.value = value } }

    fun updateValueAbsEncRatioCraneGK(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,1}(\\.\\d{0,4})?$"))) {
            _setValueAbsEncRatioCraneGK.value = value } }

    fun updateValueUDIHoistAbsEncMaxDeltaEn(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueUDIHoistAbsEncMaxDeltaEn.value = value } }

    fun updateValueUDISlewAbsEncMaxDeltaEn(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueUDISlewAbsEncMaxDeltaEn.value = value } }

    fun updateValueUDITrolleyAbsEncMaxDeltaEn(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueUDITrolleyAbsEncMaxDeltaEn.value = value } }

    fun updateValueUDICraneAbsEncMaxDeltaEn(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueUDICraneAbsEncMaxDeltaEn.value = value } }




    // Подъем. Обновления для UI
    fun updateHoistACC(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueHoistACC.value = value } }
    fun updateHoistDEC(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
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
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueHoistMontageACC.value = value }}
    fun updateHoistMontageDEC(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueHoistMontageDEC.value = value }}
    fun updateHoistMontageSpdRef1(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueHoistMontageSpdRef1.value = value }}
    fun updateHoistMontageSpdRef2(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueHoistMontageSpdRef2.value = value }}

    // Поворот. Обновления для UI
    fun updateSlewACC(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueSlewACC.value = value } }
    fun updateSlewDEC(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
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
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueTrolleyACC.value = value } }
    fun updateTrolleyDEC(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
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

    fun updateTrolleyOverLsp(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueTrolleyTrolleyOverspd.value = value }}


    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("ServiceMenuVM", "ViewModel cleared")
        // Деактивируем конфигурацию при уничтожении ViewModel
        modbusRepository.setActiveUiConfiguration(null)
    }
}