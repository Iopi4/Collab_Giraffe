package com.example.giraffenew.setting_load_capacity.ui

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
fun ParametersCargoCharacteristics(

    currentGruzovayZapasovka: Int,
    currentMaxWeight: Float,
    currentMaxWeight2: Float,
    currentCraneLenght: Float,

) {

    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        //№.1 - Левая область для отображения названия параметров грузовой характеристики
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f)
                .padding(5.dp)
                .fillMaxSize()
                .border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
            // Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.SettingLoadCapacity_Text_Parameters_GH))
            //Параметры грузовой характеристики
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_Text_PulleyBlock)
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_Text_NominalLoadCapacity)
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_Text_MaxLoadCapacity)
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_Text_ArrowLength)
        }

        //№.2 - Правая область для отображения значений параметров грузовой характеристики
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f)
                .padding(5.dp)
                .border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
            // Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.SettingLoadCapacity_Text_ValuesParameters_GH) )

            // Отображение текущих параметров ГХ
            Box(modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatZapasovka(currentGruzovayZapasovka))
            }
            Box(modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDecimal(currentMaxWeight))
            }
            Box(modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDecimal(currentMaxWeight2))
            }
            Box(modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDecimal(currentCraneLenght))
            }


        }

    }
}

private fun formatZapasovka(value: Int): String {
    return String.format("%01d", value)
}

private fun format(value: Int): String {
    return String.format("%02d", value)
}

private fun formatDecimal(value: Float): String {
    return String.format("%03.1f", value).replace(',', '.')
}

@Preview
@Composable
fun ParametersCargoCharacteristicsPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ParametersCargoCharacteristics(
            currentGruzovayZapasovka = 1,
            currentMaxWeight = 1f,
            currentMaxWeight2 = 1f,
            currentCraneLenght = 60f
        )
    }
}