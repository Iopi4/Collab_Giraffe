package com.example.giraffenew.coordinate_protection.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.BasicFilledButtonGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicNameSettingParameterGiraffe
import com.example.giraffenew.core.ui_kit.composable.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Orange

@Composable
fun CoordinateProtectionSetting(

) {
    //Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {

        //№.1 - Левая область для отображения параметров настройки датчика высоты подъема)
        Column(
            modifier = Modifier
                .fillMaxSize().weight(3f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Orange,
                    shape = RoundedCornerShape(5.dp)
                ),
            verticalArrangement = Arrangement.Top
        ) {
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Activate_Protection)
            BasicNameSettingParameterGiraffe(settingParameterId = R.string.CoordinateProtection_Text_Protection_Mode)
        }



        //№.2 - Правая область для отображения и ввода данных датчика высоты подъема
        Column(
            modifier = Modifier
                .fillMaxSize().weight(2f).padding(5.dp).fillMaxSize().border(
                    width = 3.dp,
                    color = Burgundy_violet,
                    shape = RoundedCornerShape(5.dp)),
            verticalArrangement = Arrangement.Top
        ) {

            Row(horizontalArrangement = Arrangement.Start) {
                //Поле состояния и активации коррдинатной защиты
                Box(modifier = Modifier.weight(5f)
                    .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "Выкл."
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(4f)) {
                    BasicFilledButtonGiraffe(
                        modifier = Modifier,
                        text = "Выбор",
                        onClick = {},
                    ) {}
                }
            }

            Row(horizontalArrangement = Arrangement.Start) {
                //Поле состояния и активации коррдинатной защиты
                Box(modifier = Modifier.weight(5f)
                    .padding(start = 15.dp, end = 0.dp, top = 5.dp, bottom = 0.dp)) {
                    BasicValuesSettingsParameterGiraffe(
                        modifier = Modifier,
                        valueParameterId = "Стрела."
                    )
                }
                //Кнопка ввода
                Box(modifier = Modifier.weight(4f)) {
                    BasicFilledButtonGiraffe(
                        modifier = Modifier,
                        text = "Выбор",
                        onClick = {},
                    ) {}
                }
            }



        }

    }

}



@Preview
@Composable
fun CoordinateProtectionSettingPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        CoordinateProtectionSetting(
        )
    }
}