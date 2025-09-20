package com.example.giraffenew.initial_setup.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.initial_setup.domain.CraneEntity
import com.example.giraffenew.R

@Composable
fun CraneSelectionDialog(
    cranes: List<CraneEntity>,
    onDismiss: () -> Unit,
    onCraneSelected: (CraneEntity) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Выберите модель крана", fontWeight = FontWeight.Bold) },
        text = {
            LazyColumn {
                items(cranes) { crane ->
                    Text(
                        text = crane.craneModel,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onCraneSelected(crane) }
                            .padding(16.dp),
                        fontSize = 16.sp
                    )
                    Divider()
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.Burgundy_violet),
                    contentColor = Color.White
                )
            ) {
                Text("Отмена")
            }
        }
    )
}