package com.example.giraffenew.coordinate_protection.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.giraffenew.R
import com.example.giraffenew.coordinate_protection.ui.polygon_1.Polygon1
import com.example.giraffenew.coordinate_protection.ui.polygon_1.Polygon1Content
import com.example.giraffenew.coordinate_protection.ui.polygon_2.Polygon2
import com.example.giraffenew.coordinate_protection.ui.polygon_2.Polygon2Content
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import kotlinx.coroutines.launch

@Composable
fun CoordinateProtectionContent(
) {
    val navController = rememberNavController()
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly)
    { NavBarCoordinateProtection (navController = navController)
        NavHost(navController, startDestination = NavRoutesCoordinateProtection.PagePolygon1.route){
            composable(NavRoutesCoordinateProtection.PagePolygon1.route) { Polygon1Content() }
            composable(NavRoutesCoordinateProtection.PagePolygon2.route) { Polygon2Content() }

        }
    }
}

@Composable
fun NavBarCoordinateProtection(navController: NavController) {
    //Код для формирования области в верху с кнопками переходов между страницами (название и стрелки вверху экрана)

    val data = listOf("Полигон №1", "Полигон №2")
    //Количество страниц из переменной data
    val pagerState = rememberPagerState { data.size }

    // вызове функции rememberCoroutineScope()
    val coroutineScope =
        rememberCoroutineScope() //Объект coroutineScope определяет область действия корутин.
    //Он объявляет диспетчер, который будет использоваться для запуска перехода с одной страницы на другую с отображением содержимого
    // т.е. когда мы переходим на другю страницу, содержимое предыдущей страницы перестает обрабатываться потоком.

    val myBoxSettingModifierGR = Modifier
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = DarkGrew,
            shape = RoundedCornerShape(5.dp)
        )


    //Код для перехода между страницами (название и стрелки вверху экрана)
    HorizontalPager(
        state = pagerState,
        Modifier.fillMaxWidth()
    ) { page ->

        Row (myBoxSettingModifierGR,
            horizontalArrangement = Arrangement.Start) {
            val product = data[page]

            Box (modifier = Modifier
                .padding(start = 15.dp, top = 7.dp, bottom = 0.dp)
                .clip(shape = RoundedCornerShape(5.dp))
                .weight(4f)
            ) {
                Text(product, fontSize = 25.sp, color = DarkGrew)
            }

            Box(
                modifier = Modifier
                    .padding(start = 15.dp, top = 0.dp, bottom = 0.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .weight(1f)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_setting_double_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .rotate(180f)
                        .clickable {
                            navController.navigate(NavRoutesCoordinateProtection.PagePolygon1.route)
                            coroutineScope.launch{
                                pagerState.animateScrollToPage(pagerState.currentPage -1)
                            }
                        }
                )
            }

            Box(
                modifier = Modifier
                    .padding(start = 15.dp, top = 0.dp, bottom = 0.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .weight(1f)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_setting_double_arrow),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable {
                            navController.navigate(NavRoutesCoordinateProtection.PagePolygon2.route)
                            coroutineScope.launch{
                                pagerState.animateScrollToPage(pagerState.currentPage +1)
                            }
                        }
                )
            }
        }
    }
}


sealed class NavRoutesCoordinateProtection (val route: String) {
    object PagePolygon1: NavRoutesCoordinateProtection("Полигон №1")
    object PagePolygon2: NavRoutesCoordinateProtection("Полигон №2")
}

@Preview
@Composable
fun CoordinateProtectionContentPreview () {
    GIRAFFEnewTheme(dynamicColor = false) {
        CoordinateProtectionContent(
        )
    }
}