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
fun Cycles(
    arrRestA1MW1 :Int,
    arrRestA1MW2 :Int,
    arrRestA2MW1 :Int,
    arrRestA2MW2 :Int,
    arrRestA3MW1 :Int,
    arrRestA3MW2 :Int,
    arrRestA4MW1 :Int,
    arrRestA4MW2 :Int,
    arrRestA5MW1 :Int,
    arrRestA5MW2 :Int,
    arrRestA6MW1 :Int,
    arrRestA6MW2 :Int,
    arrRestA7MW1 :Int,
    arrRestA7MW2 :Int,
    arrRestA8MW1 :Int,
    arrRestA8MW2 :Int,
    arrRestA9MW1 :Int,
    arrRestA9MW2 :Int,
    arrRestA10MW1 :Int,
    arrRestA10MW2 :Int,
    arrRestA11MW1 :Int,
    arrRestA11MW2 :Int,
    rCycleHoistMW1 :Int,
    rCycleHoistMW2 :Int,
    rCycleSlewMW1 :Int,
    rCycleSlewMW2 :Int,
    rCycleTrolleyMW1 :Int,
    rCycleTrolleyMW2 :Int,
    rCycleCraneMW1 :Int,
    rCycleCraneMW2 :Int,
    rCycleHoleCraneMW1 :Int,
    rCycleHoleCraneMW2 :Int,
){
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
                    valueParameterId = formatDoubleWord(arrRestA1MW1,arrRestA1MW2)
                )
            }
// Отображение количества циклов с нагрузкой 11_20%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDoubleWord(arrRestA2MW1,arrRestA2MW2)
                )
            }
// Отображение количества циклов с нагрузкой 21_30%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDoubleWord(arrRestA3MW1,arrRestA3MW2)
                )
            }
// Отображение количества циклов с нагрузкой 31_40%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDoubleWord(arrRestA4MW1,arrRestA4MW2)
                )
            }
// Отображение количества циклов с нагрузкой 41_50%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDoubleWord(arrRestA5MW1,arrRestA5MW2)
                )
            }
// Отображение количества циклов с нагрузкой 51_60%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDoubleWord(arrRestA6MW1,arrRestA6MW2)
                )
            }
// Отображение количества циклов с нагрузкой 61_70%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDoubleWord(arrRestA7MW1,arrRestA7MW2)
                )
            }
// Отображение количества циклов с нагрузкой 71_80%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDoubleWord(arrRestA8MW1,arrRestA8MW2)
                )
            }
// Отображение количества циклов с нагрузкой 81_90%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDoubleWord(arrRestA9MW1,arrRestA9MW2)
                )
            }
// Отображение количества циклов с нагрузкой 91_100%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDoubleWord(arrRestA10MW1,arrRestA10MW2)
                )
            }
// Отображение количества циклов с нагрузкой >100%
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDoubleWord(arrRestA11MW1,arrRestA11MW2)
                )
            }
// Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
// Отображение количества операций. Подъем
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDoubleWord(rCycleHoistMW1, rCycleHoistMW2)
                )
            }
// Отображение количества операций. Поворот
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDoubleWord(rCycleSlewMW1, rCycleSlewMW2)
                )
            }
// Отображение количества операций. Тележка
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDoubleWord(rCycleTrolleyMW1, rCycleTrolleyMW2)
                )
            }
// Отображение количества операций. Ход крана
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDoubleWord(rCycleCraneMW1, rCycleCraneMW2)
                )
            }
// Отображение количества операций. Кран
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDoubleWord(rCycleHoleCraneMW1,rCycleHoleCraneMW2)
                )
            }
        }
    }
}

private fun format(value: Int): String {
    return String.format("%05d", value)
}

private fun formatDoubleWord(value1: Int, value2: Int): String {
    return String.format("%05d%05d", value2, value1)
}


@Preview
@Composable
fun CyclesPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        Cycles(
        arrRestA1MW1 = 1,
        arrRestA1MW2  = 1,
        arrRestA2MW1  = 1,
        arrRestA2MW2  = 1,
        arrRestA3MW1  = 1,
        arrRestA3MW2  = 1,
        arrRestA4MW1  = 1,
        arrRestA4MW2  = 1,
        arrRestA5MW1  = 1,
        arrRestA5MW2  = 1,
        arrRestA6MW1  = 1,
        arrRestA6MW2  = 1,
        arrRestA7MW1  = 1,
        arrRestA7MW2  = 1,
        arrRestA8MW1  = 1,
        arrRestA8MW2  = 1,
        arrRestA9MW1  = 1,
        arrRestA9MW2  = 1,
        arrRestA10MW1  = 1,
        arrRestA10MW2  = 1,
        arrRestA11MW1  = 1,
        arrRestA11MW2  = 1,
        rCycleHoistMW1  = 1,
        rCycleHoistMW2  = 1,
        rCycleSlewMW1  = 1,
        rCycleSlewMW2  = 1,
        rCycleTrolleyMW1  = 1,
        rCycleTrolleyMW2  = 1,
        rCycleCraneMW1  = 1,
        rCycleCraneMW2  = 1,
        rCycleHoleCraneMW1  = 1,
        rCycleHoleCraneMW2  = 1,
        )
    }
}