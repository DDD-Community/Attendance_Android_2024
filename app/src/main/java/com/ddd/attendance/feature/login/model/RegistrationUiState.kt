package com.ddd.attendance.feature.login.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.ddd.attendance.core.model.accounts.Registration

@Stable
sealed interface RegistrationUiState {
    @Immutable
    data object Empty : RegistrationUiState

    @Immutable
    data object Loading : RegistrationUiState

    @Immutable
    data class Success(val data: Registration) : RegistrationUiState

    @Immutable
    data class Error(val message: String) : RegistrationUiState
}