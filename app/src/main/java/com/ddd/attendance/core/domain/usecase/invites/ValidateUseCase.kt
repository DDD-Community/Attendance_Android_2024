package com.ddd.attendance.core.domain.usecase.invites

import com.ddd.attendance.core.data.repository.DefaultInvitesRepository
import javax.inject.Inject

class ValidateUseCase @Inject constructor(
    private val repository: DefaultInvitesRepository
) {
    operator fun invoke(inviteCode: String) = repository.validate(inviteCode = inviteCode)
}