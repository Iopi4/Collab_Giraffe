package com.example.giraffenew.initial_setup.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.initial_setup.ui.ParametersCraneAndSBKB
import com.example.giraffenew.initial_setup.ui.ParametersDateAndTime
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun InitialSetupScreen(
    ) {
    BasicSettingBackground()

//Создаем поле в котором разделим экраны на горизонтальные области для отображения наших UI
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier = Modifier.weight(1f)){
            ParametersDateAndTime()
        }
        Box(modifier = Modifier.weight(1f)){
            ParametersCraneAndSBKB()
        }

    }


}

@Preview
@Composable
fun InitialSetupScreenPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        InitialSetupScreen(
        )
    }
}