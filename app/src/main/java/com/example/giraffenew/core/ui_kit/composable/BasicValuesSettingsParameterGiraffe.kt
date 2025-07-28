package com.example.giraffenew.core.ui_kit.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.R
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
//Функция отображения текста в левой области экрана для меню настроек
fun BasicValuesSettingsParameterGiraffe (
    modifier: Modifier,
    valueParameterId: String,
){

    var myBoxVisualValuesModifier = Modifier
        .clip(shape = RoundedCornerShape(5.dp))
        .background(colorResource(R.color.Orange_50_percent))
        .fillMaxWidth()
        .border(
            width = 2.dp,
            color = colorResource(R.color.Lavanda),
            shape = RoundedCornerShape(5.dp)
        )
    Box (myBoxVisualValuesModifier) {
        Text(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp, top = 5.dp,bottom = 5.dp),
            text = valueParameterId,
            fontSize = 19.sp, fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center, color = DarkGrew
        )
    }
}


@Preview
@Composable
fun BasicValuesSettingsParameterGiraffePreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        BasicValuesSettingsParameterGiraffe(
            modifier = Modifier,
            valueParameterId = "Value"
        )
    }
}