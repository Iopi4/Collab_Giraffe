package com.example.giraffenew.diagnostics.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampError
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingParameter
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.diagnostics.ui.state.StateValueWindAndLoadSensor
import com.example.giraffenew.diagnostics.ui.text.StatusDriveCS710
import com.example.giraffenew.diagnostics.ui.text.StatusDriveMD520
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun DiagnosticSystem(
    //Датчики ветра и усилия
    ogpLimSw: Int,
    currentWeightAI: Int,
    weightSensorErr:Int,
    currentWindAI : Int,
    windSensorErr : Int,
    //-----Данные с энкодеров
    currentAbsEncHoistPositionWord1 :Int,
    currentAbsEncHoistPositionWord2 :Int,
    currentAbsEncSlewPositionWord1 :Int,
    currentAbsEncSlewPositionWord2 :Int,
    currentAbsEncTrolleyPositionWord1 :Int,
    currentAbsEncTrolleyPositionWord2 :Int,
    currentAbsEncCranePositionWord1 :Int,
    currentAbsEncCranePositionWord2 :Int,
    //-----ошибка связи энкодеров
    currentHoistAbsEncErrCanOpen :Int,
    currentSlewAbsEncErrCanOpen  :Int,
    currentTrolleyAbsEncErrCanOpen :Int,
    currentCraneAbsEncErrCanOpen :Int,
    canOpenSensorsError :Int,
    //-----Статус приводов
    hoistStatusWord :Int,
    slewStatusWord :Int,
    trolleyStatusWord :Int,
    craneStatusWord :Int,
    craneRepPoint:Int,
) {
//Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {

        //№.1 - Левая область для отображения параметров настройки полигона)
        Column(
            modifier = Modifier
                .fillMaxSize().weight(3f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
            // Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.Diagnostic_Text_System))
            //Статусы оборудования системы управления
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_PLC)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Sensor_Critical_Load)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Sensor_Сargo)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Sensor_Wind)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Sensor_Hoist)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Sensor_Trolley)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Sensor_Slewing)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Sensor_Crane)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Sensor_Crane_Reference_Point)

            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Hoist)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Slewing)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Trolley)
            BasicNameSettingParameterGiraffe( settingParameterId = R.string.Diagnostic_Text_System_Status_Crane)
        }

//№.2 - Правая область для отображения и ввода данных настройки полигона
        Column(
            modifier = Modifier
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
            // Заголовок
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.Diagnostic_Text_Status))

// Отображение текущего состояния PLC
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicValuesSettingsParameterGiraffe(
                    modifier = Modifier,
                    valueParameterId = "RUN"
                )
            }

// Отображение статус датчика критического момента
            Row(horizontalArrangement = Arrangement.Start) {
                Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)
                    .weight(3f))
                Box(modifier = Modifier
                    .weight(1f)
                    .size(height = 35.dp, width = 35.dp)
                    .padding(start = 5.dp)) {
                    BasicLampSettingParameter(
                        isLampStatus = ogpLimSw
                    )
                }
            }

// Отображение статус датчика усилия
            StateValueWindAndLoadSensor(
                isValueParameter = format(currentWeightAI),
                isValueStatus = weightSensorErr
            )

// Отображение статус датчика ветра
            // Отображение статус датчика ветра
            StateValueWindAndLoadSensor(
                isValueParameter = format(currentWindAI),
                isValueStatus = windSensorErr
            )

// Отображение статуса датчика высоты Hoist
            Row(horizontalArrangement = Arrangement.Start) {
                Box(modifier = Modifier.padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                    .weight(3f)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = formatAbsEncPositionPosition(currentAbsEncHoistPositionWord1,currentAbsEncHoistPositionWord2)
                    )
                }
                Box(modifier = Modifier
                    .weight(1f)
                    .size(width = 35.dp, height = 35.dp)
                    .padding(start = 5.dp, end = 10.dp, top = 5.dp)) {
                    BasicLampError(
                        isLampStatus = currentHoistAbsEncErrCanOpen
                    )
                }
            }

// Отображение статуса датчика азимута Slewing
            Row(horizontalArrangement = Arrangement.Start) {
                Box(modifier = Modifier.padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                    .weight(3f)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = formatAbsEncPositionPosition(currentAbsEncSlewPositionWord1,currentAbsEncSlewPositionWord2)

                    )
                }
                Box(modifier = Modifier.weight(1f)
                    .size(width = 35.dp, height = 35.dp)
                    .padding(start = 5.dp, end = 10.dp, top = 5.dp)) {
                    BasicLampError(
                        isLampStatus = currentSlewAbsEncErrCanOpen
                    )
                }
            }


// Отображение статуса датчика вылета Trolley
            Row(horizontalArrangement = Arrangement.Start) {
                Box(modifier = Modifier.padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                    .weight(3f)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = formatAbsEncPositionPosition(currentAbsEncTrolleyPositionWord1,currentAbsEncTrolleyPositionWord2)

                    )
                }
                Box(modifier = Modifier.weight(1f)
                    .size(width = 30.dp, height = 35.dp)
                    .padding(start = 5.dp, end = 10.dp, top = 5.dp)) {
                    BasicLampError(
                        isLampStatus = currentTrolleyAbsEncErrCanOpen
                    )
                }
            }


// Отображение статуса датчика хода Crane
            Row(horizontalArrangement = Arrangement.Start) {
                Box(modifier = Modifier.padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                    .weight(3f)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = formatAbsEncPositionPosition(currentAbsEncCranePositionWord1,currentAbsEncCranePositionWord2)

                    )
                }
                Box(modifier = Modifier.weight(1f)
                    .size(width = 30.dp, height = 35.dp)
                    .padding(start = 5.dp, end = 10.dp, top = 5.dp)) {
                    BasicLampError(
                        isLampStatus = currentCraneAbsEncErrCanOpen
                    )
                }
            }

// Отображение статус датчика реперной точки хода крана
            Row(horizontalArrangement = Arrangement.Start) {
                Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)
                    .weight(3f))
                Box(modifier = Modifier
                    .weight(1f)
                    .size(height = 35.dp, width = 35.dp)
                    .padding(start = 5.dp)) {
                    BasicLampSettingParameter(
                        isLampStatus = craneRepPoint
                    )
                }
            }

// Отображение текущего состояния привода подъема
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                StatusDriveCS710(
                    modifier = Modifier,
                    valueParameterId = hoistStatusWord
                )
            }
// Отображение текущего состояния привода поворота
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                StatusDriveMD520(
                    modifier = Modifier,
                    valueParameterId = slewStatusWord
                )
            }
// Отображение текущего состояния привода тележки
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                StatusDriveMD520(
                    modifier = Modifier,
                    valueParameterId = trolleyStatusWord
                )
            }
// Отображение текущего состояния привода хода крана
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                StatusDriveMD520(
                    modifier = Modifier,
                    valueParameterId = craneStatusWord
                )
            }



        }
    }
}

private fun format(value: Int): String {
    return String.format("%05d", value)
}

private fun formatAbsEncPositionPosition(value1: Int, value2: Int): String {
    return String.format("%05d%05d", value2, value1)
}


@Preview
@Composable
fun DiagnosticSystemPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        DiagnosticSystem(
            ogpLimSw = 1,
            currentWeightAI = 1,
            weightSensorErr = 1,
            currentWindAI   = 1,
            windSensorErr   = 1,
            //-----Данные с энкодеров
            currentAbsEncHoistPositionWord1 = 1,
            currentAbsEncHoistPositionWord2 = 1,
            currentAbsEncSlewPositionWord1 = 1,
            currentAbsEncSlewPositionWord2 = 1,
            currentAbsEncTrolleyPositionWord1 = 1,
            currentAbsEncTrolleyPositionWord2 = 1,
            currentAbsEncCranePositionWord1 = 1,
            currentAbsEncCranePositionWord2 = 1,
            //-----ошибка связи энкодеров
            currentHoistAbsEncErrCanOpen = 1,
            currentSlewAbsEncErrCanOpen  = 1,
            currentTrolleyAbsEncErrCanOpen = 1,
            currentCraneAbsEncErrCanOpen = 1,
            canOpenSensorsError = 1,
            //-----Статус приводов
            hoistStatusWord = 1,
            slewStatusWord = 1,
            trolleyStatusWord = 1,
            craneStatusWord = 1,
            craneRepPoint = 1,
        )
    }
}