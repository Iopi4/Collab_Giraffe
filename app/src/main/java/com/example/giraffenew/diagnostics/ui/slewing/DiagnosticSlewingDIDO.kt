package com.example.giraffenew.diagnostics.ui.slewing

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
fun DiagnosticSlewingDIDO (
    hoistIL1R: Int,
    hoistOL1R: Int,
){
    val inDI1 = when (hoistIL1R) {
        1 -> 1
        else -> 0 }
    val inDI2 = when (hoistIL1R) {
        2 -> 1
        else -> 0 }
    val inDI3 = when (hoistIL1R) {
        5 -> 1
        6 -> 1
        else -> 0 }
    val inDI4 = when (hoistIL1R) {
        13 -> 1
        14 -> 1
        else -> 0 }
    val inDI5 = when (hoistIL1R) {
        29 -> 1
        30 -> 1
        else -> 0 }

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
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_Slewing_DI_1)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_Slewing_DI_2)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_DI_3)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_DI_4)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_Drive_DI_5)
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
                isDIDOStatus = inDI1
            )
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_Drive_DI2,
                isDIDOStatus = inDI2
            )
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_Drive_DI3,
                isDIDOStatus = inDI3
            )
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_Drive_DI4,
                isDIDOStatus = inDI4
            )
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_Drive_DI5,
                isDIDOStatus = inDI5
            )
// Отображение состояния выхода TA_TC (NO) и DO1
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_Drive_TATC,
                isDIDOStatus = hoistOL1R
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
fun DiagnosticSlewingDIDOPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        DiagnosticSlewingDIDO(
            hoistIL1R = 1,
            hoistOL1R = 1,
        )
    }
}