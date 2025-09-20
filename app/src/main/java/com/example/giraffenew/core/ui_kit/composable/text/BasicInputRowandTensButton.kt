package com.example.giraffenew.core.ui_kit.composable.text

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.button.ButtonOn

@Composable
fun BasicInputRowandTensButton(
    modifier: Modifier = Modifier,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    onClick: () -> Unit,
    isDecimal: Boolean = false,
) {
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Поле ввода
        Box(
            modifier = Modifier
                .padding(start = 10.dp, end = 0.dp, top = 5.dp)
                .weight(4f)
        ) {
            BasicTextFilledGiraffeDecimal(
                placeholder = placeholder,
                value = value,
                onValueChange = onValueChange,
                isDecimal = isDecimal,
                modifier = Modifier
            )
        }

        // Кнопка
        Box(modifier = Modifier.weight(2f)) {
            ButtonOn(
                modifier = Modifier,
                text = R.string.Button_ON,
                onClick = onClick
            ){}
        }
    }
}