package com.moduro.barrier_free_app.data.service

import com.moduro.barrier_free_app.data.dto.ExampleBaseResponse
import com.moduro.barrier_free_app.data.dto.response.ResponseGetExampleDto
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.API
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.USERS
import retrofit2.http.GET
import retrofit2.http.Query

interface ExampleApiService {
    @GET("/$API/$USERS")
    suspend fun getUsers(
        @Query("page") page: Int
    ): ExampleBaseResponse<List<ResponseGetExampleDto>>
}