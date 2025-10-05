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
fun Parameters(
    qNom: Int,
    craneLenght : Float,
    rAP : Int,
    rAFIso : Int,
    rQF : Int,
    kP : Int,
    classOfUseC : Int,
    rN1 : Int,
    rN2 : Int,
    settingModeYear : Int,
    settingModeMonth : Int,
    settingModeDay : Int,
    settingModeHour : Int,
    settingModeMinute : Int,
    settingModeSeconds : Int,
    hmiSBKBNo : Int,
    hmiCraneNo : Int,
    hmiSBKBInstallationDay : Int,
    hmiSBKBInstallationMonth : Int,
    hmiSBKBInstallationYear : Int,

){
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
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)

// Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
// Отображение Ном. грузоподъемность [т]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId =format(qNom)
                )
            }
// Отображение Исполнение по стреле [м]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDecimal(craneLenght)
                )
            }
// Отображение Режим работы. Паспорт [A..]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatMode(rAP)
                )
            }
// Отображение Режим работы. Факт [A..]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatMode(rAFIso)
                )
            }
// Отображение Класс нагружения. Факт [Q..]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatMode(rQF)
                )
            }
// Отображение Класс использования [С..]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatMode(classOfUseC)
                )
            }
// Отображение Характеристическое число [N..]
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatCharacteristicNumber(rN1, rN2)
                )
            }
// Отображение Время последней наладки
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDateSettingMode(
                        settingModeDay,
                        settingModeMonth,
                        settingModeYear,
                        settingModeHour,
                        settingModeMinute
                    )
                )
            }
// Отображение Заводской номер СБКБ
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = format(hmiSBKBNo)
                )
            }
// Отображение Заводской номер крана
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = format(hmiCraneNo)
                )
            }
// Отображение Дата установки СБКБ
            Box(myBoxValuesSettingsParameterModifier) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDateSBKB(
                        hmiSBKBInstallationDay,
                        hmiSBKBInstallationMonth,
                        hmiSBKBInstallationYear
                    )
                )
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

private fun format(value: Int): String {
    return String.format("%d", value)
}
private fun formatMode(value: Int): String {
    return String.format("%d", value)
}
private fun formatDecimal(value: Float): String {
    return String.format("%02.0f", value).replace(',', '.')
}

private fun formatCharacteristicNumber(rN1: Int, rN2: Int): String {
    // Объединяем два 16-битных значения в одно 32-битное целое
    val combined = (rN2.toLong() shl 16) or (rN1.toLong() and 0xFFFF)

    // Делим на 100.0 чтобы получить исходное значение с двумя знаками после запятой
    val originalValue = combined.toDouble() / 100.0

    // Форматируем с двумя знаками после запятой
    return String.format("%.2f", originalValue).replace(',', '.')
}

private fun formatDateSettingMode(day: Int, month: Int, year: Int, hour: Int, minute: Int,): String {
    return String.format("%02d.%02d.%04d_%02d:%02d", day, month, year, hour, minute)
}

private fun formatDateSBKB(day: Int, month: Int, year: Int,): String {
    return String.format("%02d.%02d.%04d", day, month, year)
}


@Preview
@Composable
fun ParametersPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        Parameters(
            qNom = 10000,
            craneLenght = 1f,
            rAP = 1,
            rAFIso = 1,
            rQF = 1,
            kP = 1,
            classOfUseC = 1,
            rN1 = 3289,
            rN2 = 0,
            settingModeYear = 1,
            settingModeMonth = 1,
            settingModeDay = 1,
            settingModeHour = 1,
            settingModeMinute = 1,
            settingModeSeconds = 1,
            hmiSBKBNo = 1,
            hmiCraneNo = 1,
            hmiSBKBInstallationDay = 1,
            hmiSBKBInstallationMonth = 1,
            hmiSBKBInstallationYear = 1,

        )
    }
}