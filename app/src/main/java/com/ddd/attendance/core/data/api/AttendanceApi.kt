package com.ddd.attendance.core.data.api

import com.ddd.attendance.core.data.ApiResponse
import com.ddd.attendance.core.data.api.model.attendance.AttendanceCountResponse
import com.ddd.attendance.core.data.api.model.attendance.AttendanceResponse
import retrofit2.http.GET

interface AttendanceApi {
    @GET("/api/v1/attendances/count/")
    suspend fun attendanceCount() : ApiResponse<AttendanceCountResponse>

    @GET("/api/v1/attendances/")
    suspend fun attendanceList() : ApiResponse<List<AttendanceResponse>>
}