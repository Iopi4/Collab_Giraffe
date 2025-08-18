package com.example.giraffenew.initial_setup.ui

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
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ParametersCraneAndSBKB(

) {
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {

        //№.1 - Левая область для отображения Бокса - Данные крана и СБКБ)
        Column(
            modifier = Modifier
                .fillMaxSize().weight(3f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
//Отступ
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.spacer))
            //Бокс 1.2 для отображения текста параметров крана
                    BasicNameSettingParameterGiraffe(
                        settingParameterId = R.string.InitialSetup_TextFilled_Model_Crane
                    )
                    BasicNameSettingParameterGiraffe(
                        settingParameterId = R.string.InitialSetup_TextFilled_Number_Crane
                    )
                    BasicNameSettingParameterGiraffe(
                        settingParameterId = R.string.InitialSetup_TextFilled_Registration
                    )
                    BasicNameSettingParameterGiraffe(
                        settingParameterId = R.string.InitialSetup_TextFilled_Operation
                    )
                    BasicNameSettingParameterGiraffe(
                        settingParameterId = R.string.InitialSetup_TextFilled_Number_SBKB
                    )
                    BasicNameSettingParameterGiraffe(
                        settingParameterId = R.string.InitialSetup_TextFilled_Date_SBKB
                    )
        }

//№.2 - Правая область для отображения и ввода параметров. ( 2-Тип крана)
        Column(
            modifier = Modifier
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {

//Бокс 2.2 для отображения и ввода параметров. (2-Данные крана и СБКБ)
//Отступ
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.spacer))
//Ввод. Модель крана
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "10.180",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )
            }
//Ввод. Заводской номер крана
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "№",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )
            }
//Ввод. Дата регистр. в Ростехнадзоре
            FieldDate(
                isValueDay = "",
                isValueMonth = "",
                isValueYear = "",
                onDayChange = TODO(),
                onMonthChange = TODO(),
                onYearChange = TODO(),)
//Ввод. Дата ввода крана в эксплуатацию
            FieldDate(
                isValueDay = "",
                isValueMonth = "",
                isValueYear = "",
                onDayChange = TODO(),
                onMonthChange = TODO(),
                onYearChange = TODO(),)
//Ввод. Заводской номер СБКБ
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "№",
                    value = "",
                    onValueChange = { ""},
                    modifier = Modifier,
                )
            }
//Ввод. Дата установки СБКБ
            FieldDate(
                isValueDay = "",
                isValueMonth = "",
                isValueYear = "",
                onDayChange = TODO(),
                onMonthChange = TODO(),
                onYearChange = TODO(),
            )

        }
    }
}
@Preview
@Composable
fun ParametersCraneAndSBKBPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ParametersCraneAndSBKB(
        )
    }
}