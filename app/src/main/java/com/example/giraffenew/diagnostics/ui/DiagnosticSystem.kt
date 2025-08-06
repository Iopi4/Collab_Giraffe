package com.example.giraffenew.diagnostics.ui

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
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingParameter
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun DiagnosticSystem(
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
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.Diagnostic_Text_System))
            //Статусы оборудования системы управления
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_PLC)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Sensor_Critical_Load)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Sensor_Сargo)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Sensor_Wind)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Sensor_Hoist)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Sensor_Trolley)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Sensor_Slewing)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Sensor_Crane)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Sensor_Crane_Reference_Point)

            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Hoist)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Slewing)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Trolley)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Crane)
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
            // Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.Diagnostic_Text_Status))

// Отображение текущего состояния PLC
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "RUN"
                )
            }

// Отображение статус датчика критического момента
            Box(modifier = Modifier.padding(start = 8.dp)) {
                BasicLampSettingParameter(
                    isLampStatus = 2
                )
            }

// Отображение статус датчика усилия
            StateValue(
                isValueParameter = "1000",
                isValueStatus = 2
            )

// Отображение статус датчика ветра
            StateValue(
                isValueParameter = "1000",
                isValueStatus = 2
            )

// Отображение статуса датчика высоты Hoist
            StateValue(
                isValueParameter = "111111111",
                isValueStatus = 2
            )

// Отображение статуса датчика азимута Slewing
            StateValue(
                isValueParameter = "222222222",
                isValueStatus = 2
            )


// Отображение статуса датчика вылета Trolley
            StateValue(
                isValueParameter = "333333333",
                isValueStatus = 2
            )


// Отображение статуса датчика хода Crane
            StateValue(
                isValueParameter = "444444444",
                isValueStatus = 2
            )

// Отображение статус датчика реперной точки хода крана
            Box(modifier = Modifier.padding(start = 8.dp)) {
                BasicLampSettingParameter(
                    isLampStatus = 2
                )
            }

// Отображение текущего состояния привода подъема
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "RUN")
            }
// Отображение текущего состояния привода поворота
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "RDY")
            }
// Отображение текущего состояния привода тележки
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "RDY")
            }
// Отображение текущего состояния привода хода крана
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "Not avalible")
            }



        }
    }
}

@Preview
@Composable
fun DiagnosticSystemPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        DiagnosticSystem(
        )
    }
}