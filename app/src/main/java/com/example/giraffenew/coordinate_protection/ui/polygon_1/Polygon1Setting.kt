package com.example.giraffenew.coordinate_protection.ui.polygon_1

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicSettingParameterLamp
import com.example.giraffenew.core.ui_kit.composable.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.BasicTextFilledGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun Polygon1Setting(

) {
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {

        //№.1 - Левая область для отображения параметров настройки полигона)
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
                text = stringResource(R.string.CoordinateProtection_Text_Setting_Polygon))
            //Активировать полигон
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Polygon_Activate)
            //Количество точек
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Number_Of_Points)
            //Высота полигона
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Height_Of_Polygon)
            //Разрешить работу вверху
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Allow_Work_At_Up)
            //Разрешить работу внизу
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Allow_Work_At_Down)

            Spacer(modifier = Modifier.size(30.dp))

            // Выбор режима задания точек
            BasicSettingsName(
                modifier = Modifier,
                text = stringResource(R.string.CoordinateProtection_Text_Point_Setting_Mode))
            //Задание точек по вылету
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Entering_Points_Trolley)
            //Задание точек по коорд.
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Entering_Points_Coordinate)
            //Spacer(modifier = Modifier.size(50.dp))

            Spacer(modifier = Modifier.size(30.dp))

            //Статус настроки полигона
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Status)

        }

//№.2 - Правая область для отображения и ввода данных настройки полигона
        Column(
            modifier = Modifier
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))

//Поле активации полигона
            Row(horizontalArrangement = Arrangement.Start) {
                //Кнопка ввода
                Box(modifier = Modifier.weight(4f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_Turn_ON_Off,
                        onClick = {},
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    BasicSettingParameterLamp(
                        isLampStatus = 2
                    )
                }
            }

//Ввод. Количество точек полигона
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "0",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,

                )
            }

//Ввод. Высота полигона
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "0.0",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,

                )
            }

//Кнопка. Разрешить работу вверху
            Row(horizontalArrangement = Arrangement.Start) {
                //Кнопка ввода
                Box(modifier = Modifier.weight(4f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_Turn_On,
                        onClick = {},
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    BasicSettingParameterLamp(
                        isLampStatus = 2
                    )
                }
            }

//Кнопка. Разрешить работу внизу
            Row(horizontalArrangement = Arrangement.Start) {
                //Кнопка ввода
                Box(modifier = Modifier.weight(4f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_Turn_On,
                        onClick = {},
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    BasicSettingParameterLamp(
                        isLampStatus = 2
                    )
                }
            }

            Spacer(modifier = Modifier.size(30.dp))

// Выбор режима задания точек
            BasicSettingsName(
                modifier = Modifier,
                text = stringResource(R.string.CoordinateProtection_Text_Mode))


//Кнопка. Выбор задание точек в ручную. Состояние
            Row(horizontalArrangement = Arrangement.Start) {
                //Кнопка ввода
                Box(modifier = Modifier.weight(4f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_Turn_On,
                        onClick = {},
                    ) {}
                }

                Box(modifier = Modifier.weight(1f)) {
                    BasicSettingParameterLamp(
                        isLampStatus = 2
                    )
                }
            }

//Кнопка. Выбор задание точек по координатам. Состояние
            Row(horizontalArrangement = Arrangement.Start) {
                //Кнопка ввода
                Box(modifier = Modifier.weight(4f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_Turn_On,
                        onClick = {},
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    BasicSettingParameterLamp(
                        isLampStatus = 2
                    )
                }
            }

            Spacer(modifier = Modifier.size(30.dp))

//Статус активации/настройки полигона
            //1.Многоугольник активирован – успешная активация)
            //2.Используется мало точек – многоугольник не активирован, если введено менее трех точек многоугольника
            //3.Настройки не завершены – многоугольник не активирован
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "Не активен"
                )
            }






        }
    }
}



@Preview
@Composable
fun Polygon1Preview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        Polygon1Setting(
        )
    }
}