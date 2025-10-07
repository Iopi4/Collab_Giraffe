package com.example.giraffenew.kiosk

import android.accessibilityservice.AccessibilityService
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.Context
import android.view.KeyEvent
import android.view.accessibility.AccessibilityEvent

class KioskAccessibilityService : AccessibilityService() {

    private var isKioskModeEnabled: Boolean = false


    override fun onServiceConnected() {
        super.onServiceConnected()
        updateKioskState()
    }

    /**
     Метод getSharedPreferences(String name, int mode) в Android используется для создания или доступа к файлу настроек (SharedPreferences).
     В этом методе передаются два аргумента:
     * name — имя файла настроек.
     * mode — режим контекста, в котором нужно хранить файл. Некоторые варианты режима:
     * MODE_PUBLIC — делает файл публичным, к нему могут получить доступ другие приложения на устройстве.
     * MODE_PRIVATE — делает файлы приватными и защищает данные пользователя.
     * MODE_APPEND — используется при чтении данных из файла настроек.
     */

    private fun updateKioskState() {
        // Читаем состояние из SharedPreferences
        val prefs = getSharedPreferences("kiosk_prefs", Context.MODE_PRIVATE)
        isKioskModeEnabled = prefs.getBoolean("kiosk_enabled", false)
    }

        //val serviceInfo = AccessibilityServiceInfo().apply {
            //eventTypes = AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED or
                    //AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED or
                    //AccessibilityEvent.TYPE_VIEW_CLICKED

            //feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC
            //notificationTimeout = 100
            //flags = AccessibilityServiceInfo.DEFAULT or
                    //AccessibilityServiceInfo.FLAG_REQUEST_FILTER_KEY_EVENTS or
                    //AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS
        //} this.serviceInfo = serviceInfo


    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        updateKioskState() //Обновляем состояние перед обработкой
        if (!isKioskModeEnabled){
            return // Не блокируем ничего, если киоск-режим выключен
        }

        event?.let {
            when (it.eventType) {
                AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED -> {
                    handleWindowStateChanged(it)
                }
            }
        }
    }

    private fun handleWindowStateChanged(event: AccessibilityEvent) {
        val packageName = event.packageName?.toString() ?: ""
        val className = event.className?.toString() ?: ""

        // Блокируем системные UI только в режиме kiosk
        if (isKioskModeEnabled){
            when {
                isSystemUI(packageName) -> {
                    performGlobalAction(GLOBAL_ACTION_BACK)
                    performGlobalAction(GLOBAL_ACTION_HOME)
                }
                isSettings(packageName) && !isKioskModeEnabled -> {
                    // Разрешаем настройки при выключенном киоск-режиме
                    return
                }
                isRecentApps(packageName, className) -> {
                    performGlobalAction(GLOBAL_ACTION_BACK)
                    performGlobalAction(GLOBAL_ACTION_HOME)
                }
            }
        }

    }
    override fun onKeyEvent(event: KeyEvent): Boolean {
       updateKioskState()

        // Блокируем системные кнопки только в режиме киоска
        return if (isKioskModeEnabled) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_HOME,
                KeyEvent.KEYCODE_APP_SWITCH,
                KeyEvent.KEYCODE_MENU -> true
                else -> false
            }
        } else {
            false // Не блокируем кнопки при выключенном режиме kiosk
        }

    }

    private fun isSystemUI(packageName: String): Boolean {
        return packageName.contains("systemui") ||
                packageName.contains("android.systemui")
    }

    private fun isSettings(packageName: String): Boolean {
        return packageName.contains("com.android.settings")
    }

    private fun isRecentApps(packageName: String, className: String): Boolean {
        return (packageName.contains("systemui") &&
                (className.contains("recents") ||
                        className.contains("overview") ||
                        className.contains("recent")))
    }



    override fun onInterrupt() {}
}