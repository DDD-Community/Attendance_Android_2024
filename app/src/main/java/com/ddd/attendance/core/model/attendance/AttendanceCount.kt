package com.ddd.attendance.core.model.attendance

import com.ddd.attendance.core.data.api.model.attendance.AttendanceCountResponse

data class AttendanceCount(
    val attendanceCount: Int,
    val presentCount: Int,
    val lateCount: Int,
    val absentCount: Int,
    val exceptionCount: Int,
    val tbdCount: Int
) {
    companion object {
        fun from(response: AttendanceCountResponse?): AttendanceCount {
            return AttendanceCount(
                attendanceCount = response?.attendanceCount?: 0,
                presentCount = response?.presentCount?: 0,
                lateCount = response?.lateCount?: 0,
                absentCount = response?.absentCount?: 0,
                exceptionCount = response?.exceptionCount?: 0,
                tbdCount = response?.tbdCount?: 0
            )
        }
    }
}
