package com.moduro.barrier_free_app.app.interceptor

import android.util.Log
import com.moduro.barrier_free_app.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject


class LocationNameTokenInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("Interceptor", "카카오 인증 헤더 추가됨")
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "KakaoAK ${BuildConfig.LOCATION_NAME_SERVICE_KEY}")
            .build()
        return chain.proceed(request)
    }
}
