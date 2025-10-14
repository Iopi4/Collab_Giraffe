package com.example.giraffenew.parameter_logger.ui.cycles

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.giraffenew.parameter_logger.presentation.ParameterLoggerViewModule

@Composable
fun CyclesContent(
    parameterLoggerView: ParameterLoggerViewModule = hiltViewModel()
) {

    // 1. Получаем текущий LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current

    // 2. Создаем Lifecycle-aware эффект
    DisposableEffect(lifecycleOwner, parameterLoggerView) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("ParameterLoggerScreen", "Lifecycle: ON_RESUME - resuming data collection")
                    parameterLoggerView.setActiveConfiguration()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("ParameterLoggerScreen", "Lifecycle: ON_PAUSE    - pausing data collection")
                    parameterLoggerView.clearActiveConfiguration()
                }
                else -> {Log.e("ParameterLoggerScreen", "Lifecycle: NOT START")}
            }
        }
        // 3. Регистрируем наблюдатель
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        // 4. Очистка при удалении Composable
        onDispose {
            Log.d("ParameterLoggerScreen", "Lifecycle: ON_DISPOSE - removing observer")
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            parameterLoggerView.clearActiveConfiguration() // Дополнительная очистка
        }
    }

    Cycles(
        arrRestA1MW1 = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA1MW1,
        arrRestA1MW2  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA1MW2,
        arrRestA2MW1  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA2MW1,
        arrRestA2MW2  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA2MW2,
        arrRestA3MW1  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA3MW1,
        arrRestA3MW2  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA3MW2,
        arrRestA4MW1  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA4MW1,
        arrRestA4MW2  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA4MW2,
        arrRestA5MW1  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA5MW1,
        arrRestA5MW2  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA5MW2,
        arrRestA6MW1  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA6MW1,
        arrRestA6MW2  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA6MW2,
        arrRestA7MW1  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA7MW1,
        arrRestA7MW2  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA7MW2,
        arrRestA8MW1  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA8MW1,
        arrRestA8MW2  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA8MW2,
        arrRestA9MW1  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA9MW1,
        arrRestA9MW2  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA9MW2,
        arrRestA10MW1  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA10MW1,
        arrRestA10MW2  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA10MW2,
        arrRestA11MW1  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA11MW1,
        arrRestA11MW2  = parameterLoggerView.state.value.cyclesOfWorkParameters.arrRestA11MW2,
        rCycleHoistMW1  = parameterLoggerView.state.value.cyclesOfWorkParameters.rCycleHoistMW1,
        rCycleHoistMW2  = parameterLoggerView.state.value.cyclesOfWorkParameters.rCycleHoistMW2,
        rCycleSlewMW1  = parameterLoggerView.state.value.cyclesOfWorkParameters.rCycleSlewMW1,
        rCycleSlewMW2  = parameterLoggerView.state.value.cyclesOfWorkParameters.rCycleSlewMW2,
        rCycleTrolleyMW1  = parameterLoggerView.state.value.cyclesOfWorkParameters.rCycleTrolleyMW1,
        rCycleTrolleyMW2  = parameterLoggerView.state.value.cyclesOfWorkParameters.rCycleTrolleyMW2,
        rCycleCraneMW1  = parameterLoggerView.state.value.cyclesOfWorkParameters.rCycleCraneMW1,
        rCycleCraneMW2  = parameterLoggerView.state.value.cyclesOfWorkParameters.rCycleCraneMW2,
        rCycleHoleCraneMW1  = parameterLoggerView.state.value.cyclesOfWorkParameters.rCycleHoleCraneMW1,
        rCycleHoleCraneMW2  = parameterLoggerView.state.value.cyclesOfWorkParameters.rCycleHoleCraneMW2,
    )


}