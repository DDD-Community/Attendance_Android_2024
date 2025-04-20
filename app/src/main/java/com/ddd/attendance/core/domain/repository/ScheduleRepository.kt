package com.ddd.attendance.core.domain.repository

import com.ddd.attendance.core.model.Schedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getSchedules(): Flow<List<Schedule>>
}