package com.example.giraffenew.coordinate_protection.ui.polygon_2

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.BasicTextFilledGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun SettingPointsPolygon2Coordinate (
) {
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {

        //№.1 - Левая область для отображения параметров настройки точек полигона)
        Column(
            modifier = Modifier
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
            Row(horizontalArrangement = Arrangement.Start) {
                //Отображение координаты X
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
                //Отображение координаты Y
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
            }

            //Координаты точки №2
            Row(horizontalArrangement = Arrangement.Start) {
                //Отображение координаты X
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
                //Отображение координаты Y
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
            }

            //Координаты точки №3
            Row(horizontalArrangement = Arrangement.Start) {
                //Отображение координаты X
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
                //Отображение координаты Y
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
            }

            //Координаты точки №4
            Row(horizontalArrangement = Arrangement.Start) {
                //Отображение координаты X
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
                //Отображение координаты Y
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
            }

            //Координаты точки №5
            Row(horizontalArrangement = Arrangement.Start) {
                //Отображение координаты X
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
                //Отображение координаты Y
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
            }

            //Координаты точки №6
            Row(horizontalArrangement = Arrangement.Start) {
                //Отображение координаты X
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
                //Отображение координаты Y
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
            }

            //Координаты точки №7
            Row(horizontalArrangement = Arrangement.Start) {
                //Отображение координаты X
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
                //Отображение координаты Y
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
            }

            //Координаты точки №8
            Row(horizontalArrangement = Arrangement.Start) {
                //Отображение координаты X
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
                //Отображение координаты Y
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
            }

            //Координаты точки №9
            Row(horizontalArrangement = Arrangement.Start) {
                //Отображение координаты X
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
                //Отображение координаты Y
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
            }

            //Координаты точки №10
            Row(horizontalArrangement = Arrangement.Start) {
                //Отображение координаты X
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
                //Отображение координаты Y
                Box(modifier = Modifier.weight(1f)
                    .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "00.0") }
            }
        }


        //№.2 - Правая область для отображения и ввода данных настройки полигона
        Column(
            modifier = Modifier
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))

            //Ввод. Требуемое значение. Координата X
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "00.0",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )
            }
            //Ввод. Требуемое значение. Координата Y
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "00.0",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )
            }

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))

            // Кнопка задать точку №1
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_1,
                onClick = {},
            ) {}
            // Кнопка задать точку №2
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_2,
                onClick = {},
            ) {}
            // Кнопка задать точку №3
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_3,
                onClick = {},
            ) {}
            // Кнопка задать точку №4
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_4,
                onClick = {},
            ) {}
            // Кнопка задать точку №5
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_5,
                onClick = {},
            ) {}
            // Кнопка задать точку №6
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_6,
                onClick = {},
            ) {}
            // Кнопка задать точку №7
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_7,
                onClick = {},
            ) {}
            // Кнопка задать точку №8
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_8,
                onClick = {},
            ) {}
            // Кнопка задать точку №9
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_9,
                onClick = {},
            ) {}
            // Кнопка задать точку №10
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.CoordinateProtection_Button_Set_Point_10,
                onClick = {},
            ) {}

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))

            // Кнопка назад



        }


    }
}

@Preview
@Composable
fun SettingPointsPolygon2CoordinatePreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        SettingPointsPolygon2Coordinate(
        )
    }
}