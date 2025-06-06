package com.example.giraffenew.setting_sensors.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.setting_sensors.ui.CalibrationSensorsContent

@Composable
fun SettingSensorsScreen (){

    CalibrationSensorsContent()

    //Создадим кнопки навигации для меню настройки датчиков положения механизмов
    // данные для отображения названия страницы


}

@Preview
@Composable
fun SettingSensorsScreenPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        SettingSensorsScreen(
        )
    }
}