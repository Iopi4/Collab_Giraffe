package com.example.giraffenew.service_menu.ui.drive

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonOnOffStatus
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServiceHoist(
    setValueHoistACC: String,
    onModelChangeHoistACC: (String) -> Unit,
    onSetHoistACC: () -> Unit,
    hmiHoistACC: Float,

    setValueHoistDEC: String,
    onModelChangeHoistDEC: (String) -> Unit,
    onSetHoistDEC: () -> Unit,
    hmiHoistDEC: Float,

    hmiSpdRefButtonHoist:    Int,
    hmiSpdRef1Hoist:    Int,
    hmiSpdRef2Hoist:    Int,
    hmiSpdRef3Hoist:    Int,
    hmiSpdRef4Hoist:    Int,
    hmiSpdRefMAXHoist:    Int,
    hmiSpdRefNomHoist:    Int,

    hmiSpdRef1HoistMontage:    Int,
    hmiSpdRef2HoistMontage:    Int,
    hmiHoistACCMontage:    Float,
    hmiHoistDECMontage:    Float,


    setValueHoistSpdRef1: String,
    onModelChangeHoistSpdRef1: (String) -> Unit,
    onSetHoistSpdRef1: () -> Unit,

    setValueHoistSpdRef2: String,
    onModelChangeHoistSpdRef2: (String) -> Unit,
    onSetHoistSpdRef2: () -> Unit,

    setValueHoistSpdRef3: String,
    onModelChangeHoistSpdRef3: (String) -> Unit,
    onSetHoistSpdRef3: () -> Unit,

    setValueHoistSpdRef4: String,
    onModelChangeHoistSpdRef4: (String) -> Unit,
    onSetHoistSpdRef4: () -> Unit,

    setValueHoistSpdRefButton: String,
    onModelChangeHoistSpdRefButton: (String) -> Unit,
    onSetHoistSpdRefButton: () -> Unit,

    setValueHoistSpdNom: String,
    updateHoistSpdRefNom: (String) -> Unit,
    onSetHoistSpdNom: () -> Unit,

    setValueHoistSpdRefMax: String,
    onModelChangeHoistSpdRefMax: (String) -> Unit,
    onSetHoistSpdRefMax: () -> Unit,

    setValueHoistMontageACC: String,
    onModelChangeHoistMontageACC: (String) -> Unit,
    onSetHoistMontageACC: () -> Unit,

    setValueHoistMontageDEC: String,
    onModelChangeHoistMontageDEC: (String) -> Unit,
    onSetHoistMontageDEC: () -> Unit,

    setValueHoistMontageSpdRef1: String,
    onModelChangeHoistMontageSpdRef1: (String) -> Unit,
    onSetHoistMontageSpdRef1: () -> Unit,

    setValueHoistMontageSpdRef2: String,
    onModelChangeHoistMontageSpdRef2: (String) -> Unit,
    onSetHoistMontageSpdRef2: () -> Unit,

    errorMessageHoist: String?,
    logMessageHoist: String?,

    onSetHoistBrakeControlEn: () -> Unit,
    hmiHoistBrakeControlStat: Int,

    setValueHoistBrakeControlTimeout: String,
    updateHoistBrakeControlTimeout: (String) -> Unit,
    onSetHoistBrakeControlTimeout: () -> Unit,
    hmiHoistBrakeControlTimeout: Float,

    onSetFltRst: () -> Unit,

){
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
//№.1 - Левая область для отображения параметров скорости и ускорений
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f)
                .padding(5.dp)
                .fillMaxSize()
                .border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
// Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.ServiceMenu_Text_Hoist_Parameters))
//Отображения текста рабочих параметров привода подъема
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Acc)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Dec)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_1)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_2)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_3)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_4)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_Nom)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Max_Speed_SS)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Hoist_SpdRefButton)

// Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.ServiceMenu_Text_Hoist_Montage))
//Отображения текста параметров привода подъема в режиме монтаж
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Acc)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Dec)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_1)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_2)
// Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.ServiceMenu_Text_Hoist_Brake_Setting))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Hoist_BrakeControl_En)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Hoist_BrakeControl_Timeout)
// Отступ
            Spacer(modifier = Modifier.size(10.dp))
//Сброс ошибки привода
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Drive_Fault_Reset)
        }

//№.2 - Правая область для отображения и ввода параметров скорости и ускорений
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f)
                .padding(5.dp)
                .fillMaxSize()
                .border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
// Отступ  и Статус записи данных настройки подъема
            Row(modifier = Modifier.fillMaxWidth()) {
                BasicSettingsName(modifier = Modifier.weight(1f), text = stringResource(R.string.spacer))

                Box (modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                ) {
                    errorMessageHoist?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp))}
                    logMessageHoist?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }
//Ввод. Разгон. сек
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiHoistACC),
                value = setValueHoistACC,
                onValueChange = onModelChangeHoistACC,
                onClick = onSetHoistACC,
                isDecimal = true,
            )
//Ввод. Торможение. сек
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiHoistDEC),
                value = setValueHoistDEC,
                onValueChange = onModelChangeHoistDEC,
                onClick = onSetHoistDEC,
                isDecimal = true,
            )
//Ввод. Скорость 1. об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef1Hoist),
                value = setValueHoistSpdRef1,
                valueMin = 0,
                valueMax = 120,
                onValueChange = onModelChangeHoistSpdRef1,
                onClick = onSetHoistSpdRef1
            )
//Ввод. Скорость 2. об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef2Hoist),
                value = setValueHoistSpdRef2,
                valueMin = 0,
                valueMax = 400,
                onValueChange = onModelChangeHoistSpdRef2,
                onClick = onSetHoistSpdRef2
            )
//Ввод. Скорость 3. об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef3Hoist),
                value = setValueHoistSpdRef3,
                valueMin = 0,
                valueMax = 1000,
                onValueChange = onModelChangeHoistSpdRef3,
                onClick = onSetHoistSpdRef3
            )
//Ввод. Скорость 4. об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef4Hoist),
                value = setValueHoistSpdRef4,
                valueMin = 0,
                valueMax = 3000,
                onValueChange = onModelChangeHoistSpdRef4,
                onClick = onSetHoistSpdRef4
            )
//Ввод. Номинальная скорость двигателя, об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRefNomHoist),
                value = setValueHoistSpdNom,
                valueMin = 0,
                valueMax = 1500,
                onValueChange = updateHoistSpdRefNom,
                onClick = onSetHoistSpdNom
            )
//Ввод. Сверх.синхр. скорость, об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRefMAXHoist),
                value = setValueHoistSpdRefMax,
                valueMin = 0,
                valueMax = 3000,
                onValueChange = onModelChangeHoistSpdRefMax,
                onClick = onSetHoistSpdRefMax
            )
//Ввод. Медленная скорость при нажатии кнопки, об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRefButtonHoist),
                valueMin = 0,
                valueMax = 120,
                value = setValueHoistSpdRefButton,
                onValueChange = onModelChangeHoistSpdRefButton,
                onClick = onSetHoistSpdRefButton,
            )

            Spacer(modifier = Modifier.size(20.dp))
// Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
//Настройка скоростей и ускорения в монтажном режиме для подъема
//Ввод. Разгон. сек
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiHoistACCMontage),
                value = setValueHoistMontageACC,
                onValueChange = onModelChangeHoistMontageACC,
                onClick = onSetHoistMontageACC,
                isDecimal = true,
            )
//Ввод. Торможение. сек
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiHoistDECMontage),
                value = setValueHoistMontageDEC,
                onValueChange = onModelChangeHoistMontageDEC,
                onClick = onSetHoistMontageDEC,
                isDecimal = true,
            )
//Ввод. Скорость 1. об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef1HoistMontage),
                value = setValueHoistMontageSpdRef1,
                valueMin = 0,
                valueMax = 240,
                onValueChange = onModelChangeHoistMontageSpdRef1,
                onClick = onSetHoistMontageSpdRef1
            )
//Ввод. Скорость 2. об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef2HoistMontage),
                valueMin = 0,
                valueMax = 3000,
                value = setValueHoistMontageSpdRef2,
                onValueChange = onModelChangeHoistMontageSpdRef2,
                onClick = onSetHoistMontageSpdRef2
            )

// Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
//Контроль отслеживаения состояния тормоза 1- отслеживаем обраную связь, 0 -нет [вкл/выкл]
            BasicButtonOnOffStatus(
                onClick = onSetHoistBrakeControlEn,
                isLampStatus = hmiHoistBrakeControlStat,
            )
//Ввод. Время ожидания обратной связи от тормоза
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiHoistBrakeControlTimeout),
                value = setValueHoistBrakeControlTimeout,
                onValueChange = updateHoistBrakeControlTimeout,
                onClick = onSetHoistBrakeControlTimeout,
                isDecimal = true,
            )
            Spacer(modifier = Modifier.size(10.dp))
//Кнопка сброса ошибка привода
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.Button_Turn_Reset,
                onClick = onSetFltRst,
            ) {}
        }
    }
}

private fun formatDecimal(value: Float): String {
    return String.format("%.1f", value).replace(',', '.')
}

private fun format(value: Int): String {
    return String.format("%d", value)
}

@Preview
@Composable
fun ServiceHoistPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ServiceHoist(
            setValueHoistACC = "",
            onModelChangeHoistACC = { },
            onSetHoistACC = { },
            hmiHoistACC = 3f,

            setValueHoistDEC = "",
            onModelChangeHoistDEC = { },
            onSetHoistDEC = { },
            hmiHoistDEC = 3f,

            setValueHoistSpdRef1 = "2",
            onModelChangeHoistSpdRef1 = { },
            onSetHoistSpdRef1 = { },

            setValueHoistSpdRef2 = "2",
            onModelChangeHoistSpdRef2 = { },
            onSetHoistSpdRef2 = { },

            setValueHoistSpdRef3 = "2",
            onModelChangeHoistSpdRef3 = { },
            onSetHoistSpdRef3 = { },

            setValueHoistSpdRef4 = "2",
            onModelChangeHoistSpdRef4 = { },
            onSetHoistSpdRef4 = { },

            setValueHoistSpdRefButton = "2",
            onModelChangeHoistSpdRefButton = { },
            onSetHoistSpdRefButton = { },

            setValueHoistSpdNom = "2",
            updateHoistSpdRefNom = { },
            onSetHoistSpdNom = { },

            setValueHoistSpdRefMax = "2",
            onModelChangeHoistSpdRefMax = { },
            onSetHoistSpdRefMax = { },

            setValueHoistMontageACC = "2",
            onModelChangeHoistMontageACC = { },
            onSetHoistMontageACC = { },

            setValueHoistMontageDEC = "2",
            onModelChangeHoistMontageDEC = { },
            onSetHoistMontageDEC = { },

            setValueHoistMontageSpdRef1 = "2",
            onModelChangeHoistMontageSpdRef1 = { },
            onSetHoistMontageSpdRef1 = { },

            setValueHoistMontageSpdRef2 = "2",
            onModelChangeHoistMontageSpdRef2 = { },
            onSetHoistMontageSpdRef2 = { },

            errorMessageHoist = null,
            logMessageHoist = null,

            hmiSpdRefButtonHoist = 20,
            hmiSpdRef1Hoist = 60,
            hmiSpdRef2Hoist = 240,
            hmiSpdRef3Hoist = 450,
            hmiSpdRef4Hoist = 600,
            hmiSpdRefMAXHoist = 2880,
            hmiSpdRefNomHoist = 980,
            hmiSpdRef1HoistMontage = 120,
            hmiSpdRef2HoistMontage = 980,
            hmiHoistACCMontage = 3f,
            hmiHoistDECMontage = 3f,

            onSetHoistBrakeControlEn = { },
            hmiHoistBrakeControlStat = 1,

            setValueHoistBrakeControlTimeout = "",
            updateHoistBrakeControlTimeout = {},
            onSetHoistBrakeControlTimeout = { },
            hmiHoistBrakeControlTimeout = 1f,

            onSetFltRst = {},
        )
    }
}