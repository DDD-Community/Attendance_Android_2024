package com.ddd.attendance.feature.main.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.ddd.attendance.core.model.attendance.AttendanceCount

@Stable
sealed interface AttendanceCountUiState {
    @Immutable
    data object Empty : AttendanceCountUiState

    @Immutable
    data object Loading : AttendanceCountUiState

    @Immutable
    data class Success(val data: AttendanceCount) : AttendanceCountUiState

    @Immutable
    data class Error(val message: String) : AttendanceCountUiState
}