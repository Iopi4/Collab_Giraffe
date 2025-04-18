package com.example.giraffenew.navigate

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.giraffenew.R
import com.example.giraffenew.navigate.ui_kit.DrawerHeaderTop
import com.example.giraffenew.navigate.ui_kit.NavigationButton

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
    navigateToLogin: () -> Unit = {},
    navigateToModbusTools: () -> Unit = {},
    closeDrawer: () -> Unit = {}

)
{
    ModalDrawerSheet(modifier = Modifier) {
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
        NavigationButton(
            iconResId = R.drawable.ic_nav_initial_setup,
            labelResId = R.string.menu_initial_setup,
            isSelected = route == AllDestinations.NAVIGATE_INITIAL_SETUP,
            onClick = {
                navigateToSettings()
                closeDrawer() },
        )

//Кнопка навигации "Настройка грузоподъемности"
        NavigationButton(
            iconResId = R.drawable.ic_nav_setting_load_capacity,
            labelResId = R.string.menu_setting_load_capacity,
            isSelected = route == AllDestinations.NAVIGATE_SETTING_LOAD_CAPASITY,
            onClick = {
                navigateToSettingLoadCapacity()
                closeDrawer() },
        )

//Кнопка навигации "Настройка датчиков"
        NavigationButton(
            iconResId = R.drawable.ic_nav_setting_sensors,
            labelResId = R.string.menu_setting_sensors,
            isSelected = route == AllDestinations.NAVIGATE_SETTING_SENSORS,
            onClick = {
                navigateToSettingSensor()
                closeDrawer() },
        )

//Кнопка навигации "Координатная защита"
        NavigationButton(
            iconResId = R.drawable.ic_nav_coordinate_protection,
            labelResId = R.string.menu_coordinate_protection,
            isSelected = route == AllDestinations.NAVIGATE_COORDINATE_PROTECTION,
            onClick = {
                navigateToCoordinateProtection()
                closeDrawer() },
        )

//Кнопка навигации "Настройка поворота"
        NavigationButton(
            iconResId = R.drawable.ic_nav_setting_rotation,
            labelResId = R.string.menu_setting_rotation,
            isSelected = route == AllDestinations.NAVIGATE_SETTING_ROTATION,
            onClick = {
                navigateToSettingRotation()
                closeDrawer() },
        )

//Кнопка навигации "Сервисное меню"
        NavigationButton(
            iconResId = R.drawable.ic_nav_service_menu,
            labelResId = R.string.menu_service,
            isSelected = route == AllDestinations.NAVIGATE_SERVICE_MENU,
            onClick = {
                navigateToServiceMenu()
                closeDrawer() },
        )

//Кнопка навигации "Диагностика"
        NavigationButton(
            iconResId = R.drawable.ic_nav_diagnostics,
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

//Кнопка навигации "Настройки Modbus"
        NavigationButton(
            iconResId = R.drawable.ic_nav_setting_modbus,
            labelResId = R.string.menu_ModbusTools,
            isSelected = route == AllDestinations.NAVIGATE_MODBUS_TOOLS,
            onClick = {
                navigateToModbusTools()
                closeDrawer() },
        )
    }
}


@Preview
@Composable
fun DrawerHeaderPreview() {
    AppDrawer(modifier = Modifier, route = AllDestinations.NAVIGATE_TO_HOME)
}