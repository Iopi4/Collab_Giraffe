package com.example.giraffenew.login.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.login.ui.Authorization
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun LoginScreen (
    loginViewModule: LoginViewModule = hiltViewModel()
){
    // 1. Получаем текущий LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current

    // 2. Создаем Lifecycle-aware эффект
    DisposableEffect(lifecycleOwner, loginViewModule) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("LoginScreen", "Lifecycle: ON_RESUME - resuming data collection")
                    loginViewModule.setActiveConfiguration()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("LoginScreen", "Lifecycle: ON_PAUSE    - pausing data collection")
                    loginViewModule.clearActiveConfiguration()
                }
                else -> {Log.e("LoginScreen", "Lifecycle: NOT START")}
            }
        }
        // 3. Регистрируем наблюдатель
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        // 4. Очистка при удалении Composable
        onDispose {
            Log.d("LoginScreen", "Lifecycle: ON_DISPOSE - removing observer")
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            loginViewModule.clearActiveConfiguration() // Дополнительная очистка
        }
    }

    BasicSettingBackground()
    Authorization(
        password = loginViewModule.password.value,
        onPasswordChange = { loginViewModule.updatePassword(it) },

        buildMode = loginViewModule.state.value.buildMode,
        serviceMode = loginViewModule.state.value.serviceMode,
        montageMode = loginViewModule.state.value.montageMode,

        onSetPasswordLogIn = {loginViewModule.onSetPasswordLogIn()},
        onSetExitLevel = {loginViewModule.onSetExitLevel()},
    )
}

@Preview
@Composable
fun LoginScreenPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        LoginScreen(
        )
    }
}