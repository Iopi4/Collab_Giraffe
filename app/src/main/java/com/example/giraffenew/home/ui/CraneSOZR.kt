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
import com.example.giraffenew.home.presentation.HomeScreenViewModel
import com.example.giraffenew.ui.theme.DarkLemon
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.white
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CraneSOZR(

    sozrParams: HomeScreenViewModel.CraneSozrState,
    polygonP1: HomeScreenViewModel.PolygonP1State,
    polygonP2: HomeScreenViewModel.PolygonP2State,
    polygonP3: HomeScreenViewModel.PolygonP3State,
    polygonP4: HomeScreenViewModel.PolygonP4State,

    ){

    // Используем данные из объектов состояния, вместо передачи в функцию большого количества параметров
    val hmiCircleSizeTrolley = sozrParams.hmiCircleSizeTrolley
    val hmiLineAngle = sozrParams.hmiLineAngle

    val hmiTrolleyCoordinateY = sozrParams.hmiTrolleyCoordinateY
    val hmiTrolleyCoordinateX = sozrParams.hmiTrolleyCoordinateX

    val hmiP1Color = sozrParams.hmiP1Color // 0-синий, 1-зеленый, 2-желтый, 3-красный
    val hmiP2Color = sozrParams.hmiP2Color // 0-синий, 1-зеленый, 2-желтый, 3-красный
    val hmiP3Color = sozrParams.hmiP3Color // 0-синий, 1-зеленый, 2-желтый, 3-красный
    val hmiP4Color = sozrParams.hmiP4Color // 0-синий, 1-зеленый, 2-желтый, 3-красный

//Координаты точек полигона №1
    val hmiSegmentP1Length00 = polygonP1.segments.hmiSegmentP1Length00
    val hmiSegmentP1Length01 = polygonP1.segments.hmiSegmentP1Length01
    val hmiSegmentP1Length02 = polygonP1.segments.hmiSegmentP1Length02
    val hmiSegmentP1Length03 = polygonP1.segments.hmiSegmentP1Length03
    val hmiSegmentP1Length04 = polygonP1.segments.hmiSegmentP1Length04
    val hmiSegmentP1Length05 = polygonP1.segments.hmiSegmentP1Length05
    val hmiSegmentP1Length06 = polygonP1.segments.hmiSegmentP1Length06
    val hmiSegmentP1Length07 = polygonP1.segments.hmiSegmentP1Length07
    val hmiSegmentP1Length08 = polygonP1.segments.hmiSegmentP1Length08
    val hmiSegmentP1Length09 = polygonP1.segments.hmiSegmentP1Length09
    val hmiSegmentP1Length10 = polygonP1.segments.hmiSegmentP1Length10
    val hmiSegmentP1Length11 = polygonP1.segments.hmiSegmentP1Length11
    val hmiSegmentP1Length12 = polygonP1.segments.hmiSegmentP1Length12
    val hmiSegmentP1Length13 = polygonP1.segments.hmiSegmentP1Length13
    val hmiSegmentP1Length14 = polygonP1.segments.hmiSegmentP1Length14
    val hmiSegmentP1Length15 = polygonP1.segments.hmiSegmentP1Length15
    val hmiSegmentP1Length16 = polygonP1.segments.hmiSegmentP1Length16
    val hmiSegmentP1Length17 = polygonP1.segments.hmiSegmentP1Length17
    val hmiSegmentP1Length18 = polygonP1.segments.hmiSegmentP1Length18
    val hmiSegmentP1Length19 = polygonP1.segments.hmiSegmentP1Length19

    val pointP1PositionY00 = polygonP1.points.pointP1PositionY00
    val pointP1PositionY01 = polygonP1.points.pointP1PositionY01
    val pointP1PositionY02 = polygonP1.points.pointP1PositionY02
    val pointP1PositionY03 = polygonP1.points.pointP1PositionY03
    val pointP1PositionY04 = polygonP1.points.pointP1PositionY04
    val pointP1PositionY05 = polygonP1.points.pointP1PositionY05
    val pointP1PositionY06 = polygonP1.points.pointP1PositionY06
    val pointP1PositionY07 = polygonP1.points.pointP1PositionY07
    val pointP1PositionY08 = polygonP1.points.pointP1PositionY08
    val pointP1PositionY09 = polygonP1.points.pointP1PositionY09
    val pointP1PositionY10 = polygonP1.points.pointP1PositionY10
    val pointP1PositionY11 = polygonP1.points.pointP1PositionY11
    val pointP1PositionY12 = polygonP1.points.pointP1PositionY12
    val pointP1PositionY13 = polygonP1.points.pointP1PositionY13
    val pointP1PositionY14 = polygonP1.points.pointP1PositionY14
    val pointP1PositionY15 = polygonP1.points.pointP1PositionY15
    val pointP1PositionY16 = polygonP1.points.pointP1PositionY16
    val pointP1PositionY17 = polygonP1.points.pointP1PositionY17
    val pointP1PositionY18 = polygonP1.points.pointP1PositionY18
    val pointP1PositionY19 = polygonP1.points.pointP1PositionY19

    val pointP1PositionX00 = polygonP1.points.pointP1PositionX00
    val pointP1PositionX01 = polygonP1.points.pointP1PositionX01
    val pointP1PositionX02 = polygonP1.points.pointP1PositionX02
    val pointP1PositionX03 = polygonP1.points.pointP1PositionX03
    val pointP1PositionX04 = polygonP1.points.pointP1PositionX04
    val pointP1PositionX05 = polygonP1.points.pointP1PositionX05
    val pointP1PositionX06 = polygonP1.points.pointP1PositionX06
    val pointP1PositionX07 = polygonP1.points.pointP1PositionX07
    val pointP1PositionX08 = polygonP1.points.pointP1PositionX08
    val pointP1PositionX09 = polygonP1.points.pointP1PositionX09
    val pointP1PositionX10 = polygonP1.points.pointP1PositionX10
    val pointP1PositionX11 = polygonP1.points.pointP1PositionX11
    val pointP1PositionX12 = polygonP1.points.pointP1PositionX12
    val pointP1PositionX13 = polygonP1.points.pointP1PositionX13
    val pointP1PositionX14 = polygonP1.points.pointP1PositionX14
    val pointP1PositionX15 = polygonP1.points.pointP1PositionX15
    val pointP1PositionX16 = polygonP1.points.pointP1PositionX16
    val pointP1PositionX17 = polygonP1.points.pointP1PositionX17
    val pointP1PositionX18 = polygonP1.points.pointP1PositionX18
    val pointP1PositionX19 = polygonP1.points.pointP1PositionX19

    val segmentP1Angle00 = polygonP1.angles.segmentP1Angle00
    val segmentP1Angle01 = polygonP1.angles.segmentP1Angle01
    val segmentP1Angle02 = polygonP1.angles.segmentP1Angle02
    val segmentP1Angle03 = polygonP1.angles.segmentP1Angle03
    val segmentP1Angle04 = polygonP1.angles.segmentP1Angle04
    val segmentP1Angle05 = polygonP1.angles.segmentP1Angle05
    val segmentP1Angle06 = polygonP1.angles.segmentP1Angle06
    val segmentP1Angle07 = polygonP1.angles.segmentP1Angle07
    val segmentP1Angle08 = polygonP1.angles.segmentP1Angle08
    val segmentP1Angle09 = polygonP1.angles.segmentP1Angle09
    val segmentP1Angle10 = polygonP1.angles.segmentP1Angle10
    val segmentP1Angle11 = polygonP1.angles.segmentP1Angle11
    val segmentP1Angle12 = polygonP1.angles.segmentP1Angle12
    val segmentP1Angle13 = polygonP1.angles.segmentP1Angle13
    val segmentP1Angle14 = polygonP1.angles.segmentP1Angle14
    val segmentP1Angle15 = polygonP1.angles.segmentP1Angle15
    val segmentP1Angle16 = polygonP1.angles.segmentP1Angle16
    val segmentP1Angle17 = polygonP1.angles.segmentP1Angle17
    val segmentP1Angle18 = polygonP1.angles.segmentP1Angle18
    val segmentP1Angle19 = polygonP1.angles.segmentP1Angle19

    val segmentP1Visibility00 = polygonP1.visibility.segmentP1Visibility00
    val segmentP1Visibility01 = polygonP1.visibility.segmentP1Visibility01
    val segmentP1Visibility02 = polygonP1.visibility.segmentP1Visibility02
    val segmentP1Visibility03 = polygonP1.visibility.segmentP1Visibility03
    val segmentP1Visibility04 = polygonP1.visibility.segmentP1Visibility04
    val segmentP1Visibility05 = polygonP1.visibility.segmentP1Visibility05
    val segmentP1Visibility06 = polygonP1.visibility.segmentP1Visibility06
    val segmentP1Visibility07 = polygonP1.visibility.segmentP1Visibility07
    val segmentP1Visibility08 = polygonP1.visibility.segmentP1Visibility08
    val segmentP1Visibility09 = polygonP1.visibility.segmentP1Visibility09
    val segmentP1Visibility10 = polygonP1.visibility.segmentP1Visibility10
    val segmentP1Visibility11 = polygonP1.visibility.segmentP1Visibility11
    val segmentP1Visibility12 = polygonP1.visibility.segmentP1Visibility12
    val segmentP1Visibility13 = polygonP1.visibility.segmentP1Visibility13
    val segmentP1Visibility14 = polygonP1.visibility.segmentP1Visibility14
    val segmentP1Visibility15 = polygonP1.visibility.segmentP1Visibility15
    val segmentP1Visibility16 = polygonP1.visibility.segmentP1Visibility16
    val segmentP1Visibility17 = polygonP1.visibility.segmentP1Visibility17
    val segmentP1Visibility18 = polygonP1.visibility.segmentP1Visibility18
    val segmentP1Visibility19 = polygonP1.visibility.segmentP1Visibility19

//Координаты точек полигона №2
    val hmiSegmentP2Length00 = polygonP2.segments.hmiSegmentP2Length00
    val hmiSegmentP2Length01 = polygonP2.segments.hmiSegmentP2Length01
    val hmiSegmentP2Length02 = polygonP2.segments.hmiSegmentP2Length02
    val hmiSegmentP2Length03 = polygonP2.segments.hmiSegmentP2Length03
    val hmiSegmentP2Length04 = polygonP2.segments.hmiSegmentP2Length04
    val hmiSegmentP2Length05 = polygonP2.segments.hmiSegmentP2Length05
    val hmiSegmentP2Length06 = polygonP2.segments.hmiSegmentP2Length06
    val hmiSegmentP2Length07 = polygonP2.segments.hmiSegmentP2Length07
    val hmiSegmentP2Length08 = polygonP2.segments.hmiSegmentP2Length08
    val hmiSegmentP2Length09 = polygonP2.segments.hmiSegmentP2Length09
    val hmiSegmentP2Length10 = polygonP2.segments.hmiSegmentP2Length10
    val hmiSegmentP2Length11 = polygonP2.segments.hmiSegmentP2Length11
    val hmiSegmentP2Length12 = polygonP2.segments.hmiSegmentP2Length12
    val hmiSegmentP2Length13 = polygonP2.segments.hmiSegmentP2Length13
    val hmiSegmentP2Length14 = polygonP2.segments.hmiSegmentP2Length14
    val hmiSegmentP2Length15 = polygonP2.segments.hmiSegmentP2Length15
    val hmiSegmentP2Length16 = polygonP2.segments.hmiSegmentP2Length16
    val hmiSegmentP2Length17 = polygonP2.segments.hmiSegmentP2Length17
    val hmiSegmentP2Length18 = polygonP2.segments.hmiSegmentP2Length18
    val hmiSegmentP2Length19 = polygonP2.segments.hmiSegmentP2Length19

    val pointP2PositionY00 = polygonP2.points.pointP2PositionY00
    val pointP2PositionY01 = polygonP2.points.pointP2PositionY01
    val pointP2PositionY02 = polygonP2.points.pointP2PositionY02
    val pointP2PositionY03 = polygonP2.points.pointP2PositionY03
    val pointP2PositionY04 = polygonP2.points.pointP2PositionY04
    val pointP2PositionY05 = polygonP2.points.pointP2PositionY05
    val pointP2PositionY06 = polygonP2.points.pointP2PositionY06
    val pointP2PositionY07 = polygonP2.points.pointP2PositionY07
    val pointP2PositionY08 = polygonP2.points.pointP2PositionY08
    val pointP2PositionY09 = polygonP2.points.pointP2PositionY09
    val pointP2PositionY10 = polygonP2.points.pointP2PositionY10
    val pointP2PositionY11 = polygonP2.points.pointP2PositionY11
    val pointP2PositionY12 = polygonP2.points.pointP2PositionY12
    val pointP2PositionY13 = polygonP2.points.pointP2PositionY13
    val pointP2PositionY14 = polygonP2.points.pointP2PositionY14
    val pointP2PositionY15 = polygonP2.points.pointP2PositionY15
    val pointP2PositionY16 = polygonP2.points.pointP2PositionY16
    val pointP2PositionY17 = polygonP2.points.pointP2PositionY17
    val pointP2PositionY18 = polygonP2.points.pointP2PositionY18
    val pointP2PositionY19 = polygonP2.points.pointP2PositionY19

    val pointP2PositionX00 = polygonP2.points.pointP2PositionX00
    val pointP2PositionX01 = polygonP2.points.pointP2PositionX01
    val pointP2PositionX02 = polygonP2.points.pointP2PositionX02
    val pointP2PositionX03 = polygonP2.points.pointP2PositionX03
    val pointP2PositionX04 = polygonP2.points.pointP2PositionX04
    val pointP2PositionX05 = polygonP2.points.pointP2PositionX05
    val pointP2PositionX06 = polygonP2.points.pointP2PositionX06
    val pointP2PositionX07 = polygonP2.points.pointP2PositionX07
    val pointP2PositionX08 = polygonP2.points.pointP2PositionX08
    val pointP2PositionX09 = polygonP2.points.pointP2PositionX09
    val pointP2PositionX10 = polygonP2.points.pointP2PositionX10
    val pointP2PositionX11 = polygonP2.points.pointP2PositionX11
    val pointP2PositionX12 = polygonP2.points.pointP2PositionX12
    val pointP2PositionX13 = polygonP2.points.pointP2PositionX13
    val pointP2PositionX14 = polygonP2.points.pointP2PositionX14
    val pointP2PositionX15 = polygonP2.points.pointP2PositionX15
    val pointP2PositionX16 = polygonP2.points.pointP2PositionX16
    val pointP2PositionX17 = polygonP2.points.pointP2PositionX17
    val pointP2PositionX18 = polygonP2.points.pointP2PositionX18
    val pointP2PositionX19 = polygonP2.points.pointP2PositionX19

    val segmentP2Angle00 = polygonP2.angles.segmentP2Angle00
    val segmentP2Angle01 = polygonP2.angles.segmentP2Angle01
    val segmentP2Angle02 = polygonP2.angles.segmentP2Angle02
    val segmentP2Angle03 = polygonP2.angles.segmentP2Angle03
    val segmentP2Angle04 = polygonP2.angles.segmentP2Angle04
    val segmentP2Angle05 = polygonP2.angles.segmentP2Angle05
    val segmentP2Angle06 = polygonP2.angles.segmentP2Angle06
    val segmentP2Angle07 = polygonP2.angles.segmentP2Angle07
    val segmentP2Angle08 = polygonP2.angles.segmentP2Angle08
    val segmentP2Angle09 = polygonP2.angles.segmentP2Angle09
    val segmentP2Angle10 = polygonP2.angles.segmentP2Angle10
    val segmentP2Angle11 = polygonP2.angles.segmentP2Angle11
    val segmentP2Angle12 = polygonP2.angles.segmentP2Angle12
    val segmentP2Angle13 = polygonP2.angles.segmentP2Angle13
    val segmentP2Angle14 = polygonP2.angles.segmentP2Angle14
    val segmentP2Angle15 = polygonP2.angles.segmentP2Angle15
    val segmentP2Angle16 = polygonP2.angles.segmentP2Angle16
    val segmentP2Angle17 = polygonP2.angles.segmentP2Angle17
    val segmentP2Angle18 = polygonP2.angles.segmentP2Angle18
    val segmentP2Angle19 = polygonP2.angles.segmentP2Angle19

    val segmentP2Visibility00 = polygonP2.visibility.segmentP2Visibility00
    val segmentP2Visibility01 = polygonP2.visibility.segmentP2Visibility01
    val segmentP2Visibility02 = polygonP2.visibility.segmentP2Visibility02
    val segmentP2Visibility03 = polygonP2.visibility.segmentP2Visibility03
    val segmentP2Visibility04 = polygonP2.visibility.segmentP2Visibility04
    val segmentP2Visibility05 = polygonP2.visibility.segmentP2Visibility05
    val segmentP2Visibility06 = polygonP2.visibility.segmentP2Visibility06
    val segmentP2Visibility07 = polygonP2.visibility.segmentP2Visibility07
    val segmentP2Visibility08 = polygonP2.visibility.segmentP2Visibility08
    val segmentP2Visibility09 = polygonP2.visibility.segmentP2Visibility09
    val segmentP2Visibility10 = polygonP2.visibility.segmentP2Visibility10
    val segmentP2Visibility11 = polygonP2.visibility.segmentP2Visibility11
    val segmentP2Visibility12 = polygonP2.visibility.segmentP2Visibility12
    val segmentP2Visibility13 = polygonP2.visibility.segmentP2Visibility13
    val segmentP2Visibility14 = polygonP2.visibility.segmentP2Visibility14
    val segmentP2Visibility15 = polygonP2.visibility.segmentP2Visibility15
    val segmentP2Visibility16 = polygonP2.visibility.segmentP2Visibility16
    val segmentP2Visibility17 = polygonP2.visibility.segmentP2Visibility17
    val segmentP2Visibility18 = polygonP2.visibility.segmentP2Visibility18
    val segmentP2Visibility19 = polygonP2.visibility.segmentP2Visibility19

//Координаты точек полигона №3
    val hmiSegmentP3Length00 = polygonP3.segments.hmiSegmentP3Length00
    val hmiSegmentP3Length01 = polygonP3.segments.hmiSegmentP3Length01
    val hmiSegmentP3Length02 = polygonP3.segments.hmiSegmentP3Length02
    val hmiSegmentP3Length03 = polygonP3.segments.hmiSegmentP3Length03
    val hmiSegmentP3Length04 = polygonP3.segments.hmiSegmentP3Length04
    val hmiSegmentP3Length05 = polygonP3.segments.hmiSegmentP3Length05
    val hmiSegmentP3Length06 = polygonP3.segments.hmiSegmentP3Length06
    val hmiSegmentP3Length07 = polygonP3.segments.hmiSegmentP3Length07
    val hmiSegmentP3Length08 = polygonP3.segments.hmiSegmentP3Length08
    val hmiSegmentP3Length09 = polygonP3.segments.hmiSegmentP3Length09
    val hmiSegmentP3Length10 = polygonP3.segments.hmiSegmentP3Length10
    val hmiSegmentP3Length11 = polygonP3.segments.hmiSegmentP3Length11
    val hmiSegmentP3Length12 = polygonP3.segments.hmiSegmentP3Length12
    val hmiSegmentP3Length13 = polygonP3.segments.hmiSegmentP3Length13
    val hmiSegmentP3Length14 = polygonP3.segments.hmiSegmentP3Length14
    val hmiSegmentP3Length15 = polygonP3.segments.hmiSegmentP3Length15
    val hmiSegmentP3Length16 = polygonP3.segments.hmiSegmentP3Length16
    val hmiSegmentP3Length17 = polygonP3.segments.hmiSegmentP3Length17
    val hmiSegmentP3Length18 = polygonP3.segments.hmiSegmentP3Length18
    val hmiSegmentP3Length19 = polygonP3.segments.hmiSegmentP3Length19

    val pointP3PositionY00 = polygonP3.points.pointP3PositionY00
    val pointP3PositionY01 = polygonP3.points.pointP3PositionY01
    val pointP3PositionY02 = polygonP3.points.pointP3PositionY02
    val pointP3PositionY03 = polygonP3.points.pointP3PositionY03
    val pointP3PositionY04 = polygonP3.points.pointP3PositionY04
    val pointP3PositionY05 = polygonP3.points.pointP3PositionY05
    val pointP3PositionY06 = polygonP3.points.pointP3PositionY06
    val pointP3PositionY07 = polygonP3.points.pointP3PositionY07
    val pointP3PositionY08 = polygonP3.points.pointP3PositionY08
    val pointP3PositionY09 = polygonP3.points.pointP3PositionY09
    val pointP3PositionY10 = polygonP3.points.pointP3PositionY10
    val pointP3PositionY11 = polygonP3.points.pointP3PositionY11
    val pointP3PositionY12 = polygonP3.points.pointP3PositionY12
    val pointP3PositionY13 = polygonP3.points.pointP3PositionY13
    val pointP3PositionY14 = polygonP3.points.pointP3PositionY14
    val pointP3PositionY15 = polygonP3.points.pointP3PositionY15
    val pointP3PositionY16 = polygonP3.points.pointP3PositionY16
    val pointP3PositionY17 = polygonP3.points.pointP3PositionY17
    val pointP3PositionY18 = polygonP3.points.pointP3PositionY18
    val pointP3PositionY19 = polygonP3.points.pointP3PositionY19

    val pointP3PositionX00 = polygonP3.points.pointP3PositionX00
    val pointP3PositionX01 = polygonP3.points.pointP3PositionX01
    val pointP3PositionX02 = polygonP3.points.pointP3PositionX02
    val pointP3PositionX03 = polygonP3.points.pointP3PositionX03
    val pointP3PositionX04 = polygonP3.points.pointP3PositionX04
    val pointP3PositionX05 = polygonP3.points.pointP3PositionX05
    val pointP3PositionX06 = polygonP3.points.pointP3PositionX06
    val pointP3PositionX07 = polygonP3.points.pointP3PositionX07
    val pointP3PositionX08 = polygonP3.points.pointP3PositionX08
    val pointP3PositionX09 = polygonP3.points.pointP3PositionX09
    val pointP3PositionX10 = polygonP3.points.pointP3PositionX10
    val pointP3PositionX11 = polygonP3.points.pointP3PositionX11
    val pointP3PositionX12 = polygonP3.points.pointP3PositionX12
    val pointP3PositionX13 = polygonP3.points.pointP3PositionX13
    val pointP3PositionX14 = polygonP3.points.pointP3PositionX14
    val pointP3PositionX15 = polygonP3.points.pointP3PositionX15
    val pointP3PositionX16 = polygonP3.points.pointP3PositionX16
    val pointP3PositionX17 = polygonP3.points.pointP3PositionX17
    val pointP3PositionX18 = polygonP3.points.pointP3PositionX18
    val pointP3PositionX19 = polygonP3.points.pointP3PositionX19

    val segmentP3Angle00 = polygonP3.angles.segmentP3Angle00
    val segmentP3Angle01 = polygonP3.angles.segmentP3Angle01
    val segmentP3Angle02 = polygonP3.angles.segmentP3Angle02
    val segmentP3Angle03 = polygonP3.angles.segmentP3Angle03
    val segmentP3Angle04 = polygonP3.angles.segmentP3Angle04
    val segmentP3Angle05 = polygonP3.angles.segmentP3Angle05
    val segmentP3Angle06 = polygonP3.angles.segmentP3Angle06
    val segmentP3Angle07 = polygonP3.angles.segmentP3Angle07
    val segmentP3Angle08 = polygonP3.angles.segmentP3Angle08
    val segmentP3Angle09 = polygonP3.angles.segmentP3Angle09
    val segmentP3Angle10 = polygonP3.angles.segmentP3Angle10
    val segmentP3Angle11 = polygonP3.angles.segmentP3Angle11
    val segmentP3Angle12 = polygonP3.angles.segmentP3Angle12
    val segmentP3Angle13 = polygonP3.angles.segmentP3Angle13
    val segmentP3Angle14 = polygonP3.angles.segmentP3Angle14
    val segmentP3Angle15 = polygonP3.angles.segmentP3Angle15
    val segmentP3Angle16 = polygonP3.angles.segmentP3Angle16
    val segmentP3Angle17 = polygonP3.angles.segmentP3Angle17
    val segmentP3Angle18 = polygonP3.angles.segmentP3Angle18
    val segmentP3Angle19 = polygonP3.angles.segmentP3Angle19

    val segmentP3Visibility00 = polygonP3.visibility.segmentP3Visibility00
    val segmentP3Visibility01 = polygonP3.visibility.segmentP3Visibility01
    val segmentP3Visibility02 = polygonP3.visibility.segmentP3Visibility02
    val segmentP3Visibility03 = polygonP3.visibility.segmentP3Visibility03
    val segmentP3Visibility04 = polygonP3.visibility.segmentP3Visibility04
    val segmentP3Visibility05 = polygonP3.visibility.segmentP3Visibility05
    val segmentP3Visibility06 = polygonP3.visibility.segmentP3Visibility06
    val segmentP3Visibility07 = polygonP3.visibility.segmentP3Visibility07
    val segmentP3Visibility08 = polygonP3.visibility.segmentP3Visibility08
    val segmentP3Visibility09 = polygonP3.visibility.segmentP3Visibility09
    val segmentP3Visibility10 = polygonP3.visibility.segmentP3Visibility10
    val segmentP3Visibility11 = polygonP3.visibility.segmentP3Visibility11
    val segmentP3Visibility12 = polygonP3.visibility.segmentP3Visibility12
    val segmentP3Visibility13 = polygonP3.visibility.segmentP3Visibility13
    val segmentP3Visibility14 = polygonP3.visibility.segmentP3Visibility14
    val segmentP3Visibility15 = polygonP3.visibility.segmentP3Visibility15
    val segmentP3Visibility16 = polygonP3.visibility.segmentP3Visibility16
    val segmentP3Visibility17 = polygonP3.visibility.segmentP3Visibility17
    val segmentP3Visibility18 = polygonP3.visibility.segmentP3Visibility18
    val segmentP3Visibility19 = polygonP3.visibility.segmentP3Visibility19

//Координаты точек полигона №4
    val hmiSegmentP4Length00 = polygonP4.segments.hmiSegmentP4Length00
    val hmiSegmentP4Length01 = polygonP4.segments.hmiSegmentP4Length01
    val hmiSegmentP4Length02 = polygonP4.segments.hmiSegmentP4Length02
    val hmiSegmentP4Length03 = polygonP4.segments.hmiSegmentP4Length03
    val hmiSegmentP4Length04 = polygonP4.segments.hmiSegmentP4Length04
    val hmiSegmentP4Length05 = polygonP4.segments.hmiSegmentP4Length05
    val hmiSegmentP4Length06 = polygonP4.segments.hmiSegmentP4Length06
    val hmiSegmentP4Length07 = polygonP4.segments.hmiSegmentP4Length07
    val hmiSegmentP4Length08 = polygonP4.segments.hmiSegmentP4Length08
    val hmiSegmentP4Length09 = polygonP4.segments.hmiSegmentP4Length09
    val hmiSegmentP4Length10 = polygonP4.segments.hmiSegmentP4Length10
    val hmiSegmentP4Length11 = polygonP4.segments.hmiSegmentP4Length11
    val hmiSegmentP4Length12 = polygonP4.segments.hmiSegmentP4Length12
    val hmiSegmentP4Length13 = polygonP4.segments.hmiSegmentP4Length13
    val hmiSegmentP4Length14 = polygonP4.segments.hmiSegmentP4Length14
    val hmiSegmentP4Length15 = polygonP4.segments.hmiSegmentP4Length15
    val hmiSegmentP4Length16 = polygonP4.segments.hmiSegmentP4Length16
    val hmiSegmentP4Length17 = polygonP4.segments.hmiSegmentP4Length17
    val hmiSegmentP4Length18 = polygonP4.segments.hmiSegmentP4Length18
    val hmiSegmentP4Length19 = polygonP4.segments.hmiSegmentP4Length19

    val pointP4PositionY00 = polygonP4.points.pointP4PositionY00
    val pointP4PositionY01 = polygonP4.points.pointP4PositionY01
    val pointP4PositionY02 = polygonP4.points.pointP4PositionY02
    val pointP4PositionY03 = polygonP4.points.pointP4PositionY03
    val pointP4PositionY04 = polygonP4.points.pointP4PositionY04
    val pointP4PositionY05 = polygonP4.points.pointP4PositionY05
    val pointP4PositionY06 = polygonP4.points.pointP4PositionY06
    val pointP4PositionY07 = polygonP4.points.pointP4PositionY07
    val pointP4PositionY08 = polygonP4.points.pointP4PositionY08
    val pointP4PositionY09 = polygonP4.points.pointP4PositionY09
    val pointP4PositionY10 = polygonP4.points.pointP4PositionY10
    val pointP4PositionY11 = polygonP4.points.pointP4PositionY11
    val pointP4PositionY12 = polygonP4.points.pointP4PositionY12
    val pointP4PositionY13 = polygonP4.points.pointP4PositionY13
    val pointP4PositionY14 = polygonP4.points.pointP4PositionY14
    val pointP4PositionY15 = polygonP4.points.pointP4PositionY15
    val pointP4PositionY16 = polygonP4.points.pointP4PositionY16
    val pointP4PositionY17 = polygonP4.points.pointP4PositionY17
    val pointP4PositionY18 = polygonP4.points.pointP4PositionY18
    val pointP4PositionY19 = polygonP4.points.pointP4PositionY19

    val pointP4PositionX00 = polygonP4.points.pointP4PositionX00
    val pointP4PositionX01 = polygonP4.points.pointP4PositionX01
    val pointP4PositionX02 = polygonP4.points.pointP4PositionX02
    val pointP4PositionX03 = polygonP4.points.pointP4PositionX03
    val pointP4PositionX04 = polygonP4.points.pointP4PositionX04
    val pointP4PositionX05 = polygonP4.points.pointP4PositionX05
    val pointP4PositionX06 = polygonP4.points.pointP4PositionX06
    val pointP4PositionX07 = polygonP4.points.pointP4PositionX07
    val pointP4PositionX08 = polygonP4.points.pointP4PositionX08
    val pointP4PositionX09 = polygonP4.points.pointP4PositionX09
    val pointP4PositionX10 = polygonP4.points.pointP4PositionX10
    val pointP4PositionX11 = polygonP4.points.pointP4PositionX11
    val pointP4PositionX12 = polygonP4.points.pointP4PositionX12
    val pointP4PositionX13 = polygonP4.points.pointP4PositionX13
    val pointP4PositionX14 = polygonP4.points.pointP4PositionX14
    val pointP4PositionX15 = polygonP4.points.pointP4PositionX15
    val pointP4PositionX16 = polygonP4.points.pointP4PositionX16
    val pointP4PositionX17 = polygonP4.points.pointP4PositionX17
    val pointP4PositionX18 = polygonP4.points.pointP4PositionX18
    val pointP4PositionX19 = polygonP4.points.pointP4PositionX19

    val segmentP4Angle00 = polygonP4.angles.segmentP4Angle00
    val segmentP4Angle01 = polygonP4.angles.segmentP4Angle01
    val segmentP4Angle02 = polygonP4.angles.segmentP4Angle02
    val segmentP4Angle03 = polygonP4.angles.segmentP4Angle03
    val segmentP4Angle04 = polygonP4.angles.segmentP4Angle04
    val segmentP4Angle05 = polygonP4.angles.segmentP4Angle05
    val segmentP4Angle06 = polygonP4.angles.segmentP4Angle06
    val segmentP4Angle07 = polygonP4.angles.segmentP4Angle07
    val segmentP4Angle08 = polygonP4.angles.segmentP4Angle08
    val segmentP4Angle09 = polygonP4.angles.segmentP4Angle09
    val segmentP4Angle10 = polygonP4.angles.segmentP4Angle10
    val segmentP4Angle11 = polygonP4.angles.segmentP4Angle11
    val segmentP4Angle12 = polygonP4.angles.segmentP4Angle12
    val segmentP4Angle13 = polygonP4.angles.segmentP4Angle13
    val segmentP4Angle14 = polygonP4.angles.segmentP4Angle14
    val segmentP4Angle15 = polygonP4.angles.segmentP4Angle15
    val segmentP4Angle16 = polygonP4.angles.segmentP4Angle16
    val segmentP4Angle17 = polygonP4.angles.segmentP4Angle17
    val segmentP4Angle18 = polygonP4.angles.segmentP4Angle18
    val segmentP4Angle19 = polygonP4.angles.segmentP4Angle19

    val segmentP4Visibility00 = polygonP4.visibility.segmentP4Visibility00
    val segmentP4Visibility01 = polygonP4.visibility.segmentP4Visibility01
    val segmentP4Visibility02 = polygonP4.visibility.segmentP4Visibility02
    val segmentP4Visibility03 = polygonP4.visibility.segmentP4Visibility03
    val segmentP4Visibility04 = polygonP4.visibility.segmentP4Visibility04
    val segmentP4Visibility05 = polygonP4.visibility.segmentP4Visibility05
    val segmentP4Visibility06 = polygonP4.visibility.segmentP4Visibility06
    val segmentP4Visibility07 = polygonP4.visibility.segmentP4Visibility07
    val segmentP4Visibility08 = polygonP4.visibility.segmentP4Visibility08
    val segmentP4Visibility09 = polygonP4.visibility.segmentP4Visibility09
    val segmentP4Visibility10 = polygonP4.visibility.segmentP4Visibility10
    val segmentP4Visibility11 = polygonP4.visibility.segmentP4Visibility11
    val segmentP4Visibility12 = polygonP4.visibility.segmentP4Visibility12
    val segmentP4Visibility13 = polygonP4.visibility.segmentP4Visibility13
    val segmentP4Visibility14 = polygonP4.visibility.segmentP4Visibility14
    val segmentP4Visibility15 = polygonP4.visibility.segmentP4Visibility15
    val segmentP4Visibility16 = polygonP4.visibility.segmentP4Visibility16
    val segmentP4Visibility17 = polygonP4.visibility.segmentP4Visibility17
    val segmentP4Visibility18 = polygonP4.visibility.segmentP4Visibility18
    val segmentP4Visibility19 = polygonP4.visibility.segmentP4Visibility19





    val myBoxMainModifier = Modifier
        .fillMaxSize()
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .border(
            width = 2.dp,
            color = DarkLemon,
            shape = RoundedCornerShape(10.dp)
        )

    val myBoxModifier = Modifier.size(314.dp)

    val colorSrtela = colorResource(R.color.Orange)
    val colorTrolley = colorResource(R.color.Burgundy_violet)
    val colorCoordinate = colorResource(R.color.Silver)

    val hmiSegmentCoordinateLengthX: Int = 270 // Длина координаты X
    val hmiSegmentCoordinateLengthY: Int = 270 // Длина координаты Y
    val pointCoordinateX0 = 10 // Начало координат X
    val pointCoordinateY0 = 290 // Начало координат Y
    val pointCoordinateX1 = 10 // Начало координат X
    val pointCoordinateY1 = 290 // Начало координат Y
    val coordinateAngleX = 0 //Угол координаты X
    val coordinateAngleY = 270 //Угол координаты Y
    val coordinateVisibilityX = 1 //Видимость оси координат
    val coordinateVisibilityY = 1 //Видимость оси координат

    //Бокс 1 - для крана с ГХ
    Box(myBoxMainModifier){
        Box(myBoxModifier){
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


            Canvas (
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .matchParentSize()
            ) {
// Окружность рабочей области. WorkingCircumference
                drawCircle(
                    color = Color.LightGray.copy(alpha = 0.95f), // Замените на нужный цвет
                    center = center,
                    radius = 120.dp.toPx(),
                )

// Рисуем все отрезки. Примечание. Начало оси Y с нижнего левого угла squareSize
                val squareSizeWorkingCircumference = 300.dp.toPx()
                val boxSizeWorkingCircumference = 314.dp.toPx()
                val squareOffsetWorkingCircumference = (boxSizeWorkingCircumference - squareSizeWorkingCircumference) / 2
                val scaleWorkingCircumference = squareSizeWorkingCircumference / 300f

                // Создаем массивы данных для удобства обработки
                val lengthsWorkingCircumference = listOf(hmiSegmentCoordinateLengthX, hmiSegmentCoordinateLengthY)
                val positionsXWorkingCircumference = listOf(pointCoordinateX0, pointCoordinateX1)
                val positionsYWorkingCircumference = listOf(pointCoordinateY0, pointCoordinateY1)
                val anglesWorkingCircumference = listOf(coordinateAngleX, coordinateAngleY)
                val visibilitiesWorkingCircumference = listOf(coordinateVisibilityX, coordinateVisibilityY)

                for (i in 0 until 2) {
                    if (visibilitiesWorkingCircumference[i] == 1) {
                        val startX = squareOffsetWorkingCircumference + positionsXWorkingCircumference[i] * scaleWorkingCircumference
                        val startY = squareOffsetWorkingCircumference + positionsYWorkingCircumference[i] * scaleWorkingCircumference

                        // Конвертируем угол в радианы с учетом ориентации
                        val angleRad = Math.toRadians(anglesWorkingCircumference[i].toDouble())

                        // Вычисляем конечные координаты
                        val endX = startX + lengthsWorkingCircumference[i] * scaleWorkingCircumference * cos(angleRad).toFloat()
                        val endY = startY + lengthsWorkingCircumference[i] * scaleWorkingCircumference * sin(angleRad).toFloat()

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

// Стрела крана. CraneBoom
                val centerCraneBoom = Offset(size.width / 2, size.height / 2)
                val lengthCraneBoom = 120.dp.toPx()

                val angleRadCraneBoom = Math.toRadians(hmiLineAngle+180.toDouble())

                // Вычисляем конечную точку отрезка
                val endXCraneBoom = centerCraneBoom.x + lengthCraneBoom * cos(angleRadCraneBoom).toFloat()
                val endYCraneBoom = centerCraneBoom.y + lengthCraneBoom * sin(angleRadCraneBoom).toFloat()
                val endPointCraneBoom = Offset(endXCraneBoom, endYCraneBoom)

                // Рисуем отрезок
                drawLine(
                    color = colorSrtela, // Можно изменить цвет
                    start = centerCraneBoom,
                    end = endPointCraneBoom,
                    strokeWidth = 4.dp.toPx(),
                    cap = StrokeCap.Round
                )

// Контр-стрела крана. CraneCounterJib
                val centerCraneCounterJib = Offset(size.width / 2, size.height / 2)
                val lengthCraneCounterJib = 35.dp.toPx()

                val angleRadCraneCounterJib = Math.toRadians(hmiLineAngle.toDouble())

                // Вычисляем конечную точку отрезка
                val endXCraneCounterJib = centerCraneCounterJib.x + lengthCraneCounterJib * cos(angleRadCraneCounterJib).toFloat()
                val endYCraneCounterJib = centerCraneCounterJib.y + lengthCraneCounterJib * sin(angleRadCraneCounterJib).toFloat()
                val endPointCraneCounterJib = Offset(endXCraneCounterJib, endYCraneCounterJib)

                // Рисуем отрезок
                drawLine(
                    color = colorSrtela, // Можно изменить цвет
                    start = centerCraneCounterJib,
                    end = endPointCraneCounterJib,
                    strokeWidth = 4.dp.toPx(),
                    cap = StrokeCap.Round
                )

// Окружность вылета стрелы. TrolleyReach
                // Рассчитываем ограниченный диаметр окружности
                val diameterTrolleyReach = hmiCircleSizeTrolley/2

                val radiusTrolleyReach = diameterTrolleyReach.coerceIn(7,120)
                val centerXTrolleyReach = size.width / 2
                val centerYTrolleyReach = size.height / 2

                // Рисуем окружность
                drawCircle(
                    color = Color.DarkGray.copy(alpha = 0.5f), // Укажите желаемый цвет
                    radius = radiusTrolleyReach.dp.toPx(),
                    center = Offset(centerXTrolleyReach, centerYTrolleyReach),
                    style = Stroke(width = 2.dp.toPx()),
                )

// Отрисовка положения тележки. Trolley
                val centerTrolley = Offset(size.width/2, size.height/2)
                val radiusTrolley = hmiCircleSizeTrolley / 2
                val radiusPxTrolley = radiusTrolley.coerceIn(7,120).dp.toPx()

                val angleRadTrolley = Math.toRadians(hmiLineAngle+180.toDouble())

                //Вычисляем позицию тележки
                val trolleyX = centerTrolley.x + radiusPxTrolley*cos(angleRadTrolley).toFloat()
                val trolleyY = centerTrolley.y + radiusPxTrolley*sin(angleRadTrolley).toFloat()
                val trolleyCenter = Offset(trolleyX, trolleyY)

                drawCircle(
                    color = colorTrolley,
                    center = trolleyCenter,
                    radius = 5.dp.toPx() // Размер тележки
                )



            }


// Canvas для отрисовки полигона №1 из 20 отрезков
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
                    segmentP1Angle00, segmentP1Angle01, segmentP1Angle02, segmentP1Angle03, segmentP1Angle04,
                    segmentP1Angle05, segmentP1Angle06, segmentP1Angle07, segmentP1Angle08, segmentP1Angle09,
                    segmentP1Angle10, segmentP1Angle11, segmentP1Angle12, segmentP1Angle13, segmentP1Angle14,
                    segmentP1Angle15, segmentP1Angle16, segmentP1Angle17, segmentP1Angle18, segmentP1Angle19
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

// Canvas для отрисовки полигона №2 из 20 отрезков
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
                    hmiSegmentP2Length00, hmiSegmentP2Length01, hmiSegmentP2Length02, hmiSegmentP2Length03, hmiSegmentP2Length04,
                    hmiSegmentP2Length05, hmiSegmentP2Length06, hmiSegmentP2Length07, hmiSegmentP2Length08, hmiSegmentP2Length09,
                    hmiSegmentP2Length10, hmiSegmentP2Length11, hmiSegmentP2Length12, hmiSegmentP2Length13, hmiSegmentP2Length14,
                    hmiSegmentP2Length15, hmiSegmentP2Length16, hmiSegmentP2Length17, hmiSegmentP2Length18, hmiSegmentP2Length19,
                )

                val positionsX = listOf(
                    pointP2PositionX00, pointP2PositionX01, pointP2PositionX02, pointP2PositionX03, pointP2PositionX04,
                    pointP2PositionX05, pointP2PositionX06, pointP2PositionX07, pointP2PositionX08, pointP2PositionX09,
                    pointP2PositionX10, pointP2PositionX11, pointP2PositionX12, pointP2PositionX13, pointP2PositionX14,
                    pointP2PositionX15, pointP2PositionX16, pointP2PositionX17, pointP2PositionX18, pointP2PositionX19,
                )

                val positionsY = listOf(
                    pointP2PositionY00, pointP2PositionY01, pointP2PositionY02, pointP2PositionY03, pointP2PositionY04,
                    pointP2PositionY05, pointP2PositionY06, pointP2PositionY07, pointP2PositionY08, pointP2PositionY09,
                    pointP2PositionY10, pointP2PositionY11, pointP2PositionY12, pointP2PositionY13, pointP2PositionY14,
                    pointP2PositionY15, pointP2PositionY16, pointP2PositionY17, pointP2PositionY18, pointP2PositionY19,
                )

                val angles = listOf(
                    segmentP2Angle00, segmentP2Angle01, segmentP2Angle02, segmentP2Angle03, segmentP2Angle04,
                    segmentP2Angle05, segmentP2Angle06, segmentP2Angle07, segmentP2Angle08, segmentP2Angle09,
                    segmentP2Angle10, segmentP2Angle11, segmentP2Angle12, segmentP2Angle13, segmentP2Angle14,
                    segmentP2Angle15, segmentP2Angle16, segmentP2Angle17, segmentP2Angle18, segmentP2Angle19,
                )

                val visibilities = listOf(
                    segmentP2Visibility00, segmentP2Visibility01, segmentP2Visibility02, segmentP2Visibility03, segmentP2Visibility04,
                    segmentP2Visibility05, segmentP2Visibility06, segmentP2Visibility07, segmentP2Visibility08, segmentP2Visibility09,
                    segmentP2Visibility10, segmentP2Visibility11, segmentP2Visibility12, segmentP2Visibility13, segmentP2Visibility14,
                    segmentP2Visibility15, segmentP2Visibility16, segmentP2Visibility17, segmentP2Visibility18, segmentP2Visibility19,
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
                        val color = when (hmiP2Color) {
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

// Canvas для отрисовки полигона №3 из 20 отрезков
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
                    hmiSegmentP3Length00, hmiSegmentP3Length01, hmiSegmentP3Length02, hmiSegmentP3Length03, hmiSegmentP3Length04,
                    hmiSegmentP3Length05, hmiSegmentP3Length06, hmiSegmentP3Length07, hmiSegmentP3Length08, hmiSegmentP3Length09,
                    hmiSegmentP3Length10, hmiSegmentP3Length11, hmiSegmentP3Length12, hmiSegmentP3Length13, hmiSegmentP3Length14,
                    hmiSegmentP3Length15, hmiSegmentP3Length16, hmiSegmentP3Length17, hmiSegmentP3Length18, hmiSegmentP3Length19
                )

                val positionsX = listOf(
                    pointP3PositionX00, pointP3PositionX01, pointP3PositionX02, pointP3PositionX03, pointP3PositionX04,
                    pointP3PositionX05, pointP3PositionX06, pointP3PositionX07, pointP3PositionX08, pointP3PositionX09,
                    pointP3PositionX10, pointP3PositionX11, pointP3PositionX12, pointP3PositionX13, pointP3PositionX14,
                    pointP3PositionX15, pointP3PositionX16, pointP3PositionX17, pointP3PositionX18, pointP3PositionX19
                )

                val positionsY = listOf(
                    pointP3PositionY00, pointP3PositionY01, pointP3PositionY02, pointP3PositionY03, pointP3PositionY04,
                    pointP3PositionY05, pointP3PositionY06, pointP3PositionY07, pointP3PositionY08, pointP3PositionY09,
                    pointP3PositionY10, pointP3PositionY11, pointP3PositionY12, pointP3PositionY13, pointP3PositionY14,
                    pointP3PositionY15, pointP3PositionY16, pointP3PositionY17, pointP3PositionY18, pointP3PositionY19
                )

                val angles = listOf(
                    segmentP3Angle00, segmentP3Angle01, segmentP3Angle02, segmentP3Angle03, segmentP3Angle04,
                    segmentP3Angle05, segmentP3Angle06, segmentP3Angle07, segmentP3Angle08, segmentP3Angle09,
                    segmentP3Angle10, segmentP3Angle11, segmentP3Angle12, segmentP3Angle13, segmentP3Angle14,
                    segmentP3Angle15, segmentP3Angle16, segmentP3Angle17, segmentP3Angle18, segmentP3Angle19
                )

                val visibilities = listOf(
                    segmentP3Visibility00, segmentP3Visibility01, segmentP3Visibility02, segmentP3Visibility03, segmentP3Visibility04,
                    segmentP3Visibility05, segmentP3Visibility06, segmentP3Visibility07, segmentP3Visibility08, segmentP3Visibility09,
                    segmentP3Visibility10, segmentP3Visibility11, segmentP3Visibility12, segmentP3Visibility13, segmentP3Visibility14,
                    segmentP3Visibility15, segmentP3Visibility16, segmentP3Visibility17, segmentP3Visibility18, segmentP3Visibility19
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
                        val color = when (hmiP3Color) {
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

// Canvas для отрисовки полигона №4 из 20 отрезков
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
                    hmiSegmentP4Length00, hmiSegmentP4Length01, hmiSegmentP4Length02, hmiSegmentP4Length03, hmiSegmentP4Length04,
                    hmiSegmentP4Length05, hmiSegmentP4Length06, hmiSegmentP4Length07, hmiSegmentP4Length08, hmiSegmentP4Length09,
                    hmiSegmentP4Length10, hmiSegmentP4Length11, hmiSegmentP4Length12, hmiSegmentP4Length13, hmiSegmentP4Length14,
                    hmiSegmentP4Length15, hmiSegmentP4Length16, hmiSegmentP4Length17, hmiSegmentP4Length18, hmiSegmentP4Length19
                )
                val positionsX = listOf(
                    pointP4PositionX00, pointP4PositionX01, pointP4PositionX02, pointP4PositionX03, pointP4PositionX04,
                    pointP4PositionX05, pointP4PositionX06, pointP4PositionX07, pointP4PositionX08, pointP4PositionX09,
                    pointP4PositionX10, pointP4PositionX11, pointP4PositionX12, pointP4PositionX13, pointP4PositionX14,
                    pointP4PositionX15, pointP4PositionX16, pointP4PositionX17, pointP4PositionX18, pointP4PositionX19
                )
                val positionsY = listOf(
                    pointP4PositionY00, pointP4PositionY01, pointP4PositionY02, pointP4PositionY03, pointP4PositionY04,
                    pointP4PositionY05, pointP4PositionY06, pointP4PositionY07, pointP4PositionY08, pointP4PositionY09,
                    pointP4PositionY10, pointP4PositionY11, pointP4PositionY12, pointP4PositionY13, pointP4PositionY14,
                    pointP4PositionY15, pointP4PositionY16, pointP4PositionY17, pointP4PositionY18, pointP4PositionY19
                )
                val angles = listOf(
                    segmentP4Angle00, segmentP4Angle01, segmentP4Angle02, segmentP4Angle03, segmentP4Angle04,
                    segmentP4Angle05, segmentP4Angle06, segmentP4Angle07, segmentP4Angle08, segmentP4Angle09,
                    segmentP4Angle10, segmentP4Angle11, segmentP4Angle12, segmentP4Angle13, segmentP4Angle14,
                    segmentP4Angle15, segmentP4Angle16, segmentP4Angle17, segmentP4Angle18, segmentP4Angle19
                )
                val visibilities = listOf(
                    segmentP4Visibility00, segmentP4Visibility01, segmentP4Visibility02, segmentP4Visibility03, segmentP4Visibility04,
                    segmentP4Visibility05, segmentP4Visibility06, segmentP4Visibility07, segmentP4Visibility08, segmentP4Visibility09,
                    segmentP4Visibility10, segmentP4Visibility11, segmentP4Visibility12, segmentP4Visibility13, segmentP4Visibility14,
                    segmentP4Visibility15, segmentP4Visibility16, segmentP4Visibility17, segmentP4Visibility18, segmentP4Visibility19
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
                        val color = when (hmiP4Color) {
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
            sozrParams = TODO(),
            polygonP1 = TODO(),
            polygonP2 = TODO(),
            polygonP3 = TODO(),
            polygonP4 = TODO(),
        )
    }
}