package com.example.giraffenew.initial_setup.data

import android.util.Log
import com.example.giraffenew.initial_setup.domain.CraneEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CraneRepository
@Inject constructor(
    private val craneDao: CraneDao
){
    suspend fun getCraneModel(craneID: Int): String? {
        return craneDao.getCraneById(craneID)?.craneModel
    }

    suspend fun getAllCranes(): List<CraneEntity> {
        return craneDao.getAllCranes()
    }

    // УДАЛЯЕМ Flow методы:
    // fun getCraneModelFlow(craneID: Int): Flow<String?> { ... }
    // fun getAllCranesFlow(): Flow<List<CraneEntity>> { ... }
}