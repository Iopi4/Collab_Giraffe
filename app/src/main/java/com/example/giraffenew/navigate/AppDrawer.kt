package com.example.giraffenew.navigate

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            isSelected = route == AllDestinations.nav_home,
            onClick = {
                navigateToHome()
                closeDrawer() },
            )

//Кнопка навигации "Начальные настройки"
        NavigationButton(
            iconResId = R.drawable.ic_nav_initial_setup,
            labelResId = R.string.menu_initial_setup,
            isSelected = route == AllDestinations.nav_initial_setup,
            onClick = {
                navigateToSettings()
                closeDrawer() },
        )

//Кнопка навигации "Настройка грузоподъемности"
        NavigationButton(
            iconResId = R.drawable.ic_nav_setting_load_capacity,
            labelResId = R.string.menu_setting_load_capacity,
            isSelected = route == AllDestinations.nav_setting_load_capacity,
            onClick = {
                navigateToSettingLoadCapacity()
                closeDrawer() },
        )

//Кнопка навигации "Настройка датчиков"
        NavigationButton(
            iconResId = R.drawable.ic_nav_setting_sensors,
            labelResId = R.string.menu_setting_sensors,
            isSelected = route == AllDestinations.nav_setting_sensors,
            onClick = {
                navigateToSettingSensor()
                closeDrawer() },
        )

//Кнопка навигации "Координатная защита"
        NavigationButton(
            iconResId = R.drawable.ic_nav_coordinate_protection,
            labelResId = R.string.menu_coordinate_protection,
            isSelected = route == AllDestinations.nav_coordinate_protection,
            onClick = {
                navigateToCoordinateProtection()
                closeDrawer() },
        )

//Кнопка навигации "Настройка поворота"
        NavigationButton(
            iconResId = R.drawable.ic_nav_setting_rotation,
            labelResId = R.string.menu_setting_rotation,
            isSelected = route == AllDestinations.nav_setting_rotation,
            onClick = {
                navigateToSettingRotation()
                closeDrawer() },
        )

//Кнопка навигации "Сервисное меню"
        NavigationButton(
            iconResId = R.drawable.ic_nav_service_menu,
            labelResId = R.string.menu_service,
            isSelected = route == AllDestinations.nav_service_menu,
            onClick = {
                navigateToServiceMenu()
                closeDrawer() },
        )

//Кнопка навигации "Диагностика"
        NavigationButton(
            iconResId = R.drawable.ic_nav_diagnostics,
            labelResId = R.string.menu_diagnostic,
            isSelected = route == AllDestinations.nav_diagnostics,
            onClick = {
                navigateToDiagnostics()
                closeDrawer() },
        )

//Кнопка навигации "Регистратор параметров"
        NavigationButton(
            iconResId = R.drawable.ic_nav_regist_param,
            labelResId = R.string.menu_parameter_logger,
            isSelected = route == AllDestinations.nav_parameter_logger,
            onClick = {
                navigateToParameterLogger()
                closeDrawer() },
        )


//Кнопка навигации "Техническое обслуживание"
        NavigationButton(
            iconResId = R.drawable.ic_nav_technical_maintenance,
            labelResId = R.string.menu_technical_maintenance,
            isSelected = route == AllDestinations.nav_technical_maintenance,
            onClick = {
                navigateToTechnicalMaintenance()
                closeDrawer() },
        )

//Кнопка навигации "Журнал Аварий и Предупреждений"
        NavigationButton(
            iconResId = R.drawable.ic_nav_accident,
            labelResId = R.string.accident_and_warning_log,
            isSelected = route == AllDestinations.nav_accident_and_warning_log,
            onClick = {
                navigateToAccidentWarninglog()
                closeDrawer() },
        )

//Кнопка навигации "Авторизация пользователя"
        NavigationButton(
            iconResId = R.drawable.ic_nav_login,
            labelResId = R.string.menu_Login,
            isSelected = route == AllDestinations.nav_login,
            onClick = {
                navigateToLogin()
                closeDrawer() },
        )

//Кнопка навигации "Настройки Modbus"
        NavigationButton(
            iconResId = R.drawable.ic_nav_setting_modbus,
            labelResId = R.string.menu_ModbusTools,
            isSelected = route == AllDestinations.nav_modbus_tools,
            onClick = {
                navigateToModbusTools()
                closeDrawer() },
        )
    }
}







@Preview
@Composable
fun DrawerHeaderPreview() {
    AppDrawer(modifier = Modifier, route = AllDestinations.nav_home)
}