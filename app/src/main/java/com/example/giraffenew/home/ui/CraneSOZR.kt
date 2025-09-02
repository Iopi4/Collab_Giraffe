package com.example.giraffenew.home.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.text.BasicParametersText
import com.example.giraffenew.ui.theme.DarkLemon
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Burgundy_violet
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CraneSOZR(
    hmiLineAngle: Float,
    hmiTrolleyCoordinateY: Float,
    hmiTrolleyCoordinateX: Float,
    hmiCircleSizeTrolley: Int,
    hmiStrelaLenght: Float,

){
    val myBoxMainModifier = Modifier
        .padding(5.dp)
        .size(314.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .fillMaxSize()
        .border(
            width = 2.dp,
            color = DarkLemon,
            shape = RoundedCornerShape(10.dp)
        )



    //Бокс 1 - для крана с ГХ
    Box(myBoxMainModifier){
        BasicParametersText(stringResource(R.string.Home_Slewing_Angle_Right),
            modifier = Modifier
                .padding(start = 280.dp, end = 2.dp, top = 145.dp))
        BasicParametersText(stringResource(R.string.Home_Slewing_Angle_Left),
            modifier = Modifier
                .padding(start = 5.dp, end = 2.dp, top = 145.dp))
        BasicParametersText(stringResource(R.string.Home_Slewing_Angle_Top),
            modifier = Modifier
                .padding(start = 145.dp, end = 2.dp, top = 10.dp))
        BasicParametersText(stringResource(R.string.Home_Slewing_Angle_Bottom),
            modifier = Modifier
                .padding(start = 148.dp, end = 2.dp, top = 280.dp))

//Создадим окружность с радиусом 120.dp. Серый цвет. Покажем рабочую область крана
        Canvas (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            drawCircle(
                color = Color.LightGray.copy(alpha = 0.95f), // Замените на нужный цвет
                center = center,
                radius = 120.dp.toPx(),
            )
        }
//Создадим центр крана с радиусом 5.dp. Черный цвет.
        Canvas (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            drawCircle(
                color = Color.DarkGray, // Замените на нужный цвет
                center = center,
                radius = 5.dp.toPx(),
            )
        }
//Создадим окружность равной вылету стерлы крана с радиусом hmiCircleSizeTrolley. Фиолетовый цвет.
        Canvas (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            val radiusPx = hmiCircleSizeTrolley.dp.toPx()
            drawCircle(
                color = Burgundy_violet, // Замените на нужный цвет
                center = center,
                radius = radiusPx,
                style = Stroke(width = 2.dp.toPx())
            )
        }

        // Canvas для отрисовки вращающегося отрезка
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            val center = Offset(size.width / 2, size.height / 2)
            val length = 120.dp.toPx()

            // Конвертируем угол в радианы с инверсией для правильного направления
            val angleRad = Math.toRadians(hmiLineAngle.toDouble())

            // Вычисляем конечную точку отрезка
            val endX = center.x + length * cos(angleRad).toFloat()
            val endY = center.y + length * sin(angleRad).toFloat()
            val endPoint = Offset(endX, endY)

            // Рисуем отрезок
            drawLine(
                color = Color.Blue, // Можно изменить цвет
                start = center,
                end = endPoint,
                strokeWidth = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

    }
}

@Preview
@Composable
fun CraneSOZRPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        CraneSOZR(
            hmiLineAngle = 90f,
            hmiTrolleyCoordinateY= 1f,
            hmiTrolleyCoordinateX= 1f,
            hmiCircleSizeTrolley = 60 ,
            hmiStrelaLenght= 1f,
        )
    }
}