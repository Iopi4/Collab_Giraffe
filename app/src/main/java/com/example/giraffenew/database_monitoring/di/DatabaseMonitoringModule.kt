package com.example.giraffenew.database_monitoring.di

import com.example.giraffenew.accident_and_warning_log.data.local.AppDatabase
import com.example.giraffenew.accident_and_warning_log.data.local.EventDao
import com.example.giraffenew.data.ModbusDatabase
import com.example.giraffenew.data.dao.ModbusLogDao
import com.example.giraffenew.data.dao.RegisterConfigurationDao
import com.example.giraffenew.database_monitoring.AppActivityMonitor
import com.example.giraffenew.database_monitoring.dao.TrackedCraneDao
import com.example.giraffenew.database_monitoring.dao.TrackedEventDao
import com.example.giraffenew.database_monitoring.dao.TrackedModbusLogDao
import com.example.giraffenew.database_monitoring.dao.TrackedRegisterConfigurationDao
import com.example.giraffenew.database_monitoring.dao.TrackedTimeZoneDao
import com.example.giraffenew.initial_setup.data.CraneAppDatabase
import com.example.giraffenew.initial_setup.data.CraneDao
import com.example.giraffenew.initial_setup.data.TimeZoneAppDatabase
import com.example.giraffenew.initial_setup.data.TimeZoneDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseMonitoringModule {

    @Provides
    @Singleton
    fun provideAppActivityMonitor(): AppActivityMonitor {
        return AppActivityMonitor()
    }

    @Provides
    @Singleton
    fun provideTrackedEventDao(
        database: AppDatabase,
        monitor: AppActivityMonitor
    ): EventDao {
        return TrackedEventDao(database.eventDao(), monitor)
    }

    @Provides
    @Singleton
    fun provideTrackedRegisterConfigurationDao(
        database: ModbusDatabase,
        monitor: AppActivityMonitor
    ): RegisterConfigurationDao {
        return TrackedRegisterConfigurationDao(database.registerConfigurationDao(), monitor)
    }

    @Provides
    @Singleton
    fun provideTrackedTimeZoneDao(
        database: TimeZoneAppDatabase,
        monitor: AppActivityMonitor
    ): TimeZoneDao {
        return TrackedTimeZoneDao(database.timeZoneDao(), monitor)
    }

    @Provides
    @Singleton
    fun provideTrackedModbusLogDao(
        database: ModbusDatabase,
        monitor: AppActivityMonitor
    ): ModbusLogDao {
        return TrackedModbusLogDao(database.modbusLogDao(), monitor)
    }

    @Provides
    @Singleton
    fun provideTrackedCraneDao(
        database: CraneAppDatabase,
        monitor: AppActivityMonitor
    ): CraneDao {
        return TrackedCraneDao(database.craneDao(), monitor)
    }
}