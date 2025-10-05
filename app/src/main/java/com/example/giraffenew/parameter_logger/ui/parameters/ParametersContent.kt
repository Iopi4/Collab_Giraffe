package com.example.giraffenew.parameter_logger.ui.parameters

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsText
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonTabGiraffe
import com.example.giraffenew.parameter_logger.presentation.ParameterLoggerViewModule
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun ParametersContent(
    parameterLoggerViewModule: ParameterLoggerViewModule = hiltViewModel()
){

    // 1. Получаем текущий LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current

    // 2. Создаем Lifecycle-aware эффект
    DisposableEffect(lifecycleOwner, parameterLoggerViewModule) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("ParameterLoggerScreen", "Lifecycle: ON_RESUME - resuming data collection")
                    parameterLoggerViewModule.setActiveConfiguration()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("ParameterLoggerScreen", "Lifecycle: ON_PAUSE    - pausing data collection")
                    parameterLoggerViewModule.clearActiveConfiguration()
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
            parameterLoggerViewModule.clearActiveConfiguration() // Дополнительная очистка
        }
    }

    val navController = rememberNavController()

    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    {NavBarParametersContent(navController = navController)
        NavHost(navController, startDestination = NavRoutesParameters.PageParameters.route) {
            composable(NavRoutesParameters.PageParameters.route)
            {
                Parameters(
                    qNom = parameterLoggerViewModule.state.value.сraneParameters.qNom,
                    craneLenght = parameterLoggerViewModule.state.value.сraneParameters.craneLenght,
                    rAP = parameterLoggerViewModule.state.value.сraneParameters.rAP,
                    rAFIso = parameterLoggerViewModule.state.value.сraneParameters.rAFIso,
                    rQF = parameterLoggerViewModule.state.value.сraneParameters.rQF,
                    kP = parameterLoggerViewModule.state.value.сraneParameters.kP,
                    classOfUseC = parameterLoggerViewModule.state.value.сraneParameters.classOfUseC,
                    rN1 = parameterLoggerViewModule.state.value.сraneParameters.rN1,
                    rN2 = parameterLoggerViewModule.state.value.сraneParameters.rN2,
                    settingModeYear = parameterLoggerViewModule.state.value.сraneParameters.settingModeYear,
                    settingModeMonth = parameterLoggerViewModule.state.value.сraneParameters.settingModeMonth,
                    settingModeDay = parameterLoggerViewModule.state.value.сraneParameters.settingModeDay,
                    settingModeHour = parameterLoggerViewModule.state.value.сraneParameters.settingModeHour,
                    settingModeMinute = parameterLoggerViewModule.state.value.сraneParameters.settingModeMinute,
                    settingModeSeconds = parameterLoggerViewModule.state.value.сraneParameters.settingModeSeconds,
                    hmiSBKBNo = parameterLoggerViewModule.state.value.сraneParameters.hmiSBKBNo,
                    hmiCraneNo = parameterLoggerViewModule.state.value.сraneParameters.hmiCraneNo,
                    hmiSBKBInstallationDay = parameterLoggerViewModule.state.value.сraneParameters.hmiSBKBInstallationDay,
                    hmiSBKBInstallationMonth = parameterLoggerViewModule.state.value.сraneParameters.hmiSBKBInstallationMonth,
                    hmiSBKBInstallationYear = parameterLoggerViewModule.state.value.сraneParameters.hmiSBKBInstallationYear,
                )
            }
            composable(NavRoutesParameters.PageEngineHours.route)
            {
                EngineHours(
                hmiHoistOperatingHours = parameterLoggerViewModule.state.value.сraneParameters.hmiHoistOperatingHours,
                hmiSlewOperatingHours = parameterLoggerViewModule.state.value.сraneParameters.hmiSlewOperatingHours,
                hmiTrolleyOperatingHours = parameterLoggerViewModule.state.value.сraneParameters.hmiTrolleyOperatingHours,
                hmiCraneOperatingHours = parameterLoggerViewModule.state.value.сraneParameters.hmiCraneOperatingHours,
                hmiTotalCraneOperatingHours = parameterLoggerViewModule.state.value.сraneParameters.hmiTotalCraneOperatingHours,
                hmiSBKBOperatingHours = parameterLoggerViewModule.state.value.сraneParameters.hmiSBKBOperatingHours,
            )
            }
        }
    }
}


@Composable
fun NavBarParametersContent (navController: NavController){
    val myBoxNavParametersContentModifier = Modifier
        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = DarkGrew,
            shape = RoundedCornerShape(5.dp)
        )
//Код для формирования области в верху с кнопками переходов между страницами
    Column (myBoxNavParametersContentModifier,
        verticalArrangement = Arrangement.SpaceEvenly)
    {
// Заголовок
        BasicSettingsText(modifier = Modifier,
            text = stringResource(R.string.ParameterLogger_Text_Parameters_Crane))

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ParameterLogger_Button_Parameter,
                    onClick = {
                        navController.navigate(NavRoutesParameters.PageParameters.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ParameterLogger_Button_Engine_Hours,
                    onClick = {
                        navController.navigate(NavRoutesParameters.PageEngineHours.route) },) {}
            }
        }
    }
}

sealed class NavRoutesParameters (val route: String) {
    object PageParameters: NavRoutesParameters("Параметры. Даннные крана")
    object PageEngineHours: NavRoutesParameters("Параметры. Моточасы механизмов")
}

@Preview
@Composable
fun ParametersContentPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ParametersContent(
        )
    }
}