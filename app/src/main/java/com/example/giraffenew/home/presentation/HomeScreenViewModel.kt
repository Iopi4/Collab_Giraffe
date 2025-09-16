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
    private val modbusRepository: ModbusRepository
) : ViewModel() {

    val addressesCraneOperatingParameters = listOf(152,153,155,158,159,160,161,162,163,135,136,137,317) // Только нужные регистры
    val addressesCraneParameterScale = listOf(317,159,162,319,336,154,175,176,192, 710, 353, 709, 229, 212, 305, 306) // Только нужные регистры

    val addressesInformation = listOf(190, 191, 177, 239, 288, 700, 700, 701, 702, 703, 704, 705, 706, 707, 708,
        713, 714, 715, 716, 717, 718, 719, 720, 721, 722, 723, 724, 725, 726, 727, 728, 729 ) // Только нужные регистры
    val addressesCraneMechanismsCondition = listOf(711, 320,321,322,323,324,325,326,327,328,329,
        330,331,332,333,334,335,712,337) // Только нужные регистры

    val addressesCraneSOZR = listOf(400, 401, 402, 403, 404, 405, 406, 407, 408, 410,
        411, 412, 413, 414, 415, 416, 417, 418, 419, 420, 421, 422, 423, 424, 425, 426, 427, 428, 429, 430,
        431, 432, 433, 434, 435, 436, 437, 438, 439, 440, 441, 442, 443, 444, 445, 446, 447, 448, 449, 450,
        451, 452, 453, 454, 455, 456, 457, 458, 459, 460, 461, 462, 463, 464, 465, 466, 467, 468, 469, 470,
        471, 472, 473, 474, 475, 476, 477, 478, 479, 480, 481, 482, 483, 484, 485, 486, 487, 488, 489, 490,
        491, 492, 493, 494, 495, 496, 497, 498, 499, 500, 501, 502, 503, 504, 505, 506, 507, 508, 509,
        510, 511, 512, 513, 514, 515, 516, 517, 518, 519, 520, 521, 522, 523, 524, 525, 526, 527, 528, 529,
        530, 531, 532, 533, 534, 535, 536, 537, 538, 539, 540, 541, 542, 543, 544, 545, 546, 547, 548, 549,
        550, 551, 552, 553, 554, 555, 556, 557, 558, 559, 560, 561, 562, 563, 564, 565, 566, 567, 568, 569,
        570, 571, 572, 573, 574, 575, 576, 577, 578, 579, 580, 581, 582, 583, 584, 585, 586, 587, 588, 589,
        590, 591, 592, 593, 594, 595, 596, 597, 598, 599, 600, 601, 602, 603, 604, 605, 606, 607, 608, 609)
    //1. Объединим все адреса в один список
    private val allAddresses = (addressesCraneOperatingParameters + addressesCraneParameterScale
            + addressesCraneMechanismsCondition+addressesCraneSOZR+addressesInformation).distinct()

    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    private var dataCollectionJob: Job? = null

//    init {
//        // Активируем UI конфигурацию для этого экрана
//        modbusRepository.setActiveUiConfiguration(UiHomeScreenRegisterConfiguration.NAME_HOME)
//        startDataCollection()
//    }

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
        //if (dataCollectionJob?.isActive == true) return

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
            weightQnom = safeDivideThousands(cache[152] ?: 0),
            weightCurrent = safeDivideTens(cache[153] ?: 0),
            trolleyCurrent = safeDivideTens(cache[155] ?: 0),
            slewingCurrent = safeDivideTens(cache[158] ?: 0),
            heightCurrent = safeDivideTens(cache[159] ?: 0),
            moveCurrent = safeDivideTens(cache[162] ?: 0),
            windCurrent = safeDivideTens(cache[163] ?: 0),
            voltageL1L2Current = safeDivideTens(cache[135] ?: 0),
            voltageL2L3Current = safeDivideTens(cache[136] ?: 0),
            voltageL1L3Current = safeDivideTens(cache[137] ?: 0),
        ),
        // Crane Parameter Scale
        parameterScale = CraneParameterScaleState(
            weightPercent = safeDivide(cache[317] ?: 0),
            hoistPosition = safeDivideTens(cache[159] ?: 0),
            hoistMaxPosition = safeDivideTens(cache[160] ?: 0),
            hoistMinPosition = safeDivideTens(cache[161] ?: 0),
            cranePosition = safeDivideTens(cache[162] ?: 0),
            craneMaxPosition = safeDivideTens(cache[319] ?: 0),

            windCurrent = safeDivideTens(cache[163] ?: 0),
            windSpeedLvl1 = safeDivideTens (cache[175] ?: 0),
            windSpeedLvl2 = safeDivideTens (cache[154] ?: 0),
            windSpeedMontage = safeDivideTens (cache[176] ?: 0),
            anemometerStatus = cache[336] ?: 0,

            icMontageMode         = cache[192] ?: 0, //Флаг установки режима Монтаж.
            icCraneRepPointFault  = cache[710] ?: 0, //Сбой датчика реперной точки хода крана
            icCraneRepPoint       = cache[353] ?: 0, //Реперная точка хода крана. Индикатор срабатывания
            icLoadMomentSensorAlarm = cache[709] ?: 0, //Авария датчика критического грузового момента.
            icHoistBrakeSensor    = cache[229] ?: 0, //Контроль износа тормоза подъема. 1-норма. 0-Авария
            icCraneRailsEn        = cache[212] ?: 0, //Флаг включения перемещения крана. 1 - Ход крана активирован

            ictechnicalReglamentTO = cache[305] ?: 0, //Флаг проведите ТО
            icalarmTO = cache[306] ?: 0, //Флаг. ТО не проведено. Ограничение скорости
            ),
        // Mechanisms Condition
        craneMechanismsCondition = CraneMechanismsConditionState(
            weightSensorErr = cache[711] ?: 0,
            trolleyFwdStatus = cache[320] ?: 0,
            trolleyRevStatus = cache[321] ?: 0,
            hoistUpStatus = cache[322] ?: 0,
            hoistDownStatus = cache[323] ?: 0,
            slewRightStatus = cache[324] ?: 0,
            slewLeftStatus = cache[325] ?: 0,
            cranRightStatus = cache[326] ?: 0,
            craneLeftStatus = cache[327] ?: 0,
            craneRight = cache[328] ?: 0,
            craneLeft = cache[329] ?: 0,
            slewLeft = cache[330] ?: 0,
            slewRight = cache[331] ?: 0,
            trolleyFwd = cache[332] ?: 0,
            trolleyRev = cache[333] ?: 0,
            hoistUp = cache[334] ?: 0,
            hoistDown = cache[335] ?: 0,
            anemometerSensorErr = cache[712] ?: 0,
            alarmLampStatus = cache[337] ?: 0,
        ),
        //Infirmation
        information = InformationState(
            icBuildMode   = cache[190] ?: 0,
            icServiceMode = cache[191] ?: 0,

            icACSSPWR                 = cache[239] ?: 0,      //АЦСС питание в норме
            icACSSFault               = cache[288] ?: 0,     //АЦСС в ошибке
            icLukStatus               = cache[700] ?: 0,     //Состояние конечного выключателя люка ОПУ
            icLineContactorZeroPosAlarm   = cache[701] ?: 0, //Авария нулевой блокировки линейного контактора
            icMicroclimateHeaterFault     = cache[702] ?: 0,   //вария обогрева технического помещения
            icBrakePowerSupplyFault       = cache[703] ?: 0,    //Авария питания тормозов
            icVentilationPowerSupplyFault = cache[704] ?: 0, //Авария питания вентиляции электродвигателей
            icHoistJoystickError      = cache[705] ?: 0,    //Ошибка джойстика механизма подъема
            icSlewJoystickError       = cache[706] ?: 0,     //Ошибка джойстика механизма поворота
            icTrolleyJoystickError    = cache[707] ?: 0,  //Ошибка джойстика механизма тележки
            icCraneJoystickError      = cache[708] ?: 0,    //Ошибка джойстика механизма хода крана
            icCraneEncoderFault       = cache[713] ?: 0,     //Сбой энкодера механизма хода крана
            icHoistEncoderFault       = cache[714] ?: 0,     //Сбой энкодера механизма хода крана
            icSlewEncoderFault        = cache[715] ?: 0,      //Сбой энкодера механизма поворота
            icTrolleyEncoderFault     = cache[716] ?: 0,   //Сбой энкодера механизма тележки
            icPhaseControlRelayFault  = cache[717] ?: 0, //Авария реле контроля фаз
            icPowerSupplyNetworkFault = cache[718] ?: 0, //Авария питающей сети
            icHoistDriveError         = cache[720] ?: 0, //Ошибка частотного преобразователя подъема
            icSlewDriveError          = cache[721] ?: 0, //Ошибка частотного преобразователя поворота
            icTrolleyDriveError       = cache[722] ?: 0, //Ошибка частотного преобразователя тележки
            icCraneDriveError         = cache[723] ?: 0, //Ошибка частотного преобразователя хода крана
            icHoistDrivePowerError    = cache[724] ?: 0, //Отключение питания частотного преобразователя подъема
            icSlewDrivePowerError     = cache[725] ?: 0, //Отключение питания частотного преобразователя поворота
            icTrolleyDrivePowerError  = cache[726] ?: 0, //Отключение питания частотного преобразователя тележки
            icCraneDrivePowerError    = cache[727] ?: 0, //Отключение питания частотного преобразователя хода крана
            icCANOpenSensorsError     = cache[728] ?: 0, //Авария сети CANopen (датчики)
            icCANOpenDrivesError      = cache[729] ?: 0, //Авария сети CANopen (частотные преобразователи)
        ),
        //SOZR
        sozrParams = CraneSozrState(
            hmiLineAngle = safeDivideTens(cache[400] ?: 0),
            hmiTrolleyCoordinateY = safeDivideTens(cache[401] ?: 0),
            hmiTrolleyCoordinateX = safeDivideTens(cache[402] ?: 0),
            hmiCircleSizeTrolley = cache[403] ?: 0,
            hmiStrelaLenght = safeDivideTens(cache[404] ?: 0),
            hmiP1Color = cache[405] ?: 0,
            hmiP2Color = cache[406] ?: 0,
            hmiP3Color = cache[407] ?: 0,
            hmiP4Color = cache[409] ?: 0,
        ),
        //Polygon #1
        polygonP1 = PolygonP1State(
            hmiSegmentP1Length00 = cache[410] ?: 0, // Длина сегмента №00
            hmiSegmentP1Length01 = cache[411] ?: 0, // Длина сегмента №01
            hmiSegmentP1Length02 = cache[412] ?: 0, // Длина сегмента №02
            hmiSegmentP1Length03 = cache[413] ?: 0, // Длина сегмента №03
            hmiSegmentP1Length04 = cache[414] ?: 0, // Длина сегмента №04
            hmiSegmentP1Length05 = cache[415] ?: 0, // Длина сегмента №05
            hmiSegmentP1Length06 = cache[416] ?: 0, // Длина сегмента №06
            hmiSegmentP1Length07 = cache[417] ?: 0, // Длина сегмента №07
            hmiSegmentP1Length08 = cache[418] ?: 0, // Длина сегмента №08
            hmiSegmentP1Length09 = cache[419] ?: 0, // Длина сегмента №09
            hmiSegmentP1Length10 = cache[420] ?: 0, // Длина сегмента №10
            hmiSegmentP1Length11 = cache[421] ?: 0, // Длина сегмента №11
            hmiSegmentP1Length12 = cache[422] ?: 0, // Длина сегмента №12
            hmiSegmentP1Length13 = cache[423] ?: 0, // Длина сегмента №13
            hmiSegmentP1Length14 = cache[424] ?: 0, // Длина сегмента №14
            hmiSegmentP1Length15 = cache[425] ?: 0, // Длина сегмента №15
            hmiSegmentP1Length16 = cache[426] ?: 0, // Длина сегмента №16
            hmiSegmentP1Length17 = cache[427] ?: 0, // Длина сегмента №17
            hmiSegmentP1Length18 = cache[428] ?: 0, // Длина сегмента №18
            hmiSegmentP1Length19 = cache[429] ?: 0, // Длина сегмента №19

            pointP1PositionY00 = cache[430] ?: 0, // Координата Y точки №00
            pointP1PositionY01 = cache[431] ?: 0, // Координата Y точки №00
            pointP1PositionY02 = cache[432] ?: 0, // Координата Y точки №00
            pointP1PositionY03 = cache[433] ?: 0, // Координата Y точки №00
            pointP1PositionY04 = cache[434] ?: 0, // Координата Y точки №00
            pointP1PositionY05 = cache[435] ?: 0, // Координата Y точки №00
            pointP1PositionY06 = cache[436] ?: 0, // Координата Y точки №00
            pointP1PositionY07 = cache[437] ?: 0, // Координата Y точки №00
            pointP1PositionY08 = cache[438] ?: 0, // Координата Y точки №00
            pointP1PositionY09 = cache[439] ?: 0, // Координата Y точки №00
            pointP1PositionY10 = cache[440] ?: 0, // Координата Y точки №00
            pointP1PositionY11 = cache[441] ?: 0, // Координата Y точки №00
            pointP1PositionY12 = cache[442] ?: 0, // Координата Y точки №00
            pointP1PositionY13 = cache[443] ?: 0, // Координата Y точки №00
            pointP1PositionY14 = cache[444] ?: 0, // Координата Y точки №00
            pointP1PositionY15 = cache[445] ?: 0, // Координата Y точки №00
            pointP1PositionY16 = cache[446] ?: 0, // Координата Y точки №00
            pointP1PositionY17 = cache[447] ?: 0, // Координата Y точки №00
            pointP1PositionY18 = cache[448] ?: 0, // Координата Y точки №00
            pointP1PositionY19 = cache[449] ?: 0, // Координата Y точки №00

            pointP1PositionX00 = cache[450] ?: 0, // Координата X точки №00
            pointP1PositionX01 = cache[451] ?: 0, // Координата X точки №00
            pointP1PositionX02 = cache[452] ?: 0, // Координата X точки №00
            pointP1PositionX03 = cache[453] ?: 0, // Координата X точки №00
            pointP1PositionX04 = cache[454] ?: 0, // Координата X точки №00
            pointP1PositionX05 = cache[455] ?: 0, // Координата X точки №00
            pointP1PositionX06 = cache[456] ?: 0, // Координата X точки №00
            pointP1PositionX07 = cache[457] ?: 0, // Координата X точки №00
            pointP1PositionX08 = cache[458] ?: 0, // Координата X точки №00
            pointP1PositionX09 = cache[459] ?: 0, // Координата X точки №00
            pointP1PositionX10 = cache[460] ?: 0, // Координата X точки №00
            pointP1PositionX11 = cache[461] ?: 0, // Координата X точки №00
            pointP1PositionX12 = cache[462] ?: 0, // Координата X точки №00
            pointP1PositionX13 = cache[463] ?: 0, // Координата X точки №00
            pointP1PositionX14 = cache[464] ?: 0, // Координата X точки №00
            pointP1PositionX15 = cache[465] ?: 0, // Координата X точки №00
            pointP1PositionX16 = cache[466] ?: 0, // Координата X точки №00
            pointP1PositionX17 = cache[467] ?: 0, // Координата X точки №00
            pointP1PositionX18 = cache[468] ?: 0, // Координата X точки №00
            pointP1PositionX19 = cache[469] ?: 0, // Координата X точки №00

            segmentP1Angle00_01 = cache[470] ?: 0, //Угол между сегментами 00-01
            segmentP1Angle01_02 = cache[471] ?: 0, //Угол между сегментами 01-02
            segmentP1Angle02_03 = cache[472] ?: 0, //Угол между сегментами 02-03
            segmentP1Angle03_04 = cache[473] ?: 0, //Угол между сегментами 03-04
            segmentP1Angle04_05 = cache[474] ?: 0, //Угол между сегментами 04-05
            segmentP1Angle05_06 = cache[475] ?: 0, //Угол между сегментами 05-06
            segmentP1Angle06_07 = cache[476] ?: 0, //Угол между сегментами 06-07
            segmentP1Angle07_08 = cache[477] ?: 0, //Угол между сегментами 07-08
            segmentP1Angle08_09 = cache[478] ?: 0, //Угол между сегментами 08-09
            segmentP1Angle09_10 = cache[479] ?: 0, //Угол между сегментами 09-10
            segmentP1Angle10_11 = cache[480] ?: 0, //Угол между сегментами 10-11
            segmentP1Angle11_12 = cache[481] ?: 0, //Угол между сегментами 11-12
            segmentP1Angle12_13 = cache[482] ?: 0, //Угол между сегментами 12-13
            segmentP1Angle13_14 = cache[483] ?: 0, //Угол между сегментами 13-14
            segmentP1Angle14_15 = cache[484] ?: 0, //Угол между сегментами 14-15
            segmentP1Angle15_16 = cache[485] ?: 0, //Угол между сегментами 15-16
            segmentP1Angle16_17 = cache[486] ?: 0, //Угол между сегментами 16-17
            segmentP1Angle17_18 = cache[487] ?: 0, //Угол между сегментами 17-18
            segmentP1Angle18_19 = cache[488] ?: 0, //Угол между сегментами 18-19
            segmentP1Angle19_00 = cache[489] ?: 0, //Угол между сегментами 19-00

            segmentP1Visibility00 = cache[490] ?: 0, //Видимость сегмента №00
            segmentP1Visibility01 = cache[491] ?: 0, //Видимость сегмента №01
            segmentP1Visibility02 = cache[492] ?: 0, //Видимость сегмента №02
            segmentP1Visibility03 = cache[493] ?: 0, //Видимость сегмента №03
            segmentP1Visibility04 = cache[494] ?: 0, //Видимость сегмента №04
            segmentP1Visibility05 = cache[495] ?: 0, //Видимость сегмента №05
            segmentP1Visibility06 = cache[496] ?: 0, //Видимость сегмента №06
            segmentP1Visibility07 = cache[497] ?: 0, //Видимость сегмента №07
            segmentP1Visibility08 = cache[498] ?: 0, //Видимость сегмента №08
            segmentP1Visibility09 = cache[499] ?: 0, //Видимость сегмента №09
            segmentP1Visibility10 = cache[500] ?: 0, //Видимость сегмента №10
            segmentP1Visibility11 = cache[501] ?: 0, //Видимость сегмента №11
            segmentP1Visibility12 = cache[502] ?: 0, //Видимость сегмента №12
            segmentP1Visibility13 = cache[503] ?: 0, //Видимость сегмента №13
            segmentP1Visibility14 = cache[504] ?: 0, //Видимость сегмента №14
            segmentP1Visibility15 = cache[505] ?: 0, //Видимость сегмента №15
            segmentP1Visibility16 = cache[506] ?: 0, //Видимость сегмента №16
            segmentP1Visibility17 = cache[507] ?: 0, //Видимость сегмента №17
            segmentP1Visibility18 = cache[508] ?: 0, //Видимость сегмента №18
            segmentP1Visibility19 = cache[509] ?: 0, //Видимость сегмента №19
        ),
        //Polygon #2
        polygonP2 = PolygonP2State(
            // Параметры отезков для отображения полигона №2
            hmiSegmentP2Length00 = cache[510] ?: 0, // Длина сегмента №00
            hmiSegmentP2Length01 = cache[511] ?: 0, // Длина сегмента №01
            hmiSegmentP2Length02 = cache[512] ?: 0, // Длина сегмента №02
            hmiSegmentP2Length03 = cache[513] ?: 0, // Длина сегмента №03
            hmiSegmentP2Length04 = cache[514] ?: 0, // Длина сегмента №04
            hmiSegmentP2Length05 = cache[515] ?: 0, // Длина сегмента №05
            hmiSegmentP2Length06 = cache[516] ?: 0, // Длина сегмента №06
            hmiSegmentP2Length07 = cache[517] ?: 0, // Длина сегмента №07
            hmiSegmentP2Length08 = cache[518] ?: 0, // Длина сегмента №08
            hmiSegmentP2Length09 = cache[519] ?: 0, // Длина сегмента №09
            hmiSegmentP2Length10 = cache[520] ?: 0, // Длина сегмента №10
            hmiSegmentP2Length11 = cache[521] ?: 0, // Длина сегмента №11
            hmiSegmentP2Length12 = cache[522] ?: 0, // Длина сегмента №12
            hmiSegmentP2Length13 = cache[523] ?: 0, // Длина сегмента №13
            hmiSegmentP2Length14 = cache[524] ?: 0, // Длина сегмента №14
            hmiSegmentP2Length15 = cache[525] ?: 0, // Длина сегмента №15
            hmiSegmentP2Length16 = cache[526] ?: 0, // Длина сегмента №16
            hmiSegmentP2Length17 = cache[527] ?: 0, // Длина сегмента №17
            hmiSegmentP2Length18 = cache[528] ?: 0, // Длина сегмента №18
            hmiSegmentP2Length19 = cache[529] ?: 0, // Длина сегмента №19
            // Параметры отезков для отображения полигона №2
            pointP2PositionY00 = cache[530] ?: 0, // Координата Y точки №00
            pointP2PositionY01 = cache[531] ?: 0, // Координата Y точки №00
            pointP2PositionY02 = cache[532] ?: 0, // Координата Y точки №00
            pointP2PositionY03 = cache[533] ?: 0, // Координата Y точки №00
            pointP2PositionY04 = cache[534] ?: 0, // Координата Y точки №00
            pointP2PositionY05 = cache[535] ?: 0, // Координата Y точки №00
            pointP2PositionY06 = cache[536] ?: 0, // Координата Y точки №00
            pointP2PositionY07 = cache[537] ?: 0, // Координата Y точки №00
            pointP2PositionY08 = cache[538] ?: 0, // Координата Y точки №00
            pointP2PositionY09 = cache[539] ?: 0, // Координата Y точки №00
            pointP2PositionY10 = cache[540] ?: 0, // Координата Y точки №00
            pointP2PositionY11 = cache[541] ?: 0, // Координата Y точки №00
            pointP2PositionY12 = cache[542] ?: 0, // Координата Y точки №00
            pointP2PositionY13 = cache[543] ?: 0, // Координата Y точки №00
            pointP2PositionY14 = cache[544] ?: 0, // Координата Y точки №00
            pointP2PositionY15 = cache[545] ?: 0, // Координата Y точки №00
            pointP2PositionY16 = cache[546] ?: 0, // Координата Y точки №00
            pointP2PositionY17 = cache[547] ?: 0, // Координата Y точки №00
            pointP2PositionY18 = cache[548] ?: 0, // Координата Y точки №00
            pointP2PositionY19 = cache[549] ?: 0, // Координата Y точки №00
            // Параметры отезков для отображения полигона №2
            pointP2PositionX00 = cache[550] ?: 0, // Координата X точки №00
            pointP2PositionX01 = cache[551] ?: 0, // Координата X точки №00
            pointP2PositionX02 = cache[552] ?: 0, // Координата X точки №00
            pointP2PositionX03 = cache[553] ?: 0, // Координата X точки №00
            pointP2PositionX04 = cache[554] ?: 0, // Координата X точки №00
            pointP2PositionX05 = cache[555] ?: 0, // Координата X точки №00
            pointP2PositionX06 = cache[556] ?: 0, // Координата X точки №00
            pointP2PositionX07 = cache[557] ?: 0, // Координата X точки №00
            pointP2PositionX08 = cache[558] ?: 0, // Координата X точки №00
            pointP2PositionX09 = cache[559] ?: 0, // Координата X точки №00
            pointP2PositionX10 = cache[560] ?: 0, // Координата X точки №00
            pointP2PositionX11 = cache[561] ?: 0, // Координата X точки №00
            pointP2PositionX12 = cache[562] ?: 0, // Координата X точки №00
            pointP2PositionX13 = cache[563] ?: 0, // Координата X точки №00
            pointP2PositionX14 = cache[564] ?: 0, // Координата X точки №00
            pointP2PositionX15 = cache[565] ?: 0, // Координата X точки №00
            pointP2PositionX16 = cache[566] ?: 0, // Координата X точки №00
            pointP2PositionX17 = cache[567] ?: 0, // Координата X точки №00
            pointP2PositionX18 = cache[568] ?: 0, // Координата X точки №00
            pointP2PositionX19 = cache[569] ?: 0, // Координата X точки №00
            // Параметры отезков для отображения полигона №2
            segmentP2Angle00_01 = cache[570] ?: 0, //Угол между сегментами 00-01
            segmentP2Angle01_02 = cache[571] ?: 0, //Угол между сегментами 01-02
            segmentP2Angle02_03 = cache[572] ?: 0, //Угол между сегментами 02-03
            segmentP2Angle03_04 = cache[573] ?: 0, //Угол между сегментами 03-04
            segmentP2Angle04_05 = cache[574] ?: 0, //Угол между сегментами 04-05
            segmentP2Angle05_06 = cache[575] ?: 0, //Угол между сегментами 05-06
            segmentP2Angle06_07 = cache[576] ?: 0, //Угол между сегментами 06-07
            segmentP2Angle07_08 = cache[577] ?: 0, //Угол между сегментами 07-08
            segmentP2Angle08_09 = cache[578] ?: 0, //Угол между сегментами 08-09
            segmentP2Angle09_10 = cache[579] ?: 0, //Угол между сегментами 09-10
            segmentP2Angle10_11 = cache[580] ?: 0, //Угол между сегментами 10-11
            segmentP2Angle11_12 = cache[581] ?: 0, //Угол между сегментами 11-12
            segmentP2Angle12_13 = cache[582] ?: 0, //Угол между сегментами 12-13
            segmentP2Angle13_14 = cache[583] ?: 0, //Угол между сегментами 13-14
            segmentP2Angle14_15 = cache[584] ?: 0, //Угол между сегментами 14-15
            segmentP2Angle15_16 = cache[585] ?: 0, //Угол между сегментами 15-16
            segmentP2Angle16_17 = cache[586] ?: 0, //Угол между сегментами 16-17
            segmentP2Angle17_18 = cache[587] ?: 0, //Угол между сегментами 17-18
            segmentP2Angle18_19 = cache[588] ?: 0, //Угол между сегментами 18-19
            segmentP2Angle19_00 = cache[589] ?: 0, //Угол между сегментами 19-00
            // Параметры отезков для отображения полигона №2
            segmentP2Visibility00 = cache[590] ?: 0, //Видимость сегмента №00
            segmentP2Visibility01 = cache[591] ?: 0, //Видимость сегмента №01
            segmentP2Visibility02 = cache[592] ?: 0, //Видимость сегмента №02
            segmentP2Visibility03 = cache[593] ?: 0, //Видимость сегмента №03
            segmentP2Visibility04 = cache[594] ?: 0, //Видимость сегмента №04
            segmentP2Visibility05 = cache[595] ?: 0, //Видимость сегмента №05
            segmentP2Visibility06 = cache[596] ?: 0, //Видимость сегмента №06
            segmentP2Visibility07 = cache[597] ?: 0, //Видимость сегмента №07
            segmentP2Visibility08 = cache[598] ?: 0, //Видимость сегмента №08
            segmentP2Visibility09 = cache[599] ?: 0, //Видимость сегмента №09
            segmentP2Visibility10 = cache[600] ?: 0, //Видимость сегмента №10
            segmentP2Visibility11 = cache[601] ?: 0, //Видимость сегмента №11
            segmentP2Visibility12 = cache[602] ?: 0, //Видимость сегмента №12
            segmentP2Visibility13 = cache[603] ?: 0, //Видимость сегмента №13
            segmentP2Visibility14 = cache[604] ?: 0, //Видимость сегмента №14
            segmentP2Visibility15 = cache[605] ?: 0, //Видимость сегмента №15
            segmentP2Visibility16 = cache[606] ?: 0, //Видимость сегмента №16
            segmentP2Visibility17 = cache[607] ?: 0, //Видимость сегмента №17
            segmentP2Visibility18 = cache[608] ?: 0, //Видимость сегмента №18
            segmentP2Visibility19 = cache[609] ?: 0, //Видимость сегмента №19
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
    private fun safeDivideThousands(value: Int?): Float {
        return (value ?: 0) / 1000f
    }

    data class HomeScreenState(
        val operatingParams: CraneOperatingParametersState = CraneOperatingParametersState(),
        val parameterScale: CraneParameterScaleState = CraneParameterScaleState(),
        val information: InformationState = InformationState(),
        val craneMechanismsCondition: CraneMechanismsConditionState = CraneMechanismsConditionState(),
        val polygonP1: PolygonP1State = PolygonP1State(),
        val polygonP2: PolygonP2State = PolygonP2State(),
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
        // Параметры отезков для отображения полигона №1
        val segmentP1Angle00_01: Int = 0, //Угол между сегментами 00-01
        val segmentP1Angle01_02: Int = 0, //Угол между сегментами 01-02
        val segmentP1Angle02_03: Int = 0, //Угол между сегментами 02-03
        val segmentP1Angle03_04: Int = 0, //Угол между сегментами 03-04
        val segmentP1Angle04_05: Int = 0, //Угол между сегментами 04-05
        val segmentP1Angle05_06: Int = 0, //Угол между сегментами 05-06
        val segmentP1Angle06_07: Int = 0, //Угол между сегментами 06-07
        val segmentP1Angle07_08: Int = 0, //Угол между сегментами 07-08
        val segmentP1Angle08_09: Int = 0, //Угол между сегментами 08-09
        val segmentP1Angle09_10: Int = 0, //Угол между сегментами 09-10
        val segmentP1Angle10_11: Int = 0, //Угол между сегментами 10-11
        val segmentP1Angle11_12: Int = 0, //Угол между сегментами 11-12
        val segmentP1Angle12_13: Int = 0, //Угол между сегментами 12-13
        val segmentP1Angle13_14: Int = 0, //Угол между сегментами 13-14
        val segmentP1Angle14_15: Int = 0, //Угол между сегментами 14-15
        val segmentP1Angle15_16: Int = 0, //Угол между сегментами 15-16
        val segmentP1Angle16_17: Int = 0, //Угол между сегментами 16-17
        val segmentP1Angle17_18: Int = 0, //Угол между сегментами 17-18
        val segmentP1Angle18_19: Int = 0, //Угол между сегментами 18-19
        val segmentP1Angle19_00: Int = 0, //Угол между сегментами 19-00
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
        // Параметры отезков для отображения полигона №2
        val segmentP2Angle00_01: Int = 0, //Угол между сегментами 00-01
        val segmentP2Angle01_02: Int = 0, //Угол между сегментами 01-02
        val segmentP2Angle02_03: Int = 0, //Угол между сегментами 02-03
        val segmentP2Angle03_04: Int = 0, //Угол между сегментами 03-04
        val segmentP2Angle04_05: Int = 0, //Угол между сегментами 04-05
        val segmentP2Angle05_06: Int = 0, //Угол между сегментами 05-06
        val segmentP2Angle06_07: Int = 0, //Угол между сегментами 06-07
        val segmentP2Angle07_08: Int = 0, //Угол между сегментами 07-08
        val segmentP2Angle08_09: Int = 0, //Угол между сегментами 08-09
        val segmentP2Angle09_10: Int = 0, //Угол между сегментами 09-10
        val segmentP2Angle10_11: Int = 0, //Угол между сегментами 10-11
        val segmentP2Angle11_12: Int = 0, //Угол между сегментами 11-12
        val segmentP2Angle12_13: Int = 0, //Угол между сегментами 12-13
        val segmentP2Angle13_14: Int = 0, //Угол между сегментами 13-14
        val segmentP2Angle14_15: Int = 0, //Угол между сегментами 14-15
        val segmentP2Angle15_16: Int = 0, //Угол между сегментами 15-16
        val segmentP2Angle16_17: Int = 0, //Угол между сегментами 16-17
        val segmentP2Angle17_18: Int = 0, //Угол между сегментами 17-18
        val segmentP2Angle18_19: Int = 0, //Угол между сегментами 18-19
        val segmentP2Angle19_00: Int = 0, //Угол между сегментами 19-00
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

    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("HomeScreenVM", "ViewModel cleared")
        // Деактивируем конфигурацию при уничтожении ViewModel
        modbusRepository.setActiveUiConfiguration(null)
    }
}