package com.example.giraffenew.accident_and_warning_log.domian.usecase

import com.example.giraffenew.accident_and_warning_log.data.repository.EventRepository
import com.example.giraffenew.accident_and_warning_log.presentation.viewmodel.EventTimeProvider
import javax.inject.Inject

//Обработка изменения статуса
class HandleStatusChangeUseCase
@Inject constructor (//используется для внедрения зависимостей (DI) в класс
    //объявим приватное поле repository для работы с данными
    private val repository: EventRepository
)
 {
     suspend operator fun invoke (registrationNumber: String, isActive: Boolean, eventTime: Long)
     //suspend — метод асинхронный
     //operator fun invoke — позволяет использовать экземпляр класса как функцию
     //Метод invoke переопределяет оператор вызова (operator fun invoke).
     {
         repository.handleStatusChange( registrationNumber, isActive, eventTime)
        //Так мы обрщаемся в функцию handleStatusChange из EventRepository для обработки номера регистра и его статуса
     }
}

//Функциональность:
// - Принимает номер регистрации и статус активности
// - Передает параметры в репозиторий для обработки

//Пример использования: handleStatus("REG-001", true)