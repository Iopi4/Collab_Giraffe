package com.example.giraffenew.service_menu.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.service_menu.lamp.LampOnOffAndroidStatus
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun ButtonOnOffAndroidStatus (
    onClick: () -> Unit,
    isLampStatus: Boolean)
{
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
            LampOnOffAndroidStatus(
                isLampStatus = isLampStatus
            )
        }
    }
}

@Preview
@Composable
fun ButtonOnOffAndroidStatusPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        ButtonOnOffAndroidStatus(
            onClick = {},
            isLampStatus = false,
        )
    }
}
