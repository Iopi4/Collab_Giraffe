package com.example.giraffenew.initial_setup.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.core.ui_kit.composable.text.BasicTextFilledGiraffeDecimal
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun FieldDate(
    isValueDay: String,
    isValueMonth: String,
    isValueYear: String,
    onDayChange: (String) -> Unit,
    onMonthChange: (String) -> Unit,
    onYearChange: (String) -> Unit,
) {
// Ввод даты. Создадим три поля
        Row (horizontalArrangement = Arrangement.SpaceBetween) {
            //День
            Box (modifier = Modifier
                .padding(start = 0.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                .weight(3f)
            ) {
                BasicTextFilledGiraffeDecimal(
                    modifier = Modifier,
                    placeholder = "дд",
                    value = isValueDay,
                    onValueChange = { if (it.isEmpty() || it.toIntOrNull()?.let { v -> v in 0..31 }== true) {onDayChange(it)} },
                )
            }
            //Месяц
            Box (modifier = Modifier
                .padding(start = 5.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                .weight(3f)
            ) {
                BasicTextFilledGiraffeDecimal(
                    placeholder = "мм",
                    value = isValueMonth,
                    onValueChange = { if (it.isEmpty() || it.toIntOrNull()?.let { v -> v in 0..12 }== true) {onMonthChange(it)} },
                    modifier = Modifier,
                )
            }
            //Год
            Box (modifier = Modifier
                .padding(start = 5.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                .weight(5f)) {
                BasicTextFilledGiraffeDecimal(
                    placeholder = "гггг",
                    value = isValueYear,
                    onValueChange = { if (it.isEmpty() || it.toIntOrNull()?.let { v -> v in 0..2099 }== true) {onYearChange(it)} },
                    modifier = Modifier,
                )
            }

        }
}

@Preview
@Composable
fun FieldDatePreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        FieldDate(
            isValueDay = "TODO()",
            isValueMonth = "TODO()",
            isValueYear = "TODO()",
            onDayChange = { },
            onMonthChange = { },
            onYearChange = { },
        )
    }
}