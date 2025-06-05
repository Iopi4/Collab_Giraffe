package com.example.giraffenew.setting_sensors.ui

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
import com.example.giraffenew.core.ui_kit.composable.BasicFilledButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicSettingParameterLamp
import com.example.giraffenew.core.ui_kit.composable.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.BasicTextFilledGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun CalibrationSensorCrane(

) {

    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {

        //№.1 - Левая область для отображения параметров настройки датчика высоты подъема)
        Column(
            modifier = Modifier
                .fillMaxSize().weight(3f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
            // Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.SettingSensors_Text_Crane_Calibration))
            //Датчик
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.SettingSensors_Text_Sensor)
            //Путь
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Crane_Run)
            //Тест ограничений
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Examination)
            //Стоп вправо
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Crane_Stop_Right)
            //Сниж. скор. вправо
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Crane_Decline_Right)
            //Сниж. скор. влево
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Crane_Decline_Left)
            //Стоп влево
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Crane_Stop_Left)
            //Реперная точка
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Crane_Reference_Point)
            //Включение хода крана
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Crane_Activate)

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

            // Отображение текущего значения датчика хода крана
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "45459012"
                )
            }
            // Отображение пройденного пути
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "12.8"
                )
            }

            // Кнопка включения проверки защит
            BasicFilledButtonGiraffe(
                modifier = Modifier.fillMaxWidth(),
                text = "Включить тест",
                onClick = {},
            ) {}

            // Отображение кнопок ввода защит и статуса

            // Ограничение движения хода вправо
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


            // Замедление скорости движения крана вправо
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


            // Замедление скорости движения крана влево
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

            // Ограничение движения крана влево
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

            //Ввод. Реперная точка хода крана
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "00.0",
                    value = "",
                    onValueChange = {""},
                    modifier = Modifier,
                )
            }

            // Кнопка Активации хода крана
            BasicFilledButtonGiraffe(
                modifier = Modifier.fillMaxWidth(),
                text = "Активировать ход",
                onClick = {},
            ) {}



        }
    }
}

@Preview
@Composable
fun CalibrationSensorCranePreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        CalibrationSensorCrane(
        )
    }
}