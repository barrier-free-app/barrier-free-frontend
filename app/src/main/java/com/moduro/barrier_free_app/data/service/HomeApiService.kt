package com.moduro.barrier_free_app.data.service

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.response.ResponseHomeHotPlaceDto
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.PLACES
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.POPULARS
import retrofit2.http.GET

interface HomeApiService {
    @GET("/$PLACES/$POPULARS")
    suspend fun getHotPlaces() : ModuroBaseResponse<List<ResponseHomeHotPlaceDto>>
}