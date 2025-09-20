package com.example.giraffenew.initial_setup.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.giraffenew.initial_setup.domain.CraneEntity

@Dao
interface CraneDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(crane: CraneEntity)

    @Query("SELECT * FROM cranes WHERE craneID = :craneId")
    suspend fun getCraneById(craneId: Int): CraneEntity?

    @Query("SELECT * FROM cranes")
    suspend fun getAllCranes(): List<CraneEntity>
}