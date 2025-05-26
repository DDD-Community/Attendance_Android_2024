package com.ddd.attendance.core.model.login

import com.ddd.attendance.core.data.api.model.RegistrationUserResponse

data class RegistrationUser(
    val id: String,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String
) {
    companion object {
        fun from(response: RegistrationUserResponse?): RegistrationUser {
            return RegistrationUser(
                id = response?.pk ?: "",
                username = response?.name ?: "",
                email = response?.email ?: "",
                firstName = response?.firstName ?: "",
                lastName = response?.lastName ?: ""
            )
        }
    }
}