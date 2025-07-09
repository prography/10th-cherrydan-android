package com.hyunjung.core.network.di

import com.hyunjung.core.model.SocialType
import com.hyunjung.core.network.datasource.AuthRemoteDataSource
import com.hyunjung.core.network.datasource.AuthRemoteDataSourceImpl
import com.hyunjung.core.network.datasource.KakaoAuthDataSource
import com.hyunjung.core.network.datasource.SocialAuthDataSource
import com.hyunjung.core.network.model.TokenResponse
import com.hyunjung.core.network.token.AuthTokenManager
import com.hyunjung.core.network.token.TokenManager
import com.kakao.sdk.user.UserApiClient
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module
import timber.log.Timber

val networkModule = module {
    single<Json> {
        Json {
            ignoreUnknownKeys = true
            isLenient = true
        }
    }
    single<TokenManager> { AuthTokenManager(get()) }
    single<HttpClient> { provideHttpClient(get(), get()) }
    single<UserApiClient> { UserApiClient.instance }

    single<SocialAuthDataSource>(named(SocialType.KAKAO.name)) {
        KakaoAuthDataSource(get())
    }

    // single<SocialAuthDataSource>(named(SocialType.NAVER.name)) {
    //     // NaverAuthDataSource는 아직 구현되지 않았습니다.
    //     throw NotImplementedError("NaverAuthDataSource is not implemented yet.")
    // }
    single<AuthRemoteDataSource> { AuthRemoteDataSourceImpl(get()) }
    // single<SocialAuthDataSource>(named(SocialType.GOOGLE.name)) {
    //     // GoogleAuthDataSource는 아직 구현되지 않았습니다.
    //     throw NotImplementedError("GoogleAuthDataSource is not implemented yet.")
    // }
}

private fun provideHttpClient(
    json: Json,
    tokenManager: TokenManager
): HttpClient = HttpClient(CIO) {

    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = "cherrydan.com"
        }
        contentType(ContentType.Application.Json)
    }
    install(Resources)
    install(ContentNegotiation) {
        json(json)
    }

    install(Logging) {
        logger = object : Logger {
            override fun log(message: String) {
                Timber.d("HTTP: $message")
            }
        }
        level = LogLevel.BODY
    }

    install(Auth) {
        bearer {
            loadTokens {
                val accessToken = tokenManager.getAccessToken()
                val refreshToken = tokenManager.getRefreshToken()

                if (accessToken != null && refreshToken != null) {
                    BearerTokens(accessToken, refreshToken)
                } else {
                    null
                }
            }

            // todo : 토큰 갱신 로직 수정 필요
            refreshTokens {
                // 토큰 갱신 로직
                val refreshToken = tokenManager.getRefreshToken()
                if (refreshToken != null) {
                    try {
                        val response = client.post("https://cherrydan.com/api/auth/refresh") {
                            contentType(ContentType.Application.Json)
                            setBody(mapOf("refreshToken" to refreshToken))
                        }

                        val newTokens = response.body<TokenResponse>()
                        tokenManager.saveTokens(
                            newTokens.accessToken,
                            newTokens.refreshToken
                        )

                        BearerTokens(newTokens.accessToken, newTokens.refreshToken)
                    } catch (e: Exception) {
                        Timber.e(e, "토큰 갱신 실패")
                        tokenManager.clearTokens()
                        null
                    }
                } else {
                    null
                }
            }
        }
    }
}