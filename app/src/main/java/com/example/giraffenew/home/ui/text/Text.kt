package com.example.giraffenew.home.ui.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.giraffenew.ui.theme.white

@Composable
//Создадим функцию для свойств текста в названии механизмов крана (Область с параметрами работы крана)
fun ParametersTextControlEffort (text:String, modifier:Modifier=Modifier){
    Text(text, modifier, fontSize = 18.sp, textAlign = TextAlign.Center, color = white) }