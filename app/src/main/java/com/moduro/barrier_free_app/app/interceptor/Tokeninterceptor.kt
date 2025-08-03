package com.moduro.barrier_free_app.app.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class TokenInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1IiwiaWF0IjoxNzU0MTg0NDM3LCJleHAiOjE3NTQxODgwMzd9.UiIHb_kzn8SOualMq6bbzCV6KUSWsP7_a5Xk9dF7uxg") // 임시 토큰
            .build()
        return chain.proceed(newRequest)
    }
}
