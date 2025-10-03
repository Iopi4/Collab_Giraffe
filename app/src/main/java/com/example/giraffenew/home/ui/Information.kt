package com.example.giraffenew.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.home.presentation.HomeScreenViewModel
import com.example.giraffenew.home.ui.lamp.LampIconInformationACSS
import com.example.giraffenew.home.ui.lamp.LampIconInformationAlarm
import com.example.giraffenew.home.ui.lamp.LampIconInformationCanOpen
import com.example.giraffenew.home.ui.lamp.LampIconInformationDebugMode
import com.example.giraffenew.home.ui.lamp.LampIconInformationFault
import com.example.giraffenew.ui.theme.Black
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Silver

@Composable
fun Information (
    information: HomeScreenViewModel.InformationState,
){

    val icBuildMode = information.icBuildMode
    val icServiceMode = information.icServiceMode

    val icACSSPWR = information.icACSSPWR  //АЦСС питание в норме
    val icACSSFault = information.icACSSFault //АЦСС в ошибке
    val icLukStatus = information.icLukStatus //Состояние конечного выключателя люка ОПУ
    val icLineContactorZeroPosAlarm = information.icLineContactorZeroPosAlarm //Авария нулевой блокировки линейного контактора
    val icMicroclimateHeaterFault = information.icMicroclimateHeaterFault//вария обогрева технического помещения
    val icBrakePowerSupplyFault = information.icBrakePowerSupplyFault //Авария питания тормозов
    val icVentilationPowerSupplyFault = information.icVentilationPowerSupplyFault //Авария питания вентиляции электродвигателей
    val icHoistJoystickError = information.icHoistJoystickError //Ошибка джойстика механизма подъема
    val icSlewJoystickError = information.icSlewJoystickError //Ошибка джойстика механизма поворота
    val icTrolleyJoystickError = information.icTrolleyJoystickError //Ошибка джойстика механизма тележки
    val icCraneJoystickError = information.icCraneJoystickError //Ошибка джойстика механизма хода крана
    val icCraneEncoderFault = information.icCraneEncoderFault //Сбой энкодера механизма хода крана
    val icHoistEncoderFault = information.icHoistEncoderFault //Сбой энкодера механизма хода крана
    val icSlewEncoderFault = information.icSlewEncoderFault //Сбой энкодера механизма поворота
    val icTrolleyEncoderFault = information.icTrolleyEncoderFault //Сбой энкодера механизма тележки
    val icPhaseControlRelayFault = information.icPhaseControlRelayFault //Авария реле контроля фаз
    val icPowerSupplyNetworkFault = information.icPowerSupplyNetworkFault //Авария питающей сети
    val icHoistDriveError = information.icHoistDriveError //Ошибка частотного преобразователя подъема
    val icSlewDriveError = information.icSlewDriveError //Ошибка частотного преобразователя поворота
    val icTrolleyDriveError = information.icTrolleyDriveError //Ошибка частотного преобразователя тележки
    val icCraneDriveError = information.icCraneDriveError //Ошибка частотного преобразователя хода крана
    val icHoistDrivePowerError = information.icHoistDrivePowerError //Отключение питания частотного преобразователя подъема
    val icSlewDrivePowerError = information.icSlewDrivePowerError //Отключение питания частотного преобразователя поворота
    val icTrolleyDrivePowerError = information.icTrolleyDrivePowerError //Отключение питания частотного преобразователя тележки
    val icCraneDrivePowerError = information.icCraneDrivePowerError //Отключение питания частотного преобразователя хода крана
    val icCANOpenSensorsError = information.icCANOpenSensorsError //Авария сети CANopen (датчики)
    val icCANOpenDrivesError = information.icCANOpenDrivesError //Авария сети CANopen (частотные преобразователи)

    val myBoxMainModifier = Modifier
        .fillMaxSize()
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .fillMaxSize()
        .background(Black)
        .border(
            width = 2.dp, color = Silver, //рамка
            shape = RoundedCornerShape(10.dp)
        )
    val debugMode = if (listOf(icBuildMode, icServiceMode)
            .any { it == 1 }) 1 else 0

    val encoderFault = if (listOf(icHoistEncoderFault, icSlewEncoderFault, icTrolleyEncoderFault, icCraneEncoderFault)
            .any { it == 1 }) 1 else 0

    val joystickFault = if (listOf(icHoistJoystickError, icSlewJoystickError, icTrolleyJoystickError, icCraneJoystickError)
            .any { it == 1 }) 1 else 0
    val driveError = if (listOf(icHoistDriveError, icSlewDriveError, icTrolleyDriveError, icCraneDriveError,
            icHoistDrivePowerError,icSlewDrivePowerError,icTrolleyDrivePowerError,icCraneDrivePowerError)
            .any { it == 1 }) 1 else 0


    //Иконки аварий и предупреждений
    Box(myBoxMainModifier){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LampIconInformationAlarm(
                    isLampIconStatus = icPhaseControlRelayFault,
                    isIcon = painterResource(R.drawable.ic_home_info_phase_control_relay_fault),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = icPowerSupplyNetworkFault,
                    isIcon = painterResource(R.drawable.ic_home_info_power_supply_fault),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = icLineContactorZeroPosAlarm,
                    isIcon = painterResource(R.drawable.ic_home_info_line_contactor_zero_pos_alarm),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = icMicroclimateHeaterFault,
                    isIcon = painterResource(R.drawable.ic_home_info_microclimate_heater_fault),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = icVentilationPowerSupplyFault,
                    isIcon = painterResource(R.drawable.ic_home_info_ventilation_power_supply_fault),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = encoderFault,
                    isIcon = painterResource(R.drawable.ic_home_info_abs_encoder_fault),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = driveError,
                    isIcon = painterResource(R.drawable.ic_home_info_drive_error),
                )


            }

            Row (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LampIconInformationDebugMode(
                    isLampIconStatus = debugMode,
                    isIcon = painterResource(R.drawable.ic_home_info_build_mode),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = icBrakePowerSupplyFault,
                    isIcon = painterResource(R.drawable.ic_brake),
                )
                LampIconInformationFault(
                    isLampIconStatus = icLukStatus,
                    isIcon = painterResource(R.drawable.ic_home_info_luk_status),
                )
                LampIconInformationACSS(
                    isACSSPWR = icACSSPWR,
                    isACSSFault = icACSSFault,
                    isIcon = painterResource(R.drawable.ic_home_info_acss),
                )
                LampIconInformationFault(
                    isLampIconStatus = joystickFault,
                    isIcon = painterResource(R.drawable.ic_home_info_joystick_error),
                )
                LampIconInformationCanOpen(
                    isLampIconStatus = icCANOpenSensorsError,
                    isIcon = painterResource(R.drawable.ic_home_info_canopen_sensor_error),
                )
                LampIconInformationCanOpen(
                    isLampIconStatus = icCANOpenDrivesError,
                    isIcon = painterResource(R.drawable.ic_home_info_canopen_drive_error),
                )
            }
        }
    }
}

@Preview
@Composable
fun InformationPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        Information(
            information = TODO()
        )
    }
}