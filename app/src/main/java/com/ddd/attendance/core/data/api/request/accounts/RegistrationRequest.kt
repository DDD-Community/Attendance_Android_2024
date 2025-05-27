package com.ddd.attendance.core.data.api.request.accounts

data class RegistrationRequest(
    val owner: String,
    val email: String,
    val password1: String,
    val password2: String
)