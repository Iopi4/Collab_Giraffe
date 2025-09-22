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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun SettingPointsPolygon1Hook(
    currentTrolleyPosition: Float,
    currentSlewPosition: Float,
    currentCranePosition: Float,

    onSetP1PointCmd: (Int) -> Unit,


    polygon1PointY01: Float, polygon1PointX01: Float,
    polygon1PointY02: Float, polygon1PointX02: Float,
    polygon1PointY03: Float, polygon1PointX03: Float,
    polygon1PointY04: Float, polygon1PointX04: Float,
    polygon1PointY05: Float, polygon1PointX05: Float,
    polygon1PointY06: Float, polygon1PointX06: Float,
    polygon1PointY07: Float, polygon1PointX07: Float,
    polygon1PointY08: Float, polygon1PointX08: Float,
    polygon1PointY09: Float, polygon1PointX09: Float,
    polygon1PointY10: Float, polygon1PointX10: Float,
    polygon1PointY11: Float, polygon1PointX11: Float,
    polygon1PointY12: Float, polygon1PointX12: Float,
    polygon1PointY13: Float, polygon1PointX13: Float,
    polygon1PointY14: Float, polygon1PointX14: Float,
    polygon1PointY15: Float, polygon1PointX15: Float,
    polygon1PointY16: Float, polygon1PointX16: Float,
    polygon1PointY17: Float, polygon1PointX17: Float,
    polygon1PointY18: Float, polygon1PointX18: Float,
    polygon1PointY19: Float, polygon1PointX19: Float,
    polygon1PointY20: Float, polygon1PointX20: Float,

    ) { //Зададим поле прокрутки
    val scrollState = rememberScrollState()

//Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {

//№.1 - Левая область для отображения параметров точек полигона)
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
                text = stringResource(R.string.CoordinateProtection_Text_Setting_Points_Polygon_Position))
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
            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))

            //Отображение текущего вылета
                Box(modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = formatDecimal(currentTrolleyPosition)
                    )
                }
            //Отображение текущего угла поворота
            Box(modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = formatDecimal(currentSlewPosition)
                )
            }
            //Отображение расстояние крана. Путь.
            Box(modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = formatDecimal(currentCranePosition)
                )
            }

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))

            // Кнопка задать точку №1
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_1,
                onClick = {onSetP1PointCmd(1)},
            ) {}
            // Кнопка задать точку №2
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_2,
                onClick = {onSetP1PointCmd(2)},
            ) {}
            // Кнопка задать точку №3
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_3,
                onClick = {onSetP1PointCmd(3)},
            ) {}
            // Кнопка задать точку №4
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_4,
                onClick = {onSetP1PointCmd(4)},
            ) {}
            // Кнопка задать точку №5
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_5,
                onClick = {onSetP1PointCmd(5)},
            ) {}
            // Кнопка задать точку №6
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_6,
                onClick = {onSetP1PointCmd(6)},
            ) {}
            // Кнопка задать точку №7
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_7,
                onClick = {onSetP1PointCmd(7)},
            ) {}
            // Кнопка задать точку №8
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_8,
                onClick = {onSetP1PointCmd(8)},
            ) {}
            // Кнопка задать точку №9
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_9,
                onClick = {onSetP1PointCmd(9)},
            ) {}
            // Кнопка задать точку №10
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_10,
                onClick = {onSetP1PointCmd(10)},
            ) {}

            // Кнопка задать точку №11
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_11,
                onClick = {onSetP1PointCmd(11)},
            ) {}
            // Кнопка задать точку №12
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_12,
                onClick = {onSetP1PointCmd(12)},
            ) {}
            // Кнопка задать точку №13
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_13,
                onClick = {onSetP1PointCmd(13)},
            ) {}
            // Кнопка задать точку №14
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_14,
                onClick = {onSetP1PointCmd(14)},
            ) {}
            // Кнопка задать точку №15
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_15,
                onClick = {onSetP1PointCmd(15)},
            ) {}
            // Кнопка задать точку №16
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_16,
                onClick = {onSetP1PointCmd(16)},
            ) {}
            // Кнопка задать точку №17
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_17,
                onClick = {onSetP1PointCmd(17)},
            ) {}
            // Кнопка задать точку №18
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_18,
                onClick = {onSetP1PointCmd(18)},
            ) {}
            // Кнопка задать точку №19
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_19,
                onClick = {onSetP1PointCmd(19)},
            ) {}
            // Кнопка задать точку №20
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_20,
                onClick = {onSetP1PointCmd(20)},
            ) {}

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))

        }
    }
}
@Composable
fun PolygonPoint(
    pointX: String,
    pointY: String,
){
    Row(horizontalArrangement = Arrangement.Start) {
        //Отображение координаты X
        Box(modifier = Modifier.weight(1f)) {
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.CoordinateProtection_Coordinate_X) }
        Box(modifier = Modifier.weight(3f)
            .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 0.dp)) {
            BasicValuesSettingsParameterGiraffe(
                modifier = Modifier,
                valueParameterId = pointX) }


        //Отображение координаты Y
        Box(modifier = Modifier.weight(1f)) {
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.CoordinateProtection_Coordinate_Y) }
        Box(modifier = Modifier.weight(3f)
            .padding(start = 5.dp, end = 5.dp, top = 5.dp, bottom = 0.dp)) {
            BasicValuesSettingsParameterGiraffe(
                modifier = Modifier,
                valueParameterId = pointY) }
    }
}

private fun formatDecimal(value: Float): String {
    return String.format("%03.1f", value).replace(',', '.')
}

@Preview
@Composable
fun SettingPointsPolygon1TrolleyPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        SettingPointsPolygon1Hook(
            currentTrolleyPosition = 1f,
            currentSlewPosition = 1f,
            currentCranePosition = 1f,

            onSetP1PointCmd = {},

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
    }
}

