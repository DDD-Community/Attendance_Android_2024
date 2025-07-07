package com.ddd.attendance.feature.login.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.ddd.attendance.core.model.accounts.CheckEmail

@Stable
sealed interface CheckEmailUiState {
    @Immutable
    data object Empty : CheckEmailUiState

    @Immutable
    data object Loading : CheckEmailUiState

    @Immutable
    data class Success(val data: CheckEmail) : CheckEmailUiState

    @Immutable
    data class Error(val message: String) : CheckEmailUiState
}