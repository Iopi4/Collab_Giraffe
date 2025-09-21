package com.example.giraffenew.initial_setup.presentation

import android.util.Log
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
import com.example.giraffenew.navigate.presentation.TopBarViewModel
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun InitialSetupScreen(
    initialSetupViewModel: InitialSetupViewModel = hiltViewModel(), // Получаем ViewModel для InitialSetupScreenhomeViewModel: Home = hiltViewModel() // Получаем ViewModel для InitialSetupScreen
    topBarViewModel: TopBarViewModel = hiltViewModel(),

    ) {
        BasicSettingBackground()

    // Показываем диалог выбора крана, если нужно
    if (initialSetupViewModel.showCraneDialog.value) {
        CraneSelectionDialog(
            cranes = initialSetupViewModel.cranesList,
            onDismiss = { initialSetupViewModel.closeCraneDialog() },
            onCraneSelected = { initialSetupViewModel.selectCrane(it) }
        )
    }
    // Показываем диалог выбора часового пояса
    if (initialSetupViewModel.showTimeZoneDialog.value) {
        TimeZoneSelectionDialog(
            timeZones = initialSetupViewModel.timeZonesList,
            onDismiss = { initialSetupViewModel.closeTimeZoneDialog() },
            onTimeZoneSelected = { initialSetupViewModel.selectTimeZone(it) }
        )
    }
    

//Создаем поле в котором разделим экраны на горизонтальные области для отображения наших UI
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier = Modifier.weight(2f)){
            ParametersDateAndTime(
                currentTime = topBarViewModel.currentTime.value,
                currentDate = topBarViewModel.currentDate.value,

                selectedTimeZone = initialSetupViewModel.selectedTimeZone.value,
                onSelectTimeZone = {initialSetupViewModel.openTimeZoneDialog()},
                onSetTimeZone = { timeZoneUTC -> initialSetupViewModel.onSetTimeZone(timeZoneUTC)},
                selectedTimeZoneUTC = initialSetupViewModel.selectedTimeZoneUTC.value,

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
                errorMessage = initialSetupViewModel.errorMessage.value,
                logMessageDateTime = initialSetupViewModel.logMessageDateTime.value,
            )
        }
        Box(modifier = Modifier.weight(3f)){
            ParametersCraneAndSBKB(
                selectedCrane  = initialSetupViewModel.selectedCrane.value,
                onSelectCrane  = { initialSetupViewModel.openCraneDialog() },
                onSetModelCrane = { craneID -> initialSetupViewModel.onSetModelCrane(craneID) },
                selectedCraneId = initialSetupViewModel.selectedCraneId.value,

                setValueNumberCrane = initialSetupViewModel.setValueNumberCrane.value,
                onNumberChangeCrane =  { initialSetupViewModel.updateNumberCrane(it) },
                onSetNumberCrane = { initialSetupViewModel.onSetNumberCrane() },

                setValueNumberSBKB = initialSetupViewModel.setValueNumberSBKB.value,
                onNumberChangeSBKB =  { initialSetupViewModel.updateValueNumberSBKB(it) },
                onSetNumberSBKB = { initialSetupViewModel.onSetNumberSBKB() },

                setValueDayCrane = initialSetupViewModel.setValueDayCrane.value,
                setValueMonthCrane = initialSetupViewModel.setValueMonthCrane.value,
                setValueYearCrane = initialSetupViewModel.setValueYearCrane.value,
                onSetDateCrane = { initialSetupViewModel.onSetDateCrane() },
                onDayChangeCrane= { initialSetupViewModel.updateDayCrane(it) },
                onMonthChangeCrane = { initialSetupViewModel.updateMonthCrane(it) },
                onYearChangeCrane = { initialSetupViewModel.updateYearCrane(it) },
                errorMessageCrane = initialSetupViewModel.errorMessageCrane.value,

                setValueDayRTN = initialSetupViewModel.setValueDayRTN.value,
                setValueMonthRTN = initialSetupViewModel.setValueMonthRTN.value,
                setValueYearRTN = initialSetupViewModel.setValueYearRTN.value,
                onSetDateRTN = { initialSetupViewModel.onSetDateRTN() },
                onDayChangeRTN = { initialSetupViewModel.updateDayRTN(it) },
                onMonthChangeRTN = { initialSetupViewModel.updateMonthRTN(it) },
                onYearChangeRTN = { initialSetupViewModel.updateYearRTN(it) },
                errorMessageRTN = initialSetupViewModel.errorMessageRTN.value,

                setValueDaySBKB = initialSetupViewModel.setValueDaySBKB.value,
                setValueMonthSBKB = initialSetupViewModel.setValueMonthSBKB.value,
                setValueYearSBKB = initialSetupViewModel.setValueYearSBKB.value,
                onSetDateSBKB = { initialSetupViewModel.onSetDateSBKB() },
                onDayChangeSBKB = { initialSetupViewModel.updateDaySBKB(it) },
                onMonthChangeSBKB = { initialSetupViewModel.updateMonthSBKB(it) },
                onYearChangeSBKB = { initialSetupViewModel.updateYearSBKB(it) },
                errorMessageSBKB = initialSetupViewModel.errorMessageSBKB.value,

                logMessageCrane = initialSetupViewModel.logMessageCrane.value,
                logMessageRTN = initialSetupViewModel.logMessageRTN.value,
                logMessageSBKB = initialSetupViewModel.logMessageSBKB.value,
            ) }
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