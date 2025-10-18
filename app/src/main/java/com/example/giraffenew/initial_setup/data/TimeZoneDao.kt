package com.example.giraffenew.initial_setup.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.giraffenew.initial_setup.domain.TimeZoneEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeZoneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(time: TimeZoneEntity)

    @Query("SELECT * FROM timeZone WHERE timeZoneUTC = :timeZoneUtc")
    suspend fun getTimeZoneByUtc(timeZoneUtc: Int): TimeZoneEntity?

    @Query("SELECT * FROM timeZone")
    suspend fun getAllTimeZones(): List<TimeZoneEntity>

    // УДАЛЯЕМ Flow методы:
    //@Query("SELECT * FROM timeZone")
    //fun getAllTimeZonesFlow(): Flow<List<TimeZoneEntity>>

    //@Query("SELECT * FROM timeZone WHERE timeZoneUTC = :timeZoneUtc")
    //fun getTimeZoneByUtcFlow(timeZoneUtc: Int): Flow<TimeZoneEntity?>

}