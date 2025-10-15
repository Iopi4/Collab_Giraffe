package com.example.giraffenew.service_menu.ui.sensors

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
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.diagnostics.state.StateValueWindAndLoadSensor
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServiceWind(
//СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА ВЕТРА
    setValueWindSpeedLvl1: String,
    onModelChangeWindSpeedLvl1: (String) -> Unit,
    onSetWindSpeedLvl1: () -> Unit,
    hmiWindSpeedLvl1: Float,

    setValueWindSpeedLvl2: String,
    onModelChangeWindSpeedLvl2: (String) -> Unit,
    onSetWindSpeedLvl2: () -> Unit,
    hmiWindSpeedLvl2: Float,

    setValueWindSpeedMontageLvl1: String,
    onModelChangeWindSpeedMontageLvl1: (String) -> Unit,
    onSetWindSpeedMontageLvl1: () -> Unit,
    hmiWindSpeedLvl1Montage: Float,

    setValueWindTimeSmooth: String,
    onModelChangeWindTimeSmooth: (String) -> Unit,
    onSetWindTimeSmooth: () -> Unit,
    hmiAnemometerTimeSmooth: Int,

    setValueAnemometerMinWind: String,
    updateAnemometerMinWind: (String) -> Unit,
    onSetAnemometerMinWindClbr: () -> Unit,
    hmiReadAnemometerMinWind: Float,

    setValueAnemometerMaxWind: String,
    updateAnemometerMaxWind: (String) -> Unit,
    onSetAnemometerMaxWindClbr: () -> Unit,
    hmiReadAnemometerMaxWind: Float,

    setValueAnemometerMinAmper: String,
    updateAnemometerMinAmper: (String) -> Unit,
    onSetAnemometerMinAmperClbr: () -> Unit,
    hmiReadAnemometerMinAmper: Float,

    setValueAnemometerMaxAmper: String,
    updateAnemometerMaxAmper: (String) -> Unit,
    onSetAnemometerMaxAmperClbr: () -> Unit,
    hmiReadAnemometerMaxAmper: Float,

    currentWind: Float,
    currentAnemometerSensor: Int,
    currentAnemometerStatus: Int,

    onSetAnemometerClbr: () -> Unit,

    errorMessageWind: String?,
    logMessageWind: String?,
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
                text = stringResource(R.string.ServiceMenu_Text_Sensor_Wind))
//Отображения текста параметров настройки датчика ветра
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Wind_Warning_Signal)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Wind_Alarm_Signal)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Wind_Alarm_Signal_Montage)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Wind_Averaging_Time)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Wind_AnemometerMinWind_Calibration)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Wind_AnemometerMaxWind_Calibration)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Wind_AnemometerMinAmper_Calibration)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Wind_AnemometerMaxAmper_Calibration)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Wind_Value)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Value)


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
// Статус записи данных настройки поворота
            Row(modifier = Modifier.fillMaxWidth()) {
                BasicSettingsName(modifier = Modifier.weight(1f), text = stringResource(R.string.spacer))

                Box (modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                ) {
                    errorMessageWind?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp))}
                    logMessageWind?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp)) }
                }
            }
//Ввод. Пред. сигнализация [м/c]
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiWindSpeedLvl1),
                value = setValueWindSpeedLvl1,
                onValueChange = onModelChangeWindSpeedLvl1,
                onClick = onSetWindSpeedLvl1,
                isDecimal = true,
            )
//Ввод. Авар. сигнализация [м/c]
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiWindSpeedLvl2),
                value = setValueWindSpeedLvl2,
                onValueChange = onModelChangeWindSpeedLvl2,
                onClick = onSetWindSpeedLvl2,
                isDecimal = true,
            )
//Ввод.Авар.сигн.монтаж.реж.[м/c]
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiWindSpeedLvl1Montage),
                value = setValueWindSpeedMontageLvl1,
                onValueChange = onModelChangeWindSpeedMontageLvl1,
                onClick = onSetWindSpeedMontageLvl1,
                isDecimal = true,
            )
//Ввод. Время усреднения [с]
            BasicInputRowandButton(
                placeholder = format(hmiAnemometerTimeSmooth),
                value = setValueWindTimeSmooth,
                valueMin = 0,
                valueMax = 10,
                onValueChange = onModelChangeWindTimeSmooth,
                onClick = onSetWindTimeSmooth
            )
//Ввод. Нижний предел измерения анемометра, м/с
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiReadAnemometerMinWind),
                value = setValueAnemometerMinWind,
                onValueChange = updateAnemometerMinWind,
                onClick = onSetAnemometerMinWindClbr,
                isDecimal = true,
            )
//Ввод. Верхнй предел измерения анемометра, м/с
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiReadAnemometerMaxWind),
                value = setValueAnemometerMaxWind,
                onValueChange = updateAnemometerMaxWind,
                onClick = onSetAnemometerMaxWindClbr,
                isDecimal = true,
            )
//Ввод. Нижний предел измерения анемометра, mA
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiReadAnemometerMinAmper),
                value = setValueAnemometerMinAmper,
                onValueChange = updateAnemometerMinAmper,
                onClick = onSetAnemometerMinAmperClbr,
                isDecimal = true,
            )
//Ввод. Верхний предел измерения анемометра, mA
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiReadAnemometerMaxAmper),
                value = setValueAnemometerMaxAmper,
                onValueChange = updateAnemometerMaxAmper,
                onClick = onSetAnemometerMaxAmperClbr,
                isDecimal = true,
            )
// Отображение текущего значения ветра [м/c]
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = formatDecimal(currentWind)
                )
            }
// Отображение статус датчика ветра
            StateValueWindAndLoadSensor(
                isValueParameter = formatDecimalMinus(currentAnemometerSensor),
                isValueStatus = currentAnemometerStatus
            )
//Кнопка калибровки датчика
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.Button_Calibration,
                onClick = onSetAnemometerClbr,
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
private fun formatThousand(value: Float): String {
    return String.format("%01.5f", value).replace(',', '.')
}
private val VIRTUAL_ZERO: Int = 32250 //Константа для виртуального нуля.
// Необходимо для корретного отображения мин. значения датчика ветра
private fun formatDecimalMinus(value: Int): String {
    val realValue = (value - VIRTUAL_ZERO)
    return if (realValue < 0) {
        String.format("%05d", realValue)
    } else {
        String.format("%05d", realValue)
    }
}

@Preview
@Composable
fun ServiceWindPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
    }
}