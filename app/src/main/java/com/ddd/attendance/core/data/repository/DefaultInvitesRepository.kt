package com.ddd.attendance.core.data.repository

import com.ddd.attendance.core.data.api.InvitesApi
import com.ddd.attendance.core.data.api.request.validate.ValidateRequest
import com.ddd.attendance.core.datastore.datasource.AccountPreferencesDataSource
import com.ddd.attendance.core.model.accounts.Validate
import com.ddd.attendance.core.network.InvitesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultInvitesRepository @Inject constructor(
    private val api: InvitesApi,
    private val dataSource: AccountPreferencesDataSource
): InvitesRepository {
    private val inviteType: Flow<String> = dataSource.accountInviteType

    override fun validate(inviteCode: String): Flow<Validate> = flow {
        val response = api.validate(
            request = ValidateRequest(
                inviteCode = inviteCode
            )
        )

        response.data?.let {
            dataSource.updateAccountInviteType(it.inviteType)
        }

        emit(Validate.from(response.data))
    }

    override fun getInviteCode(): Flow<String> {
        return inviteType
    }
}