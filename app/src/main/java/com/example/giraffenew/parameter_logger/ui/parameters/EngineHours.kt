package com.example.giraffenew.parameter_logger.ui.parameters

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
fun EngineHours(
    hmiHoistOperatingHours : Float,
    hmiSlewOperatingHours : Float,
    hmiTrolleyOperatingHours : Float,
    hmiCraneOperatingHours : Float,
    hmiTotalCraneOperatingHours : Float,
    hmiSBKBOperatingHours : Float,
    hmiTotalCraneOperatingHoursMaintenance : Float,
){
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
//№.1 - Левая область для отображения списка исполнительных механизмов
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
                text = stringResource(R.string.ParameterLogger_Text_Engine_Hours))
//Отображения текста моточасов
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Drive_Hoist)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Drive_Slewing)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Drive_Trolley)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Drive_Crane)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Crane_M_Ch)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_SBKB)
        }
//№.2 - Правая область для отображения значений моточасов исполнительных механизмов
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
// Отображение Привод подъема [м.ч.]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDecimal(hmiHoistOperatingHours)
                )
            }
// Отображение Привод поворота [м.ч.]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDecimal(hmiSlewOperatingHours)
                )

            }
// Отображение Привод тележки [м.ч.]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDecimal(hmiTrolleyOperatingHours)
                )
            }
// Отображение Привод хода крана [м.ч.]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDecimal(hmiCraneOperatingHours)
                )
            }
// Отображение Кран [м.ч.]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDecimal(hmiTotalCraneOperatingHours)
                )
            }
// Отображение Прибор безопасности [м.ч.]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDecimal(hmiSBKBOperatingHours)
                )
            }
        }
    }
}

private fun format(value: Int): String {
    return String.format("%05d", value)
}

private fun formatDecimal(value: Float): String {
    return String.format("%03.2f", value).replace(',', '.')
}

@Preview
@Composable
fun EngineHoursPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        EngineHours(
            hmiHoistOperatingHours = 1f,
            hmiSlewOperatingHours = 1f,
            hmiTrolleyOperatingHours = 1f,
            hmiCraneOperatingHours = 1f,
            hmiTotalCraneOperatingHours = 1f,
            hmiSBKBOperatingHours = 1f,
            hmiTotalCraneOperatingHoursMaintenance = 1f,
        )
    }
}