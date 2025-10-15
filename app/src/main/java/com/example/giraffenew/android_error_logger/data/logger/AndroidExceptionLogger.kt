package com.example.giraffenew.android_error_logger.data.logger

import android.util.Log
import com.example.giraffenew.android_error_logger.domian.model.ExceptionEntity
import com.example.giraffenew.android_error_logger.domian.model.LogExceptionLevel
import com.example.giraffenew.android_error_logger.domian.usecase.LogExceptionUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AndroidExceptionLogger
@Inject constructor(
    private val logExceptionUseCase: LogExceptionUseCase
){
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val timeFormat = SimpleDateFormat("HH:mm:ss.SSS", Locale.getDefault())

    fun logError(tag: String, message: String, throwable: Throwable? = null) {
        log(LogExceptionLevel.ERROR, tag, message, throwable)
    }

    fun logFatal(tag: String, message: String, throwable: Throwable? = null) {
        log(LogExceptionLevel.FATAL, tag, message, throwable)
    }

    private fun log(level: LogExceptionLevel, tag: String, message: String, throwable: Throwable?) {
        // Сначала логируем в стандартный LogCat
        when (level) {
            LogExceptionLevel.ERROR -> Log.e(tag, message, throwable)
            LogExceptionLevel.FATAL -> Log.wtf(tag, message, throwable)
        }

        // Затем сохраняем в базу данных
        val fullMessage = if (throwable != null) {
            "$message\n${throwable.stackTraceToString()}"
        } else {
            message
        }

        val currentTime = System.currentTimeMillis()
        val exceptionEntity = ExceptionEntity(
            timestamp = currentTime,
            date = dateFormat.format(Date(currentTime)),
            time = timeFormat.format(Date(currentTime)),
            pid = android.os.Process.myPid(),
            tid = android.os.Process.myTid(),
            tag = tag,
            packageName = "com.example.giraffenew", // или получать динамически
            level = level,
            message = fullMessage
        )

        // Сохраняем в базу в фоновом потоке
        CoroutineScope(Dispatchers.IO).launch {
            try {
                logExceptionUseCase(exceptionEntity)
            } catch (e: Exception) {
                Log.e("ExceptionLogger", "Failed to save exception to database", e)
            }
        }
    }

    // Глобальный обработчик неперехваченных исключений
    fun setupGlobalExceptionHandler() {
        val defaultHandler = Thread.getDefaultUncaughtExceptionHandler()

        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            logFatal(
                "UncaughtException",
                "Uncaught exception in thread ${thread.name}",
                throwable
            )

            // Передаем дальше стандартному обработчику
            defaultHandler?.uncaughtException(thread, throwable)
        }
    }

}