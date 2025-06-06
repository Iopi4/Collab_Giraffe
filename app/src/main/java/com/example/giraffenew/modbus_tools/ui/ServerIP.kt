package com.example.giraffenew.modbus_tools.ui

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
import com.example.giraffenew.core.ui_kit.composable.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicTextFilledGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.modbus_tools.data.ModbusViewModel
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServerIP(
    modifier: Modifier= Modifier,
    viewModel: ModbusViewModel=androidx.lifecycle.viewmodel.compose.viewModel(),
){
//Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {


        //№.1 - Левая область для отображения Бокса - Данные регистров)
        Column(
            modifier = Modifier
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ModbusTools_Text_Register_Address
            )

            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ModbusTools_Text_Value_To_Write
            )

            // Кнгопка Read
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.ModbusTools_Button_Read,
                onClick = { viewModel.readRegister() },
            ) {}

            // Кнгопка Write
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.ModbusTools_Button_Write,
                onClick = { viewModel.writeRegister() },
            ) {}

        }


//№.2 - Правая область для настройки порта
        Column(
            modifier = Modifier
                .fillMaxSize().weight(3f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {

            //Ввод. Register Address
            Box(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 0.dp)
            ) {
                BasicTextFilledGiraffe(
                    placeholder = "0",
                    value = viewModel.registerAddress,
                    onValueChange = { viewModel.registerAddress = it },
                    modifier = Modifier,
                )
            }
            //Ввод. Value to Write
            Box(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 0.dp)
            ) {
                BasicTextFilledGiraffe(
                    placeholder = "0",
                    value = viewModel.registerValue,
                    onValueChange = { viewModel.registerValue = it },
                    modifier = Modifier,
                )
            }

            // Отображение текущих параметров датчика усилия
            Box(
                modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 0.dp)
                    .fillMaxSize()
            ) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier.fillMaxSize(),
                    valueParameterId = "Read Value:${viewModel.registerValue}"
                )
            }

        }
    }
}


@Preview
@Composable
fun ServerIPPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        ServerIP(
        )
    }
}