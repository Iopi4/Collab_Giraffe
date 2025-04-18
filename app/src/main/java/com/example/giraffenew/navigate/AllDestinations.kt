package com.example.giraffenew.navigate

import androidx.navigation.NavHostController
import com.example.giraffenew.navigate.AllDestinations.nav_home
import com.example.giraffenew.navigate.AllDestinations.nav_initial_setup
import com.example.giraffenew.navigate.AllDestinations.nav_setting_load_capacity
import com.example.giraffenew.navigate.AllDestinations.nav_setting_sensors
import com.example.giraffenew.navigate.AllDestinations.nav_coordinate_protection
import com.example.giraffenew.navigate.AllDestinations.nav_setting_rotation
import com.example.giraffenew.navigate.AllDestinations.nav_service_menu
import com.example.giraffenew.navigate.AllDestinations.nav_diagnostics
import com.example.giraffenew.navigate.AllDestinations.nav_parameter_logger
import com.example.giraffenew.navigate.AllDestinations.nav_technical_maintenance
import com.example.giraffenew.navigate.AllDestinations.nav_accident_and_warning_log
import com.example.giraffenew.navigate.AllDestinations.nav_login
import com.example.giraffenew.navigate.AllDestinations.nav_modbus_tools


object AllDestinations {
    //Название окна в верхней панели
    const val nav_home = "Главный экран"
    const val nav_initial_setup = "Начальные настройки"
    const val nav_setting_load_capacity = "Настройка грузоподъемности"
    const val nav_setting_sensors = "Настройка датчиков положения"
    const val nav_coordinate_protection = "Координатная защита"
    const val nav_setting_rotation = "Настройка поворота"
    const val nav_service_menu = "Сервисное меню"
    const val nav_diagnostics = "Диагностика"
    const val nav_parameter_logger = "Регистратор параметров"
    const val nav_technical_maintenance = "Техническое обслуживание"
    const val nav_accident_and_warning_log = "Журнал аварий и предупреждений"
    const val nav_login = "Авторизация пользователя"
    const val nav_modbus_tools = "Настройка Modbus"
}
class AppNavigationActions(private val navController: NavHostController) {

    fun navigateToHome() {
        navController.navigate(nav_home) {
            popUpTo(nav_home)
        }
    }

    fun navigateTo_InitialSetup() {
        navController.navigate(nav_initial_setup) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_SettingLoadCapacity() {
        navController.navigate(nav_setting_load_capacity) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_SettingSensors() {
        navController.navigate(nav_setting_sensors) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_CoordinateProtection() {
        navController.navigate(nav_coordinate_protection) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_SettingRotation() {
        navController.navigate(nav_setting_rotation) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_ServiceMenu() {
        navController.navigate(nav_service_menu) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_Diagnostics() {
        navController.navigate(nav_diagnostics) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_ParameterLogger() {
        navController.navigate(nav_parameter_logger) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_TechnicalMaintenance() {
        navController.navigate(nav_technical_maintenance) {
            launchSingleTop = true
            restoreState = true
        }
    }
    fun navigateTo_AccidentAndWarningLog() {
        navController.navigate(nav_accident_and_warning_log) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_Login() {
        navController.navigate(nav_login) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_ModbusTools() {
        navController.navigate(nav_modbus_tools) {
            launchSingleTop = true
            restoreState = true
        }
    }
}
