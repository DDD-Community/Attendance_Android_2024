package com.ddd.attendance.core.data.api

import com.ddd.attendance.core.data.api.model.RegistrationResponse
import com.ddd.attendance.core.data.api.request.RegistrationRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountsApi {
    @POST("accounts/registration/")
    suspend fun registration(
        @Body request: RegistrationRequest
    ): RegistrationResponse?
}