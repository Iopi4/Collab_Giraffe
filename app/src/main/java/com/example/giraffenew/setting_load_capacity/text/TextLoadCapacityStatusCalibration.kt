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
fun TextLoadCapacityStatusCalibration(
    modifier: Modifier,
    valueParameterId: Int,
){
    //Статус калибровки датчика усилия
    // 2 Откалибрована первая точка минимума
    // 6 Откалибрована вторая точка минимума
    // 8, 14 - Откалибрована первая точка максимума
    // 24, 30 - Откалибрована вторая точка максимума
    val textStatus: String = when (valueParameterId) {
        2 -> stringResource(R.string.SettingLoadCapacity_Text_CalibrationStatus_BIT1)
        6 -> stringResource(R.string.SettingLoadCapacity_Text_CalibrationStatus_BIT2)
        8, 14 -> stringResource(R.string.SettingLoadCapacity_Text_CalibrationStatus_BIT3)
        24, 30 -> stringResource(R.string.SettingLoadCapacity_Text_CalibrationStatus_BIT4)
        else -> stringResource(R.string.SettingLoadCapacity_Text_CalibrationStatus_BIT0)
    }

    val backgroundColor = when (valueParameterId) {
        2,6,8,14,24,30 -> colorResource(R.color.Emerald)
        else -> colorResource(R.color.Orange_50_percent)// default color
    }


    val myBoxVisualValuesModifier = Modifier
        .clip(shape = RoundedCornerShape(5.dp))
        .background(backgroundColor)
        .size(width = 200.dp, height = 50.dp)
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
fun TextLoadCapacityStatusCalibrationPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        TextLoadCapacityStatusCalibration(
            modifier = Modifier,
            valueParameterId = 1,
        )
    }
}