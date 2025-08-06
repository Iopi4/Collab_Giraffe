package com.example.giraffenew.parameter_logger.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.parameter_logger.ui.ParameterLoggerContent
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun ParameterLoggerScreen (){
    BasicSettingBackground()
    ParameterLoggerContent()
}

@Preview
@Composable
fun ParameterLoggerScreenPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ParameterLoggerScreen(
        )
    }
}