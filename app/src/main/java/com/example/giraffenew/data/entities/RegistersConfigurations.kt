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
        UIServiceMenuRegisterConfiguration.NAME_SERVICE_MENU to UIServiceMenuRegisterConfiguration.createConfiguration(),
        UISettingLoadCapacityConfiguration.NAME_SETTING_LOAD_CAPACITY to UISettingLoadCapacityConfiguration.createConfiguration(),
        UISettingSensorsConfiguration.NAME_SETTING_SENSORS to UISettingSensorsConfiguration.createConfiguration(),
        UICoordinateProtectionConfiguration.NAME_COORDINATE_PROTECTION to UICoordinateProtectionConfiguration.createConfiguration()
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
            UIServiceMenuRegisterConfiguration.NAME_SERVICE_MENU -> UIServiceMenuRegisterConfiguration.REGISTERS
            UISettingLoadCapacityConfiguration.NAME_SETTING_LOAD_CAPACITY -> UISettingLoadCapacityConfiguration.REGISTERS
            UISettingSensorsConfiguration.NAME_SETTING_SENSORS->UISettingSensorsConfiguration.REGISTERS
            UICoordinateProtectionConfiguration.NAME_COORDINATE_PROTECTION -> UICoordinateProtectionConfiguration.REGISTERS
            else -> emptyList()
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
        RegisterInfo(194, "Curr_Gruz_Fun_MW", "WORD16", "Текущая выбранная грузовая характеристика"),
        RegisterInfo(194, "Curr_Gruz_Fun_MW", "WORD16", "Текущая выбранная грузовая характеристика"),
        RegisterInfo(367, "HMI_CraneSerialNumber_MW", "WORD16", "Заводской номер крана")
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
        RegisterInfo(152, "Qnom_MW", "WORD16", "Номинальная грузоподъемность в кг"),
        RegisterInfo(153, "Weight_MW", "WORD16", "Текущая грузоподъемность в кг"),
        RegisterInfo(155, "Trolley_Position_MW", "WORD16", "Текущее положение тележки на стреле *0,1м"),
        RegisterInfo(158, "Rotation_Position_MW", "WORD16", "Текущий угол поворота"),
        RegisterInfo(159, "Hoist_Position_MW", "WORD16", "Текущее положение подъема *0,1м"),
        RegisterInfo(160, "Hoist_Max_Position_MW", "WORD16", "Максимальное положение подъема *0,1м"),
        RegisterInfo(161, "Hoist_Min_Position_MW", "WORD16", "Минимальное положение подъема *0,1м"),
        RegisterInfo(162,  "Crane_Position_MW",  "WORD16",  "Текущее положение крана *0,1м"),
        RegisterInfo(163, "Wind_MW", "WORD16", "Текущие показания ветра *0,1м/с"),
        RegisterInfo(135, "VoltageL1L2_MW", "WORD16", "Линейное напряжение L1-L2"),
        RegisterInfo(136, "VoltageL2L3_MW", "WORD16", "Линейное напряжение L2-L3"),
        RegisterInfo(137, "VoltageL3L1_MW", "WORD16", "Линейное напряжение L3-L1"),
        RegisterInfo(336, "HMI_Anemometer_Status_MW", "WORD16", "статус датчика ветра (0-Rd, 1-Yl, 2-Gr) "),
        //addressesCraneParameterScale
        RegisterInfo(317, "HMI_Weight_Percent_MW", "WORD16", "Текущий вес в процентах"),
        RegisterInfo(318, "HMI_Hoist_Max_Position_MW", "WORD16", "Максимальное положение высоты крюка"),
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
        RegisterInfo(712, "HMI_Anemometer_Sensor_Err_MW", "WORD16", "Статус анемометра"),
        RegisterInfo(337, "HMI_AlarmLamp_Status_MW", "WORD16", "Cбой анемоментра"),

        //Crane SOZR
        RegisterInfo(400, "HMI_Line_Angle_MW", "WORD16", "Угол поворота стрелы на HMI"),
        RegisterInfo(401, "HMI_Trolley_Y_MW", "WORD16", "Координаты точки тележки для HMI по оси Y"),
        RegisterInfo(402, "HMI_Trolley_X_MW", "WORD16", "Координаты точки тележки для HMI по оси X"),
        RegisterInfo(403, "HMI_Circle_Size_MW", "WORD16", "Радиус круга отражающего траекторию тележки на HMI"),
        RegisterInfo(404, "HMI_Strela_Lenght_MW", "WORD16", "Длина отрезка стрелы для панели (имеет значение при наклоне стрелы)"),
        RegisterInfo(405, "HMI_P1_Color_MW", "WORD16", "Анимация цвета полигона ( 0-Синий (задан но не активен), 1-Зеленый (задан и активен), 2-желтый (приближение к границе), 3-красный (касание границы полигона))"),

        RegisterInfo(410, "HMI_SegmentLength_00_MW", "WORD16", "Длина сегмента №00"),
        RegisterInfo(411, "HMI_SegmentLength_01_MW", "WORD16", "Длина сегмента №01"),
        RegisterInfo(412, "HMI_SegmentLength_02_MW", "WORD16", "Длина сегмента №02"),
        RegisterInfo(413, "HMI_SegmentLength_03_MW", "WORD16", "Длина сегмента №03"),
        RegisterInfo(414, "HMI_SegmentLength_04_MW", "WORD16", "Длина сегмента №04"),
        RegisterInfo(415, "HMI_SegmentLength_05_MW", "WORD16", "Длина сегмента №05"),
        RegisterInfo(416, "HMI_SegmentLength_06_MW", "WORD16", "Длина сегмента №06"),
        RegisterInfo(417, "HMI_SegmentLength_07_MW", "WORD16", "Длина сегмента №07"),
        RegisterInfo(418, "HMI_SegmentLength_08_MW", "WORD16", "Длина сегмента №08"),
        RegisterInfo(419, "HMI_SegmentLength_09_MW", "WORD16", "Длина сегмента №09"),
        RegisterInfo(420, "HMI_SegmentLength_10_MW", "WORD16", "Длина сегмента №10"),
        RegisterInfo(421, "HMI_SegmentLength_11_MW", "WORD16", "Длина сегмента №11"),
        RegisterInfo(422, "HMI_SegmentLength_12_MW", "WORD16", "Длина сегмента №12"),
        RegisterInfo(423, "HMI_SegmentLength_13_MW", "WORD16", "Длина сегмента №13"),
        RegisterInfo(424, "HMI_SegmentLength_14_MW", "WORD16", "Длина сегмента №14"),
        RegisterInfo(425, "HMI_SegmentLength_15_MW", "WORD16", "Длина сегмента №15"),
        RegisterInfo(426, "HMI_SegmentLength_16_MW", "WORD16", "Длина сегмента №16"),
        RegisterInfo(427, "HMI_SegmentLength_17_MW", "WORD16", "Длина сегмента №17"),
        RegisterInfo(428, "HMI_SegmentLength_18_MW", "WORD16", "Длина сегмента №18"),
        RegisterInfo(429, "HMI_SegmentLength_19_MW", "WORD16", "Длина сегмента №19"),

        RegisterInfo(430, "HMI_PointPositionY_00_MW", "WORD16", "Координата Y точки №00"),
        RegisterInfo(431, "HMI_PointPositionY_01_MW", "WORD16", "Координата Y точки №01"),
        RegisterInfo(432, "HMI_PointPositionY_02_MW", "WORD16", "Координата Y точки №02"),
        RegisterInfo(433, "HMI_PointPositionY_03_MW", "WORD16", "Координата Y точки №03"),
        RegisterInfo(434, "HMI_PointPositionY_04_MW", "WORD16", "Координата Y точки №04"),
        RegisterInfo(435, "HMI_PointPositionY_05_MW", "WORD16", "Координата Y точки №05"),
        RegisterInfo(436, "HMI_PointPositionY_06_MW", "WORD16", "Координата Y точки №06"),
        RegisterInfo(437, "HMI_PointPositionY_07_MW", "WORD16", "Координата Y точки №07"),
        RegisterInfo(438, "HMI_PointPositionY_08_MW", "WORD16", "Координата Y точки №08"),
        RegisterInfo(439, "HMI_PointPositionY_09_MW", "WORD16", "Координата Y точки №09"),
        RegisterInfo(440, "HMI_PointPositionY_10_MW", "WORD16", "Координата Y точки №10"),
        RegisterInfo(441, "HMI_PointPositionY_11_MW", "WORD16", "Координата Y точки №11"),
        RegisterInfo(442, "HMI_PointPositionY_12_MW", "WORD16", "Координата Y точки №12"),
        RegisterInfo(443, "HMI_PointPositionY_13_MW", "WORD16", "Координата Y точки №13"),
        RegisterInfo(444, "HMI_PointPositionY_14_MW", "WORD16", "Координата Y точки №14"),
        RegisterInfo(445, "HMI_PointPositionY_15_MW", "WORD16", "Координата Y точки №15"),
        RegisterInfo(446, "HMI_PointPositionY_16_MW", "WORD16", "Координата Y точки №16"),
        RegisterInfo(447, "HMI_PointPositionY_17_MW", "WORD16", "Координата Y точки №17"),
        RegisterInfo(448, "HMI_PointPositionY_18_MW", "WORD16", "Координата Y точки №18"),
        RegisterInfo(449, "HMI_PointPositionY_19_MW", "WORD16", "Координата Y точки №19"),

        RegisterInfo(450, "HMI_PointPositionX_00_MW", "WORD16", "Координата X точки №00"),
        RegisterInfo(451, "HMI_PointPositionX_01_MW", "WORD16", "Координата X точки №01"),
        RegisterInfo(452, "HMI_PointPositionX_02_MW", "WORD16", "Координата X точки №02"),
        RegisterInfo(453, "HMI_PointPositionX_03_MW", "WORD16", "Координата X точки №03"),
        RegisterInfo(454, "HMI_PointPositionX_04_MW", "WORD16", "Координата X точки №04"),
        RegisterInfo(455, "HMI_PointPositionX_05_MW", "WORD16", "Координата X точки №05"),
        RegisterInfo(456, "HMI_PointPositionX_06_MW", "WORD16", "Координата X точки №06"),
        RegisterInfo(457, "HMI_PointPositionX_07_MW", "WORD16", "Координата X точки №07"),
        RegisterInfo(458, "HMI_PointPositionX_08_MW", "WORD16", "Координата X точки №08"),
        RegisterInfo(459, "HMI_PointPositionX_09_MW", "WORD16", "Координата X точки №09"),
        RegisterInfo(460, "HMI_PointPositionX_10_MW", "WORD16", "Координата X точки №10"),
        RegisterInfo(461, "HMI_PointPositionX_11_MW", "WORD16", "Координата X точки №11"),
        RegisterInfo(462, "HMI_PointPositionX_12_MW", "WORD16", "Координата X точки №12"),
        RegisterInfo(463, "HMI_PointPositionX_13_MW", "WORD16", "Координата X точки №13"),
        RegisterInfo(464, "HMI_PointPositionX_14_MW", "WORD16", "Координата X точки №14"),
        RegisterInfo(465, "HMI_PointPositionX_15_MW", "WORD16", "Координата X точки №15"),
        RegisterInfo(466, "HMI_PointPositionX_16_MW", "WORD16", "Координата X точки №16"),
        RegisterInfo(467, "HMI_PointPositionX_17_MW", "WORD16", "Координата X точки №17"),
        RegisterInfo(468, "HMI_PointPositionX_18_MW", "WORD16", "Координата X точки №18"),
        RegisterInfo(469, "HMI_PointPositionX_19_MW", "WORD16", "Координата X точки №19"),

        RegisterInfo(470, "HMI_SegmentAngle_00_01_MW", "WORD16", "Угол между сегментами 00-01"),
        RegisterInfo(471, "HMI_SegmentAngle_01_02_MW", "WORD16", "Угол между сегментами 01-02"),
        RegisterInfo(472, "HMI_SegmentAngle_02_03_MW", "WORD16", "Угол между сегментами 02-03"),
        RegisterInfo(473, "HMI_SegmentAngle_03_04_MW", "WORD16", "Угол между сегментами 03-04"),
        RegisterInfo(474, "HMI_SegmentAngle_04_05_MW", "WORD16", "Угол между сегментами 04-05"),
        RegisterInfo(475, "HMI_SegmentAngle_05_06_MW", "WORD16", "Угол между сегментами 05-06"),
        RegisterInfo(476, "HMI_SegmentAngle_06_07_MW", "WORD16", "Угол между сегментами 06-07"),
        RegisterInfo(477, "HMI_SegmentAngle_07_08_MW", "WORD16", "Угол между сегментами 07-08"),
        RegisterInfo(478, "HMI_SegmentAngle_08_09_MW", "WORD16", "Угол между сегментами 08-09"),
        RegisterInfo(479, "HMI_SegmentAngle_09_10_MW", "WORD16", "Угол между сегментами 09-10"),
        RegisterInfo(480, "HMI_SegmentAngle_10_11_MW", "WORD16", "Угол между сегментами 10-11"),
        RegisterInfo(481, "HMI_SegmentAngle_11_12_MW", "WORD16", "Угол между сегментами 11-12"),
        RegisterInfo(482, "HMI_SegmentAngle_12_13_MW", "WORD16", "Угол между сегментами 12-13"),
        RegisterInfo(483, "HMI_SegmentAngle_13_14_MW", "WORD16", "Угол между сегментами 13-14"),
        RegisterInfo(484, "HMI_SegmentAngle_14_15_MW", "WORD16", "Угол между сегментами 14-15"),
        RegisterInfo(485, "HMI_SegmentAngle_15_16_MW", "WORD16", "Угол между сегментами 15-16"),
        RegisterInfo(486, "HMI_SegmentAngle_16_17_MW", "WORD16", "Угол между сегментами 16-17"),
        RegisterInfo(487, "HMI_SegmentAngle_17_18_MW", "WORD16", "Угол между сегментами 17-18"),
        RegisterInfo(488, "HMI_SegmentAngle_18_19_MW", "WORD16", "Угол между сегментами 18-19"),
        RegisterInfo(489, "HMI_SegmentAngle_19_00_MW", "WORD16", "Угол между сегментами 19-00"),

        RegisterInfo(490, "HMI_SegmentVisibility_00_MW", "WORD16", "Видимость сегмента №00"),
        RegisterInfo(491, "HMI_SegmentVisibility_01_MW", "WORD16", "Видимость сегмента №01"),
        RegisterInfo(492, "HMI_SegmentVisibility_02_MW", "WORD16", "Видимость сегмента №02"),
        RegisterInfo(493, "HMI_SegmentVisibility_03_MW", "WORD16", "Видимость сегмента №03"),
        RegisterInfo(494, "HMI_SegmentVisibility_04_MW", "WORD16", "Видимость сегмента №04"),
        RegisterInfo(495, "HMI_SegmentVisibility_05_MW", "WORD16", "Видимость сегмента №05"),
        RegisterInfo(496, "HMI_SegmentVisibility_06_MW", "WORD16", "Видимость сегмента №06"),
        RegisterInfo(497, "HMI_SegmentVisibility_07_MW", "WORD16", "Видимость сегмента №07"),
        RegisterInfo(498, "HMI_SegmentVisibility_08_MW", "WORD16", "Видимость сегмента №08"),
        RegisterInfo(499, "HMI_SegmentVisibility_09_MW", "WORD16", "Видимость сегмента №09"),
        RegisterInfo(500, "HMI_SegmentVisibility_10_MW", "WORD16", "Видимость сегмента №10"),
        RegisterInfo(501, "HMI_SegmentVisibility_11_MW", "WORD16", "Видимость сегмента №11"),
        RegisterInfo(502, "HMI_SegmentVisibility_12_MW", "WORD16", "Видимость сегмента №12"),
        RegisterInfo(503, "HMI_SegmentVisibility_13_MW", "WORD16", "Видимость сегмента №13"),
        RegisterInfo(504, "HMI_SegmentVisibility_14_MW", "WORD16", "Видимость сегмента №14"),
        RegisterInfo(505, "HMI_SegmentVisibility_15_MW", "WORD16", "Видимость сегмента №15"),
        RegisterInfo(506, "HMI_SegmentVisibility_16_MW", "WORD16", "Видимость сегмента №16"),
        RegisterInfo(507, "HMI_SegmentVisibility_17_MW", "WORD16", "Видимость сегмента №17"),
        RegisterInfo(508, "HMI_SegmentVisibility_18_MW", "WORD16", "Видимость сегмента №18"),
        RegisterInfo(509, "HMI_SegmentVisibility_19_MW", "WORD16", "Видимость сегмента №19")
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
 * Конфигурация UI №3 (SettingLoadCapacityScreen UI)
 * Регистры для работы с UI настройка грузоподъемности
 * //SettingLoadCapacityViewModel
 */
object UISettingLoadCapacityConfiguration {
    const val NAME_SETTING_LOAD_CAPACITY = "UI_SettingLoadCapacity_configuration"

    val REGISTERS = listOf(
        //Параметры настройки грузовой характеристики
        RegisterInfo(195, "MaxWeight2_MW", "WORD16", "Максимальный вес на конце стрелы"),
        RegisterInfo(199, "MaxWeight1_MW", "WORD16", "Максимальный вес для выбранной ГХ"),
        RegisterInfo(197, "Crane_Lenght_MW", "WORD16", "Длина стрелы крана"),
        RegisterInfo(198, "HMI_Curr_AI_MW", "WORD16", "Текущее значение с датчика груза в единицах входа модуля"),

        RegisterInfo(314, "HMI_Weight_MW", "WORD16", "Текущий вес на крюке *0,1т"),
        RegisterInfo(316, "HMI_Zapasovka_MW", "WORD16", "Значение - текущая запасовка. 2 / 4"),

        RegisterInfo(861, "HMI_Write_xHMI_Curr_Gruz_Blok_MW", "WORD16", "Запись выбора блока, =0 с блоком, =1 без блока"),
        RegisterInfo(862, "HMI_Write_iHMI_Curr_Gruz_Fun_MW", "WORD16", "Выбор грузовая характеристика"),
        RegisterInfo(863, "HMI_Write_rMin_Weight_Val_MW", "WORD16", "C HMI значение в тоннах при минимальном грузе"),
        RegisterInfo(864, "HMI_Write_rMax_Weight_Val_MW", "WORD16", "C HMI значение в тоннах при максимальном грузе"),
        RegisterInfo(865, "HMI_Write_xMin_Weight_Set_MW", "WORD16", "Калибровать минимальный груз"),
        RegisterInfo(866, "HMI_Write_xMax_Weight_Set_MW", "WORD16", "Калибровать максимальный груз"),

        RegisterInfo(868, "HMI_Write_xUISettingLoadCapacity_Active_MW", "WORD16", "Флаг активности экрана настройки грузоподъемности")
        )

    val JSON_CONFIG = Json.encodeToString(RegistersConfiguration.serializer(), RegistersConfiguration(REGISTERS))

    fun createConfiguration(): RegisterConfiguration {
        return RegisterConfiguration(
            name = "UI_SettingLoadCapacity_configuration",
            jsonConfiguration = JSON_CONFIG,
            isActive = true
        )
    }
}


/**
 * Конфигурация UI №4 (SettingSensorsScreen UI)
 * Регистры для работы с UI настройка датчиков положения
 * //SettingSensorsViewModel
 */
object UISettingSensorsConfiguration {
    const val NAME_SETTING_SENSORS = "UI_SettingSensors_configuration"

    val REGISTERS = listOf(
        //Параметры крана
        RegisterInfo(155, "Trolley_Position_MW", "WORD16", "Текущее положение тележки на стреле *0,1м"),
        RegisterInfo(158, "Rotation_Position_MW", "WORD16", "Текущий угол поворота"),
        RegisterInfo(159, "Hoist_Position_MW", "WORD16", "Текущее положение подъема *0,1м"),
        RegisterInfo(162,  "Crane_Position_MW",  "WORD16",  "Текущее положение крана *0,1м"),
        RegisterInfo(212,  "Crane_Rails_En_MW",  "WORD16",  "Флаг включения перемещения крана. 1 - Ход крана активирован"),
        //Параметры датчиков
        RegisterInfo(382, "udi_AbsEncFenac_Hoist_Position_MW", "WORD16", "Данные датчика высоты подъема. Слово 1"),
        RegisterInfo(383, "udi_AbsEncFenac_Hoist_Position_MW", "WORD16", "Данные датчика высоты подъема. Слово 2"),
        RegisterInfo(386, "udi_AbsEncFenac_Slew_Position_MW", "WORD16", "Данные датчика поворота. Слово 1"),
        RegisterInfo(387, "udi_AbsEncFenac_Slew_Position_MW", "WORD16", "Данные датчика поворота. Слово 2"),
        RegisterInfo(390, "udi_AbsEncFenac_Trolley_Position_MW", "WORD16", "Данные датчика грузовой тележки. Слово 1"),
        RegisterInfo(391, "udi_AbsEncFenac_Trolley_Position_MW", "WORD16", "Данные датчика грузовой тележки. Слово 2"),
        RegisterInfo(394, "udi_AbsEncFenac_Crane_Position_MW", "WORD16", "Данные датчика хода крана"),
        RegisterInfo(395, "udi_AbsEncFenac_Crane_Position_MW", "WORD16", "Данные датчика хода крана"),

        //Параметры настройки датчика подъема
        RegisterInfo(803, "HMI_Write_xHoist_Limit_En_MW", "WORD16", "Включить проверку ограничения подъемного мех. крюка"),
        RegisterInfo(804, "HMI_Write_xHoist_Fwd_Mem_MW", "WORD16", "Запомнить положение по высоте при движении вверх"),
        RegisterInfo(805, "HMI_Write_xHoist_Rev_Mem_MW", "WORD16", "Запомнить положение по высоте при движении вниз"),

        RegisterInfo(806, "HMI_Write_rHoist_Pos_Fwd_Stop_MW", "WORD16", "Значение при котором останавливаем крюк вверх"),
        RegisterInfo(807, "HMI_Write_rHoist_Pos_Rev_Stop_MW", "WORD16", "Значение при котором останавливаем крюк вниз"),
        RegisterInfo(808, "HMI_Write_rHoist_Pos_Fwd_DwnSp_MW", "WORD16", "Значение при котором снижаем скорость двигая крюк вверх"),
        RegisterInfo(809, "HMI_Write_rHoist_Pos_Rev_DwnSp_MW", "WORD16", "Значение при котором снижаем скорость двигая крюк вниз"),

        RegisterInfo(810, "HMI_Write_xHoist_LSP_Auto_MW", "WORD16", "Включить адаптивное снижение скорости"),
        RegisterInfo(811, "HMI_Write_rHoist_LSP_Dist3_MW", "WORD16", "дистанция снижение до 3-й скорости"),
        RegisterInfo(812, "HMI_Write_rHoist_LSP_Dist2_MW", "WORD16", "дистанция снижение до 2-й скорости"),
        RegisterInfo(813, "HMI_Write_rHoist_LSP_Dist1_MW", "WORD16", "дистанция снижение до 1-й скорости"),
        //Параметры настройки датчика поворота
        RegisterInfo(814, "HMI_Write_xSlew_Limit_En_MW", "WORD16", "Включение принудительной проверки механизма поворота в наладочном режиме"),
        RegisterInfo(815, "HMI_Write_rMax_Slew_Angle_MW", "WORD16", "Максимальный угол поворота, устанавливается с HMI"),
        RegisterInfo(816, "HMI_Write_rCalc_Angle_MW", "WORD16", "Задаваемое значение угла при калибровке энкодера поворота"),
        RegisterInfo(817, "HMI_Write_xSlew_Zero_Mem_MW", "WORD16", "Запомнить положение стрелы при нулевом угле"),
        RegisterInfo(818, "HMI_Write_xSlew_Angle_Mem_MW", "WORD16", "Запомнить положение стрелы при заданном угле"),
        RegisterInfo(819, "HMI_Write_xSlew_LSP_Auto_MW", "WORD16", "Включить адаптивное снижение скорости"),
        RegisterInfo(820, "HMI_Write_rSlew_LSP_Dist3_MW", "WORD16", "дистанция снижение до 3-й скорости"),
        RegisterInfo(821, "HMI_Write_rSlew_LSP_Dist2_MW", "WORD16", "дистанция снижение до 2-й скорости"),
        RegisterInfo(822, "HMI_Write_rSlew_LSP_Dist1_MW", "WORD16", "дистанция снижение до 1-й скорости"),
        //Параметры настройки датчика тележки
        RegisterInfo(823, "HMI_Write_xTrolley_Limit_En_MW", "WORD16", "Включить ограничения перемещения тележки"),
        RegisterInfo(824, "HMI_Write_xTrolley_Fwd_Mem_MW", "WORD16", "Запомнить положение тележки при движении вперед"),
        RegisterInfo(825, "HMI_Write_xTrolley_Rev_Mem_MW", "WORD16", "Запомнить положение тележки при движении назад"),

        RegisterInfo(826, "HMI_Write_rTrolley_Min_Position_MW", "WORD16", "Минимальное положение тележки"),
        RegisterInfo(827, "HMI_Write_rTrolley_Max_Position_MW", "WORD16", "Максимальное положение тележки"),
        RegisterInfo(828, "HMI_Write_rTrolley_Pos_Fwd_DwnSp_MW", "WORD16", "Значение при котором снижаем скорость двигая тележку вперед"),
        RegisterInfo(829, "HMI_Write_rTrolley_Pos_Rev_DwnSp_MW", "WORD16", "Значение при котором снижаем скорость двигая тележку назад"),

        RegisterInfo(830, "HMI_Write_xTrolley_LSP_Auto_MW", "WORD16", "Включить адаптивное снижение скорости"),
        RegisterInfo(831, "HMI_Write_rTrolley_LSP_Dist3_MW", "WORD16", "дистанция снижение до 3-й скорости"),
        RegisterInfo(832, "HMI_Write_rTrolley_LSP_Dist2_MW", "WORD16", "дистанция снижение до 2-й скорости"),
        RegisterInfo(833, "HMI_Write_rTrolley_LSP_Dist1_MW", "WORD16", "дистанция снижение до 1-й скорости"),
        //Параметры настройки датчика хода крана
        RegisterInfo(869, "HMI_Write_xCrane_Rails_En_MW", "WORD16", "Активация хода крана. 1 - Ход крана активирован"),
        RegisterInfo(834, "HMI_Write_xCrane_Limit_En_MW", "WORD16", "Включить ограничения перемещения хода крана"),
        RegisterInfo(835, "HMI_Write_rCrane_Pos_CTRL_Point_MW", "WORD16", "Значение передвижения крана в контрольной точке"),
        RegisterInfo(836, "HMI_Write_xCrane_Fwd_Mem_MW", "WORD16", "Запомнить положение крана при движении вправо"),
        RegisterInfo(837, "HMI_Write_xCrane_Rev_Mem_MW", "WORD16", "Запомнить положение крана при движении влево"),
        RegisterInfo(838, "HMI_Write_rCrane_Pos_Fwd_Stop_MW", "WORD16", "Значение при котором останавливаем движение крана вперед"),
        RegisterInfo(839, "HMI_Write_rCrane_Pos_Rev_Stop_MW", "WORD16", "Значение при котором останавливаем движение крана назад"),
        RegisterInfo(840, "HMI_Write_rCrane_Pos_Fwd_DwnSp_MW", "WORD16", "Значение при котором снижаем скорость при движении крана вперед"),
        RegisterInfo(841, "HMI_Write_rCrane_Pos_Rev_DwnSp_MW", "WORD16", "Значение при котором снижаем скорость при движении крана назад"),
        //Регистры состояний активности экрана настройки датчиков
        RegisterInfo(868, "HMI_Current_Picture_MW", "WORD16", "Значение при котором снижаем скорость при движении крана назад"),
        //Регистры состояний. Проверка датчиков во время настройки
        RegisterInfo(341, "HMI_xHoist_Stop_Dwn1_MW", "WORD16", "Проверка датчика подъема. Стоп вниз"),
        RegisterInfo(342, "HMI_xHoist_Stop_Up1_MW", "WORD16", "Проверка датчика подъема. Стоп вверх"),
        RegisterInfo(343, "HMI_xHoist_Dwn_DwnSp1_MW", "WORD16", "Проверка датчика подъема. Снижение 1 ск. вниз"),
        RegisterInfo(344, "HMI_xHoist_Up_DwnSp1_MW", "WORD16", "Проверка датчика подъема. Снижение 1 ск. вверх"),

        RegisterInfo(345, "HMI_xTrolley_Stop_Fwd1_MW", "WORD16", "Проверка датчика тележки. Стоп вперед"),
        RegisterInfo(346, "HMI_xTrolley_Stop_Rev1_MW", "WORD16", "Проверка датчика тележки. Стоп назад"),
        RegisterInfo(347, "HMI_xTrolley_Fwd_DwnSp1_MW", "WORD16", "Проверка датчика тележки. Снижение 1 ск. вперед"),
        RegisterInfo(348, "HMI_xTrolley_Rev_DwnSp1_MW", "WORD16", "Проверка датчика тележки. Снижение 1 ск. назад"),

        RegisterInfo(349, "HMI_xCrane_Stop_Fwd1_MW", "WORD16", "Проверка датчика хода. Стоп вперед"),
        RegisterInfo(350, "HMI_xCrane_Stop_Rev1_MW", "WORD16", "Проверка датчика хода. Стоп назад"),
        RegisterInfo(351, "HMI_xCrane_Fwd_DwnSp1_MW", "WORD16", "Проверка датчика хода. Снижение 1 ск. вперед"),
        RegisterInfo(352, "HMI_xCrane_Rev_DwnSp1_MW", "WORD16", "Проверка датчика хода. Снижение 1 ск. назад"),
        RegisterInfo(353, "HMI_xCraneRepPoint_MW", "WORD16", "Реперная точка хода крана"),

        //-----ошибки выхода за рабочий диапазон показаний энкодера
        RegisterInfo(730, "HMI_HoistEncoder_MaxDeltaError_MW", "WORD16", "Превышение максимальной дельты энкодера подъема"),
        RegisterInfo(731, "HMI_SlewEncoder_MaxDeltaError_MW", "WORD16", "Превышение максимальной дельты энкодера поворота"),
        RegisterInfo(732, "HMI_TrolleyEncoder_MaxDeltaError_MW", "WORD16", "Превышение максимальной дельты энкодера тележки"),
        RegisterInfo(733, "HMI_TravelEncoder_MaxDeltaError_MW", "WORD16", "Превышение максимальной дельты энкодера передвижения"),
        RegisterInfo(734, "HMI_HoistEncoder_MaxValueError_MW", "WORD16", "Превышение максимального значения энкодера подъема"),
        RegisterInfo(735, "HMI_SlewEncoder_MaxValueError_MW", "WORD16", "Превышение максимального значения энкодера поворота"),
        RegisterInfo(736, "HMI_TrolleyEncoder_MaxValueError_MW", "WORD16", "Превышение максимального значения энкодера тележки"),
        RegisterInfo(737, "HMI_TravelEncoder_MaxValueError_MW", "WORD16", "Превышение максимального значения энкодера передвижения"),
        //-----ошибка связи энкодеров
        RegisterInfo(204, "Hoist_AbsEnc_Err_MW", "WORD16", "Сбой датчика подъема"),
        RegisterInfo(205, "Slew_AbsEnc_Err_MW", "WORD16", "Сбой датчика поворота"),
        RegisterInfo(206, "Trolley_AbsEnc_Err_MW", "WORD16", "Сбой датчика вылета"),
        RegisterInfo(207, "Crane_AbsEnc_Err_MW", "WORD16", "Сбой датчика перемещения крана")
        )

    val JSON_CONFIG = Json.encodeToString(RegistersConfiguration.serializer(), RegistersConfiguration(REGISTERS))

    fun createConfiguration(): RegisterConfiguration {
        return RegisterConfiguration(
            name = "UI_SettingSensors_configuration",
            jsonConfiguration = JSON_CONFIG,
            isActive = true
        )
    }
}



/**
 * Конфигурация UI №5 (CoordinateProtectionScreen UI)
 * Регистры для работы с UI настройка грузоподъемности
 * //CoordinateProtectionViewModel
 */
object UICoordinateProtectionConfiguration {
    const val NAME_COORDINATE_PROTECTION = "UI_CoordinateProtection_configuration"

    val REGISTERS = listOf(
        //НАСТРОЙКА КООРДИНАТНОЙ ЗАЩИТЫ
        //Данные механизмов
        RegisterInfo(155, "Trolley_Position_MW", "WORD16", "Текущее положение тележки на стреле *0,1м"),
        RegisterInfo(158, "Rotation_Position_MW", "WORD16", "Текущий угол поворота *0,1м"),
        RegisterInfo(162,  "Crane_Position_MW",  "WORD16",  "Текущее положение крана *0,1м"),

        //Данные состояний настройки полигонов
        RegisterInfo(610, "HMI_Pos_Prot_En_MW", "WORD16", "Активация координатной защиты. 1 - Активна. 0 - Не активна"),
        RegisterInfo(611, "HMI_Mode_Pos_Prot_MW", "WORD16", "Выбор. тип коорд. защиты: 0-защита груза; 1-защита стрелы"),

        RegisterInfo(612, "HMI_xP1_Up_MW", "WORD16", "Разрешение работы сверху полигона 1"),
        RegisterInfo(613, "HMI_xP1_Down_MW", "WORD16", "Разрешение работы снизу полигона 1"),
        RegisterInfo(614, "HMI_P1_Height_MW", "WORD16", "Высота полигона 1"),
        RegisterInfo(615, "HMI_P1_Point_Num_MW", "WORD16", "Кол-во точек полигона 1"),

        RegisterInfo(616, "HMI_xP2_Up_MW", "WORD16", "Разрешение работы сверху полигона 2"),
        RegisterInfo(617, "HMI_xP2_Down_MW", "WORD16", "Разрешение работы снизу полигона 2"),
        RegisterInfo(618, "HMI_P2_Height_MW", "WORD16", "Высота полигона 2"),
        RegisterInfo(619, "HMI_P2_Point_Num_MW", "WORD16", "Кол-во точек полигона 2"),

        RegisterInfo(620, "HMI_xP3_Up_MW", "WORD16", "Разрешение работы сверху полигона 3"),
        RegisterInfo(621, "HMI_xP3_Down_MW", "WORD16", "Разрешение работы снизу полигона 3"),
        RegisterInfo(622, "HMI_P3_Height_MW", "WORD16", "Высота полигона 3"),
        RegisterInfo(623, "HMI_P3_Point_Num_MW", "WORD16", "Кол-во точек полигона 3"),

        RegisterInfo(624, "HMI_xP4_Up_MW", "WORD16", "Разрешение работы сверху полигона 4"),
        RegisterInfo(625, "HMI_xP4_Down_MW", "WORD16", "Разрешение работы снизу полигона 4"),
        RegisterInfo(626, "HMI_P4_Height_MW", "WORD16", "Высота полигона 4"),
        RegisterInfo(627, "HMI_P4_Point_Num_MW", "WORD16", "Кол-во точек полигона 4"),

        RegisterInfo(630, "HMI_P1_Curr_Type_MW", "WORD16", "Тип ввода точек полигона 1"),
        RegisterInfo(631, "HMI_P2_Curr_Type_MW", "WORD16", "Тип ввода точек полигона 2"),
        RegisterInfo(632, "HMI_P3_Curr_Type_MW", "WORD16", "Тип ввода точек полигона 3"),
        RegisterInfo(633, "HMI_P4_Curr_Type_MW", "WORD16", "Тип ввода точек полигона 4"),

        RegisterInfo(628, "HMI_P1_Few_Point_MW", "WORD16", "Статус 1 полигона. 0-ок. 1-Мало точек"),
        RegisterInfo(629, "HMI_P1_Activ_Stat_MW", "WORD16", "Статус 1 полигона. О-не актив. 1 - Актив."),
        RegisterInfo(634, "HMI_P2_Few_Point_MW", "WORD16", "Статус 2 полигона. 0-ок 1-Мало точек."),
        RegisterInfo(635, "HMI_P2_Activ_Stat_MW", "WORD16", "Статус 2 полигона. О-не актив. 1 - Актив."),
        RegisterInfo(636, "HMI_P3_Few_Point_MW", "WORD16", "Статус 3 полигона. 0-ок 1-Мало точек"),
        RegisterInfo(637, "HMI_P3_Activ_Stat_MW", "WORD16", "Статус 3 полигона. О-не актив. 1 - Актив."),
        RegisterInfo(638, "HMI_P4_Few_Point_MW", "WORD16", "Статус 4 полигона. 0-ок 1-Мало точек."),
        RegisterInfo(639, "HMI_P4_Activ_Stat_MW", "WORD16", "Статус 4 полигона. О-не актив. 1 - Актив."),
        RegisterInfo(640, "HMI_Stats_Calibration_MW", "WORD16", "Статус настройки полигонов. 1-Настроен. 0- Нет."),


        //Данные параметров настройки полигонов.
        RegisterInfo(891, "HMI_Write_Pos_Prot_En_MW", "WORD16", "Активировать координатую защиту. (Тумблер по входному биту)"),
        RegisterInfo(892, "HMI_Write_Mode_Pos_Prot", "WORD16", "Выбор. тип коорд. защиты 0-защита груза; 1-защита стрелы (Тумблер по входному биту)"),

        RegisterInfo(870, "HMI_Write_xP1_En_MW", "WORD16", "Активировать первый полигон"),
        RegisterInfo(871, "HMI_Write_xP1_Up_MW", "WORD16", "Разрешить работу сверху полигона 1"),
        RegisterInfo(872, "HMI_Write_xP1_Down_MW", "WORD16", "Разрешить работу снизу полигона 1"),
        RegisterInfo(873, "HMI_Write_rP1_Height_MW", "WORD16", "высота полигона 1"),
        RegisterInfo(874, "HMI_Write_iP1_Point_Num_MW", "WORD16", "количество точек полигона 1"),

        RegisterInfo(875, "HMI_Write_xP2_En_MW", "WORD16", "Активировать второй полигон"),
        RegisterInfo(876, "HMI_Write_xP2_Up_MW", "WORD16", "Разрешить работу сверху полигона 2"),
        RegisterInfo(877, "HMI_Write_xP2_Down_MW", "WORD16", "Разрешить работу снизу полигона 2"),
        RegisterInfo(878, "HMI_Write_rP2_Height_MW", "WORD16", "высота полигона 2"),
        RegisterInfo(879, "HMI_Write_iP2_Point_Num_MW", "WORD16", "количество точек полигона 2"),

        RegisterInfo(880, "HMI_Write_xP3_En_MW", "WORD16", "Активировать третий полигон"),
        RegisterInfo(881, "HMI_Write_xP3_Up_MW", "WORD16", "Разрешить работу сверху полигона 3"),
        RegisterInfo(882, "HMI_Write_xP3_Down_MW", "WORD16", "Разрешить работу снизу полигона 3"),
        RegisterInfo(883, "HMI_Write_rP3_Height_MW", "WORD16", "высота полигона 3"),
        RegisterInfo(884, "HMI_Write_iP3_Point_Num_MW", "WORD16", "количество точек полигона 3"),

        RegisterInfo(885, "HMI_Write_xP4_En_MW", "WORD16", "Активировать четвертый полигон"),
        RegisterInfo(886, "HMI_Write_xP4_Up_MW", "WORD16", "Разрешить работу сверху полигона 4"),
        RegisterInfo(887, "HMI_Write_xP4_Down_MW", "WORD16", "Разрешить работу снизу полигона 4"),
        RegisterInfo(888, "HMI_Write_rP4_Height_MW", "WORD16", "высота полигона 4"),
        RegisterInfo(889, "HMI_Write_iP4_Point_Num_MW", "WORD16", "количество точек полигона 4"),

        RegisterInfo(900, "HMI_Write_xP1_Reset_MW", "WORD16", "Сбросить первый полигон"),
        RegisterInfo(901, "HMI_Write_iP1_Point_Cmd_MW", "WORD16", "команды для заполнения точек полигона 1"),
        RegisterInfo(902, "HMI_Write_xP1_Set_Type1_MW", "WORD16", "установка режима задания точек 1 (по координатам)"),
        RegisterInfo(903, "HMI_Write_xP1_Set_Type2_MW", "WORD16", "установка режима задания точек 2 (ручной ввод)"),
        RegisterInfo(904, "HMI_Write_rP1_Type1_Px_MW", "WORD16", "при типе ввода 1 - координата x"),
        RegisterInfo(905, "HMI_Write_rP1_Type1_Py_MW", "WORD16", "при типе ввода 1 - координата y"),
        RegisterInfo(906, "HMI_Write_rP1_Type2_Trolley_Pos_MW", "WORD16", "при типе ввода 2 - позиция телеги"),
        RegisterInfo(907, "HMI_Write_rP1_Type2_Slew_Pos_MW", "WORD16", "при типе ввода 2 - положение стрелы"),
        RegisterInfo(908, "HMI_Write_rP1_Type2_Crane_Pos_MW", "WORD16", "при типе ввода 2 - положение крана"),

        RegisterInfo(910, "HMI_Write_xP2_Reset_MW", "WORD16", "Сбросить второй полигон"),
        RegisterInfo(911, "HMI_Write_iP2_Point_Cmd_MW", "WORD16", "команды для заполнения точек полигона 2"),
        RegisterInfo(912, "HMI_Write_xP2_Set_Type1_MW", "WORD16", "установка режима задания точек 1 (по координатам)"),
        RegisterInfo(913, "HMI_Write_xP2_Set_Type2_MW", "WORD16", "установка режима задания точек 2 (по вылету)"),
        RegisterInfo(914, "HMI_Write_rP2_Type1_Px_MW", "WORD16", "при типе ввода 1 - координата x"),
        RegisterInfo(915, "HMI_Write_rP2_Type1_Py_MW", "WORD16", "при типе ввода 1 - координата y"),
        RegisterInfo(916, "HMI_Write_rP2_Type2_Trolley_Pos_MW", "WORD16", "при типе ввода 2 - позиция телеги"),
        RegisterInfo(917, "HMI_Write_rP2_Type2_Slew_Pos_MW", "WORD16", "при типе ввода 2 - положение стрелы"),
        RegisterInfo(918, "HMI_Write_rP2_Type2_Crane_Pos_MW", "WORD16", "при типе ввода 2 - положение крана"),

        RegisterInfo(920, "HMI_Write_xP3_Reset_MW", "WORD16", "Сбросить третий полигон"),
        RegisterInfo(921, "HMI_Write_iP3_Point_Cmd_MW", "WORD16", "команды для заполнения точек полигона 3"),
        RegisterInfo(922, "HMI_Write_xP3_Set_Type1_MW", "WORD16", "установка режима задания точек 1 (по координатам)"),
        RegisterInfo(923, "HMI_Write_xP3_Set_Type2_MW", "WORD16", "установка режима задания точек 2 (по вылету)"),
        RegisterInfo(924, "HMI_Write_rP3_Type1_Px_MW", "WORD16", "при типе ввода 1 - координата x"),
        RegisterInfo(925, "HMI_Write_rP3_Type1_Py_MW", "WORD16", "при типе ввода 1 - координата y"),
        RegisterInfo(926, "HMI_Write_rP3_Type2_Trolley_Pos_MW", "WORD16", "при типе ввода 2 - позиция телеги"),
        RegisterInfo(927, "HMI_Write_rP3_Type2_Slew_Pos_MW", "WORD16", "при типе ввода 2 - положение стрелы"),
        RegisterInfo(928, "HMI_Write_rP3_Type2_Crane_Pos_MW", "WORD16", "при типе ввода 2 - положение крана"),

        RegisterInfo(930, "HMI_Write_xP4_Reset_MW", "WORD16", "Сбросить четвертый полигон"),
        RegisterInfo(931, "HMI_Write_iP4_Point_Cmd_MW", "WORD16", "Команды для заполнения точек полигона 4"),
        RegisterInfo(932, "HMI_Write_xP4_Set_Type1_MW", "WORD16", "Установка режима задания точек 1 (по координатам)"),
        RegisterInfo(933, "HMI_Write_xP4_Set_Type2_MW", "WORD16", "Установка режима задания точек 2 (по вылету)"),
        RegisterInfo(934, "HMI_Write_rP4_Type1_Px_MW", "WORD16", "При типе ввода 1 - координата X"),
        RegisterInfo(935, "HMI_Write_rP4_Type1_Py_MW", "WORD16", "При типе ввода 1 - координата Y"),
        RegisterInfo(936, "HMI_Write_rP4_Type2_Trolley_Pos_MW", "WORD16", "При типе ввода 2 - позиция тележки"),
        RegisterInfo(937, "HMI_Write_rP4_Type2_Slew_Pos_MW", "WORD16", "При типе ввода 2 - положение стрелы"),
        RegisterInfo(938, "HMI_Write_rP4_Type2_Crane_Pos_MW", "WORD16", "При типе ввода 2 - положение крана"),

        //Задание точек полигонов с HMI
        RegisterInfo(1000, "HMI_Points_P1_00_Config_MW", "WORD16", "Точка 1X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1001, "HMI_Points_P1_01_Config_MW", "WORD16", "Точка 1Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1002, "HMI_Points_P1_10_Config_MW", "WORD16", "Точка 2X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1003, "HMI_Points_P1_11_Config_MW", "WORD16", "Точка 2Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1004, "HMI_Points_P1_20_Config_MW", "WORD16", "Точка 3X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1005, "HMI_Points_P1_21_Config_MW", "WORD16", "Точка 3Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1006, "HMI_Points_P1_30_Config_MW", "WORD16", "Точка 4X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1007, "HMI_Points_P1_31_Config_MW", "WORD16", "Точка 4Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1008, "HMI_Points_P1_40_Config_MW", "WORD16", "Точка 5X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1009, "HMI_Points_P1_41_Config_MW", "WORD16", "Точка 5Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1010, "HMI_Points_P1_50_Config_MW", "WORD16", "Точка 6X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1011, "HMI_Points_P1_51_Config_MW", "WORD16", "Точка 6Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1012, "HMI_Points_P1_60_Config_MW", "WORD16", "Точка 7X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1013, "HMI_Points_P1_61_Config_MW", "WORD16", "Точка 7Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1014, "HMI_Points_P1_70_Config_MW", "WORD16", "Точка 8X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1015, "HMI_Points_P1_71_Config_MW", "WORD16", "Точка 8Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1016, "HMI_Points_P1_80_Config_MW", "WORD16", "Точка 9X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1017, "HMI_Points_P1_81_Config_MW", "WORD16", "Точка 9Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1018, "HMI_Points_P1_90_Config_MW", "WORD16", "Точка 10X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1019, "HMI_Points_P1_91_Config_MW", "WORD16", "Точка 10Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1020, "HMI_Points_P1_10_0_Config_MW", "WORD16", "Точка 11X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1021, "HMI_Points_P1_10_1_Config_MW", "WORD16", "Точка 11Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1022, "HMI_Points_P1_11_0_Config_MW", "WORD16", "Точка 12X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1023, "HMI_Points_P1_11_1_Config_MW", "WORD16", "Точка 12Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1024, "HMI_Points_P1_12_0_Config_MW", "WORD16", "Точка 13X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1025, "HMI_Points_P1_12_1_Config_MW", "WORD16", "Точка 13Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1026, "HMI_Points_P1_13_0_Config_MW", "WORD16", "Точка 14X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1027, "HMI_Points_P1_13_1_Config_MW", "WORD16", "Точка 14Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1028, "HMI_Points_P1_14_0_Config_MW", "WORD16", "Точка 15X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1029, "HMI_Points_P1_14_1_Config_MW", "WORD16", "Точка 15Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1030, "HMI_Points_P1_15_0_Config_MW", "WORD16", "Точка 16X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1031, "HMI_Points_P1_15_1_Config_MW", "WORD16", "Точка 16Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1032, "HMI_Points_P1_16_0_Config_MW", "WORD16", "Точка 17X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1033, "HMI_Points_P1_16_1_Config_MW", "WORD16", "Точка 17Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1034, "HMI_Points_P1_17_0_Config_MW", "WORD16", "Точка 18X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1035, "HMI_Points_P1_17_1_Config_MW", "WORD16", "Точка 18Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1036, "HMI_Points_P1_18_0_Config_MW", "WORD16", "Точка 19X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1037, "HMI_Points_P1_18_1_Config_MW", "WORD16", "Точка 19Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1038, "HMI_Points_P1_19_0_Config_MW", "WORD16", "Точка 20X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1039, "HMI_Points_P1_19_1_Config_MW", "WORD16", "Точка 20Y многоугольника внутри блока конфигурации полигона 1"),

        RegisterInfo(1040, "HMI_Points_P2_00_Config_MW", "WORD16", "Точка 1X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1041, "HMI_Points_P2_01_Config_MW", "WORD16", "Точка 1Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1042, "HMI_Points_P2_10_Config_MW", "WORD16", "Точка 2X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1043, "HMI_Points_P2_11_Config_MW", "WORD16", "Точка 2Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1044, "HMI_Points_P2_20_Config_MW", "WORD16", "Точка 3X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1045, "HMI_Points_P2_21_Config_MW", "WORD16", "Точка 3Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1046, "HMI_Points_P2_30_Config_MW", "WORD16", "Точка 4X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1047, "HMI_Points_P2_31_Config_MW", "WORD16", "Точка 4Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1048, "HMI_Points_P2_40_Config_MW", "WORD16", "Точка 5X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1049, "HMI_Points_P2_41_Config_MW", "WORD16", "Точка 5Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1050, "HMI_Points_P2_50_Config_MW", "WORD16", "Точка 6X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1051, "HMI_Points_P2_51_Config_MW", "WORD16", "Точка 6Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1052, "HMI_Points_P2_60_Config_MW", "WORD16", "Точка 7X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1053, "HMI_Points_P2_61_Config_MW", "WORD16", "Точка 7Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1054, "HMI_Points_P2_70_Config_MW", "WORD16", "Точка 8X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1055, "HMI_Points_P2_71_Config_MW", "WORD16", "Точка 8Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1056, "HMI_Points_P2_80_Config_MW", "WORD16", "Точка 9X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1057, "HMI_Points_P2_81_Config_MW", "WORD16", "Точка 9Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1058, "HMI_Points_P2_90_Config_MW", "WORD16", "Точка 10X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1059, "HMI_Points_P2_91_Config_MW", "WORD16", "Точка 10Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1060, "HMI_Points_P2_10_0_Config_MW", "WORD16", "Точка 11X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1061, "HMI_Points_P2_10_1_Config_MW", "WORD16", "Точка 11Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1062, "HMI_Points_P2_11_0_Config_MW", "WORD16", "Точка 12X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1063, "HMI_Points_P2_11_1_Config_MW", "WORD16", "Точка 12Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1064, "HMI_Points_P2_12_0_Config_MW", "WORD16", "Точка 13X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1065, "HMI_Points_P2_12_1_Config_MW", "WORD16", "Точка 13Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1066, "HMI_Points_P2_13_0_Config_MW", "WORD16", "Точка 14X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1067, "HMI_Points_P2_13_1_Config_MW", "WORD16", "Точка 14Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1068, "HMI_Points_P2_14_0_Config_MW", "WORD16", "Точка 15X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1069, "HMI_Points_P2_14_1_Config_MW", "WORD16", "Точка 15Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1070, "HMI_Points_P2_15_0_Config_MW", "WORD16", "Точка 16X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1071, "HMI_Points_P2_15_1_Config_MW", "WORD16", "Точка 16Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1072, "HMI_Points_P2_16_0_Config_MW", "WORD16", "Точка 17X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1073, "HMI_Points_P2_16_1_Config_MW", "WORD16", "Точка 17Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1074, "HMI_Points_P2_17_0_Config_MW", "WORD16", "Точка 18X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1075, "HMI_Points_P2_17_1_Config_MW", "WORD16", "Точка 18Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1076, "HMI_Points_P2_18_0_Config_MW", "WORD16", "Точка 19X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1077, "HMI_Points_P2_18_1_Config_MW", "WORD16", "Точка 19Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1078, "HMI_Points_P2_19_0_Config_MW", "WORD16", "Точка 20X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(1079, "HMI_Points_P2_19_1_Config_MW", "WORD16", "Точка 20Y многоугольника внутри блока конфигурации полигона 2")
    )

    val JSON_CONFIG = Json.encodeToString(RegistersConfiguration.serializer(), RegistersConfiguration(REGISTERS))

    fun createConfiguration(): RegisterConfiguration {
        return RegisterConfiguration(
            name = "UI_CoordinateProtection_configuration",
            jsonConfiguration = JSON_CONFIG,
            isActive = true
        )
    }
}



/**
 * Конфигурация UI №7 (ServiceMenuViewModel)
 * Регистры для работы с UI экрана начальные настройки
 */
object UIServiceMenuRegisterConfiguration {
    const val NAME_SERVICE_MENU = "UI_configuration_ServiceMenu"

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

        RegisterInfo(994, "HMI_Write_xAbsEncDeltaControlEn_MW", "WORD16", "Включить контроль максимального рассогласования абсолютных энкодеров"),
        RegisterInfo(995, "HMI_Write_xDebug_Mode_Hoist_MW", "WORD16", "Флаг установки режима наладки механизма подъема при выходе датчика из строя"),
        RegisterInfo(996, "HMI_Write_xDebug_Mode_Slew_MW", "WORD16", "Флаг установки режима наладки механизма поворота при выходе датчика из строя"),

        RegisterInfo(997, "HMI_Write_udiHoistAbsEncMaxDeltaEn_MD", "WORD32", "Максимальное рассогласование абс. энкодера подъема"),
        RegisterInfo(998, "HMI_Write_udiSlewAbsEncMaxDeltaEn_MD", "WORD32", "Максимальное рассогласование абс. энкодера поворота"),
        RegisterInfo(999, "HMI_Write_udiTrolleyAbsEncMaxDeltaEn_MD", "WORD32", "Максимальное рассогласование абс. энкодера тележки"),
        RegisterInfo(989, "HMI_Write_udiCraneAbsEncMaxDeltaEn_MD", "WORD32", "Максимальное рассогласование абс. энкодера хода крана"),

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
        RegisterInfo(1205, "HMI_Write_xPasswordOrNaladka_MW", "WORD16", "Режим авторизации [naladka]"),
        RegisterInfo(1206, "HMI_Write_xFOURpoligonsEn_MW", "WORD16", "Режим работы 4 полигона, 20 точек. (По умолчанию 2 пол. 10 точек)"),
        RegisterInfo(1207, "HMI_Write_xWithoutRringCurrentCollector_MW", "WORD16", "Исполнение крана без кольц. токосъемника. (По умолчанию с токосъемником)"),
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
            name = "UI_configuration_ServiceMenu",
            jsonConfiguration = JSON_CONFIG,
            isActive = false
        )
    }
}