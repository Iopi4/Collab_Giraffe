package com.example.giraffenew.android_error_logger.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.giraffenew.android_error_logger.data.repository.RepositoryDiagnostics
import com.example.giraffenew.android_error_logger.domian.model.ExceptionEntity
import com.example.giraffenew.android_error_logger.domian.model.LogExceptionLevel
import com.example.giraffenew.android_error_logger.domian.usecase.ClearExceptionsUseCase
import com.example.giraffenew.android_error_logger.domian.usecase.GetActiveOperationsUseCase
import com.example.giraffenew.android_error_logger.domian.usecase.GetAllExceptionsUseCase
import com.example.giraffenew.android_error_logger.domian.usecase.GetExceptionsByLevelUseCase
import com.example.giraffenew.android_error_logger.domian.usecase.GetRepositoryDiagnosticsUseCase
import com.example.giraffenew.database_monitoring.DatabaseMonitor
import com.example.giraffenew.database_monitoring.MemoryUsage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
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
 */

@HiltViewModel
class ExceptionsViewModel @Inject constructor(
    private val getAllExceptionsUseCase: GetAllExceptionsUseCase,
    private val getExceptionsByLevelUseCase: GetExceptionsByLevelUseCase,
    private val clearExceptionsUseCase: ClearExceptionsUseCase,
    private val getRepositoryDiagnosticsUseCase: GetRepositoryDiagnosticsUseCase,
    private val databaseMonitor: DatabaseMonitor, // Добавляем DatabaseMonitor
    private val getActiveOperationsUseCase: GetActiveOperationsUseCase, // Новый UseCase
) : ViewModel() {

    private companion object {
        const val TAG = "ExceptionsViewModel"
    }

    private val _exceptions = MutableStateFlow<List<ExceptionEntity>>(emptyList())
    val exceptions: StateFlow<List<ExceptionEntity>> = _exceptions.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _selectedLevel = MutableStateFlow<LogExceptionLevel?>(null)
    val selectedLevel: StateFlow<LogExceptionLevel?> = _selectedLevel.asStateFlow()

    private val _diagnostics = MutableStateFlow<RepositoryDiagnostics?>(null)
    val diagnostics: StateFlow<RepositoryDiagnostics?> = _diagnostics.asStateFlow()

    // Добавляем StateFlow для мониторинга памяти
    val memoryUsage: StateFlow<MemoryUsage> = databaseMonitor.memoryUsage

    //добавляем мониторинг активных операций
    val activeOperation: StateFlow<Int> = getActiveOperationsUseCase()

    init {
        refreshData()
    }


    fun refreshData() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                val level = _selectedLevel.value
                val result = if (level == null) {
                    getAllExceptionsUseCase()
                } else {
                    getExceptionsByLevelUseCase(level)
                }
                _exceptions.value = result

                // Обновляем диагностику
                _diagnostics.value = getRepositoryDiagnosticsUseCase()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to refresh exceptions data", e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun setFilter(level: LogExceptionLevel?) {
        _selectedLevel.value = level
        refreshData()
    }

    fun clearAllExceptions() {
        viewModelScope.launch(Dispatchers.IO) {
            _loading.value = true
            try {
                clearExceptionsUseCase()
                refreshData() // Обновляем данные после очистки
            } catch (e: Exception) {
                Log.e(TAG, "Failed to clear exceptions", e)
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateDiagnostics() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _diagnostics.value = getRepositoryDiagnosticsUseCase()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to update diagnostics", e)
            }
        }
    }
}