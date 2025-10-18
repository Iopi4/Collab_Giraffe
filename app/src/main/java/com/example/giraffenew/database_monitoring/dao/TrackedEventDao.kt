package com.example.giraffenew.database_monitoring.dao

import com.example.giraffenew.accident_and_warning_log.data.local.EventDao
import com.example.giraffenew.accident_and_warning_log.domian.model.EventEntity
import com.example.giraffenew.database_monitoring.AppActivityMonitor
import kotlinx.coroutines.flow.Flow

// Декоратор для EventDao
class TrackedEventDao(
    private val delegate: EventDao,
    private val monitor: AppActivityMonitor
) : EventDao, TrackedDao(monitor, "EventDao") {

    override fun getActiveEvents(): Flow<List<EventEntity>> {
        return trackFlowOperation("getActiveEvents"){
            delegate.getActiveEvents()
        }
    }

    override fun getArchivedEvents(): Flow<List<EventEntity>> {
        return trackFlowOperation("getArchivedEvents") {
            delegate.getArchivedEvents()// Лямбда
        }
    }

    // Остальные методы без изменений
    override suspend fun insert(event: EventEntity) = trackOperation("insertEvent") { delegate.insert(event) }



    override suspend fun update(event: EventEntity) = trackOperation("updateEvent") {delegate.update(event)}

    override suspend fun clearArchive() = trackOperation("clearEvent") { delegate.clearArchive()}
    override suspend fun getActiveEventByRegistration(regNumber: String): EventEntity? = trackOperation("getActiveEventByRegistration") {
        delegate.getActiveEventByRegistration(regNumber)}
}