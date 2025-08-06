package com.example.giraffenew.technical_maintenance.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingParameter
import com.example.giraffenew.technical_maintenance.ui.TechnicalMaintenance
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun TechnicalMaintenanceScreen (){

    BasicSettingBackground()
    TechnicalMaintenance()

}

@Preview
@Composable
fun TechnicalMaintenanceScreenPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        TechnicalMaintenanceScreen(
        )
    }
}