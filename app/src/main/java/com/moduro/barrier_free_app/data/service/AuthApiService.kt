package com.moduro.barrier_free_app.data.service

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.request.SignUpRequestDto
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.AUTH
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.SIGNUP
import kotlinx.serialization.json.JsonElement
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("/$AUTH/$SIGNUP")
    suspend fun signup(
        @Body signUpRequestDto: SignUpRequestDto
    ) : ModuroBaseResponse<JsonElement>
}