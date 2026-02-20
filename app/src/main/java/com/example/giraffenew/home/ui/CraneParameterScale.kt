package com.example.giraffenew.home.ui

import android.util.Log
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.giraffenew.ui.theme.DarkLemon
import com.example.giraffenew.R
import com.example.giraffenew.home.ui.scale.ScaleHorizonWeightHi
import com.example.giraffenew.home.ui.scale.ScaleHorizonWeightHiHi
import com.example.giraffenew.home.ui.scale.ScaleHorizonWeightMax
import com.example.giraffenew.core.ui_kit.composable.text.BasicParametersText
import com.example.giraffenew.home.presentation.HomeScreenViewModel
import com.example.giraffenew.home.ui.button.ButtonIconSound
import com.example.giraffenew.home.ui.lamp.LampHookStatus
import com.example.giraffenew.home.ui.lamp.LampIconInformationAlarm
import com.example.giraffenew.home.ui.lamp.LampIconInformationBrakeHoist
import com.example.giraffenew.home.ui.lamp.LampIconInformationDebugMode
import com.example.giraffenew.home.ui.lamp.LampIconInformationTO
import com.example.giraffenew.home.ui.lamp.LampIconInformationWorking
import com.example.giraffenew.home.ui.lamp.LampWindStatus
import com.example.giraffenew.home.ui.scale.ScaleVerticalHoist
import com.example.giraffenew.home.ui.scale.ScaleVerticalWind
import com.example.giraffenew.navigate.presentation.TopBarViewModel
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

    val icalarmTO = parameterScale.icAlarmTO
    val ictechnicalReglamentTO = parameterScale.icTechnicalReglamentTO

    val icMontageMode = parameterScale.icMontageMode
    val icCraneRepPointFault = parameterScale.icCraneRepPointFault
    val icCraneRepPoint = parameterScale.icCraneRepPoint
    val icLoadMomentSensorAlarm = parameterScale.icLoadMomentSensorAlarm
    val icHoistBrakeSensor = parameterScale.icHoistBrakeSensor
    val icCraneRailsEn = parameterScale.icCraneRailsEn

    val viewModel: TopBarViewModel = hiltViewModel()
    Log.d("CraneParameterScale", "Current volume: ${viewModel.currentVolume.value}")

    val currentVolume = viewModel.currentVolume.value

    val icHoistHookAlarmStatus = parameterScale.icHoistHookAlarmStatus

    val myBoxMainModifier = Modifier
        .fillMaxSize()
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .border(
            width = 2.dp,
            color = DarkLemon,
            shape = RoundedCornerShape(10.dp)
        )

    //Бокс 1 - для крана с ГХ
    Box(myBoxMainModifier){
//Отрисовываем кнопку регулировки уровня громкости
        Box(modifier = Modifier
            .padding(start = 10.dp, top =10.dp, end = 100.dp, bottom = 100.dp),
            contentAlignment = Alignment.TopStart)
        {
            ButtonIconSound(
                currentVolume = currentVolume,
                onClick ={viewModel.toggleVolume()},
                modifier = Modifier.size(width = 12.dp, height = 40.dp)
            )
        }

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
            .padding(start = 10.dp, top =90.dp, end = 10.dp),
            contentAlignment = Alignment.TopStart) {
            LampWindStatus(
                isLampStatus = anemometerStatus
            )
        }
        val imageVectorWind = when (anemometerStatus) {
            0 -> ImageVector.vectorResource(R.drawable.ic_home_scale_wind_power_48)
            1 -> ImageVector.vectorResource(R.drawable.ic_home_scale_wind_power_48_dark_gray)
            2 -> ImageVector.vectorResource(R.drawable.ic_home_scale_wind_power_48)
            else -> ImageVector.vectorResource(R.drawable.ic_home_scale_wind_power_48)
        }
        Box(modifier = Modifier
            .padding(start = 10.dp, top =90.dp, end = 10.dp),
            contentAlignment = Alignment.TopStart) {
            Image(
                imageVector = imageVectorWind,
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
            .padding(start = 55.dp, top =90.dp, end = 10.dp),
            contentAlignment = Alignment.TopStart) {
            ScaleVerticalWind(
                currentValue = windCurrent,
                minValue = 0f,
                maxValue = maxValue,
                anemometerStatus = anemometerStatus,
                modifier = Modifier.size(width = 12.dp, height = 40.dp)
            )
        }

//Отрисовывем статус Аварии, Самопроизвольное движение крюка вниз
        val imageVectorHook = when (icHoistHookAlarmStatus) {
            0 -> ImageVector.vectorResource(R.drawable.ic_home_hook_down_alarm_dark_gray)
            1 -> ImageVector.vectorResource(R.drawable.ic_home_hook_down_alarm_red)
            else -> ImageVector.vectorResource(R.drawable.ic_home_hook_down_alarm_dark_gray)
        }
        Box(modifier = Modifier
            .padding(start = 180.dp, top =90.dp, end = 10.dp),
            contentAlignment = Alignment.TopStart) {
            LampHookStatus(
                isLampStatus = icHoistHookAlarmStatus)
            Image(
                imageVector = imageVectorHook,
                contentDescription = null,
                modifier = Modifier.size(80.dp),
            )

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
            )
        }

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
            parameterScale = HomeScreenViewModel.CraneParameterScaleState(
                weightPercent = 1f,
                hoistPosition = 40f,
                hoistMaxPosition = 3278f,
                hoistMinPosition = 0f,
                cranePosition = 20f,
                craneMaxPosition = 40f,

                windCurrent = 10f,
                windSpeedLvl1 = 15f,
                windSpeedLvl2 = 18f,
                windSpeedMontage = 10f,
                anemometerStatus = 1,

                icMontageMode         = 1, //Флаг установки режима Монтаж.
                icCraneRepPointFault  = 1, //Сбой датчика реперной точки хода крана
                icCraneRepPoint       = 1, //Реперная точка хода крана. Индикатор срабатывания
                icLoadMomentSensorAlarm = 1, //Авария датчика критического грузового момента.
                icHoistBrakeSensor    = 0, //Контроль износа тормоза подъема. 1-норма. 0-Авария
                icCraneRailsEn        = 1, //Флаг включения перемещения крана. 1 - Ход крана активирован

                icTechnicalReglamentTO = 1, //Флаг проведите ТО
                icAlarmTO = 1, //Флаг. ТО не проведено. Ограничение скорости
            )
        )
    }
}