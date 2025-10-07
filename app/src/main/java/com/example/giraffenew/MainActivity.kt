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
import androidx.lifecycle.lifecycleScope
import com.example.giraffenew.accident_and_warning_log.data.remoute.RegisterStatusDataSource
import com.example.giraffenew.kiosk.DeviceLockManager
import com.example.giraffenew.kiosk.KioskAccessibilityService
import com.example.giraffenew.navigate.SampleAppNavGraph
import com.example.giraffenew.services.ModbusBackgroundService
import com.example.giraffenew.ui.theme.GIRAFFEnewTheme
import dagger.hilt.android.AndroidEntryPoint
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
1. Оптимизация onCreate()
UI инициализируется сразу через setContent()

Тяжелые операции вынесены в initializeHeavyComponents() корутину

2. Использование корутин с Dispatchers.IO
deviceLockManager.initialize() - в фоновом потоке

deviceLockManager.isAdminActive() - в фоновом потоке

deviceLockManager.isGiraffeDefaultLauncher() - в фоновом потоке

applyKioskPoliciesInBackground() - отдельная suspend функция

3. Безопасное обновление UI
Все обновления UI через withContext(Dispatchers.Main)

Обработка ошибок в корутинах

4. Оптимизация onResume()
Проверка состояния лаунчера в фоне

Плавное обновление UI после получения данных

5. Улучшенная обработка toggleKioskMode()
Вся логика переключения в корутине

Разделение фоновых операций и UI обновлений
 */



@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var registerDataSource: RegisterStatusDataSource

    @Inject
    lateinit var deviceLockManager: DeviceLockManager

    private var isKioskModeEnabled by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            GIRAFFEnewTheme {
                SampleAppNavGraph(
                    onKioskModeToggle = { toggleKioskMode() },
                    isKioskModeEnabled = isKioskModeEnabled
                )
            }
        }

        // Тяжелые операции в корутинах
        lifecycleScope.launch {
            initializeHeavyComponents()
        }
    }
    private suspend fun initializeHeavyComponents() {
        try {
            // Инициализация DeviceLockManager в IO dispatcher
            withContext(Dispatchers.IO) {
                deviceLockManager.initialize()
            }

            // Проверка статуса администратора в IO dispatcher
            val isAdminActive = withContext(Dispatchers.IO) {
                deviceLockManager.isAdminActive()
            }

            // Обновление UI в главном потоке
            withContext(Dispatchers.Main) {
                Log.d("MainActivity", "Device admin active: $isAdminActive")
                if (!isAdminActive) {
                    Toast.makeText(
                        this@MainActivity,
                        "Для работы режима киоска нужны права администратора",
                        Toast.LENGTH_LONG
                    ).show()
                }

                // Запуск сервиса после инициализации
                startModbusService()
            }

        } catch (e: Exception) {
            Log.e("MainActivity", "Error initializing heavy components: ${e.message}")
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@MainActivity,
                    "Ошибка инициализации приложения",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun startModbusService() {
        val serviceIntent = Intent(this, ModbusBackgroundService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
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

                    // Применяем политики в корутине
                    lifecycleScope.launch {
                        applyKioskPoliciesInBackground()
                    }

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

    private suspend fun applyKioskPoliciesInBackground() {
        withContext(Dispatchers.IO) {
            try {
                deviceLockManager.applyKioskPolicies(this@MainActivity)
            } catch (e: Exception) {
                Log.e("MainActivity", "Error applying kiosk policies: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@MainActivity,
                        "Ошибка применения политик киоска",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun checkAndRequestAccessibilityPermission() {
        lifecycleScope.launch {
            val accessibilityEnabled = withContext(Dispatchers.IO) {
                isAccessibilityServiceEnabled()
            }

            if (!accessibilityEnabled) {
                withContext(Dispatchers.Main) {
                    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    Toast.makeText(
                        this@MainActivity,
                        "Включите службу доступности Giraffe для полной блокировки",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
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
        lifecycleScope.launch {
            try {
                if (!deviceLockManager.isAdminActive()) {
                    Log.d("MainActivity", "Admin not active, requesting permissions")
                    val success = deviceLockManager.enableKioskMode(this@MainActivity)
                    if (!success) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(
                                this@MainActivity,
                                "Запрос прав администратора отправлен",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    return@launch
                }

                // Проверяем текущее состояние лаунчера в фоне
                val isGiraffeLauncherState = withContext(Dispatchers.IO) {
                    deviceLockManager.isGiraffeDefaultLauncher(this@MainActivity)
                }
                val newState = !isGiraffeLauncherState

                // Сохраняем состояние в SharedPreferences для AccessibilityService
                withContext(Dispatchers.IO) {
                    val prefs = getSharedPreferences("kiosk_prefs", Context.MODE_PRIVATE)
                    prefs.edit().putBoolean("kiosk_enabled", newState).apply()
                }

                withContext(Dispatchers.Main) {
                    if (newState) {
                        // Включаем киоск-режим - выбираем Girafee как лаунчер
                        deviceLockManager.setGiraffeAsDefaultLauncher(this@MainActivity)
                        lifecycleScope.launch {
                            applyKioskPoliciesInBackground()
                        }
                        isKioskModeEnabled = true
                        checkAndRequestAccessibilityPermission()
                        Toast.makeText(
                            this@MainActivity,
                            "Режим киоска активирован",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        // Выключаем киоск-режим - выбираем стандартный лаунчер. даем доступ к настройкам
                        deviceLockManager.clearDefaultLauncher(this@MainActivity)
                        deviceLockManager.disableKioskMode(this@MainActivity)
                        isKioskModeEnabled = false
                        // Явно разрешаем системные настройки
                        allowSystemSettingsAccess()

                        Toast.makeText(
                            this@MainActivity,
                            "Выберите стандартный лаунчер (Home)",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            } catch (e: Exception) {
                Log.e("MainActivity", "Error toggling kiosk mode: ${e.message}")
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@MainActivity,
                        "Ошибка: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun allowSystemSettingsAccess() {
        // Явно запускаем настройки, чтобы пользователь мог выбрать лаунчер.
        try {
            val intent = Intent(Settings.ACTION_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        } catch (e: Exception) {
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

        // Проверка состояния киоск-режима в фоне
        lifecycleScope.launch {
            val isGiraffeLauncher = withContext(Dispatchers.IO) {
                deviceLockManager.isGiraffeDefaultLauncher(this@MainActivity)
            }

            withContext(Dispatchers.Main) {
                isKioskModeEnabled = isGiraffeLauncher

                // Обновляем состояние в SharedPreferences
                val prefs = getSharedPreferences("kiosk_prefs", Context.MODE_PRIVATE)
                prefs.edit().putBoolean("kiosk_enabled", isKioskModeEnabled).apply()

                // Восстанавливаем режим киоска если нужно
                if (isKioskModeEnabled && deviceLockManager.isAdminActive()) {
                    lifecycleScope.launch {
                        applyKioskPoliciesInBackground()
                    }
                    hideSystemUI()
                } else {
                    // Гарантируем, что системные UI доступны при выключенном режиме
                    showSystemUI()
                }
            }
        }
    }

    private fun showSystemUI() {
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