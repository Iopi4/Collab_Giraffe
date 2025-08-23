package com.example.giraffenew.core.ui_kit.composable.button

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
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingParameter
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable

fun BasicButtonOnOffStatus (
    onClick: () -> Unit,
    isLampStatus: Int,
){
    //Контроль состояния переменной для кнопки [Вкл/Выкл]
    Row(horizontalArrangement = Arrangement.SpaceBetween) {
        //Кнопка ввода
        Box(modifier = Modifier
            .weight(3f)) {
            BasicButtonGiraffe(
                text = R.string.Button_Turn_ON_Off,
                onClick = onClick,
            ) {}
        }
        Box(modifier = Modifier.weight(1f)
            .size(width = 35.dp, height = 35.dp)
            .padding(end = 10.dp, top = 5.dp)) {
            BasicLampSettingParameter(
                isLampStatus = isLampStatus
            )
        }
    }
}

@Preview
@Composable
fun BasicButtonOnOffStatusPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        BasicButtonOnOffStatus(
            onClick = {},
            isLampStatus = 1,
        )
    }
}