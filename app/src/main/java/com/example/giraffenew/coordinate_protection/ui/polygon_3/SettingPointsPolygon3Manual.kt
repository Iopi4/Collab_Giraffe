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
fun SettingPointsPolygon3Manual (
    setValuePolygon3TrolleyPos: String,
    updateValuePolygon3TrolleyPos: (String) -> Unit,
    onSetValuePolygon3TrolleyPos: () -> Unit,

    setValuePolygon3SlewPos: String,
    updateValuePolygon3SlewPos: (String) -> Unit,
    onSetValuePolygon3SlewPos: () -> Unit,

    setValuePolygon3CranePos: String,
    updateValuePolygon3CranePos: (String) -> Unit,
    onSetValuePolygon3CranePos: () -> Unit,

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
                ) { errorMessagePolygon3?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                    logMessagePolygon3?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }


            //Ввод. Требуемое значение. Вылет
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon3TrolleyPos,
                onValueChange = updateValuePolygon3TrolleyPos,
                onClick = onSetValuePolygon3TrolleyPos,
                isDecimal = true,
            )
            //Ввод. Требуемое значение. Угол поворота
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon3SlewPos,
                onValueChange = updateValuePolygon3SlewPos,
                onClick = onSetValuePolygon3SlewPos,
                isDecimal = true,
            )

            //Ввод. Требуемое значение. Путь.
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon3CranePos,
                onValueChange = updateValuePolygon3CranePos,
                onClick = onSetValuePolygon3CranePos,
                isDecimal = true,
            )

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))

            // Кнопка задать точку №1
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_1,
                onClick = {onSetP3PointCmd(61)},
            ) {}
            // Кнопка задать точку №2
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_2,
                onClick = {onSetP3PointCmd(62)},
            ) {}
            // Кнопка задать точку №3
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_3,
                onClick = {onSetP3PointCmd(63)},
            ) {}
            // Кнопка задать точку №4
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_4,
                onClick = {onSetP3PointCmd(64)},
            ) {}
            // Кнопка задать точку №5
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_5,
                onClick = {onSetP3PointCmd(65)},
            ) {}
            // Кнопка задать точку №6
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_6,
                onClick = {onSetP3PointCmd(66)},
            ) {}
            // Кнопка задать точку №7
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_7,
                onClick = {onSetP3PointCmd(67)},
            ) {}
            // Кнопка задать точку №8
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_8,
                onClick = {onSetP3PointCmd(68)},
            ) {}
            // Кнопка задать точку №9
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_9,
                onClick = {onSetP3PointCmd(69)},
            ) {}
            // Кнопка задать точку №10
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_10,
                onClick = {onSetP3PointCmd(70)},
            ) {}

            // Кнопка задать точку №11
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_11,
                onClick = {onSetP3PointCmd(71)},
            ) {}
            // Кнопка задать точку №12
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_12,
                onClick = {onSetP3PointCmd(72)},
            ) {}
            // Кнопка задать точку №13
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_13,
                onClick = {onSetP3PointCmd(73)},
            ) {}
            // Кнопка задать точку №14
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_14,
                onClick = {onSetP3PointCmd(74)},
            ) {}
            // Кнопка задать точку №15
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_15,
                onClick = {onSetP3PointCmd(75)},
            ) {}
            // Кнопка задать точку №16
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_16,
                onClick = {onSetP3PointCmd(76)},
            ) {}
            // Кнопка задать точку №17
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_17,
                onClick = {onSetP3PointCmd(77)},
            ) {}
            // Кнопка задать точку №18
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_18,
                onClick = {onSetP3PointCmd(78)},
            ) {}
            // Кнопка задать точку №19
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_19,
                onClick = {onSetP3PointCmd(79)},
            ) {}
            // Кнопка задать точку №20
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_20,
                onClick = {onSetP3PointCmd(80)},
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
fun SettingPointsPolygon3ManualPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        SettingPointsPolygon3Manual(
            setValuePolygon3TrolleyPos="1",
            updateValuePolygon3TrolleyPos ={},
            onSetValuePolygon3TrolleyPos ={},

            setValuePolygon3SlewPos ="1",
            updateValuePolygon3SlewPos ={},
            onSetValuePolygon3SlewPos ={},

            setValuePolygon3CranePos ="1",
            updateValuePolygon3CranePos ={},
            onSetValuePolygon3CranePos ={},

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