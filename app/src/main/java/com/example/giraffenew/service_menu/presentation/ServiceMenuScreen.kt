package com.example.giraffenew.service_menu.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.service_menu.ui.ServiceContent
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun ServiceMenuScreen(){
    BasicSettingBackground()
    ServiceContent()
}

@Preview
@Composable
fun ServiceMenuScreenPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ServiceMenuScreen(
        )
    }
}
