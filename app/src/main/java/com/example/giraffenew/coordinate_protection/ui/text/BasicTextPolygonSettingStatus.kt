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
fun BasicTextPolygonSettingStatus(
    modifier: Modifier,
    valueParameterId: Int,
){
    val textStatus: String = when (valueParameterId) {
        0 -> stringResource(R.string.CoordinateProtection_Text_Polygon_Status_Settings_Not_Completed)
        64 -> stringResource(R.string.CoordinateProtection_Text_Polygon_Status_Few_Points)
        256 -> stringResource(R.string.CoordinateProtection_Text_Polygon_Status_Activate)
        512 -> stringResource(R.string.CoordinateProtection_Text_Polygon_Status_Activate)
        else -> stringResource(R.string.CoordinateProtection_Text_Polygon_Status_Not_Select)
    }

    val backgroundColor = when (valueParameterId) {
        0 -> colorResource(R.color.Orange_50_percent)
        32 -> Color.Red.copy(alpha = 0.8f)
        64 -> Color.Yellow.copy(alpha = 0.8f)
        256 -> colorResource(R.color.Emerald)
        512 -> colorResource(R.color.Emerald)
        else -> colorResource(R.color.Orange_50_percent) // default color
    }

    val textColor = when (valueParameterId) {
        0 -> colorResource(R.color.DarkGrew)
        32 -> Color.White
        64 -> colorResource(R.color.DarkGrew)
        256 -> colorResource(R.color.DarkGrew)
        512 -> colorResource(R.color.DarkGrew)
        else -> colorResource(R.color.DarkGrew) // default color
    }

    val myBoxVisualValuesModifier = Modifier
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
fun BasicTextPolygonSettingStatusPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        BasicTextPolygonSettingStatus(
            valueParameterId = 512,
            modifier = Modifier
        )
    }
}