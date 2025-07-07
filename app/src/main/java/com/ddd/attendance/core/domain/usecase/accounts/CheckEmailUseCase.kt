package com.ddd.attendance.core.domain.usecase.accounts

import com.ddd.attendance.core.data.repository.DefaultAccountsRepository
import javax.inject.Inject

class CheckEmailUseCase @Inject constructor (
    private val repository: DefaultAccountsRepository
) {
    operator fun invoke(email: String) = repository.checkEmail(email = email)
}