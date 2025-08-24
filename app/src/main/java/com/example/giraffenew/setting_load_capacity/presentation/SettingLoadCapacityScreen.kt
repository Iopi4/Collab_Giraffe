package com.example.giraffenew.setting_load_capacity.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.setting_load_capacity.ui.ParametersCargoCharacteristics
import com.example.giraffenew.setting_load_capacity.ui.ParametersLoadCapacity
import com.example.giraffenew.setting_load_capacity.ui.ParameterWeightSensor
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
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    Log.d("HomeScreen", "Lifecycle: ON_START - resuming data collection")
                    settingLoadCapacityViewModel.startDataCollection()
                }
                Lifecycle.Event.ON_STOP -> {
                    Log.d("HomeScreen", "Lifecycle: ON_STOP - pausing data collection")
                    settingLoadCapacityViewModel.pauseDataCollection()
                }
                else -> {}
            }
        }

        // 3. Регистрируем наблюдатель
        lifecycleOwner.lifecycle.addObserver(observer)

        // 4. Очистка при удалении Composable
        onDispose {
            Log.d("LoadCapacityScreen", "Lifecycle: ON_DISPOSE - removing observer")
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


//Создаем поле в котором разделим экраны на горизонтальные области для отображения наших UI
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier = Modifier.weight(5f)){
            ParametersLoadCapacity(
                onSetCargoCharacteristic0 = { settingLoadCapacityViewModel.onSetCargoCharacteristic0()},
                onSetCargoCharacteristic1 = { settingLoadCapacityViewModel.onSetCargoCharacteristic1()},
                onSetCargoCharacteristic2 = { settingLoadCapacityViewModel.onSetCargoCharacteristic2()},
                onSetCargoCharacteristic3 = { settingLoadCapacityViewModel.onSetCargoCharacteristic3()},
                onSetCargoCharacteristic4 = { settingLoadCapacityViewModel.onSetCargoCharacteristic4()},
                onSetCargoCharacteristic5 = { settingLoadCapacityViewModel.onSetCargoCharacteristic5()},
                onSetCargoCharacteristic6 = { settingLoadCapacityViewModel.onSetCargoCharacteristic6()},
                errorMessageGH = settingLoadCapacityViewModel.errorMessageGH.value,
                logMessageGH = settingLoadCapacityViewModel.logMessageGH.value
            )
        }
        Box(modifier = Modifier.weight(3f)){
            ParametersCargoCharacteristics(
                currentGruzovayZapasovka = settingLoadCapacityViewModel.state.value.currentGruzovayZapasovka,
                currentMaxWeight = settingLoadCapacityViewModel.state.value.currentMaxWeight,
                currentMaxWeight2 = settingLoadCapacityViewModel.state.value.currentMaxWeight2,
                currentCraneLenght = settingLoadCapacityViewModel.state.value.currentCraneLenght,
            )
        }
        Box(modifier = Modifier.weight(4f)){
            ParameterWeightSensor(
                currentCurrAI = settingLoadCapacityViewModel.state.value.currentCurrAI,
                currentWeight = settingLoadCapacityViewModel.state.value.currentWeight,
                setValueMinWeight = settingLoadCapacityViewModel.setValueMinWeight.value,
                onModelChangeMinWeight = { settingLoadCapacityViewModel.updateMinWeight(it)},
                onSetValueMinWeight = {settingLoadCapacityViewModel.onSetValueMinWeight()},
                setValueMaxWeight = settingLoadCapacityViewModel.setValueMaxWeight.value,
                onModelChangeMaxWeight = {settingLoadCapacityViewModel.updateMaxWeight(it)},
                onSetValueMaxWeight = {settingLoadCapacityViewModel.onSetValueMaxWeight()},
                onSetMinWeightСalibration = {settingLoadCapacityViewModel.onSetMinWeightСalibration()},
                onSetMaxWeightСalibration = {settingLoadCapacityViewModel.onSetMaxWeightСalibration()},
                errorMessageWeightSensor = settingLoadCapacityViewModel.errorMessageWeightSensor.value,
                logMessageWeightSensor = settingLoadCapacityViewModel.logMessageWeightSensor.value,
            )
        }

    }
}

@Preview
@Composable
fun SettingLoadCapacityScreenPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        SettingLoadCapacityScreen(
        )
    }
}