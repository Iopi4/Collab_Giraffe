package com.example.giraffenew.android_error_logger.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.android_error_logger.domian.model.ExceptionEntity


@Composable
fun ExceptionsTable(
    exceptions: List<ExceptionEntity>,
    title: String,
    modifier: Modifier = Modifier,
    onExceptionClick: (ExceptionEntity) -> Unit = {}
) {
    Column(modifier = modifier.fillMaxSize()) {
        // Заголовок
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(8.dp)
        )
        // Список исключений
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(exceptions) { exception ->
                ExceptionRow(
                    exception = exception,
                    onItemClick = onExceptionClick
                )
            }
        }
    }
}