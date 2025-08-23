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
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonOnOffStatus
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandThousandButton
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServicePosition(
//СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКОВ ПОЛОЖЕНИЯ
    setValueAbsEncRatioHoistGK: String,
    onModelChangeAbsEncRatioHoistGK: (String) -> Unit,
    onSetAbsEncRatioHoistGK: () -> Unit,

    setValueAbsEncRatioSlewGK: String,
    onModelChangeAbsEncRatioSlewGK: (String) -> Unit,
    onSetAbsEncRatioSlewGK: () -> Unit,

    setValueAbsEncRatioTrolleyGK: String,
    onModelChangeAbsEncRatioTrolleyGK: (String) -> Unit,
    onSetAbsEncRatioTrolleyGK: () -> Unit,

    setValueAbsEncRatioCraneGK: String,
    onModelChangeAbsEncRatioCraneGK: (String) -> Unit,
    onSetAbsEncRatioCraneGK: () -> Unit,

    onSetAbsEncDeltaControlEn: () -> Unit,
    currentAbsEncDeltaControlEn: Int,

    setValueUDIHoistAbsEncMaxDeltaEn: String,
    onModelChangeUDIHoistAbsEncMaxDeltaEn: (String) -> Unit,
    onSetUDIHoistAbsEncMaxDeltaEn: () -> Unit,

    setValueUDISlewAbsEncMaxDeltaEn: String,
    onModeChangelUDISlewAbsEncMaxDeltaEn: (String) -> Unit,
    onSetUDISlewAbsEncMaxDeltaEn: () -> Unit,

    setValueUDITrolleyAbsEncMaxDeltaEn: String,
    onModelChangeUDITrolleyAbsEncMaxDeltaEn: (String) -> Unit,
    onSetUDITrolleyAbsEncMaxDeltaEn: () -> Unit,

    setValueUDICraneAbsEncMaxDeltaEn: String,
    onModelChangeUDICraneAbsEncMaxDeltaEn: (String) -> Unit,
    onSetUDICraneAbsEncMaxDeltaEn: () -> Unit,

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
//Отображения текста рабочих параметров датчиков положения
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Hoist_Coeff)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Slewing_Coeff)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Trolley_Coeff)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Crane_Coeff)
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

            Box (modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp)
            ) {
                errorMessageAbsEnc?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))}
                logMessageAbsEnc?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp)) }
            }

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
// Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.ServiceMenu_Text_Setting))
//Ввод. Коэффициент датчика подъема
            BasicInputRowandThousandButton(
                placeholder = "1.0000",
                value = setValueAbsEncRatioHoistGK,
                onValueChange = onModelChangeAbsEncRatioHoistGK,
                onClick = onSetAbsEncRatioHoistGK,
                isDecimal = true,
            )
//Ввод. Коэффициент датчика поворота
            BasicInputRowandThousandButton(
                placeholder = "1.0000",
                value = setValueAbsEncRatioSlewGK,
                onValueChange = onModelChangeAbsEncRatioSlewGK,
                onClick = onSetAbsEncRatioSlewGK,
                isDecimal = true,
            )
//Ввод. Коэффициент датчика тележки
            BasicInputRowandThousandButton(
                placeholder = "1.0000",
                value = setValueAbsEncRatioTrolleyGK,
                onValueChange = onModelChangeAbsEncRatioTrolleyGK,
                onClick = onSetAbsEncRatioTrolleyGK,
                isDecimal = true,
            )
//Ввод. Коэффициент датчика хода крана
            BasicInputRowandThousandButton(
                placeholder = "1.0000",
                value = setValueAbsEncRatioCraneGK,
                onValueChange = onModelChangeAbsEncRatioCraneGK,
                onClick = onSetAbsEncRatioCraneGK,
                isDecimal = true,
            )
// Отступ
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.spacer))

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
                placeholder = "20000",
                value = setValueUDIHoistAbsEncMaxDeltaEn,
                valueMin = 0,
                valueMax = 65535,
                onValueChange = onModelChangeUDIHoistAbsEncMaxDeltaEn,
                onClick = onSetUDIHoistAbsEncMaxDeltaEn
            )
//Ввод. Макс. рассогласование датчика поворота
            BasicInputRowandButton(
                placeholder = "20000",
                value = setValueUDISlewAbsEncMaxDeltaEn,
                valueMin = 0,
                valueMax = 65535,
                onValueChange = onModeChangelUDISlewAbsEncMaxDeltaEn,
                onClick = onSetUDISlewAbsEncMaxDeltaEn
            )
//Ввод. Макс. рассогласование датчика тележки
            BasicInputRowandButton(
                placeholder = "20000",
                value = setValueUDITrolleyAbsEncMaxDeltaEn,
                valueMin = 0,
                valueMax = 65535,
                onValueChange = onModelChangeUDITrolleyAbsEncMaxDeltaEn,
                onClick = onSetUDITrolleyAbsEncMaxDeltaEn
            )
//Ввод. Макс. рассогласование датчика хода крана
            BasicInputRowandButton(
                placeholder = "20000",
                value = setValueUDICraneAbsEncMaxDeltaEn,
                valueMin = 0,
                valueMax = 65535,
                onValueChange = onModelChangeUDICraneAbsEncMaxDeltaEn,
                onClick = onSetUDICraneAbsEncMaxDeltaEn
            )
//Поле активации датчика подъема
            BasicButtonOnOffStatus(
                onClick = onSetAbsEncDebugModeHoistEn,
                isLampStatus = currentAbsEncDebugModeHoist
            )
//Поле активации датчика поворота
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

@Preview
@Composable
fun ServicePositionPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
    }
}