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
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.text.BasicPLCDIDOName
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingParameter
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun StateDIDO(
    modifier: Modifier = Modifier,
    isDIDONumber: Int,
    isDIDOStatus: Int,
) {
    // Отображение состояния реле контроля фаз. DI_1
    Row(horizontalArrangement = Arrangement.Start) {
        // Отображение текущего значения сигнала от датчика усилия
        Box(modifier = Modifier
            .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
            .weight(3f)) {
            BasicPLCDIDOName(modifier = Modifier, text = isDIDONumber)
        }
        //Состояние
        Box(modifier = Modifier
            .weight(1f)
            .size(height = 35.dp, width = 35.dp)
            .padding(start = 5.dp)) {
            BasicLampSettingParameter(
                isLampStatus = isDIDOStatus
            )
        }
    }
}

@Preview
@Composable
fun StatePreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        StateDIDO(
            isDIDONumber = R.string.nul,
            isDIDOStatus = 2,
        )
    }
}