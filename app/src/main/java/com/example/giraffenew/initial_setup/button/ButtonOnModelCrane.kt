package com.example.giraffenew.initial_setup.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.R

@Composable
fun ButtonOnModelCrane(
    modifier: Modifier = Modifier,
    text: Int,
    onClick: () -> Unit,
    craneId: Int?
) {
    Box(modifier = modifier
        .padding(start = 5.dp, end = 10.dp, top = 5.dp, bottom = 0.dp)
        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()){
        Button(
            modifier = Modifier
                .size(width = 50.dp, height = 30.dp),
            onClick = onClick,
            enabled = craneId != null,
            contentPadding = PaddingValues(2.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.Burgundy_violet),
                contentColor = Color.White),
        ) {
            Text(text = stringResource(id = text), fontSize = 19.sp, fontWeight = FontWeight.Bold)
        }
    }
}