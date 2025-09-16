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
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun CalibrationSensorTrolley(
    currentAbsEncTrolleyPositionWord1: Int,
    currentAbsEncTrolleyPositionWord2: Int,

    currentTrolleyPosition: Float,

    onSetTrolleyLimitTestEn : () -> Unit,

    setValueTrolleyPosFwdStop: String,
    updateValueTrolleyPosFwdStop: (String) -> Unit,
    onSetValueTrolleyPosFvdStop: () -> Unit,
    currentTrolleyStopFwd1: Int,

    setValueTrolleyPosRevStop: String,
    updateValueTrolleyPosRevStop: (String) -> Unit,
    onSetValueTrolleyPosRevStop: () -> Unit,
    currentTrolleyStopRev1: Int,

    setValueTrolleyPosFwdDwnSp: String,
    updateValueTrolleyPosFwdDwnSp: (String) -> Unit,
    onSetValueTrolleyPosFwdDwnSp: () -> Unit,
    currentTrolleyFwdDwnSp1: Int,

    setValueTrolleyPosRevDwnSp: String,
    updateValueTrolleyPosRevDwnSp: (String) -> Unit,
    onSetValueTrolleyPosRevDwnSp: () -> Unit,
    currentTrolleyRevDwnSp1: Int,

    currentTrolleyEncoderMaxDeltaError: Int,
    currentTrolleyEncoderMaxValueError: Int,
    currentTrolleyAbsEncErrCanOpen: Int,

    errorMessageTrolley: String?,
    logMessageTrolley: String?,

) {

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
                    errorMessageTrolley?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 0.dp))}
                    logMessageTrolley?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }

            // Отображение текущего значения датчика вылета
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = formatAbsEncPositionPosition(currentAbsEncTrolleyPositionWord1, currentAbsEncTrolleyPositionWord2)
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
                isLampStatus = 0
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
                        placeholder = "000.0",
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


            // Замедление джвижени тележки назад
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения при котором снижается скорость подъема
                Box(modifier = Modifier.weight(4f)
                ) {
                    BasicInputRowandTensButton(
                        placeholder = "000.0",
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
                        placeholder = "000.0",
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
                        placeholder = "000.0",
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

private fun format(value: Int): String {
    //return String.format("%.1f", value).padStart(5, '0')
    return String.format("%10d", value)
}

private fun formatDecimal(value: Float): String {
    //return String.format("%.1f", value).padStart(5, '0')
    return String.format("%04.1f", value).replace(',', '.')
}

private fun formatAbsEncPositionPosition(value1: Int, value2: Int): String {
    return String.format("%05d%05d", value2, value1)
}

@Preview
@Composable
fun CalibrationSensorTrolleyPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        CalibrationSensorTrolley(
            currentTrolleyPosition = 1f,
            currentAbsEncTrolleyPositionWord1 = 1,
            currentAbsEncTrolleyPositionWord2 = 1,

            onSetTrolleyLimitTestEn = { },

            setValueTrolleyPosFwdStop = "1",
            updateValueTrolleyPosFwdStop = { },
            onSetValueTrolleyPosFvdStop = {},
            currentTrolleyStopFwd1 = 1,

            setValueTrolleyPosRevStop = "`1",
            updateValueTrolleyPosRevStop = {},
            onSetValueTrolleyPosRevStop = {},
            currentTrolleyStopRev1 = 1,

            setValueTrolleyPosFwdDwnSp = "1",
            updateValueTrolleyPosFwdDwnSp = {},
            onSetValueTrolleyPosFwdDwnSp = {},
            currentTrolleyFwdDwnSp1 = 1,

            setValueTrolleyPosRevDwnSp = "1",
            updateValueTrolleyPosRevDwnSp = { },
            onSetValueTrolleyPosRevDwnSp = {},
            currentTrolleyRevDwnSp1 = 1,

            currentTrolleyEncoderMaxDeltaError= 1,
            currentTrolleyEncoderMaxValueError= 1,
            currentTrolleyAbsEncErrCanOpen= 1,

            errorMessageTrolley = null,
            logMessageTrolley = null,
        )
    }
}