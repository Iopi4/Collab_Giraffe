package com.example.giraffenew.coordinate_protection.prezentation

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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoordinateProtectionViewModule
@Inject constructor(
    private val modbusRepository: ModbusRepository
): ViewModel(

){


    // Регистры для считывания. Те на которые подписываемся
    private val addresses = listOf(
        155, 158, 159, 162, 803, 804, 805, 806, 807, 808, 809, 810, 811, 812, 813,
        341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353,
        730, 731, 732, 733, 734, 735, 736, 737, 204, 205, 206, 207,
        382, 383, 386, 387, 390, 391, 394, 395

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
            //Статус теста ограничений подъема
            currentHoistStopDwn1 = cache[341] ?: 0,
            currentHoistStopUp1 = cache[342] ?: 0,
            currentHoistDwnDwnSp1 = cache[342] ?: 0,
            currentHoistUpDwnSp1 = cache[344] ?: 0,
            //Статус теста ограничений Тележки
            currentTrolleyStopFwd1 = cache[345] ?: 0,
            currentTrolleyStopRev1 = cache[346] ?: 0,
            currentTrolleyFwdDwnSp1 = cache[347] ?: 0,
            currentTrolleyRevDwnSp1 = cache[348] ?: 0,
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

        val currentAbsEncHoistPositionWord1: Int = 0,
        val currentAbsEncHoistPositionWord2: Int = 0,
        val currentAbsEncSlewPositionWord1: Int = 0,
        val currentAbsEncSlewPositionWord2: Int = 0,
        val currentAbsEncTrolleyPositionWord1: Int = 0,
        val currentAbsEncTrolleyPositionWord2: Int = 0,
        val currentAbsEncCranePositionWord1: Int = 0,
        val currentAbsEncCranePositionWord2: Int = 0,
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

}