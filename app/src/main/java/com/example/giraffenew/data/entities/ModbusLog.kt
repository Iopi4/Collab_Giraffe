package com.example.giraffenew.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Room entity для аудита операций Modbus и системных событий
 * 
 * Логирование критических событий:
 * - Успешные/неуспешные подключения
 * - Ошибки чтения регистров 
 * - Отправка данных в облако
 * - Восстановление после сбоев
 * 
 * @param id Primary key
 * @param timestamp Время события в миллисекундах
 * @param level Уровень лога (DEBUG, INFO, WARN, ERROR, FATAL)
 * @param category Категория события (CONNECTION, READING, CLOUD_SYNC, FILE_WRITE, SERVICE)
 * @param message Текст сообщения
 * @param details Дополнительные детали в JSON формате
 * @param registerAddress Адрес регистра если относится к конкретному регистру
 * @param errorCode Код ошибки если применимо
 */
@Entity(tableName = "modbus_logs")
@Serializable
data class ModbusLog(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val timestamp: Long = System.currentTimeMillis(),
    
    val level: LogLevel,
    
    val category: LogCategory,
    
    val message: String,
    
    val details: String? = null, // JSON с дополнительными данными
    
    val registerAddress: Int? = null,
    
    val errorCode: String? = null
)

/**
 * Уровни логирования
 */
@Serializable
enum class LogLevel {
    DEBUG,   // Отладочная информация
    INFO,    // Информационные сообщения
    WARN,    // Предупреждения
    ERROR,   // Ошибки
    FATAL    // Критические ошибки
}

/**
 * Категории событий для структурированного логирования
 */
@Serializable
enum class LogCategory {
    CONNECTION,    // Подключение/отключение TCP
    READING,       // Чтение регистров
    WRITING,       // Запись регистров
    CLOUD_SYNC,    // Синхронизация с облаком
    FILE_WRITE,    // Запись в файлы
    SERVICE,       // События сервиса
    CONFIG,        // Изменение конфигурации
    PERFORMANCE,   // Метрики производительности
    SECURITY       // События безопасности
}

/**
 * Вспомогательный класс для создания стандартных логов
 */
object ModbusLogFactory {
    
    fun connectionEstablished(ip: String, port: Int): ModbusLog {
        return ModbusLog(
            level = LogLevel.INFO,
            category = LogCategory.CONNECTION,
            message = "TCP connection established",
            details = """{"ip": "$ip", "port": $port}"""
        )
    }
    
    fun connectionFailed(ip: String, port: Int, error: String): ModbusLog {
        return ModbusLog(
            level = LogLevel.ERROR,
            category = LogCategory.CONNECTION,
            message = "TCP connection failed",
            details = """{"ip": "$ip", "port": $port, "error": "$error"}""",
            errorCode = "TCP_CONNECTION_FAILED"
        )
    }
    
    fun registerReadSuccess(address: Int, value: Int, duration: Long): ModbusLog {
        return ModbusLog(
            level = LogLevel.DEBUG,
            category = LogCategory.READING,
            message = "Register read successful",
            details = """{"address": $address, "value": $value, "duration_ms": $duration}""",
            registerAddress = address
        )
    }
    
    fun registerReadError(address: Int, error: String): ModbusLog {
        return ModbusLog(
            level = LogLevel.ERROR,
            category = LogCategory.READING,
            message = "Register read failed",
            details = """{"address": $address, "error": "$error"}""",
            registerAddress = address,
            errorCode = "REGISTER_READ_FAILED"
        )
    }
    
    fun cloudSyncSuccess(recordCount: Int, duration: Long): ModbusLog {
        return ModbusLog(
            level = LogLevel.INFO,
            category = LogCategory.CLOUD_SYNC,
            message = "Cloud sync successful",
            details = """{"records": $recordCount, "duration_ms": $duration}"""
        )
    }
    
    fun cloudSyncError(recordCount: Int, error: String): ModbusLog {
        return ModbusLog(
            level = LogLevel.ERROR,
            category = LogCategory.CLOUD_SYNC,
            message = "Cloud sync failed",
            details = """{"records": $recordCount, "error": "$error"}""",
            errorCode = "CLOUD_SYNC_FAILED"
        )
    }
    
    fun serviceStarted(): ModbusLog {
        return ModbusLog(
            level = LogLevel.INFO,
            category = LogCategory.SERVICE,
            message = "ModbusBackgroundService started"
        )
    }
    
    fun serviceStopped(reason: String): ModbusLog {
        return ModbusLog(
            level = LogLevel.INFO,
            category = LogCategory.SERVICE,
            message = "ModbusBackgroundService stopped",
            details = """{"reason": "$reason"}"""
        )
    }
    
    fun serviceError(error: String): ModbusLog {
        return ModbusLog(
            level = LogLevel.ERROR,
            category = LogCategory.SERVICE,
            message = "Service error occurred",
            details = """{"error": "$error"}""",
            errorCode = "SERVICE_ERROR"
        )
    }
    
    fun performanceMetric(cycleTime: Long, memoryUsage: Long, cpuUsage: Float): ModbusLog {
        return ModbusLog(
            level = LogLevel.DEBUG,
            category = LogCategory.PERFORMANCE,
            message = "Performance metrics",
            details = """{"cycle_time_ms": $cycleTime, "memory_mb": $memoryUsage, "cpu_percent": $cpuUsage}"""
        )
    }
}