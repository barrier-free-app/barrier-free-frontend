package com.moduro.barrier_free_app.data.datasourceimpl

import com.moduro.barrier_free_app.data.datasource.AuthDataSource
import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.EmailRequestDto
import com.moduro.barrier_free_app.data.dto.request.LoginRequestDto
import com.moduro.barrier_free_app.data.dto.request.SignUpRequestDto
import com.moduro.barrier_free_app.data.dto.request.VerifyRequestDto
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

    override suspend fun find(type: String, emailRequestDto: EmailRequestDto): ModuroBaseResponse<JsonElement> {
        return authApiService.find(type, emailRequestDto)
    }

    override suspend fun send(emailRequestDto: EmailRequestDto): ModuroBaseResponse<JsonElement> {
        return authApiService.send(emailRequestDto)
    }

    override suspend fun verify(verifyRequestDto: VerifyRequestDto): ModuroBaseResponse<JsonElement> {
        return authApiService.verify(verifyRequestDto)
    }

    override suspend fun duplicate(type: String, input: String): ModuroBaseResponse<JsonElement> {
        return authApiService.duplicate(type, input)
    }
}