package com.example.giraffenew.modbus_tools.ui

import ModbusViewModel
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicTextFilledGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun Register(
    modifier: Modifier = Modifier,
    viewModel: ModbusViewModel = viewModel(),
) {
//Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier.fillMaxSize()
    ) {

        //№.1 - Левая область для отображения Бокса - Данные регистров)
        Column(
            modifier = Modifier
                .fillMaxSize()
                    .weight(2f)
                    .padding(5.dp)
                    .fillMaxSize()
                    .border(
                    width = 3.dp, color = Orange, shape = RoundedCornerShape(5.dp)
                ), verticalArrangement = Arrangement.Top
        ) {
            BasicNameSettingParameterGiraffe(
                    settingParameterId = R.string.ModbusTools_Text_Register_Address
            )

            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ModbusTools_Text_Register_Count
            )
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ModbusTools_Text_Registers_Value
            )
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ModbusTools_Text_Value_To_Write
            )

            // Кнгопка Read
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.ModbusTools_Button_Read,
                onClick = { viewModel.startReadingRegisters() },
            ) {}

            // Кнгопка Write
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.ModbusTools_Button_Write,
                onClick = { viewModel.startWriteRegister() },
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
                val startAddress = viewModel.startAddress.collectAsState()
                BasicTextFilledGiraffe(
                    placeholder = "0",
                    value = startAddress.value.toString(),
                    onValueChange = { viewModel.updateStartAddress(it.toIntOrNull() ?: 0) },
                    modifier = Modifier,
                )
            }
            //Ввод. Registers Count
            Box(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 0.dp)
            ) {
                val registerCount = viewModel.registerCount.collectAsState()
                BasicTextFilledGiraffe(
                    placeholder = "0",
                    value = registerCount.value.toString(),
                    onValueChange = { viewModel.updateRegisterCount(it.toIntOrNull() ?: 0) },
                    modifier = Modifier,
                )
            }

            // Вывод. Данные регистров. Через
            Box(
                modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 0.dp)
            ) {
            val registerValue = viewModel.registerValues.collectAsState()

                BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier.fillMaxSize(),
                        valueParameterId = registerValue.value.toString(),
                )
        }
            //Ввод. запись регистра
            Box(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 0.dp)
            ) {
                val startAddress = viewModel.writeValue.collectAsState()
                BasicTextFilledGiraffe(
                    placeholder = "0",
                    value = startAddress.value.toString(),
                    onValueChange = { viewModel.updateWriteValue(it.toIntOrNull() ?: 0) },
                    modifier = Modifier,
                )
            }
            /*Box(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 0.dp)
                    .fillMaxSize()
            ) {
                var sensor = viewModel.
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier.fillMaxSize(),
                    valueParameterId = "Read Value:${viewModel.registerValue}"
                )
            }*/

        }
    }
}


@Preview
@Composable
fun RegisterPreview(){
    GIRAFFEnewTheme(dynamicColor = false) {
        Register(
        )
    }
}