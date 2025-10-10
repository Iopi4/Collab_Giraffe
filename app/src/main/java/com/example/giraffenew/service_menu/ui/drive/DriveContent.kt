package com.example.giraffenew.service_menu.ui.drive

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
fun DriveContent(
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
        NavBarDriveContent(navController = navController)
        NavHost(navController, startDestination = NavRoutesDriveContent.PageServiceHoist.route) {
            composable(NavRoutesDriveContent.PageServiceHoist.route)
            {ServiceHoist(
                setValueHoistACC = serviceMenuViewModel.setValueHoistACC.value,
                onModelChangeHoistACC = { serviceMenuViewModel.updateHoistACC(it) },
                onSetHoistACC = { serviceMenuViewModel.onSetHoistACC() },
                hmiHoistACC = serviceMenuViewModel.state.value.hoistDriveState.hmiHoistACC,

                setValueHoistDEC = serviceMenuViewModel.setValueHoistDEC.value,
                onModelChangeHoistDEC = { serviceMenuViewModel.updateHoistDEC(it) },
                onSetHoistDEC = { serviceMenuViewModel.onSetHoistDEC() },
                hmiHoistDEC = serviceMenuViewModel.state.value.hoistDriveState.hmiHoistDEC,

                setValueHoistSpdRef1 = serviceMenuViewModel.setValueHoistSpdRef1.value,
                onModelChangeHoistSpdRef1 = { serviceMenuViewModel.updateHoistSpdRef1(it) },
                onSetHoistSpdRef1 = { serviceMenuViewModel.onSetHoistSpdRef1() },

                setValueHoistSpdRef2 = serviceMenuViewModel.setValueHoistSpdRef2.value,
                onModelChangeHoistSpdRef2 = { serviceMenuViewModel.updateHoistSpdRef2(it) },
                onSetHoistSpdRef2 = { serviceMenuViewModel.onSetHoistSpdRef2() },

                setValueHoistSpdRef3 = serviceMenuViewModel.setValueHoistSpdRef3.value,
                onModelChangeHoistSpdRef3 = { serviceMenuViewModel.updateHoistSpdRef3(it) },
                onSetHoistSpdRef3 = { serviceMenuViewModel.onSetHoistSpdRef3() },

                setValueHoistSpdRef4 = serviceMenuViewModel.setValueHoistSpdRef4.value,
                onModelChangeHoistSpdRef4 = { serviceMenuViewModel.updateHoistSpdRef4(it) },
                onSetHoistSpdRef4 = { serviceMenuViewModel.onSetHoistSpdRef4() },

                setValueHoistSpdRefButton = serviceMenuViewModel.setValueHoistSpdRefButton.value,
                onModelChangeHoistSpdRefButton = { serviceMenuViewModel.updateHoistSpdRefButton(it) },
                onSetHoistSpdRefButton = { serviceMenuViewModel.onSetHoistSpdRefButton() },

                setValueHoistSpdRefMax = serviceMenuViewModel.setValueHoistSpdRefMax.value,
                onModelChangeHoistSpdRefMax = { serviceMenuViewModel.updateHoistSpdRefMax(it) },
                onSetHoistSpdRefMax = { serviceMenuViewModel.onSetHoistSpdRefMax() },

                setValueHoistSpdNom = serviceMenuViewModel.setValueHoistSpdNom.value,
                updateHoistSpdRefNom = { serviceMenuViewModel.updateHoistSpdRefNom(it) },
                onSetHoistSpdNom = { serviceMenuViewModel.onSetHoistSpdNom() },

                setValueHoistMontageACC = serviceMenuViewModel.setValueHoistMontageACC.value,
                onModelChangeHoistMontageACC = { serviceMenuViewModel.updateHoistMontageACC(it) },
                onSetHoistMontageACC = { serviceMenuViewModel.onSetHoistMontageACC() },

                setValueHoistMontageDEC = serviceMenuViewModel.setValueHoistMontageDEC.value,
                onModelChangeHoistMontageDEC = { serviceMenuViewModel.updateHoistMontageDEC(it) },
                onSetHoistMontageDEC = { serviceMenuViewModel.onSetHoistMontageDEC() },

                setValueHoistMontageSpdRef1 = serviceMenuViewModel.setValueHoistMontageSpdRef1.value,
                onModelChangeHoistMontageSpdRef1 = {
                    serviceMenuViewModel.updateHoistMontageSpdRef1(
                        it
                    )
                },
                onSetHoistMontageSpdRef1 = { serviceMenuViewModel.onSetHoistMontageSpdRef1() },

                setValueHoistMontageSpdRef2 = serviceMenuViewModel.setValueHoistMontageSpdRef2.value,
                onModelChangeHoistMontageSpdRef2 = {
                    serviceMenuViewModel.updateHoistMontageSpdRef2(
                        it
                    )
                },
                onSetHoistMontageSpdRef2 = { serviceMenuViewModel.onSetHoistMontageSpdRef2() },

                errorMessageHoist = serviceMenuViewModel.errorMessageHoist.value,
                logMessageHoist = serviceMenuViewModel.logMessageHoist.value,

                hmiSpdRefButtonHoist = serviceMenuViewModel.state.value.hoistDriveState.hmiSpdRefButtonHoist,
                hmiSpdRef1Hoist = serviceMenuViewModel.state.value.hoistDriveState.hmiSpdRef1Hoist,
                hmiSpdRef2Hoist = serviceMenuViewModel.state.value.hoistDriveState.hmiSpdRef2Hoist,
                hmiSpdRef3Hoist = serviceMenuViewModel.state.value.hoistDriveState.hmiSpdRef3Hoist,
                hmiSpdRef4Hoist = serviceMenuViewModel.state.value.hoistDriveState.hmiSpdRef4Hoist,
                hmiSpdRefMAXHoist = serviceMenuViewModel.state.value.hoistDriveState.hmiSpdRefMAXHoist,
                hmiSpdRefNomHoist = serviceMenuViewModel.state.value.hoistDriveState.hmiSpdRefNomHoist,
                hmiSpdRef1HoistMontage = serviceMenuViewModel.state.value.hoistDriveState.hmiSpdRef1HoistMontage,
                hmiSpdRef2HoistMontage = serviceMenuViewModel.state.value.hoistDriveState.hmiSpdRef2HoistMontage,
                hmiHoistACCMontage = serviceMenuViewModel.state.value.hoistDriveState.hmiHoistACCMontage,
                hmiHoistDECMontage = serviceMenuViewModel.state.value.hoistDriveState.hmiHoistDECMontage,

                onSetHoistBrakeControlEn = {serviceMenuViewModel.onSetHoistBrakeControlEn()},
                hmiHoistBrakeControlStat = serviceMenuViewModel.state.value.hoistDriveState.hmiHoistBrakeControlStat,

                setValueHoistBrakeControlTimeout = serviceMenuViewModel.setValueHoistBrakeControlTimeout.value,
                updateHoistBrakeControlTimeout = {serviceMenuViewModel.updateHoistBrakeControlTimeout(it)},
                onSetHoistBrakeControlTimeout = {serviceMenuViewModel.onSetHoistBrakeControlTimeout()},
                hmiHoistBrakeControlTimeout = serviceMenuViewModel.state.value.hoistDriveState.hmiHoistBrakeControlTimeout,

                onSetFltRst = { serviceMenuViewModel.onSetFltRst()},
            )}
            composable(NavRoutesDriveContent.PageServiceSlewing.route)
            {ServiceSlewing(
                setValueSlewACC = serviceMenuViewModel.setValueSlewACC.value,
                onModelChangeSlewACC = { serviceMenuViewModel.updateSlewACC(it) },
                onSetSlewACC = { serviceMenuViewModel.onSetSlewACC() },
                hmiSlewACC = serviceMenuViewModel.state.value.slewDriveState.hmiSlewACC,

                setValueSlewDEC = serviceMenuViewModel.setValueSlewDEC.value,
                onModelChangeSlewDEC = { serviceMenuViewModel.updateSlewDEC(it) },
                onSetSlewDEC = { serviceMenuViewModel.onSetSlewDEC() },
                hmiSlewDEC = serviceMenuViewModel.state.value.slewDriveState.hmiSlewDEC,

                setValueSlewSpdRef1 = serviceMenuViewModel.setValueSlewSpdRef1.value,
                onModelChangeSlewSpdRef1 = { serviceMenuViewModel.updateSlewSpdRef1(it) },
                onSetSlewSpdRef1 = { serviceMenuViewModel.onSetSlewSpdRef1() },
                hmiSpdRef1Slew = serviceMenuViewModel.state.value.slewDriveState.hmiSpdRef1Slew,

                setValueSlewSpdRef2 = serviceMenuViewModel.setValueSlewSpdRef2.value,
                onModelChangeSlewSpdRef2 = { serviceMenuViewModel.updateSlewSpdRef2(it) },
                onSetSlewSpdRef2 = { serviceMenuViewModel.onSetSlewSpdRef2() },
                hmiSpdRef2Slew = serviceMenuViewModel.state.value.slewDriveState.hmiSpdRef2Slew,

                setValueSlewSpdRef3 = serviceMenuViewModel.setValueSlewSpdRef3.value,
                onModelChangeSlewSpdRef3 = { serviceMenuViewModel.updateSlewSpdRef3(it) },
                onSetSlewSpdRef3 = { serviceMenuViewModel.onSetSlewSpdRef3() },
                hmiSpdRef3Slew = serviceMenuViewModel.state.value.slewDriveState.hmiSpdRef3Slew,

                setValueSlewSpdRef4 = serviceMenuViewModel.setValueSlewSpdRef4.value,
                onModelChangeSlewSpdRef4 = { serviceMenuViewModel.updateSlewSpdRef4(it) },
                onSetSlewSpdRef4 = { serviceMenuViewModel.onSetSlewSpdRef4() },
                hmiSpdRef4Slew = serviceMenuViewModel.state.value.slewDriveState.hmiSpdRef4Slew,

                setValueSlewSpdNom = serviceMenuViewModel.setValueSlewSpdNom.value,
                updateSlewSpdNom = { serviceMenuViewModel.updateSlewSpdNom(it) },
                onSetSlewSpdNom = { serviceMenuViewModel.onSetSlewSpdNom() },
                hmiSpdRefNomSlew = serviceMenuViewModel.state.value.slewDriveState.hmiSpdRefNomSlew,

                errorMessageSlew = serviceMenuViewModel.errorMessageSlew.value,
                logMessageSlew = serviceMenuViewModel.logMessageSlew.value,

                onSetFltRst = { serviceMenuViewModel.onSetFltRst()},
                )
            }
            composable(NavRoutesDriveContent.PageServiceTrolley.route)
            { ServiceTrolley(
                setValueTrolleyACC = serviceMenuViewModel.setValueTrolleyACC.value,
                onModelChangeTrolleyACC = {serviceMenuViewModel.updateTrolleyACC(it) },
                onSetTrolleyACC ={ serviceMenuViewModel.onSetTrolleyACC() },
                hmiTrolleyACC = serviceMenuViewModel.state.value.trolleyDriveState.hmiTrolleyACC,

                setValueTrolleyDEC = serviceMenuViewModel.setValueTrolleyDEC.value,
                onModelChangeTrolleyDEC = { serviceMenuViewModel.updateTrolleyDEC(it)},
                onSetTrolleyDEC ={ serviceMenuViewModel.onSetTrolleyDEC()},
                hmiTrolleyDEC = serviceMenuViewModel.state.value.trolleyDriveState.hmiTrolleyDEC,

                setValueTrolleySpdRef1 = serviceMenuViewModel.setValueTrolleySpdRef1.value,
                onModelChangeTrolleySpdRef1 = { serviceMenuViewModel.updateTrolleySpdRef1(it)},
                onSetTrolleySpdRef1 ={ serviceMenuViewModel.onSetTrolleySpdRef1()},
                hmiSpdRef1Trolley = serviceMenuViewModel.state.value.trolleyDriveState.hmiSpdRef1Trolley,

                setValueTrolleySpdRef2 = serviceMenuViewModel.setValueTrolleySpdRef2.value,
                onModelChangeTrolleySpdRef2 = {serviceMenuViewModel.updateTrolleySpdRef2(it) },
                onSetTrolleySpdRef2 ={ serviceMenuViewModel.onSetTrolleySpdRef2() },
                hmiSpdRef2Trolley = serviceMenuViewModel.state.value.trolleyDriveState.hmiSpdRef2Trolley,

                setValueTrolleySpdRef3 = serviceMenuViewModel.setValueTrolleySpdRef3.value,
                onModelChangeTrolleySpdRef3 = {serviceMenuViewModel.updateTrolleySpdRef3(it) },
                onSetTrolleySpdRef3 ={ serviceMenuViewModel.onSetTrolleySpdRef3() },
                hmiSpdRef3Trolley = serviceMenuViewModel.state.value.trolleyDriveState.hmiSpdRef3Trolley,

                setValueTrolleySpdRef4 = serviceMenuViewModel.setValueTrolleySpdRef4.value,
                onModelChangeTrolleySpdRef4 = { serviceMenuViewModel.updateTrolleySpdRef4(it)},
                onSetTrolleySpdRef4 ={ serviceMenuViewModel.onSetTrolleySpdRef4()},
                hmiSpdRef4Trolley = serviceMenuViewModel.state.value.trolleyDriveState.hmiSpdRef4Trolley,

                setValueTrolleySpdNom = serviceMenuViewModel.setValueTrolleySpdNom.value,
                updateTrolleySpdNom = { serviceMenuViewModel.updateTrolleySpdNom(it)},
                onSetTrolleySpdNom ={ serviceMenuViewModel.onSetTrolleySpdNom()},
                hmiSpdRefNomTrolley = serviceMenuViewModel.state.value.trolleyDriveState.hmiSpdRefNomTrolley,

                setValueTrolleyOverSpd = serviceMenuViewModel.setValueTrolleyTrolleyOverSpd.value,
                onModelChangeTrolleyOverSpd = { serviceMenuViewModel.updateTrolleyOverLsp(it)},
                onSetTrolleyOverSpd ={serviceMenuViewModel.onSetTrolleyOverSpd() },
                hmiSpdRefOverSpd = serviceMenuViewModel.state.value.trolleyDriveState.hmiSpdRefOverSpd,

                errorMessageTrolley = serviceMenuViewModel.errorMessageTrolley.value,
                logMessageTrolley = serviceMenuViewModel.logMessageTrolley.value,

                onSetFltRst = { serviceMenuViewModel.onSetFltRst()},
                )
            }
            composable(NavRoutesDriveContent.PageServiceCrane.route)
            { ServiceCrane(
                setValueCraneACC = serviceMenuViewModel.setValueCraneACC.value,
                onModelChangeCraneACC = { serviceMenuViewModel.updateCraneACC(it)},
                onSetCraneACC = {serviceMenuViewModel.onSetCraneACC() },
                hmiCraneACC = serviceMenuViewModel.state.value.craneDriveState.hmiCraneACC,

                setValueCraneDEC = serviceMenuViewModel.setValueCraneDEC.value,
                onModelChangeCraneDEC = { serviceMenuViewModel.updateCraneDEC(it)},
                onSetCraneDEC = {serviceMenuViewModel.onSetCraneDEC() },
                hmiCraneDEC = serviceMenuViewModel.state.value.craneDriveState.hmiCraneDEC,

                setValueCraneSpdRef1 = serviceMenuViewModel.setValueCraneSpdRef1.value,
                onModelChangeCraneSpdRef1 = { serviceMenuViewModel.updateCraneSpdRef1(it)},
                onSetCraneSpdRef1 = {serviceMenuViewModel.onSetCraneSpdRef1() },
                hmiSpdRef1Crane = serviceMenuViewModel.state.value.craneDriveState.hmiSpdRef1Crane,

                setValueCraneSpdRef2 = serviceMenuViewModel.setValueCraneSpdRef2.value,
                onModelChangeCraneSpdRef2 = { serviceMenuViewModel.updateCraneSpdRef2(it)},
                onSetCraneSpdRef2 = {serviceMenuViewModel.onSetCraneSpdRef2() },
                hmiSpdRef2Crane = serviceMenuViewModel.state.value.craneDriveState.hmiSpdRef2Crane,

                setValueCraneSpdRef3 = serviceMenuViewModel.setValueCraneSpdRef3.value,
                onModelChangeCraneSpdRef3 = { serviceMenuViewModel.updateCraneSpdRef3(it)},
                onSetCraneSpdRef3 = {serviceMenuViewModel.onSetCraneSpdRef3() },
                hmiSpdRef3Crane = serviceMenuViewModel.state.value.craneDriveState.hmiSpdRef3Crane,

                setValueCraneSpdRef4 = serviceMenuViewModel.setValueCraneSpdRef4.value,
                onModelChangeCraneSpdRef4 = { serviceMenuViewModel.updateCraneSpdRef4(it)},
                onSetCraneSpdRef4 = {serviceMenuViewModel.onSetCraneSpdRef4() },
                hmiSpdRef4Crane = serviceMenuViewModel.state.value.craneDriveState.hmiSpdRef4Crane,

                errorMessageCrane = serviceMenuViewModel.errorMessageCrane.value,
                logMessageCrane = serviceMenuViewModel.logMessageCrane.value,

                onSetFltRst = { serviceMenuViewModel.onSetFltRst()},
            )
            }
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
// Заголовок
        BasicSettingsText(modifier = Modifier,
            text = stringResource(R.string.ServiceMenu_Text_Drive_Parameters))

//Код для формирования области в верху с кнопками переходов между страницами
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Drive_Hoist,
                    onClick = {
                        navController.navigate(NavRoutesDriveContent.PageServiceHoist.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Drive_Slewing,
                    onClick = {
                        navController.navigate(NavRoutesDriveContent.PageServiceSlewing.route) },) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Drive_Trolley,
                    onClick = {
                        navController.navigate(NavRoutesDriveContent.PageServiceTrolley.route) },) {}
            }
            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.ServiceMenu_Button_Drive_Crane,
                    onClick = {
                        navController.navigate(NavRoutesDriveContent.PageServiceCrane.route) },) {}
            }
        }
    }
}

sealed class NavRoutesDriveContent (val route: String){
    object PageServiceHoist: NavRoutesDriveContent("Сервис. Привод подъема. Настройка")
    object PageServiceSlewing: NavRoutesDriveContent("Сервис. Привод поворота. Настройка")
    object PageServiceTrolley: NavRoutesDriveContent("Сервис. Привод тележки. Настройка")
    object PageServiceCrane: NavRoutesDriveContent("Сервис. Привод хода крана. Настройка")
}

@Preview
@Composable
fun DriveContentPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {

    }
}