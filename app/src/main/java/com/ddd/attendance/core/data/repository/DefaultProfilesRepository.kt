package com.ddd.attendance.core.data.repository

import com.ddd.attendance.core.data.api.ProfilesApi
import com.ddd.attendance.core.data.api.request.profiles.ProfileMeRequest
import com.ddd.attendance.core.datastore.datasource.AccountPreferencesDataSource
import com.ddd.attendance.core.model.invites.ProfileMe
import com.ddd.attendance.core.network.ProfilesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultProfilesRepository @Inject constructor(
    private val api: ProfilesApi,
    private val dataSource: AccountPreferencesDataSource
): ProfilesRepository {
    override fun profileMe(
        name: String,
        inviteCodeId: String,
        role: String,
        team: String,
        responsibility: String,
        cohort: String
    ): Flow<ProfileMe> = flow {
        val response = api.profileMe(
            request = ProfileMeRequest(
                name = name,
                inviteCodeId = inviteCodeId,
                role = role,
                team = team,
                responsibility = responsibility,
                cohort = cohort
            )
        )
        response.data?.let {
            val type = if (it.isStaff) "moderator" else "member"
            dataSource.updateAccountInviteType(type)
        }

        emit(ProfileMe.from(response.data))
    }
}