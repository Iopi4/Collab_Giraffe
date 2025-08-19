package com.example.giraffenew.initial_setup.presentation

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.initial_setup.ui.ParametersCraneAndSBKB
import com.example.giraffenew.initial_setup.ui.ParametersDateAndTime
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun InitialSetupScreen(
    initialSetupViewModel: InitialSetupViewModel = hiltViewModel() // Получаем ViewModel для InitialSetupScreen
    ) {
    BasicSettingBackground()
    // 1. Получаем текущий LifecycleOwner
    val lifecycleOwner = LocalLifecycleOwner.current
    // 2. Создаем Lifecycle-aware эффект
    DisposableEffect(lifecycleOwner, initialSetupViewModel) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    Log.d("HomeScreen", "Lifecycle: ON_START - resuming data collection")
                    initialSetupViewModel.startDataCollection()
                }
                Lifecycle.Event.ON_STOP -> {
                    Log.d("HomeScreen", "Lifecycle: ON_STOP - pausing data collection")
                    initialSetupViewModel.pauseDataCollection()
                }
                else -> {}
            }
        }

        // 3. Регистрируем наблюдатель
        lifecycleOwner.lifecycle.addObserver(observer)

        // 4. Очистка при удалении Composable
        onDispose {
            Log.d("InitialSetupScreen", "Lifecycle: ON_DISPOSE - removing observer")
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }


//Создаем поле в котором разделим экраны на горизонтальные области для отображения наших UI
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier = Modifier.weight(2f)){
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
                errorMessage = initialSetupViewModel.errorMessage.value,
            )
        }
        Box(modifier = Modifier.weight(3f)){
            ParametersCraneAndSBKB(
                setValueModelCrane = initialSetupViewModel.setValueModelCrane.value,
                onModelChangeCrane = { initialSetupViewModel.updateValueModelCrane(it) },
                onSetModelCrane = { initialSetupViewModel.onSetModelCrane() },

                setValueNumberCrane = initialSetupViewModel.setValueNumberCrane.value,
                onNumberChangeCrane =  { initialSetupViewModel.updateValueNumberCrane(it) },
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