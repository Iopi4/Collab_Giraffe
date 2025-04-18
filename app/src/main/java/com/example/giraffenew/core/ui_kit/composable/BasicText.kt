package com.example.giraffenew.core.ui_kit.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.white

//Экран Home
@Composable
//Создадим функцию для свойств текста в названии механизмов крана (Область с параметрами работы крана)
fun BasicParametersText (text:String, modifier:Modifier=Modifier){
    Text(text, modifier, fontSize = 16.sp, textAlign = TextAlign.Center, color = white) }
@Composable
//Создадим функцию для свойств текста для текущих значений высоты, выллета и т.д. (Область с параметрами работы крана)
fun BasicParametersValueText (text:String, modifier:Modifier=Modifier){
    Text(text, modifier, fontSize = 30.sp, textAlign = TextAlign.Center, color = white) }
@Composable
//Создадим функцию для свойств текста ПИТАНИЕ, В (Область с параметрами работы крана)
fun HomeVoltageValueText (text:String, modifier:Modifier=Modifier){
    Text(text, modifier, fontSize = 20.sp, textAlign = TextAlign.Center, color = white) }

//Функция отображения текста в левой области экрана для меню настроек
@Composable
fun BasicSettingsText (text:String, textModifier:Modifier=Modifier.padding(5.dp).fillMaxWidth()){
    Text(text, textModifier, fontSize = 20.sp, fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Start, color = DarkGrew) }

@Composable
fun BasicSettingsValues (text:String, textModifier:Modifier=Modifier.padding(5.dp).fillMaxWidth()){
    Text(text, textModifier, fontSize = 20.sp, fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center, color = DarkGrew) }