package com.example.giraffenew.navigate

import androidx.navigation.NavHostController
import com.example.giraffenew.navigate.AllDestinations.NAVIGATE_TO_HOME
import com.example.giraffenew.navigate.AllDestinations.NAVIGATE_INITIAL_SETUP
import com.example.giraffenew.navigate.AllDestinations.NAVIGATE_SETTING_LOAD_CAPASITY
import com.example.giraffenew.navigate.AllDestinations.NAVIGATE_SETTING_SENSORS
import com.example.giraffenew.navigate.AllDestinations.NAVIGATE_COORDINATE_PROTECTION
import com.example.giraffenew.navigate.AllDestinations.NAVIGATE_SETTING_ROTATION
import com.example.giraffenew.navigate.AllDestinations.NAVIGATE_SERVICE_MENU
import com.example.giraffenew.navigate.AllDestinations.NAVIGATE_DIAGNOSTICS
import com.example.giraffenew.navigate.AllDestinations.NAVIGATE_PARAMETER_LOGGER
import com.example.giraffenew.navigate.AllDestinations.NAVIGATE_TECHNICAL_MAINTENANCE
import com.example.giraffenew.navigate.AllDestinations.NAVIGATE_ACCIDENT_AND_WARNING_LOG
import com.example.giraffenew.navigate.AllDestinations.NAVIGATE_EXCEPTION
import com.example.giraffenew.navigate.AllDestinations.NAVIGATE_LOGIN
import com.example.giraffenew.navigate.AllDestinations.NAVIGATE_MODBUS_TOOLS


object AllDestinations {
    //Название окна в верхней панели
    const val NAVIGATE_TO_HOME: String = "Главный экран"
    const val NAVIGATE_INITIAL_SETUP = "Начальные настройки"
    const val NAVIGATE_SETTING_LOAD_CAPASITY = "Настройка грузоподъемности"
    const val NAVIGATE_SETTING_SENSORS = "Настройка датчиков"
    const val NAVIGATE_COORDINATE_PROTECTION = "Координатная защита"
    const val NAVIGATE_SETTING_ROTATION = "Настройка поворота"
    const val NAVIGATE_SERVICE_MENU = "Сервисное меню"
    const val NAVIGATE_DIAGNOSTICS = "Диагностика"
    const val NAVIGATE_PARAMETER_LOGGER = "Регистратор параметров"
    const val NAVIGATE_TECHNICAL_MAINTENANCE = "Техническое обслуживание"
    const val NAVIGATE_ACCIDENT_AND_WARNING_LOG = "Журнал событий"
    const val NAVIGATE_LOGIN = "Авторизация пользователя"
    const val NAVIGATE_MODBUS_TOOLS = "Настройка Modbus"
    const val NAVIGATE_EXCEPTION = "Ошибки Android"
}
class AppNavigationActions(private val navController: NavHostController) {

    fun navigateToHome() {
        navController.navigate(NAVIGATE_TO_HOME) {
            popUpTo(NAVIGATE_TO_HOME)
        }
    }

    fun navigateTo_InitialSetup() {
        navController.navigate(NAVIGATE_INITIAL_SETUP) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_SettingLoadCapacity() {
        navController.navigate(NAVIGATE_SETTING_LOAD_CAPASITY) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_SettingSensors() {
        navController.navigate(NAVIGATE_SETTING_SENSORS) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_CoordinateProtection() {
        navController.navigate(NAVIGATE_COORDINATE_PROTECTION) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_SettingRotation() {
        navController.navigate(NAVIGATE_SETTING_ROTATION) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_ServiceMenu() {
        navController.navigate(NAVIGATE_SERVICE_MENU) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_Diagnostics() {
        navController.navigate(NAVIGATE_DIAGNOSTICS) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_ParameterLogger() {
        navController.navigate(NAVIGATE_PARAMETER_LOGGER) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_TechnicalMaintenance() {
        navController.navigate(NAVIGATE_TECHNICAL_MAINTENANCE) {
            launchSingleTop = true
            restoreState = true
        }
    }
    fun navigateTo_AccidentAndWarningLog() {
        navController.navigate(NAVIGATE_ACCIDENT_AND_WARNING_LOG) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_Login() {
        navController.navigate(NAVIGATE_LOGIN) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateTo_Exceptions() {
        navController.navigate(NAVIGATE_EXCEPTION) {
            launchSingleTop = true
            restoreState = true
        }
    }

    //fun navigateTo_ModbusTools() {
        //navController.navigate(NAVIGATE_MODBUS_TOOLS) {
            //launchSingleTop = true
            //restoreState = true
        //}
    //}


}
