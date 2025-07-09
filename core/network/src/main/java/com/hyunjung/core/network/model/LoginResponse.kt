package com.hyunjung.core.network.model

import com.hyunjung.core.model.AuthTokens
import com.hyunjung.core.model.LoginResult
import com.hyunjung.core.model.User
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(
    val code: Int,
    val message: String,
    val result: LoginResultResponse
)

// TODO: Populate email, nickname, and profileImage when user profile API is added
fun LoginResponse.toDomain(): LoginResult = LoginResult(
    user = User(
        userId = result.userId,
        email = null,
        nickname = null,
        profileImage = null
    ),
    tokens = AuthTokens(
        accessToken = result.tokens.accessToken,
        refreshToken = result.tokens.refreshToken
    )
)