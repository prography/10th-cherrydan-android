package com.hyunjung.core.network.resource

import io.ktor.resources.Resource

@Resource("/api/auth")
class AuthResource() {
    @Resource("kakao")
    class Kakao(val parent: AuthResource = AuthResource()) {
        @Resource("login")
        class Login(val parent: Kakao = Kakao())
    }

    @Resource("naver")
    class Naver(val parent: AuthResource = AuthResource()) {
        @Resource("login")
        class Login(val parent: Naver = Naver())
    }

    @Resource("google")
    class Google(val parent: AuthResource = AuthResource()) {
        @Resource("login")
        class Login(val parent: Google = Google())
    }
}