package com.example.giraffenew.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.ui.theme.DarkLemon
import com.example.giraffenew.R
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import com.example.giraffenew.ui.theme.Silver


@Composable
fun CraneMechanismsCondition(
    modifier: Modifier= Modifier,
){

    //№2. Центральная область для отображения состояния механизмов крана.
    //Контейнер Column позволяет выстроить вложенные компоненты в столбик
    Column (modifier = Modifier
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .fillMaxHeight()
        .border(
            width = 2.dp,
            color = DarkLemon,
            shape = RoundedCornerShape(10.dp)
        ))
    {
        val myBoxMechanismModifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .weight(1f)



        //Датчик усилия. Состояние
        Box(myBoxMechanismModifier){

            Box( modifier = Modifier
                .padding(top = 56.dp, start = 23.dp)
                .size(height = 23.dp, width = 53.dp)
                .background(colorResource(id = R.color.Emerald))) {}

            Image (
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_load_on_the_hook),
                contentDescription = "Груз",
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 10.dp)
                    .fillMaxSize())

        }
//Тележка. Состояние
        Box(myBoxMechanismModifier){
            Box(modifier = Modifier
                .padding(top = 15.dp, start = 10.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .size(height = 20.dp, width = 36.dp)
                .background(colorResource(id = R.color.Emerald))
                .border(
                    width = 2.dp, color = Silver, //рамка
                    shape = RoundedCornerShape(5.dp)
                )) {}

            Box(modifier = Modifier
                .padding(top = 15.dp, start = 52.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .size(height = 20.dp, width = 36.dp)
                .background(colorResource(id = R.color.Emerald))
                .border(
                    width = 2.dp, color = Silver, //рамка
                    shape = RoundedCornerShape(5.dp)
                )) {}


            Image (
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_cargo_trolley),
                contentDescription = "Тележка",
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .fillMaxSize())
        }
//Поворот. Состояние
        Box(myBoxMechanismModifier){
            Box(modifier = Modifier
                .padding(top = 15.dp, start = 10.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .size(height = 100.dp, width = 30.dp)
                .background(colorResource(id = R.color.Emerald))
                .border(
                    width = 2.dp, color = Silver, //рамка
                    shape = RoundedCornerShape(5.dp)
                )) {}

            Box(modifier = Modifier
                .padding(top = 15.dp, start = 60.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .size(height = 100.dp, width = 30.dp)
                .background(colorResource(id = R.color.Emerald))
                .border(
                    width = 2.dp, color = Silver, //рамка
                    shape = RoundedCornerShape(5.dp)
                )) {}

            Image (
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_tower_crane_boom),
                contentDescription = "Поворот",
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .fillMaxSize())
        }
//Подъем. Состояние
        Box(myBoxMechanismModifier){

            Box(modifier = Modifier
                .padding(top = 15.dp, start = 9.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .size(height = 100.dp, width = 18.dp)
                .background(colorResource(id = R.color.Emerald))
                .border(
                    width = 2.dp, color = Silver, //рамка
                    shape = RoundedCornerShape(5.dp)
                )) {}

            Box(modifier = Modifier
                .padding(top = 15.dp, start = 71.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .size(height = 100.dp, width = 18.dp)
                .background(colorResource(id = R.color.Emerald))
                .border(
                    width = 2.dp, color = Silver, //рамка
                    shape = RoundedCornerShape(5.dp)
                )) {}

            Image (
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_hook_suspension),
                contentDescription = "Подъем",
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .fillMaxSize())
        }
//Ход крана. Состояние
        Box(myBoxMechanismModifier){

            Box(myBoxMechanismModifier){
                Box(modifier = Modifier
                    .padding(top = 65.dp, start = 10.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .size(height = 20.dp, width = 36.dp)
                    .background(colorResource(id = R.color.Emerald))
                    .border(
                        width = 2.dp, color = Silver, //рамка
                        shape = RoundedCornerShape(5.dp)
                    )) {}

                Box(modifier = Modifier
                    .padding(top = 65.dp, start = 52.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .size(height = 20.dp, width = 36.dp)
                    .background(colorResource(id = R.color.Emerald))
                    .border(
                        width = 2.dp, color = Silver, //рамка
                        shape = RoundedCornerShape(5.dp)
                    )) {}
            }

            Image (
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_cargo_move),
                contentDescription = "Ход крана",
                modifier = Modifier
                    .padding(horizontal = 15.dp, vertical = 10.dp)
                    .fillMaxSize())
        }
//Датчиик ветра. Состояние
        Box(myBoxMechanismModifier){

            Box(modifier = Modifier
                .padding(top = 10.dp, start = 10.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .size(height = 90.dp, width = 80.dp)
                .background(colorResource(id = R.color.Emerald))
                .border(
                    width = 2.dp, color = Silver, //рамка
                    shape = RoundedCornerShape(5.dp)
                )) {}

            Image (
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_wind_speed),
                contentDescription = "Ветер",
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .fillMaxSize())
        }
//Готовность. Состояние
        Box(myBoxMechanismModifier){
            Image (
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_crane_ready),
                contentDescription = "Подъем",
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .fillMaxSize())
        }
    }

}

@Preview
@Composable
fun CraneMechanismsConditionPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        CraneMechanismsCondition()
    }
}