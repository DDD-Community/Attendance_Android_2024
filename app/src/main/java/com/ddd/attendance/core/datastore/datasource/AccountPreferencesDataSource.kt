package com.ddd.attendance.core.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface AccountPreferencesDataSource {
    //eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzQ5NjkzNjk4LCJpYXQiOjE3NDk2MDcyOTgsImp0aSI6ImM5YTNlY2NlZTJhYTQ2NjM5YTUxNzI3MDA0ZDhiYjI3IiwidXNlcl9pZCI6MjIyfQ.trNl-QXkI6Eh2QETTccBXqM58rLkB1RmFkvpMOc5VXU
    val accountAccessToken: Flow<String>
    suspend fun updateAccountAccessToken(accessToken: String)

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