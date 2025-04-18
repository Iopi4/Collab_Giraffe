package com.example.giraffenew.navigate.ui_kit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.R


@Composable
fun NavigationButton(
    iconResId: Int,
    labelResId: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row {
        Image(
            imageVector = ImageVector.vectorResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.padding(top = 15.dp, start = 5.dp)
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = stringResource(id = labelResId),
                    fontSize = 18.sp,
                    style = MaterialTheme.typography.labelLarge
                )
            },
            selected = isSelected,
            onClick = onClick,
            shape = RoundedCornerShape(5.dp)
        )
    }
}