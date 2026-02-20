package com.example.giraffenew.setting_load_capacity.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsNameInfo
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsNameInfoBold
import com.example.giraffenew.core.ui_kit.composable.text.BasicTextFilledGiraffeDecimal
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.setting_load_capacity.text.TextLoadCapacityMessageCalibration
import com.example.giraffenew.setting_load_capacity.text.TextLoadCapacityStatusCalibration
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.DarkLemon
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ParameterWeightSensor(
    currentCurrAI:Int,
    currentWeight: Float,

    setValueMinWeight: String,
    onModelChangeMinWeight: (String) -> Unit,
    onSetValueMinWeight: () -> Unit,

    setValueMaxWeight: String,
    onModelChangeMaxWeight: (String) -> Unit,
    onSetValueMaxWeight: () -> Unit,

    errorMessageWeightSensor: String?,
    logMessageWeightSensor: String?,

    hmiWeightCalibStatus:Int,
    hmiWeightCalibMessage:Int,

    heightCurrent: Float,
    trolleyCurrent: Float,

    onResetWeightCalibration: (Int) -> Unit,

    ) {

// Общее состояние прокрутки
    val scrollState = rememberScrollState()

    Column(modifier = Modifier
        .fillMaxSize()) {
//Зона с параметрами калибровки датчика усилия
        Box(
            modifier = Modifier
                .weight(9f)
                .fillMaxSize()
        ) {
            //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
            Row(
                modifier = Modifier
                    .fillMaxSize())
            {
                //№.1 - Левая область для отображения названия параметров датчика усилия
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(3f)
                        .padding(start = 5.dp, top = 5.dp, end = 5.dp)
                        .border(
                            width = 3.dp,
                            color = Orange,
                            shape = RoundedCornerShape(5.dp)
                        ),
                    verticalArrangement = Arrangement.Top
                ) {
                    // Заголовок
                    BasicSettingsName(modifier = Modifier, text = stringResource(R.string.SettingLoadCapacity_Text_CalibrationSensor))

                    //Параметры датчика усилия
                    BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingLoadCapacity_Text_SensorReadings)
                    BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingLoadCapacity_Text_Hoist_Trolley)
                    BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingLoadCapacity_TextFilled_HookWeightMin)
                    BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingLoadCapacity_TextFilled_HookWeightMax)
                    BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingLoadCapacity_Status_Calibration)
                    Spacer(modifier = Modifier.size(20.dp))
                    BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingLoadCapacity_Message_Calibration)
                    Spacer(modifier = Modifier.size(45.dp))
                    BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingLoadCapacity_Button_Reset_Min_Calibration)
                    BasicNameSettingParameterGiraffe(settingParameterId = R.string.SettingLoadCapacity_Button_Reset_Max_Calibration)
                }


                //№.2 - Правая область для отображения значений и ввода параметров датчика усилия
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(2f)
                        .padding(start = 5.dp, top = 5.dp, end = 5.dp)
                        .border(
                            width = 3.dp,
                            color = Burgundy_violet,
                            shape = RoundedCornerShape(5.dp)
                        ),
                    verticalArrangement = Arrangement.Top
                ) {
// Отступ  и Статус записи данных настройки датчика усилия
                    Row(modifier = Modifier.fillMaxWidth()) {
                        BasicSettingsName(modifier = Modifier.weight(1f), text = stringResource(R.string.spacer))

                        Box (modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                        ) {
                            errorMessageWeightSensor?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp))}
                            logMessageWeightSensor?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                        }
                    }
// Отображение текущих параметров датчика усилия
                    Row (horizontalArrangement = Arrangement.SpaceBetween) {

                        Box(modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)) {
                            BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                                valueParameterId = formatDecimalMinus(currentCurrAI))
                        }
                        Box(modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                            BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                                valueParameterId = formatDecimal(currentWeight))
                        }
                    }
// Отображение текущих параметров высоты и вылета
                    Row (horizontalArrangement = Arrangement.SpaceBetween) {

                        Box(modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)) {
                            BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                                valueParameterId = formatDecimalMinusFloat(heightCurrent))
                        }
                        Box(modifier = Modifier
                            .weight(1f)
                            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                            BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                                valueParameterId = formatDecimal(trolleyCurrent))
                        }
                    }

                    //Ввод. Минимальный вес груза
                    Row (horizontalArrangement = Arrangement.SpaceBetween) {
                        //Поле ввода значения текущего угла поворота стрелы
                        Box (modifier = Modifier
                            .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                            .weight(1f)
                        ) {
                            BasicTextFilledGiraffeDecimal(
                                placeholder = "0.0",
                                value = setValueMinWeight,
                                onValueChange = onModelChangeMinWeight,
                                modifier = Modifier,
                                isDecimal = true,
                            )
                        }
                        //Кнопка ввода
                        Box(modifier = Modifier.weight(1f)) {
                            BasicButtonGiraffe(
                                modifier = Modifier,
                                text = R.string.SettingLoadCapacity_Button_Calibration,
                                onClick = onSetValueMinWeight,
                            ) {}
                        }
                    }

                    //Ввод. Максимальный вес груза
                    Row (horizontalArrangement = Arrangement.SpaceBetween) {
                        //Поле ввода значения текущего угла поворота стрелы
                        Box (modifier = Modifier
                            .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                            .weight(1f)
                        ) {
                            BasicTextFilledGiraffeDecimal(
                                placeholder = "10.0",
                                value = setValueMaxWeight,
                                onValueChange = onModelChangeMaxWeight,
                                modifier = Modifier,
                                isDecimal = true,
                            )
                        }
                        //Кнопка ввода
                        Box(modifier = Modifier.weight(1f)) {
                            BasicButtonGiraffe(
                                modifier = Modifier,
                                text = R.string.SettingLoadCapacity_Button_Calibration,
                                onClick = onSetValueMaxWeight,
                            ) {}
                        }
                    }

//Статус калибровки датчика усилия
                    // 2 Откалибрована первая точка минимума
                    // 6 Откалибрована вторая точка минимума
                    // 8, 14 - Откалибрована первая точка максимума
                    // 24, 30 - Откалибрована вторая точка максимума
                    Box(modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                        TextLoadCapacityStatusCalibration(
                            modifier = Modifier,
                            valueParameterId = hmiWeightCalibStatus
                        )
                    }
//Порядок калибровки датчика усилия
                    //0 - настройка не начата
                    //1 - Датчик вылета не настроен;
                    //2 - Датчик вылета настроен не корректно;
                    //3 - Некорректное значение минимального калибровочного веса
                    //4 - Значение вылета совпадает с предыдущей точкой
                    //5 - Изменение калибровочного веса
                    //6 - Точка №1 минимума откалибрована
                    //7 - Точка №2 минимума откалибрована
                    //8 - Точка №1 максимума откалибрована
                    //9 - Точка №2 максимума откалибрована
                    //10 - Точки минимума сброшены
                    //11 - Точки максимума сброшены
                    //12 - Настройке минимальный вес
                    //13 - Вылет не соответствует точке №1 минимума
                    //14 - Вылет не соответствует точке №2 минимума
                    //15 - Ошибка калибровки
                    Box(modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                        TextLoadCapacityMessageCalibration(
                            modifier = Modifier,
                            valueParameterId = hmiWeightCalibMessage
                        )
                    }
// СБРОС ТОЧЕК КАЛИБРОВКИ МИНИМАЛЬНОГО ВЕСА.  1-сброс минимума,
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.SettingLoadCapacity_Button_Reset_Calibration_12,
                        onClick = { onResetWeightCalibration(1)}
                    ) {}
// СБРОС ТОЧЕК КАЛИБРОВКИ МАКСИМАЛЬНОГО ВЕСА. 2-сброс максимума
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.SettingLoadCapacity_Button_Reset_Calibration_34,
                        onClick = { onResetWeightCalibration(2)}
                    ) {}
                }
            }





        }


//Зона с информацией о порядке калиброке датчика усилия
        Box(
            modifier = Modifier
                .weight(8f)
                .fillMaxSize()
                .padding(5.dp)
                .background(colorResource(R.color.Silver))
                .border(
                    width = 3.dp,
                    color = DarkLemon,
                    shape = RoundedCornerShape(5.dp)
                ),
            contentAlignment = Alignment.TopEnd
        ) {
                            Column(
                                modifier = Modifier
                                    .verticalScroll(scrollState) // Добавлена прокрутка
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Top
                            ) {
                // Порядок калибровки датчика
                BasicSettingsNameInfo(modifier = Modifier, text = stringResource(R.string.SettingLoadCapacity_Text_Calibration))
                BasicSettingsNameInfo(modifier = Modifier, text = stringResource(R.string.SettingLoadCapacity_Text_Calibration_0))
                BasicSettingsNameInfo(modifier = Modifier, text = stringResource(R.string.SettingLoadCapacity_Text_Calibration_1))
                BasicSettingsNameInfo(modifier = Modifier, text = stringResource(R.string.SettingLoadCapacity_Text_Calibration_2))
                BasicSettingsNameInfoBold(modifier = Modifier, text = stringResource(R.string.SettingLoadCapacity_Text_CalibrationPoint_1))
                BasicSettingsNameInfo(modifier = Modifier, text = stringResource(R.string.SettingLoadCapacity_Text_CalibrationPoint_11))
                BasicSettingsNameInfoBold(modifier = Modifier, text = stringResource(R.string.SettingLoadCapacity_Text_CalibrationPoint_2))
                BasicSettingsNameInfo(modifier = Modifier, text = stringResource(R.string.SettingLoadCapacity_Text_CalibrationPoint_22))
                BasicSettingsNameInfoBold(modifier = Modifier, text = stringResource(R.string.SettingLoadCapacity_Text_CalibrationPoint_3))
                BasicSettingsNameInfo(modifier = Modifier, text = stringResource(R.string.SettingLoadCapacity_Text_CalibrationPoint_33))
                BasicSettingsNameInfoBold(modifier = Modifier, text = stringResource(R.string.SettingLoadCapacity_Text_CalibrationPoint_4))
                BasicSettingsNameInfo(modifier = Modifier.padding(bottom = 10.dp), text = stringResource(R.string.SettingLoadCapacity_Text_CalibrationPoint_44))
            }
        }
    }
}


private fun formatDecimal(value: Float): String {
    return String.format("%03.2f", value).replace(',', '.')
}

private val VIRTUAL_ZERO: Int = 32250 //Константа для виртуального нуля.
// Необходимо для корретного отображения мин. значения датчика усилия
private fun formatDecimalMinus(value: Int): String {
    val realValue = (value - VIRTUAL_ZERO)
    return if (realValue < 0) {
        String.format("%05d", realValue)
    } else {
        String.format("%05d", realValue)
    }
}

private val VIRTUAL_ZERO_F: Float = 32250f //Константа для виртуального нуля.
// Необходимо для корретного отображения мин. значения датчика усилия
private fun formatDecimalMinusFloat(value: Float): String {
    val realValue = (value * 10 - VIRTUAL_ZERO_F) / 10f
    return if (realValue < 0) {
        String.format("%01.1f", realValue).replace(',', '.')
    } else {
        String.format("%01.1f", realValue).replace(',', '.')
    }
}


@Preview
@Composable
fun ParametersSensorPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ParameterWeightSensor(
            currentCurrAI = 1,
            currentWeight = 1f,
            setValueMinWeight = "",
            onModelChangeMinWeight = { },
            onSetValueMinWeight = {},
            setValueMaxWeight = "",
            onModelChangeMaxWeight = {},
            onSetValueMaxWeight = {},
            errorMessageWeightSensor = null,
            logMessageWeightSensor = null,
            hmiWeightCalibStatus = 1,
            hmiWeightCalibMessage = 1,
            heightCurrent = 3250f,
            trolleyCurrent = 7f,
            onResetWeightCalibration = { },
        )
    }
}