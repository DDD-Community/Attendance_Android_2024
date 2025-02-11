package com.ddd.attendance.core.model.qr

import com.ddd.attendance.core.network.model.qr.QrDecodeResponse
import com.ddd.attendance.core.utils.default

data class QrDecode(
    val userId: String,
    val timestamp: String
) {
    companion object {
        fun from(response: QrDecodeResponse?): QrDecode {
            return QrDecode(
                userId = response?.userId.default(),
                timestamp = response?.timestamp.default()
            )
        }
    }
}