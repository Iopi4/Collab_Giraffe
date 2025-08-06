package com.example.giraffenew.core.ui_kit.composable.alarm_waring.waring

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.R
import com.example.giraffenew.ui.theme.Black
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Silver

@Composable
fun LampWaringItem (
    date: String,
    time: String,
    description: String,
    isWaringOff: Boolean,
    textColor: Color = Black,)
{
    val backgroundColorId = if (isWaringOff) {colorResource(R.color.DarkLemon)}
    else {colorResource(R.color.Lemon)}

//Создаем поле в котором разделим экраны на три вертикальные области №1, №2, №3.
    Row(modifier = Modifier
        .background(color = backgroundColorId)
        .padding(all = 1.dp)
        .fillMaxWidth()
        .border(
            width = 1.dp,
            color = Silver,
            shape = RoundedCornerShape(1.dp)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically)
    {
        val myBoxDateTimeModifier = Modifier
            .padding(all = 1.dp)
            .weight(1f)
            .clip(shape = RoundedCornerShape(1.dp))
            .fillMaxWidth()

        val myBoxDescriptionModifier = Modifier
            .padding(all = 1.dp)
            .weight(4f)
            .clip(shape = RoundedCornerShape(1.dp))
            .fillMaxWidth()

//Поле №1 отображения даты Аварии
        Box(myBoxDateTimeModifier) {
            Text(modifier = Modifier.padding(start = 2.dp, end = 2.dp),
                text = date,
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium)
        }
//Поле №2 отображения времени Аварии
        Box(myBoxDateTimeModifier) {
            Text(modifier = Modifier.padding(start = 2.dp, end = 2.dp),
                text = time,
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium)
        }
//Поле №3 отображения текста Аварии
        Box(myBoxDescriptionModifier) {
            Text(modifier = Modifier.padding(start = 2.dp, end = 2.dp),
                text = description,
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium)
        }
        //Кнопка снятия бита Активной Аварии (что крановщик ознакомился с аварией)

    }
}



@Preview
@Composable
fun LampWaringItemPreview(){
    GIRAFFEnewTheme (dynamicColor = false) {
        LampWaringItem (
            date = "date",
            time = "time",
            description = "description",
            false,
        )
    }
}