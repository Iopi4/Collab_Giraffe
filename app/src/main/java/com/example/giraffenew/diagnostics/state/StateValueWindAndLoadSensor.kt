package com.example.giraffenew.diagnostics.state

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampError
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun StateValueWindAndLoadSensor(
    isValueParameter: String,
    isValueStatus: Int,
) {
    // Отображение значения и состояния параметра
    Row(horizontalArrangement = Arrangement.Start) {
       //Отображение значения и статуса переменной
        Box(modifier = Modifier
            .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
            .weight(3f)) {
            BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                valueParameterId = isValueParameter)
        }
        Box(modifier = Modifier.weight(1f)
            .size(width = 35.dp, height = 35.dp)
            .padding(start = 5.dp, end = 10.dp, top = 5.dp)) {
            BasicLampError(
                isLampStatus = isValueStatus
            )
        }
    }

}

@Preview
@Composable
fun StateValueWindAndLoadSensorPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        StateValueWindAndLoadSensor(
            isValueParameter = "",
            isValueStatus = 1,
        )
    }
}