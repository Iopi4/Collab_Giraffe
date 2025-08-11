# ModbusBackgroundService - Техническое задание для AI агента

## Цель задачи
Создать промышленный фоновый сервис для непрерывного опроса контроллера крана по протоколу Modbus TCP с:
- Загрузкой конфигурации регистров из базы данных
- Циклическим чтением регистров каждую секунду
- Записью данных на флэш-карту в hex формате
- Отправкой данных в облако каждые 30 секунд

<service_requirements>
  <core_functionality>
    <requirement id="SR-001" priority="critical">
      <title>JSON Configuration Loading</title>
      <description>
        При запуске сервиса загружать JSON конфигурацию из базы данных Room
        с перечнем регистров для опроса
      </description>
      <implementation>
        - Создать Entity RegisterConfiguration в Room database
        - JSON структура: {"registers": [{"address": 90, "name": "hoist_position", "type": "INT16"}]}
        - Кэширование конфигурации в памяти
        - Hot-reload при изменении конфигурации
      </implementation>
    </requirement>

    <requirement id="SR-002" priority="critical">
      <title>Cyclic Modbus Reading</title>
      <description>
        Каждую секунду читать все регистры из конфигурации по Modbus TCP
      </description>
      <implementation>
        - Timer-based scheduling с точным интервалом 1000ms
        - Batch reading для оптимизации (chunks по 32 регистра)
        - Automatic reconnection при обрывах связи
        - Timeout handling для каждого запроса
      </implementation>
      <connection_params>
        - IP: 192.168.1.88 (configurable)
        - Port: 502 (configurable)
        - Slave ID: 255 (configurable)
        - Timeout: 2000ms
      </connection_params>
    </requirement>

    <requirement id="SR-003" priority="critical">
      <title>In-Memory Register Cache</title>
      <description>
        Обновлять кэш регистров в памяти для быстрого доступа из других частей приложения
      </description>
      <implementation>
        - Thread-safe SharedFlow для реактивных обновлений
        - ConcurrentHashMap для fast lookup by address
        - StateFlow для UI binding
        - Memory-efficient circular buffer для истории
      </implementation>
    </requirement>

    <requirement id="SR-004" priority="high">
      <title>Flash Storage Logging</title>
      <description>
        Записывать данные на флэш-карту с временной меткой в hex формате
      </description>
      <implementation>
        - File format: "timestamp,reg90:1A2B,reg91:3C4D,reg92:5E6F\n"
        - UTF-8 encoding with BOM
        - File rotation по размеру (10MB max per file)
        - Compression для старых файлов
        - Error resilient writing (temp files + atomic moves)
      </implementation>
      <file_structure>
        /Android/data/com.example.giraffenew/files/modbus_logs/
        ├── current.log
        ├── 2025-08-11_00.log.gz
        └── 2025-08-10_23.log.gz
      </file_structure>
    </requirement>

    <requirement id="SR-005" priority="high">
      <title>Cloud Data Transmission</title>
      <description>
        Каждые 30 секунд отправлять накопленные данные в облачный сервис
      </description>
      <implementation>
        - Endpoint: https://monitor.giraffecrane.ru/api/crane/registers
        - JSON payload с массивом записей за 30-секундный период
        - Retry logic с exponential backoff
        - Offline queue с persistent storage
        - Data compression (gzip) для больших payload
      </implementation>
      <payload_format>
        {
          "crane_id": "GIRAFFE_001",
          "readings": [
            {
              "timestamp": "2025-08-11T10:30:01.123Z",
              "registers": {
                "90": "1A2B",
                "91": "3C4D",
                "92": "5E6F"
              }
            }
          ]
        }
      </payload_format>
    </requirement>
  </core_functionality>

  <quality_requirements>
    <requirement id="QR-001" priority="critical">
      <title>Industrial Grade Reliability</title>
      <description>24/7 operation без сбоев в промышленной среде</description>
      <implementation>
        - Automatic service restart при критических ошибках
        - Graceful degradation при частичных сбоях
        - Comprehensive error logging с ротацией
        - Health monitoring с самодиагностикой
        - Memory leak prevention
      </implementation>
    </requirement>

    <requirement id="QR-002" priority="high">
      <title>Performance Requirements</title>
      <description>Соответствие требованиям реального времени</description>
      <metrics>
        - Reading cycle accuracy: ±50ms от 1000ms
        - Memory usage: <100MB в steady state
        - CPU usage: <10% в steady state
        - Network latency tolerance: 500ms max
      </metrics>
    </requirement>

    <requirement id="QR-003" priority="medium">
      <title>Configuration Flexibility</title>
      <description>Настраиваемость без перекомпиляции</description>
      <implementation>
        - Runtime configuration updates
        - Validation всех параметров
        - Fallback к default values
        - Configuration backup/restore
      </implementation>
    </requirement>
  </quality_requirements>

  <integration_requirements>
    <requirement id="IR-001" priority="critical">
      <title>Room Database Integration</title>
      <description>Интеграция с существующей Room database</description>
      <implementation>
        - Create RegisterConfiguration entity
        - Create ModbusLog entity для аудита
        - Repository pattern для data access
        - Migration support для schema updates
      </implementation>
    </requirement>

    <requirement id="IR-002" priority="high">
      <title>Hilt Dependency Injection</title>
      <description>Интеграция с существующим Hilt DI</description>
      <implementation>
        - @Singleton service instance
        - Inject Repository и NetworkClient
        - Proper scope management
        - Lifecycle awareness
      </implementation>
    </requirement>

    <requirement id="IR-003" priority="high">
      <title>Modbus Protocol Compatibility</title>
      <description>Совместимость с существующей Modbus реализацией</description>
      <implementation>
        - Reuse existing ModbusViewModel TCP code
        - Extract к shared ModbusClient
        - Maintain protocol compatibility
        - Support same register ranges (90-174, 900-901)
      </implementation>
    </requirement>
  </integration_requirements>

  <error_handling_requirements>
    <requirement id="EH-001" priority="critical">
      <title>Network Error Handling</title>
      <scenarios>
        - Connection timeout: Retry с backoff
        - Network unavailable: Queue operations, retry когда available
        - Malformed responses: Log error, continue с next register
        - Controller restart: Auto-reconnect protocol
      </scenarios>
    </requirement>

    <requirement id="EH-002" priority="critical">
      <title>Storage Error Handling</title>
      <scenarios>
        - Disk full: Archive old logs, continue writing
        - File corruption: Create new file, log incident
        - Permission denied: Request permissions, fallback location
        - SD card removed: Detect insertion, resume logging
      </scenarios>
    </requirement>

    <requirement id="EH-003" priority="high">
      <title>Cloud Service Error Handling</title>
      <scenarios>
        - HTTP 5xx errors: Exponential backoff retry
        - HTTP 4xx errors: Log error, skip problematic data
        - Network timeout: Queue для retry
        - Rate limiting: Respect retry-after headers
      </scenarios>
    </requirement>
  </error_handling_requirements>

  <testing_requirements>
    <requirement id="TR-001" priority="high">
      <title>Unit Testing</title>
      <coverage>
        - ModbusClient: Mock TCP connections
        - DataProcessor: Test hex formatting
        - CloudClient: Mock HTTP responses
        - Configuration: Validate JSON parsing
      </coverage>
    </requirement>

    <requirement id="TR-002" priority="medium">
      <title>Integration Testing</title>
      <scenarios>
        - End-to-end data flow
        - Database operations
        - File system operations
        - Network connectivity
      </scenarios>
    </requirement>

    <requirement id="TR-003" priority="medium">
      <title>Performance Testing</title>
      <metrics>
        - Memory usage profiling
        - CPU usage monitoring
        - Network bandwidth utilization
        - Storage I/O performance
      </metrics>
    </requirement>
  </testing_requirements>
</service_requirements>

## Архитектурный дизайн

### Компоненты сервиса
```kotlin
// Service Architecture
ModbusBackgroundService
├── ModbusClient (TCP communication)
├── RegisterCache (In-memory state)
├── FileLogger (Flash storage)
├── CloudSynchronizer (Remote sync)
├── ConfigurationManager (JSON config)
└── HealthMonitor (Service diagnostics)
```

### Data Flow
```
Room DB (Config) → ConfigurationManager
      ↓
ModbusClient → RegisterCache → FileLogger
                    ↓
              CloudSynchronizer (30s interval)
```

### Threading Model
```
- Main Thread: Service lifecycle management
- ModbusThread: TCP communication (1s interval)
- FileThread: Disk I/O operations  
- CloudThread: HTTP communications (30s interval)
- CacheThread: Memory operations
```

## Файловая структура

### Новые файлы для создания
```
app/src/main/java/com/example/giraffenew/
├── services/
│   ├── ModbusBackgroundService.kt
│   └── ServiceModule.kt (Hilt)
├── data/
│   ├── entities/
│   │   ├── RegisterConfiguration.kt
│   │   └── ModbusLog.kt
│   ├── dao/
│   │   └── RegisterConfigurationDao.kt
│   └── repositories/
│       └── ModbusRepository.kt
├── network/
│   ├── ModbusClient.kt
│   └── CloudApiService.kt
└── utils/
    ├── HexFormatter.kt
    └── FileManager.kt
```

### Изменения в существующих файлах
```
- ModbusViewModel.kt: Refactor для использования нового сервиса
- MainActivity.kt: Добавить service binding
- AndroidManifest.xml: Регистрация сервиса
- build.gradle.kts: Новые зависимости если нужны
```

## Критерии приемки

### Функциональные
- ✅ Сервис запускается автоматически при старте приложения
- ✅ JSON конфигурация загружается из Room database
- ✅ Modbus регистры читаются каждую секунду
- ✅ Данные доступны через SharedFlow для других компонентов
- ✅ Hex данные записываются на флэш с timestamp
- ✅ Cloud sync происходит каждые 30 секунд

### Нефункциональные  
- ✅ Сервис работает 24/7 без сбоев
- ✅ Memory usage <100MB
- ✅ CPU usage <10%
- ✅ Reading accuracy ±50ms от 1000ms
- ✅ Автоматическое восстановление после ошибок

### Интеграционные
- ✅ Совместимость с существующим ModbusViewModel
- ✅ Hilt dependency injection
- ✅ Room database integration  
- ✅ Existing UI continues working

---

## Implementation Notes для агента

<agent_guidance>
  <architecture_pattern>
    Следуй существующим паттернам в codebase:
    - Hilt для DI
    - Kotlin coroutines для async operations
    - StateFlow/SharedFlow для reactive data
    - Room для database operations
    - Ktor для network requests
  </architecture_pattern>
  
  <code_quality>
    - Null safety везде
    - Structured concurrency с proper scopes
    - Resource cleanup в finally блоках
    - Extensive error handling
    - Logging для debugging и monitoring
  </code_quality>
  
  <industrial_focus>
    - Prioritize reliability over performance
    - Graceful degradation при ошибках
    - Comprehensive status reporting
    - Easy troubleshooting и monitoring
  </industrial_focus>
  
  <testing_strategy>
    - Unit tests для каждого компонента
    - Mock external dependencies
    - Test error scenarios thoroughly
    - Performance benchmarking
  </testing_strategy>
</agent_guidance>

---

**Task Assignment**: Background Services Agent  
**Priority**: Critical (foundation for real-time operations)  
**Estimated Effort**: 2-3 days for full implementation  
**Dependencies**: Room database setup (can be done in parallel)