package com.moduro.barrier_free_app.data.service

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.FindRequestDto
import com.moduro.barrier_free_app.data.dto.request.LoginRequestDto
import com.moduro.barrier_free_app.data.dto.request.SignUpRequestDto
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.AUTH
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.FIND
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.LOGIN
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.SIGNUP
import kotlinx.serialization.json.JsonElement
import retrofit2.http.Body
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
    ) : ModuroBaseResponse<JsonElement>

    @POST("/$AUTH/$FIND")
    suspend fun find(
        @Query("type") type: String,
        @Body findRequestDto: FindRequestDto
    ) : ModuroBaseResponse<JsonElement>
}