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
import com.example.giraffenew.core.ui_kit.composable.text.BasicTextFilledGiraffeDecimal
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonOnOffStatus
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingParameter
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServiceRefPoint(
    setValueCTRLPointControlInterval: String,
    updateCTRLPointControlInterval: (String) -> Unit,
    onSetValueCTRLPointControlInterval: () -> Unit,
    ctrlPointControlInterval: Float,

    onSetCTRLPointEN: () -> Unit,
    craneCTRLPointEN: Int,

    errorMessageCTRLPoint: String?,
    logMessageCTRLPoint: String?,
){
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //№.1 - Левая область для отображения параметров скорости и ускорений
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
                text = stringResource(R.string.ServiceMenu_Text_Sensor_Ref_Point))
//Отображения текста рабочих параметров датчика реперной точки
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Ref_Point_Diagnostic)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Ref_Point_Interval)
        }

//№.2 - Правая область для отображения и ввода параметров скорости и ускорений
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
// Отступ  и Статус записи данных настройки подъема
            Row(modifier = Modifier.fillMaxWidth()) {
                BasicSettingsName(modifier = Modifier.weight(1f), text = stringResource(R.string.spacer))

                Box (modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                ) {
                    errorMessageCTRLPoint?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp))}
                    logMessageCTRLPoint?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }
//Поле активации датчика реперной точки [вкл/выкл]
            BasicButtonOnOffStatus(
                onClick = onSetCTRLPointEN,
                isLampStatus = craneCTRLPointEN
            )
//Ввод. Интервал контроля датчика [м]
            BasicInputRowandTensButton(
                placeholder = formatDecimal(ctrlPointControlInterval),
                value = setValueCTRLPointControlInterval,
                onValueChange = updateCTRLPointControlInterval,
                onClick = onSetValueCTRLPointControlInterval,
                isDecimal = true,
            )
        }
    }
}

private fun formatDecimal(value: Float): String {
    return String.format("%04.1f", value).replace(',', '.')
}

@Preview
@Composable
fun ServiceRefPointPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ServiceRefPoint(
            setValueCTRLPointControlInterval = "1",
            updateCTRLPointControlInterval = { },
            onSetValueCTRLPointControlInterval = {},
            ctrlPointControlInterval = 1f,

            onSetCTRLPointEN = { },
            craneCTRLPointEN = 1,

            errorMessageCTRLPoint = null,
            logMessageCTRLPoint = null,
        )
    }
}