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

                icalarmTO = homeViewModel.state.value.parameterScale.icalarmTO,
                ictechnicalReglamentTO = homeViewModel.state.value.parameterScale.ictechnicalReglamentTO,

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

                segmentP1Angle00 = homeViewModel.state.value.polygonP1.segmentP1Angle00,
                segmentP1Angle01 = homeViewModel.state.value.polygonP1.segmentP1Angle01,
                segmentP1Angle02 = homeViewModel.state.value.polygonP1.segmentP1Angle02,
                segmentP1Angle03 = homeViewModel.state.value.polygonP1.segmentP1Angle03,
                segmentP1Angle04 = homeViewModel.state.value.polygonP1.segmentP1Angle04,
                segmentP1Angle05 = homeViewModel.state.value.polygonP1.segmentP1Angle05,
                segmentP1Angle06 = homeViewModel.state.value.polygonP1.segmentP1Angle06,
                segmentP1Angle07 = homeViewModel.state.value.polygonP1.segmentP1Angle07,
                segmentP1Angle08 = homeViewModel.state.value.polygonP1.segmentP1Angle08,
                segmentP1Angle09 = homeViewModel.state.value.polygonP1.segmentP1Angle09,
                segmentP1Angle10 = homeViewModel.state.value.polygonP1.segmentP1Angle10,
                segmentP1Angle11 = homeViewModel.state.value.polygonP1.segmentP1Angle11,
                segmentP1Angle12 = homeViewModel.state.value.polygonP1.segmentP1Angle12,
                segmentP1Angle13 = homeViewModel.state.value.polygonP1.segmentP1Angle13,
                segmentP1Angle14 = homeViewModel.state.value.polygonP1.segmentP1Angle14,
                segmentP1Angle15 = homeViewModel.state.value.polygonP1.segmentP1Angle15,
                segmentP1Angle16 = homeViewModel.state.value.polygonP1.segmentP1Angle16,
                segmentP1Angle17 = homeViewModel.state.value.polygonP1.segmentP1Angle17,
                segmentP1Angle18 = homeViewModel.state.value.polygonP1.segmentP1Angle18,
                segmentP1Angle19 = homeViewModel.state.value.polygonP1.segmentP1Angle19,

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

                segmentP2Angle00 = homeViewModel.state.value.polygonP2.segmentP2Angle00,
                segmentP2Angle01 = homeViewModel.state.value.polygonP2.segmentP2Angle01,
                segmentP2Angle02 = homeViewModel.state.value.polygonP2.segmentP2Angle02,
                segmentP2Angle03 = homeViewModel.state.value.polygonP2.segmentP2Angle03,
                segmentP2Angle04 = homeViewModel.state.value.polygonP2.segmentP2Angle04,
                segmentP2Angle05 = homeViewModel.state.value.polygonP2.segmentP2Angle05,
                segmentP2Angle06 = homeViewModel.state.value.polygonP2.segmentP2Angle06,
                segmentP2Angle07 = homeViewModel.state.value.polygonP2.segmentP2Angle07,
                segmentP2Angle08 = homeViewModel.state.value.polygonP2.segmentP2Angle08,
                segmentP2Angle09 = homeViewModel.state.value.polygonP2.segmentP2Angle09,
                segmentP2Angle10 = homeViewModel.state.value.polygonP2.segmentP2Angle10,
                segmentP2Angle11 = homeViewModel.state.value.polygonP2.segmentP2Angle11,
                segmentP2Angle12 = homeViewModel.state.value.polygonP2.segmentP2Angle12,
                segmentP2Angle13 = homeViewModel.state.value.polygonP2.segmentP2Angle13,
                segmentP2Angle14 = homeViewModel.state.value.polygonP2.segmentP2Angle14,
                segmentP2Angle15 = homeViewModel.state.value.polygonP2.segmentP2Angle15,
                segmentP2Angle16 = homeViewModel.state.value.polygonP2.segmentP2Angle16,
                segmentP2Angle17 = homeViewModel.state.value.polygonP2.segmentP2Angle17,
                segmentP2Angle18 = homeViewModel.state.value.polygonP2.segmentP2Angle18,
                segmentP2Angle19 = homeViewModel.state.value.polygonP2.segmentP2Angle19,

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

                hmiSegmentP3Length00 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length00,
                hmiSegmentP3Length01 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length01,
                hmiSegmentP3Length02 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length02,
                hmiSegmentP3Length03 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length03,
                hmiSegmentP3Length04 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length04,
                hmiSegmentP3Length05 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length05,
                hmiSegmentP3Length06 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length06,
                hmiSegmentP3Length07 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length07,
                hmiSegmentP3Length08 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length08,
                hmiSegmentP3Length09 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length09,
                hmiSegmentP3Length10 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length10,
                hmiSegmentP3Length11 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length11,
                hmiSegmentP3Length12 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length12,
                hmiSegmentP3Length13 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length13,
                hmiSegmentP3Length14 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length14,
                hmiSegmentP3Length15 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length15,
                hmiSegmentP3Length16 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length16,
                hmiSegmentP3Length17 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length17,
                hmiSegmentP3Length18 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length18,
                hmiSegmentP3Length19 = homeViewModel.state.value.polygonP3.hmiSegmentP3Length19,

                pointP3PositionY00 = homeViewModel.state.value.polygonP3.pointP3PositionY00,
                pointP3PositionY01 = homeViewModel.state.value.polygonP3.pointP3PositionY01,
                pointP3PositionY02 = homeViewModel.state.value.polygonP3.pointP3PositionY02,
                pointP3PositionY03 = homeViewModel.state.value.polygonP3.pointP3PositionY03,
                pointP3PositionY04 = homeViewModel.state.value.polygonP3.pointP3PositionY04,
                pointP3PositionY05 = homeViewModel.state.value.polygonP3.pointP3PositionY05,
                pointP3PositionY06 = homeViewModel.state.value.polygonP3.pointP3PositionY06,
                pointP3PositionY07 = homeViewModel.state.value.polygonP3.pointP3PositionY07,
                pointP3PositionY08 = homeViewModel.state.value.polygonP3.pointP3PositionY08,
                pointP3PositionY09 = homeViewModel.state.value.polygonP3.pointP3PositionY09,
                pointP3PositionY10 = homeViewModel.state.value.polygonP3.pointP3PositionY10,
                pointP3PositionY11 = homeViewModel.state.value.polygonP3.pointP3PositionY11,
                pointP3PositionY12 = homeViewModel.state.value.polygonP3.pointP3PositionY12,
                pointP3PositionY13 = homeViewModel.state.value.polygonP3.pointP3PositionY13,
                pointP3PositionY14 = homeViewModel.state.value.polygonP3.pointP3PositionY14,
                pointP3PositionY15 = homeViewModel.state.value.polygonP3.pointP3PositionY15,
                pointP3PositionY16 = homeViewModel.state.value.polygonP3.pointP3PositionY16,
                pointP3PositionY17 = homeViewModel.state.value.polygonP3.pointP3PositionY17,
                pointP3PositionY18 = homeViewModel.state.value.polygonP3.pointP3PositionY18,
                pointP3PositionY19 = homeViewModel.state.value.polygonP3.pointP3PositionY19,

                pointP3PositionX00 = homeViewModel.state.value.polygonP3.pointP3PositionX00,
                pointP3PositionX01 = homeViewModel.state.value.polygonP3.pointP3PositionX01,
                pointP3PositionX02 = homeViewModel.state.value.polygonP3.pointP3PositionX02,
                pointP3PositionX03 = homeViewModel.state.value.polygonP3.pointP3PositionX03,
                pointP3PositionX04 = homeViewModel.state.value.polygonP3.pointP3PositionX04,
                pointP3PositionX05 = homeViewModel.state.value.polygonP3.pointP3PositionX05,
                pointP3PositionX06 = homeViewModel.state.value.polygonP3.pointP3PositionX06,
                pointP3PositionX07 = homeViewModel.state.value.polygonP3.pointP3PositionX07,
                pointP3PositionX08 = homeViewModel.state.value.polygonP3.pointP3PositionX08,
                pointP3PositionX09 = homeViewModel.state.value.polygonP3.pointP3PositionX09,
                pointP3PositionX10 = homeViewModel.state.value.polygonP3.pointP3PositionX10,
                pointP3PositionX11 = homeViewModel.state.value.polygonP3.pointP3PositionX11,
                pointP3PositionX12 = homeViewModel.state.value.polygonP3.pointP3PositionX12,
                pointP3PositionX13 = homeViewModel.state.value.polygonP3.pointP3PositionX13,
                pointP3PositionX14 = homeViewModel.state.value.polygonP3.pointP3PositionX14,
                pointP3PositionX15 = homeViewModel.state.value.polygonP3.pointP3PositionX15,
                pointP3PositionX16 = homeViewModel.state.value.polygonP3.pointP3PositionX16,
                pointP3PositionX17 = homeViewModel.state.value.polygonP3.pointP3PositionX17,
                pointP3PositionX18 = homeViewModel.state.value.polygonP3.pointP3PositionX18,
                pointP3PositionX19 = homeViewModel.state.value.polygonP3.pointP3PositionX19,

                segmentP3Angle00 = homeViewModel.state.value.polygonP3.segmentP3Angle00,
                segmentP3Angle01 = homeViewModel.state.value.polygonP3.segmentP3Angle01,
                segmentP3Angle02 = homeViewModel.state.value.polygonP3.segmentP3Angle02,
                segmentP3Angle03 = homeViewModel.state.value.polygonP3.segmentP3Angle03,
                segmentP3Angle04 = homeViewModel.state.value.polygonP3.segmentP3Angle04,
                segmentP3Angle05 = homeViewModel.state.value.polygonP3.segmentP3Angle05,
                segmentP3Angle06 = homeViewModel.state.value.polygonP3.segmentP3Angle06,
                segmentP3Angle07 = homeViewModel.state.value.polygonP3.segmentP3Angle07,
                segmentP3Angle08 = homeViewModel.state.value.polygonP3.segmentP3Angle08,
                segmentP3Angle09 = homeViewModel.state.value.polygonP3.segmentP3Angle09,
                segmentP3Angle10 = homeViewModel.state.value.polygonP3.segmentP3Angle10,
                segmentP3Angle11 = homeViewModel.state.value.polygonP3.segmentP3Angle11,
                segmentP3Angle12 = homeViewModel.state.value.polygonP3.segmentP3Angle12,
                segmentP3Angle13 = homeViewModel.state.value.polygonP3.segmentP3Angle13,
                segmentP3Angle14 = homeViewModel.state.value.polygonP3.segmentP3Angle14,
                segmentP3Angle15 = homeViewModel.state.value.polygonP3.segmentP3Angle15,
                segmentP3Angle16 = homeViewModel.state.value.polygonP3.segmentP3Angle16,
                segmentP3Angle17 = homeViewModel.state.value.polygonP3.segmentP3Angle17,
                segmentP3Angle18 = homeViewModel.state.value.polygonP3.segmentP3Angle18,
                segmentP3Angle19 = homeViewModel.state.value.polygonP3.segmentP3Angle19,

                segmentP3Visibility00 = homeViewModel.state.value.polygonP3.segmentP3Visibility00,
                segmentP3Visibility01 = homeViewModel.state.value.polygonP3.segmentP3Visibility01,
                segmentP3Visibility02 = homeViewModel.state.value.polygonP3.segmentP3Visibility02,
                segmentP3Visibility03 = homeViewModel.state.value.polygonP3.segmentP3Visibility03,
                segmentP3Visibility04 = homeViewModel.state.value.polygonP3.segmentP3Visibility04,
                segmentP3Visibility05 = homeViewModel.state.value.polygonP3.segmentP3Visibility05,
                segmentP3Visibility06 = homeViewModel.state.value.polygonP3.segmentP3Visibility06,
                segmentP3Visibility07 = homeViewModel.state.value.polygonP3.segmentP3Visibility07,
                segmentP3Visibility08 = homeViewModel.state.value.polygonP3.segmentP3Visibility08,
                segmentP3Visibility09 = homeViewModel.state.value.polygonP3.segmentP3Visibility09,
                segmentP3Visibility10 = homeViewModel.state.value.polygonP3.segmentP3Visibility10,
                segmentP3Visibility11 = homeViewModel.state.value.polygonP3.segmentP3Visibility11,
                segmentP3Visibility12 = homeViewModel.state.value.polygonP3.segmentP3Visibility12,
                segmentP3Visibility13 = homeViewModel.state.value.polygonP3.segmentP3Visibility13,
                segmentP3Visibility14 = homeViewModel.state.value.polygonP3.segmentP3Visibility14,
                segmentP3Visibility15 = homeViewModel.state.value.polygonP3.segmentP3Visibility15,
                segmentP3Visibility16 = homeViewModel.state.value.polygonP3.segmentP3Visibility16,
                segmentP3Visibility17 = homeViewModel.state.value.polygonP3.segmentP3Visibility17,
                segmentP3Visibility18 = homeViewModel.state.value.polygonP3.segmentP3Visibility18,
                segmentP3Visibility19 = homeViewModel.state.value.polygonP3.segmentP3Visibility19,

                hmiSegmentP4Length00 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length00,
                hmiSegmentP4Length01 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length01,
                hmiSegmentP4Length02 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length02,
                hmiSegmentP4Length03 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length03,
                hmiSegmentP4Length04 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length04,
                hmiSegmentP4Length05 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length05,
                hmiSegmentP4Length06 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length06,
                hmiSegmentP4Length07 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length07,
                hmiSegmentP4Length08 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length08,
                hmiSegmentP4Length09 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length09,
                hmiSegmentP4Length10 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length10,
                hmiSegmentP4Length11 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length11,
                hmiSegmentP4Length12 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length12,
                hmiSegmentP4Length13 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length13,
                hmiSegmentP4Length14 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length14,
                hmiSegmentP4Length15 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length15,
                hmiSegmentP4Length16 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length16,
                hmiSegmentP4Length17 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length17,
                hmiSegmentP4Length18 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length18,
                hmiSegmentP4Length19 = homeViewModel.state.value.polygonP4.hmiSegmentP4Length19,

                pointP4PositionY00 = homeViewModel.state.value.polygonP4.pointP4PositionY00,
                pointP4PositionY01 = homeViewModel.state.value.polygonP4.pointP4PositionY01,
                pointP4PositionY02 = homeViewModel.state.value.polygonP4.pointP4PositionY02,
                pointP4PositionY03 = homeViewModel.state.value.polygonP4.pointP4PositionY03,
                pointP4PositionY04 = homeViewModel.state.value.polygonP4.pointP4PositionY04,
                pointP4PositionY05 = homeViewModel.state.value.polygonP4.pointP4PositionY05,
                pointP4PositionY06 = homeViewModel.state.value.polygonP4.pointP4PositionY06,
                pointP4PositionY07 = homeViewModel.state.value.polygonP4.pointP4PositionY07,
                pointP4PositionY08 = homeViewModel.state.value.polygonP4.pointP4PositionY08,
                pointP4PositionY09 = homeViewModel.state.value.polygonP4.pointP4PositionY09,
                pointP4PositionY10 = homeViewModel.state.value.polygonP4.pointP4PositionY10,
                pointP4PositionY11 = homeViewModel.state.value.polygonP4.pointP4PositionY11,
                pointP4PositionY12 = homeViewModel.state.value.polygonP4.pointP4PositionY12,
                pointP4PositionY13 = homeViewModel.state.value.polygonP4.pointP4PositionY13,
                pointP4PositionY14 = homeViewModel.state.value.polygonP4.pointP4PositionY14,
                pointP4PositionY15 = homeViewModel.state.value.polygonP4.pointP4PositionY15,
                pointP4PositionY16 = homeViewModel.state.value.polygonP4.pointP4PositionY16,
                pointP4PositionY17 = homeViewModel.state.value.polygonP4.pointP4PositionY17,
                pointP4PositionY18 = homeViewModel.state.value.polygonP4.pointP4PositionY18,
                pointP4PositionY19 = homeViewModel.state.value.polygonP4.pointP4PositionY19,

                pointP4PositionX00 = homeViewModel.state.value.polygonP4.pointP4PositionX00,
                pointP4PositionX01 = homeViewModel.state.value.polygonP4.pointP4PositionX01,
                pointP4PositionX02 = homeViewModel.state.value.polygonP4.pointP4PositionX02,
                pointP4PositionX03 = homeViewModel.state.value.polygonP4.pointP4PositionX03,
                pointP4PositionX04 = homeViewModel.state.value.polygonP4.pointP4PositionX04,
                pointP4PositionX05 = homeViewModel.state.value.polygonP4.pointP4PositionX05,
                pointP4PositionX06 = homeViewModel.state.value.polygonP4.pointP4PositionX06,
                pointP4PositionX07 = homeViewModel.state.value.polygonP4.pointP4PositionX07,
                pointP4PositionX08 = homeViewModel.state.value.polygonP4.pointP4PositionX08,
                pointP4PositionX09 = homeViewModel.state.value.polygonP4.pointP4PositionX09,
                pointP4PositionX10 = homeViewModel.state.value.polygonP4.pointP4PositionX10,
                pointP4PositionX11 = homeViewModel.state.value.polygonP4.pointP4PositionX11,
                pointP4PositionX12 = homeViewModel.state.value.polygonP4.pointP4PositionX12,
                pointP4PositionX13 = homeViewModel.state.value.polygonP4.pointP4PositionX13,
                pointP4PositionX14 = homeViewModel.state.value.polygonP4.pointP4PositionX14,
                pointP4PositionX15 = homeViewModel.state.value.polygonP4.pointP4PositionX15,
                pointP4PositionX16 = homeViewModel.state.value.polygonP4.pointP4PositionX16,
                pointP4PositionX17 = homeViewModel.state.value.polygonP4.pointP4PositionX17,
                pointP4PositionX18 = homeViewModel.state.value.polygonP4.pointP4PositionX18,
                pointP4PositionX19 = homeViewModel.state.value.polygonP4.pointP4PositionX19,

                segmentP4Angle00 = homeViewModel.state.value.polygonP4.segmentP4Angle00,
                segmentP4Angle01 = homeViewModel.state.value.polygonP4.segmentP4Angle01,
                segmentP4Angle02 = homeViewModel.state.value.polygonP4.segmentP4Angle02,
                segmentP4Angle03 = homeViewModel.state.value.polygonP4.segmentP4Angle03,
                segmentP4Angle04 = homeViewModel.state.value.polygonP4.segmentP4Angle04,
                segmentP4Angle05 = homeViewModel.state.value.polygonP4.segmentP4Angle05,
                segmentP4Angle06 = homeViewModel.state.value.polygonP4.segmentP4Angle06,
                segmentP4Angle07 = homeViewModel.state.value.polygonP4.segmentP4Angle07,
                segmentP4Angle08 = homeViewModel.state.value.polygonP4.segmentP4Angle08,
                segmentP4Angle09 = homeViewModel.state.value.polygonP4.segmentP4Angle09,
                segmentP4Angle10 = homeViewModel.state.value.polygonP4.segmentP4Angle10,
                segmentP4Angle11 = homeViewModel.state.value.polygonP4.segmentP4Angle11,
                segmentP4Angle12 = homeViewModel.state.value.polygonP4.segmentP4Angle12,
                segmentP4Angle13 = homeViewModel.state.value.polygonP4.segmentP4Angle13,
                segmentP4Angle14 = homeViewModel.state.value.polygonP4.segmentP4Angle14,
                segmentP4Angle15 = homeViewModel.state.value.polygonP4.segmentP4Angle15,
                segmentP4Angle16 = homeViewModel.state.value.polygonP4.segmentP4Angle16,
                segmentP4Angle17 = homeViewModel.state.value.polygonP4.segmentP4Angle17,
                segmentP4Angle18 = homeViewModel.state.value.polygonP4.segmentP4Angle18,
                segmentP4Angle19 = homeViewModel.state.value.polygonP4.segmentP4Angle19,

                segmentP4Visibility00 = homeViewModel.state.value.polygonP4.segmentP4Visibility00,
                segmentP4Visibility01 = homeViewModel.state.value.polygonP4.segmentP4Visibility01,
                segmentP4Visibility02 = homeViewModel.state.value.polygonP4.segmentP4Visibility02,
                segmentP4Visibility03 = homeViewModel.state.value.polygonP4.segmentP4Visibility03,
                segmentP4Visibility04 = homeViewModel.state.value.polygonP4.segmentP4Visibility04,
                segmentP4Visibility05 = homeViewModel.state.value.polygonP4.segmentP4Visibility05,
                segmentP4Visibility06 = homeViewModel.state.value.polygonP4.segmentP4Visibility06,
                segmentP4Visibility07 = homeViewModel.state.value.polygonP4.segmentP4Visibility07,
                segmentP4Visibility08 = homeViewModel.state.value.polygonP4.segmentP4Visibility08,
                segmentP4Visibility09 = homeViewModel.state.value.polygonP4.segmentP4Visibility09,
                segmentP4Visibility10 = homeViewModel.state.value.polygonP4.segmentP4Visibility10,
                segmentP4Visibility11 = homeViewModel.state.value.polygonP4.segmentP4Visibility11,
                segmentP4Visibility12 = homeViewModel.state.value.polygonP4.segmentP4Visibility12,
                segmentP4Visibility13 = homeViewModel.state.value.polygonP4.segmentP4Visibility13,
                segmentP4Visibility14 = homeViewModel.state.value.polygonP4.segmentP4Visibility14,
                segmentP4Visibility15 = homeViewModel.state.value.polygonP4.segmentP4Visibility15,
                segmentP4Visibility16 = homeViewModel.state.value.polygonP4.segmentP4Visibility16,
                segmentP4Visibility17 = homeViewModel.state.value.polygonP4.segmentP4Visibility17,
                segmentP4Visibility18 = homeViewModel.state.value.polygonP4.segmentP4Visibility18,
                segmentP4Visibility19 = homeViewModel.state.value.polygonP4.segmentP4Visibility19,
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
                anemometerSensorErr = homeViewModel.state.value.craneMechanismsCondition.anemometerSensorErr,
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
                hoistControlEffortHz = homeViewModel.state.value.craneMechanismsCondition.hoistControlEffortHz,
                slewControlEffortHz = homeViewModel.state.value.craneMechanismsCondition.slewControlEffortHz,
                trolleyControlEffortHz = homeViewModel.state.value.craneMechanismsCondition.trolleyControlEffortHz,
                craneControlEffortHz = homeViewModel.state.value.craneMechanismsCondition.craneControlEffortHz,
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