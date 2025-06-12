package com.ddd.attendance.core.data.api.model.attendance

import com.google.gson.annotations.SerializedName

class AttendanceResponse(
    @SerializedName("profile_summary") val profileSummary: String,
    @SerializedName("schedule_summary") val scheduleSummary: String,
    @SerializedName("updated_at") val updatedAt: String,
    val id: String,
    val status: String,
    val method: String,
    val note: String
)