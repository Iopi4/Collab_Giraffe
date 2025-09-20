package com.example.giraffenew.initial_setup.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.giraffenew.initial_setup.domain.CraneEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [CraneEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun craneDao(): CraneDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "crane_database"
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
                val craneDao = database.craneDao()

                val defaultCranes = listOf(
                    CraneEntity(11, "TDK-8.180"),
                    CraneEntity(12, "TDK-10.180"),
                    CraneEntity(13, "TDK-10.180.2"),
                    CraneEntity(14, "TDK-10.180.2kz"),
                    CraneEntity(15, "TDK-10.215"),
                    CraneEntity(16, "TDK-12.280"),
                    CraneEntity(17, "TDK-16.300"),
                    CraneEntity(18, "TDK-40.1100"),

                    CraneEntity(21, "SMK-5.66"),
                    CraneEntity(22, "SMK-10.200"),

                    CraneEntity(31, "TDKZ-10.160"),

                    CraneEntity(41, "TDKP-12,5.300"),
                    CraneEntity(42, "TDKP-12,5.440"),
                    CraneEntity(43, "TDKP-20.600"),
                    CraneEntity(44, "TDKP-10.300")
                )

                defaultCranes.forEach { crane ->
                    craneDao.insert(crane)
                }
            }
        }
    }
}