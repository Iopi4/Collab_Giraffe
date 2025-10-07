package com.example.giraffenew.core.ui_kit.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R

@Composable
fun BasicSettingBackground (){
    Box(
        modifier = Modifier
            .padding()
            .fillMaxSize()
            .background(colorResource(R.color.Silver)),
        contentAlignment = Alignment.TopEnd
    ) {
        Image(
            bitmap = ImageBitmap.imageResource(R.drawable.background_setting),
            contentDescription = "Фон меню настроек",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.size(width = 215.dp, height = 1200.dp)
                .padding(horizontal =8.dp, vertical = 5.dp)
        )
    }
}