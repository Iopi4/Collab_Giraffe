package com.example.giraffenew.login.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicTextFilledGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun Authorization(
    password: String, // Новый параметр
    onPasswordChange: (String) -> Unit, // Новый параметр
    onSetPasswordLogIn: () -> Unit,
    onSetExitLevel: () -> Unit,
    buildMode: Int,
    serviceMode: Int,
    montageMode: Int,
){

    // Формируем список активных режимов
    val activeModes = buildList {
        if (buildMode == 1) add(stringResource(R.string.LoginScreen_Text_Reset_Build_Mode))
        if (serviceMode == 1) add(stringResource(R.string.LoginScreen_Text_Reset_Service_Mode))
        if (montageMode == 1) add(stringResource(R.string.LoginScreen_Text_Reset_Montage_Mode))
    }

    // Объединяем через запятую или используем пустую строку если нет активных режимов
    val mode = if (activeModes.isNotEmpty()) {
        activeModes.joinToString()
    } else {
        "" // Пустая строка вместо null
    }

//Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    )
    {
//№.1 - Левая область для отображения параметров
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

//№.2 - Правая область для ввода пароля
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
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = mode
                )
            }
// Поле ввода пароля (ОБНОВЛЕНО)
            Box(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "0123456789",
                    value = password,
                    onValueChange = onPasswordChange,
                    modifier = Modifier,
                )
            }
//Кнопка. Подтвердить код
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.Button_Enter,
                onClick = onSetPasswordLogIn,
            ) {}
//Кнопка. Сброс уровня доступа
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.Button_Turn_Reset,
                onClick = onSetExitLevel,
            ) {}
        }
    }
}



@Preview
@Composable
fun AuthorizationPreview(){
    GIRAFFEnewTheme (dynamicColor = false) {
        Authorization(
            password = "1",
            onPasswordChange = { },

            buildMode = 1,
            serviceMode = 1,
            montageMode = 1,

            onSetPasswordLogIn = {},
            onSetExitLevel= {},
        )
    }
}