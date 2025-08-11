# UI Screens Inventory - GIRAFFE Tower Crane App

## Screen Navigation Structure

```
MainActivity (Single Activity)
├── DrawerNavigation
│   ├── HomeScreen ("/home/")
│   ├── DiagnosticsScreen ("/diagnostics/")
│   │   ├── System Tab
│   │   ├── PLC Tab  
│   │   ├── Hoist Tab
│   │   ├── Slewing Tab
│   │   ├── Trolley Tab
│   │   └── Crane Tab
│   ├── ModbusToolsScreen ("/modbus_tools/")
│   ├── LoginScreen ("/login/")
│   ├── InitialSetupScreen ("/initial_setup/")
│   ├── SettingLoadCapacityScreen ("/setting_load_capacity/")
│   ├── SettingSensorsScreen ("/setting_sensors/")
│   ├── CoordinateProtectionScreen ("/coordinate_protection/")
│   ├── ServiceMenuScreen ("/service_menu/")
│   ├── ParameterLoggerScreen
│   ├── TechnicalMaintenanceScreen
│   └── AccidentWarningLogScreen
```

## Screen Details

### 1. HomeScreen
**File**: `app/src/main/java/com/example/giraffenew/home/HomeScreen.kt`
**Status**: ✅ Fully Implemented
**Purpose**: Main dashboard displaying real-time crane status

**Key Components**:
- Crane status overview panel
- Mechanism condition indicators (BasicLampMechanismsCondition)
- Operating parameter displays
- Scale visualizations (BasicParameterScaleHorizon/Vertical)
- Alarm status indicators (BasicLampAlarmStatus)

**Data Sources**:
- Real-time Modbus data (should be connected)
- System status information
- Safety parameter monitoring

**UI Technologies**:
- Jetpack Compose with Material3
- Custom UI components from core/ui_kit
- State management with Orbit MVI

---

### 2. DiagnosticsScreen
**File**: `app/src/main/java/com/example/giraffenew/diagnostics/DiagnosticsScreen.kt`
**Status**: ✅ Fully Implemented
**Purpose**: Comprehensive diagnostic interface with multiple tabs

**Tab Structure**:
1. **System Tab**: Overall system health
2. **PLC Tab**: Programmable Logic Controller status
3. **Hoist Tab**: Hoist mechanism diagnostics
4. **Slewing Tab**: Slewing mechanism diagnostics  
5. **Trolley Tab**: Trolley mechanism diagnostics
6. **Crane Tab**: Overall crane diagnostics

**Navigation**: Internal tab navigation using NavRoutesDiagnostic

---

### 3. ModbusToolsScreen  
**File**: `app/src/main/java/com/example/giraffenew/modbus_tools/ModbusToolsScreen.kt`
**Status**: ✅ Implemented
**Purpose**: Modbus TCP register monitoring and control interface

**Features**:
- Connection management (192.168.1.88:502)
- Register reading (range 90-174)
- Register writing (range 900-901) 
- Real-time connection status
- Manual register operations

**Connected ViewModel**: ModbusViewModel with background services

---

### 4. Configuration Screens

#### LoginScreen
**File**: `app/src/main/java/com/example/giraffenew/login/LoginScreen.kt`
**Purpose**: User authentication for system access
**Status**: ✅ Implemented

#### InitialSetupScreen  
**File**: `app/src/main/java/com/example/giraffenew/initial_setup/InitialSetupScreen.kt`
**Purpose**: System initialization and date/time configuration
**Status**: ✅ Implemented

#### SettingLoadCapacityScreen
**File**: `app/src/main/java/com/example/giraffenew/setting_load_capacity/SettingLoadCapacityScreen.kt`
**Purpose**: Configure crane load capacity limits
**Status**: ✅ Implemented

#### SettingSensorsScreen
**File**: `app/src/main/java/com/example/giraffenew/setting_sensors/SettingSensorsScreen.kt`
**Purpose**: Position sensor calibration interface
**Features**: Two-page configuration wizard
**Status**: ✅ Implemented

#### CoordinateProtectionScreen
**File**: `app/src/main/java/com/example/giraffenew/coordinate_protection/CoordinateProtectionScreen.kt`
**Purpose**: Safety protection zone configuration
**Features**: Polygon-based coordinate protection settings
**Status**: ✅ Implemented

---

### 5. Service & Maintenance Screens

#### ServiceMenuScreen
**File**: `app/src/main/java/com/example/giraffenew/service_menu/ServiceMenuScreen.kt`
**Purpose**: Service operations and maintenance interface
**Status**: ✅ Implemented

#### ParameterLoggerScreen
**Purpose**: Data logging functionality and log management
**Status**: ✅ Implemented

#### TechnicalMaintenanceScreen  
**Purpose**: Maintenance operations and scheduling
**Status**: ✅ Implemented

#### AccidentWarningLogScreen
**Purpose**: Safety incident logging and reporting
**Status**: ✅ Implemented

---

## Custom UI Components Library

### Location: `app/src/main/java/com/example/giraffenew/core/ui_kit/`

#### Status Indicators
- **BasicLampAlarmStatus**: Visual alarm status indicators
- **BasicLampMechanismsCondition**: Mechanism health status lamps

#### Parameter Displays  
- **BasicParameterScaleHorizon**: Horizontal parameter scale visualization
- **BasicParameterScaleVertical**: Vertical parameter scale visualization

#### Interactive Components
- **BasicButtonGiraffe**: Custom styled button component
- **BasicButtonTabGiraffe**: Tab-style button for navigation
- **BasicTextFilledGiraffe**: Custom text input fields

#### Layout Components
- **BasicSettingBackground**: Consistent background styling for settings screens

---

## Navigation Implementation

### Primary Navigation
- **Type**: Modal Drawer Navigation
- **Framework**: Jetpack Compose Navigation  
- **Route Management**: String constants in AllDestinations

### Sub-Navigation
- **Diagnostics**: Internal tab navigation with NavRoutesDiagnostic sealed classes
- **Settings**: Sequential screen flows for configuration wizards

---

## Screen State Management

### Technology Stack
- **State Management**: Orbit MVI pattern
- **Reactive Updates**: StateFlow with collectAsStateWithLifecycle
- **Dependency Injection**: Hilt for ViewModel injection

### Data Binding Pattern
```kotlin
// Typical screen implementation
@Composable
fun ExampleScreen(viewModel: ExampleViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    
    ExampleContent(
        state = uiState,
        onAction = viewModel::handleAction
    )
}
```

---

## Integration Points

### Data Sources  
- **Modbus TCP**: Real-time data from PLC controller
- **Local Database**: (Not yet implemented - Room configured)
- **Remote Server**: GIRAFFE monitoring server synchronization

### Real-time Requirements
- **Performance Target**: <100ms response time for safety-critical actions
- **Update Frequency**: Real-time parameter monitoring
- **Connection Management**: Automatic reconnection and error handling

---

## Issues & Recommendations

### Current Issues
1. **Static Data**: Many screens show static values instead of live data
2. **Database Integration**: Room configured but not connected to screens
3. **Error Handling**: Limited industrial-grade error states
4. **Testing**: No UI tests found for any screens

### Recommendations
1. **Connect Real Data**: Link screens to ModbusViewModel StateFlow  
2. **Implement Database**: Complete Room integration for data persistence
3. **Add Error States**: Industrial-grade error handling and recovery
4. **Performance Testing**: Validate real-time requirements
5. **UI Testing**: Add Compose UI tests for critical screens

---

**Document Version**: 1.0  
**Last Updated**: 2025-08-11  
**Coverage**: 12 main screens + custom UI components