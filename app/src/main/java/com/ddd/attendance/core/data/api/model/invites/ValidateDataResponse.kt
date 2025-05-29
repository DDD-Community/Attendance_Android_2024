package com.ddd.attendance.core.data.api.model.invites

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


@Serializable
data class ValidateDataResponse(
    val valid: Boolean,
    @SerializedName("invite_code_id") val inviteCodeId: String,
    @SerializedName("invite_type") val inviteType: String,
    @SerializedName("expire_time") val expireTime: String,
    @SerializedName("one_time_use") val oneTimeUse: Boolean
)
