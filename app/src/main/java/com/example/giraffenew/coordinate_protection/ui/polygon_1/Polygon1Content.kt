package com.example.giraffenew.coordinate_protection.ui.polygon_1

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
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsText

@Composable
fun Polygon1Content(
    coordinateProtectionViewModule: CoordinateProtectionViewModule = hiltViewModel()
) {
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
    { NavBarPolygon1 (navController = navController)
        NavHost(navController, startDestination = NavRoutesPolygon1.PageSettingPolygon1.route){
            composable(NavRoutesPolygon1.PageSettingPolygon1.route)
            { Polygon1Setting(
                onSetP1En = { coordinateProtectionViewModule.onSetP1En() },
                onStartResetPolygon1 = {coordinateProtectionViewModule.startResetPolygon1()},

                setValuePolygon1PointNum = coordinateProtectionViewModule.setValuePolygon1PointNum.value,
                updateValuePolygon1PointNum = { coordinateProtectionViewModule.updateValuePolygon1PointNum(it) },
                onSetValueP1PointNum = {coordinateProtectionViewModule.onSetValueP1PointNum()},
                currentP1PointNum = coordinateProtectionViewModule.state.value.polygonState.currentP1PointNum,

                setValuePolygon1Height = coordinateProtectionViewModule.setValuePolygon1Height.value,
                updateValuePolygon1Height = { coordinateProtectionViewModule.updateValuePolygon1Height(it) },
                onSetValueP1Height =  {coordinateProtectionViewModule.onSetValueP1Height()},
                currentP1Height = coordinateProtectionViewModule.state.value.polygonState.currentP1Height,

                onSetP1UpEn =  {coordinateProtectionViewModule.onSetP1UpEn()},
                currentP1UpEn = coordinateProtectionViewModule.state.value.polygonState.currentP1Up,

                onSetP1DownEn =  {coordinateProtectionViewModule.onSetP1DownEn()},
                currentP1DownEn = coordinateProtectionViewModule.state.value.polygonState.currentP1Down,

                onSetP1Type1En =  {coordinateProtectionViewModule.onSetP1Type1En()},
                onSetP1Type2En = {coordinateProtectionViewModule.onSetP1Type2En()},
                currentP1PointType = coordinateProtectionViewModule.state.value.polygonState.currentP1PointType,

                currentP1FewPoint = coordinateProtectionViewModule.state.value.polygonState.currentP1FewPoint,
                currentP1ActiveStatus = coordinateProtectionViewModule.state.value.polygonState.currentP1ActiveStatus,
                currentStatsCalibration = coordinateProtectionViewModule.state.value.polygonState.currentStatsCalibration,

                errorMessagePolygon1 = coordinateProtectionViewModule.errorMessagePolygon1.value,
                logMessagePolygon1 = coordinateProtectionViewModule.logMessagePolygon1.value,
                logMessagePolygon1Reset = coordinateProtectionViewModule.logMessagePolygon1Reset.value
            ) }
            composable(NavRoutesPolygon1.PageSettingPointsPolygon1Trolley.route) 
            { SettingPointsPolygon1Hook(
                currentTrolleyPosition = coordinateProtectionViewModule.state.value.polygonState.currentTrolleyPosition,
                currentSlewPosition = coordinateProtectionViewModule.state.value.polygonState.currentSlewPosition,
                currentCranePosition = coordinateProtectionViewModule.state.value.polygonState.currentCranePosition,

                onSetP1PointCmd = { index -> coordinateProtectionViewModule.onSetP1PointCmd(index)},

                polygon1PointY01 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY01,
                polygon1PointX01 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX01,
                polygon1PointY02 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY02,
                polygon1PointX02 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX02,
                polygon1PointY03 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY03,
                polygon1PointX03 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX03,
                polygon1PointY04 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY04,
                polygon1PointX04 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX04,
                polygon1PointY05 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY05,
                polygon1PointX05 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX05,
                polygon1PointY06 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY06,
                polygon1PointX06 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX06,
                polygon1PointY07 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY07,
                polygon1PointX07 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX07,
                polygon1PointY08 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY08,
                polygon1PointX08 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX08,
                polygon1PointY09 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY09,
                polygon1PointX09 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX09,
                polygon1PointY10 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY10,
                polygon1PointX10 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX10,
                polygon1PointY11 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY11,
                polygon1PointX11 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX11,
                polygon1PointY12 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY12,
                polygon1PointX12 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX12,
                polygon1PointY13 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY13,
                polygon1PointX13 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX13,
                polygon1PointY14 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY14,
                polygon1PointX14 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX14,
                polygon1PointY15 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY15,
                polygon1PointX15 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX15,
                polygon1PointY16 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY16,
                polygon1PointX16 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX16,
                polygon1PointY17 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY17,
                polygon1PointX17 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX17,
                polygon1PointY18 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY18,
                polygon1PointX18 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX18,
                polygon1PointY19 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY19,
                polygon1PointX19 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX19,
                polygon1PointY20 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY20,
                polygon1PointX20 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX20,
            ) }
            composable(NavRoutesPolygon1.PageSettingPointsPolygon1Manual.route)
            { SettingPointsPolygon1Manual(
                setValuePolygon1TrolleyPos= coordinateProtectionViewModule.setValuePolygon1TrolleyPos.value,
                updateValuePolygon1TrolleyPos = { coordinateProtectionViewModule.updateValuePolygon1TrolleyPos(it)},
                onSetValuePolygon1TrolleyPos = { coordinateProtectionViewModule.onSetValuePolygon1TrolleyPos()},

                setValuePolygon1SlewPos = coordinateProtectionViewModule.setValuePolygon1SlewPos.value,
                updateValuePolygon1SlewPos = { coordinateProtectionViewModule.updateValuePolygon1SlewPos(it)},
                onSetValuePolygon1SlewPos = { coordinateProtectionViewModule.onSetValuePolygon1SlewPos()},

                setValuePolygon1CranePos = coordinateProtectionViewModule.setValuePolygon1CranePos.value,
                updateValuePolygon1CranePos = {coordinateProtectionViewModule.updateValuePolygon1CranePos(it)},
                onSetValuePolygon1CranePos = {coordinateProtectionViewModule.onSetValuePolygon1CranePos()},

                onSetP1PointCmd = { index -> coordinateProtectionViewModule.onSetP1PointCmd(index)},

                errorMessagePolygon1 = coordinateProtectionViewModule.errorMessagePolygon1Manual.value,
                logMessagePolygon1 = coordinateProtectionViewModule.logMessagePolygon1Manual.value,

                polygon1PointY01 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY01,
                polygon1PointX01 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX01,
                polygon1PointY02 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY02,
                polygon1PointX02 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX02,
                polygon1PointY03 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY03,
                polygon1PointX03 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX03,
                polygon1PointY04 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY04,
                polygon1PointX04 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX04,
                polygon1PointY05 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY05,
                polygon1PointX05 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX05,
                polygon1PointY06 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY06,
                polygon1PointX06 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX06,
                polygon1PointY07 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY07,
                polygon1PointX07 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX07,
                polygon1PointY08 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY08,
                polygon1PointX08 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX08,
                polygon1PointY09 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY09,
                polygon1PointX09 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX09,
                polygon1PointY10 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY10,
                polygon1PointX10 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX10,
                polygon1PointY11 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY11,
                polygon1PointX11 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX11,
                polygon1PointY12 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY12,
                polygon1PointX12 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX12,
                polygon1PointY13 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY13,
                polygon1PointX13 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX13,
                polygon1PointY14 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY14,
                polygon1PointX14 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX14,
                polygon1PointY15 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY15,
                polygon1PointX15 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX15,
                polygon1PointY16 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY16,
                polygon1PointX16 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX16,
                polygon1PointY17 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY17,
                polygon1PointX17 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX17,
                polygon1PointY18 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY18,
                polygon1PointX18 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX18,
                polygon1PointY19 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY19,
                polygon1PointX19 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX19,
                polygon1PointY20 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY20,
                polygon1PointX20 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX20,
            ) }
            composable(NavRoutesPolygon1.PageSettingPointsPolygon1Coordinate.route) {
                SettingPointsPolygon1Coordinate(
                    setValuePolygon1PxPos = coordinateProtectionViewModule.setValuePolygon1PxPos.value,
                    updateValuePolygon1PxPos = { coordinateProtectionViewModule.updateValuePolygon1PxPos(it)},
                    onsetValuePolygon1PxPos = { coordinateProtectionViewModule.onsetValuePolygon1PxPos()},

                    setValuePolygon1PyPos = coordinateProtectionViewModule.setValuePolygon1PyPos.value,
                    updateValuePolygon1PyPos = { coordinateProtectionViewModule.updateValuePolygon1PyPos(it) },
                    onsetValuePolygon1PyPos = { coordinateProtectionViewModule.onsetValuePolygon1PyPos() },

                    onSetP1PointCmd = { index -> coordinateProtectionViewModule.onSetP1PointCmd(index)},

                    errorMessagePolygon1 = coordinateProtectionViewModule.errorMessagePolygon1Coordinate.value,
                    logMessagePolygon1 = coordinateProtectionViewModule.logMessagePolygon1Coordinate.value,

                    polygon1PointY01 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY01,
                    polygon1PointX01 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX01,
                    polygon1PointY02 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY02,
                    polygon1PointX02 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX02,
                    polygon1PointY03 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY03,
                    polygon1PointX03 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX03,
                    polygon1PointY04 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY04,
                    polygon1PointX04 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX04,
                    polygon1PointY05 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY05,
                    polygon1PointX05 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX05,
                    polygon1PointY06 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY06,
                    polygon1PointX06 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX06,
                    polygon1PointY07 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY07,
                    polygon1PointX07 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX07,
                    polygon1PointY08 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY08,
                    polygon1PointX08 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX08,
                    polygon1PointY09 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY09,
                    polygon1PointX09 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX09,
                    polygon1PointY10 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY10,
                    polygon1PointX10 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX10,
                    polygon1PointY11 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY11,
                    polygon1PointX11 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX11,
                    polygon1PointY12 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY12,
                    polygon1PointX12 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX12,
                    polygon1PointY13 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY13,
                    polygon1PointX13 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX13,
                    polygon1PointY14 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY14,
                    polygon1PointX14 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX14,
                    polygon1PointY15 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY15,
                    polygon1PointX15 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX15,
                    polygon1PointY16 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY16,
                    polygon1PointX16 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX16,
                    polygon1PointY17 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY17,
                    polygon1PointX17 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX17,
                    polygon1PointY18 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY18,
                    polygon1PointX18 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX18,
                    polygon1PointY19 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY19,
                    polygon1PointX19 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX19,
                    polygon1PointY20 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointY20,
                    polygon1PointX20 = coordinateProtectionViewModule.state.value.polygon1Point.polygon1PointX20,
                )}
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

    Column(myBoxNavBarPolygon1Modifier,
        verticalArrangement = Arrangement.SpaceEvenly)
    {
// Заголовок
        BasicSettingsText(modifier = Modifier,
            text = stringResource(R.string.CoordinateProtection_Text_Polygon_1))

        //Код для формирования области в верху с кнопками переходов между страницами (название и стрелки вверху экрана)
        Row (modifier = Modifier.fillMaxWidth(),
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