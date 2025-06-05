package com.ddd.attendance.feature.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.attendance.core.domain.usecase.attendance.AttendanceCountUseCase
import com.ddd.attendance.core.domain.usecase.invites.GetInviteTypeUseCase
import com.ddd.attendance.feature.main.model.AttendanceCountUiState
import com.ddd.attendance.feature.main.screen.ScreenName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val inviteTypeUseCase: GetInviteTypeUseCase,
    private val attendanceCountUseCase: AttendanceCountUseCase
) : ViewModel() {
    private val _startDestination = MutableStateFlow("")
    val startDestination: StateFlow<String> = _startDestination

    private val _attendanceCountUiState =  MutableStateFlow<AttendanceCountUiState>(AttendanceCountUiState.Empty)
    val attendanceCountUiState =  _attendanceCountUiState.asStateFlow()

    init {
        viewModelScope.launch {
            inviteTypeUseCase().collect {
                val loginType = when (it) {
                    "member" -> ScreenName.MEMBER.name
                    "moderator" -> ScreenName.ADMIN.name
                    else -> ScreenName.NONE.name
                }
                _startDestination.value = loginType
            }
        }
    }
}