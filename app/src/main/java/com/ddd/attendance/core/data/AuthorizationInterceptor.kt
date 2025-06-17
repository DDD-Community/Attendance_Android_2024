package com.ddd.attendance.core.data

import com.ddd.attendance.core.datastore.datasource.AccountPreferencesDataSource
import jakarta.inject.Inject
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor @Inject constructor(
    private val accountPreferencesDataSource: AccountPreferencesDataSource
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        val token = runBlocking {
            accountPreferencesDataSource.accountAccessToken.first()
        }

        if (token.isNotEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}