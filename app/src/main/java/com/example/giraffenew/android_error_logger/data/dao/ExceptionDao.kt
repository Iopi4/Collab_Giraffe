package com.example.giraffenew.android_error_logger.data.dao

import androidx.room.*
import com.example.giraffenew.android_error_logger.domian.model.ExceptionEntity
import com.example.giraffenew.android_error_logger.domian.model.LogExceptionLevel
import kotlinx.coroutines.flow.Flow

@Dao
interface ExceptionDao {
    @Insert
    suspend fun insert(exception: ExceptionEntity)

    // Заменяем Flow на suspend функции
    @Query("SELECT * FROM exceptions ORDER BY timestamp DESC")
    suspend fun getAll(): List<ExceptionEntity>

    @Query("SELECT * FROM exceptions WHERE level = :level ORDER BY timestamp DESC")
    suspend fun getByLevel(level: LogExceptionLevel): List<ExceptionEntity>

    @Query("DELETE FROM exceptions")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM exceptions")
    suspend fun getCount(): Int

    // Опционально: оставляем один Flow метод, если действительно нужна реактивность
    //@Query("SELECT COUNT(*) FROM exceptions")
    //fun getCountFlow(): Flow<Int>

}