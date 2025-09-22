package com.example.giraffenew.coordinate_protection.ui.polygon_3

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
fun Polygon3Setting(
    onSetP3En: () -> Unit,
    onStartResetPolygon3: () -> Unit, // Новый параметр

    setValuePolygon3PointNum: String,
    updateValuePolygon3PointNum: (String) -> Unit,
    onSetValueP3PointNum: () -> Unit,
    currentP3PointNum: Int,

    setValuePolygon3Height: String,
    updateValuePolygon3Height: (String) -> Unit,
    onSetValueP3Height: () -> Unit,
    currentP3Height: Float,

    onSetP3UpEn: () -> Unit,
    currentP3UpEn: Int,

    onSetP3DownEn: () -> Unit,
    currentP3DownEn: Int,

    onSetP3Type1En: () -> Unit,
    onSetP3Type2En: () -> Unit,
    currentP3PointType: Int,

    currentP3FewPoint: Int,
    currentP3ActiveStatus: Int,
    currentStatsCalibration: Int,

    errorMessagePolygon3: String?,
    logMessagePolygon3: String?,
    logMessagePolygon3Reset: String?,

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
                ) { errorMessagePolygon3?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                    logMessagePolygon3?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }

//Поле активации полигона
            BasicButtonOnOffStatusPolygon(
                onClick = onSetP3En,
                isLampStatus = currentP3ActiveStatus
            )

//Ввод. Количество точек полигона
                BasicInputRowandButton(
                    placeholder = format(currentP3PointNum),
                    value = setValuePolygon3PointNum,
                    onValueChange = updateValuePolygon3PointNum,
                    onClick = onSetValueP3PointNum,
                    valueMin = 0,
                    valueMax = 20
                )

//Ввод. Высота полигона
                BasicInputRowandTensButton(
                    placeholder = formatDecimal(currentP3Height),
                    value = setValuePolygon3Height,
                    onValueChange = updateValuePolygon3Height,
                    onClick = onSetValueP3Height,
                    isDecimal = true,
                )

//Кнопка. Разрешить работу вверху
            BasicButtonOnOffStatus(
                onClick = onSetP3UpEn,
                isLampStatus = currentP3UpEn
            )

//Кнопка. Разрешить работу внизу
            BasicButtonOnOffStatus(
                onClick = onSetP3DownEn,
                isLampStatus = currentP3DownEn
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
                        onClick = onSetP3Type2En,
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampPointType2Status(
                        isLampStatus = currentP3PointType
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
                        onClick = onSetP3Type1En,
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampPointType1Status(
                        isLampStatus = currentP3PointType
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
                onClick = onStartResetPolygon3,
            )
            // Отступ  и Статус записи данных вылета, поворота и хода
            Row(modifier = Modifier.fillMaxWidth()) {
                BasicSettingsName(modifier = Modifier.weight(1f), text = stringResource(R.string.spacer))

                Box (modifier = Modifier.weight(10f)
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                ) { logMessagePolygon3Reset?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }


        }
    }
}

private fun formatDecimal(value: Float): String {
    return String.format("%04.1f", value).replace(',', '.')
}

private fun format(value: Int): String {
    return String.format("%02d", value)
}

@Preview
@Composable
fun Polygon3Preview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        Polygon3Setting(
            onSetP3En = { },
            onStartResetPolygon3 = { },

            setValuePolygon3PointNum = "1",
            updateValuePolygon3PointNum = { },
            onSetValueP3PointNum = {},
            currentP3PointNum = 1,

            setValuePolygon3Height = "1",
            updateValuePolygon3Height = {},
            onSetValueP3Height =  {},
            currentP3Height = 1f,

            onSetP3UpEn =  {},
            currentP3UpEn = 1,

            onSetP3DownEn =  {},
            currentP3DownEn = 1,

            onSetP3Type1En =  {},
            onSetP3Type2En = {},
            currentP3PointType = 1,

            currentP3FewPoint = 1,
            currentP3ActiveStatus  = 1,
            currentStatsCalibration  = 1,

            errorMessagePolygon3 = null,
            logMessagePolygon3 = null,
            logMessagePolygon3Reset = null,
        )
    }
}