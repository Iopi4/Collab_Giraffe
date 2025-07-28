package com.example.giraffenew.coordinate_protection.ui.polygon_1

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
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonTabGiraffe
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.R

@Composable
fun Polygon1Content(
) {
    val navController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    { NavBarPolygon1 (navController = navController)
        NavHost(navController, startDestination = NavRoutesPolygon1.PageSettingPolygon1.route){
            composable(NavRoutesPolygon1.PageSettingPolygon1.route) { Polygon1Setting() }
            composable(NavRoutesPolygon1.PageSettingPointsPolygon1Trolley.route) { SettingPointsPolygon1Trolley() }
            composable(NavRoutesPolygon1.PageSettingPointsPolygon1Manual.route) { SettingPointsPolygon1Manual() }
            composable(NavRoutesPolygon1.PageSettingPointsPolygon1Coordinate.route) { SettingPointsPolygon1Coordinate()}
        }
    }
}

@Composable
fun NavBarPolygon1(navController: NavController) {

    val myBoxNavBarPolygon1Modifier = Modifier
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = DarkGrew,
            shape = RoundedCornerShape(5.dp)
        )

    //Код для формирования области в верху с кнопками переходов между страницами (название и стрелки вверху экрана)
    Row (myBoxNavBarPolygon1Modifier,
        horizontalArrangement = Arrangement.SpaceBetween) {

        Box(modifier = Modifier.weight(1f)) {
            BasicButtonTabGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Setting,
                onClick = {
                    navController.navigate(NavRoutesPolygon1.PageSettingPolygon1.route)
                },
            ) {}
        }
        Box(modifier = Modifier.weight(1f)) {
            BasicButtonTabGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Position,
                onClick = {
                    navController.navigate(NavRoutesPolygon1.PageSettingPointsPolygon1Trolley.route)
                },
            ) {}
        }
        Box(modifier = Modifier.weight(1f)) {
            BasicButtonTabGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Manual,
                onClick = {
                    navController.navigate(NavRoutesPolygon1.PageSettingPointsPolygon1Manual.route)
                },
            ) {}
        }
        Box(modifier = Modifier.weight(1f)) {
            BasicButtonTabGiraffe(
                modifier = Modifier.padding(end = 7.dp),
                text = R.string.CoordinateProtection_Button_Coordinate,
                onClick = {
                    navController.navigate(NavRoutesPolygon1.PageSettingPointsPolygon1Coordinate.route)
                },
            ) {}
        }
    }

}



sealed class NavRoutesPolygon1 (val route: String) {
    object PageSettingPolygon1: NavRoutesPolygon1("Настройка. Полигон №1")
    object PageSettingPointsPolygon1Coordinate: NavRoutesPolygon1("Настройка точек по координатам. Полигон №1")
    object PageSettingPointsPolygon1Manual: NavRoutesPolygon1("Настройка точек в ручную. Полигон №1")
    object PageSettingPointsPolygon1Trolley: NavRoutesPolygon1("Настройка точек по тележке. Полигон №1")
}


@Preview
@Composable
fun Polygon1ContentPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        Polygon1Content(
        )
    }
}