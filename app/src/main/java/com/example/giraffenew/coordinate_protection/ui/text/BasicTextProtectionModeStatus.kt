package com.example.giraffenew.coordinate_protection.ui.text

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.R



@Composable
fun BasicTextProtectionModeStatus(
    modifier: Modifier,
    valueParameterId: Int,
){
    val textStatus: String = when (valueParameterId) {
        0 -> stringResource(R.string.CoordinateProtection_Text_Protection_Mode_Cargo)
        1 -> stringResource(R.string.CoordinateProtection_Text_Protection_Mode_Boom)
        else -> stringResource(R.string.CoordinateProtection_Text_Protection_Mode_Cargo)
    }

    val myBoxVisualValuesModifier = Modifier
        .clip(shape = RoundedCornerShape(5.dp))
        .background(colorResource(R.color.Orange_50_percent))
        .fillMaxWidth()
        .border(
            width = 2.dp,
            color = colorResource(R.color.Lavanda),
            shape = RoundedCornerShape(5.dp)
        )

    Box (myBoxVisualValuesModifier) {
        Text(modifier = modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp, top = 5.dp,bottom = 5.dp),
            text = textStatus,
            fontSize = 19.sp, fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center, color = colorResource(R.color.DarkGrew),
        )
    }




}

@Preview
@Composable
fun BasicTextProtectionModeStatusPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        BasicTextProtectionModeStatus(
            valueParameterId = 1,
            modifier = Modifier
        )
    }
}