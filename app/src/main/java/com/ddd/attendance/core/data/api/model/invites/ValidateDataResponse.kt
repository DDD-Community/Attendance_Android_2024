package com.ddd.attendance.core.data.api.model.invites

import kotlinx.serialization.SerialName

data class ValidateDataResponse(
    @SerialName("valid") val valid: Boolean,
    @SerialName("invite_code_id") val inviteCodeId: String,
    @SerialName("invite_type") val inviteType: String,
    @SerialName("expire_time") val expireTime: String,
    @SerialName("one_time_use") val oneTimeUse: Boolean
)
