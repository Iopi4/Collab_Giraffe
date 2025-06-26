package com.example.giraffenew.modbus_tools.ui

import ModbusViewModel
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun CustomButtonGiraffe(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    val myBoxButtonModifier = Modifier
        .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()

    Box(myBoxButtonModifier) {
        Button(
            modifier = modifier
                .size(width = 350.dp, height = 30.dp),
            onClick = onClick,
            contentPadding = PaddingValues(2.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.Burgundy_violet),
                contentColor = Color.White
            ),
        ) {
            Text(text = text, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

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
                settingParameterId = R.string.ModbusTools_Text_Value_To_Write
            )
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ModbusTools_Text_Registers_Value
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

            // Кнопка Start Background Service
            CustomButtonGiraffe(
                modifier = Modifier,
                text = "Start Background",
                onClick = { viewModel.startBackgroundService() }
            )

            // Кнопка Stop Background Service
            CustomButtonGiraffe(
                modifier = Modifier,
                text = "Stop Background",
                onClick = { viewModel.stopBackgroundService() }
            )

            // Кнопка Start Background Service Write Register
            CustomButtonGiraffe(
                modifier = Modifier,
                text = "Start Background-Wr",
                onClick = { viewModel.startBackgroundServiceWrite() }
            )

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
            //Ввод. Значение записываемое в регистр
            Box(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 0.dp)
            ) {
                val writeValue = viewModel.writeValue.collectAsState()
                BasicTextFilledGiraffe(
                    placeholder = "0",
                    value = writeValue.value.toString(),
                    onValueChange = { viewModel.updateWriteValue(it) },
                    modifier = Modifier,
                )
            }

            // Вывод. Данные регистров.
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

            // Background Service Status
            Box(
                modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 0.dp)
            ) {
                val backgroundServiceStatus = viewModel.backgroundServiceStatus.collectAsState()

                val statusText = when (backgroundServiceStatus.value) {
                    is ModbusViewModel.BackgroundServiceStatus.Running -> "Background Service: Running"
                    is ModbusViewModel.BackgroundServiceStatus.Stopped -> "Background Service: Stopped"
                    is ModbusViewModel.BackgroundServiceStatus.Error -> {
                        val error = (backgroundServiceStatus.value as ModbusViewModel.BackgroundServiceStatus.Error).message
                        "Background Service: Error - $error"
                    }
                }

                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier.fillMaxSize(),
                    valueParameterId = statusText,
                )
            }

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
