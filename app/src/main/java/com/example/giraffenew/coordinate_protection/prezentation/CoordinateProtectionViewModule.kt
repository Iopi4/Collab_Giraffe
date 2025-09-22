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

    //Состояние ошибок и записи данных
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

    //Состояние ошибок и записи данных
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


    //НАСТРОЙКА ПОЛИГОНА №3
    private val _setValuePolygon3Height = mutableStateOf("")
    val setValuePolygon3Height: State<String> = _setValuePolygon3Height

    private val _setValuePolygon3PointNum = mutableStateOf("")
    val setValuePolygon3PointNum: State<String> = _setValuePolygon3PointNum

    private val _setValuePolygon3TrolleyPos = mutableStateOf("")
    val setValuePolygon3TrolleyPos: State<String> = _setValuePolygon3TrolleyPos

    private val _setValuePolygon3SlewPos = mutableStateOf("")
    val setValuePolygon3SlewPos: State<String> = _setValuePolygon3SlewPos

    private val _setValuePolygon3CranePos = mutableStateOf("")
    val setValuePolygon3CranePos: State<String> = _setValuePolygon3CranePos

    private val _setValuePolygon3PxPos = mutableStateOf("")
    val setValuePolygon3PxPos: State<String> = _setValuePolygon3PxPos

    private val _setValuePolygon3PyPos = mutableStateOf("")
    val setValuePolygon3PyPos: State<String> = _setValuePolygon3PyPos

    //Состояние ошибок и записи данных
    private val _errorMessagePolygon3 = mutableStateOf<String?>(null)
    val errorMessagePolygon3: State<String?> = _errorMessagePolygon3

    private val _logMessagePolygon3 = mutableStateOf<String?>(null)
    val logMessagePolygon3: State<String?> = _logMessagePolygon3

    private val _errorMessagePolygon3Manual = mutableStateOf<String?>(null)
    val errorMessagePolygon3Manual: State<String?> = _errorMessagePolygon3Manual
    private val _logMessagePolygon3Manual = mutableStateOf<String?>(null)
    val logMessagePolygon3Manual: State<String?> = _logMessagePolygon3Manual

    private val _logMessagePolygon3Coordinate = mutableStateOf<String?>(null)
    val logMessagePolygon3Coordinate: State<String?> = _logMessagePolygon3Coordinate

    private val _errorMessagePolygon3Coordinate = mutableStateOf<String?>(null)
    val errorMessagePolygon3Coordinate: State<String?> = _errorMessagePolygon3Coordinate

    private val _logMessagePolygon3Reset = mutableStateOf<String?>(null)
    val logMessagePolygon3Reset: State<String?> = _logMessagePolygon3Reset


    //НАСТРОЙКА ПОЛИГОНА №4
    private val _setValuePolygon4Height = mutableStateOf("")
    val setValuePolygon4Height: State<String> = _setValuePolygon4Height

    private val _setValuePolygon4PointNum = mutableStateOf("")
    val setValuePolygon4PointNum: State<String> = _setValuePolygon4PointNum

    private val _setValuePolygon4TrolleyPos = mutableStateOf("")
    val setValuePolygon4TrolleyPos: State<String> = _setValuePolygon4TrolleyPos

    private val _setValuePolygon4SlewPos = mutableStateOf("")
    val setValuePolygon4SlewPos: State<String> = _setValuePolygon4SlewPos

    private val _setValuePolygon4CranePos = mutableStateOf("")
    val setValuePolygon4CranePos: State<String> = _setValuePolygon4CranePos

    private val _setValuePolygon4PxPos = mutableStateOf("")
    val setValuePolygon4PxPos: State<String> = _setValuePolygon4PxPos

    private val _setValuePolygon4PyPos = mutableStateOf("")
    val setValuePolygon4PyPos: State<String> = _setValuePolygon4PyPos

    //Состояние ошибок и записи данных
    private val _errorMessagePolygon4 = mutableStateOf<String?>(null)
    val errorMessagePolygon4: State<String?> = _errorMessagePolygon4

    private val _logMessagePolygon4 = mutableStateOf<String?>(null)
    val logMessagePolygon4: State<String?> = _logMessagePolygon4

    private val _errorMessagePolygon4Manual = mutableStateOf<String?>(null)
    val errorMessagePolygon4Manual: State<String?> = _errorMessagePolygon4Manual
    private val _logMessagePolygon4Manual = mutableStateOf<String?>(null)
    val logMessagePolygon4Manual: State<String?> = _logMessagePolygon4Manual

    private val _logMessagePolygon4Coordinate = mutableStateOf<String?>(null)
    val logMessagePolygon4Coordinate: State<String?> = _logMessagePolygon4Coordinate

    private val _errorMessagePolygon4Coordinate = mutableStateOf<String?>(null)
    val errorMessagePolygon4Coordinate: State<String?> = _errorMessagePolygon4Coordinate

    private val _logMessagePolygon4Reset = mutableStateOf<String?>(null)
    val logMessagePolygon4Reset: State<String?> = _logMessagePolygon4Reset


    // Регистры для считывания. Те на которые подписываемся
    private val addresses = listOf(
        610, 611, 612, 613, 614, 615, 616, 617, 618, 619, 620, 621, 622, 623, 624, 625, 626, 627, 628, 629,
        630, 631, 632, 633, 634, 635, 636, 637, 638, 639, 640, 155, 158, 162,
        1000, 1001, 1002, 1003, 1004, 1005, 1006, 1007, 1008, 1009, 1010, 1011, 1012, 1013, 1014, 1015, 1016, 1017, 1018, 1019,
        1020, 1021, 1022, 1023, 1024, 1025, 1026, 1027, 1028, 1029, 1030, 1031, 1032, 1033, 1034, 1035, 1036, 1037, 1038, 1039,
        1040, 1041, 1042, 1043, 1044, 1045, 1046, 1047, 1048, 1049, 1050, 1051, 1052, 1053, 1054, 1055, 1056, 1057, 1058, 1059,
        1060, 1061, 1062, 1063, 1064, 1065, 1066, 1067, 1068, 1069, 1070, 1071, 1072, 1073, 1074, 1075, 1076, 1077, 1078, 1079,
        1080, 1081, 1082, 1083, 1084, 1085, 1086, 1087, 1088, 1089, 1090, 1091, 1092, 1093, 1094, 1095, 1096, 1097, 1098, 1099,
        1100, 1101, 1102, 1103, 1104, 1105, 1106, 1107, 1108, 1109, 1110, 1111, 1112, 1113, 1114, 1115, 1116, 1117, 1118, 1119,
        1120, 1121, 1122, 1123, 1124, 1125, 1126, 1127, 1128, 1129, 1130, 1131, 1132, 1133, 1134, 1135, 1136, 1137, 1138, 1139,
        1140, 1141, 1142, 1143, 1144, 1145, 1146, 1147, 1148, 1149, 1150, 1151, 1152, 1153, 1154, 1155, 1156, 1157, 1158, 1159

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
            polygonState = PolygonState(
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
                currentP2ActiveStatus    = cache[635] ?: 0,
                currentP3FewPoint       = cache[636] ?: 0,
                currentP3ActiveStatus    = cache[637] ?: 0,
                currentP4FewPoint       = cache[638] ?: 0,
                currentP4ActiveStatus     = cache[639] ?: 0,
                currentStatsCalibration = cache[640] ?: 0,
            ),
            polygon1Point = Polygon1Point(
                //Координаты точек. ПОЛИГОН №1
                polygon1PointX01 = safeDivideTens(cache[1000] ?: 0),
                polygon1PointX02 = safeDivideTens(cache[1002] ?: 0),
                polygon1PointX03 = safeDivideTens(cache[1004] ?: 0),
                polygon1PointX04 = safeDivideTens(cache[1006] ?: 0),
                polygon1PointX05 = safeDivideTens(cache[1008] ?: 0),
                polygon1PointX06 = safeDivideTens(cache[1010] ?: 0),
                polygon1PointX07 = safeDivideTens(cache[1012] ?: 0),
                polygon1PointX08 = safeDivideTens(cache[1014] ?: 0),
                polygon1PointX09 = safeDivideTens(cache[1016] ?: 0),
                polygon1PointX10 = safeDivideTens(cache[1018] ?: 0),
                polygon1PointX11 = safeDivideTens(cache[1020] ?: 0),
                polygon1PointX12 = safeDivideTens(cache[1022] ?: 0),
                polygon1PointX13 = safeDivideTens(cache[1024] ?: 0),
                polygon1PointX14 = safeDivideTens(cache[1026] ?: 0),
                polygon1PointX15 = safeDivideTens(cache[1028] ?: 0),
                polygon1PointX16 = safeDivideTens(cache[1030] ?: 0),
                polygon1PointX17 = safeDivideTens(cache[1032] ?: 0),
                polygon1PointX18 = safeDivideTens(cache[1034] ?: 0),
                polygon1PointX19 = safeDivideTens(cache[1036] ?: 0),
                polygon1PointX20 = safeDivideTens(cache[1038] ?: 0),

                polygon1PointY01 = safeDivideTens(cache[1001] ?: 0),
                polygon1PointY02 = safeDivideTens(cache[1003] ?: 0),
                polygon1PointY03 = safeDivideTens(cache[1005] ?: 0),
                polygon1PointY04 = safeDivideTens(cache[1007] ?: 0),
                polygon1PointY05 = safeDivideTens(cache[1009] ?: 0),
                polygon1PointY06 = safeDivideTens(cache[1011] ?: 0),
                polygon1PointY07 = safeDivideTens(cache[1013] ?: 0),
                polygon1PointY08 = safeDivideTens(cache[1015] ?: 0),
                polygon1PointY09 = safeDivideTens(cache[1017] ?: 0),
                polygon1PointY10 = safeDivideTens(cache[1019] ?: 0),
                polygon1PointY11 = safeDivideTens(cache[1021] ?: 0),
                polygon1PointY12 = safeDivideTens(cache[1023] ?: 0),
                polygon1PointY13 = safeDivideTens(cache[1025] ?: 0),
                polygon1PointY14 = safeDivideTens(cache[1027] ?: 0),
                polygon1PointY15 = safeDivideTens(cache[1029] ?: 0),
                polygon1PointY16 = safeDivideTens(cache[1031] ?: 0),
                polygon1PointY17 = safeDivideTens(cache[1033] ?: 0),
                polygon1PointY18 = safeDivideTens(cache[1035] ?: 0),
                polygon1PointY19 = safeDivideTens(cache[1037] ?: 0),
                polygon1PointY20 = safeDivideTens(cache[1039] ?: 0),
            ),
            polygon2Point = Polygon2Point(
                //Координаты точек. ПОЛИГОН №2
                polygon2PointX01 = safeDivideTens(cache[1040] ?: 0),
                polygon2PointX02 = safeDivideTens(cache[1042] ?: 0),
                polygon2PointX03 = safeDivideTens(cache[1044] ?: 0),
                polygon2PointX04 = safeDivideTens(cache[1046] ?: 0),
                polygon2PointX05 = safeDivideTens(cache[1048] ?: 0),
                polygon2PointX06 = safeDivideTens(cache[1050] ?: 0),
                polygon2PointX07 = safeDivideTens(cache[1052] ?: 0),
                polygon2PointX08 = safeDivideTens(cache[1054] ?: 0),
                polygon2PointX09 = safeDivideTens(cache[1056] ?: 0),
                polygon2PointX10 = safeDivideTens(cache[1058] ?: 0),
                polygon2PointX11 = safeDivideTens(cache[1060] ?: 0),
                polygon2PointX12 = safeDivideTens(cache[1062] ?: 0),
                polygon2PointX13 = safeDivideTens(cache[1064] ?: 0),
                polygon2PointX14 = safeDivideTens(cache[1066] ?: 0),
                polygon2PointX15 = safeDivideTens(cache[1068] ?: 0),
                polygon2PointX16 = safeDivideTens(cache[1070] ?: 0),
                polygon2PointX17 = safeDivideTens(cache[1072] ?: 0),
                polygon2PointX18 = safeDivideTens(cache[1074] ?: 0),
                polygon2PointX19 = safeDivideTens(cache[1076] ?: 0),
                polygon2PointX20 = safeDivideTens(cache[1078] ?: 0),

                polygon2PointY01 = safeDivideTens(cache[1041] ?: 0),
                polygon2PointY02 = safeDivideTens(cache[1043] ?: 0),
                polygon2PointY03 = safeDivideTens(cache[1045] ?: 0),
                polygon2PointY04 = safeDivideTens(cache[1047] ?: 0),
                polygon2PointY05 = safeDivideTens(cache[1049] ?: 0),
                polygon2PointY06 = safeDivideTens(cache[1051] ?: 0),
                polygon2PointY07 = safeDivideTens(cache[1053] ?: 0),
                polygon2PointY08 = safeDivideTens(cache[1055] ?: 0),
                polygon2PointY09 = safeDivideTens(cache[1057] ?: 0),
                polygon2PointY10 = safeDivideTens(cache[1059] ?: 0),
                polygon2PointY11 = safeDivideTens(cache[1061] ?: 0),
                polygon2PointY12 = safeDivideTens(cache[1063] ?: 0),
                polygon2PointY13 = safeDivideTens(cache[1065] ?: 0),
                polygon2PointY14 = safeDivideTens(cache[1067] ?: 0),
                polygon2PointY15 = safeDivideTens(cache[1069] ?: 0),
                polygon2PointY16 = safeDivideTens(cache[1071] ?: 0),
                polygon2PointY17 = safeDivideTens(cache[1073] ?: 0),
                polygon2PointY18 = safeDivideTens(cache[1075] ?: 0),
                polygon2PointY19 = safeDivideTens(cache[1077] ?: 0),
                polygon2PointY20 = safeDivideTens(cache[1079] ?: 0),
            ),
            polygon3Point = Polygon3Point(
                polygon3PointX01 = safeDivideTens(cache[1080] ?: 0),
                polygon3PointX02 = safeDivideTens(cache[1082] ?: 0),
                polygon3PointX03 = safeDivideTens(cache[1084] ?: 0),
                polygon3PointX04 = safeDivideTens(cache[1086] ?: 0),
                polygon3PointX05 = safeDivideTens(cache[1088] ?: 0),
                polygon3PointX06 = safeDivideTens(cache[1090] ?: 0),
                polygon3PointX07 = safeDivideTens(cache[1092] ?: 0),
                polygon3PointX08 = safeDivideTens(cache[1094] ?: 0),
                polygon3PointX09 = safeDivideTens(cache[1096] ?: 0),
                polygon3PointX10 = safeDivideTens(cache[1098] ?: 0),
                polygon3PointX11 = safeDivideTens(cache[1100] ?: 0),
                polygon3PointX12 = safeDivideTens(cache[1102] ?: 0),
                polygon3PointX13 = safeDivideTens(cache[1104] ?: 0),
                polygon3PointX14 = safeDivideTens(cache[1106] ?: 0),
                polygon3PointX15 = safeDivideTens(cache[1108] ?: 0),
                polygon3PointX16 = safeDivideTens(cache[1110] ?: 0),
                polygon3PointX17 = safeDivideTens(cache[1112] ?: 0),
                polygon3PointX18 = safeDivideTens(cache[1114] ?: 0),
                polygon3PointX19 = safeDivideTens(cache[1116] ?: 0),
                polygon3PointX20 = safeDivideTens(cache[1118] ?: 0),

                polygon3PointY01 = safeDivideTens(cache[1091] ?: 0),
                polygon3PointY02 = safeDivideTens(cache[1093] ?: 0),
                polygon3PointY03 = safeDivideTens(cache[1095] ?: 0),
                polygon3PointY04 = safeDivideTens(cache[1097] ?: 0),
                polygon3PointY05 = safeDivideTens(cache[1099] ?: 0),
                polygon3PointY06 = safeDivideTens(cache[1101] ?: 0),
                polygon3PointY07 = safeDivideTens(cache[1103] ?: 0),
                polygon3PointY08 = safeDivideTens(cache[1105] ?: 0),
                polygon3PointY09 = safeDivideTens(cache[1107] ?: 0),
                polygon3PointY10 = safeDivideTens(cache[1109] ?: 0),
                polygon3PointY11 = safeDivideTens(cache[1101] ?: 0),
                polygon3PointY12 = safeDivideTens(cache[1103] ?: 0),
                polygon3PointY13 = safeDivideTens(cache[1105] ?: 0),
                polygon3PointY14 = safeDivideTens(cache[1107] ?: 0),
                polygon3PointY15 = safeDivideTens(cache[1109] ?: 0),
                polygon3PointY16 = safeDivideTens(cache[1111] ?: 0),
                polygon3PointY17 = safeDivideTens(cache[1113] ?: 0),
                polygon3PointY18 = safeDivideTens(cache[1115] ?: 0),
                polygon3PointY19 = safeDivideTens(cache[1117] ?: 0),
                polygon3PointY20 = safeDivideTens(cache[1119] ?: 0),
            ),
            polygon4Point = Polygon4Point(
                polygon4PointX01 = safeDivideTens(cache[1120] ?: 0),
                polygon4PointX02 = safeDivideTens(cache[1122] ?: 0),
                polygon4PointX03 = safeDivideTens(cache[1124] ?: 0),
                polygon4PointX04 = safeDivideTens(cache[1126] ?: 0),
                polygon4PointX05 = safeDivideTens(cache[1128] ?: 0),
                polygon4PointX06 = safeDivideTens(cache[1130] ?: 0),
                polygon4PointX07 = safeDivideTens(cache[1132] ?: 0),
                polygon4PointX08 = safeDivideTens(cache[1134] ?: 0),
                polygon4PointX09 = safeDivideTens(cache[1136] ?: 0),
                polygon4PointX10 = safeDivideTens(cache[1138] ?: 0),
                polygon4PointX11 = safeDivideTens(cache[1140] ?: 0),
                polygon4PointX12 = safeDivideTens(cache[1142] ?: 0),
                polygon4PointX13 = safeDivideTens(cache[1144] ?: 0),
                polygon4PointX14 = safeDivideTens(cache[1146] ?: 0),
                polygon4PointX15 = safeDivideTens(cache[1148] ?: 0),
                polygon4PointX16 = safeDivideTens(cache[1150] ?: 0),
                polygon4PointX17 = safeDivideTens(cache[1152] ?: 0),
                polygon4PointX18 = safeDivideTens(cache[1154] ?: 0),
                polygon4PointX19 = safeDivideTens(cache[1156] ?: 0),
                polygon4PointX20 = safeDivideTens(cache[1158] ?: 0),

                polygon4PointY01 = safeDivideTens(cache[1121] ?: 0),
                polygon4PointY02 = safeDivideTens(cache[1123] ?: 0),
                polygon4PointY03 = safeDivideTens(cache[1125] ?: 0),
                polygon4PointY04 = safeDivideTens(cache[1127] ?: 0),
                polygon4PointY05 = safeDivideTens(cache[1129] ?: 0),
                polygon4PointY06 = safeDivideTens(cache[1131] ?: 0),
                polygon4PointY07 = safeDivideTens(cache[1133] ?: 0),
                polygon4PointY08 = safeDivideTens(cache[1135] ?: 0),
                polygon4PointY09 = safeDivideTens(cache[1137] ?: 0),
                polygon4PointY10 = safeDivideTens(cache[1139] ?: 0),
                polygon4PointY11 = safeDivideTens(cache[1141] ?: 0),
                polygon4PointY12 = safeDivideTens(cache[1143] ?: 0),
                polygon4PointY13 = safeDivideTens(cache[1145] ?: 0),
                polygon4PointY14 = safeDivideTens(cache[1147] ?: 0),
                polygon4PointY15 = safeDivideTens(cache[1149] ?: 0),
                polygon4PointY16 = safeDivideTens(cache[1151] ?: 0),
                polygon4PointY17 = safeDivideTens(cache[1153] ?: 0),
                polygon4PointY18 = safeDivideTens(cache[1155] ?: 0),
                polygon4PointY19 = safeDivideTens(cache[1157] ?: 0),
                polygon4PointY20 = safeDivideTens(cache[1159] ?: 0),
            )
        )
    }

    private fun safeDivideTens(value: Int?): Float {
        return (value ?: 0) / 10f
    }

    data class CoordinateProtectionState(
        val polygonState: PolygonState = PolygonState(),
        val polygon1Point: Polygon1Point = Polygon1Point(),
        val polygon2Point: Polygon2Point = Polygon2Point(),
        val polygon3Point: Polygon3Point = Polygon3Point(),
        val polygon4Point: Polygon4Point = Polygon4Point(),
    )

    data class PolygonState(
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
    )


    data class Polygon1Point (
        //ПЕРЕМЕННЫЕ КООРДИНАТЫ ТОЧЕК ПОЛИГОНА №1
        val polygon1PointX01: Float = 0f,
        val polygon1PointX02: Float = 0f,
        val polygon1PointX03: Float = 0f,
        val polygon1PointX04: Float = 0f,
        val polygon1PointX05: Float = 0f,
        val polygon1PointX06: Float = 0f,
        val polygon1PointX07: Float = 0f,
        val polygon1PointX08: Float = 0f,
        val polygon1PointX09: Float = 0f,
        val polygon1PointX10: Float = 0f,
        val polygon1PointX11: Float = 0f,
        val polygon1PointX12: Float = 0f,
        val polygon1PointX13: Float = 0f,
        val polygon1PointX14: Float = 0f,
        val polygon1PointX15: Float = 0f,
        val polygon1PointX16: Float = 0f,
        val polygon1PointX17: Float = 0f,
        val polygon1PointX18: Float = 0f,
        val polygon1PointX19: Float = 0f,
        val polygon1PointX20: Float = 0f,

        val polygon1PointY01: Float = 0f,
        val polygon1PointY02: Float = 0f,
        val polygon1PointY03: Float = 0f,
        val polygon1PointY04: Float = 0f,
        val polygon1PointY05: Float = 0f,
        val polygon1PointY06: Float = 0f,
        val polygon1PointY07: Float = 0f,
        val polygon1PointY08: Float = 0f,
        val polygon1PointY09: Float = 0f,
        val polygon1PointY10: Float = 0f,
        val polygon1PointY11: Float = 0f,
        val polygon1PointY12: Float = 0f,
        val polygon1PointY13: Float = 0f,
        val polygon1PointY14: Float = 0f,
        val polygon1PointY15: Float = 0f,
        val polygon1PointY16: Float = 0f,
        val polygon1PointY17: Float = 0f,
        val polygon1PointY18: Float = 0f,
        val polygon1PointY19: Float = 0f,
        val polygon1PointY20: Float = 0f,
        )

    data class Polygon2Point(
        //ПЕРЕМЕННЫЕ КООРДИНАТЫ ТОЧЕК ПОЛИГОНА №2
        val polygon2PointX01: Float = 0f,
        val polygon2PointX02: Float = 0f,
        val polygon2PointX03: Float = 0f,
        val polygon2PointX04: Float = 0f,
        val polygon2PointX05: Float = 0f,
        val polygon2PointX06: Float = 0f,
        val polygon2PointX07: Float = 0f,
        val polygon2PointX08: Float = 0f,
        val polygon2PointX09: Float = 0f,
        val polygon2PointX10: Float = 0f,
        val polygon2PointX11: Float = 0f,
        val polygon2PointX12: Float = 0f,
        val polygon2PointX13: Float = 0f,
        val polygon2PointX14: Float = 0f,
        val polygon2PointX15: Float = 0f,
        val polygon2PointX16: Float = 0f,
        val polygon2PointX17: Float = 0f,
        val polygon2PointX18: Float = 0f,
        val polygon2PointX19: Float = 0f,
        val polygon2PointX20: Float = 0f,

        val polygon2PointY01: Float = 0f,
        val polygon2PointY02: Float = 0f,
        val polygon2PointY03: Float = 0f,
        val polygon2PointY04: Float = 0f,
        val polygon2PointY05: Float = 0f,
        val polygon2PointY06: Float = 0f,
        val polygon2PointY07: Float = 0f,
        val polygon2PointY08: Float = 0f,
        val polygon2PointY09: Float = 0f,
        val polygon2PointY10: Float = 0f,
        val polygon2PointY11: Float = 0f,
        val polygon2PointY12: Float = 0f,
        val polygon2PointY13: Float = 0f,
        val polygon2PointY14: Float = 0f,
        val polygon2PointY15: Float = 0f,
        val polygon2PointY16: Float = 0f,
        val polygon2PointY17: Float = 0f,
        val polygon2PointY18: Float = 0f,
        val polygon2PointY19: Float = 0f,
        val polygon2PointY20: Float = 0f,
    )

    data class Polygon3Point (
        //ПЕРЕМЕННЫЕ КООРДИНАТЫ ТОЧЕК ПОЛИГОНА №3
        val polygon3PointX01: Float = 0f,
        val polygon3PointX02: Float = 0f,
        val polygon3PointX03: Float = 0f,
        val polygon3PointX04: Float = 0f,
        val polygon3PointX05: Float = 0f,
        val polygon3PointX06: Float = 0f,
        val polygon3PointX07: Float = 0f,
        val polygon3PointX08: Float = 0f,
        val polygon3PointX09: Float = 0f,
        val polygon3PointX10: Float = 0f,
        val polygon3PointX11: Float = 0f,
        val polygon3PointX12: Float = 0f,
        val polygon3PointX13: Float = 0f,
        val polygon3PointX14: Float = 0f,
        val polygon3PointX15: Float = 0f,
        val polygon3PointX16: Float = 0f,
        val polygon3PointX17: Float = 0f,
        val polygon3PointX18: Float = 0f,
        val polygon3PointX19: Float = 0f,
        val polygon3PointX20: Float = 0f,

        val polygon3PointY01: Float = 0f,
        val polygon3PointY02: Float = 0f,
        val polygon3PointY03: Float = 0f,
        val polygon3PointY04: Float = 0f,
        val polygon3PointY05: Float = 0f,
        val polygon3PointY06: Float = 0f,
        val polygon3PointY07: Float = 0f,
        val polygon3PointY08: Float = 0f,
        val polygon3PointY09: Float = 0f,
        val polygon3PointY10: Float = 0f,
        val polygon3PointY11: Float = 0f,
        val polygon3PointY12: Float = 0f,
        val polygon3PointY13: Float = 0f,
        val polygon3PointY14: Float = 0f,
        val polygon3PointY15: Float = 0f,
        val polygon3PointY16: Float = 0f,
        val polygon3PointY17: Float = 0f,
        val polygon3PointY18: Float = 0f,
        val polygon3PointY19: Float = 0f,
        val polygon3PointY20: Float = 0f,
    )

    data class Polygon4Point (
        //ПЕРЕМЕННЫЕ КООРДИНАТЫ ТОЧЕК ПОЛИГОНА №4
        val polygon4PointX01: Float = 0f,
        val polygon4PointX02: Float = 0f,
        val polygon4PointX03: Float = 0f,
        val polygon4PointX04: Float = 0f,
        val polygon4PointX05: Float = 0f,
        val polygon4PointX06: Float = 0f,
        val polygon4PointX07: Float = 0f,
        val polygon4PointX08: Float = 0f,
        val polygon4PointX09: Float = 0f,
        val polygon4PointX10: Float = 0f,
        val polygon4PointX11: Float = 0f,
        val polygon4PointX12: Float = 0f,
        val polygon4PointX13: Float = 0f,
        val polygon4PointX14: Float = 0f,
        val polygon4PointX15: Float = 0f,
        val polygon4PointX16: Float = 0f,
        val polygon4PointX17: Float = 0f,
        val polygon4PointX18: Float = 0f,
        val polygon4PointX19: Float = 0f,
        val polygon4PointX20: Float = 0f,

        val polygon4PointY01: Float = 0f,
        val polygon4PointY02: Float = 0f,
        val polygon4PointY03: Float = 0f,
        val polygon4PointY04: Float = 0f,
        val polygon4PointY05: Float = 0f,
        val polygon4PointY06: Float = 0f,
        val polygon4PointY07: Float = 0f,
        val polygon4PointY08: Float = 0f,
        val polygon4PointY09: Float = 0f,
        val polygon4PointY10: Float = 0f,
        val polygon4PointY11: Float = 0f,
        val polygon4PointY12: Float = 0f,
        val polygon4PointY13: Float = 0f,
        val polygon4PointY14: Float = 0f,
        val polygon4PointY15: Float = 0f,
        val polygon4PointY16: Float = 0f,
        val polygon4PointY17: Float = 0f,
        val polygon4PointY18: Float = 0f,
        val polygon4PointY19: Float = 0f,
        val polygon4PointY20: Float = 0f,
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
    // Настройка. Запретить работу вверху полигона №1
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
    // Настройка. Запретить работу внизу полигона №1
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
                val model = validateAndConvertFloat(_setValuePolygon1Height.value, 0.0f, 300.0f )
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
                val model = validateAndConvertFloat(_setValuePolygon1SlewPos.value, 0.0f, 360.0f )
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
                val model = validateAndConvertFloat(_setValuePolygon1CranePos.value, 0.0f, 300.0f )
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
                val model = validateAndConvertFloat(_setValuePolygon1PxPos.value, 0.0f, 500.0f )
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
                val model = validateAndConvertFloat(_setValuePolygon1PyPos.value, 0.0f, 300.0f )
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
    // Настройка. Запретить работу вверху полигона №2
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
    // Настройка. Запретить работу внизу полигона №2
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
                val model = validateAndConvertFloat(_setValuePolygon2Height.value, 0.0f, 300.0f )
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
    // ПОЛИГОН №2. - КНОПКА ЗАПОМНИТЬ ТОЧКУ  с 1 по 20
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
                val model = validateAndConvertFloat(_setValuePolygon2SlewPos.value, 0.0f, 360.0f )
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
                val model = validateAndConvertFloat(_setValuePolygon2CranePos.value, 0.0f, 300.0f )
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
                val model = validateAndConvertFloat(_setValuePolygon2PxPos.value, 0.0f, 500.0f )
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
                val model = validateAndConvertFloat(_setValuePolygon2PyPos.value, 0.0f, 300.0f )
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





    /**
    !!!!!!!!!!!!!!!!!!!!!!ПОЛИГОН №3. - НАСТРОЙКА!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // Настройка. ВКЛЮЧИТЬ/ВЫКЛЮЧИТЬ ПОЛИГОН №3
    fun onSetP3En() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(880, 1)
                delay(200)
                modbusRepository.writeRegister(880, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Запретить работу вверху полигона №3
    fun onSetP3UpEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(881, 1)
                delay(200)
                modbusRepository.writeRegister(881, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Запретить работу внизу полигона №3
    fun onSetP3DownEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(882, 1)
                delay(200)
                modbusRepository.writeRegister(882, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Ввод количество точек полигона №3
    fun onSetValueP3PointNum() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValuePolygon3PointNum.value, 0, 20 )
                // Запись в регистры
                modbusRepository.writeRegister(884, model)
                _errorMessagePolygon3.value = null
                _logMessagePolygon3.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon3.value = null
                _errorMessagePolygon3.value = "${e.message}"
            }
        }
    }
    // Настройка. Ввод высота полигона №3
    fun onSetValueP3Height() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon3Height.value, 0.0f, 300.0f )
                // Запись в регистры
                modbusRepository.writeRegister(883, model)
                _errorMessagePolygon3.value = null
                _logMessagePolygon3.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon3.value = null
                _errorMessagePolygon3.value = "${e.message}"
            }
        }
    }
    // Настройка. Выбор режима задания точек полигона №3. По координатам X - Y
    fun onSetP3Type1En() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(922, 1)
                delay(200)
                modbusRepository.writeRegister(922, 0)
            } catch (e: Exception) {
            }
        }
    }
    // Настройка. Выбор режима задания точек полигона №3. Ручной ввод
    fun onSetP3Type2En() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(923, 1)
                delay(200)
                modbusRepository.writeRegister(923, 0)
            } catch (e: Exception) {
            }
        }
    }
    // ПОЛИГОН №3. - КНОПКА ЗАПОМНИТЬ ТОЧКУ  с 1 по 20
    fun onSetP3PointCmd(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистр
                modbusRepository.writeRegister(921, index)
                delay(200)
                modbusRepository.writeRegister(921, 0)
            } catch (e: Exception) {
            }
        }
    }

    // ПОЛИГОН №3. Настройка точек. При типе ввода 2 (Вручную) - позиция телеги
    fun onSetValuePolygon3TrolleyPos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon3TrolleyPos.value, 0.0f, 100.0f )
                // Запись в регистры
                modbusRepository.writeRegister(926, model)
                _errorMessagePolygon3Manual.value = null
                _logMessagePolygon3Manual.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon3Manual.value = null
                _errorMessagePolygon3Manual.value = "${e.message}"
            }
        }
    }
    // ПОЛИГОН №3. Настройка точек. При типе ввода 2 (Вручную) - позиция поворота
    fun onSetValuePolygon3SlewPos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon3SlewPos.value, 0.0f, 360.0f )
                // Запись в регистры
                modbusRepository.writeRegister(927, model)
                _errorMessagePolygon3Manual.value = null
                _logMessagePolygon3Manual.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon3Manual.value = null
                _errorMessagePolygon3Manual.value = "${e.message}"
            }
        }
    }
    // ПОЛИГОН №3. Настройка точек. При типе ввода 2 (Вручную) - позиция хода крана
    fun onSetValuePolygon3CranePos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon3CranePos.value, 0.0f, 300.0f )
                // Запись в регистры
                modbusRepository.writeRegister(928, model)
                _errorMessagePolygon3Manual.value = null
                _logMessagePolygon3Manual.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon3Manual.value = null
                _errorMessagePolygon3Manual.value = "${e.message}"
            }
        }
    }

    // ПОЛИГОН №3. Настройка точек. При типе ввода 1 (По Координатам) - позиция по оси Х
    fun onsetValuePolygon3PxPos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon3PxPos.value, 0.0f, 500.0f )
                // Запись в регистры
                modbusRepository.writeRegister(924, model)
                _errorMessagePolygon3Coordinate.value = null
                _logMessagePolygon3Coordinate.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon3Coordinate.value = null
                _errorMessagePolygon3Coordinate.value = "${e.message}"
            }
        }
    }
    // ПОЛИГОН №3. Настройка точек. При типе ввода 1 (По Координатам) - позиция по оси Y
    fun onsetValuePolygon3PyPos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon3PyPos.value, 0.0f, 300.0f )
                // Запись в регистры
                modbusRepository.writeRegister(925, model)
                _errorMessagePolygon3Coordinate.value = null
                _logMessagePolygon3Coordinate.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon3Coordinate.value = null
                _errorMessagePolygon3Coordinate.value = "${e.message}"
            }
        }
    }

    // НАСТРОЙКА. ПОЛИГОН №3.  СБРОСИТЬ ТРЕТИЙ ПОЛИГОН
    fun startResetPolygon3() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(920, 1)
                delay(200)
                modbusRepository.writeRegister(920, 0)
            } catch (e: Exception) {
            }
        }
    }

    //ПОЛИГОН №3. Обновление для UI
    fun updateValuePolygon3Height(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon3Height.value = value } }

    fun updateValuePolygon3PointNum(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValuePolygon3PointNum.value = value } }

    fun updateValuePolygon3TrolleyPos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon3TrolleyPos.value = value } }

    fun updateValuePolygon3SlewPos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon3SlewPos.value = value } }

    fun updateValuePolygon3CranePos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon3CranePos.value = value } }

    fun updateValuePolygon3PxPos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon3PxPos.value = value } }

    fun updateValuePolygon3PyPos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon3PyPos.value = value } }



    /**
    !!!!!!!!!!!!!!!!!!!!!!ПОЛИГОН №4. - НАСТРОЙКА!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // Настройка. ВКЛЮЧИТЬ/ВЫКЛЮЧИТЬ ПОЛИГОН №4
    fun onSetP4En() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(885, 1)
                delay(200)
                modbusRepository.writeRegister(885, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Запретить работу вверху полигона №4
    fun onSetP4UpEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(886, 1)
                delay(200)
                modbusRepository.writeRegister(886, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Запретить работу внизу полигона №4
    fun onSetP4DownEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(887, 1)
                delay(200)
                modbusRepository.writeRegister(887, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Ввод количество точек полигона №4
    fun onSetValueP4PointNum() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValuePolygon4PointNum.value, 0, 20 )
                // Запись в регистры
                modbusRepository.writeRegister(889, model)
                _errorMessagePolygon4.value = null
                _logMessagePolygon4.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon4.value = null
                _errorMessagePolygon4.value = "${e.message}"
            }
        }
    }
    // Настройка. Ввод высоты полигона №4
    fun onSetValueP4Height() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon4Height.value, 0.0f, 300.0f )
                // Запись в регистры
                modbusRepository.writeRegister(888, model)
                _errorMessagePolygon4.value = null
                _logMessagePolygon4.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon4.value = null
                _errorMessagePolygon4.value = "${e.message}"
            }
        }
    }
    // Настройка. Выбор режима задания точек полигона №4. По координатам X - Y
    fun onSetP4Type1En() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(932, 1)
                delay(200)
                modbusRepository.writeRegister(932, 0)
            } catch (e: Exception) {
            }
        }
    }
    // Настройка. Выбор режима задания точек полигона №4. Ручной ввод
    fun onSetP4Type2En() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(933, 1)
                delay(200)
                modbusRepository.writeRegister(933, 0)
            } catch (e: Exception) {
            }
        }
    }
    // ПОЛИГОН №4. - КНОПКА ЗАПОМНИТЬ ТОЧКУ  с 1 по 20
    fun onSetP4PointCmd(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистр
                modbusRepository.writeRegister(931, index)
                delay(200)
                modbusRepository.writeRegister(931, 0)
            } catch (e: Exception) {
            }
        }
    }

    // ПОЛИГОН №4. Настройка точек. При типе ввода 2 (Вручную) - позиция телеги
    fun onSetValuePolygon4TrolleyPos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon4TrolleyPos.value, 0.0f, 100.0f )
                // Запись в регистры
                modbusRepository.writeRegister(936, model)
                _errorMessagePolygon4Manual.value = null
                _logMessagePolygon4Manual.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon4Manual.value = null
                _errorMessagePolygon4Manual.value = "${e.message}"
            }
        }
    }
    // ПОЛИГОН №4. Настройка точек. При типе ввода 2 (Вручную) - позиция поворота
    fun onSetValuePolygon4SlewPos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon4SlewPos.value, 0.0f, 360.0f )
                // Запись в регистры
                modbusRepository.writeRegister(937, model)
                _errorMessagePolygon4Manual.value = null
                _logMessagePolygon4Manual.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon4Manual.value = null
                _errorMessagePolygon4Manual.value = "${e.message}"
            }
        }
    }
    // ПОЛИГОН №4. Настройка точек. При типе ввода 2 (Вручную) - позиция хода крана
    fun onSetValuePolygon4CranePos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon4CranePos.value, 0.0f, 300.0f )
                // Запись в регистры
                modbusRepository.writeRegister(938, model)
                _errorMessagePolygon4Manual.value = null
                _logMessagePolygon4Manual.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon4Manual.value = null
                _errorMessagePolygon4Manual.value = "${e.message}"
            }
        }
    }

    // ПОЛИГОН №4. Настройка точек. При типе ввода 1 (По Координатам) - позиция по оси Х
    fun onsetValuePolygon4PxPos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon2PxPos.value, 0.0f, 500.0f )
                // Запись в регистры
                modbusRepository.writeRegister(934, model)
                _errorMessagePolygon4Coordinate.value = null
                _logMessagePolygon4Coordinate.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon4Coordinate.value = null
                _errorMessagePolygon4Coordinate.value = "${e.message}"
            }
        }
    }
    // ПОЛИГОН №4. Настройка точек. При типе ввода 1 (По Координатам) - позиция по оси Y
    fun onsetValuePolygon4PyPos() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValuePolygon4PyPos.value, 0.0f, 300.0f )
                // Запись в регистры
                modbusRepository.writeRegister(935, model)
                _errorMessagePolygon4Coordinate.value = null
                _logMessagePolygon4Coordinate.value = "Сохранено"
            } catch (e: Exception) {
                _logMessagePolygon4Coordinate.value = null
                _errorMessagePolygon4Coordinate.value = "${e.message}"
            }
        }
    }

    // НАСТРОЙКА. ПОЛИГОН №4.  СБРОСИТЬ ЧЕТВЕРТЫЙ ПОЛИГОН
    fun startResetPolygon4() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(930, 1)
                delay(200)
                modbusRepository.writeRegister(930, 0)
            } catch (e: Exception) {
            }
        }
    }

    //ПОЛИГОН №4. Обновление для UI
    fun updateValuePolygon4Height(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon4Height.value = value } }

    fun updateValuePolygon4PointNum(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValuePolygon4PointNum.value = value } }

    fun updateValuePolygon4TrolleyPos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon4TrolleyPos.value = value } }

    fun updateValuePolygon4SlewPos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon4SlewPos.value = value } }

    fun updateValuePolygon4CranePos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon4CranePos.value = value } }

    fun updateValuePolygon4PxPos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon4PxPos.value = value } }

    fun updateValuePolygon4PyPos(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValuePolygon4PyPos.value = value } }



    override fun onCleared() {
        super.onCleared()
        Log.d("CoordinateProtectionVM", "ViewModel cleared")
        modbusRepository.setActiveUiConfiguration(null)
    }
}