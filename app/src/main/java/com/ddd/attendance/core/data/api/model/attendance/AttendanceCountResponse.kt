package com.ddd.attendance.core.data.api.model.attendance

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
class AttendanceCountResponse(
    @SerializedName("attendance_count") val attendanceCount: Int,
    @SerializedName("present_count") val presentCount: Int,
    @SerializedName("late_count") val lateCount: Int,
    @SerializedName("absent_count") val absentCount: Int,
    @SerializedName("exception_count") val exceptionCount: Int,
    @SerializedName("tbd_count") val tbdCount: Int
)