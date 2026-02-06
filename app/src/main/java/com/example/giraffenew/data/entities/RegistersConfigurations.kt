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
 * Главная конфигурация регистров (адреса 30001-30006)
 * Эти регистры считываются всегда, один раз в секунду
 */
object TopBarRegisterConfiguration {
    const val NAME_TOP_BAR = "TopBar_configuration"
    val REGISTERS = listOf(
        RegisterInfo(0, "test", "WORD16", "Год"),

        RegisterInfo(30001, "RE_gv_wYear_MW", "WORD16", "Год"),
        RegisterInfo(30002, "RE_gv_wMonth_MW", "WORD16", "Месяц"),
        RegisterInfo(30003, "RE_gv_wDay_MW", "WORD16", "День"),
        RegisterInfo(30004, "RE_gv_wHour_MW", "WORD16", "Часы"),
        RegisterInfo(30005, "RE_gv_wMinute_MW", "WORD16", "Минуты"),
        RegisterInfo(30006, "RE_gv_wSeconds_MW", "WORD16", "Секунды"),
        RegisterInfo(33501, "RE_CraneType_MW", "WORD16", "Заводской тип крана"),
        RegisterInfo(33001, "RE_Build_Mode_MW", "WORD16", "Флаг режима наладки"),
        RegisterInfo(33002, "RE_Service_Mode_MW", "WORD16", "Флаг режима сервис"),
        RegisterInfo(33003, "RE_MontageMode_MW", "WORD16", "Флаг режима монтаж"),
        RegisterInfo(33506, "RE_Curr_Gruz_Zapas_MW", "WORD16", "выбор кратности запасовки, =TRUE -4х кратная, =FALSE -2х кратная"),
        RegisterInfo(33507, "RE_Curr_Gruz_Fun_MW", "WORD16", "Текущая выбранная грузовая характеристика"),
        RegisterInfo(33500, "RE_sR_Crane_No_MW", "WORD16", "Заводской номер крана"),
        RegisterInfo(30210, "RE_Beeper_Weight_MW", "WORD16", "Пищалка HMI.Превышение ГП больше 90%"),
        RegisterInfo(30211, "RE_Beeper_Warning_MW", "WORD16", "Пищалка HMI. Предупреждение"),
        RegisterInfo(30212, "RE_Beeper_Alarm_Fault_MW", "WORD16", "Пищалка HMI. Авария "),
        RegisterInfo(30213, "RE_Beeper_OGP_MW", "WORD16", "Пищалка HMI. ОГП "),
        RegisterInfo(30214, "RE_Beeper_STOP_MW", "WORD16", "Пищалка HMI.Стой "),

        RegisterInfo(63530, "WR_Hmi_Active_MW", "WORD16", "Флаг режима активности HMI"),
        RegisterInfo(30007, "WR_TimeZone_MW", "WORD16", "(* Часовой пояс в UTC *)"),
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
        RegisterInfo(33503, "RE_Qnom_MW", "WORD16", "Номинальная грузоподъемность в кг"),
        RegisterInfo(33504, "RE_Weight_MW", "WORD16", "Текущая грузоподъемность в тоннах"),
        RegisterInfo(33130, "RE_Trolley_Position_MW", "WORD16", "Текущее положение тележки на стреле *0,1м"),
        RegisterInfo(33120, "RE_Rotation_Position_MW", "WORD16", "Текущий угол поворота"),
        RegisterInfo(33110, "RE_Hoist_Position_MW", "WORD16", "Текущее положение подъема *0,1м"),
        RegisterInfo(33111, "RE_Hoist_Pos_Fwd_Stop_MW", "WORD16", "Максимальное положение подъема *0,1м"),
        RegisterInfo(33112, "RE_Hoist_Pos_Rev_Stop_MW", "WORD16", "Минимальное положение подъема *0,1м"),
        RegisterInfo(33140, "RE_Crane_Position_MW",  "WORD16",  "Текущее положение крана *0,1м"),
        RegisterInfo(33010, "RE_Wind_MW", "WORD16", "Текущие показания ветра *0,1м/с"),
        RegisterInfo(30011, "RE_VoltageL1L2_MW", "WORD16", "Линейное напряжение L1-L2"),
        RegisterInfo(30012, "RE_VoltageL2L3_MW", "WORD16", "Линейное напряжение L2-L3"),
        RegisterInfo(30013, "RE_VoltageL3L1_MW", "WORD16", "Линейное напряжение L3-L1"),
        RegisterInfo(33209, "RE_Anemometer_Status_MW", "WORD16", "статус датчика ветра (0-Rd, 1-Yl, 2-Gr) "),

        //addressesCraneParameterScale
        RegisterInfo(33203, "RE_Weight_Percent_MW", "WORD16", "Текущий вес в процентах"),
        RegisterInfo(33141, "RE_Crane_Pos_Fwd_Stop_MW", "WORD16", "Максимальное положение крана"),
        RegisterInfo(33012, "RE_WindSpeedLvl2_MW", "WORD16", "Предельный ветер нерабочего состояния в м/с"),
        RegisterInfo(33011, "RE_WindSpeedLvl1_MW", "WORD16", "Предупредительный ветер рабочего состояния в м/с"),
        RegisterInfo(33013, "RE_WindSpeedMontageLvl_MW", "WORD16", "Предупредительный ветер в режиме монтаж в м/с"),

        RegisterInfo(33003, "RE_MontageMode_MW", "WORD16", "Флаг монтажного режима"),
        RegisterInfo(39010, "RE_HMI_Crane_CTRLPoint_Er_MWr", "WORD16", "Сбой датчика реперной точки хода крана"),
        RegisterInfo(39009, "RE_HMI_Alarm_OGP_SENSOR_MW", "WORD16", "Авария датчика критического грузового момента"),
        RegisterInfo(30105, "RE_PLC_DI0_0_5_HoistBrakeSensor_MW", "WORD16", "Контроль износа тормоза подъема"),
        RegisterInfo(31420, "RE_Crane_Rails_En_MW", "WORD16", "Флаг включения перемещения крана. 1 - Ход крана активирован"),
        RegisterInfo(33165, "RE_xCraneReperPoint_MW", "WORD16", "Реперная точка хода крана"),

        RegisterInfo(33551, "RE_Technical_Reglament_TO1_MW", "WORD16", "Проведите ТО"),
        RegisterInfo(33552, "Alarm_TO1_MW", "WORD16", "Скорости ограничены, проведите ТО"),

        //addressesCraneMechanismsCondition
        RegisterInfo(39011, "RE_HMI_Weight_Sensor_Err_MW",  "WORD16",  "Cбой датчика грузоподъемности"),
        RegisterInfo(33215, "RE_Trolley_Fwd_Status_MW", "WORD16", "Статус движения тележки вперед на HMI"),
        RegisterInfo(33216, "RE_Trolley_Rev_Status_MW", "WORD16", "Статус движения тележки назад на HMI"),
        RegisterInfo(33211, "RE_Hoist_Up_Status_MW", "WORD16", "Статус подъема груза на HMI"),
        RegisterInfo(33212, "RE_Hoist_Down_Status_MW", "WORD16", "Статус опускания груза на HMI"),
        RegisterInfo(33213, "RE_Slew_Right_Status_MW", "WORD16", "Статус поворота вправо на HMI"),
        RegisterInfo(33214, "RE_Slew_Left_Status_MW", "WORD16", "Статус поворота влево на HMI"),
        RegisterInfo(33217, "RE_Crane_Right_Status_MW", "WORD16", "Статус движения крана вправо на HMI"),
        RegisterInfo(33218, "RE_Crane_Left_Status_MW", "WORD16", "Статус движения крана влево на HMI"),
        RegisterInfo(33227, "RE_EM_Vihod_1_Crane_Right_MW", "WORD16", "Статус выхода из полигона движением крана вправо"),
        RegisterInfo(33228, "RE_EM_Vihod_2_Crane_Left_MW", "WORD16", "Статус выхода из полигона движением крана влево"),
        RegisterInfo(33223, "RE_EM_Vihod_3_Slew_Left_MW", "WORD16", "Статус выхода из полигона поворотом налево"),
        RegisterInfo(33224, "RE_EM_Vihod_4_Slew_Right_MW", "WORD16", "Статус выхода из полигона поворотом направо"),
        RegisterInfo(33225, "RE_EM_Vihod_5_Trolley_Fwd_MW", "WORD16", "Статус выхода из полигона движением тележки вперед"),
        RegisterInfo(33226, "RE_EM_Vihod_6_Trolley_Rev_MW", "WORD16", "Статус выхода из полигона движением тележки назад"),
        RegisterInfo(33221, "RE_EM_Vihod_7_Hoist_Up_MW", "WORD16", "Статус выхода из полигона подъемом груза"),
        RegisterInfo(33222, "RE_EM_Vihod_8_Hoist_Down_MW", "WORD16", "Статус выхода из полигона опусканием груза"),
        RegisterInfo(39012, "RE_HMI_Anemom_Sensor_Err_MW", "WORD16", "Статус анемометра. Ошибка датчика"),
        RegisterInfo(33210, "RE_AlarmLamp_Status_MW", "WORD16", "Cбой анемоментра"),

        RegisterInfo(31103, "RE_Hoist_ControlEffort_MW", "WORD16", "Текущая скорость вращения подъема *0,01Гц"),
        RegisterInfo(31203, "RE_Slew_ControlEffort_MW", "WORD16", "Текущая скорость вращения *0,01Гц"),
        RegisterInfo(31303, "RE_Trolley_ControlEffort_MW", "WORD16", "Текущая скорость тележки *0,01Гц"),
        RegisterInfo(31403, "RE_Crane_ControlEffort_MW", "WORD16", "Текущая скорость хода кран *0,01Гц"),

        //Information
        RegisterInfo(33001, "RE_Build_Mode_MW", "WORD16", "Флаг режима наладки"),
        RegisterInfo(33002, "RE_Service_Mode_MW", "WORD16", "Флаг режима сервис"),
        RegisterInfo(30117, "RE_PLC_DI1_0_7_ACSS_PWR_MW", "WORD16", "АЦСС питание в норме"),
        RegisterInfo(30120, "RE_PLC_DI1_1_0_ACSS_Fault_MW", "WORD16", "АЦСС в ошибке"),
        RegisterInfo(39000, "RE_HMI_Luk_Err_MW", "WORD16", "Состояние конечного выключателя люка ОПУ"),
        RegisterInfo(39001, "RE_HMI_LineContZeroPosAlrm_MW", "WORD16", "Авария нулевой блокировки линейного контактора"),
        RegisterInfo(39002, "RE_HMI_Microclimat_Err_MW", "WORD16", "Авария обогрева технического помещения"),
        RegisterInfo(39003, "RE_HMI_BrakePowerControl_Err_MW", "WORD16", "Авария питания тормозов"),
        RegisterInfo(39004, "RE_HMI_VentilationPowerControl_Err_MW", "WORD16", "Авария питания вентиляции электродвигателей"),
        RegisterInfo(39005, "RE_HMI_Hoist_Joy_Alarm_MW", "WORD16", "Ошибка джойстика механизма подъема"),
        RegisterInfo(39006, "RE_HMI_Slew_Joy_Alarm_MW", "WORD16", "Ошибка джойстика механизма поворота"),
        RegisterInfo(39007, "RE_HMI_Trolley_Joy_Alarm_MW", "WORD16", "Ошибка джойстика механизма тележки"),
        RegisterInfo(39008, "RE_HMI_Crane_Joy_Alarm_MW", "WORD16", "Ошибка джойстика механизма передвижения"),
        RegisterInfo(39013, "RE_HMI_Crane_AbsEnc_Err_MW", "WORD16", "Сбой энкодера механизма передвижения"),
        RegisterInfo(39014, "RE_HMI_Hoist_AbsEnc_Err_MW", "WORD16", "Сбой энкодера механизма подъема"),
        RegisterInfo(39015, "RE_HMI_Slew_AbsEnc_Err_MW", "WORD16", "Сбой энкодера механизма поворота"),
        RegisterInfo(39016, "RE_HMI_Trolley_AbsEnc_Err_MW", "WORD16", "Сбой энкодера механизма тележки"),
        RegisterInfo(39017, "RE_HMI_PhaseControl_Relay_Err_MW", "WORD16", "Авария реле контроля фаз"),
        RegisterInfo(39018, "RE_HMI_PowerLine_Fault_Err_MW", "WORD16", "Авария питающей сети"),
        RegisterInfo(39020, "RE_HMI_Hoist_Drive_Err_MW", "WORD16", "Ошибка частотного преобразователя подъема"),
        RegisterInfo(39021, "RE_HMI_Slew_Drive_Err_MW", "WORD16", "Ошибка частотного преобразователя поворота"),
        RegisterInfo(39022, "RE_HMI_Trolley_Drive_Err_MW", "WORD16", "Ошибка частотного преобразователя тележки"),
        RegisterInfo(39023, "RE_HMI_Crane_Drive_Err_MW", "WORD16", "Ошибка частотного преобразователя передвижения"),
        RegisterInfo(39024, "RE_HMI_HoistATV_PWR_Err_MW", "WORD16", "Отключение питания ПЧ подъема"),
        RegisterInfo(39025, "RE_HMI_SlewATV_PWR_Err_MW", "WORD16", "Отключение питания ПЧ поворота"),
        RegisterInfo(39026, "RE_HMI_TrolleyATV_PWR_Err_MW", "WORD16", "Отключение питания ПЧ тележки"),
        RegisterInfo(39027, "RE_HMI_CraneATV_PWR_Err_MW", "WORD16", "Отключение питания ПЧ передвижения"),
        RegisterInfo(39028, "RE_HMI_TrolleyATV_PWR_Err_MW", "WORD16", "Авария сети CANopen (датчики)"),
        RegisterInfo(39029, "RE_HMI_GlobalDrives_ComReady_Err_MW", "WORD16", "Авария сети CANopen (частотные преобразователи)"),

        //Crane SOZR. Стрела крана
        RegisterInfo(33230, "RE_Line_Angle_MW", "WORD16", "Угол поворота стрелы на HMI"),
        RegisterInfo(33231, "RE_Trolley_Y_MW", "WORD16", "Координаты точки тележки для HMI по оси Y"),
        RegisterInfo(33232, "RE_Trolley_X_MW", "WORD16", "Координаты точки тележки для HMI по оси X"),
        RegisterInfo(33233, "RE_Circle_Size_MW", "WORD16", "Радиус круга отражающего траекторию тележки на HMI"),
        RegisterInfo(33234, "RE_Strela_Lenght_MW", "WORD16", "Длина отрезка стрелы для панели (имеет значение при наклоне стрелы)"),
        RegisterInfo(33901, "HMI_P1_Color_MW", "WORD16", "Анимация цвета полигона ( 0-Синий (задан но не активен), 1-Зеленый (задан и активен), 2-желтый (приближение к границе), 3-красный (касание границы полигона))"),
        RegisterInfo(33902, "HMI_P2_Color_MW", "WORD16", "Анимация цвета полигона ( 0-Синий (задан но не активен), 1-Зеленый (задан и активен), 2-желтый (приближение к границе), 3-красный (касание границы полигона))"),
        RegisterInfo(33903, "HMI_P3_Color_MW", "WORD16", "Анимация цвета полигона ( 0-Синий (задан но не активен), 1-Зеленый (задан и активен), 2-желтый (приближение к границе), 3-красный (касание границы полигона))"),
        RegisterInfo(33904, "HMI_P4_Color_MW", "WORD16", "Анимация цвета полигона ( 0-Синий (задан но не активен), 1-Зеленый (задан и активен), 2-желтый (приближение к границе), 3-красный (касание границы полигона))"),

        //Crane SOZR
        // Параметры отезков для отображения полигона №1
        RegisterInfo(34100, "RE_Point_Arr1_0_Width_MW", "WORD16", "Длина сегмента №00"),
        RegisterInfo(34101, "RE_Point_Arr1_1_Width_MW", "WORD16", "Длина сегмента №01"),
        RegisterInfo(34102, "RE_Point_Arr1_2_Width_MW", "WORD16", "Длина сегмента №02"),
        RegisterInfo(34103, "RE_Point_Arr1_3_Width_MW", "WORD16", "Длина сегмента №03"),
        RegisterInfo(34104, "RE_Point_Arr1_4_Width_MW", "WORD16", "Длина сегмента №04"),
        RegisterInfo(34105, "RE_Point_Arr1_5_Width_MW", "WORD16", "Длина сегмента №05"),
        RegisterInfo(34106, "RE_Point_Arr1_6_Width_MW", "WORD16", "Длина сегмента №06"),
        RegisterInfo(34107, "RE_Point_Arr1_7_Width_MW", "WORD16", "Длина сегмента №07"),
        RegisterInfo(34108, "RE_Point_Arr1_8_Width_MW", "WORD16", "Длина сегмента №08"),
        RegisterInfo(34109, "RE_Point_Arr1_9_Width_MW", "WORD16", "Длина сегмента №09"),
        RegisterInfo(34110, "RE_Point_Arr1_10_Width_MW", "WORD16", "Длина сегмента №10"),
        RegisterInfo(34111, "RE_Point_Arr1_11_Width_MW", "WORD16", "Длина сегмента №11"),
        RegisterInfo(34112, "RE_Point_Arr1_12_Width_MW", "WORD16", "Длина сегмента №12"),
        RegisterInfo(34113, "RE_Point_Arr1_13_Width_MW", "WORD16", "Длина сегмента №13"),
        RegisterInfo(34114, "RE_Point_Arr1_14_Width_MW", "WORD16", "Длина сегмента №14"),
        RegisterInfo(34115, "RE_Point_Arr1_15_Width_MW", "WORD16", "Длина сегмента №15"),
        RegisterInfo(34116, "RE_Point_Arr1_16_Width_MW", "WORD16", "Длина сегмента №16"),
        RegisterInfo(34117, "RE_Point_Arr1_17_Width_MW", "WORD16", "Длина сегмента №17"),
        RegisterInfo(34118, "RE_Point_Arr1_18_Width_MW", "WORD16", "Длина сегмента №18"),
        RegisterInfo(34119, "RE_Point_Arr1_19_Width_MW", "WORD16", "Длина сегмента №19"),
        // Параметры отезков для отображения полигона №1
        RegisterInfo(34120, "RE_Point_Arr1_0_TopP_MW", "WORD16", "Координата Y точки №00"),
        RegisterInfo(34121, "RE_Point_Arr1_1_TopP_MW", "WORD16", "Координата Y точки №01"),
        RegisterInfo(34122, "RE_Point_Arr1_2_TopP_MW", "WORD16", "Координата Y точки №02"),
        RegisterInfo(34123, "RE_Point_Arr1_3_TopP_MW", "WORD16", "Координата Y точки №03"),
        RegisterInfo(34124, "RE_Point_Arr1_4_TopP_MW", "WORD16", "Координата Y точки №04"),
        RegisterInfo(34125, "RE_Point_Arr1_5_TopP_MW", "WORD16", "Координата Y точки №05"),
        RegisterInfo(34126, "RE_Point_Arr1_6_TopP_MW", "WORD16", "Координата Y точки №06"),
        RegisterInfo(34127, "RE_Point_Arr1_7_TopP_MW", "WORD16", "Координата Y точки №07"),
        RegisterInfo(34128, "RE_Point_Arr1_8_TopP_MW", "WORD16", "Координата Y точки №08"),
        RegisterInfo(34129, "RE_Point_Arr1_9_TopP_MW", "WORD16", "Координата Y точки №09"),
        RegisterInfo(34130, "RE_Point_Arr1_10_TopP_MW", "WORD16", "Координата Y точки №10"),
        RegisterInfo(34131, "RE_Point_Arr1_11_TopP_MW", "WORD16", "Координата Y точки №11"),
        RegisterInfo(34132, "RE_Point_Arr1_12_TopP_MW", "WORD16", "Координата Y точки №12"),
        RegisterInfo(34133, "RE_Point_Arr1_13_TopP_MW", "WORD16", "Координата Y точки №13"),
        RegisterInfo(34134, "RE_Point_Arr1_14_TopP_MW", "WORD16", "Координата Y точки №14"),
        RegisterInfo(34135, "RE_Point_Arr1_15_TopP_MW", "WORD16", "Координата Y точки №15"),
        RegisterInfo(34136, "RE_Point_Arr1_16_TopP_MW", "WORD16", "Координата Y точки №16"),
        RegisterInfo(34137, "RE_Point_Arr1_17_TopP_MW", "WORD16", "Координата Y точки №17"),
        RegisterInfo(34138, "RE_Point_Arr1_18_TopP_MW", "WORD16", "Координата Y точки №18"),
        RegisterInfo(34139, "RE_Point_Arr1_19_TopP_MW", "WORD16", "Координата Y точки №19"),
        // Параметры отезков для отображения полигона №1
        RegisterInfo(34140, "RE_Point_Arr1_0_LeftP_MW", "WORD16", "Координата X точки №00"),
        RegisterInfo(34141, "RE_Point_Arr1_1_LeftP_MW", "WORD16", "Координата X точки №01"),
        RegisterInfo(34142, "RE_Point_Arr1_2_LeftP_MW", "WORD16", "Координата X точки №02"),
        RegisterInfo(34143, "RE_Point_Arr1_3_LeftP_MW", "WORD16", "Координата X точки №03"),
        RegisterInfo(34144, "RE_Point_Arr1_4_LeftP_MW", "WORD16", "Координата X точки №04"),
        RegisterInfo(34145, "RE_Point_Arr1_5_LeftP_MW", "WORD16", "Координата X точки №05"),
        RegisterInfo(34146, "RE_Point_Arr1_6_LeftP_MW", "WORD16", "Координата X точки №06"),
        RegisterInfo(34147, "RE_Point_Arr1_7_LeftP_MW", "WORD16", "Координата X точки №07"),
        RegisterInfo(34148, "RE_Point_Arr1_8_LeftP_MW", "WORD16", "Координата X точки №08"),
        RegisterInfo(34149, "RE_Point_Arr1_9_LeftP_MW", "WORD16", "Координата X точки №09"),
        RegisterInfo(34150, "RE_Point_Arr1_10_LeftP_MW", "WORD16", "Координата X точки №10"),
        RegisterInfo(34151, "RE_Point_Arr1_11_LeftP_MW", "WORD16", "Координата X точки №11"),
        RegisterInfo(34152, "RE_Point_Arr1_12_LeftP_MW", "WORD16", "Координата X точки №12"),
        RegisterInfo(34153, "RE_Point_Arr1_13_LeftP_MW", "WORD16", "Координата X точки №13"),
        RegisterInfo(34154, "RE_Point_Arr1_14_LeftP_MW", "WORD16", "Координата X точки №14"),
        RegisterInfo(34155, "RE_Point_Arr1_15_LeftP_MW", "WORD16", "Координата X точки №15"),
        RegisterInfo(34156, "RE_Point_Arr1_16_LeftP_MW", "WORD16", "Координата X точки №16"),
        RegisterInfo(34157, "RE_Point_Arr1_17_LeftP_MW", "WORD16", "Координата X точки №17"),
        RegisterInfo(34158, "RE_Point_Arr1_18_LeftP_MW", "WORD16", "Координата X точки №18"),
        RegisterInfo(34159, "RE_Point_Arr1_19_LeftP_MW", "WORD16", "Координата X точки №19"),
        // Параметры отезков для отображения полигона №1
        RegisterInfo(34160, "RE_Point_Arr1_0_Angle_MW", "WORD16", "Угол между сегментами 00-01"),
        RegisterInfo(34161, "RE_Point_Arr1_1_Angle_MW", "WORD16", "Угол между сегментами 01-02"),
        RegisterInfo(34162, "RE_Point_Arr1_2_Angle_MW", "WORD16", "Угол между сегментами 02-03"),
        RegisterInfo(34163, "RE_Point_Arr1_3_Angle_MW", "WORD16", "Угол между сегментами 03-04"),
        RegisterInfo(34164, "RE_Point_Arr1_4_Angle_MW", "WORD16", "Угол между сегментами 04-05"),
        RegisterInfo(34165, "RE_Point_Arr1_5_Angle_MW", "WORD16", "Угол между сегментами 05-06"),
        RegisterInfo(34166, "RE_Point_Arr1_6_Angle_MW", "WORD16", "Угол между сегментами 06-07"),
        RegisterInfo(34167, "RE_Point_Arr1_7_Angle_MW", "WORD16", "Угол между сегментами 07-08"),
        RegisterInfo(34168, "RE_Point_Arr1_8_Angle_MW", "WORD16", "Угол между сегментами 08-09"),
        RegisterInfo(34169, "RE_Point_Arr1_9_Angle_MW", "WORD16", "Угол между сегментами 09-10"),
        RegisterInfo(34170, "RE_Point_Arr1_10_Angle_MW", "WORD16", "Угол между сегментами 10-11"),
        RegisterInfo(34171, "RE_Point_Arr1_11_Angle_MW", "WORD16", "Угол между сегментами 11-12"),
        RegisterInfo(34172, "RE_Point_Arr1_12_Angle_MW", "WORD16", "Угол между сегментами 12-13"),
        RegisterInfo(34173, "RE_Point_Arr1_13_Angle_MW", "WORD16", "Угол между сегментами 13-14"),
        RegisterInfo(34174, "RE_Point_Arr1_14_Angle_MW", "WORD16", "Угол между сегментами 14-15"),
        RegisterInfo(34175, "RE_Point_Arr1_15_Angle_MW", "WORD16", "Угол между сегментами 15-16"),
        RegisterInfo(34176, "RE_Point_Arr1_16_Angle_MW", "WORD16", "Угол между сегментами 16-17"),
        RegisterInfo(34177, "RE_Point_Arr1_17_Angle_MW", "WORD16", "Угол между сегментами 17-18"),
        RegisterInfo(34178, "RE_Point_Arr1_18_Angle_MW", "WORD16", "Угол между сегментами 18-19"),
        RegisterInfo(34179, "RE_Point_Arr1_19_Angle_MW", "WORD16", "Угол между сегментами 19-00"),
        // Параметры отезков для отображения полигона №1
        RegisterInfo(34180, "RE_Point_Arr1_0_Visilbe_MW", "WORD16", "Видимость сегмента №00"),
        RegisterInfo(34181, "RE_Point_Arr1_1_Visilbe_MW", "WORD16", "Видимость сегмента №01"),
        RegisterInfo(34182, "RE_Point_Arr1_2_Visilbe_MW", "WORD16", "Видимость сегмента №02"),
        RegisterInfo(34183, "RE_Point_Arr1_3_Visilbe_MW", "WORD16", "Видимость сегмента №03"),
        RegisterInfo(34184, "RE_Point_Arr1_4_Visilbe_MW", "WORD16", "Видимость сегмента №04"),
        RegisterInfo(34185, "RE_Point_Arr1_5_Visilbe_MW", "WORD16", "Видимость сегмента №05"),
        RegisterInfo(34186, "RE_Point_Arr1_6_Visilbe_MW", "WORD16", "Видимость сегмента №06"),
        RegisterInfo(34187, "RE_Point_Arr1_7_Visilbe_MW", "WORD16", "Видимость сегмента №07"),
        RegisterInfo(34188, "RE_Point_Arr1_8_Visilbe_MW", "WORD16", "Видимость сегмента №08"),
        RegisterInfo(34189, "RE_Point_Arr1_9_Visilbe_MW", "WORD16", "Видимость сегмента №09"),
        RegisterInfo(34190, "RE_Point_Arr1_10_Visilbe_MW", "WORD16", "Видимость сегмента №10"),
        RegisterInfo(34191, "RE_Point_Arr1_11_Visilbe_MW", "WORD16", "Видимость сегмента №11"),
        RegisterInfo(34192, "RE_Point_Arr1_12_Visilbe_MW", "WORD16", "Видимость сегмента №12"),
        RegisterInfo(34193, "RE_Point_Arr1_13_Visilbe_MW", "WORD16", "Видимость сегмента №13"),
        RegisterInfo(34194, "RE_Point_Arr1_14_Visilbe_MW", "WORD16", "Видимость сегмента №14"),
        RegisterInfo(34195, "RE_Point_Arr1_15_Visilbe_MW", "WORD16", "Видимость сегмента №15"),
        RegisterInfo(34196, "RE_Point_Arr1_16_Visilbe_MW", "WORD16", "Видимость сегмента №16"),
        RegisterInfo(34197, "RE_Point_Arr1_17_Visilbe_MW", "WORD16", "Видимость сегмента №17"),
        RegisterInfo(34198, "RE_Point_Arr1_18_Visilbe_MW", "WORD16", "Видимость сегмента №18"),
        RegisterInfo(34199, "RE_Point_Arr1_19_Visilbe_MW", "WORD16", "Видимость сегмента №19"),


        // Параметры отезков для отображения полигона №2
        RegisterInfo(34200, "RE_Point_Arr2_0_Width_MW", "WORD16", "Длина сегмента №00"),
        RegisterInfo(34201, "RE_Point_Arr2_1_Width_MW", "WORD16", "Длина сегмента №01"),
        RegisterInfo(34202, "RE_Point_Arr2_2_Width_MW", "WORD16", "Длина сегмента №02"),
        RegisterInfo(34203, "RE_Point_Arr2_3_Width_MW", "WORD16", "Длина сегмента №03"),
        RegisterInfo(34204, "RE_Point_Arr2_4_Width_MW", "WORD16", "Длина сегмента №04"),
        RegisterInfo(34205, "RE_Point_Arr2_5_Width_MW", "WORD16", "Длина сегмента №05"),
        RegisterInfo(34206, "RE_Point_Arr2_6_Width_MW", "WORD16", "Длина сегмента №06"),
        RegisterInfo(34207, "RE_Point_Arr2_7_Width_MW", "WORD16", "Длина сегмента №07"),
        RegisterInfo(34208, "RE_Point_Arr2_8_Width_MW", "WORD16", "Длина сегмента №08"),
        RegisterInfo(34209, "RE_Point_Arr2_9_Width_MW", "WORD16", "Длина сегмента №09"),
        RegisterInfo(34210, "RE_Point_Arr2_10_Width_MW", "WORD16", "Длина сегмента №10"),
        RegisterInfo(34211, "RE_Point_Arr2_11_Width_MW", "WORD16", "Длина сегмента №11"),
        RegisterInfo(34212, "RE_Point_Arr2_12_Width_MW", "WORD16", "Длина сегмента №12"),
        RegisterInfo(34213, "RE_Point_Arr2_13_Width_MW", "WORD16", "Длина сегмента №13"),
        RegisterInfo(34214, "RE_Point_Arr2_14_Width_MW", "WORD16", "Длина сегмента №14"),
        RegisterInfo(34215, "RE_Point_Arr2_15_Width_MW", "WORD16", "Длина сегмента №15"),
        RegisterInfo(34216, "RE_Point_Arr2_16_Width_MW", "WORD16", "Длина сегмента №16"),
        RegisterInfo(34217, "RE_Point_Arr2_17_Width_MW", "WORD16", "Длина сегмента №17"),
        RegisterInfo(34218, "RE_Point_Arr2_18_Width_MW", "WORD16", "Длина сегмента №18"),
        RegisterInfo(34219, "RE_Point_Arr2_19_Width_MW", "WORD16", "Длина сегмента №19"),
        // Параметры отезков для отображения полигона №2
        RegisterInfo(34220, "RE_Point_Arr2_0_TopP_MW", "WORD16", "Координата Y точки №00"),
        RegisterInfo(34221, "RE_Point_Arr2_1_TopP_MW", "WORD16", "Координата Y точки №01"),
        RegisterInfo(34222, "RE_Point_Arr2_2_TopP_MW", "WORD16", "Координата Y точки №02"),
        RegisterInfo(34223, "RE_Point_Arr2_3_TopP_MW", "WORD16", "Координата Y точки №03"),
        RegisterInfo(34224, "RE_Point_Arr2_4_TopP_MW", "WORD16", "Координата Y точки №04"),
        RegisterInfo(34225, "RE_Point_Arr2_5_TopP_MW", "WORD16", "Координата Y точки №05"),
        RegisterInfo(34226, "RE_Point_Arr2_6_TopP_MW", "WORD16", "Координата Y точки №06"),
        RegisterInfo(34227, "RE_Point_Arr2_7_TopP_MW", "WORD16", "Координата Y точки №07"),
        RegisterInfo(34228, "RE_Point_Arr2_8_TopP_MW", "WORD16", "Координата Y точки №08"),
        RegisterInfo(34229, "RE_Point_Arr2_9_TopP_MW", "WORD16", "Координата Y точки №09"),
        RegisterInfo(34230, "RE_Point_Arr2_10_TopP_MW", "WORD16", "Координата Y точки №10"),
        RegisterInfo(34231, "RE_Point_Arr2_11_TopP_MW", "WORD16", "Координата Y точки №11"),
        RegisterInfo(34232, "RE_Point_Arr2_12_TopP_MW", "WORD16", "Координата Y точки №12"),
        RegisterInfo(34233, "RE_Point_Arr2_13_TopP_MW", "WORD16", "Координата Y точки №13"),
        RegisterInfo(34234, "RE_Point_Arr2_14_TopP_MW", "WORD16", "Координата Y точки №14"),
        RegisterInfo(34235, "RE_Point_Arr2_15_TopP_MW", "WORD16", "Координата Y точки №15"),
        RegisterInfo(34236, "RE_Point_Arr2_16_TopP_MW", "WORD16", "Координата Y точки №16"),
        RegisterInfo(34237, "RE_Point_Arr2_17_TopP_MW", "WORD16", "Координата Y точки №17"),
        RegisterInfo(34238, "RE_Point_Arr2_18_TopP_MW", "WORD16", "Координата Y точки №18"),
        RegisterInfo(34239, "RE_Point_Arr2_19_TopP_MW", "WORD16", "Координата Y точки №19"),
        // Параметры отезков для отображения полигона №2
        RegisterInfo(34240, "RE_Point_Arr2_0_LeftP_MW", "WORD16", "Координата X точки №00"),
        RegisterInfo(34241, "RE_Point_Arr2_1_LeftP_MW", "WORD16", "Координата X точки №01"),
        RegisterInfo(34242, "RE_Point_Arr2_2_LeftP_MW", "WORD16", "Координата X точки №02"),
        RegisterInfo(34243, "RE_Point_Arr2_3_LeftP_MW", "WORD16", "Координата X точки №03"),
        RegisterInfo(34244, "RE_Point_Arr2_4_LeftP_MW", "WORD16", "Координата X точки №04"),
        RegisterInfo(34245, "RE_Point_Arr2_5_LeftP_MW", "WORD16", "Координата X точки №05"),
        RegisterInfo(34246, "RE_Point_Arr2_6_LeftP_MW", "WORD16", "Координата X точки №06"),
        RegisterInfo(34247, "RE_Point_Arr2_7_LeftP_MW", "WORD16", "Координата X точки №07"),
        RegisterInfo(34248, "RE_Point_Arr2_8_LeftP_MW", "WORD16", "Координата X точки №08"),
        RegisterInfo(34249, "RE_Point_Arr2_9_LeftP_MW", "WORD16", "Координата X точки №09"),
        RegisterInfo(34250, "RE_Point_Arr2_10_LeftP_MW", "WORD16", "Координата X точки №10"),
        RegisterInfo(34251, "RE_Point_Arr2_11_LeftP_MW", "WORD16", "Координата X точки №11"),
        RegisterInfo(34252, "RE_Point_Arr2_12_LeftP_MW", "WORD16", "Координата X точки №12"),
        RegisterInfo(34253, "RE_Point_Arr2_13_LeftP_MW", "WORD16", "Координата X точки №13"),
        RegisterInfo(34254, "RE_Point_Arr2_14_LeftP_MW", "WORD16", "Координата X точки №14"),
        RegisterInfo(34255, "RE_Point_Arr2_15_LeftP_MW", "WORD16", "Координата X точки №15"),
        RegisterInfo(34256, "RE_Point_Arr2_16_LeftP_MW", "WORD16", "Координата X точки №16"),
        RegisterInfo(34257, "RE_Point_Arr2_17_LeftP_MW", "WORD16", "Координата X точки №17"),
        RegisterInfo(34258, "RE_Point_Arr2_18_LeftP_MW", "WORD16", "Координата X точки №18"),
        RegisterInfo(34259, "RE_Point_Arr2_19_LeftP_MW", "WORD16", "Координата X точки №19"),
        // Параметры отезков для отображения полигона №2
        RegisterInfo(34260, "RE_Point_Arr2_0_Angle_MW", "WORD16", "Угол между сегментами 00-01"),
        RegisterInfo(34261, "RE_Point_Arr2_1_Angle_MW", "WORD16", "Угол между сегментами 01-02"),
        RegisterInfo(34262, "RE_Point_Arr2_2_Angle_MW", "WORD16", "Угол между сегментами 02-03"),
        RegisterInfo(34263, "RE_Point_Arr2_3_Angle_MW", "WORD16", "Угол между сегментами 03-04"),
        RegisterInfo(34264, "RE_Point_Arr2_4_Angle_MW", "WORD16", "Угол между сегментами 04-05"),
        RegisterInfo(34265, "RE_Point_Arr2_5_Angle_MW", "WORD16", "Угол между сегментами 05-06"),
        RegisterInfo(34266, "RE_Point_Arr2_6_Angle_MW", "WORD16", "Угол между сегментами 06-07"),
        RegisterInfo(34267, "RE_Point_Arr2_7_Angle_MW", "WORD16", "Угол между сегментами 07-08"),
        RegisterInfo(34268, "RE_Point_Arr2_8_Angle_MW", "WORD16", "Угол между сегментами 08-09"),
        RegisterInfo(34269, "RE_Point_Arr2_9_Angle_MW", "WORD16", "Угол между сегментами 09-10"),
        RegisterInfo(34270, "RE_Point_Arr2_10_Angle_MW", "WORD16", "Угол между сегментами 10-11"),
        RegisterInfo(34271, "RE_Point_Arr2_11_Angle_MW", "WORD16", "Угол между сегментами 11-12"),
        RegisterInfo(34272, "RE_Point_Arr2_12_Angle_MW", "WORD16", "Угол между сегментами 12-13"),
        RegisterInfo(34273, "RE_Point_Arr2_13_Angle_MW", "WORD16", "Угол между сегментами 13-14"),
        RegisterInfo(34274, "RE_Point_Arr2_14_Angle_MW", "WORD16", "Угол между сегментами 14-15"),
        RegisterInfo(34275, "RE_Point_Arr2_15_Angle_MW", "WORD16", "Угол между сегментами 15-16"),
        RegisterInfo(34276, "RE_Point_Arr2_16_Angle_MW", "WORD16", "Угол между сегментами 16-17"),
        RegisterInfo(34277, "RE_Point_Arr2_17_Angle_MW", "WORD16", "Угол между сегментами 17-18"),
        RegisterInfo(34278, "RE_Point_Arr2_18_Angle_MW", "WORD16", "Угол между сегментами 18-19"),
        RegisterInfo(34279, "RE_Point_Arr2_19_Angle_MW", "WORD16", "Угол между сегментами 19-00"),
        // Параметры отезков для отображения полигона №2
        RegisterInfo(34280, "RE_Point_Arr2_0_Visilbe_MW", "WORD16", "Видимость сегмента №00"),
        RegisterInfo(34281, "RE_Point_Arr2_1_Visilbe_MW", "WORD16", "Видимость сегмента №01"),
        RegisterInfo(34282, "RE_Point_Arr2_2_Visilbe_MW", "WORD16", "Видимость сегмента №02"),
        RegisterInfo(34283, "RE_Point_Arr2_3_Visilbe_MW", "WORD16", "Видимость сегмента №03"),
        RegisterInfo(34284, "RE_Point_Arr2_4_Visilbe_MW", "WORD16", "Видимость сегмента №04"),
        RegisterInfo(34285, "RE_Point_Arr2_5_Visilbe_MW", "WORD16", "Видимость сегмента №05"),
        RegisterInfo(34286, "RE_Point_Arr2_6_Visilbe_MW", "WORD16", "Видимость сегмента №06"),
        RegisterInfo(34287, "RE_Point_Arr2_7_Visilbe_MW", "WORD16", "Видимость сегмента №07"),
        RegisterInfo(34288, "RE_Point_Arr2_8_Visilbe_MW", "WORD16", "Видимость сегмента №08"),
        RegisterInfo(34289, "RE_Point_Arr2_9_Visilbe_MW", "WORD16", "Видимость сегмента №09"),
        RegisterInfo(34290, "RE_Point_Arr2_10_Visilbe_MW", "WORD16", "Видимость сегмента №10"),
        RegisterInfo(34291, "RE_Point_Arr2_11_Visilbe_MW", "WORD16", "Видимость сегмента №11"),
        RegisterInfo(34292, "RE_Point_Arr2_12_Visilbe_MW", "WORD16", "Видимость сегмента №12"),
        RegisterInfo(34293, "RE_Point_Arr2_13_Visilbe_MW", "WORD16", "Видимость сегмента №13"),
        RegisterInfo(34294, "RE_Point_Arr2_14_Visilbe_MW", "WORD16", "Видимость сегмента №14"),
        RegisterInfo(34295, "RE_Point_Arr2_15_Visilbe_MW", "WORD16", "Видимость сегмента №15"),
        RegisterInfo(34296, "RE_Point_Arr2_16_Visilbe_MW", "WORD16", "Видимость сегмента №16"),
        RegisterInfo(34297, "RE_Point_Arr2_17_Visilbe_MW", "WORD16", "Видимость сегмента №17"),
        RegisterInfo(34298, "RE_Point_Arr2_18_Visilbe_MW", "WORD16", "Видимость сегмента №18"),
        RegisterInfo(34299, "RE_Point_Arr2_19_Visilbe_MW", "WORD16", "Видимость сегмента №19"),


        // Параметры отезков для отображения полигона №3
        RegisterInfo(34300, "RE_Point_Arr3_0_Width_MW", "WORD16", "Длина сегмента №00"),
        RegisterInfo(34301, "RE_Point_Arr3_1_Width_MW", "WORD16", "Длина сегмента №01"),
        RegisterInfo(34302, "RE_Point_Arr3_2_Width_MW", "WORD16", "Длина сегмента №02"),
        RegisterInfo(34303, "RE_Point_Arr3_3_Width_MW", "WORD16", "Длина сегмента №03"),
        RegisterInfo(34304, "RE_Point_Arr3_4_Width_MW", "WORD16", "Длина сегмента №04"),
        RegisterInfo(34305, "RE_Point_Arr3_5_Width_MW", "WORD16", "Длина сегмента №05"),
        RegisterInfo(34306, "RE_Point_Arr3_6_Width_MW", "WORD16", "Длина сегмента №06"),
        RegisterInfo(34307, "RE_Point_Arr3_7_Width_MW", "WORD16", "Длина сегмента №07"),
        RegisterInfo(34308, "RE_Point_Arr3_8_Width_MW", "WORD16", "Длина сегмента №08"),
        RegisterInfo(34309, "RE_Point_Arr3_9_Width_MW", "WORD16", "Длина сегмента №09"),
        RegisterInfo(34310, "RE_Point_Arr3_10_Width_MW", "WORD16", "Длина сегмента №10"),
        RegisterInfo(34311, "RE_Point_Arr3_11_Width_MW", "WORD16", "Длина сегмента №11"),
        RegisterInfo(34312, "RE_Point_Arr3_12_Width_MW", "WORD16", "Длина сегмента №12"),
        RegisterInfo(34313, "RE_Point_Arr3_13_Width_MW", "WORD16", "Длина сегмента №13"),
        RegisterInfo(34314, "RE_Point_Arr3_14_Width_MW", "WORD16", "Длина сегмента №14"),
        RegisterInfo(34315, "RE_Point_Arr3_15_Width_MW", "WORD16", "Длина сегмента №15"),
        RegisterInfo(34316, "RE_Point_Arr3_16_Width_MW", "WORD16", "Длина сегмента №16"),
        RegisterInfo(34317, "RE_Point_Arr3_17_Width_MW", "WORD16", "Длина сегмента №17"),
        RegisterInfo(34318, "RE_Point_Arr3_18_Width_MW", "WORD16", "Длина сегмента №18"),
        RegisterInfo(34319, "RE_Point_Arr3_19_Width_MW", "WORD16", "Длина сегмента №19"),
        // Параметры отезков для отображения полигона №3
        RegisterInfo(34320, "RE_Point_Arr3_0_TopP_MW", "WORD16", "Координата Y точки №00"),
        RegisterInfo(34321, "RE_Point_Arr3_1_TopP_MW", "WORD16", "Координата Y точки №01"),
        RegisterInfo(34322, "RE_Point_Arr3_2_TopP_MW", "WORD16", "Координата Y точки №02"),
        RegisterInfo(34323, "RE_Point_Arr3_3_TopP_MW", "WORD16", "Координата Y точки №03"),
        RegisterInfo(34324, "RE_Point_Arr3_4_TopP_MW", "WORD16", "Координата Y точки №04"),
        RegisterInfo(34325, "RE_Point_Arr3_5_TopP_MW", "WORD16", "Координата Y точки №05"),
        RegisterInfo(34326, "RE_Point_Arr3_6_TopP_MW", "WORD16", "Координата Y точки №06"),
        RegisterInfo(34327, "RE_Point_Arr3_7_TopP_MW", "WORD16", "Координата Y точки №07"),
        RegisterInfo(34328, "RE_Point_Arr3_8_TopP_MW", "WORD16", "Координата Y точки №08"),
        RegisterInfo(34329, "RE_Point_Arr3_9_TopP_MW", "WORD16", "Координата Y точки №09"),
        RegisterInfo(34330, "RE_Point_Arr3_10_TopP_MW", "WORD16", "Координата Y точки №10"),
        RegisterInfo(34331, "RE_Point_Arr3_11_TopP_MW", "WORD16", "Координата Y точки №11"),
        RegisterInfo(34332, "RE_Point_Arr3_12_TopP_MW", "WORD16", "Координата Y точки №12"),
        RegisterInfo(34333, "RE_Point_Arr3_13_TopP_MW", "WORD16", "Координата Y точки №13"),
        RegisterInfo(34334, "RE_Point_Arr3_14_TopP_MW", "WORD16", "Координата Y точки №14"),
        RegisterInfo(34335, "RE_Point_Arr3_15_TopP_MW", "WORD16", "Координата Y точки №15"),
        RegisterInfo(34336, "RE_Point_Arr3_16_TopP_MW", "WORD16", "Координата Y точки №16"),
        RegisterInfo(34337, "RE_Point_Arr3_17_TopP_MW", "WORD16", "Координата Y точки №17"),
        RegisterInfo(34338, "RE_Point_Arr3_18_TopP_MW", "WORD16", "Координата Y точки №18"),
        RegisterInfo(34339, "RE_Point_Arr3_19_TopP_MW", "WORD16", "Координата Y точки №19"),
        // Параметры отезков для отображения полигона №3
        RegisterInfo(34340, "RE_Point_Arr3_0_LeftP_MW", "WORD16", "Координата X точки №00"),
        RegisterInfo(34341, "RE_Point_Arr3_1_LeftP_MW", "WORD16", "Координата X точки №01"),
        RegisterInfo(34342, "RE_Point_Arr3_2_LeftP_MW", "WORD16", "Координата X точки №02"),
        RegisterInfo(34343, "RE_Point_Arr3_3_LeftP_MW", "WORD16", "Координата X точки №03"),
        RegisterInfo(34344, "RE_Point_Arr3_4_LeftP_MW", "WORD16", "Координата X точки №04"),
        RegisterInfo(34345, "RE_Point_Arr3_5_LeftP_MW", "WORD16", "Координата X точки №05"),
        RegisterInfo(34346, "RE_Point_Arr3_6_LeftP_MW", "WORD16", "Координата X точки №06"),
        RegisterInfo(34347, "RE_Point_Arr3_7_LeftP_MW", "WORD16", "Координата X точки №07"),
        RegisterInfo(34348, "RE_Point_Arr3_8_LeftP_MW", "WORD16", "Координата X точки №08"),
        RegisterInfo(34349, "RE_Point_Arr3_9_LeftP_MW", "WORD16", "Координата X точки №09"),
        RegisterInfo(34350, "RE_Point_Arr3_10_LeftP_MW", "WORD16", "Координата X точки №10"),
        RegisterInfo(34351, "RE_Point_Arr3_11_LeftP_MW", "WORD16", "Координата X точки №11"),
        RegisterInfo(34352, "RE_Point_Arr3_12_LeftP_MW", "WORD16", "Координата X точки №12"),
        RegisterInfo(34353, "RE_Point_Arr3_13_LeftP_MW", "WORD16", "Координата X точки №13"),
        RegisterInfo(34354, "RE_Point_Arr3_14_LeftP_MW", "WORD16", "Координата X точки №14"),
        RegisterInfo(34355, "RE_Point_Arr3_15_LeftP_MW", "WORD16", "Координата X точки №15"),
        RegisterInfo(34356, "RE_Point_Arr3_16_LeftP_MW", "WORD16", "Координата X точки №16"),
        RegisterInfo(34357, "RE_Point_Arr3_17_LeftP_MW", "WORD16", "Координата X точки №17"),
        RegisterInfo(34358, "RE_Point_Arr3_18_LeftP_MW", "WORD16", "Координата X точки №18"),
        RegisterInfo(34359, "RE_Point_Arr3_19_LeftP_MW", "WORD16", "Координата X точки №19"),
        // Параметры отезков для отображения полигона №3
        RegisterInfo(34360, "RE_Point_Arr3_0_Angle_MW", "WORD16", "Угол между сегментами 00-01"),
        RegisterInfo(34361, "RE_Point_Arr3_1_Angle_MW", "WORD16", "Угол между сегментами 01-02"),
        RegisterInfo(34362, "RE_Point_Arr3_2_Angle_MW", "WORD16", "Угол между сегментами 02-03"),
        RegisterInfo(34363, "RE_Point_Arr3_3_Angle_MW", "WORD16", "Угол между сегментами 03-04"),
        RegisterInfo(34364, "RE_Point_Arr3_4_Angle_MW", "WORD16", "Угол между сегментами 04-05"),
        RegisterInfo(34365, "RE_Point_Arr3_5_Angle_MW", "WORD16", "Угол между сегментами 05-06"),
        RegisterInfo(34366, "RE_Point_Arr3_6_Angle_MW", "WORD16", "Угол между сегментами 06-07"),
        RegisterInfo(34367, "RE_Point_Arr3_7_Angle_MW", "WORD16", "Угол между сегментами 07-08"),
        RegisterInfo(34368, "RE_Point_Arr3_8_Angle_MW", "WORD16", "Угол между сегментами 08-09"),
        RegisterInfo(34369, "RE_Point_Arr3_9_Angle_MW", "WORD16", "Угол между сегментами 09-10"),
        RegisterInfo(34370, "RE_Point_Arr3_10_Angle_MW", "WORD16", "Угол между сегментами 10-11"),
        RegisterInfo(34371, "RE_Point_Arr3_11_Angle_MW", "WORD16", "Угол между сегментами 11-12"),
        RegisterInfo(34372, "RE_Point_Arr3_12_Angle_MW", "WORD16", "Угол между сегментами 12-13"),
        RegisterInfo(34373, "RE_Point_Arr3_13_Angle_MW", "WORD16", "Угол между сегментами 13-14"),
        RegisterInfo(34374, "RE_Point_Arr3_14_Angle_MW", "WORD16", "Угол между сегментами 14-15"),
        RegisterInfo(34375, "RE_Point_Arr3_15_Angle_MW", "WORD16", "Угол между сегментами 15-16"),
        RegisterInfo(34376, "RE_Point_Arr3_16_Angle_MW", "WORD16", "Угол между сегментами 16-17"),
        RegisterInfo(34377, "RE_Point_Arr3_17_Angle_MW", "WORD16", "Угол между сегментами 17-18"),
        RegisterInfo(34378, "RE_Point_Arr3_18_Angle_MW", "WORD16", "Угол между сегментами 18-19"),
        RegisterInfo(34379, "RE_Point_Arr3_19_Angle_MW", "WORD16", "Угол между сегментами 19-00"),
        // Параметры отезков для отображения полигона №3
        RegisterInfo(34380, "RE_Point_Arr3_0_Visilbe_MW", "WORD16", "Видимость сегмента №00"),
        RegisterInfo(34381, "RE_Point_Arr3_1_Visilbe_MW", "WORD16", "Видимость сегмента №01"),
        RegisterInfo(34382, "RE_Point_Arr3_2_Visilbe_MW", "WORD16", "Видимость сегмента №02"),
        RegisterInfo(34383, "RE_Point_Arr3_3_Visilbe_MW", "WORD16", "Видимость сегмента №03"),
        RegisterInfo(34384, "RE_Point_Arr3_4_Visilbe_MW", "WORD16", "Видимость сегмента №04"),
        RegisterInfo(34385, "RE_Point_Arr3_5_Visilbe_MW", "WORD16", "Видимость сегмента №05"),
        RegisterInfo(34386, "RE_Point_Arr3_6_Visilbe_MW", "WORD16", "Видимость сегмента №06"),
        RegisterInfo(34387, "RE_Point_Arr3_7_Visilbe_MW", "WORD16", "Видимость сегмента №07"),
        RegisterInfo(34388, "RE_Point_Arr3_8_Visilbe_MW", "WORD16", "Видимость сегмента №08"),
        RegisterInfo(34389, "RE_Point_Arr3_9_Visilbe_MW", "WORD16", "Видимость сегмента №09"),
        RegisterInfo(34390, "RE_Point_Arr3_10_Visilbe_MW", "WORD16", "Видимость сегмента №10"),
        RegisterInfo(34391, "RE_Point_Arr3_11_Visilbe_MW", "WORD16", "Видимость сегмента №11"),
        RegisterInfo(34392, "RE_Point_Arr3_12_Visilbe_MW", "WORD16", "Видимость сегмента №12"),
        RegisterInfo(34393, "RE_Point_Arr3_13_Visilbe_MW", "WORD16", "Видимость сегмента №13"),
        RegisterInfo(34394, "RE_Point_Arr3_14_Visilbe_MW", "WORD16", "Видимость сегмента №14"),
        RegisterInfo(34395, "RE_Point_Arr3_15_Visilbe_MW", "WORD16", "Видимость сегмента №15"),
        RegisterInfo(34396, "RE_Point_Arr3_16_Visilbe_MW", "WORD16", "Видимость сегмента №16"),
        RegisterInfo(34397, "RE_Point_Arr3_17_Visilbe_MW", "WORD16", "Видимость сегмента №17"),
        RegisterInfo(34398, "RE_Point_Arr3_18_Visilbe_MW", "WORD16", "Видимость сегмента №18"),
        RegisterInfo(34399, "RE_Point_Arr3_19_Visilbe_MW", "WORD16", "Видимость сегмента №19"),


        // Параметры отезков для отображения полигона №4
        RegisterInfo(34400, "RE_Point_Arr4_0_Width_MW", "WORD16", "Длина сегмента №00"),
        RegisterInfo(34401, "RE_Point_Arr4_1_Width_MW", "WORD16", "Длина сегмента №01"),
        RegisterInfo(34402, "RE_Point_Arr4_2_Width_MW", "WORD16", "Длина сегмента №02"),
        RegisterInfo(34403, "RE_Point_Arr4_3_Width_MW", "WORD16", "Длина сегмента №03"),
        RegisterInfo(34404, "RE_Point_Arr4_4_Width_MW", "WORD16", "Длина сегмента №04"),
        RegisterInfo(34405, "RE_Point_Arr4_5_Width_MW", "WORD16", "Длина сегмента №05"),
        RegisterInfo(34406, "RE_Point_Arr4_6_Width_MW", "WORD16", "Длина сегмента №06"),
        RegisterInfo(34407, "RE_Point_Arr4_7_Width_MW", "WORD16", "Длина сегмента №07"),
        RegisterInfo(34408, "RE_Point_Arr4_8_Width_MW", "WORD16", "Длина сегмента №08"),
        RegisterInfo(34409, "RE_Point_Arr4_9_Width_MW", "WORD16", "Длина сегмента №09"),
        RegisterInfo(34410, "RE_Point_Arr4_10_Width_MW", "WORD16", "Длина сегмента №10"),
        RegisterInfo(34411, "RE_Point_Arr4_11_Width_MW", "WORD16", "Длина сегмента №11"),
        RegisterInfo(34412, "RE_Point_Arr4_12_Width_MW", "WORD16", "Длина сегмента №12"),
        RegisterInfo(34413, "RE_Point_Arr4_13_Width_MW", "WORD16", "Длина сегмента №13"),
        RegisterInfo(34414, "RE_Point_Arr4_14_Width_MW", "WORD16", "Длина сегмента №14"),
        RegisterInfo(34415, "RE_Point_Arr4_15_Width_MW", "WORD16", "Длина сегмента №15"),
        RegisterInfo(34416, "RE_Point_Arr4_16_Width_MW", "WORD16", "Длина сегмента №16"),
        RegisterInfo(34417, "RE_Point_Arr4_17_Width_MW", "WORD16", "Длина сегмента №17"),
        RegisterInfo(34418, "RE_Point_Arr4_18_Width_MW", "WORD16", "Длина сегмента №18"),
        RegisterInfo(34419, "RE_Point_Arr4_19_Width_MW", "WORD16", "Длина сегмента №19"),
        // Параметры отезков для отображения полигона №4
        RegisterInfo(34420, "RE_Point_Arr4_0_TopP_MW", "WORD16", "Координата Y точки №00"),
        RegisterInfo(34421, "RE_Point_Arr4_1_TopP_MW", "WORD16", "Координата Y точки №01"),
        RegisterInfo(34422, "RE_Point_Arr4_2_TopP_MW", "WORD16", "Координата Y точки №02"),
        RegisterInfo(34423, "RE_Point_Arr4_3_TopP_MW", "WORD16", "Координата Y точки №03"),
        RegisterInfo(34424, "RE_Point_Arr4_4_TopP_MW", "WORD16", "Координата Y точки №04"),
        RegisterInfo(34425, "RE_Point_Arr4_5_TopP_MW", "WORD16", "Координата Y точки №05"),
        RegisterInfo(34426, "RE_Point_Arr4_6_TopP_MW", "WORD16", "Координата Y точки №06"),
        RegisterInfo(34427, "RE_Point_Arr4_7_TopP_MW", "WORD16", "Координата Y точки №07"),
        RegisterInfo(34428, "RE_Point_Arr4_8_TopP_MW", "WORD16", "Координата Y точки №08"),
        RegisterInfo(34429, "RE_Point_Arr4_9_TopP_MW", "WORD16", "Координата Y точки №09"),
        RegisterInfo(34430, "RE_Point_Arr4_10_TopP_MW", "WORD16", "Координата Y точки №10"),
        RegisterInfo(34431, "RE_Point_Arr4_11_TopP_MW", "WORD16", "Координата Y точки №11"),
        RegisterInfo(34432, "RE_Point_Arr4_12_TopP_MW", "WORD16", "Координата Y точки №12"),
        RegisterInfo(34433, "RE_Point_Arr4_13_TopP_MW", "WORD16", "Координата Y точки №13"),
        RegisterInfo(34434, "RE_Point_Arr4_14_TopP_MW", "WORD16", "Координата Y точки №14"),
        RegisterInfo(34435, "RE_Point_Arr4_15_TopP_MW", "WORD16", "Координата Y точки №15"),
        RegisterInfo(34436, "RE_Point_Arr4_16_TopP_MW", "WORD16", "Координата Y точки №16"),
        RegisterInfo(34437, "RE_Point_Arr4_17_TopP_MW", "WORD16", "Координата Y точки №17"),
        RegisterInfo(34438, "RE_Point_Arr4_18_TopP_MW", "WORD16", "Координата Y точки №18"),
        RegisterInfo(34439, "RE_Point_Arr4_19_TopP_MW", "WORD16", "Координата Y точки №19"),
        // Параметры отезков для отображения полигона №4
        RegisterInfo(34440, "RE_Point_Arr4_0_LeftP_MW", "WORD16", "Координата X точки №00"),
        RegisterInfo(34441, "RE_Point_Arr4_1_LeftP_MW", "WORD16", "Координата X точки №01"),
        RegisterInfo(34442, "RE_Point_Arr4_2_LeftP_MW", "WORD16", "Координата X точки №02"),
        RegisterInfo(34443, "RE_Point_Arr4_3_LeftP_MW", "WORD16", "Координата X точки №03"),
        RegisterInfo(34444, "RE_Point_Arr4_4_LeftP_MW", "WORD16", "Координата X точки №04"),
        RegisterInfo(34445, "RE_Point_Arr4_5_LeftP_MW", "WORD16", "Координата X точки №05"),
        RegisterInfo(34446, "RE_Point_Arr4_6_LeftP_MW", "WORD16", "Координата X точки №06"),
        RegisterInfo(34447, "RE_Point_Arr4_7_LeftP_MW", "WORD16", "Координата X точки №07"),
        RegisterInfo(34448, "RE_Point_Arr4_8_LeftP_MW", "WORD16", "Координата X точки №08"),
        RegisterInfo(34449, "RE_Point_Arr4_9_LeftP_MW", "WORD16", "Координата X точки №09"),
        RegisterInfo(34450, "RE_Point_Arr4_10_LeftP_MW", "WORD16", "Координата X точки №10"),
        RegisterInfo(34451, "RE_Point_Arr4_11_LeftP_MW", "WORD16", "Координата X точки №11"),
        RegisterInfo(34452, "RE_Point_Arr4_12_LeftP_MW", "WORD16", "Координата X точки №12"),
        RegisterInfo(34453, "RE_Point_Arr4_13_LeftP_MW", "WORD16", "Координата X точки №13"),
        RegisterInfo(34454, "RE_Point_Arr4_14_LeftP_MW", "WORD16", "Координата X точки №14"),
        RegisterInfo(34455, "RE_Point_Arr4_15_LeftP_MW", "WORD16", "Координата X точки №15"),
        RegisterInfo(34456, "RE_Point_Arr4_16_LeftP_MW", "WORD16", "Координата X точки №16"),
        RegisterInfo(34457, "RE_Point_Arr4_17_LeftP_MW", "WORD16", "Координата X точки №17"),
        RegisterInfo(34458, "RE_Point_Arr4_18_LeftP_MW", "WORD16", "Координата X точки №18"),
        RegisterInfo(34459, "RE_Point_Arr4_19_LeftP_MW", "WORD16", "Координата X точки №19"),
        // Параметры отезков для отображения полигона №4
        RegisterInfo(34460, "RE_Point_Arr4_0_Angle_MW", "WORD16", "Угол между сегментами 00-01"),
        RegisterInfo(34461, "RE_Point_Arr4_1_Angle_MW", "WORD16", "Угол между сегментами 01-02"),
        RegisterInfo(34462, "RE_Point_Arr4_2_Angle_MW", "WORD16", "Угол между сегментами 02-03"),
        RegisterInfo(34463, "RE_Point_Arr4_3_Angle_MW", "WORD16", "Угол между сегментами 03-04"),
        RegisterInfo(34464, "RE_Point_Arr4_4_Angle_MW", "WORD16", "Угол между сегментами 04-05"),
        RegisterInfo(34465, "RE_Point_Arr4_5_Angle_MW", "WORD16", "Угол между сегментами 05-06"),
        RegisterInfo(34466, "RE_Point_Arr4_6_Angle_MW", "WORD16", "Угол между сегментами 06-07"),
        RegisterInfo(34467, "RE_Point_Arr4_7_Angle_MW", "WORD16", "Угол между сегментами 07-08"),
        RegisterInfo(34468, "RE_Point_Arr4_8_Angle_MW", "WORD16", "Угол между сегментами 08-09"),
        RegisterInfo(34469, "RE_Point_Arr4_9_Angle_MW", "WORD16", "Угол между сегментами 09-10"),
        RegisterInfo(34470, "RE_Point_Arr4_10_Angle_MW", "WORD16", "Угол между сегментами 10-11"),
        RegisterInfo(34471, "RE_Point_Arr4_11_Angle_MW", "WORD16", "Угол между сегментами 11-12"),
        RegisterInfo(34472, "RE_Point_Arr4_12_Angle_MW", "WORD16", "Угол между сегментами 12-13"),
        RegisterInfo(34473, "RE_Point_Arr4_13_Angle_MW", "WORD16", "Угол между сегментами 13-14"),
        RegisterInfo(34474, "RE_Point_Arr4_14_Angle_MW", "WORD16", "Угол между сегментами 14-15"),
        RegisterInfo(34475, "RE_Point_Arr4_15_Angle_MW", "WORD16", "Угол между сегментами 15-16"),
        RegisterInfo(34476, "RE_Point_Arr4_16_Angle_MW", "WORD16", "Угол между сегментами 16-17"),
        RegisterInfo(34477, "RE_Point_Arr4_17_Angle_MW", "WORD16", "Угол между сегментами 17-18"),
        RegisterInfo(34478, "RE_Point_Arr4_18_Angle_MW", "WORD16", "Угол между сегментами 18-19"),
        RegisterInfo(34479, "RE_Point_Arr4_19_Angle_MW", "WORD16", "Угол между сегментами 19-00"),
        // Параметры отезков для отображения полигона №4
        RegisterInfo(34480, "RE_Point_Arr4_0_Visilbe_MW", "WORD16", "Видимость сегмента №00"),
        RegisterInfo(34481, "RE_Point_Arr4_1_Visilbe_MW", "WORD16", "Видимость сегмента №01"),
        RegisterInfo(34482, "RE_Point_Arr4_2_Visilbe_MW", "WORD16", "Видимость сегмента №02"),
        RegisterInfo(34483, "RE_Point_Arr4_3_Visilbe_MW", "WORD16", "Видимость сегмента №03"),
        RegisterInfo(34484, "RE_Point_Arr4_4_Visilbe_MW", "WORD16", "Видимость сегмента №04"),
        RegisterInfo(34485, "RE_Point_Arr4_5_Visilbe_MW", "WORD16", "Видимость сегмента №05"),
        RegisterInfo(34486, "RE_Point_Arr4_6_Visilbe_MW", "WORD16", "Видимость сегмента №06"),
        RegisterInfo(34487, "RE_Point_Arr4_7_Visilbe_MW", "WORD16", "Видимость сегмента №07"),
        RegisterInfo(34488, "RE_Point_Arr4_8_Visilbe_MW", "WORD16", "Видимость сегмента №08"),
        RegisterInfo(34489, "RE_Point_Arr4_9_Visilbe_MW", "WORD16", "Видимость сегмента №09"),
        RegisterInfo(34490, "RE_Point_Arr4_10_Visilbe_MW", "WORD16", "Видимость сегмента №10"),
        RegisterInfo(34491, "RE_Point_Arr4_11_Visilbe_MW", "WORD16", "Видимость сегмента №11"),
        RegisterInfo(34492, "RE_Point_Arr4_12_Visilbe_MW", "WORD16", "Видимость сегмента №12"),
        RegisterInfo(34493, "RE_Point_Arr4_13_Visilbe_MW", "WORD16", "Видимость сегмента №13"),
        RegisterInfo(34494, "RE_Point_Arr4_14_Visilbe_MW", "WORD16", "Видимость сегмента №14"),
        RegisterInfo(34495, "RE_Point_Arr4_15_Visilbe_MW", "WORD16", "Видимость сегмента №15"),
        RegisterInfo(34496, "RE_Point_Arr4_16_Visilbe_MW", "WORD16", "Видимость сегмента №16"),
        RegisterInfo(34497, "RE_Point_Arr4_17_Visilbe_MW", "WORD16", "Видимость сегмента №17"),
        RegisterInfo(34498, "RE_Point_Arr4_18_Visilbe_MW", "WORD16", "Видимость сегмента №18"),
        RegisterInfo(34499, "RE_Point_Arr4_19_Visilbe_MW", "WORD16", "Видимость сегмента №19")

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
        RegisterInfo(60000, "WR_v_xCmdSetDateTime_MW", "WORD16", "Команда установки времени"),
        RegisterInfo(60001, "WR_i_uiYear_MW", "WORD16", "Год для установки"),
        RegisterInfo(60002, "WR_i_uiMonth_MW", "WORD16", "Месяц для установки"),
        RegisterInfo(60003, "WR_i_uiDay_MW", "WORD16", "День для установки"),
        RegisterInfo(60004, "WR_i_uiHour_MW", "WORD16", "Час для установки"),
        RegisterInfo(60005, "WR_i_uiMinute_MW", "WORD16", "Минута для установки"),

        RegisterInfo(60007, "WR_TimeZone_MW", "WORD16", "(* Часовой пояс в UTC *)"),

        // Запись в регистры даты ввода крана в эксплуатацию
        RegisterInfo(63554, "WR_R_InstallDay_Crane_MW", "WORD16", "День ввода в эксплуатацию"),
        RegisterInfo(63555, "WR_R_InstallMonth_Crane_MW", "WORD16", "Месяц ввода в эксплуатацию"),
        RegisterInfo(63556, "WR_R_InstallYear_Crane_MW", "WORD16", "Год ввода в эксплуатацию"),
        // Запись даты регистрации крана в Ростехнадзоре
        RegisterInfo(63551, "WR_R_InstallDay_RTN_Crane_MW", "WORD16", "День регистрации в Ростехнадзоре"),
        RegisterInfo(63552, "WR_R_InstallMonth_RTN_Crane_MW", "WORD16", "Месяц регистрации в Ростехнадзоре"),
        RegisterInfo(63553, "WR_R_InstallYear_RTN_Crane_MW", "WORD16", "Год регистрации в Ростехнадзоре"),
        // Запись даты установки СБКБ на кран
        RegisterInfo(63557, "WR_R_InstallDaySBKB_MW", "WORD16", "День установки СБКБ"),
        RegisterInfo(63558, "WR_R_InstallMonthSBKB_MW", "WORD16", "Месяц установки СБКБ"),
        RegisterInfo(63559, "WR_R_InstallYearSBKB_MW", "WORD16", "Год установки СБКБ"),
        // Запись модели крана, зав. №-крана и №-СБКБ
        RegisterInfo(63501, "WR_sR_CraneType_MW", "UINT16", "Заводской тип крана"),
        RegisterInfo(63510, "WR_sR_SBKB_No_MW", "WORD16", "Серийный номер СБКБ"),
        RegisterInfo(63502, "WR_sR_Crane_No_MW", "WORD16", "Заводской номер крана"),
        //Чтение модели крана
        RegisterInfo(33501, "RE_CraneType_MW", "WORD16", "Заводской тип крана"),
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
        RegisterInfo(33130, "RE_Trolley_Position_MW", "WORD16", "Текущее положение тележки на стреле *0,1м"),
        RegisterInfo(33110, "RE_Hoist_Position_MW", "WORD16", "Текущее положение подъема *0,1м"),

        RegisterInfo(33508, "RE_MaxCurrentMaxWeight2_MW", "WORD16", "Максимальный вес на конце стрелы"),
        RegisterInfo(33509, "RE_MaxCurrentMaxWeight1_MW", "WORD16", "Максимальный вес для выбранной ГХ"),
        RegisterInfo(33505, "RE_Crane_Lenght_MW", "WORD16", "Длина стрелы крана"),
        RegisterInfo(33020, "RE_Current_Load_AI_MW", "WORD16", "Текущее значение с датчика груза в единицах входа модуля"),

        RegisterInfo(33200, "RE_Weight_Hook_MW", "WORD16", "Текущий вес на крюке *0,1т"),
        RegisterInfo(33202, "RE_Zapasovka_MW", "WORD16", "Значение - текущая запасовка. 2 / 4"),

        RegisterInfo(33025, "RE_Read_Weight_Calib_Stat", "WORD16", "Статус калибровки датчика усилия"),
        RegisterInfo(33026, "RE_Read_Weight_Calib_Message", "WORD16", "Сообщения при калибровке датчика усилия"),


        RegisterInfo(63537, "WR_xHMI_Curr_Gruz_Blok_MW", "WORD16", "Запись выбора блока, =0 с блоком, =1 без блока"),
        RegisterInfo(63507, "WR_iHMI_Curr_Gruz_Fun_MW", "WORD16", "Выбор грузовая характеристика"),
        RegisterInfo(63021, "WR_rMin_Weight_Val_MW", "WORD16", "C HMI значение в тоннах при минимальном грузе"),
        RegisterInfo(63022, "WR_rMax_Weight_Val_MW", "WORD16", "C HMI значение в тоннах при максимальном грузе"),
        RegisterInfo(63028, "WR_xMin_Weight_Set_MW", "WORD16", "Калибровать минимальный груз"),
        RegisterInfo(63029, "WR_xMax_Weight_Set_MW", "WORD16", "Калибровать максимальный груз"),

        RegisterInfo(63531, "WR_Current_Picture_Setting_MW", "WORD16", "Состояние экрана настройки. 21-Настройка подъема, тележки, хода. 37 - Настройка ГП"),
        RegisterInfo(63020, "WR_Weight_Calib_Reset", "WORD16", "Сброс калибровки датчики усилия 1-сброс минимума, 2-сброс максимума"),
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
        RegisterInfo(33130, "RE_Trolley_Position_MW",   "WORD16", "Текущее положение тележки на стреле *0,1м"),
        RegisterInfo(33120, "RE_Rotation_Position_MW",  "WORD16", "Текущий угол поворота"),
        RegisterInfo(33110, "RE_Hoist_Position_MW",     "WORD16", "Текущее положение подъема *0,1м"),
        RegisterInfo(33140, "RE_Crane_Position_MW",     "WORD16",  "Текущее положение крана *0,1м"),
        RegisterInfo(31420, "RE_Crane_Rails_En_MW",     "WORD16",  "Флаг включения перемещения крана. 1 - Ход крана активирован"),

        //Параметры датчиков. Здесь номера Word. В ПЛК номера в MD (Dword: AT%MD16525, 16526, 16527, 16528)
        RegisterInfo(33050,  "RE_udi_AbsEncFenac_Hoist_Position_MD",  "WORD16",  "Текущее значение датчика подъема *0,1м"),
        RegisterInfo(33051,  "RE_udi_AbsEncFenac_Hoist_Position_MD",  "WORD16",  "Текущее значение датчика подъема *0,1м"),
        RegisterInfo(33052,  "RE_udi_AbsEncFenac_Slew_Position_MD",  "WORD16",  "Текущее значение датчика поворота *0,1м"),
        RegisterInfo(33053,  "RE_udi_AbsEncFenac_Slew_Position_MD",  "WORD16",  "Текущее значение датчика поворота *0,1м"),
        RegisterInfo(33054,  "RE_udi_AbsEncFenac_Trolley_Position_MD",  "WORD16",  "Текущее значение датчика тележки *0,1м"),
        RegisterInfo(33055,  "RE_udi_AbsEncFenac_Trolley_Position_MD",  "WORD16",  "Текущее значение датчика тележки *0,1м"),
        RegisterInfo(33056,  "RE_udi_AbsEncFenac_Crane_Position_MD",  "WORD16",  "Текущее значение датчика хода *0,1м"),
        RegisterInfo(33057,  "RE_udi_AbsEncFenac_Crane_Position_MD",  "WORD16",  "Текущее значение датчика хода *0,1м"),

        //Параметры настройки датчика подъема
        RegisterInfo(61158, "WR_xHoist_Limit_En_MW", "WORD16", "Включить проверку ограничения подъемного мех. крюка"),
        RegisterInfo(31158, "RE_Hoist_Limit_Stat_MW", "WORD16", "Статус функции проверки ограничений в наладке"),
        RegisterInfo(63101, "WR_xHoist_Fwd_Mem_MW", "WORD16", "Запомнить положение по высоте при движении вверх"),
        RegisterInfo(63102, "WR_xHoist_Rev_Mem_MW", "WORD16", "Запомнить положение по высоте при движении вниз"),

        RegisterInfo(63111, "WR_rHoist_Pos_Fwd_Stop_MW", "WORD16", "Значение при котором останавливаем крюк вверх"),
        RegisterInfo(63112, "WR_rHoist_Pos_Rev_Stop_MW", "WORD16", "Значение при котором останавливаем крюк вниз"),
        RegisterInfo(63113, "WR_rHoist_Pos_Fwd_DwnSp_MW", "WORD16", "Значение при котором снижаем скорость двигая крюк вверх"),
        RegisterInfo(63114, "WR_rHoist_Pos_Rev_DwnSp_MW", "WORD16", "Значение при котором снижаем скорость двигая крюк вниз"),
        RegisterInfo(63115, "WR_rHoist_Pos_Zero_MW", "WORD16", "Значение нулевой высоты привода подъема"),
        RegisterInfo(63105, "WR_xHoist_Zero_Mem_MW", "WORD16", "Запомнить положение нулевой высоты подъема"),


        RegisterInfo(61160, "WR_xHoist_LSP_Auto_MW", "WORD16", "Включить адаптивное снижение скорости"),
        RegisterInfo(61163, "WR_rHoist_LSP_Dist3_MW", "WORD16", "дистанция снижение до 3-й скорости"),
        RegisterInfo(61162, "WR_rHoist_LSP_Dist2_MW", "WORD16", "дистанция снижение до 2-й скорости"),
        RegisterInfo(61161, "WR_rHoist_LSP_Dist1_MW", "WORD16", "дистанция снижение до 1-й скорости"),

        RegisterInfo(33111, "RE_Hoist_Pos_Fwd_Stop_MW", "WORD16", "Максимальное положение подъема *0,1м"),
        RegisterInfo(33112, "RE_Hoist_Pos_Rev_Stop_MW", "WORD16", "Минимальное положение подъема *0,1м"),
        RegisterInfo(33113, "RE_Hoist_Pos_Fwd_DwnSp_MW", "WORD16", "Положение крюка замедление вверх *0,1м"),
        RegisterInfo(33114, "RE_Hoist_Pos_Rev_DwnSp_MW", "WORD16", "Положение крюка замедление вниз *0,1м"),
        RegisterInfo(33115, "RE_Hoist_Pos_Zero_MW", "WORD16", "Положение нулевой высоты привода подъема *0,1м"),

        //Параметры настройки датчика поворота
        RegisterInfo(61258, "WR_xSlew_Limit_En_MW", "WORD16", "Включение принудительной проверки механизма поворота в наладочном режиме"),
        RegisterInfo(31258, "RE_Slew_Limit_Stat_MW", "WORD16", "Статус функции проверки ограничений в наладке"),
        RegisterInfo(63129, "WR_rMax_Slew_Angle_MW", "WORD16", "Максимальный угол поворота, устанавливается с HMI"),
        RegisterInfo(63121, "WR_rCalc_Angle_MW", "WORD16", "Задаваемое значение угла при калибровке энкодера поворота"),
        RegisterInfo(63201, "WR_xSlew_Zero_Mem_MW", "WORD16", "Запомнить положение стрелы при нулевом угле"),
        RegisterInfo(63202, "WR_xSlew_Angle_Mem_MW", "WORD16", "Запомнить положение стрелы при заданном угле"),
        RegisterInfo(61260, "WR_xSlew_LSP_Auto_MW", "WORD16", "Включить адаптивное снижение скорости"),
        RegisterInfo(61263, "WR_rSlew_LSP_Dist3_MW", "WORD16", "дистанция снижение до 3-й скорости"),
        RegisterInfo(61262, "WR_rSlew_LSP_Dist2_MW", "WORD16", "дистанция снижение до 2-й скорости"),
        RegisterInfo(61261, "WR_rSlew_LSP_Dist1_MW", "WORD16", "дистанция снижение до 1-й скорости"),

        //Параметры настройки датчика тележки
        RegisterInfo(61358, "WR_xTrolley_Limit_En_MW", "WORD16", "Включить ограничения перемещения тележки"),
        RegisterInfo(31358, "RE_Trolley_Limit_Stat_MW", "WORD16", "Статус функции проверки ограничений в наладке"),
        RegisterInfo(63301, "WR_xTrolley_Fwd_Mem_MW", "WORD16", "Запомнить положение тележки при движении вперед"),
        RegisterInfo(63302, "WR_xTrolley_Rev_Mem_MW", "WORD16", "Запомнить положение тележки при движении назад"),

        RegisterInfo(63132, "WR_rTrolley_Min_Position_MW", "WORD16", "Минимальное положение тележки"),
        RegisterInfo(63131, "WR_rTrolley_Max_Position_MW", "WORD16", "Максимальное положение тележки"),
        RegisterInfo(63133, "WR_rTrolley_Pos_Fwd_DwnSp_MW", "WORD16", "положение при котором снижаем скорость двигая тележку вперед"),
        RegisterInfo(63134, "WR_rTrolley_Pos_Rev_DwnSp_MW", "WORD16", "положение при котором снижаем скорость двигая тележку назад"),

        RegisterInfo(61360, "WR_xTrolley_LSP_Auto_MW", "WORD16", "Включить адаптивное снижение скорости"),
        RegisterInfo(61363, "WR_rTrolley_LSP_Dist3_MW", "WORD16", "дистанция снижение до 3-й скорости"),
        RegisterInfo(61362, "WR_rTrolley_LSP_Dist2_MW", "WORD16", "дистанция снижение до 2-й скорости"),
        RegisterInfo(61361, "WR_rTrolley_LSP_Dist1_MW", "WORD16", "дистанция снижение до 1-й скорости"),

        RegisterInfo(33131, "RE_Trolley_Max_Position_MW", "WORD16", "Максимальное положение тележки *0,1м"),
        RegisterInfo(33132, "RE_Trolley_Min_Position_MW", "WORD16", "Минимальное положение тележки *0,1м"),
        RegisterInfo(33133, "RE_Trolley_Pos_Fwd_DwnSp_MW", "WORD16", "положение тележки при котором снижаем скорость двигаю тележку вперед *0,1м"),
        RegisterInfo(33134, "RE_Trolley_Pos_Rev_DwnSp_MW", "WORD16", "положение тележки при котором снижаем скорость двигаю тележку назад"),


        //Параметры настройки датчика хода крана
        RegisterInfo(61420, "WR_Crane_Rails_En_MW", "WORD16", "Активация хода крана. 1 - Ход крана активирован"),
        RegisterInfo(61458, "WR_xCrane_Limit_En_MW", "WORD16", "Включить ограничения перемещения хода крана"),
        RegisterInfo(31458, "RE_Crane_Limit_Stat_MW", "WORD16", "Статус функции проверки ограничений в наладке"),
        RegisterInfo(63145, "WR_rCrane_Pos_CTRL_Point_MW", "WORD16", "Значение передвижения крана в контрольной точке"),
        RegisterInfo(63401, "WR_xCrane_Fwd_Mem_MW", "WORD16", "Запомнить положение крана при движении вправо"),
        RegisterInfo(63402, "WR_xCrane_Rev_Mem_MW", "WORD16", "Запомнить положение крана при движении влево"),
        RegisterInfo(63141, "WR_rCrane_Pos_Fwd_Stop_MW", "WORD16", "Значение при котором останавливаем движение крана вперед"),
        RegisterInfo(63142, "WR_rCrane_Pos_Rev_Stop_MW", "WORD16", "Значение при котором останавливаем движение крана назад"),
        RegisterInfo(63143, "WR_rCrane_Pos_Fwd_DwnSp_MW", "WORD16", "Значение при котором снижаем скорость при движении крана вперед"),
        RegisterInfo(63144, "WR_rCrane_Pos_Rev_DwnSp_MW", "WORD16", "Значение при котором снижаем скорость при движении крана назад"),

        RegisterInfo(33141, "RE_Crane_Pos_Fwd_Stop_MW", "WORD16", "Путь при котором останавливаем движение крана вперед"),
        RegisterInfo(33142, "RE_Crane_Pos_Rev_Stop_MW", "WORD16", "Путь при котором останавливаем движение крана назад"),
        RegisterInfo(33143, "RE_Crane_Pos_Fwd_DwnSp_MW", "WORD16", "Путь при котором снижаем скорость при движении крана вперед"),
        RegisterInfo(33144, "RE_Crane_Pos_Rev_DwnSp_MW", "WORD16", "Путь при котором снижаем скорость при движении крана назад"),
        RegisterInfo(33145, "RE_Crane_Pos_CTRL_Point_MW", "WORD16", "Реперная точка хода крана"),

        //Регистры состояний активности экрана настройки датчиков
        RegisterInfo(63531, "WR_Current_Picture_Setting_MW", "WORD16", "Значение при котором снижаем скорость при движении крана назад"),

        //Регистры состояний. Проверка датчиков во время настройки
        RegisterInfo(33151, "RE_xHoist_Stop_Dwn1_MW", "WORD16", "Проверка датчика подъема. Стоп вниз"),
        RegisterInfo(33152, "RE_xHoist_Stop_Up1_MW", "WORD16", "Проверка датчика подъема. Стоп вверх"),
        RegisterInfo(33153, "RE_xHoist_Dwn_DwnSp1_MW", "WORD16", "Проверка датчика подъема. Снижение 1 ск. вниз"),
        RegisterInfo(33154, "RE_xHoist_Up_DwnSp1_MW", "WORD16", "Проверка датчика подъема. Снижение 1 ск. вверх"),

        RegisterInfo(33155, "RE_xTrolley_Stop_Fwd1_MW", "WORD16", "Проверка датчика тележки. Стоп вперед"),
        RegisterInfo(33155, "RE_xTrolley_Stop_Rev1_MW", "WORD16", "Проверка датчика тележки. Стоп назад"),
        RegisterInfo(33157, "RE_xTrolley_Fwd_DwnSp1_MW", "WORD16", "Проверка датчика тележки. Снижение 1 ск. вперед"),
        RegisterInfo(33158, "RE_xTrolley_Rev_DwnSp1_MW", "WORD16", "Проверка датчика тележки. Снижение 1 ск. назад"),

        RegisterInfo(33161, "RE_xCrane_Stop_Fwd1_MW", "WORD16", "Проверка датчика хода. Стоп вперед"),
        RegisterInfo(33162, "RE_xCrane_Stop_Rev1_MW", "WORD16", "Проверка датчика хода. Стоп назад"),
        RegisterInfo(33163, "RE_xCrane_Fwd_DwnSp1_MW", "WORD16", "Проверка датчика хода. Снижение 1 ск. вперед"),
        RegisterInfo(33164, "RE_xCrane_Rev_DwnSp1_MW", "WORD16", "Проверка датчика хода. Снижение 1 ск. назад"),
        RegisterInfo(33165, "RE_xCrane_Reper_Point_MW", "WORD16", "Реперная точка хода крана"),

        //-----ошибки выхода за рабочий диапазон показаний энкодера
        RegisterInfo(39030, "RE_HMI_HoistAbsEnc_MaxDeltaError_MW", "WORD16", "Превышение максимальной дельты энкодера подъема"),
        RegisterInfo(39031, "RE_HMI_SlewAbsEnc_MaxDeltaError_MW", "WORD16", "Превышение максимальной дельты энкодера поворота"),
        RegisterInfo(39032, "RE_HMI_TrolleyAbsEnc_MaxDeltaError_MW", "WORD16", "Превышение максимальной дельты энкодера тележки"),
        RegisterInfo(39033, "RE_HMI_CraneAbsEnc_MaxDeltaError_MW", "WORD16", "Превышение максимальной дельты энкодера передвижения"),

        RegisterInfo(39034, "RE_HMI_HoistAbsEnc_MaxValueError_MW", "WORD16", "Превышение максимального значения энкодера подъема"),
        RegisterInfo(39035, "RE_HMI_SlewAbsEnc_MaxValueError_MW", "WORD16", "Превышение максимального значения энкодера поворота"),
        RegisterInfo(39036, "RE_HMI_TrolleyAbsEnc_MaxValueError_MW", "WORD16", "Превышение максимального значения энкодера тележки"),
        RegisterInfo(39037, "RE_HMI_CraneAbsEnc_MaxValueError_MW", "WORD16", "Превышение максимального значения энкодера передвижения"),
        //-----ошибка связи энкодеров
        RegisterInfo(30026, "RE_Hoist_AbsEnc_CANopenReady_MW", "WORD16", "Сбой датчика подъема"),
        RegisterInfo(30027, "RE_Slew_AbsEnc_CANopenReady_MW", "WORD16", "Сбой датчика поворота"),
        RegisterInfo(30028, "RE_Trolley_AbsEnc_CANopenReady_MW", "WORD16", "Сбой датчика вылета"),
        RegisterInfo(30029, "RE_Crane_AbsEnc_CANopenReady_MW", "WORD16", "Сбой датчика перемещения крана")

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
        RegisterInfo(33130, "RE_Trolley_Position_MW", "WORD16", "Текущее положение тележки на стреле *0,1м"),
        RegisterInfo(33120, "RE_Rotation_Position_MW", "WORD16", "Текущий угол поворота *0,1м"),
        RegisterInfo(33140, "RE_Crane_Position_MW",  "WORD16",  "Текущее положение крана *0,1м"),

        //Данные состояний настройки полигонов
        RegisterInfo(33900, "RE_Pos_Prot_En_MW", "WORD16", "Активация координатной защиты. 1 - Активна. 0 - Не активна"),
        RegisterInfo(33910, "RE_Mode_Pos_Prot_MW", "WORD16", "Выбор. тип коорд. защиты: 0-защита груза; 1-защита стрелы"),

        RegisterInfo(33911, "RE_xP1_Up_MW", "WORD16", "Разрешение работы сверху полигона 1"),
        RegisterInfo(33912, "RE_xP1_Down_MW", "WORD16", "Разрешение работы снизу полигона 1"),
        RegisterInfo(33913, "RE_P1_Height_MW", "WORD16", "Высота полигона 1"),
        RegisterInfo(33914, "RE_P1_Point_Num_MW", "WORD16", "Кол-во точек полигона 1"),

        RegisterInfo(33921, "RE_xP2_Up_MW", "WORD16", "Разрешение работы сверху полигона 2"),
        RegisterInfo(33922, "RE_xP2_Down_MW", "WORD16", "Разрешение работы снизу полигона 2"),
        RegisterInfo(33923, "RE_P2_Height_MW", "WORD16", "Высота полигона 2"),
        RegisterInfo(33924, "RE_P2_Point_Num_MW", "WORD16", "Кол-во точек полигона 2"),

        RegisterInfo(33931, "RE_xP3_Up_MW", "WORD16", "Разрешение работы сверху полигона 3"),
        RegisterInfo(33932, "RE_xP3_Down_MW", "WORD16", "Разрешение работы снизу полигона 3"),
        RegisterInfo(33933, "RE_P3_Height_MW", "WORD16", "Высота полигона 3"),
        RegisterInfo(33934, "RE_P3_Point_Num_MW", "WORD16", "Кол-во точек полигона 3"),

        RegisterInfo(33941, "RE_xP4_Up_MW", "WORD16", "Разрешение работы сверху полигона 4"),
        RegisterInfo(33942, "RE_xP4_Down_MW", "WORD16", "Разрешение работы снизу полигона 4"),
        RegisterInfo(33943, "RE_P4_Height_MW", "WORD16", "Высота полигона 4"),
        RegisterInfo(33944, "RE_P4_Point_Num_MW", "WORD16", "Кол-во точек полигона 4"),

        RegisterInfo(33951, "RE_P1_Curr_Type_MW", "WORD16", "Тип ввода точек полигона 1"),
        RegisterInfo(33952, "RE_P2_Curr_Type_MW", "WORD16", "Тип ввода точек полигона 2"),
        RegisterInfo(33953, "RE_P3_Curr_Type_MW", "WORD16", "Тип ввода точек полигона 3"),
        RegisterInfo(33954, "RE_P4_Curr_Type_MW", "WORD16", "Тип ввода точек полигона 4"),

        RegisterInfo(33961, "RE_P1_Few_Point_MW", "WORD16", "Статус 1 полигона. 0-ок. 1-Мало точек"),
        RegisterInfo(33962, "RE_P1_Activ_Stat_MW", "WORD16", "Статус 1 полигона. О-не актив. 1 - Актив."),
        RegisterInfo(33963, "RE_P2_Few_Point_MW", "WORD16", "Статус 2 полигона. 0-ок 1-Мало точек."),
        RegisterInfo(33964, "RE_P2_Activ_Stat_MW", "WORD16", "Статус 2 полигона. О-не актив. 1 - Актив."),
        RegisterInfo(33965, "RE_P3_Few_Point_MW", "WORD16", "Статус 3 полигона. 0-ок 1-Мало точек"),
        RegisterInfo(33966, "RE_P3_Activ_Stat_MW", "WORD16", "Статус 3 полигона. О-не актив. 1 - Актив."),
        RegisterInfo(33967, "RE_P4_Few_Point_MW", "WORD16", "Статус 4 полигона. 0-ок 1-Мало точек."),
        RegisterInfo(33968, "RE_P4_Activ_Stat_MW", "WORD16", "Статус 4 полигона. О-не актив. 1 - Актив."),
        RegisterInfo(33960, "RE_Stats_Calibration_MW", "WORD16", "Статус настройки полигонов. 1-Настроен. 0- Нет."),


        //Данные параметров настройки полигонов.
        RegisterInfo(63900, "WR_Pos_Prot_En_MW", "WORD16", "Активировать координатую защиту. (Тумблер по входному биту)"),
        RegisterInfo(63910, "HMI_Write_Mode_Pos_Prot", "WORD16", "Выбор. тип коорд. защиты 0-защита груза; 1-защита стрелы (Тумблер по входному биту)"),

        RegisterInfo(63901, "WR_xP1_En_MW", "WORD16", "Активировать первый полигон"),
        RegisterInfo(63911, "WR_xP1_Up_MW", "WORD16", "Запретить работу сверху полигона 1"),
        RegisterInfo(63912, "WR_xP1_Down_MW", "WORD16", "Запретить работу снизу полигона 1"),
        RegisterInfo(63913, "WR_rP1_Height_MW", "WORD16", "высота полигона 1"),
        RegisterInfo(63914, "WR_iP1_Point_Num_MW", "WORD16", "количество точек полигона 1"),

        RegisterInfo(63902, "WR_xP2_En_MW", "WORD16", "Активировать второй полигон"),
        RegisterInfo(63921, "WR_xP2_Up_MW", "WORD16", "Запретить работу сверху полигона 2"),
        RegisterInfo(63922, "WR_xP2_Down_MW", "WORD16", "Запретить работу снизу полигона 2"),
        RegisterInfo(63923, "WR_rP2_Height_MW", "WORD16", "высота полигона 2"),
        RegisterInfo(63924, "WR_iP2_Point_Num_MW", "WORD16", "количество точек полигона 2"),

        RegisterInfo(63903, "WR_xP3_En_MW", "WORD16", "Активировать третий полигон"),
        RegisterInfo(63931, "WR_xP3_Up_MW", "WORD16", "Запретить работу сверху полигона 3"),
        RegisterInfo(63932, "WR_xP3_Down_MW", "WORD16", "Запретить работу снизу полигона 3"),
        RegisterInfo(63933, "WR_rP3_Height_MW", "WORD16", "высота полигона 3"),
        RegisterInfo(63934, "WR_iP3_Point_Num_MW", "WORD16", "количество точек полигона 3"),

        RegisterInfo(63904, "WR_xP4_En_MW", "WORD16", "Активировать четвертый полигон"),
        RegisterInfo(63941, "WR_xP4_Up_MW", "WORD16", "Запретить работу сверху полигона 4"),
        RegisterInfo(63942, "WR_xP4_Down_MW", "WORD16", "Запретить работу снизу полигона 4"),
        RegisterInfo(63943, "WR_rP4_Height_MW", "WORD16", "высота полигона 4"),
        RegisterInfo(63944, "WR_iP4_Point_Num_MW", "WORD16", "количество точек полигона 4"),

        RegisterInfo(63950, "WR_xP1_Reset_MW", "WORD16", "Сбросить первый полигон"),
        RegisterInfo(63951, "WR_iP1_Point_Cmd_MW", "WORD16", "команды для заполнения точек полигона 1"),
        RegisterInfo(63952, "WR_xP1_Set_Type1_MW", "WORD16", "установка режима задания точек 1 (по координатам)"),
        RegisterInfo(63953, "WR_xP1_Set_Type2_MW", "WORD16", "установка режима задания точек 2 (ручной ввод)"),
        RegisterInfo(63954, "WR_rP1_Type1_Px_MW", "WORD16", "при типе ввода 1 - координата x"),
        RegisterInfo(63955, "WR_rP1_Type1_Py_MW", "WORD16", "при типе ввода 1 - координата y"),
        RegisterInfo(63956, "WR_rP1_Type2_Trolley_Pos_MW", "WORD16", "при типе ввода 2 - позиция телеги"),
        RegisterInfo(63957, "WR_rP1_Type2_Slew_Pos_MW", "WORD16", "при типе ввода 2 - положение стрелы"),
        RegisterInfo(63958, "WR_rP1_Type2_Crane_Pos_MW", "WORD16", "при типе ввода 2 - положение крана"),

        RegisterInfo(63960, "WR_xP2_Reset_MW", "WORD16", "Сбросить второй полигон"),
        RegisterInfo(63961, "WR_iP2_Point_Cmd_MW", "WORD16", "команды для заполнения точек полигона 2"),
        RegisterInfo(63962, "WR_xP2_Set_Type1_MW", "WORD16", "установка режима задания точек 1 (по координатам)"),
        RegisterInfo(63963, "WR_xP2_Set_Type2_MW", "WORD16", "установка режима задания точек 2 (по вылету)"),
        RegisterInfo(63964, "WR_rP2_Type1_Px_MW", "WORD16", "при типе ввода 1 - координата x"),
        RegisterInfo(63965, "WR_rP2_Type1_Py_MW", "WORD16", "при типе ввода 1 - координата y"),
        RegisterInfo(63966, "WR_rP2_Type2_Trolley_Pos_MW", "WORD16", "при типе ввода 2 - позиция телеги"),
        RegisterInfo(63967, "WR_rP2_Type2_Slew_Pos_MW", "WORD16", "при типе ввода 2 - положение стрелы"),
        RegisterInfo(63968, "WR_rP2_Type2_Crane_Pos_MW", "WORD16", "при типе ввода 2 - положение крана"),

        RegisterInfo(63970, "WR_xP3_Reset_MW", "WORD16", "Сбросить третий полигон"),
        RegisterInfo(63971, "WR_iP3_Point_Cmd_MW", "WORD16", "команды для заполнения точек полигона 3"),
        RegisterInfo(63972, "WR_xP3_Set_Type1_MW", "WORD16", "установка режима задания точек 1 (по координатам)"),
        RegisterInfo(63973, "WR_xP3_Set_Type2_MW", "WORD16", "установка режима задания точек 2 (по вылету)"),
        RegisterInfo(63974, "WR_rP3_Type1_Px_MW", "WORD16", "при типе ввода 1 - координата x"),
        RegisterInfo(63975, "WR_rP3_Type1_Py_MW", "WORD16", "при типе ввода 1 - координата y"),
        RegisterInfo(63976, "WR_rP3_Type2_Trolley_Pos_MW", "WORD16", "при типе ввода 2 - позиция телеги"),
        RegisterInfo(63977, "WR_rP3_Type2_Slew_Pos_MW", "WORD16", "при типе ввода 2 - положение стрелы"),
        RegisterInfo(63978, "WR_rP3_Type2_Crane_Pos_MW", "WORD16", "при типе ввода 2 - положение крана"),

        RegisterInfo(63980, "WR_xP4_Reset_MW", "WORD16", "Сбросить четвертый полигон"),
        RegisterInfo(63981, "WR_iP4_Point_Cmd_MW", "WORD16", "Команды для заполнения точек полигона 4"),
        RegisterInfo(63982, "WR_xP4_Set_Type1_MW", "WORD16", "Установка режима задания точек 1 (по координатам)"),
        RegisterInfo(63983, "WR_xP4_Set_Type2_MW", "WORD16", "Установка режима задания точек 2 (по вылету)"),
        RegisterInfo(63984, "WR_rP4_Type1_Px_MW", "WORD16", "При типе ввода 1 - координата X"),
        RegisterInfo(63985, "WR_rP4_Type1_Py_MW", "WORD16", "При типе ввода 1 - координата Y"),
        RegisterInfo(63986, "WR_rP4_Type2_Trolley_Pos_MW", "WORD16", "При типе ввода 2 - позиция тележки"),
        RegisterInfo(63987, "WR_rP4_Type2_Slew_Pos_MW", "WORD16", "При типе ввода 2 - положение стрелы"),
        RegisterInfo(63988, "WR_rP4_Type2_Crane_Pos_MW", "WORD16", "При типе ввода 2 - положение крана"),

        //Задание точек полигонов с HMI. Полигон №1
        RegisterInfo(36100, "RE_Points_P1_00_Config_MW", "WORD16", "Точка 1X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36101, "RE_Points_P1_01_Config_MW", "WORD16", "Точка 1Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36102, "RE_Points_P1_10_Config_MW", "WORD16", "Точка 2X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36103, "RE_Points_P1_11_Config_MW", "WORD16", "Точка 2Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36104, "RE_Points_P1_20_Config_MW", "WORD16", "Точка 3X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36105, "RE_Points_P1_21_Config_MW", "WORD16", "Точка 3Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36106, "RE_Points_P1_30_Config_MW", "WORD16", "Точка 4X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36107, "RE_Points_P1_31_Config_MW", "WORD16", "Точка 4Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36108, "RE_Points_P1_40_Config_MW", "WORD16", "Точка 5X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36109, "RE_Points_P1_41_Config_MW", "WORD16", "Точка 5Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36110, "RE_Points_P1_50_Config_MW", "WORD16", "Точка 6X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36111, "RE_Points_P1_51_Config_MW", "WORD16", "Точка 6Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36112, "RE_Points_P1_60_Config_MW", "WORD16", "Точка 7X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36113, "RE_Points_P1_61_Config_MW", "WORD16", "Точка 7Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36114, "RE_Points_P1_70_Config_MW", "WORD16", "Точка 8X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36115, "RE_Points_P1_71_Config_MW", "WORD16", "Точка 8Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36116, "RE_Points_P1_80_Config_MW", "WORD16", "Точка 9X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36117, "RE_Points_P1_81_Config_MW", "WORD16", "Точка 9Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36118, "RE_Points_P1_90_Config_MW", "WORD16", "Точка 10X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36119, "RE_Points_P1_91_Config_MW", "WORD16", "Точка 10Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36120, "RE_Points_P1_10_0_Config_MW", "WORD16", "Точка 11X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36121, "RE_Points_P1_10_1_Config_MW", "WORD16", "Точка 11Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36122, "RE_Points_P1_11_0_Config_MW", "WORD16", "Точка 12X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36123, "RE_Points_P1_11_1_Config_MW", "WORD16", "Точка 12Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36124, "RE_Points_P1_12_0_Config_MW", "WORD16", "Точка 13X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36125, "RE_Points_P1_12_1_Config_MW", "WORD16", "Точка 13Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36126, "RE_Points_P1_13_0_Config_MW", "WORD16", "Точка 14X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36127, "RE_Points_P1_13_1_Config_MW", "WORD16", "Точка 14Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36128, "RE_Points_P1_14_0_Config_MW", "WORD16", "Точка 15X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36129, "RE_Points_P1_14_1_Config_MW", "WORD16", "Точка 15Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36130, "RE_Points_P1_15_0_Config_MW", "WORD16", "Точка 16X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36131, "RE_Points_P1_15_1_Config_MW", "WORD16", "Точка 16Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36132, "RE_Points_P1_16_0_Config_MW", "WORD16", "Точка 17X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36133, "RE_Points_P1_16_1_Config_MW", "WORD16", "Точка 17Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36134, "RE_Points_P1_17_0_Config_MW", "WORD16", "Точка 18X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36135, "RE_Points_P1_17_1_Config_MW", "WORD16", "Точка 18Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36136, "RE_Points_P1_18_0_Config_MW", "WORD16", "Точка 19X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36137, "RE_Points_P1_18_1_Config_MW", "WORD16", "Точка 19Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36138, "RE_Points_P1_19_0_Config_MW", "WORD16", "Точка 20X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36139, "RE_Points_P1_19_1_Config_MW", "WORD16", "Точка 20Y многоугольника внутри блока конфигурации полигона 1"),
        //Задание точек полигонов с HMI. Полигон №2
        RegisterInfo(36200, "RE_Points_P2_00_Config_MW", "WORD16", "Точка 1X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36201, "RE_Points_P2_01_Config_MW", "WORD16", "Точка 1Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36202, "RE_Points_P2_10_Config_MW", "WORD16", "Точка 2X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36203, "RE_Points_P2_11_Config_MW", "WORD16", "Точка 2Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36204, "RE_Points_P2_20_Config_MW", "WORD16", "Точка 3X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36205, "RE_Points_P2_21_Config_MW", "WORD16", "Точка 3Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36206, "RE_Points_P2_30_Config_MW", "WORD16", "Точка 4X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36207, "RE_Points_P2_31_Config_MW", "WORD16", "Точка 4Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36208, "RE_Points_P2_40_Config_MW", "WORD16", "Точка 5X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36209, "RE_Points_P2_41_Config_MW", "WORD16", "Точка 5Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36210, "RE_Points_P2_50_Config_MW", "WORD16", "Точка 6X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36211, "RE_Points_P2_51_Config_MW", "WORD16", "Точка 6Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36212, "RE_Points_P2_60_Config_MW", "WORD16", "Точка 7X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36213, "RE_Points_P2_61_Config_MW", "WORD16", "Точка 7Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36214, "RE_Points_P2_70_Config_MW", "WORD16", "Точка 8X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36215, "RE_Points_P2_71_Config_MW", "WORD16", "Точка 8Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36216, "RE_Points_P2_80_Config_MW", "WORD16", "Точка 9X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36217, "RE_Points_P2_81_Config_MW", "WORD16", "Точка 9Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36218, "RE_Points_P2_90_Config_MW", "WORD16", "Точка 10X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36219, "RE_Points_P2_91_Config_MW", "WORD16", "Точка 10Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36220, "RE_Points_P2_10_0_Config_MW", "WORD16", "Точка 11X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36221, "RE_Points_P2_10_1_Config_MW", "WORD16", "Точка 11Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36222, "RE_Points_P2_11_0_Config_MW", "WORD16", "Точка 12X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36223, "RE_Points_P2_11_1_Config_MW", "WORD16", "Точка 12Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36224, "RE_Points_P2_12_0_Config_MW", "WORD16", "Точка 13X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36225, "RE_Points_P2_12_1_Config_MW", "WORD16", "Точка 13Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36226, "RE_Points_P2_13_0_Config_MW", "WORD16", "Точка 14X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36227, "RE_Points_P2_13_1_Config_MW", "WORD16", "Точка 14Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36228, "RE_Points_P2_14_0_Config_MW", "WORD16", "Точка 15X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36229, "RE_Points_P2_14_1_Config_MW", "WORD16", "Точка 15Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36230, "RE_Points_P2_15_0_Config_MW", "WORD16", "Точка 16X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36231, "RE_Points_P2_15_1_Config_MW", "WORD16", "Точка 16Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36232, "RE_Points_P2_16_0_Config_MW", "WORD16", "Точка 17X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36233, "RE_Points_P2_16_1_Config_MW", "WORD16", "Точка 17Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36234, "RE_Points_P2_17_0_Config_MW", "WORD16", "Точка 18X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36235, "RE_Points_P2_17_1_Config_MW", "WORD16", "Точка 18Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36236, "RE_Points_P2_18_0_Config_MW", "WORD16", "Точка 19X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36237, "RE_Points_P2_18_1_Config_MW", "WORD16", "Точка 19Y многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36238, "RE_Points_P2_19_0_Config_MW", "WORD16", "Точка 20X многоугольника внутри блока конфигурации полигона 2"),
        RegisterInfo(36239, "RE_Points_P2_19_1_Config_MW", "WORD16", "Точка 20Y многоугольника внутри блока конфигурации полигона 2"),

        //Задание точек полигонов с HMI. Полигон №3
        RegisterInfo(36300, "RE_Points_P3_00_Config_MW", "WORD16", "Точка 1X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36301, "RE_Points_P3_01_Config_MW", "WORD16", "Точка 1Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36302, "RE_Points_P3_10_Config_MW", "WORD16", "Точка 2X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36303, "RE_Points_P3_11_Config_MW", "WORD16", "Точка 2Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36304, "RE_Points_P3_20_Config_MW", "WORD16", "Точка 3X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36305, "RE_Points_P3_21_Config_MW", "WORD16", "Точка 3Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36306, "RE_Points_P3_30_Config_MW", "WORD16", "Точка 4X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36307, "RE_Points_P3_31_Config_MW", "WORD16", "Точка 4Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36308, "RE_Points_P3_40_Config_MW", "WORD16", "Точка 5X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36309, "RE_Points_P3_41_Config_MW", "WORD16", "Точка 5Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36310, "RE_Points_P3_50_Config_MW", "WORD16", "Точка 6X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36311, "RE_Points_P3_51_Config_MW", "WORD16", "Точка 6Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36312, "RE_Points_P3_60_Config_MW", "WORD16", "Точка 7X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36313, "RE_Points_P3_61_Config_MW", "WORD16", "Точка 7Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36314, "RE_Points_P3_70_Config_MW", "WORD16", "Точка 8X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36315, "RE_Points_P3_71_Config_MW", "WORD16", "Точка 8Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36316, "RE_Points_P3_80_Config_MW", "WORD16", "Точка 9X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36317, "RE_Points_P3_81_Config_MW", "WORD16", "Точка 9Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36318, "RE_Points_P3_90_Config_MW", "WORD16", "Точка 10X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36319, "RE_Points_P3_91_Config_MW", "WORD16", "Точка 10Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36320, "RE_Points_P3_10_0_Config_MW", "WORD16", "Точка 11X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36321, "RE_Points_P3_10_1_Config_MW", "WORD16", "Точка 11Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36322, "RE_Points_P3_11_0_Config_MW", "WORD16", "Точка 12X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36323, "RE_Points_P3_11_1_Config_MW", "WORD16", "Точка 12Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36324, "RE_Points_P3_12_0_Config_MW", "WORD16", "Точка 13X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36325, "RE_Points_P3_12_1_Config_MW", "WORD16", "Точка 13Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36326, "RE_Points_P3_13_0_Config_MW", "WORD16", "Точка 14X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36327, "RE_Points_P3_13_1_Config_MW", "WORD16", "Точка 14Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36328, "RE_Points_P3_14_0_Config_MW", "WORD16", "Точка 15X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36329, "RE_Points_P3_14_1_Config_MW", "WORD16", "Точка 15Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36330, "RE_Points_P3_15_0_Config_MW", "WORD16", "Точка 16X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36331, "RE_Points_P3_15_1_Config_MW", "WORD16", "Точка 16Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36332, "RE_Points_P3_16_0_Config_MW", "WORD16", "Точка 17X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36333, "RE_Points_P3_16_1_Config_MW", "WORD16", "Точка 17Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36334, "RE_Points_P3_17_0_Config_MW", "WORD16", "Точка 18X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36335, "RE_Points_P3_17_1_Config_MW", "WORD16", "Точка 18Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36336, "RE_Points_P3_18_0_Config_MW", "WORD16", "Точка 19X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36337, "RE_Points_P3_18_1_Config_MW", "WORD16", "Точка 19Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36338, "RE_Points_P3_19_0_Config_MW", "WORD16", "Точка 20X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36339, "RE_Points_P3_19_1_Config_MW", "WORD16", "Точка 20Y многоугольника внутри блока конфигурации полигона 1"),

        //Задание точек полигонов с HMI. Полигон №4
        RegisterInfo(36400, "RE_Points_P4_00_Config_MW", "WORD16", "Точка 1X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36401, "RE_Points_P4_01_Config_MW", "WORD16", "Точка 1Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36402, "RE_Points_P4_10_Config_MW", "WORD16", "Точка 2X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36403, "RE_Points_P4_11_Config_MW", "WORD16", "Точка 2Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36404, "RE_Points_P4_20_Config_MW", "WORD16", "Точка 3X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36405, "RE_Points_P4_21_Config_MW", "WORD16", "Точка 3Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36406, "RE_Points_P4_30_Config_MW", "WORD16", "Точка 4X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36407, "RE_Points_P4_31_Config_MW", "WORD16", "Точка 4Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36408, "RE_Points_P4_40_Config_MW", "WORD16", "Точка 5X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36409, "RE_Points_P4_41_Config_MW", "WORD16", "Точка 5Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36410, "RE_Points_P4_50_Config_MW", "WORD16", "Точка 6X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36411, "RE_Points_P4_51_Config_MW", "WORD16", "Точка 6Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36412, "RE_Points_P4_60_Config_MW", "WORD16", "Точка 7X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36413, "RE_Points_P4_61_Config_MW", "WORD16", "Точка 7Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36414, "RE_Points_P4_70_Config_MW", "WORD16", "Точка 8X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36415, "RE_Points_P4_71_Config_MW", "WORD16", "Точка 8Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36416, "RE_Points_P4_80_Config_MW", "WORD16", "Точка 9X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36417, "RE_Points_P4_81_Config_MW", "WORD16", "Точка 9Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36418, "RE_Points_P4_90_Config_MW", "WORD16", "Точка 10X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36419, "RE_Points_P4_91_Config_MW", "WORD16", "Точка 10Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36420, "RE_Points_P4_10_0_Config_MW", "WORD16", "Точка 11X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36421, "RE_Points_P4_10_1_Config_MW", "WORD16", "Точка 11Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36422, "RE_Points_P4_11_0_Config_MW", "WORD16", "Точка 12X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36423, "RE_Points_P4_11_1_Config_MW", "WORD16", "Точка 12Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36424, "RE_Points_P4_12_0_Config_MW", "WORD16", "Точка 13X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36425, "RE_Points_P4_12_1_Config_MW", "WORD16", "Точка 13Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36426, "RE_Points_P4_13_0_Config_MW", "WORD16", "Точка 14X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36427, "RE_Points_P4_13_1_Config_MW", "WORD16", "Точка 14Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36428, "RE_Points_P4_14_0_Config_MW", "WORD16", "Точка 15X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36429, "RE_Points_P4_14_1_Config_MW", "WORD16", "Точка 15Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36430, "RE_Points_P4_15_0_Config_MW", "WORD16", "Точка 16X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36431, "RE_Points_P4_15_1_Config_MW", "WORD16", "Точка 16Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36432, "RE_Points_P4_16_0_Config_MW", "WORD16", "Точка 17X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36433, "RE_Points_P4_16_1_Config_MW", "WORD16", "Точка 17Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36434, "RE_Points_P4_17_0_Config_MW", "WORD16", "Точка 18X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36435, "RE_Points_P4_17_1_Config_MW", "WORD16", "Точка 18Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36436, "RE_Points_P4_18_0_Config_MW", "WORD16", "Точка 19X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36437, "RE_Points_P4_18_1_Config_MW", "WORD16", "Точка 19Y многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36438, "RE_Points_P4_19_0_Config_MW", "WORD16", "Точка 20X многоугольника внутри блока конфигурации полигона 1"),
        RegisterInfo(36439, "RE_Points_P4_19_1_Config_MW", "WORD16", "Точка 20Y многоугольника внутри блока конфигурации полигона 1"),

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
        RegisterInfo(61269, "WR_Slew_SpeadBrakeClose", "WORD16", "Допустимая скорость ручного наложения тормоза об/мин"),
        RegisterInfo(61266, "WR_Slew_wDecRevers_MW", "WORD16", "Время торможения поворота противоходом"),
        RegisterInfo(61267, "WR_Slew_wDelay_Time_MW", "WORD16", "Задержка наложения тормоза"),
        RegisterInfo(61268, "WR_Slew_wBrake_Close_Frq", "WORD16", "Частота удержания"),

        RegisterInfo(61270, "WR_Slew_DelayTimeBrakeCurr", "WORD16", "Задержка до начала торможения постоянным током"),
        RegisterInfo(61271, "WR_Slew_TimeBrakeCurrent", "WORD16", "Время торможения постонным током"),
        RegisterInfo(61272, "WR_Slew_FrqStartBraking", "WORD16", "Частота начала торможения"),
        RegisterInfo(61273, "WR_Slew_DelayStartBraking", "WORD16", "Задержка времени при достижении частоты начала торможения"),

        RegisterInfo(31269, "RE_iSlew_SpeadBrakeClose", "WORD16", "Допустимая скорость ручного наложения тормоза об/мин"),
        RegisterInfo(31266, "RE_Slew_Dec_Revers", "WORD16", "Время торможения противовключением"),
        RegisterInfo(31267, "RE_Slew_DeleyTimeBrake", "WORD16", "Время задержки наложения тормоза"),
        RegisterInfo(31268, "RE_iSlew_BrakeCloseFrq", "WORD16", "Частота удержания"),

        RegisterInfo(31270, "RE_Slew_DelayTimeBrakeCurr", "WORD16", "Задержка до начала торможения постоянным током"),
        RegisterInfo(31271, "RE_Slew_TimeBrakeCurrent", "WORD16", "Время торможения постонным током"),
        RegisterInfo(31272, "RE_Slew_FrqStartBraking", "WORD16", "Частота начала торможения"),
        RegisterInfo(31273, "RE_Slew_DelayStartBraking", "WORD16", "Задержка времени при достижении частоты начала торможения"),
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
        RegisterInfo(61100, "WR_xFltRst_MW", "WORD16", "Сброс ошибки привода"),
        RegisterInfo(60161, "WR_ACSS_Reset_MW", "WORD16", "Сброс ошибки АЦСС"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА ВЕТРА
        RegisterInfo(63011, "WR_rWindSpeedLvl1_MW", "WORD16", "Скорость ветра для порога №1, м/с, включение желтой индикации"),
        RegisterInfo(63012, "WR_rWindSpeedLvl2_MW", "WORD16", "Скорость ветра для порога №2, м/с, включение красной индикации"),
        RegisterInfo(63013, "WR_rWindSpeedMontageLvl_MW", "WORD16", "Скорость ветра для порога №2, м/с, включение красной индикации/Монтажный режим"),
        RegisterInfo(63014, "WR_iAnemTimeSmooth_MW", "WORD16", "Время усреднения показаний анемометра, 5 сек"),
        RegisterInfo(33010, "RE_Wind_MW", "WORD16", "Текущие показания ветра *0,1м/с"),
        RegisterInfo(30151, "RE_CAN_AdvAnemometer_MW", "WORD16", "Анемометр"),
        RegisterInfo(33209, "RE_Anemometer_Status_MW", "WORD16", "Статус анемометра"),

        RegisterInfo(63016, "WR_Anem_Min_Wind_MW", "WORD16", "Нижний предел измерения анемометра, м/с"),
        RegisterInfo(63017, "WR_Anem_Max_Wind_MW", "WORD16", "Верхний предел измерения анемометра, м/с"),
        RegisterInfo(63018, "WR_Anem_Min_Amper_MW", "WORD16", "Нижний предел показаний анемометра, mA"),
        RegisterInfo(63019, "WR_Anem_Max_Amper_MW", "WORD16", "Верхний предел показаний анемометра, mA"),
        RegisterInfo(63010, "WR_xAnemomClbr_MW", "WORD16", "Команда калибровать анемометр"),

        RegisterInfo(33011, "RE_WindSpeedLvl1_MW", "WORD16", "Величина скорости ветра для порога №1, м/с, включение желтой индикации"),
        RegisterInfo(33012, "RE_WindSpeedLvl2_MW", "WORD16", "Величина скорости ветра для порогоа №2, м/с, включение красной индикации"),
        RegisterInfo(33013, "RE_WindSpeedMontageLvl_MW", "WORD16", "Велична скорости ветра для порогоа №2, м/с, включение красной индикации/ Монтажный режим"),
        RegisterInfo(33014, "RE_iAnemTimeSmooth_MW", "WORD16", "Время усреднения показаний анемометра. 5 сек."),

        RegisterInfo(33016, "RE_pv_rAnem_Min_Wind_MW", "WORD16", "Нижний предел измерения анемометра, м/с"),
        RegisterInfo(33017, "RE_pv_rAnem_Max_Wind_MW", "WORD16", "Верхний предел измерения анемометра, м/с"),
        RegisterInfo(33018, "RE_pv_rAnem_Min_Amper_MW", "WORD16", "Нижний предел показаний анемометра, mA"),
        RegisterInfo(33019, "RE_pv_rAnem_Max_Amper_MW", "WORD16", "Верхний предел показаний анемометра, mA"),


        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКОВ ПОЛОЖЕНИЯ
        RegisterInfo(33171, "RE_Slew_Number_Teeth_OPU_MW", "WORD16", "Число зубьев ОПУ крана"),
        RegisterInfo(33172, "RE_Slew_Number_Teeth_Gear_Sensor_MW", "WORD16", "Число зубьев шестерни датчика поворота крана"),
        RegisterInfo(33173, "RE_Slew_Single_Turn_Steps_Sensor_MW", "WORD16", "Количество импульсов за оборот датчика"),
        RegisterInfo(33174, "RE_Slew_Ratio_MW", "WORD16", "Коэффициент передачи ОПУ и шестерни датчика"),

        RegisterInfo(63076, "WR_xAbsEncDeltaControlEn_MW", "WORD16", "Включить контроль максимального рассогласования абсолютных энкодеров"),
        RegisterInfo(63077, "WR_xDebug_Mode_Hoist_MW", "WORD16", "Флаг установки режима наладки механизма подъема при выходе датчика из строя"),
        RegisterInfo(63078, "WR_xDebug_Mode_Slew_MW", "WORD16", "Флаг установки режима наладки механизма поворота при выходе датчика из строя"),

        RegisterInfo(63061, "WR_udiHoistAbsEncMaxDeltaEn_MD", "WORD32", "Максимальное рассогласование абс. энкодера подъема"),
        RegisterInfo(63062, "WR_udiSlewAbsEncMaxDeltaEn_MD", "WORD32", "Максимальное рассогласование абс. энкодера поворота"),
        RegisterInfo(63063, "WR_udiTrolleyAbsEncMaxDeltaEn_MD", "WORD32", "Максимальное рассогласование абс. энкодера тележки"),
        RegisterInfo(63064, "WR_udiCraneAbsEncMaxDeltaEn_MD", "WORD32", "Максимальное рассогласование абс. энкодера хода крана"),

        RegisterInfo(33076, "RE_xAbsEncDeltaControlEn_MW", "WORD16", "Флаг контроль максимального рассогласования абсолютных энкодеров"),
        RegisterInfo(33077, "RE_xDebug_Mode_Hoist_MW", "WORD16", "Флаг установки режима наладки механизма подъема при выходе датчика из строя"),
        RegisterInfo(33078, "RE_xDebug_Mode_Slew_MW", "WORD16", "Флаг установки режима наладки механизма поворота при выходе датчика из строя"),

        RegisterInfo(33171, "RE_Slew_Number_Teeth_OPU_MW", "WORD16", "Число зубьев ОПУ крана"),
        RegisterInfo(33172, "RE_Slew_Number_Teeth_Gear_Sensor_MW", "WORD16", "Число зубьев шестерни датчика поворота крана"),
        RegisterInfo(33173, "RE_Slew_Single_Turn_Steps_Sensor_MW", "WORD16", "Количество импульсов за оборот датчика"),
        RegisterInfo(33174, "RE_Slew_Ratio_MW", "WORD16", "Коэффициент передачи ОПУ и шестерни датчика"),

        RegisterInfo(33061, "RE_udiHoistAbsEncMaxDeltaEn_MD", "WORD16", "Максимальное рассогласование абс. энкодера подъема"),
        RegisterInfo(33062, "RE_udiSlewAbsEncMaxDeltaEn_MD", "WORD16", "Максимальное рассогласование абс. энкодера поворота"),
        RegisterInfo(33063, "RE_udiTrolleyAbsEncMaxDeltaEn_MD", "WORD16", "Максимальное рассогласование абс. энкодера тележки"),
        RegisterInfo(33064, "RE_udiCraneAbsEncMaxDeltaEn_MD", "WORD16", "Максимальное рассогласование абс. энкодера хода крана"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА УСИЛИЯ
        RegisterInfo(63021, "WR_iAdvLoadCell_LL_MW", "WORD16", "Минимальное значение измерения веса"),
        RegisterInfo(63022, "WR_iAdvLoadCell_HH_MW", "WORD16", "Максимальное значение измерения веса"),
        RegisterInfo(63023, "WR_iArray_Size_WeightSmooth_MW", "WORD16", "Глубина сглаживания измерения веса"),
        RegisterInfo(63024, "WR_rWeight_SP_Over_Speed_MW", "WORD16", "Уставка массы для разрешения повышенной скорости"),

        RegisterInfo(33021, "RE_iAdvLoadCell_LL_MW", "WORD16", "Минимальное значение измерения"),
        RegisterInfo(33022, "RE_iAdvLoadCell_HH_MW", "WORD16", "Максимальное значение измерения"),
        RegisterInfo(33023, "RE_iArray_Size_WeightSmooth_MW", "WORD16", "Глубина сглаживания измерения веса"),
        RegisterInfo(33024, "RE_rWeight_SP_Over_Speed_MW", "WORD16", "Уставка массы, ниже которой будет разрешена повышенная скорость"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА РЕПЕРНОЙ ТОЧКИ ХОДА КРАНА
        RegisterInfo(63147, "WR_xCTRL_Point_EN_MW", "WORD16", "Включение, отключение датчика реперной точки хода крана"),
        RegisterInfo(63148, "WR_rCTRL_Point_Control_Interval_MW", "WORD16", "Значение интервала контроля реперной точки"),
        RegisterInfo(33147, "RE_Crane_CTRL_Point_EN_MW", "WORD16", "Флаг включения, отключения датчика реперной точки хода крана"),
        RegisterInfo(33148, "RE_CTRL_Point_Control_Interval_MW", "WORD16", "Интервал контроля реперной точки"),

        //СЕРВИСНЫЙ РЕЖИМ. СИСТЕМНАЯ НАСТРОЙКА УРОВЕНЬ ИНЖЕНЕР
        RegisterInfo(63070, "WR_xDataLogErrStopHoist_MW", "WORD16", "Запрет работы подъема на скоростях выше 2-й? при ошибках записи регистратора параметров"),
        RegisterInfo(63071, "WR_xPasswordOrNaladka_MW", "WORD16", "Режим авторизации [naladka]. (По умолчанию цифровой пароль)"),
        RegisterInfo(63072, "WR_xFOURpoligonsEn_MW", "WORD16", "Режим работы 4 полигона, 20 точек. (По умолчанию 2 пол. 10 точек)"),
        RegisterInfo(63073, "WR_xWithoutRringCurrentCollector_MW", "WORD16", "Исполнение крана без токосъемника. (По умолчанию с токосъемником)"),

        RegisterInfo(63074, "WR_xZoneACC_DEC_Activ_MW", "WORD16", "Активация плавной регулировки разгона/торможения"),
        RegisterInfo(61164, "WR_ZoneAcc_Hoist_MW", "WORD16", "Зона регулирования ускорения подъема"),
        RegisterInfo(61165, "WR_ZoneDec_Hoist_MW", "WORD16", "Зона регулирования замедления подъема"),
        RegisterInfo(61264, "WR_ZoneAcc_Slew_MW", "WORD16", "Зона регулирования ускорения поворота"),
        RegisterInfo(61264, "WR_ZoneDec_Slew_MW", "WORD16", "Зона регулирования замедления поворота"),
        RegisterInfo(61364, "WR_ZoneAcc_Trolley_MW", "WORD16", "Зона регулирования ускорения тележки"),
        RegisterInfo(61365, "WR_ZoneDec_Trolley_MW", "WORD16", "Зона регулирования замедления тележки"),
        RegisterInfo(61464, "WR_ZoneAcc_Crane_MW", "WORD16", "Зона регулирования ускорения крана"),
        RegisterInfo(61465, "WR_ZoneDec_Crane_MW", "WORD16", "Зона регулирования замедления крана"),

        RegisterInfo(63075, "WR_LukCoplekte_MW", "WORD16", "Выбор исполнения крана с люком и без//TRUE-Кран с люком опу, FALSE без люка ОПУ"),

        RegisterInfo(33070, "RE_xDataLogErrStopHoist_MW", "WORD16", "Флаг. Запрет высоких скоростей при ошибке регистратора"),
        RegisterInfo(33071, "RE_xPasswordOrNaladka_MW", "WORD16", "Флаг.Режим авторизации наладки"),
        RegisterInfo(33072, "RE_xFOURpoligonsEn_MW", "WORD16", "Флаг.Режим работы полигонов (4x20 или 2x10 точек)"),
        RegisterInfo(33073, "RE_xWithoutRringCurrentCollector_MW", "WORD16", "Флаг.Выбор исполнения крана без кольцеового токосъемника, режим ограничения поворота"),
        RegisterInfo(33074, "RE_xZoneACC_DEC_Activ_MW", "WORD16", "Флаг. Активация зон регулирования ускорений/замедлений"),
        RegisterInfo(33075, "RE_LukComplekte_MW", "WORD16", "Флаг. Включения контроля люка в ОПУ. TRUE-Кран с люком опу, FALSE без люка ОПУ"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ПРИВОДА ПОДЪЕМА
        RegisterInfo(61150, "WR_Hoist_wSpdRefButton_Value_MW", "WORD16", "Скорость при нажатии кнопки"),
        RegisterInfo(61151, "WR_Hoist_wSpdRef1_Value_MW", "WORD16", "Скорость 1 подъема"),
        RegisterInfo(61152, "WR_Hoist_wSpdRef2_Value_MW", "WORD16", "Скорость 2 подъема"),
        RegisterInfo(61153, "WR_Hoist_wSpdRef3_Value_MW", "WORD16", "Скорость 3 подъема"),
        RegisterInfo(61154, "WR_Hoist_wSpdRef4_Value_MW", "WORD16", "Скорость 4 подъема"),
        RegisterInfo(61159, "WR_Hoist_iSpdRef_MAX_Value_MW", "WORD16", "Значение максимальной скорости двигателя подъема"),
        RegisterInfo(61156, "WR_Hoist_rHoist_Acc_MW", "WORD16", "Время разгона подъема, сек"),
        RegisterInfo(61157, "WR_Hoist_rHoist_Dec_MW", "WORD16", "Время торможения подъема, сек"),
        RegisterInfo(61155, "WR_Hoist_wSpdNom_MW", "WORD16", "Номинальня скорость подъема"),

        RegisterInfo(61166, "WR_Hoist_wSpdRef1Strela_MW", "WORD16", "Скорость подъема 1 в режиме монтаж"),
        RegisterInfo(61167, "WR_Hoist_wSpdRef2Strela_MW", "WORD16", "Скорость подъема 2 в режиме монтаж"),
        RegisterInfo(61168, "WR_Hoist_rStrela_Acc_MW", "WORD16", "Время разгона в режиме монтаж, сек"),
        RegisterInfo(61169, "WR_Hoist_rStrela_Dec_MW", "WORD16", "Время торможения в режиме монтаж, сек"),

        RegisterInfo(61170, "WR_Hoist_BrakeControl_Stat", "WORD16", "Вкл. Выкл. отслеживаения состояния тормоза 1- отслеживаем обраную связь, 0 -нет"),
        RegisterInfo(61171, "WR_Hoist_BrakeControl_Timeout", "WORD16", "Значение времени ожидания обратной связи от тормоза подъема, сек"),

        RegisterInfo(31150, "RE_wSpdRefButton_Hoist", "WORD16", "Пониженая скорость подъема"),
        RegisterInfo(31151, "RE_wSpdRef1_Hoist", "WORD16", "1-ая скорость подъема об/мин"),
        RegisterInfo(31152, "RE_wSpdRef2_Hoist", "WORD16", "2-ая скорость подъема об/мин"),
        RegisterInfo(31153, "RE_wSpdRef3_Hoist", "WORD16", "3-я скорость подъема об/мин"),
        RegisterInfo(31154, "RE_wSpdRef4_Hoist", "WORD16", "4-ая скорость подъема об/мин"),
        RegisterInfo(31159, "RE_iSpdRef_MAX_Value", "WORD16", "Сверхсинхронная скорость об/мин"),
        RegisterInfo(31155, "RE_wSpdNom_Hoist", "WORD16", "Номинальная скорость ЭД подъема об/мин"),
        RegisterInfo(31156, "RE_rHoist_Acc", "WORD16", "Время разгона, сек"),
        RegisterInfo(31157, "RE_rHoist_Dec", "WORD16", "Время торможения, сек"),

        RegisterInfo(31166, "RE_wSpdRef1Strela_Hoist", "WORD16", "1-ая скорость с монтажного пульта"),
        RegisterInfo(31167, "RE_wSpdRef2Strela_Hoist", "WORD16", "2-ая скорость с монтажного пульта"),
        RegisterInfo(31168, "RE_rStrela_Acc_Hoist", "WORD16", "Ускорение с монтажного пульта, сек"),
        RegisterInfo(31169, "RE_rStrela_Dec_Hoist", "WORD16", "Замедление с монтажного пульта, сек"),

        RegisterInfo(31158, "RE_Hoist_Limit_Stat_MW", "WORD16", "Статус функции проверки ограничений в наладке"),
        RegisterInfo(31160, "RE_LspAuto_Stat_Hoist_MW", "WORD16", "Статус автоматического снижения скорости"),
        RegisterInfo(31163, "RE_LspDist3_Hoist_MW", "WORD16", "Дистанция снижения до 3 скорости, м"),
        RegisterInfo(31162, "RE_LspDist2_Hoist_MW", "WORD16", "Дистанция снижения до 2 скорости, м"),
        RegisterInfo(31161, "RE_LspDist1_Hoist_MW", "WORD16", "Дистанция снижения до 1 скорости, м"),
        RegisterInfo(31164, "RE_ZoneAcc_Hoist_MW", "WORD16", "Зона регулирования ускорения подъема"),
        RegisterInfo(31165, "RE_ZoneDec_Hoist_MW", "WORD16", "Зона регулирования замедления подъема"),

        RegisterInfo(31170, "RE_Hoist_BrakeControl_Stat", "WORD16", "Бит отслеживаения состояния тормоза 1- отслеживаем обраную связь, 0 -нет"),
        RegisterInfo(31171, "RE_Hoist_BrakeControl_Timeout", "WORD16", "Время ожидания обратной связи от тормоза"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ПРИВОДА ПОВОРОТА
        RegisterInfo(61250, "WR_Slew_wDrvSpdLSP_MW", "WORD16", "Снижение скорости поворота"),
        RegisterInfo(61251, "WR_Slew_wSpdRef1_Value_MW", "WORD16", "Скорость 1 поворота"),
        RegisterInfo(61252, "WR_Slew_wSpdRef2_Value_MW", "WORD16", "Скорость 2 поворота"),
        RegisterInfo(61253, "WR_Slew_wSpdRef3_Value_MW", "WORD16", "Скорость 3 поворота"),
        RegisterInfo(61254, "WR_Slew_wSpdRef4_Value_MW", "WORD16", "Скорость 4 поворота"),
        RegisterInfo(61256, "WR_Slew_wACCnom_MW", "WORD16", "Время разгона поворота"),
        RegisterInfo(61257, "WR_Slew_wDECnom_MW", "WORD16", "Время торможения поворота"),
        RegisterInfo(61255, "WR_Slew_wSpdNom_MW", "WORD16", "Номинальня скорость поворота"),

        RegisterInfo(31250, "RE_wSpdRefLou_Slew", "WORD16", "Пониженая скорость поворота"),
        RegisterInfo(31251, "RE_wSpdRef1_Slew", "WORD16", "1-ая скорость поворота об/мин"),
        RegisterInfo(31252, "RE_wSpdRef2_Slew", "WORD16", "2-ая скорость поворота об/мин"),
        RegisterInfo(31253, "RE_wSpdRef3_Slew", "WORD16", "3-я скорость поворота об/мин"),
        RegisterInfo(31254, "RE_wSpdRef4_Slew", "WORD16", "4-ая скорость поворота об/мин"),
        RegisterInfo(31255, "RE_wSpdNom_Slew", "WORD16", "Номинальная скорость ЭД поворота об/мин"),
        RegisterInfo(31256, "RE_iSlew_Acc", "WORD16", "Время разгона, сек"),
        RegisterInfo(31257, "RE_iSlew_Dec", "WORD16", "Время торможения, сек"),

        RegisterInfo(31258, "RE_Slew_Limit_Stat_MW", "WORD16", "Стутус функции проверки ограничений в наладке"),
        RegisterInfo(31260, "RE_LspAuto_Stat_Slew_MW", "WORD16", "Статус автоматического снижения скорости"),
        RegisterInfo(31263, "RE_LspDist3_Slew_MW", "WORD16", "Дистанция снижения до 3 скорости, м"),
        RegisterInfo(31262, "RE_LspDist2_Slew_MW", "WORD16", "Дистанция снижения до 2 скорости, м"),
        RegisterInfo(31261, "RE_LspDist1_Slew_MW", "WORD16", "Дистанция снижения до 1 скорости, м"),
        RegisterInfo(31264, "RE_ZoneAcc_Slew_MW", "WORD16", "Зона регулирования ускорения поворота"),
        RegisterInfo(31265, "RE_ZoneDec_Slew_MW", "WORD16", "Зона регулирования замедления поворота"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ПРИВОДА ТЕЛЕЖКИ
        RegisterInfo(61351, "WR_Trolley_wSpdRef1_Value_MW", "WORD16", "Скорость 1 тележки"),
        RegisterInfo(61352, "WR_Trolley_wSpdRef2_Value_MW", "WORD16", "Скорость 2 тележки"),
        RegisterInfo(61353, "WR_Trolley_wSpdRef3_Value_MW", "WORD16", "Скорость 3 тележки"),
        RegisterInfo(61354, "WR_Trolley_wSpdRef4_Value_MW", "WORD16", "Скорость 4 тележки"),
        RegisterInfo(61356, "WR_rTrolley_Acc_MW", "WORD16", "Время разгона тележки, сек"),
        RegisterInfo(61357, "WR_rTrolley_Dec_MW", "WORD16", "Время торможения тележки, сек"),
        RegisterInfo(61350, "WR_wTrolleyLsp_MW", "WORD16", "Пониженная скорость тележки"),
        RegisterInfo(61359, "WR_rTrolleyOverspd_MW", "WORD16", "Значение веса груза при превышении которого запрещена сверхсинхронная работа тележки"),
        RegisterInfo(61355, "WR_Trolley_wSpdNom_MW", "WORD16", "Номинальная скорость тележки"),

        RegisterInfo(31350, "RE_wSpdRefLou_Trolley", "WORD16", "Пониженая скорость тележки"),
        RegisterInfo(31351, "RE_wSpdRef1_Trolley", "WORD16", "1-ая скорость тележки об/мин"),
        RegisterInfo(31352, "RE_wSpdRef2_Trolley", "WORD16", "2-ая скорость тележки об/мин"),
        RegisterInfo(31353, "RE_wSpdRef3_Trolley", "WORD16", "3-я скорость тележки об/мин"),
        RegisterInfo(31354, "RE_wSpdRef4_Trolley", "WORD16", "4-ая скорость тележки об/мин"),
        RegisterInfo(31355, "RE_wSpdNom_Trolley", "WORD16", "Номинальная скорость ЭД тележки об/мин"),
        RegisterInfo(31359, "RE_rTrolleyOver_Spd", "WORD16", "Масса груза выше которой запрешена повышенная скорость тележки"),
        RegisterInfo(31356, "RE_rTrolley_Acc", "WORD16", "Время разгона, сек"),
        RegisterInfo(31357, "RE_rTrolley_Dec", "WORD16", "Время торможения, сек"),

        RegisterInfo(31358, "RE_Trolley_Limit_Stat_MW", "WORD16", "Стутус функции проверки ограничений в наладке"),
        RegisterInfo(31360, "RE_LspAuto_Stat_Trolley_MW", "WORD16", "Статус автоматического снижения скорости"),
        RegisterInfo(31363, "RE_LspDist3_Trolley_MW", "WORD16", "Дистанция снижения до 3 скорости, м"),
        RegisterInfo(31362, "RE_LspDist2_Trolley_MW", "WORD16", "Дистанция снижения до 2 скорости, м"),
        RegisterInfo(31361, "RE_LspDist1_Trolley_MW", "WORD16", "Дистанция снижения до 1 скорости, м"),
        RegisterInfo(31364, "RE_ZoneAcc_Trolley_MW", "WORD16", "Зона регулирования ускорения тележки"),
        RegisterInfo(31365, "RE_ZoneDec_Trolley_MW", "WORD16", "Зона регулирования замедления тележки"),

        //СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ПРИВОДА ХОДА КРАНА
        RegisterInfo(61451, "WR_Crane_wSpdRef1_Value_MW", "WORD16", "Скорость 1 хода крана"),
        RegisterInfo(61452, "WR_Crane_wSpdRef2_Value_MW", "WORD16", "Скорость 2 хода крана"),
        RegisterInfo(61453, "WR_Crane_wSpdRef3_Value_MW", "WORD16", "Скорость 3 хода крана"),
        RegisterInfo(61454, "WR_Crane_wSpdRef4_Value_MW", "WORD16", "Скорость 4 хода крана"),
        RegisterInfo(61456, "WR_rCrane_Acc_MW", "WORD16", "Время разгона хода крана, сек"),
        RegisterInfo(61457, "WR_rCrane_Dec_MW", "WORD16", "Время торможения хода крана, сек"),

        RegisterInfo(31450, "RE_wSpdRefLou_Crane", "WORD16", "Пониженая скорость хода крана"),
        RegisterInfo(31451, "RE_wSpdRef1_Crane", "WORD16", "1-ая скорость хода крана об/мин"),
        RegisterInfo(31452, "RE_wSpdRef2_Crane", "WORD16", "2-ая скорость хода крана об/мин"),
        RegisterInfo(31453, "RE_wSpdRef3_Crane", "WORD16", "3-я скорость хода крана об/мин"),
        RegisterInfo(31454, "RE_wSpdRef4_Crane", "WORD16", "4-ая скорость хода крана об/мин"),
        RegisterInfo(31455, "RE_wSpdNom_Crane", "WORD16", "Номинальная скорость ЭД хода крана об/мин"),
        RegisterInfo(31456, "RE_rCrane_Acc", "WORD16", "Время разгона, сек"),
        RegisterInfo(31457, "RE_rCrane_Dec", "WORD16", "Время торможения, сек"),

        RegisterInfo(31458, "RE_Crane_Limit_Stat_MW", "WORD16", "Стутус функции проверки ограничений в наладке"),
        RegisterInfo(31460, "RE_LspAuto_Stat_Crane_MW", "WORD16", "Статус автоматического снижения скорости"),
        RegisterInfo(31463, "RE_LspDist3_Crane_MW", "WORD16", "Дистанция снижения до 3 скорости, м"),
        RegisterInfo(31462, "RE_LspDist2_Crane_MW", "WORD16", "Дистанция снижения до 2 скорости, м"),
        RegisterInfo(31461, "RE_LspDist1_Crane_MW", "WORD16", "Дистанция снижения до 1 скорости, м"),
        RegisterInfo(31464, "RE_ZoneAcc_Crane_MW", "WORD16", "Зона регулирования ускорения крана"),
        RegisterInfo(31465, "RE_ZoneDec_Crane_MW", "WORD16", "Зона регулирования замедления крана"),

        RegisterInfo(62000, "WR_Android_Mode_Active_MW", "WORD16", "Активация доступа к функциям Android"),
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
        RegisterInfo(33029, "RE_OGPLimSw_MW", "WORD16", "Датчик критического момента. 1-Норма (грузоподъемность не превышена). 0- превышение грузоподъемности"),
        RegisterInfo(33020, "RE_Current_Load_AI_MW", "WORD16", "Текущее значение с датчика груза в единицах входа модуля"),
        RegisterInfo(39011, "RE_HMI_Weight_Sensor_Err_MW",  "WORD16",  "Cбой датчика грузоподъемности"),
        RegisterInfo(30151, "RE_CAN_AdvAnemometer_MW", "WORD16", "Анемометр"),
        RegisterInfo(33209, "RE_Anemometer_Status_MW", "WORD16", "Статус анемометра"),

        //Параметры датчиков.
        RegisterInfo(33050,  "RE_udi_AbsEncFenac_Hoist_Position_MD",  "WORD16",  "данные датчика высоты подъема"),
        RegisterInfo(33051,  "RE_udi_AbsEncFenac_Hoist_Position_MD",  "WORD16",  "данные датчика высоты подъема"),
        RegisterInfo(33052,  "RE_udi_AbsEncFenac_Slew_Position_MD",  "WORD16",  "данные датчика азимута"),
        RegisterInfo(33053,  "RE_udi_AbsEncFenac_Slew_Position_MD",  "WORD16",  "данные датчика азимута"),
        RegisterInfo(33054,  "RE_udi_AbsEncFenac_Trolley_Position_MD",  "WORD16",  "данные датчика тележки"),
        RegisterInfo(33055,  "RE_udi_AbsEncFenac_Trolley_Position_MD",  "WORD16",  "данные датчика тележки"),
        RegisterInfo(33056,  "RE_udi_AbsEncFenac_Crane_Position_MD",  "WORD16",  "данные датчика хода крана"),
        RegisterInfo(33057,  "RE_udi_AbsEncFenac_Crane_Position_MD",  "WORD16",  "данные датчика хода крана"),

        //-----ошибка связи энкодеров
        RegisterInfo(39014, "RE_HMI_Hoist_AbsEnc_Err_MW", "WORD16", "Сбой датчика подъема"),
        RegisterInfo(39015, "RE_HMI_Slew_AbsEnc_Err_MW", "WORD16", "Сбой датчика поворота"),
        RegisterInfo(39016, "RE_HMI_Trolley_AbsEnc_Err_MW", "WORD16", "Сбой датчика вылета"),
        RegisterInfo(39013, "RE_HMI_Crane_AbsEnc_Err_MW", "WORD16", "Сбой датчика перемещения крана"),
        RegisterInfo(39028, "RE_HMI_GlobalSensors_CANopen_Err_MW", "WORD16", "Авария сети CANopen (датчики)"),
        //-----Статус приводов
        RegisterInfo(31100, "RE_Hoist_StatusWord_MW", "WORD16", "Слово состояния ПЧ подъема"),
        RegisterInfo(31200, "RE_Slew_StatusWord_MW", "WORD16", "Слово состояния ПЧ поворота"),
        RegisterInfo(31300, "RE_Trolley_StatusWord_MW", "WORD16", "Слово состояния ПЧ тележки"),
        RegisterInfo(31400, "RE_Crane_StatusWord_MW", "WORD16", "Слово состояния ПЧ хода крана"),


        //ДИАГНОСТИКА.  ПРИВОД ПОДЪЕМА
        RegisterInfo(31113, "RE_HoistATV_PWR_Err_MW", "WORD16", "Отключение питания ПЧ подъема"),
        RegisterInfo(31101, "RE_Hoist_ControlWord_MW", "WORD16", "Слово управления ПЧ подъема"),
        RegisterInfo(39020, "RE_HMI_Hoist_Drive_Err_MW", "WORD16", "Статус. Ошибка частотного преобразователя подъема"),
        RegisterInfo(31108, "RE_Hoist_Fault_MW", "WORD16", "Код ошибки ПЧ"),
        RegisterInfo(39041, "RE_HMI_stHoist_Drive_ErrCode_MW", "WORD16", "Код ошибки частотного преобразователя подъема"),

        RegisterInfo(31102, "RE_Hoist_TargetVelocity_MW", "WORD16", "Заданная скорость вращения подъема"),
        RegisterInfo(31103, "RE_Hoist_ControlEffort_MW", "WORD16", "Текущая скорость вращения подъема"),
        RegisterInfo(31104, "RE_Hoist_MotorCurrent_MW", "WORD16", "Ток двигателя подъема"),
        RegisterInfo(31110, "RE_Hoist_Encoder_MW", "WORD16", "Количество импульсов энкодера"),
        RegisterInfo(31106, "RE_Hoist_IL1R_MW", "WORD16", "Состояние дискретных входов ПЧ"),
        RegisterInfo(31107, "RE_Hoist_OL1R_MW", "WORD16", "Состояние дискретных выходов ПЧ"),


        //ДИАГНОСТИКА.  ПРИВОД ПОВОРОТА
        RegisterInfo(31213, "RE_SlewATV_PWR_Err_MW", "WORD16", "Отключение питания ПЧ поворота"),
        RegisterInfo(31201, "RE_Slew_ControlWord_MW", "WORD16", "Слово управления ПЧ поворота"),
        RegisterInfo(39021, "RE_HMI_Slew_Drive_Err_MW", "WORD16", "Статус. Ошибка частотного преобразователя поворота"),
        RegisterInfo(31208, "RE_Slew_Fault_MW", "WORD16", "Код ошибки ПЧ"),
        RegisterInfo(39042, "RE_HMI_stSlew_Drive_ErrCode_MW", "WORD16", "Код ошибки частотного преобразователя поворота"),

        RegisterInfo(31202, "RE_Slew_TargetVelocity_MW", "WORD16", "Заданная скорость вращения"),
        RegisterInfo(31203, "RE_Slew_ControlEffort_MW", "WORD16", "Текущая скорость вращения"),
        RegisterInfo(31204, "RE_Slew_MotorCurrent_MW", "WORD16", "Ток двигателя поворота"),
        RegisterInfo(31210, "RE_Slew_Encoder_MW", "WORD16", "Количество импульсов энкодера"),
        RegisterInfo(31206, "RE_Slew_IL1R_MW", "WORD16", "Состояние дискретных входов ПЧ"),
        RegisterInfo(31207, "RE_Slew_OL1R_MW", "WORD16", "Состояние дискретных выходов ПЧ"),


        //ДИАГНОСТИКА.  ПРИВОД ТЕЛЕЖКИ
        RegisterInfo(31313, "RE_TrolleyATV_PWR_Err_MW", "WORD16", "Отключение питания ПЧ тележки"),
        RegisterInfo(31301, "RE_Trolley_ControlWord_MW", "WORD16", "Слово управления ПЧ тележки"),
        RegisterInfo(39022, "RE_HMI_Trolley_Drive_Err_MW", "WORD16", "Статус. Ошибка частотного преобразователя тележки"),
        RegisterInfo(31308, "RE_Trolley_Fault_MW", "WORD16", "Код ошибки ПЧ"),
        RegisterInfo(39043, "RE_HMI_stTrolley_Drive_ErrCode_MW", "WORD16", "Код ошибки частотного преобразователя тележки"),

        RegisterInfo(31302, "RE_Trolley_TargetVelocity_MW", "WORD16", "Заданная скорость вращения"),
        RegisterInfo(31303, "RE_Trolley_ControlEffort_MW", "WORD16", "Текущая скорость вращения"),
        RegisterInfo(31304, "RE_Trolley_MotorCurrent_MW", "WORD16", "Ток двигателя тележки"),
        RegisterInfo(31310, "RE_Trolley_Encoder_MW", "WORD16", "Количество импульсов энкодера"),
        RegisterInfo(31306, "RE_Trolley_IL1R_MW", "WORD16", "Состояние дискретных входов ПЧ"),
        RegisterInfo(31307, "RE_Trolley_OL1R_MW", "WORD16", "Состояние дискретных выходов ПЧ"),

        //ДИАГНОСТИКА.  ПРИВОД ХОДА КРАНА
        RegisterInfo(31413, "RE_CraneATV_PWR_Err_MW", "WORD16", "Отключение питания ПЧ хода крана"),
        RegisterInfo(31401, "RE_Crane_ControlWord_MW", "WORD16", "Слово управления ПЧ хода крана"),
        RegisterInfo(39023, "RE_HMI_Crane_Drive_Err_MW", "WORD16", "Статус. Ошибка частотного преобразователя передвижения"),
        RegisterInfo(31408, "RE_Crane_Fault_MW", "WORD16", "Код ошибки ПЧ"),
        RegisterInfo(39044, "RE_HMI_stCrane_Drive_ErrCode_MW", "WORD16", "Код ошибки частотного преобразователя передвижения"),

        RegisterInfo(31402, "RE_Crane_TargetVelocity_MW", "WORD16", "Заданная скорость вращения"),
        RegisterInfo(31403, "RE_Crane_ControlEffort_MW", "WORD16", "Текущая скорость вращения"),
        RegisterInfo(31404, "RE_Crane_MotorCurrent_MW", "WORD16", "Ток двигателя хода крана"),
        RegisterInfo(31406, "RE_Crane_IL1R_MW", "WORD16", "Состояние дискретных входов ПЧ"),
        RegisterInfo(31407, "RE_Crane_OL1R_MW", "WORD16", "Состояние дискретных выходов ПЧ"),
        RegisterInfo(33165, "RE_xCrane_Reper_Point_MW", "WORD16", "Реперная точка хода крана"),

        RegisterInfo(39029, "RE_HMI_GlobalDrives_ComReady_Err_MW", "WORD16", "Авария сети CANopen (частотные преобразователи)"),

        //ДИАГНОСТИКА.  ПЛК.
        // Дискретные входы ПЛК
        RegisterInfo(30100, "RE_PLC_DI0_0_0_PhaseControlRelay_MW", "WORD16", "Реле контроля фаз"),
        RegisterInfo(30101, "RE_PLC_DI0_0_1_LineContactor_MW", "WORD16", "Состояние линейного контактора"),
        RegisterInfo(30102, "RE_PLC_DI0_0_2_MontageMode_MW", "WORD16", "Монтажный режим"),
        RegisterInfo(30103, "RE_PLC_DI0_0_3_OGPLimSw_MW", "WORD16", "Датчик критического момента"),
        RegisterInfo(30104, "RE_PLC_DI0_0_4_HoistBrakeStatus_MW", "WORD16", "Состояние тормоза подъема"),
        RegisterInfo(30105, "RE_PLC_DI0_0_5_HoistBrakeSensor_MW", "WORD16", "Контроль износа тормоза подъема"),
        RegisterInfo(30106, "RE_PLC_DI0_0_6_HoistLowSpdButton_MW", "WORD16", "Замедление скорости подъема"),
        RegisterInfo(30107, "RE_PLC_DI0_0_7_SlewBrake_MW", "WORD16", "Ручное наложение тормоза поворота"),

        // Дискретные выходы ПЛК
        RegisterInfo(30170, "RE_PLC_DQ0_0_LineContactorEn_MW", "WORD16", "Разрешение включения линейного контактора"),
        RegisterInfo(30171, "RE_PLC_DQ0_1_HoistPWREn_MW", "WORD16", "Разрешение работы подъема"),
        RegisterInfo(30172, "RE_PLC_DQ0_2_SlewPWREn_MW", "WORD16", "Разрешение работы поворота"),
        RegisterInfo(30173, "RE_PLC_DQ0_3_TrolleyPWREn_MW", "WORD16", "Разрешение работы тележки"),
        RegisterInfo(30174, "RE_PLC_DQ0_4_CranePWREn_MW", "WORD16", "Разрешение работы хода крана"),
        RegisterInfo(30175, "RE_PLC_DQ0_5_Hoist_Ventilation_MW", "WORD16", "Вентиляция подъема"),
        RegisterInfo(30176, "RE_PLC_DQ0_6_GlobalReady_MW", "WORD16", "Ошибка. Глобальная готовность"),
        RegisterInfo(30177, "RE_PLC_DQ0_7_HoistOverloadStop_MW", "WORD16", "Авария/перегрузка"),

        // Дискретные входы Модуля
        RegisterInfo(30110, "RE_PLC_DI1_0_0_MicroclimatInput_MW", "WORD16", "Контроль питания обогрева"),
        RegisterInfo(30111, "RE_PLC_DI1_0_1_gv_xBrakePowerControl_MW", "WORD16", "Контроль состояния питания тормозов"),
        RegisterInfo(30112, "RE_PLC_DI1_0_2_gv_xVentilationPowerControl_MW", "WORD16", "Контроль состояния питания независимой вентиляции"),
        RegisterInfo(30117, "RE_PLC_DI1_0_7_ACSS_PWR_MW", "WORD16", "АЦСС питание в норме"),

        RegisterInfo(30120, "RE_PLC_DI1_1_0_ACSS_Fault_MW", "WORD16", "АЦСС в ошибке"),
        RegisterInfo(30121, "RE_PLC_DI1_1_1_ACSS_Redy_MW", "WORD16", "АЦСС ошибок нет"),
        RegisterInfo(30122, "RE_PLC_DI1_1_2_Zapasovka_MW", "WORD16", "Выбор кратности запасовки, =1 -4х кратная, =0 -2х кратная"),
        RegisterInfo(30123, "RE_PLC_DI1_1_3_Luk_MW", "WORD16", "Люк ОПУ"),
        RegisterInfo(30124, "RE_PLC_DI1_1_4_Hoist_Zero_Pos_MW", "WORD16", "0-положение джойстика подъема"),
        RegisterInfo(30125, "RE_PLC_DI1_1_5_Slew_Zero_Pos_MW", "WORD16", "0-положение джойстика поворота"),
        RegisterInfo(30126, "RE_PLC_DI1_1_6_Trolley_Zero_Pos_MW", "WORD16", "0-положение джойстика тележки"),
        RegisterInfo(30127, "RE_PLC_DI1_1_7_Crane_Zero_Pos_MW", "WORD16", "0-положение джойстика хода крана"),

        // Дискретные выходы Модуля
        RegisterInfo(30180, "RE_PLC_DQ1_0_Sound_MW", "WORD16", "Звуковая сигнализация"),
        RegisterInfo(30181, "RE_PLC_DQ1_7_ACSS_Reset_MW", "WORD16", "Сброс ошибки АЦСС"),

        // Аналоговые входы Модуля
        RegisterInfo(30150, "RE_CAN_AdvLoadCell_MW", "WORD16", "Датчик нагрузки"),
        RegisterInfo(30151, "RE_CAN_AdvAnemometer_MW", "WORD16", "Анемометр")
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
        RegisterInfo(33503, "RE_Qnom_MW", "WORD16", "Номинальная грузоподъемность в кг"),
        RegisterInfo(33505, "RE_Crane_Lenght_MW", "WORD16", "Длина стрелы крана"),
        RegisterInfo(33541, "RE_R_AP_MW", "WORD16", "Паспортная группа режима работы"),
        RegisterInfo(33542, "RE_AF_Iso_MW", "WORD16", "Фактическая группа режима работы по ИСО"),
        RegisterInfo(33543, "RE_R_Q_MW", "WORD16", "Фактический класс нагружения"),
        RegisterInfo(33544, "RE_Kp_MW", "WORD16", "Коэффициент нагружения Кр"),
        RegisterInfo(33545, "RE_C_MW", "WORD16", "Класс использования С"),
        RegisterInfo(33546, "RE_R_N_MD", "WORD16", "текущее характеристическое число"),
        RegisterInfo(33547, "RE_R_N_MD", "WORD16", "текущее характеристическое число"),

        RegisterInfo(33521, "RE_SettingModeYear_MW", "WORD16", "Дата и время включения последнего режима наладки"),
        RegisterInfo(33522, "RE_SettingModeMonth_MW", "WORD16", "Дата и время включения последнего режима наладки"),
        RegisterInfo(33523, "RE_SettingModeDay_MW", "WORD16", "Дата и время включения последнего режима наладки"),
        RegisterInfo(33524, "RE_SettingModeHour_MW", "WORD16", "Дата и время включения последнего режима наладки"),
        RegisterInfo(33525, "RE_SettingModeMinute_MW", "WORD16", "Дата и время включения последнего режима наладки"),
        RegisterInfo(33526, "RE_SettingModeSeconds_MW", "WORD16", "Дата и время включения последнего режима наладки"),

        RegisterInfo(33510, "RE_sR_SBKB_No_MW", "WORD16", "Заводской номер СБКБ"),
        RegisterInfo(33500, "RE_sR_Crane_No_MW", "WORD16", "Заводской номер крана"),

        RegisterInfo(33517, "RE_InstallDaySBKB_MW", "WORD16", "День установки системы безопасности"),
        RegisterInfo(33518, "RE_InstallMonthSBKB_MW", "WORD16", "Месяц установки системы безопасности"),
        RegisterInfo(33519, "RE_InstallYearSBKB_MW", "WORD16", "Год установки системы безопасности"),
        //Моточасы
        RegisterInfo(33561, "RE_rHoistOperatingHours_MW", "WORD16", "Моточасы механизма подъема"),
        RegisterInfo(33562, "RE_rSlewOperatingHours_MW", "WORD16", "Моточасы механизма поворота"),
        RegisterInfo(33563, "RE_rTrolleyOperatingHours_MW", "WORD16", "Моточасы механизма тележки"),
        RegisterInfo(33564, "RE_rTravelOperatingHours_MW", "WORD16", "Моточасы механизма передвижения крана"),
        RegisterInfo(33565, "RE_rTotalCraneOperatingHours_MW", "WORD16", "Общие моточасы крана"),
        RegisterInfo(33566, "RE_SBKB_rOperatingHours_MW", "WORD16", "Моточасы системы безопасности"),
        RegisterInfo(33556, "RE_rHoleCraneHours_TO_MW", "WORD16", "Моточасы крана для технического обслуживания"),

        //ЦИКЛЫ РАБОТЫ
        RegisterInfo(33580, "RE_arr_rest_a1_MW_1", "WORD32", "0-10% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33581, "RE_arr_rest_a1_MW_2", "WORD32", "0-10% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33582, "RE_arr_rest_a2_MW_1", "WORD32", "11-20% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33583, "RE_arr_rest_a2_MW_2", "WORD32", "11-20% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33584, "RE_arr_rest_a3_MW_1", "WORD32", "21-30% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33585, "RE_arr_rest_a3_MW_2", "WORD32", "21-30% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33586, "RE_arr_rest_a4_MW_1", "WORD32", "31-40% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33587, "RE_arr_rest_a4_MW_2", "WORD32", "31-40% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33588, "RE_arr_rest_a5_MW_1", "WORD32", "41-50% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33589, "RE_arr_rest_a5_MW_2", "WORD32", "41-50% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33590, "RE_arr_rest_a6_MW_1", "WORD32", "51-60% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33591, "RE_arr_rest_a6_MW_2", "WORD32", "51-60% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33592, "RE_arr_rest_a7_MW_1", "WORD32", "61-70% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33593, "RE_arr_rest_a7_MW_2", "WORD32", "61-70% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33594, "RE_arr_rest_a8_MW_1", "WORD32", "71-80% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33595, "RE_arr_rest_a8_MW_2", "WORD32", "71-80% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33596, "RE_arr_rest_a9_MW_1", "WORD32", "81-90% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33597, "RE_arr_rest_a9_MW_2", "WORD32", "81-90% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33598, "RE_arr_rest_a10_MW_1", "WORD32", "91-100% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33599, "RE_arr_rest_a10_MW_2", "WORD32", "91-100% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33600, "RE_arr_rest_a11_MW_1", "WORD32", ">101% Количество циклов подъема в зависимости от веса груза"),
        RegisterInfo(33601, "RE_arr_rest_a11_MW_2", "WORD32", ">101% Количество циклов подъема в зависимости от веса груза"),

        RegisterInfo(33620, "RE_CycleHoist_MW_1", "WORD32", "Рабочие циклы подъема"),
        RegisterInfo(33621, "RE_CycleHoist_MW_2", "WORD32", "Рабочие циклы подъема"),
        RegisterInfo(33622, "RE_CycleSlew_MW_1", "WORD32", "Рабочие циклы поворота"),
        RegisterInfo(33623, "RE_CycleSlew_MW_1", "WORD32", "Рабочие циклы поворота"),
        RegisterInfo(33624, "RE_CycleTrolley_MW_1", "WORD32", "Рабочие циклы тележки"),
        RegisterInfo(33625, "RE_CycleTrolley_MW_1", "WORD32", "Рабочие циклы тележки"),
        RegisterInfo(33626, "RE_CycleCrane_MW_1", "WORD32", "Рабочие циклы хода крана"),
        RegisterInfo(33627, "RE_CycleCrane_MW_2", "WORD32", "Рабочие циклы хода крана"),
        RegisterInfo(33628, "RE_CycleHoleCrane_MW_1", "WORD32", "Рабочие циклы крана в целом"),
        RegisterInfo(33629, "RE_CycleHoleCrane_MW_2", "WORD32", "Рабочие циклы крана в целом"),

        //СОХРАНЕНИЕ
        //Отправка данных на панель о состоянии записи и чтения VAR-файлов на ПЛК
        RegisterInfo(35000, "RE_SDcard_Stat_MW", "WORD32", "Статус SD-карты"),
        RegisterInfo(35001, "RE_VarSave_Stat_MW", "WORD32", "Статус сохранения VAR-файла 0-Ожидание, 1- Завершено, 2- В процессе, 4-Ошибка."),
        RegisterInfo(35002, "RE_VarSave_Proc", "WORD32", "Процес выполнения сохранения 0-100%"),
        RegisterInfo(35003, "RE_VarRead_Stat_MW", "WORD32", "Статус чтения VAR-файла 0-Ожидание, 1- Завершено, 2- В процессе, 4-Ошибка."),
        RegisterInfo(35004, "RE_VarRead_Proc", "WORD32", "Процес выполнения чтения 0-100%"),
        RegisterInfo(35005, "RE_VarSize_MW", "WORD32", "Объем VAR-файла"),

        //Запись и чтение VAR-файлов на ПЛК, данных регистратора параметров и т.д
        RegisterInfo(65001, "WR_Button_Save_Var_MW", "WORD32", "Записать VAR-файл"),
        RegisterInfo(65002, "WR_Button_Read_Var_MW", "WORD32", "Считать VAR-файл"),
        RegisterInfo(65003, "WR_Button_Read_Size_MW", "WORD32", "Считать объем VAR-файла"),
        RegisterInfo(65004, "WR_Button_Save_Abort_MW", "WORD32", "Отмена записи VAR-файл"),
        RegisterInfo(65005, "WR_Button_Read_Abort_MW", "WORD32", "Отмена считать VAR-файл"),
        RegisterInfo(65006, "WR_Button_Save_RP_MW", "WORD32", "Записать файл РП-на SD-карту"),

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
        RegisterInfo(33500, "RE_sR_Crane_No_MW", "WORD16", "Заводской номер крана"),
        RegisterInfo(33550, "RE_TOCounter_MW", "WORD16", "Счетчик моточасов м.ч."),
        RegisterInfo(33551, "RE_Technical_Reglament_TO1_MW", "WORD16", "Проведите ТО"),
        RegisterInfo(33552, "RE_Alrm_TO1_MW", "WORD16", "Скорости ограничены, проведите ТО"),
        RegisterInfo(33553, "RE_MotorHourDelta_MW", "WORD16", "Сколько часов осталось до ТО"),
        RegisterInfo(33554, "RE_Technical_Reglament_TO1_Delta_MW", "WORD16", "До ТО осталось AT%MW307 часов"),
        RegisterInfo(33555, "RE_Technical_Reglament_xWarrantyEnd_MW", "WORD16", "Грузовая лебедка отработала 750 м.ч."),

        RegisterInfo(33556, "RE_rHoleCraneHours_TO_MW", "WORD16", "Моточасы крана для технического обслуживания"),
        RegisterInfo(63550, "WR_xManualResetTO_MW", "WORD16", "Команда сброса счетчика ТО"),
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
 * Регистры для работы с UI аварии и предупреждения
 */
object UIAccidentAndWaringConfiguration {
    const val NAME_ACCIDENT_AND_WARING = "UI_accident_and_waring_screen"

    val REGISTERS = listOf(
        RegisterInfo(33027, "RE_Overload_105Percent_MW", "WORD16", "Бит превышения грузоподъемности более 105%"),
        RegisterInfo(33028, "RE_Overload_110Percent_MW", "WORD16", "Бит превышения грузоподъемности более 110%"),
        RegisterInfo(33029, "RE_OGPLimSw_MW", "WORD16", "Датчик критического момента. 1-Норма (грузоподъемность не превышена). 0- превышение грузоподъемности"),
        RegisterInfo(31130, "RE_HoistBrakeSensor_MW", "WORD16", "Износ тормоза привода главного подъема. 0-Норма (тормоз исправен). 1- тормоз изношен"),

        RegisterInfo(30204, "RE_Alarm_GRUZ_FUN_MW", "WORD16", "Неверная ГХ"),
        RegisterInfo(30205, "RE_Alarm_Zapasovka_MW", "WORD16", "Ошибка выбора запасовки"),

        RegisterInfo(33001, "RE_Build_Mode_MW", "WORD16", "Флаг режима наладки"),
        RegisterInfo(33002, "RE_Service_Mode_MW", "WORD16", "Флаг режима сервис"),
        RegisterInfo(33003, "RE_MontageMode_MW", "WORD16", "Флаг монтажного режима"),

        RegisterInfo(33104, "RE_Crane_Config_Warning_MW", "WORD16", "Ход крана не настроен"),
        RegisterInfo(33101, "RE_Hoist_Config_Warning_MW", "WORD16", "Подъем не настроен"),
        RegisterInfo(33102, "RE_Slew_Config_Warning_MW", "WORD16", "Поворот крана не настроен"),
        RegisterInfo(33103, "RE_Trolley_Config_Warning_MW", "WORD16", "Вылет не настроен"),
        RegisterInfo(33100, "RE_Weight_Config_Warning_MW", "WORD16", "Грузоподъемность не настроена"),

        RegisterInfo(33551, "RE_Technical_Reglament_TO1_MW", "WORD16", "Проведите ТО"),
        RegisterInfo(33552, "RE_Alarm_TO1_MW", "WORD16", "Скорости ограничены, проведите ТО"),
        RegisterInfo(33553, "RE_MotorHourDelta_MW", "WORD16", "Сколько часов осталось до ТО"),
        RegisterInfo(33554, "RE_Technical_Reglament_TO1_Delta_MW", "WORD16", "До ТО осталось AT%MW307 часов"),
        RegisterInfo(33555, "RE_Technical_Reglament_xWarrantyEnd_MW", "WORD16", "Грузовая лебедка отработала 750 м.ч."),

        RegisterInfo(39000, "RE_HMI_Luk_Err_MW", "WORD16", "Состояние конечного выключателя люка ОПУ"),
        RegisterInfo(39001, "RE_HMI_LineContZeroPosAlrm_MW", "WORD16", "Авария нулевой блокировки линейного контактора"),
        RegisterInfo(39002, "RE_HMI_Microclimat_Err_MW", "WORD16", "Авария обогрева технического помещения"),
        RegisterInfo(39003, "RE_HMI_BrakePowerControl_Err_MW", "WORD16", "Авария питания тормозов"),
        RegisterInfo(39004, "RE_HMI_VentilationPowerControl_Err_MW", "WORD16", "Авария питания вентиляции электродвигателей"),

        RegisterInfo(39005, "RE_HMI_Hoist_Joy_Alarm_MW", "WORD16", "Ошибка джойстика механизма подъема"),
        RegisterInfo(39006, "RE_HMI_Slew_Joy_Alarm_MW", "WORD16", "Ошибка джойстика механизма поворота"),
        RegisterInfo(39007, "RE_HMI_Trolley_Joy_Alarm_MW", "WORD16", "Ошибка джойстика механизма тележки"),
        RegisterInfo(39008, "RE_HMI_Crane_Joy_Alarm_MW", "WORD16", "Ошибка джойстика механизма передвижения"),
        RegisterInfo(39009, "RE_HMI_Alarm_OGP_SENSOR_MW", "WORD16", "Авария датчика критического грузового момента"),

        RegisterInfo(39010, "RE_HMI_Crane_CTRLPoint_Er_MWr", "WORD16", "Сбой датчика реперной точки хода крана"),
        RegisterInfo(39011, "RE_HMI_Weight_Sensor_Err_MW", "WORD16", "Сбой датчика грузоподъемности"),
        RegisterInfo(39012, "RE_HMI_Anemom_Sensor_Err_MW", "WORD16", "Сбой анемометра"),

        RegisterInfo(39013, "RE_HMI_Crane_AbsEnc_Err_MW", "WORD16", "Сбой энкодера механизма передвижения"),
        RegisterInfo(39014, "RE_HMI_Hoist_AbsEnc_Err_MW", "WORD16", "Сбой энкодера механизма подъема"),
        RegisterInfo(39015, "RE_HMI_Slew_AbsEnc_Err_MW", "WORD16", "Сбой энкодера механизма поворота"),
        RegisterInfo(39016, "RE_HMI_Trolley_AbsEnc_Err_MW", "WORD16", "Сбой энкодера механизма тележки"),

        RegisterInfo(39017, "RE_HMI_PhaseControl_Relay_Err_MW", "WORD16", "Авария реле контроля фаз"),
        RegisterInfo(39018, "RE_HMI_PowerLine_Fault_Err_MW", "WORD16", "Авария питающей сети"),

        RegisterInfo(39020, "RE_HMI_Hoist_Drive_Err_MW", "WORD16", "Ошибка частотного преобразователя подъема"),
        RegisterInfo(39021, "RE_HMI_Slew_Drive_Err_MW", "WORD16", "Ошибка частотного преобразователя поворота"),
        RegisterInfo(39022, "RE_HMI_Trolley_Drive_Err_MW", "WORD16", "Ошибка частотного преобразователя тележки"),
        RegisterInfo(39023, "RE_HMI_Crane_Drive_Err_MW", "WORD16", "Ошибка частотного преобразователя передвижения"),

        RegisterInfo(39024, "RE_HMI_HoistATV_PWR_Err_MW", "WORD16", "Отключение питания ПЧ подъема"),
        RegisterInfo(39025, "RE_HMI_SlewATV_PWR_Err_MW", "WORD16", "Отключение питания ПЧ поворота"),
        RegisterInfo(39026, "RE_HMI_TrolleyATV_PWR_Err_MW", "WORD16", "Отключение питания ПЧ тележки"),
        RegisterInfo(39027, "RE_HMI_CraneATV_PWR_Err_MW", "WORD16", "Отключение питания ПЧ передвижения"),

        RegisterInfo(39028, "RE_HMI_GlobalSensors_CANopen_Err_MW", "WORD16", "Авария сети CANopen (датчики)"),
        RegisterInfo(39029, "RE_HMI_GlobalDrives_ComReady_Err_MW", "WORD16", "Авария сети CANopen (частотные преобразователи)"),

        RegisterInfo(39030, "RE_HMI_HoistAbsEnc_MaxDeltaError_MW", "WORD16", "Превышение максимальной дельты энкодера подъема"),
        RegisterInfo(39031, "RE_HMI_SlewAbsEnc_MaxDeltaError_MW", "WORD16", "Превышение максимальной дельты энкодера поворота"),
        RegisterInfo(39032, "RE_HMI_TrolleyAbsEnc_MaxDeltaError_MW", "WORD16", "Превышение максимальной дельты энкодера тележки"),
        RegisterInfo(39033, "RE_HMI_CraneAbsEnc_MaxDeltaError_MW", "WORD16", "Превышение максимальной дельты энкодера передвижения"),

        RegisterInfo(39034, "RE_HMI_HoistAbsEnc_MaxValueError_MW", "WORD16", "Превышение максимального значения энкодера подъема"),
        RegisterInfo(39035, "RE_HMI_SlewAbsEnc_MaxValueError_MW", "WORD16", "Превышение максимального значения энкодера поворота"),
        RegisterInfo(39036, "RE_HMI_TrolleyAbsEnc_MaxValueError_MW", "WORD16", "Превышение максимального значения энкодера тележки"),
        RegisterInfo(39037, "RE_HMI_CraneAbsEnc_MaxValueError_MW", "WORD16", "Превышение максимального значения энкодера передвижения"),

        RegisterInfo(39041, "RE_HMI_stHoist_Drive_ErrCode_MW", "WORD16", "Код ошибки частотного преобразователя подъема"),
        RegisterInfo(39042, "RE_HMI_stSlew_Drive_ErrCode_MW", "WORD16", "Код ошибки частотного преобразователя поворота"),
        RegisterInfo(39043, "RE_HMI_stTrolley_Drive_ErrCode_MW", "WORD16", "Код ошибки частотного преобразователя тележки"),
        RegisterInfo(39044, "RE_HMI_stCrane_Drive_ErrCode_MW", "WORD16", "Код ошибки частотного преобразователя передвижения"),

        RegisterInfo(39045, "RE_HMI_xHoist_Encoder_Fault_MW", "WORD16", "Ошибка энкодера привода подъема"),
        RegisterInfo(39046, "RE_HMI_xSlew_Encoder_Fault_MW", "WORD16", "Ошибка энкодера привода поворота"),
        RegisterInfo(39047, "RE_HMI_xTrolley_Encoder_Fault_MW", "WORD16", "Ошибка энкодера привода тележки")
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
        RegisterInfo(33001, "RE_Build_Mode_MW", "WORD16", "Флаг режима наладки"),
        RegisterInfo(33002, "RE_Service_Mode_MW", "WORD16", "Флаг режима сервис"),
        RegisterInfo(33003, "RE_MontageMode_MW", "WORD16", "Флаг монтажного режима"),

        RegisterInfo(63007, "WR_sPassword1_MW", "WORD16", "Пароль панели оператора для входа в наладку и сервис. Часть 1."),
        RegisterInfo(63008, "WR_sPassword2_MW", "WORD16", "Пароль панели оператора для входа в наладку и сервис. Часть 2."),
        RegisterInfo(63009, "WR_sPassword3_MW", "WORD16", "Пароль панели оператора для входа в наладку и сервис. Часть 3."),

        RegisterInfo(63001, "WR_LoginButton_MW", "WORD16", "Кнопка LogIn"),
        RegisterInfo(63000, "WR_ExitLevel_MW", "WORD16", "Выход из наладки и сервиса"),
        RegisterInfo(33071, "RE_xPasswordOrNaladka_MW", "WORD16", "Флаг.Режим авторизации наладки"),
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