package com.example.giraffenew.initial_setup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.core.ui_kit.composable.BasicSettingsText
import com.example.giraffenew.core.ui_kit.composable.BasicSettingsValues
import com.example.giraffenew.core.ui_kit.composable.BasicTextFilledGiraffe
import com.example.giraffenew.ui.theme.Burgundy_violet
import com.example.giraffenew.ui.theme.DarkLemon
import com.example.giraffenew.ui.theme.Orange

@Composable
fun SettingsScreen(
    ) {
    BasicSettingBackground()

//Создаем поле в котором разделим экраны на две вертикальные области №1, №2.
    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        //Переменная для рамок окон с текстовым описанием. Правая сторона экрана
        val myBoxSettingModifierOR = Modifier
            .padding(5.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .weight(1f)
            .border(
                width = 3.dp,
                color = Orange,
                shape = RoundedCornerShape(5.dp)
            )

        //Переменная для рамок окон с данными (ввод-вывод). Левая сторона экрана
        val myBoxSettingModifierBV = Modifier
            .padding(5.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .weight(1f)
            .border(
                width = 3.dp,
                color = Burgundy_violet,
                shape = RoundedCornerShape(5.dp)
            )
        val myBoxTextModifier = Modifier
            .padding(start = 7.dp, end = 7.dp, top = 7.dp, bottom = 0.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = DarkLemon,
                shape = RoundedCornerShape(5.dp)
            )

        val myBoxVisualValuesModifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 0.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .background(colorResource(R.color.Orange_50_percent))
            .fillMaxWidth()
            .border(
                width = 2.dp,
                color = colorResource(R.color.Lavanda),
                shape = RoundedCornerShape(5.dp)
            )

        val myBoxSetValuesModifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 7.dp, bottom = 0.dp)
            .clip(shape = RoundedCornerShape(5.dp))
            .background(colorResource(R.color.white))
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = (colorResource(R.color.white)),
                shape = RoundedCornerShape(5.dp)
            )


//№.1 - Левая область для отображения двух основных боксов. (1 Бокс - Дата и время , 2 Бокс - Данные крана)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(3f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            //Бокс 1.1 для отображения текста параметров даты и времени
            Box(myBoxSettingModifierOR) {
                Column(modifier = Modifier
                    .fillMaxSize(),
                    verticalArrangement = Arrangement.Top) {
                    Box (myBoxTextModifier) {
                        BasicSettingsText("Текущее время [Час:Мин]") }
                    Box (myBoxTextModifier) {
                        BasicSettingsText("Текущая дата [День.Мес.Год]") }
                    Box (myBoxTextModifier) {
                        BasicSettingsText("Ввод. [ЧЧ] [ММ]") }
                    Box (myBoxTextModifier) {
                        BasicSettingsText("Ввод. [ДД] [ММ] [ГГГГ]") }
                }
            }

            //Бокс 1.2 для отображения текста параметров крана
            Box(myBoxSettingModifierOR) {
                Column(modifier = Modifier
                    .fillMaxSize(),
                    verticalArrangement = Arrangement.Top) {

                    Box (myBoxTextModifier) {
                        BasicSettingsText("Ввод. Модель крана") }

                    Box (myBoxTextModifier) {
                        BasicSettingsText("Ввод. Заводской номер крана")}

                    //Ввод. Дата регистр. в Ростехнадзоре
                    Box (myBoxTextModifier) {
                        BasicSettingsText("Ввод. Дата рег. в Ростехнадзоре")}

                    //Ввод. Дата ввода в эксплуатацию
                    Box (myBoxTextModifier) {
                        BasicSettingsText("Ввод. Дата ввода в эксплуатацию")}

                    Box (myBoxTextModifier) {
                        BasicSettingsText("Ввод. Заводской номер СБКБ") }

                    Box (myBoxTextModifier) {
                        BasicSettingsText("Ввод. Дата установки СБКБ") }
                }
            }
        }

//№.2 - Правая область для отображения и ввода параметров. (1- Дата и время , 2-Тип крана)
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .weight(2f),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            //Бокс 2.1 для отображения и ввода параметров. (1- Дата и время)
            Box(myBoxSettingModifierBV) {

                Column(modifier = Modifier
                    .fillMaxSize(),
                    verticalArrangement = Arrangement.Top) {
                    // Отображение текущего времени
                    Box (myBoxVisualValuesModifier) {
                        BasicSettingsValues("Time") }
                    // Отображение текущей даты
                    Box (myBoxVisualValuesModifier) {
                        BasicSettingsValues(text = "Date") }

                    // Ввод времени
                    Row (horizontalArrangement = Arrangement.SpaceBetween) {
                        //Поле ввода ЧАСЫ
                        Box (modifier = Modifier
                            .padding(start = 15.dp, top = 7.dp, bottom = 0.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(colorResource(R.color.white))
                            .fillMaxWidth(0.45f)
                            .border(
                                width = 1.dp,
                                color = (colorResource(R.color.white)),
                                shape = RoundedCornerShape(5.dp)
                            )) {
                            //mySettingsValues(vm.GV_Time_HOUR,mySettingModifier)

                            BasicTextFilledGiraffe(
                                placeholder = "ЧЧ",
                                text = "",
                                onValueChange = { ""},
                                modifier = Modifier,
                            )
                        }
                        //Поле ввода МИНУТЫ
                        Box (modifier = Modifier
                            .padding(start = 15.dp, top = 7.dp, bottom = 0.dp, end = 15.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(colorResource(R.color.white))
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = (colorResource(R.color.white)),
                                shape = RoundedCornerShape(5.dp)
                            )) {
                            //mySettingsValues(vm.GV_Time_HOUR,mySettingModifier)

                            BasicTextFilledGiraffe(
                                placeholder = "MM",
                                text = "",
                                onValueChange = { ""},
                                modifier = Modifier,
                            )

                        }
                    }

                    // Ввод даты
                    Row (horizontalArrangement = Arrangement.SpaceBetween) {
                        //День
                        Box (modifier = Modifier
                            .padding(start = 15.dp, top = 7.dp, bottom = 0.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(colorResource(R.color.white))
                            .fillMaxWidth(0.19f)
                            .border(
                                width = 1.dp,
                                color = (colorResource(R.color.white)),
                                shape = RoundedCornerShape(5.dp)
                            )) {
                            //mySettingsValues("07",mySettingModifier)

                            BasicTextFilledGiraffe(
                                placeholder = "ДД",
                                text = "",
                                onValueChange = { ""},
                                modifier = Modifier,
                            )
                        }
                        //Месяц
                        Box (modifier = Modifier
                            .padding(start = 15.dp, top = 7.dp, bottom = 0.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(colorResource(R.color.white))
                            .fillMaxWidth(0.25f)
                            .border(
                                width = 1.dp,
                                color = (colorResource(R.color.white)),
                                shape = RoundedCornerShape(5.dp)
                            )) {
                            //mySettingsValues("03",mySettingModifier)

                            BasicTextFilledGiraffe(
                                placeholder = "ММ",
                                text = "",
                                onValueChange = { ""},
                                modifier = Modifier,
                            )
                        }
                        //Год
                        Box (modifier = Modifier
                            .padding(start = 15.dp, top = 7.dp, bottom = 0.dp, end = 15.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(colorResource(R.color.white))
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = (colorResource(R.color.white)),
                                shape = RoundedCornerShape(5.dp)
                            )) {
                            //mySettingsValues("2025",mySettingModifier)

                            BasicTextFilledGiraffe(
                                placeholder = "ГГГГ",
                                text = "",
                                onValueChange = { ""},
                                modifier = Modifier,
                            )
                        }

                    }
                }
            }
                //Область ввода данных крана и СБКБ
            Box(myBoxSettingModifierBV) {
                Column(modifier = Modifier
                    .fillMaxSize(),
                    verticalArrangement = Arrangement.Top) {

                    //Ввод. Модель крана
                    Box (myBoxSetValuesModifier) {
                        //mySettingsValues("TDK-10.180", mySettingModifier)
                        BasicTextFilledGiraffe(
                            placeholder = "10.180",
                            text = "",
                            onValueChange = { ""},
                            modifier = Modifier,
                        )
                    }

                    //Ввод. Заводской номер крана
                    Box (myBoxSetValuesModifier) {
                        //mySettingsValues("169", mySettingModifier)
                        BasicTextFilledGiraffe(
                            placeholder = "№",
                            text = "",
                            onValueChange = { ""},
                            modifier = Modifier,
                        )
                    }
                    //Ввод. Дата регистр. в Ростехнадзоре
                    Row (horizontalArrangement = Arrangement.SpaceBetween) {
                        //День
                        Box (modifier = Modifier
                            .padding(start = 15.dp, top = 7.dp, bottom = 0.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(colorResource(R.color.white))
                            .fillMaxWidth(0.19f)
                            .border(
                                width = 1.dp,
                                color = (colorResource(R.color.white)),
                                shape = RoundedCornerShape(5.dp)
                            )) {
                            //mySettingsValues("07",mySettingModifier)
                            BasicTextFilledGiraffe(
                                placeholder = "ДД",
                                text = "",
                                onValueChange = { ""},
                                modifier = Modifier,
                            )
                        }
                        //Месяц
                        Box (modifier = Modifier
                            .padding(start = 15.dp, top = 7.dp, bottom = 0.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(colorResource(R.color.white))
                            .fillMaxWidth(0.25f)
                            .border(
                                width = 1.dp,
                                color = (colorResource(R.color.white)),
                                shape = RoundedCornerShape(5.dp)
                            )) {
                            //mySettingsValues("03",mySettingModifier)
                            BasicTextFilledGiraffe(
                                placeholder = "MM",
                                text = "",
                                onValueChange = { ""},
                                modifier = Modifier,
                            )
                        }
                        //Год
                        Box (modifier = Modifier
                            .padding(start = 15.dp, top = 7.dp, bottom = 0.dp, end = 15.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(colorResource(R.color.white))
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = (colorResource(R.color.white)),
                                shape = RoundedCornerShape(5.dp)
                            )) {
                            //mySettingsValues("2025",mySettingModifier)
                            BasicTextFilledGiraffe(
                                placeholder = "ГГГГ",
                                text = "",
                                onValueChange = { ""},
                                modifier = Modifier,
                            )
                        }
                    }

                    //Ввод. Дата ввода крана в эксплуатацию
                    Row (horizontalArrangement = Arrangement.SpaceBetween) {
                        //День
                        Box (modifier = Modifier
                            .padding(start = 15.dp, top = 7.dp, bottom = 0.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(colorResource(R.color.white))
                            .fillMaxWidth(0.19f)
                            .border(
                                width = 1.dp,
                                color = (colorResource(R.color.white)),
                                shape = RoundedCornerShape(5.dp)
                            )) {
                            //mySettingsValues("07",mySettingModifier)
                            BasicTextFilledGiraffe(
                                placeholder = "ДД",
                                text = "",
                                onValueChange = { ""},
                                modifier = Modifier,
                            )
                        }
                        //Месяц
                        Box (modifier = Modifier
                            .padding(start = 15.dp, top = 7.dp, bottom = 0.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(colorResource(R.color.white))
                            .fillMaxWidth(0.25f)
                            .border(
                                width = 1.dp,
                                color = (colorResource(R.color.white)),
                                shape = RoundedCornerShape(5.dp)
                            )) {
                            //mySettingsValues("03",mySettingModifier)
                            BasicTextFilledGiraffe(
                                placeholder = "ММ",
                                text = "",
                                onValueChange = { ""},
                                modifier = Modifier,
                            )
                        }
                        //Год
                        Box (modifier = Modifier
                            .padding(start = 15.dp, top = 7.dp, bottom = 0.dp, end = 15.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(colorResource(R.color.white))
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = (colorResource(R.color.white)),
                                shape = RoundedCornerShape(5.dp)
                            )) {
                            //mySettingsValues("2025",mySettingModifier)
                            BasicTextFilledGiraffe(
                                placeholder = "ГГГГ",
                                text = "",
                                onValueChange = { ""},
                                modifier = Modifier,
                            )
                        }
                    }

                    //Ввод. Заводской номер СБКБ
                    Box (myBoxSetValuesModifier) {
                        //mySettingsValues("01", mySettingModifier)
                        BasicTextFilledGiraffe(
                            placeholder = "№",
                            text = "",
                            onValueChange = { ""},
                            modifier = Modifier,
                        )
                    }
                    //Ввод. Дата установки СБКБ
                    Row (horizontalArrangement = Arrangement.SpaceBetween) {
                        //День
                        Box (modifier = Modifier
                            .padding(start = 15.dp, top = 7.dp, bottom = 0.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(colorResource(R.color.white))
                            .fillMaxWidth(0.19f)
                            .border(
                                width = 1.dp,
                                color = (colorResource(R.color.white)),
                                shape = RoundedCornerShape(5.dp)
                            )) {
                            //mySettingsValues("07",mySettingModifier)
                            BasicTextFilledGiraffe(
                                placeholder = "ДД",
                                text = "",
                                onValueChange = { ""},
                                modifier = Modifier,
                            )
                        }
                        //Месяц
                        Box (modifier = Modifier
                            .padding(start = 15.dp, top = 7.dp, bottom = 0.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(colorResource(R.color.white))
                            .fillMaxWidth(0.25f)
                            .border(
                                width = 1.dp,
                                color = (colorResource(R.color.white)),
                                shape = RoundedCornerShape(5.dp)
                            )) {
                            //mySettingsValues("03",mySettingModifier)
                            BasicTextFilledGiraffe(
                                placeholder = "ММ",
                                text = "",
                                onValueChange = { ""},
                                modifier = Modifier,
                            )
                        }
                        //Год
                        Box (modifier = Modifier
                            .padding(start = 15.dp, top = 7.dp, bottom = 0.dp, end = 15.dp)
                            .clip(shape = RoundedCornerShape(5.dp))
                            .background(colorResource(R.color.white))
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = (colorResource(R.color.white)),
                                shape = RoundedCornerShape(5.dp)
                            )) {
                            //mySettingsValues("2025",mySettingModifier)
                            BasicTextFilledGiraffe(
                                placeholder = "ГГГГ",
                                text = "",
                                onValueChange = { ""},
                                modifier = Modifier,
                            )
                        }
                    }
                }
            }
        }
    }
}