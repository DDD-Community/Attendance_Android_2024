package com.ddd.attendance.core.model.qr

import com.ddd.attendance.core.network.model.qr.QrEncodeResponse
import com.ddd.attendance.core.utils.default

data class QrEncode (
    val qrString: String
) {
    companion object {
        fun from(response: QrEncodeResponse?): QrEncode {
            return QrEncode(
                qrString = response?.qrString.default()
            )
        }
    }
}