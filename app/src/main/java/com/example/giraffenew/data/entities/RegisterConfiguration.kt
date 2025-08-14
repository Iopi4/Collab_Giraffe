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
data class RegisterInfo( //
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
            {"address": 90, "name": "gv_wYear_MW", "type": "INT16", "description": "gv_wYear_MW"},
            {"address": 91, "name": "gv_wMonth_MW", "type": "INT16", "description": "gv_wMonth_MW"},
            {"address": 94, "name": "gv_wDay_MW", "type": "INT16", "description": "gv_wDay_MW"},
            {"address": 95, "name": "gv_wHour_MW", "type": "INT16", "description": "gv_wHour_MW"},
            {"address": 98, "name": "gv_wMinute_MW", "type": "INT16", "description": "gv_wMinute_MW"},
            {"address": 99, "name": "gv_wSeconds_MW", "type": "INT16", "description": "gv_wSeconds_MW"},
            {"address": 100, "name": "Hoist_StatusWord_MW", "type": "INT16", "description": "Слово состояния ПЧ подъема "},
            {"address": 101, "name": "Hoist_ControlWord_MW", "type": "INT16", "description": "Слово управления ПЧ подъема"},
            {"address": 102, "name": "Hoist_TargetVelocity_MW", "type": "INT16", "description": "Заданная скорость вращения подъема"},
            {"address": 103, "name": "Hoist_ControlEffort_MW", "type": "INT16", "description": "Текущая скорость вращения подъема"},
            {"address": 104, "name": "Hoist_MotorCurrent_MW", "type": "INT16", "description": "Ток двигателя подъема"} 
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