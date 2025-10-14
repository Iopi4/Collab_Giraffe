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
import com.example.giraffenew.parameter_logger.ui.cycles.Cycles
import com.example.giraffenew.parameter_logger.ui.cycles.CyclesContent
import com.example.giraffenew.parameter_logger.ui.load.Load
import com.example.giraffenew.parameter_logger.ui.load.LoadContent
import com.example.giraffenew.parameter_logger.ui.parameters.ParametersContent
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import kotlin.Int

@Composable
fun ParameterLoggerScreen (
    topBarViewModel: TopBarViewModel = hiltViewModel(),
){
    BasicSettingBackground()
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
            composable(NavRoutesParameterLogger.PageCycles.route) { CyclesContent() }
            composable(NavRoutesParameterLogger.PageLoad.route) { LoadContent() }
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