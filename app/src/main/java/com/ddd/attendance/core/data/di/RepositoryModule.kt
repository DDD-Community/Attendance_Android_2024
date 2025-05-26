package com.ddd.attendance.core.data.di

import com.ddd.attendance.core.data.api.AccountsApi
import com.ddd.attendance.core.data.repository.DefaultRegistrationRepository
import com.ddd.attendance.core.network.RegistrationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideRegistrationRepository(api: AccountsApi): RegistrationRepository = DefaultRegistrationRepository(api)
}