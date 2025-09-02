package com.example.giraffenew.coordinate_protection.ui.polygon_1

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
import com.example.giraffenew.coordinate_protection.ui.button.BasicButtonOnOffStatusPolygon
import com.example.giraffenew.coordinate_protection.ui.button.BasicButtonPolygonReset
import com.example.giraffenew.coordinate_protection.ui.lamp.BasicLampPointType1Status
import com.example.giraffenew.coordinate_protection.ui.lamp.BasicLampPointType2Status
import com.example.giraffenew.coordinate_protection.ui.text.BasicTextPolygonSettingStatusFewPoint
import com.example.giraffenew.coordinate_protection.ui.text.BasicTextPolygonSettingStatusCalibration
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonOnOffStatus
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicInputRowandTensButton
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun Polygon1Setting(
    onSetP1En: () -> Unit,
    onStartResetPolygon1: () -> Unit, // Новый параметр

    setValuePolygon1PointNum: String,
    updateValuePolygon1PointNum: (String) -> Unit,
    onSetValueP1PointNum: () -> Unit,
    currentP1PointNum: Int,

    setValuePolygon1Height: String,
    updateValuePolygon1Height: (String) -> Unit,
    onSetValueP1Height: () -> Unit,
    currentP1Height: Float,

    onSetP1UpEn: () -> Unit,
    currentP1UpEn: Int,

    onSetP1DownEn: () -> Unit,
    currentP1DownEn: Int,

    onSetP1Type1En: () -> Unit,
    onSetP1Type2En: () -> Unit,
    currentP1PointType: Int,

    currentP1FewPoint: Int,
    currentP1ActiveStatus: Int,
    currentStatsCalibration: Int,

    errorMessagePolygon1: String?,
    logMessagePolygon1: String?,
    logMessagePolygon1Reset: String?,

    ) {
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //№.1 - Левая область для отображения параметров настройки полигона)
        Column(
            modifier = Modifier
                .fillMaxSize().weight(3f).padding(5.dp).fillMaxSize()
                .border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
            // Заголовок
            BasicSettingsName(
                modifier = Modifier,
                text = stringResource(R.string.CoordinateProtection_Text_Setting_Polygon))
            //Активировать полигон
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Polygon_Activate)
            //Количество точек
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Number_Of_Points)
            //Высота полигона
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Height_Of_Polygon)
            //Разрешить работу вверху
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Allow_Work_At_Up)
            //Разрешить работу внизу
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Allow_Work_At_Down)

            Spacer(modifier = Modifier.size(30.dp))

            // Выбор режима задания точек. По умолчанию режим [По крюку]
            BasicSettingsName(
                modifier = Modifier,
                text = stringResource(R.string.CoordinateProtection_Text_Point_Setting_Mode))
            BasicSettingsName(
                modifier = Modifier,
                text = stringResource(R.string.CoordinateProtection_Text_Point_Setting_Mode_HOOK))
            //Задание точек в ручном режиме
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Entering_Points_Trolley)
            //Задание точек по коорд.
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Entering_Points_Coordinate)

            // Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))

            //Статус активации полигона
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Polygon_Status)

            // Отступ
            Spacer(modifier = Modifier.size(30.dp))

            //Сбросить настройки полигона
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Polygon_Reset)
        }

//№.2 - Правая область для отображения и ввода данных настройки полигона
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
            // Отступ  и Статус записи данных настройки полигона
            Row(modifier = Modifier.fillMaxWidth()) {
                BasicSettingsName(modifier = Modifier.weight(1f), text = stringResource(R.string.spacer))

                Box (modifier = Modifier.weight(10f)
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                ) { errorMessagePolygon1?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 0.dp)) }
                    logMessagePolygon1?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }

//Поле активации полигона
            BasicButtonOnOffStatusPolygon(
                onClick = onSetP1En,
                isLampStatus = currentP1ActiveStatus
            )

//Ввод. Количество точек полигона
                BasicInputRowandButton(
                    placeholder = "4...20",
                    value = setValuePolygon1PointNum,
                    onValueChange = updateValuePolygon1PointNum,
                    onClick = onSetValueP1PointNum,
                    valueMin = 0,
                    valueMax = 20
                )


//Ввод. Высота полигона

                BasicInputRowandTensButton(
                    placeholder = "00.0",
                    value = setValuePolygon1Height,
                    onValueChange = updateValuePolygon1Height,
                    onClick = onSetValueP1Height,
                    isDecimal = true,
                )


//Кнопка. Разрешить работу вверху
            BasicButtonOnOffStatus(
                onClick = onSetP1UpEn,
                isLampStatus = currentP1UpEn
            )

//Кнопка. Разрешить работу внизу
            BasicButtonOnOffStatus(
                onClick = onSetP1DownEn,
                isLampStatus = currentP1DownEn
            )


            Spacer(modifier = Modifier.size(55.dp))
// Выбор режима задания точек
            BasicSettingsName(
                modifier = Modifier,
                text = stringResource(R.string.CoordinateProtection_Text_Mode))

//Кнопка. Выбор задание точек вручную. Type2. Состояние
            Row(horizontalArrangement = Arrangement.Start) {
                //Кнопка ввода
                Box(modifier = Modifier.weight(4f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_Turn_ON_Off,
                        onClick = onSetP1Type2En,
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampPointType2Status(
                        isLampStatus = currentP1PointType
                    )
                }
            }

//Кнопка. Выбор задание точек по координатам. Type1. Состояние
            Row(horizontalArrangement = Arrangement.Start) {
                //Кнопка ввода
                Box(modifier = Modifier.weight(4f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_Turn_ON_Off,
                        onClick = onSetP1Type1En,
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampPointType1Status(
                        isLampStatus = currentP1PointType
                    )
                }
            }

            Spacer(modifier = Modifier.size(25.dp))

//Статус активации/настройки полигона
            //0.Датчики положения не настроены
            //1.Датчики положения настроены
            Box(modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)) {
                BasicTextPolygonSettingStatusCalibration(
                    modifier = Modifier,
                    valueParameterId = currentStatsCalibration
                )

            }
            Spacer(modifier = Modifier.size(10.dp))

            BasicButtonPolygonReset (
                modifier = Modifier,
                text = R.string.Button_Turn_Reset,
                onClick = onStartResetPolygon1,
            )
            // Отступ  и Статус записи данных вылета, поворота и хода
            Row(modifier = Modifier.fillMaxWidth()) {
                BasicSettingsName(modifier = Modifier.weight(1f), text = stringResource(R.string.spacer))

                Box (modifier = Modifier.weight(10f)
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                ) { logMessagePolygon1Reset?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }


        }
    }
}



@Preview
@Composable
fun Polygon1Preview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        Polygon1Setting(
            onSetP1En = { },
            onStartResetPolygon1 = { },

            setValuePolygon1PointNum = "1",
            updateValuePolygon1PointNum = { },
            onSetValueP1PointNum = {},
            currentP1PointNum = 1,

            setValuePolygon1Height = "1",
            updateValuePolygon1Height = {},
            onSetValueP1Height =  {},
            currentP1Height = 1f,

            onSetP1UpEn =  {},
            currentP1UpEn = 1,

            onSetP1DownEn =  {},
            currentP1DownEn = 1,

            onSetP1Type1En =  {},
            onSetP1Type2En = {},
            currentP1PointType = 1,

            currentP1FewPoint = 1,
            currentP1ActiveStatus  = 1,
            currentStatsCalibration  = 1,

            errorMessagePolygon1 = null,
            logMessagePolygon1 = null,
            logMessagePolygon1Reset = null,
        )
    }
}