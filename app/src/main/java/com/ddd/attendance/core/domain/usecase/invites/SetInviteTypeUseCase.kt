package com.ddd.attendance.core.domain.usecase.invites

import com.ddd.attendance.core.network.InvitesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SetInviteTypeUseCase @Inject constructor(
    private val repository: InvitesRepository
) {
    suspend operator fun invoke() {
        repository.setInviteType()
    }
}
