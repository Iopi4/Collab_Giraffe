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
    weightCurrent: Float,
    weightQnom: Float,
    trolleyCurrent: Float,
    slewingCurrent: Float,
    heightCurrent: Float,
    moveCurrent: Float,
    windCurrent: Float,
    voltageL1L2Current: Float,
    voltageL2L3Current: Float,
    voltageL1L3Current: Float,
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
            valueFirst = "${formatDecimal(weightQnom)}/",
            valueSecond = "${formatDecimal(weightCurrent)} "
        ) }

        Box (myBoxParametersModifier) { ParameterMeaning(
            modifier = Modifier,
            title = stringResource(R.string.Home_Trolley),
            valueFirst = formatDecimal(trolleyCurrent),
            valueSecond = ""
        ) }

        Box (myBoxParametersModifier) { ParameterMeaning(
            modifier = Modifier,
            title = stringResource(R.string.Home_Slewing),
            valueFirst = formatDecimal(slewingCurrent),
            valueSecond = ""
        ) }

        Box (myBoxParametersModifier) { ParameterMeaning(
            modifier = Modifier,
            title = stringResource(R.string.Home_Height),
            valueFirst = formatDecimalMinus(heightCurrent),
            valueSecond = ""
        ) }

        Box (myBoxParametersModifier) { ParameterMeaning(
            modifier = Modifier,
            title = stringResource(R.string.Home_MOVE_CRANE),
            valueFirst = formatDecimal(moveCurrent),
            valueSecond = ""
        ) }

        Box (myBoxParametersModifier) { ParameterMeaning(
            modifier = Modifier,
            title = stringResource(R.string.Home_Wind),
            valueFirst = formatDecimal(windCurrent),
            valueSecond = ""
        ) }

        Box (myBoxParametersModifier) { VoltageParameterMeaning(
            modifier = Modifier,
            title = stringResource(R.string.Home_Voltage),
            valueL1L2 = formatVoltage(voltageL1L2Current),
            valueL2L3 = formatVoltage(voltageL2L3Current),
            valueL1L3 = formatVoltage(voltageL1L3Current)
        ) }
    }
}

private val VIRTUAL_ZERO: Float = 32250f //Константа для виртуального нуля. Необходимо для корретной высоты подъема
private fun formatDecimal(value: Float): String {//return String.format("%.1f", value).padStart(5, '0')
    return String.format("%05.1f", value).replace(',', '.')
}

private fun formatDecimalMinus(value: Float): String {
    val realValue = (value * 10 - VIRTUAL_ZERO) / 10f
    return if (realValue < 0) {
        String.format("%05.1f", realValue).replace(',', '.')
    } else {
        String.format("%04.1f", realValue).replace(',', '.')
    }
}

private fun formatVoltage(value: Float): String {
    return String.format("%05.1f V", value)
}