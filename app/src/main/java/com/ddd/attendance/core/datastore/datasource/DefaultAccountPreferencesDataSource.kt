package com.ddd.attendance.core.datastore.datasource

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class DefaultAccountPreferencesDataSource @Inject constructor (
    @Named("account") private val dataStore: DataStore<Preferences>,
): AccountPreferencesDataSource {
    object PreferencesKey {
        val ACCOUNT_ACCESS_TOKEN = stringPreferencesKey("ACCOUNT_ACCESS_TOKEN")
        val ACCOUNT_INVITE_TYPE = stringPreferencesKey("ACCOUNT_INVITE_TYPE")
        val ACCOUNT_USER_ID = intPreferencesKey("ACCOUNT_USER_ID")
    }

    override val accountAccessToken: Flow<String> = dataStore.data.map { preferences ->
        preferences[PreferencesKey.ACCOUNT_ACCESS_TOKEN] ?: ""
    }

    override suspend fun updateAccountAccessToken(accessToken: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.ACCOUNT_ACCESS_TOKEN] = accessToken
            Log.e("Datastore 액세스 토큰", "${preferences[PreferencesKey.ACCOUNT_ACCESS_TOKEN]}")
        }
    }

    override val accountInviteType: Flow<String> = dataStore.data.map { preferences ->
        preferences[PreferencesKey.ACCOUNT_INVITE_TYPE] ?: ""
    }

    override suspend fun updateAccountInviteType(inviteType: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.ACCOUNT_INVITE_TYPE] = inviteType
            Log.e("Datastore 초대 코드", "${preferences[PreferencesKey.ACCOUNT_INVITE_TYPE]}")
        }
    }

    override val accountUserId: Flow<Int> = dataStore.data.map { preferences ->
        preferences[PreferencesKey.ACCOUNT_USER_ID] ?: - 1
    }

    override suspend fun updateAccountUserId(userId: Int) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.ACCOUNT_USER_ID] = userId
            Log.e("Datastore 유저 아이디", "${preferences[PreferencesKey.ACCOUNT_USER_ID]}")
        }
    }
}