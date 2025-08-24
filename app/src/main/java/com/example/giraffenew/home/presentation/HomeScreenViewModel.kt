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


/**
В ViewModel сделать фильтрацию: преобразовать поток так, чтобы он эмитил только когда изменился один из нужных регистров.
Мы пойдем по пути (б), но с одним улучшением: будем запоминать предыдущий отфильтрованный набор значений и сравнивать с новым.
Если отфильтрованный набор не изменился, то не будем обновлять состояние.
 */

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val modbusRepository: ModbusRepository
) : ViewModel() {

    val addressesCraneOperatingParameters = listOf(152,153,155,158,159,162,163,135,136,137,317) // Только нужные регистры
    val addressesCraneParameterScale = listOf(317,159,318,162,319) // Только нужные регистры
    val addressesCraneMechanismsCondition = listOf(711,320,321,322,323,324,325,326,327,328,329,
        330,331,332,333,334,335,336,337) // Только нужные регистры
    //1. Объединим все адреса в один список
    private val allAddresses = (addressesCraneOperatingParameters + addressesCraneParameterScale + addressesCraneMechanismsCondition).distinct()

    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    private var dataCollectionJob: Job? = null

    init {
        // Активируем UI конфигурацию для этого экрана
        modbusRepository.setActiveUiConfiguration(UiHomeScreenRegisterConfiguration.NAME_HOME)
        startDataCollection()
    }

    /**
    2. В коллекторе потока registerCache, будем преобразовывать весь кэш в отфильтрованную карту (только наши адреса).
        Далее, мы можем сохранять предыдущую отфильтрованную карту и сравнивать с новой. Если они одинаковые, то ничего не делаем.
        Если изменилась, то вычисляем новое состояние и обновляем _state.
     */


    // Запуск сбора данных
    fun startDataCollection() {
        if (dataCollectionJob?.isActive == true) return

        Log.d("HomeScreenVM", "Starting data collection for ${UiHomeScreenRegisterConfiguration.NAME_HOME}")
        dataCollectionJob = viewModelScope.launch(Dispatchers.Default) {
            modbusRepository.getConfigurationFlow(UiHomeScreenRegisterConfiguration.NAME_HOME)
                .collect { cache ->
                    Log.d("HomeScreenViewModel", "Updating from cache, size: ${cache.size}")
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

    //Функция createHomeScreenState() для обработки отфильтрованных данных:
    private fun createHomeScreenState(cache: Map<Int, Int?>): HomeScreenState {
    return HomeScreenState(
        // Crane Operating Parameters
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

        // Crane Parameter Scale
        weightPercent = safeDivide(cache[317] ?: 0),
        hoistPosition = safeDivideTens(cache[159] ?: 0),
        hoistMaxPosition = safeDivideTens(cache[318] ?: 0),
        cranePosition = safeDivideTens(cache[162] ?: 0),
        craneMaxPosition = safeDivideTens(cache[319] ?: 0),

        // Mechanisms Condition
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
        anemometerSensorErr = cache[336] ?: 0,
        alarmLampStatus = cache[337] ?: 0
    )
    }

    // Приостановка сбора данных
    fun pauseDataCollection() {
        Log.d("HomeScreenVM", "Pausing data collection")
        dataCollectionJob?.cancel()
        dataCollectionJob = null
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
// Crane Operating Parameters UI
        val weightQnom: Float = 0f,
        val weightCurrent: Float = 0f,
        val trolleyCurrent: Float = 0f,
        val slewingCurrent:Float = 0f,
        val heightCurrent: Float = 0f,
        val moveCurrent: Float = 0f,
        val windCurrent: Float = 0f,
        val voltageL1L2Current: Float = 0f,
        val voltageL2L3Current: Float = 0f,
        val voltageL1L3Current: Float = 0f,
// Crane Parameter Scale  UI
        val weightPercent: Float = 0f,
        val hoistPosition: Float = 0f,
        val hoistMaxPosition: Float = 0f,
        val cranePosition: Float = 0f,
        val craneMaxPosition: Float = 0f,
//CraneMechanismsCondition UI
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

    override fun onCleared() {
        super.onCleared()
        Log.d("HomeScreenVM", "ViewModel cleared")
        // Деактивируем конфигурацию при уничтожении ViewModel
        modbusRepository.setActiveUiConfiguration(null)
        dataCollectionJob?.cancel()
    }
}