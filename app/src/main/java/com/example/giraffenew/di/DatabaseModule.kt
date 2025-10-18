package com.example.giraffenew.di

import android.content.Context
import com.example.giraffenew.data.ModbusDatabase
import com.example.giraffenew.data.dao.ModbusLogDao
import com.example.giraffenew.data.dao.RegisterConfigurationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt модуль для предоставления Room Database и связанных компонентов
 * 
 * Обеспечивает:
 * - Singleton экземпляр ModbusDatabase
 * - DAO объекты для всех операций с базой данных
 * - Правильную инициализацию с WAL режимом
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    
    /**
     * Предоставляет singleton экземпляр ModbusDatabase
     */
    @Provides
    @Singleton
    fun provideModbusDatabase(
        @ApplicationContext context: Context
    ): ModbusDatabase {
        return ModbusDatabase.getDatabase(context)
    }
    
    /**
     * Предоставляет RegisterConfigurationDao
     */
    @Provides
    fun provideRegisterConfigurationDao(
        database: ModbusDatabase
    ): RegisterConfigurationDao {
        return database.registerConfigurationDao()
    }
    
    /**
     * Предоставляет ModbusLogDao
     */
    //@Provides
    //fun provideModbusLogDao(
        //database: ModbusDatabase
    //): ModbusLogDao {
        //return database.modbusLogDao()
    //}
}