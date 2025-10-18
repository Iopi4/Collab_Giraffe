package com.example.giraffenew.initial_setup.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.giraffenew.initial_setup.domain.CraneEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CraneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(crane: CraneEntity)

    @Query("SELECT * FROM cranes WHERE craneID = :craneId")
    suspend fun getCraneById(craneId: Int): CraneEntity?

    @Query("SELECT * FROM cranes")
    suspend fun getAllCranes(): List<CraneEntity>

    // УДАЛЯЕМ Flow методы:
    // @Query("SELECT * FROM cranes WHERE craneID = :craneId")
    // fun getCraneByIdFlow(craneId: Int): Flow<CraneEntity?>

    // @Query("SELECT * FROM cranes")
    // fun getAllCranesFlow(): Flow<List<CraneEntity>>
}