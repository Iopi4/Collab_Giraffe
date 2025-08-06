package com.example.giraffenew.diagnostics.ui.crane

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
fun DiagnosticCraneContent(

) {val navController = rememberNavController()

    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    {
        NavBarDiagnosticCrane(navController = navController)
        NavHost(navController, startDestination = NavRoutesDiagnosticCrane.PageDiagnosticCraneState.route) {
            composable(NavRoutesDiagnosticCrane.PageDiagnosticCraneState.route) {DiagnosticCraneState()}
            composable(NavRoutesDiagnosticCrane.PageDiagnosticCraneDIDO.route) {DiagnosticCraneDIDO() }
        }
    }
}


@Composable
fun NavBarDiagnosticCrane(navController: NavController){

    val myBoxNavBarDiagnosticSlewingModifier = Modifier
        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = DarkGrew,
            shape = RoundedCornerShape(5.dp)
        )

    Column(myBoxNavBarDiagnosticSlewingModifier,
        verticalArrangement = Arrangement.SpaceEvenly)
    {
// Заголовок
        BasicSettingsText(modifier = Modifier,
            text = stringResource(R.string.Diagnostic_Text_Drive_Crane))

        //Код для формирования области в верху с кнопками переходов между страницами
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.Diagnostic_Button_Drive,
                    onClick = {
                        navController.navigate(NavRoutesDiagnosticCrane.PageDiagnosticCraneState.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.Diagnostic_Button_Drive_LI_DO,
                    onClick = {
                        navController.navigate(NavRoutesDiagnosticCrane.PageDiagnosticCraneDIDO.route) },) {}
            }
        }
    }
}


sealed class NavRoutesDiagnosticCrane (val route: String){
    object PageDiagnosticCraneState: NavRoutesDiagnosticCrane("Диагностика. Ход крана. Состояние")
    object PageDiagnosticCraneDIDO: NavRoutesDiagnosticCrane("Диагностика. Ход крана. Входа/выхода")
}

@Preview
@Composable
fun DiagnosticCranePreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        DiagnosticCraneContent(
        )
    }
}