package com.example.giraffenew.setting_load_capacity.ui

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
import com.example.giraffenew.core.ui_kit.composable.BasicFilledButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.BasicTextFilledGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ParameterWeightSensor() {
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize())
    {
        //№.1 - Левая область для отображения названия параметров датчика усилия
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
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.SettingLoadCapacity_Text_CalibrationSensor))

            //Параметры датчика усилия
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_Text_SensorReadings)
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_Text_HookWeightReadings)
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_TextFilled_HookWeightMin)
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_TextFilled_HookWeightMax)

        }


        //№.2 - Правая область для отображения значений и ввода параметров датчика усилия
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f)
                .padding(5.dp)
                .border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
            // Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.SettingLoadCapacity_Text_SettingSensor))

            // Отображение текущих параметров датчика усилия
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "1000")
            }
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "10.0")
            }

            //Ввод. Минимальный вес груза
            Row (horizontalArrangement = Arrangement.SpaceBetween) {
                //Поле ввода значения минимального веса груза
                Box (modifier = Modifier
                    .padding(start = 15.dp, end = 0.dp, top = 7.dp, bottom = 0.dp)
                    .weight(1f)
                ) {
                    BasicTextFilledGiraffe(
                        placeholder = "0.0",
                        text = "",
                        onValueChange = { ""},
                        modifier = Modifier,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(2f)) {
                BasicFilledButtonGiraffe(
                    modifier = Modifier,
                    text = "КАЛИБР.",
                    onClick = {},
                ) {}
                }
            }

            //Ввод. Максимальный вес груза
            Row (horizontalArrangement = Arrangement.SpaceBetween) {
                //Поле ввода значения максимального веса груза
                Box (modifier = Modifier
                    .padding(start = 15.dp, end = 0.dp, top = 7.dp, bottom = 0.dp)
                    .weight(1f)
                ) {
                    BasicTextFilledGiraffe(
                        placeholder = "10.0",
                        text = "",
                        onValueChange = { ""},
                        modifier = Modifier,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(2f)) {
                    BasicFilledButtonGiraffe(
                        modifier = Modifier,
                        text = "КАЛИБР.",
                        onClick = {},
                    ) {}
                }
            }

        }


    }
}

@Preview
@Composable
fun ParametersSensorPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ParameterWeightSensor(
        )
    }
}