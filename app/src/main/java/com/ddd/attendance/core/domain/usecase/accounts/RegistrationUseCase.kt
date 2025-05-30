package com.ddd.attendance.core.domain.usecase.accounts

import com.ddd.attendance.core.data.repository.DefaultAccountsRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor (
    private val repository: DefaultAccountsRepository
) {
    operator fun invoke(
        owner: String,
        email: String,
        password1: String,
        password2: String
    ) = repository.registration(
        owner = owner,
        email = email,
        password1 = password1,
        password2 = password2
    )
}