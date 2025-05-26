package com.ddd.attendance.feature.member

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _memberId = MutableStateFlow(savedStateHandle["member"] ?: "")

    /*val attendanceUiState: StateFlow<MemberAttendanceUiState> = _memberId
        .filter { it.isNotEmpty() }
        .flatMapLatest { id ->
            memberAttendanceUseCase(id)
                .map { data ->
                    if (data.attendanceRecords.isNotEmpty()) {
                        MemberAttendanceUiState.Success(data.attendanceRecords)
                    } else {
                        MemberAttendanceUiState.Empty
                    }
                }
                .catch { throwable ->
                    emit(MemberAttendanceUiState.Error(throwable.message.default()))
                }
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000L),
            MemberAttendanceUiState.Loading
        )*/
}