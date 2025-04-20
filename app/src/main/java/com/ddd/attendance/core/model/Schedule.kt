package com.ddd.attendance.core.model

import com.ddd.attendance.core.network.model.qr.ScheduleResponse

data class Schedule(
    val month: String,
    val day: String,
    val title: String,
    val content: String
) {
    companion object {
        fun from(response: ScheduleResponse): Schedule {
            return Schedule(
                month = "12월",
                day = "22",
                title = response.title ?: "",
                content = response.description ?: "",
            )
        }
    }
}