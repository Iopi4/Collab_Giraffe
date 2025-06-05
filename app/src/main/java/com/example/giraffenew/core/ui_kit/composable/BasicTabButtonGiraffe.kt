package com.example.giraffenew.core.ui_kit.composable

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
fun BasicTabButtonGiraffe(
    modifier: Modifier = Modifier,
    text: Int,
    onClick: () -> Unit,
    function: () -> Unit,
    ) {

    val coroutineScope = rememberCoroutineScope()
    var buttonEnabled by remember { mutableStateOf(true) } // Button state

    val myBoxButtonModifier = Modifier
        .padding(start = 7.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()

    Box(myBoxButtonModifier){
        Button(
            modifier = modifier
                .size(width = 350.dp, height = 30.dp),
            onClick = onClick,
            contentPadding = PaddingValues(2.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.Grew_50_percent),
                contentColor = Color.Black),
        ) {
            Text( text = stringResource(id = text), fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }

}

@Preview
@Composable
fun BasicTabButtonGiraffePreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        BasicTabButtonGiraffe(
            text = R.string.nul,
            onClick = { },
        ) {}
    }
}