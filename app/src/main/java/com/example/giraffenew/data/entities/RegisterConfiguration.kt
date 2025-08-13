package com.example.giraffenew.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Room entity для хранения конфигурации регистров Modbus
 * 
 * Хранит JSON конфигурацию согласно техзаданию:
 * {"registers": [{"address": 90, "name": "hoist_position", "type": "INT16"}]}
 * 
 * @param id Primary key для Room
 * @param name Название конфигурации для удобства идентификации 
 * @param jsonConfiguration JSON строка с конфигурацией регистров
 * @param isActive Флаг активности конфигурации (только одна может быть активной)
 * @param createdAt Timestamp создания
 * @param updatedAt Timestamp последнего обновления
 */
@Entity(tableName = "register_configurations")
@Serializable
data class RegisterConfiguration(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    
    val name: String,
    
    val jsonConfiguration: String, // JSON с регистрами
    
    val isActive: Boolean = false,
    
    val createdAt: Long = System.currentTimeMillis(),
    
    val updatedAt: Long = System.currentTimeMillis()
)

/**
 * Data class для представления одного регистра из JSON конфигурации
 */
@Serializable
data class RegisterInfo(
    val address: Int,
    val name: String,
    val type: String, // INT16, UINT16, INT32, UINT32, FLOAT, etc.
    val description: String = ""
)

/**
 * Data class для представления полной конфигурации регистров
 */
@Serializable
data class RegistersConfiguration(
    val registers: List<RegisterInfo>
) {
    companion object
}

/**
 * Default конфигурация для системы крана согласно техзаданию
 */
object DefaultRegisterConfiguration {
    val DEFAULT_CONFIG_JSON = """{
        "registers": [
            {"address": 90, "name": "hoist_position", "type": "INT16", "description": "Позиция подъема лебедки"},
            {"address": 91, "name": "trolley_position", "type": "INT16", "description": "Позиция тележки"},
            {"address": 92, "name": "slewing_angle", "type": "INT16", "description": "Угол поворота стрелы"},
            {"address": 93, "name": "load_weight", "type": "INT16", "description": "Вес груза на крюке"},
            {"address": 94, "name": "boom_angle", "type": "INT16", "description": "Угол наклона стрелы"},
            {"address": 95, "name": "wind_speed", "type": "INT16", "description": "Скорость ветра"},
            {"address": 96, "name": "system_status", "type": "INT16", "description": "Статус системы"},
            {"address": 97, "name": "alarm_flags", "type": "INT16", "description": "Флаги тревог"},
            {"address": 98, "name": "motor_current_hoist", "type": "INT16", "description": "Ток двигателя лебедки"},
            {"address": 99, "name": "motor_current_trolley", "type": "INT16", "description": "Ток двигателя тележки"},
            {"address": 100, "name": "motor_current_slewing", "type": "INT16", "description": "Ток двигателя поворота"} 
            ] 
            }""".trimIndent()
    
    fun createDefault(): RegisterConfiguration {
        return RegisterConfiguration(
            name = "Default Crane Configuration",
            jsonConfiguration = DEFAULT_CONFIG_JSON,
            isActive = true
        )
    }
}