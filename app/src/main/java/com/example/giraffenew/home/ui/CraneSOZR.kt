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
import com.example.giraffenew.ui.theme.DarkLemon
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
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

//Координаты точек полигона №1
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

    segmentP1Angle00: Int, //Угол между сегментами 00-01
    segmentP1Angle01: Int, //Угол между сегментами 01-02
    segmentP1Angle02: Int, //Угол между сегментами 02-03
    segmentP1Angle03: Int, //Угол между сегментами 03-04
    segmentP1Angle04: Int, //Угол между сегментами 04-05
    segmentP1Angle05: Int, //Угол между сегментами 05-06
    segmentP1Angle06: Int, //Угол между сегментами 06-07
    segmentP1Angle07: Int, //Угол между сегментами 07-08
    segmentP1Angle08: Int, //Угол между сегментами 08-09
    segmentP1Angle09: Int, //Угол между сегментами 09-10
    segmentP1Angle10: Int, //Угол между сегментами 10-11
    segmentP1Angle11: Int, //Угол между сегментами 11-12
    segmentP1Angle12: Int, //Угол между сегментами 12-13
    segmentP1Angle13: Int, //Угол между сегментами 13-14
    segmentP1Angle14: Int, //Угол между сегментами 14-15
    segmentP1Angle15: Int, //Угол между сегментами 15-16
    segmentP1Angle16: Int, //Угол между сегментами 16-17
    segmentP1Angle17: Int, //Угол между сегментами 17-18
    segmentP1Angle18: Int, //Угол между сегментами 18-19
    segmentP1Angle19: Int, //Угол между сегментами 19-00

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

//Координаты точек полигона №2
    hmiSegmentP2Length00: Int, // Длина сегмента №00
    hmiSegmentP2Length01: Int, // Длина сегмента №01
    hmiSegmentP2Length02: Int, // Длина сегмента №02
    hmiSegmentP2Length03: Int, // Длина сегмента №03
    hmiSegmentP2Length04: Int, // Длина сегмента №04
    hmiSegmentP2Length05: Int, // Длина сегмента №05
    hmiSegmentP2Length06: Int, // Длина сегмента №06
    hmiSegmentP2Length07: Int, // Длина сегмента №07
    hmiSegmentP2Length08: Int, // Длина сегмента №08
    hmiSegmentP2Length09: Int, // Длина сегмента №09
    hmiSegmentP2Length10: Int, // Длина сегмента №10
    hmiSegmentP2Length11: Int, // Длина сегмента №11
    hmiSegmentP2Length12: Int, // Длина сегмента №12
    hmiSegmentP2Length13: Int, // Длина сегмента №13
    hmiSegmentP2Length14: Int, // Длина сегмента №14
    hmiSegmentP2Length15: Int, // Длина сегмента №15
    hmiSegmentP2Length16: Int, // Длина сегмента №16
    hmiSegmentP2Length17: Int, // Длина сегмента №17
    hmiSegmentP2Length18: Int, // Длина сегмента №18
    hmiSegmentP2Length19: Int, // Длина сегмента №19

    pointP2PositionY00: Int, // Координата Y точки №00
    pointP2PositionY01: Int, // Координата Y точки №01
    pointP2PositionY02: Int, // Координата Y точки №02
    pointP2PositionY03: Int, // Координата Y точки №03
    pointP2PositionY04: Int, // Координата Y точки №04
    pointP2PositionY05: Int, // Координата Y точки №05
    pointP2PositionY06: Int, // Координата Y точки №06
    pointP2PositionY07: Int, // Координата Y точки №07
    pointP2PositionY08: Int, // Координата Y точки №08
    pointP2PositionY09: Int, // Координата Y точки №09
    pointP2PositionY10: Int, // Координата Y точки №10
    pointP2PositionY11: Int, // Координата Y точки №11
    pointP2PositionY12: Int, // Координата Y точки №12
    pointP2PositionY13: Int, // Координата Y точки №13
    pointP2PositionY14: Int, // Координата Y точки №14
    pointP2PositionY15: Int, // Координата Y точки №15
    pointP2PositionY16: Int, // Координата Y точки №16
    pointP2PositionY17: Int, // Координата Y точки №17
    pointP2PositionY18: Int, // Координата Y точки №18
    pointP2PositionY19: Int, // Координата Y точки №19

    pointP2PositionX00: Int, // Координата X точки №00
    pointP2PositionX01: Int, // Координата X точки №01
    pointP2PositionX02: Int, // Координата X точки №02
    pointP2PositionX03: Int, // Координата X точки №03
    pointP2PositionX04: Int, // Координата X точки №04
    pointP2PositionX05: Int, // Координата X точки №05
    pointP2PositionX06: Int, // Координата X точки №06
    pointP2PositionX07: Int, // Координата X точки №07
    pointP2PositionX08: Int, // Координата X точки №08
    pointP2PositionX09: Int, // Координата X точки №09
    pointP2PositionX10: Int, // Координата X точки №10
    pointP2PositionX11: Int, // Координата X точки №11
    pointP2PositionX12: Int, // Координата X точки №12
    pointP2PositionX13: Int, // Координата X точки №13
    pointP2PositionX14: Int, // Координата X точки №14
    pointP2PositionX15: Int, // Координата X точки №15
    pointP2PositionX16: Int, // Координата X точки №16
    pointP2PositionX17: Int, // Координата X точки №17
    pointP2PositionX18: Int, // Координата X точки №18
    pointP2PositionX19: Int, // Координата X точки №19

    segmentP2Angle00: Int, //Угол между сегментами 00-01
    segmentP2Angle01: Int, //Угол между сегментами 01-02
    segmentP2Angle02: Int, //Угол между сегментами 02-03
    segmentP2Angle03: Int, //Угол между сегментами 03-04
    segmentP2Angle04: Int, //Угол между сегментами 04-05
    segmentP2Angle05: Int, //Угол между сегментами 05-06
    segmentP2Angle06: Int, //Угол между сегментами 06-07
    segmentP2Angle07: Int, //Угол между сегментами 07-08
    segmentP2Angle08: Int, //Угол между сегментами 08-09
    segmentP2Angle09: Int, //Угол между сегментами 09-10
    segmentP2Angle10: Int, //Угол между сегментами 10-11
    segmentP2Angle11: Int, //Угол между сегментами 11-12
    segmentP2Angle12: Int, //Угол между сегментами 12-13
    segmentP2Angle13: Int, //Угол между сегментами 13-14
    segmentP2Angle14: Int, //Угол между сегментами 14-15
    segmentP2Angle15: Int, //Угол между сегментами 15-16
    segmentP2Angle16: Int, //Угол между сегментами 16-17
    segmentP2Angle17: Int, //Угол между сегментами 17-18
    segmentP2Angle18: Int, //Угол между сегментами 18-19
    segmentP2Angle19: Int, //Угол между сегментами 19-00

    segmentP2Visibility00: Int, //Видимость сегмента №00
    segmentP2Visibility01: Int, //Видимость сегмента №01
    segmentP2Visibility02: Int, //Видимость сегмента №02
    segmentP2Visibility03: Int, //Видимость сегмента №03
    segmentP2Visibility04: Int, //Видимость сегмента №04
    segmentP2Visibility05: Int, //Видимость сегмента №05
    segmentP2Visibility06: Int, //Видимость сегмента №06
    segmentP2Visibility07: Int, //Видимость сегмента №07
    segmentP2Visibility08: Int, //Видимость сегмента №08
    segmentP2Visibility09: Int, //Видимость сегмента №09
    segmentP2Visibility10: Int, //Видимость сегмента №10
    segmentP2Visibility11: Int, //Видимость сегмента №11
    segmentP2Visibility12: Int, //Видимость сегмента №12
    segmentP2Visibility13: Int, //Видимость сегмента №13
    segmentP2Visibility14: Int, //Видимость сегмента №14
    segmentP2Visibility15: Int, //Видимость сегмента №15
    segmentP2Visibility16: Int, //Видимость сегмента №16
    segmentP2Visibility17: Int, //Видимость сегмента №17
    segmentP2Visibility18: Int, //Видимость сегмента №18
    segmentP2Visibility19: Int, //Видимость сегмента №19

//Координаты точек полигона №3
    hmiSegmentP3Length00: Int, // Длина сегмента №00
    hmiSegmentP3Length01: Int, // Длина сегмента №01
    hmiSegmentP3Length02: Int, // Длина сегмента №02
    hmiSegmentP3Length03: Int, // Длина сегмента №03
    hmiSegmentP3Length04: Int, // Длина сегмента №04
    hmiSegmentP3Length05: Int, // Длина сегмента №05
    hmiSegmentP3Length06: Int, // Длина сегмента №06
    hmiSegmentP3Length07: Int, // Длина сегмента №07
    hmiSegmentP3Length08: Int, // Длина сегмента №08
    hmiSegmentP3Length09: Int, // Длина сегмента №09
    hmiSegmentP3Length10: Int, // Длина сегмента №10
    hmiSegmentP3Length11: Int, // Длина сегмента №11
    hmiSegmentP3Length12: Int, // Длина сегмента №12
    hmiSegmentP3Length13: Int, // Длина сегмента №13
    hmiSegmentP3Length14: Int, // Длина сегмента №14
    hmiSegmentP3Length15: Int, // Длина сегмента №15
    hmiSegmentP3Length16: Int, // Длина сегмента №16
    hmiSegmentP3Length17: Int, // Длина сегмента №17
    hmiSegmentP3Length18: Int, // Длина сегмента №18
    hmiSegmentP3Length19: Int, // Длина сегмента №19

    pointP3PositionY00: Int, // Координата Y точки №00
    pointP3PositionY01: Int, // Координата Y точки №01
    pointP3PositionY02: Int, // Координата Y точки №02
    pointP3PositionY03: Int, // Координата Y точки №03
    pointP3PositionY04: Int, // Координата Y точки №04
    pointP3PositionY05: Int, // Координата Y точки №05
    pointP3PositionY06: Int, // Координата Y точки №06
    pointP3PositionY07: Int, // Координата Y точки №07
    pointP3PositionY08: Int, // Координата Y точки №08
    pointP3PositionY09: Int, // Координата Y точки №09
    pointP3PositionY10: Int, // Координата Y точки №10
    pointP3PositionY11: Int, // Координата Y точки №11
    pointP3PositionY12: Int, // Координата Y точки №12
    pointP3PositionY13: Int, // Координата Y точки №13
    pointP3PositionY14: Int, // Координата Y точки №14
    pointP3PositionY15: Int, // Координата Y точки №15
    pointP3PositionY16: Int, // Координата Y точки №16
    pointP3PositionY17: Int, // Координата Y точки №17
    pointP3PositionY18: Int, // Координата Y точки №18
    pointP3PositionY19: Int, // Координата Y точки №19

    pointP3PositionX00: Int, // Координата X точки №00
    pointP3PositionX01: Int, // Координата X точки №01
    pointP3PositionX02: Int, // Координата X точки №02
    pointP3PositionX03: Int, // Координата X точки №03
    pointP3PositionX04: Int, // Координата X точки №04
    pointP3PositionX05: Int, // Координата X точки №05
    pointP3PositionX06: Int, // Координата X точки №06
    pointP3PositionX07: Int, // Координата X точки №07
    pointP3PositionX08: Int, // Координата X точки №08
    pointP3PositionX09: Int, // Координата X точки №09
    pointP3PositionX10: Int, // Координата X точки №10
    pointP3PositionX11: Int, // Координата X точки №11
    pointP3PositionX12: Int, // Координата X точки №12
    pointP3PositionX13: Int, // Координата X точки №13
    pointP3PositionX14: Int, // Координата X точки №14
    pointP3PositionX15: Int, // Координата X точки №15
    pointP3PositionX16: Int, // Координата X точки №16
    pointP3PositionX17: Int, // Координата X точки №17
    pointP3PositionX18: Int, // Координата X точки №18
    pointP3PositionX19: Int, // Координата X точки №19

    segmentP3Angle00: Int, //Угол между сегментами 00-01
    segmentP3Angle01: Int, //Угол между сегментами 01-02
    segmentP3Angle02: Int, //Угол между сегментами 02-03
    segmentP3Angle03: Int, //Угол между сегментами 03-04
    segmentP3Angle04: Int, //Угол между сегментами 04-05
    segmentP3Angle05: Int, //Угол между сегментами 05-06
    segmentP3Angle06: Int, //Угол между сегментами 06-07
    segmentP3Angle07: Int, //Угол между сегментами 07-08
    segmentP3Angle08: Int, //Угол между сегментами 08-09
    segmentP3Angle09: Int, //Угол между сегментами 09-10
    segmentP3Angle10: Int, //Угол между сегментами 10-11
    segmentP3Angle11: Int, //Угол между сегментами 11-12
    segmentP3Angle12: Int, //Угол между сегментами 12-13
    segmentP3Angle13: Int, //Угол между сегментами 13-14
    segmentP3Angle14: Int, //Угол между сегментами 14-15
    segmentP3Angle15: Int, //Угол между сегментами 15-16
    segmentP3Angle16: Int, //Угол между сегментами 16-17
    segmentP3Angle17: Int, //Угол между сегментами 17-18
    segmentP3Angle18: Int, //Угол между сегментами 18-19
    segmentP3Angle19: Int, //Угол между сегментами 19-00

    segmentP3Visibility00: Int, //Видимость сегмента №00
    segmentP3Visibility01: Int, //Видимость сегмента №01
    segmentP3Visibility02: Int, //Видимость сегмента №02
    segmentP3Visibility03: Int, //Видимость сегмента №03
    segmentP3Visibility04: Int, //Видимость сегмента №04
    segmentP3Visibility05: Int, //Видимость сегмента №05
    segmentP3Visibility06: Int, //Видимость сегмента №06
    segmentP3Visibility07: Int, //Видимость сегмента №07
    segmentP3Visibility08: Int, //Видимость сегмента №08
    segmentP3Visibility09: Int, //Видимость сегмента №09
    segmentP3Visibility10: Int, //Видимость сегмента №10
    segmentP3Visibility11: Int, //Видимость сегмента №11
    segmentP3Visibility12: Int, //Видимость сегмента №12
    segmentP3Visibility13: Int, //Видимость сегмента №13
    segmentP3Visibility14: Int, //Видимость сегмента №14
    segmentP3Visibility15: Int, //Видимость сегмента №15
    segmentP3Visibility16: Int, //Видимость сегмента №16
    segmentP3Visibility17: Int, //Видимость сегмента №17
    segmentP3Visibility18: Int, //Видимость сегмента №18
    segmentP3Visibility19: Int, //Видимость сегмента №19

//Координаты точек полигона №4
    hmiSegmentP4Length00: Int, // Длина сегмента №00
    hmiSegmentP4Length01: Int, // Длина сегмента №01
    hmiSegmentP4Length02: Int, // Длина сегмента №02
    hmiSegmentP4Length03: Int, // Длина сегмента №03
    hmiSegmentP4Length04: Int, // Длина сегмента №04
    hmiSegmentP4Length05: Int, // Длина сегмента №05
    hmiSegmentP4Length06: Int, // Длина сегмента №06
    hmiSegmentP4Length07: Int, // Длина сегмента №07
    hmiSegmentP4Length08: Int, // Длина сегмента №08
    hmiSegmentP4Length09: Int, // Длина сегмента №09
    hmiSegmentP4Length10: Int, // Длина сегмента №10
    hmiSegmentP4Length11: Int, // Длина сегмента №11
    hmiSegmentP4Length12: Int, // Длина сегмента №12
    hmiSegmentP4Length13: Int, // Длина сегмента №13
    hmiSegmentP4Length14: Int, // Длина сегмента №14
    hmiSegmentP4Length15: Int, // Длина сегмента №15
    hmiSegmentP4Length16: Int, // Длина сегмента №16
    hmiSegmentP4Length17: Int, // Длина сегмента №17
    hmiSegmentP4Length18: Int, // Длина сегмента №18
    hmiSegmentP4Length19: Int, // Длина сегмента №19

    pointP4PositionY00: Int, // Координата Y точки №00
    pointP4PositionY01: Int, // Координата Y точки №01
    pointP4PositionY02: Int, // Координата Y точки №02
    pointP4PositionY03: Int, // Координата Y точки №03
    pointP4PositionY04: Int, // Координата Y точки №04
    pointP4PositionY05: Int, // Координата Y точки №05
    pointP4PositionY06: Int, // Координата Y точки №06
    pointP4PositionY07: Int, // Координата Y точки №07
    pointP4PositionY08: Int, // Координата Y точки №08
    pointP4PositionY09: Int, // Координата Y точки №09
    pointP4PositionY10: Int, // Координата Y точки №10
    pointP4PositionY11: Int, // Координата Y точки №11
    pointP4PositionY12: Int, // Координата Y точки №12
    pointP4PositionY13: Int, // Координата Y точки №13
    pointP4PositionY14: Int, // Координата Y точки №14
    pointP4PositionY15: Int, // Координата Y точки №15
    pointP4PositionY16: Int, // Координата Y точки №16
    pointP4PositionY17: Int, // Координата Y точки №17
    pointP4PositionY18: Int, // Координата Y точки №18
    pointP4PositionY19: Int, // Координата Y точки №19

    pointP4PositionX00: Int, // Координата X точки №00
    pointP4PositionX01: Int, // Координата X точки №01
    pointP4PositionX02: Int, // Координата X точки №02
    pointP4PositionX03: Int, // Координата X точки №03
    pointP4PositionX04: Int, // Координата X точки №04
    pointP4PositionX05: Int, // Координата X точки №05
    pointP4PositionX06: Int, // Координата X точки №06
    pointP4PositionX07: Int, // Координата X точки №07
    pointP4PositionX08: Int, // Координата X точки №08
    pointP4PositionX09: Int, // Координата X точки №09
    pointP4PositionX10: Int, // Координата X точки №10
    pointP4PositionX11: Int, // Координата X точки №11
    pointP4PositionX12: Int, // Координата X точки №12
    pointP4PositionX13: Int, // Координата X точки №13
    pointP4PositionX14: Int, // Координата X точки №14
    pointP4PositionX15: Int, // Координата X точки №15
    pointP4PositionX16: Int, // Координата X точки №16
    pointP4PositionX17: Int, // Координата X точки №17
    pointP4PositionX18: Int, // Координата X точки №18
    pointP4PositionX19: Int, // Координата X точки №19

    segmentP4Angle00: Int, //Угол между сегментами 00-01
    segmentP4Angle01: Int, //Угол между сегментами 01-02
    segmentP4Angle02: Int, //Угол между сегментами 02-03
    segmentP4Angle03: Int, //Угол между сегментами 03-04
    segmentP4Angle04: Int, //Угол между сегментами 04-05
    segmentP4Angle05: Int, //Угол между сегментами 05-06
    segmentP4Angle06: Int, //Угол между сегментами 06-07
    segmentP4Angle07: Int, //Угол между сегментами 07-08
    segmentP4Angle08: Int, //Угол между сегментами 08-09
    segmentP4Angle09: Int, //Угол между сегментами 09-10
    segmentP4Angle10: Int, //Угол между сегментами 10-11
    segmentP4Angle11: Int, //Угол между сегментами 11-12
    segmentP4Angle12: Int, //Угол между сегментами 12-13
    segmentP4Angle13: Int, //Угол между сегментами 13-14
    segmentP4Angle14: Int, //Угол между сегментами 14-15
    segmentP4Angle15: Int, //Угол между сегментами 15-16
    segmentP4Angle16: Int, //Угол между сегментами 16-17
    segmentP4Angle17: Int, //Угол между сегментами 17-18
    segmentP4Angle18: Int, //Угол между сегментами 18-19
    segmentP4Angle19: Int, //Угол между сегментами 19-00

    segmentP4Visibility00: Int, //Видимость сегмента №00
    segmentP4Visibility01: Int, //Видимость сегмента №01
    segmentP4Visibility02: Int, //Видимость сегмента №02
    segmentP4Visibility03: Int, //Видимость сегмента №03
    segmentP4Visibility04: Int, //Видимость сегмента №04
    segmentP4Visibility05: Int, //Видимость сегмента №05
    segmentP4Visibility06: Int, //Видимость сегмента №06
    segmentP4Visibility07: Int, //Видимость сегмента №07
    segmentP4Visibility08: Int, //Видимость сегмента №08
    segmentP4Visibility09: Int, //Видимость сегмента №09
    segmentP4Visibility10: Int, //Видимость сегмента №10
    segmentP4Visibility11: Int, //Видимость сегмента №11
    segmentP4Visibility12: Int, //Видимость сегмента №12
    segmentP4Visibility13: Int, //Видимость сегмента №13
    segmentP4Visibility14: Int, //Видимость сегмента №14
    segmentP4Visibility15: Int, //Видимость сегмента №15
    segmentP4Visibility16: Int, //Видимость сегмента №16
    segmentP4Visibility17: Int, //Видимость сегмента №17
    segmentP4Visibility18: Int, //Видимость сегмента №18
    segmentP4Visibility19: Int, //Видимость сегмента №19

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
    val coordinateAngleX = 0 //Угол координаты X
    val coordinateAngleY = 270 //Угол координаты Y
    val coordinateVisibilityX = 1 //Видимость оси координат
    val coordinateVisibilityY = 1 //Видимость оси координат

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
            val angles = listOf(coordinateAngleX, coordinateAngleY)
            val visibilities = listOf(coordinateVisibilityX, coordinateVisibilityY)


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

            hmiSegmentP1Length00 = 1,
            hmiSegmentP1Length01 = 1,
            hmiSegmentP1Length02 = 1,
            hmiSegmentP1Length03 = 1,
            hmiSegmentP1Length04 = 1,
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

            pointP1PositionX00 = 1,
            pointP1PositionX01 = 1,
            pointP1PositionX02 = 1,
            pointP1PositionX03 = 1,
            pointP1PositionX04 = 1,
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

            pointP1PositionY00 = 1,
            pointP1PositionY01 = 1,
            pointP1PositionY02 = 1,
            pointP1PositionY03 = 1,
            pointP1PositionY04 = 1,
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



            segmentP1Angle00 = 1,
            segmentP1Angle01 = 1,
            segmentP1Angle02 = 1,
            segmentP1Angle03 = 1,
            segmentP1Angle04 = 1,
            segmentP1Angle05 = 1,
            segmentP1Angle06 = 1,
            segmentP1Angle07 = 1,
            segmentP1Angle08 = 1,
            segmentP1Angle09 = 1,
            segmentP1Angle10 = 1,
            segmentP1Angle11 = 1,
            segmentP1Angle12 = 1,
            segmentP1Angle13 = 1,
            segmentP1Angle14 = 1,
            segmentP1Angle15 = 1,
            segmentP1Angle16 = 1,
            segmentP1Angle17 = 1,
            segmentP1Angle18 = 1,
            segmentP1Angle19 = 1,

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

            hmiSegmentP2Length00 = 1,
            hmiSegmentP2Length01 = 1,
            hmiSegmentP2Length02 = 1,
            hmiSegmentP2Length03 = 1,
            hmiSegmentP2Length04 = 0,
            hmiSegmentP2Length05 = 1,
            hmiSegmentP2Length06 = 1,
            hmiSegmentP2Length07 = 1,
            hmiSegmentP2Length08 = 1,
            hmiSegmentP2Length09 = 1,
            hmiSegmentP2Length10 = 1,
            hmiSegmentP2Length11 = 1,
            hmiSegmentP2Length12 = 1,
            hmiSegmentP2Length13 = 1,
            hmiSegmentP2Length14 = 1,
            hmiSegmentP2Length15 = 1,
            hmiSegmentP2Length16 = 1,
            hmiSegmentP2Length17 = 1,
            hmiSegmentP2Length18 = 1,
            hmiSegmentP2Length19 = 1,

            pointP2PositionX00 = 1,
            pointP2PositionX01 = 1,
            pointP2PositionX02 = 1,
            pointP2PositionX03 = 1,
            pointP2PositionX04 = 1,
            pointP2PositionX05 = 1,
            pointP2PositionX06 = 1,
            pointP2PositionX07 = 1,
            pointP2PositionX08 = 1,
            pointP2PositionX09 = 1,
            pointP2PositionX10 = 1,
            pointP2PositionX11 = 1,
            pointP2PositionX12 = 1,
            pointP2PositionX13 = 1,
            pointP2PositionX14 = 1,
            pointP2PositionX15 = 1,
            pointP2PositionX16 = 1,
            pointP2PositionX17 = 1,
            pointP2PositionX18 = 1,
            pointP2PositionX19 = 1,

            pointP2PositionY00 = 1,
            pointP2PositionY01 = 1,
            pointP2PositionY02 = 1,
            pointP2PositionY03 = 1,
            pointP2PositionY04 = 1,
            pointP2PositionY05 = 1,
            pointP2PositionY06 = 1,
            pointP2PositionY07 = 1,
            pointP2PositionY08 = 1,
            pointP2PositionY09 = 1,
            pointP2PositionY10 = 1,
            pointP2PositionY11 = 1,
            pointP2PositionY12 = 1,
            pointP2PositionY13 = 1,
            pointP2PositionY14 = 1,
            pointP2PositionY15 = 1,
            pointP2PositionY16 = 1,
            pointP2PositionY17 = 1,
            pointP2PositionY18 = 1,
            pointP2PositionY19 = 1,

            segmentP2Angle00 = 1,
            segmentP2Angle01 = 1,
            segmentP2Angle02 = 1,
            segmentP2Angle03 = 1,
            segmentP2Angle04 = 1,
            segmentP2Angle05 = 1,
            segmentP2Angle06 = 1,
            segmentP2Angle07 = 1,
            segmentP2Angle08 = 1,
            segmentP2Angle09 = 1,
            segmentP2Angle10 = 1,
            segmentP2Angle11 = 1,
            segmentP2Angle12 = 1,
            segmentP2Angle13 = 1,
            segmentP2Angle14 = 1,
            segmentP2Angle15 = 1,
            segmentP2Angle16 = 1,
            segmentP2Angle17 = 1,
            segmentP2Angle18 = 1,
            segmentP2Angle19 = 1,

            segmentP2Visibility00 = 1,
            segmentP2Visibility01 = 1,
            segmentP2Visibility02 = 1,
            segmentP2Visibility03 = 1,
            segmentP2Visibility04 = 1,
            segmentP2Visibility05 = 1,
            segmentP2Visibility06 = 1,
            segmentP2Visibility07 = 1,
            segmentP2Visibility08 = 1,
            segmentP2Visibility09 = 1,
            segmentP2Visibility10 = 1,
            segmentP2Visibility11 = 1,
            segmentP2Visibility12 = 1,
            segmentP2Visibility13 = 1,
            segmentP2Visibility14 = 1,
            segmentP2Visibility15 = 1,
            segmentP2Visibility16 = 1,
            segmentP2Visibility17 = 1,
            segmentP2Visibility18 = 1,
            segmentP2Visibility19 = 1,

            hmiSegmentP3Length00 = 1,
            hmiSegmentP3Length01 = 1,
            hmiSegmentP3Length02 = 1,
            hmiSegmentP3Length03 = 1,
            hmiSegmentP3Length04 = 1,
            hmiSegmentP3Length05 = 1,
            hmiSegmentP3Length06 = 1,
            hmiSegmentP3Length07 = 1,
            hmiSegmentP3Length08 = 1,
            hmiSegmentP3Length09 = 1,
            hmiSegmentP3Length10 = 1,
            hmiSegmentP3Length11 = 1,
            hmiSegmentP3Length12 = 1,
            hmiSegmentP3Length13 = 1,
            hmiSegmentP3Length14 = 1,
            hmiSegmentP3Length15 = 1,
            hmiSegmentP3Length16 = 1,
            hmiSegmentP3Length17 = 1,
            hmiSegmentP3Length18 = 1,
            hmiSegmentP3Length19 = 1,

            pointP3PositionX00 = 1,
            pointP3PositionX01 = 1,
            pointP3PositionX02 = 1,
            pointP3PositionX03 = 1,
            pointP3PositionX04 = 1,
            pointP3PositionX05 = 1,
            pointP3PositionX06 = 1,
            pointP3PositionX07 = 1,
            pointP3PositionX08 = 1,
            pointP3PositionX09 = 1,
            pointP3PositionX10 = 1,
            pointP3PositionX11 = 1,
            pointP3PositionX12 = 1,
            pointP3PositionX13 = 1,
            pointP3PositionX14 = 1,
            pointP3PositionX15 = 1,
            pointP3PositionX16 = 1,
            pointP3PositionX17 = 1,
            pointP3PositionX18 = 1,
            pointP3PositionX19 = 1,

            pointP3PositionY00 = 1,
            pointP3PositionY01 = 1,
            pointP3PositionY02 = 1,
            pointP3PositionY03 = 1,
            pointP3PositionY04 = 1,
            pointP3PositionY05 = 1,
            pointP3PositionY06 = 1,
            pointP3PositionY07 = 1,
            pointP3PositionY08 = 1,
            pointP3PositionY09 = 1,
            pointP3PositionY10 = 1,
            pointP3PositionY11 = 1,
            pointP3PositionY12 = 1,
            pointP3PositionY13 = 1,
            pointP3PositionY14 = 1,
            pointP3PositionY15 = 1,
            pointP3PositionY16 = 1,
            pointP3PositionY17 = 1,
            pointP3PositionY18 = 1,
            pointP3PositionY19 = 1,

            segmentP3Angle00 = 1,
            segmentP3Angle01 = 1,
            segmentP3Angle02 = 1,
            segmentP3Angle03 = 1,
            segmentP3Angle04 = 1,
            segmentP3Angle05 = 1,
            segmentP3Angle06 = 1,
            segmentP3Angle07 = 1,
            segmentP3Angle08 = 1,
            segmentP3Angle09 = 1,
            segmentP3Angle10 = 1,
            segmentP3Angle11 = 1,
            segmentP3Angle12 = 1,
            segmentP3Angle13 = 1,
            segmentP3Angle14 = 1,
            segmentP3Angle15 = 1,
            segmentP3Angle16 = 1,
            segmentP3Angle17 = 1,
            segmentP3Angle18 = 1,
            segmentP3Angle19 = 1,

            segmentP3Visibility00 = 1,
            segmentP3Visibility01 = 1,
            segmentP3Visibility02 = 1,
            segmentP3Visibility03 = 1,
            segmentP3Visibility04 = 1,
            segmentP3Visibility05 = 1,
            segmentP3Visibility06 = 1,
            segmentP3Visibility07 = 1,
            segmentP3Visibility08 = 1,
            segmentP3Visibility09 = 1,
            segmentP3Visibility10 = 1,
            segmentP3Visibility11 = 1,
            segmentP3Visibility12 = 1,
            segmentP3Visibility13 = 1,
            segmentP3Visibility14 = 1,
            segmentP3Visibility15 = 1,
            segmentP3Visibility16 = 1,
            segmentP3Visibility17 = 1,
            segmentP3Visibility18 = 1,
            segmentP3Visibility19 = 1,

            hmiSegmentP4Length00 = 1,
            hmiSegmentP4Length01 = 1,
            hmiSegmentP4Length02 = 1,
            hmiSegmentP4Length03 = 1,
            hmiSegmentP4Length04 = 1,
            hmiSegmentP4Length05 = 1,
            hmiSegmentP4Length06 = 1,
            hmiSegmentP4Length07 = 1,
            hmiSegmentP4Length08 = 1,
            hmiSegmentP4Length09 = 1,
            hmiSegmentP4Length10 = 1,
            hmiSegmentP4Length11 = 1,
            hmiSegmentP4Length12 = 1,
            hmiSegmentP4Length13 = 1,
            hmiSegmentP4Length14 = 1,
            hmiSegmentP4Length15 = 1,
            hmiSegmentP4Length16 = 1,
            hmiSegmentP4Length17 = 1,
            hmiSegmentP4Length18 = 1,
            hmiSegmentP4Length19 = 1,

            pointP4PositionX00 = 1,
            pointP4PositionX01 = 1,
            pointP4PositionX02 = 1,
            pointP4PositionX03 = 1,
            pointP4PositionX04 = 1,
            pointP4PositionX05 = 1,
            pointP4PositionX06 = 1,
            pointP4PositionX07 = 1,
            pointP4PositionX08 = 1,
            pointP4PositionX09 = 1,
            pointP4PositionX10 = 1,
            pointP4PositionX11 = 1,
            pointP4PositionX12 = 1,
            pointP4PositionX13 = 1,
            pointP4PositionX14 = 1,
            pointP4PositionX15 = 1,
            pointP4PositionX16 = 1,
            pointP4PositionX17 = 1,
            pointP4PositionX18 = 1,
            pointP4PositionX19 = 1,

            pointP4PositionY00 = 1,
            pointP4PositionY01 = 1,
            pointP4PositionY02 = 1,
            pointP4PositionY03 = 1,
            pointP4PositionY04 = 1,
            pointP4PositionY05 = 1,
            pointP4PositionY06 = 1,
            pointP4PositionY07 = 1,
            pointP4PositionY08 = 1,
            pointP4PositionY09 = 1,
            pointP4PositionY10 = 1,
            pointP4PositionY11 = 1,
            pointP4PositionY12 = 1,
            pointP4PositionY13 = 1,
            pointP4PositionY14 = 1,
            pointP4PositionY15 = 1,
            pointP4PositionY16 = 1,
            pointP4PositionY17 = 1,
            pointP4PositionY18 = 1,
            pointP4PositionY19 = 1,

            segmentP4Angle00 = 1,
            segmentP4Angle01 = 1,
            segmentP4Angle02 = 1,
            segmentP4Angle03 = 1,
            segmentP4Angle04 = 1,
            segmentP4Angle05 = 1,
            segmentP4Angle06 = 1,
            segmentP4Angle07 = 1,
            segmentP4Angle08 = 1,
            segmentP4Angle09 = 1,
            segmentP4Angle10 = 1,
            segmentP4Angle11 = 1,
            segmentP4Angle12 = 1,
            segmentP4Angle13 = 1,
            segmentP4Angle14 = 1,
            segmentP4Angle15 = 1,
            segmentP4Angle16 = 1,
            segmentP4Angle17 = 1,
            segmentP4Angle18 = 1,
            segmentP4Angle19 = 1,

            segmentP4Visibility00 = 1,
            segmentP4Visibility01 = 1,
            segmentP4Visibility02 = 1,
            segmentP4Visibility03 = 1,
            segmentP4Visibility04 = 1,
            segmentP4Visibility05 = 1,
            segmentP4Visibility06 = 1,
            segmentP4Visibility07 = 1,
            segmentP4Visibility08 = 1,
            segmentP4Visibility09 = 1,
            segmentP4Visibility10 = 1,
            segmentP4Visibility11 = 1,
            segmentP4Visibility12 = 1,
            segmentP4Visibility13 = 1,
            segmentP4Visibility14 = 1,
            segmentP4Visibility15 = 1,
            segmentP4Visibility16 = 1,
            segmentP4Visibility17 = 1,
            segmentP4Visibility18 = 1,
            segmentP4Visibility19 = 1,
        )
    }
}