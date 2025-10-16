package com.example.giraffenew.home.ui.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun ButtonIconAndroid(
    onClick: () -> Unit,
    isKioskModeEnabled: Boolean
) {
    val iconColor = if (isKioskModeEnabled) {
        colorResource(R.color.Orange) // Серый - доступ к Android запрещен
    } else {
        colorResource(R.color.Orange) // Зеленый - доступ к Android разрешен
    }

    val description = if (isKioskModeEnabled) {
        "Доступен"
    } else {
        "Не доступен"
    }

    Box(
        modifier = Modifier
            .size(width = 70.dp, height = 20.dp)
            .padding(start = 50.dp, top = 2.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Button(
            onClick = onClick,
            contentPadding = PaddingValues(1.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.Orange),
                contentColor = Color.White
            ),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_topbar_android),
                contentDescription = description,
                modifier = Modifier.size(20.dp),
                tint = iconColor
            )
        }
    }
}



@Preview
@Composable
fun ButtonIconAndroidPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        ButtonIconAndroid(
            onClick = {},
            isKioskModeEnabled = false,
        )
    }
}