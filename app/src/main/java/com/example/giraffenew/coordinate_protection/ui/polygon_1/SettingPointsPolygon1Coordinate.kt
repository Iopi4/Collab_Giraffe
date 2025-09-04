package com.example.giraffenew.coordinate_protection.ui.polygon_1

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicTextFilledGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun SettingPointsPolygon1Coordinate (

    setValuePolygon1PxPos: String,
    updateValuePolygon1PxPos: (String) -> Unit,
    onsetValuePolygon1PxPos: () -> Unit,

    setValuePolygon1PyPos: String,
    updateValuePolygon1PyPos: (String) -> Unit,
    onsetValuePolygon1PyPos: () -> Unit,

    onSetP1PointCmd: (Int) -> Unit,

    errorMessagePolygon1: String?,
    logMessagePolygon1: String?,


    Polygon1PointY01: Float, Polygon1PointX01: Float,
    Polygon1PointY02: Float, Polygon1PointX02: Float,
    Polygon1PointY03: Float, Polygon1PointX03: Float,
    Polygon1PointY04: Float, Polygon1PointX04: Float,
    Polygon1PointY05: Float, Polygon1PointX05: Float,
    Polygon1PointY06: Float, Polygon1PointX06: Float,
    Polygon1PointY07: Float, Polygon1PointX07: Float,
    Polygon1PointY08: Float, Polygon1PointX08: Float,
    Polygon1PointY09: Float, Polygon1PointX09: Float,
    Polygon1PointY10: Float, Polygon1PointX10: Float,
//    Polygon1PointY11: Float, Polygon1PointX11: Float,
//    Polygon1PointY12: Float, Polygon1PointX12: Float,
//    Polygon1PointY13: Float, Polygon1PointX13: Float,
//    Polygon1PointY14: Float, Polygon1PointX14: Float,
//    Polygon1PointY15: Float, Polygon1PointX15: Float,
//    Polygon1PointY16: Float, Polygon1PointX16: Float,
//    Polygon1PointY17: Float, Polygon1PointX17: Float,
//    Polygon1PointY18: Float, Polygon1PointX18: Float,
//    Polygon1PointY19: Float, Polygon1PointX19: Float,
//    Polygon1PointY20: Float, Polygon1PointX20: Float,
) {
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {

//№.1 - Левая область для отображения параметров настройки точек полигона)
        Column(
            modifier = Modifier
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
                pointX = formatDecimal(Polygon1PointX01),
                pointY = formatDecimal(Polygon1PointY01)
            )
            //Координаты точки №2
            PolygonPoint(
                pointX = formatDecimal(Polygon1PointX02),
                pointY = formatDecimal(Polygon1PointY02)
            )
            //Координаты точки №3
            PolygonPoint(
                pointX = formatDecimal(Polygon1PointX03),
                pointY = formatDecimal(Polygon1PointY03)
            )
            //Координаты точки №4
            PolygonPoint(
                pointX = formatDecimal(Polygon1PointX04),
                pointY = formatDecimal(Polygon1PointY04)
            )
            //Координаты точки №5
            PolygonPoint(
                pointX = formatDecimal(Polygon1PointX05),
                pointY = formatDecimal(Polygon1PointY05)
            )
            //Координаты точки №6
            PolygonPoint(
                pointX = formatDecimal(Polygon1PointX06),
                pointY = formatDecimal(Polygon1PointY06)
            )
            //Координаты точки №7
            PolygonPoint(
                pointX = formatDecimal(Polygon1PointX07),
                pointY = formatDecimal(Polygon1PointY07)
            )
            //Координаты точки №8
            PolygonPoint(
                pointX = formatDecimal(Polygon1PointX08),
                pointY = formatDecimal(Polygon1PointY08)
            )
            //Координаты точки №9
            PolygonPoint(
                pointX = formatDecimal(Polygon1PointX09),
                pointY = formatDecimal(Polygon1PointY09)
            )
            //Координаты точки №10
            PolygonPoint(
                pointX = formatDecimal(Polygon1PointX10),
                pointY = formatDecimal(Polygon1PointY10)
            )
        }


        //№.2 - Правая область для отображения и ввода данных настройки полигона
        Column(
            modifier = Modifier
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
                ) { errorMessagePolygon1?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 0.dp)) }
                    logMessagePolygon1?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }

            //Ввод. Требуемое значение. Координата X
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon1PxPos,
                onValueChange = updateValuePolygon1PxPos,
                onClick = onsetValuePolygon1PxPos,
                isDecimal = true,
            )
            //Ввод. Требуемое значение. Координата Y
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValuePolygon1PyPos,
                onValueChange = updateValuePolygon1PyPos,
                onClick = onsetValuePolygon1PyPos,
                isDecimal = true,
            )

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))

            // Кнопка задать точку №1
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_1,
                onClick = {onSetP1PointCmd(31)},
            ) {}
            // Кнопка задать точку №2
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_2,
                onClick = {onSetP1PointCmd(32)},
            ) {}
            // Кнопка задать точку №3
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_3,
                onClick = {onSetP1PointCmd(33)},
            ) {}
            // Кнопка задать точку №4
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_4,
                onClick = {onSetP1PointCmd(34)},
            ) {}
            // Кнопка задать точку №5
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_5,
                onClick = {onSetP1PointCmd(35)},
            ) {}
            // Кнопка задать точку №6
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_6,
                onClick = {onSetP1PointCmd(36)},
            ) {}
            // Кнопка задать точку №7
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_7,
                onClick = {onSetP1PointCmd(37)},
            ) {}
            // Кнопка задать точку №8
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_8,
                onClick = {onSetP1PointCmd(38)},
            ) {}
            // Кнопка задать точку №9
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_9,
                onClick = {onSetP1PointCmd(39)},
            ) {}
            // Кнопка задать точку №10
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_10,
                onClick = {onSetP1PointCmd(40)},
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
fun SettingPointsPolygon1CoordinatePreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        SettingPointsPolygon1Coordinate(
            setValuePolygon1PxPos = "1",
            updateValuePolygon1PxPos = { },
            onsetValuePolygon1PxPos = { },

            setValuePolygon1PyPos = "1",
            updateValuePolygon1PyPos = { },
            onsetValuePolygon1PyPos = { },

            onSetP1PointCmd = {},

            errorMessagePolygon1 = null,
            logMessagePolygon1 = null,


            Polygon1PointY01 = 1f,
            Polygon1PointX01 = 1f,
            Polygon1PointY02 = 1f,
            Polygon1PointX02 = 1f,
            Polygon1PointY03 = 1f,
            Polygon1PointX03 = 1f,
            Polygon1PointY04 = 1f,
            Polygon1PointX04 = 1f,
            Polygon1PointY05 = 1f,
            Polygon1PointX05 = 1f,
            Polygon1PointY06 = 1f,
            Polygon1PointX06 = 1f,
            Polygon1PointY07 = 1f,
            Polygon1PointX07 = 1f,
            Polygon1PointY08 = 1f,
            Polygon1PointX08 = 1f,
            Polygon1PointY09 = 1f,
            Polygon1PointX09 = 1f,
            Polygon1PointY10 = 1f,
            Polygon1PointX10 = 1f,
        )
    }
}