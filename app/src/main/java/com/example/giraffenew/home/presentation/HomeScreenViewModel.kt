package com.example.giraffenew.home.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.entities.UiHomeScreenRegisterConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Int


/**
В ViewModel сделать фильтрацию: преобразовать поток так, чтобы он эмитил только когда изменился один из нужных регистров.
Мы пойдем по пути (б), но с одним улучшением: будем запоминать предыдущий отфильтрованный набор значений и сравнивать с новым.
Если отфильтрованный набор не изменился, то не будем обновлять состояние.
 */

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val modbusRepository: ModbusRepository,
) : ViewModel() {

    val addressesCraneOperatingParameters = listOf(33503,33504,33130,33120,33110,33111,33112,33140,33010,30011,30012,30013,33203) // Только нужные регистры
    val addressesCraneParameterScale = listOf(33203,33110,33140,33141,33209,33012,33011,33013,33003, 39010, 33165, 39009, 30105, 31420, 33551, 33552) // Только нужные регистры

    val addressesInformation = listOf(33001, 33002, 30117, 30120, 39000, 39001, 39002, 39003, 39004, 39005, 39006, 39007, 39008,
        39013, 39014, 39015, 39016, 39017, 39018, 39020, 39021, 39022, 39023, 39024, 39025, 39026, 39027, 39028, 39029 ) // Только нужные регистры
    val addressesCraneMechanismsCondition = listOf(39011, 33215,33216,33211,33212,33213,33214,33217,33218,33227,33228,
        33223,33224,33225,33226,33221,33222,39012,33210, 31103, 31203, 31303, 31403) // Только нужные регистры

    val addressesCraneSOZR = listOf(33230, 33231, 33232, 33233, 33234, 33901, 33902, 33903, 33904,
        34100, 34101, 34102, 34103, 34104, 34105, 34106, 34107, 34108, 34109,
        34110, 34111, 34112, 34113, 34114, 34115, 34116, 34117, 34118, 34119,
        34120, 34121, 34122, 34123, 34124, 34125, 34126, 34127, 34128, 34129,
        34130, 34131, 34132, 34133, 34134, 34135, 34136, 34137, 34138, 34139,
        34140, 34141, 34142, 34143, 34144, 34145, 34146, 34147, 34148, 34149,
        34150, 34151, 34152, 34153, 34154, 34155, 34156, 34157, 34158, 34159,
        34160, 34161, 34162, 34163, 34164, 34165, 34166, 34167, 34168, 34169,
        34170, 34171, 34172, 34173, 34174, 34175, 34176, 34177, 34178, 34179,
        34180, 34181, 34182, 34183, 34184, 34185, 34186, 34187, 34188, 34189,
        34190, 34191, 34192, 34193, 34194, 34195, 34196, 34197, 34198, 34199,

        34200, 34201, 34202, 34203, 34204, 34205, 34206, 34207, 34208, 34209,
        34210, 34211, 34212, 34213, 34214, 34215, 34216, 34217, 34218, 34219,
        34220, 34221, 34222, 34223, 34224, 34225, 34226, 34227, 34228, 34229,
        34230, 34231, 34232, 34233, 34234, 34235, 34236, 34237, 34238, 34239,
        34240, 34241, 34242, 34243, 34244, 34245, 34246, 34247, 34248, 34249,
        34250, 34251, 34252, 34253, 34254, 34255, 34256, 34257, 34258, 34259,
        34260, 34261, 34262, 34263, 34264, 34265, 34266, 34267, 34268, 34269,
        34270, 34271, 34272, 34273, 34274, 34275, 34276, 34277, 34278, 34279,
        34280, 34281, 34282, 34283, 34284, 34285, 34286, 34287, 34288, 34289,
        34290, 34291, 34292, 34293, 34294, 34295, 34296, 34297, 34298, 34299,

        34300, 34301, 34302, 34303, 34304, 34305, 34306, 34307, 34308, 34309,
        34310, 34311, 34312, 34313, 34314, 34315, 34316, 34317, 34318, 34319,
        34320, 34321, 34322, 34323, 34324, 34325, 34326, 34327, 34328, 34329,
        34330, 34331, 34332, 34333, 34334, 34335, 34336, 34337, 34338, 34339,
        34340, 34341, 34342, 34343, 34344, 34345, 34346, 34347, 34348, 34349,
        34350, 34351, 34352, 34353, 34354, 34355, 34356, 34357, 34358, 34359,
        34360, 34361, 34362, 34363, 34364, 34365, 34366, 34367, 34368, 34369,
        34370, 34371, 34372, 34373, 34374, 34375, 34376, 34377, 34378, 34379,
        34380, 34381, 34382, 34383, 34384, 34385, 34386, 34387, 34388, 34389,
        34390, 34391, 34392, 34393, 34394, 34395, 34396, 34397, 34398, 34399,

        34400, 34401, 34402, 34403, 34404, 34405, 34406, 34407, 34408, 34409,
        34410, 34411, 34412, 34413, 34414, 34415, 34416, 34417, 34418, 34419,
        34420, 34421, 34422, 34423, 34424, 34425, 34426, 34427, 34428, 34429,
        34430, 34431, 34432, 34433, 34434, 34435, 34436, 34437, 34438, 34439,
        34440, 34441, 34442, 34443, 34444, 34445, 34446, 34447, 34448, 34449,
        34450, 34451, 34452, 34453, 34454, 34455, 34456, 34457, 34458, 34459,
        34460, 34461, 34462, 34463, 34464, 34465, 34466, 34467, 34468, 34469,
        34470, 34471, 34472, 34473, 34474, 34475, 34476, 34477, 34478, 34479,
        34480, 34481, 34482, 34483, 34484, 34485, 34486, 34487, 34488, 34489,
        34490, 34491, 34492, 34493, 34494, 34495, 34496, 34497, 34498, 34499)

    //1. Объединим все адреса в один список
    private val allAddresses = (addressesCraneOperatingParameters + addressesCraneParameterScale
            + addressesCraneMechanismsCondition+addressesCraneSOZR+addressesInformation).distinct()

    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    private var dataCollectionJob: Job? = null

    /*
    //1.1 Создадим переменные и функцию для управления уровнем громкости звуковых сигналов на главном экрана
    private val _currentVolume = mutableStateOf(0.1f) // Начальное значение Low. Уровень громкости для звукового оповешения
    val currentVolume: State<Float> = _currentVolume

    private val volumeLevels = listOf(0.1f, 0.3f, 0.5f) //Low, Medium, Max

    init {
        // Установим начальную громкость в soundController
        soundController.setVolume(_currentVolume.value)
    }

    fun toggleVolume() {
        val currentIndex = volumeLevels.indexOf(_currentVolume.value)
        val nextIndex = (currentIndex + 1) % volumeLevels.size
        _currentVolume.value = volumeLevels[nextIndex]
        // Обновляем громкость в soundController
        soundController.setVolume(_currentVolume.value)
    }
     */

    /**
    2. В коллекторе потока registerCache, будем преобразовывать весь кэш в отфильтрованную карту (только наши адреса).
    Далее, мы можем сохранять предыдущую отфильтрованную карту и сравнивать с новой. Если они одинаковые, то ничего не делаем.
    Если изменилась, то вычисляем новое состояние и обновляем _state.
     */

    fun setActiveConfiguration() {
        modbusRepository.setActiveUiConfiguration(UiHomeScreenRegisterConfiguration.NAME_HOME)
        startDataCollection()
    }

    fun clearActiveConfiguration() {
        pauseDataCollection()
        modbusRepository.setActiveUiConfiguration(null)
    }

    // Запуск сбора данных
    fun startDataCollection() {
        dataCollectionJob?.cancel() // Всегда отменяем предыдущую job

        Log.d("HomeScreenVM", "Starting data collection for ${UiHomeScreenRegisterConfiguration.NAME_HOME}")
        dataCollectionJob = viewModelScope.launch(Dispatchers.IO) {
            modbusRepository.getConfigurationFlow(UiHomeScreenRegisterConfiguration.NAME_HOME)
                .collect { cache ->
                    Log.d("HomeScreenViewModel", "Updating from cache, size: ${cache.size}")
                    // Обработка данных
                    try {
                        // Создаем отфильтрованную карту только для нужных адресов
                        val filteredCache = allAddresses.associate { addr ->
                            addr to cache[addr]?.value
                        }
                        _state.value = createHomeScreenState(filteredCache)
                    } catch (e: Exception) {
                        Log.e("HomeScreenVM", "Data collection error", e)
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

    //Функция createHomeScreenState() для обработки отфильтрованных данных:
    private fun createHomeScreenState(cache: Map<Int, Int?>): HomeScreenState {
        return HomeScreenState(
            // Crane Operating Parameters
            operatingParams = CraneOperatingParametersState(
                weightQnom = safeDivideThousands(cache[33503] ?: 0),
                weightCurrent = safeDivideTens(cache[33504] ?: 0),
                trolleyCurrent = safeDivideTens(cache[33130] ?: 0),
                slewingCurrent = safeDivideTens(cache[33120] ?: 0),
                heightCurrent = safeDivideTens(cache[33110] ?: 0),
                moveCurrent = safeDivideTens(cache[33140] ?: 0),
                windCurrent = safeDivideTens(cache[33010] ?: 0),
                voltageL1L2Current = safeDivideTens(cache[30011] ?: 0),
                voltageL2L3Current = safeDivideTens(cache[30012] ?: 0),
                voltageL1L3Current = safeDivideTens(cache[30013] ?: 0),
            ),
            // Crane Parameter Scale
            parameterScale = CraneParameterScaleState(
                weightPercent = safeDivide(cache[33203] ?: 0),
                hoistPosition = safeDivideTens(cache[33110] ?: 0),
                hoistMaxPosition = safeDivideTens(cache[33111] ?: 0),
                hoistMinPosition = safeDivideTens(cache[33112] ?: 0),
                cranePosition = safeDivideTens(cache[33140] ?: 0),
                craneMaxPosition = safeDivideTens(cache[33141] ?: 0),

                windCurrent = safeDivideTens(cache[33010] ?: 0),
                windSpeedLvl1 = safeDivideTens (cache[33011] ?: 0),
                windSpeedLvl2 = safeDivideTens (cache[33012] ?: 0),
                windSpeedMontage = safeDivideTens (cache[176] ?: 0),
                anemometerStatus = cache[33209] ?: 0,

                icMontageMode         = cache[33003] ?: 0, //Флаг установки режима Монтаж.
                icCraneRepPointFault  = cache[39010] ?: 0, //Сбой датчика реперной точки хода крана
                icCraneRepPoint       = cache[33165] ?: 0, //Реперная точка хода крана. Индикатор срабатывания
                icLoadMomentSensorAlarm = cache[39009] ?: 0, //Авария датчика критического грузового момента.
                icHoistBrakeSensor    = cache[30105] ?: 0, //Контроль износа тормоза подъема. 1-норма. 0-Авария
                icCraneRailsEn        = cache[31420] ?: 0, //Флаг включения перемещения крана. 1 - Ход крана активирован

                ictechnicalReglamentTO = cache[33551] ?: 0, //Флаг проведите ТО
                icalarmTO = cache[33552] ?: 0, //Флаг. ТО не проведено. Ограничение скорости
            ),
            // Mechanisms Condition
            craneMechanismsCondition = CraneMechanismsConditionState(
                weightSensorErr = cache[39011] ?: 0,
                trolleyFwdStatus = cache[33215] ?: 0,
                trolleyRevStatus = cache[33216] ?: 0,
                hoistUpStatus = cache[33211] ?: 0,
                hoistDownStatus = cache[33212] ?: 0,
                slewRightStatus = cache[33213] ?: 0,
                slewLeftStatus = cache[33214] ?: 0,
                cranRightStatus = cache[33217] ?: 0,
                craneLeftStatus = cache[33218] ?: 0,
                craneRight = cache[33227] ?: 0,
                craneLeft = cache[33228] ?: 0,
                slewLeft = cache[33223] ?: 0,
                slewRight = cache[33224] ?: 0,
                trolleyFwd = cache[33225] ?: 0,
                trolleyRev = cache[33226] ?: 0,
                hoistUp = cache[33221] ?: 0,
                hoistDown = cache[33222] ?: 0,
                anemometerSensorErr = cache[39012] ?: 0,
                alarmLampStatus = cache[33210] ?: 0,

                hoistControlEffortHz = safeDivideHundred(cache[31103] ?: 0),
                slewControlEffortHz = safeDivideHundred(cache[31203] ?: 0),
                trolleyControlEffortHz = safeDivideHundred(cache[31303] ?: 0),
                craneControlEffortHz = safeDivideHundred(cache[31403] ?: 0),

                ),
            //Infirmation
            information = InformationState(
                icBuildMode   = cache[33001] ?: 0,
                icServiceMode = cache[33002] ?: 0,

                icACSSPWR                 = cache[30117] ?: 0,      //АЦСС питание в норме
                icACSSFault               = cache[30120] ?: 0,     //АЦСС в ошибке
                icLukStatus               = cache[39000] ?: 0,     //Состояние конечного выключателя люка ОПУ
                icLineContactorZeroPosAlarm   = cache[39001] ?: 0, //Авария нулевой блокировки линейного контактора
                icMicroclimateHeaterFault     = cache[39002] ?: 0,   //вария обогрева технического помещения
                icBrakePowerSupplyFault       = cache[39003] ?: 0,    //Авария питания тормозов
                icVentilationPowerSupplyFault = cache[39004] ?: 0, //Авария питания вентиляции электродвигателей
                icHoistJoystickError      = cache[39005] ?: 0,    //Ошибка джойстика механизма подъема
                icSlewJoystickError       = cache[39006] ?: 0,     //Ошибка джойстика механизма поворота
                icTrolleyJoystickError    = cache[39007] ?: 0,  //Ошибка джойстика механизма тележки
                icCraneJoystickError      = cache[39008] ?: 0,    //Ошибка джойстика механизма хода крана
                icCraneEncoderFault       = cache[39013] ?: 0,     //Сбой энкодера механизма хода крана
                icHoistEncoderFault       = cache[39014] ?: 0,     //Сбой энкодера механизма хода крана
                icSlewEncoderFault        = cache[39015] ?: 0,      //Сбой энкодера механизма поворота
                icTrolleyEncoderFault     = cache[39016] ?: 0,   //Сбой энкодера механизма тележки
                icPhaseControlRelayFault  = cache[39017] ?: 0, //Авария реле контроля фаз
                icPowerSupplyNetworkFault = cache[39018] ?: 0, //Авария питающей сети
                icHoistDriveError         = cache[39020] ?: 0, //Ошибка частотного преобразователя подъема
                icSlewDriveError          = cache[39021] ?: 0, //Ошибка частотного преобразователя поворота
                icTrolleyDriveError       = cache[39022] ?: 0, //Ошибка частотного преобразователя тележки
                icCraneDriveError         = cache[39023] ?: 0, //Ошибка частотного преобразователя хода крана
                icHoistDrivePowerError    = cache[39024] ?: 0, //Отключение питания частотного преобразователя подъема
                icSlewDrivePowerError     = cache[39025] ?: 0, //Отключение питания частотного преобразователя поворота
                icTrolleyDrivePowerError  = cache[39026] ?: 0, //Отключение питания частотного преобразователя тележки
                icCraneDrivePowerError    = cache[39027] ?: 0, //Отключение питания частотного преобразователя хода крана
                icCANOpenSensorsError     = cache[39028] ?: 0, //Авария сети CANopen (датчики)
                icCANOpenDrivesError      = cache[39029] ?: 0, //Авария сети CANopen (частотные преобразователи)
            ),
            //SOZR
            sozrParams = CraneSozrState(
                hmiLineAngle = safeDivideTens(cache[33230] ?: 0),
                hmiTrolleyCoordinateY = safeDivideTens(cache[33231] ?: 0),
                hmiTrolleyCoordinateX = safeDivideTens(cache[33232] ?: 0),
                hmiCircleSizeTrolley = cache[33233] ?: 0,
                hmiStrelaLenght = safeDivideTens(cache[33234] ?: 0),
                hmiP1Color = cache[33901] ?: 0,
                hmiP2Color = cache[33902] ?: 0,
                hmiP3Color = cache[33903] ?: 0,
                hmiP4Color = cache[33904] ?: 0,
            ),
            //Polygon #1
            polygonP1 = PolygonP1State(
                segments = SegmentDataP1(
                    hmiSegmentP1Length00 = cache[34100] ?: 0, // Длина сегмента №00
                    hmiSegmentP1Length01 = cache[34101] ?: 0, // Длина сегмента №01
                    hmiSegmentP1Length02 = cache[34102] ?: 0, // Длина сегмента №02
                    hmiSegmentP1Length03 = cache[34103] ?: 0, // Длина сегмента №03
                    hmiSegmentP1Length04 = cache[34104] ?: 0, // Длина сегмента №04
                    hmiSegmentP1Length05 = cache[34105] ?: 0, // Длина сегмента №05
                    hmiSegmentP1Length06 = cache[34106] ?: 0, // Длина сегмента №06
                    hmiSegmentP1Length07 = cache[34107] ?: 0, // Длина сегмента №07
                    hmiSegmentP1Length08 = cache[34108] ?: 0, // Длина сегмента №08
                    hmiSegmentP1Length09 = cache[34109] ?: 0, // Длина сегмента №09
                    hmiSegmentP1Length10 = cache[34110] ?: 0, // Длина сегмента №10
                    hmiSegmentP1Length11 = cache[34111] ?: 0, // Длина сегмента №11
                    hmiSegmentP1Length12 = cache[34112] ?: 0, // Длина сегмента №12
                    hmiSegmentP1Length13 = cache[34113] ?: 0, // Длина сегмента №13
                    hmiSegmentP1Length14 = cache[34114] ?: 0, // Длина сегмента №14
                    hmiSegmentP1Length15 = cache[34115] ?: 0, // Длина сегмента №15
                    hmiSegmentP1Length16 = cache[34116] ?: 0, // Длина сегмента №16
                    hmiSegmentP1Length17 = cache[34117] ?: 0, // Длина сегмента №17
                    hmiSegmentP1Length18 = cache[34118] ?: 0, // Длина сегмента №18
                    hmiSegmentP1Length19 = cache[34119] ?: 0, // Длина сегмента №19
                ),
                points = PointDataP1(
                    pointP1PositionY00 = cache[34120] ?: 0, // Координата Y точки №00
                    pointP1PositionY01 = cache[34121] ?: 0, // Координата Y точки №01
                    pointP1PositionY02 = cache[34122] ?: 0, // Координата Y точки №02
                    pointP1PositionY03 = cache[34123] ?: 0, // Координата Y точки №03
                    pointP1PositionY04 = cache[34124] ?: 0, // Координата Y точки №04
                    pointP1PositionY05 = cache[34125] ?: 0, // Координата Y точки №05
                    pointP1PositionY06 = cache[34126] ?: 0, // Координата Y точки №06
                    pointP1PositionY07 = cache[34127] ?: 0, // Координата Y точки №07
                    pointP1PositionY08 = cache[34128] ?: 0, // Координата Y точки №08
                    pointP1PositionY09 = cache[34129] ?: 0, // Координата Y точки №09
                    pointP1PositionY10 = cache[34130] ?: 0, // Координата Y точки №10
                    pointP1PositionY11 = cache[34131] ?: 0, // Координата Y точки №11
                    pointP1PositionY12 = cache[34132] ?: 0, // Координата Y точки №12
                    pointP1PositionY13 = cache[34133] ?: 0, // Координата Y точки №13
                    pointP1PositionY14 = cache[34134] ?: 0, // Координата Y точки №14
                    pointP1PositionY15 = cache[34135] ?: 0, // Координата Y точки №15
                    pointP1PositionY16 = cache[34136] ?: 0, // Координата Y точки №16
                    pointP1PositionY17 = cache[34137] ?: 0, // Координата Y точки №17
                    pointP1PositionY18 = cache[34138] ?: 0, // Координата Y точки №18
                    pointP1PositionY19 = cache[34139] ?: 0, // Координата Y точки №19

                    pointP1PositionX00 = cache[34140] ?: 0, // Координата X точки №00
                    pointP1PositionX01 = cache[34141] ?: 0, // Координата X точки №01
                    pointP1PositionX02 = cache[34142] ?: 0, // Координата X точки №02
                    pointP1PositionX03 = cache[34143] ?: 0, // Координата X точки №03
                    pointP1PositionX04 = cache[34144] ?: 0, // Координата X точки №04
                    pointP1PositionX05 = cache[34145] ?: 0, // Координата X точки №05
                    pointP1PositionX06 = cache[34146] ?: 0, // Координата X точки №06
                    pointP1PositionX07 = cache[34147] ?: 0, // Координата X точки №07
                    pointP1PositionX08 = cache[34148] ?: 0, // Координата X точки №08
                    pointP1PositionX09 = cache[34149] ?: 0, // Координата X точки №09
                    pointP1PositionX10 = cache[34150] ?: 0, // Координата X точки №10
                    pointP1PositionX11 = cache[34151] ?: 0, // Координата X точки №11
                    pointP1PositionX12 = cache[34152] ?: 0, // Координата X точки №12
                    pointP1PositionX13 = cache[34153] ?: 0, // Координата X точки №13
                    pointP1PositionX14 = cache[34154] ?: 0, // Координата X точки №14
                    pointP1PositionX15 = cache[34155] ?: 0, // Координата X точки №15
                    pointP1PositionX16 = cache[34156] ?: 0, // Координата X точки №16
                    pointP1PositionX17 = cache[34157] ?: 0, // Координата X точки №17
                    pointP1PositionX18 = cache[34158] ?: 0, // Координата X точки №18
                    pointP1PositionX19 = cache[34159] ?: 0, // Координата X точки №19
                ),
                angles = AngleDataP1(
                    segmentP1Angle00 = cache[34160] ?: 0, //Угол между сегментами 00-01
                    segmentP1Angle01 = cache[34161] ?: 0, //Угол между сегментами 01-02
                    segmentP1Angle02 = cache[34162] ?: 0, //Угол между сегментами 02-03
                    segmentP1Angle03 = cache[34163] ?: 0, //Угол между сегментами 03-04
                    segmentP1Angle04 = cache[34164] ?: 0, //Угол между сегментами 04-05
                    segmentP1Angle05 = cache[34165] ?: 0, //Угол между сегментами 05-06
                    segmentP1Angle06 = cache[34166] ?: 0, //Угол между сегментами 06-07
                    segmentP1Angle07 = cache[34167] ?: 0, //Угол между сегментами 07-08
                    segmentP1Angle08 = cache[34168] ?: 0, //Угол между сегментами 08-09
                    segmentP1Angle09 = cache[34169] ?: 0, //Угол между сегментами 09-10
                    segmentP1Angle10 = cache[34170] ?: 0, //Угол между сегментами 10-11
                    segmentP1Angle11 = cache[34171] ?: 0, //Угол между сегментами 11-12
                    segmentP1Angle12 = cache[34172] ?: 0, //Угол между сегментами 12-13
                    segmentP1Angle13 = cache[34173] ?: 0, //Угол между сегментами 13-14
                    segmentP1Angle14 = cache[34174] ?: 0, //Угол между сегментами 14-15
                    segmentP1Angle15 = cache[34175] ?: 0, //Угол между сегментами 15-16
                    segmentP1Angle16 = cache[34176] ?: 0, //Угол между сегментами 16-17
                    segmentP1Angle17 = cache[34177] ?: 0, //Угол между сегментами 17-18
                    segmentP1Angle18 = cache[34178] ?: 0, //Угол между сегментами 18-19
                    segmentP1Angle19 = cache[34179] ?: 0, //Угол между сегментами 19-00
                ),
                visibility = VisibilityDataP1(
                    segmentP1Visibility00 = cache[34180] ?: 0, //Видимость сегмента №00
                    segmentP1Visibility01 = cache[34181] ?: 0, //Видимость сегмента №01
                    segmentP1Visibility02 = cache[34182] ?: 0, //Видимость сегмента №02
                    segmentP1Visibility03 = cache[34183] ?: 0, //Видимость сегмента №03
                    segmentP1Visibility04 = cache[34184] ?: 0, //Видимость сегмента №04
                    segmentP1Visibility05 = cache[34185] ?: 0, //Видимость сегмента №05
                    segmentP1Visibility06 = cache[34186] ?: 0, //Видимость сегмента №06
                    segmentP1Visibility07 = cache[34187] ?: 0, //Видимость сегмента №07
                    segmentP1Visibility08 = cache[34188] ?: 0, //Видимость сегмента №08
                    segmentP1Visibility09 = cache[34189] ?: 0, //Видимость сегмента №09
                    segmentP1Visibility10 = cache[34190] ?: 0, //Видимость сегмента №10
                    segmentP1Visibility11 = cache[34191] ?: 0, //Видимость сегмента №11
                    segmentP1Visibility12 = cache[34192] ?: 0, //Видимость сегмента №12
                    segmentP1Visibility13 = cache[34193] ?: 0, //Видимость сегмента №13
                    segmentP1Visibility14 = cache[34194] ?: 0, //Видимость сегмента №14
                    segmentP1Visibility15 = cache[34195] ?: 0, //Видимость сегмента №15
                    segmentP1Visibility16 = cache[34196] ?: 0, //Видимость сегмента №16
                    segmentP1Visibility17 = cache[34197] ?: 0, //Видимость сегмента №17
                    segmentP1Visibility18 = cache[34198] ?: 0, //Видимость сегмента №18
                    segmentP1Visibility19 = cache[34199] ?: 0, //Видимость сегмента №19
                ),

                ),
            //Polygon #2
            polygonP2 = PolygonP2State(
                segments = SegmentDataP2(
                    // Параметры отезков для отображения полигона №2
                    hmiSegmentP2Length00 = cache[34200] ?: 0, // Длина сегмента №00
                    hmiSegmentP2Length01 = cache[34201] ?: 0, // Длина сегмента №01
                    hmiSegmentP2Length02 = cache[34202] ?: 0, // Длина сегмента №02
                    hmiSegmentP2Length03 = cache[34203] ?: 0, // Длина сегмента №03
                    hmiSegmentP2Length04 = cache[34204] ?: 0, // Длина сегмента №04
                    hmiSegmentP2Length05 = cache[34205] ?: 0, // Длина сегмента №05
                    hmiSegmentP2Length06 = cache[34206] ?: 0, // Длина сегмента №06
                    hmiSegmentP2Length07 = cache[34207] ?: 0, // Длина сегмента №07
                    hmiSegmentP2Length08 = cache[34208] ?: 0, // Длина сегмента №08
                    hmiSegmentP2Length09 = cache[34209] ?: 0, // Длина сегмента №09
                    hmiSegmentP2Length10 = cache[34210] ?: 0, // Длина сегмента №10
                    hmiSegmentP2Length11 = cache[34211] ?: 0, // Длина сегмента №11
                    hmiSegmentP2Length12 = cache[34212] ?: 0, // Длина сегмента №12
                    hmiSegmentP2Length13 = cache[34213] ?: 0, // Длина сегмента №13
                    hmiSegmentP2Length14 = cache[34214] ?: 0, // Длина сегмента №14
                    hmiSegmentP2Length15 = cache[34215] ?: 0, // Длина сегмента №15
                    hmiSegmentP2Length16 = cache[34216] ?: 0, // Длина сегмента №16
                    hmiSegmentP2Length17 = cache[34217] ?: 0, // Длина сегмента №17
                    hmiSegmentP2Length18 = cache[34218] ?: 0, // Длина сегмента №18
                    hmiSegmentP2Length19 = cache[34219] ?: 0, // Длина сегмента №19
                ),
                points = PointDataP2(
                    // Параметры отезков для отображения полигона №2
                    pointP2PositionY00 = cache[34220] ?: 0, // Координата Y точки №00
                    pointP2PositionY01 = cache[34221] ?: 0, // Координата Y точки №00
                    pointP2PositionY02 = cache[34222] ?: 0, // Координата Y точки №00
                    pointP2PositionY03 = cache[34223] ?: 0, // Координата Y точки №00
                    pointP2PositionY04 = cache[34224] ?: 0, // Координата Y точки №00
                    pointP2PositionY05 = cache[34225] ?: 0, // Координата Y точки №00
                    pointP2PositionY06 = cache[34226] ?: 0, // Координата Y точки №00
                    pointP2PositionY07 = cache[34227] ?: 0, // Координата Y точки №00
                    pointP2PositionY08 = cache[34228] ?: 0, // Координата Y точки №00
                    pointP2PositionY09 = cache[34229] ?: 0, // Координата Y точки №00
                    pointP2PositionY10 = cache[34230] ?: 0, // Координата Y точки №00
                    pointP2PositionY11 = cache[34231] ?: 0, // Координата Y точки №00
                    pointP2PositionY12 = cache[34232] ?: 0, // Координата Y точки №00
                    pointP2PositionY13 = cache[34233] ?: 0, // Координата Y точки №00
                    pointP2PositionY14 = cache[34234] ?: 0, // Координата Y точки №00
                    pointP2PositionY15 = cache[34235] ?: 0, // Координата Y точки №00
                    pointP2PositionY16 = cache[34236] ?: 0, // Координата Y точки №00
                    pointP2PositionY17 = cache[34237] ?: 0, // Координата Y точки №00
                    pointP2PositionY18 = cache[34238] ?: 0, // Координата Y точки №00
                    pointP2PositionY19 = cache[34239] ?: 0, // Координата Y точки №00
                    // Параметры отезков для отображения полигона №2
                    pointP2PositionX00 = cache[34240] ?: 0, // Координата X точки №00
                    pointP2PositionX01 = cache[34241] ?: 0, // Координата X точки №00
                    pointP2PositionX02 = cache[34242] ?: 0, // Координата X точки №00
                    pointP2PositionX03 = cache[34243] ?: 0, // Координата X точки №00
                    pointP2PositionX04 = cache[34244] ?: 0, // Координата X точки №00
                    pointP2PositionX05 = cache[34245] ?: 0, // Координата X точки №00
                    pointP2PositionX06 = cache[34246] ?: 0, // Координата X точки №00
                    pointP2PositionX07 = cache[34247] ?: 0, // Координата X точки №00
                    pointP2PositionX08 = cache[34248] ?: 0, // Координата X точки №00
                    pointP2PositionX09 = cache[34249] ?: 0, // Координата X точки №00
                    pointP2PositionX10 = cache[34250] ?: 0, // Координата X точки №00
                    pointP2PositionX11 = cache[34251] ?: 0, // Координата X точки №00
                    pointP2PositionX12 = cache[34252] ?: 0, // Координата X точки №00
                    pointP2PositionX13 = cache[34253] ?: 0, // Координата X точки №00
                    pointP2PositionX14 = cache[34254] ?: 0, // Координата X точки №00
                    pointP2PositionX15 = cache[34255] ?: 0, // Координата X точки №00
                    pointP2PositionX16 = cache[34256] ?: 0, // Координата X точки №00
                    pointP2PositionX17 = cache[34257] ?: 0, // Координата X точки №00
                    pointP2PositionX18 = cache[34258] ?: 0, // Координата X точки №00
                    pointP2PositionX19 = cache[34259] ?: 0, // Координата X точки №00
                ),
                angles = AngleDataP2(
                    // Параметры отезков для отображения полигона №2
                    segmentP2Angle00 = cache[34260] ?: 0, //Угол между сегментами 00-01
                    segmentP2Angle01 = cache[34261] ?: 0, //Угол между сегментами 01-02
                    segmentP2Angle02 = cache[34262] ?: 0, //Угол между сегментами 02-03
                    segmentP2Angle03 = cache[34263] ?: 0, //Угол между сегментами 03-04
                    segmentP2Angle04 = cache[34264] ?: 0, //Угол между сегментами 04-05
                    segmentP2Angle05 = cache[34265] ?: 0, //Угол между сегментами 05-06
                    segmentP2Angle06 = cache[34266] ?: 0, //Угол между сегментами 06-07
                    segmentP2Angle07 = cache[34267] ?: 0, //Угол между сегментами 07-08
                    segmentP2Angle08 = cache[34268] ?: 0, //Угол между сегментами 08-09
                    segmentP2Angle09 = cache[34269] ?: 0, //Угол между сегментами 09-10
                    segmentP2Angle10 = cache[34270] ?: 0, //Угол между сегментами 10-11
                    segmentP2Angle11 = cache[34271] ?: 0, //Угол между сегментами 11-12
                    segmentP2Angle12 = cache[34272] ?: 0, //Угол между сегментами 12-13
                    segmentP2Angle13 = cache[34273] ?: 0, //Угол между сегментами 13-14
                    segmentP2Angle14 = cache[34274] ?: 0, //Угол между сегментами 14-15
                    segmentP2Angle15 = cache[34275] ?: 0, //Угол между сегментами 15-16
                    segmentP2Angle16 = cache[34276] ?: 0, //Угол между сегментами 16-17
                    segmentP2Angle17 = cache[34277] ?: 0, //Угол между сегментами 17-18
                    segmentP2Angle18 = cache[34278] ?: 0, //Угол между сегментами 18-19
                    segmentP2Angle19 = cache[34279] ?: 0, //Угол между сегментами 19-00
                ),
                visibility = VisibilityDataP2(
                    // Параметры отезков для отображения полигона №2
                    segmentP2Visibility00 = cache[34280] ?: 0, //Видимость сегмента №00
                    segmentP2Visibility01 = cache[34281] ?: 0, //Видимость сегмента №01
                    segmentP2Visibility02 = cache[34282] ?: 0, //Видимость сегмента №02
                    segmentP2Visibility03 = cache[34283] ?: 0, //Видимость сегмента №03
                    segmentP2Visibility04 = cache[34284] ?: 0, //Видимость сегмента №04
                    segmentP2Visibility05 = cache[34285] ?: 0, //Видимость сегмента №05
                    segmentP2Visibility06 = cache[34286] ?: 0, //Видимость сегмента №06
                    segmentP2Visibility07 = cache[34287] ?: 0, //Видимость сегмента №07
                    segmentP2Visibility08 = cache[34288] ?: 0, //Видимость сегмента №08
                    segmentP2Visibility09 = cache[34289] ?: 0, //Видимость сегмента №09
                    segmentP2Visibility10 = cache[34290] ?: 0, //Видимость сегмента №10
                    segmentP2Visibility11 = cache[34291] ?: 0, //Видимость сегмента №11
                    segmentP2Visibility12 = cache[34292] ?: 0, //Видимость сегмента №12
                    segmentP2Visibility13 = cache[34293] ?: 0, //Видимость сегмента №13
                    segmentP2Visibility14 = cache[34294] ?: 0, //Видимость сегмента №14
                    segmentP2Visibility15 = cache[34295] ?: 0, //Видимость сегмента №15
                    segmentP2Visibility16 = cache[34296] ?: 0, //Видимость сегмента №16
                    segmentP2Visibility17 = cache[34297] ?: 0, //Видимость сегмента №17
                    segmentP2Visibility18 = cache[34298] ?: 0, //Видимость сегмента №18
                    segmentP2Visibility19 = cache[34299] ?: 0, //Видимость сегмента №19
                ),
            ),
            //Polygon #3
            polygonP3 = PolygonP3State(
                segments = SegmentDataP3(
                    hmiSegmentP3Length00 = cache[34300] ?: 0, // Длина сегмента №00
                    hmiSegmentP3Length01 = cache[34301] ?: 0, // Длина сегмента №01
                    hmiSegmentP3Length02 = cache[34302] ?: 0, // Длина сегмента №02
                    hmiSegmentP3Length03 = cache[34303] ?: 0, // Длина сегмента №03
                    hmiSegmentP3Length04 = cache[34304] ?: 0, // Длина сегмента №04
                    hmiSegmentP3Length05 = cache[34305] ?: 0, // Длина сегмента №05
                    hmiSegmentP3Length06 = cache[3430] ?: 0, // Длина сегмента №06
                    hmiSegmentP3Length07 = cache[34307] ?: 0, // Длина сегмента №07
                    hmiSegmentP3Length08 = cache[34308] ?: 0, // Длина сегмента №08
                    hmiSegmentP3Length09 = cache[34309] ?: 0, // Длина сегмента №09
                    hmiSegmentP3Length10 = cache[34310] ?: 0, // Длина сегмента №10
                    hmiSegmentP3Length11 = cache[34311] ?: 0, // Длина сегмента №11
                    hmiSegmentP3Length12 = cache[34312] ?: 0, // Длина сегмента №12
                    hmiSegmentP3Length13 = cache[34313] ?: 0, // Длина сегмента №13
                    hmiSegmentP3Length14 = cache[34314] ?: 0, // Длина сегмента №14
                    hmiSegmentP3Length15 = cache[34315] ?: 0, // Длина сегмента №15
                    hmiSegmentP3Length16 = cache[34316] ?: 0, // Длина сегмента №16
                    hmiSegmentP3Length17 = cache[34317] ?: 0, // Длина сегмента №17
                    hmiSegmentP3Length18 = cache[34318] ?: 0, // Длина сегмента №18
                    hmiSegmentP3Length19 = cache[34319] ?: 0, // Длина сегмента №19
                ),

                points = PointDataP3(
                    pointP3PositionY00 = cache[34320] ?: 0, // Координата Y точки №00
                    pointP3PositionY01 = cache[34321] ?: 0, // Координата Y точки №00
                    pointP3PositionY02 = cache[34322] ?: 0, // Координата Y точки №00
                    pointP3PositionY03 = cache[34323] ?: 0, // Координата Y точки №00
                    pointP3PositionY04 = cache[34324] ?: 0, // Координата Y точки №00
                    pointP3PositionY05 = cache[34325] ?: 0, // Координата Y точки №00
                    pointP3PositionY06 = cache[34326] ?: 0, // Координата Y точки №00
                    pointP3PositionY07 = cache[34327] ?: 0, // Координата Y точки №00
                    pointP3PositionY08 = cache[34328] ?: 0, // Координата Y точки №00
                    pointP3PositionY09 = cache[34329] ?: 0, // Координата Y точки №00
                    pointP3PositionY10 = cache[34330] ?: 0, // Координата Y точки №00
                    pointP3PositionY11 = cache[34331] ?: 0, // Координата Y точки №00
                    pointP3PositionY12 = cache[34332] ?: 0, // Координата Y точки №00
                    pointP3PositionY13 = cache[34333] ?: 0, // Координата Y точки №00
                    pointP3PositionY14 = cache[34334] ?: 0, // Координата Y точки №00
                    pointP3PositionY15 = cache[34335] ?: 0, // Координата Y точки №00
                    pointP3PositionY16 = cache[34336] ?: 0, // Координата Y точки №00
                    pointP3PositionY17 = cache[34337] ?: 0, // Координата Y точки №00
                    pointP3PositionY18 = cache[34338] ?: 0, // Координата Y точки №00
                    pointP3PositionY19 = cache[34339] ?: 0, // Координата Y точки №00

                    pointP3PositionX00 = cache[34340] ?: 0, // Координата X точки №00
                    pointP3PositionX01 = cache[34341] ?: 0, // Координата X точки №00
                    pointP3PositionX02 = cache[34342] ?: 0, // Координата X точки №00
                    pointP3PositionX03 = cache[34343] ?: 0, // Координата X точки №00
                    pointP3PositionX04 = cache[34344] ?: 0, // Координата X точки №00
                    pointP3PositionX05 = cache[34345] ?: 0, // Координата X точки №00
                    pointP3PositionX06 = cache[34346] ?: 0, // Координата X точки №00
                    pointP3PositionX07 = cache[34347] ?: 0, // Координата X точки №00
                    pointP3PositionX08 = cache[34348] ?: 0, // Координата X точки №00
                    pointP3PositionX09 = cache[34349] ?: 0, // Координата X точки №00
                    pointP3PositionX10 = cache[34350] ?: 0, // Координата X точки №00
                    pointP3PositionX11 = cache[34351] ?: 0, // Координата X точки №00
                    pointP3PositionX12 = cache[34352] ?: 0, // Координата X точки №00
                    pointP3PositionX13 = cache[34353] ?: 0, // Координата X точки №00
                    pointP3PositionX14 = cache[34354] ?: 0, // Координата X точки №00
                    pointP3PositionX15 = cache[34355] ?: 0, // Координата X точки №00
                    pointP3PositionX16 = cache[34356] ?: 0, // Координата X точки №00
                    pointP3PositionX17 = cache[34357] ?: 0, // Координата X точки №00
                    pointP3PositionX18 = cache[34358] ?: 0, // Координата X точки №00
                    pointP3PositionX19 = cache[34359] ?: 0, // Координата X точки №00
                ),

                angles = AngleDataP3(
                    segmentP3Angle00 = cache[34360] ?: 0, //Угол между сегментами 00-01
                    segmentP3Angle01 = cache[34361] ?: 0, //Угол между сегментами 01-02
                    segmentP3Angle02 = cache[34362] ?: 0, //Угол между сегментами 02-03
                    segmentP3Angle03 = cache[34363] ?: 0, //Угол между сегментами 03-04
                    segmentP3Angle04 = cache[34364] ?: 0, //Угол между сегментами 04-05
                    segmentP3Angle05 = cache[34365] ?: 0, //Угол между сегментами 05-06
                    segmentP3Angle06 = cache[34366] ?: 0, //Угол между сегментами 06-07
                    segmentP3Angle07 = cache[34367] ?: 0, //Угол между сегментами 07-08
                    segmentP3Angle08 = cache[34368] ?: 0, //Угол между сегментами 08-09
                    segmentP3Angle09 = cache[34369] ?: 0, //Угол между сегментами 09-10
                    segmentP3Angle10 = cache[34370] ?: 0, //Угол между сегментами 10-11
                    segmentP3Angle11 = cache[34371] ?: 0, //Угол между сегментами 11-12
                    segmentP3Angle12 = cache[34372] ?: 0, //Угол между сегментами 12-13
                    segmentP3Angle13 = cache[34373] ?: 0, //Угол между сегментами 13-14
                    segmentP3Angle14 = cache[34374] ?: 0, //Угол между сегментами 14-15
                    segmentP3Angle15 = cache[34375] ?: 0, //Угол между сегментами 15-16
                    segmentP3Angle16 = cache[34376] ?: 0, //Угол между сегментами 16-17
                    segmentP3Angle17 = cache[34377] ?: 0, //Угол между сегментами 17-18
                    segmentP3Angle18 = cache[34378] ?: 0, //Угол между сегментами 18-19
                    segmentP3Angle19 = cache[34379] ?: 0, //Угол между сегментами 19-00
                ),

                visibility = VisibilityDataP3(
                    segmentP3Visibility00 = cache[34380] ?: 0, //Видимость сегмента №00
                    segmentP3Visibility01 = cache[34381] ?: 0, //Видимость сегмента №01
                    segmentP3Visibility02 = cache[34382] ?: 0, //Видимость сегмента №02
                    segmentP3Visibility03 = cache[34383] ?: 0, //Видимость сегмента №03
                    segmentP3Visibility04 = cache[34384] ?: 0, //Видимость сегмента №04
                    segmentP3Visibility05 = cache[34385] ?: 0, //Видимость сегмента №05
                    segmentP3Visibility06 = cache[34386] ?: 0, //Видимость сегмента №06
                    segmentP3Visibility07 = cache[34387] ?: 0, //Видимость сегмента №07
                    segmentP3Visibility08 = cache[34388] ?: 0, //Видимость сегмента №08
                    segmentP3Visibility09 = cache[34389] ?: 0, //Видимость сегмента №09
                    segmentP3Visibility10 = cache[34390] ?: 0, //Видимость сегмента №10
                    segmentP3Visibility11 = cache[34391] ?: 0, //Видимость сегмента №11
                    segmentP3Visibility12 = cache[34392] ?: 0, //Видимость сегмента №12
                    segmentP3Visibility13 = cache[34393] ?: 0, //Видимость сегмента №13
                    segmentP3Visibility14 = cache[34394] ?: 0, //Видимость сегмента №14
                    segmentP3Visibility15 = cache[34395] ?: 0, //Видимость сегмента №15
                    segmentP3Visibility16 = cache[34396] ?: 0, //Видимость сегмента №16
                    segmentP3Visibility17 = cache[34397] ?: 0, //Видимость сегмента №17
                    segmentP3Visibility18 = cache[34398] ?: 0, //Видимость сегмента №18
                    segmentP3Visibility19 = cache[34399] ?: 0, //Видимость сегмента №19
                ),


                ),
            //Polygon #4
            polygonP4 = PolygonP4State(
                segments = SegmentDataP4(
                    // Параметры отезков для отображения полигона №4
                    hmiSegmentP4Length00 = cache[34400] ?: 0, // Длина сегмента №00
                    hmiSegmentP4Length01 = cache[34401] ?: 0, // Длина сегмента №01
                    hmiSegmentP4Length02 = cache[34402] ?: 0, // Длина сегмента №02
                    hmiSegmentP4Length03 = cache[34403] ?: 0, // Длина сегмента №03
                    hmiSegmentP4Length04 = cache[34404] ?: 0, // Длина сегмента №04
                    hmiSegmentP4Length05 = cache[34405] ?: 0, // Длина сегмента №05
                    hmiSegmentP4Length06 = cache[34406] ?: 0, // Длина сегмента №06
                    hmiSegmentP4Length07 = cache[34407] ?: 0, // Длина сегмента №07
                    hmiSegmentP4Length08 = cache[34408] ?: 0, // Длина сегмента №08
                    hmiSegmentP4Length09 = cache[34409] ?: 0, // Длина сегмента №09
                    hmiSegmentP4Length10 = cache[34410] ?: 0, // Длина сегмента №10
                    hmiSegmentP4Length11 = cache[34411] ?: 0, // Длина сегмента №11
                    hmiSegmentP4Length12 = cache[34412] ?: 0, // Длина сегмента №12
                    hmiSegmentP4Length13 = cache[34413] ?: 0, // Длина сегмента №13
                    hmiSegmentP4Length14 = cache[34414] ?: 0, // Длина сегмента №14
                    hmiSegmentP4Length15 = cache[34415] ?: 0, // Длина сегмента №15
                    hmiSegmentP4Length16 = cache[34416] ?: 0, // Длина сегмента №16
                    hmiSegmentP4Length17 = cache[34417] ?: 0, // Длина сегмента №17
                    hmiSegmentP4Length18 = cache[34418] ?: 0, // Длина сегмента №18
                    hmiSegmentP4Length19 = cache[34419] ?: 0, // Длина сегмента №19
                ),
                points = PointDataP4(
                    // Параметры отезков для отображения полигона №4
                    pointP4PositionY00 = cache[34420] ?: 0, // Координата Y точки №00
                    pointP4PositionY01 = cache[34421] ?: 0, // Координата Y точки №00
                    pointP4PositionY02 = cache[34422] ?: 0, // Координата Y точки №00
                    pointP4PositionY03 = cache[34423] ?: 0, // Координата Y точки №00
                    pointP4PositionY04 = cache[34424] ?: 0, // Координата Y точки №00
                    pointP4PositionY05 = cache[34425] ?: 0, // Координата Y точки №00
                    pointP4PositionY06 = cache[34426] ?: 0, // Координата Y точки №00
                    pointP4PositionY07 = cache[34427] ?: 0, // Координата Y точки №00
                    pointP4PositionY08 = cache[34428] ?: 0, // Координата Y точки №00
                    pointP4PositionY09 = cache[34429] ?: 0, // Координата Y точки №00
                    pointP4PositionY10 = cache[34430] ?: 0, // Координата Y точки №00
                    pointP4PositionY11 = cache[34431] ?: 0, // Координата Y точки №00
                    pointP4PositionY12 = cache[34432] ?: 0, // Координата Y точки №00
                    pointP4PositionY13 = cache[34433] ?: 0, // Координата Y точки №00
                    pointP4PositionY14 = cache[34434] ?: 0, // Координата Y точки №00
                    pointP4PositionY15 = cache[34435] ?: 0, // Координата Y точки №00
                    pointP4PositionY16 = cache[34436] ?: 0, // Координата Y точки №00
                    pointP4PositionY17 = cache[34437] ?: 0, // Координата Y точки №00
                    pointP4PositionY18 = cache[34438] ?: 0, // Координата Y точки №00
                    pointP4PositionY19 = cache[34439] ?: 0, // Координата Y точки №00

                    // Параметры отезков для отображения полигона №4
                    pointP4PositionX00 = cache[34440] ?: 0, // Координата X точки №00
                    pointP4PositionX01 = cache[34441] ?: 0, // Координата X точки №00
                    pointP4PositionX02 = cache[34442] ?: 0, // Координата X точки №00
                    pointP4PositionX03 = cache[34443] ?: 0, // Координата X точки №00
                    pointP4PositionX04 = cache[34444] ?: 0, // Координата X точки №00
                    pointP4PositionX05 = cache[34445] ?: 0, // Координата X точки №00
                    pointP4PositionX06 = cache[34446] ?: 0, // Координата X точки №00
                    pointP4PositionX07 = cache[34447] ?: 0, // Координата X точки №00
                    pointP4PositionX08 = cache[34448] ?: 0, // Координата X точки №00
                    pointP4PositionX09 = cache[34449] ?: 0, // Координата X точки №00
                    pointP4PositionX10 = cache[34450] ?: 0, // Координата X точки №00
                    pointP4PositionX11 = cache[34451] ?: 0, // Координата X точки №00
                    pointP4PositionX12 = cache[34452] ?: 0, // Координата X точки №00
                    pointP4PositionX13 = cache[34453] ?: 0, // Координата X точки №00
                    pointP4PositionX14 = cache[34454] ?: 0, // Координата X точки №00
                    pointP4PositionX15 = cache[34455] ?: 0, // Координата X точки №00
                    pointP4PositionX16 = cache[34456] ?: 0, // Координата X точки №00
                    pointP4PositionX17 = cache[34457] ?: 0, // Координата X точки №00
                    pointP4PositionX18 = cache[34458] ?: 0, // Координата X точки №00
                    pointP4PositionX19 = cache[34459] ?: 0, // Координата X точки №00
                ),

                angles = AngleDataP4(
                    // Параметры отезков для отображения полигона №4
                    segmentP4Angle00 = cache[34460] ?: 0, //Угол между сегментами 00-01
                    segmentP4Angle01 = cache[34461] ?: 0, //Угол между сегментами 01-02
                    segmentP4Angle02 = cache[34462] ?: 0, //Угол между сегментами 02-03
                    segmentP4Angle03 = cache[34463] ?: 0, //Угол между сегментами 03-04
                    segmentP4Angle04 = cache[34464] ?: 0, //Угол между сегментами 04-05
                    segmentP4Angle05 = cache[34465] ?: 0, //Угол между сегментами 05-06
                    segmentP4Angle06 = cache[34466] ?: 0, //Угол между сегментами 06-07
                    segmentP4Angle07 = cache[34467] ?: 0, //Угол между сегментами 07-08
                    segmentP4Angle08 = cache[34468] ?: 0, //Угол между сегментами 08-09
                    segmentP4Angle09 = cache[34469] ?: 0, //Угол между сегментами 09-10
                    segmentP4Angle10 = cache[34470] ?: 0, //Угол между сегментами 10-11
                    segmentP4Angle11 = cache[34471] ?: 0, //Угол между сегментами 11-12
                    segmentP4Angle12 = cache[34472] ?: 0, //Угол между сегментами 12-13
                    segmentP4Angle13 = cache[34473] ?: 0, //Угол между сегментами 13-14
                    segmentP4Angle14 = cache[34474] ?: 0, //Угол между сегментами 14-15
                    segmentP4Angle15 = cache[34475] ?: 0, //Угол между сегментами 15-16
                    segmentP4Angle16 = cache[34476] ?: 0, //Угол между сегментами 16-17
                    segmentP4Angle17 = cache[34477] ?: 0, //Угол между сегментами 17-18
                    segmentP4Angle18 = cache[34478] ?: 0, //Угол между сегментами 18-19
                    segmentP4Angle19 = cache[34479] ?: 0, //Угол между сегментами 19-00
                ),
                visibility = VisibilityDataP4(
                    // Параметры отезков для отображения полигона №4
                    segmentP4Visibility00 = cache[34480] ?: 0, //Видимость сегмента №00
                    segmentP4Visibility01 = cache[34481] ?: 0, //Видимость сегмента №01
                    segmentP4Visibility02 = cache[34482] ?: 0, //Видимость сегмента №02
                    segmentP4Visibility03 = cache[34483] ?: 0, //Видимость сегмента №03
                    segmentP4Visibility04 = cache[34484] ?: 0, //Видимость сегмента №04
                    segmentP4Visibility05 = cache[34485] ?: 0, //Видимость сегмента №05
                    segmentP4Visibility06 = cache[34486] ?: 0, //Видимость сегмента №06
                    segmentP4Visibility07 = cache[34487] ?: 0, //Видимость сегмента №07
                    segmentP4Visibility08 = cache[34488] ?: 0, //Видимость сегмента №08
                    segmentP4Visibility09 = cache[34489] ?: 0, //Видимость сегмента №09
                    segmentP4Visibility10 = cache[34490] ?: 0, //Видимость сегмента №10
                    segmentP4Visibility11 = cache[34491] ?: 0, //Видимость сегмента №11
                    segmentP4Visibility12 = cache[34492] ?: 0, //Видимость сегмента №12
                    segmentP4Visibility13 = cache[34493] ?: 0, //Видимость сегмента №13
                    segmentP4Visibility14 = cache[34494] ?: 0, //Видимость сегмента №14
                    segmentP4Visibility15 = cache[34495] ?: 0, //Видимость сегмента №15
                    segmentP4Visibility16 = cache[34496] ?: 0, //Видимость сегмента №16
                    segmentP4Visibility17 = cache[34497] ?: 0, //Видимость сегмента №17
                    segmentP4Visibility18 = cache[34498] ?: 0, //Видимость сегмента №18
                    segmentP4Visibility19 = cache[34499] ?: 0, //Видимость сегмента №19
                ),
            ),
        )
    }

    // Очистка при уничтожении ViewModel


    private fun safeDivide(value: Int?): Float {
        return (value ?: 0) / 1f
    }

    private fun safeDivideTens(value: Int?): Float {
        return (value ?: 0) / 10f
    }

    private fun safeDivideHundred(value: Int?): Float {
        return (value ?: 0) / 100f
    }
    private fun safeDivideThousands(value: Int?): Float {
        return (value ?: 0) / 1000f
    }

    data class HomeScreenState(
        val operatingParams: CraneOperatingParametersState = CraneOperatingParametersState(),
        val parameterScale: CraneParameterScaleState = CraneParameterScaleState(),
        val information: InformationState = InformationState(),
        val craneMechanismsCondition: CraneMechanismsConditionState = CraneMechanismsConditionState(),
        val polygonP1: PolygonP1State = PolygonP1State(
            segments = SegmentDataP1(),
            points = PointDataP1(),
            angles = AngleDataP1(),
            visibility = VisibilityDataP1(),
        ),
        val polygonP2: PolygonP2State = PolygonP2State(
            segments = SegmentDataP2(),
            points = PointDataP2(),
            angles = AngleDataP2(),
            visibility = VisibilityDataP2(),
        ),
        val polygonP3: PolygonP3State = PolygonP3State(
            segments = SegmentDataP3(),
            points = PointDataP3(),
            angles = AngleDataP3(),
            visibility = VisibilityDataP3(),
        ),
        val polygonP4: PolygonP4State = PolygonP4State(
            segments = SegmentDataP4(),
            points = PointDataP4(),
            angles = AngleDataP4(),
            visibility = VisibilityDataP4(),
        ),
        val sozrParams: CraneSozrState = CraneSozrState()
    )


    data class CraneOperatingParametersState(
        val weightQnom: Float = 0f,
        val weightCurrent: Float = 0f,
        val trolleyCurrent: Float = 0f,
        val slewingCurrent:Float = 0f,
        val heightCurrent: Float = -0f,
        val moveCurrent: Float = 0f,
        val windCurrent: Float = 0f,

        val voltageL1L2Current: Float = 0f,
        val voltageL2L3Current: Float = 0f,
        val voltageL1L3Current: Float = 0f,
    )
    data class CraneParameterScaleState(
        val weightPercent: Float = 0f,
        val hoistPosition: Float = 0f,
        val hoistMaxPosition: Float = 0f,
        val hoistMinPosition: Float = -0f,
        val cranePosition: Float = 0f,
        val craneMaxPosition: Float = 0f,
        val windCurrent: Float = 0f,
        val windSpeedLvl1 : Float = 0f,
        val windSpeedLvl2 : Float = 0f,
        val windSpeedMontage : Float = 0f,
        val anemometerStatus: Int = 0,
        val icMontageMode: Int = 0,
        val icCraneRepPointFault: Int = 0, //Сбой датчика реперной точки хода крана
        val icCraneRepPoint: Int = 0, //Реперная точка хода крана. Индикатор срабатывания
        val icLoadMomentSensorAlarm: Int = 0, //Авария датчика критического грузового момента.
        val icHoistBrakeSensor: Int = 0, //Контроль износа тормоза подъема. 1-норма. 0-Авария
        val icCraneRailsEn : Int = 0, //Флаг включения перемещения крана. 1 - Ход крана активирован
        val ictechnicalReglamentTO: Int = 0,
        val icalarmTO: Int = 0,
    )

    data class InformationState (
        val icBuildMode: Int = 0,     //Флаг режима наладки
        val icServiceMode: Int = 0,   //Флаг режима сервис
        val icACSSPWR: Int = 0,       //АЦСС питание в норме
        val icACSSFault: Int = 0,     //АЦСС в ошибке
        val icLukStatus: Int = 0,     //Состояние конечного выключателя люка ОПУ
        val icLineContactorZeroPosAlarm : Int = 0, //Авария нулевой блокировки линейного контактора
        val icMicroclimateHeaterFault: Int = 0,   //вария обогрева технического помещения
        val icBrakePowerSupplyFault : Int = 0,    //Авария питания тормозов
        val icVentilationPowerSupplyFault: Int = 0, //Авария питания вентиляции электродвигателей
        val icHoistJoystickError: Int = 0,    //Ошибка джойстика механизма подъема
        val icSlewJoystickError: Int = 0,     //Ошибка джойстика механизма поворота
        val icTrolleyJoystickError: Int = 0,  //Ошибка джойстика механизма тележки
        val icCraneJoystickError: Int = 0,    //Ошибка джойстика механизма хода крана
        val icCraneEncoderFault: Int = 0,     //Сбой энкодера механизма хода крана
        val icHoistEncoderFault: Int = 0,     //Сбой энкодера механизма хода крана
        val icSlewEncoderFault: Int = 0,      //Сбой энкодера механизма поворота
        val icTrolleyEncoderFault: Int = 0,   //Сбой энкодера механизма тележки
        val icPhaseControlRelayFault: Int = 0, //Авария реле контроля фаз
        val icPowerSupplyNetworkFault: Int = 0, //Авария питающей сети
        val icHoistDriveError : Int = 0, //Ошибка частотного преобразователя подъема
        val icSlewDriveError : Int = 0, //Ошибка частотного преобразователя поворота
        val icTrolleyDriveError : Int = 0, //Ошибка частотного преобразователя тележки
        val icCraneDriveError : Int = 0, //Ошибка частотного преобразователя хода крана
        val icHoistDrivePowerError : Int = 0, //Отключение питания частотного преобразователя подъема
        val icSlewDrivePowerError : Int = 0, //Отключение питания частотного преобразователя поворота
        val icTrolleyDrivePowerError : Int = 0, //Отключение питания частотного преобразователя тележки
        val icCraneDrivePowerError : Int = 0, //Отключение питания частотного преобразователя хода крана
        val icCANOpenSensorsError : Int = 0, //Авария сети CANopen (датчики)
        val icCANOpenDrivesError : Int = 0, //Авария сети CANopen (частотные преобразователи)


    )

    data class CraneMechanismsConditionState(
        val weightSensorErr: Int = 0,
        val anemometerSensorErr: Int = 0,
        val alarmLampStatus: Int = 0,
        val trolleyFwdStatus: Int = 0,
        val trolleyRevStatus: Int = 0,
        val hoistUpStatus: Int = 0,
        val hoistDownStatus: Int = 0,
        val slewRightStatus: Int = 0,
        val slewLeftStatus: Int = 0,
        val cranRightStatus: Int = 0,
        val craneLeftStatus: Int = 0,
        val craneRight: Int = 0,
        val craneLeft: Int = 0,
        val slewLeft: Int = 0,
        val slewRight: Int = 0,
        val trolleyFwd: Int = 0,
        val trolleyRev: Int = 0,
        val hoistUp: Int = 0,
        val hoistDown: Int = 0,

        val hoistControlEffortHz: Float = 0f,
        val slewControlEffortHz: Float = 0f,
        val trolleyControlEffortHz: Float = 0f,
        val craneControlEffortHz: Float = 0f,
    )

    data class CraneSozrState(
        val hmiLineAngle: Float = 0f,
        val hmiTrolleyCoordinateY: Float = 0f,
        val hmiTrolleyCoordinateX: Float = 0f,
        val hmiCircleSizeTrolley: Int = 0,
        val hmiStrelaLenght: Float = 0f,
        val hmiP1Color: Int = 0,
        val hmiP2Color: Int = 0,
        val hmiP3Color: Int = 0,
        val hmiP4Color: Int = 0,
    )

    data class PolygonP1State(
        val segments: SegmentDataP1,
        val points: PointDataP1,
        val angles: AngleDataP1,
        val visibility: VisibilityDataP1,)
    data class SegmentDataP1(
        // Параметры отезков для отображения полигона №1
        val hmiSegmentP1Length00: Int = 0, //Длина сегмента №00
        val hmiSegmentP1Length01: Int = 0, //Длина сегмента №01
        val hmiSegmentP1Length02: Int = 0, //Длина сегмента №02
        val hmiSegmentP1Length03: Int = 0, //Длина сегмента №03
        val hmiSegmentP1Length04: Int = 0, //Длина сегмента №04
        val hmiSegmentP1Length05: Int = 0, //Длина сегмента №05
        val hmiSegmentP1Length06: Int = 0, //Длина сегмента №06
        val hmiSegmentP1Length07: Int = 0, //Длина сегмента №07
        val hmiSegmentP1Length08: Int = 0, //Длина сегмента №08
        val hmiSegmentP1Length09: Int = 0, //Длина сегмента №09
        val hmiSegmentP1Length10: Int = 0, //Длина сегмента №10
        val hmiSegmentP1Length11: Int = 0, //Длина сегмента №11
        val hmiSegmentP1Length12: Int = 0, //Длина сегмента №12
        val hmiSegmentP1Length13: Int = 0, //Длина сегмента №13
        val hmiSegmentP1Length14: Int = 0, //Длина сегмента №14
        val hmiSegmentP1Length15: Int = 0, //Длина сегмента №15
        val hmiSegmentP1Length16: Int = 0, //Длина сегмента №16
        val hmiSegmentP1Length17: Int = 0, //Длина сегмента №17
        val hmiSegmentP1Length18: Int = 0, //Длина сегмента №18
        val hmiSegmentP1Length19: Int = 0, //Длина сегмента №19
    )
    data class PointDataP1 (
        // Параметры отезков для отображения полигона №1
        val pointP1PositionY00: Int = 0, //Координата Y точки №00
        val pointP1PositionY01: Int = 0, //Координата Y точки №01
        val pointP1PositionY02: Int = 0, //Координата Y точки №02
        val pointP1PositionY03: Int = 0, //Координата Y точки №03
        val pointP1PositionY04: Int = 0, //Координата Y точки №04
        val pointP1PositionY05: Int = 0, //Координата Y точки №05
        val pointP1PositionY06: Int = 0, //Координата Y точки №06
        val pointP1PositionY07: Int = 0, //Координата Y точки №07
        val pointP1PositionY08: Int = 0, //Координата Y точки №08
        val pointP1PositionY09: Int = 0, //Координата Y точки №09
        val pointP1PositionY10: Int = 0, //Координата Y точки №10
        val pointP1PositionY11: Int = 0, //Координата Y точки №11
        val pointP1PositionY12: Int = 0, //Координата Y точки №12
        val pointP1PositionY13: Int = 0, //Координата Y точки №13
        val pointP1PositionY14: Int = 0, //Координата Y точки №14
        val pointP1PositionY15: Int = 0, //Координата Y точки №15
        val pointP1PositionY16: Int = 0, //Координата Y точки №16
        val pointP1PositionY17: Int = 0, //Координата Y точки №17
        val pointP1PositionY18: Int = 0, //Координата Y точки №18
        val pointP1PositionY19: Int = 0, //Координата Y точки №19

        // Параметры отезков для отображения полигона №1
        val pointP1PositionX00: Int = 0, //Координата X точки №00
        val pointP1PositionX01: Int = 0, //Координата X точки №01
        val pointP1PositionX02: Int = 0, //Координата X точки №02
        val pointP1PositionX03: Int = 0, //Координата X точки №03
        val pointP1PositionX04: Int = 0, //Координата X точки №04
        val pointP1PositionX05: Int = 0, //Координата X точки №05
        val pointP1PositionX06: Int = 0, //Координата X точки №06
        val pointP1PositionX07: Int = 0, //Координата X точки №07
        val pointP1PositionX08: Int = 0, //Координата X точки №08
        val pointP1PositionX09: Int = 0, //Координата X точки №09
        val pointP1PositionX10: Int = 0, //Координата X точки №10
        val pointP1PositionX11: Int = 0, //Координата X точки №11
        val pointP1PositionX12: Int = 0, //Координата X точки №12
        val pointP1PositionX13: Int = 0, //Координата X точки №13
        val pointP1PositionX14: Int = 0, //Координата X точки №14
        val pointP1PositionX15: Int = 0, //Координата X точки №15
        val pointP1PositionX16: Int = 0, //Координата X точки №16
        val pointP1PositionX17: Int = 0, //Координата X точки №17
        val pointP1PositionX18: Int = 0, //Координата X точки №18
        val pointP1PositionX19: Int = 0, //Координата X точки №19
    )
    data class AngleDataP1(
        // Параметры отезков для отображения полигона №1
        val segmentP1Angle00: Int = 0, //Угол между сегментами 00-01
        val segmentP1Angle01: Int = 0, //Угол между сегментами 01-02
        val segmentP1Angle02: Int = 0, //Угол между сегментами 02-03
        val segmentP1Angle03: Int = 0, //Угол между сегментами 03-04
        val segmentP1Angle04: Int = 0, //Угол между сегментами 04-05
        val segmentP1Angle05: Int = 0, //Угол между сегментами 05-06
        val segmentP1Angle06: Int = 0, //Угол между сегментами 06-07
        val segmentP1Angle07: Int = 0, //Угол между сегментами 07-08
        val segmentP1Angle08: Int = 0, //Угол между сегментами 08-09
        val segmentP1Angle09: Int = 0, //Угол между сегментами 09-10
        val segmentP1Angle10: Int = 0, //Угол между сегментами 10-11
        val segmentP1Angle11: Int = 0, //Угол между сегментами 11-12
        val segmentP1Angle12: Int = 0, //Угол между сегментами 12-13
        val segmentP1Angle13: Int = 0, //Угол между сегментами 13-14
        val segmentP1Angle14: Int = 0, //Угол между сегментами 14-15
        val segmentP1Angle15: Int = 0, //Угол между сегментами 15-16
        val segmentP1Angle16: Int = 0, //Угол между сегментами 16-17
        val segmentP1Angle17: Int = 0, //Угол между сегментами 17-18
        val segmentP1Angle18: Int = 0, //Угол между сегментами 18-19
        val segmentP1Angle19: Int = 0, //Угол между сегментами 19-00
    )

    data class VisibilityDataP1(
        // Параметры отезков для отображения полигона №1
        val segmentP1Visibility00: Int = 0, //Видимость сегмента №00
        val segmentP1Visibility01: Int = 0, //Видимость сегмента №01
        val segmentP1Visibility02: Int = 0, //Видимость сегмента №02
        val segmentP1Visibility03: Int = 0, //Видимость сегмента №03
        val segmentP1Visibility04: Int = 0, //Видимость сегмента №04
        val segmentP1Visibility05: Int = 0, //Видимость сегмента №05
        val segmentP1Visibility06: Int = 0, //Видимость сегмента №06
        val segmentP1Visibility07: Int = 0, //Видимость сегмента №07
        val segmentP1Visibility08: Int = 0, //Видимость сегмента №08
        val segmentP1Visibility09: Int = 0, //Видимость сегмента №09
        val segmentP1Visibility10: Int = 0, //Видимость сегмента №10
        val segmentP1Visibility11: Int = 0, //Видимость сегмента №11
        val segmentP1Visibility12: Int = 0, //Видимость сегмента №12
        val segmentP1Visibility13: Int = 0, //Видимость сегмента №13
        val segmentP1Visibility14: Int = 0, //Видимость сегмента №14
        val segmentP1Visibility15: Int = 0, //Видимость сегмента №15
        val segmentP1Visibility16: Int = 0, //Видимость сегмента №16
        val segmentP1Visibility17: Int = 0, //Видимость сегмента №17
        val segmentP1Visibility18: Int = 0, //Видимость сегмента №18
        val segmentP1Visibility19: Int = 0, //Видимость сегмента №19
    )

    data class PolygonP2State(
        val segments: SegmentDataP2,
        val points: PointDataP2,
        val angles: AngleDataP2,
        val visibility: VisibilityDataP2,
    )
    data class SegmentDataP2(
        // Параметры отезков для отображения полигона №2
        val hmiSegmentP2Length00: Int = 0, //Длина сегмента №00
        val hmiSegmentP2Length01: Int = 0, //Длина сегмента №01
        val hmiSegmentP2Length02: Int = 0, //Длина сегмента №02
        val hmiSegmentP2Length03: Int = 0, //Длина сегмента №03
        val hmiSegmentP2Length04: Int = 0, //Длина сегмента №04
        val hmiSegmentP2Length05: Int = 0, //Длина сегмента №05
        val hmiSegmentP2Length06: Int = 0, //Длина сегмента №06
        val hmiSegmentP2Length07: Int = 0, //Длина сегмента №07
        val hmiSegmentP2Length08: Int = 0, //Длина сегмента №08
        val hmiSegmentP2Length09: Int = 0, //Длина сегмента №09
        val hmiSegmentP2Length10: Int = 0, //Длина сегмента №10
        val hmiSegmentP2Length11: Int = 0, //Длина сегмента №11
        val hmiSegmentP2Length12: Int = 0, //Длина сегмента №12
        val hmiSegmentP2Length13: Int = 0, //Длина сегмента №13
        val hmiSegmentP2Length14: Int = 0, //Длина сегмента №14
        val hmiSegmentP2Length15: Int = 0, //Длина сегмента №15
        val hmiSegmentP2Length16: Int = 0, //Длина сегмента №16
        val hmiSegmentP2Length17: Int = 0, //Длина сегмента №17
        val hmiSegmentP2Length18: Int = 0, //Длина сегмента №18
        val hmiSegmentP2Length19: Int = 0, //Длина сегмента №19
    )
    data class PointDataP2(
        // Параметры отезков для отображения полигона №2
        val pointP2PositionY00: Int = 0, //Координата Y точки №00
        val pointP2PositionY01: Int = 0, //Координата Y точки №01
        val pointP2PositionY02: Int = 0, //Координата Y точки №02
        val pointP2PositionY03: Int = 0, //Координата Y точки №03
        val pointP2PositionY04: Int = 0, //Координата Y точки №04
        val pointP2PositionY05: Int = 0, //Координата Y точки №05
        val pointP2PositionY06: Int = 0, //Координата Y точки №06
        val pointP2PositionY07: Int = 0, //Координата Y точки №07
        val pointP2PositionY08: Int = 0, //Координата Y точки №08
        val pointP2PositionY09: Int = 0, //Координата Y точки №09
        val pointP2PositionY10: Int = 0, //Координата Y точки №10
        val pointP2PositionY11: Int = 0, //Координата Y точки №11
        val pointP2PositionY12: Int = 0, //Координата Y точки №12
        val pointP2PositionY13: Int = 0, //Координата Y точки №13
        val pointP2PositionY14: Int = 0, //Координата Y точки №14
        val pointP2PositionY15: Int = 0, //Координата Y точки №15
        val pointP2PositionY16: Int = 0, //Координата Y точки №16
        val pointP2PositionY17: Int = 0, //Координата Y точки №17
        val pointP2PositionY18: Int = 0, //Координата Y точки №18
        val pointP2PositionY19: Int = 0, //Координата Y точки №19
        // Параметры отезков для отображения полигона №2
        val pointP2PositionX00: Int = 0, //Координата X точки №00
        val pointP2PositionX01: Int = 0, //Координата X точки №01
        val pointP2PositionX02: Int = 0, //Координата X точки №02
        val pointP2PositionX03: Int = 0, //Координата X точки №03
        val pointP2PositionX04: Int = 0, //Координата X точки №04
        val pointP2PositionX05: Int = 0, //Координата X точки №05
        val pointP2PositionX06: Int = 0, //Координата X точки №06
        val pointP2PositionX07: Int = 0, //Координата X точки №07
        val pointP2PositionX08: Int = 0, //Координата X точки №08
        val pointP2PositionX09: Int = 0, //Координата X точки №09
        val pointP2PositionX10: Int = 0, //Координата X точки №10
        val pointP2PositionX11: Int = 0, //Координата X точки №11
        val pointP2PositionX12: Int = 0, //Координата X точки №12
        val pointP2PositionX13: Int = 0, //Координата X точки №13
        val pointP2PositionX14: Int = 0, //Координата X точки №14
        val pointP2PositionX15: Int = 0, //Координата X точки №15
        val pointP2PositionX16: Int = 0, //Координата X точки №16
        val pointP2PositionX17: Int = 0, //Координата X точки №17
        val pointP2PositionX18: Int = 0, //Координата X точки №18
        val pointP2PositionX19: Int = 0, //Координата X точки №19
    )
    data class AngleDataP2(
        // Параметры отезков для отображения полигона №2
        val segmentP2Angle00: Int = 0, //Угол между сегментами 00-01
        val segmentP2Angle01: Int = 0, //Угол между сегментами 01-02
        val segmentP2Angle02: Int = 0, //Угол между сегментами 02-03
        val segmentP2Angle03: Int = 0, //Угол между сегментами 03-04
        val segmentP2Angle04: Int = 0, //Угол между сегментами 04-05
        val segmentP2Angle05: Int = 0, //Угол между сегментами 05-06
        val segmentP2Angle06: Int = 0, //Угол между сегментами 06-07
        val segmentP2Angle07: Int = 0, //Угол между сегментами 07-08
        val segmentP2Angle08: Int = 0, //Угол между сегментами 08-09
        val segmentP2Angle09: Int = 0, //Угол между сегментами 09-10
        val segmentP2Angle10: Int = 0, //Угол между сегментами 10-11
        val segmentP2Angle11: Int = 0, //Угол между сегментами 11-12
        val segmentP2Angle12: Int = 0, //Угол между сегментами 12-13
        val segmentP2Angle13: Int = 0, //Угол между сегментами 13-14
        val segmentP2Angle14: Int = 0, //Угол между сегментами 14-15
        val segmentP2Angle15: Int = 0, //Угол между сегментами 15-16
        val segmentP2Angle16: Int = 0, //Угол между сегментами 16-17
        val segmentP2Angle17: Int = 0, //Угол между сегментами 17-18
        val segmentP2Angle18: Int = 0, //Угол между сегментами 18-19
        val segmentP2Angle19: Int = 0, //Угол между сегментами 19-00
    )
    data class VisibilityDataP2(
        // Параметры отезков для отображения полигона №2
        val segmentP2Visibility00: Int = 0, //Видимость сегмента №00
        val segmentP2Visibility01: Int = 0, //Видимость сегмента №01
        val segmentP2Visibility02: Int = 0, //Видимость сегмента №02
        val segmentP2Visibility03: Int = 0, //Видимость сегмента №03
        val segmentP2Visibility04: Int = 0, //Видимость сегмента №04
        val segmentP2Visibility05: Int = 0, //Видимость сегмента №05
        val segmentP2Visibility06: Int = 0, //Видимость сегмента №06
        val segmentP2Visibility07: Int = 0, //Видимость сегмента №07
        val segmentP2Visibility08: Int = 0, //Видимость сегмента №08
        val segmentP2Visibility09: Int = 0, //Видимость сегмента №09
        val segmentP2Visibility10: Int = 0, //Видимость сегмента №10
        val segmentP2Visibility11: Int = 0, //Видимость сегмента №11
        val segmentP2Visibility12: Int = 0, //Видимость сегмента №12
        val segmentP2Visibility13: Int = 0, //Видимость сегмента №13
        val segmentP2Visibility14: Int = 0, //Видимость сегмента №14
        val segmentP2Visibility15: Int = 0, //Видимость сегмента №15
        val segmentP2Visibility16: Int = 0, //Видимость сегмента №16
        val segmentP2Visibility17: Int = 0, //Видимость сегмента №17
        val segmentP2Visibility18: Int = 0, //Видимость сегмента №18
        val segmentP2Visibility19: Int = 0, //Видимость сегмента №19
    )

    data class PolygonP3State(
        val segments: SegmentDataP3,
        val points: PointDataP3,
        val angles: AngleDataP3,
        val visibility: VisibilityDataP3,
    )

    data class SegmentDataP3(
        // Параметры отезков для отображения полигона №2
        val hmiSegmentP3Length00: Int = 0, //Длина сегмента №00
        val hmiSegmentP3Length01: Int = 0, //Длина сегмента №01
        val hmiSegmentP3Length02: Int = 0, //Длина сегмента №02
        val hmiSegmentP3Length03: Int = 0, //Длина сегмента №03
        val hmiSegmentP3Length04: Int = 0, //Длина сегмента №04
        val hmiSegmentP3Length05: Int = 0, //Длина сегмента №05
        val hmiSegmentP3Length06: Int = 0, //Длина сегмента №06
        val hmiSegmentP3Length07: Int = 0, //Длина сегмента №07
        val hmiSegmentP3Length08: Int = 0, //Длина сегмента №08
        val hmiSegmentP3Length09: Int = 0, //Длина сегмента №09
        val hmiSegmentP3Length10: Int = 0, //Длина сегмента №10
        val hmiSegmentP3Length11: Int = 0, //Длина сегмента №11
        val hmiSegmentP3Length12: Int = 0, //Длина сегмента №12
        val hmiSegmentP3Length13: Int = 0, //Длина сегмента №13
        val hmiSegmentP3Length14: Int = 0, //Длина сегмента №14
        val hmiSegmentP3Length15: Int = 0, //Длина сегмента №15
        val hmiSegmentP3Length16: Int = 0, //Длина сегмента №16
        val hmiSegmentP3Length17: Int = 0, //Длина сегмента №17
        val hmiSegmentP3Length18: Int = 0, //Длина сегмента №18
        val hmiSegmentP3Length19: Int = 0, //Длина сегмента №19
    )
    data class PointDataP3(
        // Параметры отезков для отображения полигона №2
        val pointP3PositionY00: Int = 0, //Координата Y точки №00
        val pointP3PositionY01: Int = 0, //Координата Y точки №01
        val pointP3PositionY02: Int = 0, //Координата Y точки №02
        val pointP3PositionY03: Int = 0, //Координата Y точки №03
        val pointP3PositionY04: Int = 0, //Координата Y точки №04
        val pointP3PositionY05: Int = 0, //Координата Y точки №05
        val pointP3PositionY06: Int = 0, //Координата Y точки №06
        val pointP3PositionY07: Int = 0, //Координата Y точки №07
        val pointP3PositionY08: Int = 0, //Координата Y точки №08
        val pointP3PositionY09: Int = 0, //Координата Y точки №09
        val pointP3PositionY10: Int = 0, //Координата Y точки №10
        val pointP3PositionY11: Int = 0, //Координата Y точки №11
        val pointP3PositionY12: Int = 0, //Координата Y точки №12
        val pointP3PositionY13: Int = 0, //Координата Y точки №13
        val pointP3PositionY14: Int = 0, //Координата Y точки №14
        val pointP3PositionY15: Int = 0, //Координата Y точки №15
        val pointP3PositionY16: Int = 0, //Координата Y точки №16
        val pointP3PositionY17: Int = 0, //Координата Y точки №17
        val pointP3PositionY18: Int = 0, //Координата Y точки №18
        val pointP3PositionY19: Int = 0, //Координата Y точки №19
        // Параметры отезков для отображения полигона №2
        val pointP3PositionX00: Int = 0, //Координата X точки №00
        val pointP3PositionX01: Int = 0, //Координата X точки №01
        val pointP3PositionX02: Int = 0, //Координата X точки №02
        val pointP3PositionX03: Int = 0, //Координата X точки №03
        val pointP3PositionX04: Int = 0, //Координата X точки №04
        val pointP3PositionX05: Int = 0, //Координата X точки №05
        val pointP3PositionX06: Int = 0, //Координата X точки №06
        val pointP3PositionX07: Int = 0, //Координата X точки №07
        val pointP3PositionX08: Int = 0, //Координата X точки №08
        val pointP3PositionX09: Int = 0, //Координата X точки №09
        val pointP3PositionX10: Int = 0, //Координата X точки №10
        val pointP3PositionX11: Int = 0, //Координата X точки №11
        val pointP3PositionX12: Int = 0, //Координата X точки №12
        val pointP3PositionX13: Int = 0, //Координата X точки №13
        val pointP3PositionX14: Int = 0, //Координата X точки №14
        val pointP3PositionX15: Int = 0, //Координата X точки №15
        val pointP3PositionX16: Int = 0, //Координата X точки №16
        val pointP3PositionX17: Int = 0, //Координата X точки №17
        val pointP3PositionX18: Int = 0, //Координата X точки №18
        val pointP3PositionX19: Int = 0, //Координата X точки №19
    )
    data class AngleDataP3(
        // Параметры отезков для отображения полигона №2
        val segmentP3Angle00: Int = 0, //Угол между сегментами 00-01
        val segmentP3Angle01: Int = 0, //Угол между сегментами 01-02
        val segmentP3Angle02: Int = 0, //Угол между сегментами 02-03
        val segmentP3Angle03: Int = 0, //Угол между сегментами 03-04
        val segmentP3Angle04: Int = 0, //Угол между сегментами 04-05
        val segmentP3Angle05: Int = 0, //Угол между сегментами 05-06
        val segmentP3Angle06: Int = 0, //Угол между сегментами 06-07
        val segmentP3Angle07: Int = 0, //Угол между сегментами 07-08
        val segmentP3Angle08: Int = 0, //Угол между сегментами 08-09
        val segmentP3Angle09: Int = 0, //Угол между сегментами 09-10
        val segmentP3Angle10: Int = 0, //Угол между сегментами 10-11
        val segmentP3Angle11: Int = 0, //Угол между сегментами 11-12
        val segmentP3Angle12: Int = 0, //Угол между сегментами 12-13
        val segmentP3Angle13: Int = 0, //Угол между сегментами 13-14
        val segmentP3Angle14: Int = 0, //Угол между сегментами 14-15
        val segmentP3Angle15: Int = 0, //Угол между сегментами 15-16
        val segmentP3Angle16: Int = 0, //Угол между сегментами 16-17
        val segmentP3Angle17: Int = 0, //Угол между сегментами 17-18
        val segmentP3Angle18: Int = 0, //Угол между сегментами 18-19
        val segmentP3Angle19: Int = 0, //Угол между сегментами 19-00
    )
    data class VisibilityDataP3(
        // Параметры отезков для отображения полигона №2
        val segmentP3Visibility00: Int = 0, //Видимость сегмента №00
        val segmentP3Visibility01: Int = 0, //Видимость сегмента №01
        val segmentP3Visibility02: Int = 0, //Видимость сегмента №02
        val segmentP3Visibility03: Int = 0, //Видимость сегмента №03
        val segmentP3Visibility04: Int = 0, //Видимость сегмента №04
        val segmentP3Visibility05: Int = 0, //Видимость сегмента №05
        val segmentP3Visibility06: Int = 0, //Видимость сегмента №06
        val segmentP3Visibility07: Int = 0, //Видимость сегмента №07
        val segmentP3Visibility08: Int = 0, //Видимость сегмента №08
        val segmentP3Visibility09: Int = 0, //Видимость сегмента №09
        val segmentP3Visibility10: Int = 0, //Видимость сегмента №10
        val segmentP3Visibility11: Int = 0, //Видимость сегмента №11
        val segmentP3Visibility12: Int = 0, //Видимость сегмента №12
        val segmentP3Visibility13: Int = 0, //Видимость сегмента №13
        val segmentP3Visibility14: Int = 0, //Видимость сегмента №14
        val segmentP3Visibility15: Int = 0, //Видимость сегмента №15
        val segmentP3Visibility16: Int = 0, //Видимость сегмента №16
        val segmentP3Visibility17: Int = 0, //Видимость сегмента №17
        val segmentP3Visibility18: Int = 0, //Видимость сегмента №18
        val segmentP3Visibility19: Int = 0, //Видимость сегмента №19
    )

    data class PolygonP4State(
        val segments: SegmentDataP4,
        val points: PointDataP4,
        val angles: AngleDataP4,
        val visibility: VisibilityDataP4,
    )

    data class SegmentDataP4(
        // Параметры отезков для отображения полигона №4
        val hmiSegmentP4Length00: Int = 0, //Длина сегмента №00
        val hmiSegmentP4Length01: Int = 0, //Длина сегмента №01
        val hmiSegmentP4Length02: Int = 0, //Длина сегмента №02
        val hmiSegmentP4Length03: Int = 0, //Длина сегмента №03
        val hmiSegmentP4Length04: Int = 0, //Длина сегмента №04
        val hmiSegmentP4Length05: Int = 0, //Длина сегмента №05
        val hmiSegmentP4Length06: Int = 0, //Длина сегмента №06
        val hmiSegmentP4Length07: Int = 0, //Длина сегмента №07
        val hmiSegmentP4Length08: Int = 0, //Длина сегмента №08
        val hmiSegmentP4Length09: Int = 0, //Длина сегмента №09
        val hmiSegmentP4Length10: Int = 0, //Длина сегмента №10
        val hmiSegmentP4Length11: Int = 0, //Длина сегмента №11
        val hmiSegmentP4Length12: Int = 0, //Длина сегмента №12
        val hmiSegmentP4Length13: Int = 0, //Длина сегмента №13
        val hmiSegmentP4Length14: Int = 0, //Длина сегмента №14
        val hmiSegmentP4Length15: Int = 0, //Длина сегмента №15
        val hmiSegmentP4Length16: Int = 0, //Длина сегмента №16
        val hmiSegmentP4Length17: Int = 0, //Длина сегмента №17
        val hmiSegmentP4Length18: Int = 0, //Длина сегмента №18
        val hmiSegmentP4Length19: Int = 0, //Длина сегмента №19
    )
    data class PointDataP4(
        // Параметры отезков для отображения полигона №4
        val pointP4PositionY00: Int = 0, //Координата Y точки №00
        val pointP4PositionY01: Int = 0, //Координата Y точки №01
        val pointP4PositionY02: Int = 0, //Координата Y точки №02
        val pointP4PositionY03: Int = 0, //Координата Y точки №03
        val pointP4PositionY04: Int = 0, //Координата Y точки №04
        val pointP4PositionY05: Int = 0, //Координата Y точки №05
        val pointP4PositionY06: Int = 0, //Координата Y точки №06
        val pointP4PositionY07: Int = 0, //Координата Y точки №07
        val pointP4PositionY08: Int = 0, //Координата Y точки №08
        val pointP4PositionY09: Int = 0, //Координата Y точки №09
        val pointP4PositionY10: Int = 0, //Координата Y точки №10
        val pointP4PositionY11: Int = 0, //Координата Y точки №11
        val pointP4PositionY12: Int = 0, //Координата Y точки №12
        val pointP4PositionY13: Int = 0, //Координата Y точки №13
        val pointP4PositionY14: Int = 0, //Координата Y точки №14
        val pointP4PositionY15: Int = 0, //Координата Y точки №15
        val pointP4PositionY16: Int = 0, //Координата Y точки №16
        val pointP4PositionY17: Int = 0, //Координата Y точки №17
        val pointP4PositionY18: Int = 0, //Координата Y точки №18
        val pointP4PositionY19: Int = 0, //Координата Y точки №19
        // Параметры отезков для отображения полигона №4
        val pointP4PositionX00: Int = 0, //Координата X точки №00
        val pointP4PositionX01: Int = 0, //Координата X точки №01
        val pointP4PositionX02: Int = 0, //Координата X точки №02
        val pointP4PositionX03: Int = 0, //Координата X точки №03
        val pointP4PositionX04: Int = 0, //Координата X точки №04
        val pointP4PositionX05: Int = 0, //Координата X точки №05
        val pointP4PositionX06: Int = 0, //Координата X точки №06
        val pointP4PositionX07: Int = 0, //Координата X точки №07
        val pointP4PositionX08: Int = 0, //Координата X точки №08
        val pointP4PositionX09: Int = 0, //Координата X точки №09
        val pointP4PositionX10: Int = 0, //Координата X точки №10
        val pointP4PositionX11: Int = 0, //Координата X точки №11
        val pointP4PositionX12: Int = 0, //Координата X точки №12
        val pointP4PositionX13: Int = 0, //Координата X точки №13
        val pointP4PositionX14: Int = 0, //Координата X точки №14
        val pointP4PositionX15: Int = 0, //Координата X точки №15
        val pointP4PositionX16: Int = 0, //Координата X точки №16
        val pointP4PositionX17: Int = 0, //Координата X точки №17
        val pointP4PositionX18: Int = 0, //Координата X точки №18
        val pointP4PositionX19: Int = 0, //Координата X точки №19
    )
    data class AngleDataP4(
        // Параметры отезков для отображения полигона №4
        val segmentP4Angle00: Int = 0, //Угол между сегментами 00-01
        val segmentP4Angle01: Int = 0, //Угол между сегментами 01-02
        val segmentP4Angle02: Int = 0, //Угол между сегментами 02-03
        val segmentP4Angle03: Int = 0, //Угол между сегментами 03-04
        val segmentP4Angle04: Int = 0, //Угол между сегментами 04-05
        val segmentP4Angle05: Int = 0, //Угол между сегментами 05-06
        val segmentP4Angle06: Int = 0, //Угол между сегментами 06-07
        val segmentP4Angle07: Int = 0, //Угол между сегментами 07-08
        val segmentP4Angle08: Int = 0, //Угол между сегментами 08-09
        val segmentP4Angle09: Int = 0, //Угол между сегментами 09-10
        val segmentP4Angle10: Int = 0, //Угол между сегментами 10-11
        val segmentP4Angle11: Int = 0, //Угол между сегментами 11-12
        val segmentP4Angle12: Int = 0, //Угол между сегментами 12-13
        val segmentP4Angle13: Int = 0, //Угол между сегментами 13-14
        val segmentP4Angle14: Int = 0, //Угол между сегментами 14-15
        val segmentP4Angle15: Int = 0, //Угол между сегментами 15-16
        val segmentP4Angle16: Int = 0, //Угол между сегментами 16-17
        val segmentP4Angle17: Int = 0, //Угол между сегментами 17-18
        val segmentP4Angle18: Int = 0, //Угол между сегментами 18-19
        val segmentP4Angle19: Int = 0, //Угол между сегментами 19-00
    )
    data class VisibilityDataP4(
        // Параметры отезков для отображения полигона №4
        val segmentP4Visibility00: Int = 0, //Видимость сегмента №00
        val segmentP4Visibility01: Int = 0, //Видимость сегмента №01
        val segmentP4Visibility02: Int = 0, //Видимость сегмента №02
        val segmentP4Visibility03: Int = 0, //Видимость сегмента №03
        val segmentP4Visibility04: Int = 0, //Видимость сегмента №04
        val segmentP4Visibility05: Int = 0, //Видимость сегмента №05
        val segmentP4Visibility06: Int = 0, //Видимость сегмента №06
        val segmentP4Visibility07: Int = 0, //Видимость сегмента №07
        val segmentP4Visibility08: Int = 0, //Видимость сегмента №08
        val segmentP4Visibility09: Int = 0, //Видимость сегмента №09
        val segmentP4Visibility10: Int = 0, //Видимость сегмента №10
        val segmentP4Visibility11: Int = 0, //Видимость сегмента №11
        val segmentP4Visibility12: Int = 0, //Видимость сегмента №12
        val segmentP4Visibility13: Int = 0, //Видимость сегмента №13
        val segmentP4Visibility14: Int = 0, //Видимость сегмента №14
        val segmentP4Visibility15: Int = 0, //Видимость сегмента №15
        val segmentP4Visibility16: Int = 0, //Видимость сегмента №16
        val segmentP4Visibility17: Int = 0, //Видимость сегмента №17
        val segmentP4Visibility18: Int = 0, //Видимость сегмента №18
        val segmentP4Visibility19: Int = 0, //Видимость сегмента №19
    )

    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("HomeScreenVM", "ViewModel cleared")
        // Деактивируем конфигурацию при уничтожении ViewModel
        modbusRepository.setActiveUiConfiguration(null)
    }
}