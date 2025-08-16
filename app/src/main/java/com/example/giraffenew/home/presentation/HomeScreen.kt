package com.example.giraffenew.home.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
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
                weightPercent = homeViewModel.weightPercent.value,
                hoistPosition = homeViewModel.hoistPosition.value,
                hoistMaxPosition = homeViewModel.hoistMaxPosition.value,
                cranePosition = homeViewModel.cranePosition.value,
                craneMaxPosition = homeViewModel.craneMaxPosition.value,
                weightSensorErr = homeViewModel.weightSensorErr.value
            ) }

            Box (modifier = Modifier
                .weight(3f)) { CraneSOZR() }

            Box (modifier = Modifier
                .weight(1f)) { Information() }

        }

//№.2 - Средняя область для отображения параметров механизмов крана
        Column (modifier = Modifier
            .weight(1f)) {

            CraneMechanismsCondition()
        }

//№.3 - Левая область для отображения параметров работы крана
        Column(modifier = Modifier
            .fillMaxSize()
            .weight(1f),)
        {
            СraneOperatingParameters(
                weightQnom = homeViewModel.weightMax.value,
                weightCurrent = homeViewModel.weightCurrent.value,
                trolleyCurrent = homeViewModel.trolleyCurrent.value,
                slewingCurrent = homeViewModel.slewingCurrent.value,
                heightCurrent = homeViewModel.heightCurrent.value,
                moveCurrent = homeViewModel.moveCurrent.value,
                windCurrent = homeViewModel.windCurrent.value,
                voltageL1L2Current = homeViewModel.voltageL1L2Current.value,
                voltageL2L3Current = homeViewModel.voltageL2L3Current.value,
                voltageL1L3Current = homeViewModel.voltageL1L3Current.value,
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