package com.example.giraffenew.diagnostics.prezentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.diagnostics.ui.DiagnosticContent
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun DiagnosticsScreen (){

    BasicSettingBackground()
    DiagnosticContent()


}


@Preview
@Composable
fun DiagnosticsScreenPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        DiagnosticsScreen(
        )
    }
}