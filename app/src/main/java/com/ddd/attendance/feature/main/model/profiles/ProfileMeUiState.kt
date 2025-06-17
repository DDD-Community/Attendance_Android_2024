package com.ddd.attendance.feature.main.model.profiles

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.ddd.attendance.core.model.invites.ProfileMe

@Stable
sealed interface ProfileMeUiState {
    @Immutable
    data object Empty : ProfileMeUiState

    @Immutable
    data object Loading : ProfileMeUiState

    @Immutable
    data class Success(val data: ProfileMe) : ProfileMeUiState

    @Immutable
    data class Error(val message: String) : ProfileMeUiState
}