package com.ddd.attendance.core.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationResponse(
    @SerialName("access") val accessToken: String,
    @SerialName("refresh") val refreshToken: String,
    @SerialName("user") val user: RegistrationUserResponse?
)
