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
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServiceTrolley(
    setValueTrolleyACC: String,
    onModelChangeTrolleyACC: (String) -> Unit,
    onSetTrolleyACC: () -> Unit,
    hmiTrolleyACC :  Float,

    setValueTrolleyDEC: String,
    onModelChangeTrolleyDEC: (String) -> Unit,
    onSetTrolleyDEC: () -> Unit,
    hmiTrolleyDEC :  Float,

    setValueTrolleySpdRef1: String,
    onModelChangeTrolleySpdRef1: (String) -> Unit,
    onSetTrolleySpdRef1: () -> Unit,
    hmiSpdRef1Trolley:  Int,

    setValueTrolleySpdRef2: String,
    onModelChangeTrolleySpdRef2: (String) -> Unit,
    onSetTrolleySpdRef2: () -> Unit,
    hmiSpdRef2Trolley:  Int,

    setValueTrolleySpdRef3: String,
    onModelChangeTrolleySpdRef3: (String) -> Unit,
    onSetTrolleySpdRef3: () -> Unit,
    hmiSpdRef3Trolley:  Int,

    setValueTrolleySpdRef4: String,
    onModelChangeTrolleySpdRef4: (String) -> Unit,
    onSetTrolleySpdRef4: () -> Unit,
    hmiSpdRef4Trolley:  Int,

    setValueTrolleySpdNom: String,
    updateTrolleySpdNom: (String) -> Unit,
    onSetTrolleySpdNom: () -> Unit,
    hmiSpdRefNomTrolley: Int,

    setValueTrolleyOverSpd: String,
    onModelChangeTrolleyOverSpd: (String) -> Unit,
    onSetTrolleyOverSpd: () -> Unit,
    hmiSpdRefOverSpd:  Float,

    errorMessageTrolley: String?,
    logMessageTrolley: String?,

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
                text = stringResource(R.string.ServiceMenu_Text_Trolley_Parameters))
//Отображения текста рабочих параметров привода подъема
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Acc)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Dec)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_1)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_2)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_3)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_4)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_Nom)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Trolley_Speed_SS_Ban)
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
// Отступ  и Статус записи данных настройки тележки
            Row(modifier = Modifier.fillMaxWidth()) {
                BasicSettingsName(modifier = Modifier.weight(1f), text = stringResource(R.string.spacer))

                Box (modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                ) {
                    errorMessageTrolley?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp))}
                    logMessageTrolley?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }
//Ввод. Разгон. Секунды
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiTrolleyACC),
                value = setValueTrolleyACC,
                onValueChange = onModelChangeTrolleyACC,
                onClick = onSetTrolleyACC,
                isDecimal = true,
            )
//Ввод. Торможение. Секунды
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiTrolleyDEC),
                value = setValueTrolleyDEC,
                onValueChange = onModelChangeTrolleyDEC,
                onClick = onSetTrolleyDEC,
                isDecimal = true,
            )
//Ввод. Скорость 1. Об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef1Trolley),
                value = setValueTrolleySpdRef1,
                valueMin = 0,
                valueMax = 9999,
                onValueChange = onModelChangeTrolleySpdRef1,
                onClick = onSetTrolleySpdRef1
            )
//Ввод. Скорость 2.Об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef2Trolley),
                value = setValueTrolleySpdRef2,
                valueMin = 0,
                valueMax = 9999,
                onValueChange = onModelChangeTrolleySpdRef2,
                onClick = onSetTrolleySpdRef2
            )
//Ввод. Скорость 3.Об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef3Trolley),
                value = setValueTrolleySpdRef3,
                valueMin = 0,
                valueMax = 9999,
                onValueChange = onModelChangeTrolleySpdRef3,
                onClick = onSetTrolleySpdRef3
            )
//Ввод. Скорость 4.Об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRef4Trolley),
                value = setValueTrolleySpdRef4,
                valueMin = 0,
                valueMax = 9999,
                onValueChange = onModelChangeTrolleySpdRef4,
                onClick = onSetTrolleySpdRef4
            )
//Ввод. Номинальная скорость 4.Об/мин
            BasicInputRowandButton(
                placeholder = format(hmiSpdRefNomTrolley),
                value = setValueTrolleySpdNom,
                valueMin = 0,
                valueMax = 9999,
                onValueChange = updateTrolleySpdNom,
                onClick = onSetTrolleySpdNom
            )

//Ввод. Запрет движения на сверхсинхронной скорости, тонны
            BasicInputRowandTensButton(
                placeholder = formatDecimal(hmiSpdRefOverSpd),
                value = setValueTrolleyOverSpd,
                onValueChange = onModelChangeTrolleyOverSpd,
                onClick = onSetTrolleyOverSpd,
                isDecimal = true
            )
        }

    }
}

private fun formatDecimal(value: Float): String {
    return String.format("%04.1f", value).replace(',', '.')
}

private fun format(value: Int): String {
    return String.format("%04d", value)
}

@Preview
@Composable
fun ServiceTrolleyPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ServiceTrolley(
            setValueTrolleyACC = "4",
            onModelChangeTrolleyACC = { },
            onSetTrolleyACC = { },

            setValueTrolleyDEC = "4",
            onModelChangeTrolleyDEC = { },
            onSetTrolleyDEC = { },

            setValueTrolleySpdRef1 = "150",
            onModelChangeTrolleySpdRef1 = { },
            onSetTrolleySpdRef1 = { },

            setValueTrolleySpdRef2 = "500",
            onModelChangeTrolleySpdRef2 = { },
            onSetTrolleySpdRef2 = { },

            setValueTrolleySpdRef3 = "1000",
            onModelChangeTrolleySpdRef3 = { },
            onSetTrolleySpdRef3 = { },

            setValueTrolleySpdRef4 = "1500",
            onModelChangeTrolleySpdRef4 = { },
            onSetTrolleySpdRef4 = { },

            setValueTrolleySpdNom = "1500",
            updateTrolleySpdNom = { },
            onSetTrolleySpdNom = { },

            setValueTrolleyOverSpd = "5",
            onModelChangeTrolleyOverSpd = { },
            onSetTrolleyOverSpd = { },

            errorMessageTrolley = null,
            logMessageTrolley = null,

            hmiTrolleyACC = 5f,
            hmiTrolleyDEC = 5f,
            hmiSpdRef1Trolley = 150,
            hmiSpdRef2Trolley = 300,
            hmiSpdRef3Trolley = 600,
            hmiSpdRef4Trolley = 1500,
            hmiSpdRefOverSpd = 5f,
            hmiSpdRefNomTrolley = 5,
        )
    }
}