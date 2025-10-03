package com.example.giraffenew.data.repositories

import android.content.Context
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepository
@Inject constructor(
    @ApplicationContext private val context: Context
)
{
 private val sharedPreferences = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    private companion object {
        const val KEY_VOLUME_LEVEL = "volume_level"
        const val DEFAULT_VOLUME = 1f
    }

    fun saveVolumeLevel (volume: Float){
        Log.d("SettingsRepository", "Saving volume: $volume")
        sharedPreferences.edit().putFloat(KEY_VOLUME_LEVEL, volume).apply()
    }

    fun getVolumeLevel (): Float {
        val volume = sharedPreferences.getFloat(KEY_VOLUME_LEVEL,DEFAULT_VOLUME)
        Log.d("SettingsRepository", "Retrieved volume: $volume")
        return volume
    }
}