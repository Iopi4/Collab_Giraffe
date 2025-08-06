package com.example.giraffenew.service_menu.ui.sensors

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
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicTextFilledGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.diagnostics.ui.StateValue
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServiceWind(){
//Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
//№.1 - Левая область для отображения параметров скорости и ускорений
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
                text = stringResource(R.string.ServiceMenu_Text_Sensor_Wind))
//Отображения текста параметров настройки датчика ветра
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Wind_Warning_Signal)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Wind_Alarm_Signal)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Wind_Alarm_Signal_Montage)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Wind_Averaging_Time)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Wind_Calibration)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Wind_Value)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Value)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Calibration_Factor)
        }

//№.2 - Правая область для отображения и ввода параметров скорости и ускорений
        Column(
            modifier = Modifier
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
// Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.ServiceMenu_Text_Setting))
//Ввод. Пред. сигнализация [м/c]
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "0.0",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )}
//Ввод. Авар. сигнализация [м/c]
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "0.0",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )}
//Ввод.Авар.сигн.монтаж.реж.[м/c]
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "0.0",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )}
//Ввод. Время усреднения [с]
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "0.0",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )}
//Ввод. Значение калибровки [м/c]
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "0.0",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )}
// Отображение текущего значения ветра [м/c]
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "12.1"
                )
            }
// Отображение статус датчика ветра
            StateValue(
                isValueParameter = "1000",
                isValueStatus = 1
            )
//Ввод. Коэффициент калибровки
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "0.0",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )}
//Кнопка калибровки датчика
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.Button_Calibration,
                onClick = {},
            ) {}
        }
    }
}

@Preview
@Composable
fun ServiceWindPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ServiceWind(
        )
    }
}