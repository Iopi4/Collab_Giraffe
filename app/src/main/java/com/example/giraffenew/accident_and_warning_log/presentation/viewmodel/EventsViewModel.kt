package com.example.giraffenew.accident_and_warning_log.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.accident_and_warning_log.data.remoute.JsonDataSource
import com.example.giraffenew.accident_and_warning_log.domian.model.EventEntity
import com.example.giraffenew.accident_and_warning_log.domian.usecase.ClearArchiveUseCase
import com.example.giraffenew.accident_and_warning_log.domian.usecase.GetActiveEventsUseCase
import com.example.giraffenew.accident_and_warning_log.domian.usecase.GetArchiveEventsUseCase
import com.example.giraffenew.accident_and_warning_log.domian.usecase.HandleStatusChangeUseCase
import com.example.giraffenew.data.entities.UIAccidentAndWaringConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Int


@HiltViewModel
 // — специальная аннотация для Hilt, которая:
// - Позволяет Hilt управлять жизненным циклом ViewModel
// - Обеспечивает корректную интеграцию с системой DI

open class EventsViewModel
@Inject constructor( //— указывает на использование DI для внедрения зависимостей

    //Содержит зависимости, которые представляют бизнес-логику через Use Case:
    private val getActiveEvents: GetActiveEventsUseCase, //use case для получения активных событий
    private val clearArchive: ClearArchiveUseCase, //use case для очистки архива
    private val getArchivedEvents: GetArchiveEventsUseCase, //use case для отображения архивных событий
    private val handleStatusChange: HandleStatusChangeUseCase,

    private val modbusRepository: ModbusRepository,
    private val jsonDataSource: JsonDataSource, // Добавляем источник JSON данных
    private val eventTimeProvider: EventTimeProvider

): ViewModel() {

    val addresses = listOf(186, 187, 188, 189, 244, 245,
        190, 191, 192, 300, 301, 302, 303, 304, 305, 306,
        307, 308, 309, 700, 701, 702, 703, 704, 705, 706,
        707, 708, 709, 710, 711, 712, 713, 714, 715, 716,
        717, 718, 720, 721, 722, 723, 724, 725, 726, 727,
        728, 729, 730, 731, 732, 733, 734, 735, 736, 737,
        738, 739, 740, 741, 742, 743, 744)

    private val _state = mutableStateOf(EventsState())
    val state: State<EventsState> = _state

    private var dataCollectionJob: Job? = null

    // Карта для отслеживания предыдущих состояний регистров
    private val previousRegisterStates = mutableMapOf<Int, Int>()


    fun setActiveConfiguration() {
        modbusRepository.setActiveUiConfiguration(UIAccidentAndWaringConfiguration.NAME_ACCIDENT_AND_WARING)
        startEventsCollection()
    }

    fun clearActiveConfiguration() {
        pauseEventsCollection()
        modbusRepository.setActiveUiConfiguration(null)
    }

    // Запуск сбора данных
    fun startEventsCollection() {
        dataCollectionJob?.cancel() // Всегда отменяем предыдущую job

        Log.d("AccidentАndWarningVM", "Starting data collection for ${UIAccidentAndWaringConfiguration.NAME_ACCIDENT_AND_WARING}")
        dataCollectionJob = viewModelScope.launch(Dispatchers.IO) {
            modbusRepository.getConfigurationFlow(UIAccidentAndWaringConfiguration.NAME_ACCIDENT_AND_WARING)
                .collect { cache ->
                    // Обработка данных
                    try {
                        // Создаем отфильтрованную карту только для нужных адресов
                        val filteredCache = addresses.associate { addr ->
                            addr to cache[addr]?.value
                        }
                        _state.value = createEventsState(filteredCache)

                        // Обрабатываем изменения состояний регистров
                        processRegisterChanges(filteredCache)

                    } catch (e: Exception) {
                        Log.e("AccidentАndWarningVM", "Data collection error", e)
                    }
                }
        }
    }
    /**
    Связывание регистров с определениями событий:
    При изменении значения регистра (например, 700) система проверяет, есть ли для него определение в JSON
    Если регистр становится активным (значение = 1), создается соответствующее событие
    JsonDataSource предоставляет доступ к определениям событий
    Для каждого регистра извлекается соответствующее описание и тип события
     */

    // Обработка изменений состояний регистров
    private fun processRegisterChanges(currentCache: Map<Int, Int?>) {
        viewModelScope.launch {

            // Получаем текущее время событий
            val eventTime = eventTimeProvider.eventTimeMillis.value

            currentCache.forEach { (register, value) ->
                val currentValue = value ?: 0
                val previousValue = previousRegisterStates[register] ?: 0

                // Если состояние изменилось
                if (currentValue != previousValue) {
                    // Для регистров, которые должны обрабатываться как события
                    if (addresses.contains(register)) {
                        // TRUE = 1, FALSE = 0
                        val isActive = currentValue == 1

                        try {
                            // Получаем определение события из JSON
                            val eventDefinition = jsonDataSource.getEventDefinition(register.toString())
                            // Обрабатываем изменение статуса
                            handleStatusChange(register.toString(), isActive, eventTime)
                        } catch (e: NoSuchElementException) {
                            Log.w("EventsViewModel", "No event definition for register $register")
                        } catch (e: Exception) {
                            Log.e("EventsViewModel", "Error handling status change for register $register", e)
                        }
                    }

                    // Обновляем предыдущее состояние
                    previousRegisterStates[register] = currentValue
                }
            }
        }
    }




    private fun createEventsState(cache: Map<Int, Int?>): EventsState {
        return EventsState(
            overload105Percent      = cache[186] ?: 0, //Бит превышения грузоподъемности более 105%
            overload110Percent      = cache[187] ?: 0, //Бит превышения грузоподъемности более 110%
            ogpLimSw                = cache[188] ?: 0, //Датчик критического момента. 1-Норма (грузоподъемность не превышена). 0- превышение грузоподъемности
            hoistBrakeSensor        = cache[189] ?: 0, //Износ тормоза привода главного подъема. 0-Норма (тормоз исправен). 1- тормоз изношен
            buildMode               = cache[190] ?: 0, //Флаг режима наладки
            serviceMode             = cache[191] ?: 0, //Флаг режима сервис
            montageMode             = cache[192] ?: 0, //Флаг монтажного режима
            craneConfigWarning      = cache[300] ?: 0, //Ход крана не настроен
            hoistConfigWarning      = cache[301] ?: 0, //Подъем не настроен
            slewConfigWarning       = cache[302] ?: 0, //Поворот крана не настроен
            trolleyConfigWarning    = cache[303] ?: 0, //Вылет не настроен
            weightConfigWarning     = cache[304] ?: 0, //Грузоподъемность не настроена
            technicalReglamentTO1   = cache[305] ?: 0, //Проведите ТО
            alrmTO1                 = cache[306] ?: 0, //Скорости ограничены, проведите ТО
            motorHourDelta          = cache[307] ?: 0, //Сколько часов осталось до ТО
            technicalReglamentTO1Delta      = cache[308] ?: 0, //До ТО осталось AT%MW307 часов
            technicalReglamentXWarrantyEnd  = cache[309] ?: 0, //Грузовая лебедка отработала 750 м.ч.

            hmiHatchLimitSwitchStatus       = cache[700] ?: 0,
            hmiLineContactorZeroPosAlarm    = cache[701] ?: 0,
            hmiMicroclimateHeaterFault      = cache[702] ?: 0,
            hmiBrakePowerSupplyFault        = cache[703] ?: 0,
            hmiVentilationPowerSupplyFault  = cache[704] ?: 0,

            hmiHoistJoystickError           = cache[705] ?: 0,
            hmiSlewJoystickError            = cache[706] ?: 0,
            hmiTrolleyJoystickError         = cache[707] ?: 0,
            hmiTravelJoystickError          = cache[708] ?: 0,
            hmiLoadMomentSensorAlarm        = cache[709] ?: 0,

            hmiCraneReferencePointSensorFault = cache[711] ?: 0,
            hmiLoadCellSensorFault          = cache[711] ?: 0,
            hmiAnemometerSensorFault        = cache[712] ?: 0,

            hmiAbsCraneEncoderFault         = cache[713] ?: 0,
            hmiAbsHoistEncoderFault         = cache[714] ?: 0,
            hmiAbsSlewEncoderFault          = cache[715] ?: 0,
            hmiAbsTrolleyEncoderFault       = cache[716] ?: 0,

            hmiPhaseControlRelayFault       = cache[717] ?: 0,
            hmiPowerSupplyNetworkFault      = cache[718] ?: 0,

            hmiHoistDriveError              = cache[720] ?: 0,
            hmiSlewDriveError               = cache[721] ?: 0,
            hmiTrolleyDriveError            = cache[722] ?: 0,
            hmiCraneDriveError              = cache[723] ?: 0,

            hmiHoistDrivePowerError         = cache[724] ?: 0,
            hmiSlewDrivePowerError          = cache[725] ?: 0,
            hmiTrolleyDrivePowerError       = cache[726] ?: 0,
            hmiCraneDrivePowerError         = cache[727] ?: 0,

            hmiCANopenSensorsError          = cache[728] ?: 0,
            hmiCANopenDrivesError           = cache[729] ?: 0,

            hmiHoistAbsEncoderMaxDeltaError    = cache[730] ?: 0,
            hmiSlewAbsEncoderMaxDeltaError     = cache[731] ?: 0,
            hmiTrolleyAbsEncoderMaxDeltaError = cache[732] ?: 0,
            hmiCraneAbsEncoderMaxDeltaError   = cache[733] ?: 0,

            hmiHoistAbsEncoderMaxValueError    = cache[734] ?: 0,
            hmiSlewAbsEncoderMaxValueError     = cache[735] ?: 0,
            hmiTrolleyAbsEncoderMaxValueError  = cache[736] ?: 0,
            hmiCraneAbsEncoderMaxValueError   = cache[737] ?: 0,

            hmiHoistDriveErrorCode          = cache[738] ?: 0,
            hmiSlewDriveErrorCode           = cache[739] ?: 0,
            hmiTrolleyDriveErrorCode        = cache[740] ?: 0,
            hmiCraneDriveErrorCode          = cache[741] ?: 0,

            hmiHoistEncoderFault            = cache[742] ?: 0,
            hmiSlewEncoderFault             = cache[743] ?: 0,
            hmiTrolleyEncoderFault          = cache[744] ?: 0,
        )
    }

    data class EventsState(
        val overload105Percent: Int = 0, //Бит превышения грузоподъемности более 105%
        val overload110Percent: Int = 0, //Бит превышения грузоподъемности более 110%
        val ogpLimSw: Int = 0, //Датчик критического момента. 1-Норма (грузоподъемность не превышена). 0- превышение грузоподъемности
        val hoistBrakeSensor: Int = 0, //Износ тормоза привода главного подъема. 0-Норма (тормоз исправен). 1- тормоз изношен
        val buildMode: Int = 0, //Флаг режима наладки
        val serviceMode: Int = 0, //Флаг режима сервис
        val montageMode: Int = 0, //Флаг монтажного режима
        val craneConfigWarning: Int = 0, //Ход крана не настроен
        val hoistConfigWarning: Int = 0, //Подъем не настроен
        val slewConfigWarning: Int = 0, //Поворот крана не настроен
        val trolleyConfigWarning: Int = 0, //Вылет не настроен
        val weightConfigWarning: Int = 0, //Грузоподъемность не настроена
        val technicalReglamentTO1: Int = 0, //Проведите ТО
        val alrmTO1: Int = 0, //Скорости ограничены, проведите ТО
        val motorHourDelta: Int = 0, //Сколько часов осталось до ТО
        val technicalReglamentTO1Delta: Int = 0, //До ТО осталось AT%MW307 часов
        val technicalReglamentXWarrantyEnd: Int = 0, //Грузовая лебедка отработала 750 м.ч.

        val hmiHatchLimitSwitchStatus: Int = 0,
        val hmiLineContactorZeroPosAlarm: Int = 0,
        val hmiMicroclimateHeaterFault: Int = 0,
        val hmiBrakePowerSupplyFault: Int = 0,
        val hmiVentilationPowerSupplyFault: Int = 0,

        val hmiHoistJoystickError: Int = 0,
        val hmiSlewJoystickError: Int = 0,
        val hmiTrolleyJoystickError: Int = 0,
        val hmiTravelJoystickError: Int = 0,
        val hmiLoadMomentSensorAlarm: Int = 0,

        val hmiCraneReferencePointSensorFault: Int = 0,
        val hmiLoadCellSensorFault: Int = 0,
        val hmiAnemometerSensorFault: Int = 0,

        val hmiAbsCraneEncoderFault: Int = 0,
        val hmiAbsHoistEncoderFault: Int = 0,
        val hmiAbsSlewEncoderFault: Int = 0,
        val hmiAbsTrolleyEncoderFault: Int = 0,

        val hmiPhaseControlRelayFault: Int = 0,
        val hmiPowerSupplyNetworkFault: Int = 0,

        val hmiHoistDriveError: Int = 0,
        val hmiSlewDriveError: Int = 0,
        val hmiTrolleyDriveError: Int = 0,
        val hmiCraneDriveError: Int = 0,

        val hmiHoistDrivePowerError: Int = 0,
        val hmiSlewDrivePowerError: Int = 0,
        val hmiTrolleyDrivePowerError: Int = 0,
        val hmiCraneDrivePowerError: Int = 0,

        val hmiCANopenSensorsError: Int = 0,
        val hmiCANopenDrivesError: Int = 0,

        val hmiHoistAbsEncoderMaxDeltaError: Int = 0,
        val hmiSlewAbsEncoderMaxDeltaError: Int = 0,
        val hmiTrolleyAbsEncoderMaxDeltaError: Int = 0,
        val hmiCraneAbsEncoderMaxDeltaError: Int = 0,

        val hmiHoistAbsEncoderMaxValueError: Int = 0,
        val hmiSlewAbsEncoderMaxValueError: Int = 0,
        val hmiTrolleyAbsEncoderMaxValueError: Int = 0,
        val hmiCraneAbsEncoderMaxValueError: Int = 0,

        val hmiHoistDriveErrorCode: Int = 0,
        val hmiSlewDriveErrorCode: Int = 0,
        val hmiTrolleyDriveErrorCode: Int = 0,
        val hmiCraneDriveErrorCode: Int = 0,

        val hmiHoistEncoderFault: Int = 0,
        val hmiSlewEncoderFault: Int = 0,
        val hmiTrolleyEncoderFault: Int = 0
    )

    /**
    Свойство activeEvents:
    - getActiveEvents() — вызов use case, который возвращает Flow.
    - stateIn() — преобразование Flow в StateFlow с параметрами:
    - scope = viewModelScope — определяет область видимости
    - SharingStarted.WhileSubscribed(5000) — настройка автоматического запуска:
    - Запускает сбор данных при наличии подписчика
    - Сохраняет соединение 5 секунд после отписки
    - initialValue — начальное значение (пустой список)
     */

    open val activeEvents: StateFlow<List<EventEntity>> = getActiveEvents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    //Оператор .stateIn в Kotlin преобразует «холодный» поток (Flow) в «горячий» поток состояний (StateFlow).
    //Это позволяет делиться последним выдаваемым значением из одного запущенного экземпляра восходящего потока с несколькими подписчиками.
    //scope = viewModelScope, //— область действия сопрограммы, в которой начинается совместное использование.
    //started = SharingStarted.WhileSubscribed(5000), //started — стратегия, управляющая началом и остановкой совместного использования.
    //initialValue = emptyList() //initialValue — начальное значение потока состояний.
    // Это значение также используется при сбросе потока состояний с помощью стратегии SharingStarted.WhileSubscribed с параметром stopTimeoutMillis.


// По аналогии добавляем StateFlow для архивных событий
    open val archivedEvents: StateFlow<List<EventEntity>> = getArchivedEvents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun clearArchive() = viewModelScope.launch { //запускает корутину в контексте ViewModel
        clearArchive //вызывает use case для очистки архива
    }

    // Приостановка сбора данных
    fun pauseEventsCollection() {
        Log.d("AccidentАndWarningVM", "Pausing data collection")
        dataCollectionJob?.cancel()
        dataCollectionJob = null
        // Очищаем предыдущие статусы при остановке?
        // previousStatuses.clear()
    }
}