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


  //Настройка защиты от самопроизвольного движения крюка вниз
    private val _setValueHoistSpdHookUp = mutableStateOf("")
    val setValueHoistSpdHookUp: State<String> = _setValueHoistSpdHookUp
    private val _setValueHoistEncoderMaxDeltaEn = mutableStateOf("")
    val setValueHoistEncoderMaxDeltaEn: State<String> = _setValueHoistEncoderMaxDeltaEn




    private val _errorMessageHoist = mutableStateOf<String?>(null)
    val errorMessageHoist: State<String?> = _errorMessageHoist

    private val _logMessageHoist = mutableStateOf<String?>(null)
    val logMessageHoist: State<String?> = _logMessageHoist

    private val _errorMessageHoistMontage = mutableStateOf<String?>(null)
    val errorMessageHoistMontage: State<String?> = _errorMessageHoistMontage

    private val _logMessageHoistMontage = mutableStateOf<String?>(null)
    val logMessageHoistMontage: State<String?> = _logMessageHoistMontage

    private val _errorMessageHoistBrake = mutableStateOf<String?>(null)
    val errorMessageHoistBrake: State<String?> = _errorMessageHoistBrake

    private val _logMessageHoistBrake = mutableStateOf<String?>(null)
    val logMessageHoistBrake: State<String?> = _logMessageHoistBrake

    private val _errorMessageHoistHookSetting = mutableStateOf<String?>(null)
    val errorMessageHoistHookSetting: State<String?> = _errorMessageHoistHookSetting

    private val _logMessageHoistHookSetting = mutableStateOf<String?>(null)
    val logMessageHoistHookSetting: State<String?> = _logMessageHoistHookSetting


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

    private val _setValueAnemometerMinWind = mutableStateOf("")
    val setValueAnemometerMinWind: State<String> = _setValueAnemometerMinWind

    private val _setValueAnemometerMaxWind = mutableStateOf("")
    val setValueAnemometerMaxWind: State<String> = _setValueAnemometerMaxWind

    private val _setValueAnemometerMinAmper = mutableStateOf("")
    val setValueAnemometerMinAmper: State<String> = _setValueAnemometerMinAmper

    private val _setValueAnemometerMaxAmper = mutableStateOf("")
    val setValueAnemometerMaxAmper: State<String> = _setValueAnemometerMaxAmper

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

    //Настройка параметров максимального рассогласования абсолютных энкодеров
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
        33010, 30151, 33209, 33147, 33148, 33011, 33012, 33013, 33014, 33016, 33021, 33022, 33023, 33024,
        33171, 33172, 33173, 33174, 33061, 33062, 33063, 33064, 33017, 33018, 33019,
        33070, 33071, 33072, 33073, 33074, 33075, 33076, 33077, 33078,
        31150, 31151, 31152, 31153, 31154, 31155, 31156, 31157,31158, 31159,
        31160, 31161, 31162, 31163, 31164, 31165, 31166, 31167, 31168, 31169,
        31170, 31171, 31175, 31176,
        31250, 31251, 31252, 31253, 31254, 31255, 31256, 31257, 31258,
        31260, 31263, 31262, 31261, 31264, 31265,
        31350, 31351, 31352, 1693, 31354, 31355, 31359, 31356, 31357, 31358,
        31360, 31363, 31362, 31361, 31364, 31365,
        31450, 31451, 31452, 31453, 31454, 31455, 31456, 31457, 31458,
        31460, 31463, 31462, 31461, 31464, 31465,
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
            userState = UserState (
                //СЕРВИСНЫЙ РЕЖИМ. СИСТЕМНАЯ НАСТРОЙКА УРОВЕНЬ ИНЖЕНЕР
                currentDataLogErrStopHoist = cache[33070] ?: 0,
                currentPasswordOrNaladka = cache[33071] ?: 0,
                currentFOURPolygonsEn = cache[33072] ?: 0,
                currentWithoutRingCurrentCollector = cache[33073] ?: 0,
                currentZoneACCDECActive = cache[33074] ?: 0,
                currentLukModeActiveEn = cache[33075] ?: 0,
            ),

            sensorsState = SensorsState (
                //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА ВЕТРА
                currentWind = safeDivideTens(cache[33010] ?: 0),
                currentAnemometerSensor = cache[30151] ?: 0,
                currentAnemometerStatus = cache[33209] ?: 0,

                hmiWindSpeedLvl1 = safeDivideTens(cache[33011] ?: 0),
                hmiWindSpeedLvl2 = safeDivideTens(cache[33012] ?: 0),
                hmiWindSpeedLvl1Montage = safeDivideTens(cache[33013] ?: 0),
                hmiAnemometerTimeSmooth = cache[33014] ?: 0,
                hmiReadAnemometerMinWind = safeDivideTens(cache[33016] ?: 0), //Нижний предел измерения анемометра, м/с
                hmiReadAnemometerMaxWind = safeDivideTens(cache[33017] ?: 0), //Верхний предел измерения анемометра, м/с
                hmiReadAnemometerMinAmper = safeDivideTens(cache[33018] ?: 0), //Нижний предел показаний анемометра, mA
                hmiReadAnemometerMaxAmper = safeDivideTens(cache[33019] ?: 0), //Верхний предел показаний анемометра, mA


                //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА ГРУЗА
                hmiAdvLoadCellLL= cache[33021] ?: 0, //Минимальное значение измерения
                hmiAdvLoadCellHH= cache[33022] ?: 0, //Максимальное значение измерения
                hmiArraySizeWeightSmooth= cache[33023] ?: 0, //Глубина сглаживания измерения веса
                hmiWeightSPOverSpeed = safeDivideTens(cache[33024] ?: 0), //Уставка массы, ниже которой будет разрешена повышенная скорость


                //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКОВ ПОЛОЖЕНИЯ
                currentAbsEncDeltaControlEn = cache[33076] ?: 0,
                currentAbsEncDebugModeHoist = cache[33077] ?: 0,
                currentAbsEncDebugModeSlew = cache[33078] ?: 0,

                hmiSlewNumberTeethOPU= cache[33171] ?: 0, //Число зубьев ОПУ крана
                hmiSlewNumberTeethGearSensor= cache[33172] ?: 0, //Число зубьев шестерни датчика поворота крана
                hmiSlewSingleTurnStepsSensor= cache[33173] ?: 0, //Количество импульсов за оборот датчика
                hmiSlewRatioK = safeDivideHundred(cache[33174] ?: 0), //Коэффициент передачи ОПУ и шестерни датчика

                hmiHoistAbsEncMaxDeltaEn= cache[33061] ?: 0, //Максимальное рассогласование абс. энкодера подъема
                hmiSlewAbsEncMaxDeltaEn= cache[33062] ?: 0, //Максимальное рассогласование абс. энкодера подъема
                hmiTrolleyAbsEncMaxDeltaEn= cache[33063] ?: 0, //Максимальное рассогласование абс. энкодера подъема
                hmiCraneAbsEncMaxDeltaEn= cache[33064] ?: 0, //Максимальное рассогласование абс. энкодера подъема

                //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА РЕПЕРНОЙ ТОЧКИ
                craneCTRLPointEN = cache[33147] ?: 0,
                ctrlPointControlInterval = safeDivideTens(cache[33148] ?: 0),
            ),
            hoistDriveState = HoistDriveState(
                hmiSpdRefButtonHoist    = cache[31150] ?: 0,
                hmiSpdRef1Hoist         = cache[31151] ?: 0,
                hmiSpdRef2Hoist         = cache[31152] ?: 0,
                hmiSpdRef3Hoist         = cache[31153] ?: 0,
                hmiSpdRef4Hoist         = cache[31154] ?: 0,
                hmiSpdRefMAXHoist       = cache[31159] ?: 0,
                hmiSpdRefNomHoist       = cache[31155] ?: 0,
                hmiHoistACC = safeDivideTens(cache[31156] ?: 0),
                hmiHoistDEC = safeDivideTens(cache[31157] ?: 0),

                hmiSpdRef1HoistMontage = cache[31166] ?: 0,
                hmiSpdRef2HoistMontage = cache[31167] ?: 0,
                hmiHoistACCMontage = safeDivideTens(cache[31168] ?: 0),
                hmiHoistDECMontage = safeDivideTens(cache[31169] ?: 0),

                hmiHoistLimitState = cache[31158] ?: 0,
                hmiLspAutoStatHoist = cache[31160] ?: 0,
                hmiLspDist3Hoist = safeDivideTens(cache[31163] ?: 0),
                hmiLspDist2Hoist = safeDivideTens(cache[31162] ?: 0),
                hmiLspDist1Hoist = safeDivideTens(cache[31161] ?: 0),
                hmiZoneAccHoist = safeDivideTens(cache[31164] ?: 0),
                hmiZoneDecHoist = safeDivideTens(cache[31165] ?: 0),

                hmiHoistBrakeControlStat = cache[31170] ?: 0, //Бит отслеживаения состояния тормоза 1- отслеживаем обраную связь, 0 -нет
                hmiHoistBrakeControlTimeout = safeDivideTens(cache[31171] ?: 0), //Время ожидания обратной связи от тормоза

                hmiHoistSpdHookUp= safeDivideTens(cache[31175] ?: 0), //Значение скорости подъема при аварийном движении крюка вниз [*0.1Hz]
                hmiHoistEncoderMaxDelta= cache[31176] ?: 0, //Величина рассогласования по энкодеру для срабатывания ошибки по самопроизвольному движению крюка вниз

            ),
            slewDriveState = SlewDriveState(
                hmiSpdRefLowSlew = cache[31250] ?: 0,
                hmiSpdRef1Slew  = cache[31251] ?: 0,
                hmiSpdRef2Slew  = cache[31252] ?: 0,
                hmiSpdRef3Slew  = cache[31253] ?: 0,
                hmiSpdRef4Slew  = cache[31254] ?: 0,
                hmiSpdRefNomSlew = cache[31255] ?: 0,
                hmiSlewACC = safeDivideTens(cache[31256] ?: 0),
                hmiSlewDEC = safeDivideTens(cache[31257] ?: 0),

                hmiSlewLimitState = cache[31258] ?: 0,
                hmiLspAutoStatSlew = cache[31260] ?: 0,
                hmiLspDist3Slew = safeDivideTens(cache[31263] ?: 0),
                hmiLspDist2Slew = safeDivideTens(cache[31262] ?: 0),
                hmiLspDist1Slew = safeDivideTens(cache[31261] ?: 0),
                hmiZoneAccSlew = safeDivideTens(cache[31264] ?: 0),
                hmiZoneDecSlew = safeDivideTens(cache[1654] ?: 0),
                ),
            trolleyDriveState = TrolleyDriveState(
                hmiSpdRefLowTrolley = cache[31350] ?: 0,
                hmiSpdRef1Trolley   = cache[31351] ?: 0,
                hmiSpdRef2Trolley   = cache[31352] ?: 0,
                hmiSpdRef3Trolley   = cache[31353] ?: 0,
                hmiSpdRef4Trolley   = cache[31354] ?: 0,
                hmiSpdRefNomTrolley = cache[31355] ?: 0,
                hmiSpdRefOverSpd    = safeDivideTens( cache[31359] ?: 0), //Масса груза выше которой запрешена повышенная скорость тележки
                hmiTrolleyACC = safeDivideTens(cache[31356] ?: 0),
                hmiTrolleyDEC = safeDivideTens(cache[31357] ?: 0),

                hmiTrolleyLimitState = cache[31358] ?: 0,
                hmiLspAutoStatSlew = cache[31360] ?: 0,
                hmiLspDist3Slew = safeDivideTens(cache[31363] ?: 0),
                hmiLspDist2Slew =safeDivideTens(cache[31362] ?: 0),
                hmiLspDist1Slew = safeDivideTens(cache[31361] ?: 0),
                hmiZoneAccSlew = safeDivideTens(cache[31364] ?: 0),
                hmiZoneDecSlew = safeDivideTens(cache[31365] ?: 0)
            ),
            craneDriveState = CraneDriveState(
                hmiSpdRefLowCrane = cache[31450] ?: 0,
                hmiSpdRef1Crane = cache[31451] ?: 0,
                hmiSpdRef2Crane = cache[31452] ?: 0,
                hmiSpdRef3Crane = cache[31453] ?: 0,
                hmiSpdRef4Crane = cache[31454] ?: 0,
                hmiSpdRefNomCrane = cache[31455] ?: 0,
                hmiCraneACC = safeDivideTens(cache[31456] ?: 0),
                hmiCraneDEC = safeDivideTens(cache[31457] ?: 0),

                hmiCraneLimitState = cache[31458] ?: 0,
                hmiLspAutoStatCrane = cache[31460] ?: 0,
                hmiLspDist3Crane = safeDivideTens(cache[31463] ?: 0),
                hmiLspDist2Crane = safeDivideTens(cache[31462] ?: 0),
                hmiLspDist1Crane = safeDivideTens(cache[31461] ?: 0),
                hmiZoneAccCrane = safeDivideTens(cache[31464] ?: 0),
                hmiZoneDecCrane = safeDivideTens(cache[31465] ?: 0)
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
        val userState: UserState = UserState(),
        val sensorsState: SensorsState = SensorsState(),
        val hoistDriveState: HoistDriveState = HoistDriveState(),
        val slewDriveState: SlewDriveState = SlewDriveState(),
        val trolleyDriveState: TrolleyDriveState = TrolleyDriveState(),
        val craneDriveState: CraneDriveState = CraneDriveState(),
    )

    data class UserState(
        //СЕРВИСНЫЙ РЕЖИМ. СИСТЕМНАЯ НАСТРОЙКА УРОВЕНЬ ИНЖЕНЕР
        val currentDataLogErrStopHoist: Int = 0,
        val currentPasswordOrNaladka: Int = 0,
        val currentFOURPolygonsEn: Int = 0,
        val currentWithoutRingCurrentCollector: Int = 0,
        val currentZoneACCDECActive: Int = 0,
        val currentLukModeActiveEn: Int = 0,
    )

    data class SensorsState(
        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА ВЕТРА
        val currentWind: Float = 0f,
        val currentAnemometerSensor: Int = 0,
        val currentAnemometerStatus: Int = 0,

        val hmiWindSpeedLvl1: Float = 0f,
        val hmiWindSpeedLvl2: Float = 0f,
        val hmiWindSpeedLvl1Montage: Float = 0f,
        val hmiAnemometerTimeSmooth: Int = 0,
        val hmiReadAnemometerMinWind: Float = 0f, //Нижний предел измерения анемометра, м/с
        val hmiReadAnemometerMaxWind: Float = 0f, //Верхний предел измерения анемометра, м/с
        val hmiReadAnemometerMinAmper: Float = 0f, //Нижний предел показаний анемометра, mA
        val hmiReadAnemometerMaxAmper: Float = 0f, //Верхний предел показаний анемометра, mA

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

        val hmiHoistSpdHookUp: Float = 0f, //Значение скорости подъема при аварийном движении крюка вниз [*0.1Hz]
        val hmiHoistEncoderMaxDelta: Int = 0, //Величина рассогласования по энкодеру для срабатывания ошибки по самопроизвольному движению крюка вниз
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
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ПРИВОДА!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */

    // Driver. Сброс ошибки приводов
    fun onSetFltRst() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Сброс ошибки
                modbusRepository.writeRegister(61100, 1)
                delay(200)
                modbusRepository.writeRegister(61100, 0)
                // Логирование
            } catch (e: Exception) {
            }
        }
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
                modbusRepository.writeRegister(61156, model)
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
                modbusRepository.writeRegister(61157, model)
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
                modbusRepository.writeRegister(61151, model)
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
                modbusRepository.writeRegister(61152, model)
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
                modbusRepository.writeRegister(61153, model)
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
                modbusRepository.writeRegister(61154, model)
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
                modbusRepository.writeRegister(61150, model)
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
                modbusRepository.writeRegister(61159, model)
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
                modbusRepository.writeRegister(61155, model)
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
                modbusRepository.writeRegister(61168, model)
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
                modbusRepository.writeRegister(61169, model)
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
                modbusRepository.writeRegister(61166, model)
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
                modbusRepository.writeRegister(61167, model)
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
                modbusRepository.writeRegister(61170, 1)
                delay(200)
                modbusRepository.writeRegister(61170, 0)
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
                modbusRepository.writeRegister(61171, model)
                _errorMessageHoistBrake.value = null
                _logMessageHoistBrake.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoistBrake.value = null
                _errorMessageHoistBrake.value = "${e.message}"
            }
        }
    }


    // Hoist. Запись значения скорости подъема при аварийном движении крюка вниз [*0.1Hz]
    fun onSetHoistSpdHookUp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistSpdHookUp.value, 0.0f,10.0f )
                // Запись в регистры
                modbusRepository.writeRegister(61175, model)
                _errorMessageHoistHookSetting.value = null
                _logMessageHoistHookSetting.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoistHookSetting.value = null
                _errorMessageHoistHookSetting.value = "${e.message}"
            }
        }
    }

    // Hoist. Запись величины рассогласования по энкодеру для срабатывания ошибки по самопроизвольному движению крюка вниз
    fun onSetHoistEncoderMaxDeltaEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueHoistEncoderMaxDeltaEn.value, 0,100 )
                // Запись в регистры
                modbusRepository.writeRegister(61176, model)
                _errorMessageHoistHookSetting.value = null
                _logMessageHoistHookSetting.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoistHookSetting.value = null
                _errorMessageHoistHookSetting.value = "${e.message}"
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
                modbusRepository.writeRegister(61256, model)
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
                modbusRepository.writeRegister(61257, model)
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
                modbusRepository.writeRegister(61251, model)
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
                modbusRepository.writeRegister(61252, model)
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
                modbusRepository.writeRegister(61253, model)
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
                modbusRepository.writeRegister(61254, model)
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
                modbusRepository.writeRegister(61255, model)
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
                modbusRepository.writeRegister(61356, model)
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
                modbusRepository.writeRegister(61357, model)
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
                modbusRepository.writeRegister(61351, model)
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
                modbusRepository.writeRegister(61352, model)
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
                modbusRepository.writeRegister(61353, model)
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
                modbusRepository.writeRegister(61354, model)
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
                modbusRepository.writeRegister(61350, model)
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
                modbusRepository.writeRegister(61359, model)
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
                modbusRepository.writeRegister(61355, model)
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
                modbusRepository.writeRegister(61456, model)
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
                modbusRepository.writeRegister(61457, model)
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
                modbusRepository.writeRegister(61451, model)
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
                modbusRepository.writeRegister(61452, model)
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
                modbusRepository.writeRegister(61453, model)
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
                modbusRepository.writeRegister(61454, model)
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
                modbusRepository.writeRegister(63147, 1)
                delay(200)
                modbusRepository.writeRegister(63147, 0)
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
                modbusRepository.writeRegister(63148, model)
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
                modbusRepository.writeRegister(63011, model)
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
                modbusRepository.writeRegister(63012, model)
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
                modbusRepository.writeRegister(63013, model)
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
                modbusRepository.writeRegister(63014, model)
                _errorMessageWind.value = null
                _logMessageWind.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWind.value = null
                _errorMessageWind.value = "${e.message}"
            }
        }
    }
// Wind. Запись. Нижний предел измерения анемометра, м/с
    fun onSetAnemometerMinWindClbr() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueAnemometerMinWind.value, 0.0f,30.0f )
                // Запись в регистры
                modbusRepository.writeRegister(63016, model)
                _errorMessageWind.value = null
                _logMessageWind.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWind.value = null
                _errorMessageWind.value = "${e.message}"
            }
        }
    }
// Wind. Запись. Верхний предел измерения анемометра, м/с
    fun onSetAnemometerMaxWindClbr() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueAnemometerMaxWind.value, 0.0f,100.0f )
                // Запись в регистры
                modbusRepository.writeRegister(63017, model)
                _errorMessageWind.value = null
                _logMessageWind.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWind.value = null
                _errorMessageWind.value = "${e.message}"
            }
        }
    }

    // Wind. Запись. Нижний предел измерения анемометра, mA
    fun onSetAnemometerMinAmperClbr() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueAnemometerMinAmper.value, 0.0f,20.0f )
                // Запись в регистры
                modbusRepository.writeRegister(63017, model)
                _errorMessageWind.value = null
                _logMessageWind.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWind.value = null
                _errorMessageWind.value = "${e.message}"
            }
        }
    }
// Wind. Запись. Верхний предел измерения анемометра, mA
    fun onSetAnemometerMaxAmperClbr() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueAnemometerMaxAmper.value, 0.0f,20.0f )
                // Запись в регистры
                modbusRepository.writeRegister(63017, model)
                _errorMessageWind.value = null
                _logMessageWind.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageWind.value = null
                _errorMessageWind.value = "${e.message}"
            }
        }
    }
// Wind. Команда калибровать
    fun onSetAnemometerClbr() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                Log.d("InitialSetupViewModel", "Writing registers...")
                // Активация калибровки
                modbusRepository.writeRegister(63010, 1)
                delay(200)
                modbusRepository.writeRegister(63010, 0)
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
                modbusRepository.writeRegister(63021, model)
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
                modbusRepository.writeRegister(63022, model)
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
                modbusRepository.writeRegister(63023, model)
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
                modbusRepository.writeRegister(63024, model)
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
                modbusRepository.writeRegister(33171, model)
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
                modbusRepository.writeRegister(33172, model)
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
                modbusRepository.writeRegister(33173, model)
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
                modbusRepository.writeRegister(33174, model)
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
                modbusRepository.writeRegister(63061, model)
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
                modbusRepository.writeRegister(63062, model)
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
                modbusRepository.writeRegister(63063, model)
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
                modbusRepository.writeRegister(63064, model)
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
                modbusRepository.writeRegister(63066, 1)
                delay(200)
                modbusRepository.writeRegister(63066, 0)
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
                modbusRepository.writeRegister(63067, 1)
                delay(200)
                modbusRepository.writeRegister(63067, 0)
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
                modbusRepository.writeRegister(63068, 1)
                delay(200)
                modbusRepository.writeRegister(63068, 0)
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
                modbusRepository.writeRegister(63070, 1)
                delay(200)
                modbusRepository.writeRegister(63070, 0)
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
                modbusRepository.writeRegister(63071, 1)
                delay(200)
                modbusRepository.writeRegister(63071, 0)
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
                modbusRepository.writeRegister(63072, 1)
                delay(200)
                modbusRepository.writeRegister(63072, 0)
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
                modbusRepository.writeRegister(63073, 1)
                delay(200)
                modbusRepository.writeRegister(63073, 0)
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
                modbusRepository.writeRegister(63074, 1)
                delay(200)
                modbusRepository.writeRegister(63074, 0)
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
                modbusRepository.writeRegister(63075, 1)
                delay(200)
                modbusRepository.writeRegister(63075, 0)
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
                // Активация функции
                modbusRepository.writeRegister(62000, 1)
                delay(200)
                modbusRepository.writeRegister(62000, 0)
            } catch (e: Exception) {
            }
        }
    }

    // ACSS. Сброс ошибки АЦСС
    fun onSetACSSRst() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Сброс ошибки
                modbusRepository.writeRegister(60161, 1)
                delay(200)
                modbusRepository.writeRegister(60161, 0)
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

    fun updateAnemometerMinWind(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueAnemometerMinWind.value = value } }

    fun updateAnemometerMaxWind(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueAnemometerMaxWind.value = value } }

    fun updateAnemometerMinAmper(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueAnemometerMinAmper.value = value } }

    fun updateAnemometerMaxAmper(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,2}(\\.\\d{0,1})?$"))) {
            _setValueAnemometerMaxAmper.value = value } }



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

    fun updateHoistSpdHookUp(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,1}(\\.\\d{0,1})?$"))) {
            _setValueHoistSpdHookUp.value = value }}

    fun updateHoistEncoderMaxDeltaEn(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueHoistEncoderMaxDeltaEn.value = value }}


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