package com.example.giraffenew.navigate

import androidx.compose.foundation.background
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.giraffenew.R
import com.example.giraffenew.navigate.presentation.TopBarViewModel
import com.example.giraffenew.navigate.ui_kit.DrawerHeaderTop
import com.example.giraffenew.navigate.ui_kit.NavigationButton
import com.example.giraffenew.navigate.ui_kit.BuildNavigationButton
import com.example.giraffenew.navigate.ui_kit.ServiceNavigationButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawer(
    route: String,
    modifier: Modifier = Modifier,
    navigateToHome: () -> Unit = {},
    navigateToSettings: () -> Unit = {},
    navigateToSettingSensor: () -> Unit = {},
    navigateToSettingLoadCapacity: () -> Unit = {},
    navigateToCoordinateProtection: () -> Unit = {},
    navigateToSettingRotation: () -> Unit = {},
    navigateToServiceMenu: () -> Unit = {},
    navigateToDiagnostics: () -> Unit = {},
    navigateToParameterLogger: () -> Unit = {},
    navigateToTechnicalMaintenance: () -> Unit = {},
    navigateToAccidentWarninglog: () -> Unit = {},
    navigateToExceptionLog: () -> Unit = {},
    navigateToLogin: () -> Unit = {},
    //navigateToModbusTools: () -> Unit = {},
    closeDrawer: () -> Unit = {},
    buildMode: Int,
    serviceMode: Int,
)
{
    val topBarViewModule: TopBarViewModel = hiltViewModel()
    val protectedMenusEnabled = buildMode == 1 || serviceMode == 1
    val protectedMenusEnabledService = serviceMode == 1

    ModalDrawerSheet(modifier = Modifier.background(colorResource(R.color.Silver))) {
//Верхняя часть выдвижного окна навигации
        DrawerHeaderTop(modifier)

//Кнопка навигации "Клавный экран"
        NavigationButton(
            iconResId = R.drawable.ic_nav_home,
            labelResId = R.string.menu_home,
            isSelected = route == AllDestinations.NAVIGATE_TO_HOME,
            onClick = {
                navigateToHome()
                closeDrawer() },
            )

//Кнопка навигации "Начальные настройки"
        BuildNavigationButton(
            iconResId = R.drawable.ic_nav_initial_setup,
            labelResId = R.string.menu_initial_setup,
            isSelected = route == AllDestinations.NAVIGATE_INITIAL_SETUP,
            isLocked = !protectedMenusEnabled, //Статус блокировки работы кнопки
            onClick = {
                if (protectedMenusEnabled) {
                    navigateToSettings()
                    closeDrawer()
                }
                else topBarViewModule.playAccessDeniedSound()
                      },
        )

//Кнопка навигации "Настройка грузоподъемности"
        BuildNavigationButton(
            iconResId = R.drawable.ic_nav_setting_load_capacity,
            labelResId = R.string.menu_setting_load_capacity,
            isSelected = route == AllDestinations.NAVIGATE_SETTING_LOAD_CAPASITY,
            isLocked = !protectedMenusEnabled, //Статус блокировки работы кнопки
            onClick = {
                if (protectedMenusEnabled) {
                    navigateToSettingLoadCapacity()
                    closeDrawer()
                } else topBarViewModule.playAccessDeniedSound()
                      },
        )

//Кнопка навигации "Настройка датчиков"
        BuildNavigationButton(
            iconResId = R.drawable.ic_nav_setting_sensors,
            labelResId = R.string.menu_setting_sensors,
            isSelected = route == AllDestinations.NAVIGATE_SETTING_SENSORS,
            isLocked = !protectedMenusEnabled,
            onClick = {
                if (protectedMenusEnabled) {
                    navigateToSettingSensor()
                    closeDrawer()
                } else topBarViewModule.playAccessDeniedSound()
                      },
        )

//Кнопка навигации "Координатная защита"
        BuildNavigationButton(
            iconResId = R.drawable.ic_nav_coordinate_protection,
            labelResId = R.string.menu_coordinate_protection,
            isSelected = route == AllDestinations.NAVIGATE_COORDINATE_PROTECTION,
            isLocked = !protectedMenusEnabled,
            onClick = {
                if (protectedMenusEnabled) {
                navigateToCoordinateProtection()
                closeDrawer()
                } else topBarViewModule.playAccessDeniedSound()
                      },
        )

//Кнопка навигации "Настройка поворота"
        ServiceNavigationButton(
            iconResId = R.drawable.ic_nav_setting_rotation,
            labelResId = R.string.menu_setting_rotation,
            isSelected = route == AllDestinations.NAVIGATE_SETTING_ROTATION,
            isLocked = !protectedMenusEnabledService,
            onClick = {
            if (protectedMenusEnabledService){
                navigateToSettingRotation()
                closeDrawer()
            } else topBarViewModule.playAccessDeniedSound()
                      },
        )

//Кнопка навигации "Сервисное меню"
        ServiceNavigationButton(
            iconResId = R.drawable.ic_nav_service_menu,
            labelResId = R.string.menu_service,
            isSelected = route == AllDestinations.NAVIGATE_SERVICE_MENU,
            isLocked = !protectedMenusEnabledService,
            onClick = {
                if (protectedMenusEnabledService){
                navigateToServiceMenu()
                closeDrawer()
                } else topBarViewModule.playAccessDeniedSound()
                      },
        )

//Кнопка навигации "Диагностика"
        NavigationButton(
            iconResId = R.drawable.ic_nav_diagnostic_search,
            labelResId = R.string.menu_diagnostic,
            isSelected = route == AllDestinations.NAVIGATE_DIAGNOSTICS,
            onClick = {
                navigateToDiagnostics()
                closeDrawer() },
        )

//Кнопка навигации "Регистратор параметров"
        NavigationButton(
            iconResId = R.drawable.ic_nav_regist_param,
            labelResId = R.string.menu_parameter_logger,
            isSelected = route == AllDestinations.NAVIGATE_PARAMETER_LOGGER,
            onClick = {
                navigateToParameterLogger()
                closeDrawer() },
        )


//Кнопка навигации "Техническое обслуживание"
        NavigationButton(
            iconResId = R.drawable.ic_nav_technical_maintenance,
            labelResId = R.string.menu_technical_maintenance,
            isSelected = route == AllDestinations.NAVIGATE_TECHNICAL_MAINTENANCE,
            onClick = {
                navigateToTechnicalMaintenance()
                closeDrawer() },
        )

//Кнопка навигации "Журнал Аварий и Предупреждений"
        NavigationButton(
            iconResId = R.drawable.ic_nav_accident,
            labelResId = R.string.accident_and_warning_log,
            isSelected = route == AllDestinations.NAVIGATE_ACCIDENT_AND_WARNING_LOG,
            onClick = {
                navigateToAccidentWarninglog()
                closeDrawer() },
        )

//Кнопка навигации "Авторизация пользователя"
        NavigationButton(
            iconResId = R.drawable.ic_nav_login,
            labelResId = R.string.menu_Login,
            isSelected = route == AllDestinations.NAVIGATE_LOGIN,
            onClick = {
                navigateToLogin()
                closeDrawer() },
        )

        //Кнопка навигации "Ошибки Android"
        NavigationButton(
            iconResId = R.drawable.ic_topbar_android,
            labelResId = R.string.menu_Exception_Android,
            isSelected = route == AllDestinations.NAVIGATE_EXCEPTION,
            onClick = {
                navigateToExceptionLog()
                closeDrawer()
            },
        )

        /**
        //Кнопка навигации "Настройки Modbus"
        ServiceNavigationButton(
        iconResId = R.drawable.ic_nav_setting_modbus,
        labelResId = R.string.menu_ModbusTools,
        isSelected = route == AllDestinations.NAVIGATE_MODBUS_TOOLS,
        isLocked = !protectedMenusEnabledService,
        onClick = {
        if (protectedMenusEnabledService) {
        navigateToModbusTools()
        closeDrawer()
        } else topBarViewModule.playAccessDeniedSound()
        },
        )
         */


    }
}