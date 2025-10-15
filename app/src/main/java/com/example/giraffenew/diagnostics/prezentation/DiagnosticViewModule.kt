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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DiagnosticViewModule @Inject constructor(
    private val modbusRepository: ModbusRepository
) : ViewModel() {

    val addressesSystemParameters = listOf(188, 198, 711, 381, 336,
        382, 383, 386, 387, 390, 391, 394, 395,
        204, 205, 206, 207, 728, 100, 109, 118, 127) // Только нужные регистры Вкладки Система управления

    val addressesDrivesParameters = listOf(724, 101, 720, 108, 738, 102, 103, 104, 177, 106, 107,
        725, 110, 721, 117, 739, 111, 112, 113, 178, 115, 116,
        726, 119, 722, 1126, 740, 120, 121, 122, 179, 124, 125,
        727, 128, 723, 134, 741, 129, 130, 131, 132, 133, 729, 353) // Только нужные регистры Вкладки Система управления

    val addressesPLCParameters = listOf(221, 225, 226, 227, 228, 229, 230, 231, 232, 233, 234, 239, 288,
        289, 290, 291, 292, 293, 294, 295, 380, 381, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
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
                ogpLimSw        = cache[188] ?: 0,
                currentWeightAI = cache[198] ?: 0,
                weightSensorErr = cache[711] ?: 0,
                currentWindAI   = cache[381] ?: 0,
                windSensorErr   = cache[336] ?: 0,
                //Данные с абсолютного энкодера. Подъем
                currentAbsEncHoistPositionWord1 = cache[382] ?: 0,
                currentAbsEncHoistPositionWord2 = cache[383] ?: 0,

                //Данные с абсолютного энкодера. Поворот
                currentAbsEncSlewPositionWord1 = cache[386] ?: 0,
                currentAbsEncSlewPositionWord2 = cache[387] ?: 0,

                //Данные с абсолютного энкодера. Тележка
                currentAbsEncTrolleyPositionWord1 = cache[390] ?: 0,
                currentAbsEncTrolleyPositionWord2 = cache[391] ?: 0,

                //Данные с абсолютного энкодера. Ход крана
                currentAbsEncCranePositionWord1 = cache[394] ?: 0,
                currentAbsEncCranePositionWord2 = cache[395] ?: 0,

                //-----ошибка связи энкодеров
                currentHoistAbsEncErrCanOpen = cache[204] ?: 0,
                currentSlewAbsEncErrCanOpen = cache[205] ?: 0,
                currentTrolleyAbsEncErrCanOpen = cache[206] ?: 0,
                currentCraneAbsEncErrCanOpen = cache[207] ?: 0,
                canOpenSensorsError = cache[728] ?: 0,
                //-----Статус приводов
                hoistStatusWord = cache[100] ?: 0,
                slewStatusWord = cache[109] ?: 0,
                trolleyStatusWord = cache[118] ?: 0,
                craneStatusWord = cache[127] ?: 0,
                craneRepPoint = cache[353] ?: 0,
            ),
            // Drive Parameters
            drivesParameters = DrivesParametersState(
                //ДИАГНОСТИКА.  ПРИВОД ПОДЪЕМА
                hoistDrivePowerError = cache[724] ?: 0,
                hoistControlWord = cache[101] ?: 0,
                hoistDriveError = cache[720] ?: 0,
                hoistFault = cache[108] ?: 0,
                hoistDriveErrorCode = cache[738] ?: 0,
                hoistTargetVelocity = safeDivideHundred(cache[102] ?: 0),
                hoistControlEffort = safeDivideHundred(cache[103] ?: 0),
                hoistMotorCurrent = safeDivideTens(cache[104] ?: 0),
                hoistEncoder = cache[177] ?: 0,
                hoistIL1R = cache[106] ?: 0,
                hoistOL1R = cache[107] ?: 0,
                //ДИАГНОСТИКА.  ПРИВОД ПОВОРОТА
                slewDrivePowerError = cache[725] ?: 0,
                slewControlWord = cache[110] ?: 0,
                slewDriveError = cache[721] ?: 0,
                slewFault = cache[117] ?: 0,
                slewDriveErrorCode = cache[739] ?: 0,
                slewTargetVelocity = safeDivideHundred(cache[111] ?: 0),
                slewControlEffort = safeDivideHundred(cache[112] ?: 0),
                slewMotorCurrent = safeDivideTens(cache[113] ?: 0),
                slewEncoder = cache[178] ?: 0,
                slewIL1R = cache[115] ?: 0,
                slewOL1R = cache[116] ?: 0,
                //ДИАГНОСТИКА.  ПРИВОД ТЕЛЕЖКИ
                trolleyDrivePowerError = cache[726] ?: 0,
                trolleyControlWord = cache[119] ?: 0,
                trolleyDriveError = cache[722] ?: 0,
                trolleyFault = cache[126] ?: 0,
                trolleyDriveErrorCode = cache[740] ?: 0,
                trolleyTargetVelocity = safeDivideHundred(cache[120] ?: 0),
                trolleyControlEffort = safeDivideHundred(cache[121] ?: 0),
                trolleyMotorCurrent = safeDivideTens(cache[122] ?: 0),
                trolleyEncoder = cache[179] ?: 0,
                trolleyIL1R = cache[124] ?: 0,
                trolleyOL1R = cache[125] ?: 0,
                //ДИАГНОСТИКА.  ПРИВОД ХОДА КРАНА
                craneDrivePowerError = cache[727] ?: 0,
                craneControlWord = cache[128] ?: 0,
                craneDriveError = cache[723] ?: 0,
                craneFault = cache[134] ?: 0,
                craneDriveErrorCode = cache[741] ?: 0,
                craneTargetVelocity = safeDivideHundred(cache[129] ?: 0),
                craneControlEffort = safeDivideHundred(cache[130] ?: 0),
                craneMotorCurrent = safeDivideTens(cache[131] ?: 0),
                craneIL1R = cache[132] ?: 0,
                craneOL1R = cache[133] ?: 0,

                canOpenDrivesError = cache[729] ?: 0,
            ),
            plcParameters = PLCParametersState(
                // Дискретные входы ПЛК
                plcDI000PhaseControlRelay = cache[221] ?: 0,
                plcDI001LineContactor = cache[225] ?: 0,
                plcDI002MontageMode = cache[226] ?: 0,
                plcDI003OGPLimSw = cache[227] ?: 0,
                plcDI004HoistBrakeStatus = cache[228] ?: 0,
                plcDI005HoistBrakeSensor = cache[229] ?: 0,
                plcDI006HoistLowSpdButton = cache[230] ?: 0,
                plcDI007SlewBrake = cache[231] ?: 0,
                // Дискретные выходы ПЛК
                plcDQ000LineContactorEn = cache[2] ?: 0,
                plcDQ001HoistPWREn = cache[3] ?: 0,
                plcDQ002SlewPWREn = cache[4] ?: 0,
                plcDQ003TrolleyPWREn = cache[5] ?: 0,
                plcDQ004CranePWREn = cache[6] ?: 0,
                plcDQ005HoistVentilation = cache[7] ?: 0,
                plcDQ006GlobalReady = cache[8] ?: 0,
                plcDQ007HoistOverloadStop = cache[9] ?: 0,
                // Дискретные входы Модуля
                plcDI100MicroclimatInput = cache[232] ?: 0,
                plcDI101BrakePowerControl = cache[233] ?: 0,
                plcDI102VentilationPowerControl = cache[234] ?: 0,
                plcDI107ACSSPowerControl = cache[239] ?: 0,

                plcDI110ACSSFault = cache[288] ?: 0,
                plcDI111ACSSReady = cache[289] ?: 0,
                plcDI112Zapasovka = cache[290] ?: 0,
                plcDI113Luk = cache[291] ?: 0,
                plcDI114HoistZeroPos = cache[292] ?: 0,
                plcDI115SlewZeroPos = cache[293] ?: 0,
                plcDI116TrolleyZeroPos = cache[294] ?: 0,
                plcDI117CraneZeroPos = cache[295] ?: 0,
                // Дискретные выходы Модуля
                plcDQ100Sound = cache[10] ?: 0,
                plcDQ117ACSSReset = cache[11] ?: 0,
                // Аналоговые входы Модуля
                plcAI0AnemometerSensor = cache[380] ?: 0,
                plcAI1LoadCellSensor = cache[381] ?: 0,
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


    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("DiagnosticScreenVM", "ViewModel cleared")
        // Деактивируем конфигурацию при уничтожении ViewModel
        modbusRepository.setActiveUiConfiguration(null)
    }
}

