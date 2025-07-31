package com.moduro.barrier_free_app.app.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0IiwiaWF0IjoxNzUzOTA1Nzk4LCJleHAiOjE3NTM5MDkzOTh9.Rpj8bbrLumHyshN-nGiH9Mk2vtbz7j4ntbfr4D9kl4U") // 임시 토큰
            .build()
        return chain.proceed(newRequest)
    }
}
