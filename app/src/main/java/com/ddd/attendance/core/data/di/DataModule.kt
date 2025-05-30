package com.ddd.attendance.core.data.di

import com.ddd.attendance.core.datastore.datasource.AccountPreferencesDataSource
import com.ddd.attendance.core.datastore.datasource.DefaultAccountPreferencesDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
internal abstract class DataModule {
    @Binds
    abstract fun bindAccountLocalDataSource(
        dataModule: DefaultAccountPreferencesDataSource
    ): AccountPreferencesDataSource
}