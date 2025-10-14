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
import com.example.giraffenew.setting_sensors.ui.text.BasicInputRowandTensButtonBelowZero
import com.example.giraffenew.setting_sensors.ui.text.BasicTextFilledBelowZero
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun CalibrationSensorHoist(
    sensorsHoistState: SettingSensorsViewModule.SensorsHoistState,

    onSetHoistLimitTestEn : () -> Unit,

    setValueHoistPosFwdStop: String,
    updateValueHoistPosFwdStop: (String) -> Unit,
    onSetValueHoistPosFvdStop: () -> Unit,

    setValueHoistPosRevStop: String,
    updateValueHoistPosRevStop: (String) -> Unit,
    onSetValueHoistPosRevStop: () -> Unit,

    setValueHoistPosZero: String,
    updateValueHoistPosZero: (String) -> Unit,
    onSetValueHoistPosZero: () -> Unit,

    setValueHoistPosFwdDwnSp: String,
    updateValueHoistPosFwdDwnSp: (String) -> Unit,
    onSetValueHoistPosFwdDwnSp: () -> Unit,

    setValueHoistPosRevDwnSp: String,
    updateValueHoistPosRevDwnSp: (String) -> Unit,
    onSetValueHoistPosRevDwnSp: () -> Unit,

    errorMessageHoist: String?,
    logMessageHoist: String?,

) {
    val currentHoistPosition = sensorsHoistState.currentHoistPosition
    val currentAbsEncHoistPositionWord1 = sensorsHoistState.currentAbsEncHoistPositionWord1
    val currentAbsEncHoistPositionWord2 = sensorsHoistState.currentAbsEncHoistPositionWord2
    val currentAbsEncHoistPositionWord3 = sensorsHoistState.currentAbsEncHoistPositionWord3
    val currentAbsEncHoistPositionWord4 = sensorsHoistState.currentAbsEncHoistPositionWord4
    val currentAbsEncHoistPositionWord5 = sensorsHoistState.currentAbsEncHoistPositionWord5
    val currentAbsEncHoistPositionWord6 = sensorsHoistState.currentAbsEncHoistPositionWord6
    val currentAbsEncHoistPositionWord7 = sensorsHoistState.currentAbsEncHoistPositionWord7
    val currentAbsEncHoistPositionWord8 = sensorsHoistState.currentAbsEncHoistPositionWord8
    val currentAbsEncHoistPositionWord9 = sensorsHoistState.currentAbsEncHoistPositionWord9
    val currentAbsEncHoistPositionWord10 = sensorsHoistState.currentAbsEncHoistPositionWord10
    val currentAbsEncHoistPositionWord11 = sensorsHoistState.currentAbsEncHoistPositionWord11
    val currentAbsEncHoistPositionWord12 = sensorsHoistState.currentAbsEncHoistPositionWord12

    val hoistLimitStatus = sensorsHoistState.hoistLimitStatus
    val currentHoistStopUp1 = sensorsHoistState.currentHoistStopUp1
    val hoistMaxPosition = sensorsHoistState.hoistMaxPosition

    val currentHoistStopDwn1 = sensorsHoistState.currentHoistStopDwn1
    val hoistMinPosition = sensorsHoistState.hoistMinPosition

    val hoistZeroPosition = sensorsHoistState.hoistZeroPosition

    val currentHoistUpDwnSp1 = sensorsHoistState.currentHoistUpDwnSp1
    val hoistPosFwdDwnSp = sensorsHoistState.hoistPosFwdDwnSp

    val currentHoistDwnDwnSp1 = sensorsHoistState.currentHoistDwnDwnSp1
    val hoistPosRevDwnSp = sensorsHoistState.hoistPosRevDwnSp

    val currentHoistEncoderMaxDeltaError = sensorsHoistState.currentHoistEncoderMaxDeltaError
    val currentHoistEncoderMaxValueError = sensorsHoistState.currentHoistEncoderMaxValueError
    val currentHoistAbsEncErrCanOpen = sensorsHoistState.currentHoistAbsEncErrCanOpen

    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {

        //№.1 - Левая область для отображения параметров настройки датчика высоты подъема)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f)
                .padding(5.dp)
                .border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
            // Заголовок
            BasicSettingsName(
                modifier = Modifier,
                text = stringResource(R.string.SettingSensors_Text_Hoist_Calibration))
            //Датчик
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Sensor)
            //Высота подъема
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Hoist_Lifting)
            //Тест ограничений
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Examination)
            //Стоп вверх
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Hoist_Stop_Up)
            //Снижение скорости вверх
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Hoist_Decline_Up)
            //Снижение скорости вниз
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Hoist_Decline_Down)
            //Стоп вниз
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Hoist_Stop_Down)
            //Нулевая высота
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Hoist_Zero)

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.SettingSensors_Text_Sensor_Status))

            //Статус энкодера
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
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
            // Отступ  и Статус записи данных настройки датчика
            Row(modifier = Modifier.fillMaxWidth()) {
                BasicSettingsName(modifier = Modifier.weight(1f), text = stringResource(R.string.spacer))

                Box (modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                ) {
                    errorMessageHoist?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp))}
                    logMessageHoist?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }
            // Отображение текущего значения датчика
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = formatAbsEncPositionPosition(
                            currentAbsEncHoistPositionWord1,
                            currentAbsEncHoistPositionWord2,
                            currentAbsEncHoistPositionWord3,
                            currentAbsEncHoistPositionWord4,
                            currentAbsEncHoistPositionWord5,
                            currentAbsEncHoistPositionWord6,
                            currentAbsEncHoistPositionWord7,
                            currentAbsEncHoistPositionWord8,
                            currentAbsEncHoistPositionWord9,
                            currentAbsEncHoistPositionWord10,
                            currentAbsEncHoistPositionWord11,
                            currentAbsEncHoistPositionWord12,
                            )
                    )
            }

            // Отображение текущей высоты подъема
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = formatDecimalMinus(currentHoistPosition)
                )
            }

            // Кнопка включения проверки защит
            BasicButtonOnOffStatusPolygon(
                onClick = onSetHoistLimitTestEn,
                isLampStatus = hoistLimitStatus
            )

            // Отображение кнопок ввода защит и статуса

            // Ограничение подъема в верх
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения максимальной высоты подъема
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                        .weight(2f)
                ) {
                    BasicTextFilledGiraffeDecimal(
                        placeholder = formatDecimalMinus(hoistMaxPosition),
                        value = setValueHoistPosFwdStop,
                        onValueChange = updateValueHoistPosFwdStop,
                        modifier = Modifier,
                        isDecimal = true,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(2f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_Memory,
                        onClick = onSetValueHoistPosFvdStop,
                    ) {}
                }
                //Статус. Индикация срабатывания остановки
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampSettingSensorStop(
                        isLampStatus = currentHoistStopUp1
                    )
                }
            }


            // Замедление подъема крюка в верх
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения при котором снижается скорость подъема
                Box(modifier = Modifier.weight(4f)
                ) {
                    BasicInputRowandTensButton(
                        placeholder = formatDecimal(hoistPosFwdDwnSp),
                        value = setValueHoistPosFwdDwnSp,
                        onValueChange = updateValueHoistPosFwdDwnSp,
                        onClick = onSetValueHoistPosFwdDwnSp,
                        isDecimal = true,
                    )
                }
                //Статус. Индикация срабатывания замедления
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampSettingSensorDwn(
                        isLampStatus = currentHoistUpDwnSp1
                    )
                }
            }


            // Замедление опускание крюка в низ
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения при котором снижается скорость опускания
                Box(
                    modifier = Modifier
                        .weight(4f)
                ) {
                    BasicInputRowandTensButtonBelowZero(
                        placeholder = formatDecimalMinus(hoistPosRevDwnSp),
                        value = setValueHoistPosRevDwnSp,
                        onValueChange = updateValueHoistPosRevDwnSp,
                        onClick = onSetValueHoistPosRevDwnSp,
                        isDecimal = true,
                    )
                }
                //Статус. Индикация срабатывания замедления
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampSettingSensorDwn(
                        isLampStatus = currentHoistDwnDwnSp1
                    )
                }
            }
            // Ограничение опускание крюка в низ
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения при котором снижается скорость опускания
                Box(
                    modifier = Modifier
                        .weight(4f)
                ) {
                    BasicInputRowandTensButtonBelowZero(
                        placeholder = formatDecimalMinus(hoistMinPosition),
                        value = setValueHoistPosRevStop,
                        onValueChange = updateValueHoistPosRevStop,
                        onClick = onSetValueHoistPosRevStop,
                        isDecimal = true,
                    )
                }
                //Статус. Индикация срабатывания замедления
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampSettingSensorStop(
                        isLampStatus = currentHoistStopDwn1
                    )
                }
            }


            // Нулевая высота
            Row(horizontalArrangement = Arrangement.Start) {
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                        .weight(8f)
                ) {
                    BasicTextFilledBelowZero(
                        placeholder = formatDecimalMinus(hoistZeroPosition),
                        value = setValueHoistPosZero,
                        onValueChange = updateValueHoistPosZero,
                        isDecimal = true
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(7f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_Memory,
                        onClick = onSetValueHoistPosZero,
                    ) {}
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
                        isLampStatus = currentHoistEncoderMaxDeltaError
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
                        isLampStatus = currentHoistEncoderMaxValueError
                    )
                }
            }
            //Статус датчика. Контроль связи по CanOPEN
            Row(horizontalArrangement = Arrangement.Start) {
                Box(modifier = Modifier.weight(3f))
                Box(modifier = Modifier
                        .weight(1f)
                        .size(height = 35.dp, width = 50.dp)
                        .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicLampError(
                        isLampStatus = currentHoistAbsEncErrCanOpen
                    )
                }
            }

        }
    }
}

private val VIRTUAL_ZERO: Float = 32250f //Константа для виртуального нуля. Необходимо для корретной высоты подъема

private fun formatDecimal(value: Float): String {
    return String.format("%.1f", value).replace(',', '.')
}
private fun formatDecimalMinus(value: Float): String {
    val realValue = (value * 10 - VIRTUAL_ZERO) / 10f
    return if (realValue < 0) {
        String.format("%05.1f", realValue).replace(',', '.')
    } else {
        String.format("%04.1f", realValue).replace(',', '.')
    }
}

private fun formatAbsEncPositionPosition(
    value1: Int, value2: Int, value3: Int,
    value4: Int, value5: Int, value6: Int,
    value7: Int, value8: Int, value9: Int,
    value10: Int, value11: Int, value12: Int,
    ): String {
    return String.format("%d%d%d%d%d%d%d%d%d%d%d%d", value1, value2, value3, value4, value5, value6,
        value7, value8, value9, value10, value11, value12)
}





@Preview
@Composable
fun CalibrationSensorHoistPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        CalibrationSensorHoist(

            sensorsHoistState = SettingSensorsViewModule.SensorsHoistState(
                currentHoistPosition = 1f,
                currentAbsEncHoistPositionWord1 = 1,
                currentAbsEncHoistPositionWord2 = 1,
                currentAbsEncHoistPositionWord3 = 1,
                currentAbsEncHoistPositionWord4 = 1,
                currentAbsEncHoistPositionWord5 = 1,
                currentAbsEncHoistPositionWord6 = 1,
                currentAbsEncHoistPositionWord7 = 1,
                currentAbsEncHoistPositionWord8 = 1,
                currentAbsEncHoistPositionWord9 = 1,
                currentAbsEncHoistPositionWord10 = 1,
                currentAbsEncHoistPositionWord11 = 1,
                currentAbsEncHoistPositionWord12 = 1,


                currentHoistStopUp1 = 1,
                hoistMaxPosition = 50f,
                currentHoistStopDwn1 = 1,
                hoistMinPosition = 0f,
                hoistZeroPosition = 0f,
                currentHoistUpDwnSp1 = 1,
                hoistPosFwdDwnSp = 45f,
                currentHoistDwnDwnSp1 = 1,
                hoistPosRevDwnSp = 5f,

                currentHoistEncoderMaxDeltaError = 1,
                currentHoistEncoderMaxValueError = 1,
                currentHoistAbsEncErrCanOpen = 1,
                hoistLimitStatus = 1,
            ),

            onSetHoistLimitTestEn = { },

            setValueHoistPosFwdStop = "1",
            updateValueHoistPosFwdStop = { },
            onSetValueHoistPosFvdStop = {},


            setValueHoistPosRevStop = "`1",
            updateValueHoistPosRevStop = {},
            onSetValueHoistPosRevStop = {},


            setValueHoistPosZero = "`1",
            updateValueHoistPosZero = {},
            onSetValueHoistPosZero = {},


            setValueHoistPosFwdDwnSp = "1",
            updateValueHoistPosFwdDwnSp = {},
            onSetValueHoistPosFwdDwnSp = {},


            setValueHoistPosRevDwnSp = "1",
            updateValueHoistPosRevDwnSp = { },
            onSetValueHoistPosRevDwnSp = {},

            errorMessageHoist = null,
            logMessageHoist = null,
        )
    }
}