package com.example.giraffenew.diagnostics.ui.plc

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
import com.example.giraffenew.diagnostics.prezentation.DiagnosticViewModule
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun DiagnosticPLCContent(
    diagnosticViewModule: DiagnosticViewModule = hiltViewModel()
) {
// 1. Получаем текущий LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current

    // 2. Создаем Lifecycle-aware эффект
    DisposableEffect(lifecycleOwner, diagnosticViewModule) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("DiagnosticsScreen", "Lifecycle: ON_RESUME - resuming data collection")
                    diagnosticViewModule.setActiveConfiguration()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("DiagnosticsScreen", "Lifecycle: ON_PAUSE    - pausing data collection")
                    diagnosticViewModule.clearActiveConfiguration()
                }
                else -> {Log.e("DiagnosticsScreen", "Lifecycle: NOT START")}
            }
        }
        // 3. Регистрируем наблюдатель
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        // 4. Очистка при удалении Composable
        onDispose {
            Log.d("DiagnosticsScreen", "Lifecycle: ON_DISPOSE - removing observer")
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            diagnosticViewModule.clearActiveConfiguration() // Дополнительная очистка
        }
    }

    val navController = rememberNavController()

    Column (modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    {
        NavBarDiagnosticPLC(navController = navController)
        NavHost(navController, startDestination = NavRoutesDiagnosticPLC.PageDiagnosticPLCDIDO.route){
            composable(NavRoutesDiagnosticPLC.PageDiagnosticPLCDIDO.route)
            {
                DiagnosticPLCDIDO(
                    plcDI000PhaseControlRelay = diagnosticViewModule.state.value.plcParameters.plcDI000PhaseControlRelay,
                    plcDI001LineContactor = diagnosticViewModule.state.value.plcParameters.plcDI001LineContactor,
                    plcDI002MontageMode = diagnosticViewModule.state.value.plcParameters.plcDI002MontageMode,
                    plcDI003OGPLimSw = diagnosticViewModule.state.value.plcParameters.plcDI003OGPLimSw,
                    plcDI004HoistBrakeStatus = diagnosticViewModule.state.value.plcParameters.plcDI004HoistBrakeStatus,
                    plcDI005HoistBrakeSensor = diagnosticViewModule.state.value.plcParameters.plcDI005HoistBrakeSensor,
                    plcDI006HoistLowSpdButton = diagnosticViewModule.state.value.plcParameters.plcDI006HoistLowSpdButton,
                    plcDI007SlewBrake = diagnosticViewModule.state.value.plcParameters.plcDI007SlewBrake,

                    plcDQ000LineContactorEn = diagnosticViewModule.state.value.plcParameters.plcDQ000LineContactorEn,
                    plcDQ001HoistPWREn = diagnosticViewModule.state.value.plcParameters.plcDQ001HoistPWREn,
                    plcDQ002SlewPWREn = diagnosticViewModule.state.value.plcParameters.plcDQ002SlewPWREn,
                    plcDQ003TrolleyPWREn = diagnosticViewModule.state.value.plcParameters.plcDQ003TrolleyPWREn,
                    plcDQ004CranePWREn = diagnosticViewModule.state.value.plcParameters.plcDQ004CranePWREn,
                    plcDQ005HoistVentilation = diagnosticViewModule.state.value.plcParameters.plcDQ005HoistVentilation,
                    plcDQ006GlobalReady = diagnosticViewModule.state.value.plcParameters.plcDQ006GlobalReady,
                    plcDQ007HoistOverloadStop = diagnosticViewModule.state.value.plcParameters.plcDQ007HoistOverloadStop,
                )
            }
            composable(NavRoutesDiagnosticPLC.PageDiagnosticPLCModuleDI.route)
            {
                DiagnosticPLCModuleDI(
                    plcDI100MicroclimatInput = diagnosticViewModule.state.value.plcParameters.plcDI100MicroclimatInput,
                    plcDI101BrakePowerControl = diagnosticViewModule.state.value.plcParameters.plcDI101BrakePowerControl,
                    plcDI102VentilationPowerControl = diagnosticViewModule.state.value.plcParameters.plcDI102VentilationPowerControl,
                    plcDI107ACSSPowerControl = diagnosticViewModule.state.value.plcParameters.plcDI107ACSSPowerControl,
                    plcDI110ACSSFault = diagnosticViewModule.state.value.plcParameters.plcDI110ACSSFault,
                    plcDI111ACSSReady = diagnosticViewModule.state.value.plcParameters.plcDI111ACSSReady,
                    plcDI112Zapasovka = diagnosticViewModule.state.value.plcParameters.plcDI112Zapasovka,
                    plcDI113Luk = diagnosticViewModule.state.value.plcParameters.plcDI113Luk,
                    plcDI114HoistZeroPos = diagnosticViewModule.state.value.plcParameters.plcDI114HoistZeroPos,
                    plcDI115SlewZeroPos = diagnosticViewModule.state.value.plcParameters.plcDI115SlewZeroPos,
                    plcDI116TrolleyZeroPos = diagnosticViewModule.state.value.plcParameters.plcDI116TrolleyZeroPos,
                    plcDI117CraneZeroPos = diagnosticViewModule.state.value.plcParameters.plcDI117CraneZeroPos,
                )
            }
            composable(NavRoutesDiagnosticPLC.PageDiagnosticPLCModuleDO.route)
            {
                DiagnosticPLCModuleDO(
                    plcDQ100Sound =diagnosticViewModule.state.value.plcParameters.plcDQ100Sound,
                    plcDQ117ACSSReset = diagnosticViewModule.state.value.plcParameters.plcDQ117ACSSReset,
                )
            }
            composable(NavRoutesDiagnosticPLC.PageDiagnosticPLCModuleAI.route)
            {
                DiagnosticPLCModuleAI(
                    plcAI0AnemometerSensor = diagnosticViewModule.state.value.plcParameters.plcAI0AnemometerSensor,
                    plcAI1LoadCellSensor = diagnosticViewModule.state.value.plcParameters.plcAI1LoadCellSensor,
                )
            }
        }

    }
}

@Composable
fun NavBarDiagnosticPLC(navController: NavController){

    val myBoxNavBarDiagnosticPLCModifier = Modifier
        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = DarkGrew,
            shape = RoundedCornerShape(5.dp)
        )
    Column (myBoxNavBarDiagnosticPLCModifier,
        verticalArrangement = Arrangement.SpaceEvenly)
    {
        // Заголовок
        BasicSettingsText(modifier = Modifier,
            text = stringResource(R.string.Diagnostic_Text_PLC))

        //Код для формирования области в верху с кнопками переходов между страницами
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.Diagnostic_Button_PLC_DIDO,
                    onClick = {
                        navController.navigate(NavRoutesDiagnosticPLC.PageDiagnosticPLCDIDO.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.Diagnostic_Button_PLC_Module_DI,
                    onClick = {
                        navController.navigate(NavRoutesDiagnosticPLC.PageDiagnosticPLCModuleDI.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.Diagnostic_Button_PLC_Module_DO,
                    onClick = {
                        navController.navigate(NavRoutesDiagnosticPLC.PageDiagnosticPLCModuleDO.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier.padding(end = 7.dp),
                    text = R.string.Diagnostic_Button_PLC_Module_AI,
                    onClick = {
                        navController.navigate(NavRoutesDiagnosticPLC.PageDiagnosticPLCModuleAI.route) },) {}
            }


        }

    }

}




sealed class NavRoutesDiagnosticPLC (val route: String){
    object PageDiagnosticPLCDIDO: NavRoutesDiagnosticPLC("Диагностика. ПЛК. Дискр. входа/выхода")
    object PageDiagnosticPLCModuleDI: NavRoutesDiagnosticPLC("Диагностика. ПЛК. Модуль. Дискретные входа")
    object PageDiagnosticPLCModuleDO: NavRoutesDiagnosticPLC("Диагностика. ПЛК. Модуль. Дискретные выходы")
    object PageDiagnosticPLCModuleAI: NavRoutesDiagnosticPLC("Диагностика. АЛК. Модуль. Аналоговые")
}

@Preview
@Composable
fun DiagnosticPLCPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        DiagnosticPLCContent(
        )
    }
}