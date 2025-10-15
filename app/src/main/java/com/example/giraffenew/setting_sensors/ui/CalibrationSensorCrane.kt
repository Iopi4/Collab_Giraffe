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
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingParameter
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
fun CalibrationSensorCrane(
    sensorsCraneState: SettingSensorsViewModule.SensorsCraneState,

    onSetCraneLimitTestEn : () -> Unit,

    onSetCraneRailsEn : () -> Unit,

    setValueCranePosFwdStop: String,
    updateValueCranePosFwdStop: (String) -> Unit,
    onSetValueCranePosFwdStop: () -> Unit,

    setValueCranePosRevStop: String,
    updateValueCranePosRevStop: (String) -> Unit,
    onSetValueCranePosRevStop: () -> Unit,

    setValueCranePosFwdDwnSp: String,
    updateValueCranePosFwdDwnSp: (String) -> Unit,
    onSetValueCranePosFwdDwnSp: () -> Unit,

    setValueCranePosRevDwnSp: String,
    updateValueCranePosRevDwnSp: (String) -> Unit,
    onSetValueCranePosRevDwnSp: () -> Unit,

    setValueCranePosRepPoint: String,
    updateValueCranePosRepPoint: (String) -> Unit,
    onSetValueCranePosRepPoint: () -> Unit,

    errorMessageCrane: String?,
    logMessageCrane: String?,
) {

    val currentAbsEncCranePositionWord1 = sensorsCraneState.currentAbsEncCranePositionWord1
    val currentAbsEncCranePositionWord2 = sensorsCraneState.currentAbsEncCranePositionWord2

    val currentCranePosition = sensorsCraneState.currentCranePosition

    val currentCraneRailsEn = sensorsCraneState.currentCraneRailsEn
    val currentCraneStopFwd1 = sensorsCraneState.currentCraneStopFwd1
    val craneMaxPosition = sensorsCraneState.craneMaxPosition
    val currentCraneStopRev1 = sensorsCraneState.currentCraneStopRev1
    val craneMinPosition = sensorsCraneState.craneMinPosition
    val currentCraneFwdDwnSp1 = sensorsCraneState.currentCraneFwdDwnSp1
    val cranePosFwdDwnSp = sensorsCraneState.cranePosFwdDwnSp
    val currentCraneRevDwnSp1 = sensorsCraneState.currentCraneRevDwnSp1
    val cranePosRevDwnSp = sensorsCraneState.cranePosRevDwnSp
    val currentCraneRepPoint = sensorsCraneState.currentCraneRepPoint
    val cranePosCTRLPoint = sensorsCraneState.cranePosCTRLPoint
    val currentCraneEncoderMaxDeltaError = sensorsCraneState.currentCraneEncoderMaxDeltaError
    val currentCraneEncoderMaxValueError = sensorsCraneState.currentCraneEncoderMaxValueError
    val currentCraneAbsEncErrCanOpen = sensorsCraneState.currentCraneAbsEncErrCanOpen

    val craneLimitStatus = sensorsCraneState.craneLimitStatus

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
                .fillMaxSize().border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
            // Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.SettingSensors_Text_Crane_Calibration))
            //Датчик
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.SettingSensors_Text_Sensor)
            //Путь
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Crane_Run)
            //Тест ограничений
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Examination)
            //Стоп вправо
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Crane_Stop_Right)
            //Сниж. скор. вправо
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Crane_Decline_Right)
            //Сниж. скор. влево
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Crane_Decline_Left)
            //Стоп влево
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Crane_Stop_Left)
            //Реперная точка
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Crane_Reference_Point)
            //Включение хода крана
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Crane_Activate)

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.SettingSensors_Text_Sensor_Status))

            //Ошибки и статус энкодера
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Sensor_Alarm_1_1)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Sensor_Alarm_1_2)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Sensor_Alarm_2)
        }


//№.2 - Правая область для отображения и ввода данных датчика хода крана
        Column(
            modifier = Modifier
                .fillMaxSize().weight(2f)
                .padding(5.dp)
                .fillMaxSize().border(
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
                    errorMessageCrane?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp))}
                    logMessageCrane?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }

// Отображение текущего значения датчика хода крана
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = formatAbsEncPositionPosition(
                        currentAbsEncCranePositionWord2,
                        currentAbsEncCranePositionWord1,
                    )
                )
            }
// Отображение пройденного пути
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = formatDecimal(currentCranePosition)
                )
            }

// Кнопка включения проверки защит
            BasicButtonOnOffStatusPolygon(
                onClick = onSetCraneLimitTestEn,
                isLampStatus = craneLimitStatus
            )

// Отображение кнопок ввода защит и статуса

// Ограничение движения хода вперед (вправо)
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения остановка вперед (вправо)
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                        .weight(2f)
                ) {
                    BasicTextFilledGiraffeDecimal(
                        placeholder = formatDecimal(craneMaxPosition),
                        value = setValueCranePosFwdStop,
                        onValueChange = updateValueCranePosFwdStop,
                        modifier = Modifier,
                        isDecimal = true,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(2f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_Memory,
                        onClick = onSetValueCranePosFwdStop,
                    ) {}
                }
                //Статус. Индикация срабатывания остановки
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampSettingSensorStop(
                        isLampStatus = currentCraneStopFwd1
                    )
                }
            }


// Замедление скорости движения крана вперед (вправо)
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения при котором снижается скорость подъема
                Box(modifier = Modifier.weight(4f)
                ) {
                    BasicInputRowandTensButton(
                        placeholder = formatDecimal(cranePosFwdDwnSp),
                        value = setValueCranePosFwdDwnSp,
                        onValueChange = updateValueCranePosFwdDwnSp,
                        onClick = onSetValueCranePosFwdDwnSp,
                        isDecimal = true,
                    )
                }
                //Статус. Индикация срабатывания замедления
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampSettingSensorDwn(
                        isLampStatus = currentCraneFwdDwnSp1
                    )
                }
            }


// Замедление скорости движения крана назад (влево)
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения при котором снижается скорость подъема
                Box(modifier = Modifier.weight(4f)
                ) {
                    BasicInputRowandTensButton(
                        placeholder = formatDecimal(cranePosRevDwnSp),
                        value = setValueCranePosRevDwnSp,
                        onValueChange = updateValueCranePosRevDwnSp,
                        onClick = onSetValueCranePosRevDwnSp,
                        isDecimal = true,
                    )
                }
                //Статус. Индикация срабатывания замедления
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampSettingSensorDwn(
                        isLampStatus = currentCraneRevDwnSp1
                    )
                }
            }

// Ограничение движения крана влево
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения остановка назад (влево)
                Box(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                        .weight(2f)
                ) {
                    BasicTextFilledGiraffeDecimal(
                        placeholder = formatDecimal(craneMinPosition),
                        value = setValueCranePosRevStop,
                        onValueChange = updateValueCranePosRevStop,
                        modifier = Modifier,
                        isDecimal = true,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(2f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_Memory,
                        onClick = onSetValueCranePosRevStop,
                    ) {}
                }
                //Статус. Индикация срабатывания остановки
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampSettingSensorStop(
                        isLampStatus = currentCraneStopRev1
                    )
                }
            }

//Ввод. Реперная точка хода крана
            Row(horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения при котором срабатывает реперная точка
                Box(modifier = Modifier.weight(4f)
                ) {
                    BasicInputRowandTensButton(
                        placeholder = formatDecimal(cranePosCTRLPoint),
                        value = setValueCranePosRepPoint,
                        onValueChange = updateValueCranePosRepPoint,
                        onClick = onSetValueCranePosRepPoint,
                        isDecimal = true,
                    )
                }
                //Статус. Индикация срабатывания точки
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampSettingParameter(
                        isLampStatus = currentCraneRepPoint
                    )
                }
            }

            // Кнопка Активации хода крана
            BasicButtonOnOffStatusPolygon(
                onClick = onSetCraneRailsEn,
                isLampStatus = currentCraneRailsEn
            )
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
                        isLampStatus = currentCraneEncoderMaxDeltaError
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
                        isLampStatus = currentCraneEncoderMaxValueError
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
                        isLampStatus = currentCraneAbsEncErrCanOpen
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
fun CalibrationSensorCranePreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        CalibrationSensorCrane(
            sensorsCraneState = SettingSensorsViewModule.SensorsCraneState(
                currentAbsEncCranePositionWord1 = 1,
                currentAbsEncCranePositionWord2 = 1,
                currentCranePosition = 1f,

                craneLimitStatus = 1,
                currentCraneRailsEn = 1,
                currentCraneStopFwd1 = 1,
                craneMaxPosition = 40F,
                currentCraneStopRev1 = 1,
                craneMinPosition = 0f,
                currentCraneFwdDwnSp1 = 1,
                cranePosFwdDwnSp = 35f,
                currentCraneRevDwnSp1 = 1,
                cranePosRevDwnSp = 5f,

                currentCraneRepPoint = 1,
                cranePosCTRLPoint = 20f,

                currentCraneEncoderMaxDeltaError = 1,
                currentCraneEncoderMaxValueError = 1,
                currentCraneAbsEncErrCanOpen = 1,
                ),
            onSetCraneLimitTestEn = { },

            onSetCraneRailsEn = { },

            setValueCranePosFwdStop = "1",
            updateValueCranePosFwdStop = { },
            onSetValueCranePosFwdStop = { },

            setValueCranePosRevStop = "1",
            updateValueCranePosRevStop = { },
            onSetValueCranePosRevStop = { },

            setValueCranePosFwdDwnSp = "1",
            updateValueCranePosFwdDwnSp = { },
            onSetValueCranePosFwdDwnSp = { },

            setValueCranePosRevDwnSp = "1",
            updateValueCranePosRevDwnSp = { },
            onSetValueCranePosRevDwnSp = { },

            setValueCranePosRepPoint = "1",
            updateValueCranePosRepPoint = { },
            onSetValueCranePosRepPoint = { },

            errorMessageCrane = null,
            logMessageCrane = null,
        )
    }
}