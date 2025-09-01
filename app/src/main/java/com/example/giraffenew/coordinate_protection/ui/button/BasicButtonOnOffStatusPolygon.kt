package com.example.giraffenew.coordinate_protection.ui.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingParameter
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Silver

@Composable

fun BasicButtonOnOffStatusPolygon (
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
        Box(modifier = Modifier.weight(1f)
            .padding(start = 0.dp, end = 10.dp, top = 5.dp, bottom = 0.dp),
            contentAlignment = Alignment.Center)
        {
            val lampColor = when (isLampStatus) {
                0 -> colorResource(R.color.Grey)
                1 -> colorResource(R.color.Emerald)
                else -> colorResource(R.color.Grey) // default color
            }

            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(5.dp))
                    .size(width = 50.dp, height = 30.dp)
                    .border(
                        width = 2.dp,
                        color = Silver,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .shadow(1.dp, shape = MaterialTheme.shapes.small)
                    .background(lampColor, shape = MaterialTheme.shapes.small)
            )
        }
    }
}

@Preview
@Composable
fun BasicButtonOnOffStatusPolygonPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        BasicButtonOnOffStatusPolygon(
            onClick = {},
            isLampStatus = 1,
        )
    }
}