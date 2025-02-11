package com.ddd.attendance.core.model.member

import com.ddd.attendance.core.network.model.member.MemberAttendanceResponse

data class MemberAttendance(
    val memberId: String,
    val totalAttendance: Int,
    val lateCount: Int,
    val absentCount: Int,
    val attendanceRecords: List<MemberAttendanceRecord>
) {
    companion object {
        fun from(response: MemberAttendanceResponse): MemberAttendance {
            return MemberAttendance(
                memberId = response.memberId,
                totalAttendance = response.totalAttendance,
                lateCount = response.lateCount,
                absentCount = response.absentCount,
                attendanceRecords = response.attendanceRecords.map {
                    MemberAttendanceRecord(
                        test = it.test,
                        data = it.data
                    )
                }
            )
        }
    }
}

data class MemberAttendanceRecord(
    val test: String,
    val data: String
)