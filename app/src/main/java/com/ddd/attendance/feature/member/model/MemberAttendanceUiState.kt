package com.ddd.attendance.feature.member.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.ddd.attendance.core.model.member.MemberAttendanceRecord

@Stable
sealed interface MemberAttendanceUiState {

    @Immutable
    data object Empty : MemberAttendanceUiState

    @Immutable
    data object Loading : MemberAttendanceUiState

    @Immutable
    data class Success(val attendanceRecords: List<MemberAttendanceRecord>) : MemberAttendanceUiState

    @Immutable
    data class Error(val message: String) : MemberAttendanceUiState
}
