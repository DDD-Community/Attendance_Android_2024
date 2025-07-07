package com.ddd.attendance.core.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface AccountPreferencesDataSource {
    val accountAccessToken: Flow<String>
    suspend fun updateAccountAccessToken(accessToken: String)

    //test@gmail.com
    val accountEmail: Flow<String>
    suspend fun updateEmail(email: String)

    // member or moderator
    val accountInviteType: Flow<String>
    suspend fun updateAccountInviteType(inviteType: String)

    // bbc75674-3a89-447f-91b1-9f7de11d9fb2
    val accountInviteCodeId: Flow<String>
    suspend fun updateAccountInviteCodeId(inviteCodeId: String)

    // 127
    val accountUserId: Flow<Int>
    suspend fun updateAccountUserId(userId: Int)
}