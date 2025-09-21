package com.example.giraffenew.service_menu.ui.sensors

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
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonOnOffStatus
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandThousandButton
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServicePosition(
//СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКОВ ПОЛОЖЕНИЯ
    setValueSlewNumberTeethOPU: String,
    updateValueSlewNumberTeethOPU: (String) -> Unit,
    onSetSlewNumberTeethOPU: () -> Unit,
    hmiSlewNumberTeethOPU: Int,

    setValueSlewNumberTeethGearSensor: String,
    updateValueSlewNumberTeethGearSensor: (String) -> Unit,
    onSetSlewNumberTeethGearSensor: () -> Unit,
    hmiSlewNumberTeethGearSensor: Int,

    setValueSlewSingleTurnStepsSensor: String,
    updateValueSlewSingleTurnStepsSensor: (String) -> Unit,
    onSetSlewSingleTurnStepsSensor: () -> Unit,
    hmiSlewSingleTurnStepsSensor: Int,

    setValueSlewRatioK: String,
    updateValueSlewRatioK: (String) -> Unit,
    onSetSlewRatioK: () -> Unit,
    hmiSlewRatioK: Float,

    onSetAbsEncDeltaControlEn: () -> Unit,
    currentAbsEncDeltaControlEn: Int,

    setValueUDIHoistAbsEncMaxDeltaEn: String,
    onModelChangeUDIHoistAbsEncMaxDeltaEn: (String) -> Unit,
    onSetUDIHoistAbsEncMaxDeltaEn: () -> Unit,
    hmiHoistAbsEncMaxDeltaEn: Int,

    setValueUDISlewAbsEncMaxDeltaEn: String,
    onModeChangelUDISlewAbsEncMaxDeltaEn: (String) -> Unit,
    onSetUDISlewAbsEncMaxDeltaEn: () -> Unit,
    hmiSlewAbsEncMaxDeltaEn: Int,

    setValueUDITrolleyAbsEncMaxDeltaEn: String,
    onModelChangeUDITrolleyAbsEncMaxDeltaEn: (String) -> Unit,
    onSetUDITrolleyAbsEncMaxDeltaEn: () -> Unit,
    hmiTrolleyAbsEncMaxDeltaEn: Int,

    setValueUDICraneAbsEncMaxDeltaEn: String,
    onModelChangeUDICraneAbsEncMaxDeltaEn: (String) -> Unit,
    onSetUDICraneAbsEncMaxDeltaEn: () -> Unit,
    hmiCraneAbsEncMaxDeltaEn: Int,

    onSetAbsEncDebugModeHoistEn: () -> Unit,
    currentAbsEncDebugModeHoist: Int,

    onSetAbsEncDebugModeSlewEn: () -> Unit,
    currentAbsEncDebugModeSlew: Int,

    errorMessageAbsEnc: String?,
    logMessageAbsEnc: String?,


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
                text = stringResource(R.string.ServiceMenu_Text_Sensor_Position))
//Отображения текста рабочих параметров датчика поворота
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Slew_Number_Teeth_OPU)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Slew_Number_Teeth_Gear_Sensor)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Slew_Single_Turn_Steps_Sensor)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Slew_Ratio_K)
// Отступ
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.spacer))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensors_Control)
// Отступ
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.spacer))
// Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.ServiceMenu_Sensor_Misalignment))
//Отображения текста рабочих параметров датчиков положения

            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Hoist)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Trolley)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Slewing)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Crane)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Hoist_Activate)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Slewing_Activate)
        }

//№.2 - Правая область для отображения и ввода параметров датчиков
        Column(
            modifier = Modifier
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
// Отступ  и Статус записи данных настройки подъема
            Row(modifier = Modifier.fillMaxWidth()) {
                BasicSettingsName(modifier = Modifier.weight(1f), text = stringResource(R.string.spacer))

                Box (modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                ) {
                    errorMessageAbsEnc?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp))}
                    logMessageAbsEnc?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }
//Ввод. Число зубьев ОПУ крана
            BasicInputRowandThousandButton(
                placeholder = format(hmiSlewNumberTeethOPU),
                value = setValueSlewNumberTeethOPU,
                onValueChange = updateValueSlewNumberTeethOPU,
                onClick = onSetSlewNumberTeethOPU,
                isDecimal = true,
            )
//Ввод. Число зубьев шестерни датчика поворота крана
            BasicInputRowandThousandButton(
                placeholder = format(hmiSlewNumberTeethGearSensor),
                value = setValueSlewNumberTeethGearSensor,
                onValueChange = updateValueSlewNumberTeethGearSensor,
                onClick = onSetSlewNumberTeethGearSensor,
                isDecimal = true,
            )
//Отступ
            Spacer(modifier = Modifier.size(20.dp))
//Ввод. Количество импульсов за оборот датчика
            BasicInputRowandThousandButton(
                placeholder = format(hmiSlewSingleTurnStepsSensor),
                value = setValueSlewSingleTurnStepsSensor,
                onValueChange = updateValueSlewSingleTurnStepsSensor,
                onClick = onSetSlewSingleTurnStepsSensor,
                isDecimal = true,
            )
//Отступ
            Spacer(modifier = Modifier.size(20.dp))
//Ввод. Коэффициент передачи ОПУ и шестерни датчика
            BasicInputRowandThousandButton(
                placeholder = formatHundred(hmiSlewRatioK),
                value = setValueSlewRatioK,
                onValueChange = updateValueSlewRatioK,
                onClick = onSetSlewRatioK,
                isDecimal = true,
            )
//Отступ
            Spacer(modifier = Modifier.size(30.dp))

//Контроль датчиков [вкл/выкл]
            BasicButtonOnOffStatus(
                onClick = onSetAbsEncDeltaControlEn,
                isLampStatus = currentAbsEncDeltaControlEn
            )
// Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
//Ввод. Макс. рассогласование датчика подъема
            BasicInputRowandButton(
                placeholder = format(hmiHoistAbsEncMaxDeltaEn),
                value = setValueUDIHoistAbsEncMaxDeltaEn,
                valueMin = 0,
                valueMax = 65535,
                onValueChange = onModelChangeUDIHoistAbsEncMaxDeltaEn,
                onClick = onSetUDIHoistAbsEncMaxDeltaEn
            )
//Ввод. Макс. рассогласование датчика поворота
            BasicInputRowandButton(
                placeholder = format(hmiSlewAbsEncMaxDeltaEn),
                value = setValueUDISlewAbsEncMaxDeltaEn,
                valueMin = 0,
                valueMax = 65535,
                onValueChange = onModeChangelUDISlewAbsEncMaxDeltaEn,
                onClick = onSetUDISlewAbsEncMaxDeltaEn
            )
//Ввод. Макс. рассогласование датчика тележки
            BasicInputRowandButton(
                placeholder = format(hmiTrolleyAbsEncMaxDeltaEn),
                value = setValueUDITrolleyAbsEncMaxDeltaEn,
                valueMin = 0,
                valueMax = 65535,
                onValueChange = onModelChangeUDITrolleyAbsEncMaxDeltaEn,
                onClick = onSetUDITrolleyAbsEncMaxDeltaEn
            )
//Ввод. Макс. рассогласование датчика хода крана
            BasicInputRowandButton(
                placeholder = format(hmiCraneAbsEncMaxDeltaEn),
                value = setValueUDICraneAbsEncMaxDeltaEn,
                valueMin = 0,
                valueMax = 65535,
                onValueChange = onModelChangeUDICraneAbsEncMaxDeltaEn,
                onClick = onSetUDICraneAbsEncMaxDeltaEn
            )
//Поле деактивации датчика подъема
            BasicButtonOnOffStatus(
                onClick = onSetAbsEncDebugModeHoistEn,
                isLampStatus = currentAbsEncDebugModeHoist
            )
//Поле деактивации датчика поворота
            BasicButtonOnOffStatus(
                onClick = onSetAbsEncDebugModeSlewEn,
                isLampStatus = currentAbsEncDebugModeSlew
            )
// Заголовок. "Запрещено отключать оба датчика!.
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.ServiceMenu_Text_Sensor_Disconnect))

        }
    }
}

private fun format(value: Int): String {
    //return String.format("%.1f", value).padStart(5, '0')
    return String.format("%05d", value)
}

private fun formatDecimal(value: Float): String {
    return String.format("%02.1f", value).replace(',', '.')
}

private fun formatHundred(value: Float): String {
    return String.format("%03.2f", value).replace(',', '.')
}

@Preview
@Composable
fun ServicePositionPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
    }
}