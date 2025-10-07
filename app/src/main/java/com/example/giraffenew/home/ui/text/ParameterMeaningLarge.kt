package com.example.giraffenew.home.ui.text

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange
import com.example.giraffenew.core.ui_kit.composable.text.BasicParametersText
import com.example.giraffenew.core.ui_kit.composable.text.BasicParametersValueText
import com.example.giraffenew.core.ui_kit.composable.text.BasicParametersValueTextLarge
import com.example.giraffenew.ui.theme.white

@Composable
fun ParameterMeaningLarge(
    modifier: Modifier= Modifier,
    title: String,
    value: String,
){

    Column(modifier = modifier
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
            BasicParametersValueTextLarge(value,myParametersModifier)
        }
    }

}




@Preview
@Composable
fun ParameterMeaningLargePreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        ParameterMeaningLarge(
            modifier = Modifier,
            title = "ГРУЗ [T]",
            value = "10.0",
        )
    }
}