package com.hyunjung.core.model

data class User(
    val userId: Long,
    val email: String?,
    val nickname: String?,
    val profileImage: String?
)