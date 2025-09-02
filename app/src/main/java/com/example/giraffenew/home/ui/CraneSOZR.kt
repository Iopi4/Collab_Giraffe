package com.example.giraffenew.home.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.ui.theme.DarkLemon
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.R
import com.example.giraffenew.ui.theme.Burgundy_violet

@Composable
fun CraneSOZR(
    circleSizeTrolley: Int,
){
    val myBoxMainModifier = Modifier
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .fillMaxSize()
        .border(
            width = 2.dp,
            color = DarkLemon,
            shape = RoundedCornerShape(10.dp)
        )

    //Бокс 1 - для крана с ГХ
    Box(myBoxMainModifier){
        Canvas (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            drawCircle(
                color = Color.LightGray.copy(alpha = 0.9f), // Замените на нужный цвет
                center = center,
                radius = 100.dp.toPx(),
            )
        }
        Canvas (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            val radiusPx = circleSizeTrolley.dp.toPx()
            drawCircle(
                color = Burgundy_violet, // Замените на нужный цвет
                center = center,
                radius = radiusPx,
                style = Stroke(width = 2.dp.toPx())
            )
        }

    }

}

@Preview
@Composable
fun CraneSOZRPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        CraneSOZR(
            circleSizeTrolley = 50
        )
    }
}