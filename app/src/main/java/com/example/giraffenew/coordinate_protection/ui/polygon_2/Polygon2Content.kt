package com.example.giraffenew.coordinate_protection.ui.polygon_2

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
fun Polygon2Content(
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
    { NavBarPolygon2 (navController = navController)
        NavHost(navController, startDestination = NavRoutesPolygon2.PageSettingPolygon2.route){
            composable(NavRoutesPolygon2.PageSettingPolygon2.route)
            { Polygon2Setting(
                onSetP2En = { coordinateProtectionViewModule.onSetP2En() },
                onStartResetPolygon2 = {coordinateProtectionViewModule.startResetPolygon2()},

                setValuePolygon2PointNum = coordinateProtectionViewModule.setValuePolygon2PointNum.value,
                updateValuePolygon2PointNum = { coordinateProtectionViewModule.updateValuePolygon2PointNum(it) },
                onSetValueP2PointNum = {coordinateProtectionViewModule.onSetValueP2PointNum()},
                currentP2PointNum = coordinateProtectionViewModule.state.value.polygonState.currentP2PointNum,

                setValuePolygon2Height = coordinateProtectionViewModule.setValuePolygon2Height.value,
                updateValuePolygon2Height = { coordinateProtectionViewModule.updateValuePolygon2Height(it) },
                onSetValueP2Height = {coordinateProtectionViewModule.onSetValueP2Height()},
                currentP2Height = coordinateProtectionViewModule.state.value.polygonState.currentP2Height,

                onSetP2UpEn = {coordinateProtectionViewModule.onSetP2UpEn()},
                currentP2UpEn = coordinateProtectionViewModule.state.value.polygonState.currentP2Up,

                onSetP2DownEn = {coordinateProtectionViewModule.onSetP2DownEn()},
                currentP2DownEn = coordinateProtectionViewModule.state.value.polygonState.currentP2Down,

                onSetP2Type1En = {coordinateProtectionViewModule.onSetP2Type1En()},
                onSetP2Type2En = {coordinateProtectionViewModule.onSetP2Type2En()},
                currentP2PointType = coordinateProtectionViewModule.state.value.polygonState.currentP2PointType,

                currentP2FewPoint = coordinateProtectionViewModule.state.value.polygonState.currentP2FewPoint,
                currentP2ActiveStatus = coordinateProtectionViewModule.state.value.polygonState.currentP2ActiveStatus,
                currentStatsCalibration = coordinateProtectionViewModule.state.value.polygonState.currentStatsCalibration,

                errorMessagePolygon2 = coordinateProtectionViewModule.errorMessagePolygon2.value,
                logMessagePolygon2 = coordinateProtectionViewModule.logMessagePolygon2.value,
                logMessagePolygon2Reset = coordinateProtectionViewModule.logMessagePolygon2Reset.value
            ) }
            composable(NavRoutesPolygon2.PageSettingPointsPolygon2Trolley.route)
            { SettingPointsPolygon2Hook(
                currentTrolleyPosition = coordinateProtectionViewModule.state.value.polygonState.currentTrolleyPosition,
                currentSlewPosition = coordinateProtectionViewModule.state.value.polygonState.currentSlewPosition,
                currentCranePosition = coordinateProtectionViewModule.state.value.polygonState.currentCranePosition,

                onSetP2PointCmd = { index -> coordinateProtectionViewModule.onSetP2PointCmd(index)},

                polygon2PointY01 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY01,
                polygon2PointX01 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX01,
                polygon2PointY02 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY02,
                polygon2PointX02 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX02,
                polygon2PointY03 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY03,
                polygon2PointX03 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX03,
                polygon2PointY04 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY04,
                polygon2PointX04 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX04,
                polygon2PointY05 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY05,
                polygon2PointX05 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX05,
                polygon2PointY06 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY06,
                polygon2PointX06 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX06,
                polygon2PointY07 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY07,
                polygon2PointX07 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX07,
                polygon2PointY08 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY08,
                polygon2PointX08 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX08,
                polygon2PointY09 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY09,
                polygon2PointX09 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX09,
                polygon2PointY10 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY10,
                polygon2PointX10 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX10,

                polygon2PointY11 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY11,
                polygon2PointX11 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX11,
                polygon2PointY12 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY12,
                polygon2PointX12 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX12,
                polygon2PointY13 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY13,
                polygon2PointX13 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX13,
                polygon2PointY14 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY14,
                polygon2PointX14 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX14,
                polygon2PointY15 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY15,
                polygon2PointX15 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX15,
                polygon2PointY16 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY16,
                polygon2PointX16 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX16,
                polygon2PointY17 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY17,
                polygon2PointX17 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX17,
                polygon2PointY18 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY18,
                polygon2PointX18 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX18,
                polygon2PointY19 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY19,
                polygon2PointX19 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX19,
                polygon2PointY20 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY20,
                polygon2PointX20 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX20,
            ) }
            composable(NavRoutesPolygon2.PageSettingPointsPolygon2Manual.route)
            { SettingPointsPolygon2Manual(
                setValuePolygon2TrolleyPos = coordinateProtectionViewModule.setValuePolygon2TrolleyPos.value,
                updateValuePolygon2TrolleyPos = { coordinateProtectionViewModule.updateValuePolygon2TrolleyPos(it)},
                onSetValuePolygon2TrolleyPos = { coordinateProtectionViewModule.onSetValuePolygon2TrolleyPos()},

                setValuePolygon2SlewPos = coordinateProtectionViewModule.setValuePolygon2SlewPos.value,
                updateValuePolygon2SlewPos = { coordinateProtectionViewModule.updateValuePolygon2SlewPos(it)},
                onSetValuePolygon2SlewPos = { coordinateProtectionViewModule.onSetValuePolygon2SlewPos()},

                setValuePolygon2CranePos = coordinateProtectionViewModule.setValuePolygon2CranePos.value,
                updateValuePolygon2CranePos = {coordinateProtectionViewModule.updateValuePolygon2CranePos(it)},
                onSetValuePolygon2CranePos = {coordinateProtectionViewModule.onSetValuePolygon2CranePos()},

                onSetP2PointCmd = { index -> coordinateProtectionViewModule.onSetP2PointCmd(index)},

                errorMessagePolygon2 = coordinateProtectionViewModule.errorMessagePolygon2Manual.value,
                logMessagePolygon2 = coordinateProtectionViewModule.logMessagePolygon2Manual.value,

                polygon2PointY01 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY01,
                polygon2PointX01 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX01,
                polygon2PointY02 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY02,
                polygon2PointX02 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX02,
                polygon2PointY03 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY03,
                polygon2PointX03 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX03,
                polygon2PointY04 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY04,
                polygon2PointX04 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX04,
                polygon2PointY05 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY05,
                polygon2PointX05 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX05,
                polygon2PointY06 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY06,
                polygon2PointX06 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX06,
                polygon2PointY07 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY07,
                polygon2PointX07 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX07,
                polygon2PointY08 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY08,
                polygon2PointX08 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX08,
                polygon2PointY09 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY09,
                polygon2PointX09 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX09,
                polygon2PointY10 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY10,
                polygon2PointX10 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX10,

                polygon2PointY11 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY11,
                polygon2PointX11 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX11,
                polygon2PointY12 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY12,
                polygon2PointX12 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX12,
                polygon2PointY13 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY13,
                polygon2PointX13 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX13,
                polygon2PointY14 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY14,
                polygon2PointX14 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX14,
                polygon2PointY15 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY15,
                polygon2PointX15 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX15,
                polygon2PointY16 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY16,
                polygon2PointX16 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX16,
                polygon2PointY17 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY17,
                polygon2PointX17 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX17,
                polygon2PointY18 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY18,
                polygon2PointX18 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX18,
                polygon2PointY19 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY19,
                polygon2PointX19 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX19,
                polygon2PointY20 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY20,
                polygon2PointX20 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX20,
            ) }
            composable(NavRoutesPolygon2.PageSettingPointsPolygon2Coordinate.route)
            { SettingPointsPolygon2Coordinate(
                setValuePolygon2PxPos = coordinateProtectionViewModule.setValuePolygon2PxPos.value,
                updateValuePolygon2PxPos = { coordinateProtectionViewModule.updateValuePolygon2PxPos(it)},
                onsetValuePolygon2PxPos = { coordinateProtectionViewModule.onsetValuePolygon2PxPos()},

                setValuePolygon2PyPos = coordinateProtectionViewModule.setValuePolygon2PyPos.value,
                updateValuePolygon2PyPos = { coordinateProtectionViewModule.updateValuePolygon2PyPos(it) },
                onsetValuePolygon2PyPos = { coordinateProtectionViewModule.onsetValuePolygon2PyPos() },

                onSetP2PointCmd = { index -> coordinateProtectionViewModule.onSetP2PointCmd(index)},

                errorMessagePolygon2 = coordinateProtectionViewModule.errorMessagePolygon2Coordinate.value,
                logMessagePolygon2 = coordinateProtectionViewModule.logMessagePolygon2Coordinate.value,

                polygon2PointY01 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY01,
                polygon2PointX01 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX01,
                polygon2PointY02 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY02,
                polygon2PointX02 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX02,
                polygon2PointY03 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY03,
                polygon2PointX03 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX03,
                polygon2PointY04 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY04,
                polygon2PointX04 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX04,
                polygon2PointY05 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY05,
                polygon2PointX05 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX05,
                polygon2PointY06 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY06,
                polygon2PointX06 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX06,
                polygon2PointY07 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY07,
                polygon2PointX07 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX07,
                polygon2PointY08 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY08,
                polygon2PointX08 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX08,
                polygon2PointY09 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY09,
                polygon2PointX09 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX09,
                polygon2PointY10 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY10,
                polygon2PointX10 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX10,

                polygon2PointY11 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY11,
                polygon2PointX11 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX11,
                polygon2PointY12 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY12,
                polygon2PointX12 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX12,
                polygon2PointY13 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY13,
                polygon2PointX13 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX13,
                polygon2PointY14 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY14,
                polygon2PointX14 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX14,
                polygon2PointY15 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY15,
                polygon2PointX15 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX15,
                polygon2PointY16 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY16,
                polygon2PointX16 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX16,
                polygon2PointY17 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY17,
                polygon2PointX17 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX17,
                polygon2PointY18 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY18,
                polygon2PointX18 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX18,
                polygon2PointY19 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY19,
                polygon2PointX19 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX19,
                polygon2PointY20 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointY20,
                polygon2PointX20 = coordinateProtectionViewModule.state.value.polygon2Point.polygon2PointX20,
            ) }
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