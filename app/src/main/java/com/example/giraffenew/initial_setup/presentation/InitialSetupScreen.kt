package com.example.giraffenew.initial_setup.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.initial_setup.ui.ParametersCraneAndSBKB
import com.example.giraffenew.initial_setup.ui.ParametersDateAndTime
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun InitialSetupScreen(
    initialSetupViewModel: InitialSetupViewModel = hiltViewModel() // Получаем ViewModel для InitialSetupScreen
    ) {
    BasicSettingBackground()

//Создаем поле в котором разделим экраны на горизонтальные области для отображения наших UI
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier = Modifier.weight(1f)){
            ParametersDateAndTime(
                currentTime = initialSetupViewModel.currentTime.value,
                currentDate = initialSetupViewModel.currentDate.value,
                setValueHour = initialSetupViewModel.setValueHour.value,
                setValueMinute = initialSetupViewModel.setValueMinute.value,
                setValueDay = initialSetupViewModel.setValueDay.value,
                setValueMonth = initialSetupViewModel.setValueMonth.value,
                setValueYear = initialSetupViewModel.setValueYear.value,
                onHourChange = { initialSetupViewModel.updateHour(it) },
                onMinuteChange = { initialSetupViewModel.updateMinute(it) },
                onDayChange = { initialSetupViewModel.updateDay(it) },
                onMonthChange = { initialSetupViewModel.updateMonth(it) },
                onYearChange = { initialSetupViewModel.updateYear(it) },
                onSetDateTime = { initialSetupViewModel.onSetDateTime() },
                errorMessage = initialSetupViewModel.errorMessage.value
            )
        }
//        Box(modifier = Modifier.weight(1f)){
//            ParametersCraneAndSBKB() }
    }
}

@Preview
@Composable
fun InitialSetupScreenPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        InitialSetupScreen(
        )
    }
}