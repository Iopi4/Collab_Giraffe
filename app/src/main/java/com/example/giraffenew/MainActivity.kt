package com.example.giraffenew

import android.app.Activity
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.giraffenew.accident_and_warning_log.data.remoute.RegisterStatusDataSource
import com.example.giraffenew.kiosk.DeviceLockManager
import com.example.giraffenew.kiosk.KioskAccessibilityService
import com.example.giraffenew.navigate.SampleAppNavGraph
import com.example.giraffenew.services.ModbusBackgroundService
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var registerDataSource: RegisterStatusDataSource

    @Inject
    lateinit var deviceLockManager: DeviceLockManager

    private var isKioskModeEnabled by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация должна быть ДО setContent
        deviceLockManager.initialize()

        // Проверяем текущий статус прав администратора
        checkAdminStatus()

        val serviceIntent = Intent(this, ModbusBackgroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }

        enableEdgeToEdge()
        setContent {
            GIRAFFEnewTheme {
                SampleAppNavGraph(
                    onKioskModeToggle = { toggleKioskMode() },
                    isKioskModeEnabled = isKioskModeEnabled
                )
            }
        }
    }

    private fun checkAdminStatus() {
        val isAdminActive = deviceLockManager.isAdminActive()
        Log.d("MainActivity", "Device admin active: $isAdminActive")
        if (!isAdminActive) {
            Toast.makeText(this, "Для работы режима киоска нужны права администратора", Toast.LENGTH_LONG).show()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("MainActivity", "onActivityResult: requestCode=$requestCode, resultCode=$resultCode")

        when (requestCode) {
            DeviceLockManager.ADMIN_INTENT -> {
                if (resultCode == Activity.RESULT_OK) {
                    // Права администратора получены
                    Log.d("MainActivity", "Device admin permissions granted")
                    isKioskModeEnabled = true
                    deviceLockManager.applyKioskPolicies(this)
                    Toast.makeText(this, "Права администратора получены! Режим киоска активирован.", Toast.LENGTH_LONG).show()
                } else {
                    // Пользователь отказал в правах
                    Log.d("MainActivity", "Device admin permissions denied")
                    isKioskModeEnabled = false
                    Toast.makeText(this,
                        "Необходимы права администратора. " +
                                "Предоставьте права.",
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun checkAndRequestAccessibilityPermission() {
        val accessibilityEnabled = isAccessibilityServiceEnabled()
        if (!accessibilityEnabled) {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            Toast.makeText(this, "Включите службу доступности Giraffe для полной блокировки", Toast.LENGTH_LONG).show()
        }
    }

    private fun isAccessibilityServiceEnabled(): Boolean {
        val expectedServiceName = ComponentName(this, KioskAccessibilityService::class.java).flattenToString()
        val enabledServices = Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        ) ?: ""
        return enabledServices.contains(expectedServiceName)
    }

    private fun toggleKioskMode() {
        try {
            if (!deviceLockManager.isAdminActive()) {
                Log.d("MainActivity", "Admin not active, requesting permissions")
                val success = deviceLockManager.enableKioskMode(this)
                if (!success) {
                    Toast.makeText(this, "Запрос прав администратора отправлен", Toast.LENGTH_SHORT).show()
                }
                return
            }


            // Проверяем текущее состояние лаунчера
            val isGiraffeLauncherState = deviceLockManager.isGiraffeDefaultLauncher(this)
            val newState = !isGiraffeLauncherState

            // Сохраняем состояние в SharedPreferences для AccessibilityService
            val prefs = getSharedPreferences("kiosk_prefs", Context.MODE_PRIVATE)
            prefs.edit().putBoolean("kiosk_enabled",newState).apply()


            if (newState) {
                // Включаем киоск-режим - выбираем Girafee как лаунчер
                deviceLockManager.setGiraffeAsDefaultLauncher(this)
                deviceLockManager.applyKioskPolicies(this)
                isKioskModeEnabled = true
                checkAndRequestAccessibilityPermission()
                Toast.makeText(this, "Режим киоска активирован", Toast.LENGTH_LONG).show()
            } else {
                // Выключаем киоск-режим - выбираем стандартный лаунчер. даем доступ к настройкам
                deviceLockManager.clearDefaultLauncher(this)
                deviceLockManager.disableKioskMode(this)
                isKioskModeEnabled = false
                // Явно разрешаем системные настройки
                allowSystemSettingsAccess()

                Toast.makeText(this, "Выберите стандартный лаунчер (Home)", Toast.LENGTH_LONG).show()
            }

        } catch (e: Exception) {
            Log.e("MainActivity", "Error toggling kiosk mode: ${e.message}")
            Toast.makeText(this, "Ошибка: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun allowSystemSettingsAccess(){
        //Явно запускаем настройки, что бы пользователь мог выбрать лаунчер.
        try {
            val intent = Intent(Settings.ACTION_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception){
            Log.e("MainActivity", "Cannot open settings: ${e.message}")
        }

    }


    override fun onBackPressed() {
        if (isKioskModeEnabled) {
            Toast.makeText(this, "Доступ к Android ограничен", Toast.LENGTH_SHORT).show()
            return
        }
        super.onBackPressed()
    }

    private fun hideSystemUI() {
        if (isKioskModeEnabled) {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                            View.SYSTEM_UI_FLAG_FULLSCREEN or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )
        }
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
    }

    private fun isInForeground(): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningTasks = activityManager.getRunningTasks(1)
        return if (runningTasks.isNotEmpty()) {
            runningTasks[0].topActivity?.packageName == packageName
        } else {
            false
        }
    }

    private fun bringToForeground() {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        }
        startActivity(intent)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (isKioskModeEnabled) {
            when (keyCode) {
                KeyEvent.KEYCODE_VOLUME_UP,
                KeyEvent.KEYCODE_VOLUME_DOWN,
                KeyEvent.KEYCODE_RECENT_APPS,
                KeyEvent.KEYCODE_HOME,
                KeyEvent.KEYCODE_BACK,
                KeyEvent.KEYCODE_MENU
                    -> {
                    // Полностью блокируем системные кнопки
                    Toast.makeText(this, "Функция заблокирована", Toast.LENGTH_SHORT).show()
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && isKioskModeEnabled) {
            hideSystemUI()
            window.decorView.postDelayed({
                if (!isInForeground()) {
                    bringToForeground()
                }
            }, 1000)
        }
    }

    override fun onResume() {
        super.onResume()
        // Синхронизируем состояние с реальным положением дел
        isKioskModeEnabled = deviceLockManager.isGiraffeDefaultLauncher(this)

        // Обновляем состояние в SharedPreferences
        val prefs = getSharedPreferences("kiosk_prefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("kiosk_enabled", isKioskModeEnabled).apply()

        //Восстанавливаем режим киоска если нужно
        if (isKioskModeEnabled && deviceLockManager.isAdminActive()) {
            deviceLockManager.applyKioskPolicies(this)
            hideSystemUI()
        } else {
            // Гарантируем, что системные UI доступны при выключенном режиме
            showSystemUI()
        }
    }
    private fun showSystemUI(){
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                )
    }


    override fun onPause() {
        super.onPause()
        if (isKioskModeEnabled) {
            // В режиме киоска немедленно возвращаем фокус
            if (isKioskModeEnabled && !isInForeground()) {
                bringToForeground()
            }

        }
    }
}