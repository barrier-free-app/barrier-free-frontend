package com.moduro.barrier_free_app.data.datasourceimpl

import com.moduro.barrier_free_app.data.datasource.AuthDataSource
import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.LoginRequestDto
import com.moduro.barrier_free_app.data.dto.request.SignUpRequestDto
import com.moduro.barrier_free_app.data.service.AuthApiService
import kotlinx.serialization.json.JsonElement
import javax.inject.Inject

class AuthDataSourceImpl @Inject constructor(
    private val authApiService: AuthApiService
) : AuthDataSource {
    override suspend fun signup(signUpRequestDto: SignUpRequestDto): ModuroBaseResponse<JsonElement> {
        return authApiService.signup(signUpRequestDto)
    }

    override suspend fun login(loginRequestDto: LoginRequestDto): ModuroBaseResponse<JsonElement> {
        return authApiService.login(loginRequestDto)
    }
}