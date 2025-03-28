package com.ddd.attendance.feature.member

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.attendance.core.domain.usecase.GetMemberAttendanceUseCase
import com.ddd.attendance.core.domain.usecase.QrDecodeUseCase
import com.ddd.attendance.core.domain.usecase.QrEncodeUseCase
import com.ddd.attendance.core.model.member.MemberAttendanceUiState
import com.ddd.attendance.core.model.qr.QrGenerateUiState
import com.ddd.attendance.core.model.qr.QrScanUiState
import com.ddd.attendance.core.utils.default
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemberViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val memberAttendanceUseCase: GetMemberAttendanceUseCase,
) : ViewModel() {

    private val _memberId = MutableStateFlow(savedStateHandle["member"] ?: "")

    val attendanceUiState: StateFlow<MemberAttendanceUiState> = _memberId
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
        )
}