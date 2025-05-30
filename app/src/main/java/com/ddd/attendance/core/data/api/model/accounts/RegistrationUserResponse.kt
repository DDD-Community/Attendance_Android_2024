package com.ddd.attendance.core.data.api.model.accounts

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RegistrationUserResponse(
    val pk: String,
    val email: String,
    @SerializedName("username") val name: String,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String
)