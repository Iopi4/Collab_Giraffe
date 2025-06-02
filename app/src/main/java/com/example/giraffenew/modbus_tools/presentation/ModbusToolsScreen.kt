package com.example.giraffenew.modbus_tools.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.giraffenew.modbus_tools.ui.Register
import com.example.giraffenew.modbus_tools.ui.ServerIP
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun ModbusToolsScreen() {

//Создаем поле в котором разделим экраны на горизонтальные области для отображения наших UI
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier = Modifier.weight(1f)){
            Register()
        }
        Box(modifier = Modifier.weight(1f)){
            ServerIP()
        }
    }
}

@Preview
@Composable
fun ModbusToolsScreenPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        ModbusToolsScreen(
        )
    }
}