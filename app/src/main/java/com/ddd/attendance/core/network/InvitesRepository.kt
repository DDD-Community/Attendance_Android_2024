package com.ddd.attendance.core.network

import com.ddd.attendance.core.model.invites.Validate
import kotlinx.coroutines.flow.Flow

interface InvitesRepository {
    fun validate(inviteCode: String): Flow<Validate>
    fun getInviteType(): Flow<String>
    fun getInviteCodeId(): Flow<String>
    suspend fun setInviteType()
}