package com.example.giraffenew.navigate.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.data.entities.TopBarRegisterConfiguration
import com.example.giraffenew.data.repositories.ModbusRepository
import com.example.giraffenew.network.CraneIdRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

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
    private val craneIdRepository: CraneIdRepository // Добавим репозиторий хранения craneId
) : ViewModel() {
    private val _currentTime = mutableStateOf("00:00:00")
    val currentTime: State<String> = _currentTime

    private val _currentDate = mutableStateOf("00:00:0000")
    val currentDate: State<String> = _currentDate

    private val _currentModelCrane = mutableStateOf("TDK-00.000")
    val currentModelCrane: State<String> = _currentModelCrane

    private val _currentPulleyBlock = mutableStateOf("Запасовка [... кр.]")
    val currentPulleyBlock: State<String> = _currentPulleyBlock

    private val _currentLoadCapacity = mutableStateOf("ГХ [...]")
    val currentLoadCapacity: State<String> = _currentLoadCapacity

    private val addresses = listOf(90, 91, 94, 95, 98, 150, 193, 194, 367 ) // Только нужные регистры

    // Для отслеживания предыдущих значений
    private var previousValues: Map<Int, Int?> = emptyMap()

    private val _craneId = mutableStateOf("CRANE_ID")
    val craneId: State<String> = _craneId

        //Однако, в текущей реализации TopBarViewModel, коллектор срабатывает при каждом обновлении кэша (даже если обновлены регистры не из списка).
        //Но сам коллектор срабатывает при каждом обновлении кэша (даже если обновлен регистр не из нашего списка).
        //

    init {
        startDataCollection()
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
                        Log.d("TopBarViewModel", "Relevant registers changed: $currentValues")

                        // Обновляем данные на основе отфильтрованного кэша
                        updateTime(currentValues)
                        updateDate(currentValues)
                        updateModelCrane(currentValues)
                        updatePulleyBlock(currentValues)
                        updateLoadCapacity(currentValues)
                        updateCraneID(currentValues)

                        // Сохраняем текущие значения для следующего сравнения
                        previousValues = currentValues
                    }
                }
        }
    }
    // Функция для проверки изменений в relevant регистрах
    private fun haveRelevantRegistersChanged(currentValues: Map<Int, Int?>): Boolean {
        return addresses.any { address ->
            currentValues[address] != previousValues[address]
        }
    }
    private fun updateTime(cache: Map<Int, Int?>) {
        val hour = cache[95] ?: return
        val minute = cache[98] ?: return
        _currentTime.value = String.format("%02d:%02d", hour, minute)
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
}

/**
Объяснение решения:
DateTimeViewModel:
Подписывается на registerCache из ModbusRepository
Преобразует значения регистров в форматированные строки времени и даты
Использует State для реактивного обновления UI
Обрабатывает отсутствие значений (показывает "--:--:--" и "--.--.----")
 */