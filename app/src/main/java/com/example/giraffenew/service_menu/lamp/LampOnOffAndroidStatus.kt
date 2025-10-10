package com.example.giraffenew.service_menu.lamp

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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Silver

@Composable
fun LampOnOffAndroidStatus(
    isLampStatus: Boolean,
) {
    Box(modifier = Modifier
        .padding(start = 0.dp, end = 10.dp, top = 5.dp, bottom = 0.dp),
        contentAlignment = Alignment.Center)
    {
        val lampColor = if (isLampStatus) {
            colorResource(R.color.Grey) // Серый - доступ к Android запрещен
        } else {
            colorResource(R.color.Emerald) // Зеленый - доступ к Android разрешен
        }


        Box(
            modifier = Modifier
                .clip(shape = RoundedCornerShape(5.dp))
                .size(width = 50.dp, height = 30.dp)
                .border(
                    width = 2.dp,
                    color = Silver,
                    shape = RoundedCornerShape(5.dp)
                )
                .shadow(1.dp, shape = MaterialTheme.shapes.small)
                .background(lampColor, shape = MaterialTheme.shapes.small)
        )
    }
}

@Preview
@Composable
fun LampOnOffAndroidStarusPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        LampOnOffAndroidStatus(
            isLampStatus = false
        )
    }
}