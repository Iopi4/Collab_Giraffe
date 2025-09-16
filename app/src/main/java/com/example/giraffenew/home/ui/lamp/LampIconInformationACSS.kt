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
fun LampIconInformationACSS(
    isACSSPWR: Int,
    isACSSFault: Int,
    isIcon: Painter,

){
    Box(modifier = Modifier,
        contentAlignment = Alignment.Center)
    {
        // Анимируем цвет с периодичностью 500 мс
        val targetColor = when {
            isACSSFault == 1 && (isACSSPWR == 0 || isACSSPWR == 1)-> Color.Red
            isACSSPWR == 1 -> Color.Green
            else -> Color.DarkGray
        }

// Анимируем цвет с периодичностью 500 мс
        val animatedColor by animateColorAsState(
            targetValue = targetColor,
            animationSpec = if (isACSSFault == 1) {
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
            .padding(start = 5.dp, top =5.dp),
            contentAlignment = Alignment.TopStart) {
            Icon(
                painter = isIcon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = animatedColor
            )
        }
    }
}

@Preview
@Composable
fun LampIconInformationACSSPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        LampIconInformationACSS(
            isACSSPWR = 0,
            isACSSFault =1,
            isIcon = painterResource(R.drawable.ic_home_info_campaign),
        )
    }
}