package com.ddd.attendance.core.domain.usecase.accounts

import com.ddd.attendance.core.network.AccountsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEmailUseCase @Inject constructor(private val repository: AccountsRepository) {
    operator fun invoke(): Flow<String> = repository.getEmail()
}
