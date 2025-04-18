package com.example.giraffenew.core.ui_kit.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.R
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme


@Composable
//Базовая кнопка с заполненным фоном. Ее можно будет использовать в разных частях приложения,
//например в качестве кнопки сохранения или обновления данных
fun BasicFilledButtonGiraffe(
    modifier: Modifier= Modifier,
    text: String,
    onClick: () -> Unit,
    ) {
    Button(
    modifier = modifier
        .size(width = 150.dp, height = 30.dp),
    onClick = onClick,
    contentPadding = PaddingValues(2.dp),
    shape = RoundedCornerShape(5.dp),
    colors = ButtonDefaults.buttonColors(
        containerColor = colorResource(R.color.Burgundy_violet),
        contentColor = Color.White),
) {
        Text( text = text, fontSize = 20.sp)
    }
}

@Preview
@Composable
fun BasicFilledButtonGiraffePreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        BasicFilledButtonGiraffe(
            modifier = Modifier,
            text = "Create",
            onClick = { },
        )
    }
}