package com.example.giraffenew.service_menu.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.service_menu.lamp.LampDebugOnOffStatus
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable

fun ButtonDebugOnOffStatus (
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
            LampDebugOnOffStatus(
                isLampStatus = isLampStatus
            )
        }
    }
}

@Preview
@Composable
fun ButtonDebugOnOffStatusPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        ButtonDebugOnOffStatus(
            onClick = {},
            isLampStatus = 1,
        )
    }
}