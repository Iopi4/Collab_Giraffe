package com.example.giraffenew.coordinate_protection.prezentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.giraffenew.R
import com.example.giraffenew.coordinate_protection.ui.CoordinateProtectionSetting
import com.example.giraffenew.coordinate_protection.ui.polygon_1.Polygon1Content
import com.example.giraffenew.coordinate_protection.ui.polygon_2.Polygon2Content
import com.example.giraffenew.core.ui_kit.composable.BasicSettingBackground
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonTabGiraffe
import com.example.giraffenew.service_menu.presentation.NavRoutesService
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

import kotlinx.coroutines.launch

@Composable
fun CoordinateProtectionScreen (){
    BasicSettingBackground()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(modifier = Modifier.weight(1f)) {
            CoordinateProtectionSetting()
        }
        Box(modifier = Modifier.weight(7f)) {
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
    }
}

@Composable
fun NavBarCoordinateProtection(navController: NavController) {
    val myBoxNavServiceContentModifier = Modifier
        .padding(top = 5.dp, start = 5.dp, end = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()
        .border(
            width = 3.dp,
            color = DarkGrew,
            shape = RoundedCornerShape(5.dp)
        )
//Код для формирования области в верху с кнопками переходов между страницами
    Column (myBoxNavServiceContentModifier,
        verticalArrangement = Arrangement.SpaceEvenly)
    {
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween) {

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.CoordinateProtection_Button_Polygon_1,
                    onClick = {
                        navController.navigate(NavRoutesCoordinateProtection.PagePolygon1.route) },) {}
            }

            Box(modifier = Modifier.weight(1f)) {
                BasicButtonTabGiraffe(
                    modifier = Modifier,
                    text = R.string.CoordinateProtection_Button_Polygon_2,
                    onClick = {
                        navController.navigate(NavRoutesCoordinateProtection.PagePolygon2.route) },) {}
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
fun CoordinateProtectionScreenPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        CoordinateProtectionScreen(
        )
    }
}