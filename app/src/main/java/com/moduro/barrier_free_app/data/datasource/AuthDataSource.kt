package com.moduro.barrier_free_app.data.datasource

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.FindRequestDto
import com.moduro.barrier_free_app.data.dto.request.LoginRequestDto
import com.moduro.barrier_free_app.data.dto.request.SignUpRequestDto
import kotlinx.serialization.json.JsonElement

interface AuthDataSource {
    suspend fun signup(signUpRequestDto: SignUpRequestDto) : ModuroBaseResponse<JsonElement>

    suspend fun login(loginRequestDto: LoginRequestDto) : ModuroBaseResponse<JsonElement>

    suspend fun find(type: String, findRequestDto: FindRequestDto) : ModuroBaseResponse<JsonElement>
}