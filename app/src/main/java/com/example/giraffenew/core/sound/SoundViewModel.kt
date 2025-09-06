package com.example.giraffenew.core.sound

import android.util.Log
import com.example.giraffenew.R
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.entities.BeepSoundRegisterConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SoundViewModel
    @Inject constructor(
        private val soundManager: SoundManager,
        private val modbusRepository: ModbusRepository
    ) : ViewModel() {

    val addressesBeepSound = listOf(177)

    private var monitoringJob: Job? = null

    init {
        // Переносим конфигурацию в ViewModel
        configureSound("weight", R.raw.beep_sound_weight, 1000L)
        configureSound("stop", R.raw.beep_sound_stop, 5000L)
        configureSound("dwn", R.raw.beep_sound_dwn, 3000L)
        configureSound("attention", R.raw.beep_sound_attention, 10000L)
        startBeepSoundMonitoring()
    }
    private fun startBeepSoundMonitoring() {
        viewModelScope.launch {
            modbusRepository.getConfigurationFlow(BeepSoundRegisterConfiguration.NAME_BEEP_SOUND)
                .collect { cache ->
                    try {
                        // Создаем отфильтрованную карту только для нужных адресов
                        val filteredCache = addressesBeepSound.associate { addr ->
                            addr to cache[addr]?.value
                        }

                        // Обрабатываем изменения регистров
                        handleRegisterChanges(filteredCache)
                    } catch (e: Exception) {
                        Log.e("SoundViewModel", "Data collection error", e)
                    }
                }
        }
    }


    private fun handleRegisterChanges(cache: Map<Int, Int?>) {
        // Обрабатываем регистр 177 для звука "weight"
        val beepWeighValue = cache[177] ?: 0

        if (beepWeighValue != 0) {
            setSoundState("weight", 1) // Включить звук
        } else {
            setSoundState("weight", 0) // Выключить звук
        }

        // Добавьте обработку других регистов здесь по аналогии
        // Например:
        // val stopValue = cache[178] ?: 0
        // if (stopValue != 0) setSoundState("stop", 1) else setSoundState("stop", 0)
    }



    // Остальной код без изменений
    private val _soundState = MutableStateFlow(0)
    val soundState: StateFlow<Int> = _soundState

    private val soundConfigurations = mutableMapOf<String, Pair<Int, Long>>()

    fun configureSound(type: String, soundResId: Int, delayBetweenRepeats: Long) {
        soundConfigurations[type] = Pair(soundResId, delayBetweenRepeats)
    }

    fun setSoundState(type: String, state: Int) {
        _soundState.value = state
        if (state == 1) {
            soundConfigurations[type]?.let { (resId, delay) ->
                soundManager.setSound(resId, delay)
                soundManager.startSound()
            }
        } else {
            soundManager.stopSound()
        }
    }
}