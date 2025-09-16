package com.example.giraffenew.parameter_logger.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffeAccess
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingParameter
import com.example.giraffenew.home.ui.scale.ScaleHorizonWeightHi
import com.example.giraffenew.navigate.presentation.TopBarViewModel
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun Load(
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
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.ParameterLogger_Text_Logger_Save))
//Отображения названия кнопок
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Logger_Write)
            Spacer(modifier = Modifier.size(40.dp))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Logger_Read)
            Spacer(modifier = Modifier.size(40.dp))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Logger_Size)
            Spacer(modifier = Modifier.size(70.dp))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Abort_Logger_Read)

        }
//№.2 - Правая область для отображения кнопок записи и чтения регистратора параметров
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
// Кнопка. Записать данные на SD-карту
            Row(horizontalArrangement = Arrangement.Start) {
                //Кнопка ввода
                Box(modifier = Modifier.weight(4f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.ParameterLogger_Button_Logger_Write,
                        onClick = {},
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampSettingParameter(
                        isLampStatus = 0
                    )
                }
            }
            Spacer(modifier = Modifier.size(15.dp))
//Шкала записи на SD-карту
            Box(modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)) {
                ScaleHorizonWeightHi(
                currentValue = 50f,
                minValue = 0f,
                maxValue = 100f,
                modifier = Modifier.size(width = 200.dp, height = 10.dp)
            ) }

            Spacer(modifier = Modifier.size(15.dp))
// Кнопка. Загрузить данные с SD-карты
            Row(horizontalArrangement = Arrangement.Start) {
                //Кнопка ввода
                Box(modifier = Modifier.weight(4f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.ParameterLogger_Button_Logger_Read,
                        onClick = {},
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampSettingParameter(
                        isLampStatus = 0
                    )
                }
            }
            Spacer(modifier = Modifier.size(15.dp))
//Шкала зугрузки с SD-карты
            Box(modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)) {
                ScaleHorizonWeightHi(
                    currentValue = 50f,
                    minValue = 0f,
                    maxValue = 100f,
                    modifier = Modifier.size(width = 200.dp, height = 10.dp)
                ) }
            Spacer(modifier = Modifier.size(15.dp))
// Отображение Размер файла
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "30000")
            }
            Spacer(modifier = Modifier.size(70.dp))
// Кнопка. Отмена загрузки
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.ParameterLogger_Button_Abort_Logger_Read,
                onClick = {},
            ) {}
        }
    }
}

@Preview
@Composable
fun LoadPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        Load(

        )
    }
}