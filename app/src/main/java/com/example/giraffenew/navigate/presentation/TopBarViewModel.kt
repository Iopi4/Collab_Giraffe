package com.example.giraffenew.navigate.presentation

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
import com.example.giraffenew.data.repositories.SettingsRepository
import com.example.giraffenew.initial_setup.data.CraneRepository
import com.example.giraffenew.initial_setup.data.TimeZoneRepository
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

//Создание ViewModel для управления временем, датой и типом крана
@HiltViewModel
class TopBarViewModel @Inject constructor(
    private val modbusRepository: ModbusRepository,
    private val craneIdRepository: CraneIdRepository,
    val soundController: SoundController,
    private val eventTimeProvider: EventTimeProvider,
    private val craneRepository: CraneRepository,
    private var timeZoneRepository: TimeZoneRepository,
    private val settingsRepository: SettingsRepository
    // Добавим репозиторий хранения craneId
) : ViewModel() {

    private val _appActiveState = mutableStateOf(true)
    private var lastAppState: Boolean? = null
    private val mutex = Mutex()


    //1.1 Создадим переменные и функцию для управления уровнем громкости звуковых сигналов на главном экрана
    // Начальное значение Medium. Уровень громкости для звукового оповешения
    private val _currentVolume = mutableStateOf(settingsRepository.getVolumeLevel())
    val currentVolume: State<Float> = _currentVolume

    private val volumeLevels = listOf(0.7f, 1f, 0.5f) //Low, Medium, Max




    private val _currentTime = mutableStateOf("00:00")
    val currentTime: State<String> = _currentTime

    private val _currentTimeEvents = mutableStateOf("00:00:00")
    val currentTimeEvents: State<String> = _currentTimeEvents

    private val _currentDate = mutableStateOf("00:00:0000")
    val currentDate: State<String> = _currentDate

    private val _currentModelCrane = mutableStateOf("XXX-00.000")
    val currentModelCrane: State<String> = _currentModelCrane

    private val _currentTimeZone = mutableStateOf("")
    val currentTimeZone: State<String> = _currentTimeZone

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

    private val addresses = listOf(90, 91, 94, 95, 98, 99, 150, 190, 191, 192, 193, 194,
        367, 278, 279, 280, 281, 282, 641) // Только нужные регистры

    // Для отслеживания предыдущих значений
    private var previousValues: Map<Int, Int?> = emptyMap()

    // Для отслеживания текущего craneId
    private var currentCraneId: Int? = null
    private var currentTimeZoneUTC: Int? = null

    init {
        Log.d("TopBarViewModel", "Init called, initial volume: ${_currentVolume.value}")
        startDataCollection()
        setupAppStateMonitoring()

        // Запускаем наблюдение за изменениями в базе данных кранов
        observeCraneDatabase()
        // Запускаем наблюдение за изменениями в базе данных часовых поясов
        observeTimeZoneDatabase()

        // Устанавливаем независимую громкость при инициализации
        soundController.setAppVolumeIndependent()
    }

    fun toggleVolume() {
        val currentIndex = volumeLevels.indexOf(_currentVolume.value)
        val nextIndex = (currentIndex + 1) % volumeLevels.size
        val newVolume = volumeLevels[nextIndex]

        _currentVolume.value = newVolume
        settingsRepository.saveVolumeLevel(newVolume)

        // Применяем новую громкость
        soundController.setAppVolumeIndependent()

        Log.d("TopBarViewModel", "Volume toggled to: $newVolume")
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
                        updateTimeZone(currentValues)
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

    //override fun onCleared() {
    //super.onCleared()
    // Убрать вызов soundController.release()
    //soundController.release()
    //}

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

    //Записываем в регистр состояние активности приложения. Если приложение активно отправляем 1.
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
            "%02d.%02d.%04d.%02d:%02d:%02d", day, month, year, hour, minute, second)
        eventTimeProvider.updateEventTime(timeInMillis)
    }



    private fun updateDate(cache: Map<Int, Int?>) {
        val year = cache[90] ?: return
        val month = cache[91] ?: return
        val day = cache[94] ?: return

        _currentDate.value = String.format("%02d.%02d.%04d", day, month, year)
    }

    // Функция для наблюдения за изменениями в базе данных
    private fun observeCraneDatabase() {
        viewModelScope.launch {
            // Подписываемся на изменения всех кранов
            craneRepository.getAllCranesFlow()
                .collect { cranes ->
                    // Если у нас есть текущий craneId, обновляем модель
                    currentCraneId?.let { id ->
                        updateModelFromDatabase(id)
                    }
                }
        }
    }

    //Чтобы TopBarViewModel реагировал на изменения в базе данных кранов в реальном времени, надо использовать Flow:
    private fun updateModelCrane(cache: Map<Int, Int?>) {
        val craneId = cache[150] ?: return
        currentCraneId = craneId
        updateModelFromDatabase(craneId)
    }

    private fun updateModelFromDatabase(craneId: Int) {
        viewModelScope.launch {
            try {
                // Используем Flow для получения модели крана
                craneRepository.getCraneModelFlow(craneId)
                    .collect { model ->
                        _currentModelCrane.value = model ?: "XXX-00.000"
                    }
            } catch (e: Exception) {
                _currentModelCrane.value = "ОШИБКА"
                Log.e("TopBarViewModel", "Ошибка получения модели крана: ${e.message}")
            }
        }
    }

    //Чтобы TopBarViewModel реагировал на изменения в базе данных часовых поясов, надо использовать Flow:
    private fun updateTimeZone(cache: Map<Int, Int?>) {
        val timeZoneUTC = cache[641] ?: return
        currentTimeZoneUTC = timeZoneUTC
        updateTimeZoneFromDatabase(timeZoneUTC)
    }
    private fun observeTimeZoneDatabase() {
        viewModelScope.launch {
            // Подписываемся на изменения часовых поясов
            timeZoneRepository.getAllTimeZoneFlow()
                .collect { timeZones ->
                    // Если у нас есть текущий timeZoneUTC, обновляем модель
                    currentTimeZoneUTC?.let { id ->
                        updateTimeZoneFromDatabase(id)
                    }
                }
        }
    }
    private fun updateTimeZoneFromDatabase(timeZoneUTC: Int) {
        viewModelScope.launch {
            try {
                // Используем Flow для получения модели крана
                timeZoneRepository.getTimeZoneFlow(timeZoneUTC)
                    .collect { timeZone ->
                        _currentTimeZone.value = timeZone ?: "UTC+X(MCK+XX)"
                    }
            } catch (e: Exception) {
                _currentTimeZone.value = "ОШИБКА"
                Log.e("InitialSetupViewModel", "Ошибка получения часового пояса: ${e.message}")
            }
        }
    }

    private fun updateCraneID(cache: Map<Int, Int?>) {
        val serialNumber = cache[367] ?: return
        val model = _currentModelCrane.value
        val newCraneId = "${model}_${serialNumber}"

        _craneId.value = newCraneId
        craneIdRepository.updateCraneId(newCraneId) // Обновляем значение в репозитории
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


    //Закомментировать.
    override fun onCleared() {
        super.onCleared()
        //soundController.release()
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