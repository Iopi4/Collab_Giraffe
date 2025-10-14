package com.example.giraffenew.parameter_logger.ui.load

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.giraffenew.parameter_logger.presentation.ParameterLoggerViewModule

@Composable
fun LoadContent(
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

    Load(
        hmiReadSDcardStatus = parameterLoggerView.state.value.savingAndLoadingSettings.hmiReadSDcardStatus,

        onSetSaveVarFile = {parameterLoggerView.onSetSaveVarFile()},
        onSetSaveAbortVarFile = {parameterLoggerView.onSetSaveAbortVarFile()},
        hmiReadVarSaveProcess = parameterLoggerView.state.value.savingAndLoadingSettings.hmiReadVarSaveProcess,
        hmiReadVarSaveStatus = parameterLoggerView.state.value.savingAndLoadingSettings.hmiReadVarSaveStatus,
        hmiReadVarSize = parameterLoggerView.state.value.savingAndLoadingSettings.hmiReadVarSize,

        onSetReadSizeVarFile = {parameterLoggerView.onSetReadSizeVarFile()},
        onSetReadVarFile= {parameterLoggerView.onSetReadVarFile()},
        hmiReadVarReadProcess = parameterLoggerView.state.value.savingAndLoadingSettings.hmiReadVarReadProcess,
        hmiReadVarReadStatus = parameterLoggerView.state.value.savingAndLoadingSettings.hmiReadVarReadStatus,
        onSetReadAbortVarFile = {parameterLoggerView.onSetReadAbortVarFile()},
        onSetSaveRPFileToSD = {parameterLoggerView.onSetSaveRPFileToSD()},
    )
}