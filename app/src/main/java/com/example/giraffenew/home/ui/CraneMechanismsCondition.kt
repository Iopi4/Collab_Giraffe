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
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme


@Composable
fun CraneMechanismsCondition(
    weightSensorErr     : Int,  //AT%MW711 Cбой датчика усилия
    anemometerSensorErr : Int,	//AT%MW712 Cбой анемометра
    alarmLampStatus	    : Int,	//AT%MW337 статус лампочки аварии на главном экране (0-Green, 1-Yel, 2-Red)

    trolleyFwdStatus    : Int,	//AT%MW320 Cтатус запрета движений на HMI (0-Red, 1-Yel, 2-Green, 3-Blue)
    trolleyRevStatus    : Int,	//AT%MW321 Cтатус запрета движений на HMI (0-Red, 1-Yel, 2-Green, 3-Blue)
    hoistUpStatus       : Int,	//AT%MW322 Cтатус запрета движений на HMI (0-Red, 1-Yel, 2-Green, 3-Blue)
    hoistDownStatus     : Int,	//AT%MW323 Cтатус запрета движений на HMI (0-Red, 1-Yel, 2-Green, 3-Blue)
    slewRightStatus     : Int,	//AT%MW324 Cтатус запрета движений на HMI (0-Red, 1-Yel, 2-Green, 3-Blue)
    slewLeftStatus      : Int,	//AT%MW325 Cтатус запрета движений на HMI (0-Red, 1-Yel, 2-Green, 3-Blue)
    cranRightStatus 	: Int,	//AT%MW326 Cтатус запрета движений на HMI (0-Red, 1-Yel, 2-Green, 3-Blue)
    craneLeftStatus 	: Int,	//AT%MW327 Cтатус запрета движений на HMI (0-Red, 1-Yel, 2-Green, 3-Blue)

    craneRight	: Int, //AT%MW328 Статус можно выйти из полигона краном вперед
    craneLeft	: Int, //AT%MW329 Статус можно выйти из полигона краном назад
    slewLeft	: Int, //AT%MW330 Статус можно выйти из полигона поворотом налево
    slewRight	: Int, //AT%MW331 Статус можно выйти из полигона поворотом направо
    trolleyFwd	: Int, //AT%MW332 Статус можно выйти из полигона телегой вперед
    trolleyRev	: Int, //AT%MW333 Статус можно выйти из полигона телегой назад
    hoistUp		: Int, //AT%MW334 Статус можно выйти из полигона крюком вверх
    hoistDown	: Int, //AT%MW335 Статус можно выйти из полигона крюком вниз

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
//Тележка. Состояние
        Box(myBoxMechanismModifier){
//Cтатус запрета движений на HMI (0-Red, 1-Yellow, 2-Green, 3-Blue)
            Box(modifier = Modifier
                .padding(top = 15.dp, start = 10.dp)
                .size(height = 20.dp, width = 36.dp))
            { BasicLampMechanismsCondition(isLampStatus = trolleyRevStatus)}
            Box(modifier = Modifier
                .padding(top = 15.dp, start = 52.dp)
                .size(height = 20.dp, width = 36.dp))
            { BasicLampMechanismsCondition(isLampStatus = trolleyFwdStatus)}
//Cтатус разрешения / запрета выхода из полигона
            Box(modifier = Modifier
                .padding(top = 15.dp, start = 10.dp)
                .size(height = 20.dp, width = 36.dp))
            { BasicLampMechanismsPoligon(isLampStatus = trolleyRev, isVisibility =trolleyRev ) }
            Box(modifier = Modifier
                .padding(top = 15.dp, start = 52.dp)
                .size(height = 20.dp, width = 36.dp))
            { BasicLampMechanismsPoligon(isLampStatus = trolleyFwd, isVisibility =trolleyFwd )}

            Image (
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_cargo_trolley),
                contentDescription = "Тележка",
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .fillMaxSize())
        }
//Поворот. Состояние
        Box(myBoxMechanismModifier){
//Cтатус запрета движений на HMI (0-Red, 1-Yellow, 2-Green, 3-Blue)
            Box(modifier = Modifier
                .padding(top = 20.dp, start = 10.dp)
                .size(height = 30.dp, width = 30.dp))
            { BasicLampMechanismsCondition(isLampStatus = slewRightStatus,) }
            Box(modifier = Modifier
                .padding(top = 20.dp, start = 58.dp)
                .size(height = 30.dp, width = 30.dp))
            { BasicLampMechanismsCondition(isLampStatus = slewLeftStatus,) }
//Cтатус разрешения / запрета выхода из полигона
            Box(modifier = Modifier
                .padding(top = 20.dp, start = 10.dp)
                .size(height = 30.dp, width = 30.dp))
            { BasicLampMechanismsPoligon(isLampStatus = slewRight, isVisibility = slewRight) }
            Box(modifier = Modifier
                .padding(top = 20.dp, start = 58.dp)
                .size(height = 30.dp, width = 30.dp))
            { BasicLampMechanismsPoligon(isLampStatus = slewLeft, isVisibility = slewLeft) }

            Image (
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_home_tower_crane_boom),
                contentDescription = "Поворот",
                modifier = Modifier
                    .padding(horizontal = 10.dp, vertical = 10.dp)
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
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .fillMaxSize())
        }
//Ход крана. Состояние
        Box(myBoxMechanismModifier){
//Cтатус запрета движений на HMI (0-Red, 1-Yellow, 2-Green, 3-Blue)
            Box(modifier = Modifier
                .padding(top = 65.dp, start = 10.dp)
                .size(height = 20.dp, width = 36.dp))
            { BasicLampMechanismsCondition(isLampStatus = craneLeftStatus,)}
            Box(modifier = Modifier
                .padding(top = 65.dp, start = 52.dp)
                .size(height = 20.dp, width = 36.dp))
            { BasicLampMechanismsCondition(isLampStatus = cranRightStatus,)}
//Cтатус разрешения / запрета выхода из полигона
            Box(modifier = Modifier
                .padding(top = 65.dp, start = 10.dp)
                .size(height = 20.dp, width = 36.dp))
            { BasicLampMechanismsPoligon(isLampStatus = craneLeft, isVisibility =craneLeft )}
            Box(modifier = Modifier
                .padding(top = 65.dp, start = 52.dp)
                .size(height = 20.dp, width = 36.dp))
            { BasicLampMechanismsPoligon(isLampStatus = craneRight, isVisibility = craneRight )}

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

@Preview
@Composable
fun CraneMechanismsConditionPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        CraneMechanismsCondition(
            weightSensorErr = 0,
            trolleyFwdStatus = 1,
            trolleyRevStatus = 1,
            hoistUpStatus = 0,
            hoistDownStatus = 0,
            slewRightStatus = 2,
            slewLeftStatus = 2,
            cranRightStatus = 2,
            craneLeftStatus = 1,
            craneRight = 1,
            craneLeft = 0,
            slewLeft = 0,
            slewRight = 0,
            trolleyFwd = 0,
            trolleyRev = 0,
            hoistUp = 0,
            hoistDown = 0,
            anemometerSensorErr = 0,
            alarmLampStatus = 2
        )
    }
}