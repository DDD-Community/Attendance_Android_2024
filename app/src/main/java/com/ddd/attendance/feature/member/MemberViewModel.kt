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
    private val qrEncodeUseCase: QrEncodeUseCase,
    private val qrDecodeUseCase: QrDecodeUseCase
) : ViewModel() {

    private val _isPermissionRequested = MutableStateFlow(false)
    val isPermissionRequested: StateFlow<Boolean> = _isPermissionRequested

    private val _qrGenerateUiState = MutableStateFlow<QrGenerateUiState>(QrGenerateUiState.Loading)
    val qrGenerateUiState: StateFlow<QrGenerateUiState> = _qrGenerateUiState.asStateFlow()

    private val _qrScanUiState = MutableStateFlow<QrScanUiState>(QrScanUiState.Loading)
    val qrScanUiState: StateFlow<QrScanUiState> = _qrScanUiState.asStateFlow()

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

    fun permissionRequested(value: Boolean) {
        _isPermissionRequested.value = value
    }

    fun updateMemberId(newId: String) {
        _memberId.value = newId
        _qrGenerateUiState.value = QrGenerateUiState.Loading // QR 상태 초기화
    }

    fun generateQr(userId: String) {
        viewModelScope.launch {
            _qrGenerateUiState.value = QrGenerateUiState.Loading
            try {
                val data = qrEncodeUseCase(userId = userId)
                _qrGenerateUiState.value = QrGenerateUiState.Success(qrString = data.qrString)
            } catch (e: Exception) {
                _qrGenerateUiState.value = QrGenerateUiState.Error(e.message ?: "알 수 없는 오류 발생")
            }
        }
    }

    fun scanQr(qrString: String) {
        viewModelScope.launch {
            _qrScanUiState.value = QrScanUiState.Loading
            try {
                val data = qrDecodeUseCase(qrString = qrString)
                _qrScanUiState.value = QrScanUiState.Success(userId = data.userId, timeStamp = data.timestamp)
            } catch (e: Exception) {
                _qrScanUiState.value = QrScanUiState.Error(e.message ?: "알 수 없는 오류 발생")
            }
        }
    }
}