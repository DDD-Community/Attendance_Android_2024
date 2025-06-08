package com.ddd.attendance.feature.login

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.attendance.core.domain.usecase.accounts.RegistrationUseCase
import com.ddd.attendance.core.domain.usecase.invites.ValidateUseCase
import com.ddd.attendance.core.model.accounts.UserInfo
import com.ddd.attendance.core.model.accounts.google.GoogleLogin
import com.ddd.attendance.feature.login.model.RegistrationUiState
import com.ddd.attendance.feature.login.model.ValidateUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginProcessViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val validateUseCase: ValidateUseCase,
    private val registrationUseCase: RegistrationUseCase,
) : ViewModel() {
    private val _userInfo = MutableStateFlow(UserInfo())
    val userInfo: StateFlow<UserInfo> = _userInfo.asStateFlow()

    private val _validateUiState = MutableStateFlow<ValidateUiState>(ValidateUiState.Empty)
    val validateUiState: StateFlow<ValidateUiState> = _validateUiState

    private val _registrationUiState = MutableStateFlow<RegistrationUiState>(RegistrationUiState.Empty)
    val registrationUiState: StateFlow<RegistrationUiState> = _registrationUiState

    fun setUpdateUser(googleLogin: GoogleLogin) {
        _userInfo.value = _userInfo.value.copy(
            email = googleLogin.email,
            name = googleLogin.name,
            uid = googleLogin.uid
        )
        Log.d("구글 로그인 결과", "name: ${googleLogin.name}, email: ${googleLogin.email}, uid: ${googleLogin.uid}")
    }

    private fun updateUserInfo(update: UserInfo.() -> UserInfo, logTag: String) {
        _userInfo.value = _userInfo.value.update()
        Log.d(logTag, "${_userInfo.value}")
    }

    private fun setUpdateUserInviteType(value: String) = updateUserInfo(update = { copy(inviteType = value) }, "유저 초대 코드 압데이트")

    fun setUpdateUserName(value: String) = updateUserInfo(update = { copy(name = value) }, "유저 이름 업데이트")

    fun setUpdateUserJobRole(value: String) = updateUserInfo(update = { copy(jobRole = value) }, "유저 직무 업데이트")

    fun setUpdateUserAffiliation(value: String) = updateUserInfo(update = { copy(affiliation = value) }, "유저 소속 업데이트")

    fun inviteValidation(value: String) {
        viewModelScope.launch {
            _validateUiState.value = ValidateUiState.Loading

            try {
                validateUseCase(inviteCode = value)
                    .filterNotNull()
                    .collect { result ->
                        _validateUiState.value = ValidateUiState.Success(result)
                    }
            } catch (e: Exception) {
                _validateUiState.value = ValidateUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun registration() {
        viewModelScope.launch {
            _registrationUiState.value = RegistrationUiState.Loading
            try {
                val userInfo = _userInfo.value
                registrationUseCase(
                    owner = userInfo.name,
                    email = userInfo.email,
                    password1 = userInfo.uid,
                    password2 = userInfo.uid
                )
                    .filterNotNull()
                    .collect { result ->
                        _registrationUiState.value = RegistrationUiState.Success(result)
                    }
            } catch (e: Exception) {
                _registrationUiState.value = RegistrationUiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun resetValidateState() {
        _validateUiState.value = ValidateUiState.Empty
    }

    /*@OptIn(ExperimentalCoroutinesApi::class)
    val registrationUiState: StateFlow<RegistrationUiState> =
        _googleInfo
            .filterNotNull()
            .filter { it.uid.isNotEmpty() } // 인증 성공된 사용자만
            // flatMapLatest: userInfo가 바뀔 때마다 registrationUseCase를 새로 실행
            .flatMapLatest { user ->
                registrationUseCase(
                    owner = user.name,
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
            )*/
}