package com.ddd.attendance.core.model.invites

import com.ddd.attendance.core.data.api.model.profiles.ProfileMeResponse

data class ProfileMe(
    val id: String,
    val userId: Int,
    val name: String,
    val inviteCodeId: String,
    val role: String,
    val team: String,
    val responsibility: String,
    val cohort: String,
    val isStaff: Boolean,
    val createdAt: String,
    val updatedAt: String
) {
    companion object {
        fun from(response: ProfileMeResponse?): ProfileMe {
            return ProfileMe(
                id = response?.id?: "",
                userId = response?.userId?: 0,
                name = response?.name?: "",
                inviteCodeId = response?.inviteCodeId?: "",
                role = response?.role?: "",
                team = response?.team?: "",
                responsibility = response?.responsibility?: "",
                cohort = response?.cohort?: "",
                isStaff = response?.isStaff?: false,
                createdAt = response?.createdAt?: "",
                updatedAt = response?.updatedAt?: ""
            )
        }
    }
}