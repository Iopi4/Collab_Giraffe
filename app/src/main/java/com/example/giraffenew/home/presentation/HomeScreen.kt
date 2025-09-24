package com.example.giraffenew.home.presentation

import android.content.Context
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
import com.example.giraffenew.home.ui.CraneOperatingParameters

@Composable
internal fun HomeScreen (
    homeViewModel: HomeScreenViewModel = hiltViewModel(),
){
    val state = homeViewModel.state.value

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
            {
                CraneParameterScale(
                    parameterScale = state.parameterScale,
            ) }

            Box (modifier = Modifier
                .weight(3f))
            { CraneSOZR(
                sozrParams = state.sozrParams,
                polygonP1 = state.polygonP1,
                polygonP2 = state.polygonP2,
                polygonP3 = state.polygonP3,
                polygonP4 = state.polygonP4,


            ) }

            Box (modifier = Modifier
                .weight(1f))
            {
                Information(
                    information = state.information,
                )
            }
        }

//№.2 - Средняя область для отображения параметров механизмов крана
        Column (modifier = Modifier
            .weight(1f))
        {   //CraneMechanismsCondition(mechanismsState = state.craneMechanismsCondition)
            CraneMechanismsCondition(
                craneMechanismsCondition = state.craneMechanismsCondition,
            )
        }

//№.3 - Левая область для отображения параметров работы крана
        Column(modifier = Modifier
            .fillMaxSize()
            .weight(1f),)
        {   //CraneOperatingParameters(operatingParams = state.operatingParams)
            CraneOperatingParameters(
                operatingParams = state.operatingParams,
            )
        }
    }
}