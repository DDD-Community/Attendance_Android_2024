package com.ddd.attendance.core.model.accounts

data class UserInfo(
    val email: String = "",
    val name: String = "",
    val uid: String = "",
    val role: String = "",
    val team: String = "",
    val affiliation: String = "",
    val inviteType: String = "",
    val inviteCodeId: String = ""
)