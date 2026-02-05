package com.example.giraffenew.technical_maintenance.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.entities.UITechnicalMaintenanceConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TechnicalMaintenanceViewModule
@Inject constructor(
    private val modbusRepository: ModbusRepository
): ViewModel(

){
    private val _setValueTOPassword = mutableStateOf("")
    val setValueTOPassword: State<String> = _setValueTOPassword

    private val _errorMessagePassword = mutableStateOf<String?>(null)
    val errorMessagePassword: State<String?> = _errorMessagePassword

    private val _logMessagePassword = mutableStateOf<String?>(null)
    val logMessagePassword: State<String?> = _logMessagePassword

    private val addresses = listOf( 33550, 33556, 33500, 33551, 33552, 33553, 33554, 33555)


    private val _state = mutableStateOf(SettingSensorsState())
    val state: State<SettingSensorsState> = _state

    private var dataCollectionJob: Job? = null

    fun setActiveConfiguration() {
        modbusRepository.setActiveUiConfiguration(UITechnicalMaintenanceConfiguration.NAME_TECHNICAL_MAINTENANCE)
        startDataCollection()
    }

    fun startDataCollection() {
        dataCollectionJob?.cancel() // Всегда отменяем предыдущую job

        Log.d("TechnicalMaintenanceVM", "Starting data collection for ${UITechnicalMaintenanceConfiguration.NAME_TECHNICAL_MAINTENANCE}")
        dataCollectionJob = viewModelScope.launch(Dispatchers.Default) {
            modbusRepository.getConfigurationFlow(UITechnicalMaintenanceConfiguration.NAME_TECHNICAL_MAINTENANCE)
                .collect { cache ->
                    Log.d("TechnicalMaintenanceViewModel", "Updating from cache, size: ${cache.size}")
                    try {
                        // Создаем отфильтрованную карту только для нужных адресов
                        val filteredCache = addresses.associate { addr ->
                            addr to cache[addr]?.value
                        }
                        _state.value = createTechnicalMaintenanceState(filteredCache)

                    } catch (e: Exception) {
                        Log.e("TechnicalMaintenanceVM", "Data collection error", e)
                    }
                }
        }
    }

    fun clearActiveConfiguration() {
        pauseDataCollection()
        modbusRepository.setActiveUiConfiguration(null)
    }


    // Приостановка сбора данных
    fun pauseDataCollection() {
        Log.d("TechnicalMaintenanceViewModel", "Pausing data collection")
        dataCollectionJob?.cancel()
        dataCollectionJob = null
    }

    // //Функция createTechnicalMaintenanceState() для обработки отфильтрованных данных:
    private fun createTechnicalMaintenanceState(cache: Map<Int, Int?>): SettingSensorsState {
        return SettingSensorsState(
            //Параметры крана
            craneNo = cache[33500] ?: 0,
            tOCounter = cache[33550] ?: 0,
            technicalReglamentTO1 = cache[33551] ?: 0,
            alarmTO1 = cache[33552] ?: 0,
            motorHourDelta = safeDivideTens(cache[33553] ?: 0),
            technicalReglamentTO1Delta = cache[33554] ?: 0,
            technicalReglamentxWarrantyEnd= cache[33555] ?: 0,
            hmiTotalCraneOperatingHoursMaintenance = safeDivideTens(cache[33556] ?: 0),
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
        val craneNo: Int=0, //Заводской номер крана
        val tOCounter: Int = 0, //Счетчик моточасов м.ч.
        val technicalReglamentTO1: Int = 0, //Проведите ТО
        val alarmTO1: Int = 0, //Скорости ограничены, проведите ТО
        val motorHourDelta: Float = 0f, //Сколько часов осталось до ТО
        val technicalReglamentTO1Delta: Int = 0, //До ТО осталось motorHourDelta часов
        val technicalReglamentxWarrantyEnd: Int = 0,//Грузовая лебедка отработала 750 м.ч.
        val hmiTotalCraneOperatingHoursMaintenance: Float = 0f, //Моточасы крана для технического обслуживания
    )

    //Команда сбросить счетчик ТО
    fun onResetPassword() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                // Запись в регистры
                modbusRepository.writeRegister(63550, 1)
                delay(200)
                modbusRepository.writeRegister(63550, 0)
            } catch (e: Exception) {
            }
        }
    }


    // Запись пароля для сброса счетчика ТО
    fun onSetValueTOPassword() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val model = validateAndConvertFloatTens(_setValueTOPassword.value, 0.0f, 99.9f )
                // Запись в регистры
                modbusRepository.writeRegister(863, model)
            } catch (e: Exception) {
            }
        }
    }

    private fun validateAndConvertFloatTens(value: String, min: Float, max: Float): Int {
        val floatValue = value.toFloatOrNull() ?: throw NumberFormatException("Некорректное число")
        if (floatValue < min || floatValue > max) throw IllegalArgumentException("Значение вне диапазона")
        return (floatValue * 10).toInt()
    }


    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("TechnicalMaintenanceVM", "ViewModel cleared")
        // Деактивируем конфигурацию при уничтожении ViewModel
        modbusRepository.setActiveUiConfiguration(null)
    }
}