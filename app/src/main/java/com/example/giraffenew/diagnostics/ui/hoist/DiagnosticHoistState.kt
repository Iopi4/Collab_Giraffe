package com.example.giraffenew.diagnostics.ui.hoist

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingParameter
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe

@Composable
fun DiagnosticHoistState (
    hoistDrivePowerError: Int,
    hoistControlWord: Int,
    hoistDriveError: Int,
    hoistFault:Int,
    hoistDriveErrorCode:Int,
    hoistTargetVelocity: Float,
    hoistControlEffort: Float,
    hoistMotorCurrent: Float,
    hoistEncoder:Int,
){

    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {

//№.1 - Левая область для отображения параметров привода)
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
                text = stringResource(R.string.Diagnostic_Text_Drive_Parameters))

//Наименование параметров диагностики состояния преобразователя частоты привода подъема
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_Readiness)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_Status)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_Fault)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_Amperage)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_Speed_Setting)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_Speed_Current)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_Encoder)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_Last_Fault)
        }

//№.2 - Правая область для отображения состояния параметров привода
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

// Отображение статус Готовность привода к работе
            Row(horizontalArrangement = Arrangement.Start) {
                Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)
                    .weight(3f))
                Box(modifier = Modifier
                    .weight(1f)
                    .size(height = 35.dp, width = 35.dp)
                    .padding(start = 5.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicLampSettingParameter(
                        isLampStatus = hoistDrivePowerError
                    )
                }
            }

// Отображение статус Состояние привода
            Row(horizontalArrangement = Arrangement.Start) {
                Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)
                    .weight(3f))
                Box(modifier = Modifier
                    .weight(1f)
                    .size(height = 35.dp, width = 35.dp)
                    .padding(start = 5.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicLampSettingParameter(
                        isLampStatus = hoistControlWord
                    )
                }
            }

// Отображение статус Ошибка привода
            Row(horizontalArrangement = Arrangement.Start) {
                Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)
                    .weight(3f))
                Box(modifier = Modifier
                    .weight(1f)
                    .size(height = 35.dp, width = 35.dp)
                    .padding(start = 5.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicLampSettingParameter(
                        isLampStatus = hoistDriveError
                    )
                }
            }

// Отображение текущего Тока, А
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = formatDecimal(hoistMotorCurrent)
                )
            }

// Отображение  Заданная скорость, Гц
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = formatDecimal(hoistTargetVelocity)
                )
            }

// Отображение  Текущая скорость, Гц
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = formatDecimal(hoistControlEffort)
                )
            }

// Отображение  Текущее значения энкодера
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = format(hoistEncoder)
                )
            }

// Отображение  Последняя ошибка
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = format(hoistDriveErrorCode)
                )
            }
        }
    }
}

private fun format(value: Int): String {
    //return String.format("%.1f", value).padStart(5, '0')
    return String.format("%10d", value)
}

private fun formatDecimal(value: Float): String {
    //return String.format("%.1f", value).padStart(5, '0')
    return String.format("%04.1f", value).replace(',', '.')
}

@Preview
@Composable
fun DiagnosticHoistStatePreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        DiagnosticHoistState(
            hoistDrivePowerError = 1,
            hoistControlWord = 1,
            hoistDriveError = 1,
            hoistFault = 1,
            hoistDriveErrorCode = 1,
            hoistTargetVelocity = 1f,
            hoistControlEffort = 1f,
            hoistMotorCurrent = 1f,
            hoistEncoder = 1,
        )
    }
}