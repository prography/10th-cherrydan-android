package com.hyunjung.auth.presentation.login

interface LogInAction {
    data object OnKakaoLogInClick : LogInAction
    data object OnNaverLogInClick : LogInAction
    data object OnGoogleLogInClick : LogInAction
}