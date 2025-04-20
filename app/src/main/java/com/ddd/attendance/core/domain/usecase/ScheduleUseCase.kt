package com.ddd.attendance.core.domain.usecase

import com.ddd.attendance.core.domain.repository.ScheduleRepository
import com.ddd.attendance.core.model.Schedule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

class ScheduleUseCase @Inject constructor(
    private val repository: ScheduleRepository
) {
    operator fun invoke(): Flow<List<Schedule>> {
        return repository.getSchedules().filterNotNull()
    }
}