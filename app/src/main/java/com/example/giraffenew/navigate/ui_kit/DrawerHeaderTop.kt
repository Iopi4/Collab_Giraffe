package com.example.giraffenew.navigate.ui_kit

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.R

@Composable
//Верхняя часть выдвижного окна навигации
fun DrawerHeaderTop(modifier: Modifier) {

    Row (verticalAlignment = Alignment.Top,
        modifier = modifier
            .background(colorResource(id = R.color.Burgundy_violet))
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Image(
            painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = modifier
                .size(70.dp)
                .clip(shape = RoundedCornerShape(3.dp))
        )

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = modifier
                .background(colorResource(id = R.color.Burgundy_violet))
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                modifier= Modifier,
                textAlign = TextAlign.Start,
                fontSize = 16.sp, color = colorResource(id= R.color.white))
            Text(
                text = stringResource(id = R.string.mail),
                modifier= Modifier,
                textAlign = TextAlign.Start,
                fontSize = 14.sp, color = colorResource(id= R.color.Silver))
        }

    }
}