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
import com.example.giraffenew.home.presentation.HomeScreenViewModel
import com.example.giraffenew.home.ui.text.ParameterMeaning
import com.example.giraffenew.home.ui.text.ParameterMeaningLarge
import com.example.giraffenew.home.ui.text.VoltageParameterMeaning

@Composable
fun CraneOperatingParameters(
    operatingParams: HomeScreenViewModel.CraneOperatingParametersState,
){
    val weightQnom = operatingParams.weightQnom
    val weightCurrent = operatingParams.weightCurrent
    val trolleyCurrent = operatingParams.trolleyCurrent
    val slewingCurrent = operatingParams.slewingCurrent
    val heightCurrent = operatingParams.heightCurrent
    val moveCurrent = operatingParams.moveCurrent
    val windCurrent = operatingParams.windCurrent
    val voltageL1L2Current = operatingParams.voltageL1L2Current
    val voltageL2L3Current = operatingParams.voltageL2L3Current
    val voltageL1L3Current = operatingParams.voltageL1L3Current

    Column(modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly) {

        val myBoxParametersModifier = Modifier
            .weight(1f)

        Box(myBoxParametersModifier) {
            ParameterMeaningLarge(
                modifier = Modifier,
                title = stringResource(R.string.Home_Weight),
                value = formatDecimal(weightCurrent),
            )
        }

        Box (myBoxParametersModifier) {
            ParameterMeaningLarge(
                modifier = Modifier,
                title = stringResource(R.string.Home_Height),
                value = formatDecimalMinus(heightCurrent),
            )
        }

        Box (myBoxParametersModifier) {
            ParameterMeaningLarge(
                modifier = Modifier,
                title = stringResource(R.string.Home_Slewing),
                value = formatDecimal(slewingCurrent),
            )
        }

        Box (myBoxParametersModifier) {
            ParameterMeaningLarge(
                modifier = Modifier,
                title = stringResource(R.string.Home_Trolley),
                value = formatDecimal(trolleyCurrent),
            )
        }


        Box (myBoxParametersModifier) {
            ParameterMeaningLarge(
                modifier = Modifier,
                title = stringResource(R.string.Home_MOVE_CRANE),
                value = formatDecimal(moveCurrent),
            )
        }

        Box (myBoxParametersModifier) {
            ParameterMeaningLarge(
                modifier = Modifier,
                title = stringResource(R.string.Home_Wind),
                value = formatDecimal(windCurrent),
            )
        }

        Box (myBoxParametersModifier) {
            VoltageParameterMeaning(
                modifier = Modifier,
                title = stringResource(R.string.Home_Voltage),
                valueL1L2 = formatVoltage(voltageL1L2Current),
                valueL2L3 = formatVoltage(voltageL2L3Current),
                valueL1L3 = formatVoltage(voltageL1L3Current)
            )
        }
    }
}

private val VIRTUAL_ZERO: Float = 32250f //Константа для виртуального нуля. Необходимо для корретной высоты подъема
private fun formatDecimal(value: Float): String {//return String.format("%.1f", value).padStart(5, '0')
    return String.format("%01.1f", value).replace(',', '.')
}

private fun formatDecimalMinus(value: Float): String {
    val realValue = (value * 10 - VIRTUAL_ZERO) / 10f
    return if (realValue < 0) {
        String.format("%01.1f", realValue).replace(',', '.')
    } else {
        String.format("%01.1f", realValue).replace(',', '.')
    }
}

private fun formatVoltage(value: Float): String {
    return String.format("%01.1f V", value)
}