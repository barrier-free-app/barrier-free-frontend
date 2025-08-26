package com.moduro.barrier_free_app.data.repositoryimpl

import com.moduro.barrier_free_app.data.datasource.AuthDataSource
import com.moduro.barrier_free_app.data.dto.request.EmailRequestDto
import com.moduro.barrier_free_app.data.dto.request.LoginRequestDto
import com.moduro.barrier_free_app.data.dto.request.SignUpRequestDto
import com.moduro.barrier_free_app.data.dto.request.TokenRequestDto
import com.moduro.barrier_free_app.data.dto.request.VerifyRequestDto
import com.moduro.barrier_free_app.data.dto.response.LoginResponseDto
import com.moduro.barrier_free_app.data.dto.response.TokenResponseDto
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

    override suspend fun login(dto: LoginRequestDto) : Result<LoginResponseDto> {
        return kotlin.runCatching {
            val response = authDataSource.login(dto)

            if (response.isSuccess && response.result != null) {
                response.result
            } else {
                throw IllegalStateException(response.message)
            }
        }
    }

    override suspend fun find(type: String, email: String): Result<JsonElement> {
        return kotlin.runCatching {
            val response = authDataSource.find(
                type = type,
                EmailRequestDto(email = email)
            )
            val result = response.result ?: throw Exception("result is null")

            result
        }
    }

    override suspend fun send(email: String): Result<JsonElement> {
        return kotlin.runCatching {
            val response = authDataSource.send(
                EmailRequestDto(email = email)
            )
            val result = response.result ?: throw Exception("result is null")

            result
        }
    }

    override suspend fun verify(email: String, verificationCode: String): Result<JsonElement> {
        return kotlin.runCatching {
            val response = authDataSource.verify(
                VerifyRequestDto(email = email, verificationCode = verificationCode)
            )
            val result = response.result ?: throw Exception("result is null")

            result
        }
    }

    override suspend fun duplicate(type: String, input: String): Result<JsonElement> {
        return kotlin.runCatching {
            val response = authDataSource.duplicate(
                type = type,
                input = input
            )
            val result = response.result ?: throw Exception("result is null")

            result
        }
    }

    override suspend fun exchangeToken(dto: TokenRequestDto): Result<TokenResponseDto> {
        return kotlin.runCatching {
            val response = authDataSource.exchangeToken(dto)

            if (response.isSuccess && response.result != null) {
                response.result
            } else {
                throw IllegalStateException(response.message)
            }
        }
    }
}