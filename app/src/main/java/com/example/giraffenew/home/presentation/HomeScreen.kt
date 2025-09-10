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
                weightPercent = homeViewModel.state.value.parameterScale.weightPercent,
                hoistPosition = homeViewModel.state.value.parameterScale.hoistPosition,
                hoistMaxPosition = homeViewModel.state.value.parameterScale.hoistMaxPosition,
                hoistMinPosition = homeViewModel.state.value.parameterScale.hoistMinPosition,
                cranePosition = homeViewModel.state.value.parameterScale.cranePosition,
                craneMaxPosition = homeViewModel.state.value.parameterScale.craneMaxPosition,

                windCurrent = homeViewModel.state.value.parameterScale.windCurrent,
                windSpeedLvl2 = homeViewModel.state.value.parameterScale.windSpeedLvl2,
                windSpeedMontage = homeViewModel.state.value.parameterScale.windSpeedMontage,
                anemometerStatus = homeViewModel.state.value.parameterScale.anemometerStatus,

                beeperWeightLimit = homeViewModel.state.value.parameterScale.beeperWeightLimit,

                icMontageMode = homeViewModel.state.value.parameterScale.icMontageMode,
                icCraneRepPointFault= homeViewModel.state.value.parameterScale.icCraneRepPointFault,
                icCraneRepPoint= homeViewModel.state.value.parameterScale.icCraneRepPoint,
                icLoadMomentSensorAlarm= homeViewModel.state.value.parameterScale.icLoadMomentSensorAlarm,
                icHoistBrakeSensor= homeViewModel.state.value.parameterScale.icHoistBrakeSensor,
                icCraneRailsEn= homeViewModel.state.value.parameterScale.icCraneRailsEn,
            ) }

            Box (modifier = Modifier
                .weight(3f))
            { CraneSOZR(
                hmiCircleSizeTrolley = homeViewModel.state.value.sozrParams.hmiCircleSizeTrolley,
                hmiLineAngle = homeViewModel.state.value.sozrParams.hmiLineAngle,

                hmiTrolleyCoordinateY = homeViewModel.state.value.sozrParams.hmiTrolleyCoordinateY,
                hmiTrolleyCoordinateX = homeViewModel.state.value.sozrParams.hmiTrolleyCoordinateX,

                hmiP1Color = homeViewModel.state.value.sozrParams.hmiP1Color,
                hmiP2Color = homeViewModel.state.value.sozrParams.hmiP2Color,
                hmiP3Color = homeViewModel.state.value.sozrParams.hmiP3Color,
                hmiP4Color = homeViewModel.state.value.sozrParams.hmiP4Color,

                hmiSegmentP1Length00 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length00,
                hmiSegmentP1Length01 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length01,
                hmiSegmentP1Length02 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length02,
                hmiSegmentP1Length03 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length03,
                hmiSegmentP1Length04 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length04,
                hmiSegmentP1Length05 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length05,
                hmiSegmentP1Length06 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length06,
                hmiSegmentP1Length07 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length07,
                hmiSegmentP1Length08 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length08,
                hmiSegmentP1Length09 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length09,
                hmiSegmentP1Length10 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length10,
                hmiSegmentP1Length11 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length11,
                hmiSegmentP1Length12 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length12,
                hmiSegmentP1Length13 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length13,
                hmiSegmentP1Length14 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length14,
                hmiSegmentP1Length15 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length15,
                hmiSegmentP1Length16 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length16,
                hmiSegmentP1Length17 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length17,
                hmiSegmentP1Length18 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length18,
                hmiSegmentP1Length19 = homeViewModel.state.value.polygonP1.hmiSegmentP1Length19,

                pointP1PositionY00 = homeViewModel.state.value.polygonP1.pointP1PositionY00,
                pointP1PositionY01 = homeViewModel.state.value.polygonP1.pointP1PositionY01,
                pointP1PositionY02 = homeViewModel.state.value.polygonP1.pointP1PositionY02,
                pointP1PositionY03 = homeViewModel.state.value.polygonP1.pointP1PositionY03,
                pointP1PositionY04 = homeViewModel.state.value.polygonP1.pointP1PositionY04,
                pointP1PositionY05 = homeViewModel.state.value.polygonP1.pointP1PositionY05,
                pointP1PositionY06 = homeViewModel.state.value.polygonP1.pointP1PositionY06,
                pointP1PositionY07 = homeViewModel.state.value.polygonP1.pointP1PositionY07,
                pointP1PositionY08 = homeViewModel.state.value.polygonP1.pointP1PositionY08,
                pointP1PositionY09 = homeViewModel.state.value.polygonP1.pointP1PositionY09,
                pointP1PositionY10 = homeViewModel.state.value.polygonP1.pointP1PositionY10,
                pointP1PositionY11 = homeViewModel.state.value.polygonP1.pointP1PositionY11,
                pointP1PositionY12 = homeViewModel.state.value.polygonP1.pointP1PositionY12,
                pointP1PositionY13 = homeViewModel.state.value.polygonP1.pointP1PositionY13,
                pointP1PositionY14 = homeViewModel.state.value.polygonP1.pointP1PositionY14,
                pointP1PositionY15 = homeViewModel.state.value.polygonP1.pointP1PositionY15,
                pointP1PositionY16 = homeViewModel.state.value.polygonP1.pointP1PositionY16,
                pointP1PositionY17 = homeViewModel.state.value.polygonP1.pointP1PositionY17,
                pointP1PositionY18 = homeViewModel.state.value.polygonP1.pointP1PositionY18,
                pointP1PositionY19 = homeViewModel.state.value.polygonP1.pointP1PositionY19,

                pointP1PositionX00 = homeViewModel.state.value.polygonP1.pointP1PositionX00,
                pointP1PositionX01 = homeViewModel.state.value.polygonP1.pointP1PositionX01,
                pointP1PositionX02 = homeViewModel.state.value.polygonP1.pointP1PositionX02,
                pointP1PositionX03 = homeViewModel.state.value.polygonP1.pointP1PositionX03,
                pointP1PositionX04 = homeViewModel.state.value.polygonP1.pointP1PositionX04,
                pointP1PositionX05 = homeViewModel.state.value.polygonP1.pointP1PositionX05,
                pointP1PositionX06 = homeViewModel.state.value.polygonP1.pointP1PositionX06,
                pointP1PositionX07 = homeViewModel.state.value.polygonP1.pointP1PositionX07,
                pointP1PositionX08 = homeViewModel.state.value.polygonP1.pointP1PositionX08,
                pointP1PositionX09 = homeViewModel.state.value.polygonP1.pointP1PositionX09,
                pointP1PositionX10 = homeViewModel.state.value.polygonP1.pointP1PositionX10,
                pointP1PositionX11 = homeViewModel.state.value.polygonP1.pointP1PositionX11,
                pointP1PositionX12 = homeViewModel.state.value.polygonP1.pointP1PositionX12,
                pointP1PositionX13 = homeViewModel.state.value.polygonP1.pointP1PositionX13,
                pointP1PositionX14 = homeViewModel.state.value.polygonP1.pointP1PositionX14,
                pointP1PositionX15 = homeViewModel.state.value.polygonP1.pointP1PositionX15,
                pointP1PositionX16 = homeViewModel.state.value.polygonP1.pointP1PositionX16,
                pointP1PositionX17 = homeViewModel.state.value.polygonP1.pointP1PositionX17,
                pointP1PositionX18 = homeViewModel.state.value.polygonP1.pointP1PositionX18,
                pointP1PositionX19 = homeViewModel.state.value.polygonP1.pointP1PositionX19,

                segmentP1Angle00_01 = homeViewModel.state.value.polygonP1.segmentP1Angle00_01,
                segmentP1Angle01_02 = homeViewModel.state.value.polygonP1.segmentP1Angle01_02,
                segmentP1Angle02_03 = homeViewModel.state.value.polygonP1.segmentP1Angle02_03,
                segmentP1Angle03_04 = homeViewModel.state.value.polygonP1.segmentP1Angle03_04,
                segmentP1Angle04_05 = homeViewModel.state.value.polygonP1.segmentP1Angle04_05,
                segmentP1Angle05_06 = homeViewModel.state.value.polygonP1.segmentP1Angle05_06,
                segmentP1Angle06_07 = homeViewModel.state.value.polygonP1.segmentP1Angle06_07,
                segmentP1Angle07_08 = homeViewModel.state.value.polygonP1.segmentP1Angle07_08,
                segmentP1Angle08_09 = homeViewModel.state.value.polygonP1.segmentP1Angle08_09,
                segmentP1Angle09_10 = homeViewModel.state.value.polygonP1.segmentP1Angle09_10,
                segmentP1Angle10_11 = homeViewModel.state.value.polygonP1.segmentP1Angle10_11,
                segmentP1Angle11_12 = homeViewModel.state.value.polygonP1.segmentP1Angle11_12,
                segmentP1Angle12_13 = homeViewModel.state.value.polygonP1.segmentP1Angle12_13,
                segmentP1Angle13_14 = homeViewModel.state.value.polygonP1.segmentP1Angle13_14,
                segmentP1Angle14_15 = homeViewModel.state.value.polygonP1.segmentP1Angle14_15,
                segmentP1Angle15_16 = homeViewModel.state.value.polygonP1.segmentP1Angle15_16,
                segmentP1Angle16_17 = homeViewModel.state.value.polygonP1.segmentP1Angle16_17,
                segmentP1Angle17_18 = homeViewModel.state.value.polygonP1.segmentP1Angle17_18,
                segmentP1Angle18_19 = homeViewModel.state.value.polygonP1.segmentP1Angle18_19,
                segmentP1Angle19_00 = homeViewModel.state.value.polygonP1.segmentP1Angle19_00,

                segmentP1Visibility00 = homeViewModel.state.value.polygonP1.segmentP1Visibility00,
                segmentP1Visibility01 = homeViewModel.state.value.polygonP1.segmentP1Visibility01,
                segmentP1Visibility02 = homeViewModel.state.value.polygonP1.segmentP1Visibility02,
                segmentP1Visibility03 = homeViewModel.state.value.polygonP1.segmentP1Visibility03,
                segmentP1Visibility04 = homeViewModel.state.value.polygonP1.segmentP1Visibility04,
                segmentP1Visibility05 = homeViewModel.state.value.polygonP1.segmentP1Visibility05,
                segmentP1Visibility06 = homeViewModel.state.value.polygonP1.segmentP1Visibility06,
                segmentP1Visibility07 = homeViewModel.state.value.polygonP1.segmentP1Visibility07,
                segmentP1Visibility08 = homeViewModel.state.value.polygonP1.segmentP1Visibility08,
                segmentP1Visibility09 = homeViewModel.state.value.polygonP1.segmentP1Visibility09,
                segmentP1Visibility10 = homeViewModel.state.value.polygonP1.segmentP1Visibility10,
                segmentP1Visibility11 = homeViewModel.state.value.polygonP1.segmentP1Visibility11,
                segmentP1Visibility12 = homeViewModel.state.value.polygonP1.segmentP1Visibility12,
                segmentP1Visibility13 = homeViewModel.state.value.polygonP1.segmentP1Visibility13,
                segmentP1Visibility14 = homeViewModel.state.value.polygonP1.segmentP1Visibility14,
                segmentP1Visibility15 = homeViewModel.state.value.polygonP1.segmentP1Visibility15,
                segmentP1Visibility16 = homeViewModel.state.value.polygonP1.segmentP1Visibility16,
                segmentP1Visibility17 = homeViewModel.state.value.polygonP1.segmentP1Visibility17,
                segmentP1Visibility18 = homeViewModel.state.value.polygonP1.segmentP1Visibility18,
                segmentP1Visibility19 = homeViewModel.state.value.polygonP1.segmentP1Visibility19,

                hmiSegmentP2Length00 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length00,
                hmiSegmentP2Length01 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length01,
                hmiSegmentP2Length02 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length02,
                hmiSegmentP2Length03 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length03,
                hmiSegmentP2Length04 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length04,
                hmiSegmentP2Length05 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length05,
                hmiSegmentP2Length06 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length06,
                hmiSegmentP2Length07 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length07,
                hmiSegmentP2Length08 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length08,
                hmiSegmentP2Length09 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length09,
                hmiSegmentP2Length10 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length10,
                hmiSegmentP2Length11 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length11,
                hmiSegmentP2Length12 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length12,
                hmiSegmentP2Length13 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length13,
                hmiSegmentP2Length14 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length14,
                hmiSegmentP2Length15 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length15,
                hmiSegmentP2Length16 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length16,
                hmiSegmentP2Length17 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length17,
                hmiSegmentP2Length18 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length18,
                hmiSegmentP2Length19 = homeViewModel.state.value.polygonP2.hmiSegmentP2Length19,

                pointP2PositionY00 = homeViewModel.state.value.polygonP2.pointP2PositionY00,
                pointP2PositionY01 = homeViewModel.state.value.polygonP2.pointP2PositionY01,
                pointP2PositionY02 = homeViewModel.state.value.polygonP2.pointP2PositionY02,
                pointP2PositionY03 = homeViewModel.state.value.polygonP2.pointP2PositionY03,
                pointP2PositionY04 = homeViewModel.state.value.polygonP2.pointP2PositionY04,
                pointP2PositionY05 = homeViewModel.state.value.polygonP2.pointP2PositionY05,
                pointP2PositionY06 = homeViewModel.state.value.polygonP2.pointP2PositionY06,
                pointP2PositionY07 = homeViewModel.state.value.polygonP2.pointP2PositionY07,
                pointP2PositionY08 = homeViewModel.state.value.polygonP2.pointP2PositionY08,
                pointP2PositionY09 = homeViewModel.state.value.polygonP2.pointP2PositionY09,
                pointP2PositionY10 = homeViewModel.state.value.polygonP2.pointP2PositionY10,
                pointP2PositionY11 = homeViewModel.state.value.polygonP2.pointP2PositionY11,
                pointP2PositionY12 = homeViewModel.state.value.polygonP2.pointP2PositionY12,
                pointP2PositionY13 = homeViewModel.state.value.polygonP2.pointP2PositionY13,
                pointP2PositionY14 = homeViewModel.state.value.polygonP2.pointP2PositionY14,
                pointP2PositionY15 = homeViewModel.state.value.polygonP2.pointP2PositionY15,
                pointP2PositionY16 = homeViewModel.state.value.polygonP2.pointP2PositionY16,
                pointP2PositionY17 = homeViewModel.state.value.polygonP2.pointP2PositionY17,
                pointP2PositionY18 = homeViewModel.state.value.polygonP2.pointP2PositionY18,
                pointP2PositionY19 = homeViewModel.state.value.polygonP2.pointP2PositionY19,

                pointP2PositionX00 = homeViewModel.state.value.polygonP2.pointP2PositionX00,
                pointP2PositionX01 = homeViewModel.state.value.polygonP2.pointP2PositionX01,
                pointP2PositionX02 = homeViewModel.state.value.polygonP2.pointP2PositionX02,
                pointP2PositionX03 = homeViewModel.state.value.polygonP2.pointP2PositionX03,
                pointP2PositionX04 = homeViewModel.state.value.polygonP2.pointP2PositionX04,
                pointP2PositionX05 = homeViewModel.state.value.polygonP2.pointP2PositionX05,
                pointP2PositionX06 = homeViewModel.state.value.polygonP2.pointP2PositionX06,
                pointP2PositionX07 = homeViewModel.state.value.polygonP2.pointP2PositionX07,
                pointP2PositionX08 = homeViewModel.state.value.polygonP2.pointP2PositionX08,
                pointP2PositionX09 = homeViewModel.state.value.polygonP2.pointP2PositionX09,
                pointP2PositionX10 = homeViewModel.state.value.polygonP2.pointP2PositionX10,
                pointP2PositionX11 = homeViewModel.state.value.polygonP2.pointP2PositionX11,
                pointP2PositionX12 = homeViewModel.state.value.polygonP2.pointP2PositionX12,
                pointP2PositionX13 = homeViewModel.state.value.polygonP2.pointP2PositionX13,
                pointP2PositionX14 = homeViewModel.state.value.polygonP2.pointP2PositionX14,
                pointP2PositionX15 = homeViewModel.state.value.polygonP2.pointP2PositionX15,
                pointP2PositionX16 = homeViewModel.state.value.polygonP2.pointP2PositionX16,
                pointP2PositionX17 = homeViewModel.state.value.polygonP2.pointP2PositionX17,
                pointP2PositionX18 = homeViewModel.state.value.polygonP2.pointP2PositionX18,
                pointP2PositionX19 = homeViewModel.state.value.polygonP2.pointP2PositionX19,

                segmentP2Angle00_01 = homeViewModel.state.value.polygonP2.segmentP2Angle00_01,
                segmentP2Angle01_02 = homeViewModel.state.value.polygonP2.segmentP2Angle01_02,
                segmentP2Angle02_03 = homeViewModel.state.value.polygonP2.segmentP2Angle02_03,
                segmentP2Angle03_04 = homeViewModel.state.value.polygonP2.segmentP2Angle03_04,
                segmentP2Angle04_05 = homeViewModel.state.value.polygonP2.segmentP2Angle04_05,
                segmentP2Angle05_06 = homeViewModel.state.value.polygonP2.segmentP2Angle05_06,
                segmentP2Angle06_07 = homeViewModel.state.value.polygonP2.segmentP2Angle06_07,
                segmentP2Angle07_08 = homeViewModel.state.value.polygonP2.segmentP2Angle07_08,
                segmentP2Angle08_09 = homeViewModel.state.value.polygonP2.segmentP2Angle08_09,
                segmentP2Angle09_10 = homeViewModel.state.value.polygonP2.segmentP2Angle09_10,
                segmentP2Angle10_11 = homeViewModel.state.value.polygonP2.segmentP2Angle10_11,
                segmentP2Angle11_12 = homeViewModel.state.value.polygonP2.segmentP2Angle11_12,
                segmentP2Angle12_13 = homeViewModel.state.value.polygonP2.segmentP2Angle12_13,
                segmentP2Angle13_14 = homeViewModel.state.value.polygonP2.segmentP2Angle13_14,
                segmentP2Angle14_15 = homeViewModel.state.value.polygonP2.segmentP2Angle14_15,
                segmentP2Angle15_16 = homeViewModel.state.value.polygonP2.segmentP2Angle15_16,
                segmentP2Angle16_17 = homeViewModel.state.value.polygonP2.segmentP2Angle16_17,
                segmentP2Angle17_18 = homeViewModel.state.value.polygonP2.segmentP2Angle17_18,
                segmentP2Angle18_19 = homeViewModel.state.value.polygonP2.segmentP2Angle18_19,
                segmentP2Angle19_00 = homeViewModel.state.value.polygonP2.segmentP2Angle19_00,

                segmentP2Visibility00 = homeViewModel.state.value.polygonP2.segmentP2Visibility00,
                segmentP2Visibility01 = homeViewModel.state.value.polygonP2.segmentP2Visibility01,
                segmentP2Visibility02 = homeViewModel.state.value.polygonP2.segmentP2Visibility02,
                segmentP2Visibility03 = homeViewModel.state.value.polygonP2.segmentP2Visibility03,
                segmentP2Visibility04 = homeViewModel.state.value.polygonP2.segmentP2Visibility04,
                segmentP2Visibility05 = homeViewModel.state.value.polygonP2.segmentP2Visibility05,
                segmentP2Visibility06 = homeViewModel.state.value.polygonP2.segmentP2Visibility06,
                segmentP2Visibility07 = homeViewModel.state.value.polygonP2.segmentP2Visibility07,
                segmentP2Visibility08 = homeViewModel.state.value.polygonP2.segmentP2Visibility08,
                segmentP2Visibility09 = homeViewModel.state.value.polygonP2.segmentP2Visibility09,
                segmentP2Visibility10 = homeViewModel.state.value.polygonP2.segmentP2Visibility10,
                segmentP2Visibility11 = homeViewModel.state.value.polygonP2.segmentP2Visibility11,
                segmentP2Visibility12 = homeViewModel.state.value.polygonP2.segmentP2Visibility12,
                segmentP2Visibility13 = homeViewModel.state.value.polygonP2.segmentP2Visibility13,
                segmentP2Visibility14 = homeViewModel.state.value.polygonP2.segmentP2Visibility14,
                segmentP2Visibility15 = homeViewModel.state.value.polygonP2.segmentP2Visibility15,
                segmentP2Visibility16 = homeViewModel.state.value.polygonP2.segmentP2Visibility16,
                segmentP2Visibility17 = homeViewModel.state.value.polygonP2.segmentP2Visibility17,
                segmentP2Visibility18 = homeViewModel.state.value.polygonP2.segmentP2Visibility18,
                segmentP2Visibility19 = homeViewModel.state.value.polygonP2.segmentP2Visibility19,
            ) }

            Box (modifier = Modifier
                .weight(1f))
            { Information(
                icBuildMode = homeViewModel.state.value.information.icBuildMode,
                icServiceMode = homeViewModel.state.value.information.icServiceMode,

                icACSSPWR = homeViewModel.state.value.information.icACSSPWR,
                icACSSFault = homeViewModel.state.value.information.icACSSFault,
                icLukStatus = homeViewModel.state.value.information.icLukStatus,
                icLineContactorZeroPosAlarm = homeViewModel.state.value.information.icLineContactorZeroPosAlarm,
                icMicroclimateHeaterFault = homeViewModel.state.value.information.icMicroclimateHeaterFault,
                icBrakePowerSupplyFault = homeViewModel.state.value.information.icBrakePowerSupplyFault,
                icVentilationPowerSupplyFault = homeViewModel.state.value.information.icVentilationPowerSupplyFault,
                icHoistJoystickError = homeViewModel.state.value.information.icHoistJoystickError,
                icSlewJoystickError = homeViewModel.state.value.information.icSlewJoystickError,
                icTrolleyJoystickError = homeViewModel.state.value.information.icTrolleyJoystickError,
                icCraneJoystickError = homeViewModel.state.value.information.icCraneJoystickError,
                icCraneEncoderFault = homeViewModel.state.value.information.icCraneEncoderFault,
                icHoistEncoderFault = homeViewModel.state.value.information.icHoistEncoderFault,
                icSlewEncoderFault = homeViewModel.state.value.information.icSlewEncoderFault,
                icTrolleyEncoderFault = homeViewModel.state.value.information.icTrolleyEncoderFault,
                icPhaseControlRelayFault = homeViewModel.state.value.information.icPhaseControlRelayFault,
                icPowerSupplyNetworkFault = homeViewModel.state.value.information.icPowerSupplyNetworkFault,
                icHoistDriveError = homeViewModel.state.value.information.icHoistDriveError,
                icSlewDriveError = homeViewModel.state.value.information.icSlewDriveError,
                icTrolleyDriveError = homeViewModel.state.value.information.icTrolleyDriveError,
                icCraneDriveError = homeViewModel.state.value.information.icCraneDriveError,
                icHoistDrivePowerError = homeViewModel.state.value.information.icHoistDrivePowerError,
                icSlewDrivePowerError = homeViewModel.state.value.information.icSlewDrivePowerError,
                icTrolleyDrivePowerError = homeViewModel.state.value.information.icTrolleyDrivePowerError,
                icCraneDrivePowerError = homeViewModel.state.value.information.icCraneDrivePowerError,
                icCANOpenSensorsError = homeViewModel.state.value.information.icCANOpenSensorsError,
                icCANOpenDrivesError = homeViewModel.state.value.information.icCANOpenDrivesError,
                ) }
        }

//№.2 - Средняя область для отображения параметров механизмов крана
        Column (modifier = Modifier
            .weight(1f)) {
            CraneMechanismsCondition(
                weightSensorErr = homeViewModel.state.value.craneMechanismsCondition.weightSensorErr,
                anemometerSensorErr= homeViewModel.state.value.craneMechanismsCondition.anemometerSensorErr,
                trolleyFwdStatus = homeViewModel.state.value.craneMechanismsCondition.trolleyFwdStatus,
                trolleyRevStatus = homeViewModel.state.value.craneMechanismsCondition.trolleyRevStatus,
                hoistUpStatus = homeViewModel.state.value.craneMechanismsCondition.hoistUpStatus,
                hoistDownStatus = homeViewModel.state.value.craneMechanismsCondition.hoistDownStatus,
                slewRightStatus = homeViewModel.state.value.craneMechanismsCondition.slewRightStatus,
                slewLeftStatus = homeViewModel.state.value.craneMechanismsCondition.slewLeftStatus,
                cranRightStatus = homeViewModel.state.value.craneMechanismsCondition.cranRightStatus,
                craneLeftStatus = homeViewModel.state.value.craneMechanismsCondition.craneLeftStatus,
                craneRight = homeViewModel.state.value.craneMechanismsCondition.craneRight,
                craneLeft = homeViewModel.state.value.craneMechanismsCondition.craneLeft,
                slewLeft = homeViewModel.state.value.craneMechanismsCondition.slewLeft,
                slewRight = homeViewModel.state.value.craneMechanismsCondition.slewRight,
                trolleyFwd = homeViewModel.state.value.craneMechanismsCondition.trolleyFwd,
                trolleyRev = homeViewModel.state.value.craneMechanismsCondition.trolleyRev,
                hoistUp = homeViewModel.state.value.craneMechanismsCondition.hoistUp,
                hoistDown = homeViewModel.state.value.craneMechanismsCondition.hoistDown,
                alarmLampStatus = homeViewModel.state.value.craneMechanismsCondition.alarmLampStatus,
            )
        }

//№.3 - Левая область для отображения параметров работы крана
        Column(modifier = Modifier
            .fillMaxSize()
            .weight(1f),)
        {
            СraneOperatingParameters(
                weightQnom = homeViewModel.state.value.operatingParams.weightQnom,
                weightCurrent = homeViewModel.state.value.operatingParams.weightCurrent,
                trolleyCurrent = homeViewModel.state.value.operatingParams.trolleyCurrent,
                slewingCurrent = homeViewModel.state.value.operatingParams.slewingCurrent,
                heightCurrent = homeViewModel.state.value.operatingParams.heightCurrent,
                moveCurrent = homeViewModel.state.value.operatingParams.moveCurrent,
                windCurrent = homeViewModel.state.value.operatingParams.windCurrent,
                voltageL1L2Current = homeViewModel.state.value.operatingParams.voltageL1L2Current,
                voltageL2L3Current = homeViewModel.state.value.operatingParams.voltageL2L3Current,
                voltageL1L3Current = homeViewModel.state.value.operatingParams.voltageL1L3Current,
            )
        }
    }
}