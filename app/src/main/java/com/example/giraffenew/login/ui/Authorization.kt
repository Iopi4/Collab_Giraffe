package com.example.giraffenew.login.ui

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
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.core.ui_kit.composable.BasicTextFilledGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun Authorization(){
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
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.LoginScreen_Text_Access_Level)
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.LoginScreen_Text_Access_Code)
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.LoginScreen_Text_Confirm_Code)
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.LoginScreen_Text_Reset_Access_Level)
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
// Отображение текущих уровня доступа
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "эксплуатация")
            }
//Ввод. Пароль
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "1234567890AaBbCcDd",
                    value = "",
                    onValueChange = {""},
                    modifier = Modifier,
                )
            }
            //Кнопка. Подтвердить код
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.Button_Enter,
                onClick = {},
            ) {}
                //Кнопка. Сброс уровня доступа
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.Button_Turn_Reset,
                onClick = {},
            ) {}
        }
    }
}

@Preview
@Composable
fun AuthorizationPreview(){
    GIRAFFEnewTheme (dynamicColor = false) {
        Authorization()
    }
}