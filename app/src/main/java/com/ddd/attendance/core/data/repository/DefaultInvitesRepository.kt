package com.ddd.attendance.core.data.repository

import com.ddd.attendance.core.data.api.InvitesApi
import com.ddd.attendance.core.data.api.request.invites.ValidateRequest
import com.ddd.attendance.core.datastore.datasource.AccountPreferencesDataSource
import com.ddd.attendance.core.model.invites.Validate
import com.ddd.attendance.core.network.InvitesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultInvitesRepository @Inject constructor(
    private val api: InvitesApi,
    private val dataSource: AccountPreferencesDataSource
): InvitesRepository {
    private val inviteType: Flow<String> = dataSource.accountInviteType
    private val inviteCodeId: Flow<String> = dataSource.accountInviteCodeId

    override fun validate(inviteCode: String): Flow<Validate> = flow {
        val response = api.validate(
            request = ValidateRequest(
                inviteCode = inviteCode
            )
        )

        response.data?.let {
            dataSource.updateAccountInviteCodeId(it.inviteCodeId)
        }

        emit(Validate.from(response.data))
    }

    override fun getInviteType(): Flow<String> = inviteType

    override fun getInviteCodeId(): Flow<String> = inviteCodeId
}