package com.example.giraffenew.setting_load_capacity.ui

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
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandThousandButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicTextFilledGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
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

    onSetMinWeightСalibration: () -> Unit,
    onSetMaxWeightСalibration: () -> Unit,

    errorMessageWeightSensor: String?,
    logMessageWeightSensor: String?,

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
                .padding(5.dp)
                .fillMaxSize()
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
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_Text_SensorReadings)
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_Text_HookWeightReadings)
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_TextFilled_HookWeightMin)

            // Отступ
            BasicSettingsName(modifier = Modifier.padding(top = 5.dp), text = stringResource(R.string.spacer))
            BasicNameSettingParameterGiraffe(
                settingParameterId = R.string.SettingLoadCapacity_TextFilled_HookWeightMax)

            Box (modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp)
            ) {
                errorMessageWeightSensor?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))}
                logMessageWeightSensor?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp)) }
            }

        }


        //№.2 - Правая область для отображения значений и ввода параметров датчика усилия
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f)
                .padding(5.dp)
                .border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
            // Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.SettingLoadCapacity_Text_SettingSensor))

            // Отображение текущих параметров датчика усилия
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = format(currentCurrAI))
            }
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                    valueParameterId = formatDecimal(currentWeight))
            }

            //Ввод. Минимальный вес груза
            Column (modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top) {
                //Поле ввода значения минимального веса груза
                Box() {
                    BasicInputRowandThousandButton(
                        placeholder = "00.0",
                        value = setValueMinWeight,
                        onValueChange = onModelChangeMinWeight,
                        onClick = onSetValueMinWeight,
                        isDecimal = true,
                    )}
                //Кнопка калибровки
                Box() {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.SettingLoadCapacity_Button_Calibration,
                        onClick = onSetMinWeightСalibration,
                    ) {}
                }
            }

            //Ввод. Максимальный вес груза
            Column (modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Top) {
                //Поле ввода значения минимального веса груза
                Box() {
                    BasicInputRowandThousandButton(
                        placeholder = "00.0",
                        value = setValueMaxWeight,
                        onValueChange = onModelChangeMaxWeight,
                        onClick = onSetValueMaxWeight,
                        isDecimal = true,
                    )}
                //Кнопка калибровки
                Box() {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.SettingLoadCapacity_Button_Calibration,
                        onClick = onSetMaxWeightСalibration,
                    ) {}
                }
            }

        }


    }
}


private fun format(value: Int): String {
    //return String.format("%.1f", value).padStart(5, '0')
    return String.format("%05d", value)
}

private fun formatDecimal(value: Float): String {
    //return String.format("%.1f", value).padStart(5, '0')
    return String.format("%03.1f", value).replace(',', '.')
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
            onSetMinWeightСalibration = {},
            onSetMaxWeightСalibration = {},
            errorMessageWeightSensor = null,
            logMessageWeightSensor = null,
        )
    }
}