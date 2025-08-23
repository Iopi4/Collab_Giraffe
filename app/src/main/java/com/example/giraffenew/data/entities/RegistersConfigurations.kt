package com.example.giraffenew.data.entities

import kotlinx.serialization.json.Json

/**
 * Утилитарный класс для управления всеми конфигурациями регистров
 */
object RegistersConfigurations {
    private val configurations = mapOf(
        TopBarRegisterConfiguration.NAME_TOP_BAR to TopBarRegisterConfiguration.createConfiguration(),
        UiHomeScreenRegisterConfiguration.NAME_HOME to UiHomeScreenRegisterConfiguration.createConfiguration(),
        UiInitialSetupRegisterConfiguration.NAME_INITIAL_SETUP to UiInitialSetupRegisterConfiguration.createConfiguration(),
        UiServiceMenuRegisterConfiguration.NAME_SERVICE_MENU to UiServiceMenuRegisterConfiguration.createConfiguration()
    )

    fun getAllConfigurations(): List<RegisterConfiguration> {
        return configurations.values.toList()
    }

    fun getConfigurationByName(name: String): RegisterConfiguration? {
        return configurations[name]
    }

    fun getRegistersByConfiguration(name: String): List<RegisterInfo> {
        return when (name) {
            TopBarRegisterConfiguration.NAME_TOP_BAR -> TopBarRegisterConfiguration.REGISTERS
            UiHomeScreenRegisterConfiguration.NAME_HOME -> UiHomeScreenRegisterConfiguration.REGISTERS
            UiInitialSetupRegisterConfiguration.NAME_INITIAL_SETUP -> UiInitialSetupRegisterConfiguration.REGISTERS
            UiServiceMenuRegisterConfiguration.NAME_SERVICE_MENU -> UiServiceMenuRegisterConfiguration.REGISTERS
            else -> emptyList()
        }
    }

    fun getConfigurationNameByAddress(address: Int): String? {
        return when (address) {
            in 90..99 -> TopBarRegisterConfiguration.NAME_TOP_BAR
            in 100..104 -> UiHomeScreenRegisterConfiguration.NAME_HOME
            in 105..108 -> UiInitialSetupRegisterConfiguration.NAME_INITIAL_SETUP
            in 900..980 ->UiServiceMenuRegisterConfiguration.NAME_SERVICE_MENU
            else -> null
        }
    }
}


/**
 * Главная конфигурация регистров (адреса 90-99)
 * Эти регистры считываются всегда, один раз в секунду
 */
object TopBarRegisterConfiguration {
    const val NAME_TOP_BAR = "TopBar_configuration"

    val REGISTERS = listOf(
        RegisterInfo(90, "gv_wYear_MW", "WORD16", "Год"),
        RegisterInfo(91, "gv_wMonth_MW", "WORD16", "Месяц"),
        RegisterInfo(94, "gv_wDay_MW", "WORD16", "День"),
        RegisterInfo(95, "gv_wHour_MW", "WORD16", "Часы"),
        RegisterInfo(98, "gv_wMinute_MW", "WORD16", "Минуты"),
        RegisterInfo(99, "gv_wSeconds_MW", "WORD16", "Секунды"),
        RegisterInfo(150, "Crane_No_MW", "WORD16", "Заводской номер крана"),
        RegisterInfo(193, "Curr_Gruz_Zapas_MW", "WORD16", "выбор кратности запасовки, =TRUE -4х кратная, =FALSE -2х кратная"),
        RegisterInfo(194, "Curr_Gruz_Fun_MW", "WORD16", "Текущая выбранная грузовая характеристика")
    )

    val JSON_CONFIG = Json.encodeToString(RegistersConfiguration.serializer(), RegistersConfiguration(REGISTERS))

    fun createConfiguration(): RegisterConfiguration {
        return RegisterConfiguration(
            name = "TopBar_configuration",
            jsonConfiguration = JSON_CONFIG,
            isActive = true
        )
    }
}

/**
 * Конфигурация UI №1 (HomeScreen UI)
 * Регистры для работы с UI главного экрана Home
 * //HomeScreenViewModel
 */
object UiHomeScreenRegisterConfiguration {
    const val NAME_HOME = "ui_configuration_HomeScreen"

    val REGISTERS = listOf(
        //addressesCraneOperatingParameters
        RegisterInfo(152, "Hoist_StatusWord_MW", "WORD16", "Слово состояния ПЧ подъема"),
        RegisterInfo(153, "Hoist_ControlWord_MW", "WORD16", "Слово управления ПЧ подъема"),
        RegisterInfo(155, "Hoist_TargetVelocity_MW", "WORD16", "Заданная скорость вращения подъема"),
        RegisterInfo(158, "Hoist_ControlEffort_MW", "WORD16", "Текущая скорость вращения подъема"),
        RegisterInfo(159, "Hoist_MotorCurrent_MW", "WORD16", "Ток двигателя подъема"),
        RegisterInfo(162,  "Crane_Position_MW",  "WORD16",  "Текущее положение крана *0,1м"),
        RegisterInfo(163,  "Qnom_MW",  "WORD16",  "Номинальная грузоподъемность в кг"),
        RegisterInfo(135, "VoltageL1L2_MW", "WORD16", "Линейное напряжение L1-L2"),
        RegisterInfo(136, "VoltageL2L3_MW", "WORD16", "Линейное напряжение L2-L3"),
        RegisterInfo(137, "VoltageL3L1_MW", "WORD16", "Линейное напряжение L3-L1"),
        RegisterInfo(317, "HMI_Weight_Percent_MW", "WORD16", "Текущий вес в процентах"),
        //addressesCraneParameterScale
        RegisterInfo(318, "HMI_Hoist_Max_Position_MW", "WORD16", "Максимальное положение крюка"),
        RegisterInfo(319, "HMI_Max_Crane_Position_MW", "WORD16", "Максимальное положение крана"),
        //addressesCraneMechanismsCondition
        RegisterInfo(711,  "HMI_Weight_Sensor_Err_MW",  "WORD16",  "Cбой датчика грузоподъемности"),
        RegisterInfo(320, "HMI_Trolley_Fwd_Status_MW", "WORD16", "Статус движения тележки вперед на HMI"),
        RegisterInfo(321, "HMI_Trolley_Rev_Status_MW", "WORD16", "Статус движения тележки назад на HMI"),
        RegisterInfo(322, "HMI_Hoist_Up_Status_MW", "WORD16", "Статус подъема груза на HMI"),
        RegisterInfo(323, "HMI_Hoist_Down_Status_MW", "WORD16", "Статус опускания груза на HMI"),
        RegisterInfo(324, "HMI_Slew_Right_Status_MW", "WORD16", "Статус поворота вправо на HMI"),
        RegisterInfo(325, "HMI_Slew_Left_Status_MW", "WORD16", "Статус поворота влево на HMI"),
        RegisterInfo(326, "HMI_Crane_Right_Status_MW", "WORD16", "Статус движения крана вправо на HMI"),
        RegisterInfo(327, "HMI_Crane_Left_Status_MW", "WORD16", "Статус движения крана влево на HMI"),
        RegisterInfo(328, "HMI_EM_Vihod_1_Crane_Right_MW", "WORD16", "Статус выхода из полигона движением крана вправо"),
        RegisterInfo(329, "HMI_EM_Vihod_2_Crane_Left_MW", "WORD16", "Статус выхода из полигона движением крана влево"),
        RegisterInfo(330, "HMI_EM_Vihod_3_Slew_Left_MW", "WORD16", "Статус выхода из полигона поворотом налево"),
        RegisterInfo(331, "HMI_EM_Vihod_4_Slew_Right_MW", "WORD16", "Статус выхода из полигона поворотом направо"),
        RegisterInfo(332, "HMI_EM_Vihod_5_Trolley_Fwd_MW", "WORD16", "Статус выхода из полигона движением тележки вперед"),
        RegisterInfo(333, "HMI_EM_Vihod_6_Trolley_Rev_MW", "WORD16", "Статус выхода из полигона движением тележки назад"),
        RegisterInfo(334, "HMI_EM_Vihod_7_Hoist_Up_MW", "WORD16", "Статус выхода из полигона подъемом груза"),
        RegisterInfo(335, "HMI_EM_Vihod_8_Hoist_Down_MW", "WORD16", "Статус выхода из полигона опусканием груза"),
        RegisterInfo(336, "HMI_Anemometer_Status_MW", "WORD16", "Статус анемометра"),
        RegisterInfo(337, "HMI_AlarmLamp_Status_MW", "WORD16", "Статус аварийной лампы")
    )

    val JSON_CONFIG = Json.encodeToString(RegistersConfiguration.serializer(), RegistersConfiguration(REGISTERS))

    fun createConfiguration(): RegisterConfiguration {
        return RegisterConfiguration(
            name = "ui_configuration_HomeScreen",
            jsonConfiguration = JSON_CONFIG,
            isActive = false
        )
    }
}

/**
 * Конфигурация UI №2 (InitialSetupViewModel)
 * Регистры для работы с UI экрана начальные настройки
 */
object UiInitialSetupRegisterConfiguration {
    const val NAME_INITIAL_SETUP = "ui_configuration_InitialSetup"

    val REGISTERS = listOf(
        // Запись даты и времени в ПЛК
        RegisterInfo(842, "HMI_Write_v_xCmdSetDateTime_MW", "WORD16", "Команда установки времени"),
        RegisterInfo(843, "HMI_Write_i_uiYear_MW", "WORD16", "Год для установки"),
        RegisterInfo(844, "HMI_Write_i_uiMonth_MW", "WORD16", "Месяц для установки"),
        RegisterInfo(845, "HMI_Write_i_uiDay_MW", "WORD16", "День для установки"),
        RegisterInfo(846, "HMI_Write_i_uiHour_MW", "WORD16", "Час для установки"),
        RegisterInfo(847, "HMI_Write_i_uiMinute_MW", "WORD16", "Минута для установки"),
        // Запись в регистры даты ввода крана в эксплуатацию
        RegisterInfo(854, "HMI_Write_R_InstallDay_Crane_MW", "WORD16", "День ввода в эксплуатацию"),
        RegisterInfo(855, "HMI_Write_R_InstallMonth_Crane_MW", "WORD16", "Месяц ввода в эксплуатацию"),
        RegisterInfo(856, "HMI_Write_R_InstallYear_Crane_MW", "WORD16", "Год ввода в эксплуатацию"),
        // Запись даты регистрации крана в Ростехнадзоре
        RegisterInfo(851, "HMI_Write_R_InstallDay_RTN_Crane_MW", "WORD16", "День регистрации в Ростехнадзоре"),
        RegisterInfo(852, "HMI_Write_R_InstallMonth_RTN_Crane_MW", "WORD16", "Месяц регистрации в Ростехнадзоре"),
        RegisterInfo(853, "HMI_Write_R_InstallYear_RTN_Crane_MW", "WORD16", "Год регистрации в Ростехнадзоре"),
        // Запись даты установки СБКБ на кран
        RegisterInfo(858, "HMI_Write_R_InstallDaySBKB_MW", "WORD16", "День установки СБКБ"),
        RegisterInfo(859, "HMI_Write_R_InstallMonthSBKB_MW", "WORD16", "Месяц установки СБКБ"),
        RegisterInfo(860, "HMI_Write_R_InstallYearSBKB_MW", "WORD16", "Год установки СБКБ"),
        // Запись модели крана, зав. № и № СБКБ
        RegisterInfo(848, "HMI_Write_sR_CraneType_MW", "UINT16", "Тип крана"),
        RegisterInfo(849, "HMI_Write_rQnom_MW", "WORD16", "Номинальная грузоподъемность"),
        RegisterInfo(850, "HMI_Write_sR_Crane_No_MW", "WORD16", "Заводской номер крана")
    )

    val JSON_CONFIG = Json.encodeToString(RegistersConfiguration.serializer(), RegistersConfiguration(REGISTERS))

    fun createConfiguration(): RegisterConfiguration {
        return RegisterConfiguration(
            name = "ui_configuration_InitialSetup",
            jsonConfiguration = JSON_CONFIG,
            isActive = false
        )
    }
}


/**
 * Конфигурация UI №7 (ServiceMenuViewModel)
 * Регистры для работы с UI экрана начальные настройки
 */
object UiServiceMenuRegisterConfiguration {
    const val NAME_SERVICE_MENU = "ui_configuration_ServiceMenu"

    val REGISTERS = listOf(
        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА ВЕТРА
        RegisterInfo(980, "HMI_Write_rWindSpeedLvl1_MW", "WORD16", "Скорость ветра для порога №1, м/с, включение желтой индикации"),
        RegisterInfo(981, "HMI_Write_rWindSpeedLvl2_MW", "WORD16", "Скорость ветра для порога №2, м/с, включение красной индикации"),
        RegisterInfo(982, "HMI_Write_rWindSpeedMontageLvl_MW", "WORD16", "Скорость ветра для порога №2, м/с, включение красной индикации/Монтажный режим"),
        RegisterInfo(983, "HMI_Write_iAnemTimeSmooth_MW", "WORD16", "Время усреднения показаний анемометра, 5 сек"),
        RegisterInfo(984, "HMI_Write_rWindSpeedLvlClbr_MW", "WORD16", "Значение калибровки скорости ветра"),
        RegisterInfo(163, "Wind_MW", "WORD16", "Текущие показания ветра *0,1м/с"),
        RegisterInfo(381, "HMI_AnemometerSensor_MW", "WORD16", "Анемометр"),
        RegisterInfo(336, "HMI_Anemometer_Status_MW", "WORD16", "Статус анемометра"),
        RegisterInfo(985, "HMI_Write_rK_MW", "WORD16", "Коэффициент калибровки"),
        RegisterInfo(986, "HMI_Write_xAnemomClbr_MW", "WORD16", "Команда калибровать анемометр"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКОВ ПОЛОЖЕНИЯ
        RegisterInfo(990, "HMI_Write_rAbsEnc_Ratio_Hoist_MW", "WORD16", "Коэффициент передачи энкодера подъема"),
        RegisterInfo(991, "HMI_Write_rAbsEnc_Ratio_Slew_MW", "WORD16", "Коэффициент передачи энкодера поворота"),
        RegisterInfo(992, "HMI_Write_rAbsEnc_Ratio_Trolley_MW", "WORD16", "Коэффициент передачи энкодера тележки"),
        RegisterInfo(993, "HMI_Write_rAbsEnc_Ratio_Crane_MW", "WORD16", "Коэффициент передачи энкодера крана"),

        RegisterInfo(50, "HMI_Write_udiHoistAbsEncMaxDeltaEn_MD", "WORD32", "Максимальное рассогласование абс. энкодера подъема"),
        RegisterInfo(52, "HMI_Write_udiSlewAbsEncMaxDeltaEn_MD", "WORD32", "Максимальное рассогласование абс. энкодера поворота"),
        RegisterInfo(54, "HMI_Write_udiTrolleyAbsEncMaxDeltaEn_MD", "WORD32", "Максимальное рассогласование абс. энкодера тележки"),
        RegisterInfo(56, "HMI_Write_udiCraneAbsEncMaxDeltaEn_MD", "WORD32", "Максимальное рассогласование абс. энкодера хода крана"),

        RegisterInfo(994, "HMI_Write_xAbsEncDeltaControlEn_MW", "WORD16", "Включить контроль максимального рассогласования абсолютных энкодеров"),
        RegisterInfo(995, "HMI_Write_xDebug_Mode_Hoist_MW", "WORD16", "Флаг установки режима наладки механизма подъема при выходе датчика из строя"),
        RegisterInfo(996, "HMI_Write_xDebug_Mode_Slew_MW", "WORD16", "Флаг установки режима наладки механизма поворота при выходе датчика из строя"),

        RegisterInfo(755, "HMI_xAbsEncDeltaControlEn_MW", "WORD16", "Флаг контроль максимального рассогласования абсолютных энкодеров"),
        RegisterInfo(756, "HMI_xDebug_Mode_Hoist_MW", "WORD16", "Флаг установки режима наладки механизма подъема при выходе датчика из строя"),
        RegisterInfo(757, "HMI_xDebug_Mode_Slew_MW", "WORD16", "Флаг установки режима наладки механизма поворота при выходе датчика из строя"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА УСИЛИЯ
        RegisterInfo(1200, "LoadCell_MinWeight_MW", "WORD16", "Минимальное значение измерения веса"),
        RegisterInfo(1201, "LoadCell_MaxWeight_MW", "WORD16", "Максимальное значение измерения веса"),
        RegisterInfo(1202, "LoadCell_SmoothingDepth_MW", "WORD16", "Глубина сглаживания измерения веса"),
        RegisterInfo(1203, "LoadCell_OverSpeedThreshold_MW", "WORD16", "Уставка массы для разрешения повышенной скорости"),


        //СЕРВИСНЫЙ РЕЖИМ. СИСТЕМНАЯ НАСТРОЙКА УРОВЕНЬ ИНЖЕНЕР
        RegisterInfo(1204, "HMI_Write_xDataLogErrStopHoist_MW", "WORD16", "Запрет высоких скоростей при ошибке регистратора"),
        RegisterInfo(1205, "HMI_Write_xPasswordOrNaladka_MW", "WORD16", "Режим авторизации наладки"),
        RegisterInfo(1206, "HMI_Write_xFOURpoligonsEn_MW", "WORD16", "Режим работы полигонов (4x20 или 2x10 точек)"),
        RegisterInfo(1207, "HMI_Write_xWithoutRringCurrentCollector_MW", "WORD16", "Режим ограничения поворота без токосъемника"),
        RegisterInfo(1208, "HMI_Write_xZoneACC_DEC_Activ_MW", "WORD16", "Активация зон регулирования ускорений/замедлений"),

        RegisterInfo(750, "HMI_xDataLogErrStopHoist_MW", "WORD16", "Флаг. Запрет высоких скоростей при ошибке регистратора"),
        RegisterInfo(751, "HMI_xPasswordOrNaladka_MW", "WORD16", "Флаг.Режим авторизации наладки"),
        RegisterInfo(752, "HMI_xFOURpoligonsEn_MW", "WORD16", "Флаг.Режим работы полигонов (4x20 или 2x10 точек)"),
        RegisterInfo(753, "HMI_xWithoutRringCurrentCollector_MW", "WORD16", "Флаг.Выбор исполнения крана без кольцеового токосъемника, режим ограничения поворота"),
        RegisterInfo(754, "HMI_xZoneACC_DEC_Activ_MW", "WORD16", "Флаг. Активация зон регулирования ускорений/замедлений"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ПРИВОДА ПОДЪЕМА
        RegisterInfo(940, "HMI_Write_Hoist_wSpdRefButton_Value_MW", "WORD16", "Скорость при нажатии кнопки"),
        RegisterInfo(941, "HMI_Write_Hoist_wSpdRef1_Value_MW", "WORD16", "Скорость 1 подъема"),
        RegisterInfo(942, "HMI_Write_Hoist_wSpdRef2_Value_MW", "WORD16", "Скорость 2 подъема"),
        RegisterInfo(943, "HMI_Write_Hoist_wSpdRef3_Value_MW", "WORD16", "Скорость 3 подъема"),
        RegisterInfo(944, "HMI_Write_Hoist_wSpdRef4_Value_MW", "WORD16", "Скорость 4 подъема"),
        RegisterInfo(945, "HMI_Write_Hoist_iSpdRef_MAX_Value_MW", "WORD16", "Значение максимальной скорости двигателя подъема"),
        RegisterInfo(946, "HMI_Write_Hoist_rHoist_Acc_MW", "WORD16", "Время разгона подъема, сек"),
        RegisterInfo(947, "HMI_Write_Hoist_rHoist_Dec_MW", "WORD16", "Время торможения подъема, сек"),

        RegisterInfo(950, "HMI_Write_Hoist_wSpdRef1Strela_MW", "WORD16", "Скорость подъема 1 в режиме монтаж"),
        RegisterInfo(951, "HMI_Write_Hoist_wSpdRef2Strela_MW", "WORD16", "Скорость подъема 2 в режиме монтаж"),
        RegisterInfo(952, "HMI_Write_Hoist_rStrela_Acc_MW", "WORD16", "Время разгона в режиме монтаж, сек"),
        RegisterInfo(953, "HMI_Write_Hoist_rStrela_Dec_MW", "WORD16", "Время торможения в режиме монтаж, сек"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ПРИВОДА ПОВОРОТА
        RegisterInfo(955, "HMI_Write_Slew_wDrvSpdLSP_MW", "WORD16", "Снижение скорости поворота"),
        RegisterInfo(956, "HMI_Write_Slew_wSpdRef1_Value_MW", "WORD16", "Скорость 1 поворота"),
        RegisterInfo(957, "HMI_Write_Slew_wSpdRef2_Value_MW", "WORD16", "Скорость 2 поворота"),
        RegisterInfo(958, "HMI_Write_Slew_wSpdRef3_Value_MW", "WORD16", "Скорость 3 поворота"),
        RegisterInfo(959, "HMI_Write_Slew_wSpdRef4_Value_MW", "WORD16", "Скорость 4 поворота"),
        RegisterInfo(968, "HMI_Write_Slew_wACCnom_MW", "WORD16", "Время разгона поворота"),
        RegisterInfo(969, "HMI_Write_Slew_wDECnom_MW", "WORD16", "Время торможения поворота"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ПРИВОДА ТЕЛЕЖКИ
        RegisterInfo(960, "HMI_Write_Trolley_wSpdRef1_Value_MW", "WORD16", "Скорость 1 тележки"),
        RegisterInfo(961, "HMI_Write_Trolley_wSpdRef2_Value_MW", "WORD16", "Скорость 2 тележки"),
        RegisterInfo(962, "HMI_Write_Trolley_wSpdRef3_Value_MW", "WORD16", "Скорость 3 тележки"),
        RegisterInfo(963, "HMI_Write_Trolley_wSpdRef4_Value_MW", "WORD16", "Скорость 4 тележки"),
        RegisterInfo(964, "HMI_Write_rTrolley_Acc_MW", "WORD16", "Время разгона тележки, сек"),
        RegisterInfo(965, "HMI_Write_rTrolley_Dec_MW", "WORD16", "Время торможения тележки, сек"),
        RegisterInfo(966, "HMI_Write_wTrolleyLsp_MW", "WORD16", "Пониженная скорость тележки"),
        RegisterInfo(967, "HMI_Write_rTrolleyOverspd_MW", "WORD16", "Значение веса груза при превышении которого запрещена сверхсинхронная работа тележки")
    )

    val JSON_CONFIG = Json.encodeToString(RegistersConfiguration.serializer(), RegistersConfiguration(REGISTERS))

    fun createConfiguration(): RegisterConfiguration {
        return RegisterConfiguration(
            name = "ui_configuration_ServiceMenu",
            jsonConfiguration = JSON_CONFIG,
            isActive = false
        )
    }
}