package com.example.giraffenew.android_error_logger

import android.content.Context
import android.os.Environment
import kotlinx.io.IOException
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter
import java.io.PrintWriter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Singleton
import kotlin.concurrent.Volatile


object ErrorLogger {

    private const val logFileName: String = "error_log.txt"
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    fun logException(context: Context, exception: Throwable) {
        try {
            val logFile = File(getLogsDirectory(context), logFileName)
            FileOutputStream(logFile, true).use { stream ->
                val timestamp = dateFormat.format(Date())
                val writer = PrintWriter(OutputStreamWriter(stream, "UTF-8"))
                writer.apply {
                    appendLine("$timestamp: ${exception.message}")
                    appendLine("Stack trace:")
                    exception.printStackTrace(this)
                    appendLine("----------------------------------------")
                    flush()
                    close()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun getLogsDirectory(context: Context): File {
        val directory = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
            File(context.getExternalFilesDir(null), "Logs")
        } else {
            File(context.filesDir, "Logs")
        }
        if (!directory.exists()) directory.mkdirs()
        return directory
    }

    fun getLogFile(context: Context): File = File(getLogsDirectory(context), logFileName)
}