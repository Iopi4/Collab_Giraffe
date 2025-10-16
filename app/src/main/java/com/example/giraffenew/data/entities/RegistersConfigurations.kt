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
        UiSettingRotationConfiguration.NAME_SETTING_ROTATION to UiSettingRotationConfiguration.createConfiguration(),
        UIServiceMenuRegisterConfiguration.NAME_SERVICE_MENU to UIServiceMenuRegisterConfiguration.createConfiguration(),
        UISettingLoadCapacityConfiguration.NAME_SETTING_LOAD_CAPACITY to UISettingLoadCapacityConfiguration.createConfiguration(),
        UISettingSensorsConfiguration.NAME_SETTING_SENSORS to UISettingSensorsConfiguration.createConfiguration(),
        UICoordinateProtectionConfiguration.NAME_COORDINATE_PROTECTION to UICoordinateProtectionConfiguration.createConfiguration(),
        UIDiagnosticRegisterConfiguration.NAME_DIAGNOSTIC to UIDiagnosticRegisterConfiguration.createConfiguration(),
        UIParameterLoggerConfiguration.NAME_PARAMETER_LOGGER to UIParameterLoggerConfiguration.createConfiguration(),
        UITechnicalMaintenanceConfiguration.NAME_TECHNICAL_MAINTENANCE to UITechnicalMaintenanceConfiguration.createConfiguration(),
        UILoginConfiguration.NAME_LOGIN to UILoginConfiguration.createConfiguration(),
        UIAccidentAndWaringConfiguration.NAME_ACCIDENT_AND_WARING to UIAccidentAndWaringConfiguration.createConfiguration()
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
            UiSettingRotationConfiguration.NAME_SETTING_ROTATION -> UiSettingRotationConfiguration.REGISTERS
            UISettingLoadCapacityConfiguration.NAME_SETTING_LOAD_CAPACITY -> UISettingLoadCapacityConfiguration.REGISTERS
            UISettingSensorsConfiguration.NAME_SETTING_SENSORS->UISettingSensorsConfiguration.REGISTERS
            UICoordinateProtectionConfiguration.NAME_COORDINATE_PROTECTION -> UICoordinateProtectionConfiguration.REGISTERS
            UIDiagnosticRegisterConfiguration.NAME_DIAGNOSTIC -> UIDiagnosticRegisterConfiguration.REGISTERS
            UIParameterLoggerConfiguration.NAME_PARAMETER_LOGGER -> UIParameterLoggerConfiguration.REGISTERS
            UITechnicalMaintenanceConfiguration.NAME_TECHNICAL_MAINTENANCE -> UITechnicalMaintenanceConfiguration.REGISTERS
            UILoginConfiguration.NAME_LOGIN -> UILoginConfiguration.REGISTERS
            UIAccidentAndWaringConfiguration.NAME_ACCIDENT_AND_WARING -> UIAccidentAndWaringConfiguration.REGISTERS
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
        RegisterInfo(0, "test", "WORD16", "Год"),

        RegisterInfo(90, "gv_wYear_MW", "WORD16", "Год"),
        RegisterInfo(91, "gv_wMonth_MW", "WORD16", "Месяц"),
        RegisterInfo(94, "gv_wDay_MW", "WORD16", "День"),
        RegisterInfo(95, "gv_wHour_MW", "WORD16", "Часы"),
        RegisterInfo(98, "gv_wMinute_MW", "WORD16", "Минуты"),
        RegisterInfo(99, "gv_wSeconds_MW", "WORD16", "Секунды"),
        RegisterInfo(150, "Crane_No_MW", "WORD16", "Заводской тип крана"),
        RegisterInfo(190, "Build_Mode_MW", "WORD16", "Флаг режима наладки"),
        RegisterInfo(191, "Service_Mode_MW", "WORD16", "Флаг режима сервис"),
        RegisterInfo(192, "Montage_Mode_MW", "WORD16", "Флаг режима монтаж"),
        RegisterInfo(193, "Curr_Gruz_Zapas_MW", "WORD16", "выбор кратности запасовки, =TRUE -4х кратная, =FALSE -2х кратная"),
        RegisterInfo(194, "Curr_Gruz_Fun_MW", "WORD16", "Текущая выбранная грузовая характеристика"),
        RegisterInfo(367, "HMI_CraneSerialNumber_MW", "WORD16", "Заводской номер крана"),
        RegisterInfo(278, "Beeper_Weight_MW", "WORD16", "Пищалка HMI.Превышение ГП больше 90%"),
        RegisterInfo(279, "Beeper_Warning_MW", "WORD16", "Пищалка HMI. Предупреждение"),
        RegisterInfo(280, "Beeper_Alarm_Fault_MW", "WORD16", "Пищалка HMI. Авария "),
        RegisterInfo(281, "Beeper_OGP_MW", "WORD16", "Пищалка HMI. ОГП "),
        RegisterInfo(282, "Beeper_STOP_MW", "WORD16", "Пищалка HMI.Стой "),

        RegisterInfo(893, "HMI_Write_HMI_Active_MW", "WORD16", "Флаг режима активности HMI"),
        RegisterInfo(641, "HMI_TimeZone_MW", "WORD16", "(* Часовой пояс в UTC *)"),
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
        RegisterInfo(153, "Weight_MW", "WORD16", "Текущая грузоподъемность в тоннах"),
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
        RegisterInfo(319, "HMI_Max_Crane_Position_MW", "WORD16", "Максимальное положение крана"),
        RegisterInfo(154, "WindSpeedLvl2", "WORD16", "Предельный ветер нерабочего состояния в м/с"),
        RegisterInfo(175, "WindSpeedLvl1", "WORD16", "Предупредительный ветер рабочего состояния в м/с"),
        RegisterInfo(176, "WindSpeedMontageLv1_MW", "WORD16", "Предупредительный ветер в режиме монтаж в м/с"),
        RegisterInfo(177, "Beeper_Weight_MW", "WORD16", "Пищалка HMI"),
        RegisterInfo(192, "MontageMode_MW", "WORD16", "Флаг монтажного режима"),
        RegisterInfo(710, "HMI_CraneReferencePointSensor_Fault_MW", "WORD16", "Сбой датчика реперной точки хода крана"),
        RegisterInfo(709, "HMI_LoadMomentSensor_Alarm_MW", "WORD16", "Авария датчика критического грузового момента"),
        RegisterInfo(229, "PLC_DI0_0_5_HoistBrakeSensor_MW", "WORD16", "Контроль износа тормоза подъема"),
        RegisterInfo(212, "Crane_Rails_En_MW", "WORD16", "Флаг включения перемещения крана. 1 - Ход крана активирован"),
        RegisterInfo(353, "HMI_xCraneRepPoint_MW", "WORD16", "Реперная точка хода крана"),

        RegisterInfo(305, "Technical_Reglament_TO1_MW", "WORD16", "Проведите ТО"),
        RegisterInfo(306, "Alarm_TO1_MW", "WORD16", "Скорости ограничены, проведите ТО"),

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
        RegisterInfo(712, "HMI_Anemometer_Sensor_Err_MW", "WORD16", "Статус анемометраю Ошибка датчика"),
        RegisterInfo(337, "HMI_AlarmLamp_Status_MW", "WORD16", "Cбой анемоментра"),

        RegisterInfo(103, "Hoist_ControlEffort_MW", "WORD16", "Текущая скорость вращения подъема *0,01Гц"),
        RegisterInfo(112, "Slew_ControlEffort_MW", "WORD16", "Текущая скорость вращения *0,01Гц"),
        RegisterInfo(121, "Trolley_ControlEffort_MW", "WORD16", "Текущая скорость вращения *0,01Гц"),
        RegisterInfo(130, "Crane_ControlEffort_MW", "WORD16", "Текущая скорость вращения *0,01Гц"),

        //Information
        RegisterInfo(190, "Build_Mode_MW", "WORD16", "Флаг режима наладки"),
        RegisterInfo(191, "Service_Mode_MW", "WORD16", "Флаг режима сервис"),
        RegisterInfo(239, "PLC_DI1_0_7_ACSS_PWR_MW", "WORD16", "АЦСС питание в норме"),
        RegisterInfo(288, "PLC_DI1_1_0_ACSS_Fault_MW", "WORD16", "АЦСС в ошибке"),
        RegisterInfo(700, "HMI_HatchLimitSwitch_Status_MW", "WORD16", "Состояние конечного выключателя люка ОПУ"),
        RegisterInfo(701, "HMI_LineContactorZeroPos_Alarm_MW", "WORD16", "Авария нулевой блокировки линейного контактора"),
        RegisterInfo(702, "HMI_MicroclimateHeater_Fault_MW", "WORD16", "Авария обогрева технического помещения"),
        RegisterInfo(703, "HMI_BrakePowerSupply_Fault_MW", "WORD16", "Авария питания тормозов"),
        RegisterInfo(704, "HMI_VentilationPowerSupply_Fault_MW", "WORD16", "Авария питания вентиляции электродвигателей"),
        RegisterInfo(705, "HMI_HoistJoystick_Error_MW", "WORD16", "Ошибка джойстика механизма подъема"),
        RegisterInfo(706, "HMI_SlewJoystick_Error_MW", "WORD16", "Ошибка джойстика механизма поворота"),
        RegisterInfo(707, "HMI_TrolleyJoystick_Error_MW", "WORD16", "Ошибка джойстика механизма тележки"),
        RegisterInfo(708, "HMI_TravelJoystick_Error_MW", "WORD16", "Ошибка джойстика механизма передвижения"),
        RegisterInfo(713, "HMI_CraneEncoder_Fault_MW", "WORD16", "Сбой энкодера механизма передвижения"),
        RegisterInfo(714, "HMI_HoistEncoder_Fault_MW", "WORD16", "Сбой энкодера механизма подъема"),
        RegisterInfo(715, "HMI_SlewEncoder_Fault_MW", "WORD16", "Сбой энкодера механизма поворота"),
        RegisterInfo(716, "HMI_TrolleyEncoder_Fault_MW", "WORD16", "Сбой энкодера механизма тележки"),
        RegisterInfo(717, "HMI_PhaseControlRelay_Fault_MW", "WORD16", "Авария реле контроля фаз"),
        RegisterInfo(718, "HMI_PowerSupplyNetwork_Fault_MW", "WORD16", "Авария питающей сети"),
        RegisterInfo(720, "HMI_HoistDrive_Error_MW", "WORD16", "Ошибка частотного преобразователя подъема"),
        RegisterInfo(721, "HMI_SlewDrive_Error_MW", "WORD16", "Ошибка частотного преобразователя поворота"),
        RegisterInfo(722, "HMI_TrolleyDrive_Error_MW", "WORD16", "Ошибка частотного преобразователя тележки"),
        RegisterInfo(723, "HMI_TravelDrive_Error_MW", "WORD16", "Ошибка частотного преобразователя передвижения"),
        RegisterInfo(724, "HMI_HoistDrivePower_Error_MW", "WORD16", "Отключение питания ПЧ подъема"),
        RegisterInfo(725, "HMI_SlewDrivePower_Error_MW", "WORD16", "Отключение питания ПЧ поворота"),
        RegisterInfo(726, "HMI_TrolleyDrivePower_Error_MW", "WORD16", "Отключение питания ПЧ тележки"),
        RegisterInfo(727, "HMI_TravelDrivePower_Error_MW", "WORD16", "Отключение питания ПЧ передвижения"),
        RegisterInfo(728, "HMI_CANopenSensors_Error_MW", "WORD16", "Авария сети CANopen (датчики)"),
        RegisterInfo(729, "HMI_CANopenDrives_Error_MW", "WORD16", "Авария сети CANopen (частотные преобразователи)"),

        //Crane SOZR. Стрела крана
        RegisterInfo(400, "HMI_Line_Angle_MW", "WORD16", "Угол поворота стрелы на HMI"),
        RegisterInfo(401, "HMI_Trolley_Y_MW", "WORD16", "Координаты точки тележки для HMI по оси Y"),
        RegisterInfo(402, "HMI_Trolley_X_MW", "WORD16", "Координаты точки тележки для HMI по оси X"),
        RegisterInfo(403, "HMI_Circle_Size_MW", "WORD16", "Радиус круга отражающего траекторию тележки на HMI"),
        RegisterInfo(404, "HMI_Strela_Lenght_MW", "WORD16", "Длина отрезка стрелы для панели (имеет значение при наклоне стрелы)"),
        RegisterInfo(405, "HMI_P1_Color_MW", "WORD16", "Анимация цвета полигона ( 0-Синий (задан но не активен), 1-Зеленый (задан и активен), 2-желтый (приближение к границе), 3-красный (касание границы полигона))"),
        RegisterInfo(406, "HMI_P2_Color_MW", "WORD16", "Анимация цвета полигона ( 0-Синий (задан но не активен), 1-Зеленый (задан и активен), 2-желтый (приближение к границе), 3-красный (касание границы полигона))"),
        RegisterInfo(407, "HMI_P3_Color_MW", "WORD16", "Анимация цвета полигона ( 0-Синий (задан но не активен), 1-Зеленый (задан и активен), 2-желтый (приближение к границе), 3-красный (касание границы полигона))"),
        RegisterInfo(408, "HMI_P4_Color_MW", "WORD16", "Анимация цвета полигона ( 0-Синий (задан но не активен), 1-Зеленый (задан и активен), 2-желтый (приближение к границе), 3-красный (касание границы полигона))"),

        //Crane SOZR
        // Параметры отезков для отображения полигона №1
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
        // Параметры отезков для отображения полигона №1
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
        // Параметры отезков для отображения полигона №1
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
        // Параметры отезков для отображения полигона №1
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
        // Параметры отезков для отображения полигона №1
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
        RegisterInfo(509, "HMI_SegmentVisibility_19_MW", "WORD16", "Видимость сегмента №19"),


        // Параметры отезков для отображения полигона №2
        RegisterInfo(510, "HMI_Point_Arr2_0_Width_MW", "WORD16", "Длина отрезка №00"),
        RegisterInfo(511, "HMI_Point_Arr2_1_Width_MW", "WORD16", "Длина отрезка №01"),
        RegisterInfo(512, "HMI_Point_Arr2_2_Width_MW", "WORD16", "Длина отрезка №02"),
        RegisterInfo(513, "HMI_Point_Arr2_3_Width_MW", "WORD16", "Длина отрезка №03"),
        RegisterInfo(514, "HMI_Point_Arr2_4_Width_MW", "WORD16", "Длина отрезка №04"),
        RegisterInfo(515, "HMI_Point_Arr2_5_Width_MW", "WORD16", "Длина отрезка №05"),
        RegisterInfo(516, "HMI_Point_Arr2_6_Width_MW", "WORD16", "Длина отрезка №06"),
        RegisterInfo(517, "HMI_Point_Arr2_7_Width_MW", "WORD16", "Длина отрезка №07"),
        RegisterInfo(518, "HMI_Point_Arr2_8_Width_MW", "WORD16", "Длина отрезка №08"),
        RegisterInfo(519, "HMI_Point_Arr2_9_Width_MW", "WORD16", "Длина отрезка №09"),
        RegisterInfo(520, "HMI_Point_Arr2_10_Width_MW", "WORD16", "Длина отрезка №10"),
        RegisterInfo(521, "HMI_Point_Arr2_11_Width_MW", "WORD16", "Длина отрезка №11"),
        RegisterInfo(522, "HMI_Point_Arr2_12_Width_MW", "WORD16", "Длина отрезка №12"),
        RegisterInfo(523, "HMI_Point_Arr2_13_Width_MW", "WORD16", "Длина отрезка №13"),
        RegisterInfo(524, "HMI_Point_Arr2_14_Width_MW", "WORD16", "Длина отрезка №14"),
        RegisterInfo(525, "HMI_Point_Arr2_15_Width_MW", "WORD16", "Длина отрезка №15"),
        RegisterInfo(526, "HMI_Point_Arr2_16_Width_MW", "WORD16", "Длина отрезка №16"),
        RegisterInfo(527, "HMI_Point_Arr2_17_Width_MW", "WORD16", "Длина отрезка №17"),
        RegisterInfo(528, "HMI_Point_Arr2_18_Width_MW", "WORD16", "Длина отрезка №18"),
        RegisterInfo(529, "HMI_Point_Arr2_19_Width_MW", "WORD16", "Длина отрезка №19"),
        // Параметры отезков для отображения полигона №2
        RegisterInfo(530, "HMI_Point_Arr2_0_TopP_MW", "WORD16", "Положение точки №0 по оси Y"),
        RegisterInfo(531, "HMI_Point_Arr2_1_TopP_MW", "WORD16", "Положение точки №1 по оси Y"),
        RegisterInfo(532, "HMI_Point_Arr2_2_TopP_MW", "WORD16", "Положение точки №2 по оси Y"),
        RegisterInfo(533, "HMI_Point_Arr2_3_TopP_MW", "WORD16", "Положение точки №3 по оси Y"),
        RegisterInfo(534, "HMI_Point_Arr2_4_TopP_MW", "WORD16", "Положение точки №4 по оси Y"),
        RegisterInfo(535, "HMI_Point_Arr2_5_TopP_MW", "WORD16", "Положение точки №5 по оси Y"),
        RegisterInfo(536, "HMI_Point_Arr2_6_TopP_MW", "WORD16", "Положение точки №6 по оси Y"),
        RegisterInfo(537, "HMI_Point_Arr2_7_TopP_MW", "WORD16", "Положение точки №7 по оси Y"),
        RegisterInfo(538, "HMI_Point_Arr2_8_TopP_MW", "WORD16", "Положение точки №8 по оси Y"),
        RegisterInfo(539, "HMI_Point_Arr2_9_TopP_MW", "WORD16", "Положение точки №9 по оси Y"),
        RegisterInfo(540, "HMI_Point_Arr2_10_TopP_MW", "WORD16", "Положение точки №10 по оси Y"),
        RegisterInfo(541, "HMI_Point_Arr2_11_TopP_MW", "WORD16", "Положение точки №11 по оси Y"),
        RegisterInfo(542, "HMI_Point_Arr2_12_TopP_MW", "WORD16", "Положение точки №12 по оси Y"),
        RegisterInfo(543, "HMI_Point_Arr2_13_TopP_MW", "WORD16", "Положение точки №13 по оси Y"),
        RegisterInfo(544, "HMI_Point_Arr2_14_TopP_MW", "WORD16", "Положение точки №14 по оси Y"),
        RegisterInfo(545, "HMI_Point_Arr2_15_TopP_MW", "WORD16", "Положение точки №15 по оси Y"),
        RegisterInfo(546, "HMI_Point_Arr2_16_TopP_MW", "WORD16", "Положение точки №16 по оси Y"),
        RegisterInfo(547, "HMI_Point_Arr2_17_TopP_MW", "WORD16", "Положение точки №17 по оси Y"),
        RegisterInfo(548, "HMI_Point_Arr2_18_TopP_MW", "WORD16", "Положение точки №18 по оси Y"),
        RegisterInfo(549, "HMI_Point_Arr2_19_TopP_MW", "WORD16", "Положение точки №19 по оси Y"),
        // Параметры отезков для отображения полигона №2
        RegisterInfo(550, "HMI_Point_Arr2_0_LeftP_MW", "WORD16", "Положение точки №0 по оси X"),
        RegisterInfo(551, "HMI_Point_Arr2_1_LeftP_MW", "WORD16", "Положение точки №1 по оси X"),
        RegisterInfo(552, "HMI_Point_Arr2_2_LeftP_MW", "WORD16", "Положение точки №2 по оси X"),
        RegisterInfo(553, "HMI_Point_Arr2_3_LeftP_MW", "WORD16", "Положение точки №3 по оси X"),
        RegisterInfo(554, "HMI_Point_Arr2_4_LeftP_MW", "WORD16", "Положение точки №4 по оси X"),
        RegisterInfo(555, "HMI_Point_Arr2_5_LeftP_MW", "WORD16", "Положение точки №5 по оси X"),
        RegisterInfo(556, "HMI_Point_Arr2_6_LeftP_MW", "WORD16", "Положение точки №6 по оси X"),
        RegisterInfo(557, "HMI_Point_Arr2_7_LeftP_MW", "WORD16", "Положение точки №7 по оси X"),
        RegisterInfo(558, "HMI_Point_Arr2_8_LeftP_MW", "WORD16", "Положение точки №8 по оси X"),
        RegisterInfo(559, "HMI_Point_Arr2_9_LeftP_MW", "WORD16", "Положение точки №9 по оси X"),
        RegisterInfo(560, "HMI_Point_Arr2_10_LeftP_MW", "WORD16", "Положение точки №10 по оси X"),
        RegisterInfo(561, "HMI_Point_Arr2_11_LeftP_MW", "WORD16", "Положение точки №11 по оси X"),
        RegisterInfo(562, "HMI_Point_Arr2_12_LeftP_MW", "WORD16", "Положение точки №12 по оси X"),
        RegisterInfo(563, "HMI_Point_Arr2_13_LeftP_MW", "WORD16", "Положение точки №13 по оси X"),
        RegisterInfo(564, "HMI_Point_Arr2_14_LeftP_MW", "WORD16", "Положение точки №14 по оси X"),
        RegisterInfo(565, "HMI_Point_Arr2_15_LeftP_MW", "WORD16", "Положение точки №15 по оси X"),
        RegisterInfo(566, "HMI_Point_Arr2_16_LeftP_MW", "WORD16", "Положение точки №16 по оси X"),
        RegisterInfo(567, "HMI_Point_Arr2_17_LeftP_MW", "WORD16", "Положение точки №17 по оси X"),
        RegisterInfo(568, "HMI_Point_Arr2_18_LeftP_MW", "WORD16", "Положение точки №18 по оси X"),
        RegisterInfo(569, "HMI_Point_Arr2_19_LeftP_MW", "WORD16", "Положение точки №19 по оси X"),
        // Параметры отезков для отображения полигона №2
        RegisterInfo(570, "HMI_Point_Arr2_0_Angle_MW", "WORD16", "Угол между точками 0 и 1"),
        RegisterInfo(571, "HMI_Point_Arr2_1_Angle_MW", "WORD16", "Угол между точками 1 и 2"),
        RegisterInfo(572, "HMI_Point_Arr2_2_Angle_MW", "WORD16", "Угол между точками 2 и 3"),
        RegisterInfo(573, "HMI_Point_Arr2_3_Angle_MW", "WORD16", "Угол между точками 3 и 4"),
        RegisterInfo(574, "HMI_Point_Arr2_4_Angle_MW", "WORD16", "Угол между точками 4 и 5"),
        RegisterInfo(575, "HMI_Point_Arr2_5_Angle_MW", "WORD16", "Угол между точками 5 и 6"),
        RegisterInfo(576, "HMI_Point_Arr2_6_Angle_MW", "WORD16", "Угол между точками 6 и 7"),
        RegisterInfo(577, "HMI_Point_Arr2_7_Angle_MW", "WORD16", "Угол между точками 7 и 8"),
        RegisterInfo(578, "HMI_Point_Arr2_8_Angle_MW", "WORD16", "Угол между точками 8 и 9"),
        RegisterInfo(579, "HMI_Point_Arr2_9_Angle_MW", "WORD16", "Угол между точками 9 и 10"),
        RegisterInfo(580, "HMI_Point_Arr2_10_Angle_MW", "WORD16", "Угол между точками 10 и 11"),
        RegisterInfo(581, "HMI_Point_Arr2_11_Angle_MW", "WORD16", "Угол между точками 11 и 12"),
        RegisterInfo(582, "HMI_Point_Arr2_12_Angle_MW", "WORD16", "Угол между точками 12 и 13"),
        RegisterInfo(583, "HMI_Point_Arr2_13_Angle_MW", "WORD16", "Угол между точками 13 и 14"),
        RegisterInfo(584, "HMI_Point_Arr2_14_Angle_MW", "WORD16", "Угол между точками 14 и 15"),
        RegisterInfo(585, "HMI_Point_Arr2_15_Angle_MW", "WORD16", "Угол между точками 15 и 16"),
        RegisterInfo(586, "HMI_Point_Arr2_16_Angle_MW", "WORD16", "Угол между точками 16 и 17"),
        RegisterInfo(587, "HMI_Point_Arr2_17_Angle_MW", "WORD16", "Угол между точками 17 и 18"),
        RegisterInfo(588, "HMI_Point_Arr2_18_Angle_MW", "WORD16", "Угол между точками 18 и 19"),
        RegisterInfo(589, "HMI_Point_Arr2_19_Angle_MW", "WORD16", "Угол между точками 19 и 0"),
        // Параметры отезков для отображения полигона №2
        RegisterInfo(590, "HMI_Point_Arr2_0_Visilbe_MW", "WORD16", "Отображение отрезка 0"),
        RegisterInfo(591, "HMI_Point_Arr2_1_Visilbe_MW", "WORD16", "Отображение отрезка 1"),
        RegisterInfo(592, "HMI_Point_Arr2_2_Visilbe_MW", "WORD16", "Отображение отрезка 2"),
        RegisterInfo(593, "HMI_Point_Arr2_3_Visilbe_MW", "WORD16", "Отображение отрезка 3"),
        RegisterInfo(594, "HMI_Point_Arr2_4_Visilbe_MW", "WORD16", "Отображение отрезка 4"),
        RegisterInfo(595, "HMI_Point_Arr2_5_Visilbe_MW", "WORD16", "Отображение отрезка 5"),
        RegisterInfo(596, "HMI_Point_Arr2_6_Visilbe_MW", "WORD16", "Отображение отрезка 6"),
        RegisterInfo(597, "HMI_Point_Arr2_7_Visilbe_MW", "WORD16", "Отображение отрезка 7"),
        RegisterInfo(598, "HMI_Point_Arr2_8_Visilbe_MW", "WORD16", "Отображение отрезка 8"),
        RegisterInfo(599, "HMI_Point_Arr2_9_Visilbe_MW", "WORD16", "Отображение отрезка 9"),
        RegisterInfo(600, "HMI_Point_Arr2_10_Visilbe_MW", "WORD16", "Отображение отрезка 10"),
        RegisterInfo(601, "HMI_Point_Arr2_11_Visilbe_MW", "WORD16", "Отображение отрезка 11"),
        RegisterInfo(602, "HMI_Point_Arr2_12_Visilbe_MW", "WORD16", "Отображение отрезка 12"),
        RegisterInfo(603, "HMI_Point_Arr2_13_Visilbe_MW", "WORD16", "Отображение отрезка 13"),
        RegisterInfo(604, "HMI_Point_Arr2_14_Visilbe_MW", "WORD16", "Отображение отрезка 14"),
        RegisterInfo(605, "HMI_Point_Arr2_15_Visilbe_MW", "WORD16", "Отображение отрезка 15"),
        RegisterInfo(606, "HMI_Point_Arr2_16_Visilbe_MW", "WORD16", "Отображение отрезка 16"),
        RegisterInfo(607, "HMI_Point_Arr2_17_Visilbe_MW", "WORD16", "Отображение отрезка 17"),
        RegisterInfo(608, "HMI_Point_Arr2_18_Visilbe_MW", "WORD16", "Отображение отрезка 18"),
        RegisterInfo(609, "HMI_Point_Arr2_19_Visilbe_MW", "WORD16", "Отображение отрезка 19"),


        // Параметры отезков для отображения полигона №3
        RegisterInfo(1410, "HMI_Point_Arr3_0_Width_MW", "WORD16", "Длина отрезка №00"),
        RegisterInfo(1411, "HMI_Point_Arr3_1_Width_MW", "WORD16", "Длина отрезка №01"),
        RegisterInfo(1412, "HMI_Point_Arr3_2_Width_MW", "WORD16", "Длина отрезка №02"),
        RegisterInfo(1413, "HMI_Point_Arr3_3_Width_MW", "WORD16", "Длина отрезка №03"),
        RegisterInfo(1414, "HMI_Point_Arr3_4_Width_MW", "WORD16", "Длина отрезка №04"),
        RegisterInfo(1415, "HMI_Point_Arr3_5_Width_MW", "WORD16", "Длина отрезка №05"),
        RegisterInfo(1416, "HMI_Point_Arr3_6_Width_MW", "WORD16", "Длина отрезка №06"),
        RegisterInfo(1417, "HMI_Point_Arr3_7_Width_MW", "WORD16", "Длина отрезка №07"),
        RegisterInfo(1418, "HMI_Point_Arr3_8_Width_MW", "WORD16", "Длина отрезка №08"),
        RegisterInfo(1419, "HMI_Point_Arr3_9_Width_MW", "WORD16", "Длина отрезка №09"),
        RegisterInfo(1420, "HMI_Point_Arr3_10_Width_MW", "WORD16", "Длина отрезка №10"),
        RegisterInfo(1421, "HMI_Point_Arr3_11_Width_MW", "WORD16", "Длина отрезка №11"),
        RegisterInfo(1422, "HMI_Point_Arr3_12_Width_MW", "WORD16", "Длина отрезка №12"),
        RegisterInfo(1423, "HMI_Point_Arr3_13_Width_MW", "WORD16", "Длина отрезка №13"),
        RegisterInfo(1424, "HMI_Point_Arr3_14_Width_MW", "WORD16", "Длина отрезка №14"),
        RegisterInfo(1425, "HMI_Point_Arr3_15_Width_MW", "WORD16", "Длина отрезка №15"),
        RegisterInfo(1426, "HMI_Point_Arr3_16_Width_MW", "WORD16", "Длина отрезка №16"),
        RegisterInfo(1427, "HMI_Point_Arr3_17_Width_MW", "WORD16", "Длина отрезка №17"),
        RegisterInfo(1428, "HMI_Point_Arr3_18_Width_MW", "WORD16", "Длина отрезка №18"),
        RegisterInfo(1429, "HMI_Point_Arr3_19_Width_MW", "WORD16", "Длина отрезка №19"),
        // Параметры отезков для отображения полигона №3
        RegisterInfo(1430, "HMI_Point_Arr3_0_TopP_MW", "WORD16", "Положение точки №0 по оси Y"),
        RegisterInfo(1431, "HMI_Point_Arr3_1_TopP_MW", "WORD16", "Положение точки №1 по оси Y"),
        RegisterInfo(1432, "HMI_Point_Arr3_2_TopP_MW", "WORD16", "Положение точки №2 по оси Y"),
        RegisterInfo(1433, "HMI_Point_Arr3_3_TopP_MW", "WORD16", "Положение точки №3 по оси Y"),
        RegisterInfo(1434, "HMI_Point_Arr3_4_TopP_MW", "WORD16", "Положение точки №4 по оси Y"),
        RegisterInfo(1435, "HMI_Point_Arr3_5_TopP_MW", "WORD16", "Положение точки №5 по оси Y"),
        RegisterInfo(1436, "HMI_Point_Arr3_6_TopP_MW", "WORD16", "Положение точки №6 по оси Y"),
        RegisterInfo(1437, "HMI_Point_Arr3_7_TopP_MW", "WORD16", "Положение точки №7 по оси Y"),
        RegisterInfo(1438, "HMI_Point_Arr3_8_TopP_MW", "WORD16", "Положение точки №8 по оси Y"),
        RegisterInfo(1439, "HMI_Point_Arr3_9_TopP_MW", "WORD16", "Положение точки №9 по оси Y"),
        RegisterInfo(1440, "HMI_Point_Arr3_10_TopP_MW", "WORD16", "Положение точки №10 по оси Y"),
        RegisterInfo(1441, "HMI_Point_Arr3_11_TopP_MW", "WORD16", "Положение точки №11 по оси Y"),
        RegisterInfo(1442, "HMI_Point_Arr3_12_TopP_MW", "WORD16", "Положение точки №12 по оси Y"),
        RegisterInfo(1443, "HMI_Point_Arr3_13_TopP_MW", "WORD16", "Положение точки №13 по оси Y"),
        RegisterInfo(1444, "HMI_Point_Arr3_14_TopP_MW", "WORD16", "Положение точки №14 по оси Y"),
        RegisterInfo(1445, "HMI_Point_Arr3_15_TopP_MW", "WORD16", "Положение точки №15 по оси Y"),
        RegisterInfo(1446, "HMI_Point_Arr3_16_TopP_MW", "WORD16", "Положение точки №16 по оси Y"),
        RegisterInfo(1447, "HMI_Point_Arr3_17_TopP_MW", "WORD16", "Положение точки №17 по оси Y"),
        RegisterInfo(1448, "HMI_Point_Arr3_18_TopP_MW", "WORD16", "Положение точки №18 по оси Y"),
        RegisterInfo(1449, "HMI_Point_Arr3_19_TopP_MW", "WORD16", "Положение точки №19 по оси Y"),
        // Параметры отезков для отображения полигона №3
        RegisterInfo(1450, "HMI_Point_Arr3_0_LeftP_MW", "WORD16", "Положение точки №0 по оси X"),
        RegisterInfo(1451, "HMI_Point_Arr3_1_LeftP_MW", "WORD16", "Положение точки №1 по оси X"),
        RegisterInfo(1452, "HMI_Point_Arr3_2_LeftP_MW", "WORD16", "Положение точки №2 по оси X"),
        RegisterInfo(1453, "HMI_Point_Arr3_3_LeftP_MW", "WORD16", "Положение точки №3 по оси X"),
        RegisterInfo(1454, "HMI_Point_Arr3_4_LeftP_MW", "WORD16", "Положение точки №4 по оси X"),
        RegisterInfo(1455, "HMI_Point_Arr3_5_LeftP_MW", "WORD16", "Положение точки №5 по оси X"),
        RegisterInfo(1456, "HMI_Point_Arr3_6_LeftP_MW", "WORD16", "Положение точки №6 по оси X"),
        RegisterInfo(1457, "HMI_Point_Arr3_7_LeftP_MW", "WORD16", "Положение точки №7 по оси X"),
        RegisterInfo(1458, "HMI_Point_Arr3_8_LeftP_MW", "WORD16", "Положение точки №8 по оси X"),
        RegisterInfo(1459, "HMI_Point_Arr3_9_LeftP_MW", "WORD16", "Положение точки №9 по оси X"),
        RegisterInfo(1460, "HMI_Point_Arr3_10_LeftP_MW", "WORD16", "Положение точки №10 по оси X"),
        RegisterInfo(1461, "HMI_Point_Arr3_11_LeftP_MW", "WORD16", "Положение точки №11 по оси X"),
        RegisterInfo(1462, "HMI_Point_Arr3_12_LeftP_MW", "WORD16", "Положение точки №12 по оси X"),
        RegisterInfo(1463, "HMI_Point_Arr3_13_LeftP_MW", "WORD16", "Положение точки №13 по оси X"),
        RegisterInfo(1464, "HMI_Point_Arr3_14_LeftP_MW", "WORD16", "Положение точки №14 по оси X"),
        RegisterInfo(1465, "HMI_Point_Arr3_15_LeftP_MW", "WORD16", "Положение точки №15 по оси X"),
        RegisterInfo(1466, "HMI_Point_Arr3_16_LeftP_MW", "WORD16", "Положение точки №16 по оси X"),
        RegisterInfo(1467, "HMI_Point_Arr3_17_LeftP_MW", "WORD16", "Положение точки №17 по оси X"),
        RegisterInfo(1468, "HMI_Point_Arr3_18_LeftP_MW", "WORD16", "Положение точки №18 по оси X"),
        RegisterInfo(1469, "HMI_Point_Arr3_19_LeftP_MW", "WORD16", "Положение точки №19 по оси X"),
        // Параметры отезков для отображения полигона №3
        RegisterInfo(1470, "HMI_Point_Arr3_0_Angle_MW", "WORD16", "Угол между точками 0 и 1"),
        RegisterInfo(1471, "HMI_Point_Arr3_1_Angle_MW", "WORD16", "Угол между точками 1 и 2"),
        RegisterInfo(1472, "HMI_Point_Arr3_2_Angle_MW", "WORD16", "Угол между точками 2 и 3"),
        RegisterInfo(1473, "HMI_Point_Arr3_3_Angle_MW", "WORD16", "Угол между точками 3 и 4"),
        RegisterInfo(1474, "HMI_Point_Arr3_4_Angle_MW", "WORD16", "Угол между точками 4 и 5"),
        RegisterInfo(1475, "HMI_Point_Arr3_5_Angle_MW", "WORD16", "Угол между точками 5 и 6"),
        RegisterInfo(1476, "HMI_Point_Arr3_6_Angle_MW", "WORD16", "Угол между точками 6 и 7"),
        RegisterInfo(1477, "HMI_Point_Arr3_7_Angle_MW", "WORD16", "Угол между точками 7 и 8"),
        RegisterInfo(1478, "HMI_Point_Arr3_8_Angle_MW", "WORD16", "Угол между точками 8 и 9"),
        RegisterInfo(1479, "HMI_Point_Arr3_9_Angle_MW", "WORD16", "Угол между точками 9 и 10"),
        RegisterInfo(1480, "HMI_Point_Arr3_10_Angle_MW", "WORD16", "Угол между точками 10 и 11"),
        RegisterInfo(1481, "HMI_Point_Arr3_11_Angle_MW", "WORD16", "Угол между точками 11 и 12"),
        RegisterInfo(1482, "HMI_Point_Arr3_12_Angle_MW", "WORD16", "Угол между точками 12 и 13"),
        RegisterInfo(1483, "HMI_Point_Arr3_13_Angle_MW", "WORD16", "Угол между точками 13 и 14"),
        RegisterInfo(1484, "HMI_Point_Arr3_14_Angle_MW", "WORD16", "Угол между точками 14 и 15"),
        RegisterInfo(1485, "HMI_Point_Arr3_15_Angle_MW", "WORD16", "Угол между точками 15 и 16"),
        RegisterInfo(1486, "HMI_Point_Arr3_16_Angle_MW", "WORD16", "Угол между точками 16 и 17"),
        RegisterInfo(1487, "HMI_Point_Arr3_17_Angle_MW", "WORD16", "Угол между точками 17 и 18"),
        RegisterInfo(1488, "HMI_Point_Arr3_18_Angle_MW", "WORD16", "Угол между точками 18 и 19"),
        RegisterInfo(1489, "HMI_Point_Arr3_19_Angle_MW", "WORD16", "Угол между точками 19 и 0"),
        // Параметры отезков для отображения полигона №3
        RegisterInfo(1490, "HMI_Point_Arr3_0_Visilbe_MW", "WORD16", "Отображение отрезка 0"),
        RegisterInfo(1491, "HMI_Point_Arr3_1_Visilbe_MW", "WORD16", "Отображение отрезка 1"),
        RegisterInfo(1492, "HMI_Point_Arr3_2_Visilbe_MW", "WORD16", "Отображение отрезка 2"),
        RegisterInfo(1493, "HMI_Point_Arr3_3_Visilbe_MW", "WORD16", "Отображение отрезка 3"),
        RegisterInfo(1494, "HMI_Point_Arr3_4_Visilbe_MW", "WORD16", "Отображение отрезка 4"),
        RegisterInfo(1495, "HMI_Point_Arr3_5_Visilbe_MW", "WORD16", "Отображение отрезка 5"),
        RegisterInfo(1496, "HMI_Point_Arr3_6_Visilbe_MW", "WORD16", "Отображение отрезка 6"),
        RegisterInfo(1497, "HMI_Point_Arr3_7_Visilbe_MW", "WORD16", "Отображение отрезка 7"),
        RegisterInfo(1498, "HMI_Point_Arr3_8_Visilbe_MW", "WORD16", "Отображение отрезка 8"),
        RegisterInfo(1499, "HMI_Point_Arr3_9_Visilbe_MW", "WORD16", "Отображение отрезка 9"),
        RegisterInfo(1500, "HMI_Point_Arr3_10_Visilbe_MW", "WORD16", "Отображение отрезка 10"),
        RegisterInfo(1501, "HMI_Point_Arr3_11_Visilbe_MW", "WORD16", "Отображение отрезка 11"),
        RegisterInfo(1502, "HMI_Point_Arr3_12_Visilbe_MW", "WORD16", "Отображение отрезка 12"),
        RegisterInfo(1503, "HMI_Point_Arr3_13_Visilbe_MW", "WORD16", "Отображение отрезка 13"),
        RegisterInfo(1504, "HMI_Point_Arr3_14_Visilbe_MW", "WORD16", "Отображение отрезка 14"),
        RegisterInfo(1505, "HMI_Point_Arr3_15_Visilbe_MW", "WORD16", "Отображение отрезка 15"),
        RegisterInfo(1506, "HMI_Point_Arr3_16_Visilbe_MW", "WORD16", "Отображение отрезка 16"),
        RegisterInfo(1507, "HMI_Point_Arr3_17_Visilbe_MW", "WORD16", "Отображение отрезка 17"),
        RegisterInfo(1508, "HMI_Point_Arr3_18_Visilbe_MW", "WORD16", "Отображение отрезка 18"),
        RegisterInfo(1509, "HMI_Point_Arr3_19_Visilbe_MW", "WORD16", "Отображение отрезка 19"),


        // Параметры отезков для отображения полигона №4
        RegisterInfo(1510, "HMI_Point_Arr4_0_Width_MW", "WORD16", "Длина отрезка №00"),
        RegisterInfo(1511, "HMI_Point_Arr4_1_Width_MW", "WORD16", "Длина отрезка №01"),
        RegisterInfo(1512, "HMI_Point_Arr4_2_Width_MW", "WORD16", "Длина отрезка №02"),
        RegisterInfo(1513, "HMI_Point_Arr4_3_Width_MW", "WORD16", "Длина отрезка №03"),
        RegisterInfo(1514, "HMI_Point_Arr4_4_Width_MW", "WORD16", "Длина отрезка №04"),
        RegisterInfo(1515, "HMI_Point_Arr4_5_Width_MW", "WORD16", "Длина отрезка №05"),
        RegisterInfo(1516, "HMI_Point_Arr4_6_Width_MW", "WORD16", "Длина отрезка №06"),
        RegisterInfo(1517, "HMI_Point_Arr4_7_Width_MW", "WORD16", "Длина отрезка №07"),
        RegisterInfo(1518, "HMI_Point_Arr4_8_Width_MW", "WORD16", "Длина отрезка №08"),
        RegisterInfo(1519, "HMI_Point_Arr4_9_Width_MW", "WORD16", "Длина отрезка №09"),
        RegisterInfo(1520, "HMI_Point_Arr4_10_Width_MW", "WORD16", "Длина отрезка №10"),
        RegisterInfo(1521, "HMI_Point_Arr4_11_Width_MW", "WORD16", "Длина отрезка №11"),
        RegisterInfo(1522, "HMI_Point_Arr4_12_Width_MW", "WORD16", "Длина отрезка №12"),
        RegisterInfo(1523, "HMI_Point_Arr4_13_Width_MW", "WORD16", "Длина отрезка №13"),
        RegisterInfo(1524, "HMI_Point_Arr4_14_Width_MW", "WORD16", "Длина отрезка №14"),
        RegisterInfo(1525, "HMI_Point_Arr4_15_Width_MW", "WORD16", "Длина отрезка №15"),
        RegisterInfo(1526, "HMI_Point_Arr4_16_Width_MW", "WORD16", "Длина отрезка №16"),
        RegisterInfo(1527, "HMI_Point_Arr4_17_Width_MW", "WORD16", "Длина отрезка №17"),
        RegisterInfo(1528, "HMI_Point_Arr4_18_Width_MW", "WORD16", "Длина отрезка №18"),
        RegisterInfo(1529, "HMI_Point_Arr4_19_Width_MW", "WORD16", "Длина отрезка №19"),
        // Параметры отезков для отображения полигона №4
        RegisterInfo(1530, "HMI_Point_Arr4_0_TopP_MW", "WORD16", "Положение точки №0 по оси Y"),
        RegisterInfo(1531, "HMI_Point_Arr4_1_TopP_MW", "WORD16", "Положение точки №1 по оси Y"),
        RegisterInfo(1532, "HMI_Point_Arr4_2_TopP_MW", "WORD16", "Положение точки №2 по оси Y"),
        RegisterInfo(1533, "HMI_Point_Arr4_3_TopP_MW", "WORD16", "Положение точки №3 по оси Y"),
        RegisterInfo(1534, "HMI_Point_Arr4_4_TopP_MW", "WORD16", "Положение точки №4 по оси Y"),
        RegisterInfo(1535, "HMI_Point_Arr4_5_TopP_MW", "WORD16", "Положение точки №5 по оси Y"),
        RegisterInfo(1536, "HMI_Point_Arr4_6_TopP_MW", "WORD16", "Положение точки №6 по оси Y"),
        RegisterInfo(1537, "HMI_Point_Arr4_7_TopP_MW", "WORD16", "Положение точки №7 по оси Y"),
        RegisterInfo(1538, "HMI_Point_Arr4_8_TopP_MW", "WORD16", "Положение точки №8 по оси Y"),
        RegisterInfo(1539, "HMI_Point_Arr4_9_TopP_MW", "WORD16", "Положение точки №9 по оси Y"),
        RegisterInfo(1540, "HMI_Point_Arr4_10_TopP_MW", "WORD16", "Положение точки №10 по оси Y"),
        RegisterInfo(1541, "HMI_Point_Arr4_11_TopP_MW", "WORD16", "Положение точки №11 по оси Y"),
        RegisterInfo(1542, "HMI_Point_Arr4_12_TopP_MW", "WORD16", "Положение точки №12 по оси Y"),
        RegisterInfo(1543, "HMI_Point_Arr4_13_TopP_MW", "WORD16", "Положение точки №13 по оси Y"),
        RegisterInfo(1544, "HMI_Point_Arr4_14_TopP_MW", "WORD16", "Положение точки №14 по оси Y"),
        RegisterInfo(1545, "HMI_Point_Arr4_15_TopP_MW", "WORD16", "Положение точки №15 по оси Y"),
        RegisterInfo(1546, "HMI_Point_Arr4_16_TopP_MW", "WORD16", "Положение точки №16 по оси Y"),
        RegisterInfo(1547, "HMI_Point_Arr4_17_TopP_MW", "WORD16", "Положение точки №17 по оси Y"),
        RegisterInfo(1548, "HMI_Point_Arr4_18_TopP_MW", "WORD16", "Положение точки №18 по оси Y"),
        RegisterInfo(1549, "HMI_Point_Arr4_19_TopP_MW", "WORD16", "Положение точки №19 по оси Y"),
        // Параметры отезков для отображения полигона №4
        RegisterInfo(1550, "HMI_Point_Arr4_0_LeftP_MW", "WORD16", "Положение точки №0 по оси X"),
        RegisterInfo(1551, "HMI_Point_Arr4_1_LeftP_MW", "WORD16", "Положение точки №1 по оси X"),
        RegisterInfo(1552, "HMI_Point_Arr4_2_LeftP_MW", "WORD16", "Положение точки №2 по оси X"),
        RegisterInfo(1553, "HMI_Point_Arr4_3_LeftP_MW", "WORD16", "Положение точки №3 по оси X"),
        RegisterInfo(1554, "HMI_Point_Arr4_4_LeftP_MW", "WORD16", "Положение точки №4 по оси X"),
        RegisterInfo(1555, "HMI_Point_Arr4_5_LeftP_MW", "WORD16", "Положение точки №5 по оси X"),
        RegisterInfo(1556, "HMI_Point_Arr4_6_LeftP_MW", "WORD16", "Положение точки №6 по оси X"),
        RegisterInfo(1557, "HMI_Point_Arr4_7_LeftP_MW", "WORD16", "Положение точки №7 по оси X"),
        RegisterInfo(1558, "HMI_Point_Arr4_8_LeftP_MW", "WORD16", "Положение точки №8 по оси X"),
        RegisterInfo(1559, "HMI_Point_Arr4_9_LeftP_MW", "WORD16", "Положение точки №9 по оси X"),
        RegisterInfo(1560, "HMI_Point_Arr4_10_LeftP_MW", "WORD16", "Положение точки №10 по оси X"),
        RegisterInfo(1561, "HMI_Point_Arr4_11_LeftP_MW", "WORD16", "Положение точки №11 по оси X"),
        RegisterInfo(1562, "HMI_Point_Arr4_12_LeftP_MW", "WORD16", "Положение точки №12 по оси X"),
        RegisterInfo(1563, "HMI_Point_Arr4_13_LeftP_MW", "WORD16", "Положение точки №13 по оси X"),
        RegisterInfo(1564, "HMI_Point_Arr4_14_LeftP_MW", "WORD16", "Положение точки №14 по оси X"),
        RegisterInfo(1565, "HMI_Point_Arr4_15_LeftP_MW", "WORD16", "Положение точки №15 по оси X"),
        RegisterInfo(1566, "HMI_Point_Arr4_16_LeftP_MW", "WORD16", "Положение точки №16 по оси X"),
        RegisterInfo(1567, "HMI_Point_Arr4_17_LeftP_MW", "WORD16", "Положение точки №17 по оси X"),
        RegisterInfo(1568, "HMI_Point_Arr4_18_LeftP_MW", "WORD16", "Положение точки №18 по оси X"),
        RegisterInfo(1569, "HMI_Point_Arr4_19_LeftP_MW", "WORD16", "Положение точки №19 по оси X"),
        // Параметры отезков для отображения полигона №4
        RegisterInfo(1570, "HMI_Point_Arr4_0_Angle_MW", "WORD16", "Угол между точками 0 и 1"),
        RegisterInfo(1571, "HMI_Point_Arr4_1_Angle_MW", "WORD16", "Угол между точками 1 и 2"),
        RegisterInfo(1572, "HMI_Point_Arr4_2_Angle_MW", "WORD16", "Угол между точками 2 и 3"),
        RegisterInfo(1573, "HMI_Point_Arr4_3_Angle_MW", "WORD16", "Угол между точками 3 и 4"),
        RegisterInfo(1574, "HMI_Point_Arr4_4_Angle_MW", "WORD16", "Угол между точками 4 и 5"),
        RegisterInfo(1575, "HMI_Point_Arr4_5_Angle_MW", "WORD16", "Угол между точками 5 и 6"),
        RegisterInfo(1576, "HMI_Point_Arr4_6_Angle_MW", "WORD16", "Угол между точками 6 и 7"),
        RegisterInfo(1577, "HMI_Point_Arr4_7_Angle_MW", "WORD16", "Угол между точками 7 и 8"),
        RegisterInfo(1578, "HMI_Point_Arr4_8_Angle_MW", "WORD16", "Угол между точками 8 и 9"),
        RegisterInfo(1579, "HMI_Point_Arr4_9_Angle_MW", "WORD16", "Угол между точками 9 и 10"),
        RegisterInfo(1580, "HMI_Point_Arr4_10_Angle_MW", "WORD16", "Угол между точками 10 и 11"),
        RegisterInfo(1581, "HMI_Point_Arr4_11_Angle_MW", "WORD16", "Угол между точками 11 и 12"),
        RegisterInfo(1582, "HMI_Point_Arr4_12_Angle_MW", "WORD16", "Угол между точками 12 и 13"),
        RegisterInfo(1583, "HMI_Point_Arr4_13_Angle_MW", "WORD16", "Угол между точками 13 и 14"),
        RegisterInfo(1584, "HMI_Point_Arr4_14_Angle_MW", "WORD16", "Угол между точками 14 и 15"),
        RegisterInfo(1585, "HMI_Point_Arr4_15_Angle_MW", "WORD16", "Угол между точками 15 и 16"),
        RegisterInfo(1586, "HMI_Point_Arr4_16_Angle_MW", "WORD16", "Угол между точками 16 и 17"),
        RegisterInfo(1587, "HMI_Point_Arr4_17_Angle_MW", "WORD16", "Угол между точками 17 и 18"),
        RegisterInfo(1588, "HMI_Point_Arr4_18_Angle_MW", "WORD16", "Угол между точками 18 и 19"),
        RegisterInfo(1589, "HMI_Point_Arr4_19_Angle_MW", "WORD16", "Угол между точками 19 и 0"),
        // Параметры отезков для отображения полигона №4
        RegisterInfo(1590, "HMI_Point_Arr4_0_Visilbe_MW", "WORD16", "Отображение отрезка 0"),
        RegisterInfo(1591, "HMI_Point_Arr4_1_Visilbe_MW", "WORD16", "Отображение отрезка 1"),
        RegisterInfo(1592, "HMI_Point_Arr4_2_Visilbe_MW", "WORD16", "Отображение отрезка 2"),
        RegisterInfo(1593, "HMI_Point_Arr4_3_Visilbe_MW", "WORD16", "Отображение отрезка 3"),
        RegisterInfo(1594, "HMI_Point_Arr4_4_Visilbe_MW", "WORD16", "Отображение отрезка 4"),
        RegisterInfo(1595, "HMI_Point_Arr4_5_Visilbe_MW", "WORD16", "Отображение отрезка 5"),
        RegisterInfo(1596, "HMI_Point_Arr4_6_Visilbe_MW", "WORD16", "Отображение отрезка 6"),
        RegisterInfo(1597, "HMI_Point_Arr4_7_Visilbe_MW", "WORD16", "Отображение отрезка 7"),
        RegisterInfo(1598, "HMI_Point_Arr4_8_Visilbe_MW", "WORD16", "Отображение отрезка 8"),
        RegisterInfo(1599, "HMI_Point_Arr4_9_Visilbe_MW", "WORD16", "Отображение отрезка 9"),
        RegisterInfo(1600, "HMI_Point_Arr4_10_Visilbe_MW", "WORD16", "Отображение отрезка 10"),
        RegisterInfo(1601, "HMI_Point_Arr4_11_Visilbe_MW", "WORD16", "Отображение отрезка 11"),
        RegisterInfo(1602, "HMI_Point_Arr4_12_Visilbe_MW", "WORD16", "Отображение отрезка 12"),
        RegisterInfo(1603, "HMI_Point_Arr4_13_Visilbe_MW", "WORD16", "Отображение отрезка 13"),
        RegisterInfo(1604, "HMI_Point_Arr4_14_Visilbe_MW", "WORD16", "Отображение отрезка 14"),
        RegisterInfo(1605, "HMI_Point_Arr4_15_Visilbe_MW", "WORD16", "Отображение отрезка 15"),
        RegisterInfo(1606, "HMI_Point_Arr4_16_Visilbe_MW", "WORD16", "Отображение отрезка 16"),
        RegisterInfo(1607, "HMI_Point_Arr4_17_Visilbe_MW", "WORD16", "Отображение отрезка 17"),
        RegisterInfo(1608, "HMI_Point_Arr4_18_Visilbe_MW", "WORD16", "Отображение отрезка 18"),
        RegisterInfo(1609, "HMI_Point_Arr4_19_Visilbe_MW", "WORD16", "Отображение отрезка 19")

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

        RegisterInfo(641, "HMI_TimeZone_MW", "WORD16", "(* Часовой пояс в UTC *)"),
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
        RegisterInfo(850, "HMI_Write_sR_Crane_No_MW", "WORD16", "Заводской номер крана"),

        RegisterInfo(150, "Crane_No_MW", "WORD16", "Заводской тип крана"),
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

        RegisterInfo(155, "Trolley_Position_MW", "WORD16", "Текущее положение тележки на стреле *0,1м"),
        RegisterInfo(159, "Hoist_Position_MW", "WORD16", "Текущее положение подъема *0,1м"),

        RegisterInfo(195, "MaxWeight2_MW", "WORD16", "Максимальный вес на конце стрелы"),
        RegisterInfo(199, "MaxWeight1_MW", "WORD16", "Максимальный вес для выбранной ГХ"),
        RegisterInfo(197, "Crane_Lenght_MW", "WORD16", "Длина стрелы крана"),
        RegisterInfo(198, "HMI_Curr_AI_MW", "WORD16", "Текущее значение с датчика груза в единицах входа модуля"),

        RegisterInfo(314, "HMI_Weight_MW", "WORD16", "Текущий вес на крюке *0,1т"),
        RegisterInfo(316, "HMI_Zapasovka_MW", "WORD16", "Значение - текущая запасовка. 2 / 4"),

        RegisterInfo(661, "HMI_Read_Weight_Calib_Stat", "WORD16", "Статус калибровки датчика усилия"),
        RegisterInfo(662, "HMI_Read_Weight_Calib_Message", "WORD16", "Сообщения при калибровке датчика усилия"),


        RegisterInfo(861, "HMI_Write_xHMI_Curr_Gruz_Blok_MW", "WORD16", "Запись выбора блока, =0 с блоком, =1 без блока"),
        RegisterInfo(862, "HMI_Write_iHMI_Curr_Gruz_Fun_MW", "WORD16", "Выбор грузовая характеристика"),
        RegisterInfo(863, "HMI_Write_rMin_Weight_Val_MW", "WORD16", "C HMI значение в тоннах при минимальном грузе"),
        RegisterInfo(864, "HMI_Write_rMax_Weight_Val_MW", "WORD16", "C HMI значение в тоннах при максимальном грузе"),
        RegisterInfo(865, "HMI_Write_xMin_Weight_Set_MW", "WORD16", "Калибровать минимальный груз"),
        RegisterInfo(866, "HMI_Write_xMax_Weight_Set_MW", "WORD16", "Калибровать максимальный груз"),

        RegisterInfo(868, "HMI_Write_xUISettingLoadCapacity_Active_MW", "WORD16", "Флаг активности экрана настройки грузоподъемности"),
        RegisterInfo(1260, "HMI_Read_Weight_Calib_Reset", "WORD16", "Сброс калибровки датчики усилия 1-сброс минимума, 2-сброс максимума"),



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

        //Параметры датчиков.
        RegisterInfo(382,  "udi_AbsEncFenac_Hoist_Position_MD",  "WORD16",  "Текущее положение крана *0,1м"),
        RegisterInfo(383,  "udi_AbsEncFenac_Hoist_Position_MD",  "WORD16",  "Текущее положение крана *0,1м"),
        RegisterInfo(386,  "udi_AbsEncFenac_Slew_Position_MD",  "WORD16",  "Текущее положение крана *0,1м"),
        RegisterInfo(387,  "udi_AbsEncFenac_Slew_Position_MD",  "WORD16",  "Текущее положение крана *0,1м"),
        RegisterInfo(390,  "udi_AbsEncFenac_Trolley_Position_MD",  "WORD16",  "Текущее положение крана *0,1м"),
        RegisterInfo(391,  "udi_AbsEncFenac_Trolley_Position_MD",  "WORD16",  "Текущее положение крана *0,1м"),
        RegisterInfo(394,  "udi_AbsEncFenac_Crane_Position_MD",  "WORD16",  "Текущее положение крана *0,1м"),
        RegisterInfo(395,  "udi_AbsEncFenac_Crane_Position_MD",  "WORD16",  "Текущее положение крана *0,1м"),

        //Параметры настройки датчика подъема
        RegisterInfo(803, "HMI_Write_xHoist_Limit_En_MW", "WORD16", "Включить проверку ограничения подъемного мех. крюка"),
        RegisterInfo(1623, "HMI_Hoist_Limit_Stat_MW", "WORD16", "Статус функции проверки ограничений в наладке"),
        RegisterInfo(804, "HMI_Write_xHoist_Fwd_Mem_MW", "WORD16", "Запомнить положение по высоте при движении вверх"),
        RegisterInfo(805, "HMI_Write_xHoist_Rev_Mem_MW", "WORD16", "Запомнить положение по высоте при движении вниз"),

        RegisterInfo(806, "HMI_Write_rHoist_Pos_Fwd_Stop_MW", "WORD16", "Значение при котором останавливаем крюк вверх"),
        RegisterInfo(807, "HMI_Write_rHoist_Pos_Rev_Stop_MW", "WORD16", "Значение при котором останавливаем крюк вниз"),
        RegisterInfo(808, "HMI_Write_rHoist_Pos_Fwd_DwnSp_MW", "WORD16", "Значение при котором снижаем скорость двигая крюк вверх"),
        RegisterInfo(809, "HMI_Write_rHoist_Pos_Rev_DwnSp_MW", "WORD16", "Значение при котором снижаем скорость двигая крюк вниз"),
        RegisterInfo(894, "HMI_Write_rHoist_Pos_Zero_MW", "WORD16", "Значение нулевой высоты привода подъема"),
        RegisterInfo(895, "HMI_Write_xHoist_Zero_Mem_MW", "WORD16", "Запомнить положение нулевой высоты подъема"),


        RegisterInfo(810, "HMI_Write_xHoist_LSP_Auto_MW", "WORD16", "Включить адаптивное снижение скорости"),
        RegisterInfo(811, "HMI_Write_rHoist_LSP_Dist3_MW", "WORD16", "дистанция снижение до 3-й скорости"),
        RegisterInfo(812, "HMI_Write_rHoist_LSP_Dist2_MW", "WORD16", "дистанция снижение до 2-й скорости"),
        RegisterInfo(813, "HMI_Write_rHoist_LSP_Dist1_MW", "WORD16", "дистанция снижение до 1-й скорости"),

        RegisterInfo(160, "Hoist_Max_Position_MW", "WORD16", "Максимальное положение подъема *0,1м"),
        RegisterInfo(161, "Hoist_Min_Position_MW", "WORD16", "Минимальное положение подъема *0,1м"),
        RegisterInfo(338, "HMI_Hoist_Pos_Fwd_DwnSp_MW", "WORD16", "Положение крюка замедление вверх *0,1м"),
        RegisterInfo(339, "HMI_Hoist_Pos_Rev_DwnSp_MW", "WORD16", "Положение крюка замедление вниз *0,1м"),
        RegisterInfo(340, "HMI_Hoist_Pos_Zero_MW", "WORD16", "Положение нулевой высоты привода подъема *0,1м"),


        //Параметры настройки датчика поворота
        RegisterInfo(814, "HMI_Write_xSlew_Limit_En_MW", "WORD16", "Включение принудительной проверки механизма поворота в наладочном режиме"),
        RegisterInfo(1648, "HMI_Slew_Limit_Stat_MW", "WORD16", "Статус функции проверки ограничений в наладке"),
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
        RegisterInfo(1699, "HMI_Trolley_Limit_Stat_MW", "WORD16", "Статус функции проверки ограничений в наладке"),
        RegisterInfo(824, "HMI_Write_xTrolley_Fwd_Mem_MW", "WORD16", "Запомнить положение тележки при движении вперед"),
        RegisterInfo(825, "HMI_Write_xTrolley_Rev_Mem_MW", "WORD16", "Запомнить положение тележки при движении назад"),

        RegisterInfo(826, "HMI_Write_rTrolley_Min_Position_MW", "WORD16", "Минимальное положение тележки"),
        RegisterInfo(827, "HMI_Write_rTrolley_Max_Position_MW", "WORD16", "Максимальное положение тележки"),
        RegisterInfo(828, "HMI_Write_rTrolley_Pos_Fwd_DwnSp_MW", "WORD16", "положение при котором снижаем скорость двигая тележку вперед"),
        RegisterInfo(829, "HMI_Write_rTrolley_Pos_Rev_DwnSp_MW", "WORD16", "положение при котором снижаем скорость двигая тележку назад"),

        RegisterInfo(830, "HMI_Write_xTrolley_LSP_Auto_MW", "WORD16", "Включить адаптивное снижение скорости"),
        RegisterInfo(831, "HMI_Write_rTrolley_LSP_Dist3_MW", "WORD16", "дистанция снижение до 3-й скорости"),
        RegisterInfo(832, "HMI_Write_rTrolley_LSP_Dist2_MW", "WORD16", "дистанция снижение до 2-й скорости"),
        RegisterInfo(833, "HMI_Write_rTrolley_LSP_Dist1_MW", "WORD16", "дистанция снижение до 1-й скорости"),

        RegisterInfo(156, "Trolley_Max_Position_MW", "WORD16", "Максимальное положение тележки *0,1м"),
        RegisterInfo(157, "Trolley_Min_Position_MW", "WORD16", "Минимальное положение тележки *0,1м"),
        RegisterInfo(354, "HMI_Trolley_Pos_Fwd_DwnSp_MW", "WORD16", "положение тележки при котором снижаем скорость двигаю тележку вперед *0,1м"),
        RegisterInfo(355, "HMI_Trolley_Pos_Rev_DwnSp_MW", "WORD16", "положение тележки при котором снижаем скорость двигаю тележку назад"),


        //Параметры настройки датчика хода крана
        RegisterInfo(869, "HMI_Write_xCrane_Rails_En_MW", "WORD16", "Активация хода крана. 1 - Ход крана активирован"),
        RegisterInfo(834, "HMI_Write_xCrane_Limit_En_MW", "WORD16", "Включить ограничения перемещения хода крана"),
        RegisterInfo(1738, "HMI_Crane_Limit_Stat_MW", "WORD16", "Статус функции проверки ограничений в наладке"),
        RegisterInfo(835, "HMI_Write_rCrane_Pos_CTRL_Point_MW", "WORD16", "Значение передвижения крана в контрольной точке"),
        RegisterInfo(836, "HMI_Write_xCrane_Fwd_Mem_MW", "WORD16", "Запомнить положение крана при движении вправо"),
        RegisterInfo(837, "HMI_Write_xCrane_Rev_Mem_MW", "WORD16", "Запомнить положение крана при движении влево"),
        RegisterInfo(838, "HMI_Write_rCrane_Pos_Fwd_Stop_MW", "WORD16", "Значение при котором останавливаем движение крана вперед"),
        RegisterInfo(839, "HMI_Write_rCrane_Pos_Rev_Stop_MW", "WORD16", "Значение при котором останавливаем движение крана назад"),
        RegisterInfo(840, "HMI_Write_rCrane_Pos_Fwd_DwnSp_MW", "WORD16", "Значение при котором снижаем скорость при движении крана вперед"),
        RegisterInfo(841, "HMI_Write_rCrane_Pos_Rev_DwnSp_MW", "WORD16", "Значение при котором снижаем скорость при движении крана назад"),

        RegisterInfo(356, "HMI_Crane_Pos_Fwd_Stop_MW", "WORD16", "Путь при котором останавливаем движение крана вперед"),
        RegisterInfo(357, "HMI_Crane_Pos_Rev_Stop_MW", "WORD16", "Путь при котором останавливаем движение крана назад"),
        RegisterInfo(358, "HMI_Crane_Pos_Fwd_DwnSp_MW", "WORD16", "Путь при котором снижаем скорость при движении крана вперед"),
        RegisterInfo(359, "HMI_Crane_Pos_Rev_DwnSp_MW", "WORD16", "Путь при котором снижаем скорость при движении крана назад"),
        RegisterInfo(372, "HMI_Crane_Pos_CTRL_Point_MW", "WORD16", "Реперная точка хода крана"),

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
        RegisterInfo(871, "HMI_Write_xP1_Up_MW", "WORD16", "Запретить работу сверху полигона 1"),
        RegisterInfo(872, "HMI_Write_xP1_Down_MW", "WORD16", "Запретить работу снизу полигона 1"),
        RegisterInfo(873, "HMI_Write_rP1_Height_MW", "WORD16", "высота полигона 1"),
        RegisterInfo(874, "HMI_Write_iP1_Point_Num_MW", "WORD16", "количество точек полигона 1"),

        RegisterInfo(875, "HMI_Write_xP2_En_MW", "WORD16", "Активировать второй полигон"),
        RegisterInfo(876, "HMI_Write_xP2_Up_MW", "WORD16", "Запретить работу сверху полигона 2"),
        RegisterInfo(877, "HMI_Write_xP2_Down_MW", "WORD16", "Запретить работу снизу полигона 2"),
        RegisterInfo(878, "HMI_Write_rP2_Height_MW", "WORD16", "высота полигона 2"),
        RegisterInfo(879, "HMI_Write_iP2_Point_Num_MW", "WORD16", "количество точек полигона 2"),

        RegisterInfo(880, "HMI_Write_xP3_En_MW", "WORD16", "Активировать третий полигон"),
        RegisterInfo(881, "HMI_Write_xP3_Up_MW", "WORD16", "Запретить работу сверху полигона 3"),
        RegisterInfo(882, "HMI_Write_xP3_Down_MW", "WORD16", "Запретить работу снизу полигона 3"),
        RegisterInfo(883, "HMI_Write_rP3_Height_MW", "WORD16", "высота полигона 3"),
        RegisterInfo(884, "HMI_Write_iP3_Point_Num_MW", "WORD16", "количество точек полигона 3"),

        RegisterInfo(885, "HMI_Write_xP4_En_MW", "WORD16", "Активировать четвертый полигон"),
        RegisterInfo(886, "HMI_Write_xP4_Up_MW", "WORD16", "Запретить работу сверху полигона 4"),
        RegisterInfo(887, "HMI_Write_xP4_Down_MW", "WORD16", "Запретить работу снизу полигона 4"),
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

        //Задание точек полигонов с HMI. Полигон №1
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
        //Задание точек полигонов с HMI. Полигон №2
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
        RegisterInfo(1079, "HMI_Points_P2_19_1_Config_MW", "WORD16", "Точка 20Y многоугольника внутри блока конфигурации полигона 2"),

        //Задание точек полигонов с HMI. Полигон №3
        RegisterInfo(1080, "HMI_Points_P3_00_Config_MW", "WORD16", "Точка 1X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1081, "HMI_Points_P3_01_Config_MW", "WORD16", "Точка 1Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1082, "HMI_Points_P3_10_Config_MW", "WORD16", "Точка 2X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1083, "HMI_Points_P3_11_Config_MW", "WORD16", "Точка 2Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1084, "HMI_Points_P3_20_Config_MW", "WORD16", "Точка 3X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1085, "HMI_Points_P3_21_Config_MW", "WORD16", "Точка 3Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1086, "HMI_Points_P3_30_Config_MW", "WORD16", "Точка 4X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1087, "HMI_Points_P3_31_Config_MW", "WORD16", "Точка 4Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1088, "HMI_Points_P3_40_Config_MW", "WORD16", "Точка 5X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1089, "HMI_Points_P3_41_Config_MW", "WORD16", "Точка 5Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1090, "HMI_Points_P3_50_Config_MW", "WORD16", "Точка 6X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1091, "HMI_Points_P3_51_Config_MW", "WORD16", "Точка 6Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1092, "HMI_Points_P3_60_Config_MW", "WORD16", "Точка 7X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1093, "HMI_Points_P3_61_Config_MW", "WORD16", "Точка 7Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1094, "HMI_Points_P3_70_Config_MW", "WORD16", "Точка 8X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1095, "HMI_Points_P3_71_Config_MW", "WORD16", "Точка 8Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1096, "HMI_Points_P3_80_Config_MW", "WORD16", "Точка 9X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1097, "HMI_Points_P3_81_Config_MW", "WORD16", "Точка 9Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1098, "HMI_Points_P3_90_Config_MW", "WORD16", "Точка 10X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1099, "HMI_Points_P3_91_Config_MW", "WORD16", "Точка 10Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1100, "HMI_Points_P3_10_0_Config_MW", "WORD16", "Точка 11X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1101, "HMI_Points_P3_10_1_Config_MW", "WORD16", "Точка 11Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1102, "HMI_Points_P3_11_0_Config_MW", "WORD16", "Точка 12X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1103, "HMI_Points_P3_11_1_Config_MW", "WORD16", "Точка 12Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1104, "HMI_Points_P3_12_0_Config_MW", "WORD16", "Точка 13X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1105, "HMI_Points_P3_12_1_Config_MW", "WORD16", "Точка 13Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1106, "HMI_Points_P3_13_0_Config_MW", "WORD16", "Точка 14X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1107, "HMI_Points_P3_13_1_Config_MW", "WORD16", "Точка 14Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1108, "HMI_Points_P3_14_0_Config_MW", "WORD16", "Точка 15X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1109, "HMI_Points_P3_14_1_Config_MW", "WORD16", "Точка 15Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1110, "HMI_Points_P3_15_0_Config_MW", "WORD16", "Точка 16X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1111, "HMI_Points_P3_15_1_Config_MW", "WORD16", "Точка 16Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1112, "HMI_Points_P3_16_0_Config_MW", "WORD16", "Точка 17X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1113, "HMI_Points_P3_16_1_Config_MW", "WORD16", "Точка 17Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1114, "HMI_Points_P3_17_0_Config_MW", "WORD16", "Точка 18X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1115, "HMI_Points_P3_17_1_Config_MW", "WORD16", "Точка 18Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1116, "HMI_Points_P3_18_0_Config_MW", "WORD16", "Точка 19X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1117, "HMI_Points_P3_18_1_Config_MW", "WORD16", "Точка 19Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1118, "HMI_Points_P3_19_0_Config_MW", "WORD16", "Точка 20X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1119, "HMI_Points_P3_19_1_Config_MW", "WORD16", "Точка 20Y многоугольника внутри блока конфигурации полигона 1"),

        //Задание точек полигонов с HMI. Полигон №4
        RegisterInfo(1120, "HMI_Points_P4_00_Config_MW", "WORD16", "Точка 1X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1121, "HMI_Points_P4_01_Config_MW", "WORD16", "Точка 1Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1122, "HMI_Points_P4_10_Config_MW", "WORD16", "Точка 2X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1123, "HMI_Points_P4_11_Config_MW", "WORD16", "Точка 2Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1124, "HMI_Points_P4_20_Config_MW", "WORD16", "Точка 3X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1125, "HMI_Points_P4_21_Config_MW", "WORD16", "Точка 3Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1126, "HMI_Points_P4_30_Config_MW", "WORD16", "Точка 4X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1127, "HMI_Points_P4_31_Config_MW", "WORD16", "Точка 4Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1128, "HMI_Points_P4_40_Config_MW", "WORD16", "Точка 5X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1129, "HMI_Points_P4_41_Config_MW", "WORD16", "Точка 5Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1130, "HMI_Points_P4_50_Config_MW", "WORD16", "Точка 6X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1131, "HMI_Points_P4_51_Config_MW", "WORD16", "Точка 6Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1132, "HMI_Points_P4_60_Config_MW", "WORD16", "Точка 7X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1133, "HMI_Points_P4_61_Config_MW", "WORD16", "Точка 7Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1134, "HMI_Points_P4_70_Config_MW", "WORD16", "Точка 8X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1135, "HMI_Points_P4_71_Config_MW", "WORD16", "Точка 8Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1136, "HMI_Points_P4_80_Config_MW", "WORD16", "Точка 9X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1137, "HMI_Points_P4_81_Config_MW", "WORD16", "Точка 9Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1138, "HMI_Points_P4_90_Config_MW", "WORD16", "Точка 10X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1139, "HMI_Points_P4_91_Config_MW", "WORD16", "Точка 10Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1140, "HMI_Points_P4_10_0_Config_MW", "WORD16", "Точка 11X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1141, "HMI_Points_P4_10_1_Config_MW", "WORD16", "Точка 11Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1142, "HMI_Points_P4_11_0_Config_MW", "WORD16", "Точка 12X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1143, "HMI_Points_P4_11_1_Config_MW", "WORD16", "Точка 12Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1144, "HMI_Points_P4_12_0_Config_MW", "WORD16", "Точка 13X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1145, "HMI_Points_P4_12_1_Config_MW", "WORD16", "Точка 13Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1146, "HMI_Points_P4_13_0_Config_MW", "WORD16", "Точка 14X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1147, "HMI_Points_P4_13_1_Config_MW", "WORD16", "Точка 14Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1148, "HMI_Points_P4_14_0_Config_MW", "WORD16", "Точка 15X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1149, "HMI_Points_P4_14_1_Config_MW", "WORD16", "Точка 15Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1150, "HMI_Points_P4_15_0_Config_MW", "WORD16", "Точка 16X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1151, "HMI_Points_P4_15_1_Config_MW", "WORD16", "Точка 16Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1152, "HMI_Points_P4_16_0_Config_MW", "WORD16", "Точка 17X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1153, "HMI_Points_P4_16_1_Config_MW", "WORD16", "Точка 17Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1154, "HMI_Points_P4_17_0_Config_MW", "WORD16", "Точка 18X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1155, "HMI_Points_P4_17_1_Config_MW", "WORD16", "Точка 18Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1156, "HMI_Points_P4_18_0_Config_MW", "WORD16", "Точка 19X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1157, "HMI_Points_P4_18_1_Config_MW", "WORD16", "Точка 19Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1158, "HMI_Points_P4_19_0_Config_MW", "WORD16", "Точка 20X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(1159, "HMI_Points_P4_19_1_Config_MW", "WORD16", "Точка 20Y многоугольника внутри блока конфигурации полигона 1"),

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
 * Конфигурация UI №6 (SettingRotationViewModel)
 * Регистры для работы с UI экрана настройка поворота
 */
object UiSettingRotationConfiguration {
    const val NAME_SETTING_ROTATION = "ui_configuration_SettingRotation"

    val REGISTERS = listOf(
        // Параметры настройки блока упраления приводом поворота в ПЛК
        RegisterInfo(1217, "HMI_Write_Slew_SpeadBrakeClose", "WORD16", "Допустимая скорость ручного наложения тормоза об/мин"),
        RegisterInfo(987, "HMI_Write_Slew_wDecRevers_MW", "WORD16", "Время торможения поворота противоходом"),
        RegisterInfo(988, "HMI_Write_Slew_wDelay_Time_MW", "WORD16", "Задержка наложения тормоза"),
        RegisterInfo(989, "HMI_Write_Slew_wBrake_Close_Frq", "WORD16", "Частота удержания"),

        RegisterInfo(1218, "HMI_Write_Slew_DelayTimeBrakeCurr", "WORD16", "Задержка до начала торможения постоянным током"),
        RegisterInfo(1219, "HMI_Write_Slew_TimeBrakeCurrent", "WORD16", "Время торможения постонным током"),
        RegisterInfo(1220, "HMI_Write_Slew_FrqStartBraking", "WORD16", "Частота начала торможения"),
        RegisterInfo(1221, "HMI_Write_Slew_DelayStartBraking", "WORD16", "Задержка времени при достижении частоты начала торможения"),

        RegisterInfo(1658, "HMI_iSlew_SpeedBrakeClose", "WORD16", "Допустимая скорость ручного наложения тормоза об/мин"),
        RegisterInfo(1655, "HMI_Slew_Dec_Revers", "WORD16", "Время торможения противовключением"),
        RegisterInfo(1656, "HMI_Slew_DelayTimeBrake", "WORD16", "Время задержки наложения тормоза"),
        RegisterInfo(1657, "HMI_iSlew_BrakeCloseFrq", "WORD16", "Частота удержания"),

        RegisterInfo(1659, "HMI_Slew_DelayTimeBrakeCurr", "WORD16", "Задержка до начала торможения постоянным током"),
        RegisterInfo(1660, "HMI_Slew_TimeBrakeCurrent", "WORD16", "Время торможения постонным током"),
        RegisterInfo(1661, "HMI_Slew_FrqStartBraking", "WORD16", "Частота начала торможения"),
        RegisterInfo(1662, "HMI_Slew_DelayStartBraking", "WORD16", "Задержка времени при достижении частоты начала торможения"),
    )
    val JSON_CONFIG = Json.encodeToString(RegistersConfiguration.serializer(), RegistersConfiguration(REGISTERS))

    fun createConfiguration(): RegisterConfiguration {
        return RegisterConfiguration(
            name = "ui_configuration_SettingRotation",
            jsonConfiguration = JSON_CONFIG,
            isActive = false
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
        //СЕРВИСНЫЙ РЕЖИМ. КНОПКИ СБРОСА ОШИБОК
        RegisterInfo(890, "HMI_Write_xFltRst_MW", "WORD16", "Сброс ошибки привода"),
        RegisterInfo(954, "HMI_Write_ACSS_Reset_MW", "WORD16", "Сброс ошибки АЦСС"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА ВЕТРА
        RegisterInfo(980, "HMI_Write_rWindSpeedLvl1_MW", "WORD16", "Скорость ветра для порога №1, м/с, включение желтой индикации"),
        RegisterInfo(981, "HMI_Write_rWindSpeedLvl2_MW", "WORD16", "Скорость ветра для порога №2, м/с, включение красной индикации"),
        RegisterInfo(982, "HMI_Write_rWindSpeedMontageLvl_MW", "WORD16", "Скорость ветра для порога №2, м/с, включение красной индикации/Монтажный режим"),
        RegisterInfo(983, "HMI_Write_iAnemTimeSmooth_MW", "WORD16", "Время усреднения показаний анемометра, 5 сек"),
        RegisterInfo(163, "Wind_MW", "WORD16", "Текущие показания ветра *0,1м/с"),
        RegisterInfo(381, "HMI_AnemometerSensor_MW", "WORD16", "Анемометр"),
        RegisterInfo(336, "HMI_Anemometer_Status_MW", "WORD16", "Статус анемометра"),

        RegisterInfo(984, "HMI_Write_Anem_Min_Wind_MW", "WORD16", "Нижний предел измерения анемометра, м/с"),
        RegisterInfo(985, "HMI_Write_Anem_Max_Wind_MW", "WORD16", "Верхний предел измерения анемометра, м/с"),
        RegisterInfo(986, "HMI_Write_Anem_Min_Amper_MW", "WORD16", "Нижний предел показаний анемометра, mA"),
        RegisterInfo(1224, "HMI_Write_Anem_Max_Amper_MW", "WORD16", "Верхний предел показаний анемометра, mA"),
        RegisterInfo(1225, "HMI_Write_xAnemomClbr_MW", "WORD16", "Команда калибровать анемометр"),

        RegisterInfo(175, "HMI_rWindSpeedLvl1_MW", "WORD16", "Величина скорости ветра для порога №1, м/с, включение желтой индикации"),
        RegisterInfo(154, "HMI_rWindSpeedLvl2_MW", "WORD16", "Величина скорости ветра для порогоа №2, м/с, включение красной индикации"),
        RegisterInfo(176, "HMI_rWindSpeedMontageLvl_MW", "WORD16", "Велична скорости ветра для порогоа №2, м/с, включение красной индикации/ Монтажный режим"),
        RegisterInfo(644, "HMI_iAnemTimeSmooth_MW", "WORD16", "Время усреднения показаний анемометра. 5 сек."),

        RegisterInfo(645, "HMI_Read_Anem_Min_Wind_MW", "WORD16", "Нижний предел измерения анемометра, м/с"),
        RegisterInfo(658, "HMI_Read_Anem_Max_Wind_MW", "WORD16", "Верхний предел измерения анемометра, м/с"),
        RegisterInfo(659, "HMI_Read_Anem_Min_Amper_MW", "WORD16", "Нижний предел показаний анемометра, mA"),
        RegisterInfo(660, "HMI_Read_Anem_Max_Amper_MW", "WORD16", "Верхний предел показаний анемометра, mA"),


        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКОВ ПОЛОЖЕНИЯ
        RegisterInfo(990, "HMI_Write_Slew_Number_Teeth_OPU_MW", "WORD16", "Число зубьев ОПУ крана"),
        RegisterInfo(991, "HMI_Write_Slew_Number_Teeth_Gear_Sensor_MW", "WORD16", "Число зубьев шестерни датчика поворота крана"),
        RegisterInfo(992, "HMI_Write_Slew_Single_Turn_Steps_Sensor_MW", "WORD16", "Количество импульсов за оборот датчика"),
        RegisterInfo(993, "HMI_Write_Slew_Ratio_K_MW", "WORD16", "Коэффициент передачи ОПУ и шестерни датчика"),

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

        RegisterInfo(650, "HMI_Slew_Number_Teeth_OPU_MW", "WORD16", "Число зубьев ОПУ крана"),
        RegisterInfo(651, "HMI_Slew_Number_Teeth_Gear_Sensor_MW", "WORD16", "Число зубьев шестерни датчика поворота крана"),
        RegisterInfo(652, "HMI_Slew_Single_Turn_Steps_Sensor_MW", "WORD16", "Количество импульсов за оборот датчика"),
        RegisterInfo(653, "HMI_Slew_Ratio_K_MW", "WORD16", "Коэффициент передачи ОПУ и шестерни датчика"),

        RegisterInfo(654, "HMI_udiHoistAbsEncMaxDeltaEn_MD", "WORD16", "Максимальное рассогласование абс. энкодера подъема"),
        RegisterInfo(655, "HMI_udiSlewAbsEncMaxDeltaEn_MD", "WORD16", "Максимальное рассогласование абс. энкодера поворота"),
        RegisterInfo(656, "HMI_udiTrolleyAbsEncMaxDeltaEn_MD", "WORD16", "Максимальное рассогласование абс. энкодера тележки"),
        RegisterInfo(657, "HMI_udiCraneAbsEncMaxDeltaEn_MD", "WORD16", "Максимальное рассогласование абс. энкодера хода крана"),



        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА УСИЛИЯ
        RegisterInfo(1200, "HMI_Write_iAdvLoadCell_LL_MW", "WORD16", "Минимальное значение измерения веса"),
        RegisterInfo(1201, "HMI_Write_iAdvLoadCell_HH_MW", "WORD16", "Максимальное значение измерения веса"),
        RegisterInfo(1202, "HMI_Write_iArray_Size_WeightSmooth_MW", "WORD16", "Глубина сглаживания измерения веса"),
        RegisterInfo(1203, "HMI_Write_rWeight_SP_Over_Speed_MW", "WORD16", "Уставка массы для разрешения повышенной скорости"),

        RegisterInfo(646, "HMI_iAdvLoadCell_LL_MW", "WORD16", "Минимальное значение измерения"),
        RegisterInfo(647, "HMI_iAdvLoadCell_HH_MW", "WORD16", "Максимальное значение измерения"),
        RegisterInfo(648, "HMI_iArray_Size_WeightSmooth_MW", "WORD16", "Глубина сглаживания измерения веса"),
        RegisterInfo(649, "HMI_rWeight_SP_Over_Speed_MW", "WORD16", "Уставка массы, ниже которой будет разрешена повышенная скорость"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА РЕПЕРНОЙ ТОЧКИ ХОДА КРАНА
        RegisterInfo(978, "HMI_Write_xCTRL_Point_EN_MW", "WORD16", "Включение, отключение датчика реперной точки хода крана"),
        RegisterInfo(979, "HMI_Write_rCTRL_Point_Control_Interval_MW", "WORD16", "Значение интервала контроля реперной точки"),
        RegisterInfo(373, "HMI_Crane_CTRL_Point_EN_MW", "WORD16", "Флаг включения, отключения датчика реперной точки хода крана"),
        RegisterInfo(374, "HMI_CTRL_Point_Control_Interval_MW", "WORD16", "Интервал контроля реперной точки"),

        //СЕРВИСНЫЙ РЕЖИМ. СИСТЕМНАЯ НАСТРОЙКА УРОВЕНЬ ИНЖЕНЕР
        RegisterInfo(1204, "HMI_Write_xDataLogErrStopHoist_MW", "WORD16", "Запрет работы подъема на скоростях выше 2-й? при ошибках записи регистратора параметров"),
        RegisterInfo(1205, "HMI_Write_xPasswordOrNaladka_MW", "WORD16", "Режим авторизации [naladka]. (По умолчанию цифровой пароль)"),
        RegisterInfo(1206, "HMI_Write_xFOURpoligonsEn_MW", "WORD16", "Режим работы 4 полигона, 20 точек. (По умолчанию 2 пол. 10 точек)"),
        RegisterInfo(1207, "HMI_Write_xWithoutRringCurrentCollector_MW", "WORD16", "Исполнение крана без токосъемника. (По умолчанию с токосъемником)"),

        RegisterInfo(1208, "HMI_Write_xZoneACC_DEC_Active_MW", "WORD16", "Активация плавной регулировки разгона/торможения"),
        RegisterInfo(1209, "HMI_Write_ZoneAcc_Hoist_MW", "WORD16", "Зона регулирования ускорения подъема"),
        RegisterInfo(1210, "HMI_Write_ZoneDec_Hoist_MW", "WORD16", "Зона регулирования замедления подъема"),
        RegisterInfo(1211, "HMI_Write_ZoneAcc_Slew_MW", "WORD16", "Зона регулирования ускорения поворота"),
        RegisterInfo(1212, "HMI_Write_ZoneDec_Slew_MW", "WORD16", "Зона регулирования замедления поворота"),
        RegisterInfo(1213, "HMI_Write_ZoneAcc_Trolley_MW", "WORD16", "Зона регулирования ускорения тележки"),
        RegisterInfo(1214, "HMI_Write_ZoneDec_Trolley_MW", "WORD16", "Зона регулирования замедления тележки"),
        RegisterInfo(1215, "HMI_Write_ZoneAcc_Crane_MW", "WORD16", "Зона регулирования ускорения крана"),
        RegisterInfo(1216, "HMI_Write_ZoneDec_Crane_MW", "WORD16", "Зона регулирования замедления крана"),

        RegisterInfo(1226, "HMI_Write_LukCoplekte_MW", "WORD16", "Выбор исполнения крана с люком и без//TRUE-Кран с люком опу, FALSE без люка ОПУ"),

        RegisterInfo(750, "HMI_xDataLogErrStopHoist_MW", "WORD16", "Флаг. Запрет высоких скоростей при ошибке регистратора"),
        RegisterInfo(751, "HMI_xPasswordOrNaladka_MW", "WORD16", "Флаг.Режим авторизации наладки"),
        RegisterInfo(752, "HMI_xFOURpoligonsEn_MW", "WORD16", "Флаг.Режим работы полигонов (4x20 или 2x10 точек)"),
        RegisterInfo(753, "HMI_xWithoutRringCurrentCollector_MW", "WORD16", "Флаг.Выбор исполнения крана без кольцеового токосъемника, режим ограничения поворота"),
        RegisterInfo(754, "HMI_xZoneACC_DEC_Activ_MW", "WORD16", "Флаг. Активация зон регулирования ускорений/замедлений"),
        RegisterInfo(758, "HMI_LukCoplekte_MW", "WORD16", "Флаг. Включения контроля люка в ОПУ. TRUE-Кран с люком опу, FALSE без люка ОПУ"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ПРИВОДА ПОДЪЕМА
        RegisterInfo(940, "HMI_Write_Hoist_wSpdRefButton_Value_MW", "WORD16", "Скорость при нажатии кнопки"),
        RegisterInfo(941, "HMI_Write_Hoist_wSpdRef1_Value_MW", "WORD16", "Скорость 1 подъема"),
        RegisterInfo(942, "HMI_Write_Hoist_wSpdRef2_Value_MW", "WORD16", "Скорость 2 подъема"),
        RegisterInfo(943, "HMI_Write_Hoist_wSpdRef3_Value_MW", "WORD16", "Скорость 3 подъема"),
        RegisterInfo(944, "HMI_Write_Hoist_wSpdRef4_Value_MW", "WORD16", "Скорость 4 подъема"),
        RegisterInfo(945, "HMI_Write_Hoist_iSpdRef_MAX_Value_MW", "WORD16", "Значение максимальной скорости двигателя подъема"),
        RegisterInfo(946, "HMI_Write_Hoist_rHoist_Acc_MW", "WORD16", "Время разгона подъема, сек"),
        RegisterInfo(947, "HMI_Write_Hoist_rHoist_Dec_MW", "WORD16", "Время торможения подъема, сек"),
        RegisterInfo(948, "HMI_Write_Hoist_wSpdNom_MW", "WORD16", "Номинальня скорость подъема"),

        RegisterInfo(950, "HMI_Write_Hoist_wSpdRef1Strela_MW", "WORD16", "Скорость подъема 1 в режиме монтаж"),
        RegisterInfo(951, "HMI_Write_Hoist_wSpdRef2Strela_MW", "WORD16", "Скорость подъема 2 в режиме монтаж"),
        RegisterInfo(952, "HMI_Write_Hoist_rStrela_Acc_MW", "WORD16", "Время разгона в режиме монтаж, сек"),
        RegisterInfo(953, "HMI_Write_Hoist_rStrela_Dec_MW", "WORD16", "Время торможения в режиме монтаж, сек"),

        RegisterInfo(1222, "HMI_Write_Hoist_BrakeControl_Stat", "WORD16", "Вкл. Выкл. отслеживаения состояния тормоза 1- отслеживаем обраную связь, 0 -нет"),
        RegisterInfo(1223, "HMI_Write_Hoist_BrakeControl_Timeout", "WORD16", "Значение времени ожидания обратной связи от тормоза подъема, сек"),

        RegisterInfo(1610, "HMI_wSpdRefButton_Hoist", "WORD16", "Пониженая скорость подъема"),
        RegisterInfo(1611, "HMI_wSpdRef1_Hoist", "WORD16", "1-ая скорость подъема об/мин"),
        RegisterInfo(1612, "HMI_wSpdRef2_Hoist", "WORD16", "2-ая скорость подъема об/мин"),
        RegisterInfo(1613, "HMI_wSpdRef3_Hoist", "WORD16", "3-я скорость подъема об/мин"),
        RegisterInfo(1614, "HMI_wSpdRef4_Hoist", "WORD16", "4-ая скорость подъема об/мин"),
        RegisterInfo(1615, "HMI_iSpdRef_MAX_Value", "WORD16", "Сверхсинхронная скорость об/мин"),
        RegisterInfo(1616, "HMI_wSpdNom_Hoist", "WORD16", "Номинальная скорость ЭД подъема об/мин"),
        RegisterInfo(1617, "HMI_rHoist_Acc", "WORD16", "Время разгона, сек"),
        RegisterInfo(1618, "HMI_rHoist_Dec", "WORD16", "Время торможения, сек"),

        RegisterInfo(1619, "HMI_wSpdRef1Strela_Hoist", "WORD16", "1-ая скорость с монтажного пульта"),
        RegisterInfo(1620, "HMI_wSpdRef2Strela_Hoist", "WORD16", "2-ая скорость с монтажного пульта"),
        RegisterInfo(1621, "HMI_rStrela_Acc_Hoist", "WORD16", "Ускорение с монтажного пульта, сек"),
        RegisterInfo(1622, "HMI_rStrela_Dec_Hoist", "WORD16", "Замедление с монтажного пульта, сек"),

        RegisterInfo(1623, "HMI_Hoist_Limit_Stat_MW", "WORD16", "Статус функции проверки ограничений в наладке"),
        RegisterInfo(1624, "HMI_LspAuto_Stat_Hoist_MW", "WORD16", "Статус автоматического снижения скорости"),
        RegisterInfo(1625, "HMI_LspDist3_Hoist_MW", "WORD16", "Дистанция снижения до 3 скорости, м"),
        RegisterInfo(1626, "HMI_LspDist2_Hoist_MW", "WORD16", "Дистанция снижения до 2 скорости, м"),
        RegisterInfo(1627, "HMI_LspDist1_Hoist_MW", "WORD16", "Дистанция снижения до 1 скорости, м"),
        RegisterInfo(1628, "HMI_ZoneAcc_Hoist_MW", "WORD16", "Зона регулирования ускорения подъема"),
        RegisterInfo(1629, "HMI_ZoneDec_Hoist_MW", "WORD16", "Зона регулирования замедления подъема"),

        RegisterInfo(1630, "HMI_Hoist_BrakeControl_Stat", "WORD16", "Бит отслеживаения состояния тормоза 1- отслеживаем обраную связь, 0 -нет"),
        RegisterInfo(1631, "HMI_ZoneDec_Hoist_MW", "WORD16", "Время ожидания обратной связи от тормоза"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ПРИВОДА ПОВОРОТА
        RegisterInfo(955, "HMI_Write_Slew_wDrvSpdLSP_MW", "WORD16", "Снижение скорости поворота"),
        RegisterInfo(956, "HMI_Write_Slew_wSpdRef1_Value_MW", "WORD16", "Скорость 1 поворота"),
        RegisterInfo(957, "HMI_Write_Slew_wSpdRef2_Value_MW", "WORD16", "Скорость 2 поворота"),
        RegisterInfo(958, "HMI_Write_Slew_wSpdRef3_Value_MW", "WORD16", "Скорость 3 поворота"),
        RegisterInfo(959, "HMI_Write_Slew_wSpdRef4_Value_MW", "WORD16", "Скорость 4 поворота"),
        RegisterInfo(968, "HMI_Write_Slew_wACCnom_MW", "WORD16", "Время разгона поворота"),
        RegisterInfo(969, "HMI_Write_Slew_wDECnom_MW", "WORD16", "Время торможения поворота"),
        RegisterInfo(970, "HMI_Write_Slew_wSpdNom_MW", "WORD16", "Номинальня скорость поворота"),


        RegisterInfo(1640, "HMI_wSpdRefLou_Slew", "WORD16", "Пониженая скорость поворота"),
        RegisterInfo(1641, "HMI_wSpdRef1_Slew", "WORD16", "1-ая скорость поворота об/мин"),
        RegisterInfo(1642, "HMI_wSpdRef2_Slew", "WORD16", "2-ая скорость поворота об/мин"),
        RegisterInfo(1643, "HMI_wSpdRef3_Slew", "WORD16", "3-я скорость поворота об/мин"),
        RegisterInfo(1644, "HMI_wSpdRef4_Slew", "WORD16", "4-ая скорость поворота об/мин"),
        RegisterInfo(1645, "HMI_wSpdNom_Slew", "WORD16", "Номинальная скорость ЭД поворота об/мин"),
        RegisterInfo(1646, "HMI_iSlew_Acc", "WORD16", "Время разгона, сек"),
        RegisterInfo(1647, "HMI_iSlew_Dec", "WORD16", "Время торможения, сек"),

        RegisterInfo(1648, "HMI_Slew_Limit_Stat_MW", "WORD16", "Стутус функции проверки ограничений в наладке"),
        RegisterInfo(1649, "HMI_LspAuto_Stat_Slew_MW", "WORD16", "Статус автоматического снижения скорости"),
        RegisterInfo(1650, "HMI_LspDist3_Slew_MW", "WORD16", "Дистанция снижения до 3 скорости, м"),
        RegisterInfo(1651, "HMI_LspDist2_Slew_MW", "WORD16", "Дистанция снижения до 2 скорости, м"),
        RegisterInfo(1652, "HMI_LspDist1_Slew_MW", "WORD16", "Дистанция снижения до 1 скорости, м"),
        RegisterInfo(1653, "HMI_ZoneAcc_Slew_MW", "WORD16", "Зона регулирования ускорения поворота"),
        RegisterInfo(1654, "HMI_ZoneDec_Slew_MW", "WORD16", "Зона регулирования замедления поворота"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ПРИВОДА ТЕЛЕЖКИ
        RegisterInfo(960, "HMI_Write_Trolley_wSpdRef1_Value_MW", "WORD16", "Скорость 1 тележки"),
        RegisterInfo(961, "HMI_Write_Trolley_wSpdRef2_Value_MW", "WORD16", "Скорость 2 тележки"),
        RegisterInfo(962, "HMI_Write_Trolley_wSpdRef3_Value_MW", "WORD16", "Скорость 3 тележки"),
        RegisterInfo(963, "HMI_Write_Trolley_wSpdRef4_Value_MW", "WORD16", "Скорость 4 тележки"),
        RegisterInfo(964, "HMI_Write_rTrolley_Acc_MW", "WORD16", "Время разгона тележки, сек"),
        RegisterInfo(965, "HMI_Write_rTrolley_Dec_MW", "WORD16", "Время торможения тележки, сек"),
        RegisterInfo(966, "HMI_Write_wTrolleyLsp_MW", "WORD16", "Пониженная скорость тележки"),
        RegisterInfo(967, "HMI_Write_rTrolleyOverspd_MW", "WORD16", "Значение веса груза при превышении которого запрещена сверхсинхронная работа тележки"),
        RegisterInfo(971, "HMI_Write_Trolley_wSpdNom_MW", "WORD16", "Номинальная скорость тележки"),

        RegisterInfo(1690, "HMI_wSpdRefLou_Trolley", "WORD16", "Пониженая скорость тележки"),
        RegisterInfo(1691, "HMI_wSpdRef1_Trolley", "WORD16", "1-ая скорость тележки об/мин"),
        RegisterInfo(1692, "HMI_wSpdRef2_Trolley", "WORD16", "2-ая скорость тележки об/мин"),
        RegisterInfo(1693, "HMI_wSpdRef3_Trolley", "WORD16", "3-я скорость тележки об/мин"),
        RegisterInfo(1694, "HMI_wSpdRef4_Trolley", "WORD16", "4-ая скорость тележки об/мин"),
        RegisterInfo(1695, "HMI_wSpdNom_Trolley", "WORD16", "Номинальная скорость ЭД тележки об/мин"),
        RegisterInfo(1696, "HMI_rTrolleyOverspd", "WORD16", "Масса груза выше которой запрешена повышенная скорость тележки"),
        RegisterInfo(1697, "HMI_rTrolley_Acc", "WORD16", "Время разгона, сек"),
        RegisterInfo(1698, "HMI_rTrolley_Dec", "WORD16", "Время торможения, сек"),

        RegisterInfo(1699, "HMI_Trolley_Limit_Stat_MW", "WORD16", "Стутус функции проверки ограничений в наладке"),
        RegisterInfo(1700, "HMI_LspAuto_Stat_Trolley_MW", "WORD16", "Статус автоматического снижения скорости"),
        RegisterInfo(1701, "HMI_LspDist3_Trolley_MW", "WORD16", "Дистанция снижения до 3 скорости, м"),
        RegisterInfo(1702, "HMI_LspDist2_Trolley_MW", "WORD16", "Дистанция снижения до 2 скорости, м"),
        RegisterInfo(1703, "HMI_LspDist1_Trolley_MW", "WORD16", "Дистанция снижения до 1 скорости, м"),
        RegisterInfo(1704, "HMI_ZoneAcc_Trolley_MW", "WORD16", "Зона регулирования ускорения тележки"),
        RegisterInfo(1705, "HMI_ZoneDec_Trolley_MW", "WORD16", "Зона регулирования замедления тележки"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ПРИВОДА ХОДА КРАНА
        RegisterInfo(972, "HMI_Write_Crane_wSpdRef1_Value_MW", "WORD16", "Скорость 1 хода крана"),
        RegisterInfo(973, "HMI_Write_Crane_wSpdRef2_Value_MW", "WORD16", "Скорость 2 хода крана"),
        RegisterInfo(974, "HMI_Write_Crane_wSpdRef3_Value_MW", "WORD16", "Скорость 3 хода крана"),
        RegisterInfo(975, "HMI_Write_Crane_wSpdRef4_Value_MW", "WORD16", "Скорость 4 хода крана"),
        RegisterInfo(976, "HMI_Write_rCrane_Acc_MW", "WORD16", "Время разгона хода крана, сек"),
        RegisterInfo(977, "HMI_Write_rCrane_Dec_MW", "WORD16", "Время торможения хода крана, сек"),

        RegisterInfo(1730, "HMI_wSpdRefLou_Crane", "WORD16", "Пониженая скорость хода крана"),
        RegisterInfo(1731, "HMI_wSpdRef1_Crane", "WORD16", "1-ая скорость хода крана об/мин"),
        RegisterInfo(1732, "HMI_wSpdRef2_Crane", "WORD16", "2-ая скорость хода крана об/мин"),
        RegisterInfo(1733, "HMI_wSpdRef3_Crane", "WORD16", "3-я скорость хода крана об/мин"),
        RegisterInfo(1734, "HMI_wSpdRef4_Crane", "WORD16", "4-ая скорость хода крана об/мин"),
        RegisterInfo(1735, "HMI_wSpdNom_Crane", "WORD16", "Номинальная скорость ЭД хода крана об/мин"),
        RegisterInfo(1736, "HMI_rCrane_Acc", "WORD16", "Время разгона, сек"),
        RegisterInfo(1737, "HMI_rCrane_Dec", "WORD16", "Время торможения, сек"),

        RegisterInfo(1738, "HMI_Crane_Limit_Stat_MW", "WORD16", "Стутус функции проверки ограничений в наладке"),
        RegisterInfo(1739, "HMI_LspAuto_Stat_Crane_MW", "WORD16", "Статус автоматического снижения скорости"),
        RegisterInfo(1740, "HMI_LspDist3_Crane_MW", "WORD16", "Дистанция снижения до 3 скорости, м"),
        RegisterInfo(1741, "HMI_LspDist2_Crane_MW", "WORD16", "Дистанция снижения до 2 скорости, м"),
        RegisterInfo(1742, "HMI_LspDist1_Crane_MW", "WORD16", "Дистанция снижения до 1 скорости, м"),
        RegisterInfo(1743, "HMI_ZoneAcc_Crane_MW", "WORD16", "Зона регулирования ускорения крана"),
        RegisterInfo(1744, "HMI_ZoneDec_Crane_MW", "WORD16", "Зона регулирования замедления крана"),

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


/**
 * Конфигурация UI №8 (DiagnosticMenuViewModel)
 * Регистры для работы с UI экрана начальные настройки
 */
object UIDiagnosticRegisterConfiguration {
    const val NAME_DIAGNOSTIC = "UI_configuration_Diagnostic"

    val REGISTERS = listOf(
        //ДИАГНОСТИКА.  СИСТЕМА УПРАВЛЕНИЯ.
        //Датчики ветра и усилия
        RegisterInfo(188, "OGPLimSw_MW", "WORD16", "Датчик критического момента. 1-Норма (грузоподъемность не превышена). 0- превышение грузоподъемности"),
        RegisterInfo(198, "HMI_Curr_AI_MW", "WORD16", "Текущее значение с датчика груза в единицах входа модуля"),
        RegisterInfo(711,  "HMI_Weight_Sensor_Err_MW",  "WORD16",  "Cбой датчика грузоподъемности"),
        RegisterInfo(381, "HMI_AnemometerSensor_MW", "WORD16", "Анемометр"),
        RegisterInfo(336, "HMI_Anemometer_Status_MW", "WORD16", "Статус анемометра"),

        //Параметры датчиков.
        RegisterInfo(382,  "udi_AbsEncFenac_Hoist_Position_MD",  "WORD16",  "данные датчика высоты подъема"),
        RegisterInfo(383,  "udi_AbsEncFenac_Hoist_Position_MD",  "WORD16",  "данные датчика высоты подъема"),
        RegisterInfo(386,  "udi_AbsEncFenac_Slew_Position_MD",  "WORD16",  "данные датчика азимута"),
        RegisterInfo(387,  "udi_AbsEncFenac_Slew_Position_MD",  "WORD16",  "данные датчика азимута"),
        RegisterInfo(390,  "udi_AbsEncFenac_Trolley_Position_MD",  "WORD16",  "данные датчика тележки"),
        RegisterInfo(391,  "udi_AbsEncFenac_Trolley_Position_MD",  "WORD16",  "данные датчика тележки"),
        RegisterInfo(394,  "udi_AbsEncFenac_Crane_Position_MD",  "WORD16",  "данные датчика хода крана"),
        RegisterInfo(395,  "udi_AbsEncFenac_Crane_Position_MD",  "WORD16",  "данные датчика хода крана"),

        //-----ошибка связи энкодеров
        RegisterInfo(204, "Hoist_AbsEnc_Err_MW", "WORD16", "Сбой датчика подъема"),
        RegisterInfo(205, "Slew_AbsEnc_Err_MW", "WORD16", "Сбой датчика поворота"),
        RegisterInfo(206, "Trolley_AbsEnc_Err_MW", "WORD16", "Сбой датчика вылета"),
        RegisterInfo(207, "Crane_AbsEnc_Err_MW", "WORD16", "Сбой датчика перемещения крана"),
        RegisterInfo(728, "HMI_CANopenSensors_Error_MW", "WORD16", "Авария сети CANopen (датчики)"),
        //-----Статус приводов
        RegisterInfo(100, "Hoist_StatusWord_MW", "WORD16", "Слово состояния ПЧ подъема"),
        RegisterInfo(109, "Slew_StatusWord_MW", "WORD16", "Слово состояния ПЧ поворота"),
        RegisterInfo(118, "Trolley_StatusWord_MW", "WORD16", "Слово состояния ПЧ тележки"),
        RegisterInfo(127, "Crane_StatusWord_MW", "WORD16", "Слово состояния ПЧ хода крана"),


        //ДИАГНОСТИКА.  ПРИВОД ПОДЪЕМА
        RegisterInfo(724, "HMI_HoistDrivePower_Error_MW", "WORD16", "Отключение питания ПЧ подъема"),
        RegisterInfo(101, "Hoist_ControlWord_MW", "WORD16", "Слово управления ПЧ подъема"),
        RegisterInfo(720, "HMI_HoistDrive_Error_MW", "WORD16", "Статус. Ошибка частотного преобразователя подъема"),
        RegisterInfo(108, "Hoist_Fault_MW", "WORD16", "Код ошибки ПЧ"),
        RegisterInfo(738, "HMI_HoistDrive_ErrorCode_MW", "WORD16", "Код ошибки частотного преобразователя подъема"),

        RegisterInfo(102, "Hoist_TargetVelocity_MW", "WORD16", "Заданная скорость вращения подъема"),
        RegisterInfo(103, "Hoist_ControlEffort_MW", "WORD16", "Текущая скорость вращения подъема"),
        RegisterInfo(104, "Hoist_MotorCurrent_MW", "WORD16", "Ток двигателя подъема"),
        RegisterInfo(177, "Hoist_Encoder_MW", "WORD16", "Количество импульсов энкодера"),
        RegisterInfo(106, "Hoist_IL1R_MW", "WORD16", "Состояние дискретных входов ПЧ"),
        RegisterInfo(107, "Hoist_OL1R_MW", "WORD16", "Состояние дискретных выходов ПЧ"),


        //ДИАГНОСТИКА.  ПРИВОД ПОВОРОТА
        RegisterInfo(725, "HMI_SlewDrivePower_Error_MW", "WORD16", "Отключение питания ПЧ поворота"),
        RegisterInfo(110, "Slew_ControlWord_MW", "WORD16", "Слово управления ПЧ поворота"),
        RegisterInfo(721, "HMI_SlewDrive_Error_MW", "WORD16", "Статус. Ошибка частотного преобразователя поворота"),
        RegisterInfo(117, "Slew_Fault_MW", "WORD16", "Код ошибки ПЧ"),
        RegisterInfo(739, "HMI_SlewDrive_ErrorCode_MW", "WORD16", "Код ошибки частотного преобразователя поворота"),

        RegisterInfo(111, "Slew_TargetVelocity_MW", "WORD16", "Заданная скорость вращения"),
        RegisterInfo(112, "Slew_ControlEffort_MW", "WORD16", "Текущая скорость вращения"),
        RegisterInfo(113, "Slew_MotorCurrent_MW", "WORD16", "Ток двигателя поворота"),
        RegisterInfo(178, "Slew_Encoder_MW", "WORD16", "Количество импульсов энкодера"),
        RegisterInfo(115, "Slew_IL1R_MW", "WORD16", "Состояние дискретных входов ПЧ"),
        RegisterInfo(116, "Slew_OL1R_MW", "WORD16", "Состояние дискретных выходов ПЧ"),


        //ДИАГНОСТИКА.  ПРИВОД ТЕЛЕЖКИ
        RegisterInfo(726, "HMI_TrolleyDrivePower_Error_MW", "WORD16", "Отключение питания ПЧ тележки"),
        RegisterInfo(119, "Trolley_ControlWord_MW", "WORD16", "Слово управления ПЧ тележки"),
        RegisterInfo(722, "HMI_TrolleyDrive_Error_MW", "WORD16", "Статус. Ошибка частотного преобразователя тележки"),
        RegisterInfo(126, "Trolley_Fault_MW", "WORD16", "Код ошибки ПЧ"),
        RegisterInfo(740, "HMI_TrolleyDrive_ErrorCode_MW", "WORD16", "Код ошибки частотного преобразователя тележки"),

        RegisterInfo(120, "Trolley_TargetVelocity_MW", "WORD16", "Заданная скорость вращения"),
        RegisterInfo(121, "Trolley_ControlEffort_MW", "WORD16", "Текущая скорость вращения"),
        RegisterInfo(122, "Trolley_MotorCurrent_MW", "WORD16", "Ток двигателя тележки"),
        RegisterInfo(179, "Trolley_Encoder_MW", "WORD16", "Количество импульсов энкодера"),
        RegisterInfo(124, "Trolley_IL1R_MW", "WORD16", "Состояние дискретных входов ПЧ"),
        RegisterInfo(125, "Trolley_OL1R_MW", "WORD16", "Состояние дискретных выходов ПЧ"),

        //ДИАГНОСТИКА.  ПРИВОД ХОДА КРАНА
        RegisterInfo(727, "HMI_TravelDrivePower_Error_MW", "WORD16", "Отключение питания ПЧ передвижения"),
        RegisterInfo(128, "Crane_ControlWord_MW", "WORD16", "Слово управления ПЧ хода крана"),
        RegisterInfo(723, "HMI_TravelDrive_Error_MW", "WORD16", "Статус. Ошибка частотного преобразователя передвижения"),
        RegisterInfo(134, "Crane_Fault_MW", "WORD16", "Код ошибки ПЧ"),
        RegisterInfo(741, "HMI_TravelDrive_ErrorCode_MW", "WORD16", "Код ошибки частотного преобразователя передвижения"),

        RegisterInfo(129, "Crane_TargetVelocity_MW", "WORD16", "Заданная скорость вращения"),
        RegisterInfo(130, "Crane_ControlEffort_MW", "WORD16", "Текущая скорость вращения"),
        RegisterInfo(131, "Crane_MotorCurrent_MW", "WORD16", "Ток двигателя хода крана"),
        RegisterInfo(132, "Crane_IL1R_MW", "WORD16", "Состояние дискретных входов ПЧ"),
        RegisterInfo(133, "Crane_OL1R_MW", "WORD16", "Состояние дискретных выходов ПЧ"),
        RegisterInfo(353, "HMI_xCraneRepPoint_MW", "WORD16", "Реперная точка хода крана"),

        RegisterInfo(729, "HMI_CANopenDrives_Error_MW", "WORD16", "Авария сети CANopen (частотные преобразователи)"),

        //ДИАГНОСТИКА.  ПЛК.
        // Дискретные входы ПЛК
        RegisterInfo(221, "PLC_DI0_0_0_PhaseControlRelay_MW", "WORD16", "Реле контроля фаз"),
        RegisterInfo(225, "PLC_DI0_0_1_LineContactor_MW", "WORD16", "Состояние линейного контактора"),
        RegisterInfo(226, "PLC_DI0_0_2_MontageMode_MW", "WORD16", "Монтажный режим"),
        RegisterInfo(227, "PLC_DI0_0_3_OGPLimSw_MW", "WORD16", "Датчик критического момента"),
        RegisterInfo(228, "PLC_DI0_0_4_HoistBrakeStatus_MW", "WORD16", "Состояние тормоза подъема"),
        RegisterInfo(229, "PLC_DI0_0_5_HoistBrakeSensor_MW", "WORD16", "Контроль износа тормоза подъема"),
        RegisterInfo(230, "PLC_DI0_0_6_HoistLowSpdButton_MW", "WORD16", "Замедление скорости подъема"),
        RegisterInfo(231, "PLC_DI0_0_7_SlewBrake_MW", "WORD16", "Ручное наложение тормоза поворота"),

        // Дискретные выходы ПЛК
        RegisterInfo(2, "PLC_DQ0_0_LineContactorEn_MW", "WORD16", "Разрешение включения линейного контактора"),
        RegisterInfo(3, "PLC_DQ0_1_HoistPWREn_MW", "WORD16", "Разрешение работы подъема"),
        RegisterInfo(4, "PLC_DQ0_2_SlewPWREn_MW", "WORD16", "Разрешение работы поворота"),
        RegisterInfo(5, "PLC_DQ0_3_TrolleyPWREn_MW", "WORD16", "Разрешение работы тележки"),
        RegisterInfo(6, "PLC_DQ0_4_CranePWREn_MW", "WORD16", "Разрешение работы хода крана"),
        RegisterInfo(7, "PLC_DQ0_5_Hoist_Ventilation_MW", "WORD16", "Вентиляция подъема"),
        RegisterInfo(8, "PLC_DQ0_6_GlobalReady_MW", "WORD16", "Ошибка. Глобальная готовность"),
        RegisterInfo(9, "PLC_DQ0_7_HoistOverloadStop_MW", "WORD16", "Авария/перегрузка"),

        // Дискретные входы Модуля
        RegisterInfo(232, "PLC_DI1_0_0_MicroclimatInput_MW", "WORD16", "Контроль питания обогрева"),
        RegisterInfo(233, "PLC_DI1_0_1_gv_xBrakePowerControl_MW", "WORD16", "Контроль состояния питания тормозов"),
        RegisterInfo(234, "PLC_DI1_0_2_gv_xVentilationPowerControl_MW", "WORD16", "Контроль состояния питания независимой вентиляции"),
        RegisterInfo(239, "PLC_DI1_0_7_ACSS_PWR_MW", "WORD16", "АЦСС питание в норме"),

        RegisterInfo(288, "PLC_DI1_1_0_ACSS_Fault_MW", "WORD16", "АЦСС в ошибке"),
        RegisterInfo(289, "PLC_DI1_1_1_ACSS_Ready_MW", "WORD16", "АЦСС ошибок нет"),
        RegisterInfo(290, "PLC_DI1_1_2_Zapasovka_MW", "WORD16", "Выбор кратности запасовки, =1 -4х кратная, =0 -2х кратная"),
        RegisterInfo(291, "PLC_DI1_1_3_Luk_MW", "WORD16", "Люк ОПУ"),
        RegisterInfo(292, "PLC_DI1_1_4_Hoist_Zero_Pos_MW", "WORD16", "0-положение джойстика подъема"),
        RegisterInfo(293, "PLC_DI1_1_5_Slew_Zero_Pos_MW", "WORD16", "0-положение джойстика поворота"),
        RegisterInfo(294, "PLC_DI1_1_6_Trolley_Zero_Pos_MW", "WORD16", "0-положение джойстика тележки"),
        RegisterInfo(295, "PLC_DI1_1_7_Crane_Zero_Pos_MW", "WORD16", "0-положение джойстика хода крана"),

        // Дискретные выходы Модуля
        RegisterInfo(10, "PLC_DQ1_0_0_Sound_MW", "WORD16", "Звуковая сигнализация"),
        RegisterInfo(11, "PLC_DQ1_1_7_ACSS_Reset_MW", "WORD16", "Сброс ошибки АЦСС"),

        // Аналоговые входы Модуля
        RegisterInfo(380, "HMI_LoadCellSensor_MW", "WORD16", "Датчик нагрузки"),
        RegisterInfo(381, "HMI_AnemometerSensor_MW", "WORD16", "Анемометр")
    )

    val JSON_CONFIG = Json.encodeToString(RegistersConfiguration.serializer(), RegistersConfiguration(REGISTERS))

    fun createConfiguration(): RegisterConfiguration {
        return RegisterConfiguration(
            name = "UI_configuration_Diagnostic",
            jsonConfiguration = JSON_CONFIG,
            isActive = false
        )
    }
}


/**
 * Конфигурация UI №9 (ParameterLoggerScreen)
 * Регистры для работы с UI экрана начальные настройки
 */
object UIParameterLoggerConfiguration {
    const val NAME_PARAMETER_LOGGER = "UI_Parameter_Logger"

    val REGISTERS = listOf(
        //РЕГИСТРАТОР ПАРАМЕТРОВ
        //Параметры крана.
        RegisterInfo(152, "Qnom_MW", "WORD16", "Номинальная грузоподъемность в кг"),
        RegisterInfo(197, "Crane_Lenght_MW", "WORD16", "Длина стрелы крана"),
        RegisterInfo(171, "R_AP_MW", "WORD16", "Паспортная группа режима работы"),
        RegisterInfo(172, "AF_Iso_MW", "WORD16", "Фактическая группа режима работы по ИСО"),
        RegisterInfo(173, "R_Q_MW", "WORD16", "Фактический класс нагружения"),
        RegisterInfo(174, "Kp_MW", "WORD16", "Коэффициент нагружения Кр"),
        RegisterInfo(283, "C_MW", "WORD16", "Класс использования С"),
        RegisterInfo(0, "R_N_MW_1", "WORD16", "текущее характеристическое число"),
        RegisterInfo(1, "R_N_MW_2", "WORD16", "текущее характеристическое число"),

        RegisterInfo(165, "SettingModeYear_MW", "WORD16", "Дата и время включения последнего режима наладки"),
        RegisterInfo(166, "SettingModeMonth_MW", "WORD16", "Дата и время включения последнего режима наладки"),
        RegisterInfo(167, "SettingModeDay_MW", "WORD16", "Дата и время включения последнего режима наладки"),
        RegisterInfo(168, "SettingModeHour_MW", "WORD16", "Дата и время включения последнего режима наладки"),
        RegisterInfo(169, "SettingModeMinute_MW", "WORD16", "Дата и время включения последнего режима наладки"),
        RegisterInfo(170, "SettingModeSeconds_MW", "WORD16", "Дата и время включения последнего режима наладки"),

        RegisterInfo(368, "HMI_sR_SBKB_No_MW", "WORD16", "Заводской номер СБКБ"),
        RegisterInfo(367, "HMI_sR_Crane_No_MW", "WORD16", "Заводской номер крана"),

        RegisterInfo(369, "HMI_SBKB_InstallationDay_MW", "WORD16", "День установки системы безопасности"),
        RegisterInfo(370, "HMI_SBKB_InstallationMonth_MW", "WORD16", "Месяц установки системы безопасности"),
        RegisterInfo(371, "HMI_SBKB_InstallationYear_MW", "WORD16", "Год установки системы безопасности"),
        //Моточасы
        RegisterInfo(360, "HMI_HoistOperatingHours_MW", "WORD16", "Моточасы механизма подъема"),
        RegisterInfo(361, "HMI_SlewOperatingHours_MW", "WORD16", "Моточасы механизма поворота"),
        RegisterInfo(362, "HMI_TrolleyOperatingHours_MW", "WORD16", "Моточасы механизма тележки"),
        RegisterInfo(363, "HMI_TravelOperatingHours_MW", "WORD16", "Моточасы механизма передвижения крана"),
        RegisterInfo(364, "HMI_TotalCraneOperatingHours_MW", "WORD16", "Общие моточасы крана"),
        RegisterInfo(365, "HMI_SBKB_OperatingHours_MW", "WORD16", "Моточасы системы безопасности"),
        RegisterInfo(366, "HMI_TotalCraneOperatingHours_Maintenance_MW", "WORD16", "Моточасы крана для технического обслуживания"),

        //ЦИКЛЫ РАБОТЫ
        RegisterInfo(20, "arr_rest_a1_MW_1", "WORD32", "0-10% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(21, "arr_rest_a1_MW_2", "WORD32", "0-10% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(24, "arr_rest_a2_MW_1", "WORD32", "11-20% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(25, "arr_rest_a2_MW_2", "WORD32", "11-20% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(28, "arr_rest_a3_MW_1", "WORD32", "21-30% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(29, "arr_rest_a3_MW_2", "WORD32", "21-30% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(32, "arr_rest_a4_MW_1", "WORD32", "31-40% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33, "arr_rest_a4_MW_2", "WORD32", "31-40% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(36, "arr_rest_a5_MW_1", "WORD32", "41-50% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(37, "arr_rest_a5_MW_2", "WORD32", "41-50% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(40, "arr_rest_a6_MW_1", "WORD32", "51-60% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(41, "arr_rest_a6_MW_2", "WORD32", "51-60% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(44, "arr_rest_a7_MW_1", "WORD32", "61-70% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(45, "arr_rest_a7_MW_2", "WORD32", "61-70% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(48, "arr_rest_a8_MW_1", "WORD32", "71-80% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(49, "arr_rest_a8_MW_2", "WORD32", "71-80% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(52, "arr_rest_a9_MW_1", "WORD32", "81-90% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(53, "arr_rest_a9_MW_2", "WORD32", "81-90% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(56, "arr_rest_a10_MW_1", "WORD32", "91-100% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(57, "arr_rest_a10_MW_2", "WORD32", "91-100% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(60, "arr_rest_a11_MW_1", "WORD32", ">101% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(61, "arr_rest_a11_MW_2", "WORD32", ">101% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(80, "R_CycleHoist_MW_1", "WORD32", "Рабочие циклы подъема"),
        RegisterInfo(81, "R_CycleHoist_MW_2", "WORD32", "Рабочие циклы подъема"),
        RegisterInfo(84, "R_CycleSlew_MW_1", "WORD32", "Рабочие циклы поворота"),
        RegisterInfo(85, "R_CycleSlew_MW_1", "WORD32", "Рабочие циклы поворота"),
        RegisterInfo(88, "R_CycleTrolley_MW_1", "WORD32", "Рабочие циклы тележки"),
        RegisterInfo(89, "R_CycleTrolley_MW_1", "WORD32", "Рабочие циклы тележки"),
        RegisterInfo(92, "R_CycleCrane_MW_1", "WORD32", "Рабочие циклы хода крана"),
        RegisterInfo(93, "R_CycleCrane_MW_2", "WORD32", "Рабочие циклы хода крана"),
        RegisterInfo(96, "R_CycleHoleCrane_MW_1", "WORD32", "Рабочие циклы крана в целом"),
        RegisterInfo(97, "R_CycleHoleCrane_MW_2", "WORD32", "Рабочие циклы крана в целом"),

        //СОХРАНЕНИЕ
        //Отправка данных на панель о состоянии записи и чтения VAR-файлов на ПЛК
        RegisterInfo(1800, "HMI_Read_SDcard_Stat_MW", "WORD32", "Статус SD-карты"),
        RegisterInfo(1801, "HMI_Read_VarSave_Stat_MW", "WORD32", "Статус сохранения VAR-файла 0-Ожидание, 1- Завершено, 2- В процессе, 4-Ошибка."),
        RegisterInfo(1802, "HMI_Read_VarSave_Proc", "WORD32", "Процес выполнения сохранения 0-100%"),
        RegisterInfo(1803, "HMI_Read_VarRead_Stat_MW", "WORD32", "Статус чтения VAR-файла 0-Ожидание, 1- Завершено, 2- В процессе, 4-Ошибка."),
        RegisterInfo(1804, "HMI_Read_VarRead_Proc", "WORD32", "Процес выполнения чтения 0-100%"),
        RegisterInfo(1805, "HMI_Read_VarSize_MW", "WORD32", "Объем VAR-файла"),

        //Запись и чтение VAR-файлов на ПЛК, данных регистратора параметров и т.д
        RegisterInfo(1250, "HMI_Write_Button_Save_Var_MW", "WORD32", "Записать VAR-файл"),
        RegisterInfo(1251, "HMI_Write_Button_Read_Var_MW", "WORD32", "Считать VAR-файл"),
        RegisterInfo(1252, "HMI_Write_Button_Read_Size_MW", "WORD32", "Считать объем VAR-файла"),
        RegisterInfo(1253, "HMI_Write_Button_Save_Abort_MW", "WORD32", "Отмена записи VAR-файл"),
        RegisterInfo(1254, "HMI_Write_Button_Read_Abort_MW", "WORD32", "Отмена считать VAR-файл"),
        RegisterInfo(1255, "HMI_Write_Button_Save_RP_MW", "WORD32", "Записать файл РП-на SD-карту"),

        )

    val JSON_CONFIG = Json.encodeToString(RegistersConfiguration.serializer(), RegistersConfiguration(REGISTERS))

    fun createConfiguration(): RegisterConfiguration {
        return RegisterConfiguration(
            name = "UI_configuration_ParameterLogger",
            jsonConfiguration = JSON_CONFIG,
            isActive = false
        )
    }
}

/**
 * Конфигурация UI №10 (TechnicalMaintenanceScreen)
 * Регистры для работы с UI экрана начальные настройки
 */
object UITechnicalMaintenanceConfiguration {
    const val NAME_TECHNICAL_MAINTENANCE = "UI_Technical_Maintenance"

    val REGISTERS = listOf(
        //ТЕХНИЧЕСКОЕ ОБСЛУЖИВАНИЕ
        RegisterInfo(368, "HMI_sR_Crane_No_MW", "WORD16", "Заводской номер крана"),
        RegisterInfo(299, "TOCounter_MW", "WORD16", "Счетчик моточасов м.ч."),
        RegisterInfo(305, "Technical_Reglament_TO1_MW", "WORD16", "Проведите ТО"),
        RegisterInfo(306, "Alarm_TO1_MW", "WORD16", "Скорости ограничены, проведите ТО"),
        RegisterInfo(307, "MotorHourDelta_MW", "WORD16", "Сколько часов осталось до ТО"),
        RegisterInfo(308, "Technical_Reglament_TO1_Delta_MW", "WORD16", "До ТО осталось AT%MW307 часов"),
        RegisterInfo(309, "Technical_Reglament_xWarrantyEnd_MW", "WORD16", "Грузовая лебедка отработала 750 м.ч."),

        RegisterInfo(366, "HMI_TotalCraneOperatingHours_Maintenance_MW", "WORD16", "Моточасы крана для технического обслуживания"),
        RegisterInfo(867, "HMI_WritesTOReset_MW", "WORD16", "Команда сброса счетчика ТО"),
    )

    val JSON_CONFIG = Json.encodeToString(RegistersConfiguration.serializer(), RegistersConfiguration(REGISTERS))

    fun createConfiguration(): RegisterConfiguration {
        return RegisterConfiguration(
            name = "UI_Technical_Maintenance",
            jsonConfiguration = JSON_CONFIG,
            isActive = false
        )
    }
}


/**
 * Конфигурация UI №12 (LoginScreen)
 * Регистры для работы с UI экрана начальные настройки
 */
object UIAccidentAndWaringConfiguration {
    const val NAME_ACCIDENT_AND_WARING = "UI_accident_and_waring_screen"

    val REGISTERS = listOf(
        RegisterInfo(186, "Overload_105Percent_MW", "WORD16", "Бит превышения грузоподъемности более 105%"),
        RegisterInfo(187, "Overload_110Percent_MW", "WORD16", "Бит превышения грузоподъемности более 110%"),
        RegisterInfo(188, "OGPLimSw_MW", "WORD16", "Датчик критического момента. 1-Норма (грузоподъемность не превышена). 0- превышение грузоподъемности"),
        RegisterInfo(189, "HoistBrakeSensor_MW", "WORD16", "Износ тормоза привода главного подъема. 0-Норма (тормоз исправен). 1- тормоз изношен"),

        RegisterInfo(244, "Alarm_GRUZ_FUN_MW", "WORD16", "Неверная ГХ"),
        RegisterInfo(245, "Alarm_Zapasovka_MW", "WORD16", "Ошибка выбора запасовки"),

        RegisterInfo(190, "Build_Mode_MW", "WORD16", "Флаг режима наладки"),
        RegisterInfo(191, "Service_Mode_MW", "WORD16", "Флаг режима сервис"),
        RegisterInfo(192, "MontageMode_MW", "WORD16", "Флаг монтажного режима"),

        RegisterInfo(300, "Crane_Config_Warning_MW", "WORD16", "Ход крана не настроен"),
        RegisterInfo(301, "Hoist_Config_Warning_MW", "WORD16", "Подъем не настроен"),
        RegisterInfo(302, "Slew_Config_Warning_MW", "WORD16", "Поворот крана не настроен"),
        RegisterInfo(303, "Trolley_Config_Warning_MW", "WORD16", "Вылет не настроен"),
        RegisterInfo(304, "Weight_Config_Warning_MW", "WORD16", "Грузоподъемность не настроена"),
        RegisterInfo(305, "Technical_Reglament_TO1_MW", "WORD16", "Проведите ТО"),
        RegisterInfo(306, "Alrm_TO1_MW", "WORD16", "Скорости ограничены, проведите ТО"),
        RegisterInfo(307, "MotorHourDelta_MW", "WORD16", "Сколько часов осталось до ТО"),
        RegisterInfo(308, "Technical_Reglament_TO1_Delta_MW", "WORD16", "До ТО осталось AT%MW307 часов"),
        RegisterInfo(309, "Technical_Reglament_xWarrantyEnd_MW", "WORD16", "Грузовая лебедка отработала 750 м.ч."),

        RegisterInfo(700, "HMI_HatchLimitSwitch_Status_MW", "WORD16", "Состояние конечного выключателя люка ОПУ"),
        RegisterInfo(701, "HMI_LineContactorZeroPos_Alarm_MW", "WORD16", "Авария нулевой блокировки линейного контактора"),
        RegisterInfo(702, "HMI_MicroclimateHeater_Fault_MW", "WORD16", "Авария обогрева технического помещения"),
        RegisterInfo(703, "HMI_BrakePowerSupply_Fault_MW", "WORD16", "Авария питания тормозов"),
        RegisterInfo(704, "HMI_VentilationPowerSupply_Fault_MW", "WORD16", "Авария питания вентиляции электродвигателей"),

        RegisterInfo(705, "HMI_HoistJoystick_Error_MW", "WORD16", "Ошибка джойстика механизма подъема"),
        RegisterInfo(706, "HMI_SlewJoystick_Error_MW", "WORD16", "Ошибка джойстика механизма поворота"),
        RegisterInfo(707, "HMI_TrolleyJoystick_Error_MW", "WORD16", "Ошибка джойстика механизма тележки"),
        RegisterInfo(708, "HMI_TravelJoystick_Error_MW", "WORD16", "Ошибка джойстика механизма передвижения"),
        RegisterInfo(709, "HMI_LoadMomentSensor_Alarm_MW", "WORD16", "Авария датчика критического грузового момента"),

        RegisterInfo(710, "HMI_CraneReferencePointSensor_Fault_MW", "WORD16", "Сбой датчика реперной точки хода крана"),
        RegisterInfo(711, "HMI_LoadCellSensor_Fault_MW", "WORD16", "Сбой датчика грузоподъемности"),
        RegisterInfo(712, "HMI_AnemometerSensor_Fault_MW", "WORD16", "Сбой анемометра"),

        RegisterInfo(713, "HMI_AbsCraneEncoder_Fault_MW", "WORD16", "Сбой энкодера механизма передвижения"),
        RegisterInfo(714, "HMI_AbsHoistEncoder_Fault_MW", "WORD16", "Сбой энкодера механизма подъема"),
        RegisterInfo(715, "HMI_AbsSlewEncoder_Fault_MW", "WORD16", "Сбой энкодера механизма поворота"),
        RegisterInfo(716, "HMI_AbsTrolleyEncoder_Fault_MW", "WORD16", "Сбой энкодера механизма тележки"),

        RegisterInfo(717, "HMI_PhaseControlRelay_Fault_MW", "WORD16", "Авария реле контроля фаз"),
        RegisterInfo(718, "HMI_PowerSupplyNetwork_Fault_MW", "WORD16", "Авария питающей сети"),

        RegisterInfo(720, "HMI_HoistDrive_Error_MW", "WORD16", "Ошибка частотного преобразователя подъема"),
        RegisterInfo(721, "HMI_SlewDrive_Error_MW", "WORD16", "Ошибка частотного преобразователя поворота"),
        RegisterInfo(722, "HMI_TrolleyDrive_Error_MW", "WORD16", "Ошибка частотного преобразователя тележки"),
        RegisterInfo(723, "HMI_TravelDrive_Error_MW", "WORD16", "Ошибка частотного преобразователя передвижения"),

        RegisterInfo(724, "HMI_HoistDrivePower_Error_MW", "WORD16", "Отключение питания ПЧ подъема"),
        RegisterInfo(725, "HMI_SlewDrivePower_Error_MW", "WORD16", "Отключение питания ПЧ поворота"),
        RegisterInfo(726, "HMI_TrolleyDrivePower_Error_MW", "WORD16", "Отключение питания ПЧ тележки"),
        RegisterInfo(727, "HMI_TravelDrivePower_Error_MW", "WORD16", "Отключение питания ПЧ передвижения"),

        RegisterInfo(728, "HMI_CANopenSensors_Error_MW", "WORD16", "Авария сети CANopen (датчики)"),
        RegisterInfo(729, "HMI_CANopenDrives_Error_MW", "WORD16", "Авария сети CANopen (частотные преобразователи)"),

        RegisterInfo(730, "HMI_HoistEncoder_MaxDeltaError_MW", "WORD16", "Превышение максимальной дельты энкодера подъема"),
        RegisterInfo(731, "HMI_SlewEncoder_MaxDeltaError_MW", "WORD16", "Превышение максимальной дельты энкодера поворота"),
        RegisterInfo(732, "HMI_TrolleyEncoder_MaxDeltaError_MW", "WORD16", "Превышение максимальной дельты энкодера тележки"),
        RegisterInfo(733, "HMI_TravelEncoder_MaxDeltaError_MW", "WORD16", "Превышение максимальной дельты энкодера передвижения"),

        RegisterInfo(734, "HMI_HoistEncoder_MaxValueError_MW", "WORD16", "Превышение максимального значения энкодера подъема"),
        RegisterInfo(735, "HMI_SlewEncoder_MaxValueError_MW", "WORD16", "Превышение максимального значения энкодера поворота"),
        RegisterInfo(736, "HMI_TrolleyEncoder_MaxValueError_MW", "WORD16", "Превышение максимального значения энкодера тележки"),
        RegisterInfo(737, "HMI_TravelEncoder_MaxValueError_MW", "WORD16", "Превышение максимального значения энкодера передвижения"),

        RegisterInfo(738, "HMI_HoistDrive_ErrorCode_MW", "WORD16", "Код ошибки частотного преобразователя подъема"),
        RegisterInfo(739, "HMI_SlewDrive_ErrorCode_MW", "WORD16", "Код ошибки частотного преобразователя поворота"),
        RegisterInfo(740, "HMI_TrolleyDrive_ErrorCode_MW", "WORD16", "Код ошибки частотного преобразователя тележки"),
        RegisterInfo(741, "HMI_TravelDrive_ErrorCode_MW", "WORD16", "Код ошибки частотного преобразователя передвижения"),

        RegisterInfo(742, "HMI_HoistEncoder_Fault_MW", "WORD16", "Ошибка энкодера привода подъема"),
        RegisterInfo(743, "HMI_SlewEncoder_Fault_MW", "WORD16", "Ошибка энкодера привода поворота"),
        RegisterInfo(744, "HMI_TrolleyEncoder_Fault_MW", "WORD16", "Ошибка энкодера привода тележки")
    )

    val JSON_CONFIG = Json.encodeToString(RegistersConfiguration.serializer(), RegistersConfiguration(REGISTERS))

    fun createConfiguration(): RegisterConfiguration {
        return RegisterConfiguration(
            name = "UI_accident_and_waring_screen",
            jsonConfiguration = JSON_CONFIG,
            isActive = false
        )
    }
}



/**
 * Конфигурация UI №12 (LoginScreen)
 * Регистры для работы с UI экрана начальные настройки
 */
object UILoginConfiguration {
    const val NAME_LOGIN = "UI_Login"

    val REGISTERS = listOf(
        RegisterInfo(190, "Build_Mode_MW", "WORD16", "Флаг режима наладки"),
        RegisterInfo(191, "Service_Mode_MW", "WORD16", "Флаг режима сервис"),
        RegisterInfo(192, "MontageMode_MW", "WORD16", "Флаг монтажного режима"),

        RegisterInfo(798, "HMI_Write_sPassword_MW_1", "WORD16", "Пароль панели оператора для входа в наладку и сервис. Часть 1."),
        RegisterInfo(799, "HMI_Write_sPassword_MW_2", "WORD16", "Пароль панели оператора для входа в наладку и сервис. Часть 2."),
        RegisterInfo(800, "HMI_Write_sPassword_MW_3", "WORD16", "Пароль панели оператора для входа в наладку и сервис. Часть 3."),

        RegisterInfo(801, "HMI_Write_LoginButton_MW", "WORD16", "Кнопка LogIn"),
        RegisterInfo(802, "HMI_Write_ExitLevel_MW", "WORD16", "Выход из наладки и сервиса"),
        RegisterInfo(751, "HMI_xPasswordOrNaladka_MW", "WORD16", "Флаг.Режим авторизации наладки"),
    )

    val JSON_CONFIG = Json.encodeToString(RegistersConfiguration.serializer(), RegistersConfiguration(REGISTERS))

    fun createConfiguration(): RegisterConfiguration {
        return RegisterConfiguration(
            name = "UI_Login",
            jsonConfiguration = JSON_CONFIG,
            isActive = false
        )
    }
}