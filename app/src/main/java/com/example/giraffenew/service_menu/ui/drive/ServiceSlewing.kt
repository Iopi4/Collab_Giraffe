package com.example.giraffenew.service_menu.ui.drive

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandThousandButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServiceSlewing(
    setValueSlewACC: String,
    onModelChangeSlewACC: (String) -> Unit,
    onSetSlewACC: () -> Unit,
    hmiSlewACC : Float,

    setValueSlewDEC: String,
    onModelChangeSlewDEC: (String) -> Unit,
    onSetSlewDEC: () -> Unit,
    hmiSlewDEC : Float,

    setValueSlewSpdRef1: String,
    onModelChangeSlewSpdRef1: (String) -> Unit,
    onSetSlewSpdRef1: () -> Unit,
    hmiSpdRef1Slew : Int,

    setValueSlewSpdRef2: String,
    onModelChangeSlewSpdRef2: (String) -> Unit,
    onSetSlewSpdRef2: () -> Unit,
    hmiSpdRef2Slew  : Int,

    setValueSlewSpdRef3: String,
    onModelChangeSlewSpdRef3: (String) -> Unit,
    onSetSlewSpdRef3: () -> Unit,
    hmiSpdRef3Slew  : Int,

    setValueSlewSpdRef4: String,
    onModelChangeSlewSpdRef4: (String) -> Unit,
    onSetSlewSpdRef4: () -> Unit,
    hmiSpdRef4Slew  : Int,

    setValueSlewSpdStopButton: String,
    onModelChangeSlewSpdStopButton: (String) -> Unit,
    onSetSlewSpdStopButton: () -> Unit,

    setValueSlewDECRevers: String,
    updateSlewDECRevers: (String) -> Unit,
    onSetSlewDECRevers: () -> Unit,

    setValueSlewDelayTime: String,
    updateSlewDelayTime: (String) -> Unit,
    onSetSlewDelayTime: () -> Unit,

    setValueSlewBrakeCloseFrq: String,
    updateSlewBrakeCloseFrq: (String) -> Unit,
    onSetSlewBrakeCloseFrq: () -> Unit,

    errorMessageSlew: String?,
    logMessageSlew: String?,

    hmiSlewDecRevers        : Float,
    hmiSlewDelayTimeBrake   : Float,
    hmiSlewBrakeCloseFrq    : Float,

    ){
//Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
//№.1 - Левая область для отображения параметров скорости и ускорений
        Column(
            modifier = Modifier
                .fillMaxSize().weight(3f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
            // Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.ServiceMenu_Text_Slewing_Parameters))
//Отображения текста рабочих параметров привода подъема
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_1)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_2)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_3)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_4)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Stoping_Speed)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Acc)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Dec)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Dec_Revers)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Delay_Time)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Brake_Close_Frq)
        }

//№.2 - Правая область для отображения и ввода параметров скорости и ускорений
        Column(
            modifier = Modifier
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
// Отступ  и Статус записи данных настройки поворота
            Row(modifier = Modifier.fillMaxWidth()) {
                BasicSettingsName(modifier = Modifier.weight(1f), text = stringResource(R.string.spacer))

                Box (modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                ) {
                    errorMessageSlew?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 0.dp))}
                    logMessageSlew?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }

//Ввод. Скорость 1. Об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef1Slew),
                value = setValueSlewSpdRef1,
                valueMin = 0,
                valueMax = 500,
                onValueChange = onModelChangeSlewSpdRef1,
                onClick = onSetSlewSpdRef1
            )
//Ввод. Скорость 2.Об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef2Slew),
                value = setValueSlewSpdRef2,
                valueMin = 0,
                valueMax = 1000,
                onValueChange = onModelChangeSlewSpdRef2,
                onClick = onSetSlewSpdRef2
            )
//Ввод. Скорость 3.Об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef3Slew),
                value = setValueSlewSpdRef3,
                valueMin = 0,
                valueMax = 1500,
                onValueChange = onModelChangeSlewSpdRef3,
                onClick = onSetSlewSpdRef3
            )
//Ввод. Скорость 4.Об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef4Slew),
                value = setValueSlewSpdRef4,
                valueMin = 0,
                valueMax = 3000,
                onValueChange = onModelChangeSlewSpdRef4,
                onClick = onSetSlewSpdRef4
            )
//Ввод. Остановка от кнопки, об/мин
            BasicInputRowandButton(
                placeholder = "000",
                value = setValueSlewSpdStopButton,
                valueMin = 0,
                valueMax = 200,
                onValueChange = onModelChangeSlewSpdStopButton,
                onClick = onSetSlewSpdStopButton,
            )
//Ввод. Разгон. Секунды
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiSlewACC),
                value = setValueSlewACC,
                onValueChange = onModelChangeSlewACC,
                onClick = onSetSlewACC,
                isDecimal = true,
            )
//Ввод. Торможение. Секунды
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiSlewDEC),
                value = setValueSlewDEC,
                onValueChange = onModelChangeSlewDEC,
                onClick = onSetSlewDEC,
                isDecimal = true,
            )
//Ввод. Торможение противоходом. Секунды
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiSlewDecRevers),
                value = setValueSlewDECRevers,
                onValueChange = updateSlewDECRevers,
                onClick = onSetSlewDECRevers,
                isDecimal = true,
            )
//Ввод. Задержка наложения тормоза. Секунды
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiSlewDelayTimeBrake),
                value = setValueSlewDelayTime,
                onValueChange = updateSlewDelayTime,
                onClick = onSetSlewDelayTime,
                isDecimal = true,
            )
//Ввод. Частота удержания. Секунды
            BasicInputRowandThousandButton(
                placeholder = formatHundred(hmiSlewBrakeCloseFrq),
                value = setValueSlewBrakeCloseFrq,
                onValueChange = updateSlewBrakeCloseFrq,
                onClick = onSetSlewBrakeCloseFrq,
                isDecimal = true,
            )
        }
    }
}

private fun formatDecimal(value: Float): String {
    return String.format("%04.1f", value).replace(',', '.')
}

private fun formatHundred(value: Float): String {
    return String.format("%2.2f", value).replace(',', '.')
}

private fun format(value: Int): String {
    return String.format("%04d", value)
}

@Preview
@Composable
fun ServiceSlewingPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ServiceSlewing(
            setValueSlewACC = "15",
            onModelChangeSlewACC = { },
            onSetSlewACC = { },

            setValueSlewDEC = "20",
            onModelChangeSlewDEC = { },
            onSetSlewDEC = { },

            setValueSlewSpdRef1 = "150",
            onModelChangeSlewSpdRef1 = { },
            onSetSlewSpdRef1 = { },

            setValueSlewSpdRef2 = "700",
            onModelChangeSlewSpdRef2 = { },
            onSetSlewSpdRef2 = { },

            setValueSlewSpdRef3 = "1000",
            onModelChangeSlewSpdRef3 = { },
            onSetSlewSpdRef3 = { },

            setValueSlewSpdRef4 = "1500",
            onModelChangeSlewSpdRef4 = { },
            onSetSlewSpdRef4 = { },

            setValueSlewSpdStopButton = "100",
            onModelChangeSlewSpdStopButton = { },
            onSetSlewSpdStopButton = { },

            setValueSlewDECRevers = "15",
            updateSlewDECRevers = { },
            onSetSlewDECRevers = { },

            setValueSlewDelayTime = "25",
            updateSlewDelayTime = { },
            onSetSlewDelayTime = { },

            setValueSlewBrakeCloseFrq = "0.01",
            updateSlewBrakeCloseFrq = { },
            onSetSlewBrakeCloseFrq = { },

            errorMessageSlew = null,
            logMessageSlew = null,

            hmiSlewACC = 15f,
            hmiSlewDEC = 15f,
            hmiSpdRef1Slew = 200,
            hmiSpdRef2Slew = 400,
            hmiSpdRef3Slew = 750,
            hmiSpdRef4Slew = 1500,

            hmiSlewDecRevers = 15f,
            hmiSlewDelayTimeBrake = 100f,
            hmiSlewBrakeCloseFrq = 1f,
        )
    }
}