package com.example.giraffenew.initial_setup.data

import android.util.Log
import com.example.giraffenew.initial_setup.domain.TimeZoneEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TimeZoneRepository @Inject constructor(
    private val timeZoneDao: TimeZoneDao
){
    suspend fun getTimeZoneModel (timeZoneUTC: Int): String?{
        return timeZoneDao.getTimeZoneByUtc(timeZoneUTC)?.timeZoneRegion
    }

    suspend fun getAllTimeZone(): List<TimeZoneEntity>{
        return timeZoneDao.getAllTimeZones()
    }

    // УДАЛЯЕМ Flow методы, если они есть
    //fun getAllTimeZoneFlow(): Flow<List<TimeZoneEntity>>{
        //return timeZoneDao.getAllTimeZonesFlow()
    //}
    /*
    fun getTimeZoneFlow(timeZoneUTC: Int): Flow<String?> {
        return timeZoneDao.getTimeZoneByUtcFlow(timeZoneUTC)
            .map { it?.timeZoneRegion }
            .catch { e->
                Log.e("TimeZoneRepository", "Ошибка получения региона: ${e.message}")
                emit(null)
            }
    }
    */

}