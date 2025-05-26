package com.ddd.attendance.core.domain.usecase

import com.ddd.attendance.core.data.repository.DefaultRegistrationRepository
import javax.inject.Inject

class RegistrationUseCase @Inject constructor (
    private val repository: DefaultRegistrationRepository
) {
    operator fun invoke(
        owner: String,
        email: String,
        password1: String,
        password2: String
    ) = repository.registration(owner, email, password1, password2)
}