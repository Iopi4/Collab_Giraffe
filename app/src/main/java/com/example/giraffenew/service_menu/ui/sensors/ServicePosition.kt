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
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingParameter
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServicePosition(

){
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
                text = stringResource(R.string.ServiceMenu_Text_Sensor_Position))
//Отображения текста рабочих параметров датчиков положения
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Hoist_Coeff)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Slewing_Coeff)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Trolley_Coeff)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Crane_Coeff)
// Отступ
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.spacer))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensors_Control)
// Отступ
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.spacer))
// Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.ServiceMenu_Sensor_Misalignment))
//Отображения текста рабочих параметров датчиков положения

            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Hoist)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Trolley)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Slewing)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Crane)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Hoist_Activate)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Slewing_Activate)
        }

//№.2 - Правая область для отображения и ввода параметров датчиков
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
//Ввод. Коэффициент датчика подъема
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "0",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )}
//Ввод. Коэффициент датчика поворота
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "0",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )}
//Ввод. Коэффициент датчика тележки
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "0",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )}
//Ввод. Коэффициент датчика хода крана
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "0",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )}
// Отступ
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.spacer))

//Контроль датчиков [вкл/выкл]
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
                    BasicLampSettingParameter(
                        isLampStatus = 1
                    )
                }
            }
// Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
//Ввод. Макс. рассогласование датчика подъема
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "20000",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )}
//Ввод. Макс. рассогласование датчика поворота
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "20000",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )}
//Ввод. Макс. рассогласование датчика тележки
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "20000",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )}
//Ввод. Макс. рассогласование датчика хода крана
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "20000",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )}
//Поле активации датчика подъема
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
                    BasicLampSettingParameter(
                        isLampStatus = 1
                    )
                }
            }
//Поле активации датчика поворота
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
                    BasicLampSettingParameter(
                        isLampStatus = 1
                    )
                }
            }
// Заголовок. "Запрещено отключать оба датчика!.
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.ServiceMenu_Text_Sensor_Disconnect))

        }
    }
}

@Preview
@Composable
fun ServicePositionPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
    }
}