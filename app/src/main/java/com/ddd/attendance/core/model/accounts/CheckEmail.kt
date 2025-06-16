package com.ddd.attendance.core.model.accounts

import com.ddd.attendance.core.data.api.model.accounts.CheckEmailResponse

data class CheckEmail(
    val emailUsed: Boolean
) {
    companion object {
        fun from(response: CheckEmailResponse?): CheckEmail {
            return CheckEmail(
                emailUsed = response?.emailUsed?: false
            )
        }
    }
}