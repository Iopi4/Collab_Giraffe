package com.example.giraffenew.diagnostics.ui

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
import com.example.giraffenew.core.ui_kit.composable.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicSettingsName
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun DiagnosticPLCModuleDI(

) {
//Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {

        //№.1 - Левая область для отображения параметров настройки полигона)
        Column(
            modifier = Modifier
                .fillMaxSize().weight(3f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
            // Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.Diagnostic_Text_PLC_Module_DI))
            //Состояние дискретных входных сигналов от СУ
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Status_DI_0)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Status_DI_1)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Status_DI_2)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Status_DI_3_DI_7)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Status_DI_10_DI_12)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Status_DI_13)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Status_DI_14)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Status_DI_15)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Status_DI_16)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Status_DI_17)
        }

//№.2 - Правая область для отображения и ввода данных настройки полигона
        Column(
            modifier = Modifier
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {}


    }
}

@Preview
@Composable
fun DiagnosticPLCDIPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {

        DiagnosticPLCModuleDI(
        )
    }
}