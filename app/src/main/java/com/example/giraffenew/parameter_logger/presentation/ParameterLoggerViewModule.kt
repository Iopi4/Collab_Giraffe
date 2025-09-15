package com.example.giraffenew.parameter_logger.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.entities.UIParameterLoggerConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParameterLoggerViewModule @Inject constructor(
    private val modbusRepository: ModbusRepository
) : ViewModel() {

    private val _currentDateSettingMode = mutableStateOf("00.00.0000")
    val currentDateSettingMode: State<String> = _currentDateSettingMode

    private val _currentTimeSettingMode = mutableStateOf("00:00")
    val currentTimeSettingMode: State<String> = _currentTimeSettingMode

    private val _currentDateSBKBInstallation = mutableStateOf("00.00.0000")
    val currentDateSBKBInstallation: State<String> = _currentDateSBKBInstallation


    val addressParameterCrane = listOf( 152, 197, 171, 172, 173, 174, 175, 0, 1,
        165, 166, 167, 168, 169, 170, 367, 368, 369, 370, 371, 360, 361, 362, 363, 364, 365, 366)

    val addressCyclesOfWork = listOf(20, 21, 24, 25, 28, 29, 32, 33, 36, 37, 40, 41, 44, 45, 48, 49,
        52, 53, 56, 57, 60, 61, 80, 81, 84, 85, 88, 89, 92, 93, 96, 97)

    private val allAddresses = (addressParameterCrane+addressCyclesOfWork).distinct()

    private val _state = mutableStateOf(ParameterLoggerState())
    val state: State<ParameterLoggerState> = _state

    private var dataCollectionJob: Job? = null


    /**
    2. В коллекторе потока registerCache, будем преобразовывать весь кэш в отфильтрованную карту (только наши адреса).
    Далее, мы можем сохранять предыдущую отфильтрованную карту и сравнивать с новой. Если они одинаковые, то ничего не делаем.
    Если изменилась, то вычисляем новое состояние и обновляем _state.
     */

    fun setActiveConfiguration() {
        modbusRepository.setActiveUiConfiguration(UIParameterLoggerConfiguration.NAME_PARAMETER_LOGGER)
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

        Log.d(
            "ParameterLoggerScreenVM",
            "Starting data collection for ${UIParameterLoggerConfiguration.NAME_PARAMETER_LOGGER}"
        )
        dataCollectionJob = viewModelScope.launch(Dispatchers.IO) {
            modbusRepository.getConfigurationFlow(UIParameterLoggerConfiguration.NAME_PARAMETER_LOGGER)
                .collect { cache ->
                    Log.d("ParameterLoggerViewModel", "Updating from cache, size: ${cache.size}")
                    // Обработка данных
                    try {
                        // Создаем отфильтрованную карту только для нужных адресов
                        val filteredCache = allAddresses.associate { addr ->
                            addr to cache[addr]?.value
                        }
                        _state.value = createParameterLoggerScreenState(filteredCache)
                    } catch (e: Exception) {
                        Log.e("ParameterLoggerScreenVM", "Data collection error", e)
                    }
                }
        }
    }


    // Приостановка сбора данных
    fun pauseDataCollection() {
        Log.d("ParameterLoggerVM", "Pausing data collection")
        dataCollectionJob?.cancel()
        dataCollectionJob = null
    }

    private fun createParameterLoggerScreenState(cache: Map<Int, Int?>): ParameterLoggerState{
        return ParameterLoggerState(
            сraneParameters = CraneParametersState(
                qNom        = cache[152] ?: 0,
                craneLenght = safeDivideTens(cache[197] ?: 0),
                rAP         = cache[171] ?: 0,
                rAFIso      = cache[172] ?: 0,
                rQF         = cache[173] ?: 0,
                kP          = cache[174] ?: 0,
                classOfUseC = cache[175] ?: 0,
                rN1         = cache[0] ?: 0,
                rN2         = cache[1] ?: 0,
                settingModeYear     = cache[165] ?: 0,
                settingModeMonth    = cache[166] ?: 0,
                settingModeDay      = cache[167] ?: 0,
                settingModeHour     = cache[168] ?: 0,
                settingModeMinute   = cache[169] ?: 0,
                settingModeSeconds  = cache[170] ?: 0,
                hmiSBKBNo           = cache[367] ?: 0,
                hmiCraneNo          = cache[368] ?: 0,

                hmiSBKBInstallationDay= cache[369] ?: 0,
                hmiSBKBInstallationMonth= cache[370] ?: 0,
                hmiSBKBInstallationYear= cache[371] ?: 0,

                hmiHoistOperatingHours  = safeDivideTens(cache[360] ?: 0),
                hmiSlewOperatingHours   = safeDivideTens(cache[361] ?: 0),
                hmiTrolleyOperatingHours    = safeDivideTens(cache[362] ?: 0),
                hmiCraneOperatingHours      = safeDivideTens(cache[363] ?: 0),
                hmiTotalCraneOperatingHours = safeDivideTens(cache[364] ?: 0),
                hmiSBKBOperatingHours       = safeDivideTens(cache[365] ?: 0),
                hmiTotalCraneOperatingHoursMaintenance = safeDivideTens(cache[366] ?: 0),
            ),

            cyclesOfWorkParameters = CyclesOfWorkParametersState(
                arrRestA1MW1 = cache[20] ?: 0,
                arrRestA1MW2 = cache[21] ?: 0,
                arrRestA2MW1 = cache[24] ?: 0,
                arrRestA2MW2 = cache[25] ?: 0,
                arrRestA3MW1 = cache[28] ?: 0,
                arrRestA3MW2 = cache[29] ?: 0,
                arrRestA4MW1 = cache[32] ?: 0,
                arrRestA4MW2 = cache[33] ?: 0,
                arrRestA5MW1 = cache[36] ?: 0,
                arrRestA5MW2 = cache[37] ?: 0,
                arrRestA6MW1 = cache[40] ?: 0,
                arrRestA6MW2 = cache[41] ?: 0,
                arrRestA7MW1 = cache[44] ?: 0,
                arrRestA7MW2 = cache[45] ?: 0,
                arrRestA8MW1 = cache[48] ?: 0,
                arrRestA8MW2 = cache[49] ?: 0,
                arrRestA9MW1 = cache[52] ?: 0,
                arrRestA9MW2 = cache[53] ?: 0,
                arrRestA10MW1 = cache[56] ?: 0,
                arrRestA10MW2 = cache[57] ?: 0,
                arrRestA11MW1 = cache[60] ?: 0,
                arrRestA11MW2 = cache[61] ?: 0,
                rCycleHoistMW1 = cache[80] ?: 0,
                rCycleHoistMW2 = cache[81] ?: 0,
                rCycleSlewMW1 = cache[84] ?: 0,
                rCycleSlewMW2 = cache[85] ?: 0,
                rCycleTrolleyMW1 = cache[88] ?: 0,
                rCycleTrolleyMW2 = cache[89] ?: 0,
                rCycleCraneMW1 = cache[92] ?: 0,
                rCycleCraneMW2 = cache[93] ?: 0,
                rCycleHoleCraneMW1 = cache[96] ?: 0,
                rCycleHoleCraneMW2 = cache[97] ?: 0,
            ),
        )
    }




    private fun safeDivide(value: Int?): Float {
        return (value ?: 0) / 1f
    }

    private fun safeDivideTens(value: Int?): Float {
        return (value ?: 0) / 10f
    }
    private fun safeDivideThousands(value: Int?): Float {
        return (value ?: 0) / 1000f
    }

    data class ParameterLoggerState(
        val сraneParameters: CraneParametersState = CraneParametersState(

        ),
        val cyclesOfWorkParameters: CyclesOfWorkParametersState = CyclesOfWorkParametersState(

        ),
    )


    data class CraneParametersState(
        val qNom: Int=0, //Номинальная грузоподъемность в кг
        val craneLenght: Float=0f, //Длина стрелы крана
        val rAP: Int=0, //Паспортная группа режима работы
        val rAFIso: Int=0, //Фактическая группа режима работы по ИСО
        val rQF: Int=0, //Фактический класс нагружения
        val kP: Int=0, //Коэффициент нагружения Кр
        val classOfUseC: Int=0, //Класс использования С
        val rN1: Int=0, //текущее характеристическое число. Слово 1
        val rN2: Int=0, //текущее характеристическое число. Слово 2
        //Дата и время включения последнего режима наладки
        val settingModeYear: Int=0,
        val settingModeMonth: Int=0,
        val settingModeDay: Int=0,
        val settingModeHour: Int=0,
        val settingModeMinute: Int=0,
        val settingModeSeconds: Int=0,

        val hmiSBKBNo: Int=0, //Заводской номер СБКБ
        val hmiCraneNo: Int=0, //Заводской номер крана

        val hmiSBKBInstallationDay: Int=0, //День установки системы безопасности
        val hmiSBKBInstallationMonth: Int=0, //Месяц установки системы безопасности
        val hmiSBKBInstallationYear: Int=0, //Год установки системы безопасности

        //Моточасы
        val hmiHoistOperatingHours: Float=0f, //Моточасы механизма подъема
        val hmiSlewOperatingHours: Float=0f, //Моточасы механизма поворота
        val hmiTrolleyOperatingHours: Float=0f,//Моточасы механизма тележки
        val hmiCraneOperatingHours: Float=0f, //Моточасы механизма передвижения крана
        val hmiTotalCraneOperatingHours: Float=0f,//Общие моточасы крана
        val hmiSBKBOperatingHours: Float=0f, //Моточасы системы безопасности
        val hmiTotalCraneOperatingHoursMaintenance: Float=0f, //Моточасы крана для технического обслуживания
    )
    data class CyclesOfWorkParametersState(
        val arrRestA1MW1: Int=0, //0-10% Количество циклов подъема в зависимости от веса груза
        val arrRestA1MW2: Int=0, //0-10% Количество циклов подъема в зависимости от веса груза
        val arrRestA2MW1: Int=0, //11-20% Количество циклов подъема в зависимости от веса груза
        val arrRestA2MW2: Int=0, //11-20% Количество циклов подъема в зависимости от веса груза
        val arrRestA3MW1: Int=0, //21-30% Количество циклов подъема в зависимости от веса груза
        val arrRestA3MW2: Int=0, //21-30% Количество циклов подъема в зависимости от веса груза
        val arrRestA4MW1: Int=0, //31-40% Количество циклов подъема в зависимости от веса груза
        val arrRestA4MW2: Int=0, //31-40% Количество циклов подъема в зависимости от веса груза
        val arrRestA5MW1: Int=0, //41-50% Количество циклов подъема в зависимости от веса груза
        val arrRestA5MW2: Int=0, //41-50% Количество циклов подъема в зависимости от веса груза
        val arrRestA6MW1: Int=0, //51-60% Количество циклов подъема в зависимости от веса груза
        val arrRestA6MW2: Int=0, //51-60% Количество циклов подъема в зависимости от веса груза
        val arrRestA7MW1: Int=0, //61-70% Количество циклов подъема в зависимости от веса груза
        val arrRestA7MW2: Int=0, //61-70% Количество циклов подъема в зависимости от веса груза
        val arrRestA8MW1: Int=0, //71-80% Количество циклов подъема в зависимости от веса груза
        val arrRestA8MW2: Int=0, //71-80% Количество циклов подъема в зависимости от веса груза
        val arrRestA9MW1: Int=0, //81-90% Количество циклов подъема в зависимости от веса груза
        val arrRestA9MW2: Int=0, //81-90% Количество циклов подъема в зависимости от веса груза
        val arrRestA10MW1: Int=0, //91-100% Количество циклов подъема в зависимости от веса груза
        val arrRestA10MW2: Int=0, //91-100% Количество циклов подъема в зависимости от веса груза
        val arrRestA11MW1: Int=0, //>101% Количество циклов подъема в зависимости от веса груза
        val arrRestA11MW2: Int=0, //>101% Количество циклов подъема в зависимости от веса груза
        val rCycleHoistMW1: Int=0, //Рабочие циклы подъема
        val rCycleHoistMW2: Int=0, //Рабочие циклы подъема
        val rCycleSlewMW1: Int=0, //Рабочие циклы поворота
        val rCycleSlewMW2: Int=0, //Рабочие циклы поворота
        val rCycleTrolleyMW1: Int=0, //Рабочие циклы тележки
        val rCycleTrolleyMW2: Int=0, //Рабочие циклы тележки
        val rCycleCraneMW1: Int=0, //Рабочие циклы хода крана
        val rCycleCraneMW2: Int=0, //Рабочие циклы хода крана
        val rCycleHoleCraneMW1: Int=0, //Рабочие циклы крана в целом
        val rCycleHoleCraneMW2: Int=0 //Рабочие циклы крана в целом
    )



    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("ParameterLoggerVM", "ViewModel cleared")
        // Деактивируем конфигурацию при уничтожении ViewModel
        modbusRepository.setActiveUiConfiguration(null)
    }







}