package com.example.giraffenew.service_menu.ui.drive

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
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicTextFilledGiraffeDecimal
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServiceCrane(
    setValueCraneACC: String,
    onModelChangeCraneACC: (String) -> Unit,
    onSetCraneACC: () -> Unit,
    hmiCraneACC: Float,

    setValueCraneDEC: String,
    onModelChangeCraneDEC: (String) -> Unit,
    onSetCraneDEC: () -> Unit,
    hmiCraneDEC: Float,

    setValueCraneSpdRef1: String,
    onModelChangeCraneSpdRef1: (String) -> Unit,
    onSetCraneSpdRef1: () -> Unit,
    hmiSpdRef1Crane:    Int,

    setValueCraneSpdRef2: String,
    onModelChangeCraneSpdRef2: (String) -> Unit,
    onSetCraneSpdRef2: () -> Unit,
    hmiSpdRef2Crane:    Int,

    setValueCraneSpdRef3: String,
    onModelChangeCraneSpdRef3: (String) -> Unit,
    onSetCraneSpdRef3: () -> Unit,
    hmiSpdRef3Crane:    Int,

    setValueCraneSpdRef4: String,
    onModelChangeCraneSpdRef4: (String) -> Unit,
    onSetCraneSpdRef4: () -> Unit,
    hmiSpdRef4Crane:    Int,

    errorMessageCrane: String?,
    logMessageCrane: String?,
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
                text = stringResource(R.string.ServiceMenu_Text_Crane_Parameters))
//Отображения текста рабочих параметров привода подъема
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Acc)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Dec)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_1)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_2)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_3)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_4)
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
// Отступ  и Статус записи данных настройки подъема
            Row(modifier = Modifier.fillMaxWidth()) {
                BasicSettingsName(modifier = Modifier.weight(1f), text = stringResource(R.string.spacer))

                Box (modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                ) {
                    errorMessageCrane?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp))}
                    logMessageCrane?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }
//Ввод. Разгон. Секунды
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiCraneACC),
                value = setValueCraneACC,
                onValueChange = onModelChangeCraneACC,
                onClick = onSetCraneACC,
                isDecimal = true,
            )
//Ввод. Торможение. Секунды
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiCraneDEC),
                value = setValueCraneDEC,
                onValueChange = onModelChangeCraneDEC,
                onClick = onSetCraneDEC,
                isDecimal = true,
            )
//Ввод. Скорость 1. Об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef1Crane),
                value = setValueCraneSpdRef1,
                valueMin = 0,
                valueMax = 300,
                onValueChange = onModelChangeCraneSpdRef1,
                onClick = onSetCraneSpdRef1
            )
//Ввод. Скорость 2.Об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef2Crane),
                value = setValueCraneSpdRef2,
                valueMin = 0,
                valueMax = 700,
                onValueChange = onModelChangeCraneSpdRef2,
                onClick = onSetCraneSpdRef2
            )
//Ввод. Скорость 3.Об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef3Crane),
                value = setValueCraneSpdRef3,
                valueMin = 0,
                valueMax = 1500,
                onValueChange = onModelChangeCraneSpdRef3,
                onClick = onSetCraneSpdRef3
            )
//Ввод. Скорость 4.Об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef4Crane),
                value = setValueCraneSpdRef4,
                valueMin = 0,
                valueMax = 2000,
                onValueChange = onModelChangeCraneSpdRef4,
                onClick = onSetCraneSpdRef4
            )
        }
    }
}

private fun formatDecimal(value: Float): String {
    return String.format("%.1f", value).replace(',', '.')
}

private fun format(value: Int): String {
    return String.format("%d", value)
}

@Preview
@Composable
fun ServiceCranePreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ServiceCrane(
            setValueCraneACC = "",
            onModelChangeCraneACC = { },
            onSetCraneACC = { },
            hmiCraneACC = 3f,

            setValueCraneDEC = "",
            onModelChangeCraneDEC = { },
            onSetCraneDEC = { },
            hmiCraneDEC = 3f,

            setValueCraneSpdRef1 = "2",
            onModelChangeCraneSpdRef1 = { },
            onSetCraneSpdRef1 = { },

            setValueCraneSpdRef2 = "2",
            onModelChangeCraneSpdRef2 = { },
            onSetCraneSpdRef2 = { },

            setValueCraneSpdRef3 = "2",
            onModelChangeCraneSpdRef3 = { },
            onSetCraneSpdRef3 = { },

            setValueCraneSpdRef4 = "2",
            onModelChangeCraneSpdRef4 = { },
            onSetCraneSpdRef4 = { },

            errorMessageCrane = null,
            logMessageCrane = null,

            hmiSpdRef1Crane = 160,
            hmiSpdRef2Crane = 300,
            hmiSpdRef3Crane = 600,
            hmiSpdRef4Crane = 1500,
        )
    }
}