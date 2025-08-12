package com.example.giraffenew.data

import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.giraffenew.data.dao.ModbusLogDao
import com.example.giraffenew.data.dao.RegisterConfigurationDao
import com.example.giraffenew.data.entities.LogCategory
import com.example.giraffenew.data.entities.LogLevel
import com.example.giraffenew.data.entities.ModbusLog
import com.example.giraffenew.data.entities.RegisterConfiguration

/**
 * Room Database для хранения конфигурации и логов системы Modbus
 * 
 * Особенности:
 * - Версионирование схемы с миграциями
 * - Инициализация с default конфигурацией
 * - Type converters для enum классов
 * - WAL режим для высокой производительности concurrent access
 */
@Database(
    entities = [
        RegisterConfiguration::class,
        ModbusLog::class
    ],
    version = 1,
    exportSchema = true // Для отслеживания изменений схемы
)
@TypeConverters(ModbusDatabaseConverters::class)
abstract class ModbusDatabase : RoomDatabase() {
    
    abstract fun registerConfigurationDao(): RegisterConfigurationDao
    abstract fun modbusLogDao(): ModbusLogDao
    
    companion object {
        const val DATABASE_NAME = "modbus_database"
        
        @Volatile
        private var INSTANCE: ModbusDatabase? = null
        
        /**
         * Singleton pattern с thread-safe initialization
         */
        fun getDatabase(context: Context): ModbusDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ModbusDatabase::class.java,
                    DATABASE_NAME
                )
                    .setJournalMode(RoomDatabase.JournalMode.WAL) // WAL режим для concurrent access
                    .addCallback(DatabaseCallback()) // Callback для инициализации
                    .addMigrations(/* будущие миграции */)
                    .fallbackToDestructiveMigration() // Только для development
                    .build()
                    
                INSTANCE = instance
                instance
            }
        }
        
        /**
         * Callback для инициализации базы данных
         */
        private class DatabaseCallback : RoomDatabase.Callback() {
            
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // Инициализация выполняется в отдельной корутине через Repository
            }
            
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // Можно добавить логику при каждом открытии БД
            }
        }
    }
}

/**
 * Type converters для Room Database
 * Конвертирует enum классы в строки и обратно
 */
class ModbusDatabaseConverters {
    
    @TypeConverter
    fun fromLogLevel(logLevel: LogLevel): String {
        return logLevel.name
    }
    
    @TypeConverter
    fun toLogLevel(logLevelString: String): LogLevel {
        return LogLevel.valueOf(logLevelString)
    }
    
    @TypeConverter
    fun fromLogCategory(logCategory: LogCategory): String {
        return logCategory.name
    }
    
    @TypeConverter
    fun toLogCategory(logCategoryString: String): LogCategory {
        return LogCategory.valueOf(logCategoryString)
    }
}

/**
 * Миграции для будущих версий схемы
 */
object DatabaseMigrations {
    
    // Пример миграции для будущих изменений
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            // SQL команды для миграции схемы
            // db.execSQL("ALTER TABLE register_configurations ADD COLUMN new_field TEXT")
        }
    }
}

/**
 * Инициализация базы данных с default данными
 */
object DatabaseInitializer {
    
    /**
     * Инициализировать БД с default конфигурацией если она пустая
     */
    suspend fun initialize(database: ModbusDatabase) {
        val configDao = database.registerConfigurationDao()
        val logDao = database.modbusLogDao()
        
        // Проверяем есть ли конфигурации
        val configCount = configDao.getConfigurationCount()
        
        if (configCount == 0) {
            // Создаем default конфигурацию
            val defaultConfig = com.example.giraffenew.data.entities.DefaultRegisterConfiguration.createDefault()
            configDao.insert(defaultConfig)
            
            // Логируем инициализацию
            val initLog = ModbusLog(
                level = LogLevel.INFO,
                category = LogCategory.CONFIG,
                message = "Database initialized with default configuration",
                details = """{"config_name": "${defaultConfig.name}", "registers_count": 11}"""
            )
            logDao.insert(initLog)
        }
        
        // Выполняем ротацию логов при старте
        logDao.performLogRotation()
    }
    
    /**
     * Создать тестовые данные для разработки
     */
    suspend fun insertTestData(database: ModbusDatabase) {
        val logDao = database.modbusLogDao()
        
        val testLogs = listOf(
            ModbusLog(
                level = LogLevel.INFO,
                category = LogCategory.SERVICE,
                message = "Service started successfully"
            ),
            ModbusLog(
                level = LogLevel.DEBUG,
                category = LogCategory.CONNECTION,
                message = "TCP connection established",
                details = """{"ip": "192.168.1.88", "port": 502}"""
            ),
            ModbusLog(
                level = LogLevel.WARN,
                category = LogCategory.READING,
                message = "Register read timeout",
                registerAddress = 90,
                errorCode = "TIMEOUT"
            )
        )
        
        logDao.insertAll(testLogs)
    }
}