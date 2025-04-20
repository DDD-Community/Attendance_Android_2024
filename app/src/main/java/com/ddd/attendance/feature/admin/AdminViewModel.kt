package com.ddd.attendance.feature.admin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.attendance.core.domain.usecase.ScheduleUseCase
import com.ddd.attendance.core.model.ScheduleUiState
import com.ddd.attendance.core.utils.default
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * 운영진 QR 스캔
 * */
@HiltViewModel
class AdminViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val scheduleUseCase: ScheduleUseCase,
) : ViewModel() {

    private val _menu: MutableStateFlow<String> = MutableStateFlow("출석")
    val menu: StateFlow<String> = _menu

    fun setMenu(value: String) {
        _menu.value = value
    }

    val scheduleUiState: StateFlow<ScheduleUiState> = scheduleUseCase()
        .map { data ->
            if (data.isNotEmpty()) {
                ScheduleUiState.Success(data)
            } else {
                ScheduleUiState.Empty
            }
        }
        .catch { t ->
            emit(ScheduleUiState.Error(t.message.default()))
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            ScheduleUiState.Loading
        )

}