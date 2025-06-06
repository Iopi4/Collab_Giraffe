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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.core.ui_kit.composable.BasicNameSettingParameterGiraffe
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.BasicTextFilledGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet

@Composable
fun ParametersDateAndTime(

) {
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        //№.1 - Левая область для отображения 1 Бокс - Дата и время
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
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.InitialSetup_Text_Current_Time
            )
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.InitialSetup_Text_Current_Date
            )
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.InitialSetup_TextFilled_Time
            )
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.InitialSetup_TextFilled_Date
            )
        }


//№.2 - Правая область для отображения и ввода параметров. (1- Дата и время)
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


            // Отображение текущего времени
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "Time"
                )
            }
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "Date"
                )
            }

            // Ввод времени. Создадим два поля
            FieldTime(
                isValueHour = "",
                isValueMinute = ""
            )

            // Ввод даты
            FieldDate(
                isValueDay = "",
                isValueMonth = "",
                isValueYear = "",
            )

        }
    }
}


@Preview
@Composable
fun ParametersDataAndTimeTextPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ParametersDateAndTime(
        )
    }
}