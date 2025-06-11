package com.ddd.attendance.core.data.api

import com.ddd.attendance.core.data.ApiResponse
import com.ddd.attendance.core.data.api.model.profiles.ProfileMeResponse
import com.ddd.attendance.core.data.api.request.profiles.ProfileMeRequest
import retrofit2.http.Body
import retrofit2.http.PATCH

interface ProfilesApi {
    @PATCH("/api/v1/profiles/me/")
    suspend fun profileMe(
        @Body request: ProfileMeRequest
    ): ApiResponse<ProfileMeResponse>
}