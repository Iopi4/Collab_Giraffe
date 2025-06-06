package com.example.giraffenew.diagnostics.ui.trolley

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
import com.example.giraffenew.core.ui_kit.composable.BasicSettingsText
import com.example.giraffenew.core.ui_kit.composable.BasicButtonTabGiraffe
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun DiagnosticTrolleyContent()
{
    val navController = rememberNavController()

    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    {
        NavBarDiagnosticTrolley(navController = navController)
        NavHost(navController, startDestination = NavRoutesDiagnosticTrolley.PageDiagnosticTrolleyState.route) {
            composable(NavRoutesDiagnosticTrolley.PageDiagnosticTrolleyState.route) {DiagnosticTrolleyState()}
            composable(NavRoutesDiagnosticTrolley.PageDiagnosticTrolleyDIDO.route) {DiagnosticTrolleyDIDO() }
        }
    }
}


@Composable
fun NavBarDiagnosticTrolley(navController: NavController){

    val myBoxNavBarDiagnosticTrolleyModifier = Modifier
        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = DarkGrew,
            shape = RoundedCornerShape(5.dp)
        )

    Column(myBoxNavBarDiagnosticTrolleyModifier,
        verticalArrangement = Arrangement.SpaceEvenly)
    {
// Заголовок
        BasicSettingsText(modifier = Modifier,
            text = stringResource(R.string.Diagnostic_Text_Drive_Trolley))

        //Код для формирования области в верху с кнопками переходов между страницами
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.Diagnostic_Button_Drive,
                    onClick = {
                        navController.navigate(NavRoutesDiagnosticTrolley.PageDiagnosticTrolleyState.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.Diagnostic_Button_Drive_LI_DO,
                    onClick = {
                        navController.navigate(NavRoutesDiagnosticTrolley.PageDiagnosticTrolleyDIDO.route) },) {}
            }
        }
    }
}


sealed class NavRoutesDiagnosticTrolley (val route: String){
    object PageDiagnosticTrolleyState: NavRoutesDiagnosticTrolley("Диагностика. Поворот. Состояние")
    object PageDiagnosticTrolleyDIDO: NavRoutesDiagnosticTrolley("Диагностика. Поворот. Входа/выхода")
}


@Preview
@Composable
fun DiagnosticTrolleyPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        DiagnosticTrolleyContent(
        )
    }
}