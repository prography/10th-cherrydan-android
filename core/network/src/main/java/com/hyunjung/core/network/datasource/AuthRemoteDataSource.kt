package com.hyunjung.core.network.datasource

import com.hyunjung.core.model.LoginResult
import com.hyunjung.core.model.SocialType

interface AuthRemoteDataSource {
    suspend fun loginWithSocial(
        socialType: SocialType,
        accessToken: String,
        fcmToken: String? = null,
    ): Result<LoginResult, DataError>
}