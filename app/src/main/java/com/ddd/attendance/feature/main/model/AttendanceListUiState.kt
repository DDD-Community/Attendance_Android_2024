package com.ddd.attendance.feature.main.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.ddd.attendance.core.model.attendance.Attendance

@Stable
sealed interface AttendanceListUiState {
    @Immutable
    data object Empty : AttendanceListUiState

    @Immutable
    data object Loading : AttendanceListUiState

    @Immutable
    data class Success(val data: List<Attendance>) : AttendanceListUiState

    @Immutable
    data class Error(val message: String) : AttendanceListUiState
}