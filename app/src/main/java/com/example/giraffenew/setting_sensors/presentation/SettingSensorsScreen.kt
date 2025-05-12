package com.example.giraffenew.setting_sensors.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.setting_sensors.ui.CalibrationSensorHoist
import com.example.giraffenew.setting_sensors.ui.CalibrationSensorTrolley
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import kotlinx.coroutines.launch
import com.example.giraffenew.R
import com.example.giraffenew.setting_load_capacity.ui.ParameterWeightSensor
import com.example.giraffenew.setting_load_capacity.ui.ParametersCargoCharacteristics
import com.example.giraffenew.setting_load_capacity.ui.ParametersLoadCapacity
import com.example.giraffenew.setting_sensors.ui.CalibrationSensorCrane
import com.example.giraffenew.setting_sensors.ui.CalibrationSensorSlewing
import com.example.giraffenew.setting_sensors.ui.CalibrationSensorsContent
import com.example.giraffenew.setting_sensors.ui.CalibrationSensorsPageOne
import com.example.giraffenew.setting_sensors.ui.CalibrationSensorsPageTwo

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