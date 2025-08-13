package com.example.giraffenew.data.dao

import androidx.room.*
import com.example.giraffenew.data.entities.LogCategory
import com.example.giraffenew.data.entities.LogLevel
import com.example.giraffenew.data.entities.ModbusLog
import kotlinx.coroutines.flow.Flow

/**
 * DAO для работы с логами системы Modbus
 * 
 * Обеспечивает:
 * - Высокопроизводительную запись логов
 * - Фильтрацию по уровню и категории
 * - Автоматическую ротацию старых записей
 * - Поиск и аналитику событий
 */
@Dao
interface ModbusLogDao {
    
    /**
     * Вставить новый лог
     */
    @Insert
    suspend fun insert(log: ModbusLog): Long
    
    /**
     * Batch insert для высокой производительности
     */
    @Insert
    suspend fun insertAll(logs: List<ModbusLog>)
    
    /**
     * Получить все логи отсортированные по времени (DESC)
     */
    @Query("SELECT * FROM modbus_logs ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentLogs(limit: Int = 1000): Flow<List<ModbusLog>>
    
    /**
     * Получить логи по уровню
     */
    @Query("SELECT * FROM modbus_logs WHERE level = :level ORDER BY timestamp DESC LIMIT :limit")
    fun getLogsByLevel(level: LogLevel, limit: Int = 500): Flow<List<ModbusLog>>
    
    /**
     * Получить логи по категории
     */
    @Query("SELECT * FROM modbus_logs WHERE category = :category ORDER BY timestamp DESC LIMIT :limit")
    fun getLogsByCategory(category: LogCategory, limit: Int = 500): Flow<List<ModbusLog>>
    
    /**
     * Получить логи за определенный период
     */
    @Query("""
        SELECT * FROM modbus_logs 
        WHERE timestamp BETWEEN :startTime AND :endTime 
        ORDER BY timestamp DESC 
        LIMIT :limit
    """)
    suspend fun getLogsByTimeRange(
        startTime: Long, 
        endTime: Long, 
        limit: Int = 1000
    ): List<ModbusLog>
    
    /**
     * Получить логи ошибок за последний час
     */
    @Query("""
        SELECT * FROM modbus_logs 
        WHERE level IN ('ERROR', 'FATAL') 
        AND timestamp > :sinceTime 
        ORDER BY timestamp DESC
    """)
    suspend fun getRecentErrors(sinceTime: Long): List<ModbusLog>
    
    /**
     * Получить логи для конкретного регистра
     */
    @Query("""
        SELECT * FROM modbus_logs 
        WHERE registerAddress = :address 
        ORDER BY timestamp DESC 
        LIMIT :limit
    """)
    suspend fun getLogsForRegister(address: Int, limit: Int = 100): List<ModbusLog>
    
    /**
     * Получить статистику логов по уровням
     */
    @Query("""
        SELECT level, COUNT(*) as count 
        FROM modbus_logs 
        WHERE timestamp > :sinceTime 
        GROUP BY level
    """)
    suspend fun getLogStatistics(sinceTime: Long): List<LogLevelCount>
    
    /**
     * Получить количество ошибок за период
     */
    @Query("""
        SELECT COUNT(*) FROM modbus_logs 
        WHERE level IN ('ERROR', 'FATAL') 
        AND timestamp BETWEEN :startTime AND :endTime
    """)
    suspend fun getErrorCount(startTime: Long, endTime: Long): Int
    
    /**
     * Поиск логов по тексту сообщения
     */
    @Query("""
        SELECT * FROM modbus_logs 
        WHERE message LIKE '%' || :searchText || '%' 
        OR details LIKE '%' || :searchText || '%' 
        ORDER BY timestamp DESC 
        LIMIT :limit
    """)
    suspend fun searchLogs(searchText: String, limit: Int = 200): List<ModbusLog>
    
    /**
     * Удалить старые логи (старше указанного времени)
     */
    @Query("DELETE FROM modbus_logs WHERE timestamp < :beforeTime")
    suspend fun deleteLogsBefore(beforeTime: Long): Int
    
    /**
     * Удалить логи определенного уровня старше указанного времени
     */
    @Query("""
        DELETE FROM modbus_logs 
        WHERE level = :level AND timestamp < :beforeTime
    """)
    suspend fun deleteLogsByLevelBefore(level: LogLevel, beforeTime: Long): Int
    
    /**
     * Получить общее количество логов
     */
    @Query("SELECT COUNT(*) FROM modbus_logs")
    suspend fun getTotalLogCount(): Int
    
    /**
     * Получить размер таблицы логов (приблизительно)
     */
    @Query("SELECT COUNT(*) * 500 as estimated_size_bytes FROM modbus_logs")
    suspend fun getEstimatedTableSize(): Long
    
    /**
     * Очистка логов с умной ротацией
     * Удаляет DEBUG логи старше 24 часов
     * INFO логи старше 7 дней  
     * WARN логи старше 30 дней
     * Оставляет ERROR и FATAL логи
     */
    @Transaction
    suspend fun performLogRotation() {
        val now = System.currentTimeMillis()
        val oneDayAgo = now - (24 * 60 * 60 * 1000L)
        val sevenDaysAgo = now - (7 * 24 * 60 * 60 * 1000L)
        val thirtyDaysAgo = now - (30 * 24 * 60 * 60 * 1000L)
        
        // Удаляем DEBUG логи старше 24 часов
        deleteLogsByLevelBefore(LogLevel.DEBUG, oneDayAgo)
        
        // Удаляем INFO логи старше 7 дней
        deleteLogsByLevelBefore(LogLevel.INFO, sevenDaysAgo)
        
        // Удаляем WARN логи старше 30 дней
        deleteLogsByLevelBefore(LogLevel.WARN, thirtyDaysAgo)
        
        // ERROR и FATAL логи не удаляем автоматически
    }
    
    /**
     * Экстренная очистка при переполнении
     * Оставляет только критически важные логи
     */
    @Query("""
        DELETE FROM modbus_logs 
        WHERE id NOT IN (
            SELECT id FROM modbus_logs 
            WHERE level IN ('ERROR', 'FATAL')
            UNION ALL
            SELECT id FROM (
                SELECT id FROM modbus_logs 
                WHERE level = 'WARN' 
                ORDER BY timestamp DESC 
                LIMIT 1000 )
            UNION ALL
            SELECT id FROM (
                SELECT id FROM modbus_logs 
                WHERE level = 'INFO' 
                ORDER BY timestamp DESC 
                LIMIT 500)
            )
    """)
    suspend fun performEmergencyCleanup(): Int
}

/**
 * Data class для статистики логов
 */
data class LogLevelCount(
    val level: LogLevel,
    val count: Int
)