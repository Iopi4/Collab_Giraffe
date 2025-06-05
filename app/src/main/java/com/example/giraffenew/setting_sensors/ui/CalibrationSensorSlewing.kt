package com.example.giraffenew.setting_sensors.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun CalibrationSensorSlewing(

) {

    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {

        //№.1 - Левая область для отображения параметров настройки датчика высоты подъема)
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
                text = stringResource(R.string.SettingSensors_Text_Slewing_Calibration))
            //Датчик
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.SettingSensors_Text_Sensor)
            //Угол поворота
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Slewing_Corner)
            //Установка 0
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Slewing_Installation_Zero)
            //Установка угла
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Slewing_Calibration_Corner)
            //Установка максимального угла
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Slewing_Calibration_Corner)

        }


        //№.2 - Правая область для отображения и ввода данных датчика высоты подъема
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f)
                .padding(5.dp)
                .fillMaxSize()
                .border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
            // Заголовок
            BasicSettingsName(
                modifier = Modifier,
                text = stringResource(R.string.SettingSensors_Text_Parameters)
            )

            // Отображение текущего значения датчика хода крана
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "145598"
                )
            }
            // Отображение текущего угла поворота
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "106"
                )
            }
        // Кнопка установки нуля градусов
            BasicFilledButtonGiraffe(
                modifier = Modifier.fillMaxWidth(),
                text = "Установить",
                onClick = {},
            ) {}

            //Ввод. Установка угла в градусах
            Row (horizontalArrangement = Arrangement.SpaceBetween) {
                //Поле ввода значения текущего угла поворота стрелы
                Box (modifier = Modifier
                    .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                    .weight(1f)
                ) {
                    BasicTextFilledGiraffe(
                        placeholder = "000.0",
                        value = "",
                        onValueChange = { ""},
                        modifier = Modifier,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(2f)) {
                    BasicFilledButtonGiraffe(
                        modifier = Modifier,
                        text = "Уст. угла",
                        onClick = {},
                    ) {}
                }
            }

            //Ввод. Установка максимального угла в градусах
            Row (horizontalArrangement = Arrangement.SpaceBetween) {
                //Поле ввода значения максимального угла поворота стрелы
                Box (modifier = Modifier
                    .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                    .weight(1f)
                ) {
                    BasicTextFilledGiraffe(
                        placeholder = "000.0",
                        value = "",
                        onValueChange = { ""},
                        modifier = Modifier,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(2f)) {
                    BasicFilledButtonGiraffe(
                        modifier = Modifier,
                        text = "Макс. угол",
                        onClick = {},
                    ) {}
                }
            }





        }
    }
}

@Preview
@Composable
fun CalibrationSensorSlewingPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        CalibrationSensorSlewing(
        )
    }
}