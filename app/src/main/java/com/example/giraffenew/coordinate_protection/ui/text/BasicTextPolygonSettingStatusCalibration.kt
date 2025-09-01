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
fun BasicTextPolygonSettingStatusCalibration(
    modifier: Modifier,
    isVisibility: Int,
){
    val myBoxVisualValuesModifier = Modifier
        .alpha(if (isVisibility == 1) 1f else 0f)
        .clip(shape = RoundedCornerShape(5.dp))
        .background(colorResource(R.color.Emerald))
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
            text = stringResource(R.string.CoordinateProtection_Text_Polygon_Status_Activate),
            fontSize = 19.sp, fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center, color = colorResource(R.color.DarkGrew)
        )
    }




}

@Preview
@Composable
fun BasicTextPolygonSettingStatusActivatePreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        BasicTextPolygonSettingStatusCalibration(
            modifier = Modifier,
            isVisibility = 1,
        )
    }
}