package com.ddd.attendance.feature.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.attendance.core.domain.usecase.profiles.GetProfileMeUseCase
import com.ddd.attendance.feature.login.model.ProfileMeUiState
import com.ddd.attendance.feature.main.screen.ScreenName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getProfileMeUseCase: GetProfileMeUseCase
) : ViewModel() {
    private val _startDestination = MutableStateFlow("")
    val startDestination: StateFlow<String> = _startDestination.asStateFlow()

    private val _userName = MutableStateFlow("")
    val userName: StateFlow<String> = _userName.asStateFlow()

    private val _profileMeUiState = MutableStateFlow<ProfileMeUiState>(ProfileMeUiState.Empty)
    val profileMeUiState: StateFlow<ProfileMeUiState> = _profileMeUiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                _profileMeUiState.value = ProfileMeUiState.Loading

                getProfileMeUseCase()
                    .filterNotNull()
                    .map { ProfileMeUiState.Success(it) as ProfileMeUiState }
                    .catch { emit(ProfileMeUiState.Error(it.message ?: "Unknown Error")) }
                    .collect { state ->
                        _profileMeUiState.value = state

                        if (state is ProfileMeUiState.Success) {
                            _startDestination.value = if (state.data.isStaff) ScreenName.ADMIN.name else ScreenName.MEMBER.name
                            _userName.value = state.data.name
                        }
                    }
            } catch (e: Exception) {
                _profileMeUiState.value = ProfileMeUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}