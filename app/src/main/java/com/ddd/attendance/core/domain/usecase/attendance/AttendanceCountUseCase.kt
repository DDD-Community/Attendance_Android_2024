package com.ddd.attendance.core.domain.usecase.attendance

import com.ddd.attendance.core.data.repository.DefaultAttendanceRepository
import javax.inject.Inject

class AttendanceCountUseCase @Inject constructor (
    private val repository: DefaultAttendanceRepository
) {
    operator fun invoke() = repository.attendanceCount()
}