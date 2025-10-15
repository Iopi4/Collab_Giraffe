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
import com.example.giraffenew.diagnostics.prezentation.DiagnosticViewModule
import com.example.giraffenew.diagnostics.state.StateValueWindAndLoadSensor
import com.example.giraffenew.diagnostics.text.StatusDriveCS710
import com.example.giraffenew.diagnostics.text.StatusDriveMD520
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun DiagnosticSystem(
    systemParams: DiagnosticViewModule.ControlSystemParametersState,
) {

    val ogpLimSw = systemParams.ogpLimSw
    val currentWeightAI = systemParams.currentWeightAI
    val weightSensorErr = systemParams.weightSensorErr
    val currentWindAI   = systemParams.currentWindAI
    val windSensorErr   = systemParams.windSensorErr
    //-----Данные с энкодеров
    val currentAbsEncHoistPositionWord1 = systemParams.currentAbsEncHoistPositionWord1
    val currentAbsEncHoistPositionWord2 = systemParams.currentAbsEncHoistPositionWord2

    val currentAbsEncSlewPositionWord1 = systemParams.currentAbsEncSlewPositionWord1
    val currentAbsEncSlewPositionWord2 = systemParams.currentAbsEncSlewPositionWord2

    val currentAbsEncTrolleyPositionWord1 = systemParams.currentAbsEncTrolleyPositionWord1
    val currentAbsEncTrolleyPositionWord2 = systemParams.currentAbsEncTrolleyPositionWord2

    val currentAbsEncCranePositionWord1 = systemParams.currentAbsEncCranePositionWord1
    val currentAbsEncCranePositionWord2 = systemParams.currentAbsEncCranePositionWord2

    //-----ошибка связи энкодеров
    val currentHoistAbsEncErrCanOpen = systemParams.currentHoistAbsEncErrCanOpen
    val currentSlewAbsEncErrCanOpen  = systemParams.currentSlewAbsEncErrCanOpen
    val currentTrolleyAbsEncErrCanOpen = systemParams.currentTrolleyAbsEncErrCanOpen
    val currentCraneAbsEncErrCanOpen = systemParams.currentCraneAbsEncErrCanOpen
    val canOpenSensorsError = systemParams.canOpenSensorsError
    //-----Статус приводов
    val hoistStatusWord = systemParams.hoistStatusWord
    val slewStatusWord = systemParams.slewStatusWord
    val trolleyStatusWord = systemParams.trolleyStatusWord
    val craneStatusWord = systemParams.craneStatusWord
    val craneRepPoint = systemParams.craneRepPoint


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
                        valueParameterId = formatAbsEncPositionPosition(
                            currentAbsEncHoistPositionWord2,
                            currentAbsEncHoistPositionWord1,
                        )
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
                        valueParameterId = formatAbsEncPositionPosition(
                            currentAbsEncSlewPositionWord2,
                            currentAbsEncSlewPositionWord1,)

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
                        valueParameterId = formatAbsEncPositionPosition(
                            currentAbsEncTrolleyPositionWord2,
                            currentAbsEncTrolleyPositionWord1,)

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
                        valueParameterId = formatAbsEncPositionPosition(
                            currentAbsEncCranePositionWord2,
                            currentAbsEncCranePositionWord1,)

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
    return String.format("%d", value)
}

private fun formatAbsEncPositionPosition(high: Int, low: Int): String {
    // Правильное объединение: старший регистр (high) сдвигается на 16 бит
    val combined = (high.toLong() shl 16) or (low.toLong() and 0xFFFF)
    return String.format("%d", combined)
}


@Preview
@Composable
fun DiagnosticSystemPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        DiagnosticSystem(
            systemParams = DiagnosticViewModule.ControlSystemParametersState(
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
            ),


        )
    }
}