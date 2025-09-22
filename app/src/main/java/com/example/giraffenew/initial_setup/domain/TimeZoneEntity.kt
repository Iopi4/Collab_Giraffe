package com.example.giraffenew.initial_setup.domain

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.ktor.util.internal.OpDescriptor


@Entity("timeZone")
class TimeZoneEntity(
    @PrimaryKey
    val timeZoneUTC: Int,
    val timeZoneRegion: String,
    val timeZoneDescription: String,
)