package com.example.giraffenew

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Hilt Application class для DI контейнера
 * Инициализирует Dependency Injection для всего приложения
 * 
 * Обеспечивает:
 * - Singleton сервисы для background operations
 * - Repository layer для data access
 * - Network clients для Modbus TCP и Cloud sync
 */
@HiltAndroidApp
class GiraffeApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        // Application-level initialization если потребуется
    }
}