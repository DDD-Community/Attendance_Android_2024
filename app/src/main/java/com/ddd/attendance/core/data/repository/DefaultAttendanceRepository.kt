package com.ddd.attendance.core.data.repository

import com.ddd.attendance.core.data.api.AttendanceApi
import com.ddd.attendance.core.datastore.datasource.AccountPreferencesDataSource
import com.ddd.attendance.core.model.attendance.AttendanceCount
import com.ddd.attendance.core.model.attendance.Attendance
import com.ddd.attendance.core.network.AttendanceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultAttendanceRepository @Inject constructor(
    private val api: AttendanceApi,
    private val dataSource: AccountPreferencesDataSource
): AttendanceRepository {
    override fun attendanceCount(): Flow<AttendanceCount> = flow {
        val response = api.attendanceCount()

        emit(
            value = AttendanceCount.from(
                response = response.data
            )
        )
    }

    override fun attendanceList(): Flow<List<Attendance>> = flow {
        val response = api.attendanceList()

        emit(
            value = Attendance.from(
                response = response.data
            )
        )
    }
}