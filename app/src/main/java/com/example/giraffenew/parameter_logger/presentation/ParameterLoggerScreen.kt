package com.example.giraffenew.parameter_logger.presentation

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
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonTabGiraffe
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonTabGiraffeAccess
import com.example.giraffenew.navigate.presentation.TopBarViewModel
import com.example.giraffenew.parameter_logger.ui.Cycles
import com.example.giraffenew.parameter_logger.ui.Load
import com.example.giraffenew.parameter_logger.ui.parameters.ParametersContent
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import kotlin.Int

@Composable
fun ParameterLoggerScreen (
    parameterLoggerViewModule: ParameterLoggerViewModule = hiltViewModel(),

){
    BasicSettingBackground()

    val topBarViewModel: TopBarViewModel = hiltViewModel()
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
    {NavBarParameterLoggerContent(
        navController = navController,
        buildMode = topBarViewModel.buildMode.value, // Передаем значение buildMode
        serviceMode = topBarViewModel.serviceMode.value,
        )
        NavHost(navController, startDestination = NavRoutesParameterLogger.PageParameters.route) {
            composable(NavRoutesParameterLogger.PageParameters.route){ ParametersContent() }
            composable(NavRoutesParameterLogger.PageCycles.route)
            {
                Cycles(
                    arrRestA1MW1 = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA1MW1,
                    arrRestA1MW2  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA1MW2,
                    arrRestA2MW1  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA2MW1,
                    arrRestA2MW2  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA2MW2,
                    arrRestA3MW1  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA3MW1,
                    arrRestA3MW2  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA3MW2,
                    arrRestA4MW1  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA4MW1,
                    arrRestA4MW2  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA4MW2,
                    arrRestA5MW1  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA5MW1,
                    arrRestA5MW2  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA5MW2,
                    arrRestA6MW1  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA6MW1,
                    arrRestA6MW2  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA6MW2,
                    arrRestA7MW1  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA7MW1,
                    arrRestA7MW2  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA7MW2,
                    arrRestA8MW1  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA8MW1,
                    arrRestA8MW2  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA8MW2,
                    arrRestA9MW1  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA9MW1,
                    arrRestA9MW2  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA9MW2,
                    arrRestA10MW1  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA10MW1,
                    arrRestA10MW2  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA10MW2,
                    arrRestA11MW1  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA11MW1,
                    arrRestA11MW2  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.arrRestA11MW2,
                    rCycleHoistMW1  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.rCycleHoistMW1,
                    rCycleHoistMW2  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.rCycleHoistMW2,
                    rCycleSlewMW1  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.rCycleSlewMW1,
                    rCycleSlewMW2  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.rCycleSlewMW2,
                    rCycleTrolleyMW1  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.rCycleTrolleyMW1,
                    rCycleTrolleyMW2  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.rCycleTrolleyMW2,
                    rCycleCraneMW1  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.rCycleCraneMW1,
                    rCycleCraneMW2  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.rCycleCraneMW2,
                    rCycleHoleCraneMW1  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.rCycleHoleCraneMW1,
                    rCycleHoleCraneMW2  = parameterLoggerViewModule.state.value.cyclesOfWorkParameters.rCycleHoleCraneMW2,
                )
            }
            composable(NavRoutesParameterLogger.PageLoad.route)
            {
                Load(
                    hmiReadSDcardStatus = parameterLoggerViewModule.state.value.savingAndLoadingSettings.hmiReadSDcardStatus,

                    onSetSaveVarFile = {parameterLoggerViewModule.onSetSaveVarFile()},
                    onSetSaveAbortVarFile = {parameterLoggerViewModule.onSetSaveAbortVarFile()},
                    hmiReadVarSaveProcess = parameterLoggerViewModule.state.value.savingAndLoadingSettings.hmiReadVarSaveProcess,
                    hmiReadVarSaveStatus = parameterLoggerViewModule.state.value.savingAndLoadingSettings.hmiReadVarSaveStatus,
                    hmiReadVarSize = parameterLoggerViewModule.state.value.savingAndLoadingSettings.hmiReadVarSize,

                    onSetReadSizeVarFile = {parameterLoggerViewModule.onSetReadSizeVarFile()},
                    onSetReadVarFile= {parameterLoggerViewModule.onSetReadVarFile()},
                    hmiReadVarReadProcess = parameterLoggerViewModule.state.value.savingAndLoadingSettings.hmiReadVarReadProcess,
                    hmiReadVarReadStatus = parameterLoggerViewModule.state.value.savingAndLoadingSettings.hmiReadVarReadStatus,
                    onSetReadAbortVarFile = {parameterLoggerViewModule.onSetReadAbortVarFile()},
                    onSetSaveRPFileToSD = {parameterLoggerViewModule.onSetSaveRPFileToSD()},
                )
            }
        }
    }
}

@Composable
fun NavBarParameterLoggerContent (
    navController: NavController,
    buildMode: Int,
    serviceMode: Int,
){

    val myBoxNavServiceContentModifier = Modifier
        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = DarkGrew,
            shape = RoundedCornerShape(5.dp)
        )
    val topBarViewModel: TopBarViewModel = hiltViewModel()
    val protectedMenusEnabled = buildMode == 1 || serviceMode == 1
    val protectedMenusEnabledService = serviceMode == 1

//Код для формирования области в верху с кнопками переходов между страницами
    Column (myBoxNavServiceContentModifier,
        verticalArrangement = Arrangement.SpaceEvenly)
    {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ParameterLogger_Button_Parameters,
                    onClick = {
                        navController.navigate(NavRoutesParameterLogger.PageParameters.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ParameterLogger_Button_Cycles,
                    onClick = {
                        navController.navigate(NavRoutesParameterLogger.PageCycles.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffeAccess(
                    modifier = Modifier.padding(end = 7.dp),
                    text = R.string.ParameterLogger_Button_Load,
                    isLocked = !protectedMenusEnabled,
                    onClick = {
                        if (protectedMenusEnabled)
                        {
                            navController.navigate(NavRoutesParameterLogger.PageLoad.route)
                        }
                        else topBarViewModel.playInactiveButtonSound()
                              },)
            }
        }
    }
}

sealed class NavRoutesParameterLogger (val route: String){
    object PageParameters: NavRoutesParameterLogger("Регистратор параметров. ПАРАМЕТРЫ КРАНА")
    object PageCycles: NavRoutesParameterLogger("Регистратор параметров. ЦИКЛЫ РАБОТЫ")
    object PageLoad: NavRoutesParameterLogger("Регистратор параметров. СОХРАНЕНИЕ")
}

@Preview
@Composable
fun ParameterLoggerScreenPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ParameterLoggerScreen(
        )
    }
}