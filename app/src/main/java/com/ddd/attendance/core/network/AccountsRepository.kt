package com.ddd.attendance.core.network

import com.ddd.attendance.core.model.accounts.CheckEmail
import com.ddd.attendance.core.model.accounts.Registration
import com.ddd.attendance.core.model.accounts.TokenEmail
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {
    fun checkEmail(email: String): Flow<CheckEmail>
    fun loginEmail(email: String): Flow<TokenEmail>
    fun registration(owner: String, email: String, password1: String, password2: String): Flow<Registration>

    fun getEmail(): Flow<String>
    fun getAccessToken(): Flow<String>
}