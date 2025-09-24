package com.example.giraffenew.coordinate_protection.ui.polygon_4

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
import androidx.compose.ui.res.stringResource
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
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonTabGiraffe
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.R
import com.example.giraffenew.coordinate_protection.prezentation.CoordinateProtectionViewModule
import com.example.giraffenew.coordinate_protection.ui.polygon_3.Polygon3Setting
import com.example.giraffenew.coordinate_protection.ui.polygon_3.SettingPointsPolygon3Coordinate
import com.example.giraffenew.coordinate_protection.ui.polygon_3.SettingPointsPolygon3Hook
import com.example.giraffenew.coordinate_protection.ui.polygon_3.SettingPointsPolygon3Manual
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsText

@Composable
fun Polygon4Content(
    coordinateProtectionViewModule: CoordinateProtectionViewModule = hiltViewModel()
) {
    val state = coordinateProtectionViewModule.state.value

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

    val navController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    { NavBarPolygon4 (navController = navController)
        NavHost(navController, startDestination = NavRoutesPolygon4.PageSettingPolygon4.route){
            composable(NavRoutesPolygon4.PageSettingPolygon4.route)
            {
                Polygon4Setting(
                    onSetP4En = { coordinateProtectionViewModule.onSetP4En() },
                    onStartResetPolygon4 = { coordinateProtectionViewModule.startResetPolygon4() },

                    setValuePolygon4PointNum = coordinateProtectionViewModule.setValuePolygon4PointNum.value,
                    updateValuePolygon4PointNum = { coordinateProtectionViewModule.updateValuePolygon4PointNum(it) },
                    onSetValueP4PointNum = { coordinateProtectionViewModule.onSetValueP4PointNum() },
                    currentP4PointNum = coordinateProtectionViewModule.state.value.polygonState.currentP4PointNum,

                    setValuePolygon4Height = coordinateProtectionViewModule.setValuePolygon4Height.value,
                    updateValuePolygon4Height = { coordinateProtectionViewModule.updateValuePolygon4Height(it) },
                    onSetValueP4Height = { coordinateProtectionViewModule.onSetValueP4Height() },
                    currentP4Height = coordinateProtectionViewModule.state.value.polygonState.currentP4Height,

                    onSetP4UpEn = { coordinateProtectionViewModule.onSetP4UpEn() },
                    currentP4UpEn = coordinateProtectionViewModule.state.value.polygonState.currentP4Up,

                    onSetP4DownEn = { coordinateProtectionViewModule.onSetP4DownEn() },
                    currentP4DownEn = coordinateProtectionViewModule.state.value.polygonState.currentP4Down,

                    onSetP4Type1En = { coordinateProtectionViewModule.onSetP4Type1En() },
                    onSetP4Type2En = { coordinateProtectionViewModule.onSetP4Type2En() },
                    currentP4PointType = coordinateProtectionViewModule.state.value.polygonState.currentP4PointType,

                    currentP4FewPoint = coordinateProtectionViewModule.state.value.polygonState.currentP4FewPoint,
                    currentP4ActiveStatus = coordinateProtectionViewModule.state.value.polygonState.currentP4ActiveStatus,
                    currentStatsCalibration = coordinateProtectionViewModule.state.value.polygonState.currentStatsCalibration,

                    errorMessagePolygon4 = coordinateProtectionViewModule.errorMessagePolygon4.value,
                    logMessagePolygon4 = coordinateProtectionViewModule.logMessagePolygon4.value,
                    logMessagePolygon4Reset = coordinateProtectionViewModule.logMessagePolygon4Reset.value
                )
            }
            composable(NavRoutesPolygon4.PageSettingPointsPolygon4Trolley.route)
            {
                SettingPointsPolygon4Hook(
                    currentTrolleyPosition = coordinateProtectionViewModule.state.value.polygonState.currentTrolleyPosition,
                    currentSlewPosition = coordinateProtectionViewModule.state.value.polygonState.currentSlewPosition,
                    currentCranePosition = coordinateProtectionViewModule.state.value.polygonState.currentCranePosition,

                    onSetP4PointCmd = { index -> coordinateProtectionViewModule.onSetP4PointCmd(index) },

                    polygon4Point = state.polygon4Point,
                )
            }
            composable(NavRoutesPolygon4.PageSettingPointsPolygon4Manual.route)
            {
                SettingPointsPolygon4Manual(
                    setValuePolygon4TrolleyPos = coordinateProtectionViewModule.setValuePolygon4TrolleyPos.value,
                    updateValuePolygon4TrolleyPos = { coordinateProtectionViewModule.updateValuePolygon4TrolleyPos(it) },
                    onSetValuePolygon4TrolleyPos = { coordinateProtectionViewModule.onSetValuePolygon4TrolleyPos() },

                    setValuePolygon4SlewPos = coordinateProtectionViewModule.setValuePolygon4SlewPos.value,
                    updateValuePolygon4SlewPos = { coordinateProtectionViewModule.updateValuePolygon4SlewPos(it) },
                    onSetValuePolygon4SlewPos = { coordinateProtectionViewModule.onSetValuePolygon4SlewPos() },

                    setValuePolygon4CranePos = coordinateProtectionViewModule.setValuePolygon4CranePos.value,
                    updateValuePolygon4CranePos = { coordinateProtectionViewModule.updateValuePolygon4CranePos(it) },
                    onSetValuePolygon4CranePos = { coordinateProtectionViewModule.onSetValuePolygon4CranePos() },

                    onSetP4PointCmd = { index -> coordinateProtectionViewModule.onSetP4PointCmd(index) },

                    errorMessagePolygon4 = coordinateProtectionViewModule.errorMessagePolygon4Manual.value,
                    logMessagePolygon4 = coordinateProtectionViewModule.logMessagePolygon4Manual.value,

                    polygon4Point = state.polygon4Point,
                )
            }
            composable(NavRoutesPolygon4.PageSettingPointsPolygon4Coordinate.route) {
                SettingPointsPolygon4Coordinate(
                    setValuePolygon4PxPos = coordinateProtectionViewModule.setValuePolygon4PxPos.value,
                    updateValuePolygon4PxPos = { coordinateProtectionViewModule.updateValuePolygon4PxPos(it) },
                    onsetValuePolygon4PxPos = { coordinateProtectionViewModule.onsetValuePolygon4PxPos() },

                    setValuePolygon4PyPos = coordinateProtectionViewModule.setValuePolygon4PyPos.value,
                    updateValuePolygon4PyPos = { coordinateProtectionViewModule.updateValuePolygon4PyPos(it) },
                    onsetValuePolygon4PyPos = { coordinateProtectionViewModule.onsetValuePolygon4PyPos() },

                    onSetP4PointCmd = { index -> coordinateProtectionViewModule.onSetP4PointCmd(index) },

                    errorMessagePolygon4 = coordinateProtectionViewModule.errorMessagePolygon4Coordinate.value,
                    logMessagePolygon4 = coordinateProtectionViewModule.logMessagePolygon4Coordinate.value,

                    polygon4Point = state.polygon4Point,
                )
            }
        }
    }
}

@Composable
fun NavBarPolygon4(navController: NavController) {

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
        BasicSettingsText(modifier = Modifier,
            text = stringResource(R.string.CoordinateProtection_Text_Polygon_4))

        //Код для формирования области в верху с кнопками переходов между страницами (название и стрелки вверху экрана)
        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.CoordinateProtection_Button_Setting,
                    onClick = {
                        navController.navigate(NavRoutesPolygon4.PageSettingPolygon4.route)
                    },
                ) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.CoordinateProtection_Button_Position,
                    onClick = {
                        navController.navigate(NavRoutesPolygon4.PageSettingPointsPolygon4Trolley.route)
                    },
                ) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.CoordinateProtection_Button_Manual,
                    onClick = {
                        navController.navigate(NavRoutesPolygon4.PageSettingPointsPolygon4Manual.route)
                    },
                ) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier.padding(end = 7.dp),
                    text = R.string.CoordinateProtection_Button_Coordinate,
                    onClick = {
                        navController.navigate(NavRoutesPolygon4.PageSettingPointsPolygon4Coordinate.route)
                    },
                ) {}
            }
        }
    }
}

sealed class NavRoutesPolygon4 (val route: String) {
    object PageSettingPolygon4: NavRoutesPolygon4("Настройка. Полигон №4")
    object PageSettingPointsPolygon4Coordinate: NavRoutesPolygon4("Настройка точек по координатам. Полигон №4")
    object PageSettingPointsPolygon4Manual: NavRoutesPolygon4("Настройка точек в ручную. Полигон №4")
    object PageSettingPointsPolygon4Trolley: NavRoutesPolygon4("Настройка точек по тележке. Полигон №4")
}


@Preview
@Composable
fun Polygon3ContentPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        Polygon4Content(
        )
    }
}