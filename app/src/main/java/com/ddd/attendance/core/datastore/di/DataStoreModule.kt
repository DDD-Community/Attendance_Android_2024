package com.ddd.attendance.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private const val ACCOUNT_DATASTORE_NAME = "ACCOUNT_PREFERENCES"

    private val Context.accountDataStore by preferencesDataStore(ACCOUNT_DATASTORE_NAME)

    @Provides
    @Singleton
    @Named("account")
    fun provideAccountDataStore(@ApplicationContext context: Context): DataStore<Preferences> = context.accountDataStore
}