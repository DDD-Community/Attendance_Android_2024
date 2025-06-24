package com.ddd.attendance.core.model.accounts

import com.ddd.attendance.core.data.api.model.accounts.TokenEmailResponse

data class TokenEmail(
    val id: Int,
    val email: String,
    val accessToken: String,
    val refreshToken: String
) {
    companion object {
        fun from(response: TokenEmailResponse?): TokenEmail {
            return TokenEmail(
                id = response?.id?: 0,
                email = response?.email?: "",
                accessToken = response?.accessToken?: "",
                refreshToken = response?.refreshToken?: ""
            )
        }
    }
}