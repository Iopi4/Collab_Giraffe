package com.example.giraffenew.home.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.text.BasicParametersText
import com.example.giraffenew.ui.theme.DarkLemon
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.white
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CraneSOZR(
    hmiLineAngle: Float,
    hmiCircleSizeTrolley: Int,
    hmiTrolleyCoordinateY: Float,
    hmiTrolleyCoordinateX: Float,

    hmiP1Color: Int, // 0-синий, 1-зеленый, 2-желтый, 3-красный
    hmiP2Color: Int, // 0-синий, 1-зеленый, 2-желтый, 3-красный
    hmiP3Color: Int, // 0-синий, 1-зеленый, 2-желтый, 3-красный
    hmiP4Color: Int, // 0-синий, 1-зеленый, 2-желтый, 3-красный

    hmiSegmentP1Length00: Int, // Длина сегмента №00
    hmiSegmentP1Length01: Int, // Длина сегмента №01
    hmiSegmentP1Length02: Int, // Длина сегмента №02
    hmiSegmentP1Length03: Int, // Длина сегмента №03
    hmiSegmentP1Length04: Int, // Длина сегмента №04
    hmiSegmentP1Length05: Int, // Длина сегмента №05
    hmiSegmentP1Length06: Int, // Длина сегмента №06
    hmiSegmentP1Length07: Int, // Длина сегмента №07
    hmiSegmentP1Length08: Int, // Длина сегмента №08
    hmiSegmentP1Length09: Int, // Длина сегмента №09
    hmiSegmentP1Length10: Int, // Длина сегмента №10
    hmiSegmentP1Length11: Int, // Длина сегмента №11
    hmiSegmentP1Length12: Int, // Длина сегмента №12
    hmiSegmentP1Length13: Int, // Длина сегмента №13
    hmiSegmentP1Length14: Int, // Длина сегмента №14
    hmiSegmentP1Length15: Int, // Длина сегмента №15
    hmiSegmentP1Length16: Int, // Длина сегмента №16
    hmiSegmentP1Length17: Int, // Длина сегмента №17
    hmiSegmentP1Length18: Int, // Длина сегмента №18
    hmiSegmentP1Length19: Int, // Длина сегмента №19

    pointP1PositionY00: Int, // Координата Y точки №00
    pointP1PositionY01: Int, // Координата Y точки №01
    pointP1PositionY02: Int, // Координата Y точки №02
    pointP1PositionY03: Int, // Координата Y точки №03
    pointP1PositionY04: Int, // Координата Y точки №04
    pointP1PositionY05: Int, // Координата Y точки №05
    pointP1PositionY06: Int, // Координата Y точки №06
    pointP1PositionY07: Int, // Координата Y точки №07
    pointP1PositionY08: Int, // Координата Y точки №08
    pointP1PositionY09: Int, // Координата Y точки №09
    pointP1PositionY10: Int, // Координата Y точки №10
    pointP1PositionY11: Int, // Координата Y точки №11
    pointP1PositionY12: Int, // Координата Y точки №12
    pointP1PositionY13: Int, // Координата Y точки №13
    pointP1PositionY14: Int, // Координата Y точки №14
    pointP1PositionY15: Int, // Координата Y точки №15
    pointP1PositionY16: Int, // Координата Y точки №16
    pointP1PositionY17: Int, // Координата Y точки №17
    pointP1PositionY18: Int, // Координата Y точки №18
    pointP1PositionY19: Int, // Координата Y точки №19

    pointP1PositionX00: Int, // Координата X точки №00
    pointP1PositionX01: Int, // Координата X точки №01
    pointP1PositionX02: Int, // Координата X точки №02
    pointP1PositionX03: Int, // Координата X точки №03
    pointP1PositionX04: Int, // Координата X точки №04
    pointP1PositionX05: Int, // Координата X точки №05
    pointP1PositionX06: Int, // Координата X точки №06
    pointP1PositionX07: Int, // Координата X точки №07
    pointP1PositionX08: Int, // Координата X точки №08
    pointP1PositionX09: Int, // Координата X точки №09
    pointP1PositionX10: Int, // Координата X точки №10
    pointP1PositionX11: Int, // Координата X точки №11
    pointP1PositionX12: Int, // Координата X точки №12
    pointP1PositionX13: Int, // Координата X точки №13
    pointP1PositionX14: Int, // Координата X точки №14
    pointP1PositionX15: Int, // Координата X точки №15
    pointP1PositionX16: Int, // Координата X точки №16
    pointP1PositionX17: Int, // Координата X точки №17
    pointP1PositionX18: Int, // Координата X точки №18
    pointP1PositionX19: Int, // Координата X точки №19

    segmentP1Angle00_01: Int, //Угол между сегментами 00-01
    segmentP1Angle01_02: Int, //Угол между сегментами 01-02
    segmentP1Angle02_03: Int, //Угол между сегментами 02-03
    segmentP1Angle03_04: Int, //Угол между сегментами 03-04
    segmentP1Angle04_05: Int, //Угол между сегментами 04-05
    segmentP1Angle05_06: Int, //Угол между сегментами 05-06
    segmentP1Angle06_07: Int, //Угол между сегментами 06-07
    segmentP1Angle07_08: Int, //Угол между сегментами 07-08
    segmentP1Angle08_09: Int, //Угол между сегментами 08-09
    segmentP1Angle09_10: Int, //Угол между сегментами 09-10
    segmentP1Angle10_11: Int, //Угол между сегментами 10-11
    segmentP1Angle11_12: Int, //Угол между сегментами 11-12
    segmentP1Angle12_13: Int, //Угол между сегментами 12-13
    segmentP1Angle13_14: Int, //Угол между сегментами 13-14
    segmentP1Angle14_15: Int, //Угол между сегментами 14-15
    segmentP1Angle15_16: Int, //Угол между сегментами 15-16
    segmentP1Angle16_17: Int, //Угол между сегментами 16-17
    segmentP1Angle17_18: Int, //Угол между сегментами 17-18
    segmentP1Angle18_19: Int, //Угол между сегментами 18-19
    segmentP1Angle19_00: Int, //Угол между сегментами 19-00

    segmentP1Visibility00: Int, //Видимость сегмента №00
    segmentP1Visibility01: Int, //Видимость сегмента №01
    segmentP1Visibility02: Int, //Видимость сегмента №02
    segmentP1Visibility03: Int, //Видимость сегмента №03
    segmentP1Visibility04: Int, //Видимость сегмента №04
    segmentP1Visibility05: Int, //Видимость сегмента №05
    segmentP1Visibility06: Int, //Видимость сегмента №06
    segmentP1Visibility07: Int, //Видимость сегмента №07
    segmentP1Visibility08: Int, //Видимость сегмента №08
    segmentP1Visibility09: Int, //Видимость сегмента №09
    segmentP1Visibility10: Int, //Видимость сегмента №10
    segmentP1Visibility11: Int, //Видимость сегмента №11
    segmentP1Visibility12: Int, //Видимость сегмента №12
    segmentP1Visibility13: Int, //Видимость сегмента №13
    segmentP1Visibility14: Int, //Видимость сегмента №14
    segmentP1Visibility15: Int, //Видимость сегмента №15
    segmentP1Visibility16: Int, //Видимость сегмента №16
    segmentP1Visibility17: Int, //Видимость сегмента №17
    segmentP1Visibility18: Int, //Видимость сегмента №18
    segmentP1Visibility19: Int, //Видимость сегмента №19
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
    val colorSrtela = colorResource(R.color.Orange)
    val colorTrolley = colorResource(R.color.Burgundy_violet)
    val colorCoordinate = colorResource(R.color.Silver)

    val hmiSegmentCoordinateLengthX: Int = 270 // Длина координаты X
    val hmiSegmentCoordinateLengthY: Int = 270 // Длина координаты Y
    val pointCoordinateX0 = 10 // Начало координат X
    val pointCoordinateY0 = 290 // Начало координат Y
    val pointCoordinateX1 = 10 // Начало координат X
    val pointCoordinateY1 = 290 // Начало координат Y
    val CoordinateAngleX = 0 //Угол координаты X
    val CoordinateAngleY = 270 //Угол координаты Y
    val CoordinateVisibilityX = 1 //Видимость оси координат
    val CoordinateVisibilityY = 1 //Видимость оси координат

    //Бокс 1 - для крана с ГХ
    Box(myBoxMainModifier){
        BasicParametersTextSOZR(stringResource(R.string.Home_Slewing_Angle_Right),
            modifier = Modifier.padding(start = 280.dp, end = 2.dp, top = 145.dp))

        BasicParametersTextSOZR(stringResource(R.string.Home_Slewing_Angle_Left),
            modifier = Modifier.padding(start = 25.dp, end = 2.dp, top = 145.dp))

        BasicParametersTextSOZR(stringResource(R.string.Home_Slewing_Angle_Top),
            modifier = Modifier.padding(start = 145.dp, end = 2.dp, top = 10.dp))

        BasicParametersTextSOZR(stringResource(R.string.Home_Slewing_Angle_Bottom),
            modifier = Modifier.padding(start = 148.dp, end = 2.dp, top = 275.dp))
        BasicParametersTextSOZR(stringResource(R.string.Home_Slewing_Coordinate_X),
            modifier = Modifier.padding(start = 295.dp, end = 2.dp, top = 285.dp))
        BasicParametersTextSOZR(stringResource(R.string.Home_Slewing_Coordinate_Y),
            modifier = Modifier.padding(start = 12.dp, end = 2.dp, top = 5.dp))

// Окружность рабочей области.
        Canvas (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .matchParentSize()
        ) {
            drawCircle(
                color = Color.LightGray.copy(alpha = 0.95f), // Замените на нужный цвет
                center = center,
                radius = 120.dp.toPx(),
            )

            val squareSize = 300.dp.toPx()
            val boxSize = 314.dp.toPx()
            val squareOffset = (boxSize - squareSize) / 2
            val scale = squareSize / 300f

            // Создаем массивы данных для удобства обработки
            val lengths = listOf(hmiSegmentCoordinateLengthX, hmiSegmentCoordinateLengthY)
            val positionsX = listOf(pointCoordinateX0, pointCoordinateX1)
            val positionsY = listOf(pointCoordinateY0, pointCoordinateY1)
            val angles = listOf(CoordinateAngleX, CoordinateAngleY)
            val visibilities = listOf(CoordinateVisibilityX, CoordinateVisibilityY)


            // Рисуем все отрезки. Примечание. Начало оси Y с нижнего левого угла squareSize
            for (i in 0 until 2) {
                if (visibilities[i] == 1) {
                    val startX = squareOffset + positionsX[i] * scale
                    val startY = squareOffset + positionsY[i] * scale

                    // Конвертируем угол в радианы с учетом ориентации
                    val angleRad = Math.toRadians(angles[i].toDouble())

                    // Вычисляем конечные координаты
                    val endX = startX + lengths[i] * scale * cos(angleRad).toFloat()
                    val endY = startY + lengths[i] * scale * sin(angleRad).toFloat()

                    // Выбираем цвет в зависимости от параметра
                    val color = colorCoordinate.copy(alpha = 0.9f)


                    // Рисуем отрезок
                    drawLine(
                        color = color,
                        start = Offset(startX, startY),
                        end = Offset(endX, endY),
                        strokeWidth = 3.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }



        }




// Стрела крана
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            val center = Offset(size.width / 2, size.height / 2)
            val length = 120.dp.toPx()

            val angleRad = Math.toRadians(hmiLineAngle+180.toDouble())

            // Вычисляем конечную точку отрезка
            val endX = center.x + length * cos(angleRad).toFloat()
            val endY = center.y + length * sin(angleRad).toFloat()
            val endPoint = Offset(endX, endY)

            // Рисуем отрезок
            drawLine(
                color = colorSrtela, // Можно изменить цвет
                start = center,
                end = endPoint,
                strokeWidth = 4.dp.toPx(),
                cap = StrokeCap.Round
            )
        }
        // Контр-стрела крана
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            val center = Offset(size.width / 2, size.height / 2)
            val length = 35.dp.toPx()

            val angleRad = Math.toRadians(hmiLineAngle.toDouble())

            // Вычисляем конечную точку отрезка
            val endX = center.x + length * cos(angleRad).toFloat()
            val endY = center.y + length * sin(angleRad).toFloat()
            val endPoint = Offset(endX, endY)

            // Рисуем отрезок
            drawLine(
                color = colorSrtela, // Можно изменить цвет
                start = center,
                end = endPoint,
                strokeWidth = 4.dp.toPx(),
                cap = StrokeCap.Round
            )
        }

// Окружность вылета стрелы
        Canvas (
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.Center)
        ) {
            // Рассчитываем ограниченный диаметр окружности
            val diameter = hmiCircleSizeTrolley/2

            val radius = diameter.coerceIn(7,120)
            val centerX = size.width / 2
            val centerY = size.height / 2

            // Рисуем окружность
            drawCircle(
                color = Color.DarkGray.copy(alpha = 0.5f), // Укажите желаемый цвет
                radius = radius.dp.toPx(),
                center = Offset(centerX, centerY),
                style = Stroke(width = 2.dp.toPx()),
            )
        }
// Canvas для отрисовки положеня тележки
        Canvas(
            modifier = Modifier
                .size(240.dp)
                .align(Alignment.Center)
        ) {
            val center = Offset(size.width/2, size.height/2)
            val radius = hmiCircleSizeTrolley / 2
            val radiusPx = radius.coerceIn(7,120).dp.toPx()

            val angleRad = Math.toRadians(hmiLineAngle+180.toDouble())

            //Вычисляем позицию тележки
            val trolleyX = center.x + radiusPx*cos(angleRad).toFloat()
            val trolleyY = center.y + radiusPx*sin(angleRad).toFloat()
            val trolleyCenter = Offset(trolleyX, trolleyY)

            drawCircle(
                color = colorTrolley,
                center = trolleyCenter,
                radius = 5.dp.toPx() // Размер тележки
            )
        }
// Canvas для отрисовки полигона из 20 отрезков
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            val squareSize = 240.dp.toPx()
            val boxSize = 314.dp.toPx()
            val squareOffset = (boxSize - squareSize) / 2
            val scale = squareSize / 240f

            // Создаем массивы данных для удобства обработки
            val lengths = listOf(
                hmiSegmentP1Length00, hmiSegmentP1Length01, hmiSegmentP1Length02, hmiSegmentP1Length03, hmiSegmentP1Length04,
                hmiSegmentP1Length05, hmiSegmentP1Length06, hmiSegmentP1Length07, hmiSegmentP1Length08, hmiSegmentP1Length09,
                hmiSegmentP1Length10, hmiSegmentP1Length11, hmiSegmentP1Length12, hmiSegmentP1Length13, hmiSegmentP1Length14,
                hmiSegmentP1Length15, hmiSegmentP1Length16, hmiSegmentP1Length17, hmiSegmentP1Length18, hmiSegmentP1Length19
            )

            val positionsX = listOf(
                pointP1PositionX00, pointP1PositionX01, pointP1PositionX02, pointP1PositionX03, pointP1PositionX04,
                pointP1PositionX05, pointP1PositionX06, pointP1PositionX07, pointP1PositionX08, pointP1PositionX09,
                pointP1PositionX10, pointP1PositionX11, pointP1PositionX12, pointP1PositionX13, pointP1PositionX14,
                pointP1PositionX15, pointP1PositionX16, pointP1PositionX17, pointP1PositionX18, pointP1PositionX19
            )

            val positionsY = listOf(
                pointP1PositionY00, pointP1PositionY01, pointP1PositionY02, pointP1PositionY03, pointP1PositionY04,
                pointP1PositionY05, pointP1PositionY06, pointP1PositionY07, pointP1PositionY08, pointP1PositionY09,
                pointP1PositionY10, pointP1PositionY11, pointP1PositionY12, pointP1PositionY13, pointP1PositionY14,
                pointP1PositionY15, pointP1PositionY16, pointP1PositionY17, pointP1PositionY18, pointP1PositionY19
            )

            val angles = listOf(
                segmentP1Angle00_01, segmentP1Angle01_02, segmentP1Angle02_03, segmentP1Angle03_04, segmentP1Angle04_05,
                segmentP1Angle05_06, segmentP1Angle06_07, segmentP1Angle07_08, segmentP1Angle08_09, segmentP1Angle09_10,
                segmentP1Angle10_11, segmentP1Angle11_12, segmentP1Angle12_13, segmentP1Angle13_14, segmentP1Angle14_15,
                segmentP1Angle15_16, segmentP1Angle16_17, segmentP1Angle17_18, segmentP1Angle18_19, segmentP1Angle19_00
            )

            val visibilities = listOf(
                segmentP1Visibility00, segmentP1Visibility01, segmentP1Visibility02, segmentP1Visibility03, segmentP1Visibility04,
                segmentP1Visibility05, segmentP1Visibility06, segmentP1Visibility07, segmentP1Visibility08, segmentP1Visibility09,
                segmentP1Visibility10, segmentP1Visibility11, segmentP1Visibility12, segmentP1Visibility13, segmentP1Visibility14,
                segmentP1Visibility15, segmentP1Visibility16, segmentP1Visibility17, segmentP1Visibility18, segmentP1Visibility19
            )


            // Рисуем все отрезки. Примечание. Начало оси Y с нижнего левого угла squareSize
            for (i in 0 until 20) {
                if (visibilities[i] == 1) {
                    val startX = squareOffset + positionsX[i] * scale
                    val startY = squareOffset + positionsY[i] * scale

                    // Конвертируем угол в радианы с учетом ориентации
                    val angleRad = Math.toRadians(angles[i].toDouble())

                    // Вычисляем конечные координаты
                    val endX = startX + lengths[i] * scale * cos(angleRad).toFloat()
                    val endY = startY + lengths[i] * scale * sin(angleRad).toFloat()

                    // Выбираем цвет в зависимости от параметра
                    val color = when (hmiP1Color) {
                        0 -> Color.Cyan.copy(alpha = 0.6f)
                        1 -> Color.Green
                        2 -> Color.Yellow
                        3 -> Color.Red
                        else -> Color.Cyan.copy(alpha = 0.6f)
                    }

                    // Рисуем отрезок
                    drawLine(
                        color = color,
                        start = Offset(startX, startY),
                        end = Offset(endX, endY),
                        strokeWidth = 3.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }
            }
        }



        //Создадим центр крана с радиусом 5.dp. Черный цвет.
        Canvas (
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
        ) {
            val squareSize = 10.dp.toPx() // Размер квадрата
            val topLeft = Offset(
                (size.width - squareSize) / 2, // Центрирование по горизонтали
                (size.height - squareSize) / 2 // Центрирование по вертикали
            )

            drawRect(
                color = colorSrtela,
                topLeft = topLeft,
                size = Size(squareSize, squareSize),
                style = Stroke(width = 3.dp.toPx()),
            )
        }
    }
}

@Composable
//Создадим функцию для свойств текста в названии механизмов крана (Область с параметрами работы крана)
fun BasicParametersTextSOZR (text:String, modifier:Modifier=Modifier){
    Text(text, modifier, fontSize = 16.sp, textAlign = TextAlign.Center, color = white, fontWeight = FontWeight.Bold ) }



@Preview
@Composable
fun CraneSOZRPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        CraneSOZR(
            hmiLineAngle = 90f,
            hmiCircleSizeTrolley = 60,
            hmiP1Color = 4,
            hmiP2Color = 1,
            hmiP3Color = 1,
            hmiP4Color = 1,

            hmiTrolleyCoordinateY = 60f,
            hmiTrolleyCoordinateX = 60f,

            hmiSegmentP1Length00 = 53,
            hmiSegmentP1Length01 = 40,
            hmiSegmentP1Length02 = 59,
            hmiSegmentP1Length03 = 39,
            hmiSegmentP1Length04 = 0,
            hmiSegmentP1Length05 = 1,
            hmiSegmentP1Length06 = 1,
            hmiSegmentP1Length07 = 1,
            hmiSegmentP1Length08 = 1,
            hmiSegmentP1Length09 = 1,
            hmiSegmentP1Length10 = 1,
            hmiSegmentP1Length11 = 1,
            hmiSegmentP1Length12 = 1,
            hmiSegmentP1Length13 = 1,
            hmiSegmentP1Length14 = 1,
            hmiSegmentP1Length15 = 1,
            hmiSegmentP1Length16 = 1,
            hmiSegmentP1Length17 = 1,
            hmiSegmentP1Length18 = 1,
            hmiSegmentP1Length19 = 1,

            pointP1PositionX00 = 13,
            pointP1PositionX01 = 11,
            pointP1PositionX02 = 11,
            pointP1PositionX03 = 67,
            pointP1PositionX04 = 40,
            pointP1PositionX05 = 1,
            pointP1PositionX06 = 1,
            pointP1PositionX07 = 1,
            pointP1PositionX08 = 1,
            pointP1PositionX09 = 1,
            pointP1PositionX10 = 1,
            pointP1PositionX11 = 1,
            pointP1PositionX12 = 1,
            pointP1PositionX13 = 1,
            pointP1PositionX14 = 1,
            pointP1PositionX15 = 1,
            pointP1PositionX16 = 1,
            pointP1PositionX17 = 1,
            pointP1PositionX18 = 1,
            pointP1PositionX19 = 1,

            pointP1PositionY00 = 80,
            pointP1PositionY01 = 40,
            pointP1PositionY02 = 40,
            pointP1PositionY03 = 80,
            pointP1PositionY04 = 80,
            pointP1PositionY05 = 1,
            pointP1PositionY06 = 1,
            pointP1PositionY07 = 1,
            pointP1PositionY08 = 1,
            pointP1PositionY09 = 1,
            pointP1PositionY10 = 1,
            pointP1PositionY11 = 1,
            pointP1PositionY12 = 1,
            pointP1PositionY13 = 1,
            pointP1PositionY14 = 1,
            pointP1PositionY15 = 1,
            pointP1PositionY16 = 1,
            pointP1PositionY17 = 1,
            pointP1PositionY18 = 1,
            pointP1PositionY19 = 1,



            segmentP1Angle00_01 = 0,
            segmentP1Angle01_02 = 86,
            segmentP1Angle02_03 = 1,
            segmentP1Angle03_04 = 274,
            segmentP1Angle04_05 = 270,
            segmentP1Angle05_06 = 1,
            segmentP1Angle06_07 = 1,
            segmentP1Angle07_08 = 1,
            segmentP1Angle08_09 = 1,
            segmentP1Angle09_10 = 1,
            segmentP1Angle10_11 = 1,
            segmentP1Angle11_12 = 1,
            segmentP1Angle12_13 = 1,
            segmentP1Angle13_14 = 1,
            segmentP1Angle14_15 = 1,
            segmentP1Angle15_16 = 1,
            segmentP1Angle16_17 = 1,
            segmentP1Angle17_18 = 1,
            segmentP1Angle18_19 = 1,
            segmentP1Angle19_00 = 1,

            segmentP1Visibility00 = 1,
            segmentP1Visibility01 = 1,
            segmentP1Visibility02 = 1,
            segmentP1Visibility03 = 1,
            segmentP1Visibility04 = 1,
            segmentP1Visibility05 = 1,
            segmentP1Visibility06 = 1,
            segmentP1Visibility07 = 1,
            segmentP1Visibility08 = 1,
            segmentP1Visibility09 = 1,
            segmentP1Visibility10 = 1,
            segmentP1Visibility11 = 1,
            segmentP1Visibility12 = 1,
            segmentP1Visibility13 = 1,
            segmentP1Visibility14 = 1,
            segmentP1Visibility15 = 1,
            segmentP1Visibility16 = 1,
            segmentP1Visibility17 = 1,
            segmentP1Visibility18 = 1,
            segmentP1Visibility19 = 1,
        )
    }
}