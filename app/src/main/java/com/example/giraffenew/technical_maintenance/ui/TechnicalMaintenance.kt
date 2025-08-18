package com.example.giraffenew.technical_maintenance.ui

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicTextFilledGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampWaring
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun TechnicalMaintenance(){
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
//№.1 - Левая область для отображения списка параметров ТО
        Column(
            modifier = Modifier
                .fillMaxSize().weight(3f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
// Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
//Отображения названия параметров
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.TechnicalMaintenance_Text_Number_Crane)
            Spacer(modifier = Modifier.size(20.dp))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.TechnicalMaintenance_Text_Plane_Maintenance)
            Spacer(modifier = Modifier.size(20.dp))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.TechnicalMaintenance_Text_Maintenance_Left)
            Spacer(modifier = Modifier.size(20.dp))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.TechnicalMaintenance_Text_Work_Permit)
            Spacer(modifier = Modifier.size(20.dp))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.TechnicalMaintenance_Text_Maintenance_Not)
            Spacer(modifier = Modifier.size(20.dp))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.TechnicalMaintenance_Text_Password)
            Spacer(modifier = Modifier.size(20.dp))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.TechnicalMaintenance_Text_Reset_Counter)
        }
//№.2 - Правая область для отображения данных ТО
        Column(
            modifier = Modifier
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
            val myBoxValuesSettingsParameterModifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)

// Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
// Отображение Заводской номер крана
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "123")
            }
            Spacer(modifier = Modifier.size(20.dp))
// Отображение Плановое ТО [м.ч.]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "999")
            }
            Spacer(modifier = Modifier.size(20.dp))
// Отображение До планового ТО [м.ч.]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "555")
            }
            Spacer(modifier = Modifier.size(20.dp))
// Отображение Разрешение работы на скоростях 3 и 4.
            Box(modifier = Modifier.padding(start = 8.dp)) {
                BasicLampWaring(
                    isLampStatus = 0
                )
            }
            Spacer(modifier = Modifier.size(20.dp))

// Отображение Плановое ТО не проводилось
            Box(modifier = Modifier.padding(start = 8.dp)) {
                BasicLampWaring(
                    isLampStatus = 0
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
//Ввод. Пароль
            Box(modifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffe(
                    placeholder = "1234567890AaBbCcDd",
                    value = "",
                    onValueChange = {""},
                    modifier = Modifier,
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
//Кнопка. Сброс счетчика моточасов
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.Button_Turn_Reset,
                onClick = {},
            ) {}
        }
    }
}

@Preview
@Composable
fun TechnicalMaintenancePreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        TechnicalMaintenance(
        )
    }
}