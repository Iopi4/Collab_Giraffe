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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.coordinate_protection.prezentation.CoordinateProtectionViewModule
import com.example.giraffenew.coordinate_protection.ui.polygon_1.PolygonPoint
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun SettingPointsPolygon3Hook(
    currentTrolleyPosition: Float,
    currentSlewPosition: Float,
    currentCranePosition: Float,

    onSetP3PointCmd: (Int) -> Unit,

    polygon3Point: CoordinateProtectionViewModule.Polygon3Point,

    ) { //Зададим поле прокрутки
    val scrollState = rememberScrollState()

    val polygon3PointY01 = polygon3Point.polygon3PointY01
    val polygon3PointX01 = polygon3Point.polygon3PointX01
    val polygon3PointY02 = polygon3Point.polygon3PointY02
    val polygon3PointX02 = polygon3Point.polygon3PointX02
    val polygon3PointY03 = polygon3Point.polygon3PointY03
    val polygon3PointX03 = polygon3Point.polygon3PointX03
    val polygon3PointY04 = polygon3Point.polygon3PointY04
    val polygon3PointX04 = polygon3Point.polygon3PointX04
    val polygon3PointY05 = polygon3Point.polygon3PointY05
    val polygon3PointX05 = polygon3Point.polygon3PointX05
    val polygon3PointY06 = polygon3Point.polygon3PointY06
    val polygon3PointX06 = polygon3Point.polygon3PointX06
    val polygon3PointY07 = polygon3Point.polygon3PointY07
    val polygon3PointX07 = polygon3Point.polygon3PointX07
    val polygon3PointY08 = polygon3Point.polygon3PointY08
    val polygon3PointX08 = polygon3Point.polygon3PointX08
    val polygon3PointY09 = polygon3Point.polygon3PointY09
    val polygon3PointX09 = polygon3Point.polygon3PointX09
    val polygon3PointY10 = polygon3Point.polygon3PointY10
    val polygon3PointX10 = polygon3Point.polygon3PointX10
    val polygon3PointY11 = polygon3Point.polygon3PointY11
    val polygon3PointX11 = polygon3Point.polygon3PointX11
    val polygon3PointY12 = polygon3Point.polygon3PointY12
    val polygon3PointX12 = polygon3Point.polygon3PointX12
    val polygon3PointY13 = polygon3Point.polygon3PointY13
    val polygon3PointX13 = polygon3Point.polygon3PointX13
    val polygon3PointY14 = polygon3Point.polygon3PointY14
    val polygon3PointX14 = polygon3Point.polygon3PointX14
    val polygon3PointY15 = polygon3Point.polygon3PointY15
    val polygon3PointX15 = polygon3Point.polygon3PointX15
    val polygon3PointY16 = polygon3Point.polygon3PointY16
    val polygon3PointX16 = polygon3Point.polygon3PointX16
    val polygon3PointY17 = polygon3Point.polygon3PointY17
    val polygon3PointX17 = polygon3Point.polygon3PointX17
    val polygon3PointY18 = polygon3Point.polygon3PointY18
    val polygon3PointX18 = polygon3Point.polygon3PointX18
    val polygon3PointY19 = polygon3Point.polygon3PointY19
    val polygon3PointX19 = polygon3Point.polygon3PointX19
    val polygon3PointY20 = polygon3Point.polygon3PointY20
    val polygon3PointX20 = polygon3Point.polygon3PointX20

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
                onClick = {onSetP3PointCmd(1)},
            ) {}
            // Кнопка задать точку №2
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_2,
                onClick = {onSetP3PointCmd(2)},
            ) {}
            // Кнопка задать точку №3
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_3,
                onClick = {onSetP3PointCmd(3)},
            ) {}
            // Кнопка задать точку №4
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_4,
                onClick = {onSetP3PointCmd(4)},
            ) {}
            // Кнопка задать точку №5
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_5,
                onClick = {onSetP3PointCmd(5)},
            ) {}
            // Кнопка задать точку №6
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_6,
                onClick = {onSetP3PointCmd(6)},
            ) {}
            // Кнопка задать точку №7
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_7,
                onClick = {onSetP3PointCmd(7)},
            ) {}
            // Кнопка задать точку №8
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_8,
                onClick = {onSetP3PointCmd(8)},
            ) {}
            // Кнопка задать точку №9
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_9,
                onClick = {onSetP3PointCmd(9)},
            ) {}
            // Кнопка задать точку №10
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_10,
                onClick = {onSetP3PointCmd(10)},
            ) {}

            // Кнопка задать точку №11
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_11,
                onClick = {onSetP3PointCmd(11)},
            ) {}
            // Кнопка задать точку №12
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_12,
                onClick = {onSetP3PointCmd(12)},
            ) {}
            // Кнопка задать точку №13
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_13,
                onClick = {onSetP3PointCmd(13)},
            ) {}
            // Кнопка задать точку №14
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_14,
                onClick = {onSetP3PointCmd(14)},
            ) {}
            // Кнопка задать точку №15
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_15,
                onClick = {onSetP3PointCmd(15)},
            ) {}
            // Кнопка задать точку №16
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_16,
                onClick = {onSetP3PointCmd(16)},
            ) {}
            // Кнопка задать точку №17
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_17,
                onClick = {onSetP3PointCmd(17)},
            ) {}
            // Кнопка задать точку №18
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_18,
                onClick = {onSetP3PointCmd(18)},
            ) {}
            // Кнопка задать точку №19
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_19,
                onClick = {onSetP3PointCmd(19)},
            ) {}
            // Кнопка задать точку №20
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_20,
                onClick = {onSetP3PointCmd(20)},
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
fun SettingPointsPolygon3TrolleyPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        SettingPointsPolygon3Hook(
            currentTrolleyPosition = 1f,
            currentSlewPosition = 1f,
            currentCranePosition = 1f,

            onSetP3PointCmd = {},

            polygon3Point = CoordinateProtectionViewModule.Polygon3Point(
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
            ),


        )
    }
}

