package com.hyunjung.core.network.datasource

import com.hyunjung.core.model.LoginResult
import com.hyunjung.core.model.SocialType
import io.ktor.client.HttpClient
import io.ktor.client.request.post

class AuthRemoteDataSourceImpl(
    private val client: HttpClient
) : AuthRemoteDataSource {
    override suspend fun loginWithSocial(
        socialType: SocialType,
        accessToken: String,
        fcmToken: String?,
    ): Result<LoginResult, DataError> =
        client.post<AuthResource.Kakao.Login, LoginResponse>(
            resource = AuthResource.Kakao.Login(),
            body = KakaoLoginRequestResponse(
                accessToken = accessToken,
                fcmToken = fcmToken,
                deviceType = "android"
            )
        ).map { it.toDomain() }
}