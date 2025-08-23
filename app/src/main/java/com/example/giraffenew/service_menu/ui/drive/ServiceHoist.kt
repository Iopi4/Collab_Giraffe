package com.example.giraffenew.service_menu.ui.drive

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServiceHoist(
    setValueHoistACC: String,
    onModelChangeHoistACC: (String) -> Unit,
    onSetHoistACC: () -> Unit,

    setValueHoistDEC: String,
    onModelChangeHoistDEC: (String) -> Unit,
    onSetHoistDEC: () -> Unit,

    setValueHoistSpdRef1: String,
    onModelChangeHoistSpdRef1: (String) -> Unit,
    onSetHoistSpdRef1: () -> Unit,

    setValueHoistSpdRef2: String,
    onModelChangeHoistSpdRef2: (String) -> Unit,
    onSetHoistSpdRef2: () -> Unit,

    setValueHoistSpdRef3: String,
    onModelChangeHoistSpdRef3: (String) -> Unit,
    onSetHoistSpdRef3: () -> Unit,

    setValueHoistSpdRef4: String,
    onModelChangeHoistSpdRef4: (String) -> Unit,
    onSetHoistSpdRef4: () -> Unit,

    setValueHoistSpdRefButton: String,
    onModelChangeHoistSpdRefButton: (String) -> Unit,
    onSetHoistSpdRefButton: () -> Unit,

    setValueHoistSpdRefMax: String,
    onModelChangeHoistSpdRefMax: (String) -> Unit,
    onSetHoistSpdRefMax: () -> Unit,

    setValueHoistMontageACC: String,
    onModelChangeHoistMontageACC: (String) -> Unit,
    onSetHoistMontageACC: () -> Unit,

    setValueHoistMontageDEC: String,
    onModelChangeHoistMontageDEC: (String) -> Unit,
    onSetHoistMontageDEC: () -> Unit,

    setValueHoistMontageSpdRef1: String,
    onModelChangeHoistMontageSpdRef1: (String) -> Unit,
    onSetHoistMontageSpdRef1: () -> Unit,

    setValueHoistMontageSpdRef2: String,
    onModelChangeHoistMontageSpdRef2: (String) -> Unit,
    onSetHoistMontageSpdRef2: () -> Unit,

    errorMessageHoist: String?,
    logMessageHoist: String?,

    errorMessageHoistMontage: String?,
    logMessageHoistMontage: String?,

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
                text = stringResource(R.string.ServiceMenu_Text_Hoist_Parameters))
//Отображения текста рабочих параметров привода подъема
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Acc)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Dec)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_1)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_2)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_3)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_4)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Max_Speed_SS)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Hoist_SpdRefButton)

            //Spacer(modifier = Modifier.size(30.dp))
            Box (modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp)
            ) {
                errorMessageHoist?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))}
                logMessageHoist?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp)) }
            }
// Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.ServiceMenu_Text_Hoist_Montage))
//Отображения текста параметров привода подъема в режиме монтаж
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Acc)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Dec)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_1)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Speed_2)

            Box (modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp)
            ) {
                errorMessageHoistMontage?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))}
                logMessageHoistMontage?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp)) }
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
//Ввод. Разгон. сек
            BasicInputRowandTensButton(
                placeholder = "3.0",
                value = setValueHoistACC,
                onValueChange = onModelChangeHoistACC,
                onClick = onSetHoistACC,
                isDecimal = true,
            )
//Ввод. Торможение. сек
            BasicInputRowandTensButton(
                placeholder = "3.0",
                value = setValueHoistDEC,
                onValueChange = onModelChangeHoistDEC,
                onClick = onSetHoistDEC,
                isDecimal = true,
            )
//Ввод. Скорость 1. об/мин
            BasicInputRowandButton(
                placeholder = "250",
                value = setValueHoistSpdRef1,
                valueMin = 0,
                valueMax = 9999,
                onValueChange = onModelChangeHoistSpdRef1,
                onClick = onSetHoistSpdRef1
            )
//Ввод. Скорость 2. об/мин
            BasicInputRowandButton(
                placeholder = "450",
                value = setValueHoistSpdRef2,
                valueMin = 0,
                valueMax = 9999,
                onValueChange = onModelChangeHoistSpdRef2,
                onClick = onSetHoistSpdRef2
            )
//Ввод. Скорость 3. об/мин
            BasicInputRowandButton(
                placeholder = "900",
                value = setValueHoistSpdRef3,
                valueMin = 0,
                valueMax = 9999,
                onValueChange = onModelChangeHoistSpdRef3,
                onClick = onSetHoistSpdRef3
            )
//Ввод. Скорость 4. об/мин
            BasicInputRowandButton(
                placeholder = "1500",
                value = setValueHoistSpdRef4,
                valueMin = 0,
                valueMax = 9999,
                onValueChange = onModelChangeHoistSpdRef4,
                onClick = onSetHoistSpdRef4
            )
//Ввод. Сверх.синхр. скорость, об/мин
            BasicInputRowandButton(
                placeholder = "3000",
                value = setValueHoistSpdRefMax,
                valueMin = 0,
                valueMax = 9999,
                onValueChange = onModelChangeHoistSpdRefMax,
                onClick = onSetHoistSpdRefMax
            )
//Ввод. Медленная скорость при нажатии кнопки, об/мин
            BasicInputRowandButton(
                placeholder = "90",
                valueMin = 0,
                valueMax = 999,
                value = setValueHoistSpdRefButton,
                onValueChange = onModelChangeHoistSpdRefButton,
                onClick = onSetHoistSpdRefButton,
            )

            Spacer(modifier = Modifier.size(30.dp))
// Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
//Настройка скоростей и ускорения в монтажном режиме для подъема
//Ввод. Разгон. сек
            BasicInputRowandTensButton(
                placeholder = "3.0",
                value = setValueHoistMontageACC,
                onValueChange = onModelChangeHoistMontageACC,
                onClick = onSetHoistMontageACC,
                isDecimal = true,
            )
//Ввод. Торможение. сек
            BasicInputRowandTensButton(
                placeholder = "3.0",
                value = setValueHoistMontageDEC,
                onValueChange = onModelChangeHoistMontageDEC,
                onClick = onSetHoistMontageDEC,
                isDecimal = true,
            )
//Ввод. Скорость 1. об/мин
            BasicInputRowandButton(
                placeholder = "250",
                value = setValueHoistMontageSpdRef1,
                valueMin = 0,
                valueMax = 99,
                onValueChange = onModelChangeHoistMontageSpdRef1,
                onClick = onSetHoistMontageSpdRef1
            )
//Ввод. Скорость 2. об/мин
            BasicInputRowandButton(
                placeholder = "450",
                valueMin = 0,
                valueMax = 99,
                value = setValueHoistMontageSpdRef2,
                onValueChange = onModelChangeHoistMontageSpdRef2,
                onClick = onSetHoistMontageSpdRef2
            )
        }
    }
}

@Preview
@Composable
fun ServiceHoistPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ServiceHoist(
            setValueHoistACC = "2",
            onModelChangeHoistACC = { },
            onSetHoistACC = { },

            setValueHoistDEC = "2",
            onModelChangeHoistDEC = { },
            onSetHoistDEC = { },

            setValueHoistSpdRef1 = "2",
            onModelChangeHoistSpdRef1 = { },
            onSetHoistSpdRef1 = { },

            setValueHoistSpdRef2 = "2",
            onModelChangeHoistSpdRef2 = { },
            onSetHoistSpdRef2 = { },

            setValueHoistSpdRef3 = "2",
            onModelChangeHoistSpdRef3 = { },
            onSetHoistSpdRef3 = { },

            setValueHoistSpdRef4 = "2",
            onModelChangeHoistSpdRef4 = { },
            onSetHoistSpdRef4 = { },

            setValueHoistSpdRefButton = "2",
            onModelChangeHoistSpdRefButton = { },
            onSetHoistSpdRefButton = { },

            setValueHoistSpdRefMax = "2",
            onModelChangeHoistSpdRefMax = { },
            onSetHoistSpdRefMax = { },

            setValueHoistMontageACC = "2",
            onModelChangeHoistMontageACC = { },
            onSetHoistMontageACC = { },

            setValueHoistMontageDEC = "2",
            onModelChangeHoistMontageDEC = { },
            onSetHoistMontageDEC = { },

            setValueHoistMontageSpdRef1 = "2",
            onModelChangeHoistMontageSpdRef1 = { },
            onSetHoistMontageSpdRef1 = { },

            setValueHoistMontageSpdRef2 = "2",
            onModelChangeHoistMontageSpdRef2 = { },
            onSetHoistMontageSpdRef2 = { },

            errorMessageHoist = null,
            logMessageHoist = null,

            errorMessageHoistMontage = null,
            logMessageHoistMontage = null
        )
    }
}