package com.example.giraffenew.home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val modbusRepository: ModbusRepository
) : ViewModel() {

// Crane Operating Parameters  UI
    private val _weightQnom = mutableStateOf(0f)
    val weightMax: State<Float> = _weightQnom

    private val _weightCurrent = mutableStateOf(0f)
    val weightCurrent: State<Float> = _weightCurrent

    private val _trolleyCurrent = mutableStateOf(0f)
    val trolleyCurrent: State<Float> = _trolleyCurrent

    private val _slewingCurrent = mutableStateOf(0f)
    val slewingCurrent: State<Float> = _slewingCurrent

    private val _heightCurrent = mutableStateOf(0f)
    val heightCurrent: State<Float> = _heightCurrent

    private val _moveCurrent = mutableStateOf(0f)
    val moveCurrent: State<Float> = _moveCurrent

    private val _windCurrent = mutableStateOf(0f)
    val windCurrent: State<Float> = _windCurrent

    private val _voltageL1L2Current = mutableStateOf(0f)
    val voltageL1L2Current: State<Float> = _voltageL1L2Current

    private val _voltageL2L3Current = mutableStateOf(0f)
    val voltageL2L3Current: State<Float> = _voltageL2L3Current

    private val _voltageL1L3Current = mutableStateOf(0f)
    val voltageL1L3Current: State<Float> = _voltageL1L3Current

// Crane Parameter Scale  UI
    private val _weightPercent = mutableStateOf(0f)
    val weightPercent: State<Float> = _weightPercent

    private val _hoistPosition = mutableStateOf(0f)
    val hoistPosition: State<Float> = _hoistPosition

    private val _hoistMaxPosition = mutableStateOf(0f)
    val hoistMaxPosition: State<Float> = _hoistMaxPosition

    private val _cranePosition = mutableStateOf(0f)
    val cranePosition: State<Float> = _cranePosition

    private val _craneMaxPosition = mutableStateOf(0f)
    val craneMaxPosition: State<Float> = _craneMaxPosition

//CraneMechanismsCondition UI
    private val _weightSensorErr = mutableStateOf(0)
    val weightSensorErr: State<Int> = _weightSensorErr

    private val _anemometerSensorErr = mutableStateOf(0)
    val anemometerSensorErr: State<Int> = _anemometerSensorErr

    private val _alarmLampStatus = mutableStateOf(0)
    val alarmLampStatus: State<Int> = _alarmLampStatus

    private val _trolleyFwdStatus = mutableStateOf(0)
    val trolleyFwdStatus: State<Int> = _trolleyFwdStatus

    private val _trolleyRevStatus = mutableStateOf(0)
    val trolleyRevStatus: State<Int> = _trolleyRevStatus

    private val _hoistUpStatus = mutableStateOf(0)
    val hoistUpStatus: State<Int> = _hoistUpStatus

    private val _hoistDownStatus = mutableStateOf(0)
    val hoistDownStatus: State<Int> = _hoistDownStatus

    private val _slewRightStatus = mutableStateOf(0)
    val slewRightStatus: State<Int> = _slewRightStatus

    private val _slewLeftStatus = mutableStateOf(0)
    val slewLeftStatus: State<Int> = _slewLeftStatus

    private val _cranRightStatus = mutableStateOf(0)
    val cranRightStatus: State<Int> = _cranRightStatus

    private val _craneLeftStatus = mutableStateOf(0)
    val craneLeftStatus: State<Int> = _craneLeftStatus

    private val _craneRight = mutableStateOf(0)
    val craneRight: State<Int> = _craneRight

    private val _craneLeft = mutableStateOf(0)
    val craneLeft: State<Int> = _craneLeft

    private val _slewLeft = mutableStateOf(0)
    val slewLeft: State<Int> = _slewLeft

    private val _slewRight = mutableStateOf(0)
    val slewRight: State<Int> = _slewRight

    private val _trolleyFwd = mutableStateOf(0)
    val trolleyFwd: State<Int> = _trolleyFwd

    private val _trolleyRev = mutableStateOf(0)
    val trolleyRev: State<Int> = _trolleyRev

    private val _hoistUp = mutableStateOf(0)
    val hoistUp: State<Int> = _hoistUp

    private val _hoistDown = mutableStateOf(0)
    val hoistDown: State<Int> = _hoistDown

    private var dataCollectionJob: Job? = null

    init {
        startDataCollection()
    }

    private fun startDataCollection() {
        dataCollectionJob = viewModelScope.launch(Dispatchers.Default) {
            modbusRepository.registerCache.collect {
                    cache ->
                try {
                    // Crane Operating Parameters UI
                    _weightQnom.value = safeDivideThousands(cache[152]?.value?:0)
                    _weightCurrent.value = safeDivideTens(cache[153]?.value?:0)
                    _trolleyCurrent.value = safeDivideTens(cache[155]?.value?:0)
                    _slewingCurrent.value = safeDivideTens(cache[158]?.value?:0)
                    _heightCurrent.value = safeDivideTens(cache[159]?.value?:0)
                    _moveCurrent.value = safeDivideTens(cache[162]?.value?:0)
                    _windCurrent.value = safeDivideTens(cache[163]?.value?:0)
                    _voltageL1L2Current.value = safeDivideTens(cache[135]?.value?:0)
                    _voltageL2L3Current.value = safeDivideTens(cache[136]?.value?:0)
                    _voltageL1L3Current.value = safeDivideTens(cache[137]?.value?:0)

                    // Crane Parameter Scale UI
                    _weightPercent.value = safeDivide(cache[317]?.value?:0)
                    _hoistPosition.value = safeDivideTens(cache[159]?.value?:0)
                    _hoistMaxPosition.value = safeDivideTens(cache[318]?.value?:0)
                    _cranePosition.value = safeDivideTens(cache[162]?.value?:0)
                    _craneMaxPosition.value = safeDivideTens(cache[319]?.value?:0)

                    //CraneMechanismsCondition UI
                    _weightSensorErr.value      = cache[711]?.value?: return@collect
                    _trolleyFwdStatus.value     = cache[320]?.value?: return@collect
                    _trolleyRevStatus.value     = cache[321]?.value?: return@collect
                    _hoistUpStatus.value        = cache[322]?.value?: return@collect
                    _hoistDownStatus.value      = cache[323]?.value?: return@collect
                    _slewRightStatus.value      = cache[324]?.value?: return@collect
                    _slewLeftStatus.value       = cache[325]?.value?: return@collect
                    _cranRightStatus.value      = cache[326]?.value?: return@collect
                    _craneLeftStatus.value      = cache[327]?.value?: return@collect
                    _craneRight.value   = cache[328]?.value?: return@collect
                    _craneLeft.value    = cache[329]?.value?: return@collect
                    _slewLeft.value     = cache[330]?.value?: return@collect
                    _slewRight.value    = cache[331]?.value?: return@collect
                    _trolleyFwd.value   = cache[332]?.value?: return@collect
                    _trolleyRev.value   = cache[333]?.value?: return@collect
                    _hoistUp.value      = cache[334]?.value?: return@collect
                    _hoistDown.value    = cache[335]?.value?: return@collect
                    _anemometerSensorErr.value  = cache[336]?.value?: return@collect
                    _alarmLampStatus.value     = cache[337]?.value?: return@collect
                }
                catch (e: Exception){
                }
            }
        }
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
}