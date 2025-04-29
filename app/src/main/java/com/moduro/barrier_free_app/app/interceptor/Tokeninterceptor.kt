package com.moduro.barrier_free_app.app.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer your_token_here") // 임시 토큰
            .build()
        return chain.proceed(newRequest)
    }
}
