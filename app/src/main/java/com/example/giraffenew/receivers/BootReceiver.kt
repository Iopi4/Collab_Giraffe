package com.example.giraffenew.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.giraffenew.services.ModbusBackgroundService

/**
 * BroadcastReceiver для автоматического запуска ModbusBackgroundService
 * при старте системы или обновлении приложения
 * 
 * Обеспечивает industrial-grade надежность:
 * - Автоматический запуск после перезагрузки системы
 * - Запуск после обновления приложения
 * - Graceful handling различных версий Android
 */
class BootReceiver : BroadcastReceiver() {
    
    companion object {
        private const val TAG = "BootReceiver"
    }
    
    override fun onReceive(context: Context, intent: Intent) {
        Log.i(TAG, "Boot receiver triggered with action: ${intent.action}")
        
        when (intent.action) {
            Intent.ACTION_BOOT_COMPLETED -> {
                Log.i(TAG, "System boot completed - starting Modbus service")
                startModbusService(context)
            }
            Intent.ACTION_MY_PACKAGE_REPLACED,
            Intent.ACTION_PACKAGE_REPLACED -> {
                Log.i(TAG, "Package updated - restarting Modbus service")
                startModbusService(context)
            }
            else -> {
                Log.w(TAG, "Unknown action: ${intent.action}")
            }
        }
    }
    
    /**
     * Запустить ModbusBackgroundService с учетом версии Android
     */
    private fun startModbusService(context: Context) {
        try {
            val serviceIntent = Intent(context, ModbusBackgroundService::class.java).apply {
                action = ModbusBackgroundService.ACTION_START
            }
            
            // Для Android O+ используем startForegroundService
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(serviceIntent)
            } else {
                context.startService(serviceIntent)
            }
            
            Log.i(TAG, "ModbusBackgroundService start requested")
            
        } catch (e: Exception) {
            Log.e(TAG, "Failed to start ModbusBackgroundService", e)
        }
    }
}