package com.example.giraffenew.accident_and_warning_log.data.di

import android.content.Context
import androidx.room.Room
import com.example.giraffenew.accident_and_warning_log.data.local.AppDatabase
import com.example.giraffenew.accident_and_warning_log.data.local.EventDao
import com.example.giraffenew.accident_and_warning_log.data.remoute.FakeRegisterStatusDataSource
import com.example.giraffenew.accident_and_warning_log.data.remoute.JsonDataSource
import com.example.giraffenew.accident_and_warning_log.data.remoute.RegisterStatusDataSource
import com.example.giraffenew.accident_and_warning_log.domian.usecase.HandleStatusChangeUseCase
import com.example.giraffenew.accident_and_warning_log.simulator.EventSimulator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


//Данный код представляет собой модуль Dagger-Hilt для внедрения зависимостей в Android-приложении.
//Код определяет модуль под названием AppModule, который отвечает за предоставление основных зависимостей приложении.

@Module //Указывает, что данный класс является модулем для внедрения зависимостей
        //Модуль содержит методы, которые предоставляют различные зависимости приложению

@InstallIn(SingletonComponent::class) //указывает, что модуль будет установлен в SingletonComponent -
    //То есть, один и тот же экземпляр зависимости будет использоваться во всем приложении

object AppModule {
    @Provides //Аннотация @Provides указывает, что метод предоставляет зависимость
     @Singleton//Аннотация @Singleton гарантирует, что будет создан только один экземпляр базы данных

    //Метод принимает контекст приложения (помечен аннотацией @ApplicationContext)
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {


        //Использует Room для создания базы данных:
        return Room.databaseBuilder(
            context, //- контекст приложения
            AppDatabase::class.java, //- класс, представляющий базу данных
            "events-db" // - имя файла базы данных
        ).build()
    }

    //Метод provideEventDao
    // - Предоставляет Data Access Object (DAO) для работы с событиям
    // - Принимает экземпляр базы данных (AppDatabase)
    // - Возвращает объект DAO через метод eventDao()
    @Provides
    fun provideEventDao(db: AppDatabase): EventDao = db.eventDao()

    @Provides
    fun provideEventSimulator(handleStatusChange: HandleStatusChangeUseCase): EventSimulator {
        return EventSimulator(handleStatusChange)
    }




    @Provides
    @Singleton
    fun provideRegisterStatusDataSource(
        jsonDataSource: JsonDataSource
    ): RegisterStatusDataSource {
        val ds = FakeRegisterStatusDataSource()
        // Инициализация начальных состояний
        jsonDataSource.getAllRegistrationNumbers().forEach { reg ->
            ds.updateStatus(reg, false)
        }
        return ds
    }
}