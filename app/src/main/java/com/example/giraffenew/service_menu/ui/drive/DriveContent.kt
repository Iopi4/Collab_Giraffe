package com.example.giraffenew.service_menu.ui.drive

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
fun DriveContent(
){
    val navController= rememberNavController()
    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    {
    NavBarDriveContent(navController = navController)
        NavHost(navController, startDestination = NavRoutesDriveContent.PageServiceHoist.route) {
            composable(NavRoutesDriveContent.PageServiceHoist.route){ServiceHoist()}
            composable(NavRoutesDriveContent.PageServiceSlewing.route){ServiceSlewing()}
            composable(NavRoutesDriveContent.PageServiceTrolley.route) {ServiceTrolley()}
            composable(NavRoutesDriveContent.PageServiceCrane.route) {ServiceCrane()}
        }
    }
}

@Composable
fun NavBarDriveContent(navController: NavController){

    val myBoxNavBarDriveContentModifier = Modifier
        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = DarkGrew,
            shape = RoundedCornerShape(5.dp)
        )

    Column(myBoxNavBarDriveContentModifier,
        verticalArrangement = Arrangement.SpaceEvenly)
    {
// Заголовок
        BasicSettingsText(modifier = Modifier,
            text = stringResource(R.string.ServiceMenu_Text_Drive_Parameters))

//Код для формирования области в верху с кнопками переходов между страницами
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Drive_Hoist,
                    onClick = {
                        navController.navigate(NavRoutesDriveContent.PageServiceHoist.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Drive_Slewing,
                    onClick = {
                        navController.navigate(NavRoutesDriveContent.PageServiceSlewing.route) },) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Drive_Trolley,
                    onClick = {
                        navController.navigate(NavRoutesDriveContent.PageServiceTrolley.route) },) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Drive_Crane,
                    onClick = {
                        navController.navigate(NavRoutesDriveContent.PageServiceCrane.route) },) {}
            }
        }
    }
}

sealed class NavRoutesDriveContent (val route: String){
    object PageServiceHoist: NavRoutesDriveContent("Сервис. Привод подъема. Настройка")
    object PageServiceSlewing: NavRoutesDriveContent("Сервис. Привод поворота. Настройка")
    object PageServiceTrolley: NavRoutesDriveContent("Сервис. Привод тележки. Настройка")
    object PageServiceCrane: NavRoutesDriveContent("Сервис. Привод хода крана. Настройка")
}

@Preview
@Composable
fun DriveContentPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        DriveContent(
        )
    }
}