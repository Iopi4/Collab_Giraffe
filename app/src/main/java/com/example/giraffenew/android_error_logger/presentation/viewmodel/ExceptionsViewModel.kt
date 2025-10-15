package com.example.giraffenew.android_error_logger.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.android_error_logger.domian.model.ExceptionEntity
import com.example.giraffenew.android_error_logger.domian.model.LogExceptionLevel
import com.example.giraffenew.android_error_logger.domian.usecase.ClearExceptionsUseCase
import com.example.giraffenew.android_error_logger.domian.usecase.GetAllExceptionsUseCase
import com.example.giraffenew.android_error_logger.domian.usecase.GetExceptionsByLevelUseCase
import com.example.giraffenew.android_error_logger.domian.usecase.LogExceptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExceptionsViewModel @Inject constructor(
    private val getAllExceptionsUseCase: GetAllExceptionsUseCase,
    private val getExceptionsByLevelUseCase: GetExceptionsByLevelUseCase,
    private val clearExceptionsUseCase: ClearExceptionsUseCase,
    private val logExceptionUseCase: LogExceptionUseCase
) : ViewModel() {

    private val _selectedLevel = MutableStateFlow<LogExceptionLevel?>(null)
    val selectedLevel: StateFlow<LogExceptionLevel?> = _selectedLevel.asStateFlow()

    val allExceptions: StateFlow<List<ExceptionEntity>> = getAllExceptionsUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val filteredExceptions: StateFlow<List<ExceptionEntity>> =
        _selectedLevel.flatMapLatest { level ->
            if (level == null) {
                getAllExceptionsUseCase()
            } else {
                getExceptionsByLevelUseCase(level)
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun setFilter(level: LogExceptionLevel?) {
        _selectedLevel.value = level
    }

    fun clearAllExceptions() {
        viewModelScope.launch(Dispatchers.IO) {
            clearExceptionsUseCase()
        }
    }
}