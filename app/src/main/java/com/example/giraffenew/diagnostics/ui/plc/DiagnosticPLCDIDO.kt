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
import com.example.giraffenew.diagnostics.state.StateDIDO
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun DiagnosticPLCDIDO(
    // Дискретные входы ПЛК
    plcDI000PhaseControlRelay: Int,
    plcDI001LineContactor: Int,
    plcDI002MontageMode: Int,
    plcDI003OGPLimSw: Int,
    plcDI004HoistBrakeStatus: Int,
    plcDI005HoistBrakeSensor: Int,
    plcDI006HoistLowSpdButton: Int,
    plcDI007SlewBrake: Int,
    // Дискретные выходы ПЛК
    plcDQ000LineContactorEn: Int,
    plcDQ001HoistPWREn: Int,
    plcDQ002SlewPWREn: Int,
    plcDQ003TrolleyPWREn: Int,
    plcDQ004CranePWREn: Int,
    plcDQ005HoistVentilation: Int,
    plcDQ006GlobalReady: Int,
    plcDQ007HoistOverloadStop: Int,
) {

    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
//№.1 - Левая область для отображения назначения дискретных входов и выходов ПЛК
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
               text = stringResource(R.string.Diagnostic_Text_PLC_DI_DO))

//Состояние дискретных входных сигналов от СУ
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DI_0)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DI_1)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DI_2)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DI_3)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DI_4)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DI_5)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DI_6)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DI_7)
            //Spacer(modifier = Modifier.size(30.dp))
//Состояние дискретных выходных сигналов от СУ
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DO_0)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DO_1)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DO_2)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DO_3)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DO_4)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DO_5)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DO_6)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_PLC_DO_7)
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
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.Diagnostic_Text_State_DIDO))

// Отображение состояния реле контроля фаз. DI_0
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI0,
                isDIDOStatus = plcDI000PhaseControlRelay
            )
// Отображение состояния Линейный контактор. DI_1
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI1,
                isDIDOStatus = plcDI001LineContactor
            )
// Отображение состояния Монтажный режим. DI_2
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI2,
                isDIDOStatus = plcDI002MontageMode
            )
// Отображение состояния Датчик критического момента. DI_3
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI3,
                isDIDOStatus = plcDI003OGPLimSw
            )
// Отображение состояния Состояние тормоза подъема. DI_4
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI4,
                isDIDOStatus = plcDI004HoistBrakeStatus
            )
// Отображение состояния Износ тормоза подъема. DI_5
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI5,
                isDIDOStatus = plcDI005HoistBrakeSensor
            )
// Отображение состояния Замедл. скорости подъема. DI_6
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI6,
                isDIDOStatus = plcDI006HoistLowSpdButton
            )
// Отображение состояния Руч. налож. тормоза поворота. DI_7
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI7,
                isDIDOStatus = plcDI007SlewBrake
            )



// Отображение состояния Разреш. вкл. лин. контакт. DO_0
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DO0,
                isDIDOStatus = plcDQ000LineContactorEn
            )
// Отображение состояния Разреш. работа подъема. DO_1
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DO1,
                isDIDOStatus = plcDQ001HoistPWREn
            )
// Отображение состояния Разреш. работа поворота. DO_2
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DO2,
                isDIDOStatus = plcDQ002SlewPWREn
            )
// Отображение состояния Разреш. работа тележки. DO_3
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DO3,
                isDIDOStatus = plcDQ003TrolleyPWREn
            )
// Отображение состояния Разреш. работа хода крана. DO_4
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DO4,
                isDIDOStatus = plcDQ004CranePWREn
            )
// Отображение состояния Вкл. вентилятор подъема. DO_5
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DO5,
                isDIDOStatus = plcDQ005HoistVentilation
            )
// Отображение состояния Ошибка готовности. DO_6
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DO6,
                isDIDOStatus = plcDQ006GlobalReady
            )
// Отображение состояния Авария / Перегрузка. DO_7
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DO7,
                isDIDOStatus = plcDQ007HoistOverloadStop
            )






        }

    }
}


@Preview
@Composable
fun DiagnosticPLCDIDOPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {

        DiagnosticPLCDIDO(
            plcDI000PhaseControlRelay = 1,
            plcDI001LineContactor = 1,
            plcDI002MontageMode = 1,
            plcDI003OGPLimSw = 1,
            plcDI004HoistBrakeStatus = 1,
            plcDI005HoistBrakeSensor = 1,
            plcDI006HoistLowSpdButton = 1,
            plcDI007SlewBrake = 1,
            plcDQ000LineContactorEn = 1,
            plcDQ001HoistPWREn = 1,
            plcDQ002SlewPWREn = 1,
            plcDQ003TrolleyPWREn = 1,
            plcDQ004CranePWREn = 1,
            plcDQ005HoistVentilation = 1,
            plcDQ006GlobalReady = 1,
            plcDQ007HoistOverloadStop = 1,
        )
    }
}