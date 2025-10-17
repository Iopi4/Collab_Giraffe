package com.example.giraffenew.initial_setup.data

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TimeZoneDatabaseModule {
    @Provides
    @Singleton
    fun provideTimeZoneAppDataBase(@ApplicationContext context: Context): TimeZoneAppDatabase {
        return TimeZoneAppDatabase.getDatabase(context)
    }


    //Замена оригинального DAO на отслеживаемую версию TrackedTimeZoneDao
    //@Provides
    //@Singleton
    //fun provideTimeZoneDao (appDatabase: TimeZoneAppDatabase): TimeZoneDao{
        //return appDatabase.timeZoneDao()
    //}
}