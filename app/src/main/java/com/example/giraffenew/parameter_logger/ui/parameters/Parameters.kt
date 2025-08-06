package com.example.giraffenew.parameter_logger.ui.parameters

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun Parameters(){
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
//№.1 - Левая область для отображения названия параметров крана
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
                text = stringResource(R.string.ParameterLogger_Text_Parameters_Crane))
//Отображения текста регистратора параметров крана
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Load_Capacity)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Arrow_Length)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Passport_Mode_Group)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Actual_Mode_Group)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Actual_Load_Class)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Class_Of_Use)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Characteristic_Number)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Final_Naladka)

            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Number_SBKB)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Number_Crane)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Date_SBKB)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_SD_Cart_Status)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Data_Transfer)
        }
//№.2 - Правая область для отображения значений параметров крана
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
// Отображение Ном. грузоподъемность [т]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "10")
            }
// Отображение Исполнение по стреле [м]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "60")
            }
// Отображение Режим работы. Паспорт [A..]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "5")
            }
// Отображение Режим работы. Факт [A..]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "3")
            }
// Отображение Класс нагружения. Факт [Q..]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "5")
            }
// Отображение Класс использования [С..]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "5")
            }
// Отображение Характеристическое число [N..]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "30")
            }
// Отображение Время последней наладки
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "Date")
            }
// Отображение Заводской номер СБКБ
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "123")
            }
// Отображение Заводской номер крана
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "123")
            }
// Отображение Дата установки СБКБ
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "12.12.1234")
            }
// Отображение Cтатус FLASH-карты
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "Не подключен")
            }
// Отображение Cтатус копирования данных регистратора
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = "Ожидание")
            }
        }
    }
}

@Preview
@Composable
fun ParametersPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        Parameters(
        )
    }
}