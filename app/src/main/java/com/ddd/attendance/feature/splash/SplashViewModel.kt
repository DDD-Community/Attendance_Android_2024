package com.ddd.attendance.feature.splash

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.attendance.core.domain.usecase.accounts.GetAccessTokenUseCase
import com.ddd.attendance.core.domain.usecase.accounts.GetEmailUseCase
import com.ddd.attendance.core.domain.usecase.invites.GetInviteTypeUseCase
import com.ddd.attendance.feature.main.screen.ScreenName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getEmailUseCase: GetEmailUseCase,
    private val getAccessTokenUseCase: GetAccessTokenUseCase
) : ViewModel() {
    private val _screenType: MutableStateFlow<String> = MutableStateFlow("")
    val screenType: StateFlow<String> = _screenType.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                getEmailUseCase(),
                getAccessTokenUseCase()
            ) { email, token ->
                email to token
            }.collect { (email, token) ->
                Log.d("테스트테스트테스트", "$email, $token")
            }
        }
    }
}