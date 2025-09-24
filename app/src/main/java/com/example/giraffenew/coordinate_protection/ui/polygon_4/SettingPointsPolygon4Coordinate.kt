package com.example.giraffenew.coordinate_protection.ui.polygon_4

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.coordinate_protection.prezentation.CoordinateProtectionViewModule
import com.example.giraffenew.coordinate_protection.ui.polygon_1.PolygonPoint
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun SettingPointsPolygon4Coordinate (
    setValuePolygon4PxPos: String,
    updateValuePolygon4PxPos: (String) -> Unit,
    onsetValuePolygon4PxPos: () -> Unit,

    setValuePolygon4PyPos: String,
    updateValuePolygon4PyPos: (String) -> Unit,
    onsetValuePolygon4PyPos: () -> Unit,

    onSetP4PointCmd: (Int) -> Unit,

    errorMessagePolygon4: String?,
    logMessagePolygon4: String?,

    polygon4Point: CoordinateProtectionViewModule.Polygon4Point,
) {
    // Общее состояние прокрутки
    val scrollState = rememberScrollState()

    val polygon4PointY01 = polygon4Point.polygon4PointY01
    val polygon4PointX01 = polygon4Point.polygon4PointX01
    val polygon4PointY02 = polygon4Point.polygon4PointY02
    val polygon4PointX02 = polygon4Point.polygon4PointX02
    val polygon4PointY03 = polygon4Point.polygon4PointY03
    val polygon4PointX03 = polygon4Point.polygon4PointX03
    val polygon4PointY04 = polygon4Point.polygon4PointY04
    val polygon4PointX04 = polygon4Point.polygon4PointX04
    val polygon4PointY05 = polygon4Point.polygon4PointY05
    val polygon4PointX05 = polygon4Point.polygon4PointX05
    val polygon4PointY06 = polygon4Point.polygon4PointY06
    val polygon4PointX06 = polygon4Point.polygon4PointX06
    val polygon4PointY07 = polygon4Point.polygon4PointY07
    val polygon4PointX07 = polygon4Point.polygon4PointX07
    val polygon4PointY08 = polygon4Point.polygon4PointY08
    val polygon4PointX08 = polygon4Point.polygon4PointX08
    val polygon4PointY09 = polygon4Point.polygon4PointY09
    val polygon4PointX09 = polygon4Point.polygon4PointX09
    val polygon4PointY10 = polygon4Point.polygon4PointY10
    val polygon4PointX10 = polygon4Point.polygon4PointX10

    val polygon4PointY11 = polygon4Point.polygon4PointY11
    val polygon4PointX11 = polygon4Point.polygon4PointX11
    val polygon4PointY12 = polygon4Point.polygon4PointY12
    val polygon4PointX12 = polygon4Point.polygon4PointX12
    val polygon4PointY13 = polygon4Point.polygon4PointY13
    val polygon4PointX13 = polygon4Point.polygon4PointX13
    val polygon4PointY14 = polygon4Point.polygon4PointY14
    val polygon4PointX14 = polygon4Point.polygon4PointX14
    val polygon4PointY15 = polygon4Point.polygon4PointY15
    val polygon4PointX15 = polygon4Point.polygon4PointX15
    val polygon4PointY16 = polygon4Point.polygon4PointY16
    val polygon4PointX16 = polygon4Point.polygon4PointX16
    val polygon4PointY17 = polygon4Point.polygon4PointY17
    val polygon4PointX17 = polygon4Point.polygon4PointX17
    val polygon4PointY18 = polygon4Point.polygon4PointY18
    val polygon4PointX18 = polygon4Point.polygon4PointX18
    val polygon4PointY19 = polygon4Point.polygon4PointY19
    val polygon4PointX19 = polygon4Point.polygon4PointX19
    val polygon4PointY20 = polygon4Point.polygon4PointY20
    val polygon4PointX20 = polygon4Point.polygon4PointX20

    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {

//№.1 - Левая область для отображения параметров настройки точек полигона)
        Column(
            modifier = Modifier
                .verticalScroll(scrollState) // Добавлена прокрутка
                .fillMaxSize()
                .weight(3f)
                .padding(5.dp)
                .fillMaxSize()
                .border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
            // Заголовок
            BasicSettingsName(
                modifier = Modifier,
                text = stringResource(R.string.CoordinateProtection_Text_Setting_Points_Polygon_Coordinate))


            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Coordinate_Point_X)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Coordinate_Point_Y)

            // Координаты X и Y точек
            BasicSettingsName(
                modifier = Modifier,
                text = stringResource(R.string.CoordinateProtection_Text_Coordinate_Points))
            //Координаты точки №1
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX01),
                pointY = formatDecimal(polygon4PointY01)
            )
            //Координаты точки №2
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX02),
                pointY = formatDecimal(polygon4PointY02)
            )
            //Координаты точки №3
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX03),
                pointY = formatDecimal(polygon4PointY03)
            )
            //Координаты точки №4
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX04),
                pointY = formatDecimal(polygon4PointY04)
            )
            //Координаты точки №5
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX05),
                pointY = formatDecimal(polygon4PointY05)
            )
            //Координаты точки №6
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX06),
                pointY = formatDecimal(polygon4PointY06)
            )
            //Координаты точки №7
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX07),
                pointY = formatDecimal(polygon4PointY07)
            )
            //Координаты точки №8
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX08),
                pointY = formatDecimal(polygon4PointY08)
            )
            //Координаты точки №9
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX09),
                pointY = formatDecimal(polygon4PointY09)
            )
            //Координаты точки №10
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX10),
                pointY = formatDecimal(polygon4PointY10)
            )
            //Координаты точки №11
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX11),
                pointY = formatDecimal(polygon4PointY11)
            )
            //Координаты точки №12
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX12),
                pointY = formatDecimal(polygon4PointY12)
            )
            //Координаты точки №13
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX13),
                pointY = formatDecimal(polygon4PointY13)
            )
            //Координаты точки №14
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX14),
                pointY = formatDecimal(polygon4PointY14)
            )
            //Координаты точки №15
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX15),
                pointY = formatDecimal(polygon4PointY15)
            )
            //Координаты точки №16
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX16),
                pointY = formatDecimal(polygon4PointY16)
            )
            //Координаты точки №17
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX17),
                pointY = formatDecimal(polygon4PointY17)
            )
            //Координаты точки №18
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX18),
                pointY = formatDecimal(polygon4PointY18)
            )
            //Координаты точки №19
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX19),
                pointY = formatDecimal(polygon4PointY19)
            )
            //Координаты точки №20
            PolygonPoint(
                pointX = formatDecimal(polygon4PointX20),
                pointY = formatDecimal(polygon4PointY20)
            )
            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
        }


        //№.2 - Правая область для отображения и ввода данных настройки полигона
        Column(
            modifier = Modifier
                .verticalScroll(scrollState) // Добавлена прокрутка
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize()
                .border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
            // Отступ  и Статус записи данных вылета, поворота и хода
            Row(modifier = Modifier.fillMaxWidth()) {
                BasicSettingsName(modifier = Modifier.weight(1f), text = stringResource(R.string.spacer))

                Box (modifier = Modifier.weight(10f)
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                ) { errorMessagePolygon4?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                    logMessagePolygon4?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }

            //Ввод. Требуемое значение. Координата X
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon4PxPos,
                onValueChange = updateValuePolygon4PxPos,
                onClick = onsetValuePolygon4PxPos,
                isDecimal = true,
            )
            //Ввод. Требуемое значение. Координата Y
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon4PyPos,
                onValueChange = updateValuePolygon4PyPos,
                onClick = onsetValuePolygon4PyPos,
                isDecimal = true,
            )

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))

            // Кнопка задать точку №1
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_1,
                onClick = {onSetP4PointCmd(31)},
            ) {}
            // Кнопка задать точку №2
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_2,
                onClick = {onSetP4PointCmd(32)},
            ) {}
            // Кнопка задать точку №3
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_3,
                onClick = {onSetP4PointCmd(33)},
            ) {}
            // Кнопка задать точку №4
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_4,
                onClick = {onSetP4PointCmd(34)},
            ) {}
            // Кнопка задать точку №5
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_5,
                onClick = {onSetP4PointCmd(35)},
            ) {}
            // Кнопка задать точку №6
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_6,
                onClick = {onSetP4PointCmd(36)},
            ) {}
            // Кнопка задать точку №7
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_7,
                onClick = {onSetP4PointCmd(37)},
            ) {}
            // Кнопка задать точку №8
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_8,
                onClick = {onSetP4PointCmd(38)},
            ) {}
            // Кнопка задать точку №9
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_9,
                onClick = {onSetP4PointCmd(39)},
            ) {}
            // Кнопка задать точку №10
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_10,
                onClick = {onSetP4PointCmd(40)},
            ) {}

            // Кнопка задать точку №11
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_11,
                onClick = {onSetP4PointCmd(41)},
            ) {}
            // Кнопка задать точку №12
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_12,
                onClick = {onSetP4PointCmd(42)},
            ) {}
            // Кнопка задать точку №13
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_13,
                onClick = {onSetP4PointCmd(43)},
            ) {}
            // Кнопка задать точку №14
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_14,
                onClick = {onSetP4PointCmd(44)},
            ) {}
            // Кнопка задать точку №15
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_15,
                onClick = {onSetP4PointCmd(45)},
            ) {}
            // Кнопка задать точку №16
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_16,
                onClick = {onSetP4PointCmd(46)},
            ) {}
            // Кнопка задать точку №17
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_17,
                onClick = {onSetP4PointCmd(47)},
            ) {}
            // Кнопка задать точку №18
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_18,
                onClick = {onSetP4PointCmd(48)},
            ) {}
            // Кнопка задать точку №19
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_19,
                onClick = {onSetP4PointCmd(49)},
            ) {}
            // Кнопка задать точку №20
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_20,
                onClick = {onSetP4PointCmd(50)},
            ) {}

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
        }
    }
}

private fun formatDecimal(value: Float): String {
    return String.format("%03.1f", value).replace(',', '.')
}

@Preview
@Composable
fun SettingPointsPolygon4CoordinatePreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        SettingPointsPolygon4Coordinate(
            setValuePolygon4PxPos = "1",
            updateValuePolygon4PxPos = { },
            onsetValuePolygon4PxPos = { },

            setValuePolygon4PyPos = "1",
            updateValuePolygon4PyPos = { },
            onsetValuePolygon4PyPos = { },

            onSetP4PointCmd = {},

            errorMessagePolygon4 = null,
            logMessagePolygon4 = null,

            polygon4Point = CoordinateProtectionViewModule.Polygon4Point(
                polygon4PointY01 = 1f,
                polygon4PointX01 = 1f,
                polygon4PointY02 = 1f,
                polygon4PointX02 = 1f,
                polygon4PointY03 = 1f,
                polygon4PointX03 = 1f,
                polygon4PointY04 = 1f,
                polygon4PointX04 = 1f,
                polygon4PointY05 = 1f,
                polygon4PointX05 = 1f,
                polygon4PointY06 = 1f,
                polygon4PointX06 = 1f,
                polygon4PointY07 = 1f,
                polygon4PointX07 = 1f,
                polygon4PointY08 = 1f,
                polygon4PointX08 = 1f,
                polygon4PointY09 = 1f,
                polygon4PointX09 = 1f,
                polygon4PointY10 = 1f,
                polygon4PointX10 = 1f,

                polygon4PointY11 = 1f,
                polygon4PointX11 = 1f,
                polygon4PointY12 = 1f,
                polygon4PointX12 = 1f,
                polygon4PointY13 = 1f,
                polygon4PointX13 = 1f,
                polygon4PointY14 = 1f,
                polygon4PointX14 = 1f,
                polygon4PointY15 = 1f,
                polygon4PointX15 = 1f,
                polygon4PointY16 = 1f,
                polygon4PointX16 = 1f,
                polygon4PointY17 = 1f,
                polygon4PointX17 = 1f,
                polygon4PointY18 = 1f,
                polygon4PointX18 = 1f,
                polygon4PointY19 = 1f,
                polygon4PointX19 = 1f,
                polygon4PointY20 = 1f,
                polygon4PointX20 = 1f,
            ),
        )
    }
}