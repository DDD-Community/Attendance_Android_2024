package com.ddd.attendance.core.data.repository

import com.ddd.attendance.core.domain.repository.QrRepository
import com.ddd.attendance.core.model.qr.QrDecode
import com.ddd.attendance.core.model.qr.QrEncode
import com.ddd.attendance.core.network.model.qr.request.QrDecodeRequest
import com.ddd.attendance.core.network.model.qr.request.QrEncodeRequest
import com.ddd.attendance.core.network.service.QrApiService
import javax.inject.Inject

class DefaultQrRepository @Inject constructor (
    private val api: QrApiService
): QrRepository {
    override suspend fun qrEncode(userId: String): QrEncode {
        return QrEncode.from(
            api.qrEncode(
                request = QrEncodeRequest(
                    user_id = userId
                )
            ).data
        )
    }

    override suspend fun qrDecode(qrString: String): QrDecode {
        return QrDecode.from(
            api.qrDecode(
                request = QrDecodeRequest(
                    qr_string = qrString
                )
            ).data
        )
    }
}