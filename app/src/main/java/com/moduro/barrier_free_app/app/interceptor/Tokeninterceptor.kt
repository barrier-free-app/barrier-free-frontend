package com.moduro.barrier_free_app.app.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI0IiwiaWF0IjoxNzUzODAwMzA1LCJleHAiOjE3NTM4MDM5MDV9.7O3mjTMuzgtXS2re4QAq6MbTeVYlRLTv38et6chcWoM") // 임시 토큰
            .build()
        return chain.proceed(newRequest)
    }
}
