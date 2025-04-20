package com.ddd.attendance.core.network.service

import com.ddd.attendance.core.network.model.ApiResponse
import com.ddd.attendance.core.network.model.qr.ScheduleResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ScheduleApiService {
    /**
     * 동아리 일정 정보 조회
     */
    @GET("/api/v1/schedules/")
    suspend fun getSchedules(
        @Header("Authorization") token: String,
    ) : ApiResponse<List<ScheduleResponse>>
}