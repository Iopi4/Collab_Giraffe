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
import com.example.giraffenew.coordinate_protection.ui.polygon_1.PolygonPoint
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun SettingPointsPolygon4Manual (
    setValuePolygon4TrolleyPos: String,
    updateValuePolygon4TrolleyPos: (String) -> Unit,
    onSetValuePolygon4TrolleyPos: () -> Unit,

    setValuePolygon4SlewPos: String,
    updateValuePolygon4SlewPos: (String) -> Unit,
    onSetValuePolygon4SlewPos: () -> Unit,

    setValuePolygon4CranePos: String,
    updateValuePolygon4CranePos: (String) -> Unit,
    onSetValuePolygon4CranePos: () -> Unit,

    onSetP4PointCmd: (Int) -> Unit,

    errorMessagePolygon4: String?,
    logMessagePolygon4: String?,


    polygon4PointY01: Float, polygon4PointX01: Float,
    polygon4PointY02: Float, polygon4PointX02: Float,
    polygon4PointY03: Float, polygon4PointX03: Float,
    polygon4PointY04: Float, polygon4PointX04: Float,
    polygon4PointY05: Float, polygon4PointX05: Float,
    polygon4PointY06: Float, polygon4PointX06: Float,
    polygon4PointY07: Float, polygon4PointX07: Float,
    polygon4PointY08: Float, polygon4PointX08: Float,
    polygon4PointY09: Float, polygon4PointX09: Float,
    polygon4PointY10: Float, polygon4PointX10: Float,
    polygon4PointY11: Float, polygon4PointX11: Float,
    polygon4PointY12: Float, polygon4PointX12: Float,
    polygon4PointY13: Float, polygon4PointX13: Float,
    polygon4PointY14: Float, polygon4PointX14: Float,
    polygon4PointY15: Float, polygon4PointX15: Float,
    polygon4PointY16: Float, polygon4PointX16: Float,
    polygon4PointY17: Float, polygon4PointX17: Float,
    polygon4PointY18: Float, polygon4PointX18: Float,
    polygon4PointY19: Float, polygon4PointX19: Float,
    polygon4PointY20: Float, polygon4PointX20: Float,

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
                text = stringResource(R.string.CoordinateProtection_Text_Setting_Points_Polygon_Manual))

            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Trolley)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Slewing)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Crane)

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



//№.2 - Правая область для отображения и ввода данных датчика высоты подъема
        Column(
            modifier = Modifier
                .verticalScroll(scrollState) // Добавлена прокрутка
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)),
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


            //Ввод. Требуемое значение. Вылет
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon4TrolleyPos,
                onValueChange = updateValuePolygon4TrolleyPos,
                onClick = onSetValuePolygon4TrolleyPos,
                isDecimal = true,
            )
            //Ввод. Требуемое значение. Угол поворота
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon4SlewPos,
                onValueChange = updateValuePolygon4SlewPos,
                onClick = onSetValuePolygon4SlewPos,
                isDecimal = true,
            )

            //Ввод. Требуемое значение. Путь.
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon4CranePos,
                onValueChange = updateValuePolygon4CranePos,
                onClick = onSetValuePolygon4CranePos,
                isDecimal = true,
            )

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))

            // Кнопка задать точку №1
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_1,
                onClick = {onSetP4PointCmd(61)},
            ) {}
            // Кнопка задать точку №2
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_2,
                onClick = {onSetP4PointCmd(62)},
            ) {}
            // Кнопка задать точку №3
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_3,
                onClick = {onSetP4PointCmd(63)},
            ) {}
            // Кнопка задать точку №4
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_4,
                onClick = {onSetP4PointCmd(64)},
            ) {}
            // Кнопка задать точку №5
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_5,
                onClick = {onSetP4PointCmd(65)},
            ) {}
            // Кнопка задать точку №6
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_6,
                onClick = {onSetP4PointCmd(66)},
            ) {}
            // Кнопка задать точку №7
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_7,
                onClick = {onSetP4PointCmd(67)},
            ) {}
            // Кнопка задать точку №8
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_8,
                onClick = {onSetP4PointCmd(68)},
            ) {}
            // Кнопка задать точку №9
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_9,
                onClick = {onSetP4PointCmd(69)},
            ) {}
            // Кнопка задать точку №10
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_10,
                onClick = {onSetP4PointCmd(70)},
            ) {}

            // Кнопка задать точку №11
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_11,
                onClick = {onSetP4PointCmd(71)},
            ) {}
            // Кнопка задать точку №12
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_12,
                onClick = {onSetP4PointCmd(72)},
            ) {}
            // Кнопка задать точку №13
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_13,
                onClick = {onSetP4PointCmd(73)},
            ) {}
            // Кнопка задать точку №14
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_14,
                onClick = {onSetP4PointCmd(74)},
            ) {}
            // Кнопка задать точку №15
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_15,
                onClick = {onSetP4PointCmd(75)},
            ) {}
            // Кнопка задать точку №16
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_16,
                onClick = {onSetP4PointCmd(76)},
            ) {}
            // Кнопка задать точку №17
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_17,
                onClick = {onSetP4PointCmd(77)},
            ) {}
            // Кнопка задать точку №18
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_18,
                onClick = {onSetP4PointCmd(78)},
            ) {}
            // Кнопка задать точку №19
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_19,
                onClick = {onSetP4PointCmd(79)},
            ) {}
            // Кнопка задать точку №20
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_20,
                onClick = {onSetP4PointCmd(80)},
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
fun SettingPointsPolygon4ManualPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        SettingPointsPolygon4Manual(
            setValuePolygon4TrolleyPos="1",
            updateValuePolygon4TrolleyPos ={},
            onSetValuePolygon4TrolleyPos ={},

            setValuePolygon4SlewPos ="1",
            updateValuePolygon4SlewPos ={},
            onSetValuePolygon4SlewPos ={},

            setValuePolygon4CranePos ="1",
            updateValuePolygon4CranePos ={},
            onSetValuePolygon4CranePos ={},

            onSetP4PointCmd = {},

            errorMessagePolygon4 = null,
            logMessagePolygon4 = null,


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
        )
    }
}