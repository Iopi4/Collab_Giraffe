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
fun DiagnosticPLCDIDO(

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
                isDIDOStatus = 2)
// Отображение состояния Линейный контактор. DI_1
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI1,
                isDIDOStatus = 2)
// Отображение состояния Монтажный режим. DI_2
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI2,
                isDIDOStatus = 2)
// Отображение состояния Датчик критического момента. DI_3
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI3,
                isDIDOStatus = 2)
// Отображение состояния Состояние тормоза подъема. DI_4
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI4,
                isDIDOStatus = 2)
// Отображение состояния Износ тормоза подъема. DI_5
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI5,
                isDIDOStatus = 2)
// Отображение состояния Замедл. скорости подъема. DI_6
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI6,
                isDIDOStatus = 2)
// Отображение состояния Руч. налож. тормоза поворота. DI_7
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DI7,
                isDIDOStatus = 2)



// Отображение состояния Разреш. вкл. лин. контакт. DO_0
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DO0,
                isDIDOStatus = 2)
// Отображение состояния Разреш. работа подъема. DO_1
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DO1,
                isDIDOStatus = 2)
// Отображение состояния Разреш. работа поворота. DO_2
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DO2,
                isDIDOStatus = 2)
// Отображение состояния Разреш. работа тележки. DO_3
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DO3,
                isDIDOStatus = 2)
// Отображение состояния Разреш. работа хода крана. DO_4
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DO4,
                isDIDOStatus = 2)
// Отображение состояния Вкл. вентилятор подъема. DO_5
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DO5,
                isDIDOStatus = 2)
// Отображение состояния Ошибка готовности. DO_6
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DO6,
                isDIDOStatus = 2)
// Отображение состояния Авария / Перегрузка. DO_7
            StateDIDO(
                isDIDONumber = R.string.Diagnostic_Text_PLC_DO7,
                isDIDOStatus = 2)






        }

    }
}


@Preview
@Composable
fun DiagnosticPLCDIDOPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {

        DiagnosticPLCDIDO(
        )
    }
}