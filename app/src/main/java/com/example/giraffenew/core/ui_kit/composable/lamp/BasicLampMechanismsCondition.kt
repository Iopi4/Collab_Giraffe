package com.example.giraffenew.core.ui_kit.composable.lamp

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Silver
import com.example.giraffenew.R


@Composable
fun BasicLampMechanismsCondition(
    isLampStatus: Int,
){
    Box(
        modifier = Modifier
            .size(100.dp),
            contentAlignment = Alignment.Center
    ) {
        val lampColor = when (isLampStatus) {
            0 -> Color.Red
            1 -> Color.Yellow
            2 -> colorResource(R.color.Emerald)
            //2 -> Color.Green
            3 -> Color.Blue
            else -> Color.Gray // default color
        }

        Box(
            modifier = Modifier
                .size(100.dp)
                .border(
                    width = 2.dp, color = Silver, //рамка
                    shape = RoundedCornerShape(5.dp))
                .shadow(1.dp, shape = MaterialTheme.shapes.small)
                .background(lampColor, shape = MaterialTheme.shapes.small)
        )
    }
}

@Preview
@Composable
fun LampMechanismsConditionPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        BasicLampMechanismsCondition(
            isLampStatus = 2,
        )
    }
}