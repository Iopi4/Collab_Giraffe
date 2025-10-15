package com.example.giraffenew.android_error_logger.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.giraffenew.android_error_logger.domian.model.ExceptionEntity
import com.example.giraffenew.android_error_logger.domian.model.LogExceptionLevel
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme


@Composable
fun ExceptionRow(
    exception: ExceptionEntity,
    onItemClick:(ExceptionEntity)-> Unit = {}
){
    val backgroundColor = when (exception.level) {
        LogExceptionLevel.ERROR -> Color.Yellow.copy(alpha = 0.8f)
        LogExceptionLevel.FATAL -> Color.Red.copy(alpha = 0.8f)
    }

    val textColor = when (exception.level) {
        LogExceptionLevel.ERROR -> Color.Black
        LogExceptionLevel.FATAL -> Color.White
    }

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable() { onItemClick(exception) }
            .padding(2.dp)
            .border(1.dp, textColor.copy(alpha = 0.5f), RoundedCornerShape(2.dp))
            .padding(8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top
    ) {
        Column (modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "${exception.date} ${exception.time}",
                    color = textColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "PID: ${exception.pid} TID: ${exception.tid}",
                    color = textColor,
                    fontSize = 12.sp
                )
                Text(
                    text = exception.level.name,
                    color = textColor,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "[${exception.tag}] ${exception.message.take(1000)}${if (exception.message.length > 1000) "..." else ""}",
                color = textColor,
                fontSize = 15.sp,
                lineHeight = 16.sp,
                fontWeight = FontWeight.Bold// Межстрочный интервал
            )
        }
    }
}

@Preview
@Composable
fun ExceptionRowPreview(){
    GIRAFFEnewTheme (dynamicColor = false) {
        ExceptionRow(
            exception = ExceptionEntity(
                id = 1,
                timestamp = System.currentTimeMillis(),
                date = "2025-10-08",
                time = "16:29:49.712",
                pid = 123,
                tid = 456,
                tag = "empty",
                packageName = "empty",
                level = LogExceptionLevel.FATAL,
                message = "message",

            )
        )
    }
}