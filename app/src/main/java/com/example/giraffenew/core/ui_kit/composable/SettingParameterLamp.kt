package com.example.giraffenew.core.ui_kit.composable

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Grew_50_percent



@Composable
fun BasicSettingParameterLamp(
    modifier: Modifier = Modifier,
    isLampStatus: Int,
    ) {
    val coroutineScope = rememberCoroutineScope()
    var colorState by remember { mutableStateOf(Color.Gray) } // Lamp state
    var LampEnabledColor by remember { mutableStateOf(true) } // Lamp state


    val animatedColor: Color by animateColorAsState(
        targetValue = colorState,
    )

    val myBoxTextModifier = Modifier
        .padding(start = 0.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .size(width = 450.dp, height = 30.dp)
        .border(
            width = 2.dp,
            color = Grew_50_percent,
            shape = RoundedCornerShape(5.dp)
        )

    Box(myBoxTextModifier) {

        Box(Modifier.background(animatedColor)
        ){
            when(isLampStatus){
                0 -> colorState == Color.Red
                1 -> colorState == Color.Yellow
                2 -> colorState == Color.Green
                3 -> colorState == Color.Blue
                else -> colorState == Color.Gray
            }
        }
    }
}

@Preview
@Composable
fun BasicSettingParameterLampPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        BasicSettingParameterLamp(
            isLampStatus = 2
        )
    }
}