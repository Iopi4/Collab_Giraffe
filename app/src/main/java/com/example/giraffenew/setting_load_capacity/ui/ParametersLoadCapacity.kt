package com.example.giraffenew.setting_load_capacity.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicSettingsName
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ParametersLoadCapacity() {

    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    )
    {
        //№.1 - Левая область для отображения параметров грузовой характеристики
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
                text = stringResource(R.string.SettingLoadCapacity_Text_Type_GH))
            //Типы грузовых характеристик
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_Text_GH_0)
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_Text_GH_1)
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_Text_GH_2)
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_Text_GH_3)
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_Text_GH_4)
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_Text_GH_5)
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_Text_GH_6)
        }

//№.2 - Правая область для выбора грузовой характеристики
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
                text = stringResource(R.string.SettingLoadCapacity_Text_ChoiceType_GH) )

            // Отображение кнопок выбора грузовой характеристики
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.SettingLoadCapacity_Button_GH_0,
                onClick = {},
            ) {}
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.SettingLoadCapacity_Button_GH_1,
                onClick = {},
            ) {}
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.SettingLoadCapacity_Button_GH_2,
                onClick = {},
            ) {}
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.SettingLoadCapacity_Button_GH_3,
                onClick = {},
            ) {}
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.SettingLoadCapacity_Button_GH_4,
                onClick = {},
            ) {}
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.SettingLoadCapacity_Button_GH_5,
                onClick = {},
            ) {}
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.SettingLoadCapacity_Button_GH_6,
                onClick = {},
            ) {}


        }
    }
}

@Preview
@Composable
fun ParametersLoadCapacityPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ParametersLoadCapacity(
        )
    }
}