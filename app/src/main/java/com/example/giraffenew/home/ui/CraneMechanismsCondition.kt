package com.example.giraffenew.home.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.ui.theme.DarkLemon
import com.example.giraffenew.R
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampError
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampAlarmStatus
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampMechanismsCondition
import com.example.giraffenew.core.ui_kit.composable.lamp.BasicLampMechanismsPoligon
import com.example.giraffenew.core.ui_kit.composable.text.BasicParametersText
import com.example.giraffenew.home.presentation.HomeScreenViewModel
import com.example.giraffenew.home.ui.text.ParametersTextControlEffort
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme


@Composable
fun CraneMechanismsCondition(
    craneMechanismsCondition: HomeScreenViewModel.CraneMechanismsConditionState,
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
        val weightSensorErr = craneMechanismsCondition.weightSensorErr
        val anemometerSensorErr = craneMechanismsCondition.anemometerSensorErr
        val trolleyFwdStatus = craneMechanismsCondition.trolleyFwdStatus
        val trolleyRevStatus = craneMechanismsCondition.trolleyRevStatus
        val hoistUpStatus = craneMechanismsCondition.hoistUpStatus
        val hoistDownStatus = craneMechanismsCondition.hoistDownStatus
        val slewRightStatus = craneMechanismsCondition.slewRightStatus
        val slewLeftStatus = craneMechanismsCondition.slewLeftStatus
        val cranRightStatus = craneMechanismsCondition.cranRightStatus
        val craneLeftStatus = craneMechanismsCondition.craneLeftStatus
        val craneRight = craneMechanismsCondition.craneRight
        val craneLeft = craneMechanismsCondition.craneLeft
        val slewLeft = craneMechanismsCondition.slewLeft
        val slewRight = craneMechanismsCondition.slewRight
        val trolleyFwd = craneMechanismsCondition.trolleyFwd
        val trolleyRev = craneMechanismsCondition.trolleyRev
        val hoistUp = craneMechanismsCondition.hoistUp
        val hoistDown = craneMechanismsCondition.hoistDown
        val alarmLampStatus = craneMechanismsCondition.alarmLampStatus
        val hoistControlEffortHz = craneMechanismsCondition.hoistControlEffortHz
        val slewControlEffortHz = craneMechanismsCondition.slewControlEffortHz
        val trolleyControlEffortHz = craneMechanismsCondition.trolleyControlEffortHz
        val craneControlEffortHz = craneMechanismsCondition.craneControlEffortHz


        val myBoxMechanismModifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
            .weight(1f)

//Датчик усилия. Состояние
        Box(myBoxMechanismModifier){
            Box(modifier = Modifier
                .padding(top = 57.dp, start = 23.dp)
                .size(height = 23.dp, width = 54.dp)
            )
            { BasicLampError(isLampStatus = weightSensorErr) }

            Image (
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_load_on_the_hook),
                contentDescription = "Груз",
                modifier = Modifier
                    .padding(horizontal = 5.dp, vertical = 10.dp)
                    .fillMaxSize())
        }
//Подъем. Состояние
        Box(myBoxMechanismModifier){
//Cтатус запрета движений на HMI (0-Red, 1-Yellow, 2-Green, 3-Blue)
            Box(modifier = Modifier
                .padding(top = 25.dp, start = 9.dp)
                .size(height = 55.dp, width = 18.dp))
            { BasicLampMechanismsCondition(isLampStatus = hoistDownStatus,)}
            Box(modifier = Modifier
                .padding(top = 25.dp, start = 71.dp)
                .size(height = 55.dp, width = 18.dp))
            { BasicLampMechanismsCondition(isLampStatus = hoistUpStatus,)}
//Cтатус разрешения / запрета выхода из полигона
            Box(modifier = Modifier
                .padding(top = 25.dp, start = 9.dp)
                .size(height = 55.dp, width = 18.dp))
            { BasicLampMechanismsPoligon(isLampStatus = hoistDown, isVisibility = hoistDown)}
            Box(modifier = Modifier
                .padding(top = 25.dp, start = 71.dp)
                .size(height = 55.dp, width = 18.dp))
            { BasicLampMechanismsPoligon(isLampStatus = hoistUp, isVisibility = hoistUp)}

            Image (
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_hook_suspension),
                contentDescription = "Подъем",
                modifier = Modifier
                    .padding(start = 10.dp, end =10.dp, top = 25.dp)
            )
            ParametersTextControlEffort(
                formatDecimalHoist(hoistControlEffortHz),
                modifier = Modifier
                    .padding(start = 5.dp, end =1.dp, top = 1.dp))
        }
//Поворот. Состояние
        Box(myBoxMechanismModifier){
//Cтатус запрета движений на HMI (0-Red, 1-Yellow, 2-Green, 3-Blue)
            Box(modifier = Modifier
                .padding(top = 32.dp, start = 10.dp)
                .size(height = 20.dp, width = 30.dp))
            { BasicLampMechanismsCondition(isLampStatus = slewLeftStatus,) }
            Box(modifier = Modifier
                .padding(top = 32.dp, start = 58.dp)
                .size(height = 20.dp, width = 30.dp))
            { BasicLampMechanismsCondition(isLampStatus = slewRightStatus,) }
//Cтатус разрешения / запрета выхода из полигона
            Box(modifier = Modifier
                .padding(top = 32.dp, start = 10.dp)
                .size(height = 20.dp, width = 30.dp))
            { BasicLampMechanismsPoligon(isLampStatus = slewRight, isVisibility = slewLeft) }
            Box(modifier = Modifier
                .padding(top = 32.dp, start = 58.dp)
                .size(height = 20.dp, width = 30.dp))
            { BasicLampMechanismsPoligon(isLampStatus = slewLeft, isVisibility = slewRight) }

            Image (
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_tower_crane_boom),
                contentDescription = "Поворот",
                modifier = Modifier
                    .padding(start = 10.dp, end =10.dp, top = 18.dp, bottom = 5.dp)
                    .fillMaxSize())
            ParametersTextControlEffort(
                formatDecimalSlew(slewControlEffortHz),
                modifier = Modifier
                    .padding(start = 6.dp, end =2.dp, top = 1.dp))
        }

//Тележка. Состояние
        Box(myBoxMechanismModifier){
//Cтатус запрета движений на HMI (0-Red, 1-Yellow, 2-Green, 3-Blue)
            Box(modifier = Modifier
                .padding(top = 25.dp, start = 10.dp)
                .size(height = 20.dp, width = 36.dp))
            { BasicLampMechanismsCondition(isLampStatus = trolleyRevStatus)}
            Box(modifier = Modifier
                .padding(top = 25.dp, start = 52.dp)
                .size(height = 20.dp, width = 36.dp))
            { BasicLampMechanismsCondition(isLampStatus = trolleyFwdStatus)}
//Cтатус разрешения / запрета выхода из полигона
            Box(modifier = Modifier
                .padding(top = 25.dp, start = 10.dp)
                .size(height = 20.dp, width = 36.dp))
            { BasicLampMechanismsPoligon(isLampStatus = trolleyRev, isVisibility =trolleyRev ) }
            Box(modifier = Modifier
                .padding(top = 25.dp, start = 52.dp)
                .size(height = 20.dp, width = 36.dp))
            { BasicLampMechanismsPoligon(isLampStatus = trolleyFwd, isVisibility =trolleyFwd )}

            Image (
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_cargo_trolley),
                contentDescription = "Тележка",
                modifier = Modifier
                    .padding(start = 10.dp, end =10.dp, top = 22.dp, bottom = 5.dp)
                    .fillMaxSize())
            ParametersTextControlEffort(
                formatDecimalTrolley(trolleyControlEffortHz),
                modifier = Modifier
                    .padding(start = 9.dp, end =2.dp, top = 1.dp))
        }

//Ход крана. Состояние
        Box(myBoxMechanismModifier){
//Cтатус запрета движений на HMI (0-Red, 1-Yellow, 2-Green, 3-Blue)
            Box(modifier = Modifier
                .padding(top = 75.dp, start = 10.dp)
                .size(height = 20.dp, width = 36.dp))
            { BasicLampMechanismsCondition(isLampStatus = craneLeftStatus,)}
            Box(modifier = Modifier
                .padding(top = 75.dp, start = 52.dp)
                .size(height = 20.dp, width = 36.dp))
            { BasicLampMechanismsCondition(isLampStatus = cranRightStatus,)}
//Cтатус разрешения / запрета выхода из полигона
            Box(modifier = Modifier
                .padding(top = 75.dp, start = 10.dp)
                .size(height = 20.dp, width = 36.dp))
            { BasicLampMechanismsPoligon(isLampStatus = craneLeft, isVisibility =craneLeft )}
            Box(modifier = Modifier
                .padding(top = 75.dp, start = 52.dp)
                .size(height = 20.dp, width = 36.dp))
            { BasicLampMechanismsPoligon(isLampStatus = craneRight, isVisibility = craneRight )}

            Image (
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_cargo_move),
                contentDescription = "Ход крана",
                modifier = Modifier
                    .padding(start = 10.dp, end =10.dp, top = 27.dp, bottom = 5.dp)
                    .fillMaxSize())
            ParametersTextControlEffort(
                formatDecimalCrane(craneControlEffortHz),
                modifier = Modifier
                    .padding(start = 9.dp, end =2.dp, top = 1.dp))
        }
//Датчиик ветра. Состояние
        Box(myBoxMechanismModifier){
            Box(modifier = Modifier
                .padding(top = 13.dp, start = 17.dp)
                .size(height = 24.dp, width = 65.dp))
            {BasicLampError(isLampStatus = anemometerSensorErr,) }

            Image (
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_wind_speed),
                contentDescription = "Ветер",
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .fillMaxSize())
        }
//Готовность. Состояние (0-Ready, 1 - Warning, 2 - Alarm)
        Box(myBoxMechanismModifier){
            BasicLampAlarmStatus(
                modifier= Modifier,
                statusLamp = alarmLampStatus
            )

        }
    }

}

private val HOIST_COEFFICIENT: Float = 0.93f //Коэффициент коррекции скорости подъема
private val SLEW_COEFFICIENT: Float = 0.016f //Коэффициент коррекции скорости поворота
private val TROLLEY_COEFFICIENT: Float = 1f //Коэффициент коррекции скорости тележки
private val CRANE_COEFFICIENT: Float = 1f //Коэффициент коррекции скорости подъема

private fun formatDecimal(value: Float): String {
    return String.format("%04.1f", value).replace(',', '.')
}

private fun formatDecimalHoist(value: Float): String {
    val realValue = (value * HOIST_COEFFICIENT) / 1f
    return String.format("%05.1fм/мин", realValue).replace(',', '.')
}
private fun formatDecimalSlew(value: Float): String {
    val realValue = (value * SLEW_COEFFICIENT) / 1f
    return String.format("%04.1fоб/мин", realValue).replace(',', '.')
}
private fun formatDecimalTrolley(value: Float): String {
    val realValue = (value * TROLLEY_COEFFICIENT) / 1f
    return String.format("%04.1fм/мин", realValue).replace(',', '.')
}
private fun formatDecimalCrane(value: Float): String {
    val realValue = (value * CRANE_COEFFICIENT) / 1f
    return String.format("%04.1fм/мин", realValue).replace(',', '.')
}

@Preview
@Composable
fun CraneMechanismsConditionPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        CraneMechanismsCondition(
            craneMechanismsCondition = TODO()
        )
    }
}