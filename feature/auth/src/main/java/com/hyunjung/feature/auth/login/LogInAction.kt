package com.hyunjung.feature.auth.login

interface LogInAction {
    data object OnKakaoLogInClick : LogInAction
    data object OnNaverLogInClick : LogInAction
    data object OnGoogleLogInClick : LogInAction
}