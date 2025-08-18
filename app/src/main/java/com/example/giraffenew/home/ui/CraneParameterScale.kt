package com.example.giraffenew.home.ui

import android.text.Highlights
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.ui.theme.DarkLemon
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.scale.BasicParameterScaleHorizon
import com.example.giraffenew.core.ui_kit.composable.text.BasicParametersText
import com.example.giraffenew.core.ui_kit.composable.scale.BasicParameterScaleVertical
import com.example.giraffenew.core.ui_kit.composable.text.BasicValuesSettingsParameterGiraffe
import com.example.giraffenew.ui.theme.DarkGrew
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import kotlin.Int

@Composable
fun CraneParameterScale(
    weightPercent:      Float,
    hoistPosition:      Float,
    hoistMaxPosition:   Float,
    cranePosition:      Float,
    craneMaxPosition:   Float,
    modifier: Modifier= Modifier,
){



    val myBoxMainModifier = Modifier
        .padding(5.dp)
        .clip(shape = RoundedCornerShape(10.dp))
        .fillMaxSize()
        .border(
            width = 2.dp,
            color = DarkLemon,
            shape = RoundedCornerShape(10.dp)
        )

    //Бокс 1 - для крана с ГХ
    Box(myBoxMainModifier){
//Кран
        Image (
            imageVector = ImageVector.vectorResource(id = R.drawable.image_home_tower_crane_vector),
            contentDescription = "Кран",
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 50.dp))
//Шкала грузоподъемности
        BasicParametersText(
            stringResource(R.string.Home_LOADING_CAPACITY),
            modifier = Modifier
            .padding(start = 130.dp, end = 5.dp, top = 10.dp))

        Box(
            modifier = Modifier
                .padding(start = 130.dp, top = 35.dp, end = 10.dp),
            contentAlignment = Alignment.TopStart
        ) {
            BasicParameterScaleHorizon(
            currentValue = weightPercent,
            minValue = 0f,
            maxValue = 120f,
            modifier = Modifier.size(width = 200.dp, height = 10.dp)
        )}

//Шкала высоты подъема. Заголовок
        BasicParametersText(
            stringResource(R.string.Home_LIFT_HEIGHT),
            modifier = Modifier
            .padding(start = 170.dp, end = 50.dp, top = 137.dp))
        //Значение максимальной высоты подъема
        BasicParametersText(
            formatDecimal(hoistMaxPosition),
            modifier = Modifier
                .padding(start = 135.dp, end = 50.dp, top = 147.dp))
        //Шкала
        Box(
            modifier = Modifier
                .padding(start = 260.dp, top = 80.dp,),
            contentAlignment = Alignment.TopStart
        ) {
            BasicParameterScaleVertical(
                currentValue = hoistPosition,
                minValue = 0f,
                maxValue = hoistMaxPosition,
                modifier = Modifier.size(width = 10.dp, height = 150.dp)
            )
        }

//Шкала хода крана. Заголовок
        BasicParametersText(
            stringResource(R.string.Home_MOVE_CRANE),
            modifier = Modifier
            .padding(start = 10.dp, top = 225.dp))
        //Значение максимальной длины кранового пути
        BasicParametersText(
            formatDecimal(craneMaxPosition),
            modifier = Modifier
                .padding(start = 150.dp, end = 50.dp, top = 240.dp))
        Box(
            modifier = Modifier
                .padding(start = 10.dp, top = 245.dp, end = 15.dp),
            contentAlignment = Alignment.TopStart
        ) {
            BasicParameterScaleHorizon(
                currentValue = cranePosition,
                minValue = 0f,
                maxValue = craneMaxPosition,
                modifier = Modifier.size(width = 135.dp, height = 10.dp)
            )}

    }

}

private fun formatDecimal(value: Float): String {
    //return String.format("%.1f", value).padStart(5, '0')
    return String.format("%04.1f", value).replace(',', '.')
}

@Preview
@Composable
fun CraneParameterScalePreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        CraneParameterScale(
            weightPercent= 80f,
            hoistPosition = 30f,
            hoistMaxPosition= 60f,
            cranePosition = 20f,
            craneMaxPosition= 40f,
            )
    }
}