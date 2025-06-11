package com.ddd.attendance.feature.login.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.ddd.attendance.core.model.invites.Validate

@Stable
sealed interface ValidateUiState {
    @Immutable
    data object Empty : ValidateUiState

    @Immutable
    data object Loading : ValidateUiState

    @Immutable
    data class Success(val data: Validate) : ValidateUiState

    @Immutable
    data class Error(val message: String) : ValidateUiState
}