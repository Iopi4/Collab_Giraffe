package com.example.giraffenew.android_error_logger.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.giraffenew.android_error_logger.domian.model.LogExceptionLevel
import com.example.giraffenew.android_error_logger.presentation.components.ExceptionsTable
import com.example.giraffenew.android_error_logger.presentation.viewmodel.ExceptionsViewModel

@Composable
fun ExceptionsScreen(
    exceptionsViewModel: ExceptionsViewModel = hiltViewModel(),
) {

    val exceptions by exceptionsViewModel.exceptions.collectAsState()
    val loading by exceptionsViewModel.loading.collectAsState()
    val selectedLevel by exceptionsViewModel.selectedLevel.collectAsState()
    val context = LocalContext.current

    val diagnostics by exceptionsViewModel.diagnostics.collectAsState()
    val memoryUsage by exceptionsViewModel.memoryUsage.collectAsState()

    var showClearDialog by remember { mutableStateOf(false) }

    // Активные операции в реальном времени
    val activeOperations by exceptionsViewModel.activeOperation.collectAsState()

    //Обновим данные при первом открытии экрана
    LaunchedEffect(Unit) {
        exceptionsViewModel.refreshData()
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        Text("Database Diagnostics",)
        Spacer(modifier = Modifier.height(5.dp))
        Box () {

            Column(modifier = Modifier.padding(16.dp)) {
                Text("Repository Status", style = MaterialTheme.typography.bodyMedium)
                Text("Active operations: $activeOperations") // активные операции
                Text("Total exceptions: ${diagnostics?.totalExceptions ?: 0}")

                Spacer(modifier = Modifier.height(5.dp))

                Text("Memory Usage", style = MaterialTheme.typography.bodyMedium)
                Text("Used: ${memoryUsage.usedMemoryMB} MB")
                Text("Max: ${memoryUsage.maxMemoryMB} MB")
                Text("Usage: ${"%.1f".format(memoryUsage.usedMemoryMB.toDouble() / memoryUsage.maxMemoryMB.toDouble() * 100)}%")
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        Button (
            onClick = { exceptionsViewModel.updateDiagnostics() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Refresh Diagnostics")
        }


        // Фильтры
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            FilterChip(
                selected = selectedLevel == null,
                onClick = { exceptionsViewModel.setFilter(null) },
                label = { Text("Все") }
            )
            FilterChip(
                selected = selectedLevel == LogExceptionLevel.ERROR,
                onClick = { exceptionsViewModel.setFilter(LogExceptionLevel.ERROR) },
                label = { Text("ERROR") }
            )
            FilterChip(
                selected = selectedLevel == LogExceptionLevel.FATAL,
                onClick = { exceptionsViewModel.setFilter(LogExceptionLevel.FATAL) },
                label = { Text("FATAL") }
            )
            Button(onClick = { showClearDialog = true }) {
                Text("Очистить логи")
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        // Состояние загрузки
        if (loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            // Таблица исключений
            ExceptionsTable(
                exceptions = exceptions,
                title = "Всего ошибок (${exceptions.size})",
                modifier = Modifier.weight(1f)
            )
        }
    }

    // Диалог очистки
    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("Очистка логов") },
            text = { Text("Вы уверены, что хотите очистить все логи ошибок?") },
            confirmButton = {
                TextButton (
                    onClick = {
                        exceptionsViewModel.clearAllExceptions()
                        showClearDialog = false
                    }
                ) {
                    Text("Очистить")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showClearDialog = false }
                ) {
                    Text("Отмена")
                }
            }
        )
    }
}