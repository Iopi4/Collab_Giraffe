package com.example.giraffenew.initial_setup.ui

import android.os.Message
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
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.initial_setup.button.ButtonModelCrane
import com.example.giraffenew.initial_setup.button.ButtonOnModelCrane
import com.example.giraffenew.initial_setup.domain.CraneEntity
import com.example.giraffenew.initial_setup.domain.TimeZoneEntity
import com.example.giraffenew.ui.theme.Burgundy_violet

@Composable
fun ParametersDateAndTime(
    currentTime: String,
    currentDate: String,

    selectedTimeZone: TimeZoneEntity?,
    onSelectTimeZone: () -> Unit,
    onSetTimeZone: (Int) -> Unit,
    selectedTimeZoneUTC: Int?,

    setValueHour: String, //AT%MW846 (* Hours 0 to 23 *)
    setValueMinute: String, //AT%MW847 (* Minutes 0 to 59 *)
    setValueDay: String, //AT%MW845 (* Days 1 to 31 *)
    setValueMonth: String, //AT%MW844 (* Mounths 1 to 12 *)
    setValueYear: String, //AT%MW843 (* min 2006 *)
    onHourChange: (String) -> Unit,
    onMinuteChange: (String) -> Unit,
    onDayChange: (String) -> Unit,
    onMonthChange: (String) -> Unit,
    onYearChange: (String) -> Unit,

    onSetDateTime: () -> Unit,
    errorMessage: String?,
    logMessageDateTime: String?,
    ) {
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize())
    {
        //№.1 - Левая область для отображения 1 Бокс - Дата и время
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
//Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.InitialSetup_Text_Setting_Date_Time))

            BasicNameSettingParameterGiraffe(settingParameterId = R.string.InitialSetup_Text_Current_Time)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.InitialSetup_Text_Current_Date)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.InitialSetup_Text_Current_TimeZone)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.InitialSetup_TextFilled_Time)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.InitialSetup_TextFilled_Date)

            Box (modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp)
            ) { errorMessage?.let { Text(text = it, color = Color.Red, modifier = Modifier.padding(top = 8.dp)) }
                logMessageDateTime?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp)) }
            }

        }

//№.2 - Правая область для отображения и ввода параметров. (1- Дата и время)
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
//Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
// Отображение текущего времени и даты
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp))
            { BasicValuesSettingsParameterGiraffe(modifier = Modifier, valueParameterId = currentTime) }
            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 5.dp))
            { BasicValuesSettingsParameterGiraffe(modifier = Modifier, valueParameterId = currentDate) }
//Ввод. Модель крана
            Row (horizontalArrangement = Arrangement.SpaceBetween) {
                //Поле ввода значения максимального веса груза
                Box (modifier = Modifier
                    .padding(start = 10.dp, end = 0.dp, top = 5.dp)
                    .weight(3f)
                ) {
                    ButtonModelCrane(
                        modifier = Modifier,
                        onClick = onSelectTimeZone,
                        text = selectedTimeZone?.timeZoneRegion?:
                        stringResource(R.string.InitialSetup_Button_Model),
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(1f)) {
                    ButtonOnModelCrane(
                        modifier = Modifier,
                        text = R.string.Button_ON,
                        onClick = { selectedTimeZoneUTC?.let { onSetTimeZone(it) } },
                        craneId = selectedTimeZoneUTC
                    )
                }
            }
// Ввод времени. Создадим два поля
            FieldTime(
                isValueHour = setValueHour,
                isValueMinute = setValueMinute,
                onHourChange = onHourChange,
                onMinuteChange = onMinuteChange,)
            // Ввод даты

            Box(modifier = Modifier.padding(start = 10.dp, end = 10.dp))
            {FieldDate(
                isValueDay = setValueDay,
                isValueMonth = setValueMonth,
                isValueYear = setValueYear,
                onDayChange = onDayChange,
                onMonthChange = onMonthChange,
                onYearChange = onYearChange,)}

            BasicButtonGiraffe(
                text = R.string.Button_Enter,
                onClick = onSetDateTime,
            ) {}


        }
    }
}


@Preview
@Composable
fun ParametersDataAndTimeTextPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ParametersDateAndTime(
            currentTime = "123456",
            currentDate = "123456",

            selectedTimeZone = TimeZoneEntity(12,""),
            onSelectTimeZone = {  },
            onSetTimeZone = {  },
            selectedTimeZoneUTC = 12,

            setValueHour = "12",
            setValueMinute = "12",
            setValueDay = "31",
            setValueMonth = "12",
            setValueYear = "2000",
            onHourChange = {},
            onMinuteChange = {},
            onDayChange = {},
            onMonthChange = {},
            onYearChange = {},
            onSetDateTime = { },
            errorMessage = null,
            logMessageDateTime = null,

        )
    }
}