package com.example.giraffenew.setting_rotation.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.setting_rotation.ui.SettingRotation

@Composable
fun SettingRotationScreen (
    settingRotationViewModel: SettingRotationViewModel = hiltViewModel()
) {
    BasicSettingBackground()

// 1. Получаем текущий LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current

    // 2. Создаем Lifecycle-aware эффект
    DisposableEffect(lifecycleOwner, settingRotationViewModel) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("SettingRotationScreen", "Lifecycle: ON_RESUME- resuming data collection")
                    settingRotationViewModel.setActiveConfiguration()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("SettingRotationScreen", "Lifecycle: ON_PAUSE - pausing data collection")
                    settingRotationViewModel.clearActiveConfiguration()
                }
                else -> {Log.e("SettingRotationScreen", "Lifecycle: NOT START")}
            }
        }

        // 3. Регистрируем наблюдатель
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        // 4. Очистка при удалении Composable
        onDispose {
            Log.d("SettingRotationScreen", "Lifecycle: ON_DISPOSE - removing observer")
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            settingRotationViewModel.clearActiveConfiguration() // Дополнительная очистка
        }
    }

    SettingRotation(
        setValueSlewSpdStopButton = settingRotationViewModel.setValueSlewSpdStopButton.value,
        onModelChangeSlewSpdStopButton = { settingRotationViewModel.updateSlewSpdStopButton(it) },
        onSetSlewSpdStopButton = { settingRotationViewModel.onSetSlewSpdStopButton() },
        hmiSlewSpeedBrakeClose = settingRotationViewModel.state.value.hmiSlewSpeedBrakeClose,

        setValueSlewDECRevers = settingRotationViewModel.setValueSlewDECRevers.value,
        updateSlewDECRevers = { settingRotationViewModel.updateSlewDECRevers(it) },
        onSetSlewDECRevers = { settingRotationViewModel.onSetSlewDECRevers() },
        hmiSlewDecRevers = settingRotationViewModel.state.value.hmiSlewDecRevers,

        setValueSlewDelayTime = settingRotationViewModel.setValueSlewDelayTime.value,
        updateSlewDelayTime = { settingRotationViewModel.updateSlewDelayTime(it) },
        onSetSlewDelayTime = { settingRotationViewModel.onSetSlewDelayTime() },
        hmiSlewDelayTimeBrake = settingRotationViewModel.state.value.hmiSlewDelayTimeBrake,

        setValueSlewBrakeCloseFrq = settingRotationViewModel.setValueSlewBrakeCloseFrq.value,
        updateSlewBrakeCloseFrq = { settingRotationViewModel.updateSlewBrakeCloseFrq(it) },
        onSetSlewBrakeCloseFrq = { settingRotationViewModel.onSetSlewBrakeCloseFrq() },
        hmiSlewBrakeCloseFrq = settingRotationViewModel.state.value.hmiSlewBrakeCloseFrq,

        setValueSlewFrqStartBraking = settingRotationViewModel.setValueSlewFrqStartBraking.value,
        updateSlewFrqStartBraking = { settingRotationViewModel.updateSlewFrqStartBraking(it) },
        onSetSlewFrqStartBraking = { settingRotationViewModel.onSetSlewFrqStartBraking() },
        hmiSlewFrqStartBraking = settingRotationViewModel.state.value.hmiSlewFrqStartBraking,

        setValueSlewDelayStartBraking = settingRotationViewModel.setValueSlewDelayStartBraking.value,
        updateSlewDelayStartBraking = { settingRotationViewModel.updateSlewDelayStartBraking(it) },
        onSetSlewDelayStartBraking = { settingRotationViewModel.onSetSlewDelayStartBraking() },
        hmiSlewDelayStartBraking = settingRotationViewModel.state.value.hmiSlewDelayStartBraking,

        setValueSlewDelayTimeBrakeCurr = settingRotationViewModel.setValueSlewDelayTimeBrakeCurr.value,
        updateSlewDelayTimeBrakeCurr = { settingRotationViewModel.updateSlewDelayTimeBrakeCurr(it) },
        onSetSlewDelayTimeBrakeCurr = { settingRotationViewModel.onSetSlewDelayTimeBrakeCurr() },
        hmiSlewDelayTimeBrakeCurr = settingRotationViewModel.state.value.hmiSlewDelayTimeBrakeCurr,

        setValueSlewTimeBrakeCurrent = settingRotationViewModel.setValueSlewTimeBrakeCurrent.value,
        updateSlewTimeBrakeCurrent = { settingRotationViewModel.updateSlewTimeBrakeCurrent(it) },
        onSetSlewTimeBrakeCurrent = { settingRotationViewModel.onSetSlewTimeBrakeCurrent() },
        hmiSlewTimeBrakeCurrent = settingRotationViewModel.state.value.hmiSlewTimeBrakeCurrent,

        errorMessageSlew = settingRotationViewModel.errorMessageSlew.value,
        logMessageSlew = settingRotationViewModel.logMessageSlew.value,

    )

}