package com.example.giraffenew.navigate.ui_kit

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.R

@Composable
fun ServiceNavigationButton(
    iconResId: Int,
    labelResId: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    isLocked: Boolean = false, // Добавляем параметр блокировки
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            )
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = iconResId),
            contentDescription = null,
            modifier = Modifier.padding(top = 15.dp, start = 5.dp)
        )
        NavigationDrawerItem(
            label = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(id = labelResId),
                        fontSize = 18.sp,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.weight(1f)
                    )
                    if (isLocked) {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_home_navigate_lock),
                            contentDescription = stringResource(R.string.menu_access_denied),
                            modifier = Modifier.padding(top = 5.dp, start = 5.dp),
                            alpha = 0.5f
                        )
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_home_navigate_lock),
                            contentDescription = stringResource(R.string.menu_access_denied),
                            modifier = Modifier.padding(top = 5.dp, start = 5.dp),
                            alpha = 0.5f
                        )
                    } else {
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_home_navigate_lock_open),
                            contentDescription = stringResource(R.string.menu_access_granted),
                            modifier = Modifier.padding(top = 5.dp, start = 5.dp),
                            alpha = 0.5f
                        )
                        Image(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_home_navigate_lock_open),
                            contentDescription = stringResource(R.string.menu_access_granted),
                            modifier = Modifier.padding(top = 5.dp, start = 5.dp),
                            alpha = 0.5f
                        )
                    }
                }
            },
            selected = isSelected,
            onClick = onClick,
            shape = RoundedCornerShape(5.dp),
            colors = NavigationDrawerItemDefaults.colors(
                unselectedContainerColor = Color.Transparent,
                unselectedTextColor = if (isLocked) {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                } else {
                    MaterialTheme.colorScheme.onSurface
                },
                unselectedIconColor = if (isLocked) {
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
        )
    }
}

