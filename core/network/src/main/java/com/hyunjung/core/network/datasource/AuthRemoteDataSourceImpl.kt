package com.hyunjung.core.network.datasource

import com.hyunjung.core.common.util.DataError
import com.hyunjung.core.common.util.Result
import com.hyunjung.core.common.util.map
import com.hyunjung.core.model.LoginResult
import com.hyunjung.core.model.SocialType
import com.hyunjung.core.network.model.LoginResponse
import com.hyunjung.core.network.model.SocialLoginRequestResponse
import com.hyunjung.core.network.model.toDomain
import com.hyunjung.core.network.resource.AuthResource
import com.hyunjung.core.network.util.post
import io.ktor.client.HttpClient

class AuthRemoteDataSourceImpl(
    private val client: HttpClient
) : AuthRemoteDataSource {
    override suspend fun loginWithSocial(
        socialType: SocialType,
        accessToken: String,
        fcmToken: String?,
    ): Result<LoginResult, DataError> {
        return when (socialType) {
            SocialType.KAKAO -> loginWithKakao(accessToken, fcmToken)
            SocialType.NAVER -> loginWithNaver(accessToken, fcmToken)
            SocialType.GOOGLE -> loginWithGoogle(accessToken, fcmToken)
        }
    }

    private suspend fun loginWithKakao(
        accessToken: String,
        fcmToken: String?
    ): Result<LoginResult, DataError> =
        performSocialLogin(AuthResource.Kakao.Login(), accessToken, fcmToken)

    private suspend fun loginWithNaver(
        accessToken: String,
        fcmToken: String?
    ): Result<LoginResult, DataError> =
        performSocialLogin(AuthResource.Naver.Login(), accessToken, fcmToken)

    private suspend fun loginWithGoogle(
        accessToken: String,
        fcmToken: String?
    ): Result<LoginResult, DataError> =
        performSocialLogin(AuthResource.Google.Login(), accessToken, fcmToken)

    private suspend inline fun <reified T : Any> performSocialLogin(
        resource: T,
        accessToken: String,
        fcmToken: String?
    ): Result<LoginResult, DataError> = client.post<T, LoginResponse>(
        resource = resource,
        body = SocialLoginRequestResponse(
            accessToken = accessToken,
            fcmToken = fcmToken,
            deviceType = "android"
        )
    ).map { it.toDomain() }
}