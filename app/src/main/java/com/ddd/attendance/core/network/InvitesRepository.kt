package com.ddd.attendance.core.network

import com.ddd.attendance.core.model.accounts.Validate
import kotlinx.coroutines.flow.Flow

interface InvitesRepository {
    fun validate(inviteCode: String): Flow<Validate>
    fun getInviteCode(): Flow<String>
}