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
fun BasicLampEthernetDone(
    isStatusEthernet: Boolean
){
    val iconColor = if (isStatusEthernet) {
        colorResource(R.color.Orange) //Оранжевый Ethernet соединение подключено
    } else {
        colorResource(R.color.Red) //Красный Ethernet соединение потеряно
    }

    val backgroundColor = if (isStatusEthernet) {
        colorResource(R.color.Orange) //
    } else {
        colorResource(R.color.black) //
    }

    val description = if (isStatusEthernet) {
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
            painter = painterResource(R.drawable.ic_topbar_ethernet_off),
            contentDescription = description,
            modifier = Modifier.size(40.dp).background(backgroundColor),
            tint = iconColor
        )
    }
}

@Preview
@Composable
fun BasicLampEthernetDonePreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        BasicLampEthernetDone(
            isStatusEthernet = false,
        )
    }
}