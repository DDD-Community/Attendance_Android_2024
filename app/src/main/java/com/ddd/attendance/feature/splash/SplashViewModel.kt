package com.ddd.attendance.feature.splash

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.attendance.core.domain.usecase.accounts.GetAccessTokenUseCase
import com.ddd.attendance.core.domain.usecase.accounts.GetEmailUseCase
import com.ddd.attendance.core.domain.usecase.accounts.LoginEmailUseCase
import com.ddd.attendance.core.domain.usecase.profiles.GetProfileMeUseCase
import com.ddd.attendance.feature.login.model.LoginEmailUiState
import com.ddd.attendance.feature.login.model.ProfileMeUiState
import com.ddd.attendance.feature.main.screen.ScreenName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getEmailUseCase: GetEmailUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase,
    private val loginEmailUseCase: LoginEmailUseCase,
    private val getProfileMeUseCase: GetProfileMeUseCase
) : ViewModel() {
    private val _screenType: MutableStateFlow<String> = MutableStateFlow("")
    val screenType: StateFlow<String> = _screenType.asStateFlow()

    private val _loginEmailUiState = MutableStateFlow<LoginEmailUiState>(LoginEmailUiState.Empty)
    val loginEmailUiState: StateFlow<LoginEmailUiState> = _loginEmailUiState.asStateFlow()

    private val _profileMeUiState = MutableStateFlow<ProfileMeUiState>(ProfileMeUiState.Empty)
    val profileMeUiState: StateFlow<ProfileMeUiState> = _profileMeUiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                getEmailUseCase(),
                getAccessTokenUseCase()
            ) { email, token ->
                email to token
            }.collect { (email, token) ->
                if (email.isEmpty() || token.isEmpty()) {
                    // 이메일이나 토큰이 없으면 신규 → 로그인 화면
                    _screenType.value = ScreenName.LOGIN.name
                } else {
                    // 이메일, 토큰 있으면 기존 유저 → 메인 화면
                    loginEmail(email)
                }
            }
        }
    }

    private fun loginEmail(email: String) {
        viewModelScope.launch {
            try {
                _loginEmailUiState.value = LoginEmailUiState.Loading

                loginEmailUseCase(email = email)
                    .filterNotNull()
                    .map { LoginEmailUiState.Success(it) as LoginEmailUiState }
                    .catch { emit(LoginEmailUiState.Error(it.message ?: "Unknown Error")) }
                    .collect { state ->
                        _loginEmailUiState.value = state

                        if (state is LoginEmailUiState.Success) profileMe()
                    }
            } catch (e: Exception) {
                _loginEmailUiState.value = LoginEmailUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    private fun profileMe() {
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
                            if (state.data.isStaff) {
                                _screenType.value = ScreenName.ADMIN.name
                            } else {
                                _screenType.value = ScreenName.MEMBER.name
                            }
                        }
                    }
            } catch (e: Exception) {
                _profileMeUiState.value = ProfileMeUiState.Error(e.message ?: "Unknown error")
            }
        }
    }
}