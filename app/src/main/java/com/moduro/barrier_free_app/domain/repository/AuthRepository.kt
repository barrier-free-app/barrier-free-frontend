package com.moduro.barrier_free_app.domain.repository

import com.moduro.barrier_free_app.data.dto.request.LoginRequestDto
import com.moduro.barrier_free_app.data.dto.request.TokenRequestDto
import com.moduro.barrier_free_app.data.dto.response.LoginResponseDto
import com.moduro.barrier_free_app.data.dto.response.TokenResponseDto
import kotlinx.serialization.json.JsonElement

interface AuthRepository{

    suspend fun signup(
        email: String,
        nickname: String,
        username: String,
        password: String,
        verifyPassword: String,
        userType: String,
        userFacilityIds: List<Int>
    ) : Result<JsonElement>

    suspend fun login(dto: LoginRequestDto) : Result<LoginResponseDto>

    suspend fun find(
        type: String,
        email: String
    ) : Result<JsonElement>

    suspend fun send(
        email: String
    ) : Result<JsonElement>

    suspend fun verify(
        email: String,
        verificationCode: String
    ) : Result<JsonElement>

    suspend fun duplicate(
        type: String,
        input: String
    ) : Result<JsonElement>

    suspend fun exchangeToken(dto: TokenRequestDto) : Result<TokenResponseDto>
}