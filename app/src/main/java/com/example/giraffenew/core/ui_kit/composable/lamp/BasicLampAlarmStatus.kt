package com.example.giraffenew.core.ui_kit.composable.lamp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.R

@Composable
fun BasicLampAlarmStatus(
    modifier: Modifier,
    statusLamp: Int,
){
    Box(
        modifier = Modifier
            .size(100.dp),
        contentAlignment = Alignment.Center
    ) {
        val imageResId = when (statusLamp) {
            0 -> R.drawable.ic_home_system_ready
            1 -> R.drawable.ic_home_system_warning
            2 -> R.drawable.ic_home_system_alarm
            else -> R.drawable.ic_home_system_alarm
        }

        Image(imageVector = ImageVector.vectorResource(id=imageResId),
            contentDescription = "status",
            modifier = Modifier
                .size(100.dp)
        )
    }
}

@Preview
@Composable
fun BasicLampAlarmStatusPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        BasicLampAlarmStatus(
            modifier = Modifier,
            statusLamp = 2,
        )
    }
}