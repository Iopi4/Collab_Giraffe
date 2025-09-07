package com.example.giraffenew.home.presentation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.giraffenew.R
import com.example.giraffenew.home.ui.CraneMechanismsCondition
import com.example.giraffenew.home.ui.CraneParameterScale
import com.example.giraffenew.home.ui.CraneSOZR
import com.example.giraffenew.home.ui.Information
import com.example.giraffenew.home.ui.СraneOperatingParameters

@Composable
internal fun HomeScreen (
    homeViewModel: HomeScreenViewModel = hiltViewModel() // Получаем ViewModel для HomeScreen
){

    // 1. Получаем текущий LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current

    // 2. Создаем Lifecycle-aware эффект
    DisposableEffect(lifecycleOwner, homeViewModel) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("HomeScreen", "Lifecycle: ON_RESUME - resuming data collection")
                    homeViewModel.setActiveConfiguration()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("HomeScreen", "Lifecycle: ON_PAUSE    - pausing data collection")
                    homeViewModel.clearActiveConfiguration()
                }
                else -> {Log.e("HomeScreen", "Lifecycle: NOT START")}
            }
        }
        // 3. Регистрируем наблюдатель
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        // 4. Очистка при удалении Composable
        onDispose {
            Log.d("HomeScreen", "Lifecycle: ON_DISPOSE - removing observer")
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            homeViewModel.clearActiveConfiguration() // Дополнительная очистка
        }
    }

    Box(
        modifier = Modifier
            .padding()
            .fillMaxSize()
            .background(colorResource(R.color.black))
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.background_home_portrait),

            contentDescription = "Фон главного экрана",
            modifier = Modifier.padding(5.dp)
        )
    }

//Создаем область в котором вертикально разместим три области №1, №2, №3
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
//№.1 - Левая область для отображения двух основных окон. (1- кран с ГХ, 2-Стрела и СОЗР. 3 - Статус и сообщения)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Box (modifier = Modifier
                .weight(3f))
            { CraneParameterScale(
                weightPercent = homeViewModel.state.value.weightPercent,
                hoistPosition = homeViewModel.state.value.hoistPosition,
                hoistMaxPosition = homeViewModel.state.value.hoistMaxPosition,
                hoistMinPosition = homeViewModel.state.value.hoistMinPosition,
                cranePosition = homeViewModel.state.value.cranePosition,
                craneMaxPosition = homeViewModel.state.value.craneMaxPosition,
                anemometerStatus = homeViewModel.state.value.anemometerStatus,
                windCurrent = homeViewModel.state.value.windCurrent,
                windSpeedLvl2 = homeViewModel.state.value.windSpeedLvl2,
                windSpeedMontage = homeViewModel.state.value.windSpeedMontage,
                montageMode = homeViewModel.state.value.montageMode,
                beeper = homeViewModel.state.value.beeper,
            ) }

            Box (modifier = Modifier
                .weight(3f))
            { CraneSOZR(
                hmiCircleSizeTrolley = homeViewModel.state.value.hmiCircleSizeTrolley,
                hmiLineAngle = homeViewModel.state.value.hmiLineAngle,

                hmiTrolleyCoordinateY = homeViewModel.state.value.hmiTrolleyCoordinateY,
                hmiTrolleyCoordinateX = homeViewModel.state.value.hmiTrolleyCoordinateX,

                hmiP1Color = homeViewModel.state.value.hmiP1Color,
                hmiP2Color = homeViewModel.state.value.hmiP2Color,
                hmiP3Color = homeViewModel.state.value.hmiP3Color,
                hmiP4Color = homeViewModel.state.value.hmiP4Color,

                hmiSegmentP1Length00 = homeViewModel.state.value.hmiSegmentP1Length00,
                hmiSegmentP1Length01 = homeViewModel.state.value.hmiSegmentP1Length01,
                hmiSegmentP1Length02 = homeViewModel.state.value.hmiSegmentP1Length02,
                hmiSegmentP1Length03 = homeViewModel.state.value.hmiSegmentP1Length03,
                hmiSegmentP1Length04 = homeViewModel.state.value.hmiSegmentP1Length04,
                hmiSegmentP1Length05 = homeViewModel.state.value.hmiSegmentP1Length05,
                hmiSegmentP1Length06 = homeViewModel.state.value.hmiSegmentP1Length06,
                hmiSegmentP1Length07 = homeViewModel.state.value.hmiSegmentP1Length07,
                hmiSegmentP1Length08 = homeViewModel.state.value.hmiSegmentP1Length08,
                hmiSegmentP1Length09 = homeViewModel.state.value.hmiSegmentP1Length09,
                hmiSegmentP1Length10 = homeViewModel.state.value.hmiSegmentP1Length10,
                hmiSegmentP1Length11 = homeViewModel.state.value.hmiSegmentP1Length11,
                hmiSegmentP1Length12 = homeViewModel.state.value.hmiSegmentP1Length12,
                hmiSegmentP1Length13 = homeViewModel.state.value.hmiSegmentP1Length13,
                hmiSegmentP1Length14 = homeViewModel.state.value.hmiSegmentP1Length14,
                hmiSegmentP1Length15 = homeViewModel.state.value.hmiSegmentP1Length15,
                hmiSegmentP1Length16 = homeViewModel.state.value.hmiSegmentP1Length16,
                hmiSegmentP1Length17 = homeViewModel.state.value.hmiSegmentP1Length17,
                hmiSegmentP1Length18 = homeViewModel.state.value.hmiSegmentP1Length18,
                hmiSegmentP1Length19 = homeViewModel.state.value.hmiSegmentP1Length19,

                pointP1PositionY00 = homeViewModel.state.value.pointP1PositionY00,
                pointP1PositionY01 = homeViewModel.state.value.pointP1PositionY01,
                pointP1PositionY02 = homeViewModel.state.value.pointP1PositionY02,
                pointP1PositionY03 = homeViewModel.state.value.pointP1PositionY03,
                pointP1PositionY04 = homeViewModel.state.value.pointP1PositionY04,
                pointP1PositionY05 = homeViewModel.state.value.pointP1PositionY05,
                pointP1PositionY06 = homeViewModel.state.value.pointP1PositionY06,
                pointP1PositionY07 = homeViewModel.state.value.pointP1PositionY07,
                pointP1PositionY08 = homeViewModel.state.value.pointP1PositionY08,
                pointP1PositionY09 = homeViewModel.state.value.pointP1PositionY09,
                pointP1PositionY10 = homeViewModel.state.value.pointP1PositionY10,
                pointP1PositionY11 = homeViewModel.state.value.pointP1PositionY11,
                pointP1PositionY12 = homeViewModel.state.value.pointP1PositionY12,
                pointP1PositionY13 = homeViewModel.state.value.pointP1PositionY13,
                pointP1PositionY14 = homeViewModel.state.value.pointP1PositionY14,
                pointP1PositionY15 = homeViewModel.state.value.pointP1PositionY15,
                pointP1PositionY16 = homeViewModel.state.value.pointP1PositionY16,
                pointP1PositionY17 = homeViewModel.state.value.pointP1PositionY17,
                pointP1PositionY18 = homeViewModel.state.value.pointP1PositionY18,
                pointP1PositionY19 = homeViewModel.state.value.pointP1PositionY19,

                pointP1PositionX00 = homeViewModel.state.value.pointP1PositionX00,
                pointP1PositionX01 = homeViewModel.state.value.pointP1PositionX01,
                pointP1PositionX02 = homeViewModel.state.value.pointP1PositionX02,
                pointP1PositionX03 = homeViewModel.state.value.pointP1PositionX03,
                pointP1PositionX04 = homeViewModel.state.value.pointP1PositionX04,
                pointP1PositionX05 = homeViewModel.state.value.pointP1PositionX05,
                pointP1PositionX06 = homeViewModel.state.value.pointP1PositionX06,
                pointP1PositionX07 = homeViewModel.state.value.pointP1PositionX07,
                pointP1PositionX08 = homeViewModel.state.value.pointP1PositionX08,
                pointP1PositionX09 = homeViewModel.state.value.pointP1PositionX09,
                pointP1PositionX10 = homeViewModel.state.value.pointP1PositionX10,
                pointP1PositionX11 = homeViewModel.state.value.pointP1PositionX11,
                pointP1PositionX12 = homeViewModel.state.value.pointP1PositionX12,
                pointP1PositionX13 = homeViewModel.state.value.pointP1PositionX13,
                pointP1PositionX14 = homeViewModel.state.value.pointP1PositionX14,
                pointP1PositionX15 = homeViewModel.state.value.pointP1PositionX15,
                pointP1PositionX16 = homeViewModel.state.value.pointP1PositionX16,
                pointP1PositionX17 = homeViewModel.state.value.pointP1PositionX17,
                pointP1PositionX18 = homeViewModel.state.value.pointP1PositionX18,
                pointP1PositionX19 = homeViewModel.state.value.pointP1PositionX19,

                segmentP1Angle00_01 = homeViewModel.state.value.segmentP1Angle00_01,
                segmentP1Angle01_02 = homeViewModel.state.value.segmentP1Angle01_02,
                segmentP1Angle02_03 = homeViewModel.state.value.segmentP1Angle02_03,
                segmentP1Angle03_04 = homeViewModel.state.value.segmentP1Angle03_04,
                segmentP1Angle04_05 = homeViewModel.state.value.segmentP1Angle04_05,
                segmentP1Angle05_06 = homeViewModel.state.value.segmentP1Angle05_06,
                segmentP1Angle06_07 = homeViewModel.state.value.segmentP1Angle06_07,
                segmentP1Angle07_08 = homeViewModel.state.value.segmentP1Angle07_08,
                segmentP1Angle08_09 = homeViewModel.state.value.segmentP1Angle08_09,
                segmentP1Angle09_10 = homeViewModel.state.value.segmentP1Angle09_10,
                segmentP1Angle10_11 = homeViewModel.state.value.segmentP1Angle10_11,
                segmentP1Angle11_12 = homeViewModel.state.value.segmentP1Angle11_12,
                segmentP1Angle12_13 = homeViewModel.state.value.segmentP1Angle12_13,
                segmentP1Angle13_14 = homeViewModel.state.value.segmentP1Angle13_14,
                segmentP1Angle14_15 = homeViewModel.state.value.segmentP1Angle14_15,
                segmentP1Angle15_16 = homeViewModel.state.value.segmentP1Angle15_16,
                segmentP1Angle16_17 = homeViewModel.state.value.segmentP1Angle16_17,
                segmentP1Angle17_18 = homeViewModel.state.value.segmentP1Angle17_18,
                segmentP1Angle18_19 = homeViewModel.state.value.segmentP1Angle18_19,
                segmentP1Angle19_00 = homeViewModel.state.value.segmentP1Angle19_00,

                segmentP1Visibility00 = homeViewModel.state.value.segmentP1Visibility00,
                segmentP1Visibility01 = homeViewModel.state.value.segmentP1Visibility01,
                segmentP1Visibility02 = homeViewModel.state.value.segmentP1Visibility02,
                segmentP1Visibility03 = homeViewModel.state.value.segmentP1Visibility03,
                segmentP1Visibility04 = homeViewModel.state.value.segmentP1Visibility04,
                segmentP1Visibility05 = homeViewModel.state.value.segmentP1Visibility05,
                segmentP1Visibility06 = homeViewModel.state.value.segmentP1Visibility06,
                segmentP1Visibility07 = homeViewModel.state.value.segmentP1Visibility07,
                segmentP1Visibility08 = homeViewModel.state.value.segmentP1Visibility08,
                segmentP1Visibility09 = homeViewModel.state.value.segmentP1Visibility09,
                segmentP1Visibility10 = homeViewModel.state.value.segmentP1Visibility10,
                segmentP1Visibility11 = homeViewModel.state.value.segmentP1Visibility11,
                segmentP1Visibility12 = homeViewModel.state.value.segmentP1Visibility12,
                segmentP1Visibility13 = homeViewModel.state.value.segmentP1Visibility13,
                segmentP1Visibility14 = homeViewModel.state.value.segmentP1Visibility14,
                segmentP1Visibility15 = homeViewModel.state.value.segmentP1Visibility15,
                segmentP1Visibility16 = homeViewModel.state.value.segmentP1Visibility16,
                segmentP1Visibility17 = homeViewModel.state.value.segmentP1Visibility17,
                segmentP1Visibility18 = homeViewModel.state.value.segmentP1Visibility18,
                segmentP1Visibility19 = homeViewModel.state.value.segmentP1Visibility19,
            ) }

            Box (modifier = Modifier
                .weight(1f))
            { Information(
                    beeper = homeViewModel.state.value.beeper,
                ) }

        }

//№.2 - Средняя область для отображения параметров механизмов крана
        Column (modifier = Modifier
            .weight(1f)) {

            CraneMechanismsCondition(
                weightSensorErr = homeViewModel.state.value.weightSensorErr,
                anemometerSensorErr= homeViewModel.state.value.anemometerSensorErr,
                trolleyFwdStatus = homeViewModel.state.value.trolleyFwdStatus,
                trolleyRevStatus = homeViewModel.state.value.trolleyRevStatus,
                hoistUpStatus = homeViewModel.state.value.hoistUpStatus,
                hoistDownStatus = homeViewModel.state.value.hoistDownStatus,
                slewRightStatus = homeViewModel.state.value.slewRightStatus,
                slewLeftStatus = homeViewModel.state.value.slewLeftStatus,
                cranRightStatus = homeViewModel.state.value.cranRightStatus,
                craneLeftStatus = homeViewModel.state.value.craneLeftStatus,
                craneRight = homeViewModel.state.value.craneRight,
                craneLeft = homeViewModel.state.value.craneLeft,
                slewLeft = homeViewModel.state.value.slewLeft,
                slewRight = homeViewModel.state.value.slewRight,
                trolleyFwd = homeViewModel.state.value.trolleyFwd,
                trolleyRev = homeViewModel.state.value.trolleyRev,
                hoistUp = homeViewModel.state.value.hoistUp,
                hoistDown = homeViewModel.state.value.hoistDown,
                alarmLampStatus = homeViewModel.state.value.alarmLampStatus,
            )
        }

//№.3 - Левая область для отображения параметров работы крана
        Column(modifier = Modifier
            .fillMaxSize()
            .weight(1f),)
        {
            СraneOperatingParameters(
                weightQnom = homeViewModel.state.value.weightQnom,
                weightCurrent = homeViewModel.state.value.weightCurrent,
                trolleyCurrent = homeViewModel.state.value.trolleyCurrent,
                slewingCurrent = homeViewModel.state.value.slewingCurrent,
                heightCurrent = homeViewModel.state.value.heightCurrent,
                moveCurrent = homeViewModel.state.value.moveCurrent,
                windCurrent = homeViewModel.state.value.windCurrent,
                voltageL1L2Current = homeViewModel.state.value.voltageL1L2Current,
                voltageL2L3Current = homeViewModel.state.value.voltageL2L3Current,
                voltageL1L3Current = homeViewModel.state.value.voltageL1L3Current,
            )
        }
    }
}