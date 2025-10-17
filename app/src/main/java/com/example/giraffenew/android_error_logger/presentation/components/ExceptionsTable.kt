package com.example.giraffenew.android_error_logger.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.android_error_logger.domian.model.ExceptionEntity


/**
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


 */
@Composable
fun ExceptionsTable(
    exceptions: List<ExceptionEntity>,
    title: String,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier,
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Заголовок
            Text(
                text = title,
                modifier = Modifier.padding(4.dp)
            )

            if (exceptions.isEmpty()) {
                // Сообщение при отсутствии данных
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Нет записей об ошибках")
                }
            } else {
                // Список исключений
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(exceptions) { exception ->
                        ExceptionRow(
                            exception = exception,
                            //isExpanded = expandedExceptionId == exception.id,
                            //onExpandToggle = {
                                //expandedExceptionId = if (expandedExceptionId == exception.id) null else exception.id
                            //}

                            //onItemClick = onExceptionClick
                        )
                        //VerticalDivider()
                    }
                }
            }
        }
    }
}