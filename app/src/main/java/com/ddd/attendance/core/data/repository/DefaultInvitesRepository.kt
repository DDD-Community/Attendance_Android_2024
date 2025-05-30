package com.ddd.attendance.core.data.repository

import com.ddd.attendance.core.data.api.InvitesApi
import com.ddd.attendance.core.data.api.request.validate.ValidateRequest
import com.ddd.attendance.core.model.accounts.Validate
import com.ddd.attendance.core.network.InvitesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultInvitesRepository @Inject constructor(
    private val api: InvitesApi
): InvitesRepository {
    override fun validate(inviteCode: String): Flow<Validate> = flow {
        val response = api.validate(
            request = ValidateRequest(
                inviteCode = inviteCode
            )
        )

        emit(Validate.from(response.data))
    }
}