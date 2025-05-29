package com.ddd.attendance.core.data.api.model.accounts

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationResponse(
    @SerializedName("access") val accessToken: String,
    @SerializedName("refresh") val refreshToken: String,
    val user: RegistrationUserResponse?
)
