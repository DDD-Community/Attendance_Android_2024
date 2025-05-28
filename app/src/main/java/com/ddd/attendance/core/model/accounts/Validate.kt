package com.ddd.attendance.core.model.accounts

import com.ddd.attendance.core.data.api.model.invites.ValidateDataResponse

class Validate(
    val valid: Boolean,
    val inviteCodeId: String,
    val inviteType: String,
    val expireTime: String,
    val oneTimeUse: Boolean
) {
    companion object {
        fun from(response: ValidateDataResponse?): Validate {
            return Validate(
                valid = response?.valid?: false,
                inviteCodeId = response?.inviteCodeId?: "",
                inviteType = response?.inviteType?: "",
                expireTime = response?.expireTime?: "",
                oneTimeUse = response?.oneTimeUse?: false
            )
        }
    }
}