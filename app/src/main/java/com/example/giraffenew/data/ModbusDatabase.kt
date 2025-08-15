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
        //Список сущностей в базе данных.
        RegisterConfiguration::class, //конфигурации регистров
        ModbusLog::class //журнал операций
    ],
    version = 1,
    exportSchema = true // Для отслеживания изменений схемы. Позволяет Room экспортировать схему базы данных в папку проекта
)
@TypeConverters(ModbusDatabaseConverters::class)
//TypeConverters указывает класс, который содержит конвертеры для нестандартных типов
//(в данном случае, для enum классов `LogLevel` и `LogCategory`).
abstract class ModbusDatabase : RoomDatabase() {
    //Абстрактные методы DAO:
    //Предоставляем доступ к операциям с таблицами через:
    abstract fun registerConfigurationDao(): RegisterConfigurationDao //для работы с конфигурациями регистров
    abstract fun modbusLogDao(): ModbusLogDao //для работы с логами.
    
    companion object { // Он позволяет определить методы и свойства, которые относятся к классу ModbusDatabase, а не к его экземплярам.
        const val DATABASE_NAME = "modbus_database"
        
        @Volatile
        private var INSTANCE: ModbusDatabase? = null
        
        /**
         * Singleton pattern с thread-safe initialization (Паттерн «Одиночка» с потокобезопасной инициализацией)
         * Реализацию синглтона с двойной проверкой блокировки (`synchronized`) для потокобезопасности.
         * Если экземпляр уже создан, возвращает его.
         * Если нет, то синхронизирует блок и создает базу данных с помощью `Room.databaseBuilder`.
         */
        fun getDatabase(context: Context): ModbusDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ModbusDatabase::class.java,
                    DATABASE_NAME
                ) /** В билдере настраивается:

                 */

                    .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING) // WAL режим для concurrent access. WAL позволяет читать и записывать данные в базу данных одновременно.
                    .addCallback(DatabaseCallback()) // Callback для инициализации. Добавляет колбэк для обработки событий создания и открытия БД.
                    .addMigrations(/* будущие миграции */)
                    .fallbackToDestructiveMigration() // Только для development. В случае отсутствия миграции при обновлении версии, база будет пересоздана
                    .build()

                    
                INSTANCE = instance
                instance
            }
        }
        
        /**
         * Callback для инициализации базы данных.
         * Внутри DatabaseCallback переопределены методы:
         * - `onCreate(db)`: вызывается при создании БД (здесь можно вставить начальные данные, но в данном случае инициализация вынесена в `DatabaseInitializer` через репозиторий).
         * - `onOpen(db)`: вызывается при открытии БД (можно добавить логику при каждом открытии).
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

    //TypeConverter помечает методы, которые преобразуют enum в строку (и обратно) для хранения в базе.
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
     * suspend функция initialize, проверяет, пуста ли таблица конфигураций.
     * Если да, то вставляет дефолтную конфигурацию и логирует это событие.
     * Затем выполняет ротацию логов (вероятно, удаление старых записей).
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
                details = """{"config_name": "${defaultConfig.name}", "registers_count": 12}"""
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

/**
 * Опишем, как это работает вместе:
 *  - При первом запуске приложения, когда вызывается `ModbusDatabase.getDatabase(context)`, создается база данных.
 *  - Room вызывает `onCreate` колбэка, но в нем ничего не делается, потому что инициализация перенесена в `DatabaseInitializer.initialize`,
 *  который должен быть вызван явно (например, из репозитория или при старте приложения).
 *  - После создания экземпляра базы данных, приложение (например, в классе Application или в ViewModel)
 *  вызывает `DatabaseInitializer.initialize` в корутине.
 *    - Эта функция проверяет количество записей в таблице конфигураций. Если 0, то вставляет дефолтную конфигурацию
 *    (созданную фабричным методом `DefaultRegisterConfiguration.createDefault()`).
 *    - Затем создает лог-запись о инициализации и вставляет ее в таблицу логов.
 *    - Вызывает `logDao.performLogRotation()` (предположительно, для очистки старых логов).
 *  - Если нужно добавить тестовые данные, можно вызвать `DatabaseInitializer.insertTestData`.
 *  - Конвертеры автоматически используются Room, когда в сущностях встречаются поля типов `LogLevel` или `LogCategory`.
 *  - При обновлении версии базы данных (если изменить version в @Database), Room будет искать соответствующую миграцию в `addMigrations`.
 *  Если не найдет, то при `fallbackToDestructiveMigration()` база будет уничтожена и создана заново (данные потеряются).
 *  В продакшене следует убрать этот фалбэк и предоставить миграции.
 *  Таким образом, данный код обеспечивает:
 *    - Создание базы данных с двумя таблицами.
 *    - Потокобезопасный синглтон для доступа к БД.
 *    - Конвертацию enum в строки.
 *    - Инициализацию начальными данными.
 *    - Возможность миграций (пока в зачаточном состоянии).
 *  Важно: `fallbackToDestructiveMigration` используется только для разработки, в релизе его следует убрать и реализовать миграции.
 */