package com.example.giraffenew.coordinate_protection.ui.polygon_2

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
fun Polygon2Setting(
    onSetP2En: () -> Unit,
    onStartResetPolygon2: () -> Unit, // Новый параметр

    setValuePolygon2PointNum: String,
    updateValuePolygon2PointNum: (String) -> Unit,
    onSetValueP2PointNum: () -> Unit,
    currentP2PointNum: Int,

    setValuePolygon2Height: String,
    updateValuePolygon2Height: (String) -> Unit,
    onSetValueP2Height: () -> Unit,
    currentP2Height: Float,

    onSetP2UpEn: () -> Unit,
    currentP2UpEn: Int,

    onSetP2DownEn: () -> Unit,
    currentP2DownEn: Int,

    onSetP2Type1En: () -> Unit,
    onSetP2Type2En: () -> Unit,
    currentP2PointType: Int,

    currentP2FewPoint: Int,
    currentP2ActiveStatus: Int,
    currentStatsCalibration: Int,

    errorMessagePolygon2: String?,
    logMessagePolygon2: String?,
    logMessagePolygon2Reset: String?,

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
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
            // Отступ  и Статус записи данных настройки полигона
            Row(modifier = Modifier.fillMaxWidth()) {
                BasicSettingsName(modifier = Modifier.weight(1f), text = stringResource(R.string.spacer))

                Box (modifier = Modifier.weight(10f)
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                ) { errorMessagePolygon2?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                    logMessagePolygon2?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
                }
            }

//Поле активации полигона
            BasicButtonOnOffStatusPolygon(
                onClick = onSetP2En,
                isLampStatus = currentP2ActiveStatus
            )

//Ввод. Количество точек полигона
            BasicInputRowandButton(
                placeholder = format(currentP2PointNum),
                value = setValuePolygon2PointNum,
                onValueChange = updateValuePolygon2PointNum,
                onClick = onSetValueP2PointNum,
                valueMin = 0,
                valueMax = 20
            )

//Ввод. Высота полигона

            BasicInputRowandTensButton(
                placeholder = formatDecimal(currentP2Height),
                value = setValuePolygon2Height,
                onValueChange = updateValuePolygon2Height,
                onClick = onSetValueP2Height,
                isDecimal = true,
            )


//Кнопка. Разрешить работу вверху
            BasicButtonOnOffStatus(
                onClick = onSetP2UpEn,
                isLampStatus = currentP2UpEn
            )

//Кнопка. Разрешить работу внизу
            BasicButtonOnOffStatus(
                onClick = onSetP2DownEn,
                isLampStatus = currentP2DownEn
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
                        onClick = onSetP2Type2En,
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampPointType2Status(
                        isLampStatus = currentP2PointType
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
                        onClick = onSetP2Type1En,
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    BasicLampPointType1Status(
                        isLampStatus = currentP2PointType
                    )
                }
            }

            Spacer(modifier = Modifier.size(25.dp))

//Статус активации/настройки полигона
            //1.Многоугольник активирован – успешная активация)
            //2.Используется мало точек – многоугольник не активирован, если введено менее трех точек многоугольника
            //3.Настройки не завершены – многоугольник не активирован
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
                onClick = onStartResetPolygon2,
            )
            // Отступ  и Статус записи данных вылета, поворота и хода
            Row(modifier = Modifier.fillMaxWidth()) {
                BasicSettingsName(modifier = Modifier.weight(1f), text = stringResource(R.string.spacer))

                Box (modifier = Modifier.weight(10f)
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp)
                ) { logMessagePolygon2Reset?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 0.dp)) }
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
fun Polygon2Preview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        Polygon2Setting(
            onSetP2En = { },
            onStartResetPolygon2 = { },

            setValuePolygon2PointNum = "1",
            updateValuePolygon2PointNum = { },
            onSetValueP2PointNum = {},
            currentP2PointNum = 1,

            setValuePolygon2Height = "1",
            updateValuePolygon2Height = {},
            onSetValueP2Height =  {},
            currentP2Height = 1f,

            onSetP2UpEn =  {},
            currentP2UpEn = 1,

            onSetP2DownEn =  {},
            currentP2DownEn = 1,

            onSetP2Type1En =  {},
            onSetP2Type2En = {},
            currentP2PointType = 1,

            currentP2FewPoint = 1,
            currentP2ActiveStatus  = 1,
            currentStatsCalibration  = 1,

            errorMessagePolygon2 = null,
            logMessagePolygon2 = null,
            logMessagePolygon2Reset = null,
        )
    }
}