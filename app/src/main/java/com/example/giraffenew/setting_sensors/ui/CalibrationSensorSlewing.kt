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
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsNameInfo
import com.example.giraffenew.core.ui_kit.composable.text.BasicTextFilledGiraffeDecimal
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun CalibrationSensorSlewing(
    currentAbsEncSlewPositionWord1: Int,
    currentAbsEncSlewPositionWord2: Int,
    currentSlewPosition: Float,

    onSetSlewZeroMemEn: () -> Unit,

    setValueSlewCalcAngle: String,
    updateValueSlewCalcAngle: (String) -> Unit,
    onSetValueSlewCalcAngle: () -> Unit,

    setValueSlewMaxAngle: String,
    updateValueSlewMaxAngle: (String) -> Unit,
    onSetValueSlewMaxAngle: () -> Unit,

    currentSlewEncoderMaxDeltaError: Int,
    currentSlewEncoderMaxValueError: Int,
    currentSlewAbsEncErrCanOpen: Int,

    errorMessageSlew: String?,
    logMessageSlew: String?,
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
                text = stringResource(R.string.SettingSensors_Text_Slewing_Calibration))
            //Датчик
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.SettingSensors_Text_Sensor)
            //Угол поворота
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Slewing_Corner)
            //Установка 0
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Slewing_Installation_Zero)
            //Установка угла
            //BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Slewing_Calibration_Corner)
            //Установка максимального угла
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Slewing_Calibration_Corner_Max)

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.SettingSensors_Text_Sensor_Status))

            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Sensor_Alarm_1_1)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Sensor_Alarm_1_2)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingSensors_Text_Sensor_Alarm_2)

            // Информация!
            BasicSettingsNameInfo(modifier = Modifier, text = stringResource(R.string.SettingSensors_Text_Slewing_Information_1))
            BasicSettingsNameInfo(modifier = Modifier, text = stringResource(R.string.SettingSensors_Text_Slewing_Information_11))
            BasicSettingsNameInfo(modifier = Modifier, text = stringResource(R.string.SettingSensors_Text_Slewing_Information_12))
            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
            BasicSettingsNameInfo(modifier = Modifier, text = stringResource(R.string.SettingSensors_Text_Slewing_Information_2))

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
                    errorMessageSlew?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp))}
                    logMessageSlew?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }

            // Отображение текущего значения датчика хода крана
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = formatAbsEncPositionPosition(currentAbsEncSlewPositionWord1, currentAbsEncSlewPositionWord2)
                )
            }
            // Отображение текущего угла поворота
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = formatDecimal(currentSlewPosition)
                )
            }
        // Кнопка установки нуля градусов
            BasicButtonGiraffe(
                modifier = Modifier.fillMaxWidth(),
                text = R.string.Button_Memory,
                onClick = onSetSlewZeroMemEn,
            ) {}

/**Исключена за ненадобностью
            //Ввод. Установка угла в градусах
            Row (horizontalArrangement = Arrangement.SpaceBetween) {
            //Поле ввода значения текущего угла поворота стрелы
            Box (modifier = Modifier
            .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
            .weight(1f)
            ) {
            BasicTextFilledGiraffeDecimal(
            placeholder = "000",
            value = setValueSlewCalcAngle,
            onValueChange = updateValueSlewCalcAngle,
            modifier = Modifier,
            )
            }
            //Кнопка ввода
            Box(modifier = Modifier.weight(1f)) {
            BasicButtonGiraffe(
            modifier = Modifier,
            text = R.string.Button_Memory,
            onClick = onSetValueSlewCalcAngle,
            ) {}
            }
            }
*/


            //Ввод. Установка максимального угла в градусах
            Row (horizontalArrangement = Arrangement.SpaceBetween) {
                //Поле ввода значения максимального угла поворота стрелы
                Box (modifier = Modifier
                    .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                    .weight(1f)
                ) {
                    BasicTextFilledGiraffeDecimal(
                        placeholder = "0000",
                        value = setValueSlewMaxAngle,
                        onValueChange = updateValueSlewMaxAngle,
                        modifier = Modifier,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(1f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_Memory,
                        onClick = onSetValueSlewMaxAngle,
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
                        isLampStatus = currentSlewEncoderMaxDeltaError
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
                        isLampStatus = currentSlewEncoderMaxValueError
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
                        isLampStatus = currentSlewAbsEncErrCanOpen
                    )
                }
            }




        }
    }
}


private fun formatDecimal(value: Float): String {
    return String.format("%04.1f", value).replace(',', '.')
}

private fun formatAbsEncPositionPosition(value1: Int, value2: Int): String {
    return String.format("%d%05d", value2, value1)
}

@Preview
@Composable
fun CalibrationSensorSlewingPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        CalibrationSensorSlewing(
            currentAbsEncSlewPositionWord1 = 1,
            currentAbsEncSlewPositionWord2 = 1,
            currentSlewPosition = 1f,
            onSetSlewZeroMemEn = { },
            setValueSlewCalcAngle = "`1",
            updateValueSlewCalcAngle = { },
            onSetValueSlewCalcAngle = { },
            setValueSlewMaxAngle = "`1",
            updateValueSlewMaxAngle = { },
            onSetValueSlewMaxAngle = { },

            currentSlewEncoderMaxDeltaError = 1,
            currentSlewEncoderMaxValueError = 1,
            currentSlewAbsEncErrCanOpen = 1,

            errorMessageSlew = null,
            logMessageSlew = null
        )
    }
}