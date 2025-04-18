package com.example.giraffenew.initial_setup.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.core.ui_kit.composable.BasicNameSettingParameterGiraffe
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange
import com.example.giraffenew.R


@Composable
fun ParametersDataAndTimeText(

) {

//№.1 - Левая область для отображения двух основных боксов. (1 Бокс - Дата и время , 2 Бокс - Данные крана)
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        val myBoxSettingModifierOrange = Modifier
            .padding(5.dp)
            .weight(1f)
            .clip(shape = RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .border(
                width = 3.dp,
                color = Orange,
                shape = RoundedCornerShape(5.dp)
            )

        //Бокс 1.1 отображения текста названия параметра
        Box(myBoxSettingModifierOrange) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
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
        }

        //Бокс 1.2 для отображения текста параметров крана
        Box(myBoxSettingModifierOrange) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {

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

        }

    }
}


@Preview
@Composable
fun ParametersDataAndTimeTextPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ParametersDataAndTimeText(
        )
    }
}