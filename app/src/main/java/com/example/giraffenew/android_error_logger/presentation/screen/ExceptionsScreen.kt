package com.example.giraffenew.android_error_logger.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.giraffenew.android_error_logger.domian.model.LogExceptionLevel
import com.example.giraffenew.android_error_logger.presentation.components.ExceptionsTable
import com.example.giraffenew.android_error_logger.presentation.viewmodel.ExceptionsViewModel
import com.example.giraffenew.database_monitoring.AppActivityMonitor

@Composable
fun ExceptionsScreen(
    exceptionsViewModel: ExceptionsViewModel = hiltViewModel(),
) {

    val exceptions by exceptionsViewModel.exceptions.collectAsState()
    val loading by exceptionsViewModel.loading.collectAsState()
    val selectedLevel by exceptionsViewModel.selectedLevel.collectAsState()

    // Мониторинг активности
    val diagnostics by exceptionsViewModel.diagnostics.collectAsState()
    val memoryUsage by exceptionsViewModel.memoryUsage.collectAsState()

    var showClearDialog by remember { mutableStateOf(false) }

    // Активные операции в реальном времени
    val activeOperations by exceptionsViewModel.activeOperation.collectAsState()

    // Мониторинг активности
    val globalOperations by exceptionsViewModel.globalActiveOperations.collectAsState()
    val methodStats by exceptionsViewModel.methodStatsFlow.collectAsState()
    val memoryStats by exceptionsViewModel.memoryStats.collectAsState()

    var expandedSections by remember { mutableStateOf(setOf<String>()) }

    //Обновим данные при первом открытии экрана
    LaunchedEffect(Unit) {
        exceptionsViewModel.refreshData()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
    ) {
        // Детальный мониторинг DAO операций
        DetailedDaoMonitoringSection(
            globalOperations = globalOperations,
            methodStats = methodStats,
            memoryStats = memoryStats,
            expandedSections = expandedSections,
            onSectionToggle = { section ->
                expandedSections = if (expandedSections.contains(section)) {
                    expandedSections - section
                } else {
                    expandedSections + section
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Существующие фильтры и таблица
        Row(
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

        Spacer(modifier = Modifier.height(8.dp))

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
            ExceptionsTable(
                exceptions = exceptions,
                title = "Логи ошибок (${exceptions.size})",
                modifier = Modifier.weight(1f)
            )
        }
    }

    if (showClearDialog) {
        AlertDialog(
            onDismissRequest = { showClearDialog = false },
            title = { Text("Очистка логов") },
            text = { Text("Вы уверены, что хотите очистить все логи ошибок?") },
            confirmButton = {
                TextButton(
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

@Composable
fun DetailedDaoMonitoringSection(
    globalOperations: Int,
    methodStats: Map<String, AppActivityMonitor.MethodStats>,
    memoryStats: AppActivityMonitor.MemoryStats,
    expandedSections: Set<String>,
    onSectionToggle: (String) -> Unit
) {
    val criticalMethods = methodStats.values.filter {
        it.activeCalls > 0 || it.errorCount > 0
    }.sortedByDescending { it.activeCalls }

    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Заголовок с общей информацией
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text("DAO Operations Monitor", style = MaterialTheme.typography.titleSmall)
                    Text("Active: $globalOperations ops", color = Color.Gray, fontSize = 12.sp)
                }
                IconButton(
                    onClick = { onSectionToggle("main") },
                    modifier = Modifier.size(25.dp)
                ) {
                    Icon(
                        imageVector = if (expandedSections.contains("main"))
                            Icons.Default.Menu else Icons.Default.Face,
                        contentDescription = "Toggle details"
                    )
                }
            }

            // Основные метрики
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MetricItem(
                    title = "Memory",
                    value = "${memoryStats.usedMemoryMB}MB",
                    subtitle = "of ${memoryStats.maxMemoryMB}MB",
                    color = if (memoryStats.usedMemoryMB > memoryStats.maxMemoryMB * 0.8) Color.Red else Color.Green
                )
                MetricItem(
                    title = "Active Ops",
                    value = globalOperations.toString(),
                    subtitle = "operations",
                    color = if (globalOperations > 10) Color.Red else Color.Green
                )
                MetricItem(
                    title = "DB Memory",
                    value = "${memoryStats.databaseMemoryMB}MB",
                    subtitle = "estimated",
                    color = Color.Blue
                )
            }

            // Детальная информация
            if (expandedSections.contains("main")) {
                Spacer(modifier = Modifier.height(8.dp))
                Divider()
                Spacer(modifier = Modifier.height(8.dp))

                // Критические методы
                if (criticalMethods.isNotEmpty()) {
                    Text("Critical Methods:", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(4.dp))

                    criticalMethods.forEach { stats ->
                        CriticalMethodItem(stats = stats)
                    }
                }

                // Все методы (свернуто)
                if (methodStats.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    val showAllMethods = expandedSections.contains("all_methods")

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("All Methods (${methodStats.size})", style = MaterialTheme.typography.bodyMedium)
                        IconButton(
                            onClick = { onSectionToggle("all_methods") },
                            modifier = Modifier.size(20.dp)
                        ) {
                            Icon(
                                imageVector = if (showAllMethods)
                                    Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                                contentDescription = "Toggle all methods"
                            )
                        }
                    }

                    if (showAllMethods) {
                        Spacer(modifier = Modifier.height(4.dp))
                        methodStats.values.sortedByDescending { it.callCount }.forEach { stats ->
                            MethodStatsItem(stats = stats)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MetricItem(title: String, value: String, subtitle: String, color: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(value, color = color, fontWeight = FontWeight.Bold)
        Text(title, fontSize = 10.sp, color = Color.Gray)
        Text(subtitle, fontSize = 8.sp, color = Color.Gray)
    }
}

@Composable
fun CriticalMethodItem(stats: AppActivityMonitor.MethodStats) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (stats.activeCalls > 0) Color(0xFFFFEBEE) else Color(0xFFFFF3E0)
        )
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    stats.methodName,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "Calls: ${stats.callCount} | Active: ${stats.activeCalls} | Errors: ${stats.errorCount}",
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            }
            if (stats.activeCalls > 0) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(Color.Red, CircleShape)
                )
            }
        }
    }
}

@Composable
fun MethodStatsItem(stats: AppActivityMonitor.MethodStats) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            stats.methodName,
            fontSize = 11.sp,
            modifier = Modifier.weight(3f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text("${stats.callCount}", fontSize = 11.sp, modifier = Modifier.weight(1f))
        Text("${stats.averageExecutionTime}ms", fontSize = 11.sp, modifier = Modifier.weight(1f))
        Text("${stats.errorCount}", fontSize = 11.sp, modifier = Modifier.weight(1f))
    }
}