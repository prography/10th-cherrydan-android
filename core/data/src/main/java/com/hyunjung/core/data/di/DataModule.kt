package com.hyunjung.core.data.di

import com.hyunjung.core.data.repository.AuthRepositoryImpl
import com.hyunjung.core.domain.repository.AuthRepository
import com.hyunjung.core.model.SocialType
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module {
    single<AuthRepository> {
        AuthRepositoryImpl(
            kakao = get(named(SocialType.KAKAO.name)),
            authRemoteDataSource = get(),
            tokenManager = get()
        )
    }
}