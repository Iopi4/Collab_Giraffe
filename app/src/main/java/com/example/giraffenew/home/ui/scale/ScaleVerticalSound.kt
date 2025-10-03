package com.example.giraffenew.home.ui.scale

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.R

@Composable
fun ScaleVerticalSound(
    currentVolume: Float,
    modifier: Modifier,
) {
    val minValue = 0.5f
    val maxValue = 1f // Максимальное значение из требований
    val lineThickness = 16f
    val animationDuration = 100

    val colorMarker = colorResource(R.color.Orange)

    val lineLength by animateFloatAsState(
        targetValue = if (maxValue > minValue) {
            (currentVolume - minValue) / (maxValue - minValue)
        } else {
            0f
        }.coerceIn(0f, 1f),
        animationSpec = tween(durationMillis = animationDuration),
        label = "lineLengthAnimation"
    )

    Box(
        modifier = Modifier.border(
            width = 1.dp,
            color = Color.White,
            shape = RoundedCornerShape(1.dp)
        )
    ) {
        Canvas(modifier = modifier) {
            val availableHeight = size.height - lineThickness
            val endY = if (availableHeight > 0) {
                size.height - lineThickness / 2 - availableHeight * lineLength
            } else {
                size.height - lineThickness / 2
            }

            // Отрисовываем шкалу громкости
            drawLine(
                color = Color.White,
                start = Offset((size.width / 2), size.height - lineThickness / 2),
                end = Offset(size.width / 2, endY),
                strokeWidth = lineThickness,
                cap = StrokeCap.Square
            )



            // Дополнительно: рисуем маркеры для уровней громкости
            val levels = listOf(0.5f, 0.7f, 1f)
            levels.forEach { level ->
                val levelRatio = (level - minValue) / (maxValue - minValue)
                val markerY = size.height - lineThickness / 2 - availableHeight * levelRatio

                drawCircle(
                    color = if (currentVolume >= level) Color.Green else Color.Gray,
                    radius = 3.dp.toPx(),
                    center = Offset(size.width / 2, markerY)
                )
            }
        }
    }
}

@Preview
@Composable
fun ScaleVerticalSound2Preview() {
    GIRAFFEnewTheme(dynamicColor = false) {
        ScaleVerticalSound(
            currentVolume = 0.9f,
            modifier = Modifier
                .size(width = 10.dp, height = 40.dp)
        )

    }
}