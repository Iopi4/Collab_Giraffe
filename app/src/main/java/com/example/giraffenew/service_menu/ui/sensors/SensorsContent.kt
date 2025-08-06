package com.example.giraffenew.service_menu.ui.sensors

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
fun SensorsContent(
){
    val navController= rememberNavController()
    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    {
        NavBarSensorsContent(navController = navController)
        NavHost(navController, startDestination = NavRoutesSensorsContent.PageServiceWind.route) {
            composable(NavRoutesSensorsContent.PageServiceWind.route){ServiceWind()}
            composable(NavRoutesSensorsContent.PageServiceWeight.route){ServiceWeight()}
            composable(NavRoutesSensorsContent.PageServicePosition.route){ServicePosition()}
            composable(NavRoutesSensorsContent.PageServiceRefPoint.route) {ServiceRefPoint()}
        }
    }
}


@Composable
fun NavBarSensorsContent (navController: NavController){
    val myBoxNavBarSensorsContentModifier = Modifier
        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = DarkGrew,
            shape = RoundedCornerShape(5.dp)
        )
    Column(myBoxNavBarSensorsContentModifier,
        verticalArrangement = Arrangement.SpaceEvenly)
    {
// Заголовок
        BasicSettingsText(modifier = Modifier,
            text = stringResource(R.string.ServiceMenu_Text_Sensors_Parameters))

        //Код для формирования области в верху с кнопками переходов между страницами
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Sensors_Wind,
                    onClick = {
                        navController.navigate(NavRoutesSensorsContent.PageServiceWind.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Sensors_Weight,
                    onClick = {
                        navController.navigate(NavRoutesSensorsContent.PageServiceWeight.route) },) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Sensors_Position,
                    onClick = {
                        navController.navigate(NavRoutesSensorsContent.PageServicePosition.route) },) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Sensors_Ref_Point,
                    onClick = {
                        navController.navigate(NavRoutesSensorsContent.PageServiceRefPoint.route) },) {}
            }
        }
    }
}

sealed class NavRoutesSensorsContent (val route: String){
    object PageServiceWind: NavRoutesSensorsContent("Сервис. Датчик ветра. Настройка")
    object PageServiceWeight: NavRoutesSensorsContent("Сервис. Датчик усилия. Настройка")
    object PageServicePosition: NavRoutesSensorsContent("Сервис. Датчики положения. Настройка")
    object PageServiceRefPoint: NavRoutesSensorsContent("Сервис. Датчик реперной точки. Настройка")
}

@Preview
@Composable
fun SensorsContentPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        SensorsContent(
        )
    }
}