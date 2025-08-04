package com.moduro.barrier_free_app.data.service

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.EmailRequestDto
import com.moduro.barrier_free_app.data.dto.request.LoginRequestDto
import com.moduro.barrier_free_app.data.dto.request.SignUpRequestDto
import com.moduro.barrier_free_app.data.dto.request.VerifyRequestDto
import com.moduro.barrier_free_app.data.dto.response.LoginResponseDto
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.AUTH
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.EMAIL
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.FIND
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.LOGIN
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.SEND
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.SIGNUP
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.VERIFY
import kotlinx.serialization.json.JsonElement
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {

    @POST("/$AUTH/$SIGNUP")
    suspend fun signup(
        @Body signUpRequestDto: SignUpRequestDto
    ) : ModuroBaseResponse<JsonElement>

    @POST("/$AUTH/$LOGIN")
    suspend fun login(
        @Body loginRequestDto: LoginRequestDto
    ) : ModuroBaseResponse<LoginResponseDto>

    @POST("/$AUTH/$FIND")
    suspend fun find(
        @Query("type") type: String,
        @Body emailRequestDto: EmailRequestDto
    ) : ModuroBaseResponse<JsonElement>

    @POST("/$AUTH/$EMAIL/$SEND")
    suspend fun send(
        @Body emailRequestDto: EmailRequestDto
    ) : ModuroBaseResponse<JsonElement>

    @POST("/$AUTH/$EMAIL/$VERIFY")
    suspend fun verify(
        @Body verifyRequestDto: VerifyRequestDto
    ) : ModuroBaseResponse<JsonElement>

    @GET("/$AUTH/$SIGNUP/$VERIFY")
    suspend fun duplicate(
        @Query("type") type: String,
        @Query("input") input: String
    ) : ModuroBaseResponse<JsonElement>
}