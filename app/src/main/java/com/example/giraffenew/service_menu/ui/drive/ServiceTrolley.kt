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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicTextFilledGiraffe
import com.example.giraffenew.initial_setup.ui.ButtonOn
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServiceTrolley(
    setValueTrolleyACC: String,
    onModelChangeTrolleyACC: (String) -> Unit,
    onSetTrolleyACC: () -> Unit,

    setValueTrolleyDEC: String,
    onModelChangeTrolleyDEC: (String) -> Unit,
    onSetTrolleyDEC: () -> Unit,

    setValueTrolleySpdRef1: String,
    onModelChangeTrolleySpdRef1: (String) -> Unit,
    onSetTrolleySpdRef1: () -> Unit,

    setValueTrolleySpdRef2: String,
    onModelChangeTrolleySpdRef2: (String) -> Unit,
    onSetTrolleySpdRef2: () -> Unit,

    setValueTrolleySpdRef3: String,
    onModelChangeTrolleySpdRef3: (String) -> Unit,
    onSetTrolleySpdRef3: () -> Unit,

    setValueTrolleySpdRef4: String,
    onModelChangeTrolleySpdRef4: (String) -> Unit,
    onSetTrolleySpdRef4: () -> Unit,

    setValueTrolleyLsp: String,
    onModelChangeTrolleyLsp: (String) -> Unit,
    onSetTrolleyLsp: () -> Unit,

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
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Max_Speed_SS)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Trolley_Speed_SS_Ban)

            Box (modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp)
            ) {
                errorMessageTrolley?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))}
                logMessageTrolley?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp)) }
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
                placeholder = "3.0",
                value = setValueTrolleyACC,
                onValueChange = onModelChangeTrolleyACC,
                onClick = onSetTrolleyACC
            )
//Ввод. Торможение. Секунды
            BasicInputRowandTensButton(
                placeholder = "3.0",
                value = setValueTrolleyDEC,
                onValueChange = onModelChangeTrolleyDEC,
                onClick = onSetTrolleyDEC
            )
//Ввод. Скорость 1. Об/мин
            BasicInputRowandButton(
                placeholder = "150",
                value = setValueTrolleySpdRef1,
                onValueChange = onModelChangeTrolleySpdRef1,
                onClick = onSetTrolleySpdRef1
            )
//Ввод. Скорость 2.Об/мин
            BasicInputRowandButton(
                placeholder = "700",
                value = setValueTrolleySpdRef2,
                onValueChange = onModelChangeTrolleySpdRef2,
                onClick = onSetTrolleySpdRef2
            )
//Ввод. Скорость 3.Об/мин
            BasicInputRowandButton(
                placeholder = "1500",
                value = setValueTrolleySpdRef3,
                onValueChange = onModelChangeTrolleySpdRef3,
                onClick = onSetTrolleySpdRef3
            )
//Ввод. Сверх.синхр. скорость, об/мин
            BasicInputRowandButton(
                placeholder = "3000",
                value = setValueTrolleySpdRef4,
                onValueChange = onModelChangeTrolleySpdRef4,
                onClick = onSetTrolleySpdRef4
            )

//Ввод. Запрет движения на сверхсинхронной скорости, тонны
            BasicInputRowandButton(
                placeholder = "1500",
                value = setValueTrolleyLsp,
                onValueChange = onModelChangeTrolleyLsp,
                onClick = onSetTrolleyLsp
            )
        }

    }
}



@Preview
@Composable
fun ServiceTrolleyPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ServiceTrolley(
            setValueTrolleyACC = "2",
            onModelChangeTrolleyACC = { },
            onSetTrolleyACC ={ },

            setValueTrolleyDEC = "2",
            onModelChangeTrolleyDEC = { },
            onSetTrolleyDEC ={ },

            setValueTrolleySpdRef1 = "150",
            onModelChangeTrolleySpdRef1 = { },
            onSetTrolleySpdRef1 ={ },

            setValueTrolleySpdRef2 = "700",
            onModelChangeTrolleySpdRef2 = { },
            onSetTrolleySpdRef2 ={ },

            setValueTrolleySpdRef3 = "1500",
            onModelChangeTrolleySpdRef3 = { },
            onSetTrolleySpdRef3 ={ },

            setValueTrolleySpdRef4 = "3000",
            onModelChangeTrolleySpdRef4 = { },
            onSetTrolleySpdRef4 ={ },

            setValueTrolleyLsp = "1500",
            onModelChangeTrolleyLsp = { },
            onSetTrolleyLsp ={ },

            errorMessageTrolley = null,
            logMessageTrolley= null
        )
    }
}