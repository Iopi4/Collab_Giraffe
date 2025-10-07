package com.example.giraffenew.kiosk

import android.annotation.TargetApi
import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import javax.inject.Inject
import javax.inject.Singleton

/**
class Context в Kotlin для Android Studio — это абстрактный класс, который представляет текущее состояние приложения
и обеспечивает доступ к различным ресурсам и сервисам.
Цель класса — соединить код приложения с системой Android, предоставить доступ к ресурсам и функциям,
доступным только системе. Через классы, которые наследуются от Context (активности, службы, приложение),
приложение получает возможность получать доступ к ресурсам и функциям, доступным только системе.

DeviceLockManager:
Управление правами администратора, блокировкой задач, установкой лаунчера.

 - applyKioskPolicies:
активирует блокировку задачи (Lock Task Mode), устанавливает флаги окна,
отключает кнопку недавних приложений.

 - setGiraffeAsDefaultLauncher и clearDefaultLauncher: показывают диалог выбора лаунчера.
 */


@Singleton
class DeviceLockManager @Inject constructor(
    private val context: Context
) {
    private var isLockEnabled = false
    private lateinit var devicePolicyManager: DevicePolicyManager
    private lateinit var adminComponent: ComponentName

    companion object {
        const val ADMIN_INTENT = 123
    }

    fun initialize() {
        devicePolicyManager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        adminComponent = ComponentName(context, DeviceAdminReceiver::class.java)
    }

    fun isAdminActive(): Boolean {
        return try {
            devicePolicyManager.isAdminActive(adminComponent)
        } catch (e: Exception) {
            false
        }
    }

    fun enableKioskMode(activity: Activity): Boolean {
        if (!::devicePolicyManager.isInitialized || !::adminComponent.isInitialized) {
            initialize()
        }

        // Проверяем, есть ли уже права администратора
        if (!isAdminActive()) {
            Log.d("DeviceLockManager", "Requesting device admin permissions")
            requestAdminPermissions(activity)
            return false // Возвращаем false, так как права еще не получены
        }

        try {
            isLockEnabled = true
            applyKioskPolicies(activity)
            Log.d("DeviceLockManager", "Kiosk mode enabled successfully")
            return true
        } catch (e: SecurityException) {
            Log.e("DeviceLockManager", "Security exception: ${e.message}")
            Toast.makeText(activity, "Ошибка безопасности: ${e.message}", Toast.LENGTH_LONG).show()
            return false
        }
    }

    private fun requestAdminPermissions(activity: Activity) {
        try {
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, adminComponent)
            intent.putExtra(
                DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "Для активации режима киоска необходимы права администратора устройства. Это позволит заблокировать устройство для безопасной работы."
            )
            activity.startActivityForResult(intent, ADMIN_INTENT)
        } catch (e: Exception) {
            Log.e("DeviceLockManager", "Error requesting admin permissions: ${e.message}")
            Toast.makeText(activity, "Ошибка запроса прав администратора", Toast.LENGTH_LONG).show()
        }
    }


    fun disableKioskMode(activity: Activity) {
        try {
            isLockEnabled = false
            removeKioskPolicies(activity)
            Log.d("DeviceLockManager", "Kiosk mode disabled")
        } catch (e: Exception) {
            Log.e("DeviceLockManager", "Error disabling kiosk mode: ${e.message}")
        }
    }

    fun isKioskModeEnabled(): Boolean = isLockEnabled

    fun isProfileOwner(): Boolean {
        return devicePolicyManager.isProfileOwnerApp(adminComponent.packageName)
    }

    fun applyKioskPolicies(activity: Activity) {
        if (!isProfileOwner()) {
            Log.e("DeviceLockManager", "App is not profile owner - cannot use Lock Task")
            // Альтернативные методы блокировки через AccessibilityService
            return
        }

        try {
            if (isAdminActive()) {
                Log.d("DeviceLockManager", "Applying kiosk policies")

                // 1. Активируем Lock Task Mode
                activateLockTaskMode(activity)

                // 2. Устанавливаем флаги окна
                setWindowFlags(activity)

                // 3. Блокируем системные кнопки
                disableRecentAppsButton(activity)

                Log.d("DeviceLockManager", "Kiosk policies applied successfully")
            } else {
                Log.w("DeviceLockManager", "Cannot apply policies: admin not active")
            }
        } catch (e: Exception) {
            Log.e("DeviceLockManager", "Error applying kiosk policies: ${e.message}")
        }
    }

    private fun blockRecentApps(activity: Activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        }
    }

    private fun activateLockTaskMode(activity: Activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Останавливаем предыдущую блокировку если была активна
                if (isLockTaskModeActive(activity)) {
                    activity.stopLockTask()
                }

                // Устанавливаем пакеты для блокировки
                devicePolicyManager.setLockTaskPackages(adminComponent, arrayOf(activity.packageName))

                // Запускаем блокировку
                activity.startLockTask()

                Log.d("DeviceLockManager", "Lock task mode activated")
            }
        } catch (e: Exception) {
            Log.e("DeviceLockManager", "Error activating lock task: ${e.message}")
            throw e
        }
    }

    private fun isLockTaskModeActive(activity: Activity): Boolean {
        return try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                // Пытаемся остановить - если не было исключения, значит режим был активен
                activity.stopLockTask()
                true
            } else {
                false
            }
        } catch (e: SecurityException) {
            // SecurityException означает, что Lock Task mode не был активен
            false
        } catch (e: Exception) {
            // Другие исключения также трактуем как неактивный режим
            Log.e("DeviceLockManager", "Error checking lock task: ${e.message}")
            false
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun disableRecentAppsFeature() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                devicePolicyManager.setLockTaskFeatures(
                    adminComponent,
                    DevicePolicyManager.LOCK_TASK_FEATURE_SYSTEM_INFO or
                            DevicePolicyManager.LOCK_TASK_FEATURE_KEYGUARD
                    // Исключаем LOCK_TASK_FEATURE_OVERVIEW чтобы заблокировать Recent Apps
                )
                Log.d("DeviceLockManager", "Recent Apps button disabled via LockTask features")
            }
        } catch (e: Exception) {
            Log.e("DeviceLockManager", "Error disabling Recent Apps feature: ${e.message}")
        }
    }

    private fun disableRecentAppsButton(activity: Activity) {
        try {
            // Метод 1: Через системные UI флаги (работает на большинстве устройств)
            activity.window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION )
            (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY )
            (View.SYSTEM_UI_FLAG_FULLSCREEN )
            (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION )
            (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

            // Метод 2: Дополнительные флаги окна
            activity.window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
            activity.window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)

            Log.d("DeviceLockManager", "Recent Apps button disabled via UI flags")
        } catch (e: Exception) {
            Log.e("DeviceLockManager", "Error disabling Recent Apps button: ${e.message}")
        }
    }

    private fun setWindowFlags(activity: Activity) {
        try {
            val window = activity.window
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            window.addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
            window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
            window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)

            // Дополнительные флаги для полного экрана
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val params = window.attributes
                params.layoutInDisplayCutoutMode =
                    WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
                window.attributes = params
            }
        } catch (e: Exception) {
            Log.e("DeviceLockManager", "Error setting window flags: ${e.message}")
        }
    }

    private fun setAsDefaultLauncher(activity: Activity) {
        try {
            // Открываем диалог выбора лаунчера
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(intent)
        } catch (e: Exception) {
            Log.e("DeviceLockManager", "Error setting launcher: ${e.message}")
        }
    }

    private fun removeKioskPolicies(activity: Activity) {
        try {
            // Останавливаем блокировку задачи только если она активна
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && isLockTaskModeActive(activity)) {
                activity.stopLockTask()
            }

            // Убираем флаги окна
            val window = activity.window
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            window.clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD)
            window.clearFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED)
            window.clearFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON)

            Log.d("DeviceLockManager", "Kiosk policies removed")
        } catch (e: Exception) {
            Log.e("DeviceLockManager", "Error removing kiosk policies: ${e.message}")
        }
    }

//управление лаунчером по умолчанию
     fun setGiraffeAsDefaultLauncher(activity: Activity) {
        try {
            // Более надежный способ установки лаунчера
            val packageName = activity.packageName
            val packageManager = activity.packageManager

            // Создаем интент для HOME
            val homeIntent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                addCategory(Intent.CATEGORY_DEFAULT)
            }
            //Создадим компонент для нашего приложения
            val componentName = ComponentName(packageName,"${packageName}.MainActivity")

            //очищаем предыдущие предпочтения
            packageManager.clearPackagePreferredActivities(packageName)

            //Устанавливаем наш лаунчер
            val intentFilter = IntentFilter(Intent.ACTION_MAIN)
            intentFilter.addCategory(Intent.CATEGORY_HOME)
            intentFilter.addCategory(Intent.CATEGORY_DEFAULT)

            val componentNames = arrayOf(componentName)
            val intentFilters = arrayOf(intentFilter)

            packageManager.addPreferredActivity(intentFilter, IntentFilter.MATCH_CATEGORY_EMPTY,componentNames,componentName)

            Log.d("DeviceLockManager", "Giraffe set as default launcher")

        } catch (e: Exception) {
            Log.e("DeviceLockManager", "Error setting launcher: ${e.message}")

            // Создаем интент для выбора лаунчера
            val selectorIntent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            // Показываем диалог выбора лаунчера
            activity.startActivity(selectorIntent)
        }
    }

   fun clearDefaultLauncher(activity: Activity) {
        try {
            val packageName = activity.packageName
            val packageManager = activity.packageManager

            //Полностью очищаем предпочтения лаунчера
            packageManager.clearPackagePreferredActivities(packageName)

            // Сбрасываем на системные настройки
            val intent = Intent(Settings.ACTION_HOME_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            // Снова показываем диалог выбора для сброса
            activity.startActivity(intent)

            Log.d("DeviceLockManager", "Default launcher preferences cleared")
        } catch (e: Exception) {
            Log.e("DeviceLockManager", "Error clearing launcher: ${e.message}")

            // Снова создаем интент для выбора лаунчера
            val intent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            activity.startActivity(intent)

        }
    }

    fun isGiraffeDefaultLauncher(activity: Activity): Boolean {
        try {
            val intent = Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
            }
            val resolveInfo = activity.packageManager.resolveActivity(intent, 0)
            return resolveInfo?.activityInfo?.packageName == activity.packageName
        } catch (e: Exception) {
            Log.e("DeviceLockManager", "Error checking default launcher: ${e.message}")
            return false
        }
    }


}