package com.moduro.barrier_free_app.data.service

import com.moduro.barrier_free_app.data.dto.ModuroBaseResponse
import com.moduro.barrier_free_app.data.dto.response.ResponseHomeHotPlaceDto
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.PLACES
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.POPULARS
import com.moduro.barrier_free_app.data.service.ApiKeyStorage.RECOMMENDATIONS
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiService {
    @GET("/$PLACES/$POPULARS")
    suspend fun getHotPlaces() : ModuroBaseResponse<List<ResponseHomeHotPlaceDto>>

    @GET("$RECOMMENDATIONS")
    suspend fun getRecommendPlaces(
        @Query("type") type : String?,
        @Query("facilities") facilities : List<Int>?,
    ) : ModuroBaseResponse<List<ResponseHomeHotPlaceDto>>
}