package com.example.giraffenew.setting_sensors.presentation

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
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonTabGiraffe
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.setting_sensors.ui.CalibrationSensorHoist
import com.example.giraffenew.setting_sensors.ui.CalibrationSensorTrolley
import com.example.giraffenew.setting_sensors.ui.CalibrationSensorCrane
import com.example.giraffenew.setting_sensors.ui.CalibrationSensorSlewing
import com.example.giraffenew.ui.theme.DarkGrew

@Composable
fun SettingSensorsScreen (
    settingSensorsViewModule: SettingSensorsViewModule = hiltViewModel()
){

    // 1. Получаем текущий LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current

    // 2. Создаем Lifecycle-aware эффект
    DisposableEffect(lifecycleOwner, settingSensorsViewModule) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("SettingSensorsScreen", "Lifecycle: ON_RESUME- resuming data collection")
                    settingSensorsViewModule.setActiveConfiguration()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("SettingSensorsScreen", "Lifecycle: ON_PAUSE - pausing data collection")
                    settingSensorsViewModule.clearActiveConfiguration()
                }
                else -> {Log.e("SettingSensorsScreen", "Lifecycle: NOT START")}
            }
        }

        // 3. Регистрируем наблюдатель
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        // 4. Очистка при удалении Composable
        onDispose {
            Log.d("SettingSensorsScreen", "Lifecycle: ON_DISPOSE - removing observer")
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            settingSensorsViewModule.clearActiveConfiguration() // Дополнительная очистка
        }
    }

    BasicSettingBackground()
    //Создадим кнопки навигации для меню настройки датчиков положения механизмов
    // данные для отображения названия страницы

    val navController= rememberNavController()
    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    {
        NavBarDriveContent(navController = navController)
        NavHost(navController, startDestination = NavRoutesDriveContent.PageSensorHoist.route) {
            composable(NavRoutesDriveContent.PageSensorHoist.route) 
            { 
                CalibrationSensorHoist(
                    currentHoistPosition = settingSensorsViewModule.state.value.currentHoistPosition,
                    currentAbsEncHoistPositionWord1 = settingSensorsViewModule.state.value.currentAbsEncHoistPositionWord1,
                    currentAbsEncHoistPositionWord2 = settingSensorsViewModule.state.value.currentAbsEncHoistPositionWord2,
                    onSetHoistLimitTestEn = { settingSensorsViewModule.onSetHoistLimitTestEn()},
                    hoistLimitStatus = settingSensorsViewModule.state.value.hoistLimitStatus,

                    setValueHoistPosFwdStop = settingSensorsViewModule.setValueHoistPosFwdStop.value,
                    updateValueHoistPosFwdStop = { settingSensorsViewModule.updateValueHoistPosFwdStop(it)},
                    onSetValueHoistPosFvdStop = {settingSensorsViewModule.onSetValueHoistPosFvdStop()},
                    currentHoistStopUp1 = settingSensorsViewModule.state.value.currentHoistStopUp1,

                    setValueHoistPosRevStop = settingSensorsViewModule.setValueHoistPosRevStop.value,
                    updateValueHoistPosRevStop = {settingSensorsViewModule.updateValueHoistPosRevStop(it)},
                    onSetValueHoistPosRevStop = {settingSensorsViewModule.onSetValueHoistPosRevStop()},
                    currentHoistStopDwn1 = settingSensorsViewModule.state.value.currentHoistStopDwn1,

                    setValueHoistPosFwdDwnSp = settingSensorsViewModule.setValueHoistPosFwdDwnSp.value,
                    updateValueHoistPosFwdDwnSp = {settingSensorsViewModule.updateValueHoistPosFwdDwnSp(it)},
                    onSetValueHoistPosFwdDwnSp = {settingSensorsViewModule.onSetValueHoistPosFwdDwnSp()},
                    currentHoistUpDwnSp1 = settingSensorsViewModule.state.value.currentHoistUpDwnSp1,

                    setValueHoistPosRevDwnSp = settingSensorsViewModule.setValueHoistPosRevDwnSp.value,
                    updateValueHoistPosRevDwnSp = {settingSensorsViewModule.updateValueHoistPosRevDwnSp(it)},
                    onSetValueHoistPosRevDwnSp = {settingSensorsViewModule.onSetValueHoistPosRevDwnSp()},
                    currentHoistDwnDwnSp1 = settingSensorsViewModule.state.value.currentHoistDwnDwnSp1,

                    currentHoistEncoderMaxDeltaError= settingSensorsViewModule.state.value.currentHoistEncoderMaxDeltaError,
                    currentHoistEncoderMaxValueError= settingSensorsViewModule.state.value.currentHoistEncoderMaxValueError,
                    currentHoistAbsEncErrCanOpen= settingSensorsViewModule.state.value.currentHoistAbsEncErrCanOpen,

                    errorMessageHoist = settingSensorsViewModule.errorMessageHoist.value,
                    logMessageHoist = settingSensorsViewModule.logMessageHoist.value,
                )}
            composable(NavRoutesDriveContent.PageSensorSlewing.route)
            { CalibrationSensorSlewing(
                currentAbsEncSlewPositionWord1 = settingSensorsViewModule.state.value.currentAbsEncSlewPositionWord1,
                currentAbsEncSlewPositionWord2 = settingSensorsViewModule.state.value.currentAbsEncSlewPositionWord2,
                currentSlewPosition = settingSensorsViewModule.state.value.currentSlewPosition,

                onSetSlewZeroMemEn = {settingSensorsViewModule.onSetSlewZeroMemEn()},

                setValueSlewCalcAngle = settingSensorsViewModule.setValueSlewCalcAngle.value,
                updateValueSlewCalcAngle = {settingSensorsViewModule.updateValueSlewCalcAngle(it) },
                onSetValueSlewCalcAngle = { settingSensorsViewModule.onSetValueSlewCalcAngle()},

                setValueSlewMaxAngle = settingSensorsViewModule.setValueSlewMaxAngle.value,
                updateValueSlewMaxAngle = {settingSensorsViewModule.updateValueSlewMaxAngle(it) },
                onSetValueSlewMaxAngle = { settingSensorsViewModule.onSetValueSlewMaxAngle()},

                currentSlewEncoderMaxDeltaError = settingSensorsViewModule.state.value.currentSlewEncoderMaxDeltaError,
                currentSlewEncoderMaxValueError = settingSensorsViewModule.state.value.currentSlewEncoderMaxValueError,
                currentSlewAbsEncErrCanOpen = settingSensorsViewModule.state.value.currentSlewAbsEncErrCanOpen,

                errorMessageSlew = settingSensorsViewModule.errorMessageSlew.value,
                logMessageSlew = settingSensorsViewModule.logMessageSlew.value
            ) }

            composable(NavRoutesDriveContent.PageSensorTrolley.route) {
                CalibrationSensorTrolley(
                    currentTrolleyPosition = settingSensorsViewModule.state.value.currentTrolleyPosition,
                    currentAbsEncTrolleyPositionWord1 = settingSensorsViewModule.state.value.currentAbsEncTrolleyPositionWord1,
                    currentAbsEncTrolleyPositionWord2 = settingSensorsViewModule.state.value.currentAbsEncTrolleyPositionWord2,
                    onSetTrolleyLimitTestEn = { settingSensorsViewModule.onSetTrolleyLimitTestEn()},

                    setValueTrolleyPosFwdStop = settingSensorsViewModule.setValueTrolleyPosFwdStop.value,
                    updateValueTrolleyPosFwdStop = { settingSensorsViewModule.updateValueTrolleyPosFwdStop(it)},
                    onSetValueTrolleyPosFvdStop = {settingSensorsViewModule.onSetValueTrolleyPosFvdStop()},
                    currentTrolleyStopFwd1 = settingSensorsViewModule.state.value.currentTrolleyStopFwd1,

                    setValueTrolleyPosRevStop = settingSensorsViewModule.setValueTrolleyPosRevStop.value,
                    updateValueTrolleyPosRevStop = {settingSensorsViewModule.updateValueTrolleyPosRevStop(it)},
                    onSetValueTrolleyPosRevStop = {settingSensorsViewModule.onSetValueTrolleyPosRevStop()},
                    currentTrolleyStopRev1 = settingSensorsViewModule.state.value.currentTrolleyStopRev1,

                    setValueTrolleyPosFwdDwnSp = settingSensorsViewModule.setValueTrolleyPosFwdDwnSp.value,
                    updateValueTrolleyPosFwdDwnSp = {settingSensorsViewModule.updateValueTrolleyPosFwdDwnSp(it)},
                    onSetValueTrolleyPosFwdDwnSp = {settingSensorsViewModule.onSetValueTrolleyPosFwdDwnSp()},
                    currentTrolleyFwdDwnSp1 = settingSensorsViewModule.state.value.currentTrolleyFwdDwnSp1,

                    setValueTrolleyPosRevDwnSp = settingSensorsViewModule.setValueTrolleyPosRevDwnSp.value,
                    updateValueTrolleyPosRevDwnSp = { settingSensorsViewModule.updateValueTrolleyPosRevDwnSp(it)},
                    onSetValueTrolleyPosRevDwnSp = {settingSensorsViewModule.onSetValueTrolleyPosRevDwnSp()},
                    currentTrolleyRevDwnSp1 = settingSensorsViewModule.state.value.currentTrolleyRevDwnSp1,

                    currentTrolleyEncoderMaxDeltaError= settingSensorsViewModule.state.value.currentTrolleyEncoderMaxDeltaError,
                    currentTrolleyEncoderMaxValueError= settingSensorsViewModule.state.value.currentTrolleyEncoderMaxValueError,
                    currentTrolleyAbsEncErrCanOpen= settingSensorsViewModule.state.value.currentTrolleyAbsEncErrCanOpen,

                    errorMessageTrolley = settingSensorsViewModule.errorMessageTrolley.value,
                    logMessageTrolley = settingSensorsViewModule.logMessageTrolley.value,
                    trolleyLimitStatus = settingSensorsViewModule.state.value.trolleyLimitStatus,
                ) }
            composable(NavRoutesDriveContent.PageSensorCrane.route)
            { CalibrationSensorCrane(
                currentAbsEncCranePositionWord1 = settingSensorsViewModule.state.value.currentAbsEncCranePositionWord1,
                currentAbsEncCranePositionWord2 = settingSensorsViewModule.state.value.currentAbsEncCranePositionWord2,
                currentCranePosition = settingSensorsViewModule.state.value.currentCranePosition,

                onSetCraneLimitTestEn = { settingSensorsViewModule.onSetCraneLimitTestEn() },
                onSetCraneRailsEn = { settingSensorsViewModule.onSetCraneRailsEn() },
                currentCraneRailsEn = settingSensorsViewModule.state.value.currentCraneRailsEn,

                setValueCranePosFwdStop = settingSensorsViewModule.setValueCranePosFwdStop.value,
                updateValueCranePosFwdStop = {settingSensorsViewModule.updateValueCranePosFwdStop(it) },
                onSetValueCranePosFwdStop = { settingSensorsViewModule.onSetValueCranePosFwdStop() },
                currentCraneStopFwd1 = settingSensorsViewModule.state.value.currentCraneStopFwd1,

                setValueCranePosRevStop = settingSensorsViewModule.setValueCranePosRevStop.value,
                updateValueCranePosRevStop = { settingSensorsViewModule.updateValueCranePosRevStop(it) },
                onSetValueCranePosRevStop = { settingSensorsViewModule.onSetValueCranePosRevStop() },
                currentCraneStopRev1 = settingSensorsViewModule.state.value.currentCraneStopRev1,

                setValueCranePosFwdDwnSp = settingSensorsViewModule.setValueCranePosFwdDwnSp.value,
                updateValueCranePosFwdDwnSp = { settingSensorsViewModule.updateValueCranePosFwdDwnSp(it) },
                onSetValueCranePosFwdDwnSp = { settingSensorsViewModule.onSetValueCranePosFwdDwnSp()},
                currentCraneFwdDwnSp1 = settingSensorsViewModule.state.value.currentCraneFwdDwnSp1,

                setValueCranePosRevDwnSp = settingSensorsViewModule.setValueCranePosRevDwnSp.value,
                updateValueCranePosRevDwnSp = { settingSensorsViewModule.updateValueCranePosRevDwnSp(it)},
                onSetValueCranePosRevDwnSp = {settingSensorsViewModule.onSetValueCranePosRevDwnSp() },
                currentCraneRevDwnSp1 = settingSensorsViewModule.state.value.currentCraneRevDwnSp1,

                setValueCranePosRepPoint = settingSensorsViewModule.setValueCranePosRepPoint.value,
                updateValueCranePosRepPoint = {settingSensorsViewModule.updateValueCranePosRepPoint(it) },
                onSetValueCranePosRepPoint = {settingSensorsViewModule.onSetValueCranePosRepPoint() },
                currentCraneRepPoint = settingSensorsViewModule.state.value.currentCraneRepPoint,

                currentCraneEncoderMaxDeltaError = settingSensorsViewModule.state.value.currentCraneEncoderMaxDeltaError,
                currentCraneEncoderMaxValueError = settingSensorsViewModule.state.value.currentCraneEncoderMaxValueError,
                currentCraneAbsEncErrCanOpen = settingSensorsViewModule.state.value.currentCraneAbsEncErrCanOpen,

                errorMessageCrane = settingSensorsViewModule.errorMessageCrane.value,
                logMessageCrane = settingSensorsViewModule.logMessageCrane.value,

                craneLimitStatus = settingSensorsViewModule.state.value.craneLimitStatus
            ) }
        }
    }
}

@Composable
fun NavBarDriveContent(navController: NavController){

    val myBoxNavBarDriveContentModifier = Modifier
        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = DarkGrew,
            shape = RoundedCornerShape(5.dp)
        )

    Column(myBoxNavBarDriveContentModifier,
        verticalArrangement = Arrangement.SpaceEvenly)
    {
//Код для формирования области в верху с кнопками переходов между страницами
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.SettingSensors_Button_HOIST,
                    onClick = {
                        navController.navigate(NavRoutesDriveContent.PageSensorHoist.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.SettingSensors_Button_SLEW,
                    onClick = {
                        navController.navigate(NavRoutesDriveContent.PageSensorSlewing.route) },) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.SettingSensors_Button_TROLLEY,
                    onClick = {
                        navController.navigate(NavRoutesDriveContent.PageSensorTrolley.route) },) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.SettingSensors_Button_CRANE,
                    onClick = {
                        navController.navigate(NavRoutesDriveContent.PageSensorCrane.route) },) {}
            }
        }
    }
}

sealed class NavRoutesDriveContent (val route: String){
    object PageSensorHoist: NavRoutesDriveContent("Настройка. Датчик высоты подъема")
    object PageSensorSlewing: NavRoutesDriveContent("Настройка. Датчик поворота")
    object PageSensorTrolley: NavRoutesDriveContent("Настройка. Датчик тележки")
    object PageSensorCrane: NavRoutesDriveContent("Настройка. Датчик хода крана")
}

@Preview
@Composable
fun SettingSensorsScreenPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        SettingSensorsScreen(
        )
    }
}