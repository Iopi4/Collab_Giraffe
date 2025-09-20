package com.example.giraffenew.initial_setup.data

import com.example.giraffenew.initial_setup.domain.CraneEntity
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
}