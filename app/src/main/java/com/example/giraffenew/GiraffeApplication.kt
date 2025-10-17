package com.example.giraffenew

import android.app.Application
import com.example.giraffenew.android_error_logger.data.logger.AndroidExceptionLogger
import com.example.giraffenew.database_monitoring.DatabaseMonitor
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

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

    @Inject
    lateinit var exceptionLogger: AndroidExceptionLogger
    @Inject
    lateinit var databaseMonitor: DatabaseMonitor


    override fun onCreate() {
        super.onCreate()
        // Application-level initialization если потребуется

        // Настраиваем глобальный обработчик исключений
        exceptionLogger.setupGlobalExceptionHandler()

        //Запускаем мониторинг базы данных
        databaseMonitor.startMonitoring()

        // Логируем метрики при запуске
        databaseMonitor.logDatabaseMetrics()

    }
}