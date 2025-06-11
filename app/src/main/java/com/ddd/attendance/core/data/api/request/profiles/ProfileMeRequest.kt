package com.ddd.attendance.core.data.api.request.profiles

import com.google.gson.annotations.SerializedName

data class ProfileMeRequest(
    @SerializedName("invite_code_id") val inviteCodeId: String,
    val name: String,
    val role: String,
    val team: String,
    val responsibility: String,
    val cohort: String
)