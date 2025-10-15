package com.example.giraffenew.android_error_logger.data.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.giraffenew.android_error_logger.data.dao.ExceptionDao
import com.example.giraffenew.android_error_logger.data.logger.AndroidExceptionLogger
import com.example.giraffenew.android_error_logger.data.repository.ExceptionRepositoryImpl
import com.example.giraffenew.android_error_logger.domian.model.ExceptionEntity
import com.example.giraffenew.android_error_logger.domian.repository.ExceptionRepository
import com.example.giraffenew.android_error_logger.domian.usecase.LogExceptionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ExceptionModule {

    @Provides
    @Singleton
    fun provideAppDatabaseException(@ApplicationContext context: Context): AppDatabaseException {
        return Room.databaseBuilder(
            context,
            AppDatabaseException::class.java,
            "exceptions_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideExceptionDao(db: AppDatabaseException): ExceptionDao = db.exceptionDao()

    @Provides
    @Singleton
    fun provideExceptionRepository(dao: ExceptionDao): ExceptionRepository =
        ExceptionRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideAndroidExceptionLogger(
        logExceptionUseCase: LogExceptionUseCase
    ): AndroidExceptionLogger = AndroidExceptionLogger(logExceptionUseCase)
}

@Database(
    entities = [ExceptionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabaseException : RoomDatabase() {
    abstract fun exceptionDao(): ExceptionDao
}