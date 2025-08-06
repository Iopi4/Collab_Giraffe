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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsText
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonTabGiraffe
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun ParametersContent(
){
    val navController = rememberNavController()

    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    {NavBarParametersContent(navController = navController)
        NavHost(navController, startDestination = NavRoutesParameters.PageParameters.route) {
            composable(NavRoutesParameters.PageParameters.route){ Parameters() }
            composable(NavRoutesParameters.PageEngineHours.route) { EngineHours() }
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