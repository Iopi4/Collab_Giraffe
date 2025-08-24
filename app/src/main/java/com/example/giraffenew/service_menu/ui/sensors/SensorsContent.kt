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

                setValueWindSpeedLvl2 = serviceMenuViewModel.setValueWindSpeedLvl2.value,
                onModelChangeWindSpeedLvl2 = { serviceMenuViewModel.updateWindSpeedLvl2(it) },
                onSetWindSpeedLvl2 = { serviceMenuViewModel.onSetWindSpeedLvl2() },

                setValueWindSpeedMontageLvl1 = serviceMenuViewModel.setValueWindSpeedMontageLvl.value,
                onModelChangeWindSpeedMontageLvl1 = { serviceMenuViewModel.updateWindSpeedMontageLvl(it) },
                onSetWindSpeedMontageLvl1 = { serviceMenuViewModel.onSetWindSpeedMontageLvl() },

                setValueWindTimeSmooth = serviceMenuViewModel.setValueWindTimeSmooth.value,
                onModelChangeWindTimeSmooth = { serviceMenuViewModel.updateWindTimeSmooth(it) },
                onSetWindTimeSmooth = { serviceMenuViewModel.onSetWindTimeSmooth() },

                setValueWindSpeedLvlClbr = serviceMenuViewModel.setValueWindSpeedLvlClbr.value,
                onModelChangeWindSpeedLvlClbr = { serviceMenuViewModel.updateWindSpeedLvlClbr(it) },
                onSetWindSpeedLvlClbr = { serviceMenuViewModel.onSetWindSpeedLvlClbr() },

                currentWind = serviceMenuViewModel.state.value.currentWind,
                currentAnemometerSensor = serviceMenuViewModel.state.value.currentAnemometerSensor,
                currentAnemometerStatus = serviceMenuViewModel.state.value.currentAnemometerStatus,

                setValueWindRK = serviceMenuViewModel.setValueWindRK.value,
                onModelChangeWindRK = { serviceMenuViewModel.updateWindRK(it) },
                onSetWindRK = { serviceMenuViewModel.onSetWindRK() },

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

                setValueLoadCellMaxWeight = serviceMenuViewModel.setLoadCellMaxWeight.value,
                onModelChangeLoadCellMaxWeight = { serviceMenuViewModel.updateLoadCellMaxWeight(it) },
                onSetLoadCellMaxWeight = { serviceMenuViewModel.onSetLoadCellMaxWeight() },

                setValueLoadCellSmoothingDepth = serviceMenuViewModel.setLoadCellSmoothingDepth.value,
                onModelChangeLoadCellSmoothingDepth = { serviceMenuViewModel.updateLoadCellSmoothingDepth(it) },
                onSetLoadCellSmoothingDepth = { serviceMenuViewModel.onSetLoadCellSmoothingDepth() },

                setValueLoadCellOverSpeedThreshold = serviceMenuViewModel.setLoadCellOverSpeedThreshold.value,
                onModelChangeLoadCellOverSpeedThreshold = { serviceMenuViewModel.updateLoadCellOverSpeedThreshold(it) },
                onSetLoadCellOverSpeedThreshold = { serviceMenuViewModel.onSetLoadCellOverSpeedThreshold() },

                errorMessageLoadCell = serviceMenuViewModel.errorMessageLoadCell.value,
                logMessageLoadCell = serviceMenuViewModel.logMessageLoadCell.value
            )}
            composable(NavRoutesSensorsContent.PageServicePosition.route)
            {ServicePosition(
                setValueAbsEncRatioHoistGK = serviceMenuViewModel.setValueAbsEncRatioHoistGK.value,
                onModelChangeAbsEncRatioHoistGK = { serviceMenuViewModel.updateValueAbsEncRatioHoistGK(it) },
                onSetAbsEncRatioHoistGK = { serviceMenuViewModel.onSetAbsEncRatioHoistGK() },

                setValueAbsEncRatioSlewGK = serviceMenuViewModel.setValueAbsEncRatioSlewGK.value,
                onModelChangeAbsEncRatioSlewGK = { serviceMenuViewModel.updateValueAbsEncRatioSlewGK(it) },
                onSetAbsEncRatioSlewGK = { serviceMenuViewModel.onSetAbsEncRatioSlewGK() },

                setValueAbsEncRatioTrolleyGK = serviceMenuViewModel.setValueAbsEncRatioTrolleyGK.value,
                onModelChangeAbsEncRatioTrolleyGK = { serviceMenuViewModel.updateValueAbsEncRatioTrolleyGK(it) },
                onSetAbsEncRatioTrolleyGK = { serviceMenuViewModel.onSetAbsEncRatioTrolleyGK() },

                setValueAbsEncRatioCraneGK = serviceMenuViewModel.setValueAbsEncRatioCraneGK.value,
                onModelChangeAbsEncRatioCraneGK = { serviceMenuViewModel.updateValueAbsEncRatioCraneGK(it) },
                onSetAbsEncRatioCraneGK = { serviceMenuViewModel.onSetAbsEncRatioCraneGK() },

                onSetAbsEncDeltaControlEn = { serviceMenuViewModel.onSetAbsEncDeltaControlEn() },
                currentAbsEncDeltaControlEn = serviceMenuViewModel.state.value.currentAbsEncDeltaControlEn,

                setValueUDIHoistAbsEncMaxDeltaEn = serviceMenuViewModel.setValueUDIHoistAbsEncMaxDeltaEn.value,
                onModelChangeUDIHoistAbsEncMaxDeltaEn = { serviceMenuViewModel.updateValueUDIHoistAbsEncMaxDeltaEn(it) },
                onSetUDIHoistAbsEncMaxDeltaEn ={ serviceMenuViewModel.onSetUDIHoistAbsEncMaxDeltaEn() },

                setValueUDISlewAbsEncMaxDeltaEn = serviceMenuViewModel.setValueUDISlewAbsEncMaxDeltaEn.value,
                onModeChangelUDISlewAbsEncMaxDeltaEn ={ serviceMenuViewModel.updateValueUDISlewAbsEncMaxDeltaEn(it) },
                onSetUDISlewAbsEncMaxDeltaEn ={ serviceMenuViewModel.onSetUDISlewAbsEncMaxDeltaEn() },

                setValueUDITrolleyAbsEncMaxDeltaEn = serviceMenuViewModel.setValueUDITrolleyAbsEncMaxDeltaEn.value,
                onModelChangeUDITrolleyAbsEncMaxDeltaEn = { serviceMenuViewModel.updateValueUDITrolleyAbsEncMaxDeltaEn(it) },
                onSetUDITrolleyAbsEncMaxDeltaEn = { serviceMenuViewModel.onSetUDITrolleyAbsEncMaxDeltaEn() },

                setValueUDICraneAbsEncMaxDeltaEn = serviceMenuViewModel.setValueUDICraneAbsEncMaxDeltaEn.value,
                onModelChangeUDICraneAbsEncMaxDeltaEn = { serviceMenuViewModel.updateValueUDICraneAbsEncMaxDeltaEn(it) },
                onSetUDICraneAbsEncMaxDeltaEn = { serviceMenuViewModel.onSetUDICraneAbsEncMaxDeltaEn() },

                onSetAbsEncDebugModeHoistEn = { serviceMenuViewModel.onSetAbsEncDebugModeHoistEn()},
                onSetAbsEncDebugModeSlewEn = { serviceMenuViewModel.onSetAbsEncDebugModeSlewEn()},

                errorMessageAbsEnc = serviceMenuViewModel.errorMessageAbsEnc.value,
                logMessageAbsEnc = serviceMenuViewModel.logMessageAbsEnc.value,

                currentAbsEncDebugModeHoist = serviceMenuViewModel.state.value.currentAbsEncDebugModeHoist,
                currentAbsEncDebugModeSlew = serviceMenuViewModel.state.value.currentAbsEncDebugModeSlew,
            )
            }
            composable(NavRoutesSensorsContent.PageServiceRefPoint.route) {ServiceRefPoint()}
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