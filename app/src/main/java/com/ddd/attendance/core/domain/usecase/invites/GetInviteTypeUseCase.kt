package com.ddd.attendance.core.domain.usecase.invites

import com.ddd.attendance.core.network.InvitesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetInviteTypeUseCase @Inject constructor(
    private val repository: InvitesRepository
) {
    operator fun invoke(): Flow<String> = repository.getInviteCode()
}
