package com.example.giraffenew.technical_maintenance.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.technical_maintenance.ui.TechnicalMaintenance
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun TechnicalMaintenanceScreen (
    technicalMaintenanceViewModule: TechnicalMaintenanceViewModule = hiltViewModel()
){
    // 1. Получаем текущий LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current

    // 2. Создаем Lifecycle-aware эффект
    DisposableEffect(lifecycleOwner, technicalMaintenanceViewModule) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("TechnicalMaintenanceScreen", "Lifecycle: ON_RESUME - resuming data collection")
                    technicalMaintenanceViewModule.setActiveConfiguration()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("TechnicalMaintenanceScreen", "Lifecycle: ON_PAUSE    - pausing data collection")
                    technicalMaintenanceViewModule.clearActiveConfiguration()
                }
                else -> {Log.e("TechnicalMaintenanceScreen", "Lifecycle: NOT START")}
            }
        }
        // 3. Регистрируем наблюдатель
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        // 4. Очистка при удалении Composable
        onDispose {
            Log.d("TechnicalMaintenanceScreen", "Lifecycle: ON_DISPOSE - removing observer")
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            technicalMaintenanceViewModule.clearActiveConfiguration() // Дополнительная очистка
        }
    }

    BasicSettingBackground()

    TechnicalMaintenance(
        password = technicalMaintenanceViewModule.password.value,
        onPasswordChange = { technicalMaintenanceViewModule.updatePassword(it) },
        onSetPasswordLogIn = {technicalMaintenanceViewModule.onSetPasswordLogIn()},
        craneNo = technicalMaintenanceViewModule.state.value.craneNo,
        tOCounter = technicalMaintenanceViewModule.state.value.tOCounter,
        technicalReglamentTO1 = technicalMaintenanceViewModule.state.value.technicalReglamentTO1,
        alarmTO1 = technicalMaintenanceViewModule.state.value.alarmTO1,
        motorHourDelta = technicalMaintenanceViewModule.state.value.motorHourDelta,
        technicalReglamentTO1Delta = technicalMaintenanceViewModule.state.value.technicalReglamentTO1Delta,
        technicalReglamentxWarrantyEnd = technicalMaintenanceViewModule.state.value.technicalReglamentxWarrantyEnd,
        hmiTotalCraneOperatingHoursMaintenance = technicalMaintenanceViewModule.state.value.hmiTotalCraneOperatingHoursMaintenance,
        onResetPassword = {technicalMaintenanceViewModule.onResetPassword() },
    )

}

@Preview
@Composable
fun TechnicalMaintenanceScreenPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        TechnicalMaintenanceScreen(
        )
    }
}