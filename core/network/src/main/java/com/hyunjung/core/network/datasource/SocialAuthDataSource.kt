package com.hyunjung.core.network.datasource

import com.hyunjung.core.model.AuthTokens

interface SocialAuthDataSource {
    suspend fun login(): Result<AuthTokens, DataError>
}