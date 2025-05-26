package com.ddd.attendance.core.model.login

import com.ddd.attendance.core.data.api.model.RegistrationResponse

data class Registration(
    val accessToken: String,
    val refreshToken: String,
    val user: RegistrationUser?
) {
    companion object {
        fun from(response: RegistrationResponse?): Registration {
            return Registration(
                accessToken = response?.accessToken ?: "",
                refreshToken = response?.refreshToken ?: "",
                user = response?.user?.let { RegistrationUser.from(it) }
            )
        }
    }
}