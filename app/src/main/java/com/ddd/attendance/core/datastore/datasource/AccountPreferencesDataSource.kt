package com.ddd.attendance.core.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface AccountPreferencesDataSource {
    val accountAccessToken: Flow<String>
    suspend fun updateAccountAccessToken(accessToken: String)

    val accountInviteType: Flow<String>
    suspend fun updateAccountInviteType(inviteType: String)

    val accountUserId: Flow<Int>
    suspend fun updateAccountUserId(userId: Int)
}