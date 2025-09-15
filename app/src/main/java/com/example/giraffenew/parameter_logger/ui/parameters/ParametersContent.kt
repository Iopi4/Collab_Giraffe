package com.example.giraffenew.parameter_logger.ui.parameters

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
 parameterLoggerState: ParameterLoggerViewModule = hiltViewModel()
){
    val navController = rememberNavController()

    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    {NavBarParametersContent(navController = navController)
        NavHost(navController, startDestination = NavRoutesParameters.PageParameters.route) {
            composable(NavRoutesParameters.PageParameters.route)
            {
                Parameters(
                    qNom = parameterLoggerState.state.value.сraneParameters.qNom,
                    craneLenght = parameterLoggerState.state.value.сraneParameters.craneLenght,
                    rAP = parameterLoggerState.state.value.сraneParameters.rAP,
                    rAFIso = parameterLoggerState.state.value.сraneParameters.rAFIso,
                    rQF = parameterLoggerState.state.value.сraneParameters.rQF,
                    kP = parameterLoggerState.state.value.сraneParameters.kP,
                    classOfUseC = parameterLoggerState.state.value.сraneParameters.classOfUseC,
                    rN1 = parameterLoggerState.state.value.сraneParameters.rN1,
                    rN2 = parameterLoggerState.state.value.сraneParameters.rN2,
                    settingModeYear = parameterLoggerState.state.value.сraneParameters.settingModeYear,
                    settingModeMonth = parameterLoggerState.state.value.сraneParameters.settingModeMonth,
                    settingModeDay = parameterLoggerState.state.value.сraneParameters.settingModeDay,
                    settingModeHour = parameterLoggerState.state.value.сraneParameters.settingModeHour,
                    settingModeMinute = parameterLoggerState.state.value.сraneParameters.settingModeMinute,
                    settingModeSeconds = parameterLoggerState.state.value.сraneParameters.settingModeSeconds,
                    hmiSBKBNo = parameterLoggerState.state.value.сraneParameters.hmiSBKBNo,
                    hmiCraneNo = parameterLoggerState.state.value.сraneParameters.hmiCraneNo,
                    hmiSBKBInstallationDay = parameterLoggerState.state.value.сraneParameters.hmiSBKBInstallationDay,
                    hmiSBKBInstallationMonth = parameterLoggerState.state.value.сraneParameters.hmiSBKBInstallationMonth,
                    hmiSBKBInstallationYear = parameterLoggerState.state.value.сraneParameters.hmiSBKBInstallationYear,

                )
            }
            composable(NavRoutesParameters.PageEngineHours.route)
            {
                EngineHours(
                hmiHoistOperatingHours = parameterLoggerState.state.value.сraneParameters.hmiHoistOperatingHours,
                hmiSlewOperatingHours = parameterLoggerState.state.value.сraneParameters.hmiSlewOperatingHours,
                hmiTrolleyOperatingHours = parameterLoggerState.state.value.сraneParameters.hmiTrolleyOperatingHours,
                hmiCraneOperatingHours = parameterLoggerState.state.value.сraneParameters.hmiCraneOperatingHours,
                hmiTotalCraneOperatingHours = parameterLoggerState.state.value.сraneParameters.hmiTotalCraneOperatingHours,
                hmiSBKBOperatingHours = parameterLoggerState.state.value.сraneParameters.hmiSBKBOperatingHours,
                hmiTotalCraneOperatingHoursMaintenance = parameterLoggerState.state.value.сraneParameters.hmiTotalCraneOperatingHoursMaintenance,
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