package com.example.giraffenew.setting_load_capacity.text

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.R



@Composable
fun TextLoadCapacityMessageCalibration(
    modifier: Modifier,
    valueParameterId: Int,
){

    //Порядок калибровки датчика усилия
    //0 - пустое сообщение
    //1 - Датчик вылета не настроен;
    //2 - Датчик вылета настроен не корректно;
    //3 - Некорректное значение минимального калибровочного веса
    //4 - Значение вылета совпадает с предыдущей точкой
    //5 - Изменение калибровочного веса
    //6 - Точка №1 минимума откалибрована
    //7 - Точка №2 минимума откалибрована
    //8 - Точка №1 максимума откалибрована
    //9 - Точка №2 максимума откалибрована
    //10 - Точки минимума сброшены
    //11 - Точки максимума сброшены
    //12 - Настройке минимальный вес
    //13 - Вылет не соответствует точке №1 минимума
    //14 - Вылет не соответствует точке №2 минимума
    //15 - Не известная ошибка, множество ошибок
    val textStatus: String = when (valueParameterId) {
        1 -> stringResource(R.string.SettingLoadCapacity_Text_Message_1)
        2 -> stringResource(R.string.SettingLoadCapacity_Text_Message_2)
        3 -> stringResource(R.string.SettingLoadCapacity_Text_Message_3)
        4 -> stringResource(R.string.SettingLoadCapacity_Text_Message_4)
        5 -> stringResource(R.string.SettingLoadCapacity_Text_Message_5)
        6 -> stringResource(R.string.SettingLoadCapacity_Text_Message_6)
        7 -> stringResource(R.string.SettingLoadCapacity_Text_Message_7)
        8 -> stringResource(R.string.SettingLoadCapacity_Text_Message_8)
        9 -> stringResource(R.string.SettingLoadCapacity_Text_Message_9)
        10 -> stringResource(R.string.SettingLoadCapacity_Text_Message_10)
        11 -> stringResource(R.string.SettingLoadCapacity_Text_Message_11)
        12 -> stringResource(R.string.SettingLoadCapacity_Text_Message_12)
        13 -> stringResource(R.string.SettingLoadCapacity_Text_Message_13)
        14 -> stringResource(R.string.SettingLoadCapacity_Text_Message_14)
        15 -> stringResource(R.string.SettingLoadCapacity_Text_Message_15)
        else -> stringResource(R.string.SettingLoadCapacity_Text_Message_0)
    }

    val backgroundColor = when (valueParameterId) {
        0,1,2,3,4,5,10,11 -> colorResource(R.color.Orange_50_percent)
        6,7,8,9, -> colorResource(R.color.Emerald)
        12,13,14,15 -> colorResource(R.color.Lemon)
        else -> colorResource(R.color.Orange_50_percent)// default color
    }


    val myBoxVisualValuesModifier = Modifier
        .clip(shape = RoundedCornerShape(5.dp))
        .background(backgroundColor)
        .size(width = 200.dp, height = 75.dp)
        .border(
            width = 2.dp,
            color = colorResource(R.color.Lavanda),
            shape = RoundedCornerShape(5.dp)
        )

    Box (myBoxVisualValuesModifier) {
        Text(modifier = modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp, top = 5.dp,bottom = 5.dp),
            text = textStatus,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            color = colorResource(R.color.DarkGrew)
        )
    }




}

@Preview
@Composable
fun TextLoadCapacityMessageCalibrationPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        TextLoadCapacityMessageCalibration(
            modifier = Modifier,
            valueParameterId = 1,
        )
    }
}