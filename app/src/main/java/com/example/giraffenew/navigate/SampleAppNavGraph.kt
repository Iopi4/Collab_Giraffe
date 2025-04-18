package com.example.giraffenew.navigate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.giraffenew.accident_and_warning_log.AccidentWarninglogScreen
import com.example.giraffenew.coordinate_protection.CoordinateProtectionScreen
import com.example.giraffenew.diagnostics.DiagnosticsScreen
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.Orange
import com.example.giraffenew.ui.theme.white
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.giraffenew.home.presentanion.HomeScreen
import com.example.giraffenew.initial_setup.SettingsScreen
import com.example.giraffenew.login.LoginScreen
import com.example.giraffenew.modbus_tools.ModbusToolsScreen
import com.example.giraffenew.parameter_logger.ParameterLoggerScreen
import com.example.giraffenew.service_menu.ServiceMenuScreen
import com.example.giraffenew.setting_load_capacity.SettingLoadCapacityScreen
import com.example.giraffenew.setting_rotation.SettingRotationScreen
import com.example.giraffenew.setting_sensors.SettingSensorsScreen
import com.example.giraffenew.technical_maintenance.TechnicalMaintenanceScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleAppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {

    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: AllDestinations.nav_home
    val navigationActions = remember (navController) {
        AppNavigationActions(navController)
    }

    ModalNavigationDrawer(drawerContent = {
        AppDrawer(
            route = currentRoute,
            navigateToHome = { navigationActions.navigateToHome() },
            navigateToSettings = { navigationActions.navigateTo_InitialSetup() },
            navigateToSettingLoadCapacity = { navigationActions.navigateTo_SettingLoadCapacity() },
            navigateToSettingSensor = { navigationActions.navigateTo_SettingSensors() },
            navigateToCoordinateProtection = { navigationActions.navigateTo_CoordinateProtection() },
            navigateToSettingRotation = { navigationActions.navigateTo_SettingRotation() },
            navigateToServiceMenu = { navigationActions.navigateTo_ServiceMenu() },
            navigateToDiagnostics = { navigationActions.navigateTo_Diagnostics() },
            navigateToParameterLogger = { navigationActions.navigateTo_ParameterLogger() },
            navigateToTechnicalMaintenance = { navigationActions.navigateTo_TechnicalMaintenance() },
            navigateToAccidentWarninglog = { navigationActions.navigateTo_AccidentAndWarningLog() },
            navigateToLogin = { navigationActions.navigateTo_Login() },
            navigateToModbusTools = {navigationActions.navigateTo_ModbusTools() },
            closeDrawer = { coroutineScope.launch { drawerState.close() } },
            modifier = Modifier
        )
    }, drawerState = drawerState) {
        val boxTopBarText = Modifier
            .padding(top = 5.dp, start = 16.dp)
            .clip(shape = RoundedCornerShape(1.dp))

        Scaffold(

            topBar = {
                Row (modifier = Modifier.fillMaxHeight(0.08f).background(color = Orange)) {
                    Box(modifier = Modifier.weight(4f)) {

//Область отображения типа крана и кнопки меню навигации
                        Column (modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween ) {
//Область отображения типа крана
                            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.SpaceBetween) {

                                Box (boxTopBarText) {TopBarText("ModelCrane") }
                                Box (boxTopBarText) {TopBarText("GV_PulleyBlock") }
                                Box (boxTopBarText) {TopBarText("LoadCapacity") }
                            }

//Область отображения кнопки меню навигации
                            Box (modifier = Modifier.weight(1f)){
                                TopAppBar(title = { Text(text = currentRoute, color = white) },
                                    modifier = Modifier.fillMaxWidth(),
                                    navigationIcon = { IconButton(onClick = { coroutineScope.launch { drawerState.open() } },
                                        content = { Icon(imageVector = Icons.Default.Menu, contentDescription = null, tint = white) }) },
                                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Orange))
                            }
                        }
                    }
//Область отобтражения даты и времени
                    Box(modifier = Modifier.weight(1f)) {

                        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                            //модификатор устанавливает отступы  между границей компонента и границами контейнера)
                            //В начале определяем данные в виде списке langs. В столбце Column пробегаемся
                            // по этому списку и создаем для каждого свой компонент Text

                            TopBarTextDateTime("Time")

                            TopBarTextDateTime("Data")

                        }
                    }
                }

            }, modifier = Modifier

        ) {
            NavHost(
                //При вызове в NavHost передается объект NavHostController, компонент, который будет служить
                // начальным экраном/начальным пунктом назначения, и граф навигации (navigation graph):
                navController = navController,
                startDestination = AllDestinations.nav_home, modifier = modifier.padding(it)
                    //startDestination = HOME_SCREEN_ROUTE
                ) {

                composable (AllDestinations.nav_home) {
                    HomeScreen()
                }

                composable(AllDestinations.nav_initial_setup) {
                    SettingsScreen()
                }

                composable(AllDestinations.nav_setting_load_capacity) {
                    SettingLoadCapacityScreen()
                }

                composable(AllDestinations.nav_setting_sensors) {
                    SettingSensorsScreen()
                }

                composable(AllDestinations.nav_coordinate_protection) {
                    CoordinateProtectionScreen()
                }

                composable(AllDestinations.nav_setting_rotation) {
                    SettingRotationScreen()
                }

                composable(AllDestinations.nav_service_menu) {
                    ServiceMenuScreen()
                }

                composable(AllDestinations.nav_diagnostics) {
                    DiagnosticsScreen()
                }

                composable(AllDestinations.nav_parameter_logger) {
                    ParameterLoggerScreen()
                }

                composable(AllDestinations.nav_technical_maintenance) {
                    TechnicalMaintenanceScreen()
                }

                composable(AllDestinations.nav_accident_and_warning_log) {
                    AccidentWarninglogScreen()
                }

                composable(AllDestinations.nav_login) {
                    LoginScreen()
                }

                composable(AllDestinations.nav_modbus_tools) {
                    ModbusToolsScreen()
                }

            }
        }
    }
}



@Composable
//Создадим функцию для свойств текста TopAppBar (Верхняя часть экрана - Информация о кране)
fun TopBarText (text:String, textModifier:Modifier=Modifier){
    Text(text, textModifier, fontSize = 16.sp, textAlign = TextAlign.Start, color = Burgundy_violet, fontWeight = FontWeight.Bold) }

@Composable
//Создадим функцию для свойств текста TopAppBar (Верхняя часть экрана - Дата и время)
fun TopBarTextDateTime (text:String, textModifier:Modifier=Modifier){
    Text(text, modifier = Modifier.padding(5.dp), fontSize = 20.sp, textAlign = TextAlign.Center, color = Burgundy_violet, fontWeight = FontWeight.Bold) }