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

    val addressesSystemParameters = listOf(188, 198, 711, 381, 336, 382, 383, 386, 387, 390, 391, 394, 395,
        204, 205, 206, 207, 728, 100, 109, 118, 127) // Только нужные регистры Вкладки Система управления

    val addressesDrivesParameters = listOf(724, 101, 720, 108, 738, 102, 103, 104, 177, 106, 107,
        725, 110, 721, 117, 739, 111, 112, 113, 178, 115, 116,
        726, 119, 722, 1126, 740, 120, 121, 122, 179, 124, 125,
        727, 128, 723, 134, 741, 129, 130, 131, 132, 133, 729, 353) // Только нужные регистры Вкладки Система управления


    private val allAddresses = (addressesSystemParameters+addressesDrivesParameters).distinct()

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
        Log.d("HomeScreenVM", "Pausing data collection")
        dataCollectionJob?.cancel()
        dataCollectionJob = null
    }

    //Функция createDiagnosticScreenState() для обработки отфильтрованных данных:
    private fun createDiagnosticScreenState(cache: Map<Int, Int?>): DiagnosticState {
        return DiagnosticState(
            ControlSystemParametersState(
                //Датчики ветра и усилия
                ogpLimSw        = cache[188] ?: 0,
                currentWeightAI = cache[198] ?: 0,
                weightSensorErr = cache[711] ?: 0,
                currentWindAI   = cache[381] ?: 0,
                windSensorErr   = cache[336] ?: 0,
                //-----Данные с энкодеров
                currentAbsEncHoistPositionWord1 = cache[382] ?: 0,
                currentAbsEncHoistPositionWord2 = cache[383] ?: 0,
                currentAbsEncSlewPositionWord1 = cache[386] ?: 0,
                currentAbsEncSlewPositionWord2 = cache[387] ?: 0,
                currentAbsEncTrolleyPositionWord1 = cache[390] ?: 0,
                currentAbsEncTrolleyPositionWord2 = cache[391] ?: 0,
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
                hoistTargetVelocity = safeDivideTens(cache[102] ?: 0),
                hoistControlEffort = safeDivideTens(cache[103] ?: 0),
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
                slewTargetVelocity = safeDivideTens(cache[111] ?: 0),
                slewControlEffort = safeDivideTens(cache[112] ?: 0),
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
                trolleyTargetVelocity = safeDivideTens(cache[120] ?: 0),
                trolleyControlEffort = safeDivideTens(cache[121] ?: 0),
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
                craneTargetVelocity = safeDivideTens(cache[129] ?: 0),
                craneControlEffort = safeDivideTens(cache[130] ?: 0),
                craneMotorCurrent = safeDivideTens(cache[131] ?: 0),
                craneIL1R = cache[132] ?: 0,
                craneOL1R = cache[133] ?: 0,

                canOpenDrivesError = cache[729] ?: 0,
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
    private fun safeDivideThousands(value: Int?): Float {
        return (value ?: 0) / 1000f
    }


    data class DiagnosticState(
        val systemParams: ControlSystemParametersState = ControlSystemParametersState(),
        val drivesParameters: DrivesParametersState = DrivesParametersState(),
    )


    data class ControlSystemParametersState(
        //Датчики ветра и усилия
        val ogpLimSw: Int= 0,
        val currentWeightAI: Int= 0,
        val weightSensorErr: Int= 0,
        val currentWindAI: Int= 0,
        val windSensorErr: Int= 0,
        //-----Данные с энкодеров
        val currentAbsEncHoistPositionWord1: Int = 0,
        val currentAbsEncHoistPositionWord2: Int = 0,
        val currentAbsEncSlewPositionWord1: Int = 0,
        val currentAbsEncSlewPositionWord2: Int = 0,
        val currentAbsEncTrolleyPositionWord1: Int = 0,
        val currentAbsEncTrolleyPositionWord2: Int = 0,
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


    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("DiagnosticScreenVM", "ViewModel cleared")
        // Деактивируем конфигурацию при уничтожении ViewModel
        modbusRepository.setActiveUiConfiguration(null)
    }
}

