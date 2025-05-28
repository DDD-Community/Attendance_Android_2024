package com.ddd.attendance.core.data.api

import com.ddd.attendance.core.data.ApiResponse
import com.ddd.attendance.core.data.api.model.invites.ValidateDataResponse
import com.ddd.attendance.core.data.api.request.validate.ValidateRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface InvitesApi {
    @POST("/api/v1/invites/validate/")
    suspend fun validate(
        @Body request: ValidateRequest
    ): ApiResponse<ValidateDataResponse>
}