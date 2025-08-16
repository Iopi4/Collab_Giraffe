package com.example.giraffenew.home.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val modbusRepository: ModbusRepository
) : ViewModel() {

    // Crane Operating Parameters
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

// Crane Parameter Scale
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

    private val _weightSensorErr = mutableStateOf(0)
    val weightSensorErr: State<Int> = _weightSensorErr



    init {
        viewModelScope.launch(Dispatchers.IO){
            modbusRepository.registerCache.collect {
                cache ->
                try {
                    // Crane Operating Parameters
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

                    // Crane Parameter Scale
                    _weightPercent.value = safeDivide(cache[317]?.value?:0)
                    _hoistPosition.value = safeDivideTens(cache[159]?.value?:0)
                    _hoistMaxPosition.value = safeDivideTens(cache[318]?.value?:0)
                    _cranePosition.value = safeDivideTens(cache[162]?.value?:0)
                    _craneMaxPosition.value = safeDivideTens(cache[319]?.value?:0)
                    _weightSensorErr.value = cache[711]?.value?: return@collect

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