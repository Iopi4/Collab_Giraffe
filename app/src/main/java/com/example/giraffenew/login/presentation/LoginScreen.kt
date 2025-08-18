package com.example.giraffenew.login.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.login.ui.Authorization
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun LoginScreen (){
    BasicSettingBackground()
    Authorization()
}

@Preview
@Composable
fun LoginScreenPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        LoginScreen(
        )
    }
}