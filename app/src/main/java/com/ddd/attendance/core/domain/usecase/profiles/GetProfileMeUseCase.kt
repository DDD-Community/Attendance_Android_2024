package com.ddd.attendance.core.domain.usecase.profiles

import com.ddd.attendance.core.data.repository.DefaultProfilesRepository
import javax.inject.Inject

class GetProfileMeUseCase @Inject constructor(
    private val repository: DefaultProfilesRepository
) {
    operator fun invoke() = repository.getProfileMe()
}