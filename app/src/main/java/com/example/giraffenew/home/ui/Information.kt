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
import com.example.giraffenew.home.ui.lamp.LampIconInformationAlarm
import com.example.giraffenew.ui.theme.Black
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Silver

@Composable
fun Information (
    beeper: Int,

    icBuildMode   : Int,
    icServiceMode : Int,

    icACSSPWR   : Int,      //АЦСС питание в норме
    icACSSFault : Int,     //АЦСС в ошибке
    icLukStatus : Int,     //Состояние конечного выключателя люка ОПУ
    icLineContactorZeroPosAlarm : Int, //Авария нулевой блокировки линейного контактора
    icMicroclimateHeaterFault   : Int,   //вария обогрева технического помещения
    icBrakePowerSupplyFault     : Int,    //Авария питания тормозов
    icVentilationPowerSupplyFault : Int, //Авария питания вентиляции электродвигателей
    icHoistJoystickError        : Int,    //Ошибка джойстика механизма подъема
    icSlewJoystickError         : Int,     //Ошибка джойстика механизма поворота
    icTrolleyJoystickError      : Int,  //Ошибка джойстика механизма тележки
    icCraneJoystickError      : Int,    //Ошибка джойстика механизма хода крана
    icCraneEncoderFault       : Int,     //Сбой энкодера механизма хода крана
    icHoistEncoderFault       : Int,     //Сбой энкодера механизма хода крана
    icSlewEncoderFault        : Int,      //Сбой энкодера механизма поворота
    icTrolleyEncoderFault     : Int,   //Сбой энкодера механизма тележки
    icPhaseControlRelayFault  : Int, //Авария реле контроля фаз
    icPowerSupplyNetworkFault : Int, //Авария питающей сети
    icHoistDriveError         : Int, //Ошибка частотного преобразователя подъема
    icSlewDriveError          : Int, //Ошибка частотного преобразователя поворота
    icTrolleyDriveError       : Int, //Ошибка частотного преобразователя тележки
    icCraneDriveError         : Int, //Ошибка частотного преобразователя хода крана
    icHoistDrivePowerError    : Int, //Отключение питания частотного преобразователя подъема
    icSlewDrivePowerError     : Int, //Отключение питания частотного преобразователя поворота
    icTrolleyDrivePowerError  : Int, //Отключение питания частотного преобразователя тележки
    icCraneDrivePowerError    : Int, //Отключение питания частотного преобразователя хода крана
    icCANOpenSensorsError     : Int, //Авария сети CANopen (датчики)
    icCANOpenDrivesError      : Int, //Авария сети CANopen (частотные преобразователи)


){
    val myBoxMainModifier = Modifier
        .size(width = 324.dp, height = 106.dp)
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .fillMaxSize()
        .background(Black)
        .border(
            width = 2.dp, color = Silver, //рамка
            shape = RoundedCornerShape(10.dp)
        )

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
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )


            }

            Row (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
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
            beeper = 1,
            icBuildMode = 1,
            icServiceMode = 1,
            icACSSPWR = 1,
            icACSSFault = 1,
            icLukStatus = 1,
            icLineContactorZeroPosAlarm = 1,
            icMicroclimateHeaterFault = 1,
            icBrakePowerSupplyFault = 1,
            icVentilationPowerSupplyFault = 1,
            icHoistJoystickError = 1,
            icSlewJoystickError =1,
            icTrolleyJoystickError = 1,
            icCraneJoystickError = 1,
            icCraneEncoderFault = 1,
            icHoistEncoderFault = 1,
            icSlewEncoderFault = 1,
            icTrolleyEncoderFault = 1,
            icPhaseControlRelayFault = 1,
            icPowerSupplyNetworkFault = 1,
            icHoistDriveError = 1,
            icSlewDriveError = 1,
            icTrolleyDriveError = 1,
            icCraneDriveError = 1,
            icHoistDrivePowerError = 1,
            icSlewDrivePowerError = 1,
            icTrolleyDrivePowerError = 1,
            icCraneDrivePowerError = 1,
            icCANOpenSensorsError = 1,
            icCANOpenDrivesError = 1,
        )
    }
}