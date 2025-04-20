package com.ddd.attendance.core.data.di

import com.ddd.attendance.core.data.repository.DefaultMemberRepository
import com.ddd.attendance.core.data.repository.DefaultQrRepository
import com.ddd.attendance.core.data.repository.DefaultScheduleRepository
import com.ddd.attendance.core.domain.repository.MemberRepository
import com.ddd.attendance.core.domain.repository.QrRepository
import com.ddd.attendance.core.domain.repository.ScheduleRepository
import com.ddd.attendance.core.network.service.MemberApiService
import com.ddd.attendance.core.network.service.QrApiService
import com.ddd.attendance.core.network.service.ScheduleApiService
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
    fun provideQrRepository(api: QrApiService): QrRepository = DefaultQrRepository(api)

    @Provides
    @Singleton
    fun provideMemberRepository(api: MemberApiService): MemberRepository = DefaultMemberRepository(api)

    @Provides
    @Singleton
    fun provideScheduleRepository(api: ScheduleApiService): ScheduleRepository = DefaultScheduleRepository(api)
}