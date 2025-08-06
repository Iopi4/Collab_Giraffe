package com.example.giraffenew.accident_and_warning_log.presentation.screen

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.accident_and_warning_log.domian.model.EventEntity
import com.example.giraffenew.accident_and_warning_log.domian.model.EventType
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Silver
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


//3. Компонент EventRow, отвечает за:
// - Форматирование даты и времени
// - Определение цвета фона
// - Отображение строки
@Composable
fun EventRow(event: EventEntity) {
    val date = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        .format(Date(event.timestamp))
    val time = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        .format(Date(event.timestamp))

    val color = when (event.type) {
        EventType.ACCIDENT -> Color.Red.copy(alpha = 0.8f)
        EventType.WARNING -> Color.Yellow.copy(alpha = 0.8f)
    }

    val textColor = when (event.type) {
        EventType.ACCIDENT -> Color.White
        EventType.WARNING -> Color.Black
    }

    Row(modifier = Modifier
        .background(color = color)
        .padding(all = 1.dp)
        .fillMaxWidth()
        .border(
            width = 1.dp,
            color = Silver,
            shape = RoundedCornerShape(1.dp)
        ),
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
            .weight(5f)
            .clip(shape = RoundedCornerShape(1.dp))
            .fillMaxWidth()

        //Столбец №1 отображения даты Аварии
        Box(myBoxDateTimeModifier) {
            Text(modifier = Modifier.padding(start = 2.dp, end = 2.dp),
                text = date,
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium)
        }
        //Столбец №2 отображения времени Аварии
        Box(myBoxDateTimeModifier) {
            Text(modifier = Modifier.padding(start = 2.dp, end = 2.dp),
                text = time,
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium)
        }
        //Столбец №3 отображения текста Аварии
        Box(myBoxDescriptionModifier) {
            Text(modifier = Modifier.padding(start = 2.dp, end = 2.dp),
                text = event.description,
                color = textColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium)
        }
    }
}

@Preview
@Composable
fun LampWaringItemPreview(){
    GIRAFFEnewTheme (dynamicColor = false) {
        EventRow(
            event = EventEntity(
                id = 1,
                registrationNumber = "1",
                type = EventType.WARNING,
                description = "description",
                isActive = true,
                timestamp = System.currentTimeMillis()
            )
        )
    }
}