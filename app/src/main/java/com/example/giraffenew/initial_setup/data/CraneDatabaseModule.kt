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
object CraneDatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): CraneAppDatabase {
        return CraneAppDatabase.getDatabase(context)
    }

    //@Provides
    //@Singleton
    //fun provideCraneDao(appDatabase: CraneAppDatabase): CraneDao {
        //return appDatabase.craneDao()
    //}
}