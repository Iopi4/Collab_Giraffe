package com.example.giraffenew.coordinate_protection.prezentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.entities.UICoordinateProtectionConfiguration
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

    private val _errorMessagePolygon1 = mutableStateOf<String?>(null)
    val errorMessagePolygon1: State<String?> = _errorMessagePolygon1

    private val _logMessagePolygon1 = mutableStateOf<String?>(null)
    val logMessagePolygon1: State<String?> = _logMessagePolygon1

    private val _errorMessagePolygon1Manual = mutableStateOf<String?>(null)
    val errorMessagePolygon1Manual: State<String?> = _errorMessagePolygon1Manual
    private val _logMessagePolygon1Manual = mutableStateOf<String?>(null)
    val logMessagePolygon1Manual: State<String?> = _logMessagePolygon1Manual

    private val _logMessagePolygon1Coordinate = mutableStateOf<String?>(null)
    val logMessagePolygon1Coordinate: State<String?> = _logMessagePolygon1Coordinate

    private val _errorMessagePolygon1Coordinate = mutableStateOf<String?>(null)
    val errorMessagePolygon1Coordinate: State<String?> = _errorMessagePolygon1Coordinate

    private val _logMessagePolygon1Reset = mutableStateOf<String?>(null)
    val logMessagePolygon1Reset: State<String?> = _logMessagePolygon1Reset

    private val _errorMessagePolygon2 = mutableStateOf<String?>(null)
    val errorMessagePolygon2: State<String?> = _errorMessagePolygon2

    private val _logMessagePolygon2 = mutableStateOf<String?>(null)
    val logMessagePolygon2: State<String?> = _logMessagePolygon2

    private val _errorMessagePolygon2Manual = mutableStateOf<String?>(null)
    val errorMessagePolygon2Manual: State<String?> = _errorMessagePolygon2Manual
    private val _logMessagePolygon2Manual = mutableStateOf<String?>(null)
    val logMessagePolygon2Manual: State<String?> = _logMessagePolygon2Manual

    private val _logMessagePolygon2Coordinate = mutableStateOf<String?>(null)
    val logMessagePolygon2Coordinate: State<String?> = _logMessagePolygon2Coordinate

    private val _errorMessagePolygon2Coordinate = mutableStateOf<String?>(null)
    val errorMessagePolygon2Coordinate: State<String?> = _errorMessagePolygon2Coordinate

    private val _logMessagePolygon2Reset = mutableStateOf<String?>(null)
    val logMessagePolygon2Reset: State<String?> = _logMessagePolygon2Reset


    // Регистры для считывания. Те на которые подписываемся
    private val addresses = listOf(
        610, 611, 612, 613, 614, 615, 616, 617, 618, 619, 620, 621, 622, 623, 624, 625, 626, 627, 628, 629,
        630, 631, 632, 633, 634, 635, 636, 637, 638, 639, 640, 155, 158, 162,
        1000, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010, 1011, 1012, 1013, 1014, 1015, 1016, 1017, 1018, 1019,
        1020, 1021, 1022, 1023, 1024, 1025, 1026, 1027, 1028, 1029, 1030, 1031, 1032, 1033, 1034, 1035, 1036, 1037, 1038, 1039,
        1040, 1041, 1042, 1043, 1044, 1045, 1046, 1047, 1048, 1049, 1050, 1051, 1052, 1053, 1054, 1055, 1056, 1057, 1058, 1059,
        1060, 1061, 1062, 1063, 1064, 1065, 1066, 1067, 1068, 1069, 1070, 1071, 1072, 1073, 1074, 1075, 1076, 1077, 1078, 1079
    )

    private val _state = mutableStateOf(CoordinateProtectionState())
    val state: State<CoordinateProtectionState> = _state

    private var dataCollectionJob: Job? = null

    fun setActiveConfiguration() {
        modbusRepository.setActiveUiConfiguration(UICoordinateProtectionConfiguration.NAME_COORDINATE_PROTECTION)
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
            currentP1PointType = cache[630] ?: 0,

            currentP2Up     = cache[616] ?: 0,
            currentP2Down   = cache[617] ?: 0,
            currentP2Height    = safeDivideTens(cache[618] ?: 0),
            currentP2PointNum = cache[619] ?: 0,
            currentP2PointType = cache[631] ?: 0,

            currentP3Up     = cache[620] ?: 0,
            currentP3Down   = cache[621] ?: 0,
            currentP3Height    = safeDivideTens(cache[622] ?: 0),
            currentP3PointNum = cache[623] ?: 0,
            currentP3PointType = cache[632] ?: 0,

            currentP4Up     = cache[624] ?: 0,
            currentP4Down   = cache[625] ?: 0,
            currentP4Height    = safeDivideTens(cache[626] ?: 0),
            currentP4PointNum = cache[627] ?: 0,
            currentP4PointType = cache[633] ?: 0,

            currentP1FewPoint       = cache[628] ?: 0,
            currentP1ActiveStatus    = cache[629] ?: 0,
            currentP2FewPoint       = cache[634] ?: 0,
            currentP2ActiveStatus    = cache[335] ?: 0,
            currentP3FewPoint       = cache[636] ?: 0,
            currentP3ActiveStatus    = cache[637] ?: 0,
            currentP4FewPoint       = cache[638] ?: 0,
            currentP4ActiveStatus     = cache[639] ?: 0,
            currentStatsCalibration = cache[640] ?: 0,

            //Координаты точек. ПОЛИГОН №1
            Polygon1PointX01 = safeDivideTens(cache[1000] ?: 0),
            Polygon1PointX02 = safeDivideTens(cache[1002] ?: 0),
            Polygon1PointX03 = safeDivideTens(cache[1004] ?: 0),
            Polygon1PointX04 = safeDivideTens(cache[1006] ?: 0),
            Polygon1PointX05 = safeDivideTens(cache[1008] ?: 0),
            Polygon1PointX06 = safeDivideTens(cache[1010] ?: 0),
            Polygon1PointX07 = safeDivideTens(cache[1012] ?: 0),
            Polygon1PointX08 = safeDivideTens(cache[1014] ?: 0),
            Polygon1PointX09 = safeDivideTens(cache[1016] ?: 0),
            Polygon1PointX10 = safeDivideTens(cache[1018] ?: 0),
            Polygon1PointX11 = safeDivideTens(cache[1020] ?: 0),
            Polygon1PointX12 = safeDivideTens(cache[1022] ?: 0),
            Polygon1PointX13 = safeDivideTens(cache[1024] ?: 0),
            Polygon1PointX14 = safeDivideTens(cache[1026] ?: 0),
            Polygon1PointX15 = safeDivideTens(cache[1028] ?: 0),
            Polygon1PointX16 = safeDivideTens(cache[1030] ?: 0),
            Polygon1PointX17 = safeDivideTens(cache[1032] ?: 0),
            Polygon1PointX18 = safeDivideTens(cache[1034] ?: 0),
            Polygon1PointX19 = safeDivideTens(cache[1036] ?: 0),
            Polygon1PointX20 = safeDivideTens(cache[1038] ?: 0),

            Polygon1PointY01 = safeDivideTens(cache[1001] ?: 0),
            Polygon1PointY02 = safeDivideTens(cache[1003] ?: 0),
            Polygon1PointY03 = safeDivideTens(cache[1005] ?: 0),
            Polygon1PointY04 = safeDivideTens(cache[1007] ?: 0),
            Polygon1PointY05 = safeDivideTens(cache[1009] ?: 0),
            Polygon1PointY06 = safeDivideTens(cache[1011] ?: 0),
            Polygon1PointY07 = safeDivideTens(cache[1013] ?: 0),
            Polygon1PointY08 = safeDivideTens(cache[1015] ?: 0),
            Polygon1PointY09 = safeDivideTens(cache[1017] ?: 0),
            Polygon1PointY10 = safeDivideTens(cache[1019] ?: 0),
            Polygon1PointY11 = safeDivideTens(cache[1021] ?: 0),
            Polygon1PointY12 = safeDivideTens(cache[1023] ?: 0),
            Polygon1PointY13 = safeDivideTens(cache[1025] ?: 0),
            Polygon1PointY14 = safeDivideTens(cache[1027] ?: 0),
            Polygon1PointY15 = safeDivideTens(cache[1029] ?: 0),
            Polygon1PointY16 = safeDivideTens(cache[1031] ?: 0),
            Polygon1PointY17 = safeDivideTens(cache[1033] ?: 0),
            Polygon1PointY18 = safeDivideTens(cache[1035] ?: 0),
            Polygon1PointY19 = safeDivideTens(cache[1037] ?: 0),
            Polygon1PointY20 = safeDivideTens(cache[1039] ?: 0),

            //Координаты точек. ПОЛИГОН №1
            Polygon2PointX01 = safeDivideTens(cache[1040] ?: 0),
            Polygon2PointX02 = safeDivideTens(cache[1042] ?: 0),
            Polygon2PointX03 = safeDivideTens(cache[1044] ?: 0),
            Polygon2PointX04 = safeDivideTens(cache[1046] ?: 0),
            Polygon2PointX05 = safeDivideTens(cache[1048] ?: 0),
            Polygon2PointX06 = safeDivideTens(cache[1050] ?: 0),
            Polygon2PointX07 = safeDivideTens(cache[1052] ?: 0),
            Polygon2PointX08 = safeDivideTens(cache[1054] ?: 0),
            Polygon2PointX09 = safeDivideTens(cache[1056] ?: 0),
            Polygon2PointX10 = safeDivideTens(cache[1058] ?: 0),
            Polygon2PointX11 = safeDivideTens(cache[1060] ?: 0),
            Polygon2PointX12 = safeDivideTens(cache[1062] ?: 0),
            Polygon2PointX13 = safeDivideTens(cache[1064] ?: 0),
            Polygon2PointX14 = safeDivideTens(cache[1066] ?: 0),
            Polygon2PointX15 = safeDivideTens(cache[1068] ?: 0),
            Polygon2PointX16 = safeDivideTens(cache[1070] ?: 0),
            Polygon2PointX17 = safeDivideTens(cache[1072] ?: 0),
            Polygon2PointX18 = safeDivideTens(cache[1074] ?: 0),
            Polygon2PointX19 = safeDivideTens(cache[1076] ?: 0),
            Polygon2PointX20 = safeDivideTens(cache[1078] ?: 0),

            Polygon2PointY01 = safeDivideTens(cache[1041] ?: 0),
            Polygon2PointY02 = safeDivideTens(cache[1043] ?: 0),
            Polygon2PointY03 = safeDivideTens(cache[1045] ?: 0),
            Polygon2PointY04 = safeDivideTens(cache[1047] ?: 0),
            Polygon2PointY05 = safeDivideTens(cache[1049] ?: 0),
            Polygon2PointY06 = safeDivideTens(cache[1051] ?: 0),
            Polygon2PointY07 = safeDivideTens(cache[1053] ?: 0),
            Polygon2PointY08 = safeDivideTens(cache[1055] ?: 0),
            Polygon2PointY09 = safeDivideTens(cache[1057] ?: 0),
            Polygon2PointY10 = safeDivideTens(cache[1059] ?: 0),
            Polygon2PointY11 = safeDivideTens(cache[1061] ?: 0),
            Polygon2PointY12 = safeDivideTens(cache[1063] ?: 0),
            Polygon2PointY13 = safeDivideTens(cache[1065] ?: 0),
            Polygon2PointY14 = safeDivideTens(cache[1067] ?: 0),
            Polygon2PointY15 = safeDivideTens(cache[1069] ?: 0),
            Polygon2PointY16 = safeDivideTens(cache[1071] ?: 0),
            Polygon2PointY17 = safeDivideTens(cache[1073] ?: 0),
            Polygon2PointY18 = safeDivideTens(cache[1075] ?: 0),
            Polygon2PointY19 = safeDivideTens(cache[1077] ?: 0),
            Polygon2PointY20 = safeDivideTens(cache[1079] ?: 0),
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
        val currentP1PointType: Int = 0,

        val currentP2Up: Int = 0,
        val currentP2Down: Int = 0,
        val currentP2Height: Float = 0f,
        val currentP2PointNum: Int = 0,
        val currentP2PointType: Int = 0,

        val currentP3Up: Int = 0,
        val currentP3Down: Int = 0,
        val currentP3Height: Float = 0f,
        val currentP3PointNum: Int = 0,
        val currentP3PointType: Int = 0,

        val currentP4Up: Int = 0,
        val currentP4Down: Int = 0,
        val currentP4Height: Float = 0f,
        val currentP4PointNum: Int = 0,
        val currentP4PointType: Int = 0,

        val currentP1FewPoint: Int = 0,
        val currentP1ActiveStatus: Int = 0,
        val currentP2FewPoint: Int = 0,
        val currentP2ActiveStatus: Int = 0,
        val currentP3FewPoint: Int = 0,
        val currentP3ActiveStatus: Int = 0,
        val currentP4FewPoint: Int = 0,
        val currentP4ActiveStatus: Int = 0,
        val currentStatsCalibration: Int = 0,



        //ПЕРЕМЕННЫЕ КООРДИНАТЫ ТОЧЕК ПОЛИГОНА №1
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
        val Polygon1PointX20: Float = 0f,

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
        val Polygon1PointY20: Float = 0f,

        //ПЕРЕМЕННЫЕ КООРДИНАТЫ ТОЧЕК ПОЛИГОНА №2
        val Polygon2PointX01: Float = 0f,
        val Polygon2PointX02: Float = 0f,
        val Polygon2PointX03: Float = 0f,
        val Polygon2PointX04: Float = 0f,
        val Polygon2PointX05: Float = 0f,
        val Polygon2PointX06: Float = 0f,
        val Polygon2PointX07: Float = 0f,
        val Polygon2PointX08: Float = 0f,
        val Polygon2PointX09: Float = 0f,
        val Polygon2PointX10: Float = 0f,
        val Polygon2PointX11: Float = 0f,
        val Polygon2PointX12: Float = 0f,
        val Polygon2PointX13: Float = 0f,
        val Polygon2PointX14: Float = 0f,
        val Polygon2PointX15: Float = 0f,
        val Polygon2PointX16: Float = 0f,
        val Polygon2PointX17: Float = 0f,
        val Polygon2PointX18: Float = 0f,
        val Polygon2PointX19: Float = 0f,
        val Polygon2PointX20: Float = 0f,

        val Polygon2PointY01: Float = 0f,
        val Polygon2PointY02: Float = 0f,
        val Polygon2PointY03: Float = 0f,
        val Polygon2PointY04: Float = 0f,
        val Polygon2PointY05: Float = 0f,
        val Polygon2PointY06: Float = 0f,
        val Polygon2PointY07: Float = 0f,
        val Polygon2PointY08: Float = 0f,
        val Polygon2PointY09: Float = 0f,
        val Polygon2PointY10: Float = 0f,
        val Polygon2PointY11: Float = 0f,
        val Polygon2PointY12: Float = 0f,
        val Polygon2PointY13: Float = 0f,
        val Polygon2PointY14: Float = 0f,
        val Polygon2PointY15: Float = 0f,
        val Polygon2PointY16: Float = 0f,
        val Polygon2PointY17: Float = 0f,
        val Polygon2PointY18: Float = 0f,
        val Polygon2PointY19: Float = 0f,
        val Polygon2PointY20: Float = 0f,
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
            } catch (e: Exception) {
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
            } catch (e: Exception) { }
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
            } catch (e: Exception) { }
        }
    }
    // Настройка. Разрешить работу вверху полигона №1
    fun onSetP1UpEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(871, 1)
                delay(200)
                modbusRepository.writeRegister(871, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Разрешить работу внизу полигона №1
    fun onSetP1DownEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(872, 1)
                delay(200)
                modbusRepository.writeRegister(872, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Ввод количество точек полигона №1
    fun onSetValueP1PointNum() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValuePolygon1PointNum.value, 0, 20 )
                // Запись в регистры
                modbusRepository.writeRegister(874, model)
                _errorMessagePolygon1.value = null
                _logMessagePolygon1.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon1.value = null
                _errorMessagePolygon1.value = "${e.message}"
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
                _errorMessagePolygon1.value = "${e.message}"
            }
        }
    }
    // Настройка. Выбор режима задания точек полигона №1. По координатам X - Y
    fun onSetP1Type1En() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(902, 1)
                delay(200)
                modbusRepository.writeRegister(902, 0)
            } catch (e: Exception) {
            }
        }
    }
    // Настройка. Выбор режима задания точек полигона №1. Ручной ввод
    fun onSetP1Type2En() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(903, 1)
                delay(200)
                modbusRepository.writeRegister(903, 0)
            } catch (e: Exception) {
            }
        }
    }
    // ПОЛИГОН №1. - КНОПКА ЗАПОМНИТЬ ТОЧКУ  с 1 по 20
    fun onSetP1PointCmd(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистр
                modbusRepository.writeRegister(901, index)
                delay(200)
                modbusRepository.writeRegister(901, 0)
            } catch (e: Exception) {
            }
        }
    }

    // ПОЛИГОН №1. Настройка точек. При типе ввода 2 (Вручную) - позиция телеги
    fun onSetValuePolygon1TrolleyPos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon1TrolleyPos.value, 0.0f, 100.0f )
                // Запись в регистры
                modbusRepository.writeRegister(906, model)
                _errorMessagePolygon1Manual.value = null
                _logMessagePolygon1Manual.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon1Manual.value = null
                _errorMessagePolygon1Manual.value = "${e.message}"
            }
        }
    }
    // ПОЛИГОН №1. Настройка точек. При типе ввода 2 (Вручную) - позиция поворота
    fun onSetValuePolygon1SlewPos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon1SlewPos.value, 0.0f, 720.0f )
                // Запись в регистры
                modbusRepository.writeRegister(907, model)
                _errorMessagePolygon1Manual.value = null
                _logMessagePolygon1Manual.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon1Manual.value = null
                _errorMessagePolygon1Manual.value = "${e.message}"
            }
        }
    }
    // ПОЛИГОН №1. Настройка точек. При типе ввода 2 (Вручную) - позиция хода крана
    fun onSetValuePolygon1CranePos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon1CranePos.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(908, model)
                _errorMessagePolygon1Manual.value = null
                _logMessagePolygon1Manual.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon1Manual.value = null
                _errorMessagePolygon1Manual.value = "${e.message}"
            }
        }
    }

    // ПОЛИГОН №1. Настройка точек. При типе ввода 1 (По Координатам) - позиция по оси Х
    fun onsetValuePolygon1PxPos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon1PxPos.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(904, model)
                _errorMessagePolygon1Coordinate.value = null
                _logMessagePolygon1Coordinate.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon1Coordinate.value = null
                _errorMessagePolygon1Coordinate.value = "${e.message}"
            }
        }
    }
    // ПОЛИГОН №1. Настройка точек. При типе ввода 1 (По Координатам) - позиция по оси Y
    fun onsetValuePolygon1PyPos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon1PyPos.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(905, model)
                _errorMessagePolygon1Coordinate.value = null
                _logMessagePolygon1Coordinate.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon1Coordinate.value = null
                _errorMessagePolygon1Coordinate.value = "${e.message}"
            }
        }
    }

    // НАСТРОЙКА. ПОЛИГОН №1.  СБРОСИТЬ ПЕРВЫЙ ПОЛИГОН
    fun startResetPolygon1() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(900, 1)
                delay(200)
                modbusRepository.writeRegister(900, 0)
            } catch (e: Exception) {
            }
        }
    }


    //ПОЛИГОН №1. Обновление для UI
    fun updateValuePolygon1Height(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon1Height.value = value } }

    fun updateValuePolygon1PointNum(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValuePolygon1PointNum.value = value } }

    fun updateValuePolygon1TrolleyPos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon1TrolleyPos.value = value } }

    fun updateValuePolygon1SlewPos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon1SlewPos.value = value } }

    fun updateValuePolygon1CranePos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon1CranePos.value = value } }

    fun updateValuePolygon1PxPos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon1PxPos.value = value } }

    fun updateValuePolygon1PyPos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon1PyPos.value = value } }




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
            } catch (e: Exception) { }
        }
    }
    // Настройка. Разрешить работу вверху полигона №2
    fun onSetP2UpEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(876, 1)
                delay(200)
                modbusRepository.writeRegister(876, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Разрешить работу внизу полигона №2
    fun onSetP2DownEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(877, 1)
                delay(200)
                modbusRepository.writeRegister(877, 0)
            } catch (e: Exception) { }
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
                _errorMessagePolygon2.value = "${e.message}"
            }
        }
    }
    // Настройка. Ввод высоты полигона №2
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
                _errorMessagePolygon2.value = "${e.message}"
            }
        }
    }
    // Настройка. Выбор режима задания точек полигона №2. По координатам X - Y
    fun onSetP2Type1En() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(912, 1)
                delay(200)
                modbusRepository.writeRegister(912, 0)
            } catch (e: Exception) {
            }
        }
    }
    // Настройка. Выбор режима задания точек полигона №2. Ручной ввод
    fun onSetP2Type2En() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(913, 1)
                delay(200)
                modbusRepository.writeRegister(913, 0)
            } catch (e: Exception) {
            }
        }
    }
    // ПОЛИГОН №1. - КНОПКА ЗАПОМНИТЬ ТОЧКУ  с 1 по 20
    fun onSetP2PointCmd(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистр
                modbusRepository.writeRegister(911, index)
                delay(200)
                modbusRepository.writeRegister(911, 0)
            } catch (e: Exception) {
            }
        }
    }

    // ПОЛИГОН №2. Настройка точек. При типе ввода 2 (Вручную) - позиция телеги
    fun onSetValuePolygon2TrolleyPos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon2TrolleyPos.value, 0.0f, 100.0f )
                // Запись в регистры
                modbusRepository.writeRegister(916, model)
                _errorMessagePolygon2Manual.value = null
                _logMessagePolygon2Manual.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon2Manual.value = null
                _errorMessagePolygon2Manual.value = "${e.message}"
            }
        }
    }
    // ПОЛИГОН №2. Настройка точек. При типе ввода 2 (Вручную) - позиция поворота
    fun onSetValuePolygon2SlewPos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon2SlewPos.value, 0.0f, 720.0f )
                // Запись в регистры
                modbusRepository.writeRegister(917, model)
                _errorMessagePolygon2Manual.value = null
                _logMessagePolygon2Manual.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon2Manual.value = null
                _errorMessagePolygon2Manual.value = "${e.message}"
            }
        }
    }
    // ПОЛИГОН №2. Настройка точек. При типе ввода 2 (Вручную) - позиция хода крана
    fun onSetValuePolygon2CranePos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon2CranePos.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(918, model)
                _errorMessagePolygon2Manual.value = null
                _logMessagePolygon2Manual.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon2Manual.value = null
                _errorMessagePolygon2Manual.value = "${e.message}"
            }
        }
    }

    // ПОЛИГОН №2. Настройка точек. При типе ввода 1 (По Координатам) - позиция по оси Х
    fun onsetValuePolygon2PxPos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon2PxPos.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(914, model)
                _errorMessagePolygon2Coordinate.value = null
                _logMessagePolygon2Coordinate.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon2Coordinate.value = null
                _errorMessagePolygon2Coordinate.value = "${e.message}"
            }
        }
    }
    // ПОЛИГОН №2. Настройка точек. При типе ввода 1 (По Координатам) - позиция по оси Y
    fun onsetValuePolygon2PyPos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon2PyPos.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(915, model)
                _errorMessagePolygon2Coordinate.value = null
                _logMessagePolygon2Coordinate.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon2Coordinate.value = null
                _errorMessagePolygon2Coordinate.value = "${e.message}"
            }
        }
    }

    // НАСТРОЙКА. ПОЛИГОН №2.  СБРОСИТЬ ВТОРОЙ ПОЛИГОН
    fun startResetPolygon2() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(910, 1)
                delay(200)
                modbusRepository.writeRegister(910, 0)
            } catch (e: Exception) {
            }
        }
    }

    //ПОЛИГОН №2. Обновление для UI
    fun updateValuePolygon2Height(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon2Height.value = value } }

    fun updateValuePolygon2PointNum(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValuePolygon2PointNum.value = value } }

    fun updateValuePolygon2TrolleyPos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon2TrolleyPos.value = value } }

    fun updateValuePolygon2SlewPos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon2SlewPos.value = value } }

    fun updateValuePolygon2CranePos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon2CranePos.value = value } }

    fun updateValuePolygon2PxPos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon2PxPos.value = value } }

    fun updateValuePolygon2PyPos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon2PyPos.value = value } }


    override fun onCleared() {
        super.onCleared()
        Log.d("CoordinateProtectionVM", "ViewModel cleared")
        modbusRepository.setActiveUiConfiguration(null)
    }
}