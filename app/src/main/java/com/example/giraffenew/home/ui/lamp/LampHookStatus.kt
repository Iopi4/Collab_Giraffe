package com.example.giraffenew.home.ui.lamp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Grew
import com.example.giraffenew.ui.theme.Grew_50_percent
import com.example.giraffenew.ui.theme.Silver

@Composable
fun LampHookStatus(
    isLampStatus: Int,
){
    Box(modifier = Modifier,
        contentAlignment = Alignment.Center)
    {
        val lampColor = when (isLampStatus) {
            1 -> colorResource(R.color.Lemon)
            0 -> colorResource(R.color.black)
            else -> colorResource(R.color.black) // default color
        }

        val borderColor = when (isLampStatus) {
            1 -> colorResource(R.color.Red)
            0 -> colorResource(R.color.black)
            else -> colorResource(R.color.black) // default color
        }

        Box(
            modifier = Modifier
                .size(80.dp)
                .border(
                    width = 2.dp, color = borderColor, //рамка
                    shape = RoundedCornerShape(5.dp))
                .shadow(1.dp, shape = MaterialTheme.shapes.small)
                .background(lampColor, shape = MaterialTheme.shapes.small)
        )
    }
}

@Preview
@Composable
fun LampHookStatusPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        LampHookStatus(
            isLampStatus = 1,
        )
    }
}