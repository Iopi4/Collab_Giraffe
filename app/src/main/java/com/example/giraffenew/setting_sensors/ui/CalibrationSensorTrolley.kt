package com.example.giraffenew.setting_sensors.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.giraffenew.coordinate_protection.ui.button.BasicButtonOnOffStatusPolygon
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampError
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicTextFilledGiraffeDecimal
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingSensorDwn
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingSensorStop
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.setting_sensors.presentation.SettingSensorsViewModule
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun CalibrationSensorTrolley(
    sensorsTrolleyState: SettingSensorsViewModule.SensorsTrolleyState,

    onSetTrolleyLimitTestEn : () -> Unit,

    setValueTrolleyPosFwdStop: String,
    updateValueTrolleyPosFwdStop: (String) -> Unit,
    onSetValueTrolleyPosFvdStop: () -> Unit,

    setValueTrolleyPosRevStop: String,
    updateValueTrolleyPosRevStop: (String) -> Unit,
    onSetValueTrolleyPosRevStop: () -> Unit,

    setValueTrolleyPosFwdDwnSp: String,
    updateValueTrolleyPosFwdDwnSp: (String) -> Unit,
    onSetValueTrolleyPosFwdDwnSp: () -> Unit,

    setValueTrolleyPosRevDwnSp: String,
    updateValueTrolleyPosRevDwnSp: (String) -> Unit,
    onSetValueTrolleyPosRevDwnSp: () -> Unit,

    errorMessageTrolley: String?,
    logMessageTrolley: String?,

) {
    val currentTrolleyPosition = sensorsTrolleyState.currentTrolleyPosition
    val currentAbsEncTrolleyPositionWord1 = sensorsTrolleyState.currentAbsEncTrolleyPositionWord1
    val currentAbsEncTrolleyPositionWord2 = sensorsTrolleyState.currentAbsEncTrolleyPositionWord2

    val currentTrolleyStopFwd1 = sensorsTrolleyState.currentTrolleyStopFwd1
    val trolleyMaxPosition = sensorsTrolleyState.trolleyMaxPosition
    val currentTrolleyStopRev1 = sensorsTrolleyState.currentTrolleyStopRev1
    val trolleyMinPosition = sensorsTrolleyState.trolleyMinPosition
    val currentTrolleyFwdDwnSp1 = sensorsTrolleyState.currentTrolleyFwdDwnSp1
    val trolleyPosFwdDwnSp = sensorsTrolleyState.trolleyPosFwdDwnSp
    val currentTrolleyRevDwnSp1 = sensorsTrolleyState.currentTrolleyRevDwnSp1
    val trolleyPosRevDwnSp = sensorsTrolleyState.trolleyPosRevDwnSp

    val currentTrolleyEncoderMaxDeltaError= sensorsTrolleyState.currentTrolleyEncoderMaxDeltaError
    val currentTrolleyEncoderMaxValueError= sensorsTrolleyState.currentTrolleyEncoderMaxValueError
    val currentTrolleyAbsEncErrCanOpen= sensorsTrolleyState.currentTrolleyAbsEncErrCanOpen

    val trolleyLimitStatus = sensorsTrolleyState.trolleyLimitStatus

    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {

//№.1 - Левая область для отображения параметров настройки датчика высоты подъема)
        Column(
            modifier = Modifier
                .fillMaxSize().weight(3f)
                .padding(5.dp)
                .border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
            // Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.SettingSensors_Text_Trolley_Calibration))
            //Датчик
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.SettingSensors_Text_Sensor)
            //Вылет
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Trolley_Departure)
            //Тест ограничений
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Examination)
            //Стоп вперед
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Trolley_Stop_Revers)
            //Снижение скорости вперед
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Trolley_Decline_Revers)
            //Снижение скорости назад
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Trolley_Decline_Forward)
            //Стоп назад
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Trolley_Stop_Forward)

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.SettingSensors_Text_Sensor_Status))

            //Ошибки и статус энкодера
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Sensor_Alarm_1_1)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Sensor_Alarm_1_2)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Sensor_Alarm_2)

        }


//№.2 - Правая область для отображения и ввода данных датчика высоты подъема
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f)
                .padding(5.dp)
                .fillMaxSize()
                .border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)),
                verticalArrangement = Arrangement.Top
        ) {
// Отступ  и Статус записи данных настройки датчика
            Row(modifier = Modifier.fillMaxWidth()) {
                BasicSettingsName(modifier = Modifier.weight(1f), text = stringResource(R.string.spacer))

                Box (modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                ) {
                    errorMessageTrolley?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp))}
                    logMessageTrolley?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }

// Отображение текущего значения датчика вылета
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = formatAbsEncPositionPosition(
                        currentAbsEncTrolleyPositionWord2,
                        currentAbsEncTrolleyPositionWord1,
                        )
                )
            }
// Отображение текущего вылета
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = formatDecimal(currentTrolleyPosition)
                )
            }

// Кнопка включения проверки защит
            BasicButtonOnOffStatusPolygon(
                onClick = onSetTrolleyLimitTestEn,
                isLampStatus = trolleyLimitStatus
            )

// Отображение кнопок ввода защит и статуса

// Ограничение тележка назад
            Row(horizontalArrangement = Arrangement.Start) {
//Поле ввода значения минимального вылета
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                        .weight(2f)
                ) {
                    BasicTextFilledGiraffeDecimal(
                        placeholder = formatDecimal(trolleyMinPosition),
                        value = setValueTrolleyPosRevStop,
                        onValueChange = updateValueTrolleyPosRevStop,
                        modifier = Modifier,
                        isDecimal = true,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(2f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_Memory,
                        onClick = onSetValueTrolleyPosRevStop,
                    ) {}
                }
                //Статус. Индикация срабатывания остановки
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampSettingSensorStop(
                        isLampStatus = currentTrolleyStopRev1
                    )
                }
            }


// Замедление джвижения тележки назад
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения при котором снижается скорость подъема
                Box(modifier = Modifier.weight(4f)
                ) {
                    BasicInputRowandTensButton(
                        placeholder = formatDecimal(trolleyPosRevDwnSp),
                        value = setValueTrolleyPosRevDwnSp,
                        onValueChange = updateValueTrolleyPosRevDwnSp,
                        onClick = onSetValueTrolleyPosRevDwnSp,
                        isDecimal = true,
                    )
                }
                //Статус. Индикация срабатывания замедления
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampSettingSensorDwn(
                        isLampStatus = currentTrolleyRevDwnSp1
                    )
                }
            }


// Замедление движение тележки вперед
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения при котором снижается скорость опускания
                Box(
                    modifier = Modifier
                        .weight(4f)
                ) {
                    BasicInputRowandTensButton(
                        placeholder = formatDecimal(trolleyPosFwdDwnSp),
                        value = setValueTrolleyPosFwdDwnSp,
                        onValueChange = updateValueTrolleyPosFwdDwnSp,
                        onClick = onSetValueTrolleyPosFwdDwnSp,
                        isDecimal = true,
                    )
                }
//Статус. Индикация срабатывания замедления
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampSettingSensorDwn(
                        isLampStatus = currentTrolleyFwdDwnSp1
                    )
                }
            }

// Ограничение остановки тележки вперед
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения минимальной высоты подъема
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                        .weight(2f)
                ) {
                    BasicTextFilledGiraffeDecimal(
                        placeholder = formatDecimal(trolleyMaxPosition),
                        value = setValueTrolleyPosFwdStop,
                        onValueChange = updateValueTrolleyPosFwdStop,
                        modifier = Modifier,
                    )
                }
//Кнопка ввода
                Box(modifier = Modifier.weight(2f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_Memory,
                        onClick = onSetValueTrolleyPosFvdStop,
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampSettingSensorStop(
                        isLampStatus = currentTrolleyStopFwd1
                    )
                }
            }

// Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))

//Статус датчика. Контроль максимального рассогласования
            Row(horizontalArrangement = Arrangement.Start) {
                Box(modifier = Modifier.weight(3f))
                Box(modifier = Modifier
                    .weight(1f)
                    .size(height = 35.dp, width = 50.dp)
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicLampError(
                        isLampStatus = currentTrolleyEncoderMaxDeltaError
                    )
                }
            }
//Статус датчика. Контроль максимального значения
            Row(horizontalArrangement = Arrangement.Start) {
                Box(modifier = Modifier.weight(3f))
                Box(modifier = Modifier
                    .weight(1f)
                    .size(height = 35.dp, width = 50.dp)
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicLampError(
                        isLampStatus = currentTrolleyEncoderMaxValueError
                    )
                }
            }
//Статус датчика. Контроль связи
            Row(horizontalArrangement = Arrangement.Start) {
                Box(modifier = Modifier.weight(3f))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .size(height = 35.dp, width = 50.dp)
                        .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicLampError(
                        isLampStatus = currentTrolleyAbsEncErrCanOpen
                    )
                }
            }
        }
    }
}

private fun formatDecimal(value: Float): String {
    return String.format("%04.1f", value).replace(',', '.')
}

private fun formatAbsEncPositionPosition(high: Int, low: Int): String {
    // Правильное объединение: старший регистр (high) сдвигается на 16 бит
    val combined = (high.toLong() shl 16) or (low.toLong() and 0xFFFF)
    return String.format("%d", combined)
}

@Preview
@Composable
fun CalibrationSensorTrolleyPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        CalibrationSensorTrolley(
            sensorsTrolleyState = SettingSensorsViewModule.SensorsTrolleyState(
                currentTrolleyPosition = 1f,
                currentAbsEncTrolleyPositionWord1 = 1,
                currentAbsEncTrolleyPositionWord2 = 1,

                trolleyLimitStatus = 1,

                currentTrolleyStopFwd1 = 1,
                trolleyMaxPosition = 60f,
                currentTrolleyStopRev1 = 1,
                trolleyMinPosition = 2f,
                currentTrolleyFwdDwnSp1 = 1,
                trolleyPosFwdDwnSp = 55f,
                currentTrolleyRevDwnSp1 = 1,
                trolleyPosRevDwnSp = 5f,
                currentTrolleyEncoderMaxDeltaError= 1,
                currentTrolleyEncoderMaxValueError= 1,
                currentTrolleyAbsEncErrCanOpen= 1,
            ),

            onSetTrolleyLimitTestEn = { },

            setValueTrolleyPosFwdStop = "1",
            updateValueTrolleyPosFwdStop = { },
            onSetValueTrolleyPosFvdStop = {},

            setValueTrolleyPosRevStop = "`1",
            updateValueTrolleyPosRevStop = {},
            onSetValueTrolleyPosRevStop = {},

            setValueTrolleyPosFwdDwnSp = "1",
            updateValueTrolleyPosFwdDwnSp = {},
            onSetValueTrolleyPosFwdDwnSp = {},

            setValueTrolleyPosRevDwnSp = "1",
            updateValueTrolleyPosRevDwnSp = { },
            onSetValueTrolleyPosRevDwnSp = {},

            errorMessageTrolley = null,
            logMessageTrolley = null,
        )
    }
}