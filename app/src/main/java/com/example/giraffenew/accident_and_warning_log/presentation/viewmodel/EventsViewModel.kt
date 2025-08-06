package com.example.giraffenew.accident_and_warning_log.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.accident_and_warning_log.data.remoute.RegisterStatusDataSource
import com.example.giraffenew.accident_and_warning_log.domian.model.EventEntity
import com.example.giraffenew.accident_and_warning_log.domian.usecase.ClearArchiveUseCase
import com.example.giraffenew.accident_and_warning_log.domian.usecase.GetActiveEventsUseCase
import com.example.giraffenew.accident_and_warning_log.domian.usecase.GetArchiveEventsUseCase
import com.example.giraffenew.accident_and_warning_log.domian.usecase.HandleStatusChangeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
 // — специальная аннотация для Hilt, которая:
// - Позволяет Hilt управлять жизненным циклом ViewModel
// - Обеспечивает корректную интеграцию с системой DI

open class EventsViewModel
@Inject constructor( //— указывает на использование DI для внедрения зависимостей

    //Содержит зависимости, которые представляют бизнес-логику через Use Case:
    private val getActiveEvents: GetActiveEventsUseCase, //use case для получения активных событий
    private val clearArchive: ClearArchiveUseCase, //use case для очистки архива
    private val getArchivedEvents: GetArchiveEventsUseCase, //use case для отображения архивных событий
    private val handleStatusChange: HandleStatusChangeUseCase,

    //обработка состояний регистров
    private val registerDataSource: RegisterStatusDataSource
): ViewModel() {

    init {
        viewModelScope.launch {
            // Подписка на изменения состояний регистров
            // Инициализация при старте
            val initialStatuses = registerDataSource.registerStatuses.first()
            initialStatuses.forEach { (register, active) ->
                handleStatusChange(register, active)
            }

            // Дальнейшие обновления
            registerDataSource.registerStatuses
                .drop(1) // Пропускаем начальное состояние
                .collect { statusMap ->
                    statusMap.forEach { (register, active) ->
                        handleStatusChange(register, active)
                    }
                }
        }
    }

    //Свойство activeEvents:
    //- getActiveEvents() — вызов use case, который возвращает Flow.
    //- stateIn() — преобразование Flow в StateFlow с параметрами:
        //- scope = viewModelScope — определяет область видимости
        //- SharingStarted.WhileSubscribed(5000) — настройка автоматического запуска:
        //- Запускает сбор данных при наличии подписчика
        //- Сохраняет соединение 5 секунд после отписки
    //- initialValue — начальное значение (пустой список)

    open val activeEvents: StateFlow<List<EventEntity>> = getActiveEvents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
    //Оператор .stateIn в Kotlin преобразует «холодный» поток (Flow) в «горячий» поток состояний (StateFlow).
    //Это позволяет делиться последним выдаваемым значением из одного запущенного экземпляра восходящего потока с несколькими подписчиками.
    //scope = viewModelScope, //— область действия сопрограммы, в которой начинается совместное использование.
    //started = SharingStarted.WhileSubscribed(5000), //started — стратегия, управляющая началом и остановкой совместного использования.
    //initialValue = emptyList() //initialValue — начальное значение потока состояний.
    // Это значение также используется при сбросе потока состояний с помощью стратегии SharingStarted.WhileSubscribed с параметром stopTimeoutMillis.


// По аналогии добавляем StateFlow для архивных событий
    open val archivedEvents: StateFlow<List<EventEntity>> = getArchivedEvents()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun clearArchive() = viewModelScope.launch { //запускает корутину в контексте ViewModel
        clearArchive //вызывает use case для очистки архива
    }
}