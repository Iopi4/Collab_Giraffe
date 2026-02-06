package com.example.giraffenew.setting_sensors.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.entities.UISettingSensorsConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.Int

@HiltViewModel
class SettingSensorsViewModule
@Inject constructor(
    private val modbusRepository: ModbusRepository
): ViewModel(

){
    private val VIRTUAL_ZERO = 32250 //Константа для виртуального нуля. Необходимо для корретной высоты подъема

    //Параметры настройки датчика высоты крюка
    private val _setValueHoistPosFwdStop = mutableStateOf("")
    val setValueHoistPosFwdStop: State<String> = _setValueHoistPosFwdStop

    private val _setValueHoistPosRevStop = mutableStateOf("")
    val setValueHoistPosRevStop: State<String> = _setValueHoistPosRevStop

    private val _setValueHoistPosFwdDwnSp = mutableStateOf("")
    val setValueHoistPosFwdDwnSp: State<String> = _setValueHoistPosFwdDwnSp

    private val _setValueHoistPosRevDwnSp = mutableStateOf("")
    val setValueHoistPosRevDwnSp: State<String> = _setValueHoistPosRevDwnSp

    private val _setValueHoistPosZero = mutableStateOf("")
    val setValueHoistPosZero: State<String> = _setValueHoistPosZero


    // Параметры настройки датчика вылета
    private val _setValueTrolleyPosFwdStop = mutableStateOf("")
    val setValueTrolleyPosFwdStop: State<String> = _setValueTrolleyPosFwdStop

    private val _setValueTrolleyPosRevStop = mutableStateOf("")
    val setValueTrolleyPosRevStop: State<String> = _setValueTrolleyPosRevStop

    private val _setValueTrolleyPosFwdDwnSp = mutableStateOf("")
    val setValueTrolleyPosFwdDwnSp: State<String> = _setValueTrolleyPosFwdDwnSp

    private val _setValueTrolleyPosRevDwnSp = mutableStateOf("")
    val setValueTrolleyPosRevDwnSp: State<String> = _setValueTrolleyPosRevDwnSp


    // Параметры настройки датчика угла поворота
    private val _setValueSlewMaxAngle = mutableStateOf("")
    val setValueSlewMaxAngle: State<String> = _setValueSlewMaxAngle

    private val _setValueSlewCalcAngle = mutableStateOf("")
    val setValueSlewCalcAngle: State<String> = _setValueSlewCalcAngle


    // Параметры настройки датчика хода крана
    private val _setValueCranePosFwdStop = mutableStateOf("")
    val setValueCranePosFwdStop: State<String> = _setValueCranePosFwdStop

    private val _setValueCranePosRevStop = mutableStateOf("")
    val setValueCranePosRevStop: State<String> = _setValueCranePosRevStop

    private val _setValueCranePosFwdDwnSp = mutableStateOf("")
    val setValueCranePosFwdDwnSp: State<String> = _setValueCranePosFwdDwnSp

    private val _setValueCranePosRevDwnSp = mutableStateOf("")
    val setValueCranePosRevDwnSp: State<String> = _setValueCranePosRevDwnSp

    private val _setValueCranePosRepPoint = mutableStateOf("")
    val setValueCranePosRepPoint: State<String> = _setValueCranePosRepPoint



    private val _errorMessageHoist = mutableStateOf<String?>(null)
    val errorMessageHoist: State<String?> = _errorMessageHoist

    private val _logMessageHoist = mutableStateOf<String?>(null)
    val logMessageHoist: State<String?> = _logMessageHoist

    private val _errorMessageSlew = mutableStateOf<String?>(null)
    val errorMessageSlew: State<String?> = _errorMessageSlew

    private val _logMessageSlew = mutableStateOf<String?>(null)
    val logMessageSlew: State<String?> = _logMessageSlew


    private val _errorMessageTrolley = mutableStateOf<String?>(null)
    val errorMessageTrolley: State<String?> = _errorMessageTrolley

    private val _logMessageTrolley = mutableStateOf<String?>(null)
    val logMessageTrolley: State<String?> = _logMessageTrolley

    private val _errorMessageCrane = mutableStateOf<String?>(null)
    val errorMessageCrane: State<String?> = _errorMessageCrane

    private val _logMessageCrane = mutableStateOf<String?>(null)
    val logMessageCrane: State<String?> = _logMessageCrane



//Подписываемся на необходимые нам регистры для отображения на панели оператора
    private val addresses = listOf(
        33130, 33120, 33110, 33140, 31420,
        61158, 63101, 63102, 63111, 63112, 63113, 63114, 61160, 61163, 61163, 61161,
        33115, 33151, 33152, 33153, 33154, 33155, 33156, 33157, 33158, 33161, 33162, 33163, 33164, 33165,
        39030, 39031, 39032, 39033, 39034, 39035, 39036, 39037, 30026, 30027, 30028, 30029, 31158, 31258, 31358, 31458,
        33111, 33112, 33113, 33114, 33131, 33132, 33133, 33133, 33141, 33142, 33143, 33144, 33145,
        33050, 33051, 33052, 33053, 33054, 33055, 33056, 33057,
        // Регистры для считывания
    )

    private val _state = mutableStateOf(SettingSensorsState())
    val state: State<SettingSensorsState> = _state

    private var dataCollectionJob: Job? = null

    fun setActiveConfiguration() {
        modbusRepository.setActiveUiConfiguration(UISettingSensorsConfiguration.NAME_SETTING_SENSORS)
        startDataCollection()
        writeUiActiveStatus(21) // Активируем регистр
    }

    fun clearActiveConfiguration() {
        pauseDataCollection()
        modbusRepository.setActiveUiConfiguration(null)
        writeUiActiveStatus(0) // Деактивируем регистр
    }

    private fun writeUiActiveStatus(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            modbusRepository.writeRegister(63531, value).fold(
                onSuccess = {
                    Log.d("SettingSensorsVM", "Register 63531 set to $value")
                },
                onFailure = { e ->
                    Log.e("SettingSensorsVM", "Failed to write register 63531", e)
                }
            )
        }
    }

    fun startDataCollection() {
        dataCollectionJob?.cancel() // Всегда отменяем предыдущую job

        Log.d("SSettingSensorsVM", "Starting data collection for ${UISettingSensorsConfiguration.NAME_SETTING_SENSORS}")
        dataCollectionJob = viewModelScope.launch(Dispatchers.Default) {
            modbusRepository.getConfigurationFlow(UISettingSensorsConfiguration.NAME_SETTING_SENSORS)
                .collect { cache ->
                    Log.d("SettingSensorsViewModel", "Updating from cache, size: ${cache.size}")
                    try {
                        // Создаем отфильтрованную карту только для нужных адресов
                        val filteredCache = addresses.associate { addr ->
                            addr to cache[addr]?.value
                        }
                        _state.value = createSettingSensorsState(filteredCache)

                    } catch (e: Exception) {
                        Log.e("SettingSensorsVM", "Data collection error", e)
                    }
                }
        }
    }

    // Приостановка сбора данных
    fun pauseDataCollection() {
        Log.d("SettingSensorsViewModel", "Pausing data collection")
        dataCollectionJob?.cancel()
        dataCollectionJob = null
    }
    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДАТЧИКИ ОТОБРАЖЕНИЕ ДАННЫХ И СОСТОЯНИЯ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // //Функция createSettingLoadCapacityState() для обработки отфильтрованных данных:
    private fun createSettingSensorsState(cache: Map<Int, Int?>): SettingSensorsState {
        return SettingSensorsState(
            sensorsHoistState = SensorsHoistState(
                currentHoistPosition = safeDivideTens(cache[33110] ?: 0),
                //Статус теста ограничений подъема
                currentHoistStopDwn1 = cache[33151] ?: 0,
                currentHoistStopUp1 = cache[33152] ?: 0,
                currentHoistDwnDwnSp1 = cache[33153] ?: 0,
                currentHoistUpDwnSp1 = cache[33154] ?: 0,
                //Лампа. ошибки выхода за рабочий диапазон показаний энкодера
                currentHoistEncoderMaxDeltaError = cache[39030] ?: 0,
                currentHoistEncoderMaxValueError = cache[39034] ?: 0,
                currentHoistAbsEncErrCanOpen = cache[30026] ?: 0, //-----ошибка связи энкодеров
                //Данные с абсолютного энкодера
                currentAbsEncHoistPositionWord1 = cache[33050] ?: 0,
                currentAbsEncHoistPositionWord2 = cache[33051] ?: 0,

                hoistLimitStatus = cache[31158] ?: 0,

                hoistMaxPosition = safeDivideTens(cache[33111] ?: 0),
                hoistMinPosition = safeDivideTens(cache[33112] ?: 0),
                hoistPosFwdDwnSp = safeDivideTens(cache[33113] ?: 0),
                hoistPosRevDwnSp = safeDivideTens(cache[33114] ?: 0),
                hoistZeroPosition = safeDivideTens(cache[33115] ?: 0),
            ),
            sensorsSlewState = SensorsSlewState(
                currentSlewPosition = safeDivideTens(cache[33120] ?: 0),
                //Лампа. ошибки выхода за рабочий диапазон показаний энкодера
                currentSlewEncoderMaxDeltaError = cache[39031] ?: 0,
                currentSlewEncoderMaxValueError = cache[39035] ?: 0,
                currentSlewAbsEncErrCanOpen = cache[30027] ?: 0, //-----ошибка связи энкодеров
                //Данные с абсолютного энкодера
                currentAbsEncSlewPositionWord1 = cache[33052] ?: 0,
                currentAbsEncSlewPositionWord2 = cache[33053] ?: 0,

                slewLimitStatus = cache[31258] ?: 0,
            ),

            sensorsTrolleyState = SensorsTrolleyState(
                currentTrolleyPosition = safeDivideTens(cache[33130] ?: 0),
                //Статус теста ограничений Тележки
                currentTrolleyStopFwd1 = cache[33155] ?: 0,
                currentTrolleyStopRev1 = cache[33156] ?: 0,
                currentTrolleyFwdDwnSp1 = cache[33157] ?: 0,
                currentTrolleyRevDwnSp1 = cache[33158] ?: 0,
                //Лампа. ошибки выхода за рабочий диапазон показаний энкодера
                currentTrolleyEncoderMaxDeltaError = cache[39032] ?: 0,
                currentTrolleyEncoderMaxValueError = cache[39036] ?: 0,
                currentTrolleyAbsEncErrCanOpen = cache[30028] ?: 0, //-----ошибка связи энкодеров
                //Данные с абсолютного энкодера
                currentAbsEncTrolleyPositionWord1 = cache[33054] ?: 0,
                currentAbsEncTrolleyPositionWord2 = cache[33055] ?: 0,

                trolleyLimitStatus = cache[31358] ?: 0,

                trolleyMaxPosition = safeDivideTens(cache[33131] ?: 0),
                trolleyMinPosition = safeDivideTens(cache[33132] ?: 0),
                trolleyPosFwdDwnSp = safeDivideTens(cache[33133] ?: 0),
                trolleyPosRevDwnSp = safeDivideTens(cache[33134] ?: 0),

            ),

            sensorsCraneState = SensorsCraneState(
                currentCranePosition = safeDivideTens(cache[33140] ?: 0),
                currentCraneRailsEn = cache[31420] ?: 0,
                //Статус теста ограничений Хода крана
                currentCraneStopFwd1 = cache[33161] ?: 0,
                currentCraneStopRev1 = cache[33162] ?: 0,
                currentCraneFwdDwnSp1 = cache[33163] ?: 0,
                currentCraneRevDwnSp1 = cache[33164] ?: 0,
                currentCraneRepPoint = cache[33165] ?: 0,
                //Лампа. ошибки выхода за рабочий диапазон показаний энкодера
                currentCraneEncoderMaxDeltaError = cache[39033] ?: 0,
                currentCraneEncoderMaxValueError = cache[39037] ?: 0,
                currentCraneAbsEncErrCanOpen = cache[30029] ?: 0, //-----ошибка связи энкодеров
                //Данные с абсолютного энкодера
                currentAbsEncCranePositionWord1 = cache[33056] ?: 0,
                currentAbsEncCranePositionWord2 = cache[33057] ?: 0,

                craneLimitStatus = cache[31458] ?: 0,

                craneMaxPosition = safeDivideTens(cache[33141] ?: 0),
                craneMinPosition = safeDivideTens(cache[33142] ?: 0),
                cranePosFwdDwnSp = safeDivideTens(cache[33143] ?: 0),
                cranePosRevDwnSp = safeDivideTens(cache[33144] ?: 0),
                cranePosCTRLPoint = safeDivideTens(cache[33145] ?: 0),
                ),
        )
    }

    private fun safeDivideTens(value: Int?): Float {
        return (value ?: 0) / 10f
    }

    data class SettingSensorsState(
        val sensorsHoistState: SensorsHoistState = SensorsHoistState(),
        val sensorsSlewState: SensorsSlewState = SensorsSlewState(),
        val sensorsTrolleyState: SensorsTrolleyState = SensorsTrolleyState(),
        val sensorsCraneState: SensorsCraneState = SensorsCraneState(),
    )

    data class SensorsHoistState(
        val currentHoistPosition: Float = 0f,//ЗНАЧЕНИЕ ВЫСОТЫ
//Статус теста ограничений подъема
        val currentHoistStopDwn1: Int = 0,
        val currentHoistStopUp1: Int = 0,
        val currentHoistDwnDwnSp1: Int = 0,
        val currentHoistUpDwnSp1: Int = 0,
//-----Лампа. ошибки выхода за рабочий диапазон показаний энкодера
        val currentHoistEncoderMaxDeltaError: Int = 0,
        val currentHoistEncoderMaxValueError: Int = 0,
        val currentHoistAbsEncErrCanOpen: Int = 0, //Лампа. Ошибка связи по  CANopen
//Данные с датчиков. Подъем
        val currentAbsEncHoistPositionWord1: Int = 0,
        val currentAbsEncHoistPositionWord2: Int = 0,

        val hoistLimitStatus: Int = 0, //Статус функции проверки ограничений в наладке

//Данные крайних положений механизмов
        val hoistMaxPosition: Float = 0f, //Максимальное положение подъема
        val hoistMinPosition: Float = 0f, //Минимальное положение подъема
        val hoistPosFwdDwnSp: Float = 0f, //Положение крюка замедление вверх
        val hoistPosRevDwnSp: Float = 0f, //Положение крюка замедление вниз
        val hoistZeroPosition: Float = 0f, //Положение крюка при нулевой высоте
    )

    data class SensorsSlewState(
        val currentSlewPosition: Float = 0f,//ЗНАЧЕНИЕ АЗИМУТА
        //-----Лампа. ошибки выхода за рабочий диапазон показаний энкодера
        val currentSlewEncoderMaxDeltaError: Int = 0,
        val currentSlewEncoderMaxValueError: Int = 0,
        val currentSlewAbsEncErrCanOpen: Int = 0, //Лампа. Ошибка связи по  CANopen
        //Данные с датчика
        val currentAbsEncSlewPositionWord1: Int = 0,
        val currentAbsEncSlewPositionWord2: Int = 0,


        val slewLimitStatus: Int = 0, //Статус функции проверки ограничений в наладке

    )
    data class SensorsTrolleyState(
        val currentTrolleyPosition: Float = 0f,//ЗНАЧЕНИЕ ВЫЛЕТА
        //Статус теста ограничений Тележки
        val currentTrolleyStopFwd1: Int = 0,
        val currentTrolleyStopRev1: Int = 0,
        val currentTrolleyFwdDwnSp1: Int = 0,
        val currentTrolleyRevDwnSp1: Int = 0,
        //-----Лампа. ошибки выхода за рабочий диапазон показаний энкодера
        val currentTrolleyEncoderMaxDeltaError: Int = 0,
        val currentTrolleyEncoderMaxValueError: Int = 0,
        val currentTrolleyAbsEncErrCanOpen: Int = 0, //Лампа. Ошибка связи по  CANopen
        //Данные с датчика
        val currentAbsEncTrolleyPositionWord1: Int = 0,
        val currentAbsEncTrolleyPositionWord2: Int = 0,

        val trolleyLimitStatus: Int = 0, //Статус функции проверки ограничений в наладке
        //Данные крайних положений механизмов
        val trolleyMaxPosition: Float = 0f, //Максимальное положение тележки
        val trolleyMinPosition: Float = 0f, //Минимальное положение тележки
        val trolleyPosFwdDwnSp: Float = 0f, //положение при котором снижаем скорость двигая тележку вперед
        val trolleyPosRevDwnSp: Float = 0f, //положение при котором снижаем скорость двигая тележку назад
    )
    data class SensorsCraneState(
        val currentCranePosition: Float = 0f,//ЗНАЧЕНИЕ ПЕРЕМЕЩЕНИЯ КРАНА
        val currentCraneRailsEn: Int = 0,
        //Статус теста ограничений Хода крана
        val currentCraneStopFwd1: Int = 0,
        val currentCraneStopRev1: Int = 0,
        val currentCraneFwdDwnSp1: Int = 0,
        val currentCraneRevDwnSp1: Int = 0,
        val currentCraneRepPoint: Int = 0,
        //-----Лампа. ошибки выхода за рабочий диапазон показаний энкодера
        val currentCraneEncoderMaxDeltaError: Int = 0,
        val currentCraneEncoderMaxValueError: Int = 0,
        val currentCraneAbsEncErrCanOpen: Int = 0, //Лампа. Ошибка связи по  CANopen
        //Данные с датчика.
        val currentAbsEncCranePositionWord1: Int = 0,
        val currentAbsEncCranePositionWord2: Int = 0,

        val craneLimitStatus: Int = 0, //Статус функции проверки ограничений в наладке
        //Данные крайних положений механизмов
        val craneMaxPosition: Float = 0f, //Путь при котором останавливаем движение крана вперед
        val craneMinPosition: Float = 0f, //Путь при котором останавливаем движение крана назад
        val cranePosFwdDwnSp: Float = 0f, //Путь при котором снижаем скорость при движении крана вперед
        val cranePosRevDwnSp: Float = 0f, //Путь при котором снижаем скорость при движении крана назад
        val cranePosCTRLPoint: Float = 0f, //Путь при котором снижаем скорость при движении крана назад
    )




    //Функция проверки и преобразования целых чисел
    private fun validateAndConvert(value: String, min: Int, max: Int): Int {
        val intValue = value.toIntOrNull() ?: throw NumberFormatException("Некорректное число")
        if (intValue !in min..max) throw IllegalArgumentException("Значение вне диапазона")
        return intValue
    }
    //Функция проверки и преобразования десятичных чисел
    private fun validateAndConvertFloat(value: String, min: Float, max: Float): Int {
        val floatValue = value.toFloatOrNull() ?: throw NumberFormatException("Некорректное число")
        if (floatValue < min || floatValue > max) throw IllegalArgumentException("Значение вне диапазона")
        return (floatValue * 10).toInt()
    }


    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДАТЧИК ВЫСОТЫ ПОДЪЕМА!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // Hoist. ВКЛЮЧИТЬ/ВЫКЛЮЧИТЬ ТЕСТ ОГРАНИЧЕНИЙ
    fun onSetHoistLimitTestEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(61158, 1)
                delay(200)
                modbusRepository.writeRegister(61158, 0)
            } catch (e: Exception) {
            }
        }
    }

    // Hoist. ЗАПОМНИТЬ положение по высоте при движении вверх
    fun onSetHoistFwdMemEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63101, 1)
                delay(200)
                modbusRepository.writeRegister(63101, 0)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageHoist.value = "${e.message}"
                _logMessageHoist.value = null
            }
        }
    }


    // Hoist. ЗАПОМНИТЬ положение по высоте при движении вниз
    fun onSetHoistRevMemEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63102, 1)
                delay(200)
                modbusRepository.writeRegister(63102, 0)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageHoist.value = "${e.message}"
                _logMessageHoist.value = null
            }
        }
    }

    // Hoist. ЗАПОМНИТЬ нулевое положение высоты подъема
    fun onSetHoistZeroMemEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63105, 1)
                delay(200)
                modbusRepository.writeRegister(63105, 0)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageHoist.value = "${e.message}"
                _logMessageHoist.value = null
            }
        }
    }

    // Hoist. Запись Стоп Вверх [м]
    fun onSetValueHoistPosFvdStop() {
        onSetHoistFwdMemEn()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistPosFwdStop.value, 0.0f, 200.0f )
                // Запись в регистры
                modbusRepository.writeRegister(63111, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "${e.message}"
            }
        }
    }

    // Hoist. Запись Стоп Вниз [м]
    fun onSetValueHoistPosRevStop() {
        onSetHoistRevMemEn()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val floatValue = _setValueHoistPosRevStop.value.toFloatOrNull()
                    ?: throw NumberFormatException("Некорректное число")
                if (floatValue < -5.0f || floatValue > 200.0f) throw IllegalArgumentException("Значение вне диапазона")

                val model = (VIRTUAL_ZERO + floatValue * 10).toInt()
                modbusRepository.writeRegister(63112, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "${e.message}"
            }
        }
    }

    // Hoist. Запись Нулевая высота [м]
    fun onSetValueHoistPosZero() {
        onSetHoistZeroMemEn()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val floatValue = _setValueHoistPosZero.value.toFloatOrNull()
                    ?: throw NumberFormatException("Некорректное число")
                if (floatValue < -5.0f || floatValue > 200.0f) throw IllegalArgumentException("Значение вне диапазона")

                val model = (VIRTUAL_ZERO + floatValue * 10).toInt()
                modbusRepository.writeRegister(63115, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "${e.message}"
            }
        }
    }

    // Hoist. Запись высоты для снижение скорости Вверх [м]
    fun onSetValueHoistPosFwdDwnSp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistPosFwdDwnSp.value, 0.0f, 200.0f )
                // Запись в регистры
                modbusRepository.writeRegister(63113, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "${e.message}"
            }
        }
    }

    // Hoist. Запись высоты для снижения скорости Вниз [м]
    fun onSetValueHoistPosRevDwnSp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val floatValue = _setValueHoistPosRevDwnSp.value.toFloatOrNull()
                    ?: throw NumberFormatException("Некорректное число")
                if (floatValue < -5.0f || floatValue > 200.0f) throw IllegalArgumentException("Значение вне диапазона")

                val model = (VIRTUAL_ZERO + floatValue * 10).toInt()
                modbusRepository.writeRegister(63114, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "${e.message}"
            }
        }
    }


    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДАТЧИК ВЫЛЕТА ТЕЛЕЖКИ ГРУЗОВОЙ!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // Trolley. ВКЛЮЧИТЬ/ВЫКЛЮЧИТЬ ТЕСТ ОГРАНИЧЕНИЙ
    fun onSetTrolleyLimitTestEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(61358, 1)
                delay(200)
                modbusRepository.writeRegister(61358, 0)
            } catch (e: Exception) {
            }
        }
    }

    // Trolley. ЗАПОМНИТЬ положение по вылету при движении вперед
    fun onSetTrolleyFwdMemEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63301, 1)
                delay(200)
                modbusRepository.writeRegister(63301, 0)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageTrolley.value = "${e.message}"
                _logMessageTrolley.value = null
            }
        }
    }


    // Trolley. ЗАПОМНИТЬ положение по вылету при движении назад
    fun onSetTrolleyRevMemEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63302, 1)
                delay(200)
                modbusRepository.writeRegister(63302, 0)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageTrolley.value = "${e.message}"
                _logMessageTrolley.value = null
            }
        }
    }

    // Trolley. Запись Стоп вперед [м]
    fun onSetValueTrolleyPosFvdStop() {
        onSetTrolleyFwdMemEn()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueTrolleyPosFwdStop.value, 0.0f, 100.0f )
                // Запись в регистры
                modbusRepository.writeRegister(63131, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "${e.message}"
            }
        }
    }

    // Trolley. Запись Стоп назад [м]
    fun onSetValueTrolleyPosRevStop() {
        onSetTrolleyRevMemEn()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueTrolleyPosRevStop.value, 0.0f, 100.0f )
                // Запись в регистры
                modbusRepository.writeRegister(63132, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "${e.message}"
            }
        }
    }

    // Trolley. Запись снижение скорости вперед [м]
    fun onSetValueTrolleyPosFwdDwnSp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueTrolleyPosFwdDwnSp.value, 0.0f, 100.0f )
                // Запись в регистры
                modbusRepository.writeRegister(63133, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "${e.message}"
            }
        }
    }

    // Trolley. Запись снижение скорости Назад [м]
    fun onSetValueTrolleyPosRevDwnSp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueTrolleyPosRevDwnSp.value, 0.0f, 100.0f )
                // Запись в регистры
                modbusRepository.writeRegister(63134, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "${e.message}"
            }
        }
    }



    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДАТЧИК УГЛА ПОВОРОТА!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // SLEW. Запомнить положение стрелы при угле [0] гр.
    fun onSetSlewZeroMemEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63201, 1)
                delay(200)
                modbusRepository.writeRegister(63201, 0)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageSlew.value = "${e.message}"
                _logMessageSlew.value = null
            }
        }
    }

    // SLEW. Запомнить положение стрелы при заданном угле
    fun onSetSlewCalcAngleMemEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63202, 1)
                delay(200)
                modbusRepository.writeRegister(63202, 0)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageSlew.value = "${e.message}"
                _logMessageSlew.value = null
            }
        }
    }

    // SLEW. Установить [текущий] угол гр.
    fun onSetValueSlewCalcAngle() {
        onSetSlewCalcAngleMemEn()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueSlewCalcAngle.value, 0, 360 )
                // Запись в регистры
                modbusRepository.writeRegister(63121, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "${e.message}"
            }
        }
    }

    // SLEW. Установить [максимальный] угол поворота крана, в гр.
    fun onSetValueSlewMaxAngle() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueSlewMaxAngle.value, 0, 1024 )
                // Запись в регистры
                modbusRepository.writeRegister(63129, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "${e.message}"
            }
        }
    }

    /**
    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!ДАТЧИК ХОДА КРАНА!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    // Crane. ВКЛЮЧИТЬ/ВЫКЛЮЧИТЬ ХОД КРАНА
    fun onSetCraneRailsEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(61420, 1)
                delay(200)
                modbusRepository.writeRegister(61420, 0)
            } catch (e: Exception) {
            }
        }
    }

    // Crane. ВКЛЮЧИТЬ/ВЫКЛЮЧИТЬ ТЕСТ ОГРАНИЧЕНИЙ
    fun onSetCraneLimitTestEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(61458, 1)
                delay(200)
                modbusRepository.writeRegister(61458, 0)
            } catch (e: Exception) {
            }
        }
    }

    // Crane. ЗАПОМНИТЬ положение стоп вперед (вправо)
    fun onSetCraneFwdMemEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63401, 1)
                delay(200)
                modbusRepository.writeRegister(63401, 0)
            } catch (e: Exception) {
            }
        }
    }
    // Crane. ЗАПОМНИТЬ положение стоп назад (влево)
    fun onSetCraneRevMemEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63402, 1)
                delay(200)
                modbusRepository.writeRegister(63402, 0)
            } catch (e: Exception) {
            }
        }
    }
    // Crane. Запись Стоп вперед [м]
    fun onSetValueCranePosFwdStop() {
        onSetCraneFwdMemEn()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueCranePosFwdStop.value, 0.0f, 300.0f )
                // Запись в регистр
                modbusRepository.writeRegister(63141, model)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCrane.value = null
                _errorMessageCrane.value = "${e.message}"
            }
        }
    }

    // Crane. Запись Стоп назад [м]
    fun onSetValueCranePosRevStop() {
        onSetCraneRevMemEn()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueCranePosRevStop.value, 0.0f, 300.0f )
                // Запись в регистры
                modbusRepository.writeRegister(63142, model)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCrane.value = null
                _errorMessageCrane.value = "${e.message}"
            }
        }
    }

    // Crane. Запись снижение скорости вперед [м]
    fun onSetValueCranePosFwdDwnSp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueCranePosFwdDwnSp.value, 0.0f, 300.0f )
                // Запись в регистр
                modbusRepository.writeRegister(63143, model)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCrane.value = null
                _errorMessageCrane.value = "${e.message}"
            }
        }
    }

    // Crane. Запись снижение скорости Назад [м]
    fun onSetValueCranePosRevDwnSp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueCranePosRevDwnSp.value, 0.0f, 300.0f )
                // Запись в регистр
                modbusRepository.writeRegister(63144, model)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCrane.value = null
                _errorMessageCrane.value = "${e.message}"
            }
        }
    }
    // Crane. Запись положения реперной точки [м]
    fun onSetValueCranePosRepPoint() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueCranePosRepPoint.value, 0.0f, 300.0f )
                // Запись в регистр
                modbusRepository.writeRegister(63145, model)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCrane.value = null
                _errorMessageCrane.value = "${e.message}"
            }
        }
    }



    //ДАТЧИК ВЫСОТЫ. Обновление для UI
    fun updateValueHoistPosFwdStop(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueHoistPosFwdStop.value = value } }

    fun updateValueHoistPosRevStop(value: String) {
        if (value.isEmpty() || value.matches(Regex("^-?\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueHoistPosRevStop.value = value } }

    fun updateValueHoistPosZero(value: String) {
        if (value.isEmpty() || value.matches(Regex("^-?\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueHoistPosZero.value = value } }


    fun updateValueHoistPosFwdDwnSp(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueHoistPosFwdDwnSp.value = value } }

    fun updateValueHoistPosRevDwnSp(value: String) {
        if (value.isEmpty() || value.matches(Regex("^-?\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueHoistPosRevDwnSp.value = value } }



    //ДАТЧИК ВЫЛЕТА. Обновление для UI
    fun updateValueTrolleyPosFwdStop(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueTrolleyPosFwdStop.value = value } }

    fun updateValueTrolleyPosRevStop(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueTrolleyPosRevStop.value = value } }

    fun updateValueTrolleyPosFwdDwnSp(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueTrolleyPosFwdDwnSp.value = value } }

    fun updateValueTrolleyPosRevDwnSp(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueTrolleyPosRevDwnSp.value = value } }



    //ДАТЧИК ПОВОРОТА. Обновление для UI
    fun updateValueSlewCalcAngle(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueSlewCalcAngle.value = value } }

    fun updateValueSlewMaxAngle(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValueSlewMaxAngle.value = value } }




    //ДАТЧИК Хода крана. Обновление для UI
    fun updateValueCranePosFwdStop(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueCranePosFwdStop.value = value } }

    fun updateValueCranePosRevStop(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueCranePosRevStop.value = value } }

    fun updateValueCranePosFwdDwnSp(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueCranePosFwdDwnSp.value = value } }

    fun updateValueCranePosRevDwnSp(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueCranePosRevDwnSp.value = value } }

    fun updateValueCranePosRepPoint(value: String) {
        if (value.isEmpty() || value.matches(Regex("^\\d{0,3}(\\.\\d{0,1})?$"))) {
            _setValueCranePosRepPoint.value = value } }


    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("SettingSensorsVM", "ViewModel cleared")
        // Деактивируем конфигурацию при уничтожении ViewModel
        modbusRepository.setActiveUiConfiguration(null)
    }
}