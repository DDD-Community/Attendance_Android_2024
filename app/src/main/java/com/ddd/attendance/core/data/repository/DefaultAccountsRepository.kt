package com.ddd.attendance.core.data.repository

import com.ddd.attendance.core.data.api.AccountsApi
import com.ddd.attendance.core.data.api.model.accounts.RegistrationResponse
import com.ddd.attendance.core.data.api.request.accounts.RegistrationRequest
import com.ddd.attendance.core.model.accounts.Registration
import com.ddd.attendance.core.network.AccountsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultAccountsRepository @Inject constructor(
    private val api: AccountsApi
) : AccountsRepository {

    override fun registration(
        owner: String,
        email: String,
        password1: String,
        password2: String
    ): Flow<Registration> = flow {
        val response = api.registration(
            request = RegistrationRequest(
                owner = owner,
                email =email,
                password1 = password1,
                password2 = password2
            )
        )

        emit(
            Registration.from(
                response ?:
                RegistrationResponse(
                    accessToken = "",
                    refreshToken = "",
                    user = null
                )
            )
        )
    }
}