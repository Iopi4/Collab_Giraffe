package com.example.giraffenew.core.ui_kit.composable.scale

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
import com.example.giraffenew.ui.theme.Silver

@Composable

fun BasicParameterScaleHorizon(
    currentValue: Float,
    minValue: Float,
    maxValue: Float,
    modifier: Modifier, // Делаем параметр необязательным
    lineColor: Color = colorResource(R.color.Blue),
    lineThickness: Float = 12f,
    animationDuration: Int = 100
) {
    val lineLength by animateFloatAsState(
        targetValue = if (maxValue != minValue) {
            ((currentValue+lineThickness/2) - (minValue+lineThickness/2)) / ((maxValue+lineThickness/2) - (minValue+lineThickness/2)).coerceAtLeast(0.000f)
        } else {
            (lineThickness/2)
        },
        animationSpec = tween(durationMillis = animationDuration),
        label = "lineLengthAnimation"
    )

    Box( modifier = Modifier
        .border(
            width = 1.dp, color = Silver, //рамка
            shape = RoundedCornerShape(1.dp))) {
        Canvas(modifier = modifier) { // Убираем ненужное приведение типов
            drawLine(
                color = lineColor,
                start = Offset((lineThickness/2), size.height / 2),
                end = Offset((size.width * lineLength)-lineThickness/2, size.height/2),
                strokeWidth = lineThickness,
                cap = StrokeCap.Square
            )
        }
    }
    
}

@Preview
@Composable
fun BasicParameterScaleHorizonPreview() {
    GIRAFFEnewTheme(dynamicColor = false) {
            BasicParameterScaleHorizon(
                currentValue = 150f,
                minValue = 0f,
                maxValue = 200f,
                modifier = Modifier
                    .size(width = 200.dp, height = 10.dp)
            )

    }
}