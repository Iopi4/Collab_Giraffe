package com.example.giraffenew.setting_load_capacity.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.setting_load_capacity.ui.ParametersCargoCharacteristics
import com.example.giraffenew.setting_load_capacity.ui.ParametersLoadCapacity
import com.example.giraffenew.setting_load_capacity.ui.ParameterWeightSensor
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun SettingLoadCapacityScreen (
) {
    BasicSettingBackground()

//Создаем поле в котором разделим экраны на горизонтальные области для отображения наших UI
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier = Modifier.weight(5f)){
            ParametersLoadCapacity()
        }
        Box(modifier = Modifier.weight(3f)){
            ParametersCargoCharacteristics()
        }
        Box(modifier = Modifier.weight(3f)){
            ParameterWeightSensor()
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