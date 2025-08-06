package com.example.giraffenew.initial_setup.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.core.ui_kit.composable.text.BasicTextFilledGiraffe
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun FieldDate(
    modifier: Modifier = Modifier,
    isValueDay: String,
    isValueMonth: String,
    isValueYear: String,
) {
// Ввод времени. Создадим два поля
    Row (horizontalArrangement = Arrangement.SpaceBetween) {

// Ввод даты
        Row (horizontalArrangement = Arrangement.SpaceBetween) {
            //День
            Box (modifier = Modifier
                .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                .weight(1f)
            ) {
                BasicTextFilledGiraffe(
                    placeholder = "ДД",
                    value = isValueDay,
                    onValueChange = { ""},
                    modifier = Modifier,
                )
            }
            //Месяц
            Box (modifier = Modifier
                .padding(start = 5.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
                .weight(1f)
            ) {
                BasicTextFilledGiraffe(
                    placeholder = "ММ",
                    value = isValueMonth,
                    onValueChange = { ""},
                    modifier = Modifier,
                )
            }
            //Год
            Box (modifier = Modifier
                .padding(start = 5.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)
                .weight(2f)) {
                BasicTextFilledGiraffe(
                    placeholder = "ГГГГ",
                    value = isValueYear,
                    onValueChange = { ""},
                    modifier = Modifier,
                )
            }

        }
    }

}

@Preview
@Composable
fun FieldDatePreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        FieldDate(
            isValueDay = "",
            isValueMonth = "",
            isValueYear = "",
        )
    }
}