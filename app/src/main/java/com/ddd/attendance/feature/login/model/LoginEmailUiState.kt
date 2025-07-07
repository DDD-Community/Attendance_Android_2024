package com.ddd.attendance.feature.login.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.ddd.attendance.core.model.accounts.TokenEmail

@Stable
sealed interface LoginEmailUiState {
    @Immutable
    data object Empty : LoginEmailUiState

    @Immutable
    data object Loading : LoginEmailUiState

    @Immutable
    data class Success(val data: TokenEmail) : LoginEmailUiState

    @Immutable
    data class Error(val message: String) : LoginEmailUiState
}