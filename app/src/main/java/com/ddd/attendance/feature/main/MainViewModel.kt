package com.ddd.attendance.feature.main

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.attendance.core.domain.usecase.invites.GetInviteTypeUseCase
import com.ddd.attendance.feature.main.screen.ScreenName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val inviteTypeUseCase: GetInviteTypeUseCase,
) : ViewModel() {
    private val _startDestination = MutableStateFlow("")
    val startDestination: StateFlow<String> = _startDestination

    init {
        viewModelScope.launch {
            inviteTypeUseCase().collect {
                _startDestination.value = when (it) {
                    "member" -> ScreenName.MEMBER.name
                    "admin" -> ScreenName.ADMIN.name
                    else -> ScreenName.NONE.name
                }
            }
        }
    }

    /*private fun getDummyLoginMethodAsync(): String {
        return "member"
    }*/
}