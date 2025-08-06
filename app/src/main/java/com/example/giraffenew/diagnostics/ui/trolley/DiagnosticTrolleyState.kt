package com.example.giraffenew.diagnostics.ui.trolley

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
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingParameter
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun DiagnosticTrolleyState (){

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
//Наименование параметров диагностики состояния преобразователя частоты привода тележки
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_Readiness)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_Status)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_Fault)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_Status_Encoder)
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
            Box(modifier = Modifier.padding(start = 8.dp)) {
                BasicLampSettingParameter(
                    isLampStatus = 2) }

// Отображение статус Состояние привода
            Box(modifier = Modifier.padding(start = 8.dp)) {
                BasicLampSettingParameter(
                    isLampStatus = 2) }

// Отображение статус Ошибка привода
            Box(modifier = Modifier.padding(start = 8.dp)) {
                BasicLampSettingParameter(
                    isLampStatus = 2) }

// Отображение статус Состояние энкодера
            Box(modifier = Modifier.padding(start = 8.dp)) {
                BasicLampSettingParameter(
                    isLampStatus = 2) }

// Отображение текущего Тока, А
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "00.0") }

// Отображение  Заданная скорость, Гц
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "00.0") }

// Отображение  Текущая скорость, Гц
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "00.0") }

// Отображение  Текущее значения энкодера
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "65535") }

// Отображение  Последняя ошибка
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "Ошибка связи по сети") }
        }
    }
}

@Preview
@Composable
fun DiagnosticTrolleyStatePreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        DiagnosticTrolleyState(
        )
    }
}