package com.moduro.barrier_free_app.data.repositoryimpl

import com.moduro.barrier_free_app.data.datasource.AuthDataSource
import com.moduro.barrier_free_app.data.dto.request.FindRequestDto
import com.moduro.barrier_free_app.data.dto.request.LoginRequestDto
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

    override suspend fun login(
        username: String,
        password: String
    ) : Result<JsonElement> {
        return kotlin.runCatching {
            val response = authDataSource.login(
                LoginRequestDto(
                    username = username,
                    password = password
                )
            )
            val result = response.result ?: throw Exception("result is null")

            result
        }
    }

    override suspend fun find(type: String, email: String): Result<JsonElement> {
        return kotlin.runCatching {
            val response = authDataSource.find(
                type = type,
                FindRequestDto(email = email)
            )
            val result = response.result ?: throw Exception("result is null")

            result
        }
    }
}