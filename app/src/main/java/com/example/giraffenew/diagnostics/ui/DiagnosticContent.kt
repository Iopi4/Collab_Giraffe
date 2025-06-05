package com.example.giraffenew.diagnostics.ui

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
import com.example.giraffenew.core.ui_kit.composable.BasicTabButtonGiraffe
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun DiagnosticContent(
) {
    val navController = rememberNavController()

    Column (modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    { NavBarDiagnostic (navController = navController)
        NavHost(navController, startDestination = NavRoutesDiagnostic.PageDiagnosticSystem.roote){
            composable(NavRoutesDiagnostic.PageDiagnosticSystem.roote) { DiagnosticSystem() }
            composable(NavRoutesDiagnostic.PageDiagnosticPLC.roote) { DiagnosticPLC() }
            composable(NavRoutesDiagnostic.PageDiagnosticHoist.roote) { DiagnosticHoist() }
            composable(NavRoutesDiagnostic.PageDiagnosticSlewing.roote) { DiagnosticSlewing() }
            composable(NavRoutesDiagnostic.PageDiagnosticTrolley.roote) { DiagnosticTrolley() }
            composable(NavRoutesDiagnostic.PageDiagnosticCrane.roote) { DiagnosticCrane() }
        }
    }


}

@Composable
fun NavBarDiagnostic(navController: NavController) {

    val myBoxNavBarDiagnosticModifier = Modifier
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = DarkGrew,
            shape = RoundedCornerShape(5.dp)
        )

//Код для формирования области в верху с кнопками переходов между страницами
    Column (myBoxNavBarDiagnosticModifier,
        verticalArrangement = Arrangement.SpaceEvenly)
    {

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicTabButtonGiraffe(
                    modifier = Modifier,
                    text = "СИСТЕМА",
                    onClick = {
                        navController.navigate(NavRoutesDiagnostic.PageDiagnosticSystem.roote) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicTabButtonGiraffe(
                    modifier = Modifier,
                    text = "КОНТРОЛЛЕР",
                    onClick = {
                        navController.navigate(NavRoutesDiagnostic.PageDiagnosticPLC.roote) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicTabButtonGiraffe(
                    modifier = Modifier.padding(end = 7.dp),
                    text = "ПОДЪЕМ",
                    onClick = {
                        navController.navigate(NavRoutesDiagnostic.PageDiagnosticHoist.roote) },) {}
            }
        }

        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicTabButtonGiraffe(
                    modifier = Modifier,
                    text = "ПОВОРОТ",
                    onClick = {
                        navController.navigate(NavRoutesDiagnostic.PageDiagnosticSlewing.roote) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicTabButtonGiraffe(
                    modifier = Modifier,
                    text = "ТЕЛЕЖКА",
                    onClick = {
                        navController.navigate(NavRoutesDiagnostic.PageDiagnosticTrolley.roote) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicTabButtonGiraffe(
                    modifier = Modifier.padding(end = 7.dp),
                    text = "ХОД КРАНА",
                    onClick = {
                        navController.navigate(NavRoutesDiagnostic.PageDiagnosticCrane.roote) },) {}
            }
        }


    }



}

sealed class NavRoutesDiagnostic (val roote: String){
    object PageDiagnosticSystem: NavRoutesDiagnostic("Диагностика. Система")
    object PageDiagnosticPLC: NavRoutesDiagnostic("Диагностика. ПЛК")
    object PageDiagnosticHoist: NavRoutesDiagnostic("Диагностика. Привод подъема")
    object PageDiagnosticSlewing: NavRoutesDiagnostic("Диагностика.Привод поворота")
    object PageDiagnosticTrolley: NavRoutesDiagnostic("Диагностика. Привод тележки")
    object PageDiagnosticCrane: NavRoutesDiagnostic("Диагностика. Привод хода крана")
}

@Preview
@Composable
fun DiagnosticContentPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        DiagnosticContent(
        )
    }
}