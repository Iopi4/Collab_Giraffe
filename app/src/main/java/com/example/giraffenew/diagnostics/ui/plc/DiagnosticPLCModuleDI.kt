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
import com.example.giraffenew.core.ui_kit.composable.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicSettingsName
import com.example.giraffenew.diagnostics.ui.StateDIDO
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

        //№.1 - Левая область для отображения назначения дискретных входов
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
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Module_DI_0)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Module_DI_1)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Module_DI_2)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DI_DO_Reserve)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DI_DO_Reserve)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Module_DI_13)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Module_DI_14)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Module_DI_15)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Module_DI_16)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Module_DI_17)
        }

//№.2 - Правая область для отображения состояния дискретных входов
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
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.Diagnostic_Text_State_DI))
// Отображение состояния Питание обогрева в тех.помещ. DI_0
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI0,
                isDIDOStatus = 2
            )
// Отображение состояния Питание тормозов двигателей. DI_1
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI1,
                isDIDOStatus = 2
            )
// Отображение состояния Питание назависим. вентиляции. DI_2
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI2,
                isDIDOStatus = 2
            )
// Резерв
            BasicSettingsName(modifier = Modifier.padding(5.dp)
                , text = stringResource(R.string.Diagnostic_Text_PLC_Module_DI3_DI7))
// Резерв
            BasicSettingsName(modifier = Modifier.padding(5.dp),
                text = stringResource(R.string.Diagnostic_Text_PLC_Module_DI10_DI12))
// Отображение состояния Люк ОПУ. DI_13
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_Module_DI13,
                isDIDOStatus = 2
            )
// Отображение состояния 0-е полож. джойстика подъема. DI_14
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_Module_DI14,
                isDIDOStatus = 2
            )
// Отображение состояния 0-е полож. джойстика поворота. DI_15
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_Module_DI15,
                isDIDOStatus = 2
            )
// Отображение состояния 0-е полож. джойстика тележки. DI_16
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_Module_DI16,
                isDIDOStatus = 2
            )
// Отображение состояния 0-е полож. джойстика хода. DI_17
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_Module_DI17,
                isDIDOStatus = 2
            )




        }


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