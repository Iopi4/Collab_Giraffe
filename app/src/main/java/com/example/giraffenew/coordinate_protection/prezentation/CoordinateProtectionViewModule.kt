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
        33130, 33120, 33140,
        33900, 33910, 33911, 33912, 33913, 33914, 33921, 33922, 33923, 33924, 33931, 33932, 33933, 33934,
        33941, 33942, 33943, 33944, 33951, 33952, 33953, 33954,
        33960, 33961, 33962, 33963, 33964, 33965, 33966, 33967, 33968,

        36100, 36101, 36102, 36103, 36104, 36105, 36106, 36107, 36108, 36109,
        36110, 36111, 36112, 36113, 36114, 36115, 36116, 36117, 36118, 36119,
        36120, 36121, 36122, 36123, 36124, 36125, 36126, 36127, 36128, 36129,
        36130, 36131, 36132, 36133, 36134, 36135, 36136, 36137, 36138, 36139,

        36200, 36201, 36202, 36203, 36204, 36205, 36206, 36207, 36208, 36209,
        36210, 36211, 36212, 36213, 36214, 36215, 36216, 36217, 36218, 36219,
        36220, 36221, 36222, 36223, 36224, 36225, 36226, 36227, 36228, 36229,
        36230, 36231, 36232, 36233, 36234, 36235, 36236, 36237, 36238, 36239,

        36300, 36301, 36302, 36303, 36304, 36305, 36306, 36307, 36308, 36309,
        36310, 36311, 36312, 36313, 36314, 36315, 36316, 36317, 36318, 36319,
        36320, 36321, 36322, 36323, 36324, 36325, 36326, 36327, 36328, 36329,
        36330, 36331, 36332, 36333, 36334, 36335, 36336, 36337, 36338, 36339,

        36400, 36401, 36402, 36403, 36404, 36405, 36406, 36407, 36408, 36409,
        36410, 36411, 36412, 36413, 36414, 36415, 36416, 36417, 36418, 36419,
        36420, 36421, 36422, 36423, 36424, 36425, 36426, 36427, 36428, 36429,
        36430, 36431, 36432, 36433, 36434, 36435, 36436, 36437, 36438, 36439
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
                currentSlewPosition = safeDivideTens(cache[33120] ?: 0),
                currentTrolleyPosition = safeDivideTens(cache[33130] ?: 0),
                currentCranePosition = safeDivideTens(cache[33140] ?: 0),
                //Данные состояний настройки полигонов
                currentPosProtEn    = cache[33900] ?: 0,
                currentModePosProt  = cache[33910] ?: 0,

                currentP1Up     = cache[33911] ?: 0,
                currentP1Down   = cache[33912] ?: 0,
                currentP1Height    = safeDivideTens(cache[33913] ?: 0),
                currentP1PointNum = cache[33914] ?: 0,
                currentP1PointType = cache[33951] ?: 0,

                currentP2Up     = cache[33921] ?: 0,
                currentP2Down   = cache[33922] ?: 0,
                currentP2Height    = safeDivideTens(cache[33923] ?: 0),
                currentP2PointNum = cache[33924] ?: 0,
                currentP2PointType = cache[33952] ?: 0,

                currentP3Up     = cache[33931] ?: 0,
                currentP3Down   = cache[33932] ?: 0,
                currentP3Height    = safeDivideTens(cache[33933] ?: 0),
                currentP3PointNum = cache[33934] ?: 0,
                currentP3PointType = cache[33953] ?: 0,

                currentP4Up     = cache[33941] ?: 0,
                currentP4Down   = cache[33942] ?: 0,
                currentP4Height    = safeDivideTens(cache[33943] ?: 0),
                currentP4PointNum = cache[33944] ?: 0,
                currentP4PointType = cache[33954] ?: 0,

                currentP1FewPoint       = cache[33961] ?: 0,
                currentP1ActiveStatus    = cache[33962] ?: 0,
                currentP2FewPoint       = cache[33963] ?: 0,
                currentP2ActiveStatus    = cache[33964] ?: 0,
                currentP3FewPoint       = cache[33965] ?: 0,
                currentP3ActiveStatus    = cache[33966] ?: 0,
                currentP4FewPoint       = cache[33967] ?: 0,
                currentP4ActiveStatus     = cache[33968] ?: 0,
                currentStatsCalibration = cache[33960] ?: 0,
            ),
            polygon1Point = Polygon1Point(
                //Координаты точек. ПОЛИГОН №1
                polygon1PointX01 = safeDivideTens(cache[36100] ?: 0),
                polygon1PointX02 = safeDivideTens(cache[36102] ?: 0),
                polygon1PointX03 = safeDivideTens(cache[36104] ?: 0),
                polygon1PointX04 = safeDivideTens(cache[36106] ?: 0),
                polygon1PointX05 = safeDivideTens(cache[36108] ?: 0),
                polygon1PointX06 = safeDivideTens(cache[36110] ?: 0),
                polygon1PointX07 = safeDivideTens(cache[36112] ?: 0),
                polygon1PointX08 = safeDivideTens(cache[36114] ?: 0),
                polygon1PointX09 = safeDivideTens(cache[36116] ?: 0),
                polygon1PointX10 = safeDivideTens(cache[36118] ?: 0),
                polygon1PointX11 = safeDivideTens(cache[36120] ?: 0),
                polygon1PointX12 = safeDivideTens(cache[36122] ?: 0),
                polygon1PointX13 = safeDivideTens(cache[36124] ?: 0),
                polygon1PointX14 = safeDivideTens(cache[36126] ?: 0),
                polygon1PointX15 = safeDivideTens(cache[36128] ?: 0),
                polygon1PointX16 = safeDivideTens(cache[36130] ?: 0),
                polygon1PointX17 = safeDivideTens(cache[36132] ?: 0),
                polygon1PointX18 = safeDivideTens(cache[36134] ?: 0),
                polygon1PointX19 = safeDivideTens(cache[36136] ?: 0),
                polygon1PointX20 = safeDivideTens(cache[36138] ?: 0),

                polygon1PointY01 = safeDivideTens(cache[36101] ?: 0),
                polygon1PointY02 = safeDivideTens(cache[36103] ?: 0),
                polygon1PointY03 = safeDivideTens(cache[36105] ?: 0),
                polygon1PointY04 = safeDivideTens(cache[36107] ?: 0),
                polygon1PointY05 = safeDivideTens(cache[36109] ?: 0),
                polygon1PointY06 = safeDivideTens(cache[36111] ?: 0),
                polygon1PointY07 = safeDivideTens(cache[36113] ?: 0),
                polygon1PointY08 = safeDivideTens(cache[36115] ?: 0),
                polygon1PointY09 = safeDivideTens(cache[36117] ?: 0),
                polygon1PointY10 = safeDivideTens(cache[36119] ?: 0),
                polygon1PointY11 = safeDivideTens(cache[36121] ?: 0),
                polygon1PointY12 = safeDivideTens(cache[36123] ?: 0),
                polygon1PointY13 = safeDivideTens(cache[36125] ?: 0),
                polygon1PointY14 = safeDivideTens(cache[36127] ?: 0),
                polygon1PointY15 = safeDivideTens(cache[36129] ?: 0),
                polygon1PointY16 = safeDivideTens(cache[36131] ?: 0),
                polygon1PointY17 = safeDivideTens(cache[36133] ?: 0),
                polygon1PointY18 = safeDivideTens(cache[36135] ?: 0),
                polygon1PointY19 = safeDivideTens(cache[36137] ?: 0),
                polygon1PointY20 = safeDivideTens(cache[36139] ?: 0),
            ),
            polygon2Point = Polygon2Point(
                //Координаты точек. ПОЛИГОН №2
                polygon2PointX01 = safeDivideTens(cache[36200] ?: 0),
                polygon2PointX02 = safeDivideTens(cache[36202] ?: 0),
                polygon2PointX03 = safeDivideTens(cache[36204] ?: 0),
                polygon2PointX04 = safeDivideTens(cache[36206] ?: 0),
                polygon2PointX05 = safeDivideTens(cache[36208] ?: 0),
                polygon2PointX06 = safeDivideTens(cache[36200] ?: 0),
                polygon2PointX07 = safeDivideTens(cache[36212] ?: 0),
                polygon2PointX08 = safeDivideTens(cache[36214] ?: 0),
                polygon2PointX09 = safeDivideTens(cache[36216] ?: 0),
                polygon2PointX10 = safeDivideTens(cache[36218] ?: 0),
                polygon2PointX11 = safeDivideTens(cache[36210] ?: 0),
                polygon2PointX12 = safeDivideTens(cache[36222] ?: 0),
                polygon2PointX13 = safeDivideTens(cache[36224] ?: 0),
                polygon2PointX14 = safeDivideTens(cache[36226] ?: 0),
                polygon2PointX15 = safeDivideTens(cache[36228] ?: 0),
                polygon2PointX16 = safeDivideTens(cache[36230] ?: 0),
                polygon2PointX17 = safeDivideTens(cache[36232] ?: 0),
                polygon2PointX18 = safeDivideTens(cache[36234] ?: 0),
                polygon2PointX19 = safeDivideTens(cache[36236] ?: 0),
                polygon2PointX20 = safeDivideTens(cache[36238] ?: 0),

                polygon2PointY01 = safeDivideTens(cache[36201] ?: 0),
                polygon2PointY02 = safeDivideTens(cache[36203] ?: 0),
                polygon2PointY03 = safeDivideTens(cache[36205] ?: 0),
                polygon2PointY04 = safeDivideTens(cache[36207] ?: 0),
                polygon2PointY05 = safeDivideTens(cache[36209] ?: 0),
                polygon2PointY06 = safeDivideTens(cache[36211] ?: 0),
                polygon2PointY07 = safeDivideTens(cache[36213] ?: 0),
                polygon2PointY08 = safeDivideTens(cache[36215] ?: 0),
                polygon2PointY09 = safeDivideTens(cache[36217] ?: 0),
                polygon2PointY10 = safeDivideTens(cache[36219] ?: 0),
                polygon2PointY11 = safeDivideTens(cache[36221] ?: 0),
                polygon2PointY12 = safeDivideTens(cache[36223] ?: 0),
                polygon2PointY13 = safeDivideTens(cache[36225] ?: 0),
                polygon2PointY14 = safeDivideTens(cache[36227] ?: 0),
                polygon2PointY15 = safeDivideTens(cache[36229] ?: 0),
                polygon2PointY16 = safeDivideTens(cache[36231] ?: 0),
                polygon2PointY17 = safeDivideTens(cache[36233] ?: 0),
                polygon2PointY18 = safeDivideTens(cache[36235] ?: 0),
                polygon2PointY19 = safeDivideTens(cache[36237] ?: 0),
                polygon2PointY20 = safeDivideTens(cache[36239] ?: 0),
            ),
            polygon3Point = Polygon3Point(
                polygon3PointX01 = safeDivideTens(cache[36300] ?: 0),
                polygon3PointX02 = safeDivideTens(cache[36302] ?: 0),
                polygon3PointX03 = safeDivideTens(cache[36304] ?: 0),
                polygon3PointX04 = safeDivideTens(cache[36306] ?: 0),
                polygon3PointX05 = safeDivideTens(cache[36308] ?: 0),
                polygon3PointX06 = safeDivideTens(cache[36310] ?: 0),
                polygon3PointX07 = safeDivideTens(cache[3631] ?: 0),
                polygon3PointX08 = safeDivideTens(cache[36314] ?: 0),
                polygon3PointX09 = safeDivideTens(cache[36316] ?: 0),
                polygon3PointX10 = safeDivideTens(cache[36318] ?: 0),
                polygon3PointX11 = safeDivideTens(cache[36320] ?: 0),
                polygon3PointX12 = safeDivideTens(cache[36322] ?: 0),
                polygon3PointX13 = safeDivideTens(cache[36324] ?: 0),
                polygon3PointX14 = safeDivideTens(cache[36326] ?: 0),
                polygon3PointX15 = safeDivideTens(cache[36328] ?: 0),
                polygon3PointX16 = safeDivideTens(cache[36330] ?: 0),
                polygon3PointX17 = safeDivideTens(cache[36332] ?: 0),
                polygon3PointX18 = safeDivideTens(cache[36334] ?: 0),
                polygon3PointX19 = safeDivideTens(cache[36336] ?: 0),
                polygon3PointX20 = safeDivideTens(cache[36338] ?: 0),

                polygon3PointY01 = safeDivideTens(cache[36301] ?: 0),
                polygon3PointY02 = safeDivideTens(cache[36303] ?: 0),
                polygon3PointY03 = safeDivideTens(cache[36305] ?: 0),
                polygon3PointY04 = safeDivideTens(cache[36307] ?: 0),
                polygon3PointY05 = safeDivideTens(cache[36309] ?: 0),
                polygon3PointY06 = safeDivideTens(cache[36311] ?: 0),
                polygon3PointY07 = safeDivideTens(cache[36313] ?: 0),
                polygon3PointY08 = safeDivideTens(cache[36315] ?: 0),
                polygon3PointY09 = safeDivideTens(cache[36317] ?: 0),
                polygon3PointY10 = safeDivideTens(cache[36319] ?: 0),
                polygon3PointY11 = safeDivideTens(cache[36321] ?: 0),
                polygon3PointY12 = safeDivideTens(cache[36323] ?: 0),
                polygon3PointY13 = safeDivideTens(cache[36325] ?: 0),
                polygon3PointY14 = safeDivideTens(cache[36327] ?: 0),
                polygon3PointY15 = safeDivideTens(cache[36329] ?: 0),
                polygon3PointY16 = safeDivideTens(cache[36331] ?: 0),
                polygon3PointY17 = safeDivideTens(cache[36333] ?: 0),
                polygon3PointY18 = safeDivideTens(cache[36335] ?: 0),
                polygon3PointY19 = safeDivideTens(cache[36337] ?: 0),
                polygon3PointY20 = safeDivideTens(cache[36339] ?: 0),
            ),
            polygon4Point = Polygon4Point(
                polygon4PointX01 = safeDivideTens(cache[36400] ?: 0),
                polygon4PointX02 = safeDivideTens(cache[36402] ?: 0),
                polygon4PointX03 = safeDivideTens(cache[36404] ?: 0),
                polygon4PointX04 = safeDivideTens(cache[36406] ?: 0),
                polygon4PointX05 = safeDivideTens(cache[36408] ?: 0),
                polygon4PointX06 = safeDivideTens(cache[36410] ?: 0),
                polygon4PointX07 = safeDivideTens(cache[36412] ?: 0),
                polygon4PointX08 = safeDivideTens(cache[36414] ?: 0),
                polygon4PointX09 = safeDivideTens(cache[36416] ?: 0),
                polygon4PointX10 = safeDivideTens(cache[36418] ?: 0),
                polygon4PointX11 = safeDivideTens(cache[36420] ?: 0),
                polygon4PointX12 = safeDivideTens(cache[36422] ?: 0),
                polygon4PointX13 = safeDivideTens(cache[36424] ?: 0),
                polygon4PointX14 = safeDivideTens(cache[36426] ?: 0),
                polygon4PointX15 = safeDivideTens(cache[36428] ?: 0),
                polygon4PointX16 = safeDivideTens(cache[36430] ?: 0),
                polygon4PointX17 = safeDivideTens(cache[36432] ?: 0),
                polygon4PointX18 = safeDivideTens(cache[36434] ?: 0),
                polygon4PointX19 = safeDivideTens(cache[36436] ?: 0),
                polygon4PointX20 = safeDivideTens(cache[36438] ?: 0),

                polygon4PointY01 = safeDivideTens(cache[36401] ?: 0),
                polygon4PointY02 = safeDivideTens(cache[36403] ?: 0),
                polygon4PointY03 = safeDivideTens(cache[36405] ?: 0),
                polygon4PointY04 = safeDivideTens(cache[36407] ?: 0),
                polygon4PointY05 = safeDivideTens(cache[36409] ?: 0),
                polygon4PointY06 = safeDivideTens(cache[36411] ?: 0),
                polygon4PointY07 = safeDivideTens(cache[36413] ?: 0),
                polygon4PointY08 = safeDivideTens(cache[36415] ?: 0),
                polygon4PointY09 = safeDivideTens(cache[36417] ?: 0),
                polygon4PointY10 = safeDivideTens(cache[36419] ?: 0),
                polygon4PointY11 = safeDivideTens(cache[36421] ?: 0),
                polygon4PointY12 = safeDivideTens(cache[36423] ?: 0),
                polygon4PointY13 = safeDivideTens(cache[36425] ?: 0),
                polygon4PointY14 = safeDivideTens(cache[36427] ?: 0),
                polygon4PointY15 = safeDivideTens(cache[36429] ?: 0),
                polygon4PointY16 = safeDivideTens(cache[36431] ?: 0),
                polygon4PointY17 = safeDivideTens(cache[36433] ?: 0),
                polygon4PointY18 = safeDivideTens(cache[36435] ?: 0),
                polygon4PointY19 = safeDivideTens(cache[36437] ?: 0),
                polygon4PointY20 = safeDivideTens(cache[36439] ?: 0),
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
                modbusRepository.writeRegister(63900, 1)
                delay(200)
                modbusRepository.writeRegister(63900, 0)
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
                modbusRepository.writeRegister(63910, 1)
                delay(200)
                modbusRepository.writeRegister(63910, 0)
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
                modbusRepository.writeRegister(63901, 1)
                delay(200)
                modbusRepository.writeRegister(63901, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Запретить работу вверху полигона №1
    fun onSetP1UpEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63911, 1)
                delay(200)
                modbusRepository.writeRegister(63911, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Запретить работу внизу полигона №1
    fun onSetP1DownEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63912, 1)
                delay(200)
                modbusRepository.writeRegister(63912, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Ввод количество точек полигона №1
    fun onSetValueP1PointNum() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValuePolygon1PointNum.value, 0, 20 )
                // Запись в регистры
                modbusRepository.writeRegister(63914, model)
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
                modbusRepository.writeRegister(63913, model)
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
                modbusRepository.writeRegister(63952, 1)
                delay(200)
                modbusRepository.writeRegister(63952, 0)
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
                modbusRepository.writeRegister(63953, 1)
                delay(200)
                modbusRepository.writeRegister(63953, 0)
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
                modbusRepository.writeRegister(63951, index)
                delay(200)
                modbusRepository.writeRegister(63951, 0)
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
                modbusRepository.writeRegister(63956, model)
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
                modbusRepository.writeRegister(63957, model)
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
                modbusRepository.writeRegister(63958, model)
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
                modbusRepository.writeRegister(63954, model)
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
                modbusRepository.writeRegister(63955, model)
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
                modbusRepository.writeRegister(63950, 1)
                delay(200)
                modbusRepository.writeRegister(63950, 0)
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
                modbusRepository.writeRegister(63902, 1)
                delay(200)
                modbusRepository.writeRegister(63902, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Запретить работу вверху полигона №2
    fun onSetP2UpEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63921, 1)
                delay(200)
                modbusRepository.writeRegister(63921, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Запретить работу внизу полигона №2
    fun onSetP2DownEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63922, 1)
                delay(200)
                modbusRepository.writeRegister(63922, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Ввод количество точек полигона №2
    fun onSetValueP2PointNum() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValuePolygon2PointNum.value, 0, 20 )
                // Запись в регистры
                modbusRepository.writeRegister(63924, model)
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
                modbusRepository.writeRegister(63923, model)
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
                modbusRepository.writeRegister(63962, 1)
                delay(200)
                modbusRepository.writeRegister(63962, 0)
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
                modbusRepository.writeRegister(63963, 1)
                delay(200)
                modbusRepository.writeRegister(63963, 0)
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
                modbusRepository.writeRegister(63961, index)
                delay(200)
                modbusRepository.writeRegister(63961, 0)
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
                modbusRepository.writeRegister(63966, model)
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
                modbusRepository.writeRegister(63967, model)
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
                modbusRepository.writeRegister(63968, model)
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
                modbusRepository.writeRegister(63964, model)
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
                modbusRepository.writeRegister(63965, model)
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
                modbusRepository.writeRegister(63960, 1)
                delay(200)
                modbusRepository.writeRegister(63960, 0)
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
                modbusRepository.writeRegister(63903, 1)
                delay(200)
                modbusRepository.writeRegister(63903, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Запретить работу вверху полигона №3
    fun onSetP3UpEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63931, 1)
                delay(200)
                modbusRepository.writeRegister(63931, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Запретить работу внизу полигона №3
    fun onSetP3DownEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63932, 1)
                delay(200)
                modbusRepository.writeRegister(63932, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Ввод количество точек полигона №3
    fun onSetValueP3PointNum() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValuePolygon3PointNum.value, 0, 20 )
                // Запись в регистры
                modbusRepository.writeRegister(63934, model)
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
                modbusRepository.writeRegister(63933, model)
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
                modbusRepository.writeRegister(6397, 1)
                delay(200)
                modbusRepository.writeRegister(6397, 0)
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
                modbusRepository.writeRegister(63973, 1)
                delay(200)
                modbusRepository.writeRegister(63973, 0)
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
                modbusRepository.writeRegister(63971, index)
                delay(200)
                modbusRepository.writeRegister(63971, 0)
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
                modbusRepository.writeRegister(63976, model)
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
                modbusRepository.writeRegister(63977, model)
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
                modbusRepository.writeRegister(63978, model)
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
                modbusRepository.writeRegister(63974, model)
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
                modbusRepository.writeRegister(63975, model)
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
                modbusRepository.writeRegister(63970, 1)
                delay(200)
                modbusRepository.writeRegister(63970, 0)
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
                modbusRepository.writeRegister(63904, 1)
                delay(200)
                modbusRepository.writeRegister(63904, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Запретить работу вверху полигона №4
    fun onSetP4UpEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63941, 1)
                delay(200)
                modbusRepository.writeRegister(63941, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Запретить работу внизу полигона №4
    fun onSetP4DownEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63942, 1)
                delay(200)
                modbusRepository.writeRegister(63942, 0)
            } catch (e: Exception) { }
        }
    }
    // Настройка. Ввод количество точек полигона №4
    fun onSetValueP4PointNum() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValuePolygon4PointNum.value, 0, 20 )
                // Запись в регистры
                modbusRepository.writeRegister(63944, model)
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
                modbusRepository.writeRegister(63943, model)
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
                modbusRepository.writeRegister(63982, 1)
                delay(200)
                modbusRepository.writeRegister(63982, 0)
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
                modbusRepository.writeRegister(63983, 1)
                delay(200)
                modbusRepository.writeRegister(63983, 0)
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
                modbusRepository.writeRegister(63981, index)
                delay(200)
                modbusRepository.writeRegister(63981, 0)
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
                modbusRepository.writeRegister(63986, model)
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
                modbusRepository.writeRegister(63987, model)
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
                modbusRepository.writeRegister(63988, model)
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
                modbusRepository.writeRegister(63984, model)
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
                modbusRepository.writeRegister(63985, model)
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
                modbusRepository.writeRegister(63980, 1)
                delay(200)
                modbusRepository.writeRegister(63980, 0)
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