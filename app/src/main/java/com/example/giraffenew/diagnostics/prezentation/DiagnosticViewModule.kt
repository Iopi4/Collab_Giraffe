package com.example.giraffenew.diagnostics.prezentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.entities.UIDiagnosticRegisterConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiagnosticViewModule @Inject constructor(
    private val modbusRepository: ModbusRepository
) : ViewModel() {

    val addressesSystemParameters = listOf(33029, 33020, 39011, 30151, 33209,
        33050, 33051, 33052, 33053, 33054, 33055, 33056, 33057,
        39014, 39015, 39016, 39013, 39028, 31100, 31200, 31300, 31400) // Только нужные регистры Вкладки Система управления

    val addressesDrivesParameters = listOf(
        31113, 31101, 39020, 31108, 39041, 31102, 31103, 31104, 31110, 31106, 31107,
        31213, 31201, 39021, 31208, 39042, 31202, 31203, 31204, 31210, 31206, 31207,
        31313, 31301, 39022, 31308, 39043, 31302, 31303, 31304, 31310, 31306, 31307,
        31413, 31401, 39023, 31408, 39044, 31402, 31403, 31404, 31406, 31407, 39029, 33165) // Только нужные регистры Вкладки Система управления

    val addressesPLCParameters = listOf(
        30100, 30101, 30102, 30103, 30104, 30105, 30106, 30107, 30110, 30111, 30112, 30117,
        30120, 30121, 30122, 30123, 30124, 30125, 30126, 30127, 30150, 30151, 30170, 30171, 30172, 30173, 30174, 30175, 30176, 30177, 30180, 30181)
    private val allAddresses = (addressesSystemParameters+addressesDrivesParameters+addressesPLCParameters).distinct()

    private val _state = mutableStateOf(DiagnosticState())
    val state: State<DiagnosticState> = _state

    private var dataCollectionJob: Job? = null

    /**
    2. В коллекторе потока registerCache, будем преобразовывать весь кэш в отфильтрованную карту (только наши адреса).
    Далее, мы можем сохранять предыдущую отфильтрованную карту и сравнивать с новой. Если они одинаковые, то ничего не делаем.
    Если изменилась, то вычисляем новое состояние и обновляем _state.
     */

    fun setActiveConfiguration() {
        modbusRepository.setActiveUiConfiguration(UIDiagnosticRegisterConfiguration.NAME_DIAGNOSTIC)
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

        Log.d("DiagnosticScreenVM", "Starting data collection for ${UIDiagnosticRegisterConfiguration.NAME_DIAGNOSTIC}")
        dataCollectionJob = viewModelScope.launch(Dispatchers.IO) {
            modbusRepository.getConfigurationFlow(UIDiagnosticRegisterConfiguration.NAME_DIAGNOSTIC)
                .collect { cache ->
                    Log.d("DiagnosticViewModel", "Updating from cache, size: ${cache.size}")
                    // Обработка данных
                    try {
                        // Создаем отфильтрованную карту только для нужных адресов
                        val filteredCache = allAddresses.associate { addr ->
                            addr to cache[addr]?.value
                        }
                        _state.value = createDiagnosticScreenState(filteredCache)
                    } catch (e: Exception) {
                        Log.e("DiagnosticScreenVM", "Data collection error", e)
                    }
                }
        }
    }

    // Приостановка сбора данных
    fun pauseDataCollection() {
        Log.d("DiagnosticViewModel", "Pausing data collection")
        dataCollectionJob?.cancel()
        dataCollectionJob = null
    }

    //Функция createDiagnosticScreenState() для обработки отфильтрованных данных:
    private fun createDiagnosticScreenState(cache: Map<Int, Int?>): DiagnosticState {
        return DiagnosticState(
            systemParams = ControlSystemParametersState(
                //Датчики ветра и усилия
                ogpLimSw        = cache[33029] ?: 0,
                currentWeightAI = cache[33020] ?: 0,
                weightSensorErr = cache[39011] ?: 0,
                currentWindAI   = cache[30151] ?: 0,
                windSensorErr   = cache[33209] ?: 0,
                //Данные с абсолютного энкодера. Подъем
                currentAbsEncHoistPositionWord1 = cache[33050] ?: 0,
                currentAbsEncHoistPositionWord2 = cache[33051] ?: 0,

                //Данные с абсолютного энкодера. Поворот
                currentAbsEncSlewPositionWord1 = cache[33052] ?: 0,
                currentAbsEncSlewPositionWord2 = cache[33053] ?: 0,

                //Данные с абсолютного энкодера. Тележка
                currentAbsEncTrolleyPositionWord1 = cache[33054] ?: 0,
                currentAbsEncTrolleyPositionWord2 = cache[33055] ?: 0,

                //Данные с абсолютного энкодера. Ход крана
                currentAbsEncCranePositionWord1 = cache[33056] ?: 0,
                currentAbsEncCranePositionWord2 = cache[33057] ?: 0,

                //-----ошибка связи энкодеров
                currentHoistAbsEncErrCanOpen = cache[39014] ?: 0,
                currentSlewAbsEncErrCanOpen = cache[39015] ?: 0,
                currentTrolleyAbsEncErrCanOpen = cache[39016] ?: 0,
                currentCraneAbsEncErrCanOpen = cache[39013] ?: 0,
                canOpenSensorsError = cache[39028] ?: 0,
                //-----Статус приводов
                hoistStatusWord = cache[31100] ?: 0,
                slewStatusWord = cache[31200] ?: 0,
                trolleyStatusWord = cache[31300] ?: 0,
                craneStatusWord = cache[31400] ?: 0,
                craneRepPoint = cache[33165] ?: 0,
            ),
            // Drive Parameters
            drivesParameters = DrivesParametersState(
                //ДИАГНОСТИКА.  ПРИВОД ПОДЪЕМА
                hoistDrivePowerError = cache[31113] ?: 0,
                hoistControlWord = cache[31101] ?: 0,
                hoistDriveError = cache[39020] ?: 0,
                hoistFault = cache[31108] ?: 0,
                hoistDriveErrorCode = cache[39041] ?: 0,
                hoistTargetVelocity = safeDivideHundred(cache[31102] ?: 0),
                hoistControlEffort = safeDivideHundred(cache[31103] ?: 0),
                hoistMotorCurrent = safeDivideTens(cache[31104] ?: 0),
                hoistEncoder = cache[31110] ?: 0,
                hoistIL1R = cache[31106] ?: 0,
                hoistOL1R = cache[31107] ?: 0,
                //ДИАГНОСТИКА.  ПРИВОД ПОВОРОТА
                slewDrivePowerError = cache[31213] ?: 0,
                slewControlWord = cache[31201] ?: 0,
                slewDriveError = cache[39021] ?: 0,
                slewFault = cache[31208] ?: 0,
                slewDriveErrorCode = cache[39042] ?: 0,
                slewTargetVelocity = safeDivideHundred(cache[31202] ?: 0),
                slewControlEffort = safeDivideHundred(cache[31203] ?: 0),
                slewMotorCurrent = safeDivideTens(cache[31204] ?: 0),
                slewEncoder = cache[31210] ?: 0,
                slewIL1R = cache[31206] ?: 0,
                slewOL1R = cache[31207] ?: 0,
                //ДИАГНОСТИКА.  ПРИВОД ТЕЛЕЖКИ
                trolleyDrivePowerError = cache[31313] ?: 0,
                trolleyControlWord = cache[31301] ?: 0,
                trolleyDriveError = cache[39022] ?: 0,
                trolleyFault = cache[31308] ?: 0,
                trolleyDriveErrorCode = cache[39043] ?: 0,
                trolleyTargetVelocity = safeDivideHundred(cache[31302] ?: 0),
                trolleyControlEffort = safeDivideHundred(cache[31303] ?: 0),
                trolleyMotorCurrent = safeDivideTens(cache[31304] ?: 0),
                trolleyEncoder = cache[31310] ?: 0,
                trolleyIL1R = cache[31306] ?: 0,
                trolleyOL1R = cache[31307] ?: 0,
                //ДИАГНОСТИКА.  ПРИВОД ХОДА КРАНА
                craneDrivePowerError = cache[31413] ?: 0,
                craneControlWord = cache[31401] ?: 0,
                craneDriveError = cache[39023] ?: 0,
                craneFault = cache[31408] ?: 0,
                craneDriveErrorCode = cache[39044] ?: 0,
                craneTargetVelocity = safeDivideHundred(cache[31402] ?: 0),
                craneControlEffort = safeDivideHundred(cache[31403] ?: 0),
                craneMotorCurrent = safeDivideTens(cache[31404] ?: 0),
                craneIL1R = cache[31406] ?: 0,
                craneOL1R = cache[31407] ?: 0,

                canOpenDrivesError = cache[39029] ?: 0,
            ),
            plcParameters = PLCParametersState(
                // Дискретные входы ПЛК
                plcDI000PhaseControlRelay = cache[30100] ?: 0,
                plcDI001LineContactor = cache[30101] ?: 0,
                plcDI002MontageMode = cache[30102] ?: 0,
                plcDI003OGPLimSw = cache[30103] ?: 0,
                plcDI004HoistBrakeStatus = cache[30104] ?: 0,
                plcDI005HoistBrakeSensor = cache[30105] ?: 0,
                plcDI006HoistLowSpdButton = cache[30106] ?: 0,
                plcDI007SlewBrake = cache[30107] ?: 0,
                // Дискретные выходы ПЛК
                plcDQ000LineContactorEn = cache[30170] ?: 0,
                plcDQ001HoistPWREn = cache[30171] ?: 0,
                plcDQ002SlewPWREn = cache[30172] ?: 0,
                plcDQ003TrolleyPWREn = cache[30173] ?: 0,
                plcDQ004CranePWREn = cache[30174] ?: 0,
                plcDQ005HoistVentilation = cache[30175] ?: 0,
                plcDQ006GlobalReady = cache[30176] ?: 0,
                plcDQ007HoistOverloadStop = cache[30177] ?: 0,
                // Дискретные входы Модуля
                plcDI100MicroclimatInput = cache[30110] ?: 0,
                plcDI101BrakePowerControl = cache[30111] ?: 0,
                plcDI102VentilationPowerControl = cache[30112] ?: 0,
                plcDI107ACSSPowerControl = cache[30117] ?: 0,

                plcDI110ACSSFault = cache[30120] ?: 0,
                plcDI111ACSSReady = cache[30121] ?: 0,
                plcDI112Zapasovka = cache[30122] ?: 0,
                plcDI113Luk = cache[30123] ?: 0,
                plcDI114HoistZeroPos = cache[30124] ?: 0,
                plcDI115SlewZeroPos = cache[30125] ?: 0,
                plcDI116TrolleyZeroPos = cache[30126] ?: 0,
                plcDI117CraneZeroPos = cache[30127] ?: 0,
                // Дискретные выходы Модуля
                plcDQ100Sound = cache[30180] ?: 0,
                plcDQ117ACSSReset = cache[30181] ?: 0,
                // Аналоговые входы Модуля
                plcAI0AnemometerSensor = cache[30151] ?: 0,
                plcAI1LoadCellSensor = cache[30150] ?: 0,
            )
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


    data class DiagnosticState(
        val systemParams: ControlSystemParametersState = ControlSystemParametersState(),
        val drivesParameters: DrivesParametersState = DrivesParametersState(),
        val plcParameters: PLCParametersState = PLCParametersState(),
    )

    data class ControlSystemParametersState(
        //Датчики ветра и усилия
        val ogpLimSw: Int= 0,
        val currentWeightAI: Int= 0,
        val weightSensorErr: Int= 0,
        val currentWindAI: Int= 0,
        val windSensorErr: Int= 0,
        //-----Данные с энкодеров. Подъем
        val currentAbsEncHoistPositionWord1: Int = 0,
        val currentAbsEncHoistPositionWord2: Int = 0,
        //-----Данные с энкодеров. Поворот
        val currentAbsEncSlewPositionWord1: Int = 0,
        val currentAbsEncSlewPositionWord2: Int = 0,

        //-----Данные с энкодеров. Тележка
        val currentAbsEncTrolleyPositionWord1: Int = 0,
        val currentAbsEncTrolleyPositionWord2: Int = 0,

        //-----Данные с энкодеров. Ход крана
        val currentAbsEncCranePositionWord1: Int = 0,
        val currentAbsEncCranePositionWord2: Int = 0,


        val currentHoistAbsEncErrCanOpen: Int = 0,
        val currentSlewAbsEncErrCanOpen: Int = 0,
        val currentTrolleyAbsEncErrCanOpen: Int = 0,
        val currentCraneAbsEncErrCanOpen: Int = 0,

        val canOpenSensorsError: Int = 0,

        val hoistStatusWord:Int = 0,
        val slewStatusWord:Int = 0,
        val trolleyStatusWord:Int = 0,
        val craneStatusWord:Int = 0,
        val craneRepPoint: Int = 0,
    )
    data class DrivesParametersState(
        //ДИАГНОСТИКА.  ПРИВОД ПОДЪЕМА
        val hoistDrivePowerError:Int=0,
        val hoistControlWord: Int =0,
        val hoistDriveError: Int =0,//Статус. Ошибка ПЧ
        val hoistFault: Int=0, //Код ошибки ПЧ
        val hoistDriveErrorCode: Int = 0,//Код ошибки ПЧ
        val hoistTargetVelocity: Float = 0f,
        val hoistControlEffort: Float = 0f,
        val hoistMotorCurrent: Float = 0f,
        val hoistEncoder: Int = 0,
        val hoistIL1R: Int = 0,
        val hoistOL1R: Int = 0,
        //ДИАГНОСТИКА.  ПРИВОД ПОВОРОТА
        val slewDrivePowerError:Int = 0,
        val slewControlWord: Int =0,
        val slewDriveError: Int =0,//Статус. Ошибка ПЧ
        val slewFault: Int=0, //Код ошибки ПЧ
        val slewDriveErrorCode: Int = 0,//Код ошибки ПЧ
        val slewTargetVelocity: Float = 0f,
        val slewControlEffort: Float = 0f,
        val slewMotorCurrent: Float = 0f,
        val slewEncoder: Int = 0,
        val slewIL1R: Int = 0,
        val slewOL1R: Int = 0,
        //ДИАГНОСТИКА.  ПРИВОД ТЕЛЕЖКИ
        val trolleyDrivePowerError:Int = 0,
        val trolleyControlWord: Int =0,
        val trolleyDriveError: Int =0,//Статус. Ошибка ПЧ
        val trolleyFault: Int=0, //Код ошибки ПЧ
        val trolleyDriveErrorCode: Int = 0,//Код ошибки ПЧ
        val trolleyTargetVelocity: Float = 0f,
        val trolleyControlEffort: Float = 0f,
        val trolleyMotorCurrent: Float = 0f,
        val trolleyEncoder: Int = 0,
        val trolleyIL1R: Int = 0,
        val trolleyOL1R: Int = 0,
        //ДИАГНОСТИКА.  ПРИВОД ХОДА КРАНА
        val craneDrivePowerError:Int = 0,
        val craneControlWord: Int =0,
        val craneDriveError: Int =0,//Статус. Ошибка ПЧ
        val craneFault: Int=0, //Код ошибки ПЧ
        val craneDriveErrorCode: Int = 0,//Код ошибки ПЧ
        val craneTargetVelocity: Float = 0f,
        val craneControlEffort: Float = 0f,
        val craneMotorCurrent: Float = 0f,
        val craneIL1R: Int = 0,
        val craneOL1R: Int = 0,

        val canOpenDrivesError: Int = 0,
    )
    data class PLCParametersState(
        // Дискретные входы ПЛК
        val plcDI000PhaseControlRelay: Int= 0,
        val plcDI001LineContactor: Int= 0,
        val plcDI002MontageMode: Int= 0,
        val plcDI003OGPLimSw: Int= 0,
        val plcDI004HoistBrakeStatus: Int= 0,
        val plcDI005HoistBrakeSensor: Int= 0,
        val plcDI006HoistLowSpdButton: Int= 0,
        val plcDI007SlewBrake: Int= 0,
        // Дискретные выходы ПЛК
        val plcDQ000LineContactorEn: Int= 0,
        val plcDQ001HoistPWREn: Int= 0,
        val plcDQ002SlewPWREn: Int= 0,
        val plcDQ003TrolleyPWREn: Int= 0,
        val plcDQ004CranePWREn: Int= 0,
        val plcDQ005HoistVentilation: Int= 0,
        val plcDQ006GlobalReady: Int= 0,
        val plcDQ007HoistOverloadStop: Int= 0,
        // Дискретные входы Модуля
        val plcDI100MicroclimatInput: Int= 0,
        val plcDI101BrakePowerControl: Int= 0,
        val plcDI102VentilationPowerControl: Int= 0,
        val plcDI107ACSSPowerControl: Int= 0,

        val plcDI110ACSSFault: Int= 0,
        val plcDI111ACSSReady: Int= 0,
        val plcDI112Zapasovka: Int= 0,
        val plcDI113Luk: Int= 0,
        val plcDI114HoistZeroPos: Int= 0,
        val plcDI115SlewZeroPos: Int= 0,
        val plcDI116TrolleyZeroPos: Int= 0,
        val plcDI117CraneZeroPos: Int= 0,

        // Дискретные выходы Модуля
        val plcDQ100Sound: Int= 0,
        val plcDQ117ACSSReset: Int= 0,

        // Аналоговые входы Модуля
        val plcAI0AnemometerSensor:Int= 0,
        val plcAI1LoadCellSensor:Int= 0,
    )

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

    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("DiagnosticScreenVM", "ViewModel cleared")
        // Деактивируем конфигурацию при уничтожении ViewModel
        modbusRepository.setActiveUiConfiguration(null)
    }
}

