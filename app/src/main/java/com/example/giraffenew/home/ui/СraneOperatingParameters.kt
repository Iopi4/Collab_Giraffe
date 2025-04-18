package com.example.giraffenew.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.giraffenew.R

@Composable
fun СraneOperatingParameters(
    modifier: Modifier= Modifier,
){
    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly) {

        val myBoxParametersModifier = Modifier
            .weight(1f)

        Box(myBoxParametersModifier) { ParameterMeaning(
            modifier = Modifier,
            title = stringResource(R.string.Home_Weight),
            valueFirst = "10 /",
            valueSecond = "5.8"
        ) }

        Box (myBoxParametersModifier) { ParameterMeaning(
            modifier = Modifier,
            title = stringResource(R.string.Home_Trolley),
            valueFirst = "24.2",
            valueSecond = ""
        ) }

        Box (myBoxParametersModifier) { ParameterMeaning(
            modifier = Modifier,
            title = stringResource(R.string.Home_Slewing),
            valueFirst = "133.2",
            valueSecond = ""
        ) }

        Box (myBoxParametersModifier) { ParameterMeaning(
            modifier = Modifier,
            title = stringResource(R.string.Home_Height),
            valueFirst = "45.2",
            valueSecond = ""
        ) }

        Box (myBoxParametersModifier) { ParameterMeaning(
            modifier = Modifier,
            title = stringResource(R.string.Home_MOVE_CRANE),
            valueFirst = "12.8",
            valueSecond = ""
        ) }

        Box (myBoxParametersModifier) { ParameterMeaning(
            modifier = Modifier,
            title = stringResource(R.string.Home_Wind),
            valueFirst = "5.2",
            valueSecond = ""
        ) }

        Box (myBoxParametersModifier) { VoltageParameterMeaning(
            modifier = Modifier,
            title = stringResource(R.string.Home_Voltage),
            valueL1L2 = "380.00 V",
            valueL2L3 = "380.00 V",
            valueL1L3 = "380.00 V"
        ) }
    }

}