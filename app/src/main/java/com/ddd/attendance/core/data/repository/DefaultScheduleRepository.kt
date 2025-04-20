package com.ddd.attendance.core.data.repository

import com.ddd.attendance.core.domain.repository.ScheduleRepository
import com.ddd.attendance.core.model.Schedule
import com.ddd.attendance.core.network.service.ScheduleApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultScheduleRepository @Inject constructor(
    private val api: ScheduleApiService
) : ScheduleRepository {
    override fun getSchedules(): Flow<List<Schedule>> = flow {
        emit(api.getSchedules().data?.map { Schedule.from(it) }.orEmpty())
    }
}