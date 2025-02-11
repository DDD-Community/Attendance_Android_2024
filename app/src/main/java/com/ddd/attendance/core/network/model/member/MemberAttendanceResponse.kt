package com.ddd.attendance.core.network.model.member

import kotlinx.serialization.SerialName

data class MemberAttendanceResponse(
    @SerialName("member_id") val memberId: String,
    @SerialName("total_attendance") val totalAttendance: Int,
    @SerialName("late_count") val lateCount: Int,
    @SerialName("absent_count") val absentCount: Int,
    @SerialName("attendance_records") val attendanceRecords: List<MemberAttendanceRecordResponse>
)
data class MemberAttendanceRecordResponse(
    val test: String,
    val data: String
) //서버에서 아직 정의되지 않음