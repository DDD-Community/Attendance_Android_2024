package com.ddd.attendance.core.datastore.datasource

import kotlinx.coroutines.flow.Flow

interface AccountPreferencesDataSource {
    val accountAccessToken: Flow<String>
    suspend fun updateAccountAccessToken(accountAccessToken: String)
}