package com.example.giraffenew.home.ui.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.R
import com.example.giraffenew.home.ui.scale.ScaleVerticalSound
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun BottomIconSound(
    currentVolume: Float,
    onClick: () -> Unit,
    modifier: Modifier
) {
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.width(50.dp)) {
            Box(
                modifier = Modifier.background(colorResource(R.color.Burgundy_violet)),
                contentAlignment = Alignment.TopEnd
            ) {
                Button(
                    modifier = Modifier.size(width = 40.dp, height = 40.dp),
                    onClick = onClick,
                    contentPadding = PaddingValues(1.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(R.color.Burgundy_violet),
                        contentColor = Color.White
                    ),
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_home_info_campaign),
                        contentDescription = "Регулировка громкости",
                        modifier = Modifier.size(40.dp),
                        tint = colorResource(R.color.white)
                    )
                }
            }

            ScaleVerticalSound(
                modifier = modifier,
                currentVolume = currentVolume,
            )
        }
    }
}



@Preview
@Composable
fun BottomIconSoundPreview (){
    GIRAFFEnewTheme (dynamicColor = false) {
        BottomIconSound(
            currentVolume = 0.9f,
            onClick = {},
            modifier = Modifier.size(width = 12.dp, height = 40.dp)
        )
    }
}