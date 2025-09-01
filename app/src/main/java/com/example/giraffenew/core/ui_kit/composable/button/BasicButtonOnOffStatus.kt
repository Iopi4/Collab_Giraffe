package com.example.giraffenew.core.ui_kit.composable.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingParameter
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable

fun BasicButtonOnOffStatus (
    onClick: () -> Unit,
    isLampStatus: Int,
){
    //Контроль состояния переменной для кнопки [Вкл/Выкл]
    Row(horizontalArrangement = Arrangement.Start) {
        //Кнопка ввода
        Box(modifier = Modifier.weight(4f)) {
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.Button_Turn_ON_Off,
                onClick = onClick,
            ) {}
        }
        Box(modifier = Modifier.weight(1f)) {
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