package com.example.giraffenew.setting_sensors.ui.page_one

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.giraffenew.core.ui_kit.composable.BasicSettingParameterLamp
import com.example.giraffenew.core.ui_kit.composable.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.BasicTextFilledGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun CalibrationSensorTrolley(

) {

    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {

        //№.1 - Левая область для отображения параметров настройки датчика высоты подъема)
        Column(
            modifier = Modifier
                .fillMaxSize().weight(3f).padding(5.dp).border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
            // Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.SettingSensors_Text_Trolley_Calibration))
            //Датчик
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.SettingSensors_Text_Sensor)
            //Вылет
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Trolley_Departure)
            //Тест ограничений
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Examination)
            //Стоп вперед
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Trolley_Stop_Revers)
            //Снижение скорости вперед
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Trolley_Decline_Revers)
            //Снижение скорости назад
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Trolley_Decline_Forward)
            //Стоп назад
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Trolley_Stop_Forward)
        }


        //№.2 - Правая область для отображения и ввода данных датчика высоты подъема
        Column(
            modifier = Modifier
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
            // Заголовок
            BasicSettingsName(
                modifier = Modifier,
                text = stringResource(R.string.SettingSensors_Text_Parameters)
            )

            // Отображение текущего значения датчика вылета
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "335447445"
                )
            }
            // Отображение текущего вылета
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "29.9"
                )
            }

            // Кнопка включения проверки защит
            BasicButtonGiraffe(
                modifier = Modifier.fillMaxWidth(),
                text = R.string.Button_Turn_ON_Off,
                onClick = {},
            ) {}

            // Отображение кнопок ввода защит и статуса

            // Ограничение движения тележки назад
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения минимального вылета
                Box(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                        .weight(2f)
                ) {
                    BasicTextFilledGiraffe(
                        placeholder = "0.0",
                        value = "",
                        onValueChange = { "" },
                        modifier = Modifier,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(2f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_Enter,
                        onClick = {},
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    BasicSettingParameterLamp(
                        isLampStatus = 2
                    )
                }
            }


            // Замедление вылета назад
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения при котором снижается скорость вылета при движении назад
                Box(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)
                        .weight(4f)
                ) {
                    BasicTextFilledGiraffe(
                        placeholder = "00.0",
                        value = "",
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


            // Замедление движения тележки вперед
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения при котором снижается скорость тележки вперед
                Box(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)
                        .weight(4f)
                ) {
                    BasicTextFilledGiraffe(
                        placeholder = "00.0",
                        value = "",
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

            // Ограничение движения тележки вперед
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения минимальной высоты подъема
                Box(
                    modifier = Modifier
                        .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                        .weight(2f)
                ) {
                    BasicTextFilledGiraffe(
                        placeholder = "00.0",
                        value = "",
                        onValueChange = { "" },
                        modifier = Modifier,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(2f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_Enter,
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
fun CalibrationSensorTrolleyPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        CalibrationSensorTrolley(
        )
    }
}