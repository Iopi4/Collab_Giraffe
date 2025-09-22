package com.example.giraffenew.initial_setup.ui

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
import com.example.giraffenew.core.ui_kit.composable.text.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.text.BasicSettingsName
import com.example.giraffenew.core.ui_kit.composable.text.BasicTextFilledGiraffeDecimal
import com.example.giraffenew.initial_setup.button.ButtonModel
import com.example.giraffenew.core.ui_kit.composable.button.ButtonOn
import com.example.giraffenew.initial_setup.button.ButtonOnModel
import com.example.giraffenew.initial_setup.domain.CraneEntity
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun ParametersCraneAndSBKB(

    selectedCrane: CraneEntity?,
    onSelectCrane: () -> Unit,
    onSetModelCrane: (Int) -> Unit,
    selectedCraneId: Int?,
    currentModelCrane: String,

    setValueNumberCrane: String,
    onNumberChangeCrane: (String) -> Unit,
    onSetNumberCrane: () -> Unit,

    setValueNumberSBKB: String,
    onNumberChangeSBKB: (String) -> Unit,
    onSetNumberSBKB: () -> Unit,

    setValueDayCrane: String, //AT%MW854 (* Days 1 to 31 *)
    setValueMonthCrane: String, //AT%MW855 (* Mounths 1 to 12 *)
    setValueYearCrane: String, //AT%MW856 (* min 2006 *)
    onDayChangeCrane: (String) -> Unit,
    onMonthChangeCrane: (String) -> Unit,
    onYearChangeCrane: (String) -> Unit,
    onSetDateCrane: () -> Unit,
    errorMessageCrane: String?,

    setValueDayRTN: String, //AT%MW851 (* Days 1 to 31 *)
    setValueMonthRTN: String, //AT%MW852 (* Mounths 1 to 12 *)
    setValueYearRTN: String, //AT%MW853 (* min 2006 *)
    onDayChangeRTN: (String) -> Unit,
    onMonthChangeRTN: (String) -> Unit,
    onYearChangeRTN: (String) -> Unit,
    onSetDateRTN: () -> Unit,
    errorMessageRTN: String?,

    setValueDaySBKB: String, //AT%MW858 (* Days 1 to 31 *)
    setValueMonthSBKB: String, //AT%MW859 (* Mounths 1 to 12 *)
    setValueYearSBKB: String, //AT%MW860 (* min 2006 *)
    onDayChangeSBKB: (String) -> Unit,
    onMonthChangeSBKB: (String) -> Unit,
    onYearChangeSBKB: (String) -> Unit,
    onSetDateSBKB: () -> Unit,
    errorMessageSBKB: String?,
    logMessageCrane: String?,
    logMessageRTN: String?,
    logMessageSBKB: String?,

) {

    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {

        //№.1 - Левая область для отображения Бокса - Данные крана и СБКБ)
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
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.InitialSetup_Text_Parameters_Crane_And_SBKB))
            //Бокс 1.2 для отображения текста параметров крана
                    BasicNameSettingParameterGiraffe(settingParameterId = R.string.InitialSetup_TextFilled_Model_Crane)
                    BasicNameSettingParameterGiraffe(settingParameterId = R.string.InitialSetup_TextFilled_Number_Crane)
                    BasicNameSettingParameterGiraffe(settingParameterId = R.string.InitialSetup_TextFilled_Registration)
                    BasicNameSettingParameterGiraffe(settingParameterId = R.string.InitialSetup_TextFilled_Operation)
                    BasicNameSettingParameterGiraffe(settingParameterId = R.string.InitialSetup_TextFilled_Number_SBKB)
                    BasicNameSettingParameterGiraffe(settingParameterId = R.string.InitialSetup_TextFilled_Date_SBKB)
        }

//№.2 - Правая область для отображения и ввода параметров. ( 2-Тип крана)
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

//Бокс 2.2 для отображения и ввода параметров. (2-Данные крана и СБКБ)
//Отступ
            BasicSettingsName(modifier = Modifier,
                text = stringResource(R.string.spacer))
//Ввод. Модель крана
            Row (horizontalArrangement = Arrangement.SpaceBetween) {
                //Поле ввода значения максимального веса груза
                Box (modifier = Modifier
                    .padding(start = 10.dp, end = 0.dp, top = 5.dp)
                    .weight(3f)
                ) {
                    ButtonModel(
                        modifier = Modifier,
                        onClick = onSelectCrane,
                        text = selectedCrane?.craneModel ?:currentModelCrane,
                        //stringResource(R.string.InitialSetup_Button_Model),
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(1f)) {
                    ButtonOnModel(
                        modifier = Modifier,
                        text = R.string.Button_ON,
                        onClick = { selectedCraneId?.let { onSetModelCrane(it) } },
                        craneId = selectedCraneId
                    )
                }
            }

//Ввод. Заводской номер крана
            Row (horizontalArrangement = Arrangement.SpaceBetween) {
                //Поле ввода значения максимального веса груза
                Box (modifier = Modifier
                    .padding(start = 10.dp, top = 5.dp)
                    .weight(3f)
                ) {
                    BasicTextFilledGiraffeDecimal(
                        placeholder = "№",
                        value = setValueNumberCrane,
                        onValueChange = onNumberChangeCrane,
                        modifier = Modifier,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(1f)) {
                    ButtonOn(
                        modifier = Modifier,
                        text = R.string.Button_ON,
                        onClick = onSetNumberCrane,
                    ) {}
                }
            }
//Ввод. Дата регистр. в Ростехнадзоре
            Row (horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения максимального веса груза
                Box (modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(3f)
                ) { FieldDate(
                        isValueDay = setValueDayRTN,
                        isValueMonth = setValueMonthRTN,
                        isValueYear = setValueYearRTN,
                        onDayChange = onDayChangeRTN,
                        onMonthChange = onMonthChangeRTN,
                        onYearChange = onYearChangeRTN
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(1f)) {
                    ButtonOn(
                        text = R.string.Button_ON,
                        onClick = onSetDateRTN,
                    ) {}
                }

            }
//Ввод. Дата ввода крана в эксплуатацию
            Row (horizontalArrangement = Arrangement.SpaceBetween) {
                //Поле ввода значения максимального веса груза
                Box (modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(3f)
                ) {
                    FieldDate(
                        isValueDay = setValueDayCrane,
                        isValueMonth = setValueMonthCrane,
                        isValueYear = setValueYearCrane,
                        onDayChange = onDayChangeCrane,
                        onMonthChange = onMonthChangeCrane,
                        onYearChange = onYearChangeCrane,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(1f)) {
                    ButtonOn(
                        modifier = Modifier,
                        text = R.string.Button_ON,
                        onClick = onSetDateCrane,
                    ) {}
                }
            }
//Ввод. Заводской номер СБКБ
            Row (horizontalArrangement = Arrangement.SpaceBetween) {
                //Поле ввода значения максимального веса груза
                Box (modifier = Modifier
                    .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                    .weight(3f)
                ) {
                    BasicTextFilledGiraffeDecimal(
                        placeholder = "№",
                        value = setValueNumberSBKB,
                        onValueChange = onNumberChangeSBKB,
                        modifier = Modifier,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(1f)) {
                    ButtonOn(
                        modifier = Modifier,
                        text = R.string.Button_ON,
                        onClick = onSetNumberSBKB,
                    ) {}
                }
            }
//Ввод. Дата установки СБКБ
            Row (horizontalArrangement = Arrangement.Start) {
                //Поле ввода значения максимального веса груза
                Box (modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(3f)
                ) {
                    FieldDate(
                        isValueDay = setValueDaySBKB,
                        isValueMonth = setValueMonthSBKB,
                        isValueYear = setValueYearSBKB,
                        onDayChange = onDayChangeSBKB,
                        onMonthChange = onMonthChangeSBKB,
                        onYearChange = onYearChangeSBKB,
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(1f)) {
                    ButtonOn(
                        modifier = Modifier,
                        text = R.string.Button_ON,
                        onClick = onSetDateSBKB,
                    ) {}
                }
            }
            Box (modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp)
            ) { errorMessageCrane?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp)) }
                logMessageCrane?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp)) }
            }
            Box (modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp)
            ) { errorMessageSBKB?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp)) }
                logMessageSBKB?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp)) }
            }
            Box (modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 5.dp)
            ) { errorMessageRTN?.let { Text(text = it, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp)) }
                logMessageRTN?.let { Text(text = it, fontWeight = FontWeight.Bold, color = Color.DarkGray, modifier = Modifier.padding(top = 8.dp)) }
            }

        }
    }
}



@Preview
@Composable
fun ParametersCraneAndSBKBPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        ParametersCraneAndSBKB(

            selectedCrane = CraneEntity(14, "") ,
            onSelectCrane= { },
            onSetModelCrane= { },
            selectedCraneId= 14,
            currentModelCrane = "",

            setValueNumberCrane = "1",
            onNumberChangeCrane = { },
            onSetNumberCrane = { },

            setValueNumberSBKB = "1",
            onNumberChangeSBKB = { },
            onSetNumberSBKB = { },

            setValueDayCrane = "1",
            setValueMonthCrane = "1",
            setValueYearCrane = "2000",
            onDayChangeCrane = { },
            onMonthChangeCrane = { },
            onYearChangeCrane = { },
            onSetDateCrane = { },
            errorMessageCrane = null,

            setValueDayRTN = "1",
            setValueMonthRTN = "1",
            setValueYearRTN = "2000",
            onDayChangeRTN = { },
            onMonthChangeRTN = { },
            onYearChangeRTN = { },
            onSetDateRTN = { },
            errorMessageRTN = null,

            setValueDaySBKB = "1",
            setValueMonthSBKB = "1",
            setValueYearSBKB = "2000",
            onDayChangeSBKB = { },
            onMonthChangeSBKB = { },
            onYearChangeSBKB = { },
            onSetDateSBKB = { },
            errorMessageSBKB = null,
            logMessageCrane = null,
            logMessageSBKB = null,
            logMessageRTN = null,

        )
    }
}