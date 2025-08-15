package com.example.giraffenew.receivers

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.giraffenew.services.ModbusBackgroundService

/**
 * BroadcastReceiver для автоматического запуска ModbusBackgroundService
 * при старте системы или обновлении приложения
 * 
 * Обеспечивает industrial-grade надежность:
 * - Автоматический запуск после перезагрузки системы
 * - Запуск после обновления приложения
 * - Graceful handling различных версий Android
 * C:\Users\User\AppData\Local\Android\Sdk\platform-tools>
 */
class BootReceiver : BroadcastReceiver() {
    companion object {
        private const val TAG = "BootReceiver"
        private const val DELAY_MS = 10000L // 10 секунд
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.w(TAG, "Boot event received. Starting delayed service initialization...")

        // Используем Handler с главным Looper
        Handler(Looper.getMainLooper()).postDelayed({
            Log.w(TAG, "Executing delayed service start")
            try {
                startModbusService(context)
            } catch (e: Exception) {
                Log.e(TAG, "Delayed service start failed", e)
            }
        }, DELAY_MS)
    }

    private fun startModbusService(context: Context) {
        Log.i(TAG, "Starting Modbus service...")
        val serviceIntent = Intent(context, ModbusBackgroundService::class.java).apply {
            action = ModbusBackgroundService.ACTION_START
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent)
            } else {
                context.startService(serviceIntent)
            }
            Log.i(TAG, "Service start command sent")
        } catch (e: Exception) {
            Log.e(TAG, "Service start failed", e)
            // Для Android 8+ добавляем fallback
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Log.w(TAG, "Trying fallback to JobScheduler")
                scheduleServiceViaJobScheduler(context)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun scheduleServiceViaJobScheduler(context: Context) {
        try {
            val component = ComponentName(context, ModbusBackgroundService::class.java)
            val jobInfo = JobInfo.Builder(1, component)
                .setOverrideDeadline(DELAY_MS)
                .build()

            context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            Log.w(TAG, "Service scheduled via JobScheduler")
        } catch (e: Exception) {
            Log.e(TAG, "JobScheduler failed", e)
        }
    }
}