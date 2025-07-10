package com.hyunjung.cherrydan

import android.app.Application
import com.hyunjung.core.data.di.dataModule
import com.hyunjung.core.network.di.networkModule
import com.hyunjung.feature.auth.di.authViewModelModule
import com.kakao.sdk.common.KakaoSdk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class CherrydanApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        // Timber 초기화 (가장 먼저)
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        // 카카오 앱키 확인
        val kakaoAppKey = BuildConfig.KAKAO_NATIVE_APP_KEY
        Timber.d("Kakao App Key: $kakaoAppKey")

        if (kakaoAppKey.isEmpty()) {
            Timber.e("Kakao App Key가 설정되지 않았습니다!")
        }

        // Kakao SDK 초기화
        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_APP_KEY)

        // Koin 초기화
        startKoin {
            androidContext(this@CherrydanApplication)
            modules(
                authViewModelModule,
                dataModule,
                networkModule
                // 다른 모듈들도 여기에 추가
            )
        }
    }
}