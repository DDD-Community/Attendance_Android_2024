package com.ddd.attendance.core.network.service

import com.ddd.attendance.core.network.model.ApiResponse
import com.ddd.attendance.core.network.model.member.MemberAttendanceResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface MemberApiService {
    /**
     * ### 회원의 출석 정보 조회
     * 특정 회원의 출석 정보를 조회
     */
    @GET("/members/{member_id}/attendance")
    suspend fun getMemberAttendance(
        @Path("member_id") memberId: String
    ): ApiResponse<MemberAttendanceResponse>
}