# Services and Data Layer Analysis - GIRAFFE Tower Crane App

## Background Services Analysis

### Current Implementation Status: ⚠️ Needs Refactoring

#### Service Architecture Issue
**Problem**: Background services are implemented within `ModbusViewModel` instead of proper Android Services.

**Current Location**: `app/src/main/java/com/example/giraffenew/modbus_tools/ModbusViewModel.kt`

---

### ModbusViewModel Service Functions

#### 1. Background Register Reading Service
```kotlin
// Function: startBackgroundService()
```
**Purpose**: Continuously read sensor data from PLC controller
**Operation**:
- Reads registers 90-174 in chunks of 32 registers
- Continuous loop with coroutine scope
- Real-time data collection from crane sensors
- Updates StateFlow for UI consumption

**Technical Details**:
- **Register Range**: 90-174 (sensor data, mechanism status)
- **Chunk Size**: 32 registers per read operation
- **Protocol**: Modbus TCP Function Code 03 (Read Holding Registers)
- **Connection**: 192.168.1.88:502, Slave ID: 255

#### 2. Background Register Writing Service  
```kotlin
// Function: startBackgroundServiceWrite()
```
**Purpose**: Write control commands to crane controller
**Operation**:
- Writes to registers 900-901 for crane control
- Background command execution
- Queue-based command processing

**Technical Details**:
- **Register Range**: 900-901 (control commands)
- **Protocol**: Modbus TCP Function Code 06 (Write Single Register)
- **Operation Mode**: Background continuous writing

#### 3. Remote Server Synchronization
```kotlin
// Function: sendRegistersToRemoteServer()
```
**Purpose**: Transmit collected data to remote monitoring server
**Operation**:
- Sends register data to GIRAFFE monitoring system
- JSON payload with timestamps
- Automated data archiving

**Technical Details**:
- **Server URL**: `https://monitor.giraffecrane.ru`
- **Protocol**: HTTPS POST with JSON payload
- **Data Format**: RegisterData with timestamp
- **Payload Structure**:
  ```json
  {
    "timestamp": "2025-08-11T10:30:00Z",
    "registers": [
      {"address": 90, "value": 1234},
      {"address": 91, "value": 5678}
    ]
  }
  ```

---

### Modbus TCP Implementation Details

#### Protocol Implementation
**File**: `ModbusViewModel.kt` (lines ~200-400)

#### MBAP Header Construction
```kotlin
// Custom MBAP (Modbus Application Protocol) header
- Transaction ID: 2 bytes
- Protocol ID: 2 bytes (0x0000 for Modbus)  
- Length: 2 bytes
- Unit Identifier: 1 byte (Slave ID)
```

#### Supported Function Codes
1. **Function Code 03**: Read Holding Registers
   - Used for sensor data reading (registers 90-174)
   - Response parsing with high/low byte combination

2. **Function Code 06**: Write Single Register  
   - Used for control commands (registers 900-901)
   - Command confirmation handling

#### Network Communication
- **Framework**: Ktor 3.1.3 TCP client
- **Socket Management**: Custom TCP socket handling
- **Error Handling**: Connection retry logic
- **Data Processing**: ByteBuffer manipulation for proper byte ordering

---

### Connection Management

#### Connection States
```kotlin
sealed class ConnectionStatus {
    object Disconnected : ConnectionStatus()
    object Connected : ConnectionStatus()
    data class Error(val message: String) : ConnectionStatus()
}

sealed class BackgroundServiceStatus {
    object Stopped : BackgroundServiceStatus()
    object Running : BackgroundServiceStatus()  
    data class Error(val message: String) : BackgroundServiceStatus()
}

sealed class RemoteServerStatus {
    object NotSent : RemoteServerStatus()
    object Sending : RemoteServerStatus()
    object Success : RemoteServerStatus()
    data class Error(val message: String) : RemoteServerStatus()
}
```

#### Connection Features
- **Automatic Reconnection**: Retry logic for lost connections
- **Status Monitoring**: Real-time connection status tracking
- **Error Recovery**: Graceful handling of network interruptions
- **Configuration**: Configurable IP, port, and slave ID parameters

---

## Data Layer Analysis

### Current Status: ❌ Critical Gap

#### Database Configuration
**Framework**: Room 2.6.1 with KSP annotation processing
**Status**: Configured in build.gradle.kts but **NOT IMPLEMENTED**

```kotlin
// Dependencies exist but no implementation:
implementation("androidx.room:room-runtime:2.6.1")
implementation("androidx.room:room-ktx:2.6.1") 
kapt("androidx.room:room-compiler:2.6.1")
```

#### Placeholder Files (Empty Implementations)
1. **DataInService** - Empty interface
   - **Location**: `app/src/main/java/com/example/giraffenew/data/`
   - **Status**: ❌ No implementation

2. **ParameterValuesHomeRepository** - Empty class
   - **Purpose**: Should manage home screen parameter data
   - **Status**: ❌ No implementation

3. **SharedDataInParameterValuesHome** - Empty class  
   - **Purpose**: Shared data model for home parameters
   - **Status**: ❌ No implementation

---

### Data Models (Implemented)

#### 1. RegisterData (Serializable)
```kotlin
@Serializable
data class RegisterData(
    val address: Int,
    val value: Int,
    val timestamp: Long = System.currentTimeMillis()
)
```
**Purpose**: Container for Modbus register data with timestamp
**Usage**: Network communication and data transmission

#### 2. ServerResponse  
```kotlin
data class ServerResponse(
    val success: Boolean,
    val message: String,
    val data: Any? = null
)
```
**Purpose**: HTTP server communication response handling

#### 3. Navigation Models
- **AllDestinations**: String constants for screen navigation
- **NavRoutesDiagnostic**: Sealed classes for diagnostic sub-navigation

---

### Missing Data Layer Components

#### Required Database Entities (Not Implemented)
1. **ParameterValues Entity**
   - Real-time parameter storage
   - Historical trending data
   - Timestamp indexing

2. **DiagnosticLogs Entity**
   - System diagnostic records  
   - Error logging and tracking
   - Maintenance history

3. **SystemSettings Entity**
   - Configuration parameters
   - User preferences
   - Network settings

4. **UserSessions Entity**
   - Authentication records
   - Activity logging
   - Security audit trail

#### Required DAOs (Not Implemented)
1. **ParameterValuesDao**
   - CRUD operations for sensor data
   - Time-series queries
   - Aggregation functions

2. **DiagnosticLogsDao**
   - Error log management
   - Query by severity/category
   - Retention policy enforcement

3. **SystemSettingsDao**
   - Configuration management
   - Key-value storage
   - Validation queries

#### Required Repositories (Not Implemented)
1. **CraneDataRepository**
   - Coordinate between Modbus, Database, Remote server
   - Caching strategy implementation  
   - Offline-first architecture

2. **ConfigurationRepository**
   - Settings management
   - User preferences
   - System configuration

3. **DiagnosticsRepository**
   - Error tracking and analysis
   - Maintenance scheduling
   - Performance monitoring

---

### Data Flow Analysis

#### Current Data Flow (Partial)
```
PLC Controller (Modbus TCP) 
    ↓
ModbusViewModel (In-Memory StateFlow)
    ↓  
UI Screens (Compose State Collection)

Remote Server ← ModbusViewModel (HTTP POST)
```

#### Required Data Flow (Complete)
```
PLC Controller (Modbus TCP)
    ↓
Modbus Background Service
    ↓
Repository Layer (Caching + Validation)
    ↓
Room Database ← → Remote Server Sync
    ↓
ViewModel (Clean State Management)
    ↓  
UI Screens (Reactive Updates)
```

---

### Critical Data Layer Issues

#### High Priority Issues
1. **No Data Persistence**: All data lost on app restart
2. **No Offline Capability**: App unusable without network
3. **No Data History**: Cannot track trends or analyze patterns  
4. **No Configuration Storage**: Settings reset on restart
5. **Memory Leaks**: Large datasets accumulate in ViewModels

#### Performance Issues
1. **Real-time Data Overload**: No data throttling or buffering
2. **UI Thread Blocking**: Heavy operations in ViewModels
3. **Memory Growth**: Unlimited data accumulation
4. **Network Inefficiency**: No request batching or compression

#### Security Issues  
1. **No Data Encryption**: Sensitive data stored in plain text memory
2. **No Access Control**: No data access restrictions
3. **No Audit Trail**: No tracking of data modifications

---

### Recommended Data Architecture

#### Phase 1: Database Implementation
1. **Create Room Database**: GiraffeDatabase with all entities
2. **Implement DAOs**: Optimized queries with proper indexing
3. **Create Repositories**: Multi-source data coordination
4. **Add Migrations**: Version management for schema changes

#### Phase 2: Service Refactoring  
1. **Extract Background Services**: Move from ViewModels to proper Services
2. **Implement Data Pipeline**: Real-time processing with buffering
3. **Add Caching Layer**: Multi-level caching strategy
4. **Offline Support**: Queue operations when disconnected

#### Phase 3: Performance Optimization
1. **Data Compression**: Reduce network and storage overhead
2. **Query Optimization**: Indexed queries for large datasets
3. **Memory Management**: Proper cleanup and garbage collection
4. **Background Processing**: Move heavy operations off UI thread

---

## Service Architecture Recommendations

### Current vs Recommended Architecture

#### Current (Problematic)
```
UI Layer: Compose Screens
    ↓
ViewModel Layer: ModbusViewModel (doing too much)
    ↓ 
Network Layer: Direct Ktor calls
```

#### Recommended (Clean)
```
UI Layer: Compose Screens
    ↓
ViewModel Layer: Pure State Management  
    ↓
Repository Layer: Data Coordination
    ↓
Service Layer: Background Processing
    ↓
Network Layer: Modbus + HTTP clients
```

### Service Separation Plan
1. **ModbusService**: Dedicated Modbus TCP communication
2. **DataSyncService**: Remote server synchronization  
3. **DiagnosticsService**: System health monitoring
4. **ConfigurationService**: Settings management

---

**Document Version**: 1.0  
**Analysis Date**: 2025-08-11  
**Priority**: Critical - Data layer must be implemented before production use