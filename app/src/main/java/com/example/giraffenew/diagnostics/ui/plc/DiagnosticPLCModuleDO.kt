package com.example.giraffenew.diagnostics.ui.plc

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
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.diagnostics.ui.StateDIDO
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun DiagnosticPLCModuleDO(

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
                text = stringResource(R.string.Diagnostic_Text_PLC_Module_DO))
//Состояние дискретных выходных сигналов от СУ
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Module_DO_0)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DI_DO_Reserve)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DI_DO_Reserve)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Module_DO_17)


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
        ) {
// Заголовок
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.Diagnostic_Text_State_DO))
// Отображение состояния Звуковая сигнализация. DO_0
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DO0,
                isDIDOStatus = 2
            )
// Резерв
            BasicSettingsName(modifier = Modifier.padding(5.dp)
                , text = stringResource(R.string.Diagnostic_Text_PLC_Module_DO1_DO7))
// Резерв
            BasicSettingsName(modifier = Modifier.padding(5.dp),
                text = stringResource(R.string.Diagnostic_Text_PLC_Module_DO10_DO16))
// Отображение состояния Сброс ошибки АЦСС. DO_17
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_Module_DO17,
                isDIDOStatus = 2
            )



        }


    }
}

@Preview
@Composable
fun DiagnosticPLCDOPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        DiagnosticPLCModuleDO(
        )
    }
}