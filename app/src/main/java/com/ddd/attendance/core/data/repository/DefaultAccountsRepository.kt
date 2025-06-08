package com.ddd.attendance.core.data.repository

import com.ddd.attendance.core.data.api.AccountsApi
import com.ddd.attendance.core.data.api.model.accounts.RegistrationResponse
import com.ddd.attendance.core.data.api.request.accounts.RegistrationRequest
import com.ddd.attendance.core.datastore.datasource.AccountPreferencesDataSource
import com.ddd.attendance.core.model.accounts.Registration
import com.ddd.attendance.core.network.AccountsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultAccountsRepository @Inject constructor(
    private val api: AccountsApi,
    private val dataSource: AccountPreferencesDataSource
) : AccountsRepository {
    private val accessToken: Flow<String> = dataSource.accountAccessToken

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

        val data = Registration.from(
            response ?:
            RegistrationResponse(
                accessToken = "",
                refreshToken = "",
                user = null
            )
        )
        data.user?.id?.let {
            dataSource.updateAccountUserId(it)
        }

        dataSource.updateAccountAccessToken(data.accessToken)

        emit(data)
    }

    override fun getAccessToken(): Flow<String> {
        return accessToken.filter {
            it.isNotBlank()
        }
    }
}