package com.example.giraffenew.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.core.ui_kit.composable.BasicParametersText
import com.example.giraffenew.ui.theme.Black
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Silver

@Composable
fun Information (
    modifier: Modifier= Modifier,
){

    val myBoxMainModifier = Modifier
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .fillMaxSize()
        .background(Black)
        .border(
            width = 2.dp, color = Silver, //рамка
            shape = RoundedCornerShape(10.dp)
        )

    //Бокс 1 - для крана с ГХ
    Box(myBoxMainModifier){

        BasicParametersText(
            "ЗДЕСБ БУДЕТ ТАБЛО ИНФОРМАЦИИ"
        )

    }

}

@Preview
@Composable
fun InformationPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        Information()
    }
}