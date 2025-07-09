package com.hyunjung.core.network.model

import kotlinx.serialization.Serializable

@Serializable
data class SocialLoginRequestResponse(
    val accessToken: String,
    val fcmToken: String? = null,
    val deviceType: String = "android"
)