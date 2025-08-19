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
import androidx.compose.ui.tooling.preview.Preview
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
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
internal fun HomeScreen (
    modifier: Modifier = Modifier,
    homeViewModel: HomeScreenViewModel = hiltViewModel() // Получаем ViewModel для HomeScreen
){
    // 1. Получаем текущий LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current
    // 2. Создаем Lifecycle-aware эффект
    DisposableEffect(lifecycleOwner, homeViewModel) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    Log.d("HomeScreen", "Lifecycle: ON_START - resuming data collection")
                    homeViewModel.startDataCollection()
                }
                Lifecycle.Event.ON_STOP -> {
                    Log.d("HomeScreen", "Lifecycle: ON_STOP - pausing data collection")
                    homeViewModel.pauseDataCollection()
                }
                else -> {}
            }
        }

        // 3. Регистрируем наблюдатель
        lifecycleOwner.lifecycle.addObserver(observer)

        // 4. Очистка при удалении Composable
        onDispose {
            Log.d("HomeScreen", "Lifecycle: ON_DISPOSE - removing observer")
            lifecycleOwner.lifecycle.removeObserver(observer)
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
                .weight(3f)) { CraneParameterScale(
                weightPercent = homeViewModel.state.value.weightPercent,
                hoistPosition = homeViewModel.state.value.hoistPosition,
                hoistMaxPosition = homeViewModel.state.value.hoistMaxPosition,
                cranePosition = homeViewModel.state.value.cranePosition,
                craneMaxPosition = homeViewModel.state.value.craneMaxPosition,
            ) }

            Box (modifier = Modifier
                .weight(3f)) { CraneSOZR() }

            Box (modifier = Modifier
                .weight(1f)) { Information() }

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

@Preview
@Composable
fun HomeScreenPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        HomeScreen(
            modifier = Modifier,
        )
    }
}