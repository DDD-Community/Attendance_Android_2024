package com.ddd.attendance.core.data.api.model.accounts

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationUserResponse(
    @SerialName("pk") val pk: String,
    @SerialName("username") val name: String,
    @SerialName("email") val email: String,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String
)