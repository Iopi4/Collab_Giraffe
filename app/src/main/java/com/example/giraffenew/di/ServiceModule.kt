package com.example.giraffenew.di

import android.content.Context
import com.example.giraffenew.data.ModbusDatabase
import com.example.giraffenew.data.repositories.ModbusRepository
import com.example.giraffenew.network.CloudSynchronizer
import com.example.giraffenew.network.ModbusClient
import com.example.giraffenew.utils.FileLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

/**
 * Hilt модуль для предоставления основных сервисных компонентов
 * 
 * Обеспечивает:
 * - Singleton экземпляры всех основных сервисов
 * - Правильные dependencies между компонентами
 * - Lifecycle management для промышленных компонентов
 */
@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    
    /**
     * Предоставляет ModbusClient - основной TCP клиент
     */
    @Provides
    @Singleton
    fun provideModbusClient(): ModbusClient {
        return ModbusClient()
    }
    
    /**
     * Предоставляет ModbusRepository - основной data access layer
     */
    @Provides
    @Singleton
    fun provideModbusRepository(
        database: ModbusDatabase,
        modbusClient: ModbusClient,
        json: Json
    ): ModbusRepository {
        return ModbusRepository(database, modbusClient, json)
    }
    
    /**
     * Предоставляет FileLogger для записи hex данных на флэш
     */
    @Provides
    @Singleton
    fun provideFileLogger(
        @ApplicationContext context: Context
    ): FileLogger {
        return FileLogger(context)
    }
    
    /**
     * Предоставляет CloudSynchronizer для отправки данных в облако
     */
    @Provides
    @Singleton
    fun provideCloudSynchronizer(
        @ApplicationContext context: Context
    ): CloudSynchronizer {
        return CloudSynchronizer(context)
    }
}