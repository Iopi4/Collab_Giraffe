package com.example.giraffenew.service_menu.ui.user

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonOnOffStatus
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampSettingParameter
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ServiceUser(
    onSetDataLogErrStopHoist: () -> Unit,
    currentDataLogErrStopHoist: Int,

    onSetPasswordNaladka: () -> Unit,
    currentPasswordOrNaladka: Int,

    onSetFOURpoligonsEn: () -> Unit,
    currentFOURPolygonsEn: Int,

    onSetWithoutRingCurrentCollectorEn: () -> Unit,
    currentWithoutRingCurrentCollector: Int,

    onSetZoneACCDECActiveEn: () -> Unit,
    currentZoneACCDECActive: Int,
){
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //№.1 - Левая область для отображения параметров скорости и ускорений
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
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.ServiceMenu_Text_User_Registrator))
//Отображения текста параметров настройки сервисного инженера
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_User_Work_Ban)
//Отступ
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.spacer))
//Отображения текста параметров настройки сервисного инженера
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_User_Naladka)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_User_2_Polygons)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_User_Current_Collector)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ServiceMenu_Text_User_Setting_Mode_ACC_DEC)
        }

//№.2 - Правая область для отображения и ввода параметров сервисного инженера
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
//Отступ
            Spacer(modifier = Modifier.size(70.dp))
//Поле активации запрета работы подъема выше второй скорости при ошибках регистратора
            BasicButtonOnOffStatus(
                onClick = onSetDataLogErrStopHoist,
                isLampStatus = currentDataLogErrStopHoist
            )
//Отступ
            Spacer(modifier = Modifier.size(65.dp))
//Кнопка. Авторизация [Наладка]
            BasicButtonOnOffStatus(
                onClick = onSetPasswordNaladka,
                isLampStatus = currentPasswordOrNaladka
            )
            Spacer(modifier = Modifier.size(10.dp))
//Поле активации 4 полигона по 20 точек
            BasicButtonOnOffStatus(
                onClick = onSetFOURpoligonsEn,
                isLampStatus = currentFOURPolygonsEn
            )
            Spacer(modifier = Modifier.size(20.dp))
//Поле. Исполнение крана без токосъемника
            BasicButtonOnOffStatus(
                onClick = onSetWithoutRingCurrentCollectorEn,
                isLampStatus = currentWithoutRingCurrentCollector
            )
            Spacer(modifier = Modifier.size(20.dp))
//Поле. Режим плавной настройки расгона и торможения [вкл/выкл]
            BasicButtonOnOffStatus(
                onClick = onSetZoneACCDECActiveEn,
                isLampStatus = currentZoneACCDECActive
            )
        }
    }
}

@Preview
@Composable
fun ServiceUserPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ServiceUser(
            onSetDataLogErrStopHoist = { },
            currentDataLogErrStopHoist = 1,

            onSetPasswordNaladka = { },
            currentPasswordOrNaladka =1,

            onSetFOURpoligonsEn = { },
            currentFOURPolygonsEn =1,

            onSetWithoutRingCurrentCollectorEn = { },
            currentWithoutRingCurrentCollector = 1,

            onSetZoneACCDECActiveEn = { },
            currentZoneACCDECActive =1,

        )
    }
}