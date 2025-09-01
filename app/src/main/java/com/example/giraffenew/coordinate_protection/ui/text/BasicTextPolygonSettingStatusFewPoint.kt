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
import androidx.compose.ui.draw.alpha
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
fun BasicTextPolygonSettingStatusFewPoint(
    modifier: Modifier,
    valueParameterId: Int,
    isVisibility: Int,
){
    val textStatus: String = when (valueParameterId) {
        0 -> stringResource(R.string.CoordinateProtection_Text_Polygon_Status_Settings_Not_Completed)
        1 -> stringResource(R.string.CoordinateProtection_Text_Polygon_Status_Few_Points)
        else -> stringResource(R.string.CoordinateProtection_Text_Polygon_Status_Settings_Not_Completed)
    }

    val backgroundColor = when (valueParameterId) {
        0 -> Color.Red.copy(alpha = 0.8f)
        1 -> Color.Yellow.copy(alpha = 0.8f)
        else -> Color.Red.copy(alpha = 0.8f) // default color
    }

    val textColor = when (valueParameterId) {
        0 -> Color.White
        1 -> colorResource(R.color.DarkGrew)
        else -> Color.White // default color
    }

    val myBoxVisualValuesModifier = Modifier
        .alpha(if (isVisibility == 1) 0f else 1f)
        .clip(shape = RoundedCornerShape(5.dp))
        .background(backgroundColor)
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
            textAlign = TextAlign.Center, color = textColor
        )
    }




}

@Preview
@Composable
fun BasicTextPolygonSettingStatusFewPointPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        BasicTextPolygonSettingStatusFewPoint(
            valueParameterId = 1,
            isVisibility = 0,
            modifier = Modifier
        )
    }
}