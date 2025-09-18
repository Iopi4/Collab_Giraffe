package com.example.giraffenew.navigate.presentation

import android.app.Application
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.core.sound.SoundController
import com.example.giraffenew.data.entities.TopBarRegisterConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import com.example.giraffenew.network.CraneIdRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.giraffenew.R
import com.example.giraffenew.accident_and_warning_log.presentation.viewmodel.EventTimeProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.Calendar

/**
 * Затем будем собирать только изменения в этих регистрах. При этом важно, чтобы коллектор срабатывал только когда изменился хотя бы один из нужных регистров.
 * Однако, в текущей реализации TopBarViewModel, коллектор срабатывает при каждом обновлении кэша (даже если обновлены регистры не из списка).
для решения этого мы:
В ViewModel сделать фильтрацию: преобразовать поток так, чтобы он эмитил только когда изменился один из нужных регистров.
будем запоминать предыдущий отфильтрованный набор значений и сравнивать с новым.
Если отфильтрованный набор не изменился, то не будем обновлять состояние.
Однако, в TopBarViewModel этого нет, но мы можем добавить для еще большей оптимизации.


 */

//Создание ViewModel для управления временем и датой
@HiltViewModel
class TopBarViewModel @Inject constructor(
    private val modbusRepository: ModbusRepository,
    private val craneIdRepository: CraneIdRepository,
    private val soundController: SoundController,
    private val eventTimeProvider: EventTimeProvider,
    private val application: Application,

    // Добавим репозиторий хранения craneId
) : ViewModel() {

    private val _appActiveState = mutableStateOf(true)
    private var lastAppState: Boolean? = null
    private val mutex = Mutex()





    private val _currentTime = mutableStateOf("00:00")
    val currentTime: State<String> = _currentTime

    private val _currentTimeEvents = mutableStateOf("00:00:00")
    val currentTimeEvents: State<String> = _currentTimeEvents

    private val _currentDate = mutableStateOf("00:00:0000")
    val currentDate: State<String> = _currentDate

    private val _currentModelCrane = mutableStateOf("TDK-00.000")
    val currentModelCrane: State<String> = _currentModelCrane

    private val _currentPulleyBlock = mutableStateOf("Запасовка [... кр.]")
    val currentPulleyBlock: State<String> = _currentPulleyBlock

    private val _currentLoadCapacity = mutableStateOf("ГХ [...]")
    val currentLoadCapacity: State<String> = _currentLoadCapacity

    private val _craneId = mutableStateOf("CRANE_ID")
    val craneId: State<String> = _craneId

    private val _buildMode = mutableStateOf(0)
    val buildMode: State<Int> = _buildMode

    private val _serviceMode = mutableStateOf(0)
    val serviceMode: State<Int> = _serviceMode

    private val _montageMode = mutableStateOf(0)
    val montageMode: State<Int> = _montageMode

    private val addresses = listOf(90, 91, 94, 95, 98, 99, 150, 190, 191, 192, 193, 194, 367, 278, 279, 280, 281, 282) // Только нужные регистры

    // Для отслеживания предыдущих значений
    private var previousValues: Map<Int, Int?> = emptyMap()

    init {
        startDataCollection()
        setupAppStateMonitoring()
    }

    private fun startDataCollection() {
        viewModelScope.launch {
            modbusRepository.getConfigurationFlow(TopBarRegisterConfiguration.NAME_TOP_BAR)
                .collect { cache ->
                    // Фильтруем только нужные регистры
                    val currentValues = addresses.associate { addr ->
                        addr to cache[addr]?.value
                    }
                    // Проверяем, изменились ли значения целевых регистров
                    if (haveRelevantRegistersChanged(currentValues)) {

                        // Обновляем данные на основе отфильтрованного кэша
                        updateTime(currentValues)
                        updateTimeEvents(currentValues)
                        updateDate(currentValues)
                        updateModelCrane(currentValues)
                        updatePulleyBlock(currentValues)
                        updateLoadCapacity(currentValues)
                        updateCraneID(currentValues)
                        updateStatusMode(currentValues)

                        // Обработка звуковых регистров
                        for (register in 278..282) {
                            val currentValue = currentValues[register] ?: 0
                            val previousValue = previousValues[register] ?: 0
                            if (currentValue != previousValue) {
                                soundController.setRegisterState(register, currentValue == 1)
                            }
                        }
                        // Обработка звуковых регистров режима сервис и наладка
                        for (register in 190..191) {
                            val currentValue = currentValues[register] ?: 0
                            val previousValue = previousValues[register] ?: 0
                            if (currentValue != previousValue) {
                                soundController.setRegisterState(register, currentValue == 1)
                            }
                        }

                        // Сохраняем текущие значения для следующего сравнения
                        previousValues = currentValues
                    }
                }
        }
    }

    override fun onCleared() {
        super.onCleared()
        // Убрать вызов soundController.release()
        soundController.release()
    }
    // Функция для проверки изменений в relevant регистрах
    private fun haveRelevantRegistersChanged(currentValues: Map<Int, Int?>): Boolean {
        return addresses.any { address ->
            currentValues[address] != previousValues[address]
        }
    }

    private fun setupAppStateMonitoring() {
        val lifecycleObserver = object : DefaultLifecycleObserver {
            override fun onResume(owner: LifecycleOwner) {
                updateAppState(true)
            }

            override fun onPause(owner: LifecycleOwner) {
                updateAppState(false)
            }
        }
        ProcessLifecycleOwner.get().lifecycle.addObserver(lifecycleObserver)
    }

    private fun updateAppState(isActive: Boolean) {
        viewModelScope.launch {
            mutex.withLock {
                if (lastAppState != isActive) {
                    try {
                        delay(3000)
                        modbusRepository.writeRegister(893, if (isActive) 1 else 0)
                        lastAppState = isActive
                        _appActiveState.value = isActive
                    } catch (e: Exception) {
                        Log.e("TopBarViewModel", "Error writing to register 893", e)
                    }
                }
            }
        }
    }


    private fun updateTime(cache: Map<Int, Int?>) {
        val hour = cache[95] ?: return
        val minute = cache[98] ?: return
        _currentTime.value = String.format("%02d:%02d", hour, minute)
    }
    private fun updateTimeEvents(cache: Map<Int, Int?>) {
        val year = cache[90] ?: return
        val month = cache[91] ?: return
        val day = cache[94] ?: return
        val hour = cache[95] ?: return
        val minute = cache[98] ?: return
        val second = cache[99] ?: return

        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, second)
            set(Calendar.MILLISECOND, 0)
        }

        val timeInMillis = calendar.timeInMillis
        _currentTimeEvents.value = String.format(
            "%02d.%02d.%04d.%02d:%02d:%02d", day, month, year, hour, minute, second
        )

        eventTimeProvider.updateEventTime(timeInMillis)
    }

    private fun updateDate(cache: Map<Int, Int?>) {
        val year = cache[90] ?: return
        val month = cache[91] ?: return
        val day = cache[94] ?: return

        _currentDate.value = String.format("%02d.%02d.%04d", day, month, year)
    }

    private fun updateModelCrane(cache: Map<Int, Int?>) {
        val crane = cache[150] ?: return
        val numberStr = crane.toString().padStart(5, '0')
        _currentModelCrane.value = "TDK-${numberStr.take(2)}.${numberStr.takeLast(3)}"
    }

    private fun updatePulleyBlock(cache: Map<Int, Int?>) {
        val pulleyblock = cache[193] ?: return
        _currentPulleyBlock.value = when (pulleyblock) {
            1 -> "Запасовка [4х кр.]"
            else -> "Запасовка [2х кр.]"
        }
    }
    private fun updateLoadCapacity(cache: Map<Int, Int?>) {
        val loadCapacity = cache[194] ?: return
        _currentLoadCapacity.value = "Груз.хар. [%02d]".format(loadCapacity)
    }

    private fun updateCraneID(cache: Map<Int, Int?>) {
        val crane = cache[150] ?: return
        val serialNumber = cache[367] ?: return
        val numberStr = crane.toString().padStart(5, '0')
        val newCraneId = "TDK-${numberStr.take(2)}.${numberStr.takeLast(3)}.2кз_${serialNumber}"

        _craneId.value = newCraneId
        craneIdRepository.updateCraneId(newCraneId) // Обновляем значение в репозитории
    }

    private fun updateStatusMode(cache: Map<Int, Int?>) {
        val buildMode = cache[190] ?: 0
        val serviceMode = cache[191] ?: 0
        val montageMode = cache[192] ?: 0
        _buildMode.value = buildMode
        _serviceMode.value = serviceMode
        _montageMode.value = montageMode

    }

    fun playAccessDeniedSound() {
        soundController.playSoundOnce(R.raw.voice_access_denied_half_life)
    }

    fun playInactiveButtonSound() {
        soundController.playSoundOnce(R.raw.beep_sound_neaktive_knopka_half_life)
    }
}

/**
Объяснение решения:
DateTimeViewModel:
Подписывается на registerCache из ModbusRepository
Преобразует значения регистров в форматированные строки времени и даты
Использует State для реактивного обновления UI
Обрабатывает отсутствие значений (показывает "--:--:--" и "--.--.----")
 */