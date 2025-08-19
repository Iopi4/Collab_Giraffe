package com.example.giraffenew.home.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val modbusRepository: ModbusRepository
) : ViewModel() {

// Crane Operating Parameters  UI
//    private val _weightQnom = mutableStateOf(0f)
//    val weightMax: State<Float> = _weightQnom
//
//    private val _weightCurrent = mutableStateOf(0f)
//    val weightCurrent: State<Float> = _weightCurrent
//
//    private val _trolleyCurrent = mutableStateOf(0f)
//    val trolleyCurrent: State<Float> = _trolleyCurrent
//
//    private val _slewingCurrent = mutableStateOf(0f)
//    val slewingCurrent: State<Float> = _slewingCurrent
//
//    private val _heightCurrent = mutableStateOf(0f)
//    val heightCurrent: State<Float> = _heightCurrent
//
//    private val _moveCurrent = mutableStateOf(0f)
//    val moveCurrent: State<Float> = _moveCurrent
//
//    private val _windCurrent = mutableStateOf(0f)
//    val windCurrent: State<Float> = _windCurrent
//
//    private val _voltageL1L2Current = mutableStateOf(0f)
//    val voltageL1L2Current: State<Float> = _voltageL1L2Current
//
//    private val _voltageL2L3Current = mutableStateOf(0f)
//    val voltageL2L3Current: State<Float> = _voltageL2L3Current
//
//    private val _voltageL1L3Current = mutableStateOf(0f)
//    val voltageL1L3Current: State<Float> = _voltageL1L3Current
//
//// Crane Parameter Scale  UI
//    private val _weightPercent = mutableStateOf(0f)
//    val weightPercent: State<Float> = _weightPercent
//
//    private val _hoistPosition = mutableStateOf(0f)
//    val hoistPosition: State<Float> = _hoistPosition
//
//    private val _hoistMaxPosition = mutableStateOf(0f)
//    val hoistMaxPosition: State<Float> = _hoistMaxPosition
//
//    private val _cranePosition = mutableStateOf(0f)
//    val cranePosition: State<Float> = _cranePosition
//
//    private val _craneMaxPosition = mutableStateOf(0f)
//    val craneMaxPosition: State<Float> = _craneMaxPosition
//
////CraneMechanismsCondition UI
//    private val _weightSensorErr = mutableStateOf(0)
//    val weightSensorErr: State<Int> = _weightSensorErr
//
//    private val _anemometerSensorErr = mutableStateOf(0)
//    val anemometerSensorErr: State<Int> = _anemometerSensorErr
//
//    private val _alarmLampStatus = mutableStateOf(0)
//    val alarmLampStatus: State<Int> = _alarmLampStatus
//
//    private val _trolleyFwdStatus = mutableStateOf(0)
//    val trolleyFwdStatus: State<Int> = _trolleyFwdStatus
//
//    private val _trolleyRevStatus = mutableStateOf(0)
//    val trolleyRevStatus: State<Int> = _trolleyRevStatus
//
//    private val _hoistUpStatus = mutableStateOf(0)
//    val hoistUpStatus: State<Int> = _hoistUpStatus
//
//    private val _hoistDownStatus = mutableStateOf(0)
//    val hoistDownStatus: State<Int> = _hoistDownStatus
//
//    private val _slewRightStatus = mutableStateOf(0)
//    val slewRightStatus: State<Int> = _slewRightStatus
//
//    private val _slewLeftStatus = mutableStateOf(0)
//    val slewLeftStatus: State<Int> = _slewLeftStatus
//
//    private val _cranRightStatus = mutableStateOf(0)
//    val cranRightStatus: State<Int> = _cranRightStatus
//
//    private val _craneLeftStatus = mutableStateOf(0)
//    val craneLeftStatus: State<Int> = _craneLeftStatus
//
//    private val _craneRight = mutableStateOf(0)
//    val craneRight: State<Int> = _craneRight
//
//    private val _craneLeft = mutableStateOf(0)
//    val craneLeft: State<Int> = _craneLeft
//
//    private val _slewLeft = mutableStateOf(0)
//    val slewLeft: State<Int> = _slewLeft
//
//    private val _slewRight = mutableStateOf(0)
//    val slewRight: State<Int> = _slewRight
//
//    private val _trolleyFwd = mutableStateOf(0)
//    val trolleyFwd: State<Int> = _trolleyFwd
//
//    private val _trolleyRev = mutableStateOf(0)
//    val trolleyRev: State<Int> = _trolleyRev
//
//    private val _hoistUp = mutableStateOf(0)
//    val hoistUp: State<Int> = _hoistUp
//
//    private val _hoistDown = mutableStateOf(0)
//    val hoistDown: State<Int> = _hoistDown

    val addressesCraneOperatingParameters = listOf(152,153,155,158,159,162,163,135,136,137,317 ) // Только нужные регистры
    val addressesCraneParameterScale = listOf(317,159,318,162,319,) // Только нужные регистры
    val addressesCraneMechanismsCondition = listOf(711,320,321,322,323,324,325,326,327,328,329,
        330,331,332,333,334,335,336,337) // Только нужные регистры

    private val _state = mutableStateOf(HomeScreenState())
    val state: State<HomeScreenState> = _state

    private var dataCollectionJob: Job? = null

    init {
        startDataCollection()
    }

    // Запуск сбора данных
    fun startDataCollection() {
        if (dataCollectionJob?.isActive == true) return

        Log.d("HomeScreenVM", "Starting data collection")
        dataCollectionJob = viewModelScope.launch(Dispatchers.Default) {
            modbusRepository.registerCache
                .collect { cache ->
                    try {
                        Log.d("HomeScreenViewModel", "Updating from cache, size: ${cache.size}")
                        val newState = HomeScreenState(
                            // Crane Operating Parameters UI
                            weightQnom = safeDivideThousands(cache[152]?.value?:0),
                            weightCurrent = safeDivideTens(cache[153]?.value?:0),
                            trolleyCurrent = safeDivideTens(cache[155]?.value?:0),
                            slewingCurrent = safeDivideTens(cache[158]?.value?:0),
                            heightCurrent = safeDivideTens(cache[159]?.value?:0),
                            moveCurrent = safeDivideTens(cache[162]?.value?:0),
                            windCurrent = safeDivideTens(cache[163]?.value?:0),
                            voltageL1L2Current = safeDivideTens(cache[135]?.value?:0),
                            voltageL2L3Current = safeDivideTens(cache[136]?.value?:0),
                            voltageL1L3Current = safeDivideTens(cache[137]?.value?:0),

                            // Crane Parameter Scale UI
                            weightPercent = safeDivide(cache[317]?.value?:0),
                            hoistPosition = safeDivideTens(cache[159]?.value?:0),
                            hoistMaxPosition = safeDivideTens(cache[318]?.value?:0),
                            cranePosition = safeDivideTens(cache[162]?.value?:0),
                            craneMaxPosition = safeDivideTens(cache[319]?.value?:0),

                            //Crane Mechanisms Condition UI
                            weightSensorErr     = cache[711]?.value?: return@collect,
                            trolleyFwdStatus    = cache[320]?.value?: return@collect,
                            trolleyRevStatus     = cache[321]?.value?: return@collect,
                            hoistUpStatus        = cache[322]?.value?: return@collect,
                            hoistDownStatus      = cache[323]?.value?: return@collect,
                            slewRightStatus      = cache[324]?.value?: return@collect,
                            slewLeftStatus       = cache[325]?.value?: return@collect,
                            cranRightStatus      = cache[326]?.value?: return@collect,
                            craneLeftStatus      = cache[327]?.value?: return@collect,
                            craneRight          = cache[328]?.value?: return@collect,
                            craneLeft   = cache[329]?.value?: return@collect,
                            slewLeft     = cache[330]?.value?: return@collect,
                            slewRight    = cache[331]?.value?: return@collect,
                            trolleyFwd   = cache[332]?.value?: return@collect,
                            trolleyRev   = cache[333]?.value?: return@collect,
                            hoistUp      = cache[334]?.value?: return@collect,
                            hoistDown    = cache[335]?.value?: return@collect,
                            anemometerSensorErr  = cache[336]?.value?: return@collect,
                            alarmLampStatus     = cache[337]?.value?: return@collect,)
                        _state.value = newState
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

    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("HomeScreenVM", "ViewModel cleared")
        dataCollectionJob?.cancel()
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
}