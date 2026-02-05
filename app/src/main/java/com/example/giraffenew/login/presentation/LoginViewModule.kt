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

    private val _passwordOrNaladka = mutableStateOf(0)
    val passwordOrNaladka: State<Int> = _passwordOrNaladka

    private val addresses = listOf( 33001, 33002, 33003, 33071)

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
        val passwordOrNaladkaValue = cache[33071] ?: 0
        _passwordOrNaladka.value = passwordOrNaladkaValue // Сохраняем значение

        return LoginState(
            //Параметры крана
            buildMode = cache[33001] ?: 0,
            serviceMode = cache[33002] ?: 0,
            montageMode = cache[33003] ?: 0,
            passwordOrNaladka = passwordOrNaladkaValue,
        )
    }

    data class LoginState(
        //ПАРАМЕТРЫ КРАНА
        val buildMode: Int=0, //Флаг режима наладки
        val serviceMode: Int=0, //Флаг режима сервис
        val montageMode: Int=0, //Флаг монтажного режима
        val passwordOrNaladka: Int=0, //Флаг.Режим авторизации по слову naladka
    )

    private fun validateAndConvert(value: String, min: Int, max: Int): Int {
        val intValue = value.toIntOrNull() ?: throw NumberFormatException("Некорректное число")
        if (intValue !in min..max) throw IllegalArgumentException("Значение вне диапазона")
        return intValue
    }

    // Функция для обновления пароля
    fun updatePassword(value: String) {
        if (passwordOrNaladka.value == 1){
            if (value.length <= 9) {
                _password.value = value
                // Для цифровых значений обновляем части пароля
                if (value.all { it.isDigit() }) {
                    splitPassword(value)
                }
            }

        } else {
            if (value.length <= 9 && (value.isEmpty() || value.all { it.isDigit() })) {
                _password.value = value
                splitPassword(value)
            }
        }
    }

    // Разделение пароля на части
    private fun splitPassword(password: String) {
        val paddedPassword = password.padEnd(9, '0')
        _setValuePasswordPart1.value = "1" + paddedPassword.substring(0, 3)
        _setValuePasswordPart2.value = "1" + paddedPassword.substring(3, 6)
        _setValuePasswordPart3.value = "1" + paddedPassword.substring(6, 9)
    }


    // Запись пароля
    fun onSetValuePasswordPart1() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val model = validateAndConvert(_setValuePasswordPart1.value, 0, 1999 )
                // Запись в регистры
                modbusRepository.writeRegister(63007, model)
            } catch (e: Exception) {
            }
        }
    }
    fun onSetValuePasswordPart2() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val model = validateAndConvert(_setValuePasswordPart2.value, 0, 1999 )
                // Запись в регистры
                modbusRepository.writeRegister(63008, model)
            } catch (e: Exception) {
            }
        }
    }
    fun onSetValuePasswordPart3() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                val model = validateAndConvert(_setValuePasswordPart3.value, 0, 1999 )
                // Запись в регистры
                modbusRepository.writeRegister(63009, model)
            } catch (e: Exception) {
            }
        }
    }


    fun onSetPasswordLogIn() {
        viewModelScope.launch(Dispatchers.Default) {
            try {
                if (passwordOrNaladka.value == 1 && password.value == "naladka") {
                    // Отправляем фиксированные значения для "naladka"
                    modbusRepository.writeRegister(63007, 1009)
                    modbusRepository.writeRegister(63008, 1007)
                    modbusRepository.writeRegister(63009, 1008)
                } else {
                    // Стандартная логика отправки частей пароля
                    val part1 = validateAndConvert(_setValuePasswordPart1.value, 0, 1999)
                    modbusRepository.writeRegister(63007, part1)

                    val part2 = validateAndConvert(_setValuePasswordPart2.value, 0, 1999)
                    modbusRepository.writeRegister(63008, part2)

                    val part3 = validateAndConvert(_setValuePasswordPart3.value, 0, 1999)
                    modbusRepository.writeRegister(63009, part3)
                }

                // Отправка команды ввода
                delay(200)
                modbusRepository.writeRegister(63001, 1)
                delay(200)
                modbusRepository.writeRegister(63001, 0)
            } catch (e: Exception) {
                // Обработка ошибок
            }
        }
    }
    fun onSetExitLevel() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (!isActive) return@launch // Проверка активности ViewModel
                modbusRepository.writeRegister(63000, 1)
                delay(200)
                modbusRepository.writeRegister(63000, 0)
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