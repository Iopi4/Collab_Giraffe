package com.example.giraffenew.database_monitoring.dao

import com.example.giraffenew.data.dao.LogLevelCount
import com.example.giraffenew.data.dao.ModbusLogDao
import com.example.giraffenew.data.entities.LogCategory
import com.example.giraffenew.data.entities.LogLevel
import com.example.giraffenew.data.entities.ModbusLog
import com.example.giraffenew.database_monitoring.AppActivityMonitor
import kotlinx.coroutines.flow.Flow

// Декоратор для ModbusLogDao
class TrackedModbusLogDao(
    private val delegate: ModbusLogDao,
    private val monitor: AppActivityMonitor
) : ModbusLogDao, TrackedDao(monitor, "ModbusLogDao") {

    override suspend fun insert(log: ModbusLog): Long {
        return trackOperation("insert") {
            delegate.insert(log)
        }
    }

    override suspend fun insertAll(logs: List<ModbusLog>) {
        trackOperation("insertAll") {
            delegate.insertAll(logs)
        }
    }

    override fun getRecentLogs(limit: Int): Flow<List<ModbusLog>> {
        return trackFlowOperation("getRecentLogs", delegate.getRecentLogs(limit))
    }

    override fun getLogsByLevel(level: LogLevel, limit: Int): Flow<List<ModbusLog>> {
        return trackFlowOperation("getLogsByLevel", delegate.getLogsByLevel(level,limit))
    }

    override fun getLogsByCategory(category: LogCategory, limit: Int): Flow<List<ModbusLog>> {
        return trackFlowOperation("getLogsByCategory", delegate.getLogsByCategory(category,limit))
    }

    override suspend fun getLogsByTimeRange(
        startTime: Long,
        endTime: Long,
        limit: Int
    ): List<ModbusLog> {
        return trackOperation("getLogsByTimeRange") {
            delegate.getLogsByTimeRange(startTime, endTime, limit)
        }
    }

    override suspend fun getRecentErrors(sinceTime: Long): List<ModbusLog> {
        return trackOperation("getRecentErrors") {
            delegate.getRecentErrors(sinceTime)
        }
    }

    override suspend fun getLogsForRegister(address: Int, limit: Int): List<ModbusLog> {
        return trackOperation("getLogsForRegister") {
            delegate.getLogsForRegister(address, limit)
        }
    }

    override suspend fun getLogStatistics(sinceTime: Long): List<LogLevelCount> {
        return trackOperation("getLogStatistics") {
            delegate.getLogStatistics(sinceTime)
        }
    }

    override suspend fun getErrorCount(startTime: Long, endTime: Long): Int {
        return trackOperation("getErrorCount") {
            delegate.getErrorCount(startTime, endTime)
        }
    }

    override suspend fun searchLogs(searchText: String, limit: Int): List<ModbusLog> {
        return trackOperation("searchLogs") {
            delegate.searchLogs(searchText, limit)
        }
    }

    override suspend fun deleteLogsBefore(beforeTime: Long): Int {
        return trackOperation("deleteLogsBefore") {
            delegate.deleteLogsBefore(beforeTime)
        }
    }

    override suspend fun deleteLogsByLevelBefore(level: LogLevel, beforeTime: Long): Int {
        return trackOperation("deleteLogsByLevelBefore") {
            delegate.deleteLogsByLevelBefore(level, beforeTime)
        }
    }

    override suspend fun getTotalLogCount(): Int {
        return trackOperation("getTotalLogCount") {
            delegate.getTotalLogCount()
        }
    }

    override suspend fun getEstimatedTableSize(): Long {
        return trackOperation("getEstimatedTableSize") {
            delegate.getEstimatedTableSize()
        }
    }

    override suspend fun performLogRotation() {
        trackOperation("performLogRotation") {
            delegate.performLogRotation()
        }
    }

    override suspend fun performEmergencyCleanup(): Int {
        return trackOperation("performEmergencyCleanup") {
            delegate.performEmergencyCleanup()
        }
    }
}