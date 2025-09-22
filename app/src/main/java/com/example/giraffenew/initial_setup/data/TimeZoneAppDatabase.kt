package com.example.giraffenew.initial_setup.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.giraffenew.initial_setup.domain.TimeZoneEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [TimeZoneEntity::class], version = 1)

abstract class TimeZoneAppDatabase: RoomDatabase() {
    abstract fun timeZoneDao(): TimeZoneDao

    companion object {

        @Volatile
        private var INSTANCE: TimeZoneAppDatabase? = null

        fun getDatabase(context: Context): TimeZoneAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TimeZoneAppDatabase::class.java,
                    "timeZone_database"
                )
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            prepopulateDatabase(context)
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private fun prepopulateDatabase(context: Context) {
            CoroutineScope(Dispatchers.IO).launch {
                val database = getDatabase(context)
                val timeZoneDao = database.timeZoneDao()

                val defaultTimeZone = listOf(
                    TimeZoneEntity(1, "UTC+1 (МСК−2)","—Гибралтар"),
                    TimeZoneEntity(2, "UTC+2 (МСК−1)","—Калининградская область"),
                    TimeZoneEntity(3, "UTC+3 (МСК)","—Московское время"),
                    TimeZoneEntity(4, "UTC+4 (МСК+1)", "—Удмуртская Республика, Астраханская область, Самарская область, Саратовская область и Ульяновская область"),
                    TimeZoneEntity(5, "UTC+5 (МСК+2)", "—Башкортостан, Пермский край, Курганская область, Оренбургская область, Свердловская область, Тюменская область, Челябинская область, Ханты-Мансийский автономный округ — Югра и Ямало-Ненецкий автономный округ"),
                    TimeZoneEntity(6, "UTC+6 (МСК+3)","—Омская область"),
                    TimeZoneEntity(7, "UTC+7 (МСК+4)", "—Республика Алтай, Республика Тыва, Республика Хакасия, Алтайский край, Красноярский край, Кемеровская область — Кузбасс, Новосибирская область и Томская область"),
                    TimeZoneEntity(8, "UTC+8 (МСК+5)", "—Республика Бурятия и Иркутская область"),
                    TimeZoneEntity(9, "UTC+9 (МСК+6)" ,"—Республика Саха (Якутия) (западные и центральные районы), Забайкальский край и Амурская область"),
                    TimeZoneEntity(10, "UTC+10 (МСК+7)", "—Республика Саха (Якутия) (ряд районов), Приморский край, Хабаровский край и Еврейская автономная область"),
                    TimeZoneEntity(11, "UTC+11 (МСК+8)", "—Республика Саха (Якутия) (северо-восточные районы), Магаданская область и Сахалинская область"),
                    TimeZoneEntity(12, "UTC+12 (МСК+9)","—Камчатский край и Чукотский автономный округ"),
                )
                defaultTimeZone.forEach{time ->
                    timeZoneDao.insert(time)
                }
            }
        }
    }
}
/**
UTC+1 (CET) — Гибралтар
UTC+2 (МСК−1) — Калининградская область
UTC+3 (МСК, московское время) — большая часть европейской территории России и вся Российская железная дорога
UTC+4 (МСК+1) — Удмуртская Республика, Астраханская область, Самарская область, Саратовская область и Ульяновская область
UTC+5 (МСК+2) — Республика Башкортостан, Пермский край, Курганская область, Оренбургская область, Свердловская область, Тюменская область, Челябинская область, Ханты-Мансийский автономный округ — Югра и Ямало-Ненецкий автономный округ
UTC+6 (МСК+3) — Омская область
UTC+7 (МСК+4) — Республика Алтай, Республика Тыва, Республика Хакасия, Алтайский край, Красноярский край, Кемеровская область — Кузбасс, Новосибирская область и Томская область
UTC+8 (МСК+5) — Республика Бурятия и Иркутская область
UTC+9 (МСК+6) — Республика Саха (Якутия) (западные и центральные районы), Забайкальский край и Амурская область
UTC+10 (МСК+7) — Республика Саха (Якутия) (ряд районов), Приморский край, Хабаровский край и Еврейская автономная область
UTC+11 (МСК+8) — Республика Саха (Якутия) (северо-восточные районы), Магаданская область и Сахалинская область
UTC+12 (МСК+9) — Камчатский край и Чукотский автономный округ
 */