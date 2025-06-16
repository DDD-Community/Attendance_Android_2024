package com.ddd.attendance.feature.splash

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.attendance.core.domain.usecase.invites.GetInviteTypeUseCase
import com.ddd.attendance.feature.main.screen.ScreenName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val inviteTypeUseCase: GetInviteTypeUseCase,
) : ViewModel() {
    private val _screenType: MutableStateFlow<String> = MutableStateFlow("")
    val screenType: StateFlow<String> = _screenType.asStateFlow()

    init {
        viewModelScope.launch {
            inviteTypeUseCase().collect {
                /*val loginType = when (it) {
                    "member" -> ScreenName.MEMBER.name
                    "moderator" -> ScreenName.ADMIN.name
                    else -> ScreenName.NONE.name
                }
                _screenType.value = loginType*/
            }
        }
    }
}