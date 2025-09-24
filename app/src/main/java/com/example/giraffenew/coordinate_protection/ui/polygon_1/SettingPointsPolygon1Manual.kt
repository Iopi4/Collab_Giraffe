package com.example.giraffenew.coordinate_protection.ui.polygon_1

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
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun SettingPointsPolygon1Manual (
    setValuePolygon1TrolleyPos: String,
    updateValuePolygon1TrolleyPos: (String) -> Unit,
    onSetValuePolygon1TrolleyPos: () -> Unit,

    setValuePolygon1SlewPos: String,
    updateValuePolygon1SlewPos: (String) -> Unit,
    onSetValuePolygon1SlewPos: () -> Unit,

    setValuePolygon1CranePos: String,
    updateValuePolygon1CranePos: (String) -> Unit,
    onSetValuePolygon1CranePos: () -> Unit,

    onSetP1PointCmd: (Int) -> Unit,

    errorMessagePolygon1: String?,
    logMessagePolygon1: String?,

    polygon1Point: CoordinateProtectionViewModule.Polygon1Point,

) {
    // Общее состояние прокрутки
    val scrollState = rememberScrollState()

    val polygon1PointY01 = polygon1Point.polygon1PointY01
    val polygon1PointX01 = polygon1Point.polygon1PointX01
    val polygon1PointY02 = polygon1Point.polygon1PointY02
    val polygon1PointX02 = polygon1Point.polygon1PointX02
    val polygon1PointY03 = polygon1Point.polygon1PointY03
    val polygon1PointX03 = polygon1Point.polygon1PointX03
    val polygon1PointY04 = polygon1Point.polygon1PointY04
    val polygon1PointX04 = polygon1Point.polygon1PointX04
    val polygon1PointY05 = polygon1Point.polygon1PointY05
    val polygon1PointX05 = polygon1Point.polygon1PointX05
    val polygon1PointY06 = polygon1Point.polygon1PointY06
    val polygon1PointX06 = polygon1Point.polygon1PointX06
    val polygon1PointY07 = polygon1Point.polygon1PointY07
    val polygon1PointX07 = polygon1Point.polygon1PointX07
    val polygon1PointY08 = polygon1Point.polygon1PointY08
    val polygon1PointX08 = polygon1Point.polygon1PointX08
    val polygon1PointY09 = polygon1Point.polygon1PointY09
    val polygon1PointX09 = polygon1Point.polygon1PointX09
    val polygon1PointY10 = polygon1Point.polygon1PointY10
    val polygon1PointX10 = polygon1Point.polygon1PointX10
    val polygon1PointY11 = polygon1Point.polygon1PointY11
    val polygon1PointX11 = polygon1Point.polygon1PointX11
    val polygon1PointY12 = polygon1Point.polygon1PointY12
    val polygon1PointX12 = polygon1Point.polygon1PointX12
    val polygon1PointY13 = polygon1Point.polygon1PointY13
    val polygon1PointX13 = polygon1Point.polygon1PointX13
    val polygon1PointY14 = polygon1Point.polygon1PointY14
    val polygon1PointX14 = polygon1Point.polygon1PointX14
    val polygon1PointY15 = polygon1Point.polygon1PointY15
    val polygon1PointX15 = polygon1Point.polygon1PointX15
    val polygon1PointY16 = polygon1Point.polygon1PointY16
    val polygon1PointX16 = polygon1Point.polygon1PointX16
    val polygon1PointY17 = polygon1Point.polygon1PointY17
    val polygon1PointX17 = polygon1Point.polygon1PointX17
    val polygon1PointY18 = polygon1Point.polygon1PointY18
    val polygon1PointX18 = polygon1Point.polygon1PointX18
    val polygon1PointY19 = polygon1Point.polygon1PointY19
    val polygon1PointX19 = polygon1Point.polygon1PointX19
    val polygon1PointY20 = polygon1Point.polygon1PointY20
    val polygon1PointX20 = polygon1Point.polygon1PointX20

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
                pointX = formatDecimal(polygon1PointX01),
                pointY = formatDecimal(polygon1PointY01)
            )
            //Координаты точки №2
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX02),
                pointY = formatDecimal(polygon1PointY02)
            )
            //Координаты точки №3
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX03),
                pointY = formatDecimal(polygon1PointY03)
            )
            //Координаты точки №4
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX04),
                pointY = formatDecimal(polygon1PointY04)
            )
            //Координаты точки №5
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX05),
                pointY = formatDecimal(polygon1PointY05)
            )
            //Координаты точки №6
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX06),
                pointY = formatDecimal(polygon1PointY06)
            )
            //Координаты точки №7
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX07),
                pointY = formatDecimal(polygon1PointY07)
            )
            //Координаты точки №8
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX08),
                pointY = formatDecimal(polygon1PointY08)
            )
            //Координаты точки №9
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX09),
                pointY = formatDecimal(polygon1PointY09)
            )
            //Координаты точки №10
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX10),
                pointY = formatDecimal(polygon1PointY10)
            )
            //Координаты точки №11
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX11),
                pointY = formatDecimal(polygon1PointY11)
            )
            //Координаты точки №12
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX12),
                pointY = formatDecimal(polygon1PointY12)
            )
            //Координаты точки №13
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX13),
                pointY = formatDecimal(polygon1PointY13)
            )
            //Координаты точки №14
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX14),
                pointY = formatDecimal(polygon1PointY14)
            )
            //Координаты точки №15
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX15),
                pointY = formatDecimal(polygon1PointY15)
            )
            //Координаты точки №16
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX16),
                pointY = formatDecimal(polygon1PointY16)
            )
            //Координаты точки №17
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX17),
                pointY = formatDecimal(polygon1PointY17)
            )
            //Координаты точки №18
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX18),
                pointY = formatDecimal(polygon1PointY18)
            )
            //Координаты точки №19
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX19),
                pointY = formatDecimal(polygon1PointY19)
            )
            //Координаты точки №20
            PolygonPoint(
                pointX = formatDecimal(polygon1PointX20),
                pointY = formatDecimal(polygon1PointY20)
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
                ) { errorMessagePolygon1?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                    logMessagePolygon1?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }


            //Ввод. Требуемое значение. Вылет
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon1TrolleyPos,
                onValueChange = updateValuePolygon1TrolleyPos,
                onClick = onSetValuePolygon1TrolleyPos,
                isDecimal = true,
            )
            //Ввод. Требуемое значение. Угол поворота
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon1SlewPos,
                onValueChange = updateValuePolygon1SlewPos,
                onClick = onSetValuePolygon1SlewPos,
                isDecimal = true,
            )

            //Ввод. Требуемое значение. Путь.
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon1CranePos,
                onValueChange = updateValuePolygon1CranePos,
                onClick = onSetValuePolygon1CranePos,
                isDecimal = true,
            )

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))

            // Кнопка задать точку №1
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_1,
                onClick = {onSetP1PointCmd(61)},
            ) {}
            // Кнопка задать точку №2
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_2,
                onClick = {onSetP1PointCmd(62)},
            ) {}
            // Кнопка задать точку №3
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_3,
                onClick = {onSetP1PointCmd(63)},
            ) {}
            // Кнопка задать точку №4
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_4,
                onClick = {onSetP1PointCmd(64)},
            ) {}
            // Кнопка задать точку №5
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_5,
                onClick = {onSetP1PointCmd(65)},
            ) {}
            // Кнопка задать точку №6
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_6,
                onClick = {onSetP1PointCmd(66)},
            ) {}
            // Кнопка задать точку №7
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_7,
                onClick = {onSetP1PointCmd(67)},
            ) {}
            // Кнопка задать точку №8
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_8,
                onClick = {onSetP1PointCmd(68)},
            ) {}
            // Кнопка задать точку №9
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_9,
                onClick = {onSetP1PointCmd(69)},
            ) {}
            // Кнопка задать точку №10
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_10,
                onClick = {onSetP1PointCmd(70)},
            ) {}

            // Кнопка задать точку №11
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_11,
                onClick = {onSetP1PointCmd(71)},
            ) {}
            // Кнопка задать точку №12
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_12,
                onClick = {onSetP1PointCmd(72)},
            ) {}
            // Кнопка задать точку №13
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_13,
                onClick = {onSetP1PointCmd(73)},
            ) {}
            // Кнопка задать точку №14
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_14,
                onClick = {onSetP1PointCmd(74)},
            ) {}
            // Кнопка задать точку №15
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_15,
                onClick = {onSetP1PointCmd(75)},
            ) {}
            // Кнопка задать точку №16
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_16,
                onClick = {onSetP1PointCmd(76)},
            ) {}
            // Кнопка задать точку №17
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_17,
                onClick = {onSetP1PointCmd(77)},
            ) {}
            // Кнопка задать точку №18
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_18,
                onClick = {onSetP1PointCmd(78)},
            ) {}
            // Кнопка задать точку №19
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_19,
                onClick = {onSetP1PointCmd(79)},
            ) {}
            // Кнопка задать точку №20
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_20,
                onClick = {onSetP1PointCmd(80)},
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
fun SettingPointsPolygon1ManualPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        SettingPointsPolygon1Manual(
            setValuePolygon1TrolleyPos="1",
            updateValuePolygon1TrolleyPos ={},
            onSetValuePolygon1TrolleyPos ={},

            setValuePolygon1SlewPos ="1",
            updateValuePolygon1SlewPos ={},
            onSetValuePolygon1SlewPos ={},

            setValuePolygon1CranePos ="1",
            updateValuePolygon1CranePos ={},
            onSetValuePolygon1CranePos ={},

            onSetP1PointCmd = {},

            errorMessagePolygon1 = null,
            logMessagePolygon1 = null,

            polygon1Point = CoordinateProtectionViewModule.Polygon1Point(
            polygon1PointY01 = 1f,
            polygon1PointX01 = 1f,
            polygon1PointY02 = 1f,
            polygon1PointX02 = 1f,
            polygon1PointY03 = 1f,
            polygon1PointX03 = 1f,
            polygon1PointY04 = 1f,
            polygon1PointX04 = 1f,
            polygon1PointY05 = 1f,
            polygon1PointX05 = 1f,
            polygon1PointY06 = 1f,
            polygon1PointX06 = 1f,
            polygon1PointY07 = 1f,
            polygon1PointX07 = 1f,
            polygon1PointY08 = 1f,
            polygon1PointX08 = 1f,
            polygon1PointY09 = 1f,
            polygon1PointX09 = 1f,
            polygon1PointY10 = 1f,
            polygon1PointX10 = 1f,

            polygon1PointY11 = 1f,
            polygon1PointX11 = 1f,
            polygon1PointY12 = 1f,
            polygon1PointX12 = 1f,
            polygon1PointY13 = 1f,
            polygon1PointX13 = 1f,
            polygon1PointY14 = 1f,
            polygon1PointX14 = 1f,
            polygon1PointY15 = 1f,
            polygon1PointX15 = 1f,
            polygon1PointY16 = 1f,
            polygon1PointX16 = 1f,
            polygon1PointY17 = 1f,
            polygon1PointX17 = 1f,
            polygon1PointY18 = 1f,
            polygon1PointX18 = 1f,
            polygon1PointY19 = 1f,
            polygon1PointX19 = 1f,
            polygon1PointY20 = 1f,
            polygon1PointX20 = 1f,
            )
        )
    }
}