package com.example.giraffenew.service_menu.ui.drive

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicSettingsName
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServiceHoist(){
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
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ServiceMenu_Text_Acc
            )
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ServiceMenu_Text_Dec
            )
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ServiceMenu_Text_Speed_1
            )
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ServiceMenu_Text_Speed_2
            )
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ServiceMenu_Text_Speed_3
            )
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ServiceMenu_Text_Speed_4
            )
// Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.ServiceMenu_Text_Hoist_Montage))
//Отображения текста параметров привода подъема в режиме монтаж
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ServiceMenu_Text_Acc
            )
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ServiceMenu_Text_Dec
            )
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ServiceMenu_Text_Speed_1
            )
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.ServiceMenu_Text_Speed_2
            )
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
        }
    }
}

@Preview
@Composable
fun ServiceHoistPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ServiceHoist(
        )
    }
}