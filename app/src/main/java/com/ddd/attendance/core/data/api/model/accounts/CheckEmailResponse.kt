package com.ddd.attendance.core.data.api.model.accounts

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class CheckEmailResponse(
    @SerializedName("email_used") val emailUsed: Boolean
)
