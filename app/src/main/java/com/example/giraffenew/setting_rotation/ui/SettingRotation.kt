package com.example.giraffenew.setting_rotation.ui

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
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandThousandButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun SettingRotation(

    setValueSlewSpdStopButton: String,
    onModelChangeSlewSpdStopButton: (String) -> Unit,
    onSetSlewSpdStopButton: () -> Unit,
    hmiSlewSpeedBrakeClose: Int,

    setValueSlewDECRevers: String,
    updateSlewDECRevers: (String) -> Unit,
    onSetSlewDECRevers: () -> Unit,
    hmiSlewDecRevers : Float,

    setValueSlewDelayTime: String,
    updateSlewDelayTime: (String) -> Unit,
    onSetSlewDelayTime: () -> Unit,
    hmiSlewDelayTimeBrake   : Float,

    setValueSlewBrakeCloseFrq: String,
    updateSlewBrakeCloseFrq: (String) -> Unit,
    onSetSlewBrakeCloseFrq: () -> Unit,
    hmiSlewBrakeCloseFrq : Float,

    setValueSlewFrqStartBraking: String,
    updateSlewFrqStartBraking: (String) -> Unit,
    onSetSlewFrqStartBraking: () -> Unit,
    hmiSlewFrqStartBraking : Float,

    setValueSlewDelayStartBraking: String,
    updateSlewDelayStartBraking: (String) -> Unit,
    onSetSlewDelayStartBraking: () -> Unit,
    hmiSlewDelayStartBraking : Float,

    setValueSlewDelayTimeBrakeCurr: String,
    updateSlewDelayTimeBrakeCurr: (String) -> Unit,
    onSetSlewDelayTimeBrakeCurr: () -> Unit,
    hmiSlewDelayTimeBrakeCurr : Float,

    setValueSlewTimeBrakeCurrent: String,
    updateSlewTimeBrakeCurrent: (String) -> Unit,
    onSetSlewTimeBrakeCurrent: () -> Unit,
    hmiSlewTimeBrakeCurrent : Float,

    errorMessageSlew: String?,
    logMessageSlew: String?,

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
                text = stringResource(R.string.SettingRotation_Text_Parameters))
            //Отображения текста рабочих параметров настройкаи блока упралвения поворотом
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingRotation_Text_Stoping_Speed)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingRotation_Text_Dec_Revers)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingRotation_Text_Delay_Time)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingRotation_Text_Brake_Close_Frq)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingRotation_Text_Frq_Start_Braking)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingRotation_Text_Delay_Start_Braking)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingRotation_Text_Delay_Time_Brake_Curr)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingRotation_Text_Time_Brake_Current)
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
                    errorMessageSlew?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp))}
                    logMessageSlew?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }
            Spacer(modifier = Modifier.size(10.dp))
//Ввод. Остановка от кнопки, об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSlewSpeedBrakeClose),
                value = setValueSlewSpdStopButton,
                valueMin = 0,
                valueMax = 100,
                onValueChange = onModelChangeSlewSpdStopButton,
                onClick = onSetSlewSpdStopButton,
            )
            Spacer(modifier = Modifier.size(23.dp))
//Ввод. Торможение противоходом. Секунды
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiSlewDecRevers),
                value = setValueSlewDECRevers,
                onValueChange = updateSlewDECRevers,
                onClick = onSetSlewDECRevers,
                isDecimal = true,
            )
            Spacer(modifier = Modifier.size(20.dp))
//Ввод. Задержка наложения тормоза. Секунды
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiSlewDelayTimeBrake),
                value = setValueSlewDelayTime,
                onValueChange = updateSlewDelayTime,
                onClick = onSetSlewDelayTime,
                isDecimal = true,
            )
            Spacer(modifier = Modifier.size(20.dp))
//Ввод. Частота удержания. Гц
            BasicInputRowandThousandButton(
                placeholder = formatHundred(hmiSlewBrakeCloseFrq),
                value = setValueSlewBrakeCloseFrq,
                onValueChange = updateSlewBrakeCloseFrq,
                onClick = onSetSlewBrakeCloseFrq,
                isDecimal = true,
            )
            Spacer(modifier = Modifier.size(20.dp))
//Ввод. Частота начала торможения. Гц
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiSlewFrqStartBraking),
                value = setValueSlewFrqStartBraking,
                onValueChange = updateSlewFrqStartBraking,
                onClick = onSetSlewFrqStartBraking,
                isDecimal = true,
            )
            Spacer(modifier = Modifier.size(30.dp))
//Ввод. Задержка времени при достижении частоты начала торможения. Секунды
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiSlewDelayStartBraking),
                value = setValueSlewDelayStartBraking,
                onValueChange = updateSlewDelayStartBraking,
                onClick = onSetSlewDelayStartBraking,
                isDecimal = true,
            )
            Spacer(modifier = Modifier.size(40.dp))
//Ввод. Задержка до начала торможения постоянным током. Секунды
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiSlewDelayTimeBrakeCurr),
                value = setValueSlewDelayTimeBrakeCurr,
                onValueChange = updateSlewDelayTimeBrakeCurr,
                onClick = onSetSlewDelayTimeBrakeCurr,
                isDecimal = true,
            )
            Spacer(modifier = Modifier.size(30.dp))
//Ввод. Время торможения постонным током. Секунды
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiSlewTimeBrakeCurrent),
                value = setValueSlewTimeBrakeCurrent,
                onValueChange = updateSlewTimeBrakeCurrent,
                onClick = onSetSlewTimeBrakeCurrent,
                isDecimal = true,
            )
        }
    }
}
private fun formatDecimal(value: Float): String {
    return String.format("%02.1f", value).replace(',', '.')
}

private fun formatHundred(value: Float): String {
    return String.format("%1.2f", value).replace(',', '.')
}

private fun format(value: Int): String {
    return String.format("%03d", value)
}

@Preview
@Composable
fun SettingRotationPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        SettingRotation(
            setValueSlewSpdStopButton = "100",
            onModelChangeSlewSpdStopButton = { },
            onSetSlewSpdStopButton = { },
            hmiSlewSpeedBrakeClose = 5,

            setValueSlewDECRevers = "15",
            updateSlewDECRevers = { },
            onSetSlewDECRevers = { },
            hmiSlewDecRevers = 15f,

            setValueSlewDelayTime = "25",
            updateSlewDelayTime = { },
            onSetSlewDelayTime = { },
            hmiSlewDelayTimeBrake = 10f,

            setValueSlewBrakeCloseFrq = "0.15",
            updateSlewBrakeCloseFrq = { },
            onSetSlewBrakeCloseFrq = { },
            hmiSlewBrakeCloseFrq = 1f,

            setValueSlewFrqStartBraking = "1",
            updateSlewFrqStartBraking = { },
            onSetSlewFrqStartBraking = { },
            hmiSlewFrqStartBraking = 1f,

            setValueSlewDelayStartBraking = "3",
            updateSlewDelayStartBraking = { },
            onSetSlewDelayStartBraking = { },
            hmiSlewDelayStartBraking = 3f,

            setValueSlewDelayTimeBrakeCurr = "4",
            updateSlewDelayTimeBrakeCurr = { },
            onSetSlewDelayTimeBrakeCurr = { },
            hmiSlewDelayTimeBrakeCurr = 4f,

            setValueSlewTimeBrakeCurrent = "12",
            updateSlewTimeBrakeCurrent = { },
            onSetSlewTimeBrakeCurrent = { },
            hmiSlewTimeBrakeCurrent = 12f,

            errorMessageSlew = null,
            logMessageSlew = null,

            )
    }
}