package com.ddd.attendance.core.network

import com.ddd.attendance.core.model.login.Registration
import kotlinx.coroutines.flow.Flow

interface RegistrationRepository {
    fun registration(
        owner: String,
        email: String,
        password1: String,
        password2: String
    ): Flow<Registration>
}