package com.ddd.attendance.core.model.attendance

import com.ddd.attendance.core.data.api.model.attendance.AttendanceResponse

data class Attendance(
    val profileSummary: String,
    val scheduleSummary: String,
    val updatedAt: String,
    val id: String,
    val status: String,
    val method: String,
    val note: String
) {
    companion object {
        fun from(response: List<AttendanceResponse>?): List<Attendance> {
            return response?.map {
                Attendance(
                    profileSummary = it.profileSummary,
                    scheduleSummary = it.scheduleSummary,
                    updatedAt = it.updatedAt,
                    id = it.id,
                    status = it.status,
                    method = it.method,
                    note = it.note
                )
            } ?: emptyList()
        }
    }
}