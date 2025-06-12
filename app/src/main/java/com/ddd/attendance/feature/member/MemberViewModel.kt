package com.ddd.attendance.feature.member

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.attendance.core.domain.usecase.attendance.AttendanceCountUseCase
import com.ddd.attendance.core.domain.usecase.attendance.AttendanceListUseCase
import com.ddd.attendance.feature.main.model.AttendanceCountUiState
import com.ddd.attendance.feature.main.model.AttendanceListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val attendanceCountUseCase: AttendanceCountUseCase,
    private val attendanceListUseCase: AttendanceListUseCase
) : ViewModel() {

    val attendanceListUiState: StateFlow<AttendanceListUiState> =
        attendanceListUseCase()
            .map { data ->
                AttendanceListUiState.Success(data)
            }
            .catch { e ->
                AttendanceListUiState.Error(e.message?: "Unknown Error")
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000L),
                AttendanceListUiState.Loading
            )

    val attendanceCountUiState: StateFlow<AttendanceCountUiState> =
        attendanceCountUseCase()
            .map { data ->
                AttendanceCountUiState.Success(data)
            }
            .catch { e ->
               AttendanceCountUiState.Error(e.message?: "Unknown Error")
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000L),
                AttendanceCountUiState.Loading
            )
}