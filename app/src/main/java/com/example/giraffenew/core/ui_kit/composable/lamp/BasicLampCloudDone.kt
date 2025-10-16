package com.example.giraffenew.core.ui_kit.composable.lamp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun BasicLampCloudDone(
    isStatusCloud: Boolean
){
    val iconColor = if (isStatusCloud) {
        colorResource(R.color.Silver) //  - Данные передаются в облачный сервис
    } else {
        colorResource(R.color.Red) // Серый данные не передаются на облачный сервер
    }

    val backgroundColor = if (isStatusCloud) {
        colorResource(R.color.Orange) // Зеленый - Данные передаются в облачный сервис
    } else {
        colorResource(R.color.black) // Белый данные не передаются на облачный сервер
    }

    val iconItem = if (isStatusCloud) {
        painterResource(R.drawable.ic_topbar_cloud_done) // Зеленый - Данные передаются в облачный сервис
    } else {
        painterResource(R.drawable.ic_topbar_cloud_off) // Серый данные не передаются на облачный сервер
    }

    val description = if (isStatusCloud) {
        "Подключен"
    } else {
        "Не подключен"
    }

    Box(
        modifier = Modifier
            .size(width = 20.dp, height = 20.dp)
            .padding(top = 2.dp),
        contentAlignment = Alignment.TopStart
    ) {
        Icon(
            painter = iconItem,
            contentDescription = description,
            modifier = Modifier.size(40.dp).background(backgroundColor),
            tint = iconColor
        )
    }
}

@Preview
@Composable
fun BasicLampCloudDonePreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        BasicLampCloudDone(
            isStatusCloud = true,
        )
    }
}