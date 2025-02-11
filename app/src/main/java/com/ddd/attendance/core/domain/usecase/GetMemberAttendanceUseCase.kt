package com.ddd.attendance.core.domain.usecase

import com.ddd.attendance.core.domain.repository.MemberRepository
import com.ddd.attendance.core.model.member.MemberAttendance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

class GetMemberAttendanceUseCase @Inject constructor(
    private val repository: MemberRepository
) {
    operator fun invoke(memberId: String): Flow<MemberAttendance> {
        val data = repository.getMemberAttendance(memberId)
        return data.filterNotNull()
    }
}