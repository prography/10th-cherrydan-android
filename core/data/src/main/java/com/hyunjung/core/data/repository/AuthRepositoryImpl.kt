package com.hyunjung.core.data.repository

import android.content.Context
import com.hyunjung.core.common.util.DataError
import com.hyunjung.core.common.util.Result
import com.hyunjung.core.common.util.onSuccess
import com.hyunjung.core.domain.repository.AuthRepository
import com.hyunjung.core.model.AuthTokens
import com.hyunjung.core.model.LoginResult
import com.hyunjung.core.model.SocialType
import com.hyunjung.core.network.datasource.AuthRemoteDataSource
import com.hyunjung.core.network.datasource.SocialAuthDataSource
import com.hyunjung.core.network.token.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val kakao: SocialAuthDataSource,
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val tokenManager: TokenManager
) : AuthRepository {
    override fun login(
        context: Context,
        socialType: SocialType
    ): Flow<Result<LoginResult, DataError>> = flow {
        emit(
            when (val socialConnected = connectSocialAccount(context, socialType)) {
                is Result.Success -> {
                    authRemoteDataSource.loginWithSocial(
                        socialType,
                        socialConnected.data.accessToken
                    )
                        .onSuccess { result ->
                            val (accessToken, refreshToken) = result.tokens
                            tokenManager.saveTokens(accessToken, refreshToken)
                        }
                }

                is Result.Error -> {
                    Result.Error(socialConnected.error)
                }
            })
    }

    private suspend fun connectSocialAccount(
        context: Context,
        type: SocialType
    ): Result<AuthTokens, DataError> =
        when (type) {
            SocialType.KAKAO -> kakao.login(context)
            SocialType.NAVER -> Result.Error(DataError.Network.UNKNOWN)
            SocialType.GOOGLE -> Result.Error(DataError.Network.UNKNOWN)
        }
}