package com.example.giraffenew.database_monitoring.dao

import com.example.giraffenew.database_monitoring.AppActivityMonitor
import com.example.giraffenew.initial_setup.data.TimeZoneDao
import com.example.giraffenew.initial_setup.domain.TimeZoneEntity
import kotlinx.coroutines.flow.Flow

// Декоратор для TimeZoneDao
class TrackedTimeZoneDao(
    private val delegate: TimeZoneDao,
    private val monitor: AppActivityMonitor
) : TimeZoneDao, TrackedDao(monitor, "TimeZoneDao") {

    // метод без изменений
    override suspend fun insert(time: TimeZoneEntity) = delegate.insert(time)
    override suspend fun getTimeZoneByUtc(timeZoneUtc: Int) = delegate.getTimeZoneByUtc(timeZoneUtc)


    override fun getTimeZoneByUtcFlow(timeZoneUtc: Int): Flow<TimeZoneEntity?> {
        return trackFlowOperation("getTimeZoneByUtcFlow", delegate.getTimeZoneByUtcFlow(timeZoneUtc))
    }

    override fun getAllTimeZonesFlow(): Flow<List<TimeZoneEntity>> {
        return trackFlowOperation("getAllTimeZonesFlow", delegate.getAllTimeZonesFlow())
    }

    override suspend fun getAllTimeZones(): List<TimeZoneEntity> {
        return trackOperation("getAllTimeZones") {
            delegate.getAllTimeZones()
        }
    }
}