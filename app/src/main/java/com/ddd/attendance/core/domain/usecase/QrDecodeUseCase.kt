package com.ddd.attendance.core.domain.usecase

import com.ddd.attendance.core.domain.repository.QrRepository
import com.ddd.attendance.core.model.qr.QrDecode
import javax.inject.Inject

class QrDecodeUseCase @Inject constructor (
    private val repository: QrRepository
) {
    suspend operator fun invoke(qrString: String): QrDecode {
        return repository.qrDecode(qrString)
    }
}