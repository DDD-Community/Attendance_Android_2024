package com.ddd.attendance.feature.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.attendance.feature.main.screen.ScreenName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _startDestination = MutableStateFlow("")
    val startDestination: StateFlow<String> = _startDestination

    init {
        viewModelScope.launch {
            _startDestination.value = when (getDummyLoginMethodAsync()) {
                "member" -> ScreenName.MEMBER.name
                "admin" -> ScreenName.ADMIN.name
                else -> ScreenName.NONE.name
            }
        }
    }

    private fun getDummyLoginMethodAsync(): String {
        return "member"
    }
}