package com.example.giraffenew.setting_load_capacity.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ParametersLoadCapacity(
    onSetCargoCharacteristic0: () -> Unit,
    onSetCargoCharacteristic1: () -> Unit,
    onSetCargoCharacteristic2: () -> Unit,
    onSetCargoCharacteristic3: () -> Unit,
    onSetCargoCharacteristic4: () -> Unit,
    onSetCargoCharacteristic5: () -> Unit,
    onSetCargoCharacteristic6: () -> Unit,

    errorMessageGH: String?,
    logMessageGH: String?,
) {

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

            Box (modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 3.dp)
            ) {
                errorMessageGH?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 0.dp))}
                logMessageGH?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
            }
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
                onClick = onSetCargoCharacteristic0
            ) {}
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.SettingLoadCapacity_Button_GH_1,
                onClick = onSetCargoCharacteristic1,
            ) {}
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.SettingLoadCapacity_Button_GH_2,
                onClick = onSetCargoCharacteristic2,
            ) {}
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.SettingLoadCapacity_Button_GH_3,
                onClick = onSetCargoCharacteristic3,
            ) {}
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.SettingLoadCapacity_Button_GH_4,
                onClick = onSetCargoCharacteristic4,
            ) {}
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.SettingLoadCapacity_Button_GH_5,
                onClick = onSetCargoCharacteristic5,
            ) {}
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.SettingLoadCapacity_Button_GH_6,
                onClick = onSetCargoCharacteristic6,
            ) {}


        }
    }
}

@Preview
@Composable
fun ParametersLoadCapacityPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ParametersLoadCapacity(
            onSetCargoCharacteristic0 = { } ,
            onSetCargoCharacteristic1 = { } ,
            onSetCargoCharacteristic2 = { } ,
            onSetCargoCharacteristic3 = { } ,
            onSetCargoCharacteristic4 = { } ,
            onSetCargoCharacteristic5 = { } ,
            onSetCargoCharacteristic6 = { } ,
            errorMessageGH = null,
            logMessageGH = null
        )
    }
}