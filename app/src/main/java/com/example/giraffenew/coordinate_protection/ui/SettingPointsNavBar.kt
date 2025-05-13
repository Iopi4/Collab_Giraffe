package com.example.giraffenew.coordinate_protection.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.giraffenew.core.ui_kit.composable.BasicFilledButtonGiraffe
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun SettingPointsNavBar(
) {
val navController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    {NavBarSettingPoints(navController = navController)

    }

}

@Composable
fun NavBarSettingPoints (navController: NavController){
    Column(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 0.dp)
        .fillMaxWidth())
    {
        //Кнопка перехода в меню задания точек полигона. Перемещение крюка в требуемое положение
        Box(modifier = Modifier) {
            BasicFilledButtonGiraffe(
                modifier = Modifier,
                text = ">>Position>>",
                onClick = {
                    navController.navigate(NavRoutesPoints.PagePolygon1PointsTrolley.route)
                },
            ) {}
        }
        //Кнопка перехода в меню задания точек полигона. Ввод абсолютных координат
        Box(modifier = Modifier) {
            BasicFilledButtonGiraffe(
                modifier = Modifier,
                text = ">>Coordinate>>",
                onClick = {
                    navController.navigate(NavRoutesPoints.PagePolygon1PointsCoordinate.route)
                },
            ) {}
        }
        //Кнопка перехода в меню задания точек полигона. Ввод требуемого положения крюка
        Box(modifier = Modifier) {
            BasicFilledButtonGiraffe(
                modifier = Modifier,
                text = ">>Manual>>",
                onClick = {
                    navController.navigate(NavRoutesPoints.PagePolygon1PointsManual.route)
                },
            ) {}
        }

    }
}


sealed class NavRoutesPoints (val route: String) {
    object PagePolygon1PointsTrolley: NavRoutesCoordinateProtection("Точки полигона №1. Стрела")
    object PagePolygon1PointsManual: NavRoutesCoordinateProtection("Точки полигона №1. Ручной ввод")
    object PagePolygon1PointsCoordinate: NavRoutesCoordinateProtection("Точки полигона №1. Координаты")
    object PagePolygon2PointsTrolley: NavRoutesCoordinateProtection("Точки полигона №2. Стрела")
    object PagePolygon2PointsManual: NavRoutesCoordinateProtection("Точки полигона №2. Ручной ввод")
    object PagePolygon2PointsCoordinate: NavRoutesCoordinateProtection("Точки полигона №2. Координаты")
}


@Preview
@Composable
fun SettingPointsContentPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        SettingPointsNavBar(
        )
    }
}