package com.example.giraffenew.service_menu.ui.sensors

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
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServiceWeight(
//СЕРВИСНЫЙ РЕЖИМ. НАСТРОЙКА ДАТЧИКА ГРУЗА
    setValueLoadCellMinWeight: String,
    onModelChangeLoadCellMinWeight: (String) -> Unit,
    onSetLoadCellMinWeight: () -> Unit,

    setValueLoadCellMaxWeight: String,
    onModelChangeLoadCellMaxWeight: (String) -> Unit,
    onSetLoadCellMaxWeight: () -> Unit,

    setValueLoadCellSmoothingDepth: String,
    onModelChangeLoadCellSmoothingDepth: (String) -> Unit,
    onSetLoadCellSmoothingDepth: () -> Unit,

    setValueLoadCellOverSpeedThreshold: String,
    onModelChangeLoadCellOverSpeedThreshold: (String) -> Unit,
    onSetLoadCellOverSpeedThreshold: () -> Unit,

    errorMessageLoadCell: String?,
    logMessageLoadCell: String?,


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
                text = stringResource(R.string.ServiceMenu_Text_Sensor_Weight))
//Отображения текста рабочих параметров датчика грузоподъемности
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Weight_Min)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Weight_Max)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Weight_Averaging)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_Sensor_Weight_SS_Speed)

            Box (modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp)
            ) {
                errorMessageLoadCell?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp))}
                logMessageLoadCell?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp)) }
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
//Ввод. Мин. значение измерения [4000]
            BasicInputRowandButton(
                placeholder = "4000",
                value = setValueLoadCellMinWeight,
                valueMin = 0,
                valueMax = 10000,
                onValueChange = onModelChangeLoadCellMinWeight,
                onClick = onSetLoadCellMinWeight,
            )
//Ввод. Макс. значение измерения [32767]
            BasicInputRowandButton(
                placeholder = "32767",
                value = setValueLoadCellMaxWeight,
                valueMin = 0,
                valueMax = 32767,
                onValueChange = onModelChangeLoadCellMaxWeight,
                onClick = onSetLoadCellMaxWeight,
            )
//Ввод. Глубина сглаживания веса [70]
            BasicInputRowandButton(
                placeholder = "70",
                value = setValueLoadCellSmoothingDepth,
                valueMin = 0,
                valueMax = 200,
                onValueChange = onModelChangeLoadCellSmoothingDepth,
                onClick = onSetLoadCellSmoothingDepth,
            )
//Ввод. Огранич. сверхскор. подъема [т]
            BasicInputRowandTensButton(
                placeholder = "5.0",
                value = setValueLoadCellOverSpeedThreshold,
                onValueChange = onModelChangeLoadCellOverSpeedThreshold,
                onClick = onSetLoadCellOverSpeedThreshold,
                isDecimal = true,
            )
        }
    }
}

@Preview
@Composable
fun ServiceWeightPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
    }
}