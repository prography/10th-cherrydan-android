package com.hyunjung.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class LoginResultResponse(
    val userId: Long,
    val tokens: TokenResponse
)