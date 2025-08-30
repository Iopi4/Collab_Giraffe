package com.example.giraffenew.coordinate_protection.prezentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.entities.UICoordinateProtectionConfiguration
import com.example.giraffenew.data.entities.UISettingSensorsConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Int

@HiltViewModel
class CoordinateProtectionViewModule
@Inject constructor(
    private val modbusRepository: ModbusRepository
): ViewModel(

){
    //НАСТРОЙКА ПОЛИГОНА №1
    private val _setValuePolygon1Height = mutableStateOf("")
    val setValuePolygon1Height: State<String> = _setValuePolygon1Height

    private val _setValuePolygon1PointNum = mutableStateOf("")
    val setValuePolygon1PointNum: State<String> = _setValuePolygon1PointNum

    private val _setValuePolygon1TrolleyPos = mutableStateOf("")
    val setValuePolygon1TrolleyPos: State<String> = _setValuePolygon1TrolleyPos

    private val _setValuePolygon1SlewPos = mutableStateOf("")
    val setValuePolygon1SlewPos: State<String> = _setValuePolygon1SlewPos

    private val _setValuePolygon1CranePos = mutableStateOf("")
    val setValuePolygon1CranePos: State<String> = _setValuePolygon1CranePos

    private val _setValuePolygon1PxPos = mutableStateOf("")
    val setValuePolygon1PxPos: State<String> = _setValuePolygon1PxPos

    private val _setValuePolygon1PyPos = mutableStateOf("")
    val setValuePolygon1PyPos: State<String> = _setValuePolygon1PyPos





    //НАСТРОЙКА ПОЛИГОНА №2
    private val _setValuePolygon2Height = mutableStateOf("")
    val setValuePolygon2Height: State<String> = _setValuePolygon2Height

    private val _setValuePolygon2PointNum = mutableStateOf("")
    val setValuePolygon2PointNum: State<String> = _setValuePolygon2PointNum

    private val _setValuePolygon2TrolleyPos = mutableStateOf("")
    val setValuePolygon2TrolleyPos: State<String> = _setValuePolygon2TrolleyPos

    private val _setValuePolygon2SlewPos = mutableStateOf("")
    val setValuePolygon2SlewPos: State<String> = _setValuePolygon2SlewPos

    private val _setValuePolygon2CranePos = mutableStateOf("")
    val setValuePolygon2CranePos: State<String> = _setValuePolygon2CranePos

    private val _setValuePolygon2PxPos = mutableStateOf("")
    val setValuePolygon2PxPos: State<String> = _setValuePolygon2PxPos

    private val _setValuePolygon2PyPos = mutableStateOf("")
    val setValuePolygon2PyPos: State<String> = _setValuePolygon2PyPos


    private val _errorMessage = mutableStateOf<String?>(null)
    val errorMessag: State<String?> = _errorMessage

    private val _logMessage = mutableStateOf<String?>(null)
    val logMessage: State<String?> = _logMessage

    private val _errorMessagePolygon1 = mutableStateOf<String?>(null)
    val errorMessagePolygon1: State<String?> = _errorMessagePolygon1

    private val _logMessagePolygon1 = mutableStateOf<String?>(null)
    val logMessagePolygon1: State<String?> = _logMessagePolygon1

    private val _errorMessagePolygon2 = mutableStateOf<String?>(null)
    val errorMessagePolygon2: State<String?> = _errorMessagePolygon2

    private val _logMessagePolygon2 = mutableStateOf<String?>(null)
    val logMessagePolygon2: State<String?> = _logMessagePolygon2


    // Регистры для считывания. Те на которые подписываемся
    private val addresses = listOf(
        610, 611, 612, 613, 614, 615, 616, 617, 618, 619, 620, 621, 622, 623, 624, 625, 626, 627, 628, 629,
        155, 158, 162,
        430, 431, 432, 433, 434, 435, 436, 437, 438, 439, 440, 441, 442, 443, 444, 445, 446, 447, 448, 449,
        450, 451, 452, 453, 454, 455, 456, 457, 458, 459, 460, 461, 462, 463, 464, 465, 466, 467, 468, 469
    )

    private val _state = mutableStateOf(CoordinateProtectionState())
    val state: State<CoordinateProtectionState> = _state

    private var dataCollectionJob: Job? = null

    fun setActiveConfiguration() {
        modbusRepository.setActiveUiConfiguration(UISettingSensorsConfiguration.NAME_SETTING_SENSORS)
        startDataCollection()
    }

    fun clearActiveConfiguration() {
        pauseDataCollection()
        modbusRepository.setActiveUiConfiguration(null)
    }


    fun startDataCollection() {
        dataCollectionJob?.cancel() // Всегда отменяем предыдущую job

        Log.d("SSettingSensorsVM", "Starting data collection for ${UICoordinateProtectionConfiguration.NAME_COORDINATE_PROTECTION}")
        dataCollectionJob = viewModelScope.launch(Dispatchers.Default) {
            modbusRepository.getConfigurationFlow(UICoordinateProtectionConfiguration.NAME_COORDINATE_PROTECTION)
                .collect { cache ->
                    Log.d("CoordinateProtectionViewModule", "Updating from cache, size: ${cache.size}")
                    try {
                        // Создаем отфильтрованную карту только для нужных адресов
                        val filteredCache = addresses.associate { addr ->
                            addr to cache[addr]?.value
                        }
                        _state.value = createCoordinateProtectionState(filteredCache)

                    } catch (e: Exception) {
                        Log.e("CoordinateProtectionVM", "Data collection error", e)
                    }
                }
        }
    }

    // Приостановка сбора данных
    fun pauseDataCollection() {
        Log.d("CoordinateProtectionViewModel", "Pausing data collection")
        dataCollectionJob?.cancel()
        dataCollectionJob = null
    }
    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДАТЧИКИ ОТОБРАЖЕНИЕ ДАННЫХ И СОСТОЯНИЯ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // //Функция createCoordinateProtectionState() для обработки отфильтрованных данных:
    private fun createCoordinateProtectionState(cache: Map<Int, Int?>): CoordinateProtectionState {
        return CoordinateProtectionState(
            //Параметры крана
            currentSlewPosition = safeDivideTens(cache[158] ?: 0),
            currentTrolleyPosition = safeDivideTens(cache[155] ?: 0),
            currentCranePosition = safeDivideTens(cache[162] ?: 0),
            //Данные состояний настройки полигонов
            currentPosProtEn    = cache[610] ?: 0,
            currentModePosProt  = cache[611] ?: 0,

            currentP1Up     = cache[612] ?: 0,
            currentP1Down   = cache[613] ?: 0,
            currentP1Height    = safeDivideTens(cache[614] ?: 0),
            currentP1PointNum = cache[615] ?: 0,

            currentP2Up     = cache[616] ?: 0,
            currentP2Down   = cache[617] ?: 0,
            currentP2Height    = safeDivideTens(cache[618] ?: 0),
            currentP2PointNum = cache[619] ?: 0,

            currentP3Up     = cache[620] ?: 0,
            currentP3Down   = cache[621] ?: 0,
            currentP3Height    = safeDivideTens(cache[622] ?: 0),
            currentP3PointNum = cache[623] ?: 0,

            currentP4Up     = cache[624] ?: 0,
            currentP4Down   = cache[625] ?: 0,
            currentP4Height    = safeDivideTens(cache[626] ?: 0),
            currentP4PointNum = cache[627] ?: 0,

            currentPol12EnStatus = cache[628] ?: 0,
            currentPol34EnStatus = cache[629] ?: 0,

            //Статус теста ограничений подъема
            Polygon1PointY00 = safeDivideTens(cache[430] ?: 0),
            Polygon1PointY01 = safeDivideTens(cache[431] ?: 0),
            Polygon1PointY02 = safeDivideTens(cache[432] ?: 0),
            Polygon1PointY03 = safeDivideTens(cache[433] ?: 0),
            Polygon1PointY04 = safeDivideTens(cache[434] ?: 0),
            Polygon1PointY05 = safeDivideTens(cache[435] ?: 0),
            Polygon1PointY06 = safeDivideTens(cache[436] ?: 0),
            Polygon1PointY07 = safeDivideTens(cache[437] ?: 0),
            Polygon1PointY08 = safeDivideTens(cache[438] ?: 0),
            Polygon1PointY09 = safeDivideTens(cache[439] ?: 0),
            Polygon1PointY10 = safeDivideTens(cache[440] ?: 0),
            Polygon1PointY11 = safeDivideTens(cache[441] ?: 0),
            Polygon1PointY12 = safeDivideTens(cache[442] ?: 0),
            Polygon1PointY13 = safeDivideTens(cache[443] ?: 0),
            Polygon1PointY14 = safeDivideTens(cache[444] ?: 0),
            Polygon1PointY15 = safeDivideTens(cache[445] ?: 0),
            Polygon1PointY16 = safeDivideTens(cache[446] ?: 0),
            Polygon1PointY17 = safeDivideTens(cache[447] ?: 0),
            Polygon1PointY18 = safeDivideTens(cache[448] ?: 0),
            Polygon1PointY19 = safeDivideTens(cache[449] ?: 0),

            Polygon1PointX00 = safeDivideTens(cache[450] ?: 0),
            Polygon1PointX01 = safeDivideTens(cache[451] ?: 0),
            Polygon1PointX02 = safeDivideTens(cache[452] ?: 0),
            Polygon1PointX03 = safeDivideTens(cache[453] ?: 0),
            Polygon1PointX04 = safeDivideTens(cache[454] ?: 0),
            Polygon1PointX05 = safeDivideTens(cache[455] ?: 0),
            Polygon1PointX06 = safeDivideTens(cache[456] ?: 0),
            Polygon1PointX07 = safeDivideTens(cache[457] ?: 0),
            Polygon1PointX08 = safeDivideTens(cache[458] ?: 0),
            Polygon1PointX09 = safeDivideTens(cache[459] ?: 0),
            Polygon1PointX10 = safeDivideTens(cache[460] ?: 0),
            Polygon1PointX11 = safeDivideTens(cache[461] ?: 0),
            Polygon1PointX12 = safeDivideTens(cache[462] ?: 0),
            Polygon1PointX13 = safeDivideTens(cache[463] ?: 0),
            Polygon1PointX14 = safeDivideTens(cache[464] ?: 0),
            Polygon1PointX15 = safeDivideTens(cache[465] ?: 0),
            Polygon1PointX16 = safeDivideTens(cache[466] ?: 0),
            Polygon1PointX17 = safeDivideTens(cache[467] ?: 0),
            Polygon1PointX18 = safeDivideTens(cache[468] ?: 0),
            Polygon1PointX19 = safeDivideTens(cache[469] ?: 0),
        )
    }


    private fun safeDivide(value: Int?): Float {
        return (value ?: 0) / 1f
    }

    private fun safeDivideTens(value: Int?): Float {
        return (value ?: 0) / 10f
    }

    data class CoordinateProtectionState(
        //ПАРАМЕТРЫ КРАНА
        val currentTrolleyPosition: Float = 0f,
        val currentSlewPosition: Float = 0f,
        val currentCranePosition: Float = 0f,
        //Данные состояний настройки полигонов
        val currentPosProtEn: Int = 0,
        val currentModePosProt: Int = 0,


        val currentP1Up: Int = 0,
        val currentP1Down: Int = 0,
        val currentP1Height: Float = 0f,
        val currentP1PointNum: Int = 0,

        val currentP2Up: Int = 0,
        val currentP2Down: Int = 0,
        val currentP2Height: Float = 0f,
        val currentP2PointNum: Int = 0,

        val currentP3Up: Int = 0,
        val currentP3Down: Int = 0,
        val currentP3Height: Float = 0f,
        val currentP3PointNum: Int = 0,

        val currentP4Up: Int = 0,
        val currentP4Down: Int = 0,
        val currentP4Height: Float = 0f,
        val currentP4PointNum: Int = 0,

        val currentPol12EnStatus: Int = 0,
        val currentPol34EnStatus: Int = 0,
        //ПЕРЕМЕННЫЕ КООРДИНАТЫ ТОЧЕК ПОЛИГОНА №1
        val Polygon1PointY00: Float = 0f,
        val Polygon1PointY01: Float = 0f,
        val Polygon1PointY02: Float = 0f,
        val Polygon1PointY03: Float = 0f,
        val Polygon1PointY04: Float = 0f,
        val Polygon1PointY05: Float = 0f,
        val Polygon1PointY06: Float = 0f,
        val Polygon1PointY07: Float = 0f,
        val Polygon1PointY08: Float = 0f,
        val Polygon1PointY09: Float = 0f,
        val Polygon1PointY10: Float = 0f,
        val Polygon1PointY11: Float = 0f,
        val Polygon1PointY12: Float = 0f,
        val Polygon1PointY13: Float = 0f,
        val Polygon1PointY14: Float = 0f,
        val Polygon1PointY15: Float = 0f,
        val Polygon1PointY16: Float = 0f,
        val Polygon1PointY17: Float = 0f,
        val Polygon1PointY18: Float = 0f,
        val Polygon1PointY19: Float = 0f,

        val Polygon1PointX00: Float = 0f,
        val Polygon1PointX01: Float = 0f,
        val Polygon1PointX02: Float = 0f,
        val Polygon1PointX03: Float = 0f,
        val Polygon1PointX04: Float = 0f,
        val Polygon1PointX05: Float = 0f,
        val Polygon1PointX06: Float = 0f,
        val Polygon1PointX07: Float = 0f,
        val Polygon1PointX08: Float = 0f,
        val Polygon1PointX09: Float = 0f,
        val Polygon1PointX10: Float = 0f,
        val Polygon1PointX11: Float = 0f,
        val Polygon1PointX12: Float = 0f,
        val Polygon1PointX13: Float = 0f,
        val Polygon1PointX14: Float = 0f,
        val Polygon1PointX15: Float = 0f,
        val Polygon1PointX16: Float = 0f,
        val Polygon1PointX17: Float = 0f,
        val Polygon1PointX18: Float = 0f,
        val Polygon1PointX19: Float = 0f,
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
    !!!!!!!!!!!!!!!!!!!!!!КООРДИНАТНАЯ ЗАЩИТА - НАСТРОЙКА!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // Настройка. Защита Вкл.Выкл.
    fun onSetPosProtEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(891, 1)
                delay(200)
                modbusRepository.writeRegister(891, 0)
                _errorMessage.value = null
                _logMessage.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessage.value = "Запись не удалась: ${e.message}"
                _logMessage.value = null
            }
        }
    }

    // Настройка. Режим защиты (Груз/Стрела) 0-защита груза; 1-защита стрелы
    fun onSetModePosProt() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(892, 1)
                delay(200)
                modbusRepository.writeRegister(892, 0)
                _errorMessage.value = null
                _logMessage.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessage.value = "Запись не удалась: ${e.message}"
                _logMessage.value = null
            }
        }
    }




    /**
    !!!!!!!!!!!!!!!!!!!!!!ПОЛИГОН №1. - НАСТРОЙКА!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // Настройка. ВКЛЮЧИТЬ/ВЫКЛЮЧИТЬ ПОЛИГОН №1
    fun onSetP1En() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(870, 1)
                delay(200)
                modbusRepository.writeRegister(870, 0)
                _errorMessagePolygon1.value = null
                _logMessagePolygon1.value = "АКТИВИРОВАН"
            } catch (e: Exception) {
                _errorMessagePolygon1.value = "Активировать не удалась: ${e.message}"
                _logMessagePolygon1.value = null
            }
        }
    }
    // Настройка. Разрешить работу вверху полигона №1
    fun onSetP1Up() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(871, 1)
                delay(200)
                modbusRepository.writeRegister(871, 0)
                _errorMessagePolygon1.value = null
                _logMessagePolygon1.value = "РАБОТА ВВЕРХУ"
            } catch (e: Exception) {
                _errorMessagePolygon1.value = "Включить не удалось: ${e.message}"
                _logMessagePolygon1.value = null
            }
        }
    }
    // Настройка. Разрешить работу внизу полигона №1
    fun onSetP1Down() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(872, 1)
                delay(200)
                modbusRepository.writeRegister(872, 0)
                _errorMessagePolygon1.value = null
                _logMessagePolygon1.value = "РАБОТА ВНИЗУ"
            } catch (e: Exception) {
                _errorMessagePolygon1.value = "Включить не удалось: ${e.message}"
                _logMessagePolygon1.value = null
            }
        }
    }
    // Настройка. Ввод количество точек полигона №1
    fun onSetValueP1PointNum() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValuePolygon1PointNum.value, 0, 20 )
                // Запись в регистры
                modbusRepository.writeRegister(806, model)
                _errorMessagePolygon1.value = null
                _logMessagePolygon1.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon1.value = null
                _errorMessagePolygon1.value = "Запись не удалась: ${e.message}"
            }
        }
    }
    // Настройка. Ввод высота полигона №1
    fun onSetValueP1Height() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon1Height.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(873, model)
                _errorMessagePolygon1.value = null
                _logMessagePolygon1.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon1.value = null
                _errorMessagePolygon1.value = "Запись не удалась: ${e.message}"
            }
        }
    }
    // Настройка. Выбор режима задания точек полигона №1. По координатам
    fun onSetP1Type1() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(902, 1)
                delay(200)
                modbusRepository.writeRegister(902, 0)
                _errorMessagePolygon1.value = null
                _logMessagePolygon1.value = "По координатам"
            } catch (e: Exception) {
                _errorMessagePolygon1.value = "Не удалось: ${e.message}"
                _logMessagePolygon1.value = null
            }
        }
    }
    // Настройка. Выбор режима задания точек полигона №1. Ручной ввод
    fun onSetP1Type2() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(903, 1)
                delay(200)
                modbusRepository.writeRegister(903, 0)
                _errorMessagePolygon1.value = null
                _logMessagePolygon1.value = "Ручной ввод"
            } catch (e: Exception) {
                _errorMessagePolygon1.value = "Не удалось: ${e.message}"
                _logMessagePolygon1.value = null
            }
        }
    }
    /**
    !!!!!!!!!!!!!!!!!!!!!!ПОЛИГОН №1. - ЗАДЕНИЕ ТОЧЕК ПО КРЮКУ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */






    /**
    !!!!!!!!!!!!!!!!!!!!!!ПОЛИГОН №2. - НАСТРОЙКА!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // Настройка. ВКЛЮЧИТЬ/ВЫКЛЮЧИТЬ ПОЛИГОН №2
    fun onSetP2En() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(875, 1)
                delay(200)
                modbusRepository.writeRegister(875, 0)
                _errorMessagePolygon2.value = null
                _logMessagePolygon2.value = "АКТИВИРОВАН"
            } catch (e: Exception) {
                _errorMessagePolygon2.value = "Активировать не удалась: ${e.message}"
                _logMessagePolygon2.value = null
            }
        }
    }
    // Настройка. Разрешить работу вверху полигона №1
    fun onSetP2Up() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(876, 1)
                delay(200)
                modbusRepository.writeRegister(876, 0)
                _errorMessagePolygon2.value = null
                _logMessagePolygon2.value = "РАБОТА ВВЕРХУ"
            } catch (e: Exception) {
                _errorMessagePolygon2.value = "Включить не удалось: ${e.message}"
                _logMessagePolygon2.value = null
            }
        }
    }
    // Настройка. Разрешить работу внизу полигона №1
    fun onSetP2Down() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(877, 1)
                delay(200)
                modbusRepository.writeRegister(877, 0)
                _errorMessagePolygon2.value = null
                _logMessagePolygon2.value = "РАБОТА ВНИЗУ"
            } catch (e: Exception) {
                _errorMessagePolygon2.value = "Включить не удалось: ${e.message}"
                _logMessagePolygon2.value = null
            }
        }
    }
    // Настройка. Ввод количество точек полигона №2
    fun onSetValueP2PointNum() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValuePolygon2PointNum.value, 0, 20 )
                // Запись в регистры
                modbusRepository.writeRegister(879, model)
                _errorMessagePolygon2.value = null
                _logMessagePolygon2.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon2.value = null
                _errorMessagePolygon2.value = "Запись не удалась: ${e.message}"
            }
        }
    }
    // Настройка. Ввод высота полигона №2
    fun onSetValueP2Height() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon2Height.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(878, model)
                _errorMessagePolygon2.value = null
                _logMessagePolygon2.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon2.value = null
                _errorMessagePolygon2.value = "Запись не удалась: ${e.message}"
            }
        }
    }
    // Настройка. Выбор режима задания точек полигона №2. По координатам
    fun onSetP2Type1() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(912, 1)
                delay(200)
                modbusRepository.writeRegister(912, 0)
                _errorMessagePolygon2.value = null
                _logMessagePolygon2.value = "По координатам"
            } catch (e: Exception) {
                _errorMessagePolygon2.value = "Не удалось: ${e.message}"
                _logMessagePolygon2.value = null
            }
        }
    }
    // Настройка. Выбор режима задания точек полигона №2. Ручной ввод
    fun onSetP2Type2() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(913, 1)
                delay(200)
                modbusRepository.writeRegister(913, 0)
                _errorMessagePolygon1.value = null
                _logMessagePolygon1.value = "Ручной ввод"
            } catch (e: Exception) {
                _errorMessagePolygon1.value = "Не удалось: ${e.message}"
                _logMessagePolygon1.value = null
            }
        }
    }







}