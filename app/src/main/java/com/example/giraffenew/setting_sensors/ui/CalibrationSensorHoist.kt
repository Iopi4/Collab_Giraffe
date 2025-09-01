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
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampError
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicTextFilledGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingSensorDwn
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingSensorStop
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.setting_sensors.ui.text.BasicInputRowandTensButtonBelowZero
import com.example.giraffenew.setting_sensors.ui.text.BasicTextFilledBelowZero
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun CalibrationSensorHoist(
    currentAbsEncHoistPositionWord1: Int,
    currentAbsEncHoistPositionWord2: Int,
    currentHoistPosition: Float,

    onSetHoistLimitTestEn : () -> Unit,

    setValueHoistPosFwdStop: String,
    updateValueHoistPosFwdStop: (String) -> Unit,
    onSetValueHoistPosFvdStop: () -> Unit,
    currentHoistStopUp1: Int,

    setValueHoistPosRevStop: String,
    updateValueHoistPosRevStop: (String) -> Unit,
    onSetValueHoistPosRevStop: () -> Unit,
    currentHoistStopDwn1: Int,

    setValueHoistPosFwdDwnSp: String,
    updateValueHoistPosFwdDwnSp: (String) -> Unit,
    onSetValueHoistPosFwdDwnSp: () -> Unit,
    currentHoistUpDwnSp1: Int,

    setValueHoistPosRevDwnSp: String,
    updateValueHoistPosRevDwnSp: (String) -> Unit,
    onSetValueHoistPosRevDwnSp: () -> Unit,
    currentHoistDwnDwnSp1: Int,

    currentHoistEncoderMaxDeltaError: Int,
    currentHoistEncoderMaxValueError: Int,
    currentHoistAbsEncErrCanOpen: Int,

    errorMessageHoist: String?,
    logMessageHoist: String?,

) {

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
            //Статус энкодера
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Sensor_Alarm_1)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Sensor_Alarm_2)

            Box (modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp)
            ) {
                errorMessageHoist?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))}
                logMessageHoist?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp)) }
            }
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
            // Заголовок
            BasicSettingsName(
                modifier = Modifier,
                text = stringResource(R.string.SettingSensors_Text_Parameters)
            )
            // Отображение текущего значения датчика
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = formatAbsEncPositionPosition(currentAbsEncHoistPositionWord1,currentAbsEncHoistPositionWord2)
                )
            }

            // Отображение текущей высоты подъема
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = formatDecimal(currentHoistPosition)
                )
            }

            // Кнопка включения проверки защит
            BasicButtonGiraffe(
                modifier = Modifier.fillMaxWidth(),
                text = R.string.Button_Turn_ON_Off,
                onClick = onSetHoistLimitTestEn,
            ) {}

            // Отображение кнопок ввода защит и статуса

            // Ограничение подъема в верх
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения максимальной высоты подъема
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                        .weight(2f)
                ) {
                    BasicTextFilledGiraffe(
                        placeholder = "000.0",
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
                        placeholder = "000.0",
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
                        placeholder = "000.0",
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
                //Поле ввода значения минимальной высоты подъема
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                        .weight(2f)
                ) {
                    BasicTextFilledBelowZero(
                        placeholder = "000.0",
                        value = setValueHoistPosRevStop,
                        onValueChange = updateValueHoistPosRevStop,
                        modifier = Modifier,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(2f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_Memory,
                        onClick = onSetValueHoistPosRevStop,
                    ) {}
                }
                //Статус. Индикация срабатывания замедления
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampSettingSensorStop(
                        isLampStatus = currentHoistStopDwn1
                    )
                }
            }
            //Статус датчика. Контроль показаний
            Row(horizontalArrangement = Arrangement.Start) {
                Box(modifier = Modifier.weight(2f))
                Box(modifier = Modifier
                    .weight(1f)
                    .size(height = 35.dp, width = 50.dp)
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)){
                    BasicLampError(isLampStatus = currentHoistEncoderMaxDeltaError)
                }
                Box(modifier = Modifier
                        .weight(1f)
                        .size(height = 35.dp, width = 50.dp)
                        .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicLampError(isLampStatus = currentHoistEncoderMaxValueError)
            }
            }
            //Статус датчика. Контроль связи
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


private fun format(value: Int): String {
    return String.format("%10d", value)
}

private fun formatDecimal(value: Float): String {
    return String.format("%04.1f", value).replace(',', '.')
}

private fun formatAbsEncPositionPosition(value1: Int, value2: Int): String {
    return String.format("%05d%05d", value2, value1)
}





@Preview
@Composable
fun CalibrationSensorHoistPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        CalibrationSensorHoist(
            currentHoistPosition = 1f,
            currentAbsEncHoistPositionWord1 = 1,
            currentAbsEncHoistPositionWord2 = 1,
            onSetHoistLimitTestEn = { },

            setValueHoistPosFwdStop = "1",
            updateValueHoistPosFwdStop = { },
            onSetValueHoistPosFvdStop = {},
            currentHoistStopUp1 = 1,

            setValueHoistPosRevStop = "`1",
            updateValueHoistPosRevStop = {},
            onSetValueHoistPosRevStop = {},
            currentHoistStopDwn1 = 1,

            setValueHoistPosFwdDwnSp = "1",
            updateValueHoistPosFwdDwnSp = {},
            onSetValueHoistPosFwdDwnSp = {},
            currentHoistUpDwnSp1 = 1,

            setValueHoistPosRevDwnSp = "1",
            updateValueHoistPosRevDwnSp = { },
            onSetValueHoistPosRevDwnSp = {},
            currentHoistDwnDwnSp1 = 1,

            currentHoistEncoderMaxDeltaError= 1,
            currentHoistEncoderMaxValueError= 1,
            currentHoistAbsEncErrCanOpen= 1,

            errorMessageHoist = null,
            logMessageHoist = null,
        )
    }
}