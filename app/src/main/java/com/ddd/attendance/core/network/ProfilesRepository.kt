package com.ddd.attendance.core.network

import com.ddd.attendance.core.model.invites.ProfileMe
import kotlinx.coroutines.flow.Flow

interface ProfilesRepository {
    fun profileMe(
        name: String,
        inviteCodeId: String,
        role: String,
        team: String,
        responsibility: String,
        cohort: String
    ): Flow<ProfileMe>
}