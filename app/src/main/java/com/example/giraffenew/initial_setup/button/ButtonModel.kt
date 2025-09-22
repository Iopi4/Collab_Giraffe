package com.example.giraffenew.initial_setup.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.R


@Composable
fun ButtonModel(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String = stringResource(R.string.InitialSetup_Button_Model),
    ) {

    Box(modifier = modifier

        .clip(shape = RoundedCornerShape(5.dp))
        .fillMaxWidth()){
        Button(
            modifier = Modifier
                .size(width = 400.dp, height = 30.dp),
            onClick = onClick,
            contentPadding = PaddingValues(2.dp),
            shape = RoundedCornerShape(5.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorResource(R.color.Burgundy_violet),
                contentColor = Color.White),
        ) {
            Text(
                text = text,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = 16.sp)
        }
    }
}
