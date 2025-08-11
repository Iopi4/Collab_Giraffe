package com.example.giraffenew.data.entities

import kotlinx.serialization.json.Json
import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests для RegisterConfiguration и связанных data classes
 * 
 * Тестирует:
 * - Serialization/deserialization JSON конфигураций
 * - Default конфигурацию
 * - Validation логику
 * - Data integrity
 */
class RegisterConfigurationTest {
    
    private val json = Json { 
        ignoreUnknownKeys = true
        prettyPrint = true 
    }
    
    @Test
    fun `default configuration creates correctly`() {
        // When
        val defaultConfig = DefaultRegisterConfiguration.createDefault()
        
        // Then
        assertEquals("Default Crane Configuration", defaultConfig.name)
        assertTrue(defaultConfig.isActive)
        assertTrue(defaultConfig.jsonConfiguration.isNotEmpty())
        assertTrue(defaultConfig.createdAt > 0)
        assertTrue(defaultConfig.updatedAt > 0)
    }
    
    @Test
    fun `default configuration JSON is valid`() {
        // Given
        val defaultConfig = DefaultRegisterConfiguration.createDefault()
        
        // When/Then - должно успешно парситься
        val registersConfig = json.decodeFromString<RegistersConfiguration>(
            defaultConfig.jsonConfiguration
        )
        
        assertTrue(registersConfig.registers.isNotEmpty())
        assertEquals(11, registersConfig.registers.size) // Согласно default конфигурации
    }
    
    @Test
    fun `default configuration contains expected registers`() {
        // Given
        val defaultConfig = DefaultRegisterConfiguration.createDefault()
        val registersConfig = json.decodeFromString<RegistersConfiguration>(
            defaultConfig.jsonConfiguration
        )
        
        // When
        val registerAddresses = registersConfig.registers.map { it.address }
        
        // Then - проверяем ключевые регистры согласно техзаданию
        assertTrue(registerAddresses.contains(90)) // hoist_position
        assertTrue(registerAddresses.contains(91)) // trolley_position
        assertTrue(registerAddresses.contains(92)) // slewing_angle
        assertTrue(registerAddresses.contains(93)) // load_weight
    }
    
    @Test
    fun `register info serialization works correctly`() {
        // Given
        val registerInfo = RegisterInfo(
            address = 90,
            name = "test_register",
            type = "INT16",
            description = "Test register description"
        )
        
        // When
        val jsonString = json.encodeToString(RegisterInfo.serializer(), registerInfo)
        val deserializedRegister = json.decodeFromString<RegisterInfo>(jsonString)
        
        // Then
        assertEquals(registerInfo.address, deserializedRegister.address)
        assertEquals(registerInfo.name, deserializedRegister.name)
        assertEquals(registerInfo.type, deserializedRegister.type)
        assertEquals(registerInfo.description, deserializedRegister.description)
    }
    
    @Test
    fun `registers configuration serialization works correctly`() {
        // Given
        val registers = listOf(
            RegisterInfo(90, "hoist_position", "INT16", "Позиция лебедки"),
            RegisterInfo(91, "trolley_position", "INT16", "Позиция тележки"),
            RegisterInfo(92, "slewing_angle", "INT16", "Угол поворота")
        )
        val config = RegistersConfiguration(registers)
        
        // When
        val jsonString = json.encodeToString(RegistersConfiguration.serializer(), config)
        val deserializedConfig = json.decodeFromString<RegistersConfiguration>(jsonString)
        
        // Then
        assertEquals(registers.size, deserializedConfig.registers.size)
        
        for (i in registers.indices) {
            val original = registers[i]
            val deserialized = deserializedConfig.registers[i]
            
            assertEquals(original.address, deserialized.address)
            assertEquals(original.name, deserialized.name)
            assertEquals(original.type, deserialized.type)
        }
    }
    
    @Test
    fun `register configuration data class creates correctly`() {
        // Given
        val name = "Test Configuration"
        val jsonConfig = """{"registers": [{"address": 90, "name": "test", "type": "INT16"}]}"""
        val timestamp = System.currentTimeMillis()
        
        // When
        val config = RegisterConfiguration(
            name = name,
            jsonConfiguration = jsonConfig,
            isActive = true,
            createdAt = timestamp,
            updatedAt = timestamp
        )
        
        // Then
        assertEquals(name, config.name)
        assertEquals(jsonConfig, config.jsonConfiguration)
        assertTrue(config.isActive)
        assertEquals(timestamp, config.createdAt)
        assertEquals(timestamp, config.updatedAt)
        assertEquals(0L, config.id) // Auto-generate should be 0 initially
    }
    
    @Test
    fun `register configuration with default values`() {
        // Given/When
        val config = RegisterConfiguration(
            name = "Test",
            jsonConfiguration = "{}"
        )
        
        // Then
        assertEquals("Test", config.name)
        assertEquals("{}", config.jsonConfiguration)
        assertFalse(config.isActive) // Default should be false
        assertTrue(config.createdAt > 0)
        assertTrue(config.updatedAt > 0)
        assertEquals(config.createdAt, config.updatedAt) // Should be same initially
    }
    
    @Test
    fun `json parsing handles unknown fields gracefully`() {
        // Given - JSON с дополнительными полями
        val jsonWithExtra = """
            {
                "registers": [
                    {
                        "address": 90,
                        "name": "test_register",
                        "type": "INT16",
                        "description": "Test",
                        "extra_field": "should_be_ignored",
                        "another_extra": 123
                    }
                ],
                "unknown_top_level": "ignored"
            }
        """.trimIndent()
        
        // When/Then - должно успешно парситься, игнорируя лишние поля
        val config = json.decodeFromString<RegistersConfiguration>(jsonWithExtra)
        
        assertEquals(1, config.registers.size)
        val register = config.registers[0]
        assertEquals(90, register.address)
        assertEquals("test_register", register.name)
        assertEquals("INT16", register.type)
    }
}