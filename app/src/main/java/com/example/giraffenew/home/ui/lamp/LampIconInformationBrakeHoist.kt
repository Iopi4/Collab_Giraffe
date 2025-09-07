package com.example.giraffenew.home.ui.lamp

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun LampIconInformationBrakeHoist(
    isLampIconStatus: Int,
    isIcon: Painter,

){
    Box(modifier = Modifier,
        contentAlignment = Alignment.Center)
    {
        // Анимируем цвет с периодичностью 500 мс
        val targetColor = when (isLampIconStatus) {
            0 -> Color.Red
            1 -> Color.DarkGray
            else -> Color.Red
        }

// Анимируем цвет с периодичностью 500 мс
        val animatedColor by animateColorAsState(
            targetValue = targetColor,
            animationSpec = if (isLampIconStatus == 0) {
                infiniteRepeatable(
                    animation = tween(durationMillis = 500),
                    repeatMode = RepeatMode.Reverse
                )
            } else {
                tween(durationMillis = 500)
            },
            label = "beeperColorAnimation"
        )
        Box(modifier = Modifier
            .padding(start = 2.dp, top =10.dp),
            contentAlignment = Alignment.TopStart) {
            Icon(
                painter = isIcon,
                contentDescription = null,
                modifier = Modifier.size(30.dp),
                tint = animatedColor
            )
        }
    }
}

@Preview
@Composable
fun LampIconInformationBrakeHoistPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        LampIconInformationBrakeHoist(
            isLampIconStatus = 0,
            isIcon = painterResource(R.drawable.ic_home_info_campaign),
        )
    }
}