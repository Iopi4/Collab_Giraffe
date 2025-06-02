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
import com.example.giraffenew.core.ui_kit.composable.BasicFilledButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicTextFilledGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.modbus_tools.data.ModbusViewModel
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange


@Composable
fun Register(
    modifier: Modifier= Modifier,
    viewModel: ModbusViewModel=androidx.lifecycle.viewmodel.compose.viewModel(),
){
//Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {


        //№.1 - Левая область для отображения Бокса - Данные порта)
        Column(
            modifier = Modifier
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ModbusTools_Text_Server_IP)

            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ModbusTools_Text_Server_Port)

            // Кнгопка Connect
            BasicFilledButtonGiraffe(
                modifier = Modifier,
                text = "Connect",
                onClick = {viewModel.connect()},
            ) {}

            // Кнгопка Disconnect
            BasicFilledButtonGiraffe(
                modifier = Modifier,
                text = "Disconnect",
                onClick = {viewModel.disconnect()},
            ) {}

        }


//№.2 - Правая область для настройки порта
        Column(
            modifier = Modifier
                .fillMaxSize().weight(3f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {

        //Ввод. Server_IP
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "192.168.33.10",
                    value = viewModel.serverIp,
                    onValueChange = {viewModel.serverIp = it},
                    modifier = Modifier,

                )
            }
            //Ввод. Server_Port
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "502",
                    value = viewModel.serverPort,
                    onValueChange = {viewModel.serverPort = it},
                    modifier = Modifier,

                )
            }

            // Отображение текущих параметров датчика усилия
            Box(modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 0.dp)
                .fillMaxSize()) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier.fillMaxSize(),
                    valueParameterId = "Status:${viewModel.connectionStatus}"
                )
            }

        }



    }
}



@Preview
@Composable
fun RegisterPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        Register(
        )
    }
}