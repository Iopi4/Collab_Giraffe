package com.example.giraffenew.coordinate_protection.ui.polygon_2

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
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonTabGiraffe
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsText

@Composable
fun Polygon2Content(
) {
    val navController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    { NavBarPolygon2 (navController = navController)
        NavHost(navController, startDestination = NavRoutesPolygon2.PageSettingPolygon2.route){
            composable(NavRoutesPolygon2.PageSettingPolygon2.route) { Polygon2() }
            composable(NavRoutesPolygon2.PageSettingPointsPolygon2Trolley.route) { SettingPointsPolygon2Trolley() }
            composable(NavRoutesPolygon2.PageSettingPointsPolygon2Manual.route) { SettingPointsPolygon2Manual() }
            composable(NavRoutesPolygon2.PageSettingPointsPolygon2Coordinate.route) { SettingPointsPolygon2Coordinate() }
        }
    }
}

@Composable
fun NavBarPolygon2(navController: NavController) {

    val myBoxNavBarPolygon1Modifier = Modifier
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = DarkGrew,
            shape = RoundedCornerShape(5.dp)
        )

    Column(myBoxNavBarPolygon1Modifier,
        verticalArrangement = Arrangement.SpaceEvenly)
    {
// Заголовок
        BasicSettingsText(
            modifier = Modifier,
            text = stringResource(R.string.CoordinateProtection_Text_Polygon_2)
        )

        //Код для формирования области в верху с кнопками переходов между страницами (название и стрелки вверху экрана)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.CoordinateProtection_Button_Setting,
                    onClick = {
                        navController.navigate(NavRoutesPolygon2.PageSettingPolygon2.route)
                    },
                ) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.CoordinateProtection_Button_Position,
                    onClick = {
                        navController.navigate(NavRoutesPolygon2.PageSettingPointsPolygon2Trolley.route)
                    },
                ) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.CoordinateProtection_Button_Manual,
                    onClick = {
                        navController.navigate(NavRoutesPolygon2.PageSettingPointsPolygon2Manual.route)
                    },
                ) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier.padding(end = 7.dp),
                    text = R.string.CoordinateProtection_Button_Coordinate,
                    onClick = {
                        navController.navigate(NavRoutesPolygon2.PageSettingPointsPolygon2Coordinate.route)
                    },
                ) {}
            }
        }

    }

}



sealed class NavRoutesPolygon2 (val route: String) {
    object PageSettingPolygon2: NavRoutesPolygon2("Настройка. Полигон №1")
    object PageSettingPointsPolygon2Coordinate: NavRoutesPolygon2("Настройка точек по координатам. Полигон №1")
    object PageSettingPointsPolygon2Manual: NavRoutesPolygon2("Настройка точек в ручную. Полигон №1")
    object PageSettingPointsPolygon2Trolley: NavRoutesPolygon2("Настройка точек по тележке. Полигон №1")
}


@Preview
@Composable
fun Polygon2ContentPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        Polygon2Content(
        )
    }
}