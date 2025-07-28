package com.example.giraffenew.core.ui_kit.composable.lamp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Silver
import com.example.giraffenew.R
import com.example.giraffenew.ui.theme.Grew_50_percent


@Composable
fun BasicLampSettingSensorDwn(
    isLampStatus: Int,
){
    Box(modifier = Modifier
        .padding(start = 7.dp, end = 15.dp, top = 5.dp, bottom = 0.dp),
        contentAlignment = Alignment.Center)
    {
        val lampColor = when (isLampStatus) {
            0 -> Color.Yellow
            1 -> Color.Yellow
            2 -> Color.Red
            else -> colorResource(R.color.Emerald) // default color
        }

        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .size(width = 50.dp, height = 30.dp)
                .border(
                    width = 2.dp,
                    color = Grew_50_percent,
                    shape = RoundedCornerShape(5.dp)
                )
                .shadow(1.dp, shape = MaterialTheme.shapes.small)
                .background(lampColor, shape = MaterialTheme.shapes.small)
        )
    }
}

@Preview
@Composable
fun BasicLampSettingSensorDwnPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        BasicLampSettingSensorDwn(
            isLampStatus = 0,
        )
    }
}