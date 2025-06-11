package com.ddd.attendance.core.data.api.model.profiles

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileMeResponse(
    @SerializedName("user_id") val userId: Int,
    @SerializedName("invite_code_id") val inviteCodeId: String,
    @SerializedName("is_staff") val isStaff: Boolean,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    val id: String,
    val name: String,
    val role: String,
    val team: String,
    val responsibility: String,
    val cohort: String
)
