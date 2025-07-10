package com.hyunjung.core.model

data class LoginResult(
    val user: User,
    val tokens: AuthTokens
)