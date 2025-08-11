package com.example.giraffenew.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

/**
 * Hilt модуль для предоставления сетевых компонентов
 * 
 * Обеспечивает:
 * - Настроенный JSON serializer для API и конфигурации
 * - Singleton экземпляры для сетевых компонентов
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    
    /**
     * Предоставляет настроенный JSON serializer
     * Используется для:
     * - Парсинг конфигурации регистров
     * - Cloud API requests/responses
     * - Offline queue serialization
     */
    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            // Игнорируем неизвестные поля для forward compatibility
            ignoreUnknownKeys = true
            
            // Включаем значения по умолчанию
            encodeDefaults = true
            
            // Lenient parsing для robustness
            isLenient = true
            
            // Включаем pretty print для readable logs (если нужно)
            prettyPrint = false // Отключено для производительности
            
            // Разрешаем структурированные map keys
            allowStructuredMapKeys = true
            
            // Используем альтернативные имена для полей
            useAlternativeNames = false
        }
    }
}