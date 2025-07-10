package com.hyunjung.feature.auth.login

import com.hyunjung.core.model.User

data class LogInState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null
)