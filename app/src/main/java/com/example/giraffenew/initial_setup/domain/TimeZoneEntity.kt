package com.example.giraffenew.initial_setup.domain

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("timeZone")
class TimeZoneEntity(
    @PrimaryKey
    val timeZoneUTC: Int,
    val timeZoneRegion: String,
    val timeZoneDescription: String,
)