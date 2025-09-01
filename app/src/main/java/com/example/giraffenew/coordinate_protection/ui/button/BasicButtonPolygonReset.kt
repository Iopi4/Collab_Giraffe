package com.example.giraffenew.coordinate_protection.ui.button

import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.ui.input.pointer.pointerInput
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
fun BasicButtonPolygonReset(
    modifier: Modifier = Modifier,
    text: Int,
    onStartReset: () -> Unit,
    onCancelReset: () -> Unit,
    ) {
    Box(modifier = modifier
        .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .pointerInput(Unit) {
            detectTapGestures(
                onPress = {
                    onStartReset()
                    tryAwaitRelease()
                    onCancelReset()
                }
            )
        }){
        Button(
            modifier = Modifier
                .size(width = 400.dp, height = 30.dp),
            onClick = {},
            contentPadding = PaddingValues(2.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red.copy(alpha = 0.5f),
                contentColor = Color.White),
        ) {
            Text( text = stringResource(id = text), fontSize = 19.sp, fontWeight = FontWeight.Bold)
        }
    }

}

@Preview
@Composable
fun BasicButtonPolygonResetPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        BasicButtonPolygonReset(
            text = R.string.nul,
            onStartReset={},
            onCancelReset = {},
        )
    }
}