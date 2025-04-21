package com.ddd.attendance.feature.mypage

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _credit: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val credit: StateFlow<Boolean> = _credit

    fun setCredit(value: Boolean) {
        _credit.value = value
    }
}