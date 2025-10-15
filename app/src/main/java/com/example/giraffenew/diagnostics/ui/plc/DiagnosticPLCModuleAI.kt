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
import com.example.giraffenew.diagnostics.state.StateValueAI
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun DiagnosticPLCModuleAI(
    plcAI0AnemometerSensor: Int,
    plcAI1LoadCellSensor: Int
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
                text = stringResource(R.string.Diagnostic_Text_PLC_Module_AI))
//Состояние аналоговых входных сигналов от датчиков
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Status_AI_1)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Status_AI_2)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Status_AI_3)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Status_AI_4)
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
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.Diagnostic_Text_State_AI))

// Отображение статус датчика усилия
            StateValueAI(
                isValueParameter = format(plcAI1LoadCellSensor),
                isValueNumber = R.string.Diagnostic_Text_PLC_Status_AI1
            )

// Отображение статус датчика ветра
            StateValueAI(
                isValueParameter = format(plcAI0AnemometerSensor),
                isValueNumber = R.string.Diagnostic_Text_PLC_Status_AI2
            )
        }
    }
}

private fun format(value: Int): String {
    //return String.format("%.1f", value).padStart(5, '0')
    return String.format("%10d", value)
}

@Preview
@Composable
fun DiagnosticPLCAIPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        DiagnosticPLCModuleAI(
            plcAI0AnemometerSensor = 1,
            plcAI1LoadCellSensor = 1,
        )
    }
}