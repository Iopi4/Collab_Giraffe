package com.example.giraffenew.core.ui_kit.composable.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.R
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme


@Composable
//Базовая кнопка с заполненным фоном. Ее можно будет использовать в разных частях приложения,
//например в качестве кнопки сохранения или обновления данных
fun BasicButtonGiraffeAccess(
    modifier: Modifier = Modifier,
    text: Int,
    onClick: () -> Unit,
    isLocked: Boolean = false, // Добавляем параметр блокировки
    ) {
    val containerColor = if (isLocked){
        colorResource(R.color.Burgundy_violet).copy(alpha = 0.6f)
    } else {
        colorResource(R.color.Burgundy_violet)
    }

    val contentColor = if (isLocked){
        Color.White.copy(alpha = 0.6f)
    } else {
        Color.White
    }

    Box(modifier = modifier
        .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()){
        Button(
            modifier = Modifier
                .size(width = 400.dp, height = 30.dp),
            onClick = onClick,
            contentPadding = PaddingValues(2.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor,
                contentColor = contentColor),
        ) {
            Text( text = stringResource(id = text), fontSize = 19.sp, fontWeight = FontWeight.Bold)
        }
    }

}

@Preview
@Composable
fun BasicButtonGiraffeAccessPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        BasicButtonGiraffeAccess(
            text = R.string.nul,
            isLocked = false,
            onClick = { },
            modifier = Modifier,
        )
    }
}