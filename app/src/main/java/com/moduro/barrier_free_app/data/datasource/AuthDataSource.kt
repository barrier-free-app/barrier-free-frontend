package com.moduro.barrier_free_app.data.datasource

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.EmailRequestDto
import com.moduro.barrier_free_app.data.dto.request.LoginRequestDto
import com.moduro.barrier_free_app.data.dto.request.SignUpRequestDto
import com.moduro.barrier_free_app.data.dto.request.VerifyRequestDto
import kotlinx.serialization.json.JsonElement

interface AuthDataSource {
    suspend fun signup(signUpRequestDto: SignUpRequestDto) : ModuroBaseResponse<JsonElement>

    suspend fun login(loginRequestDto: LoginRequestDto) : ModuroBaseResponse<JsonElement>

    suspend fun find(type: String, emailRequestDto: EmailRequestDto) : ModuroBaseResponse<JsonElement>

    suspend fun send(emailRequestDto: EmailRequestDto) : ModuroBaseResponse<JsonElement>

    suspend fun verify(verifyRequestDto: VerifyRequestDto) : ModuroBaseResponse<JsonElement>
}