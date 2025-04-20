package com.ddd.attendance.core.network.model.qr

import com.google.gson.annotations.SerializedName

data class ScheduleResponse(
    @SerializedName("id")
    val id: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("description")
    val description: String? = null,

    @SerializedName("start_time")
    val startTime: String? = null,

    @SerializedName("end_time")
    val endTime: String? = null,
)
