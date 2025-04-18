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
import com.example.giraffenew.core.ui_kit.composable.BasicParametersText
import com.example.giraffenew.core.ui_kit.composable.HomeVoltageValueText
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun VoltageParameterMeaning(
    modifier: Modifier= Modifier,
    title: String,
    valueL1L2: String,
    valueL2L3: String,
    valueL1L3: String,
){

    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
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
            .padding(start = 5.dp, end = 5.dp,)
            .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly) {
            HomeVoltageValueText(valueL1L2,myParametersModifier)
            HomeVoltageValueText(valueL2L3,myParametersModifier)
            HomeVoltageValueText(valueL1L3,myParametersModifier)
        }
    }

}

@Preview
@Composable
fun VoltageParameterMeaningPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        VoltageParameterMeaning(
            modifier = Modifier,
            title = "ПИТАНИЕ",
            valueL1L2 = "380.00",
            valueL2L3 = "380.00",
            valueL1L3 = "380.00",
        )
    }
}