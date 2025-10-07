package com.example.giraffenew.di

import android.content.Context
import com.example.giraffenew.kiosk.DeviceLockManager
import com.example.giraffenew.core.sound.SoundController
import com.example.giraffenew.data.repositories.SettingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(@ApplicationContext context: Context): SettingsRepository {
        return SettingsRepository(context)
    }

    @Provides
    @Singleton
    fun provideSoundController(
        @ApplicationContext context: Context,
        settingsRepository: SettingsRepository
    ): SoundController {
        return SoundController(context, settingsRepository)
    }

    @Provides
    @Singleton
    fun provideDeviceLockManager(@ApplicationContext context: Context): DeviceLockManager {
        return DeviceLockManager(context)
    }
}