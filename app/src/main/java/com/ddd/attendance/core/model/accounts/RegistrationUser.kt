package com.ddd.attendance.core.model.accounts

import com.ddd.attendance.core.data.api.model.accounts.RegistrationUserResponse

data class RegistrationUser(
    val id: Int,
    val username: String,
    val email: String,
    val firstName: String,
    val lastName: String
) {
    companion object {
        fun from(response: RegistrationUserResponse?): RegistrationUser {
            return RegistrationUser(
                id = response?.pk ?: - 1,
                username = response?.name ?: "",
                email = response?.email ?: "",
                firstName = response?.firstName ?: "",
                lastName = response?.lastName ?: ""
            )
        }
    }
}