package com.example.giraffenew.data.dao

import androidx.room.*
import com.example.giraffenew.data.entities.RegisterConfiguration
import kotlinx.coroutines.flow.Flow

/**
 * DAO для работы с конфигурацией регистров Modbus
 * 
 * Обеспечивает:
 * - CRUD операции с конфигурациями
 * - Reactive updates через Flow
 * - Управление активной конфигурацией
 * - Hot-reload для изменений конфигурации
 */
@Dao
interface RegisterConfigurationDao {
    
    /**
     * Получить активную конфигурацию регистров
     * Reactive - автоматически обновляется при изменениях
     */
    @Query("SELECT * FROM register_configurations WHERE isActive = 1 LIMIT 1")
    fun getActiveConfigurationFlow(): Flow<RegisterConfiguration?>
    
    /**
     * Получить активную конфигурацию (suspend для single read)
     */
    @Query("SELECT * FROM register_configurations WHERE isActive = 1 LIMIT 1")
    suspend fun getActiveConfiguration(): RegisterConfiguration?
    
    /**
     * Получить все конфигурации
     */
    @Query("SELECT * FROM register_configurations ORDER BY updatedAt DESC")
    fun getAllConfigurations(): Flow<List<RegisterConfiguration>>
    
    /**
     * Получить конфигурацию по ID
     */
    @Query("SELECT * FROM register_configurations WHERE id = :id")
    suspend fun getConfigurationById(id: Long): RegisterConfiguration?
    
    /**
     * Получить конфигурацию по имени
     */
    @Query("SELECT * FROM register_configurations WHERE name = :name")
    suspend fun getConfigurationByName(name: String): RegisterConfiguration?
    
    /**
     * Вставить новую конфигурацию
     */
    @Insert
    suspend fun insert(configuration: RegisterConfiguration): Long
    
    /**
     * Обновить конфигурацию
     */
    @Update
    suspend fun update(configuration: RegisterConfiguration)
    
    /**
     * Удалить конфигурацию
     */
    @Delete
    suspend fun delete(configuration: RegisterConfiguration)
    
    /**
     * Удалить конфигурацию по ID
     */
    @Query("DELETE FROM register_configurations WHERE id = :id")
    suspend fun deleteById(id: Long)
    
    /**
     * Установить конфигурацию активной (деактивирует все остальные)
     * Транзакция обеспечивает атомарность операции
     */
    @Transaction
    suspend fun setActiveConfiguration(configurationId: Long) {
        // Сначала деактивируем все конфигурации
        deactivateAllConfigurations()
        // Затем активируем нужную
        activateConfiguration(configurationId)
    }
    
    /**
     * Деактивировать все конфигурации
     */
    @Query("UPDATE register_configurations SET isActive = 0, updatedAt = :timestamp")
    suspend fun deactivateAllConfigurations(timestamp: Long = System.currentTimeMillis())
    
    /**
     * Активировать конфигурацию по ID
     */
    @Query("UPDATE register_configurations SET isActive = 1, updatedAt = :timestamp WHERE id = :id")
    suspend fun activateConfiguration(id: Long, timestamp: Long = System.currentTimeMillis())
    
    /**
     * Проверить существует ли активная конфигурация
     */
    @Query("SELECT COUNT(*) FROM register_configurations WHERE isActive = 1")
    suspend fun hasActiveConfiguration(): Int
    
    /**
     * Получить количество всех конфигураций
     */
    @Query("SELECT COUNT(*) FROM register_configurations")
    suspend fun getConfigurationCount(): Int
    
    /**
     * Очистить старые неактивные конфигурации (оставить только последние N)
     */
    @Query("""
        DELETE FROM register_configurations 
        WHERE isActive = 0 
        AND id NOT IN (
            SELECT id FROM register_configurations 
            WHERE isActive = 0 
            ORDER BY updatedAt DESC 
            LIMIT :keepCount
        )
    """)
    suspend fun cleanupOldConfigurations(keepCount: Int = 10)
    
    /**
     * Создать backup активной конфигурации перед изменением
     */
    @Transaction
    suspend fun backupActiveConfiguration(): Long? {
        val activeConfig = getActiveConfiguration()
        return activeConfig?.let { config ->
            val backup = config.copy(
                id = 0, // Новая запись
                name = "${config.name} - Backup ${System.currentTimeMillis()}",
                isActive = false,
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
            insert(backup)
        }
    }
}