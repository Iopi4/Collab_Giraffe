package com.example.giraffenew.home.ui

import androidx.compose.foundation.Image
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
import com.example.giraffenew.home.ui.lamp.LampIconInformationAlarm
import com.example.giraffenew.home.ui.lamp.LampIconInformationBrakeHoist
import com.example.giraffenew.home.ui.lamp.LampIconInformationDebugMode
import com.example.giraffenew.home.ui.lamp.LampIconInformationFault
import com.example.giraffenew.home.ui.lamp.LampIconInformationWorking
import com.example.giraffenew.home.ui.lamp.LampWindStatus
import com.example.giraffenew.home.ui.scale.ScaleVerticalHoist
import com.example.giraffenew.home.ui.scale.ScaleVerticalWind
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun CraneParameterScale(
    weightPercent:      Float,
    hoistPosition:      Float,
    hoistMaxPosition:   Float,
    hoistMinPosition:   Float,
    cranePosition:      Float,
    craneMaxPosition:   Float,
    anemometerStatus: Int,
    windCurrent:      Float,
    windSpeedLvl2: Float,
    windSpeedMontage: Float,
    beeperWeightLimit: Int,
    //Иконки предупреждений
    icMontageMode: Int,
    icCraneRepPointFault: Int,
    icCraneRepPoint: Int,
    icLoadMomentSensorAlarm: Int,
    icHoistBrakeSensor: Int,
    icCraneRailsEn: Int,

){
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
            .padding(start = 170.dp, end = 50.dp, top = 137.dp))
        //Значение максимальной высоты подъема
        BasicParametersText(
            formatDecimalMinus(hoistMaxPosition),
            modifier = Modifier
                .padding(start = 135.dp, end = 50.dp, top = 147.dp))
        //Шкала
        Box(
            modifier = Modifier
                .padding(start = 260.dp, top = 80.dp,),
            contentAlignment = Alignment.TopStart
        ) {
            ScaleVerticalHoist(
                currentValue = hoistPosition,
                minValue = hoistMinPosition,
                maxValue = hoistMaxPosition,
                modifier = Modifier.size(width = 16.dp, height = 150.dp)
            )
        }

//Шкала хода крана. Заголовок
        BasicParametersText(
            stringResource(R.string.Home_MOVE_CRANE),
            modifier = Modifier
            .padding(start = 10.dp, top = 225.dp))
        //Значение максимальной длины кранового пути
        BasicParametersText(
            formatDecimal(craneMaxPosition),
            modifier = Modifier
                .padding(start = 150.dp, end = 50.dp, top = 244.dp))
        Box(
            modifier = Modifier
                .padding(start = 10.dp, top = 245.dp, end = 15.dp),
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
                //Лампа срабатывания ошибки реперной точки
                LampIconInformationFault(
                    isLampIconStatus = icCraneRepPointFault,
                    isIcon = painterResource(R.drawable.ic_home_scale_crane_rep_point_trigger),
                )
                LampIconInformationWorking(
                    isLampIconStatus = 0,
                    isIcon = painterResource(R.drawable.ic_home_scale_giraffe_power),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeperWeightLimit,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
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
        String.format("%05.1f", realValue).replace(',', '.')
    } else {
        String.format("%04.1f", realValue).replace(',', '.')
    }
}

@Preview
@Composable
fun CraneParameterScalePreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        CraneParameterScale(
            weightPercent= 90f,
            hoistPosition = 30f,
            hoistMaxPosition= 3225+60f,
            hoistMinPosition= -5f,
            cranePosition = 20f,
            craneMaxPosition= 40f,
            windCurrent = 10f,
            anemometerStatus = 1,
            windSpeedMontage = 10f,
            windSpeedLvl2 = 10f,
            beeperWeightLimit = 1,

            icMontageMode = 1,
            icCraneRepPointFault= 1,
            icCraneRepPoint= 1,
            icLoadMomentSensorAlarm= 1,
            icHoistBrakeSensor= 1,
            icCraneRailsEn= 1,
            )
    }
}