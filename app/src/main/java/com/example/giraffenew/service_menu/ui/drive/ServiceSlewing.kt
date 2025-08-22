package com.example.giraffenew.service_menu.ui.drive

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
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicTextFilledGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServiceSlewing(
    setValueSlewACC: String,
    onModelChangeSlewACC: (String) -> Unit,
    onSetSlewACC: () -> Unit,

    setValueSlewDEC: String,
    onModelChangeSlewDEC: (String) -> Unit,
    onSetSlewDEC: () -> Unit,

    setValueSlewSpdRef1: String,
    onModelChangeSlewSpdRef1: (String) -> Unit,
    onSetSlewSpdRef1: () -> Unit,

    setValueSlewSpdRef2: String,
    onModelChangeSlewSpdRef2: (String) -> Unit,
    onSetSlewSpdRef2: () -> Unit,

    setValueSlewSpdRef3: String,
    onModelChangeSlewSpdRef3: (String) -> Unit,
    onSetSlewSpdRef3: () -> Unit,

    setValueSlewSpdRef4: String,
    onModelChangeSlewSpdRef4: (String) -> Unit,
    onSetSlewSpdRef4: () -> Unit,

    setValueSlewSpdStopButton: String,
    onModelChangeSlewSpdStopButton: (String) -> Unit,
    onSetSlewSpdStopButton: () -> Unit,

    errorMessageSlew: String?,
    logMessageSlew: String?,
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
                text = stringResource(R.string.ServiceMenu_Text_Slewing_Parameters))
//Отображения текста рабочих параметров привода подъема
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Acc)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Dec)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_1)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_2)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_3)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_4)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Stoping_Speed)

            Box (modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp)
            ) {
                errorMessageSlew?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))}
                logMessageSlew?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp)) }
            }

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
// Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.ServiceMenu_Text_Setting))
//Ввод. Разгон. Секунды
            BasicInputRowandTensButton(
                placeholder = "8.0",
                value = setValueSlewACC,
                onValueChange = onModelChangeSlewACC,
                onClick = onSetSlewACC,
                isDecimal = true,
            )
//Ввод. Торможение. Секунды
            BasicInputRowandTensButton(
                placeholder = "12.0",
                value = setValueSlewDEC,
                onValueChange = onModelChangeSlewDEC,
                onClick = onSetSlewDEC,
                isDecimal = true,
            )
//Ввод. Скорость 1. Об/мин
            BasicInputRowandButton(
                placeholder = "150",
                value = setValueSlewSpdRef1,
                onValueChange = onModelChangeSlewSpdRef1,
                onClick = onSetSlewSpdRef1
            )
//Ввод. Скорость 2.Об/мин
            BasicInputRowandButton(
                placeholder = "450",
                value = setValueSlewSpdRef2,
                onValueChange = onModelChangeSlewSpdRef2,
                onClick = onSetSlewSpdRef2
            )
//Ввод. Скорость 3.Об/мин
            BasicInputRowandButton(
                placeholder = "900",
                value = setValueSlewSpdRef3,
                onValueChange = onModelChangeSlewSpdRef3,
                onClick = onSetSlewSpdRef3
            )
//Ввод. Скорость 4.Об/мин
            BasicInputRowandButton(
                placeholder = "1500",
                value = setValueSlewSpdRef4,
                onValueChange = onModelChangeSlewSpdRef4,
                onClick = onSetSlewSpdRef4
            )
//Ввод. Остановка от кнопки, об/мин
            BasicInputRowandButton(
                placeholder = "150",
                value = setValueSlewSpdStopButton,
                onValueChange = onModelChangeSlewSpdStopButton,
                onClick = onSetSlewSpdStopButton,
            )
        }
    }
}

@Preview
@Composable
fun ServiceSlewingPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ServiceSlewing(
            setValueSlewACC = "8",
            onModelChangeSlewACC = { },
            onSetSlewACC ={ },

            setValueSlewDEC = "12",
            onModelChangeSlewDEC = { },
            onSetSlewDEC ={ },

            setValueSlewSpdRef1 = "150",
            onModelChangeSlewSpdRef1 = { },
            onSetSlewSpdRef1 ={ },

            setValueSlewSpdRef2 = "700",
            onModelChangeSlewSpdRef2 = { },
            onSetSlewSpdRef2 ={ },

            setValueSlewSpdRef3 = "1500",
            onModelChangeSlewSpdRef3 = { },
            onSetSlewSpdRef3 ={ },

            setValueSlewSpdRef4 = "3000",
            onModelChangeSlewSpdRef4 = { },
            onSetSlewSpdRef4 ={ },

            setValueSlewSpdStopButton = "1500",
            onModelChangeSlewSpdStopButton = { },
            onSetSlewSpdStopButton ={ },

            errorMessageSlew = null,
            logMessageSlew = null
        )
    }
}