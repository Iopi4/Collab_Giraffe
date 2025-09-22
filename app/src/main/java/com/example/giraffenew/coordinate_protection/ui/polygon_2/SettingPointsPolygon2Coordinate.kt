package com.example.giraffenew.coordinate_protection.ui.polygon_2

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
import com.example.giraffenew.coordinate_protection.ui.polygon_1.PolygonPoint
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun SettingPointsPolygon2Coordinate (
    setValuePolygon2PxPos: String,
    updateValuePolygon2PxPos: (String) -> Unit,
    onsetValuePolygon2PxPos: () -> Unit,

    setValuePolygon2PyPos: String,
    updateValuePolygon2PyPos: (String) -> Unit,
    onsetValuePolygon2PyPos: () -> Unit,

    onSetP2PointCmd: (Int) -> Unit,

    errorMessagePolygon2: String?,
    logMessagePolygon2: String?,

    polygon2PointY01: Float, polygon2PointX01: Float,
    polygon2PointY02: Float, polygon2PointX02: Float,
    polygon2PointY03: Float, polygon2PointX03: Float,
    polygon2PointY04: Float, polygon2PointX04: Float,
    polygon2PointY05: Float, polygon2PointX05: Float,
    polygon2PointY06: Float, polygon2PointX06: Float,
    polygon2PointY07: Float, polygon2PointX07: Float,
    polygon2PointY08: Float, polygon2PointX08: Float,
    polygon2PointY09: Float, polygon2PointX09: Float,
    polygon2PointY10: Float, polygon2PointX10: Float,
    polygon2PointY11: Float, polygon2PointX11: Float,
    polygon2PointY12: Float, polygon2PointX12: Float,
    polygon2PointY13: Float, polygon2PointX13: Float,
    polygon2PointY14: Float, polygon2PointX14: Float,
    polygon2PointY15: Float, polygon2PointX15: Float,
    polygon2PointY16: Float, polygon2PointX16: Float,
    polygon2PointY17: Float, polygon2PointX17: Float,
    polygon2PointY18: Float, polygon2PointX18: Float,
    polygon2PointY19: Float, polygon2PointX19: Float,
    polygon2PointY20: Float, polygon2PointX20: Float,

    ) {
    // Общее состояние прокрутки
    val scrollState = rememberScrollState()

    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {

//№.1 - Левая область для отображения параметров настройки точек полигона)
        Column(
            modifier = Modifier
                .verticalScroll(scrollState) // Добавлена прокрутка
                .fillMaxSize().weight(3f).padding(5.dp).fillMaxSize().border(
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
                pointX = formatDecimal(polygon2PointX01),
                pointY = formatDecimal(polygon2PointY01)
            )
            //Координаты точки №2
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX02),
                pointY = formatDecimal(polygon2PointY02)
            )
            //Координаты точки №3
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX03),
                pointY = formatDecimal(polygon2PointY03)
            )
            //Координаты точки №4
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX04),
                pointY = formatDecimal(polygon2PointY04)
            )
            //Координаты точки №5
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX05),
                pointY = formatDecimal(polygon2PointY05)
            )
            //Координаты точки №6
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX06),
                pointY = formatDecimal(polygon2PointY06)
            )
            //Координаты точки №7
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX07),
                pointY = formatDecimal(polygon2PointY07)
            )
            //Координаты точки №8
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX08),
                pointY = formatDecimal(polygon2PointY08)
            )
            //Координаты точки №9
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX09),
                pointY = formatDecimal(polygon2PointY09)
            )
            //Координаты точки №10
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX10),
                pointY = formatDecimal(polygon2PointY10)
            )
            //Координаты точки №11
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX11),
                pointY = formatDecimal(polygon2PointY11)
            )
            //Координаты точки №12
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX12),
                pointY = formatDecimal(polygon2PointY12)
            )
            //Координаты точки №13
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX13),
                pointY = formatDecimal(polygon2PointY13)
            )
            //Координаты точки №14
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX14),
                pointY = formatDecimal(polygon2PointY14)
            )
            //Координаты точки №15
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX15),
                pointY = formatDecimal(polygon2PointY15)
            )
            //Координаты точки №16
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX16),
                pointY = formatDecimal(polygon2PointY16)
            )
            //Координаты точки №17
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX17),
                pointY = formatDecimal(polygon2PointY17)
            )
            //Координаты точки №18
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX18),
                pointY = formatDecimal(polygon2PointY18)
            )
            //Координаты точки №19
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX19),
                pointY = formatDecimal(polygon2PointY19)
            )
            //Координаты точки №20
            PolygonPoint(
                pointX = formatDecimal(polygon2PointX20),
                pointY = formatDecimal(polygon2PointY20)
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
                ) { errorMessagePolygon2?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                    logMessagePolygon2?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }

            //Ввод. Требуемое значение. Координата X
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon2PxPos,
                onValueChange = updateValuePolygon2PxPos,
                onClick = onsetValuePolygon2PxPos,
                isDecimal = true,
            )
            //Ввод. Требуемое значение. Координата Y
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon2PyPos,
                onValueChange = updateValuePolygon2PyPos,
                onClick = onsetValuePolygon2PyPos,
                isDecimal = true,
            )

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))

            // Кнопка задать точку №1
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_1,
                onClick = {onSetP2PointCmd(31)},
            ) {}
            // Кнопка задать точку №2
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_2,
                onClick = {onSetP2PointCmd(32)},
            ) {}
            // Кнопка задать точку №3
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_3,
                onClick = {onSetP2PointCmd(33)},
            ) {}
            // Кнопка задать точку №4
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_4,
                onClick = {onSetP2PointCmd(34)},
            ) {}
            // Кнопка задать точку №5
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_5,
                onClick = {onSetP2PointCmd(35)},
            ) {}
            // Кнопка задать точку №6
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_6,
                onClick = {onSetP2PointCmd(36)},
            ) {}
            // Кнопка задать точку №7
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_7,
                onClick = {onSetP2PointCmd(37)},
            ) {}
            // Кнопка задать точку №8
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_8,
                onClick = {onSetP2PointCmd(38)},
            ) {}
            // Кнопка задать точку №9
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_9,
                onClick = {onSetP2PointCmd(39)},
            ) {}
            // Кнопка задать точку №10
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_10,
                onClick = {onSetP2PointCmd(40)},
            ) {}

            // Кнопка задать точку №11
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_11,
                onClick = {onSetP2PointCmd(41)},
            ) {}
            // Кнопка задать точку №12
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_12,
                onClick = {onSetP2PointCmd(42)},
            ) {}
            // Кнопка задать точку №13
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_13,
                onClick = {onSetP2PointCmd(43)},
            ) {}
            // Кнопка задать точку №14
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_14,
                onClick = {onSetP2PointCmd(44)},
            ) {}
            // Кнопка задать точку №15
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_15,
                onClick = {onSetP2PointCmd(45)},
            ) {}
            // Кнопка задать точку №16
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_16,
                onClick = {onSetP2PointCmd(46)},
            ) {}
            // Кнопка задать точку №17
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_17,
                onClick = {onSetP2PointCmd(47)},
            ) {}
            // Кнопка задать точку №18
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_18,
                onClick = {onSetP2PointCmd(48)},
            ) {}
            // Кнопка задать точку №19
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_19,
                onClick = {onSetP2PointCmd(49)},
            ) {}
            // Кнопка задать точку №20
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_20,
                onClick = {onSetP2PointCmd(50)},
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
fun SettingPointsPolygon2CoordinatePreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        SettingPointsPolygon2Coordinate(
            setValuePolygon2PxPos = "1",
            updateValuePolygon2PxPos = { },
            onsetValuePolygon2PxPos = { },

            setValuePolygon2PyPos = "1",
            updateValuePolygon2PyPos = { },
            onsetValuePolygon2PyPos = { },

            onSetP2PointCmd = {},

            errorMessagePolygon2 = null,
            logMessagePolygon2 = null,

            polygon2PointY01 = 1f,
            polygon2PointX01 = 1f,
            polygon2PointY02 = 1f,
            polygon2PointX02 = 1f,
            polygon2PointY03 = 1f,
            polygon2PointX03 = 1f,
            polygon2PointY04 = 1f,
            polygon2PointX04 = 1f,
            polygon2PointY05 = 1f,
            polygon2PointX05 = 1f,
            polygon2PointY06 = 1f,
            polygon2PointX06 = 1f,
            polygon2PointY07 = 1f,
            polygon2PointX07 = 1f,
            polygon2PointY08 = 1f,
            polygon2PointX08 = 1f,
            polygon2PointY09 = 1f,
            polygon2PointX09 = 1f,
            polygon2PointY10 = 1f,
            polygon2PointX10 = 1f,

            polygon2PointY11 = 1f,
            polygon2PointX11 = 1f,
            polygon2PointY12 = 1f,
            polygon2PointX12 = 1f,
            polygon2PointY13 = 1f,
            polygon2PointX13 = 1f,
            polygon2PointY14 = 1f,
            polygon2PointX14 = 1f,
            polygon2PointY15 = 1f,
            polygon2PointX15 = 1f,
            polygon2PointY16 = 1f,
            polygon2PointX16 = 1f,
            polygon2PointY17 = 1f,
            polygon2PointX17 = 1f,
            polygon2PointY18 = 1f,
            polygon2PointX18 = 1f,
            polygon2PointY19 = 1f,
            polygon2PointX19 = 1f,
            polygon2PointY20 = 1f,
            polygon2PointX20 = 1f,
        )
    }
}