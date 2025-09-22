package com.example.giraffenew.coordinate_protection.ui.polygon_3

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
import com.example.giraffenew.coordinate_protection.ui.polygon_1.Polygon1Setting
import com.example.giraffenew.coordinate_protection.ui.polygon_1.SettingPointsPolygon1Coordinate
import com.example.giraffenew.coordinate_protection.ui.polygon_1.SettingPointsPolygon1Hook
import com.example.giraffenew.coordinate_protection.ui.polygon_1.SettingPointsPolygon1Manual
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsText

@Composable
fun Polygon3Content(
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
    { NavBarPolygon3 (navController = navController)
        NavHost(navController, startDestination = NavRoutesPolygon3.PageSettingPolygon3.route){
            composable(NavRoutesPolygon3.PageSettingPolygon3.route)
            {
                Polygon3Setting(
                    onSetP3En = { coordinateProtectionViewModule.onSetP3En() },
                    onStartResetPolygon3 = { coordinateProtectionViewModule.startResetPolygon3() },

                    setValuePolygon3PointNum = coordinateProtectionViewModule.setValuePolygon3PointNum.value,
                    updateValuePolygon3PointNum = { coordinateProtectionViewModule.updateValuePolygon3PointNum(it) },
                    onSetValueP3PointNum = { coordinateProtectionViewModule.onSetValueP3PointNum() },
                    currentP3PointNum = coordinateProtectionViewModule.state.value.polygonState.currentP3PointNum,

                    setValuePolygon3Height = coordinateProtectionViewModule.setValuePolygon3Height.value,
                    updateValuePolygon3Height = { coordinateProtectionViewModule.updateValuePolygon3Height(it) },
                    onSetValueP3Height = { coordinateProtectionViewModule.onSetValueP3Height() },
                    currentP3Height = coordinateProtectionViewModule.state.value.polygonState.currentP3Height,

                    onSetP3UpEn = { coordinateProtectionViewModule.onSetP3UpEn() },
                    currentP3UpEn = coordinateProtectionViewModule.state.value.polygonState.currentP3Up,

                    onSetP3DownEn = { coordinateProtectionViewModule.onSetP3DownEn() },
                    currentP3DownEn = coordinateProtectionViewModule.state.value.polygonState.currentP3Down,

                    onSetP3Type1En = { coordinateProtectionViewModule.onSetP3Type1En() },
                    onSetP3Type2En = { coordinateProtectionViewModule.onSetP3Type2En() },
                    currentP3PointType = coordinateProtectionViewModule.state.value.polygonState.currentP3PointType,

                    currentP3FewPoint = coordinateProtectionViewModule.state.value.polygonState.currentP3FewPoint,
                    currentP3ActiveStatus = coordinateProtectionViewModule.state.value.polygonState.currentP3ActiveStatus,
                    currentStatsCalibration = coordinateProtectionViewModule.state.value.polygonState.currentStatsCalibration,

                    errorMessagePolygon3 = coordinateProtectionViewModule.errorMessagePolygon3.value,
                    logMessagePolygon3 = coordinateProtectionViewModule.logMessagePolygon3.value,
                    logMessagePolygon3Reset = coordinateProtectionViewModule.logMessagePolygon3Reset.value
                )
            }
            composable(NavRoutesPolygon3.PageSettingPointsPolygon3Trolley.route)
            {
                SettingPointsPolygon3Hook(
                    currentTrolleyPosition = coordinateProtectionViewModule.state.value.polygonState.currentTrolleyPosition,
                    currentSlewPosition = coordinateProtectionViewModule.state.value.polygonState.currentSlewPosition,
                    currentCranePosition = coordinateProtectionViewModule.state.value.polygonState.currentCranePosition,

                    onSetP3PointCmd = { index -> coordinateProtectionViewModule.onSetP3PointCmd(index) },

                    polygon3PointY01 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY01,
                    polygon3PointX01 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX01,
                    polygon3PointY02 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY02,
                    polygon3PointX02 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX02,
                    polygon3PointY03 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY03,
                    polygon3PointX03 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX03,
                    polygon3PointY04 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY04,
                    polygon3PointX04 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX04,
                    polygon3PointY05 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY05,
                    polygon3PointX05 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX05,
                    polygon3PointY06 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY06,
                    polygon3PointX06 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX06,
                    polygon3PointY07 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY07,
                    polygon3PointX07 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX07,
                    polygon3PointY08 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY08,
                    polygon3PointX08 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX08,
                    polygon3PointY09 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY09,
                    polygon3PointX09 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX09,
                    polygon3PointY10 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY10,
                    polygon3PointX10 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX10,
                    polygon3PointY11 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY11,
                    polygon3PointX11 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX11,
                    polygon3PointY12 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY12,
                    polygon3PointX12 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX12,
                    polygon3PointY13 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY13,
                    polygon3PointX13 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX13,
                    polygon3PointY14 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY14,
                    polygon3PointX14 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX14,
                    polygon3PointY15 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY15,
                    polygon3PointX15 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX15,
                    polygon3PointY16 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY16,
                    polygon3PointX16 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX16,
                    polygon3PointY17 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY17,
                    polygon3PointX17 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX17,
                    polygon3PointY18 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY18,
                    polygon3PointX18 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX18,
                    polygon3PointY19 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY19,
                    polygon3PointX19 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX19,
                    polygon3PointY20 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY20,
                    polygon3PointX20 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX20,
                )
            }
            composable(NavRoutesPolygon3.PageSettingPointsPolygon3Manual.route)
            {
                SettingPointsPolygon3Manual(
                    setValuePolygon3TrolleyPos = coordinateProtectionViewModule.setValuePolygon3TrolleyPos.value,
                    updateValuePolygon3TrolleyPos = { coordinateProtectionViewModule.updateValuePolygon3TrolleyPos(it) },
                    onSetValuePolygon3TrolleyPos = { coordinateProtectionViewModule.onSetValuePolygon3TrolleyPos() },

                    setValuePolygon3SlewPos = coordinateProtectionViewModule.setValuePolygon3SlewPos.value,
                    updateValuePolygon3SlewPos = { coordinateProtectionViewModule.updateValuePolygon3SlewPos(it) },
                    onSetValuePolygon3SlewPos = { coordinateProtectionViewModule.onSetValuePolygon3SlewPos() },

                    setValuePolygon3CranePos = coordinateProtectionViewModule.setValuePolygon3CranePos.value,
                    updateValuePolygon3CranePos = { coordinateProtectionViewModule.updateValuePolygon3CranePos(it) },
                    onSetValuePolygon3CranePos = { coordinateProtectionViewModule.onSetValuePolygon3CranePos() },

                    onSetP3PointCmd = { index -> coordinateProtectionViewModule.onSetP3PointCmd(index) },

                    errorMessagePolygon3 = coordinateProtectionViewModule.errorMessagePolygon3Manual.value,
                    logMessagePolygon3 = coordinateProtectionViewModule.logMessagePolygon3Manual.value,

                    polygon3PointY01 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY01,
                    polygon3PointX01 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX01,
                    polygon3PointY02 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY02,
                    polygon3PointX02 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX02,
                    polygon3PointY03 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY03,
                    polygon3PointX03 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX03,
                    polygon3PointY04 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY04,
                    polygon3PointX04 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX04,
                    polygon3PointY05 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY05,
                    polygon3PointX05 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX05,
                    polygon3PointY06 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY06,
                    polygon3PointX06 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX06,
                    polygon3PointY07 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY07,
                    polygon3PointX07 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX07,
                    polygon3PointY08 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY08,
                    polygon3PointX08 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX08,
                    polygon3PointY09 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY09,
                    polygon3PointX09 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX09,
                    polygon3PointY10 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY10,
                    polygon3PointX10 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX10,
                    polygon3PointY11 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY11,
                    polygon3PointX11 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX11,
                    polygon3PointY12 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY12,
                    polygon3PointX12 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX12,
                    polygon3PointY13 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY13,
                    polygon3PointX13 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX13,
                    polygon3PointY14 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY14,
                    polygon3PointX14 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX14,
                    polygon3PointY15 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY15,
                    polygon3PointX15 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX15,
                    polygon3PointY16 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY16,
                    polygon3PointX16 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX16,
                    polygon3PointY17 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY17,
                    polygon3PointX17 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX17,
                    polygon3PointY18 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY18,
                    polygon3PointX18 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX18,
                    polygon3PointY19 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY19,
                    polygon3PointX19 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX19,
                    polygon3PointY20 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY20,
                    polygon3PointX20 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX20,
                )
            }
            composable(NavRoutesPolygon3.PageSettingPointsPolygon3Coordinate.route) {
                SettingPointsPolygon3Coordinate(
                    setValuePolygon3PxPos = coordinateProtectionViewModule.setValuePolygon3PxPos.value,
                    updateValuePolygon3PxPos = { coordinateProtectionViewModule.updateValuePolygon3PxPos(it) },
                    onsetValuePolygon3PxPos = { coordinateProtectionViewModule.onsetValuePolygon3PxPos() },

                    setValuePolygon3PyPos = coordinateProtectionViewModule.setValuePolygon3PyPos.value,
                    updateValuePolygon3PyPos = { coordinateProtectionViewModule.updateValuePolygon3PyPos(it) },
                    onsetValuePolygon3PyPos = { coordinateProtectionViewModule.onsetValuePolygon3PyPos() },

                    onSetP3PointCmd = { index -> coordinateProtectionViewModule.onSetP3PointCmd(index) },

                    errorMessagePolygon3 = coordinateProtectionViewModule.errorMessagePolygon3Coordinate.value,
                    logMessagePolygon3 = coordinateProtectionViewModule.logMessagePolygon3Coordinate.value,

                    polygon3PointY01 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY01,
                    polygon3PointX01 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX01,
                    polygon3PointY02 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY02,
                    polygon3PointX02 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX02,
                    polygon3PointY03 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY03,
                    polygon3PointX03 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX03,
                    polygon3PointY04 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY04,
                    polygon3PointX04 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX04,
                    polygon3PointY05 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY05,
                    polygon3PointX05 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX05,
                    polygon3PointY06 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY06,
                    polygon3PointX06 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX06,
                    polygon3PointY07 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY07,
                    polygon3PointX07 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX07,
                    polygon3PointY08 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY08,
                    polygon3PointX08 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX08,
                    polygon3PointY09 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY09,
                    polygon3PointX09 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX09,
                    polygon3PointY10 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY10,
                    polygon3PointX10 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX10,
                    polygon3PointY11 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY11,
                    polygon3PointX11 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX11,
                    polygon3PointY12 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY12,
                    polygon3PointX12 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX12,
                    polygon3PointY13 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY13,
                    polygon3PointX13 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX13,
                    polygon3PointY14 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY14,
                    polygon3PointX14 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX14,
                    polygon3PointY15 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY15,
                    polygon3PointX15 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX15,
                    polygon3PointY16 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY16,
                    polygon3PointX16 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX16,
                    polygon3PointY17 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY17,
                    polygon3PointX17 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX17,
                    polygon3PointY18 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY18,
                    polygon3PointX18 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX18,
                    polygon3PointY19 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY19,
                    polygon3PointX19 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX19,
                    polygon3PointY20 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointY20,
                    polygon3PointX20 = coordinateProtectionViewModule.state.value.polygon3Point.polygon3PointX20,
                )
            }
        }
    }
}

@Composable
fun NavBarPolygon3(navController: NavController) {

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
                        navController.navigate(NavRoutesPolygon3.PageSettingPolygon3.route)
                    },
                ) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.CoordinateProtection_Button_Position,
                    onClick = {
                        navController.navigate(NavRoutesPolygon3.PageSettingPointsPolygon3Trolley.route)
                    },
                ) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.CoordinateProtection_Button_Manual,
                    onClick = {
                        navController.navigate(NavRoutesPolygon3.PageSettingPointsPolygon3Manual.route)
                    },
                ) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier.padding(end = 7.dp),
                    text = R.string.CoordinateProtection_Button_Coordinate,
                    onClick = {
                        navController.navigate(NavRoutesPolygon3.PageSettingPointsPolygon3Coordinate.route)
                    },
                ) {}
            }
        }
    }
}

sealed class NavRoutesPolygon3 (val route: String) {
    object PageSettingPolygon3: NavRoutesPolygon3("Настройка. Полигон №3")
    object PageSettingPointsPolygon3Coordinate: NavRoutesPolygon3("Настройка точек по координатам. Полигон №3")
    object PageSettingPointsPolygon3Manual: NavRoutesPolygon3("Настройка точек в ручную. Полигон №3")
    object PageSettingPointsPolygon3Trolley: NavRoutesPolygon3("Настройка точек по тележке. Полигон №3")
}


@Preview
@Composable
fun Polygon3ContentPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        Polygon3Content(
        )
    }
}