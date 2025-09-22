package com.example.giraffenew.coordinate_protection.prezentation

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.giraffenew.R
import com.example.giraffenew.coordinate_protection.ui.CoordinateProtectionSetting
import com.example.giraffenew.coordinate_protection.ui.polygon_1.Polygon1Content
import com.example.giraffenew.coordinate_protection.ui.polygon_2.Polygon2Content
import com.example.giraffenew.coordinate_protection.ui.polygon_3.Polygon3Content
import com.example.giraffenew.coordinate_protection.ui.polygon_4.Polygon4Content
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonTabGiraffe
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun CoordinateProtectionScreen (
    coordinateProtectionViewModule: CoordinateProtectionViewModule = hiltViewModel()
){
    // 1. Получаем текущий LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current

    // 2. Создаем Lifecycle-aware эффект
    DisposableEffect(lifecycleOwner, coordinateProtectionViewModule) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("CoordinateProtectionScreen", "Lifecycle: ON_RESUME- resuming data collection")
                    coordinateProtectionViewModule.setActiveConfiguration()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("CoordinateProtectionScreen", "Lifecycle: ON_PAUSE - pausing data collection")
                    coordinateProtectionViewModule.clearActiveConfiguration()
                }
                else -> {Log.e("CoordinateProtectionScreen", "Lifecycle: NOT START")}
            }
        }

        // 3. Регистрируем наблюдатель
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        // 4. Очистка при удалении Composable
        onDispose {
            Log.d("CoordinateProtectionScreen", "Lifecycle: ON_DISPOSE - removing observer")
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            coordinateProtectionViewModule.clearActiveConfiguration() // Дополнительная очистка
        }
    }


    BasicSettingBackground()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier = Modifier.weight(1f)) {
            CoordinateProtectionSetting(
                onSetPosProtEn = {coordinateProtectionViewModule.onSetPosProtEn()},
                currentPosProtEn = coordinateProtectionViewModule.state.value.polygonState.currentPosProtEn,
                onSetModePosProt = {coordinateProtectionViewModule.onSetModePosProt()},
                currentModePosProt = coordinateProtectionViewModule.state.value.polygonState.currentModePosProt
            )
        }
        Box(modifier = Modifier.weight(7f)) {
            val navController = rememberNavController()
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly)
            { NavBarCoordinateProtection (navController = navController)
                NavHost(navController, startDestination = NavRoutesCoordinateProtection.PagePolygon1.route){
                    composable(NavRoutesCoordinateProtection.PagePolygon1.route) { Polygon1Content() }
                    composable(NavRoutesCoordinateProtection.PagePolygon2.route) { Polygon2Content() }
                    composable(NavRoutesCoordinateProtection.PagePolygon3.route) { Polygon3Content() }
                    composable(NavRoutesCoordinateProtection.PagePolygon4.route) { Polygon4Content() }

                }
            }
        }
    }
}

@Composable
fun NavBarCoordinateProtection(navController: NavController) {
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
                    text = R.string.CoordinateProtection_Button_Polygon_1,
                    onClick = {
                        navController.navigate(NavRoutesCoordinateProtection.PagePolygon1.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.CoordinateProtection_Button_Polygon_2,
                    onClick = {
                        navController.navigate(NavRoutesCoordinateProtection.PagePolygon2.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.CoordinateProtection_Button_Polygon_3,
                    onClick = {
                        navController.navigate(NavRoutesCoordinateProtection.PagePolygon3.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.CoordinateProtection_Button_Polygon_4,
                    onClick = {
                        navController.navigate(NavRoutesCoordinateProtection.PagePolygon4.route) },) {}
            }
        }
    }
}


sealed class NavRoutesCoordinateProtection (val route: String) {
    object PagePolygon1: NavRoutesCoordinateProtection("Полигон №1")
    object PagePolygon2: NavRoutesCoordinateProtection("Полигон №2")
    object PagePolygon3: NavRoutesCoordinateProtection("Полигон №3")
    object PagePolygon4: NavRoutesCoordinateProtection("Полигон №4")

}

@Preview
@Composable
fun CoordinateProtectionScreenPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        CoordinateProtectionScreen(
        )
    }
}