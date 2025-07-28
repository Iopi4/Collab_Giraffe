package com.example.giraffenew.diagnostics.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingParameter
import com.example.giraffenew.core.ui_kit.composable.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun StateValue(
    modifier: Modifier = Modifier,
    isValueParameter: String,
    isValueStatus: Int,
) {
    // Отображение значения и состояния параметра
    Row(horizontalArrangement = Arrangement.Start) {
        // Отображение текущего значения датчика высоты Hoist
        Box(modifier = Modifier
            .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
            .weight(3f)) {
            BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                valueParameterId = isValueParameter)
        }
        //Статус датчика Hoist
        Box(modifier = Modifier.weight(1f).padding(start = 5.dp)) {
            BasicLampSettingParameter(
                isLampStatus = isValueStatus
            )
        }
    }

}

@Preview
@Composable
fun StateValuePreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        StateValue(
            isValueParameter = "",
            isValueStatus = 2,
        )
    }
}