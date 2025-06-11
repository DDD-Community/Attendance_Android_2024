package com.ddd.attendance.core.data.api.request.invites

import com.google.gson.annotations.SerializedName

data class ValidateRequest(
    @SerializedName("invite_code") val inviteCode: String
)