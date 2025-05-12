package com.example.giraffenew.coordinate_protection.prezentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.giraffenew.coordinate_protection.ui.CoordinateProtectionContent
import com.example.giraffenew.coordinate_protection.ui.CoordinateProtectionSetting
import com.example.giraffenew.coordinate_protection.ui.Polygon1
import com.example.giraffenew.coordinate_protection.ui.Polygon2
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun CoordinateProtectionScreen (){
    BasicSettingBackground()



    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Box(modifier = Modifier.weight(1f)) {
            CoordinateProtectionSetting()
        }
        Box(modifier = Modifier.weight(7f)) {
            CoordinateProtectionContent()
        }

    }



}



@Preview
@Composable
fun CoordinateProtectionScreenPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        CoordinateProtectionScreen(
        )
    }
}