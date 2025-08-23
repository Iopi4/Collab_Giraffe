package com.example.giraffenew.service_menu.ui.drive

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
import androidx.hilt.navigation.compose.hiltViewModel
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

                setValueHoistDEC = serviceMenuViewModel.setValueHoistDEC.value,
                onModelChangeHoistDEC = { serviceMenuViewModel.updateHoistDEC(it) },
                onSetHoistDEC = { serviceMenuViewModel.onSetHoistDEC() },

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

                setValueHoistSpdRefButton =serviceMenuViewModel.setValueHoistSpdRefButton.value,
                onModelChangeHoistSpdRefButton = { serviceMenuViewModel.updateHoistSpdRefButton(it) },
                onSetHoistSpdRefButton = { serviceMenuViewModel.onSetHoistSpdRefButton() },

                setValueHoistSpdRefMax = serviceMenuViewModel.setValueHoistSpdRefMax.value,
                onModelChangeHoistSpdRefMax = { serviceMenuViewModel.updateHoistSpdRefMax(it) },
                onSetHoistSpdRefMax = { serviceMenuViewModel.onSetHoistSpdRefMax() },

                setValueHoistMontageACC = serviceMenuViewModel.setValueHoistMontageACC.value,
                onModelChangeHoistMontageACC = { serviceMenuViewModel.updateHoistMontageACC(it) },
                onSetHoistMontageACC = { serviceMenuViewModel.onSetHoistMontageACC() },

                setValueHoistMontageDEC = serviceMenuViewModel.setValueHoistMontageDEC.value,
                onModelChangeHoistMontageDEC = { serviceMenuViewModel.updateHoistMontageDEC(it) },
                onSetHoistMontageDEC ={ serviceMenuViewModel.onSetHoistMontageDEC() },

                setValueHoistMontageSpdRef1 = serviceMenuViewModel.setValueHoistMontageSpdRef1.value,
                onModelChangeHoistMontageSpdRef1 = { serviceMenuViewModel.updateHoistMontageSpdRef1(it) },
                onSetHoistMontageSpdRef1 = { serviceMenuViewModel.onSetHoistMontageSpdRef1() },

                setValueHoistMontageSpdRef2 = serviceMenuViewModel.setValueHoistMontageSpdRef2.value,
                onModelChangeHoistMontageSpdRef2 = { serviceMenuViewModel.updateHoistMontageSpdRef2(it) },
                onSetHoistMontageSpdRef2 = { serviceMenuViewModel.onSetHoistMontageSpdRef2() },

                errorMessageHoist = serviceMenuViewModel.errorMessageHoist.value,
                logMessageHoist = serviceMenuViewModel.logMessageHoist.value,

                errorMessageHoistMontage = serviceMenuViewModel.errorMessageHoistMontage.value,
                logMessageHoistMontage = serviceMenuViewModel.logMessageHoistMontage.value,
            )}
            composable(NavRoutesDriveContent.PageServiceSlewing.route)
            {ServiceSlewing(
                setValueSlewACC = serviceMenuViewModel.setValueSlewACC.value,
                onModelChangeSlewACC = { serviceMenuViewModel.updateSlewACC(it) },
                onSetSlewACC = { serviceMenuViewModel.onSetSlewACC() },

                setValueSlewDEC = serviceMenuViewModel.setValueSlewDEC.value,
                onModelChangeSlewDEC = { serviceMenuViewModel.updateSlewDEC(it) },
                onSetSlewDEC = { serviceMenuViewModel.onSetSlewDEC() },

                setValueSlewSpdRef1 = serviceMenuViewModel.setValueSlewSpdRef1.value,
                onModelChangeSlewSpdRef1 = { serviceMenuViewModel.updateSlewSpdRef1(it) },
                onSetSlewSpdRef1 = { serviceMenuViewModel.onSetSlewSpdRef1()},

                setValueSlewSpdRef2 = serviceMenuViewModel.setValueSlewSpdRef2.value,
                onModelChangeSlewSpdRef2 = { serviceMenuViewModel.updateSlewSpdRef2(it) },
                onSetSlewSpdRef2 = { serviceMenuViewModel.onSetSlewSpdRef2()},

                setValueSlewSpdRef3 = serviceMenuViewModel.setValueSlewSpdRef3.value,
                onModelChangeSlewSpdRef3 = { serviceMenuViewModel.updateSlewSpdRef3(it) },
                onSetSlewSpdRef3 = { serviceMenuViewModel.onSetSlewSpdRef3()},

                setValueSlewSpdRef4 = serviceMenuViewModel.setValueSlewSpdRef4.value,
                onModelChangeSlewSpdRef4 = { serviceMenuViewModel.updateSlewSpdRef4(it) },
                onSetSlewSpdRef4 = { serviceMenuViewModel.onSetSlewSpdRef4()},

                setValueSlewSpdStopButton = serviceMenuViewModel.setValueSlewSpdStopButton.value,
                onModelChangeSlewSpdStopButton = { serviceMenuViewModel.updateSlewSpdStopButton(it) },
                onSetSlewSpdStopButton = { serviceMenuViewModel.onSetSlewSpdStopButton()},

                errorMessageSlew = serviceMenuViewModel.errorMessageSlew.value,
                logMessageSlew = serviceMenuViewModel.logMessageSlew.value
            )}
            composable(NavRoutesDriveContent.PageServiceTrolley.route)
            { ServiceTrolley(
                    setValueTrolleyACC = serviceMenuViewModel.setValueTrolleyACC.value,
                    onModelChangeTrolleyACC = {serviceMenuViewModel.updateTrolleyACC(it) },
                    onSetTrolleyACC ={ serviceMenuViewModel.onSetTrolleyACC() },

                    setValueTrolleyDEC = serviceMenuViewModel.setValueTrolleyDEC.value,
                    onModelChangeTrolleyDEC = { serviceMenuViewModel.updateTrolleyDEC(it)},
                    onSetTrolleyDEC ={ serviceMenuViewModel.onSetTrolleyDEC()},

                    setValueTrolleySpdRef1 = serviceMenuViewModel.setValueTrolleySpdRef1.value,
                    onModelChangeTrolleySpdRef1 = { serviceMenuViewModel.updateTrolleySpdRef1(it)},
                    onSetTrolleySpdRef1 ={ serviceMenuViewModel.onSetTrolleySpdRef1()},

                    setValueTrolleySpdRef2 = serviceMenuViewModel.setValueTrolleySpdRef2.value,
                    onModelChangeTrolleySpdRef2 = {serviceMenuViewModel.updateTrolleySpdRef2(it) },
                    onSetTrolleySpdRef2 ={ serviceMenuViewModel.onSetTrolleySpdRef2() },

                    setValueTrolleySpdRef3 = serviceMenuViewModel.setValueTrolleySpdRef3.value,
                    onModelChangeTrolleySpdRef3 = {serviceMenuViewModel.updateTrolleySpdRef3(it) },
                    onSetTrolleySpdRef3 ={ serviceMenuViewModel.onSetTrolleySpdRef3() },

                    setValueTrolleySpdRef4 = serviceMenuViewModel.setValueTrolleySpdRef4.value,
                    onModelChangeTrolleySpdRef4 = { serviceMenuViewModel.updateTrolleySpdRef4(it)},
                    onSetTrolleySpdRef4 ={ serviceMenuViewModel.onSetTrolleySpdRef4()},

                    setValueTrolleyLsp = serviceMenuViewModel.setValueTrolleyTrolleyOverSpd.value,
                    onModelChangeTrolleyLsp = { serviceMenuViewModel.updateTrolleyOverLsp(it)},
                    onSetTrolleyOverSpd ={serviceMenuViewModel.onSetTrolleyOverSpd() },

                    errorMessageTrolley = serviceMenuViewModel.errorMessageTrolley.value,
                    logMessageTrolley = serviceMenuViewModel.logMessageTrolley.value
                )
            }
            composable(NavRoutesDriveContent.PageServiceCrane.route) {ServiceCrane()}
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