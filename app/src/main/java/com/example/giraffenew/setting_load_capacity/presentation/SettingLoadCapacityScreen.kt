package com.example.giraffenew.setting_load_capacity.presentation

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonTabGiraffe
import com.example.giraffenew.setting_load_capacity.ui.ParametersCargoCharacteristics
import com.example.giraffenew.setting_load_capacity.ui.ParametersLoadCapacity
import com.example.giraffenew.setting_load_capacity.ui.ParameterWeightSensor
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun SettingLoadCapacityScreen (
    settingLoadCapacityViewModel: SettingLoadCapacityViewModel = hiltViewModel()
) {

    BasicSettingBackground()

    // 1. Получаем текущий LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current

    // 2. Создаем Lifecycle-aware эффект
    DisposableEffect(lifecycleOwner, settingLoadCapacityViewModel) {
        val lifecycleObserver = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_RESUME -> {
                    Log.d("LoadCapacityScreen", "Lifecycle: ON_RESUME - resuming data collection")
                    settingLoadCapacityViewModel.setActiveConfiguration()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    Log.d("LoadCapacityScreen", "Lifecycle: ON_PAUSE - pausing data collection")
                    settingLoadCapacityViewModel.clearActiveConfiguration()
                }
                else -> {Log.e("LoadCapacityScreen", "Lifecycle: NOT START")}
            }
        }

        // 3. Регистрируем наблюдатель
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)

        // 4. Очистка при удалении Composable
        onDispose {
            Log.d("LoadCapacityScreen", "Lifecycle: ON_DISPOSE - removing observer")
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
            settingLoadCapacityViewModel.clearActiveConfiguration() // Дополнительная очистка
        }
    }

    val navController = rememberNavController()
    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    {NavBarLoadCapacityContent(navController = navController)
        NavHost(navController, startDestination = NavRoutesLoadCapacity.PageParametersLoadCapacity.route) {
            composable(NavRoutesLoadCapacity.PageParametersLoadCapacity.route) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Box(modifier = Modifier.weight(13f)){
                        ParametersLoadCapacity(
                            onSetCargoCharacteristic = { index -> settingLoadCapacityViewModel.onSetCargoCharacteristic(index)},
                            errorMessageGH = settingLoadCapacityViewModel.errorMessageGH.value,
                            logMessageGH = settingLoadCapacityViewModel.logMessageGH.value
                        )
                    }
                    Box(modifier = Modifier.weight(5f)){
                        ParametersCargoCharacteristics(
                            currentGruzovayZapasovka = settingLoadCapacityViewModel.state.value.currentGruzovayZapasovka,
                            currentMaxWeight = settingLoadCapacityViewModel.state.value.currentMaxWeight,
                            currentMaxWeight2 = settingLoadCapacityViewModel.state.value.currentMaxWeight2,
                            currentCraneLenght = settingLoadCapacityViewModel.state.value.currentCraneLenght,
                        )
                    }


                }
            }

            composable(NavRoutesLoadCapacity.PageWeightSensorCalibration.route) {
                ParameterWeightSensor(
                    currentCurrAI = settingLoadCapacityViewModel.state.value.currentCurrAI,
                    currentWeight = settingLoadCapacityViewModel.state.value.currentWeight,
                    setValueMinWeight = settingLoadCapacityViewModel.setValueMinWeight.value,
                    onModelChangeMinWeight = { settingLoadCapacityViewModel.updateMinWeight(it) },
                    onSetValueMinWeight = { settingLoadCapacityViewModel.onSetValueMinWeight() },
                    setValueMaxWeight = settingLoadCapacityViewModel.setValueMaxWeight.value,
                    onModelChangeMaxWeight = { settingLoadCapacityViewModel.updateMaxWeight(it) },
                    onSetValueMaxWeight = { settingLoadCapacityViewModel.onSetValueMaxWeight() },
                    errorMessageWeightSensor = settingLoadCapacityViewModel.errorMessageWeightSensor.value,
                    logMessageWeightSensor = settingLoadCapacityViewModel.logMessageWeightSensor.value,
                    hmiWeightCalibStatus = settingLoadCapacityViewModel.state.value.hmiWeightCalibStatus,
                    hmiWeightCalibMessage = settingLoadCapacityViewModel.state.value.hmiWeightCalibMessage,
                    heightCurrent = settingLoadCapacityViewModel.state.value.heightCurrent,
                    trolleyCurrent = settingLoadCapacityViewModel.state.value.trolleyCurrent,
                    onResetWeightCalibration = { index -> settingLoadCapacityViewModel.onResetWeightCalibration(index)},

                )
            }



        }

    }


//Создаем поле в котором разделим экраны на горизонтальные области для отображения наших UI

}

@Composable
fun NavBarLoadCapacityContent (navController: NavController){
    val myBoxNavBarModifier = Modifier
        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = DarkGrew,
            shape = RoundedCornerShape(5.dp)
        )
//Код для формирования области в верху с кнопками переходов между страницами
    Column (myBoxNavBarModifier,
        verticalArrangement = Arrangement.SpaceEvenly)
    {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.SettingLoadCapacityMenu_Button_LoadCapacity,
                    onClick = {
                        navController.navigate(NavRoutesLoadCapacity.PageParametersLoadCapacity.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.SettingLoadCapacityMenu_Button_WeightSensor,
                    onClick = {
                        navController.navigate(NavRoutesLoadCapacity.PageWeightSensorCalibration.route) },) {}
            }
        }
    }
}

sealed class NavRoutesLoadCapacity(val route: String){
    object PageParametersLoadCapacity: NavRoutesLoadCapacity ("Параметры грузоподъемности")
    object PageWeightSensorCalibration: NavRoutesLoadCapacity ("Настройка грузоподъемности")
}


@Preview
@Composable
fun SettingLoadCapacityScreenPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        SettingLoadCapacityScreen(
        )
    }
}