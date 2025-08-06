package com.example.giraffenew.accident_and_warning_log.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.Update
import com.example.giraffenew.accident_and_warning_log.domian.model.EventEntity
import kotlinx.coroutines.flow.Flow

//@Dao — аннотация, указывающая, что это Data Access Object (объект доступа к данным)
@Dao

//EventDao — это интерфейс, который определяет операции с базой данных для работы с событиями.
interface EventDao {
    // Методы для работы с данными

    //@Insert — аннотация для автоматической генерации SQL-запроса INSERT
    @Insert
//1 Вставка данных
    suspend fun insert (event: EventEntity) //Принимает объект EventEntity и добавляет его в базу данных

//2 Обновление данных
    //@Update — аннотация для генерации SQL-запроса UPDATE
    @Update
    suspend fun update (event: EventEntity) //Обновляет существующую запись в базе данных

//3 Получение активных событий
    //@Query — позволяет написать SQL-запрос
    @Query ("SELECT * FROM events WHERE isActive = 1")
    fun getActiveEvents(): Flow<List<EventEntity>>  //Возвращает поток (Flow) активных событий (isActive = 1)

//4 Получение архивных событий
    @Query("SELECT * FROM events WHERE isActive=0")
    fun getArchivedEvents(): Flow<List<EventEntity>> //Аналогично предыдущему, но для неактивных событий (isActive = 0)

//5 Очистка архива
    @Query("DELETE FROM events WHERE isActive=0")
    suspend fun clearArchive()

    @Query("SELECT * FROM events WHERE registrationNumber = :regNumber AND isActive = 1 LIMIT 1")
    suspend fun getActiveEventByRegistration(regNumber: String): EventEntity?
}

//@Database — аннотация для определения базы данных.
//entities — список сущностей, которые будут храниться в БД
//version — версия базы данных (важно для миграций)

@Database (
    entities = [EventEntity::class], version = 1, exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    //eventDao() — абстрактный метод для получения объекта DAO. т.е. в eventDao записывается, обновляется база данных с событиями
    abstract fun eventDao(): EventDao
}

//Как это работает в целом
//Room автоматически генерирует реализацию интерфейса EventDao
//
//Flow позволяет получать данные в реальном времени
//
//Suspend-функции позволяют выполнять операции с БД асинхронно
//
//SQL-запросы в аннотациях @Query делают код более читаемым