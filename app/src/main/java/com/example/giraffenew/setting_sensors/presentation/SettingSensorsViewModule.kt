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
        155, 158, 159, 162, 212, 803, 804, 805, 806, 807, 808, 809, 810, 811, 812, 813,
        341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353,
        730, 731, 732, 733, 734, 735, 736, 737, 204, 205, 206, 207,
        382, 383, 386, 387, 390, 391, 394, 395, 1623, 1648, 1699, 1738
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
            modbusRepository.writeRegister(868, value).fold(
                onSuccess = {
                    Log.d("SettingSensorsVM", "Register 283 set to $value")
                },
                onFailure = { e ->
                    Log.e("SettingSensorsVM", "Failed to write register 868", e)
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


            //Параметры крана
            currentHoistPosition = safeDivideTens(cache[159] ?: 0),
            currentSlewPosition = safeDivideTens(cache[158] ?: 0),
            currentTrolleyPosition = safeDivideTens(cache[155] ?: 0),
            currentCranePosition = safeDivideTens(cache[162] ?: 0),
            currentCraneRailsEn = cache[212] ?: 0,
            //Статус теста ограничений подъема
            currentHoistStopDwn1 = cache[341] ?: 0,
            currentHoistStopUp1 = cache[342] ?: 0,
            currentHoistDwnDwnSp1 = cache[343] ?: 0,
            currentHoistUpDwnSp1 = cache[344] ?: 0,
            //Статус теста ограничений Тележки
            currentTrolleyStopFwd1 = cache[345] ?: 0,
            currentTrolleyStopRev1 = cache[346] ?: 0,
            currentTrolleyFwdDwnSp1 = cache[347] ?: 0,
            currentTrolleyRevDwnSp1 = cache[348] ?: 0,
            //Статус теста ограничений Хода крана
            currentCraneStopFwd1 = cache[349] ?: 0,
            currentCraneStopRev1 = cache[350] ?: 0,
            currentCraneFwdDwnSp1 = cache[351] ?: 0,
            currentCraneRevDwnSp1 = cache[352] ?: 0,
            currentCraneRepPoint= cache[353] ?: 0,

            //Лампа. ошибки выхода за рабочий диапазон показаний энкодера
            currentHoistEncoderMaxDeltaError = cache[730] ?: 0,
            currentSlewEncoderMaxDeltaError = cache[731] ?: 0,
            currentTrolleyEncoderMaxDeltaError  = cache[732] ?: 0,
            currentCraneEncoderMaxDeltaError = cache[733] ?: 0,
            currentHoistEncoderMaxValueError = cache[734] ?: 0,
            currentSlewEncoderMaxValueError  = cache[735] ?: 0,
            currentTrolleyEncoderMaxValueError = cache[736] ?: 0,
            currentCraneEncoderMaxValueError  = cache[737] ?: 0,
            //-----ошибка связи энкодеров
            currentHoistAbsEncErrCanOpen    = cache[204] ?: 0,
            currentSlewAbsEncErrCanOpen     = cache[205] ?: 0,
            currentTrolleyAbsEncErrCanOpen  = cache[206] ?: 0,
            currentCraneAbsEncErrCanOpen    = cache[207] ?: 0,

            currentAbsEncHoistPositionWord1 = cache[382] ?: 0,
            currentAbsEncHoistPositionWord2 = cache[383] ?: 0,

            currentAbsEncSlewPositionWord1 = cache[386] ?: 0,
            currentAbsEncSlewPositionWord2 = cache[387] ?: 0,

            currentAbsEncTrolleyPositionWord1 = cache[390] ?: 0,
            currentAbsEncTrolleyPositionWord2 = cache[391] ?: 0,

            currentAbsEncCranePositionWord1 = cache[394] ?: 0,
            currentAbsEncCranePositionWord2 = cache[395] ?: 0,

            hoistLimitStatus = cache[1623] ?: 0,
            slewLimitStatus = cache[1648] ?: 0,
            trolleyLimitStatus = cache[1699] ?: 0,
            craneLimitStatus = cache[1738] ?: 0,

            )
    }


    private fun safeDivide(value: Int?): Float {
        return (value ?: 0) / 1f
    }

    private fun safeDivideTens(value: Int?): Float {
        return (value ?: 0) / 10f
    }

    data class SettingSensorsState(
        //ПАРАМЕТРЫ КРАНА
        val currentHoistPosition: Float = 0f,
        val currentSlewPosition: Float = 0f,
        val currentTrolleyPosition: Float = 0f,
        val currentCranePosition: Float = 0f,
        val currentCraneRailsEn: Int = 0,
        //ЗНАЧЕНИЕ ДАТЧИКОВ
        //Статус теста ограничений подъема
        val currentHoistStopDwn1: Int = 0,
        val currentHoistStopUp1: Int = 0,
        val currentHoistDwnDwnSp1: Int = 0,
        val currentHoistUpDwnSp1: Int = 0,
        //Статус теста ограничений Тележки
        val currentTrolleyStopFwd1: Int = 0,
        val currentTrolleyStopRev1: Int = 0,
        val currentTrolleyFwdDwnSp1: Int = 0,
        val currentTrolleyRevDwnSp1: Int = 0,
        //Статус теста ограничений Хода крана
        val currentCraneStopFwd1: Int = 0,
        val currentCraneStopRev1: Int = 0,
        val currentCraneFwdDwnSp1: Int = 0,
        val currentCraneRevDwnSp1: Int = 0,
        val currentCraneRepPoint: Int = 0,
//-----Лампа. ошибки выхода за рабочий диапазон показаний энкодера
        val currentHoistEncoderMaxDeltaError: Int = 0,
        val currentSlewEncoderMaxDeltaError: Int = 0,
        val currentTrolleyEncoderMaxDeltaError: Int = 0,
        val currentCraneEncoderMaxDeltaError: Int = 0,
        val currentHoistEncoderMaxValueError: Int = 0,
        val currentSlewEncoderMaxValueError: Int = 0,
        val currentTrolleyEncoderMaxValueError: Int = 0,
        val currentCraneEncoderMaxValueError: Int = 0,
//Лампа. Ошибка связи по  CANopen
        val currentHoistAbsEncErrCanOpen: Int = 0,
        val currentSlewAbsEncErrCanOpen: Int = 0,
        val currentTrolleyAbsEncErrCanOpen: Int = 0,
        val currentCraneAbsEncErrCanOpen: Int = 0,
//Данные с датчиков
        val currentAbsEncHoistPositionWord1: Int = 0,
        val currentAbsEncHoistPositionWord2: Int = 0,
        val currentAbsEncSlewPositionWord1: Int = 0,
        val currentAbsEncSlewPositionWord2: Int = 0,
        val currentAbsEncTrolleyPositionWord1: Int = 0,
        val currentAbsEncTrolleyPositionWord2: Int = 0,
        val currentAbsEncCranePositionWord1: Int = 0,
        val currentAbsEncCranePositionWord2: Int = 0,

//Статус функции проверки ограничений в наладке
        val hoistLimitStatus: Int = 0,
        val slewLimitStatus: Int = 0,
        val trolleyLimitStatus: Int = 0,
        val craneLimitStatus: Int = 0,
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
                modbusRepository.writeRegister(803, 1)
                delay(200)
                modbusRepository.writeRegister(803, 0)
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
                modbusRepository.writeRegister(804, 1)
                delay(200)
                modbusRepository.writeRegister(804, 0)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
                _logMessageHoist.value = null
            }
        }
    }


    // Hoist. ЗАПОМНИТЬ положение по высоте при движении вверх
    fun onSetHoistRevMemEn() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(805, 1)
                delay(200)
                modbusRepository.writeRegister(805, 0)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
                _logMessageHoist.value = null
            }
        }
    }

    // Hoist. Запись Стоп Вверх [м]
    fun onSetValueHoistPosFvdStop() {
        onSetHoistFwdMemEn()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistPosFwdStop.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(806, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
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
                if (floatValue < -5.0f || floatValue > 999.9f) throw IllegalArgumentException("Значение вне диапазона")

                val model = (VIRTUAL_ZERO + floatValue * 10).toInt()
                modbusRepository.writeRegister(807, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Hoist. Запись снижение скорости Вверх [м]
    fun onSetValueHoistPosFwdDwnSp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueHoistPosFwdDwnSp.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(808, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Hoist. Запись снижение скорости Вниз [м]
    fun onSetValueHoistPosRevDwnSp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val floatValue = _setValueHoistPosRevDwnSp.value.toFloatOrNull()
                    ?: throw NumberFormatException("Некорректное число")
                if (floatValue < -5.0f || floatValue > 999.9f) throw IllegalArgumentException("Значение вне диапазона")

                val model = (VIRTUAL_ZERO + floatValue * 10).toInt()
                modbusRepository.writeRegister(809, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
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
                modbusRepository.writeRegister(823, 1)
                delay(200)
                modbusRepository.writeRegister(823, 0)
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
                modbusRepository.writeRegister(824, 1)
                delay(200)
                modbusRepository.writeRegister(824, 0)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
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
                modbusRepository.writeRegister(825, 1)
                delay(200)
                modbusRepository.writeRegister(825, 0)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
                _logMessageTrolley.value = null
            }
        }
    }

    // Trolley. Запись Стоп вперед [м]
    fun onSetValueTrolleyPosFvdStop() {
        onSetTrolleyFwdMemEn()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueTrolleyPosFwdStop.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(827, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Trolley. Запись Стоп назад [м]
    fun onSetValueTrolleyPosRevStop() {
        onSetTrolleyRevMemEn()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueTrolleyPosRevStop.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(826, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Trolley. Запись снижение скорости вперед [м]
    fun onSetValueTrolleyPosFwdDwnSp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueTrolleyPosFwdDwnSp.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(828, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Trolley. Запись снижение скорости Назад [м]
    fun onSetValueTrolleyPosRevDwnSp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueTrolleyPosRevDwnSp.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(829, model)
                _errorMessageTrolley.value = null
                _logMessageTrolley.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageTrolley.value = null
                _errorMessageTrolley.value = "Запись не удалась: ${e.message}"
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
                modbusRepository.writeRegister(817, 1)
                delay(200)
                modbusRepository.writeRegister(817, 0)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageSlew.value = "Запись не удалась: ${e.message}"
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
                modbusRepository.writeRegister(818, 1)
                delay(200)
                modbusRepository.writeRegister(818, 0)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _errorMessageSlew.value = "Запись не удалась: ${e.message}"
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
                modbusRepository.writeRegister(816, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // SLEW. Установить [максимальный] угол гр.
    fun onSetValueSlewMaxAngle() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvert(_setValueSlewMaxAngle.value, 0, 1024 )
                // Запись в регистры
                modbusRepository.writeRegister(815, model)
                _errorMessageHoist.value = null
                _logMessageHoist.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageHoist.value = null
                _errorMessageHoist.value = "Запись не удалась: ${e.message}"
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
                modbusRepository.writeRegister(869, 1)
                delay(200)
                modbusRepository.writeRegister(869, 0)
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
                modbusRepository.writeRegister(834, 1)
                delay(200)
                modbusRepository.writeRegister(834, 0)
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
                modbusRepository.writeRegister(836, 1)
                delay(200)
                modbusRepository.writeRegister(836, 0)
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
                modbusRepository.writeRegister(837, 1)
                delay(200)
                modbusRepository.writeRegister(837, 0)
            } catch (e: Exception) {
            }
        }
    }
    // Crane. Запись Стоп вперед [м]
    fun onSetValueCranePosFwdStop() {
        onSetCraneFwdMemEn()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueCranePosFwdStop.value, 0.0f, 999.9f )
                // Запись в регистр
                modbusRepository.writeRegister(838, model)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCrane.value = null
                _errorMessageCrane.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Crane. Запись Стоп назад [м]
    fun onSetValueCranePosRevStop() {
        onSetCraneRevMemEn()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueCranePosRevStop.value, 0.0f, 999.9f )
                // Запись в регистры
                modbusRepository.writeRegister(839, model)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCrane.value = null
                _errorMessageCrane.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Crane. Запись снижение скорости вперед [м]
    fun onSetValueCranePosFwdDwnSp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueCranePosFwdDwnSp.value, 0.0f, 999.9f )
                // Запись в регистр
                modbusRepository.writeRegister(840, model)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCrane.value = null
                _errorMessageCrane.value = "Запись не удалась: ${e.message}"
            }
        }
    }

    // Crane. Запись снижение скорости Назад [м]
    fun onSetValueCranePosRevDwnSp() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueCranePosRevDwnSp.value, 0.0f, 999.9f )
                // Запись в регистр
                modbusRepository.writeRegister(841, model)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCrane.value = null
                _errorMessageCrane.value = "Запись не удалась: ${e.message}"
            }
        }
    }
    // Crane. Запись положения реперной точки [м]
    fun onSetValueCranePosRepPoint() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloat(_setValueCranePosRepPoint.value, 0.0f, 999.9f )
                // Запись в регистр
                modbusRepository.writeRegister(835, model)
                _errorMessageCrane.value = null
                _logMessageCrane.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageCrane.value = null
                _errorMessageCrane.value = "Запись не удалась: ${e.message}"
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