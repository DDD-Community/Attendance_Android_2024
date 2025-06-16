package com.ddd.attendance.core.domain.usecase.accounts

import com.ddd.attendance.core.data.repository.DefaultAccountsRepository
import com.ddd.attendance.core.network.AccountsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CheckEmailUseCase @Inject constructor (
    private val repository: DefaultAccountsRepository
) {
    operator fun invoke(email: String) = repository.checkEmail(email = email)
}