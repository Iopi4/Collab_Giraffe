package com.example.giraffenew.android_error_logger.data.dao

import androidx.room.*
import com.example.giraffenew.android_error_logger.domian.model.ExceptionEntity
import com.example.giraffenew.android_error_logger.domian.model.LogExceptionLevel
import kotlinx.coroutines.flow.Flow

@Dao
interface ExceptionDao {
    @Insert
    suspend fun insert(exception: ExceptionEntity)

    @Query("SELECT * FROM exceptions ORDER BY timestamp DESC")
    fun getAll(): Flow<List<ExceptionEntity>>

    @Query("SELECT * FROM exceptions WHERE level = :level ORDER BY timestamp DESC")
    fun getByLevel(level : LogExceptionLevel): Flow<List<ExceptionEntity>>

    @Query("DELETE FROM exceptions")
    fun clearAll()

    @Query("SELECT  COUNT(*) FROM exceptions")
    suspend fun getCount(): Int

}