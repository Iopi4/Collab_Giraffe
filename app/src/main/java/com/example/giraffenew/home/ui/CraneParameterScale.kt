package com.example.giraffenew.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.ui.theme.DarkLemon
import com.example.giraffenew.R
import com.example.giraffenew.home.ui.scale.ScaleHorizonWeightHi
import com.example.giraffenew.home.ui.scale.ScaleHorizonWeightHiHi
import com.example.giraffenew.home.ui.scale.ScaleHorizonWeightMax
import com.example.giraffenew.core.ui_kit.composable.text.BasicParametersText
import com.example.giraffenew.core.ui_kit.composable.text.BasicParametersTextSmall
import com.example.giraffenew.home.presentation.HomeScreenViewModel
import com.example.giraffenew.home.ui.lamp.LampIconInformationAlarm
import com.example.giraffenew.home.ui.lamp.LampIconInformationBrakeHoist
import com.example.giraffenew.home.ui.lamp.LampIconInformationDebugMode
import com.example.giraffenew.home.ui.lamp.LampIconInformationFault
import com.example.giraffenew.home.ui.lamp.LampIconInformationTO
import com.example.giraffenew.home.ui.lamp.LampIconInformationWorking
import com.example.giraffenew.home.ui.lamp.LampWindStatus
import com.example.giraffenew.home.ui.scale.ScaleVerticalHoist
import com.example.giraffenew.home.ui.scale.ScaleVerticalWind
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun CraneParameterScale(
    parameterScale: HomeScreenViewModel.CraneParameterScaleState,
    
){
    val weightPercent = parameterScale.weightPercent
    val hoistPosition = parameterScale.hoistPosition
    val hoistMaxPosition = parameterScale.hoistMaxPosition
    val hoistMinPosition = parameterScale.hoistMinPosition
    val cranePosition = parameterScale.cranePosition
    val craneMaxPosition = parameterScale.craneMaxPosition

    val windCurrent = parameterScale.windCurrent
    val windSpeedLvl2 = parameterScale.windSpeedLvl2
    val windSpeedMontage = parameterScale.windSpeedMontage
    val anemometerStatus = parameterScale.anemometerStatus

    val icalarmTO = parameterScale.icalarmTO
    val ictechnicalReglamentTO = parameterScale.ictechnicalReglamentTO

    val icMontageMode = parameterScale.icMontageMode
    val icCraneRepPointFault = parameterScale.icCraneRepPointFault
    val icCraneRepPoint = parameterScale.icCraneRepPoint
    val icLoadMomentSensorAlarm = parameterScale.icLoadMomentSensorAlarm
    val icHoistBrakeSensor = parameterScale.icHoistBrakeSensor
    val icCraneRailsEn = parameterScale.icCraneRailsEn


    val myBoxMainModifier = Modifier
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .size(324.dp)
        .border(
            width = 2.dp,
            color = DarkLemon,
            shape = RoundedCornerShape(10.dp)
        )

    //Бокс 1 - для крана с ГХ
    Box(myBoxMainModifier){
//Кран
        Image (
            imageVector = ImageVector.vectorResource(id = R.drawable.image_home_tower_crane_vector),
            contentDescription = "Кран",
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 50.dp))
//Шкала грузоподъемности
        BasicParametersText(
            stringResource(R.string.Home_LOADING_CAPACITY),
            modifier = Modifier
            .padding(start = 130.dp, end = 5.dp, top = 10.dp))

        Box(
            modifier = Modifier
                .padding(start = 130.dp, top = 29.dp, end = 10.dp),
            contentAlignment = Alignment.TopStart
        ) {
            ScaleHorizonWeightMax(
                currentValue = weightPercent,
                minValue = 0f,
                maxValue = 120f,
                modifier = Modifier.size(width = 170.dp, height = 16.dp))
            ScaleHorizonWeightHiHi(
                currentValue = weightPercent,
                minValue = 0f,
                maxValue = 105f,
                modifier = Modifier.size(width = 150.dp, height = 16.dp))
            ScaleHorizonWeightHi(
                currentValue = weightPercent,
                minValue = 0f,
                maxValue = 90f,
                modifier = Modifier.size(width = 130.dp, height = 16.dp))
        }
//Отрисуем лампу с вентилятором. Уровень ветра.
        Box(modifier = Modifier
            .padding(start = 10.dp, top =10.dp, end = 10.dp),
            contentAlignment = Alignment.TopStart) {
            LampWindStatus(
                isLampStatus = anemometerStatus
            )
        }
        val imageVector = when (anemometerStatus) {
            0 -> ImageVector.vectorResource(R.drawable.ic_wind_power_48)
            1 -> ImageVector.vectorResource(R.drawable.ic_wind_power_48_dark_gray)
            2 -> ImageVector.vectorResource(R.drawable.ic_wind_power_48)
            else -> ImageVector.vectorResource(R.drawable.ic_wind_power_48)
        }
        Box(modifier = Modifier
            .padding(start = 10.dp, top =10.dp, end = 10.dp),
            contentAlignment = Alignment.TopStart) {
            Image(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
            )
        }
        val maxValue = when (icMontageMode) {
            0 -> windSpeedLvl2
            1 -> windSpeedMontage
            else -> windSpeedLvl2
        }
        Box(modifier = Modifier
            .padding(start = 55.dp, top =10.dp, end = 10.dp),
            contentAlignment = Alignment.TopStart) {
            ScaleVerticalWind(
                currentValue = windCurrent,
                minValue = 0f,
                maxValue = maxValue,
                anemometerStatus = anemometerStatus,
                modifier = Modifier.size(width = 12.dp, height = 40.dp)
            )
        }




//Отрисуем лампу со звуковой сигнализацией
        Box(modifier = Modifier
            .padding(start = 10.dp, top =50.dp, end = 10.dp),
            contentAlignment = Alignment.TopStart) {

        }
        Box(modifier = Modifier
            .padding(start = 10.dp, top =50.dp, end = 10.dp),
            contentAlignment = Alignment.TopStart) {

        }

//Шкала высоты подъема. Заголовок
        BasicParametersText(
            stringResource(R.string.Home_LIFT_HEIGHT),
            modifier = Modifier
                .padding(start = 250.dp, top = 240.dp))
        //Значение максимальной высоты подъема
        Box(
            modifier = Modifier
                .padding(start = 115.dp, end = 50.dp, top = 147.dp, bottom = 90.dp)
                .background(Color.Black),
            contentAlignment = Alignment.TopStart
        ) {
            BasicParametersText(
            formatDecimalMinus(hoistMaxPosition),
            modifier = Modifier.padding(start = 4.dp, end = 4.dp, top = 2.dp, bottom = 2.dp))}


        //Шкала
        Box(
            modifier = Modifier
                .padding(start = 280.dp, top = 100.dp,),
            contentAlignment = Alignment.TopStart
        ) {
            ScaleVerticalHoist(
                currentValue = hoistPosition,
                minValue = hoistMinPosition,
                maxValue = hoistMaxPosition,
                modifier = Modifier.size(width = 16.dp, height = 140.dp)
            )
        }

//Шкала хода крана. Заголовок
        BasicParametersText(
            stringResource(R.string.Home_MOVE_CRANE),
            modifier = Modifier
            .padding(start = 10.dp, top = 230.dp))
        //Значение максимальной длины кранового пути
        BasicParametersText(
            formatDecimal(craneMaxPosition),
            modifier = Modifier
                .padding(start = 150.dp, end = 50.dp, top = 245.dp))
        Box(
            modifier = Modifier
                .padding(start = 10.dp, top = 248.dp, end = 15.dp),
            contentAlignment = Alignment.TopStart
        ) {
            ScaleHorizonWeightHi(
                currentValue = cranePosition,
                minValue = 0f,
                maxValue = craneMaxPosition,
                modifier = Modifier.size(width = 135.dp, height = 16.dp)
            )}

        Box(modifier = Modifier
            .padding(start = 0.dp, top = 260.dp,)){
            Row (
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                //Лампа включения монтажного режима
                LampIconInformationDebugMode(
                    isLampIconStatus = icMontageMode,
                    isIcon = painterResource(R.drawable.ic_home_scale_montage_mode),
                )
                //Лампа срабатывания датчика критического момента
                LampIconInformationAlarm(
                    isLampIconStatus = icLoadMomentSensorAlarm,
                    isIcon = painterResource(R.drawable.ic_home_scale_load_moment_sensor_alarm),
                )
                //Контроль износа тормоза подъема. 1-норма. 0-Авария
                LampIconInformationBrakeHoist(
                    isLampIconStatus = icHoistBrakeSensor,
                    isIcon = painterResource(R.drawable.ic_home_scale_hoist_brake_sensor),
                )
                //Лампа активации хода крана
                LampIconInformationWorking(
                    isLampIconStatus = icCraneRailsEn,
                    isIcon = painterResource(R.drawable.ic_home_scale_crane_rails_en),
                )
                //Лампа срабатывания реперной точки
                LampIconInformationWorking(
                    isLampIconStatus = icCraneRepPoint,
                    isIcon = painterResource(R.drawable.ic_home_scale_crane_rep_point_trigger),
                )
                LampIconInformationWorking(
                    isLampIconStatus = 0,
                    isIcon = painterResource(R.drawable.ic_home_scale_giraffe_power),
                )
                LampIconInformationTO(
                    istechnicalReglamentTO = ictechnicalReglamentTO,
                    isAlarmTO = icalarmTO,
                    isIcon = painterResource(R.drawable.ic_home_info_maintenance),
                )


            }
        }



    }

}
private val VIRTUAL_ZERO: Float = 32250f //Константа для виртуального нуля. Необходимо для корретной высоты подъема

private fun formatDecimal(value: Float): String {
    return String.format("%04.1f", value).replace(',', '.')
}

private fun formatDecimalMinus(value: Float): String {
    val realValue = (value * 10 - VIRTUAL_ZERO) / 10f
    return if (realValue < 0) {
        String.format("%03d", realValue.toInt()) // Для отрицательных чисел
    } else {
        String.format("%03d", realValue.toInt()) // Для положительных чисел
    }
}


@Preview
@Composable
fun CraneParameterScalePreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        CraneParameterScale(
            parameterScale = TODO()
        )
    }
}