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
import com.example.giraffenew.core.ui_kit.composable.text.BasicTextFilledGiraffeDecimal
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampError
import com.example.giraffenew.diagnostics.ui.state.StateValueWindAndLoadSensor
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun TechnicalMaintenance(
    setValueTOPassword: String,
    craneNo: Int,
    tOCounter: Int,
    technicalReglamentTO1: Int,
    alarmTO1: Int,
    motorHourDelta: Float,
    technicalReglamentTO1Delta: Int,
    technicalReglamentxWarrantyEnd: Int,
    hmiTotalCraneOperatingHoursMaintenance: Float,



    onResetPassword: () -> Unit,
){
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
//№.1 - Левая область для отображения списка параметров ТО
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
// Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
//Отображения названия параметров
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.TechnicalMaintenance_Text_Number_Crane)
            Spacer(modifier = Modifier.size(20.dp))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.TechnicalMaintenance_Text_TO_Counter)
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
            val myBoxValuesSettingsParameterModifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)

// Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
// Отображение Заводской номер крана
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = format(craneNo)
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
// Отображение Плановое ТО [м.ч.]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = format(tOCounter)
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
// Отображение До планового ТО [м.ч.]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDecimal(motorHourDelta)
                )
            }
            Spacer(modifier = Modifier.size(20.dp))
// Отображение Разрешение работы на скоростях 3 и 4.
            Row(horizontalArrangement = Arrangement.Start) {
                Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)
                    .weight(3f))
                Box(modifier = Modifier
                    .weight(1f)
                    .size(height = 35.dp, width = 35.dp)
                    .padding(start = 5.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicLampError(
                        isLampStatus = alarmTO1
                    )
                }
            }
            Spacer(modifier = Modifier.size(20.dp))

// Отображение Плановое ТО не проводилось столько часов
            val CONST_MOTOR_HOIST_DELTA = 65535
            val absMotorHourDelta: Int = CONST_MOTOR_HOIST_DELTA - (motorHourDelta.toInt())
            StateValueWindAndLoadSensor(
                isValueParameter = format(absMotorHourDelta),
                isValueStatus = alarmTO1
            )
            Spacer(modifier = Modifier.size(20.dp))
//Ввод. Пароль
            Box(modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextFilledGiraffeDecimal(
                    placeholder = "0123456789",
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
                onClick = onResetPassword,
            ) {}
        }
    }
}


private fun format(value: Int): String {
    return String.format("%d", value)
}

private fun formatDecimal(value: Float): String {
    return String.format("%01.2f", value).replace(',', '.')
}


@Preview
@Composable
fun TechnicalMaintenancePreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        TechnicalMaintenance(
            setValueTOPassword = "1",
            craneNo = 1,
            tOCounter = 1,
            technicalReglamentTO1 = 1,
            alarmTO1 = 1,
            motorHourDelta = 1f,
            technicalReglamentTO1Delta = 1,
            technicalReglamentxWarrantyEnd = 1,
            hmiTotalCraneOperatingHoursMaintenance = 1f,
            onResetPassword = { },
        )
    }
}