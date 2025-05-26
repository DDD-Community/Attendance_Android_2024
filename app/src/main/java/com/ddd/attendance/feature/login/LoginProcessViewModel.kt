package com.ddd.attendance.feature.login

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.attendance.core.domain.usecase.RegistrationUseCase
import com.ddd.attendance.core.model.login.GoogleLogin
import com.ddd.attendance.feature.login.model.RegistrationUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class LoginProcessViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val registrationUseCase: RegistrationUseCase
) : ViewModel() {
    private val _userInfo = MutableStateFlow<GoogleLogin?>(null)

    fun setUserInfo(user: GoogleLogin) {
        _userInfo.value = user
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val registrationUiState: StateFlow<RegistrationUiState> =
        _userInfo
            .filterNotNull()
            .filter { it.uid.isNotEmpty() } // 인증 성공된 사용자만
            // flatMapLatest: userInfo가 바뀔 때마다 registrationUseCase를 새로 실행
            .flatMapLatest { user ->
                registrationUseCase(
                    owner = user.displayName,
                    email = user.email,
                    password1 = user.uid,
                    password2 = user.uid
                )
                    .map {
                        RegistrationUiState.Success(it)
                    }
            }
            .catch {
                RegistrationUiState.Error("${it.message}")
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = RegistrationUiState.Loading
            )

    fun onClickGoogleLogin() {

    }

    fun onClickSignup() {

    }

    fun onClickNextFromUserName() {

    }

    fun onClickNextFromUserJob() {

    }

    fun onClickNextFromUserAffiliation() {

    }
}