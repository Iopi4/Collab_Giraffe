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
fun DiagnosticPLCModuleDI(
    plcDI100MicroclimatInput: Int,
    plcDI101BrakePowerControl: Int,
    plcDI102VentilationPowerControl: Int,
    plcDI107ACSSPowerControl: Int,
    plcDI110ACSSFault: Int,
    plcDI111ACSSReady: Int,
    plcDI112Zapasovka: Int,
    plcDI113Luk: Int,
    plcDI114HoistZeroPos: Int,
    plcDI115SlewZeroPos: Int,
    plcDI116TrolleyZeroPos: Int,
    plcDI117CraneZeroPos: Int,
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
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Module_DI_7)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Module_DI_10)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Module_DI_11)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_Module_DI_12)
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
                isDIDOStatus = plcDI100MicroclimatInput
            )
// Отображение состояния Питание тормозов двигателей. DI_1
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI1,
                isDIDOStatus = plcDI101BrakePowerControl
            )
// Отображение состояния Питание назависим. вентиляции. DI_2
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI2,
                isDIDOStatus = plcDI102VentilationPowerControl
            )
// Резерв
            BasicSettingsName(modifier = Modifier.padding(5.dp)
                , text = stringResource(R.string.Diagnostic_Text_PLC_Module_DI3_DI6))
// Отображение состояния Питание АЦСС в норме. DI_7
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI7,
                isDIDOStatus = plcDI107ACSSPowerControl
            )
// Отображение состояния. АЦСС в ошибке. DI_10
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_Module_DI10,
                isDIDOStatus = plcDI110ACSSFault
            )
// Отображение состояния. АЦСС ошибок нет. DI_11
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_Module_DI11,
                isDIDOStatus = plcDI111ACSSReady
            )
// Отображение состояния. Питание АЦСС в норме. DI_12
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_Module_DI12,
                isDIDOStatus = plcDI112Zapasovka
            )
// Отображение состояния. Люк ОПУ. DI_13
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_Module_DI13,
                isDIDOStatus = plcDI113Luk
            )
// Отображение состояния. 0-е полож. джойстика подъема. DI_14
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_Module_DI14,
                isDIDOStatus = plcDI114HoistZeroPos
            )
// Отображение состояния. 0-е полож. джойстика поворота. DI_15
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_Module_DI15,
                isDIDOStatus = plcDI115SlewZeroPos
            )
// Отображение состояния. 0-е полож. джойстика тележки. DI_16
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_Module_DI16,
                isDIDOStatus = plcDI116TrolleyZeroPos
            )
// Отображение состояния. 0-е полож. джойстика хода. DI_17
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_Module_DI17,
                isDIDOStatus = plcDI117CraneZeroPos
            )
        }
    }
}

@Preview
@Composable
fun DiagnosticPLCDIPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {

        DiagnosticPLCModuleDI(
            plcDI100MicroclimatInput = 1,
            plcDI101BrakePowerControl = 1,
            plcDI102VentilationPowerControl = 1,
            plcDI107ACSSPowerControl = 1,
            plcDI110ACSSFault = 1,
            plcDI111ACSSReady = 1,
            plcDI112Zapasovka = 1,
            plcDI113Luk = 1,
            plcDI114HoistZeroPos = 1,
            plcDI115SlewZeroPos = 1,
            plcDI116TrolleyZeroPos = 1,
            plcDI117CraneZeroPos = 1,
        )
    }
}