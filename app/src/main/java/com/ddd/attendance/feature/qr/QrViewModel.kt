package com.ddd.attendance.feature.qr

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.ddd.attendance.feature.qr.model.QrGenerateUiState
import com.ddd.attendance.feature.qr.model.QrScanUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class QrViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _qrGenerateUiState = MutableStateFlow<QrGenerateUiState>(QrGenerateUiState.Loading)
    val qrGenerateUiState: StateFlow<QrGenerateUiState> = _qrGenerateUiState.asStateFlow()

    private val _qrScanUiState = MutableStateFlow<QrScanUiState>(QrScanUiState.Loading)
    val qrScanUiState: StateFlow<QrScanUiState> = _qrScanUiState.asStateFlow()

    private val _isPermissionRequested = MutableStateFlow(false)
    val isPermissionRequested: StateFlow<Boolean> = _isPermissionRequested

    fun setPermissionRequested(value: Boolean) {
        _isPermissionRequested.value = value
    }

    /*fun generateQr(userId: String) {
        viewModelScope.launch {
            _qrGenerateUiState.value = QrGenerateUiState.Loading
            try {
                val data = qrEncodeUseCase(
                    userId = userId
                )
                _qrGenerateUiState.value = QrGenerateUiState.Success(
                    qrString = data.qrString
                )
            } catch (e: Exception) {
                _qrGenerateUiState.value = QrGenerateUiState.Error(e.message ?: "알 수 없는 오류 발생")
            }
        }
    }

    fun scanQr(qrString: String) {
        viewModelScope.launch {
            _qrScanUiState.value = QrScanUiState.Loading
            try {
                val data = qrDecodeUseCase(
                    qrString = qrString
                )
                _qrScanUiState.value = QrScanUiState.Success(
                    userId = data.userId,
                    timeStamp = data.timestamp
                )
            } catch (e: Exception) {
                _qrScanUiState.value = QrScanUiState.Error(e.message ?: "알 수 없는 오류 발생")
            }
        }
    }*/
}