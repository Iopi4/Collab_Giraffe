package com.example.giraffenew.service_menu.ui.user

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.giraffenew.service_menu.presentation.ServiceMenuViewModel

@Composable
fun UserContent(
    serviceMenuViewModel: ServiceMenuViewModel = hiltViewModel(),
    onKioskModeToggle: () -> Unit = {},
    isKioskModeEnabled: Boolean = false,
) {
    val state by serviceMenuViewModel.state
// 1. Получаем текущий LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current


    // 2. Создаем Lifecycle-aware эффект
    DisposableEffect(lifecycleOwner, ) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("ServiceMenuScreen", "Lifecycle: ON_RESUME- resuming data collection")
                    serviceMenuViewModel.setActiveConfiguration()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("ServiceMenuScreen", "Lifecycle: ON_PAUSE - pausing data collection")
                    serviceMenuViewModel.clearActiveConfiguration()
                }
                else -> {Log.e("ServiceMenuScreen", "Lifecycle: NOT START")}
            }
        }

        // 3. Регистрируем наблюдатель
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        // 4. Очистка при удалении Composable
        onDispose {
            Log.d("ServiceMenuScreen", "Lifecycle: ON_DISPOSE - removing observer")
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            serviceMenuViewModel.clearActiveConfiguration() // Дополнительная очистка
        }
    }

    ServiceUser(
        onSetDataLogErrStopHoist = { serviceMenuViewModel.onSetDataLogErrStopHoist() },
        onSetPasswordNaladka = { serviceMenuViewModel.onSetPasswordNaladka()},
        onSetFourPolygonsEn = { serviceMenuViewModel.onSetFourPolygonsEn()},
        onSetWithoutRingCurrentCollectorEn = { serviceMenuViewModel.onSetWithoutRingCurrentCollectorEn()},
        onSetZoneACCDECActiveEn = { serviceMenuViewModel.onSetZoneACCDECActiveEn()},
        onSetLukModeActiveEn= { serviceMenuViewModel.onSetLukModeActiveEn()},
        onSetAndroidModeActiveEn = {serviceMenuViewModel.onSetAndroidModeActiveEn()},

        currentDataLogErrStopHoist = state.userState.currentDataLogErrStopHoist,
        currentPasswordOrNaladka = serviceMenuViewModel.state.value.userState.currentPasswordOrNaladka,
        currentFOURPolygonsEn = serviceMenuViewModel.state.value.userState.currentFOURPolygonsEn,
        currentWithoutRingCurrentCollector = serviceMenuViewModel.state.value.userState.currentWithoutRingCurrentCollector,
        currentZoneACCDECActive = serviceMenuViewModel.state.value.userState.currentZoneACCDECActive,
        currentLukModeActiveEn  = serviceMenuViewModel.state.value.userState.currentLukModeActiveEn,

        onKioskModeToggle = onKioskModeToggle,
        isKioskModeEnabled = isKioskModeEnabled,

        onSetACSSRst = {serviceMenuViewModel.onSetACSSRst()}
    )

}