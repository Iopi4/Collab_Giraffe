package com.example.giraffenew.accident_and_warning_log.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.giraffenew.core.ui_kit.composable.button.BasicButtonGiraffe
import com.example.giraffenew.R
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme

@Composable
fun ClearArchiveDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.5f))
            .clickable(onClick = onDismiss),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .width(300.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(10.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Кнопка закрытия
                IconButton (
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Icon(Icons.Default.Clear, contentDescription = "Close")
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Текст подтверждения
                Text(
                    text = stringResource(R.string.Alarm_ArchiveEvents_Resetting_Archived_Events),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Кнопка подтверждения
                BasicButtonGiraffe (
                    onClick = onConfirm,
                    text = R.string.Button_Resetting_Archived_Events,
                    modifier = Modifier.fillMaxWidth()
                ) {
                }
            }
        }
    }
}

@Preview
@Composable
fun ClearArchiveDialogPreview (){
    GIRAFFEnewTheme(dynamicColor = false) {
        ClearArchiveDialog(
            onConfirm = { },
            onDismiss = {}
        )
    }
}