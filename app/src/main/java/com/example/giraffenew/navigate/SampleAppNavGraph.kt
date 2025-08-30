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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.giraffenew.accident_and_warning_log.AccidentWarningLogScreen
import com.example.giraffenew.coordinate_protection.prezentation.CoordinateProtectionScreen
import com.example.giraffenew.diagnostics.prezentation.DiagnosticsScreen
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.Orange
import com.example.giraffenew.ui.theme.white
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.example.giraffenew.home.presentation.HomeScreen
import com.example.giraffenew.initial_setup.presentation.InitialSetupScreen
import com.example.giraffenew.login.presentation.LoginScreen
import com.example.giraffenew.modbus_tools.presentation.ModbusToolsScreen
import com.example.giraffenew.navigate.presentation.TopBarViewModel
import com.example.giraffenew.parameter_logger.presentation.ParameterLoggerScreen
import com.example.giraffenew.service_menu.presentation.ServiceMenuScreen
import com.example.giraffenew.setting_load_capacity.presentation.SettingLoadCapacityScreen
import com.example.giraffenew.setting_rotation.SettingRotationScreen
import com.example.giraffenew.setting_sensors.presentation.SettingSensorsScreen
import com.example.giraffenew.technical_maintenance.presentation.TechnicalMaintenanceScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SampleAppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    val topBarViewModel: TopBarViewModel = hiltViewModel()
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: AllDestinations.NAVIGATE_TO_HOME
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

                                Box (boxTopBarText) {TopBarText(topBarViewModel.currentModelCrane.value) }
                                Box (boxTopBarText) {TopBarText(topBarViewModel.currentPulleyBlock.value) }
                                Box (boxTopBarText) {TopBarText(topBarViewModel.currentLoadCapacity.value) }
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

                            TopBarTextDateTime(topBarViewModel.currentTime.value)

                            TopBarTextDateTime(topBarViewModel.currentDate.value)

                        }
                    }
                }

            }, modifier = Modifier

        ) {
            NavHost(
                //При вызове в NavHost передается объект NavHostController, компонент, который будет служить
                // начальным экраном/начальным пунктом назначения, и граф навигации (navigation graph):
                navController = navController,
                startDestination = AllDestinations.NAVIGATE_COORDINATE_PROTECTION, modifier = modifier.padding(it)
                    //startDestination = HOME_SCREEN_ROUTE
                ) {

                composable (AllDestinations.NAVIGATE_TO_HOME) {
                    HomeScreen()
                }

                composable(AllDestinations.NAVIGATE_INITIAL_SETUP) {
                    InitialSetupScreen()
                }

                composable(AllDestinations.NAVIGATE_SETTING_LOAD_CAPASITY) {
                    SettingLoadCapacityScreen()
                }

                composable(AllDestinations.NAVIGATE_SETTING_SENSORS) {
                    SettingSensorsScreen()
                }

                composable(AllDestinations.NAVIGATE_COORDINATE_PROTECTION) {
                    CoordinateProtectionScreen()
                }

                composable(AllDestinations.NAVIGATE_SETTING_ROTATION) {
                    SettingRotationScreen()
                }

                composable(AllDestinations.NAVIGATE_SERVICE_MENU) {
                    ServiceMenuScreen()
                }

                composable(AllDestinations.NAVIGATE_DIAGNOSTICS) {
                    DiagnosticsScreen()
                }

                composable(AllDestinations.NAVIGATE_PARAMETER_LOGGER) {
                    ParameterLoggerScreen()
                }

                composable(AllDestinations.NAVIGATE_TECHNICAL_MAINTENANCE) {
                    TechnicalMaintenanceScreen()
                }

                composable(AllDestinations.NAVIGATE_ACCIDENT_AND_WARNING_LOG) {
                    AccidentWarningLogScreen()
                }

                composable(AllDestinations.NAVIGATE_LOGIN) {
                    LoginScreen()
                }

                composable(AllDestinations.NAVIGATE_MODBUS_TOOLS) {
                    ModbusToolsScreen()
                }

            }
        }
    }
}



@Composable
//Создадим функцию для свойств текста TopAppBar (Верхняя часть экрана - Информация о кране)
fun TopBarText (text:String, modifier:Modifier=Modifier){
    Text(text, modifier, fontSize = 18.sp, textAlign = TextAlign.Start, color = Burgundy_violet, fontWeight = FontWeight.Bold) }

@Composable
//Создадим функцию для свойств текста TopAppBar (Верхняя часть экрана - Дата и время)
fun TopBarTextDateTime (text:String, modifier:Modifier=Modifier){
    Text(text, modifier.padding(5.dp), fontSize = 20.sp, textAlign = TextAlign.Center, color = Burgundy_violet, fontWeight = FontWeight.Bold) }