package com.ddd.attendance.core.data.api

import com.ddd.attendance.core.data.ApiResponse
import com.ddd.attendance.core.data.api.model.accounts.CheckEmailResponse
import com.ddd.attendance.core.data.api.model.accounts.RegistrationResponse
import com.ddd.attendance.core.data.api.request.accounts.CheckEmailRequest
import com.ddd.attendance.core.data.api.request.accounts.RegistrationRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountsApi {
    @POST("accounts/registration/")
    suspend fun registration(
        @Body request: RegistrationRequest
    ): RegistrationResponse?

    @POST("accounts/check-email/")
    suspend fun checkEmail(
        @Body request: CheckEmailRequest
    ): ApiResponse<CheckEmailResponse>
}