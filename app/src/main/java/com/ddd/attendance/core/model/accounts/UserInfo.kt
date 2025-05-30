package com.ddd.attendance.core.model.accounts

data class UserInfo(
    val email: String = "",
    val name: String = "",
    val uid: String = "",
    val jobRole: String = "",
    val affiliation: String = ""
)