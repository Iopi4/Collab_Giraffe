package com.example.giraffenew.database_monitoring.dao

import com.example.giraffenew.data.dao.RegisterConfigurationDao
import com.example.giraffenew.data.entities.RegisterConfiguration
import com.example.giraffenew.database_monitoring.AppActivityMonitor
import kotlinx.coroutines.flow.Flow

// Декоратор для RegisterConfigurationDao
class TrackedRegisterConfigurationDao(
    private val delegate: RegisterConfigurationDao,
    private val monitor: AppActivityMonitor
) : RegisterConfigurationDao, TrackedDao(monitor, "RegisterConfigurationDao") {

    override fun getActiveConfigurationFlow(): Flow<RegisterConfiguration?> {
        return trackFlowOperation("getActiveConfigurationFlow")
            {delegate.getActiveConfigurationFlow()}
    }

    override fun getAllConfigurations(): Flow<List<RegisterConfiguration>> {
        return trackFlowOperation("getAllConfigurations"){
            delegate.getAllConfigurations()}
    }

    override suspend fun getActiveConfiguration(): RegisterConfiguration? {
        return trackOperation("getActiveConfiguration") {
            delegate.getActiveConfiguration()
        }
    }

    override suspend fun getConfigurationById(id: Long): RegisterConfiguration? {
        return trackOperation("getConfigurationById") {
            delegate.getConfigurationById(id)
        }
    }

    override suspend fun getConfigurationByName(name: String): RegisterConfiguration? {
        return trackOperation("getConfigurationByName") {
            delegate.getConfigurationByName(name)
        }
    }

    override suspend fun insert(configuration: RegisterConfiguration): Long {
        return trackOperation("insert") {
            delegate.insert(configuration)
        }
    }

    override suspend fun update(configuration: RegisterConfiguration) {
        trackOperation("update") {
            delegate.update(configuration)
        }
    }

    override suspend fun delete(configuration: RegisterConfiguration) {
        trackOperation("delete") {
            delegate.delete(configuration)
        }
    }

    override suspend fun deleteById(id: Long) {
        trackOperation("deleteById") {
            delegate.deleteById(id)
        }
    }

    override suspend fun setActiveConfiguration(configurationId: Long) {
        trackOperation("setActiveConfiguration") {
            delegate.setActiveConfiguration(configurationId)
        }
    }

    override suspend fun deactivateAllConfigurations(timestamp: Long) {
        trackOperation("deactivateAllConfigurations") {
            delegate.deactivateAllConfigurations(timestamp)
        }
    }

    override suspend fun activateConfiguration(id: Long, timestamp: Long) {
        trackOperation("activateConfiguration") {
            delegate.activateConfiguration(id, timestamp)
        }
    }

    override suspend fun hasActiveConfiguration(): Int {
        return trackOperation("hasActiveConfiguration") {
            delegate.hasActiveConfiguration()
        }
    }

    override suspend fun getConfigurationCount(): Int {
        return trackOperation("getConfigurationCount") {
            delegate.getConfigurationCount()
        }
    }

    override suspend fun cleanupOldConfigurations(keepCount: Int) {
        trackOperation("cleanupOldConfigurations") {
            delegate.cleanupOldConfigurations(keepCount)
        }
    }

    override suspend fun backupActiveConfiguration(): Long? {
        return trackOperation("backupActiveConfiguration") {
            delegate.backupActiveConfiguration()
        }
    }
}