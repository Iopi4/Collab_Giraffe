package com.example.giraffenew.setting_sensors.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.BasicFilledButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicSettingParameterLamp
import com.example.giraffenew.core.ui_kit.composable.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.BasicSettingsText
import com.example.giraffenew.core.ui_kit.composable.BasicTextFilledGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun CalibrationSensorHoist(

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
                text = stringResource(R.string.SettingSensors_Text_Hoist_Calibration)
            )
            //Датчик
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Sensor)
            //Высота подъема
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Hoist_Lifting)
            //Тест ограничений
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Examination)
            //Стоп вверх
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Hoist_Stop_Up)
            //Снижение скорости вверх
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Hoist_Decline_Up)
            //Снижение скорости вниз
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Hoist_Decline_Down)
            //Стоп вниз
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Hoist_Stop_Down)
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

            // Отображение текущего значения датчика
            Box(modifier = Modifier) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "104350"
                )
            }
            // Отображение текущей высоты подъема
            Box(modifier = Modifier) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "41.8"
                )
            }

            // Кнопка включения проверки защит
            BasicFilledButtonGiraffe(
                modifier = Modifier.fillMaxWidth(),
                text = "Включить тест",
                onClick = {},
            ) {}

            // Отображение кнопок ввода защит и статуса

            // Ограничение подъема в верх
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения максимальной высоты подъема
                Box(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 0.dp, top = 7.dp, bottom = 0.dp)
                        .weight(2f)
                ) {
                    BasicTextFilledGiraffe(
                        placeholder = "000.0",
                        text = "",
                        onValueChange = { "" },
                        modifier = Modifier,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(2f)) {
                    BasicFilledButtonGiraffe(
                        modifier = Modifier,
                        text = "Ввод.",
                        onClick = {},
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    BasicSettingParameterLamp(
                        isLampStatus = 2
                    )
                }
            }


            // Замедление подъема крюка в верх
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения при котором снижается скорость подъема
                Box(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 0.dp, top = 7.dp, bottom = 0.dp)
                        .weight(4f)
                ) {
                    BasicTextFilledGiraffe(
                        placeholder = "000.0",
                        text = "",
                        onValueChange = { "" },
                        modifier = Modifier,
                    )
                }

                Box(modifier = Modifier.weight(1f)) {
                    BasicSettingParameterLamp(
                        isLampStatus = 2
                    )
                }
            }


            // Замедление опускание крюка в низ
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения при котором снижается скорость опускания
                Box(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 0.dp, top = 7.dp, bottom = 0.dp)
                        .weight(4f)
                ) {
                    BasicTextFilledGiraffe(
                        placeholder = "000.0",
                        text = "",
                        onValueChange = { "" },
                        modifier = Modifier,
                    )
                }

                Box(modifier = Modifier.weight(1f)) {
                    BasicSettingParameterLamp(
                        isLampStatus = 2
                    )
                }
            }

            // Ограничение оаускание крюка в низ
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения минимальной высоты подъема
                Box(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 0.dp, top = 7.dp, bottom = 0.dp)
                        .weight(2f)
                ) {
                    BasicTextFilledGiraffe(
                        placeholder = "0.0",
                        text = "",
                        onValueChange = { "" },
                        modifier = Modifier,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(2f)) {
                    BasicFilledButtonGiraffe(
                        modifier = Modifier,
                        text = "Ввод.",
                        onClick = {},
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    BasicSettingParameterLamp(
                        isLampStatus = 2
                    )
                }
            }




        }
    }
}

@Preview
@Composable
fun CalibrationSensorHoistPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        CalibrationSensorHoist()
    }
}