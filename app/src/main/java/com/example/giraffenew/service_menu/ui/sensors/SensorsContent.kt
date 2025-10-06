package com.example.giraffenew.service_menu.ui.sensors

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
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsText
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonTabGiraffe
import com.example.giraffenew.service_menu.presentation.ServiceMenuViewModel
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun SensorsContent(
    serviceMenuViewModel: ServiceMenuViewModel = hiltViewModel()
){

    // 1. Получаем текущий LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current

    // 2. Создаем Lifecycle-aware эффект
    DisposableEffect(lifecycleOwner, serviceMenuViewModel) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("ServiceMenuScreen", "Lifecycle: ON_RESUME- resuming data collection")
                    serviceMenuViewModel.setActiveConfiguration()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("ServiceMenuScreen", "Lifecycle: ON_PAUSE - pausing data collection")
                    serviceMenuViewModel.clearActiveConfiguration()
                }
                else -> {Log.e("ServiceMenuScreen", "Lifecycle: NOT START")}
            }
        }

        // 3. Регистрируем наблюдатель
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        // 4. Очистка при удалении Composable
        onDispose {
            Log.d("ServiceMenuScreen", "Lifecycle: ON_DISPOSE - removing observer")
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            serviceMenuViewModel.clearActiveConfiguration() // Дополнительная очистка
        }
    }

    val navController= rememberNavController()
    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    {
        NavBarSensorsContent(navController = navController)
        NavHost(navController, startDestination = NavRoutesSensorsContent.PageServiceWind.route) {
            composable(NavRoutesSensorsContent.PageServiceWind.route)
            {ServiceWind(
                setValueWindSpeedLvl1 = serviceMenuViewModel.setValueWindSpeedLvl1.value,
                onModelChangeWindSpeedLvl1 = { serviceMenuViewModel.updateWindSpeedLvl1(it) },
                onSetWindSpeedLvl1 = { serviceMenuViewModel.onSetWindSpeedLvl1() },
                hmiWindSpeedLvl1 = serviceMenuViewModel.state.value.sensorsAndUserState.hmiWindSpeedLvl1,

                setValueWindSpeedLvl2 = serviceMenuViewModel.setValueWindSpeedLvl2.value,
                onModelChangeWindSpeedLvl2 = { serviceMenuViewModel.updateWindSpeedLvl2(it) },
                onSetWindSpeedLvl2 = { serviceMenuViewModel.onSetWindSpeedLvl2() },
                hmiWindSpeedLvl2 = serviceMenuViewModel.state.value.sensorsAndUserState.hmiWindSpeedLvl2,

                setValueWindSpeedMontageLvl1 = serviceMenuViewModel.setValueWindSpeedMontageLvl.value,
                onModelChangeWindSpeedMontageLvl1 = { serviceMenuViewModel.updateWindSpeedMontageLvl(it) },
                onSetWindSpeedMontageLvl1 = { serviceMenuViewModel.onSetWindSpeedMontageLvl() },
                hmiWindSpeedLvl1Montage = serviceMenuViewModel.state.value.sensorsAndUserState.hmiWindSpeedLvl1Montage,

                setValueWindTimeSmooth = serviceMenuViewModel.setValueWindTimeSmooth.value,
                onModelChangeWindTimeSmooth = { serviceMenuViewModel.updateWindTimeSmooth(it) },
                onSetWindTimeSmooth = { serviceMenuViewModel.onSetWindTimeSmooth() },
                hmiAnemometerTimeSmooth = serviceMenuViewModel.state.value.sensorsAndUserState.hmiAnemometerTimeSmooth,

                setValueAnemometerMinWind = serviceMenuViewModel.setValueAnemometerMinWind.value,
                updateAnemometerMinWind = { serviceMenuViewModel.updateAnemometerMinWind(it) },
                onSetAnemometerMinWindClbr = { serviceMenuViewModel.onSetAnemometerMinWindClbr() },
                hmiReadAnemometerMinWind = serviceMenuViewModel.state.value.sensorsAndUserState.hmiReadAnemometerMinWind,

                setValueAnemometerMaxWind = serviceMenuViewModel.setValueAnemometerMaxWind.value,
                updateAnemometerMaxWind = { serviceMenuViewModel.updateAnemometerMaxWind(it) },
                onSetAnemometerMaxWindClbr = { serviceMenuViewModel.onSetAnemometerMaxWindClbr() },
                hmiReadAnemometerMaxWind = serviceMenuViewModel.state.value.sensorsAndUserState.hmiReadAnemometerMaxWind,

                setValueAnemometerMinAmper = serviceMenuViewModel.setValueAnemometerMinAmper.value,
                updateAnemometerMinAmper = { serviceMenuViewModel.updateAnemometerMinAmper(it) },
                onSetAnemometerMinAmperClbr = { serviceMenuViewModel.onSetAnemometerMinAmperClbr() },
                hmiReadAnemometerMinAmper = serviceMenuViewModel.state.value.sensorsAndUserState.hmiReadAnemometerMinAmper,

                setValueAnemometerMaxAmper = serviceMenuViewModel.setValueAnemometerMaxAmper.value,
                updateAnemometerMaxAmper = { serviceMenuViewModel.updateAnemometerMaxAmper(it) },
                onSetAnemometerMaxAmperClbr = { serviceMenuViewModel.onSetAnemometerMaxAmperClbr() },
                hmiReadAnemometerMaxAmper = serviceMenuViewModel.state.value.sensorsAndUserState.hmiReadAnemometerMaxAmper,

                currentWind = serviceMenuViewModel.state.value.sensorsAndUserState.currentWind,
                currentAnemometerSensor = serviceMenuViewModel.state.value.sensorsAndUserState.currentAnemometerSensor,
                currentAnemometerStatus = serviceMenuViewModel.state.value.sensorsAndUserState.currentAnemometerStatus,

                onSetAnemometerClbr = { serviceMenuViewModel.onSetAnemometerClbr()},

                errorMessageWind = serviceMenuViewModel.errorMessageWind.value,
                logMessageWind = serviceMenuViewModel.logMessageWind.value,
            )
            }
            composable(NavRoutesSensorsContent.PageServiceWeight.route)
            { ServiceWeight(
                setValueLoadCellMinWeight = serviceMenuViewModel.setLoadCellMinWeight.value,
                onModelChangeLoadCellMinWeight = { serviceMenuViewModel.updateLoadCellMinWeight(it) },
                onSetLoadCellMinWeight = { serviceMenuViewModel.onSetLoadCellMinWeight() },
                hmiAdvLoadCellLL = serviceMenuViewModel.state.value.sensorsAndUserState.hmiAdvLoadCellLL,

                setValueLoadCellMaxWeight = serviceMenuViewModel.setLoadCellMaxWeight.value,
                onModelChangeLoadCellMaxWeight = { serviceMenuViewModel.updateLoadCellMaxWeight(it) },
                onSetLoadCellMaxWeight = { serviceMenuViewModel.onSetLoadCellMaxWeight() },
                hmiAdvLoadCellHH = serviceMenuViewModel.state.value.sensorsAndUserState.hmiAdvLoadCellHH,

                setValueLoadCellSmoothingDepth = serviceMenuViewModel.setLoadCellSmoothingDepth.value,
                onModelChangeLoadCellSmoothingDepth = { serviceMenuViewModel.updateLoadCellSmoothingDepth(it) },
                onSetLoadCellSmoothingDepth = { serviceMenuViewModel.onSetLoadCellSmoothingDepth() },
                hmiArraySizeWeightSmooth = serviceMenuViewModel.state.value.sensorsAndUserState.hmiArraySizeWeightSmooth,

                setValueLoadCellOverSpeedThreshold = serviceMenuViewModel.setLoadCellOverSpeedThreshold.value,
                onModelChangeLoadCellOverSpeedThreshold = { serviceMenuViewModel.updateLoadCellOverSpeedThreshold(it) },
                onSetLoadCellOverSpeedThreshold = { serviceMenuViewModel.onSetLoadCellOverSpeedThreshold() },
                hmiWeightSPOverSpeed = serviceMenuViewModel.state.value.sensorsAndUserState.hmiWeightSPOverSpeed,

                errorMessageLoadCell = serviceMenuViewModel.errorMessageLoadCell.value,
                logMessageLoadCell = serviceMenuViewModel.logMessageLoadCell.value
            )}
            composable(NavRoutesSensorsContent.PageServicePosition.route)
            {ServicePosition(
                setValueSlewNumberTeethOPU = serviceMenuViewModel.setValueSlewNumberTeethOPU.value,
                updateValueSlewNumberTeethOPU = { serviceMenuViewModel.updateValueSlewNumberTeethOPU(it) },
                onSetSlewNumberTeethOPU = { serviceMenuViewModel.onSetSlewNumberTeethOPU() },
                hmiSlewNumberTeethOPU = serviceMenuViewModel.state.value.sensorsAndUserState.hmiSlewNumberTeethOPU,

                setValueSlewNumberTeethGearSensor = serviceMenuViewModel.setValueSlewNumberTeethGearSensor.value,
                updateValueSlewNumberTeethGearSensor = { serviceMenuViewModel.updateValueSlewNumberTeethGearSensor(it) },
                onSetSlewNumberTeethGearSensor = { serviceMenuViewModel.onSetSlewNumberTeethGearSensor() },
                hmiSlewNumberTeethGearSensor = serviceMenuViewModel.state.value.sensorsAndUserState.hmiSlewNumberTeethGearSensor,

                setValueSlewSingleTurnStepsSensor = serviceMenuViewModel.setValueSlewSingleTurnStepsSensor.value,
                updateValueSlewSingleTurnStepsSensor = { serviceMenuViewModel.updateValueSlewSingleTurnStepsSensor(it) },
                onSetSlewSingleTurnStepsSensor = { serviceMenuViewModel.onSetSlewSingleTurnStepsSensor() },
                hmiSlewSingleTurnStepsSensor = serviceMenuViewModel.state.value.sensorsAndUserState.hmiSlewSingleTurnStepsSensor,

                setValueSlewRatioK = serviceMenuViewModel.setValueSlewRatioK.value,
                updateValueSlewRatioK = { serviceMenuViewModel.updateValueSlewRatioK(it) },
                onSetSlewRatioK = { serviceMenuViewModel.onSetSlewRatioK() },
                hmiSlewRatioK = serviceMenuViewModel.state.value.sensorsAndUserState.hmiSlewRatioK,

                onSetAbsEncDeltaControlEn = { serviceMenuViewModel.onSetAbsEncDeltaControlEn() },
                currentAbsEncDeltaControlEn = serviceMenuViewModel.state.value.sensorsAndUserState.currentAbsEncDeltaControlEn,

                setValueUDIHoistAbsEncMaxDeltaEn = serviceMenuViewModel.setValueUDIHoistAbsEncMaxDeltaEn.value,
                onModelChangeUDIHoistAbsEncMaxDeltaEn = { serviceMenuViewModel.updateValueUDIHoistAbsEncMaxDeltaEn(it) },
                onSetUDIHoistAbsEncMaxDeltaEn ={ serviceMenuViewModel.onSetUDIHoistAbsEncMaxDeltaEn() },
                hmiHoistAbsEncMaxDeltaEn = serviceMenuViewModel.state.value.sensorsAndUserState.hmiHoistAbsEncMaxDeltaEn,

                setValueUDISlewAbsEncMaxDeltaEn = serviceMenuViewModel.setValueUDISlewAbsEncMaxDeltaEn.value,
                onModeChangelUDISlewAbsEncMaxDeltaEn ={ serviceMenuViewModel.updateValueUDISlewAbsEncMaxDeltaEn(it) },
                onSetUDISlewAbsEncMaxDeltaEn ={ serviceMenuViewModel.onSetUDISlewAbsEncMaxDeltaEn() },
                hmiSlewAbsEncMaxDeltaEn = serviceMenuViewModel.state.value.sensorsAndUserState.hmiSlewAbsEncMaxDeltaEn,

                setValueUDITrolleyAbsEncMaxDeltaEn = serviceMenuViewModel.setValueUDITrolleyAbsEncMaxDeltaEn.value,
                onModelChangeUDITrolleyAbsEncMaxDeltaEn = { serviceMenuViewModel.updateValueUDITrolleyAbsEncMaxDeltaEn(it) },
                onSetUDITrolleyAbsEncMaxDeltaEn = { serviceMenuViewModel.onSetUDITrolleyAbsEncMaxDeltaEn() },
                hmiTrolleyAbsEncMaxDeltaEn = serviceMenuViewModel.state.value.sensorsAndUserState.hmiTrolleyAbsEncMaxDeltaEn,

                setValueUDICraneAbsEncMaxDeltaEn = serviceMenuViewModel.setValueUDICraneAbsEncMaxDeltaEn.value,
                onModelChangeUDICraneAbsEncMaxDeltaEn = { serviceMenuViewModel.updateValueUDICraneAbsEncMaxDeltaEn(it) },
                onSetUDICraneAbsEncMaxDeltaEn = { serviceMenuViewModel.onSetUDICraneAbsEncMaxDeltaEn() },
                hmiCraneAbsEncMaxDeltaEn = serviceMenuViewModel.state.value.sensorsAndUserState.hmiCraneAbsEncMaxDeltaEn,

                onSetAbsEncDebugModeHoistEn = { serviceMenuViewModel.onSetAbsEncDebugModeHoistEn()},
                onSetAbsEncDebugModeSlewEn = { serviceMenuViewModel.onSetAbsEncDebugModeSlewEn()},

                errorMessageAbsEnc = serviceMenuViewModel.errorMessageAbsEnc.value,
                logMessageAbsEnc = serviceMenuViewModel.logMessageAbsEnc.value,

                currentAbsEncDebugModeHoist = serviceMenuViewModel.state.value.sensorsAndUserState.currentAbsEncDebugModeHoist,
                currentAbsEncDebugModeSlew = serviceMenuViewModel.state.value.sensorsAndUserState.currentAbsEncDebugModeSlew,
            )
            }
            composable(NavRoutesSensorsContent.PageServiceRefPoint.route)
            {
                ServiceRefPoint(
                    setValueCTRLPointControlInterval = serviceMenuViewModel.setValueCTRLPointControlInterval.value,
                    updateCTRLPointControlInterval = { serviceMenuViewModel.updateCTRLPointControlInterval(it)},
                    onSetValueCTRLPointControlInterval = {serviceMenuViewModel.onSetValueCTRLPointControlInterval()},
                    ctrlPointControlInterval = serviceMenuViewModel.state.value.sensorsAndUserState.ctrlPointControlInterval,

                    onSetCTRLPointEN = { serviceMenuViewModel.onSetCTRLPointEN()},
                    craneCTRLPointEN = serviceMenuViewModel.state.value.sensorsAndUserState.craneCTRLPointEN,

                    errorMessageCTRLPoint = serviceMenuViewModel.errorMessageCTRLPoint.value,
                    logMessageCTRLPoint = serviceMenuViewModel.logMessageCTRLPoint.value
                )}
        }
    }
}


@Composable
fun NavBarSensorsContent (navController: NavController){
    val myBoxNavBarSensorsContentModifier = Modifier
        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = DarkGrew,
            shape = RoundedCornerShape(5.dp)
        )
    Column(myBoxNavBarSensorsContentModifier,
        verticalArrangement = Arrangement.SpaceEvenly)
    {
// Заголовок
        BasicSettingsText(modifier = Modifier,
            text = stringResource(R.string.ServiceMenu_Text_Sensors_Parameters))

        //Код для формирования области в верху с кнопками переходов между страницами
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Sensors_Wind,
                    onClick = {
                        navController.navigate(NavRoutesSensorsContent.PageServiceWind.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Sensors_Weight,
                    onClick = {
                        navController.navigate(NavRoutesSensorsContent.PageServiceWeight.route) },) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Sensors_Position,
                    onClick = {
                        navController.navigate(NavRoutesSensorsContent.PageServicePosition.route) },) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Sensors_Ref_Point,
                    onClick = {
                        navController.navigate(NavRoutesSensorsContent.PageServiceRefPoint.route) },) {}
            }
        }
    }
}

sealed class NavRoutesSensorsContent (val route: String){
    object PageServiceWind: NavRoutesSensorsContent("Сервис. Датчик ветра. Настройка")
    object PageServiceWeight: NavRoutesSensorsContent("Сервис. Датчик усилия. Настройка")
    object PageServicePosition: NavRoutesSensorsContent("Сервис. Датчики положения. Настройка")
    object PageServiceRefPoint: NavRoutesSensorsContent("Сервис. Датчик реперной точки. Настройка")
}

@Preview
@Composable
fun SensorsContentPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {

    }
}