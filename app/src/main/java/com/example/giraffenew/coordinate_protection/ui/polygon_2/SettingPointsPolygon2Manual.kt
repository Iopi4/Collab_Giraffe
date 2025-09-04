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
fun SettingPointsPolygon2Manual (
    setValuePolygon2TrolleyPos: String,
    updateValuePolygon2TrolleyPos: (String) -> Unit,
    onSetValuePolygon2TrolleyPos: () -> Unit,

    setValuePolygon2SlewPos: String,
    updateValuePolygon2SlewPos: (String) -> Unit,
    onSetValuePolygon2SlewPos: () -> Unit,

    setValuePolygon2CranePos: String,
    updateValuePolygon2CranePos: (String) -> Unit,
    onSetValuePolygon2CranePos: () -> Unit,

    onSetP2PointCmd: (Int) -> Unit,

    errorMessagePolygon2: String?,
    logMessagePolygon2: String?,


    Polygon2PointY01: Float, Polygon2PointX01: Float,
    Polygon2PointY02: Float, Polygon2PointX02: Float,
    Polygon2PointY03: Float, Polygon2PointX03: Float,
    Polygon2PointY04: Float, Polygon2PointX04: Float,
    Polygon2PointY05: Float, Polygon2PointX05: Float,
    Polygon2PointY06: Float, Polygon2PointX06: Float,
    Polygon2PointY07: Float, Polygon2PointX07: Float,
    Polygon2PointY08: Float, Polygon2PointX08: Float,
    Polygon2PointY09: Float, Polygon2PointX09: Float,
    Polygon2PointY10: Float, Polygon2PointX10: Float,
    Polygon2PointY11: Float, Polygon2PointX11: Float,
    Polygon2PointY12: Float, Polygon2PointX12: Float,
    Polygon2PointY13: Float, Polygon2PointX13: Float,
    Polygon2PointY14: Float, Polygon2PointX14: Float,
    Polygon2PointY15: Float, Polygon2PointX15: Float,
    Polygon2PointY16: Float, Polygon2PointX16: Float,
    Polygon2PointY17: Float, Polygon2PointX17: Float,
    Polygon2PointY18: Float, Polygon2PointX18: Float,
    Polygon2PointY19: Float, Polygon2PointX19: Float,
    Polygon2PointY20: Float, Polygon2PointX20: Float,
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
                pointX = formatDecimal(Polygon2PointX01),
                pointY = formatDecimal(Polygon2PointY01)
            )
            //Координаты точки №2
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX02),
                pointY = formatDecimal(Polygon2PointY02)
            )
            //Координаты точки №3
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX03),
                pointY = formatDecimal(Polygon2PointY03)
            )
            //Координаты точки №4
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX04),
                pointY = formatDecimal(Polygon2PointY04)
            )
            //Координаты точки №5
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX05),
                pointY = formatDecimal(Polygon2PointY05)
            )
            //Координаты точки №6
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX06),
                pointY = formatDecimal(Polygon2PointY06)
            )
            //Координаты точки №7
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX07),
                pointY = formatDecimal(Polygon2PointY07)
            )
            //Координаты точки №8
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX08),
                pointY = formatDecimal(Polygon2PointY08)
            )
            //Координаты точки №9
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX09),
                pointY = formatDecimal(Polygon2PointY09)
            )
            //Координаты точки №10
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX10),
                pointY = formatDecimal(Polygon2PointY10)
            )
            //Координаты точки №11
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX11),
                pointY = formatDecimal(Polygon2PointY11)
            )
            //Координаты точки №12
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX12),
                pointY = formatDecimal(Polygon2PointY12)
            )
            //Координаты точки №13
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX13),
                pointY = formatDecimal(Polygon2PointY13)
            )
            //Координаты точки №14
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX14),
                pointY = formatDecimal(Polygon2PointY14)
            )
            //Координаты точки №15
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX15),
                pointY = formatDecimal(Polygon2PointY15)
            )
            //Координаты точки №16
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX16),
                pointY = formatDecimal(Polygon2PointY16)
            )
            //Координаты точки №17
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX17),
                pointY = formatDecimal(Polygon2PointY17)
            )
            //Координаты точки №18
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX18),
                pointY = formatDecimal(Polygon2PointY18)
            )
            //Координаты точки №19
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX19),
                pointY = formatDecimal(Polygon2PointY19)
            )
            //Координаты точки №20
            PolygonPoint(
                pointX = formatDecimal(Polygon2PointX20),
                pointY = formatDecimal(Polygon2PointY20)
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
                ) { errorMessagePolygon2?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 0.dp)) }
                    logMessagePolygon2?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }

            //Ввод. Требуемое значение. Вылет
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon2TrolleyPos,
                onValueChange = updateValuePolygon2TrolleyPos,
                onClick = onSetValuePolygon2TrolleyPos,
                isDecimal = true,
            )
            //Ввод. Требуемое значение. Угол поворота
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon2SlewPos,
                onValueChange = updateValuePolygon2SlewPos,
                onClick = onSetValuePolygon2SlewPos,
                isDecimal = true,
            )

            //Ввод. Требуемое значение. Путь.
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon2CranePos,
                onValueChange = updateValuePolygon2CranePos,
                onClick = onSetValuePolygon2CranePos,
                isDecimal = true,
            )

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))

            // Кнопка задать точку №1
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_1,
                onClick = {onSetP2PointCmd(61)},
            ) {}
            // Кнопка задать точку №2
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_2,
                onClick = {onSetP2PointCmd(62)},
            ) {}
            // Кнопка задать точку №3
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_3,
                onClick = {onSetP2PointCmd(63)},
            ) {}
            // Кнопка задать точку №4
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_4,
                onClick = {onSetP2PointCmd(64)},
            ) {}
            // Кнопка задать точку №5
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_5,
                onClick = {onSetP2PointCmd(65)},
            ) {}
            // Кнопка задать точку №6
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_6,
                onClick = {onSetP2PointCmd(66)},
            ) {}
            // Кнопка задать точку №7
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_7,
                onClick = {onSetP2PointCmd(67)},
            ) {}
            // Кнопка задать точку №8
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_8,
                onClick = {onSetP2PointCmd(68)},
            ) {}
            // Кнопка задать точку №9
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_9,
                onClick = {onSetP2PointCmd(69)},
            ) {}
            // Кнопка задать точку №10
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_10,
                onClick = {onSetP2PointCmd(70)},
            ) {}

            // Кнопка задать точку №11
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_11,
                onClick = {onSetP2PointCmd(71)},
            ) {}
            // Кнопка задать точку №12
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_12,
                onClick = {onSetP2PointCmd(72)},
            ) {}
            // Кнопка задать точку №13
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_13,
                onClick = {onSetP2PointCmd(73)},
            ) {}
            // Кнопка задать точку №14
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_14,
                onClick = {onSetP2PointCmd(74)},
            ) {}
            // Кнопка задать точку №15
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_15,
                onClick = {onSetP2PointCmd(75)},
            ) {}
            // Кнопка задать точку №16
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_16,
                onClick = {onSetP2PointCmd(76)},
            ) {}
            // Кнопка задать точку №17
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_17,
                onClick = {onSetP2PointCmd(77)},
            ) {}
            // Кнопка задать точку №18
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_18,
                onClick = {onSetP2PointCmd(78)},
            ) {}
            // Кнопка задать точку №19
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_19,
                onClick = {onSetP2PointCmd(79)},
            ) {}
            // Кнопка задать точку №20
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_20,
                onClick = {onSetP2PointCmd(80)},
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
fun SettingPointsPolygon2ManualPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        SettingPointsPolygon2Manual(
            setValuePolygon2TrolleyPos="1",
            updateValuePolygon2TrolleyPos ={},
            onSetValuePolygon2TrolleyPos ={},

            setValuePolygon2SlewPos ="1",
            updateValuePolygon2SlewPos ={},
            onSetValuePolygon2SlewPos ={},

            setValuePolygon2CranePos ="1",
            updateValuePolygon2CranePos ={},
            onSetValuePolygon2CranePos ={},

            onSetP2PointCmd = {},

            errorMessagePolygon2 = null,
            logMessagePolygon2 = null,

            Polygon2PointY01 = 1f,
            Polygon2PointX01 = 1f,
            Polygon2PointY02 = 1f,
            Polygon2PointX02 = 1f,
            Polygon2PointY03 = 1f,
            Polygon2PointX03 = 1f,
            Polygon2PointY04 = 1f,
            Polygon2PointX04 = 1f,
            Polygon2PointY05 = 1f,
            Polygon2PointX05 = 1f,
            Polygon2PointY06 = 1f,
            Polygon2PointX06 = 1f,
            Polygon2PointY07 = 1f,
            Polygon2PointX07 = 1f,
            Polygon2PointY08 = 1f,
            Polygon2PointX08 = 1f,
            Polygon2PointY09 = 1f,
            Polygon2PointX09 = 1f,
            Polygon2PointY10 = 1f,
            Polygon2PointX10 = 1f,

            Polygon2PointY11 = 1f,
            Polygon2PointX11 = 1f,
            Polygon2PointY12 = 1f,
            Polygon2PointX12 = 1f,
            Polygon2PointY13 = 1f,
            Polygon2PointX13 = 1f,
            Polygon2PointY14 = 1f,
            Polygon2PointX14 = 1f,
            Polygon2PointY15 = 1f,
            Polygon2PointX15 = 1f,
            Polygon2PointY16 = 1f,
            Polygon2PointX16 = 1f,
            Polygon2PointY17 = 1f,
            Polygon2PointX17 = 1f,
            Polygon2PointY18 = 1f,
            Polygon2PointX18 = 1f,
            Polygon2PointY19 = 1f,
            Polygon2PointX19 = 1f,
            Polygon2PointY20 = 1f,
            Polygon2PointX20 = 1f,
        )
    }
}