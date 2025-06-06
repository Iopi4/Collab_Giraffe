package com.example.giraffenew.initial_setup.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.core.ui_kit.composable.BasicTextFilledGiraffe
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun FieldTime(
    modifier: Modifier = Modifier,
    isValueHour: String,
    isValueMinute: String,
) {
// Ввод времени. Создадим два поля
    Row (horizontalArrangement = Arrangement.SpaceBetween) {

        //Поле ввода ЧАСЫ
        Box(modifier = Modifier
            .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
            .weight(1f)) {
            BasicTextFilledGiraffe(
                placeholder = "Час",
                value = isValueHour,
                onValueChange = { ""},
                modifier = Modifier,
            )
        }

        //Поле ввода МИНУТЫ
        Box (modifier = Modifier
            .padding(start = 5.dp, end = 15.dp, top = 5.dp, bottom = 0.dp)
            .weight(1f)
        ) {
            BasicTextFilledGiraffe(
                placeholder = "Mин",
                value = isValueMinute,
                onValueChange = { ""},
                modifier = Modifier,
            )

        }
    }

}

@Preview
@Composable
fun FieldTimePreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        FieldTime(
            isValueHour = "",
            isValueMinute = "",
        )
    }
}