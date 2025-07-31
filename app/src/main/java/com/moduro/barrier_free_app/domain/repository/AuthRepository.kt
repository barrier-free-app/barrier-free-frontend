package com.moduro.barrier_free_app.domain.repository

import kotlinx.serialization.json.JsonElement

interface AuthRepository {

    suspend fun signup(
        email: String,
        nickname: String,
        username: String,
        password: String,
        verifyPassword: String,
        userType: String,
        userFacilityIds: List<Int>
    ) : Result<JsonElement>

    suspend fun login(
        username: String,
        password: String
    ) : Result<JsonElement>

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
}