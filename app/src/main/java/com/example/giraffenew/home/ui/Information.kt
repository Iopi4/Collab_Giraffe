package com.example.giraffenew.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.home.ui.lamp.LampIconInformationAlarm
import com.example.giraffenew.ui.theme.Black
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Silver

@Composable
fun Information (
    beeper: Int,
){
    val myBoxMainModifier = Modifier
        .size(width = 324.dp, height = 106.dp)
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .fillMaxSize()
        .background(Black)
        .border(
            width = 2.dp, color = Silver, //рамка
            shape = RoundedCornerShape(10.dp)
        )

    //Иконки аварий и предупреждений
    Box(myBoxMainModifier){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )


            }

            Row (
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )
                LampIconInformationAlarm(
                    isLampIconStatus = beeper,
                    isIcon = painterResource(R.drawable.ic_home_info_campaign),
                )

            }

        }

    }

}

@Preview
@Composable
fun InformationPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        Information(
            beeper = 1,
        )
    }
}