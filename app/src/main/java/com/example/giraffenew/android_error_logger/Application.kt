package com.example.giraffenew.android_error_logger

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupExceptionHandler()
    }

    private fun setupExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            // Запись ошибки в хранилище
            ErrorLogger.logException(this, throwable)

            // Передача исключения системе (опционально)
            //defaultUncaughtExceptionHandler?.uncaughtException(thread, throwable)
        }
    }
}

/**
 * И в любом месте кода, где мы хотим записать исключение, мы передаем контекст приложения.
 */