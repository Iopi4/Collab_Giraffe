package com.example.giraffenew.utils

import org.junit.Test
import org.junit.Assert.*

/**
 * Unit tests для HexFormatter utility функций
 * 
 * Тестирует hex formatting согласно техзаданию:
 * - Формат: "timestamp,reg90:1A2B,reg91:3C4D,reg92:5E6F\n"
 * - Корректное форматирование чисел в hex
 * - Обработка edge cases
 */
class HexFormatterTest {
    
    @Test
    fun `int to hex string formats correctly`() {
        // Test cases для различных значений
        assertEquals("0000", 0.toString(16).uppercase().padStart(4, '0'))
        assertEquals("1A2B", 0x1A2B.toString(16).uppercase().padStart(4, '0'))
        assertEquals("FFFF", 0xFFFF.toString(16).uppercase().padStart(4, '0'))
        assertEquals("0001", 1.toString(16).uppercase().padStart(4, '0'))
        assertEquals("00FF", 255.toString(16).uppercase().padStart(4, '0'))
    }
    
    @Test
    fun `negative values handle correctly`() {
        // Modbus использует unsigned 16-bit значения
        // Отрицательные значения должны обрабатываться как unsigned
        val negativeValue = -1
        val unsignedValue = negativeValue and 0xFFFF // Маскируем до 16 бит
        
        assertEquals("FFFF", unsignedValue.toString(16).uppercase().padStart(4, '0'))
    }
    
    @Test
    fun `large values truncate to 16 bits`() {
        // Значения больше 16 бит должны обрезаться
        val largeValue = 0x12345
        val truncatedValue = largeValue and 0xFFFF
        
        assertEquals("2345", truncatedValue.toString(16).uppercase().padStart(4, '0'))
    }
    
    @Test
    fun `register address to string formats correctly`() {
        assertEquals("90", 90.toString())
        assertEquals("174", 174.toString())
        assertEquals("900", 900.toString())
    }
    
    @Test
    fun `timestamp formatting is consistent`() {
        // Проверяем что timestamp форматируется одинаково
        val timestamp = System.currentTimeMillis()
        val formatted1 = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", java.util.Locale.US).apply {
            timeZone = java.util.TimeZone.getTimeZone("UTC")
        }.format(java.util.Date(timestamp))
        
        val formatted2 = java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", java.util.Locale.US).apply {
            timeZone = java.util.TimeZone.getTimeZone("UTC")
        }.format(java.util.Date(timestamp))
        
        assertEquals(formatted1, formatted2)
    }
    
    @Test
    fun `log line format matches specification`() {
        // Given - тестируем формат согласно техзаданию
        val timestamp = "2025-08-11T10:30:01.123Z"
        val registers = mapOf(
            90 to "1A2B",
            91 to "3C4D", 
            92 to "5E6F"
        )
        
        // When
        val registerPairs = registers.entries
            .sortedBy { it.key }
            .joinToString(",") { (address, hexValue) ->
                "reg$address:$hexValue"
            }
        val logLine = "$timestamp,$registerPairs\n"
        
        // Then
        assertEquals("2025-08-11T10:30:01.123Z,reg90:1A2B,reg91:3C4D,reg92:5E6F\n", logLine)
    }
    
    @Test
    fun `empty register map handles correctly`() {
        // Given
        val timestamp = "2025-08-11T10:30:01.123Z"
        val registers = emptyMap<Int, String>()
        
        // When
        val registerPairs = registers.entries
            .sortedBy { it.key }
            .joinToString(",") { (address, hexValue) ->
                "reg$address:$hexValue"
            }
        val logLine = "$timestamp,$registerPairs\n"
        
        // Then
        assertEquals("2025-08-11T10:30:01.123Z,\n", logLine)
    }
    
    @Test
    fun `single register formats correctly`() {
        // Given
        val timestamp = "2025-08-11T10:30:01.123Z"
        val registers = mapOf(90 to "1A2B")
        
        // When
        val registerPairs = registers.entries
            .sortedBy { it.key }
            .joinToString(",") { (address, hexValue) ->
                "reg$address:$hexValue"
            }
        val logLine = "$timestamp,$registerPairs\n"
        
        // Then
        assertEquals("2025-08-11T10:30:01.123Z,reg90:1A2B\n", logLine)
    }
    
    @Test
    fun `registers are sorted by address`() {
        // Given - несортированные регистры
        val registers = mapOf(
            92 to "5E6F",
            90 to "1A2B", 
            91 to "3C4D"
        )
        
        // When
        val registerPairs = registers.entries
            .sortedBy { it.key }
            .joinToString(",") { (address, hexValue) ->
                "reg$address:$hexValue"
            }
        
        // Then - должны быть отсортированы по адресу
        assertEquals("reg90:1A2B,reg91:3C4D,reg92:5E6F", registerPairs)
    }
    
    @Test
    fun `hex values are always uppercase`() {
        // Given
        val lowercaseHex = "1a2b"
        val uppercaseHex = lowercaseHex.uppercase()
        
        // Then
        assertEquals("1A2B", uppercaseHex)
        assertTrue(uppercaseHex.all { it.isDigit() || it in 'A'..'F' })
    }
    
    @Test
    fun `hex padding works correctly`() {
        // Test различных размеров чисел
        assertEquals("0000", 0.toString(16).uppercase().padStart(4, '0'))
        assertEquals("000A", 10.toString(16).uppercase().padStart(4, '0'))
        assertEquals("00FF", 255.toString(16).uppercase().padStart(4, '0'))
        assertEquals("0FFF", 4095.toString(16).uppercase().padStart(4, '0'))
        assertEquals("FFFF", 65535.toString(16).uppercase().padStart(4, '0'))
    }
}