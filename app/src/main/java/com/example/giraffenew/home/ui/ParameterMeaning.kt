package com.example.giraffenew.home.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange
import com.example.giraffenew.core.ui_kit.composable.BasicParametersText
import com.example.giraffenew.core.ui_kit.composable.BasicParametersValueText

@Composable
fun ParameterMeaning(
    modifier: Modifier= Modifier,
    title: String,
    valueFirst: String,
    valueSecond: String,
){

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .border(
            width = 2.dp,
            color = Orange,
            shape = RoundedCornerShape(10.dp)
        ),
        verticalArrangement = Arrangement.SpaceEvenly) {

        val myParametersModifier = Modifier
            .fillMaxWidth()

        BasicParametersText(title, modifier = Modifier.padding(top = 5.dp) .fillMaxWidth())

        Column(modifier = Modifier
            .padding(top = 5.dp, start = 5.dp, end = 5.dp,)
            .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly) {
            BasicParametersValueText(valueFirst,myParametersModifier)
            BasicParametersValueText(valueSecond, myParametersModifier) }
    }

}




@Preview
@Composable
fun ParameterMeaningPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        ParameterMeaning(
            modifier = Modifier,
            title = "ГРУЗ [T]",
            valueFirst = "10",
            valueSecond = "8,8",
        )
    }
}