package com.example.giraffenew.coordinate_protection.ui.polygon_3

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
fun SettingPointsPolygon3Coordinate (
    setValuePolygon3PxPos: String,
    updateValuePolygon3PxPos: (String) -> Unit,
    onsetValuePolygon3PxPos: () -> Unit,

    setValuePolygon3PyPos: String,
    updateValuePolygon3PyPos: (String) -> Unit,
    onsetValuePolygon3PyPos: () -> Unit,

    onSetP3PointCmd: (Int) -> Unit,

    errorMessagePolygon3: String?,
    logMessagePolygon3: String?,


    polygon3PointY01: Float, polygon3PointX01: Float,
    polygon3PointY02: Float, polygon3PointX02: Float,
    polygon3PointY03: Float, polygon3PointX03: Float,
    polygon3PointY04: Float, polygon3PointX04: Float,
    polygon3PointY05: Float, polygon3PointX05: Float,
    polygon3PointY06: Float, polygon3PointX06: Float,
    polygon3PointY07: Float, polygon3PointX07: Float,
    polygon3PointY08: Float, polygon3PointX08: Float,
    polygon3PointY09: Float, polygon3PointX09: Float,
    polygon3PointY10: Float, polygon3PointX10: Float,
    polygon3PointY11: Float, polygon3PointX11: Float,
    polygon3PointY12: Float, polygon3PointX12: Float,
    polygon3PointY13: Float, polygon3PointX13: Float,
    polygon3PointY14: Float, polygon3PointX14: Float,
    polygon3PointY15: Float, polygon3PointX15: Float,
    polygon3PointY16: Float, polygon3PointX16: Float,
    polygon3PointY17: Float, polygon3PointX17: Float,
    polygon3PointY18: Float, polygon3PointX18: Float,
    polygon3PointY19: Float, polygon3PointX19: Float,
    polygon3PointY20: Float, polygon3PointX20: Float,
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
                pointX = formatDecimal(polygon3PointX01),
                pointY = formatDecimal(polygon3PointY01)
            )
            //Координаты точки №2
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX02),
                pointY = formatDecimal(polygon3PointY02)
            )
            //Координаты точки №3
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX03),
                pointY = formatDecimal(polygon3PointY03)
            )
            //Координаты точки №4
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX04),
                pointY = formatDecimal(polygon3PointY04)
            )
            //Координаты точки №5
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX05),
                pointY = formatDecimal(polygon3PointY05)
            )
            //Координаты точки №6
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX06),
                pointY = formatDecimal(polygon3PointY06)
            )
            //Координаты точки №7
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX07),
                pointY = formatDecimal(polygon3PointY07)
            )
            //Координаты точки №8
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX08),
                pointY = formatDecimal(polygon3PointY08)
            )
            //Координаты точки №9
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX09),
                pointY = formatDecimal(polygon3PointY09)
            )
            //Координаты точки №10
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX10),
                pointY = formatDecimal(polygon3PointY10)
            )
            //Координаты точки №11
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX11),
                pointY = formatDecimal(polygon3PointY11)
            )
            //Координаты точки №12
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX12),
                pointY = formatDecimal(polygon3PointY12)
            )
            //Координаты точки №13
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX13),
                pointY = formatDecimal(polygon3PointY13)
            )
            //Координаты точки №14
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX14),
                pointY = formatDecimal(polygon3PointY14)
            )
            //Координаты точки №15
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX15),
                pointY = formatDecimal(polygon3PointY15)
            )
            //Координаты точки №16
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX16),
                pointY = formatDecimal(polygon3PointY16)
            )
            //Координаты точки №17
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX17),
                pointY = formatDecimal(polygon3PointY17)
            )
            //Координаты точки №18
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX18),
                pointY = formatDecimal(polygon3PointY18)
            )
            //Координаты точки №19
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX19),
                pointY = formatDecimal(polygon3PointY19)
            )
            //Координаты точки №20
            PolygonPoint(
                pointX = formatDecimal(polygon3PointX20),
                pointY = formatDecimal(polygon3PointY20)
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
                ) { errorMessagePolygon3?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                    logMessagePolygon3?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }

            //Ввод. Требуемое значение. Координата X
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon3PxPos,
                onValueChange = updateValuePolygon3PxPos,
                onClick = onsetValuePolygon3PxPos,
                isDecimal = true,
            )
            //Ввод. Требуемое значение. Координата Y
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon3PyPos,
                onValueChange = updateValuePolygon3PyPos,
                onClick = onsetValuePolygon3PyPos,
                isDecimal = true,
            )

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))

            // Кнопка задать точку №1
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_1,
                onClick = {onSetP3PointCmd(31)},
            ) {}
            // Кнопка задать точку №2
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_2,
                onClick = {onSetP3PointCmd(32)},
            ) {}
            // Кнопка задать точку №3
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_3,
                onClick = {onSetP3PointCmd(33)},
            ) {}
            // Кнопка задать точку №4
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_4,
                onClick = {onSetP3PointCmd(34)},
            ) {}
            // Кнопка задать точку №5
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_5,
                onClick = {onSetP3PointCmd(35)},
            ) {}
            // Кнопка задать точку №6
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_6,
                onClick = {onSetP3PointCmd(36)},
            ) {}
            // Кнопка задать точку №7
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_7,
                onClick = {onSetP3PointCmd(37)},
            ) {}
            // Кнопка задать точку №8
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_8,
                onClick = {onSetP3PointCmd(38)},
            ) {}
            // Кнопка задать точку №9
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_9,
                onClick = {onSetP3PointCmd(39)},
            ) {}
            // Кнопка задать точку №10
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_10,
                onClick = {onSetP3PointCmd(40)},
            ) {}

            // Кнопка задать точку №11
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_11,
                onClick = {onSetP3PointCmd(41)},
            ) {}
            // Кнопка задать точку №12
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_12,
                onClick = {onSetP3PointCmd(42)},
            ) {}
            // Кнопка задать точку №13
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_13,
                onClick = {onSetP3PointCmd(43)},
            ) {}
            // Кнопка задать точку №14
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_14,
                onClick = {onSetP3PointCmd(44)},
            ) {}
            // Кнопка задать точку №15
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_15,
                onClick = {onSetP3PointCmd(45)},
            ) {}
            // Кнопка задать точку №16
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_16,
                onClick = {onSetP3PointCmd(46)},
            ) {}
            // Кнопка задать точку №17
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_17,
                onClick = {onSetP3PointCmd(47)},
            ) {}
            // Кнопка задать точку №18
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_18,
                onClick = {onSetP3PointCmd(48)},
            ) {}
            // Кнопка задать точку №19
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_19,
                onClick = {onSetP3PointCmd(49)},
            ) {}
            // Кнопка задать точку №20
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_20,
                onClick = {onSetP3PointCmd(50)},
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
fun SettingPointsPolygon3CoordinatePreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        SettingPointsPolygon3Coordinate(
            setValuePolygon3PxPos = "1",
            updateValuePolygon3PxPos = { },
            onsetValuePolygon3PxPos = { },

            setValuePolygon3PyPos = "1",
            updateValuePolygon3PyPos = { },
            onsetValuePolygon3PyPos = { },

            onSetP3PointCmd = {},

            errorMessagePolygon3 = null,
            logMessagePolygon3 = null,


            polygon3PointY01 = 1f,
            polygon3PointX01 = 1f,
            polygon3PointY02 = 1f,
            polygon3PointX02 = 1f,
            polygon3PointY03 = 1f,
            polygon3PointX03 = 1f,
            polygon3PointY04 = 1f,
            polygon3PointX04 = 1f,
            polygon3PointY05 = 1f,
            polygon3PointX05 = 1f,
            polygon3PointY06 = 1f,
            polygon3PointX06 = 1f,
            polygon3PointY07 = 1f,
            polygon3PointX07 = 1f,
            polygon3PointY08 = 1f,
            polygon3PointX08 = 1f,
            polygon3PointY09 = 1f,
            polygon3PointX09 = 1f,
            polygon3PointY10 = 1f,
            polygon3PointX10 = 1f,

            polygon3PointY11 = 1f,
            polygon3PointX11 = 1f,
            polygon3PointY12 = 1f,
            polygon3PointX12 = 1f,
            polygon3PointY13 = 1f,
            polygon3PointX13 = 1f,
            polygon3PointY14 = 1f,
            polygon3PointX14 = 1f,
            polygon3PointY15 = 1f,
            polygon3PointX15 = 1f,
            polygon3PointY16 = 1f,
            polygon3PointX16 = 1f,
            polygon3PointY17 = 1f,
            polygon3PointX17 = 1f,
            polygon3PointY18 = 1f,
            polygon3PointX18 = 1f,
            polygon3PointY19 = 1f,
            polygon3PointX19 = 1f,
            polygon3PointY20 = 1f,
            polygon3PointX20 = 1f,
        )
    }
}