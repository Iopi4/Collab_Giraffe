package com.example.giraffenew.setting_sensors.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun CalibrationSensorsPageOne () {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Box(modifier = Modifier.weight(1f)) {
            CalibrationSensorHoist()
        }
        Box(modifier = Modifier.weight(1f)) {
            CalibrationSensorTrolley()
        }

    }
}

@Preview
@Composable
fun CalibrationSensorsPageOnePreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        CalibrationSensorsPageOne(
        )
    }
}