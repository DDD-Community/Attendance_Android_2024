package com.ddd.attendance.core.data.api.model.accounts

import kotlinx.serialization.SerialName

data class RegistrationResponse(
    @SerialName("access") val accessToken: String,
    @SerialName("refresh") val refreshToken: String,
    @SerialName("user") val user: RegistrationUserResponse?
)
