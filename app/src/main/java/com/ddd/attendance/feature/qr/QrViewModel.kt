package com.ddd.attendance.feature.qr

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddd.attendance.core.domain.usecase.QrDecodeUseCase
import com.ddd.attendance.core.domain.usecase.QrEncodeUseCase
import com.ddd.attendance.core.model.qr.QrGenerateUiState
import com.ddd.attendance.core.model.qr.QrScanUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val qrEncodeUseCase: QrEncodeUseCase,
    private val qrDecodeUseCase: QrDecodeUseCase
) : ViewModel() {
    private val _qrGenerateUiState = MutableStateFlow<QrGenerateUiState>(QrGenerateUiState.Loading)
    val qrGenerateUiState: StateFlow<QrGenerateUiState> = _qrGenerateUiState.asStateFlow()

    private val _qrScanUiState = MutableStateFlow<QrScanUiState>(QrScanUiState.Loading)
    val qrScanUiState: StateFlow<QrScanUiState> = _qrScanUiState.asStateFlow()

    fun generateQr(userId: String) {
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
    }
}