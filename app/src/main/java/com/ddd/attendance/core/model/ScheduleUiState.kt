package com.ddd.attendance.core.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
sealed interface ScheduleUiState {

    @Immutable
    data object Empty: ScheduleUiState

    @Immutable
    data object Loading: ScheduleUiState

    @Immutable
    data class Success(val schedules: List<Schedule>) : ScheduleUiState

    @Immutable
    data class Error(val message: String) : ScheduleUiState
}