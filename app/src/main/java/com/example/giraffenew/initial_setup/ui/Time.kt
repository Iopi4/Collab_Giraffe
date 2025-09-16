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
fun FieldTime(
    isValueHour: String,
    isValueMinute: String,
    onHourChange: (String) -> Unit,
    onMinuteChange: (String) -> Unit
) {
// Ввод времени. Создадим два поля
    Row (horizontalArrangement = Arrangement.SpaceBetween) {

        //Поле ввода ЧАСЫ
        Box(modifier = Modifier
            .padding(start = 10.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
            .weight(1f)) {
            BasicTextFilledGiraffeDecimal(
                placeholder = "Час",
                value = isValueHour,
                onValueChange = { if (it.isEmpty() || it.toIntOrNull()?.let { v -> v in 0..23 } == true) { onHourChange(it) } },
                modifier = Modifier,
            )
        }

        //Поле ввода МИНУТЫ
        Box (modifier = Modifier
            .padding(start = 5.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)
            .weight(1f)
        ) {
            BasicTextFilledGiraffeDecimal(
                placeholder = "Mин",
                value = isValueMinute,
                onValueChange = {if (it.isEmpty() || it.toIntOrNull()?.let { v -> v in 0..59 } == true) { onMinuteChange(it) } },
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
            isValueHour = "TODO()",
            isValueMinute = "TODO()",
            onHourChange = {},
            onMinuteChange = { },
        )
    }
}