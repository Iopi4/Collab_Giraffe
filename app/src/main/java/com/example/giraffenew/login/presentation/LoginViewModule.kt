package com.example.giraffenew.login.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.entities.UILoginConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModule
@Inject constructor(
    private val modbusRepository: ModbusRepository
): ViewModel(

){
    private val _setValuePasswordPart1 = mutableStateOf("1000")
    val setValuePasswordPart1: State<String> = _setValuePasswordPart1

    private val _setValuePasswordPart2 = mutableStateOf("1000")
    val setValuePasswordPart2: State<String> = _setValuePasswordPart2

    private val _setValuePasswordPart3 = mutableStateOf("1000")
    val setValuePasswordPart3: State<String> = _setValuePasswordPart3


    // Новое состояние для полного пароля
    private val _password = mutableStateOf("")
    val password: State<String> = _password

    private val addresses = listOf( 190, 191, 192)

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    private var dataCollectionJob: Job? = null

    fun setActiveConfiguration() {
        modbusRepository.setActiveUiConfiguration(UILoginConfiguration.NAME_LOGIN)
        startDataCollection()
    }

    fun startDataCollection() {
        dataCollectionJob?.cancel() // Всегда отменяем предыдущую job

        Log.d("TechnicalMaintenanceVM", "Starting data collection for ${UILoginConfiguration.NAME_LOGIN}")
        dataCollectionJob = viewModelScope.launch(Dispatchers.Default) {
            modbusRepository.getConfigurationFlow(UILoginConfiguration.NAME_LOGIN)
                .collect { cache ->
                    Log.d("LoginViewModel", "Updating from cache, size: ${cache.size}")
                    try {
                        // Создаем отфильтрованную карту только для нужных адресов
                        val filteredCache = addresses.associate { addr ->
                            addr to cache[addr]?.value
                        }
                        _state.value = createLoginState(filteredCache)

                    } catch (e: Exception) {
                        Log.e("LoginVM", "Data collection error", e)
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
        Log.d("LoginViewModel", "Pausing data collection")
        dataCollectionJob?.cancel()
        dataCollectionJob = null
    }

    private fun createLoginState(cache: Map<Int, Int?>): LoginState {
        return LoginState(
            //Параметры крана
            buildMode = cache[190] ?: 0,
            serviceMode = cache[191] ?: 0,
            montageMode = cache[192] ?: 0,

        )
    }

    data class LoginState(
        //ПАРАМЕТРЫ КРАНА
        val buildMode: Int=0, //Флаг режима наладки
        val serviceMode: Int=0, //Флаг режима сервис
        val montageMode: Int=0, //Флаг монтажного режима
    )

    private fun validateAndConvert(value: String, min: Int, max: Int): Int {
        val intValue = value.toIntOrNull() ?: throw NumberFormatException("Некорректное число")
        if (intValue !in min..max) throw IllegalArgumentException("Значение вне диапазона")
        return intValue
    }

    // Функция для обновления пароля
    fun updatePassword(value: String) {
        if (value.length <= 9 && (value.isEmpty() || value.all { it.isDigit() })) {
            _password.value = value
            splitPassword(value)
        }
    }

    // Разделение пароля на части
    private fun splitPassword(password: String) {
        val paddedPassword = password.padEnd(9, '0')
        _setValuePasswordPart1.value = "1" + paddedPassword.substring(0, 3)
        _setValuePasswordPart2.value = "1" + paddedPassword.substring(3, 6)
        _setValuePasswordPart3.value = "1" + paddedPassword.substring(6, 9)
    }


    fun onSetValuePasswordPart123() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val model1 = validateAndConvert(_setValuePasswordPart1.value, 0, 1999 )
                // Запись в регистры
                modbusRepository.writeRegister(798, model1)
                val model2 = validateAndConvert(_setValuePasswordPart2.value, 0, 1999 )
                // Запись в регистры
                modbusRepository.writeRegister(799, model2)
                val model3 = validateAndConvert(_setValuePasswordPart3.value, 0, 1999 )
                // Запись в регистры
                modbusRepository.writeRegister(800, model3)
            } catch (e: Exception) {
            }
        }
    }


    // Запись пароля
    fun onSetValuePasswordPart1() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val model = validateAndConvert(_setValuePasswordPart1.value, 0, 1999 )
                // Запись в регистры
                modbusRepository.writeRegister(798, model)
            } catch (e: Exception) {
            }
        }
    }
    fun onSetValuePasswordPart2() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val model = validateAndConvert(_setValuePasswordPart2.value, 0, 1999 )
                // Запись в регистры
                modbusRepository.writeRegister(799, model)
            } catch (e: Exception) {
            }
        }
    }
    fun onSetValuePasswordPart3() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val model = validateAndConvert(_setValuePasswordPart3.value, 0, 1999 )
                // Запись в регистры
                modbusRepository.writeRegister(800, model)
            } catch (e: Exception) {
            }
        }
    }



    fun updateValuePasswordPart1(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValuePasswordPart1.value = value }
    }
    fun updateValuePasswordPart2(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValuePasswordPart2.value = value }
    }
    fun updateValuePasswordPart3(value: String) {
        if (value.isEmpty() || value.toIntOrNull() != null) {
            _setValuePasswordPart3.value = value }
    }


    fun onSetPasswordLogIn() {
        // Сначала отправляем части пароля
        onSetValuePasswordPart1()
        onSetValuePasswordPart2()
        onSetValuePasswordPart3()
        
        // Затем отправляем команду входа
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch
                delay(200)
                modbusRepository.writeRegister(801, 1)
                delay(200)
                modbusRepository.writeRegister(801, 0)
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }
    fun onSetExitLevel() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                modbusRepository.writeRegister(802, 1)
                delay(200)
                modbusRepository.writeRegister(802, 0)
                // Логирование
            } catch (e: Exception) {
            }
        }
    }



    // Очистка при уничтожении ViewModel
    override fun onCleared() {
        super.onCleared()
        Log.d("TechnicalMaintenanceVM", "ViewModel cleared")
        // Деактивируем конфигурацию при уничтожении ViewModel
        modbusRepository.setActiveUiConfiguration(null)
    }
}