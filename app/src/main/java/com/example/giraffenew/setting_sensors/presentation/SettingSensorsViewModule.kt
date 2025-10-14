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
        155, 158, 159, 162, 212, 803, 804, 805, 806, 807, 808, 809, 810, 811, 812, 813,
        340, 341, 342, 343, 344, 345, 346, 347, 348, 349, 350, 351, 352, 353,
        730, 731, 732, 733, 734, 735, 736, 737, 204, 205, 206, 207, 1623, 1648, 1699, 1738,
        160, 161, 338, 339, 156, 157, 354, 355, 356, 357, 358, 359, 372,
        382, 383, 384, 385, 386, 387, 388, 389, 390, 391, 392, 393,
        663, 664, 665, 666, 667, 668, 669, 670, 671, 672, 673, 674,
        675, 676, 677, 678, 679, 680, 681, 682, 683, 684, 685, 686,
        687, 688, 689, 690, 691, 692, 693, 694, 695, 696, 697, 698,
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
            sensorsHoistState = SensorsHoistState(
                currentHoistPosition = safeDivideTens(cache[159] ?: 0),
                //Статус теста ограничений подъема
                currentHoistStopDwn1 = cache[341] ?: 0,
                currentHoistStopUp1 = cache[342] ?: 0,
                currentHoistDwnDwnSp1 = cache[343] ?: 0,
                currentHoistUpDwnSp1 = cache[344] ?: 0,
                //Лампа. ошибки выхода за рабочий диапазон показаний энкодера
                currentHoistEncoderMaxDeltaError = cache[730] ?: 0,
                currentHoistEncoderMaxValueError = cache[734] ?: 0,
                currentHoistAbsEncErrCanOpen = cache[204] ?: 0, //-----ошибка связи энкодеров
                //Данные с абсолютного энкодера
                currentAbsEncHoistPositionWord1 = cache[382] ?: 0,
                currentAbsEncHoistPositionWord2 = cache[383] ?: 0,
                currentAbsEncHoistPositionWord3 = cache[384] ?: 0,
                currentAbsEncHoistPositionWord4 = cache[385] ?: 0,
                currentAbsEncHoistPositionWord5 = cache[386] ?: 0,
                currentAbsEncHoistPositionWord6 = cache[387] ?: 0,
                currentAbsEncHoistPositionWord7 = cache[388] ?: 0,
                currentAbsEncHoistPositionWord8 = cache[389] ?: 0,
                currentAbsEncHoistPositionWord9 = cache[390] ?: 0,
                currentAbsEncHoistPositionWord10 = cache[391] ?: 0,
                currentAbsEncHoistPositionWord11 = cache[392] ?: 0,
                currentAbsEncHoistPositionWord12 = cache[393] ?: 0,


                hoistLimitStatus = cache[1623] ?: 0,

                hoistMaxPosition = safeDivideTens(cache[160] ?: 0),
                hoistMinPosition = safeDivideTens(cache[161] ?: 0),
                hoistPosFwdDwnSp = safeDivideTens(cache[338] ?: 0),
                hoistPosRevDwnSp = safeDivideTens(cache[339] ?: 0),
                hoistZeroPosition = safeDivideTens(cache[340] ?: 0),
            ),
            sensorsSlewState = SensorsSlewState(
                currentSlewPosition = safeDivideTens(cache[158] ?: 0),
                //Лампа. ошибки выхода за рабочий диапазон показаний энкодера
                currentSlewEncoderMaxDeltaError = cache[731] ?: 0,
                currentSlewEncoderMaxValueError = cache[735] ?: 0,
                currentSlewAbsEncErrCanOpen = cache[205] ?: 0, //-----ошибка связи энкодеров
                //Данные с абсолютного энкодера
                currentAbsEncSlewPositionWord1 = cache[663] ?: 0,
                currentAbsEncSlewPositionWord2 = cache[664] ?: 0,
                currentAbsEncSlewPositionWord3 = cache[665] ?: 0,
                currentAbsEncSlewPositionWord4 = cache[666] ?: 0,
                currentAbsEncSlewPositionWord5 = cache[667] ?: 0,
                currentAbsEncSlewPositionWord6 = cache[668] ?: 0,
                currentAbsEncSlewPositionWord7 = cache[669] ?: 0,
                currentAbsEncSlewPositionWord8 = cache[670] ?: 0,
                currentAbsEncSlewPositionWord9 = cache[671] ?: 0,
                currentAbsEncSlewPositionWord10 = cache[672] ?: 0,
                currentAbsEncSlewPositionWord11 = cache[673] ?: 0,
                currentAbsEncSlewPositionWord12 = cache[674] ?: 0,

                slewLimitStatus = cache[1648] ?: 0,
            ),

            sensorsTrolleyState = SensorsTrolleyState(
                currentTrolleyPosition = safeDivideTens(cache[155] ?: 0),
                //Статус теста ограничений Тележки
                currentTrolleyStopFwd1 = cache[345] ?: 0,
                currentTrolleyStopRev1 = cache[346] ?: 0,
                currentTrolleyFwdDwnSp1 = cache[347] ?: 0,
                currentTrolleyRevDwnSp1 = cache[348] ?: 0,
                //Лампа. ошибки выхода за рабочий диапазон показаний энкодера
                currentTrolleyEncoderMaxDeltaError = cache[732] ?: 0,
                currentTrolleyEncoderMaxValueError = cache[736] ?: 0,
                currentTrolleyAbsEncErrCanOpen = cache[206] ?: 0, //-----ошибка связи энкодеров
                //Данные с абсолютного энкодера
                currentAbsEncTrolleyPositionWord1 = cache[675] ?: 0,
                currentAbsEncTrolleyPositionWord2 = cache[676] ?: 0,
                currentAbsEncTrolleyPositionWord3 = cache[677] ?: 0,
                currentAbsEncTrolleyPositionWord4 = cache[678] ?: 0,
                currentAbsEncTrolleyPositionWord5 = cache[679] ?: 0,
                currentAbsEncTrolleyPositionWord6 = cache[680] ?: 0,
                currentAbsEncTrolleyPositionWord7 = cache[681] ?: 0,
                currentAbsEncTrolleyPositionWord8 = cache[682] ?: 0,
                currentAbsEncTrolleyPositionWord9 = cache[683] ?: 0,
                currentAbsEncTrolleyPositionWord10 = cache[684] ?: 0,
                currentAbsEncTrolleyPositionWord11 = cache[685] ?: 0,
                currentAbsEncTrolleyPositionWord12 = cache[686] ?: 0,

                trolleyLimitStatus = cache[1699] ?: 0,

                trolleyMaxPosition = safeDivideTens(cache[156] ?: 0),
                trolleyMinPosition = safeDivideTens(cache[157] ?: 0),
                trolleyPosFwdDwnSp = safeDivideTens(cache[354] ?: 0),
                trolleyPosRevDwnSp = safeDivideTens(cache[355] ?: 0),

            ),

            sensorsCraneState = SensorsCraneState(
                currentCranePosition = safeDivideTens(cache[162] ?: 0),
                currentCraneRailsEn = cache[212] ?: 0,
                //Статус теста ограничений Хода крана
                currentCraneStopFwd1 = cache[349] ?: 0,
                currentCraneStopRev1 = cache[350] ?: 0,
                currentCraneFwdDwnSp1 = cache[351] ?: 0,
                currentCraneRevDwnSp1 = cache[352] ?: 0,
                currentCraneRepPoint = cache[353] ?: 0,
                //Лампа. ошибки выхода за рабочий диапазон показаний энкодера
                currentCraneEncoderMaxDeltaError = cache[733] ?: 0,
                currentCraneEncoderMaxValueError = cache[737] ?: 0,
                currentCraneAbsEncErrCanOpen = cache[207] ?: 0, //-----ошибка связи энкодеров
                //Данные с абсолютного энкодера
                currentAbsEncCranePositionWord1 = cache[687] ?: 0,
                currentAbsEncCranePositionWord2 = cache[688] ?: 0,
                currentAbsEncCranePositionWord3 = cache[689] ?: 0,
                currentAbsEncCranePositionWord4 = cache[690] ?: 0,
                currentAbsEncCranePositionWord5 = cache[691] ?: 0,
                currentAbsEncCranePositionWord6 = cache[692] ?: 0,
                currentAbsEncCranePositionWord7 = cache[693] ?: 0,
                currentAbsEncCranePositionWord8 = cache[694] ?: 0,
                currentAbsEncCranePositionWord9 = cache[695] ?: 0,
                currentAbsEncCranePositionWord10 = cache[696] ?: 0,
                currentAbsEncCranePositionWord11 = cache[697] ?: 0,
                currentAbsEncCranePositionWord12 = cache[698] ?: 0,

                craneLimitStatus = cache[1738] ?: 0,

                craneMaxPosition = safeDivideTens(cache[356] ?: 0),
                craneMinPosition = safeDivideTens(cache[357] ?: 0),
                cranePosFwdDwnSp = safeDivideTens(cache[358] ?: 0),
                cranePosRevDwnSp = safeDivideTens(cache[359] ?: 0),
                cranePosCTRLPoint = safeDivideTens(cache[372] ?: 0),
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
        val currentAbsEncHoistPositionWord3: Int = 0,
        val currentAbsEncHoistPositionWord4: Int = 0,
        val currentAbsEncHoistPositionWord5: Int = 0,
        val currentAbsEncHoistPositionWord6: Int = 0,
        val currentAbsEncHoistPositionWord7: Int = 0,
        val currentAbsEncHoistPositionWord8: Int = 0,
        val currentAbsEncHoistPositionWord9: Int = 0,
        val currentAbsEncHoistPositionWord10: Int = 0,
        val currentAbsEncHoistPositionWord11: Int = 0,
        val currentAbsEncHoistPositionWord12: Int = 0,

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
        val currentAbsEncSlewPositionWord3: Int = 0,
        val currentAbsEncSlewPositionWord4: Int = 0,
        val currentAbsEncSlewPositionWord5: Int = 0,
        val currentAbsEncSlewPositionWord6: Int = 0,
        val currentAbsEncSlewPositionWord7: Int = 0,
        val currentAbsEncSlewPositionWord8: Int = 0,
        val currentAbsEncSlewPositionWord9: Int = 0,
        val currentAbsEncSlewPositionWord10: Int = 0,
        val currentAbsEncSlewPositionWord11: Int = 0,
        val currentAbsEncSlewPositionWord12: Int = 0,

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
        val currentAbsEncTrolleyPositionWord3: Int = 0,
        val currentAbsEncTrolleyPositionWord4: Int = 0,
        val currentAbsEncTrolleyPositionWord5: Int = 0,
        val currentAbsEncTrolleyPositionWord6: Int = 0,
        val currentAbsEncTrolleyPositionWord7: Int = 0,
        val currentAbsEncTrolleyPositionWord8: Int = 0,
        val currentAbsEncTrolleyPositionWord9: Int = 0,
        val currentAbsEncTrolleyPositionWord10: Int = 0,
        val currentAbsEncTrolleyPositionWord11: Int = 0,
        val currentAbsEncTrolleyPositionWord12: Int = 0,

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
        val currentAbsEncCranePositionWord3: Int = 0,
        val currentAbsEncCranePositionWord4: Int = 0,
        val currentAbsEncCranePositionWord5: Int = 0,
        val currentAbsEncCranePositionWord6: Int = 0,
        val currentAbsEncCranePositionWord7: Int = 0,
        val currentAbsEncCranePositionWord8: Int = 0,
        val currentAbsEncCranePositionWord9: Int = 0,
        val currentAbsEncCranePositionWord10: Int = 0,
        val currentAbsEncCranePositionWord11: Int = 0,
        val currentAbsEncCranePositionWord12: Int = 0,

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
                modbusRepository.writeRegister(805, 1)
                delay(200)
                modbusRepository.writeRegister(805, 0)
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
                modbusRepository.writeRegister(895, 1)
                delay(200)
                modbusRepository.writeRegister(895, 0)
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
                modbusRepository.writeRegister(806, model)
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
                modbusRepository.writeRegister(807, model)
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
                modbusRepository.writeRegister(894, model)
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
                modbusRepository.writeRegister(808, model)
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
                modbusRepository.writeRegister(809, model)
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
                modbusRepository.writeRegister(825, 1)
                delay(200)
                modbusRepository.writeRegister(825, 0)
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
                modbusRepository.writeRegister(827, model)
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
                modbusRepository.writeRegister(826, model)
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
                modbusRepository.writeRegister(828, model)
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
                modbusRepository.writeRegister(829, model)
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
                modbusRepository.writeRegister(817, 1)
                delay(200)
                modbusRepository.writeRegister(817, 0)
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
                modbusRepository.writeRegister(818, 1)
                delay(200)
                modbusRepository.writeRegister(818, 0)
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
                modbusRepository.writeRegister(816, model)
                _errorMessageSlew.value = null
                _logMessageSlew.value = "Сохранено"
            } catch (e: Exception) {
                _logMessageSlew.value = null
                _errorMessageSlew.value = "${e.message}"
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
                val model = validateAndConvertFloat(_setValueCranePosFwdStop.value, 0.0f, 300.0f )
                // Запись в регистр
                modbusRepository.writeRegister(838, model)
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
                modbusRepository.writeRegister(839, model)
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
                modbusRepository.writeRegister(840, model)
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
                modbusRepository.writeRegister(841, model)
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
                modbusRepository.writeRegister(835, model)
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