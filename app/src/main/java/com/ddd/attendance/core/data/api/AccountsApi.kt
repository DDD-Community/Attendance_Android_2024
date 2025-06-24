package com.ddd.attendance.core.data.api

import com.ddd.attendance.core.data.ApiResponse
import com.ddd.attendance.core.data.api.model.accounts.CheckEmailResponse
import com.ddd.attendance.core.data.api.model.accounts.RegistrationResponse
import com.ddd.attendance.core.data.api.model.accounts.TokenEmailResponse
import com.ddd.attendance.core.data.api.request.accounts.CheckEmailRequest
import com.ddd.attendance.core.data.api.request.accounts.RegistrationRequest
import com.ddd.attendance.core.data.api.request.accounts.TokenEmailRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface AccountsApi {
    /** 회원 체크 API */
    @POST("accounts/check-email/")
    suspend fun checkEmail(
        @Body request: CheckEmailRequest
    ): ApiResponse<CheckEmailResponse>

    /** 로그인 API */
    @POST("/accounts/token/email/")
    suspend fun loginEmail(
        @Body request: TokenEmailRequest
    ): ApiResponse<TokenEmailResponse>

    /** 회원 가입 API */
    @POST("accounts/registration/")
    suspend fun registration(
        @Body request: RegistrationRequest
    ): RegistrationResponse?
}