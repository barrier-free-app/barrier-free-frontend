package com.moduro.barrier_free_app.data.repositoryimpl

import com.moduro.barrier_free_app.data.datasource.AuthDataSource
import com.moduro.barrier_free_app.data.dto.request.SignUpRequestDto
import com.moduro.barrier_free_app.domain.repository.AuthRepository
import kotlinx.serialization.json.JsonElement
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource
) : AuthRepository {

    override suspend fun signup(
        email: String,
        nickname: String,
        username: String,
        password: String,
        verifyPassword: String,
        userType: String,
        userFacilityIds: List<Int>
    ): Result<JsonElement> {
        return kotlin.runCatching {
            val response = authDataSource.signup(
                SignUpRequestDto(
                    email = email,
                    nickname = nickname,
                    username = username,
                    password = password,
                    verifyPassword = verifyPassword,
                    userType = userType,
                    userFacilityIds = userFacilityIds
                )
            )
            val result = response.result ?: throw Exception("result is null")

            result
        }
    }
}