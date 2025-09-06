package com.example.giraffenew.home.ui.scale

import android.R
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun ScaleVerticalWind(
    currentValue: Float,
    minValue: Float,
    maxValue: Float,
    modifier: Modifier, // Делаем параметр необязательным
    anemometerStatus: Int,
    lineThickness: Float = 16f,
    animationDuration: Int = 100
) {
    // Определяем эффективное значение maxValue
    val effectiveMaxValue = if (maxValue == 0f) 1f else maxValue

    val lineColor = when (anemometerStatus) {
        2 -> Color.Cyan
        1 -> Color.Yellow
        0 -> Color.Red
        else -> Color.LightGray
    }

    val lineLength by animateFloatAsState(
        targetValue = if (effectiveMaxValue > minValue) {
            // Корректный расчет без смещения lineThickness
            (currentValue - minValue) / (effectiveMaxValue - minValue)
        } else {
            0f // Запасной вариант для невалидного диапазона
        }.coerceIn(0f, 1f), // Гарантируем границы [0, 1]
        animationSpec = tween(durationMillis = animationDuration),
        label = "lineLengthAnimation"
    )



    Box( modifier = Modifier
        .border(
            width = 1.dp, color = lineColor, //рамка
            shape = RoundedCornerShape(1.dp))) {
        Canvas(modifier = modifier) { // Убираем ненужное приведение типов
            // Рассчитываем доступную высоту с учетом толщины линии
            val availableHeight = size.height - lineThickness

            // Вычисляем конечную позицию Y с учетом толщины
            val endY = if (availableHeight > 0) {
                size.height - lineThickness / 2 - availableHeight * lineLength
            } else {
                size.height - lineThickness / 2
            }

            //Отрисовываем шкалу по направлению снизу вверх по вертикали
            drawLine(
                color = lineColor,
                start = Offset((size.width/2), size.height-lineThickness/2),
                end = Offset(size.width / 2, endY),
                strokeWidth = lineThickness,
                cap = StrokeCap.Square
            )
        }
    }
    
}

@Preview
@Composable
fun ScaleVerticalWindPreview() {
    GIRAFFEnewTheme(dynamicColor = false) {
        ScaleVerticalWind(
                currentValue = 0f,
                minValue = -0f,
                maxValue = 0f,
                anemometerStatus = 0,
                modifier = Modifier
                    .size(width = 10.dp, height = 200.dp)
            )

    }
}