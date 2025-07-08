package com.hyunjung.core.network.datasource

import android.content.Context
import com.hyunjung.core.model.AuthTokens
import com.hyunjung.core.network.token.TokenManager
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.suspendCancellableCoroutine
import timber.log.Timber

class KakaoAuthDataSource(
    private val kakaoUserApiClient: UserApiClient,
    private val tokenManager: TokenManager,
    private val context: Context
) : SocialAuthDataSource {

    override suspend fun login(): Result<AuthTokens, DataError> =
        suspendCancellableCoroutine { continuation ->
            Timber.d("카카오 로그인 시작 - ${context::class.java.simpleName}")

            val callback = createKakaoLoginCallback(continuation)

            if (kakaoUserApiClient.isKakaoTalkLoginAvailable(context)) {
                Timber.d("카카오톡 앱으로 로그인 시도")
                kakaoUserApiClient.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        Timber.w(error, "카카오톡 로그인 실패, 계정 로그인으로 fallback")
                        kakaoUserApiClient.loginWithKakaoAccount(
                            context = context,
                            callback = callback
                        )
                    } else {
                        callback(token, null)
                    }
                }
            } else {
                Timber.d("카카오 계정으로 로그인 시도")
                kakaoUserApiClient.loginWithKakaoAccount(context = context, callback = callback)
            }
        }

    private fun resumeIfActive(
        continuation: CancellableContinuation<Result<AuthTokens, DataError>>,
        result: Result<AuthTokens, DataError>
    ) {
        if (continuation.isActive) {
            continuation.resume(result)
        }
    }

    private fun createKakaoLoginCallback(
        continuation: CancellableContinuation<Result<AuthTokens, DataError>>,
    ): (OAuthToken?, Throwable?) -> Unit = { token, error ->
        when {
            error != null -> {
                val result =
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        Timber.d("카카오 로그인 취소됨")
                        DataError.Network.UNAUTHORIZED
                    } else {
                        Timber.e(error, "카카오 로그인 실패")
                        DataError.Network.UNKNOWN
                    }
                resumeIfActive(continuation, Result.Error(result))
            }

            token != null -> {
                Timber.d("카카오 로그인 성공: ${token.accessToken.take(5)}...")
                tokenManager.saveTokens(token.accessToken, token.refreshToken)
                val authTokens = AuthTokens(token.accessToken, token.refreshToken)
                resumeIfActive(continuation, Result.Success(authTokens))
            }

            else -> {
                Timber.e("카카오 로그인 실패: 토큰과 에러가 모두 null")
                resumeIfActive(continuation, Result.Error(DataError.Network.UNKNOWN))
            }
        }
    }
}