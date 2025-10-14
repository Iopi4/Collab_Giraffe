package com.example.giraffenew.parameter_logger.ui.load

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
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.home.ui.scale.ScaleHorizonWeightHi
import com.example.giraffenew.parameter_logger.lamp.LampSaveFileStatus
import com.example.giraffenew.parameter_logger.text.StatusSDcard
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun Load(
    hmiReadSDcardStatus: Int, //Статус SD-карты

    onSetSaveVarFile: () -> Unit, // Записать VAR-файл
    onSetSaveAbortVarFile: () -> Unit, // Отмена записи VAR-файла
    hmiReadVarSaveProcess: Float, //Процес выполнения сохранения 0-100%
    hmiReadVarSaveStatus: Int, //Статус сохранения VAR-файла 0-Ожидание, 1- Завершено, 2- В процессе, 4-Ошибка.
    hmiReadVarSize: Float, //Объем VAR-файла

    onSetReadSizeVarFile: () -> Unit, // Считать объем VAR-файла
    onSetReadVarFile: () -> Unit, // Считать VAR-файл
    hmiReadVarReadProcess: Float, //Процес выполнения чтения 0-100%
    hmiReadVarReadStatus: Int, //Статус чтения VAR-файла 0-Ожидание, 1- Завершено, 2- В процессе, 4-Ошибка.
    onSetReadAbortVarFile: () -> Unit,// Отмена чтения VAR-файла

    onSetSaveRPFileToSD: () -> Unit, // Записать файл регистратора параметров работы крана на SD-карту

    ){
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
//№.1 - Левая область для отображения списка исполнительных механизмов
        Column(
            modifier = Modifier
                .fillMaxSize().weight(3f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_SD_Cart_Status)
// Заголовок "Сохранение настроек СБКБ"
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.ParameterLogger_Text_Logger_Save_SBKB))
//Отображения названия кнопок
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Logger_Save_VarFile)
            Spacer(modifier = Modifier.size(40.dp))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Logger_Abort_Save_VarFile)
            Spacer(modifier = Modifier.size(15.dp))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Logger_Read_Size_VarFile)
            Spacer(modifier = Modifier.size(70.dp))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Logger_Read_VarFile)
            Spacer(modifier = Modifier.size(40.dp))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Logger_Abort_Read_VarFile)

// Заголовок  "Сохранение параметров крана"
            Spacer(modifier = Modifier.size(100.dp))
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.ParameterLogger_Text_Logger_Save_ParametersCrane))
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.ParameterLogger_Text_Logger_Save_RP)
        }



//№.2 - Правая область для отображения кнопок записи и чтения регистратора параметров
        Column(
            modifier = Modifier
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize().border(
                    width = 2.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {
            val myBoxValuesSettingsParameterModifier = Modifier
                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)

// Отображение Cтатус FLASH-карты
            Box(myBoxValuesSettingsParameterModifier) {
                StatusSDcard(modifier = Modifier,
                    valueParameterId = hmiReadSDcardStatus)
            }
// Отступ
            BasicSettingsName(modifier = Modifier, text = stringResource(R.string.spacer))
// Кнопка. Сохранить настройки СБКБ
            Row(horizontalArrangement = Arrangement.Start) {
                //Кнопка ввода
                Box(modifier = Modifier.weight(4f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.ParameterLogger_Button_Logger_Write,
                        onClick = onSetSaveVarFile,
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    LampSaveFileStatus(
                        isLampStatus = hmiReadVarSaveStatus
                    )
                }
            }
            Spacer(modifier = Modifier.size(15.dp))
//Шкала записи на SD-карту
            Box(modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)) {
                ScaleHorizonWeightHi(
                currentValue = hmiReadVarSaveProcess,
                minValue = 0f,
                maxValue = 10f,
                modifier = Modifier.size(width = 200.dp, height = 10.dp)
            ) }

            Spacer(modifier = Modifier.size(15.dp))

// Кнопка. Отмена сохранения настроек СБКБ
            Box(modifier = Modifier) {
                BasicButtonGiraffe(
                    modifier = Modifier,
                    text = R.string.ParameterLogger_Button_Abort_Logger_Read,
                    onClick = onSetSaveAbortVarFile,
                ) {}
            }
            Spacer(modifier = Modifier.size(15.dp))


// Кнопка. Считать размер файла настроек СБКБ
            Row(horizontalArrangement = Arrangement.Start) {
                //Кнопка ввода
                Box(modifier = Modifier.weight(2f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.Button_ON,
                        onClick = onSetReadSizeVarFile,
                    ) {}
                }
                Box(modifier = Modifier.padding(start = 5.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)
                    .weight(2f)) {
                    BasicValuesSettingsParameterGiraffe(modifier = Modifier,
                        valueParameterId = formatDecimal(hmiReadVarSize)
                    )
                }
            }
            Spacer(modifier = Modifier.size(70.dp))


// Кнопка. Считать настройки СБКБ
            Row(horizontalArrangement = Arrangement.Start) {
                //Кнопка ввода
                Box(modifier = Modifier.weight(4f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.ParameterLogger_Button_Logger_Read,
                        onClick = onSetReadVarFile,
                    ) {}
                }
                Box(modifier = Modifier.weight(1f)) {
                    LampSaveFileStatus(
                        isLampStatus = hmiReadVarReadStatus
                    )
                }
            }
            Spacer(modifier = Modifier.size(15.dp))
//Шкала зугрузки с SD-карты
            Box(modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)) {
                ScaleHorizonWeightHi(
                    currentValue = hmiReadVarReadProcess,
                    minValue = 0f,
                    maxValue = 10f,
                    modifier = Modifier.size(width = 200.dp, height = 10.dp)
                ) }
            Spacer(modifier = Modifier.size(15.dp))

// Кнопка. Отмена загрузки
            BasicButtonGiraffe(
                modifier = Modifier,
                text = R.string.ParameterLogger_Button_Abort_Logger_Read,
                onClick = onSetReadAbortVarFile,
            ) {}

            Spacer(modifier = Modifier.size(125.dp))
// Кнопка. Сохранения данных работы крана на SD-карту
            Row(horizontalArrangement = Arrangement.Start) {
                //Кнопка сохранения
                Box(modifier = Modifier.weight(4f)) {
                    BasicButtonGiraffe(
                        modifier = Modifier,
                        text = R.string.ParameterLogger_Button_Logger_Write,
                        onClick = onSetSaveRPFileToSD,
                    ) {}
                }
                //Статус сохранения
                Box(modifier = Modifier.weight(1f)) {
                    LampSaveFileStatus(
                        isLampStatus = 0
                    )
                }
            }




        }
    }
}


private fun formatDecimal(value: Float): String {
    return String.format("%02.1f", value).replace(',', '.')
}

@Preview
@Composable
fun LoadPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        Load(
            hmiReadSDcardStatus = 1,
            onSetSaveVarFile = {},
            onSetSaveAbortVarFile = {},
            hmiReadVarSaveProcess= 5f,
            hmiReadVarSaveStatus = 1,
            hmiReadVarSize = 3f,
            onSetReadSizeVarFile = {},
            onSetReadVarFile= {},
            hmiReadVarReadProcess = 5f,
            hmiReadVarReadStatus=1,
            onSetReadAbortVarFile = {},
            onSetSaveRPFileToSD = {},
        )
    }
}