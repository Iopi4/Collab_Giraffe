package com.example.giraffenew.initial_setup.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "cranes")
class CraneEntity (
    @PrimaryKey
    val craneID: Int,
    val craneModel: String,
)