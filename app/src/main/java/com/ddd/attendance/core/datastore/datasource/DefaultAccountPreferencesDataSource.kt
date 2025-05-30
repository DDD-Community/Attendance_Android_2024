package com.ddd.attendance.core.datastore.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Named

class DefaultAccountPreferencesDataSource @Inject constructor (
    @Named("account") private val dataStore: DataStore<Preferences>
): AccountPreferencesDataSource {
    object PreferencesKey {
        val ACCOUNT_PREFERENCES = stringPreferencesKey("ACCOUNT_PREFERENCES")
    }

    override val accountAccessToken: Flow<String> = dataStore.data.map { preferences ->
        preferences[PreferencesKey.ACCOUNT_PREFERENCES] ?: ""
    }

    override suspend fun updateAccountAccessToken(accountAccessToken: String) {
        dataStore.edit { preferences ->
            preferences[PreferencesKey.ACCOUNT_PREFERENCES] = accountAccessToken
        }
    }
}