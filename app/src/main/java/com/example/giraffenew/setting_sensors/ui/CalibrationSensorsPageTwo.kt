package com.example.giraffenew.setting_sensors.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun CalibrationSensorsPageTwo () {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Box(modifier = Modifier.weight(4f)) {
            CalibrationSensorSlewing()
        }
        Box(modifier = Modifier.weight(5f)) {
            CalibrationSensorCrane()
        }

    }
}

@Preview
@Composable
fun CalibrationSensorsPageTwoPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        CalibrationSensorsPageTwo(
        )
    }
}