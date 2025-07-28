package com.example.giraffenew.diagnostics.ui.hoist

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
import com.example.giraffenew.diagnostics.ui.StateDIDO
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun DiagnosticHoistDIDO (){

    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {

//№.1 - Левая область для отображения параметров привода)
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
                text = stringResource(R.string.Diagnostic_Text_Drive_DI_DO))
//Состояние дискретных входных сигналов в ПЧ
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_Hoist_DI_1)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_Hoist_DI_2)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_DI_3)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_DI_4)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_DI_5)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_24_OP)
            //Состояние дискретных выходных сигналов от ПЧ
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_TA_TC)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_DO_1)
        }

//№.2 - Правая область для отображения состояния параметров привода
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
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.Diagnostic_Text_State_DIDO))
// Отображение состояния. DI_0...DI_5
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_Drive_DI1,
                isDIDOStatus = 1
            )
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_Drive_DI2,
                isDIDOStatus = 1
            )
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_Drive_DI3,
                isDIDOStatus = 1
            )
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_Drive_DI4,
                isDIDOStatus = 1
            )
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_Drive_DI5,
                isDIDOStatus = 1
            )
// Отображение состояния. +24V
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_Drive_24OP,
                isDIDOStatus = 1
            )
// Отображение состояния выхода TA_TC (NO) и DO1
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_Drive_TATC,
                isDIDOStatus = 1
            )
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_Drive_DO1,
                isDIDOStatus = 0
            )
        }
    }
}

@Preview
@Composable
fun DiagnosticHoistDIDOPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        DiagnosticHoistDIDO(
        )
    }
}