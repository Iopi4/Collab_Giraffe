package com.example.giraffenew.android_error_logger

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.concurrent.Volatile

class ErrorLogger
private constructor(
    private val context: Context)
{
    companion object {
        @Volatile
        private var INSTANCE: ErrorLogger? = null

        fun getInstance(context: Context): ErrorLogger =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: ErrorLogger(context.applicationContext).also { INSTANCE = it }
            }
    }


    private val logFileName: String = "error_log.txt"
    private val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())

    fun logException(context: Context, exception: Throwable){}







}