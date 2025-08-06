package com.example.giraffenew.service_menu.ui

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonTabGiraffe
import com.example.giraffenew.diagnostics.ui.DiagnosticContent
import com.example.giraffenew.service_menu.ui.drive.DriveContent
import com.example.giraffenew.service_menu.ui.sensors.SensorsContent
import com.example.giraffenew.service_menu.ui.user.ServiceUser
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun ServiceContent(
){
    val navController = rememberNavController()

    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    {NavBarServiceContent(navController = navController)
        NavHost(navController, startDestination = NavRoutesService.PageDrive.route) {
            composable(NavRoutesService.PageDrive.route){ DriveContent()}
            composable(NavRoutesService.PageSensors.route) {SensorsContent()}
            composable(NavRoutesService.PageUser.route) {ServiceUser()}
        }
    }
}

@Composable
fun NavBarServiceContent (navController: NavController){
    val myBoxNavServiceContentModifier = Modifier
        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = DarkGrew,
            shape = RoundedCornerShape(5.dp)
        )
//Код для формирования области в верху с кнопками переходов между страницами
    Column (myBoxNavServiceContentModifier,
        verticalArrangement = Arrangement.SpaceEvenly)
    {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Drive,
                    onClick = {
                        navController.navigate(NavRoutesService.PageDrive.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Sensors,
                    onClick = {
                        navController.navigate(NavRoutesService.PageSensors.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier.padding(end = 7.dp),
                    text = R.string.ServiceMenu_Button_User,
                    onClick = {
                        navController.navigate(NavRoutesService.PageUser.route) },) {}
            }
        }
    }
}

sealed class NavRoutesService (val route: String){
    object PageDrive: NavRoutesService("Сервис. ПРИВОД")
    object PageSensors: NavRoutesService("Сервис. ДАТЧИКИ")
    object PageUser: NavRoutesService("Сервис. ПОЛЬЗОВАТЕЛЬ")
}

@Preview
@Composable
fun ServiceContentPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ServiceContent(
        )
    }
}