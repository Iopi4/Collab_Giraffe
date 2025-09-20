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

    // Добавляем метод для получения модели крана как Flow
    fun getCraneModelFlow(craneID: Int): Flow<String?> {
        return craneDao.getCraneByIdFlow(craneID)
            .map { it?.craneModel }
            .catch { e ->
                Log.e("CraneRepository", "Ошибка получения модели крана: ${e.message}")
                emit(null)
            }
    }

    suspend fun getAllCranes(): List<CraneEntity> {
        return craneDao.getAllCranes()
    }

    // Добавляем метод для получения всех кранов как Flow
    fun getAllCranesFlow(): Flow<List<CraneEntity>> {
        return craneDao.getAllCranesFlow()
    }
}