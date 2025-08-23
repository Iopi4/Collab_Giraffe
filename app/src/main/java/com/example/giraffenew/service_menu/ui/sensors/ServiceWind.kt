package com.example.giraffenew.service_menu.ui.sensors

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandThousandButton
import com.example.giraffenew.diagnostics.ui.StateValueWindAndLoadSensor
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServiceWind(
//СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА ВЕТРА
    setValueWindSpeedLvl1: String,
    onModelChangeWindSpeedLvl1: (String) -> Unit,
    onSetWindSpeedLvl1: () -> Unit,

    setValueWindSpeedLvl2: String,
    onModelChangeWindSpeedLvl2: (String) -> Unit,
    onSetWindSpeedLvl2: () -> Unit,

    setValueWindSpeedMontageLvl1: String,
    onModelChangeWindSpeedMontageLvl1: (String) -> Unit,
    onSetWindSpeedMontageLvl1: () -> Unit,

    setValueWindTimeSmooth: String,
    onModelChangeWindTimeSmooth: (String) -> Unit,
    onSetWindTimeSmooth: () -> Unit,

    setValueWindSpeedLvlClbr: String,
    onModelChangeWindSpeedLvlClbr: (String) -> Unit,
    onSetWindSpeedLvlClbr: () -> Unit,

    currentWind: Float,
    currentAnemometerSensor: Int,
    currentAnemometerStatus: Int,

    setValueWindRK: String,
    onModelChangeWindRK: (String) -> Unit,
    onSetWindRK: () -> Unit,

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
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Wind_Calibration)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Wind_Value)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Value)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Calibration_Factor)

            Box (modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp)
            ) {
                errorMessageWind?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))}
                logMessageWind?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp)) }
            }
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
// Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.ServiceMenu_Text_Setting))
//Ввод. Пред. сигнализация [м/c]
            BasicInputRowandTensButton(
                placeholder = "10.0",
                value = setValueWindSpeedLvl1,
                onValueChange = onModelChangeWindSpeedLvl1,
                onClick = onSetWindSpeedLvl1,
                isDecimal = true,
            )
//Ввод. Авар. сигнализация [м/c]
            BasicInputRowandTensButton(
                placeholder = "15.0",
                value = setValueWindSpeedLvl2,
                onValueChange = onModelChangeWindSpeedLvl2,
                onClick = onSetWindSpeedLvl2,
                isDecimal = true,
            )
//Ввод.Авар.сигн.монтаж.реж.[м/c]
            BasicInputRowandTensButton(
                placeholder = "15.0",
                value = setValueWindSpeedMontageLvl1,
                onValueChange = onModelChangeWindSpeedMontageLvl1,
                onClick = onSetWindSpeedMontageLvl1,
                isDecimal = true,
            )
//Ввод. Время усреднения [с]
            BasicInputRowandTensButton(
                placeholder = "5",
                value = setValueWindTimeSmooth,
                onValueChange = onModelChangeWindTimeSmooth,
                onClick = onSetWindTimeSmooth,
                isDecimal = false,
            )
//Ввод. Значение калибровки [м/c]
            BasicInputRowandTensButton(
                placeholder = "00.0",
                value = setValueWindSpeedLvlClbr,
                onValueChange = onModelChangeWindSpeedLvlClbr,
                onClick = onSetWindSpeedLvlClbr,
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
                isValueParameter = format(currentAnemometerSensor),
                isValueStatus = currentAnemometerStatus
            )
//Ввод. Коэффициент калибровки
            BasicInputRowandThousandButton(
                placeholder = "00.0",
                value = setValueWindRK,
                onValueChange = onModelChangeWindRK,
                onClick = onSetWindRK,
                isDecimal = true,
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

private fun format(value: Int): String {
    //return String.format("%.1f", value).padStart(5, '0')
    return String.format("%05d", value)
}

private fun formatDecimal(value: Float): String {
    //return String.format("%.1f", value).padStart(5, '0')
    return String.format("%04.1f", value).replace(',', '.')
}

@Preview
@Composable
fun ServiceWindPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
    }
}