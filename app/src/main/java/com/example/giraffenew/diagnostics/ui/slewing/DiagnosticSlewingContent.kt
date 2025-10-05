package com.example.giraffenew.diagnostics.ui.slewing

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
fun DiagnosticSlewingContent(
    diagnosticViewModule: DiagnosticViewModule = hiltViewModel()
)
{

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

    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    {
        NavBarDiagnosticSlewing(navController = navController)
        NavHost(navController, startDestination = NavRoutesDiagnosticSlewing.PageDiagnosticSlewingState.route) {
            composable(NavRoutesDiagnosticSlewing.PageDiagnosticSlewingState.route)
            {
                DiagnosticSlewingState(
                    slewDrivePowerError = diagnosticViewModule.state.value.drivesParameters.slewDrivePowerError,
                    slewControlWord = diagnosticViewModule.state.value.drivesParameters.slewControlWord,
                    slewDriveError = diagnosticViewModule.state.value.drivesParameters.slewDriveError,
                    slewFault = diagnosticViewModule.state.value.drivesParameters.slewFault,
                    slewDriveErrorCode = diagnosticViewModule.state.value.drivesParameters.slewDriveErrorCode,
                    slewTargetVelocity = diagnosticViewModule.state.value.drivesParameters.slewTargetVelocity,
                    slewControlEffort = diagnosticViewModule.state.value.drivesParameters.slewControlEffort,
                    slewMotorCurrent = diagnosticViewModule.state.value.drivesParameters.slewMotorCurrent,
                    slewEncoder = diagnosticViewModule.state.value.drivesParameters.slewEncoder,
                )
            }
            composable(NavRoutesDiagnosticSlewing.PageDiagnosticSlewingDIDO.route)
            {
                DiagnosticSlewingDIDO(
                    slewIL1R = diagnosticViewModule.state.value.drivesParameters.slewIL1R,
                    slewOL1R = diagnosticViewModule.state.value.drivesParameters.slewOL1R,
                )
            }
        }
    }
}


@Composable
fun NavBarDiagnosticSlewing(navController: NavController){

    val myBoxNavBarDiagnosticSlewingModifier = Modifier
        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = DarkGrew,
            shape = RoundedCornerShape(5.dp)
        )

    Column(myBoxNavBarDiagnosticSlewingModifier,
        verticalArrangement = Arrangement.SpaceEvenly)
    {
// Заголовок
        BasicSettingsText(modifier = Modifier,
            text = stringResource(R.string.Diagnostic_Text_Drive_Slewing))

        //Код для формирования области в верху с кнопками переходов между страницами
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.Diagnostic_Button_Drive,
                    onClick = {
                        navController.navigate(NavRoutesDiagnosticSlewing.PageDiagnosticSlewingState.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.Diagnostic_Button_Drive_LI_DO,
                    onClick = {
                        navController.navigate(NavRoutesDiagnosticSlewing.PageDiagnosticSlewingDIDO.route) },) {}
            }
        }
    }
}


sealed class NavRoutesDiagnosticSlewing (val route: String){
    object PageDiagnosticSlewingState: NavRoutesDiagnosticSlewing("Диагностика. Поворот. Состояние")
    object PageDiagnosticSlewingDIDO: NavRoutesDiagnosticSlewing("Диагностика. Поворот. Входа/выхода")
}


@Preview
@Composable
fun DiagnosticSlewingPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        DiagnosticSlewingContent(
        )
    }
}