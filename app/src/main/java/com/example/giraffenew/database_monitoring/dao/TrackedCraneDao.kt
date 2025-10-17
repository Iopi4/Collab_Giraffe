package com.example.giraffenew.database_monitoring.dao

import com.example.giraffenew.accident_and_warning_log.domian.model.EventEntity
import com.example.giraffenew.database_monitoring.AppActivityMonitor
import com.example.giraffenew.initial_setup.data.CraneDao
import com.example.giraffenew.initial_setup.domain.CraneEntity
import kotlinx.coroutines.flow.Flow

// Декоратор для CraneDao
class TrackedCraneDao(
    private val delegate: CraneDao,
    private val monitor: AppActivityMonitor
) : CraneDao, TrackedDao(monitor, "CraneDao") {

    override fun getCraneByIdFlow(craneId: Int): Flow<CraneEntity?> {
        return trackFlowOperation("getCraneByIdFlow",
            delegate.getCraneByIdFlow(craneId))
    }

    override fun getAllCranesFlow(): Flow<List<CraneEntity>> {
        return trackFlowOperation("getAllCranesFlow",
            delegate.getAllCranesFlow())
    }

    // Остальные методы
    override suspend fun getCraneById(craneId: Int): CraneEntity? {
        return trackOperation("getCraneById") {
            delegate.getCraneById(craneId)
        }
    }

    override suspend fun getAllCranes(): List<CraneEntity> {
        return trackOperation("getAllCranes") {
            delegate.getAllCranes()
        }
    }

    // Остальные методы без изменений
    override suspend fun insert(crane: CraneEntity) = delegate.insert(crane)

}