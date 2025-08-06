package com.example.giraffenew.parameter_logger.ui

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
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun Cycles(){
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
//№.1 - Левая область для отображения параметров циклов работы механизмов
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
                text = stringResource(R.string.ParameterLogger_Text_Number_Of_Cycles))
//Отображения параметров количества циклов по грузоподъемности
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Cycles_0_10)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Cycles_10_20)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Cycles_20_30)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Cycles_30_40)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Cycles_40_50)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Cycles_50_60)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Cycles_60_70)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Cycles_70_80)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Cycles_80_90)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Cycles_90_100)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Cycles_100)
// Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.ParameterLogger_Text_Number_Of_operations))
//Отображения параметров количества циклов операций по механизмам
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Hoist)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Slewing)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Trolley)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Crane_Move)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Crane)
        }
//№.2 - Правая область для отображения значений количества циклов работы механизмов
        Column(
            modifier = Modifier
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
            val myBoxValuesSettingsParameterModifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)

// Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
// Отображение количества циклов с нагрузкой 0_10%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "0")
            }
// Отображение количества циклов с нагрузкой 10_20%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "10")
            }
// Отображение количества циклов с нагрузкой 20_30%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "20")
            }
// Отображение количества циклов с нагрузкой 30_40%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "30")
            }
// Отображение количества циклов с нагрузкой 40_50%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "40")
            }
// Отображение количества циклов с нагрузкой 50_60%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "50")
            }
// Отображение количества циклов с нагрузкой 60_70%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "60")
            }
// Отображение количества циклов с нагрузкой 70_80%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "70")
            }
// Отображение количества циклов с нагрузкой 80_90%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "80")
            }
// Отображение количества циклов с нагрузкой 90_100%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "90")
            }
// Отображение количества циклов с нагрузкой >100%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "100")
            }
// Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
// Отображение количества операций. Подъем
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "1")
            }
// Отображение количества операций. Поворот
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "2")
            }
// Отображение количества операций. Тележка
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "3")
            }
// Отображение количества операций. Ход крана
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "4")
            }
// Отображение количества операций. Кран
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "5")
            }
        }
    }
}

@Preview
@Composable
fun CyclesPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        Cycles(
        )
    }
}