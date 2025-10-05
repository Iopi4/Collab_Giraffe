package com.example.giraffenew.service_menu.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.entities.UIServiceMenuRegisterConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Float
import kotlin.Int

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

    private val _setValueHoistSpdNom = mutableStateOf("")
    val setValueHoistSpdNom: State<String> = _setValueHoistSpdNom

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

    private val _setValueHoistBrakeControlTimeout = mutableStateOf("")
    val setValueHoistBrakeControlTimeout: State<String> = _setValueHoistBrakeControlTimeout



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

    private val _setValueSlewSpdNom = mutableStateOf("")
    val setValueSlewSpdNom: State<String> = _setValueSlewSpdNom

    private val _setValueSlewACC = mutableStateOf("")
    val setValueSlewACC: State<String> = _setValueSlewACC

    private val _setValueSlewDEC = mutableStateOf("")
    val setValueSlewDEC: State<String> = _setValueSlewDEC

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

    private val _setValueTrolleySpdNom = mutableStateOf("")
    val setValueTrolleySpdNom: State<String> = _setValueTrolleySpdNom

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


    // СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ПРИВОДА ХОДА КРАНА
    private val _setValueCraneSpdRef1 = mutableStateOf("")
    val setValueCraneSpdRef1: State<String> = _setValueCraneSpdRef1

    private val _setValueCraneSpdRef2 = mutableStateOf("")
    val setValueCraneSpdRef2: State<String> = _setValueCraneSpdRef2

    private val _setValueCraneSpdRef3 = mutableStateOf("")
    val setValueCraneSpdRef3: State<String> = _setValueCraneSpdRef3

    private val _setValueCraneSpdRef4 = mutableStateOf("")
    val setValueCraneSpdRef4: State<String> = _setValueCraneSpdRef4

    private val _setValueCraneACC = mutableStateOf("")
    val setValueCraneACC: State<String> = _setValueCraneACC

    private val _setValueCraneDEC = mutableStateOf("")
    val setValueCraneDEC: State<String> = _setValueCraneDEC

    private val _errorMessageCrane = mutableStateOf<String?>(null)
    val errorMessageCrane: State<String?> = _errorMessageCrane

    private val _logMessageCrane = mutableStateOf<String?>(null)
    val logMessageCrane: State<String?> = _logMessageCrane

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
    //Настройка датчика поворота крана
    //Число зубьев ОПУ крана
    private val _setValueSlewNumberTeethOPU = mutableStateOf("")
    val setValueSlewNumberTeethOPU: State<String> = _setValueSlewNumberTeethOPU
    //Число зубьев шестерни датчика поворота крана
    private val _setValueSlewNumberTeethGearSensor = mutableStateOf("")
    val setValueSlewNumberTeethGearSensor: State<String> = _setValueSlewNumberTeethGearSensor
    //Количество импульсов за оборот датчика
    private val _setValueSlewSingleTurnStepsSensor = mutableStateOf("")
    val setValueSlewSingleTurnStepsSensor: State<String> = _setValueSlewSingleTurnStepsSensor
    //Коэффициент передачи ОПУ и шестерни датчика
    private val _setValueSlewRatioK = mutableStateOf("")
    val setValueSlewRatioK: State<String> = _setValueSlewRatioK

    //Настройка параметров максимального рассогласования энкодеров
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

//СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА РЕПЕРНОЙ ТОЧКИ

    private val _setValueCTRLPointControlInterval = mutableStateOf("")
    val setValueCTRLPointControlInterval: State<String> = _setValueCTRLPointControlInterval

    private val _errorMessageCTRLPoint = mutableStateOf<String?>(null)
    val errorMessageCTRLPoint: State<String?> = _errorMessageCTRLPoint

    private val _logMessageCTRLPoint = mutableStateOf<String?>(null)
    val logMessageCTRLPoint: State<String?> = _logMessageCTRLPoint


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
        163, 381, 336, 373, 374, 175, 154, 176, 644, 645, 646, 647, 648, 649,
        650, 651, 652, 653, 654, 655, 656, 657, 997,
        750, 751, 752, 753, 754, 755, 756, 757,
        1610, 1611, 1612, 1613, 1614, 1615, 1616, 1617, 1618, 1619,
        1620, 1621, 1622, 1623, 1624, 1625, 1626, 1627, 1628, 1629, 1630, 1631,
        1640, 1641, 1642, 1643, 1644, 1645, 1646, 1647, 1648, 1649,
        1650, 1651, 1652, 1653, 1654, 1690, 1691, 1692, 1693, 1694,
        1695, 1696, 1697, 1698, 1699, 1700, 1701, 1702, 1703, 1704,
        1705, 1730, 1731, 1732, 1733, 1734, 1735, 1736, 1737, 1738,
        1739, 1740, 1741, 1742, 1743, 1744,
        2001
        // Регистры для считывания
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
        Log.d("ServiceMenuScreenVM", "Pausing data collection")
        dataCollectionJob?.cancel()
        dataCollectionJob = null
    }

    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДАТЧИКИ ОТОБРАЖЕНИЕ ДАННЫХ И СОСТОЯНИЯ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // //Функция createServiceMenuState() для обработки отфильтрованных данных:
    private fun createServiceMenuState(cache: Map<Int, Int?>): ServiceMenuState {
        return ServiceMenuState(
            sensorsAndUserState = SensorsAndUserState (
                //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА ВЕТРА
                currentWind = safeDivideTens(cache[163] ?: 0),
                currentAnemometerSensor = cache[381] ?: 0,
                currentAnemometerStatus = cache[336] ?: 0,

                hmiWindSpeedLvl1 = safeDivideTens(cache[175] ?: 0),
                hmiWindSpeedLvl2= safeDivideTens(cache[154] ?: 0),
                hmiWindSpeedLvl1Montage = safeDivideTens(cache[176] ?: 0),
                hmiAnemometerTimeSmooth = cache[644] ?: 0,
                hmiK= safeDivideThousand(cache[645] ?: 0),

                //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА ГРУЗА
                hmiAdvLoadCellLL= cache[646] ?: 0, //Минимальное значение измерения
                hmiAdvLoadCellHH= cache[647] ?: 0, //Максимальное значение измерения
                hmiArraySizeWeightSmooth= cache[648] ?: 0, //Глубина сглаживания измерения веса
                hmiWeightSPOverSpeed = safeDivideTens(cache[649] ?: 0), //Уставка массы, ниже которой будет разрешена повышенная скорость

                //СЕРВИСНЫЙ РЕЖИМ. СИСТЕМНАЯ НАСТРОЙКА УРОВЕНЬ ИНЖЕНЕР
                currentDataLogErrStopHoist = cache[750] ?: 0,
                currentPasswordOrNaladka = cache[751] ?: 0,
                currentFOURPolygonsEn = cache[752] ?: 0,
                currentWithoutRingCurrentCollector = cache[753] ?: 0,
                currentZoneACCDECActive = cache[754] ?: 0,
                currentLukModeActiveEn = cache[755] ?: 0,
                currentAndroidModeActiveEn = cache[2001] ?: 0,
                //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКОВ ПОЛОЖЕНИЯ
                currentAbsEncDeltaControlEn = cache[755] ?: 0,
                currentAbsEncDebugModeHoist = cache[756] ?: 0,
                currentAbsEncDebugModeSlew = cache[757] ?: 0,

                hmiSlewNumberTeethOPU= cache[650] ?: 0, //Число зубьев ОПУ крана
                hmiSlewNumberTeethGearSensor= cache[651] ?: 0, //Число зубьев шестерни датчика поворота крана
                hmiSlewSingleTurnStepsSensor= cache[652] ?: 0, //Количество импульсов за оборот датчика
                hmiSlewRatioK = safeDivideHundred(cache[653] ?: 0), //Коэффициент передачи ОПУ и шестерни датчика

                hmiHoistAbsEncMaxDeltaEn= cache[654] ?: 0, //Максимальное рассогласование абс. энкодера подъема
                hmiSlewAbsEncMaxDeltaEn= cache[655] ?: 0, //Максимальное рассогласование абс. энкодера подъема
                hmiTrolleyAbsEncMaxDeltaEn= cache[656] ?: 0, //Максимальное рассогласование абс. энкодера подъема
                hmiCraneAbsEncMaxDeltaEn= cache[657] ?: 0, //Максимальное рассогласование абс. энкодера подъема

                //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА РЕПЕРНОЙ ТОЧКИ
                craneCTRLPointEN = cache[373] ?: 0,
                ctrlPointControlInterval = safeDivideTens(cache[374] ?: 0),
            ),
            hoistDriveState = HoistDriveState(
                hmiSpdRefButtonHoist    = cache[1610] ?: 0,
                hmiSpdRef1Hoist         = cache[1611] ?: 0,
                hmiSpdRef2Hoist         = cache[1612] ?: 0,
                hmiSpdRef3Hoist         = cache[1613] ?: 0,
                hmiSpdRef4Hoist         = cache[1614] ?: 0,
                hmiSpdRefMAXHoist       = cache[1615] ?: 0,
                hmiSpdRefNomHoist       = cache[1616] ?: 0,
                hmiHoistACC = safeDivideTens(cache[1617] ?: 0),
                hmiHoistDEC = safeDivideTens(cache[1618] ?: 0),

                hmiSpdRef1HoistMontage = cache[1619] ?: 0,
                hmiSpdRef2HoistMontage = cache[1620] ?: 0,
                hmiHoistACCMontage = safeDivideTens(cache[1621] ?: 0),
                hmiHoistDECMontage = safeDivideTens(cache[1622] ?: 0),

                hmiHoistLimitState = cache[1623] ?: 0,
                hmiLspAutoStatHoist = cache[1624] ?: 0,
                hmiLspDist3Hoist = safeDivideTens(cache[1625] ?: 0),
                hmiLspDist2Hoist = safeDivideTens(cache[1626] ?: 0),
                hmiLspDist1Hoist = safeDivideTens(cache[1627] ?: 0),
                hmiZoneAccHoist = safeDivideTens(cache[1628] ?: 0),
                hmiZoneDecHoist = safeDivideTens(cache[1629] ?: 0),

                hmiHoistBrakeControlStat = cache[1630] ?: 0, //Бит отслеживаения состояния тормоза 1- отслеживаем обраную связь, 0 -нет
                hmiHoistBrakeControlTimeout = safeDivideTens(cache[1631] ?: 0), //Время ожидания обратной связи от тормоза
            ),
            slewDriveState = SlewDriveState(
                hmiSpdRefLowSlew = cache[1640] ?: 0,
                hmiSpdRef1Slew  = cache[1641] ?: 0,
                hmiSpdRef2Slew  = cache[1642] ?: 0,
                hmiSpdRef3Slew  = cache[1643] ?: 0,
                hmiSpdRef4Slew  = cache[1644] ?: 0,
                hmiSpdRefNomSlew = cache[1645] ?: 0,
                hmiSlewACC = safeDivideTens(cache[1646] ?: 0),
                hmiSlewDEC = safeDivideTens(cache[1647] ?: 0),


                hmiSlewLimitState = cache[1648] ?: 0,
                hmiLspAutoStatSlew = cache[1649] ?: 0,
                hmiLspDist3Slew = safeDivideTens(cache[1650] ?: 0),
                hmiLspDist2Slew = safeDivideTens(cache[1651] ?: 0),
                hmiLspDist1Slew = safeDivideTens(cache[1652] ?: 0),
                hmiZoneAccSlew = safeDivideTens(cache[1653] ?: 0),
                hmiZoneDecSlew = safeDivideTens(cache[1654] ?: 0),


                ),
            trolleyDriveState = TrolleyDriveState(
                hmiSpdRefLowTrolley = cache[1690] ?: 0,
                hmiSpdRef1Trolley   = cache[1691] ?: 0,
                hmiSpdRef2Trolley   = cache[1692] ?: 0,
                hmiSpdRef3Trolley   = cache[1693] ?: 0,
                hmiSpdRef4Trolley   = cache[1694] ?: 0,
                hmiSpdRefNomTrolley = cache[1695] ?: 0,
                hmiSpdRefOverSpd    = safeDivideTens( cache[1696] ?: 0), //Масса груза выше которой запрешена повышенная скорость тележки
                hmiTrolleyACC = safeDivideTens(cache[1697] ?: 0),
                hmiTrolleyDEC = safeDivideTens(cache[1698] ?: 0),

                hmiTrolleyLimitState = cache[1699] ?: 0,
                hmiLspAutoStatSlew = cache[1700] ?: 0,
                hmiLspDist3Slew = safeDivideTens(cache[1701] ?: 0),
                hmiLspDist2Slew =safeDivideTens(cache[1702] ?: 0),
                hmiLspDist1Slew = safeDivideTens(cache[1703] ?: 0),
                hmiZoneAccSlew = safeDivideTens(cache[1704] ?: 0),
                hmiZoneDecSlew = safeDivideTens(cache[1705] ?: 0)
            ),
            craneDriveState = CraneDriveState(
                hmiSpdRefLowCrane = cache[1730] ?: 0,
                hmiSpdRef1Crane = cache[1731] ?: 0,
                hmiSpdRef2Crane = cache[1732] ?: 0,
                hmiSpdRef3Crane = cache[1733] ?: 0,
                hmiSpdRef4Crane = cache[1734] ?: 0,
                hmiSpdRefNomCrane = cache[1735] ?: 0,
                hmiCraneACC = safeDivideTens(cache[1736] ?: 0),
                hmiCraneDEC = safeDivideTens(cache[1737] ?: 0),

                hmiCraneLimitState = cache[1738] ?: 0,
                hmiLspAutoStatCrane = cache[1739] ?: 0,
                hmiLspDist3Crane = safeDivideTens(cache[1740] ?: 0),
                hmiLspDist2Crane = safeDivideTens(cache[1741] ?: 0),
                hmiLspDist1Crane = safeDivideTens(cache[1742] ?: 0),
                hmiZoneAccCrane = safeDivideTens(cache[1743] ?: 0),
                hmiZoneDecCrane = safeDivideTens(cache[1744] ?: 0)
            ),
        )
    }

    private fun safeDivideTens(value: Int?): Float {
        return (value ?: 0) / 10f
    }
    private fun safeDivideHundred(value: Int?): Float {
        return (value ?: 0) / 100f
    }
    private fun safeDivideThousand(value: Int?): Float {
        return (value ?: 0) / 1000f
    }

    data class ServiceMenuState(
        val sensorsAndUserState: SensorsAndUserState = SensorsAndUserState(),
        val hoistDriveState: HoistDriveState = HoistDriveState(),
        val slewDriveState: SlewDriveState = SlewDriveState(),
        val trolleyDriveState: TrolleyDriveState = TrolleyDriveState(),
        val craneDriveState: CraneDriveState = CraneDriveState(),
    )

    data class SensorsAndUserState(
        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА ВЕТРА
        val currentWind: Float = 0f,
        val currentAnemometerSensor: Int = 0,
        val currentAnemometerStatus: Int = 0,

        val hmiWindSpeedLvl1: Float = 0f,
        val hmiWindSpeedLvl2: Float = 0f,
        val hmiWindSpeedLvl1Montage: Float = 0f,
        val hmiAnemometerTimeSmooth: Int = 0,
        val hmiK: Float = 0f,

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА ГРУЗА
        val hmiAdvLoadCellLL: Int = 0, //Минимальное значение измерения
        val hmiAdvLoadCellHH: Int = 0, //Максимальное значение измерения
        val hmiArraySizeWeightSmooth: Int = 0, //Глубина сглаживания измерения веса
        val hmiWeightSPOverSpeed: Float = 0f, //Уставка массы, ниже которой будет разрешена повышенная скорость

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКОВ ПОЛОЖЕНИЯ
        val currentAbsEncDeltaControlEn: Int = 0,
        val currentAbsEncDebugModeHoist: Int = 0,
        val currentAbsEncDebugModeSlew: Int = 0,

        val hmiSlewNumberTeethOPU: Int = 0, //Число зубьев ОПУ крана
        val hmiSlewNumberTeethGearSensor: Int = 0, //Число зубьев шестерни датчика поворота крана
        val hmiSlewSingleTurnStepsSensor: Int = 0, //Количество импульсов за оборот датчика
        val hmiSlewRatioK: Float = 0f, //Коэффициент передачи ОПУ и шестерни датчика

        val hmiHoistAbsEncMaxDeltaEn: Int = 0, //Максимальное рассогласование абс. энкодера подъема
        val hmiSlewAbsEncMaxDeltaEn: Int = 0, //Максимальное рассогласование абс. энкодера подъема
        val hmiTrolleyAbsEncMaxDeltaEn: Int = 0, //Максимальное рассогласование абс. энкодера подъема
        val hmiCraneAbsEncMaxDeltaEn: Int = 0, //Максимальное рассогласование абс. энкодера подъема


        //СЕРВИСНЫЙ РЕЖИМ. СИСТЕМНАЯ НАСТРОЙКА УРОВЕНЬ ИНЖЕНЕР
        val currentDataLogErrStopHoist: Int = 0,
        val currentPasswordOrNaladka: Int = 0,
        val currentFOURPolygonsEn: Int = 0,
        val currentWithoutRingCurrentCollector: Int = 0,
        val currentZoneACCDECActive: Int = 0,
        val currentLukModeActiveEn: Int = 0,
        val currentAndroidModeActiveEn: Int = 0,
        val craneCTRLPointEN: Int = 0, //Флаг включения, отключения датчика реперной точки хода крана
        val ctrlPointControlInterval: Float = 0f, //Интервал контроля реперной точки
    )
    data class HoistDriveState(
        val hmiSpdRefButtonHoist: Int = 0, // Пониженая скорость подъема
        val hmiSpdRef1Hoist: Int = 0, // 1-ая скорость подъема об/мин
        val hmiSpdRef2Hoist: Int = 0, // 2-ая скорость подъема об/мин
        val hmiSpdRef3Hoist: Int = 0, // 3-ая скорость подъема об/мин
        val hmiSpdRef4Hoist: Int = 0, // 4-ая скорость подъема об/мин
        val hmiSpdRefMAXHoist: Int = 0, // Сверхсинхронная скорость об/мин
        val hmiSpdRefNomHoist: Int = 0, // Номинальная скорость ЭД подъема об/мин

        val hmiHoistACC: Float = 0f, // Время разгона, сек
        val hmiHoistDEC: Float = 0f, // Время торможения, сек

        val hmiSpdRef1HoistMontage: Int = 0, // 1-ая скорость с монтажного пульта
        val hmiSpdRef2HoistMontage: Int = 0, // 2-ая скорость с монтажного пульта
        val hmiHoistACCMontage: Float = 0f, // Ускорение с монтажного пульта, сек
        val hmiHoistDECMontage: Float = 0f, // Замедление с монтажного пульта, сек

        val hmiHoistLimitState: Int = 0, //Статус функции проверки ограничений в наладке
        val hmiLspAutoStatHoist: Int = 0, //Статус автоматического снижения скорости
        val hmiLspDist3Hoist: Float = 0f, //Дистанция снижения до 3 скорости, м
        val hmiLspDist2Hoist: Float = 0f, //Дистанция снижения до 2 скорости, м
        val hmiLspDist1Hoist: Float = 0f, //Дистанция снижения до 1 скорости, м
        val hmiZoneAccHoist: Float = 0f, //Зона регулирования ускорения подъема
        val hmiZoneDecHoist: Float = 0f, //Зона регулирования замедления подъема

        val hmiHoistBrakeControlStat: Int = 0, //Бит отслеживаения состояния тормоза 1- отслеживаем обраную связь, 0 -нет
        val hmiHoistBrakeControlTimeout: Float = 0f, //Время ожидания обратной связи от тормоза
    )
    data class SlewDriveState(
        val hmiSpdRefLowSlew: Int = 0,
        val hmiSpdRef1Slew: Int = 0,
        val hmiSpdRef2Slew: Int = 0,
        val hmiSpdRef3Slew: Int = 0,
        val hmiSpdRef4Slew: Int = 0,
        val hmiSpdRefNomSlew: Int = 0,
        val hmiSlewACC: Float = 0f,
        val hmiSlewDEC: Float = 0f,

        val hmiSlewLimitState: Int = 0, //Статус функции проверки ограничений в наладке
        val hmiLspAutoStatSlew: Int = 0, //Статус автоматического снижения скорости
        val hmiLspDist3Slew: Float = 0f, //Дистанция снижения до 3 скорости, м
        val hmiLspDist2Slew: Float = 0f, //Дистанция снижения до 2 скорости, м
        val hmiLspDist1Slew: Float = 0f, //Дистанция снижения до 1 скорости, м
        val hmiZoneAccSlew: Float = 0f, //Зона регулирования ускорения поворота
        val hmiZoneDecSlew: Float = 0f, //Зона регулирования замедления поворота

    )
    data class TrolleyDriveState(
        val hmiSpdRefLowTrolley: Int = 0,
        val hmiSpdRef1Trolley: Int = 0,
        val hmiSpdRef2Trolley: Int = 0,
        val hmiSpdRef3Trolley: Int = 0,
        val hmiSpdRef4Trolley: Int = 0,
        val hmiSpdRefNomTrolley: Int = 0,
        val hmiSpdRefOverSpd: Float = 0f, //Масса груза выше которой запрешена повышенная скорость тележки
        val hmiTrolleyACC: Float = 0f,
        val hmiTrolleyDEC: Float = 0f,

        val hmiTrolleyLimitState: Int = 0, //Статус функции проверки ограничений в наладке
        val hmiLspAutoStatSlew: Int = 0, //Статус автоматического снижения скорости
        val hmiLspDist3Slew: Float = 0f, //Дистанция снижения до 3 скорости, м
        val hmiLspDist2Slew: Float = 0f, //Дистанция снижения до 2 скорости, м
        val hmiLspDist1Slew: Float = 0f, //Дистанция снижения до 1 скорости, м
        val hmiZoneAccSlew: Float = 0f, //Зона регулирования ускорения тележки
        val hmiZoneDecSlew: Float = 0f, //Зона регулирования замедления тележки
    )
    data class CraneDriveState(
        val hmiSpdRefLowCrane: Int = 0,
        val hmiSpdRef1Crane: Int = 0,
        val hmiSpdRef2Crane: Int = 0,
        val hmiSpdRef3Crane: Int = 0,
        val hmiSpdRef4Crane: Int = 0,
        val hmiSpdRefNomCrane: Int = 0,
        val hmiCraneACC: Float = 0f,
        val hmiCraneDEC: Float = 0f,

        val hmiCraneLimitState: Int = 0, //Статус функции проверки ограничений в наладке
        val hmiLspAutoStatCrane: Int = 0, //Статус автоматического снижения скорости
        val hmiLspDist3Crane: Float = 0f, //Дистанция снижения до 3 скорости, м
        val hmiLspDist2Crane: Float = 0f, //Дистанция снижения до 2 скорости, м
        val hmiLspDist1Crane: Float = 0f, //Дистанция снижения до 1 скорости, м
        val hmiZoneAccCrane: Float = 0f, //Зона регулирования ускорения хода крана
        val hmiZoneDecCrane: Float = 0f, //Зона регулирования замедления хода крана
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
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ПРИВОД ПОДЪЕМА!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // Hoist. Запись времени разгона [сек]
    fun onSetHoistACC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistACC.value, 0.0f, 10.0f )
                // Запись в регистры
                modbusRepository.writeRegister(946, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "${e.message}"
            }
        }
    }

    // Hoist. Запись времени торможения [сек]
    fun onSetHoistDEC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistDEC.value, 0.0f,10.0f )
                // Запись в регистры
                modbusRepository.writeRegister(947, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "${e.message}"

            }
        }
    }

    // Hoist. Запись скорости 1.
    fun onSetHoistSpdRef1() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistSpdRef1.value, 0,120 )
                // Запись в регистры
                modbusRepository.writeRegister(941, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "${e.message}"
            }
        }
    }

    // Hoist. Запись скорости 2.
    fun onSetHoistSpdRef2() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistSpdRef2.value, 0,400 )
                // Запись в регистры
                modbusRepository.writeRegister(942, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "${e.message}"
            }
        }
    }

    // Hoist. Запись скорости 3.
    fun onSetHoistSpdRef3() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistSpdRef3.value, 0,1000 )
                // Запись в регистры
                modbusRepository.writeRegister(943, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "${e.message}"
            }
        }
    }

    // Hoist. Запись скорости 4.
    fun onSetHoistSpdRef4() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistSpdRef4.value, 0,3000 )
                // Запись в регистры
                modbusRepository.writeRegister(944, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "${e.message}"
            }
        }
    }

    // Hoist. Запись скорости при нажатии кнопки
    fun onSetHoistSpdRefButton() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistSpdRefButton.value, 0,120 )
                // Запись в регистры
                modbusRepository.writeRegister(940, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "${e.message}"
            }
        }
    }

    // Hoist. Значение максимальной скорости двигателя подъема
    fun onSetHoistSpdRefMax() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistSpdRefMax.value, 0,3000 )
                // Запись в регистры
                modbusRepository.writeRegister(945, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "${e.message}"
            }
        }
    }

    // Hoist. Значение номинальной скорости двигателя подъема
    fun onSetHoistSpdNom() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistSpdNom.value, 0,1500 )
                // Запись в регистры
                modbusRepository.writeRegister(948, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "${e.message}"
            }
        }
    }


    // Hoist. Запись времени разгона [сек]. Режим монтаж
    fun onSetHoistMontageACC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistMontageACC.value, 0.0f,10.0f )
                // Запись в регистры
                modbusRepository.writeRegister(952, model)
                _errorMessageHoistMontage.value = null
                _logMessageHoistMontage.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoistMontage.value = null
                _errorMessageHoistMontage.value = "${e.message}"
            }
        }
    }

    // Hoist. Запись времени торможения [сек]. Режим монтаж
    fun onSetHoistMontageDEC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistMontageDEC.value, 0.0f,10.0f )
                // Запись в регистры
                modbusRepository.writeRegister(953, model)
                _errorMessageHoistMontage.value = null
                _logMessageHoistMontage.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoistMontage.value = null
                _errorMessageHoistMontage.value = "${e.message}"

            }
        }
    }

    // Hoist. Запись скорости 1. Режим монтаж
    fun onSetHoistMontageSpdRef1() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistMontageSpdRef1.value, 0,240 )
                // Запись в регистры
                modbusRepository.writeRegister(950, model)
                _errorMessageHoistMontage.value = null
                _logMessageHoistMontage.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoistMontage.value = null
                _errorMessageHoistMontage.value = "${e.message}"
            }
        }
    }

    // Hoist. Запись скорости 2. Режим монтаж
    fun onSetHoistMontageSpdRef2() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistMontageSpdRef2.value, 0,3000 )
                // Запись в регистры
                modbusRepository.writeRegister(951, model)
                _errorMessageHoistMontage.value = null
                _logMessageHoistMontage.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoistMontage.value = null
                _errorMessageHoistMontage.value = "${e.message}"
            }
        }
    }

    // Hoist. Включение отслеживаения состояния тормоза 1- отслеживаем обраную связь, 0 -нет
    fun onSetHoistBrakeControlEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Запись в регистры
                modbusRepository.writeRegister(1222, 1)
                delay(200)
                modbusRepository.writeRegister(1222, 0)
            } catch (e: Exception) {

            }
        }
    }

    // Hoist. Запись времени ожидания обратной связи от тормоза [сек]
    fun onSetHoistBrakeControlTimeout() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistBrakeControlTimeout.value, 0.0f,2.0f )
                // Запись в регистры
                modbusRepository.writeRegister(1223, model)
                _errorMessageHoistMontage.value = null
                _logMessageHoistMontage.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoistMontage.value = null
                _errorMessageHoistMontage.value = "${e.message}"
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
                val model = validateAndConvertFloat(_setValueSlewACC.value, 0.0f,30.0f )
                // Запись в регистры
                modbusRepository.writeRegister(968, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "${e.message}"
            }
        }
    }

    // Slew. Запись времени торможения [сек]
    fun onSetSlewDEC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueSlewDEC.value, 0.0f,30.0f )
                // Запись в регистры
                modbusRepository.writeRegister(969, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "${e.message}"
            }
        }
    }

    // Slew. Запись скорости 1.
    fun onSetSlewSpdRef1() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueSlewSpdRef1.value, 0,200 )
                // Запись в регистры
                modbusRepository.writeRegister(956, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "${e.message}"
            }
        }
    }

    // Slew. Запись скорости 2.
    fun onSetSlewSpdRef2() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueSlewSpdRef2.value, 0,600 )
                // Запись в регистры
                modbusRepository.writeRegister(957, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "${e.message}"
            }
        }
    }

    // Slew. Запись скорости 3.
    fun onSetSlewSpdRef3() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueSlewSpdRef3.value, 0,1200 )
                // Запись в регистры
                modbusRepository.writeRegister(958, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "${e.message}"
            }
        }
    }

    // Slew. Запись скорости 4.
    fun onSetSlewSpdRef4() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueSlewSpdRef4.value, 0,2000 )
                // Запись в регистры
                modbusRepository.writeRegister(959, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "${e.message}"
            }
        }
    }

    // Slew. Запись номинальной скорости привода поворота
    fun onSetSlewSpdNom() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueSlewSpdNom.value, 0,1500 )
                // Запись в регистры
                modbusRepository.writeRegister(970, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "${e.message}"
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
                val model = validateAndConvertFloat(_setValueTrolleyACC.value, 0.0f,10.0f )
                // Запись в регистры
                modbusRepository.writeRegister(964, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "${e.message}"
            }
        }
    }

    // Trolley. Запись времени торможения [сек]
    fun onSetTrolleyDEC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueTrolleyDEC.value, 0.0f,10.0f)
                // Запись в регистры
                modbusRepository.writeRegister(965, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "${e.message}"

            }
        }
    }

    // Trolley. Запись скорости 1.
    fun onSetTrolleySpdRef1() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleySpdRef1.value, 0,200 )
                // Запись в регистры
                modbusRepository.writeRegister(960, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "${e.message}"
            }
        }
    }

    // Trolley. Запись скорости 2.
    fun onSetTrolleySpdRef2() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleySpdRef2.value, 0,600 )
                // Запись в регистры
                modbusRepository.writeRegister(961, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "${e.message}"
            }
        }
    }

    // Trolley. Запись скорости 3.
    fun onSetTrolleySpdRef3() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleySpdRef3.value, 0,1200 )
                // Запись в регистры
                modbusRepository.writeRegister(962, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "${e.message}"
            }
        }
    }

    // Trolley. Запись скорости 4.
    fun onSetTrolleySpdRef4() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleySpdRef4.value, 0,2000 )
                // Запись в регистры
                modbusRepository.writeRegister(963, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "${e.message}"
            }
        }
    }

    // Trolley. Запись пониженная скорость тележки
    fun onSetTrolleyLsp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleyLsp.value, 0,200 )
                // Запись в регистры
                modbusRepository.writeRegister(966, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "${e.message}"
            }
        }
    }

    // Trolley. Значение веса груза при превышении которого запрещена сверхсинхронная работа тележки
    fun onSetTrolleyOverSpd() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueTrolleyTrolleyOverspd.value, 0.0f,10.0f )
                // Запись в регистры
                modbusRepository.writeRegister(967, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "${e.message}"
            }
        }
    }

    // Trolley. Запись номинальной скорости двигателя тележки
    fun onSetTrolleySpdNom() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueTrolleySpdNom.value, 0,1500 )
                // Запись в регистры
                modbusRepository.writeRegister(971, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "${e.message}"
            }
        }
    }

    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ПРИВОД ХОДА КРАНА!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // Crane. Запись времени разгона [сек]
    fun onSetCraneACC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueCraneACC.value, 0.0f,20.0f )
                // Запись в регистры
                modbusRepository.writeRegister(976, model)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCrane.value = null
                _errorMessageCrane.value = "${e.message}"
            }
        }
    }

    // Crane. Запись времени торможения [сек]
    fun onSetCraneDEC() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueCraneDEC.value, 0.0f,20.0f )
                // Запись в регистры
                modbusRepository.writeRegister(977, model)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCrane.value = null
                _errorMessageCrane.value = "${e.message}"

            }
        }
    }

    // Crane. Запись скорости 1.
    fun onSetCraneSpdRef1() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueCraneSpdRef1.value, 0,300 )
                // Запись в регистры
                modbusRepository.writeRegister(972, model)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCrane.value = null
                _errorMessageCrane.value = "${e.message}"
            }
        }
    }

    // Crane. Запись скорости 2.
    fun onSetCraneSpdRef2() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueCraneSpdRef2.value, 0,700 )
                // Запись в регистры
                modbusRepository.writeRegister(973, model)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCrane.value = null
                _errorMessageCrane.value = "${e.message}"
            }
        }
    }

    // Crane. Запись скорости 3.
    fun onSetCraneSpdRef3() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueCraneSpdRef3.value, 0,1500 )
                // Запись в регистры
                modbusRepository.writeRegister(974, model)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCrane.value = null
                _errorMessageCrane.value = "${e.message}"
            }
        }
    }

    // Crane. Запись скорости 4.
    fun onSetCraneSpdRef4() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueCraneSpdRef4.value, 0,2000 )
                // Запись в регистры
                modbusRepository.writeRegister(975, model)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCrane.value = null
                _errorMessageCrane.value = "${e.message}"
            }
        }
    }

    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДАТЧИК РЕПЕРНОЙ ТОЧКИ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // CTRLPoint. Включение, отключение контроля реперной точки
    fun onSetCTRLPointEN() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Запись в регистры
                modbusRepository.writeRegister(978, 1)
                delay(200)
                modbusRepository.writeRegister(978, 0)
                _errorMessageCTRLPoint.value = null
                _logMessageCTRLPoint.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCTRLPoint.value = null
                _errorMessageCTRLPoint.value = "${e.message}"
            }
        }
    }
    // CTRLPoint. Запись значения интервала контроля реперной точки
    fun onSetValueCTRLPointControlInterval() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueCTRLPointControlInterval.value, 0.0f,300.0f )
                // Запись в регистры
                modbusRepository.writeRegister(979, model)
                _errorMessageCTRLPoint.value = null
                _logMessageCTRLPoint.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCTRLPoint.value = null
                _errorMessageCTRLPoint.value = "${e.message}"
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
                val model = validateAndConvertFloat(_setValueWindSpeedLvl1.value, 0.0f,20.0f )
                // Запись в регистры
                modbusRepository.writeRegister(980, model)
                _errorMessageWind.value = null
                _logMessageWind.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWind.value = null
                _errorMessageWind.value = "${e.message}"
            }
        }
    }
    // Wind. Запись уровня аварийной сигнализации (м/c)
    fun onSetWindSpeedLvl2() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueWindSpeedLvl2.value, 0.0f,30.0f )
                // Запись в регистры
                modbusRepository.writeRegister(981, model)
                _errorMessageWind.value = null
                _logMessageWind.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWind.value = null
                _errorMessageWind.value = "${e.message}"
            }
        }
    }
    // Wind. Запись уровня аварийной сигнализации (м/c), в режиме монтаж
    fun onSetWindSpeedMontageLvl() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueWindSpeedMontageLvl.value, 0.0f,20.0f )
                // Запись в регистры
                modbusRepository.writeRegister(982, model)
                _errorMessageWind.value = null
                _logMessageWind.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWind.value = null
                _errorMessageWind.value = "${e.message}"
            }
        }
    }
    // Wind. Запись времени усреднения, сек
    fun onSetWindTimeSmooth() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueWindTimeSmooth.value, 0,10 )
                // Запись в регистры
                modbusRepository.writeRegister(983, model)
                _errorMessageWind.value = null
                _logMessageWind.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWind.value = null
                _errorMessageWind.value = "${e.message}"
            }
        }
    }
    // Wind. Запись значения калибровки
    fun onSetWindSpeedLvlClbr() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueWindSpeedLvlClbr.value, 0.0f,30.0f )
                // Запись в регистры
                modbusRepository.writeRegister(984, model)
                _errorMessageWind.value = null
                _logMessageWind.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWind.value = null
                _errorMessageWind.value = "${e.message}"
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
                _errorMessageWind.value = "${e.message}"
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
                _errorMessageWind.value = "${e.message}"
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
                _errorMessageLoadCell.value = "${e.message}"
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
                _errorMessageLoadCell.value = "${e.message}"
            }
        }
    }

    // LoadSensor. Запись глубина сглаживания веса. Количество точек.
    fun onSetLoadCellSmoothingDepth() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setLoadCellSmoothingDepth.value, 0,500 )
                // Запись в регистры
                modbusRepository.writeRegister(1202, model)
                _errorMessageLoadCell.value = null
                _logMessageLoadCell.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageLoadCell.value = null
                _errorMessageLoadCell.value = "${e.message}"
            }
        }
    }

    // LoadSensor. Запись веса ограничение сверхскорости подъема
    fun onSetLoadCellOverSpeedThreshold() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setLoadCellOverSpeedThreshold.value, 0.0f,10.0f )
                // Запись в регистры
                modbusRepository.writeRegister(1203, model)
                _errorMessageLoadCell.value = null
                _logMessageLoadCell.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageLoadCell.value = null
                _errorMessageLoadCell.value = "${e.message}"
            }
        }
    }

    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДАТЧИКИ ПОЛОЖЕНИЯ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // SlewEncoder. Запись Число зубьев ОПУ крана
    fun onSetSlewNumberTeethOPU() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueSlewNumberTeethOPU.value, 0,999 )
                // Запись в регистры
                modbusRepository.writeRegister(990, model)
                _errorMessageAbsEnc.value = null
                _logMessageAbsEnc.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageAbsEnc.value = null
                _errorMessageAbsEnc.value = "${e.message}"
            }
        }
    }

    // SlewEncoder. Запись Число зубьев шестерни датчика поворота крана
    fun onSetSlewNumberTeethGearSensor() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueSlewNumberTeethGearSensor.value, 0,100 )
                // Запись в регистры
                modbusRepository.writeRegister(991, model)
                _errorMessageAbsEnc.value = null
                _logMessageAbsEnc.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageAbsEnc.value = null
                _errorMessageAbsEnc.value = "${e.message}"
            }
        }
    }

    // SlewEncoder. Запись Количество импульсов за оборот датчика
    fun onSetSlewSingleTurnStepsSensor() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueSlewSingleTurnStepsSensor.value, 0,65535 )
                // Запись в регистры
                modbusRepository.writeRegister(992, model)
                _errorMessageAbsEnc.value = null
                _logMessageAbsEnc.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageAbsEnc.value = null
                _errorMessageAbsEnc.value = "${e.message}"
            }
        }
    }

    // SlewEncoder. Коэффициент передачи ОПУ и шестерни датчика
    fun onSetSlewRatioK() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueSlewRatioK.value, 0.00f,999.99f )
                // Запись в регистры
                modbusRepository.writeRegister(993, model)
                _errorMessageAbsEnc.value = null
                _logMessageAbsEnc.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageAbsEnc.value = null
                _errorMessageAbsEnc.value = "${e.message}"
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
                _errorMessageAbsEnc.value = "${e.message}"
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
                _errorMessageAbsEnc.value = "${e.message}"
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
                _errorMessageAbsEnc.value = "${e.message}"
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
                _errorMessageAbsEnc.value = "${e.message}"
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
                delay(200)
                modbusRepository.writeRegister(994, 0)
            } catch (e: Exception) {
            }
        }
    }
    // AbsEncoder. Флаг установки режима наладки механизма подъема при выходе датчика из строя
    fun onSetAbsEncDebugModeHoistEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                Log.d("InitialSetupViewModel", "Writing registers...")
                // Активация калибровки
                modbusRepository.writeRegister(995, 1)
                delay(200)
                modbusRepository.writeRegister(995, 0)
            } catch (e: Exception) {
            }
        }
    }
    //Флаг установки режима наладки механизма поворота при выходе датчика из строя"
    fun onSetAbsEncDebugModeSlewEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                Log.d("InitialSetupViewModel", "Writing registers...")
                // Активация калибровки
                modbusRepository.writeRegister(996, 1)
                delay(200)
                modbusRepository.writeRegister(996, 0)
            } catch (e: Exception) {
            }
        }
    }

    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!СЕРВИСНЫЙ ИНЖЕНЕР!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */

// Ingener. Включить запрет работы на скоростях выше второй при ошибке записи файла регистратора
    fun onSetDataLogErrStopHoist() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                // Активация калибровки
                modbusRepository.writeRegister(1204, 1)
                delay(200)
                modbusRepository.writeRegister(1204, 0)
                // Логирование
            } catch (e: Exception) {
            }
        }
    }
    // Ingener. Включить режим авторизации по паролю "naladka"
    fun onSetPasswordNaladka() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                // Активация калибровки
                modbusRepository.writeRegister(1205, 1)
                delay(200)
                modbusRepository.writeRegister(1205, 0)
                // Логирование
            } catch (e: Exception) {
            }
        }
    }
    // Ingener. Включить режим работы полигонов (1- активизация 4 полигона 20 точек, 0- 2 полигона по 10 точек )
    fun onSetFourPolygonsEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                // Активация калибровки
                modbusRepository.writeRegister(1206, 1)
                delay(200)
                modbusRepository.writeRegister(1206, 0)
                // Логирование
            } catch (e: Exception) {
            }
        }
    }
    // Ingener. Включить исполнение крана без кольцевого токосъемника. (по молчанию с токосъемником)
    fun onSetWithoutRingCurrentCollectorEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                // Активация калибровки
                modbusRepository.writeRegister(1207, 1)
                delay(200)
                modbusRepository.writeRegister(1207, 0)
                // Логирование
            } catch (e: Exception) {
            }
        }
    }
    // Ingener. Активация плавной регулирования ускорений/замедлений
    fun onSetZoneACCDECActiveEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                // Активация калибровки
                modbusRepository.writeRegister(1208, 1)
                delay(200)
                modbusRepository.writeRegister(1208, 0)
                // Логирование
            } catch (e: Exception) {
            }
        }
    }
    // Ingener. Включение функции контроля люка в ОПУ
    fun onSetLukModeActiveEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                // Активация калибровки
                modbusRepository.writeRegister(1209, 1)
                delay(200)
                modbusRepository.writeRegister(1209, 0)
                // Логирование
            } catch (e: Exception) {
            }
        }
    }

    // Ingener. Включение доступа к функциям Android
    fun onSetAndroidModeActiveEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                // Активация калибровки
                modbusRepository.writeRegister(2000, 1)
                delay(200)
                modbusRepository.writeRegister(2000, 0)
                // Логирование
            } catch (e: Exception) {
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

    //CTRLPoint. Обновление для UI
    fun updateCTRLPointControlInterval(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueCTRLPointControlInterval.value = value } }

    // ДАТЧИКИ ПОЛОЖЕНИЯ. Обновления для UI
    fun updateValueSlewNumberTeethOPU(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueSlewNumberTeethOPU.value = value } }

    fun updateValueSlewNumberTeethGearSensor(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueSlewNumberTeethGearSensor.value = value } }

    fun updateValueSlewSingleTurnStepsSensor(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueSlewSingleTurnStepsSensor.value = value } }

    fun updateValueSlewRatioK(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,2})?$"))) {
            _setValueSlewRatioK.value = value } }

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

    fun updateHoistSpdRefNom(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueHoistSpdNom.value = value }}

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

    fun updateHoistBrakeControlTimeout(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,1}(\\.\\d{0,1})?$"))) {
            _setValueHoistBrakeControlTimeout.value = value }}



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

    fun updateSlewSpdNom(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueSlewSpdNom.value = value }}


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

    fun updateTrolleySpdNom(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueTrolleySpdNom.value = value }}


    // Ход крана. Обновления для UI
    fun updateCraneACC(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueCraneACC.value = value } }
    fun updateCraneDEC(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueCraneDEC.value = value } }
    fun updateCraneSpdRef1(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueCraneSpdRef1.value = value } }
    fun updateCraneSpdRef2(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueCraneSpdRef2.value = value } }
    fun updateCraneSpdRef3(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueCraneSpdRef3.value = value } }
    fun updateCraneSpdRef4(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueCraneSpdRef4.value = value } }


    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("ServiceMenuVM", "ViewModel cleared")
        // Деактивируем конфигурацию при уничтожении ViewModel
        modbusRepository.setActiveUiConfiguration(null)
    }
}