package com.ddd.attendance.feature.admin

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * 운영진 QR 스캔
 * */
@HiltViewModel
class AdminViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _menu: MutableStateFlow<String> = MutableStateFlow("출석")
    val menu: StateFlow<String> = _menu

    fun setMenu(value: String) {
        _menu.value = value
    }
}